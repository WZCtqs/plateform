package com.szhbl.project.inquiry.handler.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.enums.CadNoteEnum;
import com.szhbl.common.enums.LanguageEnum;
import com.szhbl.common.utils.ConvertUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryOrder;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.mapper.BookingInquiryOrderMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsService;
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
import com.szhbl.project.trains.service.IBusiSiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报价信息处理
 */
@Slf4j
@Component
public class QuotationProcessService {

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private IBookingInquiryService iBookingInquiryService;
    @Autowired
    private IBookingInquiryResultService iBookingInquiryResultService;
    @Autowired
    private IBookingInquiryGoodsDetailsService iBookingInquiryGoodsDetailsService;
    @Autowired
    private CommonHandlerService commonHandlerService;
    @Autowired
    BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    BookingInquiryOrderMapper bookingInquiryOrderMapper;
    @Autowired
    ToCheckNotice toCheckNotice;
    @Autowired
    private CommonService commonService;

    @Autowired
    private InquiryProvider inquiryProvider;
    @Autowired
    private IBusiSiteService iBusiSiteService;

    @Autowired
    private BusiOrderTocheckInquiryMapper busiOrderTocheckInquiryMapper;

    /**
     * 处理集疏报价信息
     *
     * @return
     */
    @Transactional
    public Boolean handlerJs(BookingInquiryResult bookingInquiryResult) {

        if (StringUtils.isNotNull(bookingInquiryResult)) {
            bookingInquiryResult.setJsBidder(bookingInquiryResult.getBidder());
            //处理重复询价
         /*   BookingInquiryResult checkBookingInquiryResult = new BookingInquiryResult();
            checkBookingInquiryResult.setAgainInquiryId(bookingInquiryResult.getInquiryId());
            List<BookingInquiryResult> birList = iBookingInquiryResultService.selectBookingInquiryResultList(checkBookingInquiryResult);
            //查询到待更新的重复询价
            if(StringUtils.isNotEmpty(birList)){
                birList.add(bookingInquiryResult);
                for(int n=0;n<birList.size();n++){
                    bookingInquiryResult.setInquiryId(birList.get(n).getInquiryId());
                    //添加处理逻辑

                }
            }*/
            Long inquriyId = bookingInquiryResult.getInquiryId();
            BookingInquiry bookingInquiry = iBookingInquiryService.selectBookingInquiryById(inquriyId);

            if (StringUtils.isNull(bookingInquiry)){
                return Boolean.TRUE;
            }
            // 转换中文内容
            if(LanguageEnum.英文.value.equals(bookingInquiry.getLanguage()) &&
                    StringUtils.isNotEmpty(bookingInquiryResult.getNote())) {
                String noteDetail = CadNoteEnum.getNoteDetail(bookingInquiryResult.getNote());
                if (StringUtils.isNotEmpty(noteDetail)) {
                    bookingInquiryResult.setJsRemark(noteDetail);
                }
            }
            log.info("集输报价----》orderId = " + bookingInquiry.getOrderId());
            List<BookingInquiryResult> bookingInquiryResultList = iBookingInquiryResultService.selectBookingInquiryResultWithInquiryId(inquriyId);//查询询价反馈结果是否存在
            if (bookingInquiryResult.getIsNormal().equals("1")) {
                if (StringUtils.isNotEmpty(bookingInquiry.getOrderId())) {
                    if (bookingInquiry.getInquiryState().equals("4")) {
                        log.info("集输报价--->集输驳回转待询价-->询价已被驳回");
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
                                    log.error("处理集疏报价信息失败：{}", e.toString(), e.getStackTrace());
                                }
                            }
                        } else {
                            log.info("集输报价--->集输驳回转待询价");
                            // 如果集输报价驳回，则转待审核申请不通过，修改托书状态为--->草稿状态
                            busiShippingorderService.updateIsExamlineByOrderId(bookingInquiry.getOrderId(), "5");
                            busiShippingorderService.updateOrderExam(bookingInquiry.getOrderId(), bookingInquiryResult.getBusinessRemark());

                            toCheckNotice.noticeSonSystem(bookingInquiry.getOrderId(), "5", "集输系统驳回转待审核询价");
                            commonService.deleteTem(bookingInquiry.getOrderId());
                            ShippingOrder orderInfoRabbmq = busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                            if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                String messagetype = "5"; //转待审核失败
                                try {
                                    commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
                                } catch (JsonProcessingException e) {
                                    log.error("处理集疏报价信息失败：{}", e.toString(), e.getStackTrace());
                                }
                            }
                            /*询价改成原来的*/
                            // 恢复之前的询价结果信息
                            BookingInquiryOrder inquiryOrder = bookingInquiryOrderMapper.selectPreInquiryOrder(bookingInquiry.getOrderId());
                            if (inquiryOrder != null) {
                                busiShippingorderMapper.updateInquiryResultId(
                                        bookingInquiry.getOrderId(), inquiryOrder.getInquiryResultId());
                            }
                            log.info("集输报价----->驳回转待询价---->完成询价退回");
                        }
                    }
                }

                //驳回
                bookingInquiry.setInquiryState("4");
                if (StringUtils.isNotEmpty(bookingInquiryResult.getPickUpRemark())) {
                    bookingInquiry.setTurndownReason(bookingInquiryResult.getPickUpRemark());
                } else {
                    bookingInquiry.setTurndownReason(bookingInquiryResult.getDeliveryRemark());
                }
                //更新询价结果
                iBookingInquiryResultService.updateBookingInquiryResultWithInquiryId(bookingInquiryResult);
            } else {
                //集疏整柜报价乘以箱量作为价格 去程 pickUpFees公路 deliveryFees集疏
                //整柜
                if ("0".equals(bookingInquiry.getGoodsType()) ) {
                    //去程
                    if ("0".equals(bookingInquiry.getEastOrWest()) && (!"1".equals(bookingInquiry.getDistributionType()))) {
                        bookingInquiryResult.setDeliveryFees(new BigDecimal(ConvertUtils.reInt(bookingInquiryResult.getDeliveryFees())).multiply(new BigDecimal(bookingInquiry.getContainerNum())).toString());
                        if (StringUtils.isEmpty(bookingInquiryResult.getDeliveryCurrencyType())) {
                            bookingInquiryResult.setDeliveryCurrencyType("€");
                        }
                    }
                    //回程
                    else if ("1".equals(bookingInquiry.getEastOrWest()) && "0".equals(bookingInquiry.getDeliveryType())) {
                        bookingInquiryResult.setPickUpFees(new BigDecimal(ConvertUtils.reInt(bookingInquiryResult.getPickUpFees())).multiply(new BigDecimal(bookingInquiry.getContainerNum())).toString());
                        if (StringUtils.isEmpty(bookingInquiryResult.getPickUpCurrencyType())) {
                            bookingInquiryResult.setPickUpCurrencyType("€");
                        }
                    }
                }

                //拿到上货站或者下货站信息
                String uploadStation = "";//上货站
                String dropStation = "";//下货站
                if (StringUtils.isNotNull(bookingInquiry)) {
                    String lineType = bookingInquiry.getLineType();
                    String eastOrWest = bookingInquiry.getEastOrWest();
                    switch (eastOrWest) {
                        case "0":
                            //去程返回上货站，需要从询价表里查
                            uploadStation = bookingInquiry.getUploadStation();
                            if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())) {
                                dropStation = iBusiSiteService.getBusiSiteByName(bookingInquiryResult.getDropStation(), "zh");
                            } else {
                                dropStation = bookingInquiryResult.getDropStation();
                            }
                            bookingInquiryResult.setDeliveryFeedbackTime(new Date());
                            if (StringUtils.isNotEmpty(bookingInquiryResultList.get(0).getPickUpFees()) ||
                                    bookingInquiry.getBookingService().equals("3")){
                                //是3不改
                                if(!"3".equals(bookingInquiry.getInquiryState())){
                                    bookingInquiry.setInquiryState("2");
                                }
                            }
                            break;
                        case "1":
                            //回程有
                            if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())) {
                                uploadStation = iBusiSiteService.getBusiSiteByName(bookingInquiryResult.getUploadStation(), "zh");
                            } else {
                                uploadStation = bookingInquiryResult.getUploadStation();
                            }
                            dropStation = bookingInquiry.getDropStation();
                            bookingInquiryResult.setPickUpFeedbackTime(new Date());
                            if (StringUtils.isNotEmpty(bookingInquiryResultList.get(0).getDeliveryFees()) ||
                                    bookingInquiry.getBookingService().equals("1")){
                                if(!"3".equals(bookingInquiry.getInquiryState())){
                                    bookingInquiry.setInquiryState("2");
                                }
                            }
                            break;
                    }
                    bookingInquiry.setUploadStation(uploadStation);
                    bookingInquiry.setDropStation(dropStation);
                    bookingInquiryResult.setUploadStation(uploadStation);
                    bookingInquiryResult.setDropStation(dropStation);
                    if (bookingInquiry.getGoodsType().equals("0")) {
                        // 回程整柜中亚中越中俄不用处理
                        if ("1".equals(bookingInquiry.getEastOrWest()) &&
                                ("2".equals(lineType) || "3".equals(lineType) || "4".equals(lineType))
                        ) {
                            if ("0".equals(bookingInquiry.getContainerBelong())) {
                                // 国外提箱平衡费
                                if ("1".equals(bookingInquiry.getDeliveryType())){
                                    bookingInquiryResult.setTxAddress(bookingInquiryResult.getUploadStation());
                                } else {
                                    if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())) {
                                        String tx = commonHandlerService.getAddressEnName(bookingInquiryResult.getTxAddress());
                                        bookingInquiryResult.setTxAddress(tx);
                                    }
                                }
                                BusiBoxfee pickUpBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                        bookingInquiryResult.getTxAddress(),
                                        bookingInquiry.getContainerType(),
                                        bookingInquiry.getContainerNum(),
                                        "0",
                                        bookingInquiry.getBookingTimeFlag());
                                if (!ObjectUtils.isEmpty(pickUpBoxfee)) {
                                    bookingInquiryResult.setPickUpBoxFee(pickUpBoxfee.getCost());
                                    bookingInquiryResult.setPickUpBoxExpiration(pickUpBoxfee.getEndTime());
                                }
                            }
                            iBookingInquiryResultService.updateBookingInquiryResultWithInquiryId(
                                    bookingInquiryResult); // 根据inquiryid更新
                            if (StringUtils.isNotEmpty(bookingInquiryResultList.get(0).getDeliveryFees())) {
                                bookingInquiry.setInquiryState("2");
                            }
                            iBookingInquiryService.updateBookingInquiry(bookingInquiry);
                            return Boolean.TRUE;
                        }
                        BookingInquiryResult bir = null;
                        if ((!"2".equals(lineType)) &&
                                bookingInquiry.getEastOrWest().equals("0") &&
                                bookingInquiry.getBookingService().equals("0") &&
                                bookingInquiry.getDeliveryType().equals("0") &&
                                "0".equals(bookingInquiry.getContainerBelong())){
                            if (lineType.equals("0")) {
                                bir = commonHandlerService.getZOZGQCPickUpFeesAndRailFee(bookingInquiry);
                                // 提箱平衡费
                                if (!(bookingInquiry.getContainerType().endsWith("RF") ||
                                        bookingInquiry.getContainerType().endsWith("OT") ||
                                        bookingInquiry.getContainerType().endsWith("HT") ||
                                        bookingInquiry.getContainerType().endsWith("MT"))) { // 普箱
                                    if (bookingInquiry.getContainerType().startsWith("40") ||
                                            bookingInquiry.getContainerType().startsWith("45")) {
                                        bookingInquiryResult.setPickUpBoxFee("$ " + (500*bookingInquiry.getContainerNum()));
                                    } else if (bookingInquiry.getContainerType().startsWith("20") ){
                                        bookingInquiryResult.setPickUpBoxFee("$ " + (400*bookingInquiry.getContainerNum()));
                                    }
                                }
                            } else if (lineType.equals("4")) {
                                bir = commonHandlerService.getZePickUpAndRailwayFees(bookingInquiry);
                            } else if (lineType.equals("3")) {
                                bir = commonHandlerService.getZynPickUpAndRailwayFees(bookingInquiry);
                            }
                            bookingInquiryResult.setPickUpFees(bir.getPickUpFees());//提货费
                            bookingInquiryResult.setPickUpDistance(bir.getPickUpDistance());//提货距离
                            bookingInquiryResult.setPickUpAddress(bir.getPickUpAddress());//提货地
                            bookingInquiryResult.setPickUpCurrencyType(bir.getPickUpCurrencyType());//币种
                            bookingInquiryResult.setPickUpExpiration(bir.getPickUpExpiration());//有效期
                            bookingInquiryResult.setEnquiryState(bir.getEnquiryState());// 报价是否审核
                            if(!"3".equals(bookingInquiry.getInquiryState())){
                                bookingInquiry.setInquiryState("2");
                            }
                        } else {
                            if (lineType.equals("0") || lineType.equals("4")) {
                                bir = commonHandlerService.getZGRailFee(bookingInquiry);
                            } else if (lineType.equals("2")) {
                                bir = commonHandlerService.getZyZGRailFee(bookingInquiry);
                            } else if (lineType.equals("3")) {
                                bir = commonHandlerService.getZynZGRailFee(bookingInquiry);
                            }
                        }
                        bookingInquiryResult.setRailwayFeedbackTime(new Date());//铁路运费反馈时间
                        if (StringUtils.isEmpty(bir.getRailwayFees())) {
                            bookingInquiryResult.setRailRemark(bir.getRailRemark());
                        } else {
                            bookingInquiryResult.setRailwayFees(bir.getRailwayFees());//铁路费用
                            bookingInquiryResult.setRailwayCurrencyType(bir.getRailwayCurrencyType());//币种
                            bookingInquiryResult.setRailwayAging(bir.getRailwayAging()); // 铁路时效
                            bookingInquiryResult.setRailwayExpiration(bir.getRailwayExpiration()); //有效期
                        }
                        if ("0".equals(bookingInquiry.getContainerBelong())) {
                            // 国外提还箱平衡费
                            if (bookingInquiry.getEastOrWest().equals("0")){
                                if ( !"1".equals(bookingInquiry.getDistributionType())) {
                                    if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())) {
                                        String hx = commonHandlerService.getAddressEnName(bookingInquiryResult.getHxAddress());
                                        // bookingInquiry.setHxAddress(hx);
                                        bookingInquiryResult.setHxAddress(hx);
                                    }
                                } else {
                                    bookingInquiryResult.setHxAddress(dropStation);
                                }
                                BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                        bookingInquiryResult.getHxAddress(),
                                        bookingInquiry.getContainerType(),
                                        bookingInquiry.getContainerNum(),
                                        "1",
                                        bookingInquiry.getBookingTimeFlag());
                                if (!ObjectUtils.isEmpty(returnBoxfee)) {
                                    bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                                    bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                                }
                            }
                            if (bookingInquiry.getEastOrWest().equals("1")){
                                if (bookingInquiry.getDeliveryType().equals("0")) {
                                    if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())) {
                                        String tx = commonHandlerService.getAddressEnName(bookingInquiryResult.getTxAddress());
                                        // bookingInquiry.setTxAddress(tx);
                                        bookingInquiryResult.setTxAddress(tx);
                                    }
                                } else {
                                    bookingInquiryResult.setTxAddress(bookingInquiryResult.getUploadStation());
                                }
                                BusiBoxfee pickUpBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                        bookingInquiryResult.getTxAddress(),
                                        bookingInquiry.getContainerType(),
                                        bookingInquiry.getContainerNum(),
                                        "0",
                                        bookingInquiry.getBookingTimeFlag());
                                if (!ObjectUtils.isEmpty(pickUpBoxfee)) {
                                    bookingInquiryResult.setPickUpBoxFee(pickUpBoxfee.getCost());
                                    bookingInquiryResult.setPickUpBoxExpiration(pickUpBoxfee.getEndTime());
                                }
                            }
                        }
                    } else {
                        List<BookingInquiryGoodsDetails> goodsDetailsList =
                                iBookingInquiryGoodsDetailsService.selectBookingInquiryGoodsDetailsWithInquiryId(bookingInquiry.getId());
                        bookingInquiry.setBookingInquiryGoodsDetailsList(goodsDetailsList);
                        BookingInquiryResult result1 = new BookingInquiryResult();
                        if (lineType.equals("0")) {
                            result1 = commonHandlerService.getSHQCRailFee(bookingInquiry);
                        } else if (lineType.equals("4")) {
                            result1 = commonHandlerService.getZeSHQCRailFee(bookingInquiry);
                        } else if (lineType.equals("2")) {
                            result1 = commonHandlerService.getZySHQCRailFee(bookingInquiry);
                        } else if (lineType.equals("3")) {
                            result1 = commonHandlerService.getZynSHQCRailFee(bookingInquiry);
                        }
                        bookingInquiryResult.setPickUpAddress(result1.getPickUpAddress());//提货地（发货地）
                        bookingInquiryResult.setDeliveryAddress(result1.getDeliveryAddress());//派送地（收货地）
                        if (StringUtils.isEmpty(result1.getRailwayFees())) {
                            bookingInquiryResult.setRailRemark(result1.getRailRemark());
                        } else {
                            bookingInquiryResult.setRailwayFees(result1.getRailwayFees());//铁路费用
                            bookingInquiryResult.setRailwayCurrencyType(result1.getRailwayCurrencyType());//欧线、俄线和中越是美元，中亚是人民币
                            bookingInquiryResult.setRailwayAging(result1.getRailwayAging());// 时效
                            bookingInquiryResult.setRailwayExpiration(result1.getRailwayExpiration());// 有效期
                        }
                        // 计费体积
                        bookingInquiry.setBillableVolume(result1.getRailRemark());
                    }
                }

                if (StringUtils.isNotEmpty(bookingInquiry.getOrderId())) {
                    if (bookingInquiry.getInquiryState().equals("4")) {
                        log.info("集输报价----->转待报价处理开始-->询价已被驳回,处理终止");
                    } else {
                        log.info("集输报价-----> 转待报价处理开始");
                        toCheckNotice.noticeSonSystem(bookingInquiry.getOrderId(), "9", "集输系统报价转待审核询价");
                        // 如果集输报价成功，则转待审核申请通过，修改托书状态--->报价中
//                        busiShippingorderService.updateIsExamlineButNotBH(bookingInquiry.getOrderId(), "9");

                        iBookingInquiryResultService.updateBookingInquiryResultWithInquiryId(
                                bookingInquiryResult); // 根据inquiryid更新
                        ShippingOrder orderInfoRabbmq =
                                busiShippingorderService.selectBusiShippingorderById(bookingInquiry.getOrderId());
                        if (StringUtils.isNotNull(orderInfoRabbmq)) {
                            String messagetype = "4"; // 转待审核失败
                            try {
                                commonService.orderInfoMQ(orderInfoRabbmq, messagetype); // 推送消息队列
                            } catch (JsonProcessingException e) {
                                log.error("处理集疏报价信息失败：{}", e.toString(), e.getStackTrace());
                            }
                        }
                        log.info("集输报价-----> 转待报价处理结束");
                        /*整柜时 判断还箱地是否更改*/
                        if ("0".equals(bookingInquiry.getGoodsType())
                                && "0".equals(bookingInquiry.getEastOrWest())) {
                            /*获取是否还需要箱管审核还箱地，0是需要（散货改整柜派送）。1是不需要*/
                            BusiOrderTocheckInquiry tocheckInquiry =
                                    busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(
                                            bookingInquiry.getOrderId());
                            if (String.valueOf(tocheckInquiry.getHxdXgExamine()).equals("0")) {
                                String oldHxd =
                                        iBookingInquiryResultService.getLastInquiryHxd(bookingInquiry.getOrderId());
                                log.info("js原还箱地：" + oldHxd);
                                log.info("js现还箱地：" + bookingInquiryResult.getHxAddress());
                                if (!(oldHxd + "").equals(bookingInquiryResult.getHxAddress() + "")) {
                                    log.error("js还箱地更改，向箱管部审核");
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
                } else {
                    if (bookingInquiryResultList.size() == 1) {
                        if (StringUtils.isNotEmpty(bookingInquiryResultList.get(0).getRailwayFees())) {
                            // 复制之后再插入一条
                            BookingInquiryResult copyBookingResult = bookingInquiryResultList.get(0);
                            iBookingInquiryResultService.insertBookingInquiryResult(copyBookingResult);
                            // 再更新
                            bookingInquiryResult.setId(copyBookingResult.getId());
                            iBookingInquiryResultService.updateBookingInquiryResult(bookingInquiryResult);
                            log.debug("复制之后集输--requ" + bookingInquiryResult);

                        } else {
                            // 存在一条待更新的数据
                            iBookingInquiryResultService.updateBookingInquiryResultWithInquiryId(
                                    bookingInquiryResult); // 根据inquiryid更新
                        }
                    } else if (bookingInquiryResultList.size() > 1) {
                        // 存在多条，且是已经更新过的，需要获取其中一个值，然后插入
                        BookingInquiryResult copyBookingResult = bookingInquiryResultList.get(0);
                        iBookingInquiryResultService.insertBookingInquiryResult(copyBookingResult);
                        bookingInquiryResult.setId(copyBookingResult.getId());
                        iBookingInquiryResultService.updateBookingInquiryResult(bookingInquiryResult);
                    }
                }
            }
            iBookingInquiryService.updateBookingInquiry(bookingInquiry);
        }
        return Boolean.TRUE;
    }
    /**
     * 处理箱行亚欧报价信息
     *
     * @return
     */
    @Transactional
    public Boolean handlerXxyo(BookingInquiryResult bookingInquiryResult) {



        return Boolean.TRUE;
    }

}
