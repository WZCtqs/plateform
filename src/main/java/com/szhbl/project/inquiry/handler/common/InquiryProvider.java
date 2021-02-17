package com.szhbl.project.inquiry.handler.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szhbl.common.enums.LanguageEnum;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.inquiry.GnczInquiryConfig;
import com.szhbl.framework.config.rabbit.inquiry.JsInquiryConfig;
import com.szhbl.framework.config.rabbit.inquiry.XXYOInquiryConfig;
import com.szhbl.framework.config.rabbit.inquiry.XgCheckConfig;
import com.szhbl.project.inquiry.domain.*;
import com.szhbl.project.inquiry.dto.BookingInquiryGlCheck;
import com.szhbl.project.inquiry.dto.BookingInquiryJsCheck;
import com.szhbl.project.inquiry.dto.JsComeBookingInquiryResult;
import com.szhbl.project.inquiry.dto.JsGoBookingInquiryResult;
import com.szhbl.project.inquiry.mapper.BookingInquiryMapper;
import com.szhbl.project.inquiry.mapper.BookingInquiryOrderMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryResultService;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.handler.ToCheckNotice;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
// log.error("询价信息------箱型亚欧发送失败：{}",e.toString(),e.getStackTrace());
@Component
public class InquiryProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CommonHandlerService commonHandlerService;
    @Autowired
    private IBookingInquiryService iBookingInquiryService;
    @Autowired
    private BookingInquiryMapper bookingInquiryMapper;
    @Autowired
    private IBookingInquiryResultService iBookingInquiryResultService;
    @Autowired
    private QuotationProcessService quotationProcessService;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    ToCheckNotice toCheckNotice;
    @Autowired
    private CommonService commonService;
    @Autowired
    BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    BookingInquiryOrderMapper bookingInquiryOrderMapper;

    /**
     * 消息发送到箱型亚欧
     * @param bookingInquiry
     * @return
     */
    public Boolean xxyoPush(BookingInquiry bookingInquiry){
        // 消息发送到箱型亚欧
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessageProperties header = new MessageProperties();
        header.getHeaders().put("__TypeId__","Object");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BookingInquiry bi = new BookingInquiry();
            BeanUtils.copyProperties(bookingInquiry,bi);

            if (bi.getEastOrWest().equals("0")){
                String area = "";
                if (StringUtils.isNotEmpty(bi.getSenderArea())) {
                    area = bi.getSenderArea();
                }
                bi.setShipmentPlace(
                        bi.getSenderProvince()+
                        bi.getSenderCity()+
                                area+
                        bi.getShipmentPlace()
                );
            } else {
                String area = "";
                if (StringUtils.isNotEmpty(bi.getReceiveArea())) {
                    area = bi.getReceiveArea();
                }
                bi.setReceiptPlace(
                        bi.getReceiveProvince()+
                        bi.getReceiveCity()+
                                area+
                        bi.getReceiptPlace()
                );
            }
            // 用计费体积替换总体积
            //bi.setTotalVolume(commonHandlerService.getBillableVolume(bi).toString());换成总体积，不使用计费体积2020.7.8
            // 拼接货物详情
            // 尺寸（拼接 数量/重量/长*宽*高/）
            List<BookingInquiryGoodsDetails> goodsDetails = bi.getBookingInquiryGoodsDetailsList();
            StringBuilder sb = new StringBuilder();
            goodsDetails.forEach(goodsDetail->{
                sb.append("品名:")
                        .append(goodsDetail.getGoodsName()).append("; ")
                        .append(goodsDetail.getGoodsAmount()).append("件/")
                        .append(goodsDetail.getGoodsWeight()).append("kg/")
                        .append(goodsDetail.getGoodsLength()).append("cm*")
                        .append(goodsDetail.getGoodsWidth()).append("cm*")
                        .append(goodsDetail.getGoodsHeight()).append("cm;\r\n");
            });
            bi.setGoodsDetails(sb.toString());
            Message message = new Message(objectMapper.writeValueAsBytes(bi), header);
            rabbitTemplate.convertAndSend(XXYOInquiryConfig.XXYO_SYSTEM__INQUIRY_EXCHANGE, "xxyo.topic.inquiry.add",message,correlationData);
            log.debug("询价信息------箱型亚欧发送成功：{}",bi);
        }catch (Exception e){
            log.error("询价信息------箱型亚欧发送失败：{}",e.toString(),e.getStackTrace());
        }
        return Boolean.TRUE;
    }

    /**
     * 消息发送到箱型亚欧
     * @param bookingInquiry
     * @return
     */
