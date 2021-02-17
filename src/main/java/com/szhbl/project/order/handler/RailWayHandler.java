package com.szhbl.project.order.handler;

import com.szhbl.common.enums.LanguageEnum;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.inquiry.domain.*;
import com.szhbl.project.inquiry.handler.common.CommonHandlerService;
import com.szhbl.project.trains.service.IBusiSiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : 转待审核铁路费用计算
 * @Author : wangzhichao
 * @Date: 2020-07-29 11:54
 */
@Slf4j
@Service
public class RailWayHandler {

    @Autowired
    private CommonHandlerService commonHandlerService;
    @Autowired
    private IBusiSiteService iBusiSiteService;


    private static final String zzCity = "郑州";
    private static final String zzCityEn = "Zhengzhou";

    public BookingInquiryResultBackup getRailWayFeeDTD(BookingInquiryBackup inquiryBackup) {
        BookingInquiry inquiry = new BookingInquiry();
        BeanUtils.copyProperties(inquiryBackup, inquiry);
        List<BookingInquiryGoodsDetailsBackup> newGoods = inquiryBackup.getBookingInquiryGoodsDetailsBackupList();
        List<BookingInquiryGoodsDetails> oldGoods = new ArrayList<>();
        for (BookingInquiryGoodsDetailsBackup newG : newGoods) {
            BookingInquiryGoodsDetails goodsDetails = new BookingInquiryGoodsDetails();
            BeanUtils.copyProperties(newG, goodsDetails);
            oldGoods.add(goodsDetails);
        }
        inquiry.setBookingInquiryGoodsDetailsList(oldGoods);
        BookingInquiryResult inquiryResult = new BookingInquiryResult();
        inquiryResult.setUploadStation(inquiryBackup.getUploadStation());
        inquiryResult.setDropStation(inquiryBackup.getDropStation());
        // 0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄）
        switch (inquiryBackup.getLineType()) {
            case "0":
                inquiryResult = zoDTD(inquiry);
                break;
            case "2":
                inquiryResult = zzyDTD(inquiry);
                break;
            case "3":
                inquiryResult = zyDTD(inquiry);
                break;
            case "4":
                inquiryResult = zeDTD(inquiry);
                break;
        }
        BookingInquiryResultBackup resultBackup = new BookingInquiryResultBackup();
        BeanUtils.copyProperties(inquiryResult, resultBackup);
        BookingInquiryBackup inquiryBackup2 = new BookingInquiryBackup();
        BeanUtils.copyProperties(inquiry, inquiryBackup2);
        resultBackup.setBookingInquiry(inquiryBackup2);
        return resultBackup;
    }

