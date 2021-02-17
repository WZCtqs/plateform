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
 * 郑欧线路计费服务
 */
@Slf4j
@Service
public class ZoCalculateService {

    @Autowired
    private IBookingInquiryService bookingInquiryService;
    @Autowired
    private BookingInquiryResultMapper bookingInquiryResultMapper;
    @Autowired
    private InquiryProvider inquiryProvider;
    @Autowired
    private CommonHandlerService commonHandlerService;

    /**
     * 郑欧线路计费计算
     */
    public int zoCalculateAndInsertBookingInquiryResult(BookingInquiry bookingInquiry) {
        Long inquiryId = bookingInquiry.getId();
        String goodsType = bookingInquiry.getGoodsType();//整柜/拼箱
        String bookService = bookingInquiry.getBookingService();//站点
        String eastOrWest = bookingInquiry.getEastOrWest();
        String shipmentPlace = bookingInquiry.getShipmentPlace();//提货地
        String receiptPlace = bookingInquiry.getReceiptPlace();//收货地
        String receiveCity = bookingInquiry.getReceiveCity();// 收货城市
        String language = bookingInquiry.getLanguage(); // 语言
        String zzCity = "郑州";
        String zzCityEn = "Zhengzhou";
        //无论那条线路去回程，铁路运费是必须查表获取的，所以询价结果反馈表是新增
        switch (bookService) {
            case "1":
                // 门到站
                //区分整柜
                if (goodsType.equals("0")) {
                    //整柜分去回程
                    if (eastOrWest.equals("0")) {
                        BookingInquiryResult bookingInquiryResult;
                        //去程，查询去程整柜费用表
                        if("0".equals(bookingInquiry.getContainerBelong())){
                            if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
                                inquiryProvider.xxyoPush(bookingInquiry);
                                if (LanguageEnum.英文.value.equals(language)) {
                                    bookingInquiry.setUploadStation("Zhengzhou");// 默认上货站是郑州
                                } else {
                                    bookingInquiry.setUploadStation("郑州");// 默认上货站是郑州
                                }
                                bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
                                if (bookingInquiry.getContainerType().endsWith("RF") ||
                                        bookingInquiry.getContainerType().endsWith("OT") ||
                                        bookingInquiry.getContainerType().endsWith("HT") ||
                                        bookingInquiry.getContainerType().endsWith("MT")  ) {
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
                                        bookingInquiryResult.setPickUpBoxFee("$ " + (500*bookingInquiry.getContainerNum()));
                                    } else if (bookingInquiry.getContainerType().startsWith("20") ){
                                        bookingInquiryResult.setPickUpBoxFee("$ " + (400*bookingInquiry.getContainerNum()));
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
                                    bookingInquiryResult.setPickUpBoxFee("$ " + (500*bookingInquiry.getContainerNum()));
                                } else if (bookingInquiry.getContainerType().startsWith("20") ){
                                    bookingInquiryResult.setPickUpBoxFee("$ " + (400*bookingInquiry.getContainerNum()));
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
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry); //更新上货站

                    } else {
                        //整柜 回程，
                        // 对接国外集疏，消息发送
                        //回程默认下货站是郑州
                        inquiryProvider.jsPush(bookingInquiry);
                        // 更新提货地
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
                        } else { // 自备箱
                            bookingInquiryResult.setTxAddress(bookingInquiry.getTxAddress());
                        }
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setPickUpAddress(shipmentPlace);//提货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                    }
                } else {
                    //拼箱
                    //1、去程且委托zih提货，对接箱型亚欧系统+国内场站费+去程铁路运费+国外场站费用
                    if (eastOrWest.equals("0")) {
                        //拼箱且允许zih自提货
                        if (bookingInquiry.getIsPickUp().equals("0")) {
                            //1、箱型亚欧系统对接放到消息队列监听计算
                            // 消息发送到箱型亚欧
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        //2、国内场站费，人工/装拆箱；正常加固/复杂加固;提空箱费；打托；
//                        inquiryProvider.gnczPush(bookingInquiry);
                        //3、去程铁路运费，上货站是郑州默认，查散货铁路运费表
                        bookingInquiry.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//默认上货站是郑州
                        BookingInquiryResult bookingInquiryResult = commonHandlerService.getSHQCRailFee(bookingInquiry);
                        bookingInquiryResult.setDelFlag("0");
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        // 计费体积
                        bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新上货站
                        //4、国外场站费，暂不对接
                    } else {
                        //回程
                        // 1、集输公路收费(监听里写)
                        // 消息发送
                        inquiryProvider.jsPush(bookingInquiry);
                        //插入询价结果，更新提货地
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setPickUpAddress(shipmentPlace);//提货地
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);

                    }
                }
                break;
            case "0":
                //门到门
                //整柜
                if (goodsType.equals("0")) {
                    //去程,门到门，铁路运费等集疏报完价才能确定
                    if (eastOrWest.equals("0")) {
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        if ("1".equals(bookingInquiry.getDeliveryType())) { // 散货到堆场
                            inquiryProvider.xxyoPush(bookingInquiry);
                            if (bookingInquiry.getContainerType().endsWith("RF") ||
                                    bookingInquiry.getContainerType().endsWith("OT") ||
                                    bookingInquiry.getContainerType().endsWith("HT") ||
                                    bookingInquiry.getContainerType().endsWith("MT")  ) {
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
                                    bookingInquiryResult.setPickUpBoxFee("$ " + (500*bookingInquiry.getContainerNum()));
                                } else if (bookingInquiry.getContainerType().startsWith("20") ){
                                    bookingInquiryResult.setPickUpBoxFee("$ " + (400*bookingInquiry.getContainerNum()));
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
                        // +2、铁路费用等集疏报完价才能确定
                        // 3、集疏费用,发送消
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            //委托zih送货
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        // 4、（包含还箱费）查表，集输系统返
                        bookingInquiryResult.setDelFlag("0");
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新上货站
                    } else {
                        //回程
                        //1、集疏费用+
                        if (bookingInquiry.getIsPickUp().equals("0")) {
                            //回程委托zih提货
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        // 2、铁路费，查整柜铁路运费表(需要根据集疏反馈的上下货站计算)
                        // 3、送货费查整柜回程送货费zg_return_trip_fee，
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        if("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih
                            if(!"1".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                                bookingInquiryResult = commonHandlerService.getHCZGDeliveryFees2(
                                        receiptPlace, receiveCity,bookingInquiry.getCityCode(), bookingInquiry.getContainerType(),
                                        bookingInquiry.getContainerNum(), bookingInquiry.getTruckType(),bookingInquiry.getBookingTimeFlag());
                                //还箱费
                                if (StringUtils.isNotEmpty(bookingInquiryResult.getHxAddress())) {
                                    if (LanguageEnum.英文.value.equals(language)) {
                                        bookingInquiryResult.setHxAddress(
                                                commonHandlerService.getAddressEnName(bookingInquiryResult.getHxAddress()));
                                    }
                                    //还箱费
                                    bookingInquiry.setHxAddress(bookingInquiryResult.getHxAddress());
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
                            } else { // 回程整柜散货派送,发送公路询价
                                inquiryProvider.xxyoPush(bookingInquiry);
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
                        bookingInquiryResult.setInquiryId(inquiryId);
                        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新下货站
                    }

                } else {
                    //散货
                    if (eastOrWest.equals("0")) {
                        //去程
                        //拼箱且允许zih提货
                        if (bookingInquiry.getIsPickUp().equals("0")) {
                            // 消息发送到箱型亚欧
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        // 集疏费用
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            //委托zih送货
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

                    } else {
                        //回程
                        //集输公路收费
                        bookingInquiry.setDropStation(zzCity);//回程门到门站到门，默认是郑州下货站
                        if (bookingInquiry.getIsPickUp().equals("0")) {
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        //拼箱且允许zih送货
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            // 消息发送到箱型亚欧
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        //插入询价结果，更新提货地
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setPickUpAddress(shipmentPlace);//提货地
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setDeliveryAddress(receiptPlace);//收货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新下货站
                    }
                }
                break;
            case "2":
                //站到站，
                BookingInquiryResult bir;
                // 整柜区分（0整柜到堆场，1散货到堆场）
                if (goodsType.equals("0")) {
                    // +铁路费用（具体参考费用表）
                    BookingInquiryResult bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
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
                        // 客户自送货方式判断
                        if ("1".equals(bookingInquiry.getDeliverySelfType())) {
                            // 整柜 1整柜到堆场
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
                            if (eastOrWest.equals("0")) {
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
                            }
                        } else if(eastOrWest.equals("0")) {
                            if (bookingInquiry.getContainerType().endsWith("RF") ||
                                    bookingInquiry.getContainerType().endsWith("OT") ||
                                    bookingInquiry.getContainerType().endsWith("HT") ||
                                    bookingInquiry.getContainerType().endsWith("MT")  ) {
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
                                    bookingInquiryResult.setPickUpBoxFee("$ " + (500*bookingInquiry.getContainerNum()));
                                } else if (bookingInquiry.getContainerType().startsWith("20") ){
                                    bookingInquiryResult.setPickUpBoxFee("$ " + (400*bookingInquiry.getContainerNum()));
                                }
                            }
                        }
                    }
                    bookingInquiryResult.setInquiryId(inquiryId);//询价id
                    bookingInquiryResult.setRailwayCurrencyType("$");
                    bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                    bir = bookingInquiryResult;
                } else {
                    //拼箱
                    BookingInquiryResult bookingInquiryResult = commonHandlerService.getSHQCRailFee(bookingInquiry);
                    // +国内场站费
                    //国内场站费
//                        inquiryProvider.gnczPush(bookingInquiry);
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
                //站到门
                if (goodsType.equals("0")) {
                    //整柜
                    if (eastOrWest.equals("0")) {
                        // +铁路费用（具体参考费用表）,站到门需要等集疏反馈具体下货站才能计算
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            // 委托送货
                            // 集疏费用
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                        if ("0".equals(bookingInquiry.getContainerBelong()) ){
                            if( "1".equals(bookingInquiry.getDeliverySelfType())) {
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
                                        bookingInquiryResult.setPickUpBoxFee("$ " + (500*bookingInquiry.getContainerNum()));
                                    } else if (bookingInquiry.getContainerType().startsWith("20") ){
                                        bookingInquiryResult.setPickUpBoxFee("$ " + (400*bookingInquiry.getContainerNum()));
                                    }
                                }
                            } else {
                                if (bookingInquiry.getContainerType().endsWith("RF") ||
                                        bookingInquiry.getContainerType().endsWith("OT") ||
                                        bookingInquiry.getContainerType().endsWith("HT") ||
                                        bookingInquiry.getContainerType().endsWith("MT")  ) {
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
                                        bookingInquiryResult.setPickUpBoxFee("$ " + (500*bookingInquiry.getContainerNum()));
                                    } else if (bookingInquiry.getContainerType().startsWith("20") ){
                                        bookingInquiryResult.setPickUpBoxFee("$ " + (400*bookingInquiry.getContainerNum()));
                                    }
                                }
                            }
                        }
                        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation());//上货站
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);

                    } else {
                        bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//站到门下货站
                        //整柜到堆场
                        // 铁路费，回程有上货站，下货站固定是郑州，整柜铁路运费表中计算
                        BookingInquiryResult bookingInquiryResult = commonHandlerService.getZGRailFee(bookingInquiry);
                        if ("0".equals(bookingInquiry.getContainerBelong()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                            // 整柜 1整柜到堆场
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
                        }
                        if("0".equals(bookingInquiry.getContainerBelong())) { // 委托zih
                            if(!"1".equals(bookingInquiry.getDistributionType())) { // 整柜派送
                                // +送货费（整柜回程送货费用表）
                                BookingInquiryResult bookingInquiryResult1 = commonHandlerService.getHCZGDeliveryFees2(
                                        receiptPlace, receiveCity,bookingInquiry.getCityCode(), bookingInquiry.getContainerType(),
                                        bookingInquiry.getContainerNum(), bookingInquiry.getTruckType(),bookingInquiry.getBookingTimeFlag());
                                bookingInquiryResult.setDeliveryCurrencyType(bookingInquiryResult1.getDeliveryCurrencyType());//币种
                                bookingInquiryResult.setDeliveryAddress(bookingInquiryResult1.getDeliveryAddress());//派送地
                                bookingInquiryResult.setDeliveryFees(bookingInquiryResult1.getDeliveryFees());//派送费
                                bookingInquiryResult.setDeliveryDistance(bookingInquiryResult1.getDeliveryDistance());//距离
                                bookingInquiryResult.setDeliveryExpiration(bookingInquiryResult1.getDeliveryExpiration()); // 有效期
                                bookingInquiryResult.setEnquiryState(bookingInquiryResult1.getEnquiryState());
                                bookingInquiryResult.setHxAddress(bookingInquiryResult1.getHxAddress());
                                //还箱费
                                if (StringUtils.isNotEmpty(bookingInquiryResult1.getHxAddress())) {
                                    if (LanguageEnum.英文.value.equals(language)) {
                                        bookingInquiryResult.setHxAddress(
                                                commonHandlerService.getAddressEnName(bookingInquiryResult1.getHxAddress()));
                                    }
                                    bookingInquiry.setHxAddress(bookingInquiryResult.getHxAddress());
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
                            } else { // 回程整柜散货派送,发送公路询价
                                inquiryProvider.xxyoPush(bookingInquiry);
                                bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//派送地
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
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setRailwayCurrencyType("$");
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        if ("1".equals(bookingInquiry.getClientType()) ||
                                StringUtils.isEmpty(bookingInquiryResult.getRailwayFees())||
                                "0".equals(bookingInquiryResult.getRailwayFees())||
                                StringUtils.isEmpty(bookingInquiryResult.getHxAddress())||
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
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新下货站
                    }
                } else {
                    //散货
                    if (eastOrWest.equals("0")) {
                        //去程
                        // 集疏费用
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
                        // 散货铁路费
                        bookingInquiry.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);//站到门下货站
                        BookingInquiryResult bookingInquiryResult = commonHandlerService.getSHQCRailFee(bookingInquiry);
                        if (bookingInquiry.getIsDelivery().equals("1")) {
                            // 消息发送到箱型亚欧
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        bookingInquiryResult.setDelFlag("0");
                        bookingInquiryResult.setInquiryId(inquiryId);//询价id
                        bookingInquiryResult.setDropStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity);
                        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
                        bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                        // 计费体积
                        bookingInquiry.setBillableVolume(bookingInquiryResult.getRailRemark());
                        bookingInquiryService.updateBIWithDate(bookingInquiry);//更新下货站
                    }
                }
                break;
        }

        return 1;
    }

}