/*    public Boolean xxyoPush(BookingInquiry bookingInquiry) {
        //发送之前进行判断，30天内同一个数据已发送不会再进行发送
        //根据询价数据查询是否有这条询价
        //checkBookingInquiry 应该只含有影响公路报价的数据
        BookingInquiryGlCheck bookingInquiryGlCheck = new BookingInquiryGlCheck();
        BeanUtils.copyProperties(bookingInquiry, bookingInquiryGlCheck);
        //去程提取发货地
        if ("0".equals(bookingInquiryGlCheck.getEastOrWest())) {
            bookingInquiryGlCheck.setReceiptPlace(null);
        } else {
            bookingInquiryGlCheck.setShipmentPlace(null);
        }
        BookingInquiry checkBookingInquiry = new BookingInquiry();
        BeanUtils.copyProperties(bookingInquiryGlCheck, checkBookingInquiry);
        //设置当前时间30天前时间
        checkBookingInquiry.setInquiryStartTime(DateUtils.parseStr(DateUtils.getNextDay(bookingInquiry.getInquiryTime(), -30)));
        checkBookingInquiry.setInquiryState("3");
        List<BookingInquiry> list = bookingInquiryMapper.selectBookingInquiryList(checkBookingInquiry);
        log.debug("查询到之前的箱行亚欧询价list------"+list.size()+"**********"+list);
        //集合不为空不需要在进行询价
        if(StringUtils.isNotEmpty(list)){
            //查询之前的询价反馈结果
            List<BookingInquiryResult> oldBookingInquiryResultList = iBookingInquiryResultService.selectBookingInquiryResultWithInquiryId(list.get(0).getId());
            try{
                for(int i=0;i<oldBookingInquiryResultList.size();i++){
                    String inquiryId = bookingInquiry.getId().toString();
                    String price = "0".equals(bookingInquiry.getEastOrWest())?oldBookingInquiryResultList.get(i).getPickUpFees():oldBookingInquiryResultList.get(i).getDeliveryFees();
                    String xxyoRemark = oldBookingInquiryResultList.get(i).getXxyoRemark();
                    String isNormal =oldBookingInquiryResultList.get(i).getIsNormal();
                    // 询价编号
                    String inquiryNumber = oldBookingInquiryResultList.get(i).getInquiryNumber();
            //接收箱型亚欧数据，更新
            // BookingInquiry bookingInquiry = FastJsonUtils.json2Bean(body, BookingInquiry.class);
                    //--------------------------------更新询价结果反馈表,-------------------------------------
                    //BookingInquiry bookingInquiry = iBookingInquiryService.selectBookingInquiryById(Long.parseLong(inquiryId));
                    // 查询结果表
                    List<BookingInquiryResult> bookingInquiryResults = iBookingInquiryResultService.selectBookingInquiryResultWithInquiryId(Long.parseLong(inquiryId));
                    // String lineType = bookingInquiry.getLineType();//线路
                    BookingInquiryResult  bookingInquiryResult = new BookingInquiryResult();
                    //   BeanUtils.copyProperties(bookingInquiry,bookingInquiryResult);
                    //更新询价结果反馈表
                    //查询询价结果反馈表是否有inquired信息
                    bookingInquiryResult.setXxyoRemark(xxyoRemark);
                    bookingInquiryResult.setInquiryId(Long.parseLong(inquiryId));
                    if (isNormal.equals("1")) {
                        bookingInquiry.setInquiryState("4");
                        bookingInquiry.setTurndownReason(xxyoRemark);
                        if (StringUtils.isNotEmpty(bookingInquiry.getOrderId())) {
                            //转待审核报价
                            // 如果集输报价驳回，则转待审核申请不通过，修改托书状态为--->草稿状态
                            busiShippingorderService.updateIsExamlineByOrderId(bookingInquiry.getOrderId(), "5");
                            busiShippingorderService.updateOrderExam(bookingInquiry.getOrderId(), bookingInquiryResult.getXxyoRemark());

                            toCheckNotice.noticeSonSystem(bookingInquiry.getOrderId(), "5", "箱型亚欧系统驳回转待审核询价");
                            commonService.deleteTem(bookingInquiry.getOrderId());
                            ShippingOrder orderInfoRabbmq = busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                            if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                String messagetype = "5"; //转待审核失败
                                commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
                            }
                            //询价改成原来的
                            BookingInquiryOrder inquiryOrder = bookingInquiryOrderMapper.selectPreInquiryOrder(bookingInquiry.getOrderId());
                            if (inquiryOrder != null) {
                                busiShippingorderMapper.updateInquiryResultId(
                                        bookingInquiry.getOrderId(), inquiryOrder.getInquiryResultId());
                            }
                        }
                    } else {
                        bookingInquiryResult.setEnquiryState("3");
                        if (StringUtils.isNotEmpty(bookingInquiry.getOrderId())) {
                            // 如果集输报价成功，修改托书状态为--->草稿状态
                            // 先判断是否已经为驳回状态
                            busiShippingorderService.updateIsExamlineButNotBH(bookingInquiry.getOrderId(), "9");
                           //toCheckNotice.noticeSonSystem(bookingInquiry.getOrderId(), "10", "集输系统报价转待审核询价");
                        }
                        if (bookingInquiry.getEastOrWest().equals("0")) {
                            if (StringUtils.isNotEmpty(bookingInquiryResults.get(0).getDeliveryFees()) ||
                                    bookingInquiry.getBookingService().equals("1")) {
                                if (!"3".equals(bookingInquiry.getInquiryState())) {
                                    bookingInquiry.setInquiryState("2");
                                }
                            }
                            //去程
                            bookingInquiryResult.setPickUpFees(price);
                            bookingInquiryResult.setPickUpCurrencyType("￥");
                            bookingInquiryResult.setPickUpFeedbackTime(new Date());
                            // 有效期默认一个月
                            Calendar c = Calendar.getInstance();
                            c.add(Calendar.DATE, 30);//计算30天后的时间
                            bookingInquiryResult.setPickUpExpiration(c.getTime());

                        } else {
                            if (StringUtils.isNotEmpty(bookingInquiryResults.get(0).getPickUpFees()) ||
                                    bookingInquiry.getBookingService().equals("3")) {
                                if(!"3".equals(bookingInquiry.getInquiryState())){
                                    bookingInquiry.setInquiryState("2");
                                }
                            }
                            bookingInquiryResult.setDeliveryCurrencyType("￥");
                            bookingInquiryResult.setDeliveryFees(price);
                            bookingInquiryResult.setDeliveryFeedbackTime(new Date());
                            Calendar c = Calendar.getInstance();
                            c.add(Calendar.DATE, 30);//计算30天后的时间
                            bookingInquiryResult.setDeliveryExpiration(c.getTime());
                        }
                        if (StringUtils.isNotEmpty(inquiryNumber)) {
                            bookingInquiryResult.setInquiryNumber(inquiryNumber);
                        }

                    }
                    iBookingInquiryResultService.updateBookingInquiryResultWithInquiryId(bookingInquiryResult);
                    iBookingInquiryService.updateBookingInquiry(bookingInquiry);
                }
            }catch(IOException e){
                log.error("箱型亚欧系统询价结果数据处理失败：{}",e.toString(),e.getStackTrace());
            }
        }
        //集合为空直接进行询价
        else{
            checkBookingInquiry.setInquiryState(null);
            List<BookingInquiry> biList=bookingInquiryMapper.selectBookingInquiryList(checkBookingInquiry);
            if(StringUtils.isNotEmpty(biList)){
                //查询该询价对应询价结果
                List<BookingInquiryResult> birList = iBookingInquiryResultService.selectBookingInquiryResultWithInquiryId(bookingInquiry.getId());
                for(int n=0;n<birList.size();n++){
                    birList.get(n).setAgainInquiryId(list.get(list.size()-1).getId());
                    iBookingInquiryResultService.updateBookingInquiryResult(birList.get(n));
                }
            }
            else{
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                MessageProperties header = new MessageProperties();
                header.getHeaders().put("__TypeId__","Object");
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    BookingInquiry bi = new BookingInquiry();
                    BeanUtils.copyProperties(bookingInquiry,bi);
                    if (bi.getEastOrWest().equals("0")){
                        String area = "";
                        if (StringUtils.isNotEmpty(bi.getSenderArea())) {
                            area = bi.getSenderArea();
                        }
                        bi.setShipmentPlace(
                                bi.getSenderProvince()+
                                        bi.getSenderCity()+
                                        area+
                                        bi.getShipmentPlace()
                        );
                    } else {
                        String area = "";
                        if (StringUtils.isNotEmpty(bi.getReceiveArea())) {
                            area = bi.getReceiveArea();
                        }
                        bi.setReceiptPlace(
                                bi.getReceiveProvince()+
                                        bi.getReceiveCity()+
                                        area+
                                        bi.getReceiptPlace()
                        );
                    }
                    // 用计费体积替换总体积
                    //bi.setTotalVolume(commonHandlerService.getBillableVolume(bi).toString());换成总体积，不使用计费体积2020.7.8
                    // 拼接货物详情
                    // 尺寸（拼接 数量/重量/长*宽*高/）
                    List<BookingInquiryGoodsDetails> goodsDetails = bi.getBookingInquiryGoodsDetailsList();
                    StringBuilder sb = new StringBuilder();
                    goodsDetails.forEach(goodsDetail->{
                        sb.append("品名:")
                                .append(goodsDetail.getGoodsName()).append("; ")
                                .append(goodsDetail.getGoodsAmount()).append("件/")
                                .append(goodsDetail.getGoodsWeight()).append("kg/")
                                .append(goodsDetail.getGoodsLength()).append("cm*")
                                .append(goodsDetail.getGoodsWidth()).append("cm*")
                                .append(goodsDetail.getGoodsHeight()).append("cm;\r\n");
                    });
                    bi.setGoodsDetails(sb.toString());
                    Message message = new Message(objectMapper.writeValueAsBytes(bi), header);
                    rabbitTemplate.convertAndSend(XXYOInquiryConfig.XXYO_SYSTEM__INQUIRY_EXCHANGE, "xxyo.topic.inquiry.add",message,correlationData);
                    log.debug("询价信息------箱型亚欧发送成功：{}",bi);
                }catch (Exception e){
                    log.error("询价信息------箱型亚欧发送失败：{}",e.toString(),e.getStackTrace());
                }
            }
        }
        return Boolean.TRUE;
    }*/


    /**
     * 消息发送到集疏
     * @param bookingInquiry
     * @return
     */
