package com.szhbl.project.inquiry.handler.service;

import com.szhbl.common.enums.LanguageEnum;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.handler.common.CommonHandlerService;
import com.szhbl.project.inquiry.handler.common.InquiryProvider;
import com.szhbl.project.inquiry.mapper.BookingInquiryResultMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * 中亚线路计费服务
 */
@Slf4j
@Service
public class ZyCalculateService {

    @Autowired
    private IBookingInquiryService bookingInquiryService;
    @Autowired
    private BookingInquiryResultMapper bookingInquiryResultMapper;
    @Autowired
    private InquiryProvider inquiryProvider;
    @Autowired
    private CommonHandlerService commonHandlerService;

    /**
     * 中亚线路计费计算
     */
    public int zyCalculateAndInsertBookingInquiryResult(BookingInquiry bookingInquiry) {

        Long inquiryId = bookingInquiry.getId(); // 询价主表id
        String bookService = bookingInquiry.getBookingService(); // 服务类型
        String goodsType = bookingInquiry.getGoodsType(); // 整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest(); // 去回程
        String zzCity = "郑州";
        String zzCityEn = "Zhengzhou";
        String language = bookingInquiry.getLanguage();
        //无论那条线路去回程，铁路运费是必须查表获取的，所以询价结果反馈表是新增
        switch (bookService) {
            case "0":
                // 门到门
                //整柜
                if (goodsType.equals("0")) {
                    if (eastOrWest.equals("0")) {
                        if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        // 1、公路部提货费用查表
                        if ("0".equals(bookingInquiry.getContainerBelong())) {
                            bookingInquiryResult = commonHandlerService.getZyPickUpFees(bookingInquiry);
                        } else {
                            bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                            bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                            bookingInquiryResult.setPickUpFees("0");//提货费
                            bookingInquiryResult.setPickUpExpiration(commonHandlerService.getThirtyDaysLaterDate());
                            bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                            bookingInquiryResult.setPickUpCurrencyType("$");//币种
                            bookingInquiryResult.setEnquiryState("2");
                        }
                        bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        // +2、铁路费用等集疏报完价才能确定
                        // 3、集疏费用,发送消
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            //委托zih送货
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新上货站
                    } else { // 回程
                        //1、集疏费用+
                        if (bookingInquiry.getIsPickUp().equals("0")) {
                            //回程委托zih提货
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        // 2、铁路费，查整柜铁路运费表(需要根据集疏反馈的上下货站计算)
                        // 3、送货费
//                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
//                        if("0".equals(bookingInquiry.getContainerBelong())) {
//                            bookingInquiryResult = commonHandlerService.getZyDeliveryFee(bookingInquiry);
//                        } else {
//                            bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
//                            bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
//                            bookingInquiryResult.setDeliveryCurrencyType("$");//币种
//                            bookingInquiryResult.setDeliveryFees("0");//派送费
//                            bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
//                            bookingInquiryResult.setEnquiryState("2");
//                        }
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                        bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                        if(!"1".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                            // 回程派送
                            bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                            bookingInquiryResult.setDeliveryFees("0");//派送费
                        } else {
                            inquiryProvider.xxyoPush(bookingInquiry);
                            bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                            bookingInquiryResult.setReturnBoxFee("€ 0");
                        }
                        // bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
                        bookingInquiryResult.setEnquiryState("2");
                        // 回程铁路
                        bookingInquiryResult.setRailwayFees("0");
                        bookingInquiryResult.setRailwayCurrencyType("￥");
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新下货站
                    }
                } else { // 散货
                    if (eastOrWest.equals("0")) { //去程
                        if (bookingInquiry.getIsPickUp().equals("0")) {
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新上货站
                    } else { //回程
                        bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        if (bookingInquiry.getIsPickUp().equals("0")) {
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        //插入询价结果，更新提货地
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//收货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新下货站
                    }

                }
                break;
            case "1":
                // 门到站
                if (goodsType.equals("0")) { // 整柜
                    if (eastOrWest.equals("0")) {
                        bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        BookingInquiryResult bookingInquiryResult = commonHandlerService.getZyZGRailFee(bookingInquiry);
                        if("0".equals(bookingInquiry.getContainerBelong())){
                            if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
                                inquiryProvider.xxyoPush(bookingInquiry);
                            } else {
                                BookingInquiryResult bir = commonHandlerService.getZyPickUpFees(bookingInquiry);
                                // 赋值
                                bookingInquiryResult.setPickUpDistance(bir.getPickUpDistance());//提货距离
                                bookingInquiryResult.setPickUpAddress(bir.getPickUpAddress());//提货地
                                bookingInquiryResult.setPickUpCurrencyType(bir.getPickUpCurrencyType());//币种
                                bookingInquiryResult.setPickUpFees(bir.getPickUpFees());
                                //门到站整柜去程报完更新审核状态
                                if ("1".equals(bookingInquiry.getClientType()) ||
                                        StringUtils.isEmpty(bookingInquiryResult.getRailwayFees())||
                                        "0".equals(bookingInquiryResult.getRailwayFees()) ||
                                        bookingInquiry.getContainerType().endsWith("RF") ||
                                        bookingInquiry.getContainerType().endsWith("OT") ||
                                        !"3".equals(bookingInquiryResult.getEnquiryState())) {
                                    bookingInquiry.setInquiryState("2");
                                } else {
                                    bookingInquiry.setInquiryState("3");
                                }
                            }
                            //还箱费
                            bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                            BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getHxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "1",
                                    bookingInquiry.getBookingTimeFlag());
                            if (!ObjectUtils.isEmpty(returnBoxfee)) {
                                bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                                bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                            }
                        } else {
                            bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                            bookingInquiryResult.setPickUpFees("0");//提货费
                            bookingInquiryResult.setPickUpExpiration(commonHandlerService.getThirtyDaysLaterDate());
                            bookingInquiryResult.setPickUpCurrencyType("$");//币种
                            bookingInquiryResult.setEnquiryState("2");
                            bookingInquiry.setInquiryState("2");
                        }
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry); //更新上货站
                    } else {
                        //整柜 回程，
                        inquiryProvider.jsPush(bookingInquiry);
                        //更新提货地
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        if("0".equals(bookingInquiry.getContainerBelong())) {
                            //还箱费
                            bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                            BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getHxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "1",
                                    bookingInquiry.getBookingTimeFlag());
                            if (!ObjectUtils.isEmpty(returnBoxfee)) {
                                bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                                bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                            }
                        } else {
                            bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                        }
                        // 回程铁路
                        bookingInquiryResult.setRailwayFees("0");
                        bookingInquiryResult.setRailwayCurrencyType("￥");
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setDelFlag("0");
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                    }
                } else { // 散货
                    //1、去程且委托zih提货
                    if (eastOrWest.equals("0")) {
                        if (bookingInquiry.getIsPickUp().equals("0")) {
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                        BookingInquiryResult bookingInquiryResult = commonHandlerService.getZySHQCRailFee(bookingInquiry);
                        bookingInquiryResult.setDelFlag("0");
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        // 计费体积
                        bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新上货站
                    } else {
                        inquiryProvider.jsPush(bookingInquiry);
                        //插入询价结果，更新提货地
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);
                    }
                }
                break;
            case "2":
                // 站到站
                BookingInquiryResult bir;
                if (goodsType.equals("0")) { // 整柜
                    BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                    // 铁路费用（具体参考费用表）
                    if ("0".equals(eastOrWest)) { // 去程
                        bookingInquiryResult = commonHandlerService.getZyZGRailFee(bookingInquiry);
                    }else { // 回程
                        bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                        bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                        bookingInquiryResult.setEnquiryState("2");
                        bookingInquiryResult.setRailwayFees("0");
                        bookingInquiryResult.setRailwayCurrencyType("￥");
                        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());
                        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());
                    }
                    if("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih
                        //还箱费
                        bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                        BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                bookingInquiry.getHxAddress(),
                                bookingInquiry.getContainerType(),
                                bookingInquiry.getContainerNum(),
                                "1",
                                bookingInquiry.getBookingTimeFlag());
                        if (!ObjectUtils.isEmpty(returnBoxfee)) {
                            bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                            bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                        }
                        // 客户自送货方式判断 1整柜到堆场
                        if ("1".equals(bookingInquiry.getDeliverySelfType())) {
                            // 国内提箱费
                            bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                            BusiBoxfee pickUpBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getTxAddress(),
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
                    bookingInquiryResult.setInquiryId(inquiryId);//询价id
                    bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                    bir = bookingInquiryResult;
                } else { // 散货
                    BookingInquiryResult bookingInquiryResult = commonHandlerService.getZySHQCRailFee(bookingInquiry);
                    bookingInquiryResult.setInquiryId(inquiryId);//询价id
                    bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                    // 计费体积
                    bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
                    bir = bookingInquiryResult;
                }
                if ("1".equals(bookingInquiry.getClientType()) ||
                        StringUtils.isEmpty(bir.getRailwayFees())||
                        "0".equals(bir.getRailwayFees())) {
                    bookingInquiry.setInquiryState("2");
                } else {
                    bookingInquiry.setInquiryState("3");
                }
                // 特种箱
                if (bookingInquiry.getGoodsType().equals("0") &&
                        (bookingInquiry.getContainerType().endsWith("RF")||
                        bookingInquiry.getContainerType().endsWith("OT") ||
                        bookingInquiry.getContainerType().endsWith("MT") ||
                        bookingInquiry.getContainerType().endsWith("HT"))
                ) {
                    bookingInquiry.setInquiryState("2");
                }
                if (bookingInquiry.getGoodsType().equals("1")) {
                    // 是否超长超重（长11m,   宽 2.2m  , 高2.5，重量超过15吨）
                    Boolean over = false;
                    // 货物包装方式是裸装的
                    Boolean isNaked = false;
                    // 判断二级站货物尺寸重量是否可操作
                    Boolean stationIsOk = commonHandlerService.isOperable(bookingInquiry);
                    if ("裸装".equals(bookingInquiry.getPackageType()) || "Nude".equals(bookingInquiry.getPackageType())) {
                        isNaked = true;
                    }
                    for (BookingInquiryGoodsDetails bookingInquiryGoodsDetails : bookingInquiry.getBookingInquiryGoodsDetailsList()) {
                        // 单件超长或超重（长11m,   宽 2.2m  , 高2.5，重量超过15吨）
                        if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal("1200"))==1 ||
                                new BigDecimal(bookingInquiryGoodsDetails.getGoodsWidth()).compareTo(new BigDecimal("220"))==1 ||
                                new BigDecimal(bookingInquiryGoodsDetails.getGoodsHeight()).compareTo(new BigDecimal("250"))==1 ||
                                new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal("15000"))==1) {
                            over = true;
                        }
                    }
                    if(isNaked || over || stationIsOk){
                        bookingInquiry.setInquiryState("2");
                    }
                }
                bookingInquiryService.updateBIWithDate(bookingInquiry);
                break;
            case "3":
                // 站到门
                if (goodsType.equals("0")) {
                    //整柜
                    if (eastOrWest.equals("0")) {
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        if ("0".equals(bookingInquiry.getContainerBelong()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                            // 国内提箱费
                            bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                            BusiBoxfee pickUpBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getTxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "0",
                                    bookingInquiry.getBookingTimeFlag());
                            if (!ObjectUtils.isEmpty(pickUpBoxfee)) {
                                bookingInquiryResult.setPickUpBoxFee(pickUpBoxfee.getCost());
                                bookingInquiryResult.setPickUpBoxExpiration(pickUpBoxfee.getEndTime());
                            }
                        }
                        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());//上货站
                        bookingInquiryResult.setDelFlag("0");
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                    } else {
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        if ("0".equals(bookingInquiry.getContainerBelong()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                            // 整柜 0整柜到堆场
                            // 国外提箱费
                            bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                            BusiBoxfee pickUpBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getTxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "0",
                                    bookingInquiry.getBookingTimeFlag());
                            if (!ObjectUtils.isEmpty(pickUpBoxfee)) {
                                bookingInquiryResult.setPickUpBoxFee(pickUpBoxfee.getCost());
                                bookingInquiryResult.setPickUpBoxExpiration(pickUpBoxfee.getEndTime());
                            }
                        } else {
                            bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                        }
                        if("0".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                            // 回程派送
                            bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                            bookingInquiryResult.setDeliveryFees("0");//派送费
                            bookingInquiry.setInquiryState("2");
                        } else {
                            inquiryProvider.xxyoPush(bookingInquiry);
                            bookingInquiry.setInquiryState("1");
                            bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                            bookingInquiryResult.setReturnBoxFee("€ 0");
                        }
                        // bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
                        bookingInquiryResult.setEnquiryState("2");
                        // 回程铁路
                        bookingInquiryResult.setRailwayFees("0");
                        bookingInquiryResult.setRailwayCurrencyType("￥");

                        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新下货站
                    }
                } else {
                    //散货
                    if (eastOrWest.equals("0")) {
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());//上货站
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());                                                            //收货地
                        bookingInquiryResult.setDelFlag("0");
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);

                    } else {
                        //回程
                        // +散货铁路费
                        bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//站到门下货站
                        BookingInquiryResult bookingInquiryResult = commonHandlerService.getSHQCRailFee(bookingInquiry);
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        bookingInquiryResult.setDelFlag("0");
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新下货站
                    }
                }
                break;
        }
        return 1;
    }

}
