package com.szhbl.project.inquiry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.exception.BaseException;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.basic.domain.BaseGoodsnote;
import com.szhbl.project.basic.domain.DangerousGoods;
import com.szhbl.project.basic.mapper.BaseGoodsnoteMapper;
import com.szhbl.project.basic.mapper.DangerousGoodsMapper;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryOrder;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.handler.common.CommonHandlerService;
import com.szhbl.project.inquiry.handler.service.ZeCalculateService;
import com.szhbl.project.inquiry.handler.service.ZoCalculateService;
import com.szhbl.project.inquiry.handler.service.ZyCalculateService;
import com.szhbl.project.inquiry.handler.service.ZynCalculateService;
import com.szhbl.project.inquiry.handler.thread.ZeHandler;
import com.szhbl.project.inquiry.handler.thread.ZoHandler;
import com.szhbl.project.inquiry.handler.thread.ZyHandler;
import com.szhbl.project.inquiry.handler.thread.ZynHandler;
import com.szhbl.project.inquiry.mapper.BookingInquiryGoodsDetailsMapper;
import com.szhbl.project.inquiry.mapper.BookingInquiryMapper;
import com.szhbl.project.inquiry.mapper.BookingInquiryOrderMapper;
import com.szhbl.project.inquiry.mapper.BookingInquiryResultMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.handler.ToCheckNotice;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 订舱询价Service业务层处理
 *
 * @author jhm
 * @date 2020-04-01
 */
@Service
@Slf4j
public class BookingInquiryServiceImpl implements IBookingInquiryService {
    @Autowired
    private BookingInquiryMapper bookingInquiryMapper;
    @Autowired
    private BookingInquiryGoodsDetailsMapper bookingInquiryGoodsDetailsMapper;
    @Autowired
    private BookingInquiryResultMapper bookingInquiryResultMapper;
    @Autowired
    private DangerousGoodsMapper dangerousGoodsMapper;
    @Autowired
    private BaseGoodsnoteMapper baseGoodsnoteMapper;
    @Autowired
    private ZoCalculateService zoCalculateService;
    @Autowired
    private ZeCalculateService zeCalculateService;
    @Autowired
    private ZyCalculateService zyCalculateService;
    @Autowired
    private ZynCalculateService zynCalculateService;
    @Autowired
    BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    BookingInquiryOrderMapper bookingInquiryOrderMapper;
    @Autowired
    ToCheckNotice toCheckNotice;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CommonHandlerService commonHandlerService;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    /**
     * 查询订舱询价
     *
     * @param id 订舱询价ID
     * @return 订舱询价
     */
    @Override
    public BookingInquiry selectBookingInquiryById(Long id) {
        return bookingInquiryMapper.selectBookingInquiryById(id);
    }