    /*中欧————门到门  铁路运费计算*/
    public BookingInquiryResult zoDTD(BookingInquiry bookingInquiry) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String shipmentPlace = bookingInquiry.getShipmentPlace();//提货地
        String receiptPlace = bookingInquiry.getReceiptPlace();//收货地
        String receiveCity = bookingInquiry.getReceiveCity();// 收货城市
        if (goodsType.equals("0")) {
            //去程,门到门，铁路运费等集疏报完价才能确定
            if (eastOrWest.equals("0")) {
                if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
//                    inquiryProvider.xxyoPush(bookingInquiry);
                    if (bookingInquiry.getContainerType().endsWith("RF") ||
                            bookingInquiry.getContainerType().endsWith("OT") ||
                            bookingInquiry.getContainerType().endsWith("HT") ||
                            bookingInquiry.getContainerType().endsWith("MT")) {
                        bookingInquiryResult.setTxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
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
                    } else { // 普箱
                        if (bookingInquiry.getContainerType().startsWith("40") ||
                                bookingInquiry.getContainerType().startsWith("45")) {
                            bookingInquiryResult.setPickUpBoxFee("$ 500");
                        } else if (bookingInquiry.getContainerType().startsWith("20")) {
                            bookingInquiryResult.setPickUpBoxFee("$ 400");
                        }
                    }
                }
                if ("1".equals(bookingInquiry.getContainerBelong())) { // 自备箱
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    bookingInquiryResult.setPickUpFees("0");//提货费
                    bookingInquiryResult.setPickUpExpiration(commonHandlerService.getThirtyDaysLaterDate());
                    bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                    bookingInquiryResult.setPickUpCurrencyType("$");//币种
                    bookingInquiryResult.setEnquiryState("2");
                }
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            } else {
                //回程
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                if ("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih
                    if (!"1".equals(bookingInquiry.getDistributionType())) {
                        // 2、铁路费，查整柜铁路运费表(需要根据集疏反馈的上下货站计算)
                        // 3、送货费查整柜回程送货费zg_return_trip_fee，
                        BookingInquiryResult bir =
                                commonHandlerService.getHCZGDeliveryFees2(
                                        receiptPlace,
                                        receiveCity,
                                        bookingInquiry.getCityCode(),
                                        bookingInquiry.getContainerType(),
                                        bookingInquiry.getContainerNum(),
                                        bookingInquiry.getTruckType(),
                                        bookingInquiry.getBookingTimeFlag());
                        bookingInquiryResult.setDeliveryCurrencyType(bir.getDeliveryCurrencyType());
                        bookingInquiryResult.setDeliveryAddress(bir.getDeliveryAddress());
                        bookingInquiryResult.setHxAddress(bir.getHxAddress());
                        bookingInquiryResult.setDeliveryFees(bir.getDeliveryFees());
                        bookingInquiryResult.setDeliveryFees(bir.getDeliveryFees());
                        bookingInquiryResult.setDeliveryDistance(bir.getDeliveryDistance());
                        bookingInquiryResult.setDeliveryFees(bir.getDeliveryFees());
                        bookingInquiryResult.setDeliveryDistance(bir.getDeliveryDistance());
                        bookingInquiryResult.setHxAddress(bir.getHxAddress());

                        // 还箱费
                        if (StringUtils.isNotEmpty(bookingInquiryResult.getHxAddress())) {
                            if (LanguageEnum.英文.value.equals(language)) {
                                bookingInquiryResult.setHxAddress(
                                        commonHandlerService.getAddressEnName(bookingInquiryResult.getHxAddress()));
                            }
                            bookingInquiry.setHxAddress(bookingInquiryResult.getHxAddress());
                            BusiBoxfee returnBoxfee =
                                    commonHandlerService.getBoxFeesAndEndTime2(
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
                    } else {
                        bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setReturnBoxFee("€ 0");
                    }
                } else {
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                    bookingInquiryResult.setDeliveryFees("0");//派送费
                    bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
                    bookingInquiryResult.setEnquiryState("2");
                }
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        } else {
            //散货
            if (eastOrWest.equals("0")) {
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    // 消息发送到箱型亚欧
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    //委托zih送货
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
            } else {
                //回程
                //集输公路收费
                bookingInquiry.setDropStation(zzCity);//回程门到门站到门，默认是郑州下货站
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }//拼箱且允许zih送货
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    // 消息发送到箱型亚欧
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                //插入询价结果，更新提货地
                bookingInquiryResult.setPickUpAddress(shipmentPlace);//提货地
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setDeliveryAddress(receiptPlace);//收货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*郑中亚————门到门  铁路运费计算*/
    public BookingInquiryResult zzyDTD(BookingInquiry bookingInquiry) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        String goodsType = bookingInquiry.getGoodsType(); // 整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest(); // 去回程
        String language = bookingInquiry.getLanguage(); // 去回程
        String zzCity = "郑州";
        if (goodsType.equals("0")) {
            if (eastOrWest.equals("0")) {
                if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                // 1、公路部提货费用查表
                if ("0".equals(bookingInquiry.getContainerBelong())) {
                    BookingInquiryResult bir = commonHandlerService.getZyPickUpFees(bookingInquiry);
                    bookingInquiryResult.setPickUpDistance(bir.getPickUpDistance());
                    bookingInquiryResult.setPickUpAddress(bir.getPickUpAddress());
                    bookingInquiryResult.setPickUpCurrencyType(bir.getPickUpCurrencyType());
                    bookingInquiryResult.setPickUpFees(bir.getPickUpFees());
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
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    //委托zih送货
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                // +2、铁路费用等集疏报完价才能确定
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            } else { // 回程
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    //回程委托zih提货
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                // 2、铁路费，查整柜铁路运费表(需要根据集疏反馈的上下货站计算)
                // 3、送货费
                bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                if (!"1".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                    // 回程派送
                    bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                    bookingInquiryResult.setDeliveryFees("0");//派送费
                } else {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                    bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                    bookingInquiryResult.setReturnBoxFee("€ 0");
                }
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("￥");
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        } else { // 散货
            if (eastOrWest.equals("0")) { //去程
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                if (bookingInquiry.getIsPickUp().equals("0")) {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
            } else { //回程
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                if (bookingInquiry.getIsDelivery().equals("1")) {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                //插入询价结果，更新提货地
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//收货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*中越————门到门  铁路运费计算*/
    public BookingInquiryResult zyDTD(BookingInquiry bookingInquiry) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        String goodsType = bookingInquiry.getGoodsType(); // 整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest(); // 去回程
        String language = bookingInquiry.getLanguage(); // 去回程
        String zzCity = "郑州";
        if (eastOrWest.equals("0")) { // 去程
            if (goodsType.equals("0")) { // 整柜
                bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
                    // 国内公路询价
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                if ("1".equals(bookingInquiry.getContainerBelong())) { // 自备箱
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    bookingInquiryResult.setPickUpFees("0");//提货费
                    bookingInquiryResult.setPickUpExpiration(commonHandlerService.getThirtyDaysLaterDate());
                    bookingInquiryResult.setPickUpCurrencyType("$");//币种
                    bookingInquiryResult.setEnquiryState("2");
                }
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            } else { // 散货
                bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                // 国内公路询价
//                inquiryProvider.xxyoPush(bookingInquiry);
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            }
        } else { // 回程
            if ("0".equals(goodsType)) { // 整柜
                //1、集疏费用+
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    //回程委托zih提货
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                // 回程派送
                if (!"1".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                    // 回程派送
                    bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                    bookingInquiryResult.setDeliveryFees("0");//派送费
                } else {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                    bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                    bookingInquiryResult.setReturnBoxFee("€ 0");
                }
                // bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("$");

                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*中俄————门到门  铁路运费计算*/
    public BookingInquiryResult zeDTD(BookingInquiry bookingInquiry) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        String goodsType = bookingInquiry.getGoodsType(); // 整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest(); // 去回程
        String language = bookingInquiry.getLanguage(); // 去回程
        String zzCity = "郑州";
        String zzCityEn = "Zhengzhou";
        if (eastOrWest.equals("0")) { // 去程
            if (goodsType.equals("0")) { // 整柜
                bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
                    // 国内公路询价
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                if ("1".equals(bookingInquiry.getContainerBelong())) { // 自备箱
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    bookingInquiryResult.setPickUpFees("0");//提货费
                    bookingInquiryResult.setPickUpExpiration(commonHandlerService.getThirtyDaysLaterDate());
                    bookingInquiryResult.setPickUpCurrencyType("$");//币种
                    bookingInquiryResult.setEnquiryState("2");
                }
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            } else { // 散货
                bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);

                // 国内公路询价
//                inquiryProvider.xxyoPush(bookingInquiry);
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
            }
        } else { // 回程
            if ("0".equals(goodsType)) { // 整柜
                //1、集疏费用+
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    //回程委托zih提货
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                if (!"1".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                    // 回程派送
                    bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                    bookingInquiryResult.setDeliveryFees("0");//派送费
                } else {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                    bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                    bookingInquiryResult.setReturnBoxFee("€ 0");
                }

                // bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("$");

                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    public BookingInquiryResult getJSRailWayFee(BookingInquiry bookingInquiry, BookingInquiryResult bookingInquiryResult) {
        String lineType = bookingInquiry.getLineType();
        String dropStation = "";//下货站
        String uploadStation = "";//上货站
        switch (bookingInquiry.getEastOrWest()) {
            case "0":
                uploadStation = bookingInquiry.getUploadStation();
                if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())) {
                    dropStation = iBusiSiteService.getBusiSiteByName(bookingInquiry.getDropStation(), "zh");
                } else {
                    dropStation = bookingInquiry.getDropStation();
                }
                bookingInquiryResult.setDeliveryFeedbackTime(new Date());
                break;
            case "1":
                //回程有
                if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())) {
                    uploadStation = iBusiSiteService.getBusiSiteByName(bookingInquiry.getUploadStation(), "zh");
                } else {
                    uploadStation = bookingInquiry.getUploadStation();
                }
                dropStation = bookingInquiry.getDropStation();
                bookingInquiryResult.setPickUpFeedbackTime(new Date());
                break;
        }
        bookingInquiry.setUploadStation(uploadStation);
        bookingInquiry.setDropStation(dropStation);
        bookingInquiryResult.setUploadStation(uploadStation);
        bookingInquiryResult.setDropStation(dropStation);
        if (bookingInquiry.getGoodsType().equals("0")) {
            BookingInquiryResult bir = null;
            if (bookingInquiry.getEastOrWest().equals("0") && bookingInquiry.getBookingService().equals("0") && bookingInquiry.getDeliveryType().equals("0")) {
                if (lineType.equals("0")) {
                    bir = commonHandlerService.getZOZGQCPickUpFeesAndRailFee(bookingInquiry);
                } else if (lineType.equals("4")) {
                    bir = commonHandlerService.getZePickUpAndRailwayFees(bookingInquiry);
                } else if (lineType.equals("3")) {
                    bir = commonHandlerService.getZynPickUpAndRailwayFees(bookingInquiry);
                }
                bookingInquiryResult.setPickUpFees(bir.getPickUpFees());//提货费
                bookingInquiryResult.setPickUpDistance(bir.getPickUpDistance());//提货距离
                bookingInquiryResult.setPickUpAddress(bir.getPickUpAddress());//提货地
                bookingInquiryResult.setPickUpCurrencyType(bir.getPickUpCurrencyType());//币种
//                if (!"3".equals(bookingInquiry.getInquiryState())) {
//                    bookingInquiry.setInquiryState("2");
//                }
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
                bookingInquiryResult.setRailwayExpiration(bir.getRailwayExpiration());
            }
            // 国外提还箱平衡费
            if (bookingInquiry.getEastOrWest().equals("0")) {
                if (!"1".equals(bookingInquiry.getDistributionType())) {
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
            if (bookingInquiry.getEastOrWest().equals("1")) {
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
            /*if (bookingInquiry.getEastOrWest().equals("0")) {
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                        bookingInquiry.getHxAddress(),
                        bookingInquiry.getContainerType(),
                        bookingInquiry.getContainerNum(),
                        "1", bookingInquiry.getBookingTimeFlag());
                if (!ObjectUtils.isEmpty(returnBoxfee)) {
                    bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                    bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                }
            } else if (bookingInquiry.getDeliveryType().equals("0")) {
                bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                        bookingInquiry.getTxAddress(),
                        bookingInquiry.getContainerType(),
                        bookingInquiry.getContainerNum(),
                        "0",bookingInquiry.getBookingTimeFlag());
                if (!ObjectUtils.isEmpty(returnBoxfee)) {
                    bookingInquiryResult.setPickUpBoxFee(returnBoxfee.getCost());
                    bookingInquiryResult.setPickUpBoxExpiration(returnBoxfee.getEndTime());
                }
            }*/
        } else {
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
                bookingInquiryResult.setRailwayAging(result1.getRailwayAging());
                bookingInquiryResult.setRailwayExpiration(result1.getRailwayExpiration());
            }
        }
        return bookingInquiryResult;
    }

    /*************************************************************************/
    public BookingInquiryResultBackup getRailWayFeeDTS(BookingInquiryBackup inquiryBackup) {
        BookingInquiry inquiry = new BookingInquiry();
        BeanUtils.copyProperties(inquiryBackup, inquiry);
        List<BookingInquiryGoodsDetailsBackup> newGoods = inquiryBackup.getBookingInquiryGoodsDetailsBackupList();
        List<BookingInquiryGoodsDetails> oldGoods = new ArrayList<>();
        for (BookingInquiryGoodsDetailsBackup newG : newGoods) {
            BookingInquiryGoodsDetails goodsDetails = new BookingInquiryGoodsDetails();
            BeanUtils.copyProperties(newG, goodsDetails);
            oldGoods.add(goodsDetails);
        }
        inquiry.setBookingInquiryGoodsDetailsList(oldGoods);
        BookingInquiryResult inquiryResult = new BookingInquiryResult();
        // 0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄）
        switch (inquiryBackup.getLineType()) {
            case "0":
                inquiryResult = zoDTS(inquiry);
                break;
            case "2":
                inquiryResult = zzyDTS(inquiry);
                break;
            case "3":
                inquiryResult = zyDTS(inquiry);
                break;
            case "4":
                inquiryResult = zeDTS(inquiry);
                break;
        }
        BookingInquiryResultBackup resultBackup = new BookingInquiryResultBackup();
        BeanUtils.copyProperties(inquiryResult, resultBackup);

        BookingInquiryBackup inquiryBackup2 = new BookingInquiryBackup();
        BeanUtils.copyProperties(inquiry, inquiryBackup2);
        resultBackup.setBookingInquiry(inquiryBackup2);
        return resultBackup;
    }

    /*中欧————门到站  铁路运费计算*/
    public BookingInquiryResult zoDTS(BookingInquiry bookingInquiry) {
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String shipmentPlace = bookingInquiry.getShipmentPlace();//提货地
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        if (goodsType.equals("0")) {
            //整柜分去回程
            if (eastOrWest.equals("0")) {
                //去程，查询去程整柜费用表
                if ("0".equals(bookingInquiry.getContainerBelong())) {
                    if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
//                        inquiryProvider.xxyoPush(bookingInquiry);
                        if (LanguageEnum.英文.value.equals(language)) {
                            bookingInquiry.setUploadStation("Zhengzhou");// 默认上货站是郑州
                        } else {
                            bookingInquiry.setUploadStation("郑州");// 默认上货站是郑州
                        }
                        bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
                        if (bookingInquiry.getContainerType().endsWith("RF") ||
                                bookingInquiry.getContainerType().endsWith("OT") ||
                                bookingInquiry.getContainerType().endsWith("HT") ||
                                bookingInquiry.getContainerType().endsWith("MT")) {
                            bookingInquiryResult.setTxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
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
                        } else { // 普箱
                            if (bookingInquiry.getContainerType().startsWith("40") ||
                                    bookingInquiry.getContainerType().startsWith("45")) {
                                bookingInquiryResult.setPickUpBoxFee("$ 500");
                            } else if (bookingInquiry.getContainerType().startsWith("20")) {
                                bookingInquiryResult.setPickUpBoxFee("$ 400");
                            }
                        }
                    } else {
                        ///箱型判断，郑欧线主要是20、40尺箱,整柜去程表
                        bookingInquiryResult = commonHandlerService.getZOZGQCPickUpFeesAndRailFee(bookingInquiry);
                        String uploadStation = bookingInquiryResult.getUploadStation(); //门到站需要获取上货站
                        bookingInquiry.setUploadStation(uploadStation); //默认上货站郑州
                        //门到站整柜去程报完更新审核状态
                        if ("1".equals(bookingInquiry.getClientType()) ||
                                StringUtils.isEmpty(bookingInquiryResult.getRailwayFees()) ||
                                "0".equals(bookingInquiryResult.getRailwayFees()) ||
                                bookingInquiry.getContainerType().endsWith("RF") ||
                                bookingInquiry.getContainerType().endsWith("OT") ||
                                bookingInquiry.getContainerType().endsWith("MT") ||
                                bookingInquiry.getContainerType().endsWith("HT") ||
                                !"3".equals(bookingInquiryResult.getEnquiryState())) {
                            bookingInquiry.setInquiryState("2");
                        } else {
                            bookingInquiry.setInquiryState("3");
                        }
                    }
                    // 提箱平衡费
                    if (!(bookingInquiry.getContainerType().endsWith("RF") ||
                            bookingInquiry.getContainerType().endsWith("OT") ||
                            bookingInquiry.getContainerType().endsWith("HT") ||
                            bookingInquiry.getContainerType().endsWith("MT"))) { // 普箱
                        if (bookingInquiry.getContainerType().startsWith("40") ||
                                bookingInquiry.getContainerType().startsWith("45")) {
                            bookingInquiryResult.setPickUpBoxFee("$ 500");
                        } else if (bookingInquiry.getContainerType().startsWith("20")) {
                            bookingInquiryResult.setPickUpBoxFee("$ 400");
                        }
                    }
                    bookingInquiryResult.setDelFlag("0");
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
                } else { // 自备箱
                    bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                    bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    bookingInquiryResult.setPickUpFees("0");//提货费
                    bookingInquiryResult.setPickUpExpiration(commonHandlerService.getThirtyDaysLaterDate());
                    bookingInquiryResult.setPickUpCurrencyType("$");//币种
                    bookingInquiryResult.setEnquiryState("2");
                    bookingInquiry.setInquiryState("2");
                }
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            } else {
                bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                if ("0".equals(bookingInquiry.getContainerBelong())) {
                    // 还箱费
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    BusiBoxfee returnBoxfee =
                            commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getHxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "1",
                                    bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(returnBoxfee)) {
                        bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                        bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                    }
                } else { // 自备箱
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                }
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setPickUpAddress(shipmentPlace);//提货地
            }
        } else {
            //拼箱
            //1、去程且委托zih提货，对接箱型亚欧系统+国内场站费+去程铁路运费+国外场站费用
            if (eastOrWest.equals("0")) {
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    //1、箱型亚欧系统对接放到消息队列监听计算
                    // 消息发送到箱型亚欧
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                //2、国内场站费，人工/装拆箱；正常加固/复杂加固;提空箱费；打托；
//                        inquiryProvider.gnczPush(bookingInquiry);
                //3、去程铁路运费，上货站是郑州默认，查散货铁路运费表
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                bookingInquiryResult = commonHandlerService.getSHQCRailFee(bookingInquiry);
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                // 计费体积
                bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
            } else {

                bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                //插入询价结果，更新提货地
                bookingInquiryResult.setPickUpAddress(shipmentPlace);//提货地
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*郑中亚————门到站  铁路运费计算*/
    public BookingInquiryResult zzyDTS(BookingInquiry bookingInquiry) {
        String goodsType = bookingInquiry.getGoodsType(); // 整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String zzCity = "郑州";
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        if (goodsType.equals("0")) { // 整柜
            if (eastOrWest.equals("0")) {
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult = commonHandlerService.getZyZGRailFee(bookingInquiry);
                if ("0".equals(bookingInquiry.getContainerBelong())) {
                    if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
//                        inquiryProvider.xxyoPush(bookingInquiry);
                    } else {
                        BookingInquiryResult bir = commonHandlerService.getZyPickUpFees(bookingInquiry);
                        // 赋值
                        bookingInquiryResult.setPickUpDistance(bir.getPickUpDistance());//提货距离
                        bookingInquiryResult.setPickUpAddress(bir.getPickUpAddress());//提货地
                        bookingInquiryResult.setPickUpCurrencyType(bir.getPickUpCurrencyType());//币种
                        bookingInquiryResult.setPickUpFees(bir.getPickUpFees());
                        //门到站整柜去程报完更新审核状态
                        if ("1".equals(bookingInquiry.getClientType()) ||
                                StringUtils.isEmpty(bookingInquiryResult.getRailwayFees()) ||
                                "0".equals(bookingInquiryResult.getRailwayFees()) ||
                                bookingInquiry.getContainerType().endsWith("RF") ||
                                bookingInquiry.getContainerType().endsWith("OT") ||
                                !"3".equals(bookingInquiryResult.getEnquiryState())) {
                            bookingInquiry.setInquiryState("2");
                        } else {
                            bookingInquiry.setInquiryState("3");
                        }
                    }
                    bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
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
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            } else {
                //更新提货地
                bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                if ("0".equals(bookingInquiry.getContainerBelong())) {
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
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
            }
        } else { // 散货
            //1、去程且委托zih提货
            if (eastOrWest.equals("0")) {
                if (bookingInquiry.getIsPickUp().equals("0")) {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                bookingInquiryResult = commonHandlerService.getZySHQCRailFee(bookingInquiry);
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                // 计费体积
                bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
            } else {
                bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                //插入询价结果，更新提货地
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*中越————门到站  铁路运费计算*/
    public BookingInquiryResult zyDTS(BookingInquiry bookingInquiry) {
        String goodsType = bookingInquiry.getGoodsType(); // 整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String zzCity = "郑州";
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        if (eastOrWest.equals("0")) { // 去程
            if (goodsType.equals("0")) { // 整柜
                if ("0".equals(bookingInquiry.getContainerBelong())) {
                    if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
//                        inquiryProvider.xxyoPush(bookingInquiry);
                        bookingInquiry.setUploadStation(
                                LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult = commonHandlerService.getZynZGRailFee(bookingInquiry);
                    } else {
                        bookingInquiryResult = commonHandlerService.getZynPickUpAndRailwayFees(bookingInquiry);
                        // 门到站整柜去程报完更新审核状态
                        if ("1".equals(bookingInquiry.getClientType())
                                || StringUtils.isEmpty(bookingInquiryResult.getRailwayFees())
                                || "0".equals(bookingInquiryResult.getRailwayFees())
                                || bookingInquiry.getContainerType().endsWith("RF")
                                || bookingInquiry.getContainerType().endsWith("OT")
                                || !"3".equals(bookingInquiryResult.getEnquiryState())) {
                            bookingInquiry.setInquiryState("2");
                        } else {
                            bookingInquiry.setInquiryState("3");
                        }
                    }
                    bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace()); // 提货地
                    // 还箱费
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    BusiBoxfee returnBoxfee =
                            commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getHxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "1",
                                    bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(returnBoxfee)) {
                        bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                        bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                    }
                } else { // 自备箱
                    bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                    bookingInquiryResult = commonHandlerService.getZynZGRailFee(bookingInquiry);
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    bookingInquiryResult.setPickUpFees("0");//提货费
                    bookingInquiryResult.setPickUpExpiration(commonHandlerService.getThirtyDaysLaterDate());
                    bookingInquiryResult.setPickUpCurrencyType("$");//币种
                    bookingInquiryResult.setEnquiryState("2");
                    bookingInquiry.setInquiryState("2");
                }
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            } else { // 散货
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                bookingInquiryResult = commonHandlerService.getZynSHQCRailFee(bookingInquiry);
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                // 计费体积
                bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
            }
        } else { // 回程
            if ("0".equals(goodsType)) { // 整柜
                //1、集疏费用+
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    //回程委托zih提货
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                if ("0".equals(bookingInquiry.getContainerBelong())) {
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
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("$");

                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*中俄————门到站  铁路运费计算*/
    public BookingInquiryResult zeDTS(BookingInquiry bookingInquiry) {
        String goodsType = bookingInquiry.getGoodsType(); // 整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String zzCity = "郑州";
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        if (eastOrWest.equals("0")) { // 去程
            if (goodsType.equals("0")) { // 整柜
                if ("0".equals(bookingInquiry.getContainerBelong())) {
                    if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
//                        inquiryProvider.xxyoPush(bookingInquiry);
                        bookingInquiry.setUploadStation(zzCity);
                        bookingInquiry.setUploadStation(
                                LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
                    } else {
                        bookingInquiryResult = commonHandlerService.getZePickUpAndRailwayFees(bookingInquiry);
                        // 门到站整柜去程报完更新审核状态
                        if ("1".equals(bookingInquiry.getClientType())
                                || StringUtils.isEmpty(bookingInquiryResult.getRailwayFees())
                                || "0".equals(bookingInquiryResult.getRailwayFees())
                                || bookingInquiry.getContainerType().endsWith("RF")
                                || bookingInquiry.getContainerType().endsWith("OT")
                                || !"3".equals(bookingInquiryResult.getEnquiryState())) {
                            bookingInquiry.setInquiryState("2");
                        } else {
                            bookingInquiry.setInquiryState("3");
                        }
                    }
                    bookingInquiryResult.setUploadStation(zzCity);
                    bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace()); // 提货地
                    // 还箱费
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    BusiBoxfee returnBoxfee =
                            commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getHxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "1",
                                    bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(returnBoxfee)) {
                        bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                        bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                    }
                } else { // 自备箱
                    bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                    bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    bookingInquiryResult.setPickUpFees("0");//提货费
                    bookingInquiryResult.setPickUpExpiration(commonHandlerService.getThirtyDaysLaterDate());
                    bookingInquiryResult.setPickUpCurrencyType("$");//币种
                    bookingInquiryResult.setEnquiryState("2");
                    bookingInquiry.setInquiryState("2");
                }
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            } else { // 散货
                bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                bookingInquiryResult = commonHandlerService.getZeSHQCRailFee(bookingInquiry);
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                // 计费体积
                bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
            }
        } else { // 回程
            if ("0".equals(goodsType)) { // 整柜
                //1、集疏费用+
                if (bookingInquiry.getIsPickUp().equals("0")) {
                    //回程委托zih提货
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                if ("0".equals(bookingInquiry.getContainerBelong())) {
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
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("$");

                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*************************************************************************/
    public BookingInquiryResultBackup getRailWayFeeSTD(BookingInquiryBackup inquiryBackup) {
        BookingInquiry inquiry = new BookingInquiry();
        BeanUtils.copyProperties(inquiryBackup, inquiry);
        List<BookingInquiryGoodsDetailsBackup> newGoods = inquiryBackup.getBookingInquiryGoodsDetailsBackupList();
        List<BookingInquiryGoodsDetails> oldGoods = new ArrayList<>();
        for (BookingInquiryGoodsDetailsBackup newG : newGoods) {
            BookingInquiryGoodsDetails goodsDetails = new BookingInquiryGoodsDetails();
            BeanUtils.copyProperties(newG, goodsDetails);
            oldGoods.add(goodsDetails);
        }
        inquiry.setBookingInquiryGoodsDetailsList(oldGoods);
        BookingInquiryResult inquiryResult = new BookingInquiryResult();
        // 0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄）
        switch (inquiryBackup.getLineType()) {
            case "0":
                inquiryResult = zoSTD(inquiry);
                break;
            case "2":
                inquiryResult = zzySTD(inquiry);
                break;
            case "3":
                inquiryResult = zySTD(inquiry);
                break;
            case "4":
                inquiryResult = zeSTD(inquiry);
                break;
        }
        BookingInquiryResultBackup resultBackup = new BookingInquiryResultBackup();
        BeanUtils.copyProperties(inquiryResult, resultBackup);
        BookingInquiryBackup inquiryBackup2 = new BookingInquiryBackup();
        BeanUtils.copyProperties(inquiry, inquiryBackup2);
        resultBackup.setBookingInquiry(inquiryBackup2);
        return resultBackup;
    }

    /*中欧————站到门  铁路运费计算*/
    public BookingInquiryResult zoSTD(BookingInquiry bookingInquiry) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String receiptPlace = bookingInquiry.getReceiptPlace();//收货地
        String receiveCity = bookingInquiry.getReceiveCity();// 收货城市
        if (goodsType.equals("0")) {
            //整柜
            if (eastOrWest.equals("0")) {
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    // 委托送货
                    // +集疏费用（包含还箱费、公路费）
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                if ("0".equals(bookingInquiry.getContainerBelong())) {
                    if ("1".equals(bookingInquiry.getDeliverySelfType())) {
                        // 整柜 0整柜到堆场
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
                        if (!(bookingInquiry.getContainerType().endsWith("RF") ||
                                bookingInquiry.getContainerType().endsWith("OT") ||
                                bookingInquiry.getContainerType().endsWith("HT") ||
                                bookingInquiry.getContainerType().endsWith("MT"))) { // 普箱
                            if (bookingInquiry.getContainerType().startsWith("40") ||
                                    bookingInquiry.getContainerType().startsWith("45")) {
                                bookingInquiryResult.setPickUpBoxFee("$ 500");
                            } else if (bookingInquiry.getContainerType().startsWith("20")) {
                                bookingInquiryResult.setPickUpBoxFee("$ 400");
                            }
                        }
                    } else {
                        if (bookingInquiry.getContainerType().endsWith("RF") ||
                                bookingInquiry.getContainerType().endsWith("OT") ||
                                bookingInquiry.getContainerType().endsWith("HT") ||
                                bookingInquiry.getContainerType().endsWith("MT")) {
                            bookingInquiryResult.setTxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
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
                        } else { // 普箱
                            if (bookingInquiry.getContainerType().startsWith("40") ||
                                    bookingInquiry.getContainerType().startsWith("45")) {
                                bookingInquiryResult.setPickUpBoxFee("$ 500");
                            } else if (bookingInquiry.getContainerType().startsWith("20")) {
                                bookingInquiryResult.setPickUpBoxFee("$ 400");
                            }
                        }
                    }
                }
                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());//上货站
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());                                                            //收货地
                bookingInquiryResult.setDelFlag("0");

            } else {
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//站到门下货站
                //整柜到堆场
                // 铁路费，回程有上货站，下货站固定是郑州，整柜铁路运费表中计算
                bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
                if ("0".equals(bookingInquiry.getContainerBelong()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                    // 整柜 1整柜到堆场
                    // 国外提箱费
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                            bookingInquiry.getTxAddress(),
                            bookingInquiry.getContainerType(),
                            bookingInquiry.getContainerNum(),
                            "0", bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(returnBoxfee)) {
                        bookingInquiryResult.setPickUpBoxFee(returnBoxfee.getCost());
                        bookingInquiryResult.setPickUpBoxExpiration(returnBoxfee.getEndTime());
                    }
                }
                if ("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih
                    if (!"1".equals(bookingInquiry.getDistributionType())) {
                        // +送货费（整柜回程送货费用表）
                        BookingInquiryResult bookingInquiryResult1 = commonHandlerService.getHCZGDeliveryFees2(
                                receiptPlace, receiveCity, bookingInquiry.getCityCode(), bookingInquiry.getContainerType(),
                                bookingInquiry.getContainerNum(), bookingInquiry.getTruckType(), bookingInquiry.getBookingTimeFlag());
                        bookingInquiryResult.setDeliveryCurrencyType(bookingInquiryResult1.getDeliveryCurrencyType());//币种
                        bookingInquiryResult.setDeliveryAddress(bookingInquiryResult1.getDeliveryAddress());//派送地
                        bookingInquiryResult.setDeliveryFees(bookingInquiryResult1.getDeliveryFees());//派送费
                        bookingInquiryResult.setDeliveryDistance(bookingInquiryResult1.getDeliveryDistance());//距离
                        bookingInquiryResult.setDeliveryExpiration(bookingInquiryResult1.getDeliveryExpiration()); // 有效期
                        bookingInquiryResult.setEnquiryState(bookingInquiryResult1.getEnquiryState());
                        bookingInquiryResult.setHxAddress(bookingInquiryResult1.getHxAddress());
                        //还箱费
                        if (StringUtils.isNotEmpty(bookingInquiryResult1.getHxAddress())) {
                            bookingInquiry.setHxAddress(bookingInquiryResult1.getHxAddress());
                            bookingInquiryResult.setHxAddress(bookingInquiryResult1.getHxAddress());
                            BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiryResult1.getHxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "1",
                                    bookingInquiry.getBookingTimeFlag());
                            if (!ObjectUtils.isEmpty(returnBoxfee)) {
                                bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                                bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                            }
                        }
                    } else {
//                        inquiryProvider.xxyoPush(bookingInquiry);
                        bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setReturnBoxFee("€ 0");
                    }
                } else { // 自备箱
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                    bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//派送地
                    bookingInquiryResult.setDeliveryFees("0");//派送费
                    bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
                    bookingInquiryResult.setEnquiryState("2");
                }
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setRailwayCurrencyType("$");
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                if ("1".equals(bookingInquiry.getClientType()) ||
                        StringUtils.isEmpty(bookingInquiryResult.getRailwayFees()) ||
                        "0".equals(bookingInquiryResult.getRailwayFees()) ||
                        StringUtils.isEmpty(bookingInquiryResult.getHxAddress()) ||
                        !"3".equals(bookingInquiryResult.getEnquiryState())
                ) {
                    bookingInquiry.setInquiryState("2");
                } else {
                    bookingInquiry.setInquiryState("3");
                }
                // 冷藏箱
                if (bookingInquiry.getGoodsType().equals("0") &&
                        bookingInquiry.getContainerType().endsWith("RF")
                ) {
                    bookingInquiry.setInquiryState("2");
                }
                // 整柜拆箱派送
                if (bookingInquiry.getGoodsType().equals("0") &&
                        "1".equals(bookingInquiry.getDistributionType())
                ) {
                    bookingInquiry.setInquiryState("1");
                }
            }
        } else {
            //散货
            if (eastOrWest.equals("0")) {
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());//上货站
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());                                                            //收货地
                bookingInquiryResult.setDelFlag("0");
            } else {
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//站到门下货站
                bookingInquiryResult = commonHandlerService.getSHQCRailFee(bookingInquiry);
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    // 消息发送到箱型亚欧
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                // 计费体积
                bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*郑中亚————站到门  铁路运费计算*/
    public BookingInquiryResult zzySTD(BookingInquiry bookingInquiry) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String zzCity = "郑州";
        if (goodsType.equals("0")) {
            //整柜
            if (eastOrWest.equals("0")) {
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                if ("0".equals(bookingInquiry.getContainerBelong()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                    // 国内提箱费
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                            bookingInquiry.getTxAddress(),
                            bookingInquiry.getContainerType(),
                            bookingInquiry.getContainerNum(),
                            "0", bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(returnBoxfee)) {
                        bookingInquiryResult.setPickUpBoxFee(returnBoxfee.getCost());
                        bookingInquiryResult.setPickUpBoxExpiration(returnBoxfee.getEndTime());
                    }
                }
                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());//上货站
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());                                                            //收货地
                bookingInquiryResult.setDelFlag("0");
            } else {
                bookingInquiry.setDropStation(zzCity);//站到门下货站
                //整柜到堆场
                if ("0".equals(bookingInquiry.getContainerBelong()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                    // 整柜 0整柜到堆场
                    // 国外提箱费
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    BusiBoxfee returnBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                            bookingInquiry.getTxAddress(),
                            bookingInquiry.getContainerType(),
                            bookingInquiry.getContainerNum(),
                            "0", bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(returnBoxfee)) {
                        bookingInquiryResult.setPickUpBoxFee(returnBoxfee.getCost());
                        bookingInquiryResult.setPickUpBoxExpiration(returnBoxfee.getEndTime());
                    }
                } else {
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                }
                if ("0".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                    // 回程派送
                    bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                    bookingInquiryResult.setDeliveryFees("0");//派送费
                    bookingInquiry.setInquiryState("2");
                } else {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                    bookingInquiry.setInquiryState("1");
                    bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                    bookingInquiryResult.setReturnBoxFee("€ 0");
                }
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("￥");
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        } else {
            //散货
            if (eastOrWest.equals("0")) {
                if (bookingInquiry.getIsDelivery().equals("1")) {
                    bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
                }
                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());//上货站
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());                                                            //收货地
                bookingInquiryResult.setDelFlag("0");
            } else {
                //回程
                // +散货铁路费
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//站到门下货站
                bookingInquiryResult = commonHandlerService.getSHQCRailFee(bookingInquiry);
                if (bookingInquiry.getIsDelivery().equals("1")) {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                }
                bookingInquiryResult.setDelFlag("0");
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*中越————站到门  铁路运费计算*/
    public BookingInquiryResult zySTD(BookingInquiry bookingInquiry) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String goodsType = bookingInquiry.getGoodsType();
        if (eastOrWest.equals("0")) { // 去程
            bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
            if ("0".equals(bookingInquiry.getContainerBelong()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                // 整柜 0整柜到堆场
                // 国内提箱费
                bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                BusiBoxfee pickUpBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                        bookingInquiry.getTxAddress(),
                        bookingInquiry.getContainerType(),
                        bookingInquiry.getContainerNum(),
                        "0", bookingInquiry.getBookingTimeFlag());
                if (!ObjectUtils.isEmpty(pickUpBoxfee)) {
                    bookingInquiryResult.setPickUpBoxFee(pickUpBoxfee.getCost());
                    bookingInquiryResult.setPickUpBoxExpiration(pickUpBoxfee.getEndTime());
                }
            }
            bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
            bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//提货地
            bookingInquiryResult.setPickUpAddress(bookingInquiry.getReceiptPlace());//提货地
        } else {
            if ("0".equals(goodsType)) { // 整柜
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
                // 回程派送
                if (!"1".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                    // 回程派送
                    bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                    bookingInquiryResult.setDeliveryFees("0");//派送费
                    bookingInquiry.setInquiryState("2");
                } else {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                    bookingInquiry.setInquiryState("1");
                    bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                    bookingInquiryResult.setReturnBoxFee("€ 0");
                }
                // bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("$");

                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*中俄————站到门  铁路运费计算*/
    public BookingInquiryResult zeSTD(BookingInquiry bookingInquiry) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        if (eastOrWest.equals("0")) { // 去程
            bookingInquiryResult = getJSRailWayFee(bookingInquiry, bookingInquiryResult);
            bookingInquiryResult.setUploadStation("郑州");
            bookingInquiryResult.setPickUpAddress(bookingInquiry.getReceiptPlace());//提货地
            if ("0".equals(bookingInquiry.getContainerBelong()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                // 整柜 0整柜到堆场
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
            bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(bookingInquiry.getLanguage()) ? zzCityEn : zzCity);
            bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//提货地
        } else { // 回程
            if ("0".equals(bookingInquiry.getGoodsType())) { // 整柜
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
                if (!"1".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                    // 回程派送
                    bookingInquiryResult.setDeliveryCurrencyType("$");//币种
                    bookingInquiryResult.setDeliveryFees("0");//派送费
                    bookingInquiry.setInquiryState("2");
                } else {
//                    inquiryProvider.xxyoPush(bookingInquiry);
                    bookingInquiry.setInquiryState("1");
                    bookingInquiryResult.setHxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                    bookingInquiryResult.setReturnBoxFee("€ 0");
                }
                // bookingInquiryResult.setDeliveryExpiration(commonHandlerService.getThirtyDaysLaterDate()); // 有效期
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("$");

                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());
                bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//回程门到门站到门，默认是郑州下货站
                bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
            }
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return bookingInquiryResult;
    }

    /*************************************************************************/
    public BookingInquiryResultBackup getRailWayFeeSTS(BookingInquiryBackup inquiryBackup) {
        BookingInquiry inquiry = new BookingInquiry();
        BeanUtils.copyProperties(inquiryBackup, inquiry);

        List<BookingInquiryGoodsDetailsBackup> newGoods = inquiryBackup.getBookingInquiryGoodsDetailsBackupList();
        List<BookingInquiryGoodsDetails> oldGoods = new ArrayList<>();
        for (BookingInquiryGoodsDetailsBackup newG : newGoods) {
            BookingInquiryGoodsDetails goodsDetails = new BookingInquiryGoodsDetails();
            BeanUtils.copyProperties(newG, goodsDetails);
            oldGoods.add(goodsDetails);
        }
        inquiry.setBookingInquiryGoodsDetailsList(oldGoods);
        BookingInquiryResult inquiryResult = new BookingInquiryResult();
        // 0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄）
        switch (inquiryBackup.getLineType()) {
            case "0":
                inquiryResult = zoSTS(inquiry);
                break;
            case "2":
                inquiryResult = zzySTS(inquiry);
                break;
            case "3":
                inquiryResult = zySTS(inquiry);
                break;
            case "4":
                inquiryResult = zeSTS(inquiry);
                break;
        }
        BookingInquiryResultBackup resultBackup = new BookingInquiryResultBackup();
        BeanUtils.copyProperties(inquiryResult, resultBackup);
        BookingInquiryBackup inquiryBackup2 = new BookingInquiryBackup();
        BeanUtils.copyProperties(inquiry, inquiryBackup2);
        resultBackup.setBookingInquiry(inquiryBackup2);
        return resultBackup;
    }

    /*中欧————站到站  铁路运费计算*/
    public BookingInquiryResult zoSTS(BookingInquiry bookingInquiry) {
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        BookingInquiryResult bir;
        if (goodsType.equals("0")) {
            // +铁路费用（具体参考费用表）
            BookingInquiryResult bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
            if ("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih
                // 还箱费
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                BusiBoxfee returnBoxfee =
                        commonHandlerService.getBoxFeesAndEndTime2(
                                bookingInquiry.getHxAddress(),
                                bookingInquiry.getContainerType(),
                                bookingInquiry.getContainerNum(),
                                "1",
                                bookingInquiry.getBookingTimeFlag());
                if (!ObjectUtils.isEmpty(returnBoxfee)) {
                    bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                    bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                }
                // 客户自送货方式判断
                if ("1".equals(bookingInquiry.getDeliverySelfType())) {
                    // 整柜 0整柜到堆场
                    // 国内提箱费
                    // 提箱费
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    BusiBoxfee pickgoodsFee =
                            commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getTxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "0",
                                    bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(pickgoodsFee)) {
                        bookingInquiryResult.setPickUpBoxFee(pickgoodsFee.getCost());
                        bookingInquiryResult.setPickUpBoxExpiration(pickgoodsFee.getEndTime());
                    }
                    if (eastOrWest.equals("0")) {
                        if (!(bookingInquiry.getContainerType().endsWith("RF") ||
                                bookingInquiry.getContainerType().endsWith("OT") ||
                                bookingInquiry.getContainerType().endsWith("HT") ||
                                bookingInquiry.getContainerType().endsWith("MT"))) { // 普箱
                            if (bookingInquiry.getContainerType().startsWith("40") ||
                                    bookingInquiry.getContainerType().startsWith("45")) {
                                bookingInquiryResult.setPickUpBoxFee("$ 500");
                            } else if (bookingInquiry.getContainerType().startsWith("20")) {
                                bookingInquiryResult.setPickUpBoxFee("$ 400");
                            }
                        }
                    }
                } else if (eastOrWest.equals("0")) {
                    // 散货到堆场，需要场站费对接国内场站费，
                    // inquiryProvider.gnczPush(bookingInquiry);
                    if (bookingInquiry.getContainerType().endsWith("RF") ||
                            bookingInquiry.getContainerType().endsWith("OT") ||
                            bookingInquiry.getContainerType().endsWith("HT") ||
                            bookingInquiry.getContainerType().endsWith("MT")) {
                        bookingInquiryResult.setTxAddress(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
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
                    } else { // 普箱
                        if (bookingInquiry.getContainerType().startsWith("40") ||
                                bookingInquiry.getContainerType().startsWith("45")) {
                            bookingInquiryResult.setPickUpBoxFee("$ 500");
                        } else if (bookingInquiry.getContainerType().startsWith("20")) {
                            bookingInquiryResult.setPickUpBoxFee("$ 400");
                        }
                    }
                }
            }
            bookingInquiryResult.setRailwayCurrencyType("$");
            bir = bookingInquiryResult;
        } else {
            //拼箱
            BookingInquiryResult bookingInquiryResult = commonHandlerService.getSHQCRailFee(bookingInquiry);
            // +国内场站费
            //国内场站费
//                        inquiryProvider.gnczPush(bookingInquiry);
            // 计费体积
            bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
            bir = bookingInquiryResult;
        }
        if ("1".equals(bookingInquiry.getClientType()) ||
                StringUtils.isEmpty(bir.getRailwayFees()) ||
                "0".equals(bir.getRailwayFees())) {
            bookingInquiry.setInquiryState("2");
        } else {
            bookingInquiry.setInquiryState("3");
        }
        // 特种箱
        if (bookingInquiry.getGoodsType().equals("0") &&
                (bookingInquiry.getContainerType().endsWith("RF") ||
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
                if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal("1200")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsWidth()).compareTo(new BigDecimal("220")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsHeight()).compareTo(new BigDecimal("250")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal("15000")) == 1) {
                    over = true;
                }
            }
            if (isNaked || over || stationIsOk) {
                bookingInquiry.setInquiryState("2");
            }
        }
        bir.setBookingInquiry(bookingInquiry);
        return bir;
    }

    /*郑中亚————站到站  铁路运费计算*/
    public BookingInquiryResult zzySTS(BookingInquiry bookingInquiry) {
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        BookingInquiryResult bir;
        if (goodsType.equals("0")) { // 整柜
            BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
            // 铁路费用（具体参考费用表）
            if ("0".equals(eastOrWest)) { // 去程
                bookingInquiryResult = commonHandlerService.getZyZGRailFee(bookingInquiry);
            } else { // 回程
                bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                bookingInquiryResult.setEnquiryState("2");
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("￥");
                bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());
                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());
            }
            if ("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih
                // 还箱费
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                BusiBoxfee returnBoxfee =
                        commonHandlerService.getBoxFeesAndEndTime2(
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
                    BusiBoxfee pickBoxfee =
                            commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getTxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "0",
                                    bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(pickBoxfee)) {
                        bookingInquiryResult.setPickUpBoxFee(pickBoxfee.getCost());
                        bookingInquiryResult.setPickUpBoxExpiration(pickBoxfee.getEndTime());
                    }
                }
            }
            bir = bookingInquiryResult;
        } else { // 散货
            BookingInquiryResult bookingInquiryResult = commonHandlerService.getZySHQCRailFee(bookingInquiry);
            // 计费体积
            bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
            bir = bookingInquiryResult;
        }
        if ("1".equals(bookingInquiry.getClientType()) ||
                StringUtils.isEmpty(bir.getRailwayFees()) ||
                "0".equals(bir.getRailwayFees())) {
            bookingInquiry.setInquiryState("2");
        } else {
            bookingInquiry.setInquiryState("3");
        }
        // 特种箱
        if (bookingInquiry.getGoodsType().equals("0") &&
                (bookingInquiry.getContainerType().endsWith("RF") ||
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
                if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal("1200")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsWidth()).compareTo(new BigDecimal("220")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsHeight()).compareTo(new BigDecimal("250")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal("15000")) == 1) {
                    over = true;
                }
            }
            if (isNaked || over || stationIsOk) {
                bookingInquiry.setInquiryState("2");
            }
        }
        bir.setBookingInquiry(bookingInquiry);
        return bir;
    }

    /*中越————站到站  铁路运费计算*/
    public BookingInquiryResult zySTS(BookingInquiry bookingInquiry) {
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        BookingInquiryResult bir;
        if (goodsType.equals("0")) { // 整柜
            BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
            // 铁路费用（具体参考费用表）
            if ("0".equals(eastOrWest)) { // 去程
                bookingInquiryResult = commonHandlerService.getZynZGRailFee(bookingInquiry);
            } else { // 回程
                bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                bookingInquiryResult.setEnquiryState("2");
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("$");
                bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());
                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());
            }
            if ("0".equals(bookingInquiry.getContainerBelong())) {
                // 还箱费
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                BusiBoxfee returnBoxfee =
                        commonHandlerService.getBoxFeesAndEndTime2(
                                bookingInquiry.getHxAddress(),
                                bookingInquiry.getContainerType(),
                                bookingInquiry.getContainerNum(),
                                "1",
                                bookingInquiry.getBookingTimeFlag());
                if (!ObjectUtils.isEmpty(returnBoxfee)) {
                    bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                    bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                }
                // 客户自送货方式判断
                if ("1".equals(bookingInquiry.getDeliverySelfType())) {
                    // 整柜 0整柜到堆场
                    // 国内提箱费
                    // 提箱费
                    bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                    BusiBoxfee pickBoxfee =
                            commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getTxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "0",
                                    bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(pickBoxfee)) {
                        bookingInquiryResult.setPickUpBoxFee(pickBoxfee.getCost());
                        bookingInquiryResult.setPickUpBoxExpiration(pickBoxfee.getEndTime());
                    }
                } else {
                    // 散货到堆场，需要场站费对接国内场站费，
                    // inquiryProvider.gnczPush(bookingInquiry);
                }
            }
            bir = bookingInquiryResult;
        } else { // 散货
            BookingInquiryResult bookingInquiryResult = commonHandlerService.getZynSHQCRailFee(bookingInquiry);
            bir = bookingInquiryResult;
            // 计费体积
            bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
        }
        if ("1".equals(bookingInquiry.getClientType()) ||
                StringUtils.isEmpty(bir.getRailwayFees()) ||
                "0".equals(bir.getRailwayFees())) {
            bookingInquiry.setInquiryState("2");
        } else {
            bookingInquiry.setInquiryState("3");
        }
        // 特种箱
        if (bookingInquiry.getGoodsType().equals("0") &&
                (bookingInquiry.getContainerType().endsWith("RF") ||
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
                if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal("1200")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsWidth()).compareTo(new BigDecimal("220")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsHeight()).compareTo(new BigDecimal("250")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal("15000")) == 1) {
                    over = true;
                }
            }
            if (isNaked || over || stationIsOk) {
                bookingInquiry.setInquiryState("2");
            }
        }
        bir.setBookingInquiry(bookingInquiry);
        return bir;
    }

    /*中俄————站到站  铁路运费计算*/
    public BookingInquiryResult zeSTS(BookingInquiry bookingInquiry) {
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String eastOrWest = bookingInquiry.getEastOrWest();
        BookingInquiryResult bir = new BookingInquiryResult();
        if (eastOrWest.equals("0")) { // 去程
            if (goodsType.equals("0")) { // 整柜
                // 铁路费用（具体参考费用表）
                BookingInquiryResult bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
                if ("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih

                    // 还箱费
                    bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                    BusiBoxfee returnBoxfee =
                            commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiry.getHxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "1",
                                    bookingInquiry.getBookingTimeFlag());
                    if (!ObjectUtils.isEmpty(returnBoxfee)) {
                        bookingInquiryResult.setReturnBoxFee(returnBoxfee.getCost());
                        bookingInquiryResult.setReturnBoxExpiration(returnBoxfee.getEndTime());
                    }
                    // 客户自送货方式判断
                    if ("1".equals(bookingInquiry.getDeliverySelfType())) {
                        // 整柜 0整柜到堆场
                        // 国内提箱费
                        // 提箱费
                        bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                        BusiBoxfee pickBoxfee =
                                commonHandlerService.getBoxFeesAndEndTime2(
                                        bookingInquiry.getTxAddress(),
                                        bookingInquiry.getContainerType(),
                                        bookingInquiry.getContainerNum(),
                                        "0",
                                        bookingInquiry.getBookingTimeFlag());
                        if (!ObjectUtils.isEmpty(pickBoxfee)) {
                            bookingInquiryResult.setPickUpBoxFee(pickBoxfee.getCost());
                            bookingInquiryResult.setPickUpBoxExpiration(pickBoxfee.getEndTime());
                        }
                    } else {
                        // 散货到堆场，需要场站费对接国内场站费，
                        // inquiryProvider.gnczPush(bookingInquiry);
                    }
                }
                bir = bookingInquiryResult;
            } else { // 散货
                BookingInquiryResult bookingInquiryResult = commonHandlerService.getZeSHQCRailFee(bookingInquiry);
                // 计费体积
                bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
                bir = bookingInquiryResult;
            }
        } else { // 回程
            if ("0".equals(goodsType)) { // 整柜
                BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                bookingInquiryResult.setHxAddress(bookingInquiry.getHxAddress());
                bookingInquiryResult.setEnquiryState("2");
                // 回程铁路
                bookingInquiryResult.setRailwayFees("0");
                bookingInquiryResult.setRailwayCurrencyType("$");
                if ("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih
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
                    // 客户自送货方式判断
                    if ("1".equals(bookingInquiry.getDeliverySelfType())) {
                        // 整柜 0整柜到堆场
                        // 国内提箱费
                        // 提箱费
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
                bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());
                bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());
            }
        }
        if ("1".equals(bookingInquiry.getClientType()) ||
                StringUtils.isEmpty(bir.getRailwayFees()) ||
                "0".equals(bir.getRailwayFees())) {
            bookingInquiry.setInquiryState("2");
        } else {
            bookingInquiry.setInquiryState("3");
        }
        // 特种箱
        if (bookingInquiry.getGoodsType().equals("0") &&
                (bookingInquiry.getContainerType().endsWith("RF") ||
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
                if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal("1200")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsWidth()).compareTo(new BigDecimal("220")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsHeight()).compareTo(new BigDecimal("250")) == 1 ||
                        new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal("15000")) == 1) {
                    over = true;
                }
            }
            if (isNaked || over || stationIsOk) {
                bookingInquiry.setInquiryState("2");
            }
        }
        bir.setBookingInquiry(bookingInquiry);
        return bir;
    }
}
