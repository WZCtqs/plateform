package com.szhbl.project.enquiry.listenner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryOrder;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.handler.common.InquiryProvider;
import com.szhbl.project.inquiry.mapper.BookingInquiryOrderMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryResultService;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import com.szhbl.project.order.domain.BusiOrderTocheckInquiry;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.handler.ToCheckNotice;
import com.szhbl.project.order.mapper.BusiOrderTocheckInquiryMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class XinyoListenner {

    @Autowired
    private IBookingInquiryService iBookingInquiryService;
    @Autowired
    private IBookingInquiryResultService iBookingInquiryResultService;

    @Autowired
    BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    BookingInquiryOrderMapper bookingInquiryOrderMapper;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private InquiryProvider inquiryProvider;
    @Autowired
    ToCheckNotice toCheckNotice;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BusiOrderTocheckInquiryMapper busiOrderTocheckInquiryMapper;

//    @RabbitListener(queues = "xxyo_return_inquiry_queue")
    public void orderInfoListener(Channel channel, Message message) {
        try {
            log.debug("箱型亚欧系统发来的数据-------------------------询价消息队列");
            log.debug(message.toString());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            String body = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            Map map = message.getMessageProperties().getHeaders();
            String inquiryId = map.get("mqid").toString();
            String price = map.get("price").toString();
            String xxyoRemark = String.valueOf(map.get("xxyoRemark"));
            String isNormal = map.get("isNormal").toString();
            // 询价编号
            String inquiryNumber = String.valueOf(map.get("inquiryNumber"));

            //处理重复询价
            /*BookingInquiryResult checkBookingInquiryResult = new BookingInquiryResult();
            checkBookingInquiryResult.setAgainInquiryId(Long.parseLong(inquiryId));
            List<BookingInquiryResult> birList = iBookingInquiryResultService.selectBookingInquiryResultList(checkBookingInquiryResult);
            List<String> inquirIdList = new ArrayList<>();
            //查询到待更新的重复询价
            if(StringUtils.isNotEmpty(birList)){
                inquirIdList.add(inquiryId);
                for(int n=0;n<birList.size();n++){
                    inquirIdList.add(birList.get(n).getInquiryId().toString());
                }
            }
            for(int m=0;m<inquirIdList.size();m++){
                inquiryId=inquirIdList.get(m);
                //添加处理逻辑

            }*/

            /*
            //接收箱型亚欧数据，更新
            BookingInquiry bookingInquiry = FastJsonUtils.json2Bean(body, BookingInquiry.class);*/
            /*--------------------------------更新询价结果反馈表,-------------------------------------*/
            BookingInquiry bookingInquiry = iBookingInquiryService.selectBookingInquiryById(Long.parseLong(inquiryId));
            // 查询结果表
            List<BookingInquiryResult> bookingInquiryResults =
                    iBookingInquiryResultService.selectBookingInquiryResultWithInquiryId(Long.parseLong(inquiryId));

           // String lineType = bookingInquiry.getLineType();//线路
            BookingInquiryResult  bookingInquiryResult = new BookingInquiryResult();
         //   BeanUtils.copyProperties(bookingInquiry,bookingInquiryResult);
            //更新询价结果反馈表
            //查询询价结果反馈表是否有inquired信息
            if(StringUtils.isNull(bookingInquiry)){

            }else {
                bookingInquiryResult.setXxyoRemark(xxyoRemark);
                bookingInquiryResult.setInquiryId(Long.parseLong(inquiryId));
                if (isNormal.equals("1")) {
                    if (StringUtils.isNotEmpty(bookingInquiry.getOrderId())) {
                        if (bookingInquiry.getInquiryState().equals("4")) {
                            log.info("转待询价接收公路报价-->公路驳回-->询价已被驳回，不做处理");
                        } else {
                            Map IsExamline = busiShippingorderService.selectIsExamlineByOrderId(bookingInquiry.getOrderId());
                            if (IsExamline.get("IsExamline").equals("14")) {
                                BusiShippingorders busiShippingorders = new BusiShippingorders();
                                busiShippingorders.setOrderId(bookingInquiry.getOrderId());
                                busiShippingorders.setIsexamline("3");
                                if (IsExamline.get("IsConsolidation").equals("0")) {
                                    busiShippingorders.setContainerBoxamount("0");
                                }
                                busiShippingorderMapper.updateBusiShippingorder(busiShippingorders);
                                ShippingOrder orderInfoRabbmq = busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                                if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                    String messagetype = "5"; //转待审核失败
                                    try {
                                        commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
                                    } catch (JsonProcessingException e) {
                                        log.error("处理公路报价信息失败：{}", e.toString(), e.getStackTrace());
                                    }
                                }
                            } else {
                                log.info("转待询价接收公路报价-->公路驳回");
                                // 转待审核报价
                                // 如果集输报价驳回，则转待审核申请不通过，修改托书状态为--->草稿状态
                                busiShippingorderService.updateIsExamlineByOrderId(bookingInquiry.getOrderId(), "5");
                                busiShippingorderService.updateOrderExam(
                                        bookingInquiry.getOrderId(), bookingInquiryResult.getXxyoRemark());

                                toCheckNotice.noticeSonSystem(bookingInquiry.getOrderId(), "5", "箱型亚欧系统驳回转待审核询价");
                                commonService.deleteTem(bookingInquiry.getOrderId());
                                ShippingOrder orderInfoRabbmq =
                                        busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                                if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                    String messagetype = "5"; // 转待审核失败
                                    commonService.orderInfoMQ(orderInfoRabbmq, messagetype); // 推送消息队列
                                }
                                /*询价改成原来的*/
                                BookingInquiryOrder inquiryOrder =
                                        bookingInquiryOrderMapper.selectPreInquiryOrder(bookingInquiry.getOrderId());
                                if (inquiryOrder != null) {
                                    busiShippingorderMapper.updateInquiryResultId(
                                            bookingInquiry.getOrderId(), inquiryOrder.getInquiryResultId());
                                }
                            }
                        }
                    }
                    bookingInquiry.setInquiryState("4");
                    bookingInquiry.setTurndownReason(xxyoRemark);
                } else {
                    bookingInquiryResult.setEnquiryState("3");
                    if (StringUtils.isNotEmpty(bookingInquiry.getOrderId())) {
                        if (bookingInquiry.getInquiryState().equals("4")) {
                            log.info("转待询价接收公路报价-->询价已被驳回，不做处理");
                        } else {
                            log.info("转待询价接收公路报价-->处理报价");
                            // 如果集输报价成功，修改托书状态为--->草稿状态
                            // 先判断是否已经为驳回状态
//                            busiShippingorderService.updateIsExamlineButNotBH(bookingInquiry.getOrderId(), "9");
                            ShippingOrder orderInfoRabbmq =
                                    busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                            if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                String messagetype = "4";
                                commonService.orderInfoMQ(orderInfoRabbmq, messagetype); // 推送消息队列
                            }
                            //                        toCheckNotice.noticeSonSystem(bookingInquiry.getOrderId(),
                            // "10", "集输系统报价转待审核询价");
                            /*整柜时 判断还箱地是否更改*/
                            if ("0".equals(bookingInquiry.getGoodsType()) && "1".equals(bookingInquiry.getEastOrWest())) {
                                /*获取是否还需要箱管审核还箱地，0是需要（散货改整柜派送）。1是不需要*/
                                BusiOrderTocheckInquiry tocheckInquiry = busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(bookingInquiry.getOrderId());
                                if (String.valueOf(tocheckInquiry.getHxdXgExamine()).equals("0")) {
                                    String oldHxd =
                                            iBookingInquiryResultService.getLastInquiryHxd(bookingInquiry.getOrderId());
                                    log.info("原还箱地：" + oldHxd);
                                    log.info("现还箱地：" + bookingInquiryResult.getHxAddress());
                                    if (!(oldHxd + "").equals(bookingInquiryResult.getHxAddress() + "")) {
                                        log.error("还箱地更改，向箱管部审核");
                                        ShippingOrder shippingOrder =
                                                busiShippingorderMapper.selectBusiShippingorderTemById(
                                                        bookingInquiry.getOrderId());
                                        shippingOrder.setHxdExamine(String.valueOf(tocheckInquiry.getHxdXgExamine()));
                                        shippingOrder.setType("0");
                                        shippingOrder.setInquiryId(bookingInquiry.getId());
                                        shippingOrder.setReceiveHxAddress(bookingInquiryResult.getHxAddress());
                                        inquiryProvider.xgPush(shippingOrder);
                                        /*托书状态改为箱管审核中*/
                                        BusiShippingorders orderupd = new BusiShippingorders();
                                        orderupd.setOrderId(bookingInquiry.getOrderId());
                                        orderupd.setIsexamline("7");
                                        busiShippingorderMapper.updateBusiShippingorder(orderupd);
                                        bookingInquiry.setInquiryState("1");
                                    }
                                }
                            }
                        }
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
                        c.add(Calendar.MONTH, 1);//计算一个月后的时间
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
                        c.add(Calendar.MONTH, 1);//计算一个月后的时间
                        bookingInquiryResult.setDeliveryExpiration(c.getTime());
                    }
                    if (StringUtils.isNotEmpty(inquiryNumber)) {
                        bookingInquiryResult.setInquiryNumber(inquiryNumber);
                    }

                }
                    iBookingInquiryResultService.updateBookingInquiryResultWithInquiryId(bookingInquiryResult);
                    iBookingInquiryService.updateBookingInquiry(bookingInquiry);
            }
        }catch (IOException e) {
            log.error("箱型亚欧系统发来的数据处理失败：{}",e.toString(),e.getStackTrace());
        }
    }

}