    /**
     * 查询订舱询价列表
     *
     * @param bookingInquiry 订舱询价
     * @return 订舱询价
     */
    @Override
    public List<BookingInquiry> selectBookingInquiryList(BookingInquiry bookingInquiry) {
        //对接箱型亚欧、集输接口
        String deptCode = bookingInquiry.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (bookingInquiry.getDeptCode().contains("YWB")) {
                if (bookingInquiry.getDeptCode().length() > 15) {
                    bookingInquiry.setReadType("0");
                } else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    bookingInquiry.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    bookingInquiry.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    bookingInquiry.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    bookingInquiry.setReadType("5");
                } else {
                    bookingInquiry.setReadType("1");
                }
            }
        }
        return bookingInquiryMapper.selectBookingInquiryList(bookingInquiry);
    }

    /**
     * 新增订舱询价
     *
     * @param bookingInquiry 订舱询价
     * @return 结果
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public AjaxResult insertBookingInquiry(BookingInquiry bookingInquiry) {
        List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList = bookingInquiry.getBookingInquiryGoodsDetailsList();
        bookingInquiry.setInquiryTime(new Date());//询价时间

        String lineType = bookingInquiry.getLineType(); // 线路
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        // 判断中亚、中越20尺箱数量
        if ((lineType.equals("2") || lineType.equals("3")) && goodsType.equals("0") && bookingInquiry.getContainerType().startsWith("20")) {
            int num = bookingInquiry.getContainerNum();
            if (num % 2 != 0) {
                return AjaxResult.error("该线路20尺箱需订双数，请确认");
            }
        }
        // 危险品
        StringBuilder sb0 = new StringBuilder();
        // 疑似危险品
        StringBuilder sb1 = new StringBuilder();
        //特殊单证类型货物
        StringBuilder sb2 = new StringBuilder();
        // 是否超长超重（长11m,   宽 2.2m  , 高2.5，重量超过15吨）
        Boolean over = false;
        // 货物包装方式是裸装的
        Boolean isNaked = false;
        // 判断二级站货物尺寸重量是否可操作
        Boolean stationIsOk = false;
        if ("1".equals(goodsType) ||
            ("0".equals(goodsType) &&
                (
                    "1".equals(bookingInquiry.getDeliveryType()) ||
                    "0".equals(bookingInquiry.getDeliverySelfType()) ||
                    "1".equals(bookingInquiry.getDistributionType())
                )
            )
        ) {
            if (bookingInquiryGoodsDetailsList.size() == 0) {
                return AjaxResult.error("en".equals(bookingInquiry.getLanguage()) ? "The required data for LCL does not exist, please confirm that it has been filled in": "拼箱必填数据不存在，请确认已填写");
            } else {
                if ("裸装".equals(bookingInquiry.getPackageType()) || "Nude".equals(bookingInquiry.getPackageType())) {
                    isNaked = true;
                }
                for (BookingInquiryGoodsDetails bookingInquiryGoodsDetails : bookingInquiryGoodsDetailsList) {
                    if (StringUtils.isNotEmpty(bookingInquiryGoodsDetails.getGoodsName()) ){
                        DangerousGoods dangerousGoods = isDangerousGoods(bookingInquiryGoodsDetails.getGoodsName(), bookingInquiry.getLanguage());
                        if ("0".equals(dangerousGoods.getNoteLevel())) {
                            if("en".equals(bookingInquiry.getLanguage())){
                                sb0.append(bookingInquiryGoodsDetails.getGoodsName()).append("（").
                                        append(dangerousGoods.getSpecialEnRemark()).append("）、");
                            }else{
                                sb0.append(bookingInquiryGoodsDetails.getGoodsName()).append("（").
                                        append(dangerousGoods.getSpecialremark()).append("）、");
                            }
                        } else if ("1".equals(dangerousGoods.getNoteLevel())) {
                            if("en".equals(bookingInquiry.getLanguage())){
                                sb1.append(bookingInquiryGoodsDetails.getGoodsName()).append("（").
                                        append(dangerousGoods.getSpecialEnRemark()).append("）、");
                            }else{
                                sb1.append(bookingInquiryGoodsDetails.getGoodsName()).append("（").
                                        append(dangerousGoods.getSpecialremark()).append("）、");
                            }
                        }
                        //根据品名查询特殊单证类型数据
                        List<BaseGoodsnote> goodsNameRemarkList = baseGoodsnoteMapper.selectBaseGoodsnoteListByName(bookingInquiry.getEastOrWest(),bookingInquiryGoodsDetails.getGoodsName());
                        if(StringUtils.isNotEmpty(goodsNameRemarkList)){
                            for(BaseGoodsnote baseGoodsnote : goodsNameRemarkList){
                                sb2.append(bookingInquiryGoodsDetails.getGoodsName()).append(baseGoodsnote.getSpecialnotes()).append("、");
                            }
                        }
                    }
                    // 单件超长或超重（长11m,   宽 2.2m  , 高2.5，重量超过15吨）
                    if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal("1200"))==1 ||
                            new BigDecimal(bookingInquiryGoodsDetails.getGoodsWidth()).compareTo(new BigDecimal("220"))==1 ||
                            new BigDecimal(bookingInquiryGoodsDetails.getGoodsHeight()).compareTo(new BigDecimal("250"))==1 ||
                            new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal("15000"))==1) {
                        over = true;
                    }
                }
                if (sb0.length() > 0) {
                    return AjaxResult.error(sb0.substring(0, sb0.length() - 1) + ("en".equals(bookingInquiry.getLanguage()) ? ",not available for inquiry":"，不可询价"));
                }
                stationIsOk = commonHandlerService.isOperable(bookingInquiry);
                int i = bookingInquiryMapper.insertBookingInquiry(bookingInquiry);
                for (BookingInquiryGoodsDetails bookingInquiryGoodsDetails : bookingInquiryGoodsDetailsList) {
                    bookingInquiryGoodsDetails.setInquiryId(bookingInquiry.getId());
                    //插入拼箱数据
                    bookingInquiryGoodsDetailsMapper.insertBookingInquiryGoodsDetails(bookingInquiryGoodsDetails);
                }
            }
        } else {
            int i = bookingInquiryMapper.insertBookingInquiry(bookingInquiry);
        }
        String inquiryMsg="en".equals(bookingInquiry.getLanguage())?"Successful inquiry! ":"询价成功！";
        StringBuilder msg = new StringBuilder(inquiryMsg);
        Boolean isMsg = true;
        if (isNaked) {
            String isNakedMsg="en".equals(bookingInquiry.getLanguage())?"The goods are naked, please contact the sales department to confirm whether it can be operated":"货物裸装，请联系业务部确认是否可以操作。";
            msg.append(isNakedMsg);
            isMsg = false;
        }
        if (over) {
            String overMsg="en".equals(bookingInquiry.getLanguage())?"The cargo is too long or heavy, please contact the sales department to confirm whether it can be operated":"货物超长超重，请联系业务部确认是否可以操作。";
            msg.append(overMsg);
            isMsg = false;
        }
        if (stationIsOk) {
            String stationIsOkMsg="en".equals(bookingInquiry.getLanguage())?"The size of the goods exceeds the site operation limit, you need to contact the business department for a single inquiry":"货物尺寸超出站点操作限制，需要联系业务部单询。";
            msg.append(stationIsOkMsg);
            isMsg = false;
        }
        if (sb1.length() > 0) {
            String sb1Msg="en".equals(bookingInquiry.getLanguage())?"Suspected dangerous goods(" + sb1.substring(0, sb1.length() - 1) + ")":"货物疑似危险品(" + sb1.substring(0, sb1.length() - 1) + ")";
            msg.append(sb1Msg);
            isMsg = false;
        }
        if (sb2.length() > 0) {
            String sb2Msg="en".equals(bookingInquiry.getLanguage())?"Goods are special documents(" + sb2.substring(0, sb2.length() - 1) + ")":"货物为特殊单证物品(" + sb2.substring(0, sb2.length() - 1) + ")";
            msg.append(sb2Msg);
            isMsg = false;
        }
        if (isMsg) {
            // 如果没有特殊情况，啥也不返回
            msg= new StringBuilder();
        }
        Thread t = new Thread();
        /*----------------------------------------计算铁路费用或者提货费用结果保存到询价结果反馈表--------------------------------------------*/
        /*考虑线路、门到站、站到站、站到门；去回程、整拼箱*/
        switch (lineType) {
            case "0":
                // 郑欧
                t = new Thread(new ZoHandler(bookingInquiry, zoCalculateService));
                break;
            case "2":
                //2郑中亚（中亚）
                t = new Thread(new ZyHandler(bookingInquiry, zyCalculateService));
                break;
            case "3":
                //3郑东盟(中越)）
                t = new Thread(new ZynHandler(bookingInquiry, zynCalculateService));
                break;
            case "4":
                //4郑俄（中俄）
                t = new Thread(new ZeHandler(bookingInquiry, zeCalculateService));
                break;
        }

        if (!isNaked && !over && !stationIsOk &&

            // 前提是非直客
            (!StringUtils.equals("1",bookingInquiry.getClientType())) &&
            (
                // 站到站服务
                "2".equals(bookingInquiry.getBookingService()) ||
                // 站到门 回程整柜
                ("3".equals(bookingInquiry.getBookingService()) && "1".equals(bookingInquiry.getEastOrWest()) &&
                    "0".equals(bookingInquiry.getGoodsType())) ||
                 // 门到站 去程整柜 整柜到堆场
                ("1".equals(bookingInquiry.getBookingService()) && "0".equals(bookingInquiry.getEastOrWest()) &&
                    "0".equals(bookingInquiry.getGoodsType())) && "0".equals(bookingInquiry.getDeliveryType()) &&
                    (!bookingInquiry.getContainerType().endsWith("RF")) && (!bookingInquiry.getContainerType().endsWith("OT"))
            )
        ) {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            long start = System.currentTimeMillis();
            log.debug("start = " + start);
            Future future = executorService.submit(t);//子线程启动
            try {
                future.get();
            } catch (InterruptedException e) {
                log.error("子线程启动--InterruptedException失败：{}",e.toString(),e.getStackTrace());
                throw new BaseException("en".equals(bookingInquiry.getLanguage())?"The inquiry is abnormal, please make another inquiry!":"询价异常，请重新询价！");
            } catch (ExecutionException e) {
                log.error("子线程启动--ExecutionException失败：{}",e.toString(),e.getStackTrace());
                throw new BaseException("en".equals(bookingInquiry.getLanguage())?"The inquiry is abnormal, please make another inquiry!":"询价异常，请重新询价！");
            }
            long end = System.currentTimeMillis();
            log.debug("end = " + end);
            log.debug("end - start = " + (end - start));
            executorService.shutdown();
            List<BookingInquiryResult> bookingInquiryResults =
                    bookingInquiryResultMapper.selectBookingInquiryResultWithInquiryId(bookingInquiry.getId());
            if (CollectionUtils.isNotEmpty(bookingInquiryResults) &&
                    StringUtils.isNotEmpty(bookingInquiryResults.get(0).getRailwayFees()) &&
                    !"0".equals(bookingInquiryResults.get(0).getRailwayFees())) {
                //回程站到门整柜没有还箱地不直接返回数据
                if("3".equals(bookingInquiry.getBookingService()) && "1".equals(bookingInquiry.getEastOrWest()) &&
                        "0".equals(bookingInquiry.getGoodsType())&& StringUtils.isEmpty(bookingInquiryResults.get(0).getHxAddress())){
                    return AjaxResult.success(msg.toString());
                }
                // 特种箱
                if (bookingInquiry.getGoodsType().equals("0") &&
                        (
                            bookingInquiry.getContainerType().endsWith("RF") ||
                            bookingInquiry.getContainerType().endsWith("OT") ||
                            bookingInquiry.getContainerType().endsWith("MT") ||
                            bookingInquiry.getContainerType().endsWith("HT")
                        )
                ) {
                    return AjaxResult.success(msg.toString());
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("inquiryId", bookingInquiryResults.get(0).getInquiryId());
                jsonObject.put("resultId", bookingInquiryResults.get(0).getId());
                return AjaxResult.success(msg.toString(),jsonObject);
            }
        } else {
            // 正常启动，不等待结果返回
            t.start();
        }
        return AjaxResult.success(msg.toString());
    }

    /**
     * 修改订舱询价
     *
     * @param bookingInquiry 订舱询价
     * @return 结果
     */
    @Override
    public int updateBookingInquiry(BookingInquiry bookingInquiry) {
        if (StringUtils.isNotEmpty(bookingInquiry.getOrderId())) {
            // 转待审核询价处理
            Map IsExamline = busiShippingorderService.selectIsExamlineByOrderId(bookingInquiry.getOrderId());
            if (bookingInquiry.getInquiryState().equals("4")) {
                /*处理再次订舱功能*/
                if (IsExamline.get("IsExamline").equals("14")) {
                    BusiShippingorders order = new BusiShippingorders();
                    order.setOrderId(bookingInquiry.getOrderId());
                    if (IsExamline.get("IsConsolidation").equals("0")) {
                        order.setContainerBoxamount("0");
                    }
                    order.setIsexamline("3");
                    busiShippingorderMapper.updateBusiShippingorder(order);
                    ShippingOrder orderInfoRabbmq = busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                    if (StringUtils.isNotNull(orderInfoRabbmq)) {
                        commonService.sendEmailJsBh(orderInfoRabbmq);
                        String messagetype = "4";
                        try {
                            commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage());
                        }
                    }
                } else {
                    /*驳回询价*/
                    /*询价改成原来的*/
                    BookingInquiryOrder inquiryOrder = bookingInquiryOrderMapper.selectPreInquiryOrder(bookingInquiry.getOrderId());
                    if (inquiryOrder != null) {
                        busiShippingorderMapper.updateInquiryResultId(
                                bookingInquiry.getOrderId(), inquiryOrder.getInquiryResultId());
                    }
                    if (IsExamline.get("IsExamline").equals("9")) {
                        busiShippingorderMapper.updateIsExamlineButNotBH(bookingInquiry.getOrderId(), "5");
                        commonService.deleteTem(bookingInquiry.getOrderId());
                        toCheckNotice.noticeSonSystem(bookingInquiry.getOrderId(), "5", "业务部驳回转待审核询价");

                        ShippingOrder orderInfoRabbmq = busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                        if (StringUtils.isNotNull(orderInfoRabbmq)) {
                            String messagetype = "4";
                            try {
                                commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
                            } catch (JsonProcessingException e) {
                                log.error(e.getMessage());
                            }
                        }
                    }
                }
            }
            if (bookingInquiry.getInquiryState().equals("3")) {
                /*发送客户*/
                if (IsExamline.get("IsExamline").equals("9")) {
                    busiShippingorderMapper.updateIsExamlineButNotBH(bookingInquiry.getOrderId(), "10");
                    toCheckNotice.noticeSonSystem(bookingInquiry.getOrderId(), "10", "业务部发送客户确认转待审核询价");

                    ShippingOrder orderInfoRabbmq = busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                    if (StringUtils.isNotNull(orderInfoRabbmq)) {
                        String messagetype = "4";
                        try {
                            commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage());
                        }
                    }
                }
                if (IsExamline.get("IsExamline").equals("14")) {
                    BusiShippingorders order = new BusiShippingorders();
                    order.setOrderId(bookingInquiry.getOrderId());
                    order.setIsexamline("15");
                    busiShippingorderMapper.updateBusiShippingorder(order);
                }
            }
        }
        // 发送客户询价
        if (bookingInquiry.getInquiryState().equals("3")) {
            List<BookingInquiryResult> bookingInquiryResults =
                    bookingInquiryResultMapper.selectBookingInquiryResultWithInquiryId(bookingInquiry.getId());
            if(CollectionUtils.isNotEmpty(bookingInquiryResults)) {
                bookingInquiry.setValidDate(
                        commonHandlerService.getValidDate(
                                bookingInquiryResults.get(0).getPickUpExpiration(),
                                bookingInquiryResults.get(0).getRailwayExpiration(),
                                bookingInquiryResults.get(0).getDeliveryExpiration(),
                                bookingInquiryResults.get(0).getPickUpBoxExpiration(),
                                bookingInquiryResults.get(0).getReturnBoxExpiration()
                        )
                );
            }
        }
        return bookingInquiryMapper.updateBookingInquiry(bookingInquiry);
    }

    /**
     * 修改订舱询价
     *
     * @param bookingInquiry 订舱询价
     * @return 结果
     */
    @Override
    public int updateBIWithDate(BookingInquiry bookingInquiry) {
        // 更新信息
        if ("3".equals(bookingInquiry.getInquiryState())) {
            List<BookingInquiryResult> bookingInquiryResults =
                    bookingInquiryResultMapper.selectBookingInquiryResultWithInquiryId(bookingInquiry.getId());
            if(CollectionUtils.isNotEmpty(bookingInquiryResults)) {
                bookingInquiry.setValidDate(
                        commonHandlerService.getValidDate(
                                bookingInquiryResults.get(0).getPickUpExpiration(),
                                bookingInquiryResults.get(0).getRailwayExpiration(),
                                bookingInquiryResults.get(0).getDeliveryExpiration(),
                                bookingInquiryResults.get(0).getPickUpBoxExpiration(),
                                bookingInquiryResults.get(0).getReturnBoxExpiration()
                        )
                );
            }
        }
        return bookingInquiryMapper.updateBookingInquiry(bookingInquiry);
    }

    /**
     * 批量删除订舱询价
     *
     * @param ids 需要删除的订舱询价ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryByIds(Long[] ids) {
        return bookingInquiryMapper.deleteBookingInquiryByIds(ids);
    }

    /**
     * 删除订舱询价信息
     *
     * @param id 订舱询价ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryById(Long id) {
        return bookingInquiryMapper.deleteBookingInquiryById(id);
    }

    /**
     * 判断货物品名是否为危险品
     *
     * @param goodsName
     * @return
     */
    public DangerousGoods isDangerousGoods(String goodsName, String language) {
        DangerousGoods dangerousGoods = dangerousGoodsMapper.getCnEnName(goodsName, language);
        if (StringUtils.isNotNull(dangerousGoods)) {
            return dangerousGoods;
        }
        dangerousGoods = new DangerousGoods();
        dangerousGoods.setNoteLevel("1");
        if (goodsName.contains("混合") || goodsName.contains("液") || goodsName.contains("剂") || goodsName.contains("粉")) {
            dangerousGoods.setSpecialremark("可能是危险品，慎运");
            return dangerousGoods;
        }else if(goodsName.contains("mix") || goodsName.contains("liquid") || goodsName.contains("powder")){
            dangerousGoods.setSpecialEnRemark("It may be dangerous. Handle with caution.");
            return dangerousGoods;
        }
        dangerousGoods.setNoteLevel("2");
        return dangerousGoods;
    }

}