/*    public Boolean jsPush(BookingInquiry bookingInquiry){
        //发送之前进行判断，30天内同一个数据已发送不会再进行发送
        //根据询价数据查询是否有这条询价
        //checkBookingInquiry 应该只含有影响集疏报价的数据
        BookingInquiryJsCheck bookingInquiryJsCheck=new BookingInquiryJsCheck();
        BeanUtils.copyProperties(bookingInquiry,bookingInquiryJsCheck);
        //去程提取收货地
        if("0".equals(bookingInquiryJsCheck.getEastOrWest())){
            bookingInquiryJsCheck.setShipmentPlace(null);
        }else{
            bookingInquiryJsCheck.setReceiptPlace(null);
        }
        BookingInquiry checkBookingInquiry=new BookingInquiry();
        BeanUtils.copyProperties(bookingInquiryJsCheck,checkBookingInquiry);
        //设置当前时间30天前时间
        checkBookingInquiry.setInquiryStartTime(DateUtils.parseStr(DateUtils.getNextDay(bookingInquiry.getInquiryTime(),-30)));
        checkBookingInquiry.setInquiryState("3");
        //1.已报过价的 已经报过价的直接给出价格
        List<BookingInquiry> alreadyList=bookingInquiryMapper.selectBookingInquiryList(checkBookingInquiry);
        log.debug("查询到报价完成的集疏询价list------"+alreadyList.size()+"**********"+alreadyList);
        //alreadyList集合不为空不需要在进行询价
        if(StringUtils.isNotEmpty(alreadyList)){
            //查询之前的询价反馈结果
            List<BookingInquiryResult> oldBookingInquiryResultList = iBookingInquiryResultService.selectBookingInquiryResultWithInquiryId(alreadyList.get(0).getId());
            BookingInquiryResult bookingInquiryResult=null;
            for(int i=0;i<oldBookingInquiryResultList.size();i++){
                bookingInquiryResult =new BookingInquiryResult();
                if("0".equals(bookingInquiry.getEastOrWest())){
                    JsGoBookingInquiryResult goBookingInquiryResult=new JsGoBookingInquiryResult();
                    BeanUtils.copyProperties(oldBookingInquiryResultList.get(i),goBookingInquiryResult);
                    BeanUtils.copyProperties(goBookingInquiryResult,bookingInquiryResult);
                    //集疏还未报价，又有新的询价,标记该结果，询价结果来的时候进行更新
                    if(StringUtils.isEmpty(bookingInquiryResult.getDeliveryFees())){

                    }
                }else if("1".equals(bookingInquiry.getEastOrWest())){
                    JsComeBookingInquiryResult comeBookingInquiryResult=new JsComeBookingInquiryResult();
                    BeanUtils.copyProperties(oldBookingInquiryResultList.get(i),comeBookingInquiryResult);
                    BeanUtils.copyProperties(comeBookingInquiryResult,bookingInquiryResult);
                    //集疏还未报价，又有新的询价，标记该结果，询价结果来的时候进行更新
                    if(StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())){

                    }
                }
                bookingInquiryResult.setInquiryId(bookingInquiry.getId());
                bookingInquiryResult.setIsNormal("0");
                bookingInquiryResult.setBidder(bookingInquiryResult.getJsBidder());
                //集疏整柜报价除以箱量作为价格 去程 pickUpFees公路 deliveryFees集疏
                //整柜
                if ("0".equals(bookingInquiry.getGoodsType())) {
                    //去程
                    if ("0".equals(bookingInquiry.getEastOrWest())) {
                        bookingInquiryResult.setDeliveryFees(new BigDecimal(bookingInquiryResult.getDeliveryFees()).divide(new BigDecimal(bookingInquiry.getContainerNum())).toString());
                    }
                    //回程
                    else if ("1".equals(bookingInquiry.getEastOrWest()) && "0".equals(bookingInquiry.getDeliveryType())) {
                        bookingInquiryResult.setPickUpFees(new BigDecimal(bookingInquiryResult.getPickUpFees()).divide(new BigDecimal(bookingInquiry.getContainerNum())).toString());
                    }
                }
                quotationProcessService.handlerJs(bookingInquiryResult);
            }
        }
        //集合为空根据情况进行处理
        else{
            checkBookingInquiry.setInquiryState(null);
            List<BookingInquiry> list=bookingInquiryMapper.selectBookingInquiryList(checkBookingInquiry);
            //2.未完成报过价的 多个询价都未报价，第一个发送，其余的不发送，询价结果表做标记 list倒序，获取最早的一个list.size()-1
            if(StringUtils.isNotEmpty(list)){
                //查询该询价对应询价结果
                List<BookingInquiryResult> birList = iBookingInquiryResultService.selectBookingInquiryResultWithInquiryId(bookingInquiry.getId());
                for(int n=0;n<birList.size();n++){
                    birList.get(n).setAgainInquiryId(list.get(list.size()-1).getId());
                    iBookingInquiryResultService.updateBookingInquiryResult(birList.get(n));
                }
            }
            //未查询到符合条件的询价信息
            else{
                rabbitTemplate.convertAndSend(JsInquiryConfig.JS_SYSTEM__INQUIRY_EXCHANGE, "js.system.topic.inquiry.add", bookingInquiry);
                log.debug("询价信息------集疏系统发送成功：{}",bookingInquiry);
            }
        }
        return Boolean.TRUE;
    }*/

    /**
     * 消息发送到集疏
     * @param bookingInquiry
     * @return
     */
   public Boolean jsPush(BookingInquiry bookingInquiry){
       BookingInquiry bi = new BookingInquiry();
       BeanUtils.copyProperties(bookingInquiry,bi);
       // 自备箱包含提、还箱地
       if (LanguageEnum.英文.value.equals(bi.getLanguage()) && "0".equals(bi.getGoodsType()) &&
              "1".equals(bi.getContainerBelong())){
            if ("0".equals(bi.getEastOrWest())) {
                // 去程还箱
                bi.setHxAddress(commonHandlerService.getAddressName(bi.getHxAddress()));
            } else {
                // 回程提箱
                bi.setTxAddress(commonHandlerService.getAddressName(bi.getTxAddress()));
            }
       }
       rabbitTemplate.convertAndSend(JsInquiryConfig.JS_SYSTEM__INQUIRY_EXCHANGE, "js.system.topic.inquiry.add", bi);
       log.debug("询价信息------集疏系统发送成功：{}",bookingInquiry);
       return Boolean.TRUE;
    }
    /**
     * 消息发送到国内场站
     * @param bookingInquiry
     * @return
     */
    public Boolean gnczPush(BookingInquiry bookingInquiry){

        // 添加推送判断
        if (!commonHandlerService.isNeedDomesticOrder(bookingInquiry)) {
            log.debug("询价信息------不需要国内场站报价，询价id：{}",bookingInquiry.getId());
            return Boolean.TRUE;
        }
        rabbitTemplate.convertAndSend(GnczInquiryConfig.GNCZ_SYSTEM_INQUIRY_EXCHANGE, "gncz.system.topic.inquiry.add", bookingInquiry);
        log.debug("询价信息------国内场站发送成功：{}",bookingInquiry);
        return Boolean.TRUE;
    }

    /**
     * 消息发送到箱管
     * @param shippingOrder
     * @return
     */
    public Boolean xgPush(ShippingOrder shippingOrder){
        rabbitTemplate.convertAndSend(XgCheckConfig.XG_SYSTEM_CHECK_EXCHANGE, "xg.system.topic.check.add", shippingOrder);
        log.debug("审核信息-----箱管发送成功：{}",shippingOrder);
        return Boolean.TRUE;
    }

    /**
     * 已发送客户的询价编码通知到集疏
     *
     * @param number
     * @return
     */
    public Boolean jsSendQuoter(String number){
        rabbitTemplate.convertAndSend(JsInquiryConfig.JS_SEND_QUOTER_EXCHANGE, JsInquiryConfig.JS_SEND_QUOTER_ROUTINGKEY, number);
        log.debug("已发送客户的询价编码通知到集疏：{}",number);
        return Boolean.TRUE;
    }
}
