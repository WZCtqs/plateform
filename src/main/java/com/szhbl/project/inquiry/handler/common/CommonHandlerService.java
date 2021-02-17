package com.szhbl.project.inquiry.handler.common;


import com.szhbl.common.enums.GoodsOperableEnum;
import com.szhbl.common.enums.LanguageEnum;
import com.szhbl.common.enums.ReturnAddressEnum;
import com.szhbl.common.utils.ConvertUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.BaiDuMapUtils;
import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.basic.mapper.BusiBoxfeeMapper;
import com.szhbl.project.enquiry.domain.*;
import com.szhbl.project.enquiry.mapper.*;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.domain.City;
import com.szhbl.project.inquiry.mapper.PcaMapper;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.mapper.BusiClassesMapper;
import com.szhbl.project.trains.service.IBusiSiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公共计算服务
 */
@Slf4j
@Component
public class CommonHandlerService {

    @Autowired
    private ZgRailDivisionMapper zgRailDivisionMapper;
    @Autowired
    private ShRailDivisionMapper shRailDivisionMapper;
    @Autowired
    private ZgTripFeeMapper zgTripFeeMapper;
    @Autowired
    private ZgRussiaGoingFeeMapper zgRussiaGoingFeeMapper;
    @Autowired
    private ZgReturnTripFeeMapper zgReturnTripFeeMapper;
    @Autowired
    private ZgReturnNonStandardMapper zgReturnNonStandardMapper;
    @Autowired
    private BusiBoxfeeMapper busiBoxfeeMapper;
    @Autowired
    private BusiClassesMapper busiClassesMapper;

    @Autowired
    private IBusiSiteService iBusiSiteService;
    @Autowired
    private PcaMapper pcaMapper;

    /**
     * 郑欧线门到站整柜去程费用比较取最小值
     */
    public ZgTripFee getMinPickUpFee(List<ZgTripFee> zgTripFeeList) {
        String cost = "0";
        //循环遍历list集合取出最小值的对象
        if (zgTripFeeList.size() == 0) {
            return null;
        }
        ZgTripFee zgTripFee = zgTripFeeList.stream().min(Comparator.comparing(ZgTripFee::getBzhPickUpCharge)).get();
        log.debug("最小值对象:" + zgTripFee);
        return zgTripFee;
    }

    /**
     * 整柜铁路费用比较取最小值
     */
    public ZgRailDivision getMinRailFee(List<ZgRailDivision> zgRailDivisionList) {
        String cost = "0";
        //循环遍历list集合取出最小值的对象
        if (zgRailDivisionList.size() == 0) {
            return null;
        }
        ZgRailDivision zgRailDivision = zgRailDivisionList.stream().min(Comparator.comparing(ZgRailDivision::getRailCost)).get();
        log.debug("最小值对象:" + zgRailDivision);
        return zgRailDivision;
    }

    /**
     * 计算距离
     */
    public Float getDistances(String origin, String destination){
        String dis = null;
        try {
            dis = BaiDuMapUtils.getDistance(origin, destination);
        }catch (Exception e){
            return 0f;
        }
        if(dis.contains("公里")){
            dis = dis.replace("公里","");
            return Float.valueOf(dis);
        }
        return Float.valueOf(dis.replace("米",""))/1000;
    }
    /**
     * 获取对应等价的箱型  箱型,20HC等价于20GP,40HC等价于40GP,20OT等价于20HOT,40OT等价于40HOT
     */
    public String getSameContainerType(String containerType){
        String sameContainerType = "";
        if("20HC".equals(containerType)){
            sameContainerType="20GP";
        }else if("20GP".equals(containerType)){
            sameContainerType="20HC";
        }else if("40HC".equals(containerType)){
            sameContainerType="40GP";
        }else if("40GP".equals(containerType)){
            sameContainerType="40HC";
        }else if("20OT".equals(containerType)){
            sameContainerType="20HOT";
        }else if("20HOT".equals(containerType)){
            sameContainerType="20OT";
        }else if("40OT".equals(containerType)){
            sameContainerType="40HOT";
        }else if("40HOT".equals(containerType)){
            sameContainerType="40OT";
        }
        return sameContainerType;
    }

    /**
     * 获取提还箱费用(对于去程到站的还箱费（和集疏没有关系）从提还箱费用表里面获取)
     */
    public String getRetrunBoxFees(BookingInquiry bookingInquiry) {
        String hxFee = "0";
        Map<String,String> map = new HashMap<>();
        map.put("boxType","0");
        map.put("addressType","1");
        map.put("hxAddressId",bookingInquiry.getHxAddressId());
        List<BusiBoxfee> busiBoxfeeList = busiBoxfeeMapper.selectBusiBoxfeeWithMap(map);
        if (busiBoxfeeList.size() > 0) {
            hxFee = busiBoxfeeList.get(0).getCost();
        }
        return hxFee;
    }
    /**
     * 获取提还箱费用
     */
    public String getBoxFees(String address, String containerType, Integer containerNum, String addressType) {
        String boxFee = null;
        Map<String,String> map = new HashMap<>();
        map.put("containerType",containerType);
        map.put("addressType",addressType);
        map.put("address",address);
        List<BusiBoxfee> boxFeeList = busiBoxfeeMapper.selectBoxFeeWithMap(map);
        if (boxFeeList.size() > 0) {
            boxFee = boxFeeList.get(0).getCost();
            StringBuilder sb = new StringBuilder(boxFee.substring(0,1) + " ");
            int i = ConvertUtils.reInt(boxFee)*containerNum;
            boxFee = sb.append(i).toString();
        }
        return boxFee;
    }
    /**
     * 获取提还箱费用和有效截止时间
     */
    public BusiBoxfee getBoxFeesAndEndTime(String address, String containerType, Integer containerNum, String addressType) {
        BusiBoxfee result = null;
        Map<String,String> map = new HashMap<>();
        map.put("containerType",containerType);
        map.put("addressType",addressType);
        map.put("address",address);
        List<BusiBoxfee> boxFeeList = busiBoxfeeMapper.selectBoxFeeWithMap(map);
        if (boxFeeList.size() > 0) {
            result = boxFeeList.get(0);
            String boxFee = result.getCost();
            StringBuilder sb = new StringBuilder(boxFee.substring(0,1) + " ");
            int i = ConvertUtils.reInt(boxFee) * containerNum;
            boxFee = sb.append(i).toString();
            // 赋值
            result.setCost(boxFee);
        }
        return result;
    }
    /**
     * 获取提还箱费用和有效截止时间（含当月次月）
     */
    public BusiBoxfee getBoxFeesAndEndTime2(String address, String containerType,
                                            Integer containerNum, String addressType,
                                            String bookingTimeFlag) {
        BusiBoxfee result = null;
        Map<String,String> map = new HashMap<>();
        map.put("containerType",containerType);
        map.put("addressType",addressType);
        map.put("address",address);
        map.put("bookingTimeFlag",bookingTimeFlag);
        List<BusiBoxfee> boxFeeList = busiBoxfeeMapper.selectBoxFeeWithMap2(map);
        if (boxFeeList.size() > 0) {
            result = boxFeeList.get(0);
            String boxFee = result.getCost();
            StringBuilder sb = new StringBuilder(boxFee.substring(0,1) + " ");
            int i = ConvertUtils.reInt(boxFee) * containerNum;
            boxFee = sb.append(i).toString();
            // 赋值
            result.setCost(boxFee);
        }
        return result;
    }

    /**
     * 计算散货铁路运费最小单价费用
     *
     * @param shRailDivisionList
     * @return
     */
    public ShRailDivision getMinShRailFee(List<ShRailDivision> shRailDivisionList) {

        //循环遍历list集合取出最小值的对象
        if (shRailDivisionList.size() == 0) {
            return null;
        }
        ShRailDivision shRailDivision = shRailDivisionList.stream().min(Comparator.comparing(ShRailDivision::getLclFreight)).get();
        System.out.println("最小值对象:" + shRailDivision);
        return shRailDivision;
    }

    /**
     * 计算回程整柜送货费最小单价费用
     *
     * @param zgReturnTripFeeList
     * @return
     */
    public ZgReturnTripFee getMinZgReturnTripFee(List<ZgReturnTripFee> zgReturnTripFeeList) {

        //循环遍历list集合取出最小值的对象
        if (zgReturnTripFeeList.size() == 0) {
            return null;
        }
        ZgReturnTripFee zgReturnTripFee = zgReturnTripFeeList.stream().min(Comparator.comparing(ZgReturnTripFee::getDeliveryFee)).get();
        System.out.println("最小值对象:" + zgReturnTripFee);
        return zgReturnTripFee;
    }

    /**
     * 郑欧散货去程、回程铁路运费计算
     */
    public BookingInquiryResult getSHQCRailFee(BookingInquiry bookingInquiry) {
        String uploadStation = bookingInquiry.getUploadStation();//上货站
        String dropStation0 = bookingInquiry.getDropStation();//下货站
        Map<String, String> map1 = new HashMap<>();
        if (("en").equals(bookingInquiry.getLanguage())) {
            uploadStation = iBusiSiteService.getBusiSiteByName(uploadStation, "en");
            dropStation0 = iBusiSiteService.getBusiSiteByName(dropStation0, "en");
        }
        map1.put("orderUploadSite", uploadStation);
        map1.put("orderUnloadSite", dropStation0);
        map1.put("eastOrWest", bookingInquiry.getEastOrWest());//去回程
        map1.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        ShRailDivision shRailDivision = getMinShRailFee(shRailDivisionMapper.selectShRailDivisionWithMap(map1));
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setInquiryId(bookingInquiry.getId());//询价id
        //散货铁路运费计算规则，去程运费+目的站收费（目的站基础费用+根据体积判断每立方收费标准）*总体积
        // 最终选用体积
        BigDecimal billableVolume = getBillableVolume(bookingInquiry);
        if (billableVolume.compareTo(new BigDecimal("0.5")) == -1) {
            billableVolume = new BigDecimal("0.5");
        }
        if (StringUtils.isNotNull(shRailDivision)) {
            // 超出最大体积增加固定收费票数
            BigDecimal fixedNum = new BigDecimal("1");
            // 下半年不限制最大方数
//            if(bookingInquiry.getEastOrWest().equals("0")){ // 去程最大60方
//                BigDecimal sixty = new BigDecimal("60");
//                if(billableVolume.compareTo(sixty) > 0) {
//                    fixedNum = billableVolume.divide(sixty,0, RoundingMode.CEILING);
//                }
//            } else { // 回程最大40方
//                BigDecimal fourty = new BigDecimal("40");
//                if(billableVolume.compareTo(fourty) > 0) {
//                    fixedNum = billableVolume.divide(fourty,0, RoundingMode.CEILING);
//                }
//            }
            //不为空计算
            String lcFreight = shRailDivision.getLclFreight();//去程运费
            Long orderUnloadSiteBacost = shRailDivision.getOrderUnloadSiteBacost();//目的站收费
            Long orderUploadSiteCost = shRailDivision.getOrderUploadSiteCost();// 起运站收费
            BigDecimal bgLcF = new BigDecimal(lcFreight);
            // 俄线下货站
            String[] e = {"莫斯科","布列斯特","明斯克","圣彼得堡"};
            //欧线上货站
            String o = "布列斯特";
            String dropStation = shRailDivision.getOrderUnloadSite();
            if (Arrays.asList(e).contains(dropStation)||o.equals(shRailDivision.getOrderUploadSite())){
                // 直客运费正常费用，货代和贸易公司优惠30
                if(!bookingInquiry.getClientType().equals("1")){
                    bgLcF = new BigDecimal(lcFreight).subtract(new BigDecimal("30"));
                }
                // 俄线没有最大限制
                fixedNum = new BigDecimal("1");
            }
            BigDecimal bgOrdunload = new BigDecimal("0");
            if (!ObjectUtils.isEmpty(orderUnloadSiteBacost)) {
                bgOrdunload = new BigDecimal(orderUnloadSiteBacost);
            }
            BigDecimal totalSHFee = new BigDecimal("0");
            totalSHFee = totalSHFee.add(bgLcF.add(bgOrdunload));
            // 直客收起运站费用
            if(bookingInquiry.getClientType().equals("1")){
                BigDecimal ordupload = new BigDecimal("0");
                if (!ObjectUtils.isEmpty(orderUploadSiteCost)) {
                    ordupload = new BigDecimal(orderUploadSiteCost);
                }
                totalSHFee = totalSHFee.add(ordupload);
            }
            // 下货站
            BigDecimal totalRailFees = billableVolume.multiply(totalSHFee).setScale(2,BigDecimal.ROUND_HALF_UP);//总铁路运费
            if(StringUtils.isNotEmpty(shRailDivision.getMinVolume()) && StringUtils.isNotEmpty(shRailDivision.getMaxVolume())){
                if (billableVolume.compareTo(new BigDecimal(shRailDivision.getMinVolume())) < 1) {
                    //小于等于
                    BigDecimal bgMinvolumCost = new BigDecimal(shRailDivision.getMinVolumeCost());
                    totalRailFees = totalRailFees.add(bgMinvolumCost);
                } else if (billableVolume.compareTo(new BigDecimal(shRailDivision.getMinVolume())) == 1 && billableVolume.compareTo(new BigDecimal(shRailDivision.getMaxVolume())) == -1) {
                    //大于最小值且小于最大值
                    BigDecimal bgMiddleVolumCost = new BigDecimal(shRailDivision.getMiddleVolumeCost());
                    totalRailFees = totalRailFees.add(bgMiddleVolumCost);
                    //修改字段，起运站费，去程费用、回程费用字段
                } else {
                    BigDecimal bgMaxVolumCost = new BigDecimal(shRailDivision.getMaxVolumeCost()).multiply(fixedNum);
                    totalRailFees = totalRailFees.add(bgMaxVolumCost);
                }
            } else {
                BigDecimal bgMaxVolumCost = new BigDecimal(shRailDivision.getMaxVolumeCost()).multiply(fixedNum);
                totalRailFees = totalRailFees.add(bgMaxVolumCost);
            }
            bookingInquiryResult.setRailwayFees(totalRailFees.toString());//铁路费用
            bookingInquiryResult.setRailwayFeedbackTime(new Date());//铁路运费反馈时间
            bookingInquiryResult.setRailwayExpiration(shRailDivision.getValidEndDate()); // 有效期

        }else{
            bookingInquiryResult.setRailwayFees("0"); //铁路费用
            bookingInquiryResult.setRailwayExpiration(
                    getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                            bookingInquiry.getBookingTimeFlag())
            );
        }
        bookingInquiryResult.setRailwayCurrencyType("$");//欧线、俄线和中越是美元，中亚是人民币
        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation()); //上货站
        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());//下货站
        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地（发货地）
        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//派送地（收货地）
        bookingInquiryResult.setRailwayAging(
                getRailwayAging(
                        bookingInquiry.getLineType(),
                        uploadStation,
                        dropStation0)
        );
        // 计费体积暂存
        bookingInquiryResult.setRailRemark(String.valueOf(billableVolume));
        return bookingInquiryResult;
    }

    /**
     * 郑欧整柜铁路运费（查询条件：去回程和线路）
     */
    public BookingInquiryResult getZGRailFee(BookingInquiry bookingInquiry) {
        String lineType = bookingInquiry.getLineType();//线路
        String uploadStation = bookingInquiry.getUploadStation();//上货站
        String dropStation = bookingInquiry.getDropStation();//下货站
        //箱属字段反了
        String containerBelong = bookingInquiry.getContainerBelong().equals("0") ? "1" : "0";
        String containerType = bookingInquiry.getContainerType();//箱型
        String eastOrWest = bookingInquiry.getEastOrWest();//去回程
        String sameContainerType = getSameContainerType(containerType);//箱型,20HC和20GP,40HC和40GP
        Long inquiryId = bookingInquiry.getId();
        Integer containerNum = bookingInquiry.getContainerNum();
        Map<String, String> map1 = new HashMap<>();
        if ("en".equals(bookingInquiry.getLanguage())) {
            if (!("郑州".equals(uploadStation) || "工厂".equals(uploadStation))){
                uploadStation = iBusiSiteService.getBusiSiteByName(uploadStation, "en");
            }
            if (!"郑州".equals(dropStation)) {
                dropStation = iBusiSiteService.getBusiSiteByName(dropStation, "en");
            }
        }
        map1.put("orderUploadSite", uploadStation);
        map1.put("orderUnloadSite", dropStation);
        map1.put("containerBelong", containerBelong);
        map1.put("containerType", containerType);
        map1.put("sameContainerType", sameContainerType);
        map1.put("lineType", lineType);
        map1.put("eastOrWest", eastOrWest);
        map1.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        ZgRailDivision zgRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map1));
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        if (StringUtils.isNotNull(zgRailDivision)) {
            BigDecimal tlf = new BigDecimal(zgRailDivision.getRailCost()).setScale(2, BigDecimal.ROUND_HALF_UP);      //铁路费
            //更新到询价结果反馈表中
            bookingInquiryResult.setInquiryId(inquiryId);//询价id
            if (StringUtils.isNotNull(containerNum)) {
                BigDecimal totalRailFee = tlf.multiply(new BigDecimal(containerNum));
                bookingInquiryResult.setRailwayFees(totalRailFee.toString());//铁路费用
            } else {
                bookingInquiryResult.setRailwayFees(tlf.toString());//铁路费用
            }
            // 有效期
            bookingInquiryResult.setRailwayExpiration(zgRailDivision.getValidEndDate());
        } else {
            bookingInquiryResult.setRailwayFees("0"); //铁路费用
            bookingInquiryResult.setRailwayExpiration(
                    getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                            bookingInquiry.getBookingTimeFlag())
            );
            // bookingInquiryResult.setRailRemark("相应站点铁路费用暂未维护，铁路费用无法计算");
        }
        bookingInquiryResult.setRailwayFeedbackTime(new Date());//铁路运费反馈时间
        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation()); //上货站
        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());//下货站
        bookingInquiryResult.setRailwayCurrencyType("$");//欧线、俄线和中越是美元，中亚是人民币
        if (bookingInquiry.getLineType().equals("4") && bookingInquiry.getEastOrWest().equals("0")) {
            uploadStation = "郑州";
        }
        bookingInquiryResult.setRailwayAging(
                getRailwayAging(
                        lineType,
                        uploadStation,
                        dropStation)
        );
        return bookingInquiryResult;
    }

    /**
     * 门到门，站到门回程整柜送货费计算
     */
    public BookingInquiryResult getHCZGDeliveryFees(String receiptPlace,String receiveCity,String containerType,Integer containerNum,String trunkType) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setDeliveryCurrencyType("$");//币种
        bookingInquiryResult.setDeliveryAddress(receiptPlace);//派送地
        if (receiveCity.contains("市")) {
            receiveCity = receiveCity.substring(0,receiveCity.lastIndexOf("市"));
        }
        Map<String, String> map = new HashMap<>();
        map.put("receiptPlace", receiveCity);
        map.put("flag","1");
        /*map.put("receiptPlace",truckType);//*/
        ZgReturnTripFee zgReturnTripFee = getMinZgReturnTripFee(zgReturnTripFeeMapper.selectZgReturnTripFeeWithMap(map));
        if(zgReturnTripFee == null || (containerType.endsWith("RF") || containerType.endsWith("OT"))){
            //如果表里未维护该还箱地还箱费数据，则查郑州陆港园区到送货地所在火车站的距离
            Float zRDistance = getDistances("郑州国际陆港园区",receiveCity+"火车站");
            // 非标准还箱地查询
            List<ZgReturnNonStandard> zgReturnNonStandards = zgReturnNonStandardMapper.selectListByCity(receiveCity);
            if (!ObjectUtils.isEmpty(zgReturnNonStandards) && !(containerType.endsWith("RF") || containerType.endsWith("OT"))) {
                // 还箱地
                bookingInquiryResult.setHxAddress(zgReturnNonStandards.get(0).getReturnBoxAddress());
                String address = ReturnAddressEnum.getAddress(zgReturnNonStandards.get(0).getReturnBoxAddress());
                if (StringUtils.isNotEmpty(address)) {
                    Float returnDistance = getDistances(receiveCity+"火车站",address);
                    zRDistance = zRDistance + returnDistance;
                }
            }
            if(zRDistance != null){
                String singleFee = "1.3";
                if(containerType.endsWith("RF") || containerType.endsWith("OT")){
                    singleFee = "1.1";
                }
                if ("2".equals(trunkType)) {// 白卡
                    singleFee = "1.44";
                    if(containerType.endsWith("RF") || containerType.endsWith("OT")){
                        singleFee = "1.24";
                    }
                }
                BigDecimal bgDeliveryFee = new BigDecimal(zRDistance).multiply(new BigDecimal(singleFee)).setScale(1,BigDecimal.ROUND_HALF_UP);
                if(bgDeliveryFee.compareTo(new BigDecimal("100")) == 1){
                    //郑欧线卡车提送货费规则，如果提货费大于100usd/车
                    bookingInquiryResult.setDeliveryFees(bgDeliveryFee.multiply(new BigDecimal(containerNum)).toString());//派送费

                }else {
                    //小于100，默认100usd/车
                    bookingInquiryResult.setDeliveryFees(String.valueOf(100*containerNum));//派送费
                }
                bookingInquiryResult.setDeliveryDistance(zRDistance.toString());//距离
                bookingInquiryResult.setDeliveryExpiration(getThirtyDaysLaterDate());
            }
            bookingInquiryResult.setEnquiryState("2");

            return bookingInquiryResult;
        }
        long deliveryFee = zgReturnTripFee.getDeliveryFee();
        if ("2".equals(trunkType)) {// 白卡
            deliveryFee = zgReturnTripFee.getWhiteCardDeliveryFee();
        }
        bookingInquiryResult.setDeliveryFees(String.valueOf(deliveryFee*containerNum));//派送费
        bookingInquiryResult.setDeliveryDistance(String.valueOf(zgReturnTripFee.getDistance()));//距离
        if (StringUtils.isNull(zgReturnTripFee.getValidEndDate())) {
            bookingInquiryResult.setDeliveryExpiration(getThirtyDaysLaterDate());
        } else {
            bookingInquiryResult.setDeliveryExpiration(zgReturnTripFee.getValidEndDate());
        }
        // 还箱地
        bookingInquiryResult.setHxAddress(zgReturnTripFee.getCityTrainStation());
        // 判断整柜派送费是否维护，不是就转待审核
        bookingInquiryResult.setEnquiryState("3");
        return bookingInquiryResult;
    }
    /**
     * 门到门，站到门回程整柜送货费计算(包含当月次月询价)
     */
    public BookingInquiryResult getHCZGDeliveryFees2(String receiptPlace, String receiveCity,String cityCode,
                                                     String containerType,Integer containerNum,
                                                     String trunkType,String bookingTimeFlag) {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setDeliveryCurrencyType("$");//币种
        bookingInquiryResult.setDeliveryAddress(receiptPlace);//派送地
        // 如果存在cityCode
        if (StringUtils.isNotEmpty(cityCode)) {
            receiveCity = getCityName(cityCode);
        }
        if (receiveCity.contains("市")) {
            receiveCity = receiveCity.substring(0,receiveCity.lastIndexOf("市"));
        }
        Map<String, String> map = new HashMap<>();
        map.put("receiptPlace", receiveCity);
        map.put("flag","1");
        map.put("bookingTimeFlag",bookingTimeFlag);
        /*map.put("receiptPlace",truckType);//*/
        ZgReturnTripFee zgReturnTripFee = getMinZgReturnTripFee(zgReturnTripFeeMapper.selectZgReturnTripFeeWithMap(map));
        if(zgReturnTripFee == null || (containerType.endsWith("RF") || containerType.endsWith("OT"))){
            //如果表里未维护该还箱地还箱费数据，则查郑州陆港园区到送货地所在火车站的距离
            Float zRDistance = getDistances("郑州国际陆港园区",receiveCity+"火车站");
            // 非标准还箱地查询
            List<ZgReturnNonStandard> zgReturnNonStandards = zgReturnNonStandardMapper.selectListByCity(receiveCity);
            if (!ObjectUtils.isEmpty(zgReturnNonStandards) && !(containerType.endsWith("RF") || containerType.endsWith("OT"))) {
                // 还箱地
                bookingInquiryResult.setHxAddress(zgReturnNonStandards.get(0).getReturnBoxAddress());
                String address = ReturnAddressEnum.getAddress(zgReturnNonStandards.get(0).getReturnBoxAddress());
                if (StringUtils.isNotEmpty(address)) {
                    Float returnDistance = getDistances(receiveCity+"火车站",address);
                    zRDistance = zRDistance + returnDistance;
                }
            }
            if(zRDistance != null){
                String singleFee = "1.3";
                if(containerType.endsWith("RF") || containerType.endsWith("OT")){
                    singleFee = "1.1";
                }
                if ("2".equals(trunkType)) {// 白卡
                    singleFee = "1.44";
                    if(containerType.endsWith("RF") || containerType.endsWith("OT")){
                        singleFee = "1.24";
                    }
                }
                BigDecimal bgDeliveryFee = new BigDecimal(zRDistance).multiply(new BigDecimal(singleFee)).setScale(1,BigDecimal.ROUND_HALF_UP);
                if(bgDeliveryFee.compareTo(new BigDecimal("100")) == 1){
                    //郑欧线卡车提送货费规则，如果提货费大于100usd/车
                    bookingInquiryResult.setDeliveryFees(bgDeliveryFee.multiply(new BigDecimal(containerNum)).toString());//派送费

                }else {
                    //小于100，默认100usd/车
                    bookingInquiryResult.setDeliveryFees(String.valueOf(100*containerNum));//派送费
                }
                bookingInquiryResult.setDeliveryDistance(zRDistance.toString());//距离
                bookingInquiryResult.setDeliveryExpiration(getThirtyDaysLaterDate());
            }
            bookingInquiryResult.setEnquiryState("2");

            return bookingInquiryResult;
        }
        long deliveryFee = zgReturnTripFee.getDeliveryFee();
        if ("2".equals(trunkType)) {// 白卡
            deliveryFee = zgReturnTripFee.getWhiteCardDeliveryFee();
        }
        bookingInquiryResult.setDeliveryFees(String.valueOf(deliveryFee*containerNum));//派送费
        bookingInquiryResult.setDeliveryDistance(String.valueOf(zgReturnTripFee.getDistance()));//距离
        if (StringUtils.isNull(zgReturnTripFee.getValidEndDate())) {
            bookingInquiryResult.setDeliveryExpiration(getThirtyDaysLaterDate());
        } else {
            bookingInquiryResult.setDeliveryExpiration(zgReturnTripFee.getValidEndDate());
        }
        // 还箱地
        bookingInquiryResult.setHxAddress(zgReturnTripFee.getCityTrainStation());
        // 判断整柜派送费是否维护，不是就转待审核
        bookingInquiryResult.setEnquiryState("3");
        return bookingInquiryResult;
    }

    /**
     * 计算郑欧线整柜去程门到站提货费+铁路运费
     */
    public BookingInquiryResult getZOZGQCPickUpFeesAndRailFee(BookingInquiry bookingInquiry) {
        Map<String, String> map = new HashMap<>();
        String dropStation = bookingInquiry.getDropStation();
        if (("en").equals(bookingInquiry.getLanguage())) {
            dropStation = iBusiSiteService.getBusiSiteByName(dropStation, "en");
        }
        //箱属字段反了
        String containerBelong = bookingInquiry.getContainerBelong().equals("0") ? "1" : "0";

        String containerType = bookingInquiry.getContainerType();
        String sameContainerType = getSameContainerType(containerType);//箱型,20HC和20GP,40HC和40GP

        String lineType = bookingInquiry.getLineType();
        String eastOrWest = bookingInquiry.getEastOrWest();
        String language = bookingInquiry.getLanguage();
        String zzCity = "郑州";
        String zzCityEn = "Zhengzhou";
        String whCity = "武汉";
        // 新增上海集货点
        String shCity = "上海";
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        ZgRailDivision zgRailDivision = null;
        Map<String, String> map1 = new HashMap<>();
        map1.put("lineType", lineType);
        map1.put("eastOrWest", eastOrWest);
        map1.put("orderUnloadSite", dropStation);
        map1.put("containerBelong", containerBelong);
        map1.put("containerType", containerType);
        map1.put("sameContainerType", sameContainerType);
        map1.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        String senderCity = bookingInquiry.getSenderCity();
        // 如果是英文且存在cityCode
        if (LanguageEnum.英文.value.equals(language) && StringUtils.isNotEmpty(bookingInquiry.getCityCode())) {
            senderCity = getCityName(bookingInquiry.getCityCode());
        }

        if (senderCity.contains("市")) {
            senderCity = senderCity.substring(0,senderCity.lastIndexOf("市"));
        }
        /*String ct = bookingInquiry.getContainerType();
        if (ct.endsWith("HC")) {
            ct = ct.replace("HC","GP");
        }*/
        map.put("shipmentPlace", senderCity.trim());
        map.put("containerBelong", containerBelong);
        map.put("sameContainerType", sameContainerType);
        map.put("containerType", containerType);//箱型判断，郑欧线主要是20、40尺箱,整柜去程表
        map.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        List<ZgTripFee> zgTripFeeList =  zgTripFeeMapper.selectZgTripFeeWithMap(map);
        ZgTripFee minZgTripFee = getMinPickUpFee(zgTripFeeList);
        if (minZgTripFee != null) {
            if (zgTripFeeList.size() == 1) {
                String cargoCollectionPoint = zgTripFeeList.get(0).getCargoCollectionPoint(); //获得上货站 (集货点),铁路费，先得到集货点去铁路费用表查
                bookingInquiryResult.setPickUpFees(String.valueOf(zgTripFeeList.get(0).getBzhPickUpCharge() * bookingInquiry.getContainerNum()));//提货费
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity); //上货站
                bookingInquiryResult.setPickUpDistance(String.valueOf(zgTripFeeList.get(0).getDistance()));//提货距离
                if (StringUtils.isNull(zgTripFeeList.get(0).getValidEndDate())){
                    bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());
                } else {
                    bookingInquiryResult.setPickUpExpiration(zgTripFeeList.get(0).getValidEndDate());
                }
                map1.put("orderUploadSite",cargoCollectionPoint);
                zgRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map1));
                if (null == zgRailDivision) {
                    bookingInquiryResult.setRailwayFees("0"); //铁路费用
                    bookingInquiryResult.setRailwayExpiration(
                            getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                                    bookingInquiry.getBookingTimeFlag())
                    );
                    // bookingInquiryResult.setRailRemark("相应站点铁路费用暂未维护，铁路费用无法计算");
                } else {
                    bookingInquiryResult.setRailwayFees(new BigDecimal(String.valueOf(zgRailDivision.getRailCost())).multiply(new BigDecimal(bookingInquiry.getContainerNum())).toString());//铁路费用
                    bookingInquiryResult.setRailwayExpiration(zgRailDivision.getValidEndDate());
                }
            } else {
                ZgTripFee zgZZTripFee = null;// 郑州提货费用
                ZgTripFee zgWHTripFee = null;// 武汉提货费用
                ZgTripFee zgSHTripFee = null;// 上海提货费用
                for (ZgTripFee z: zgTripFeeList) {
                    if(z.getCargoCollectionPoint().equals(zzCity)) {
                        zgZZTripFee = z;
                    } else if (z.getCargoCollectionPoint().equals(whCity)){
                        zgWHTripFee = z;
                    } else if (z.getCargoCollectionPoint().equals(shCity)){
                        zgSHTripFee = z;
                    }
                }
                map1.put("orderUploadSite",zzCity);
                ZgRailDivision zgZZRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map1));
                map1.put("orderUploadSite",whCity);
                ZgRailDivision zgWHRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map1));
                map1.put("orderUploadSite",shCity);
                ZgRailDivision zgSHRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map1));
                // 比较价格最小值
                List<PickAndRailFees> list = new ArrayList<>();
                list.add(PickAndRailFees.builder().zgTripFee(zgZZTripFee).zgRailDivision(zgZZRailDivision).build());
                list.add(PickAndRailFees.builder().zgTripFee(zgWHTripFee).zgRailDivision(zgWHRailDivision).build());
                list.add(PickAndRailFees.builder().zgTripFee(zgSHTripFee).zgRailDivision(zgSHRailDivision).build());
                PickAndRailFees pickAndRailFees = CompareFeesUtil.getMinPickFee(list);
                // 询价结果赋值
                bookingInquiryResult.setPickUpFees(String.valueOf(pickAndRailFees.getZgTripFee().getBzhPickUpCharge() * bookingInquiry.getContainerNum()));//提货费
                if (StringUtils.isNull(pickAndRailFees.getZgTripFee().getValidEndDate())){
                    bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());
                } else {
                    bookingInquiryResult.setPickUpExpiration(pickAndRailFees.getZgTripFee().getValidEndDate());
                }
                bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity); //上货站
                bookingInquiryResult.setPickUpDistance(String.valueOf(pickAndRailFees.getZgTripFee().getDistance()));//提货距离
                bookingInquiryResult.setRailwayFees(new BigDecimal(pickAndRailFees.getZgRailDivision().getRailCost()).multiply(new BigDecimal(bookingInquiry.getContainerNum())).toString());//铁路费用
                bookingInquiryResult.setRailwayExpiration(pickAndRailFees.getZgRailDivision().getValidEndDate());
            }
            // 判断整柜提货费是否维护，不是就转待审核
            bookingInquiryResult.setEnquiryState("3");
        }else {
            //如果不存在该地的提货费，还得计算提货城市到集货点的距离，拿武汉和郑州进行对比价格（公路运费+集货点的铁路运费）,提货地先判断不能为空
            if (containerType.endsWith("RF") || containerType.endsWith("OT") || containerType.endsWith("HT") || containerType.endsWith("MT")) {
                // 特种箱
                Float zRDistance = getDistances("郑州国际陆港园区",senderCity+"火车站");
                String singleFee = "1.1";
                if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
                    singleFee = "1.24";
                }
                BigDecimal bgDeliveryFee = new BigDecimal(zRDistance).multiply(new BigDecimal(singleFee)).setScale(1,BigDecimal.ROUND_HALF_UP);
                if(bgDeliveryFee.compareTo(new BigDecimal("100")) == 1){
                    //郑欧线卡车提送货费规则，如果提货费大于100usd/车
                    bookingInquiryResult.setPickUpFees(bgDeliveryFee.multiply(new BigDecimal(bookingInquiry.getContainerNum())).toString());//提货费
                }else {
                    //小于100，默认100usd/车
                    bookingInquiryResult.setPickUpFees(String.valueOf(100*bookingInquiry.getContainerNum()));//提货费
                }
                // 有效期
                bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());
                bookingInquiryResult.setPickUpDistance(zRDistance.toString());//距离
                Map<String, String> map2 = new HashMap<>();
                map2.put("orderUploadSite", zzCity);
                map2.put("orderUnloadSite", dropStation);
                map2.put("containerBelong", containerBelong);
                map2.put("containerType", containerType);
                map2.put("sameContainerType", sameContainerType);
                map2.put("lineType", lineType);
                map2.put("eastOrWest", eastOrWest);
                map2.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
                ZgRailDivision zgZZRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map2));
                if(null!=zgZZRailDivision) {
                    bookingInquiryResult.setRailwayFees(String.valueOf(new BigDecimal(zgZZRailDivision.getRailCost()).multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//铁路费用
                    bookingInquiryResult.setRailwayExpiration(zgZZRailDivision.getValidEndDate());
                } else {
                    bookingInquiryResult.setRailwayFees("0"); //铁路费用
                    bookingInquiryResult.setRailwayExpiration(
                            getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                                    bookingInquiry.getBookingTimeFlag())
                    );
                    // bookingInquiryResult.setRailRemark("相应站点铁路费用暂未维护，铁路费用无法计算");
                }

            } else {
                if(!containerType.startsWith("20")){
                    Float shipPlaceAndZZDistance = getDistances(senderCity+"火车站", "郑州国际陆港园区");
                    Float shipPlaceAndWHDistance = getDistances(senderCity+"火车站", "武汉");
                    if (shipPlaceAndZZDistance != null && shipPlaceAndWHDistance != null) {
                        BigDecimal bgZZ = new BigDecimal(shipPlaceAndZZDistance);
                        BigDecimal bgWH = new BigDecimal(shipPlaceAndWHDistance);
                        String singleFee = "1.5";
                        if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
                            singleFee = "1.64";
                        }
                        BigDecimal bgZZPickUPFee = bgZZ.multiply(new BigDecimal(singleFee)).setScale(1, BigDecimal.ROUND_HALF_UP);//到郑州的提货费
                        BigDecimal bgWHPickUPFee = bgWH.multiply(new BigDecimal(singleFee)).setScale(1, BigDecimal.ROUND_HALF_UP);//到武汉的提货费
                        //查询从武汉和从郑州到下货站的铁路运费
                        Map<String, String> map2 = new HashMap<>();
                        map2.put("orderUploadSite", zzCity);
                        map2.put("orderUnloadSite", dropStation);
                        map2.put("containerBelong", containerBelong);
                        map2.put("containerType", containerType);
                        map2.put("sameContainerType", sameContainerType);
                        map2.put("lineType", lineType);
                        map2.put("eastOrWest", eastOrWest);
                        map2.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
                        ZgRailDivision zgZZRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map2));
                        Map<String, String> map3 = new HashMap<>();
                        map3.put("orderUploadSite", whCity);
                        map3.put("orderUnloadSite", dropStation);
                        map3.put("containerBelong", containerBelong);
                        map3.put("containerType", containerType);
                        map3.put("sameContainerType", sameContainerType);
                        map3.put("lineType", lineType);
                        map3.put("eastOrWest", eastOrWest);
                        map3.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
                        ZgRailDivision zgWHRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map3));
                        boolean wh = true;
                        if (zgWHRailDivision == null) {
                            wh = false;
                        }
                        String zgZZRailCost = zgZZRailDivision == null ? "0" : zgZZRailDivision.getRailCost().toString();
                        String zgWHRailCost = zgWHRailDivision == null ? "0" : zgWHRailDivision.getRailCost().toString();

                        BigDecimal bgZZZGRailFee = new BigDecimal(zgZZRailCost).setScale(1, BigDecimal.ROUND_HALF_UP);
                        BigDecimal bgWHZGRailFee = new BigDecimal(zgWHRailCost).setScale(1, BigDecimal.ROUND_HALF_UP);
                        BigDecimal bgZZTotalFee = bgZZZGRailFee.add(bgZZPickUPFee);
                        BigDecimal bgWHTotallFee = bgWHZGRailFee.add(bgWHPickUPFee);
                        //比较返回最小值
                        if (bgZZTotalFee.compareTo(bgWHTotallFee) == 1 && wh) {
                            //郑州站大于武汉站,则选择武汉
                            bookingInquiryResult.setRailwayFees(String.valueOf(bgWHZGRailFee.multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//铁路费用
                            bookingInquiryResult.setRailwayExpiration(zgWHRailDivision.getValidEndDate());
                            if (bgWHPickUPFee.compareTo(new BigDecimal("100")) == 1) {
                                //郑欧线卡车提送货费规则，如果提货费大于100usd/车
                                bookingInquiryResult.setPickUpFees(String.valueOf(bgWHPickUPFee.multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//提货费
                            } else {
                                //小于100，默认100usd/车
                                bookingInquiryResult.setPickUpFees(String.valueOf(100 * bookingInquiry.getContainerNum()));//提货费
                            }

                            bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity); //上货站，不管虚拟集货点是什么，去程上货站默认是郑州，但铁路运费仍然按照虚拟集货点跟下货站距离计算
                            bookingInquiryResult.setPickUpDistance(shipPlaceAndWHDistance.toString());//提货距离
                            bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());

                        } else {
                            bookingInquiryResult.setRailwayFees(String.valueOf(bgZZZGRailFee.multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//铁路费用
                            if (null == zgZZRailDivision) {
                                bookingInquiryResult.setRailwayExpiration(
                                        getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                                                bookingInquiry.getBookingTimeFlag())
                                );
                            } else {
                                bookingInquiryResult.setRailwayExpiration(zgZZRailDivision.getValidEndDate());
                            }
                            if (bgZZPickUPFee.compareTo(new BigDecimal("100")) == 1) {
                                //郑欧线卡车提送货费规则，如果提货费大于100usd/车
                                bookingInquiryResult.setPickUpFees(String.valueOf(bgZZPickUPFee.multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//提货费
                            } else {
                                //小于100，默认100usd/车
                                bookingInquiryResult.setPickUpFees(String.valueOf(100 * bookingInquiry.getContainerNum()));//提货费
                            }

                            bookingInquiryResult.setUploadStation(LanguageEnum.英文.value.equals(language) ? zzCityEn : zzCity); //上货站
                            bookingInquiryResult.setPickUpDistance(shipPlaceAndZZDistance.toString());//提货距离
                            bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());
                        }
                    }
                }else{
                    Float zRDistance = getDistances("郑州国际陆港园区",senderCity+"火车站");
                    String singleFee = "1.5";
                    if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
                        singleFee = "1.64";
                    }
                    BigDecimal bgDeliveryFee = new BigDecimal(zRDistance).multiply(new BigDecimal(singleFee)).setScale(1,BigDecimal.ROUND_HALF_UP);
                    if(bgDeliveryFee.compareTo(new BigDecimal("100")) == 1){
                        //郑欧线卡车提送货费规则，如果提货费大于100usd/车
                        bookingInquiryResult.setPickUpFees(bgDeliveryFee.multiply(new BigDecimal(bookingInquiry.getContainerNum())).toString());//派送费
                    }else {
                        //小于100，默认100usd/车
                        bookingInquiryResult.setPickUpFees(String.valueOf(100*bookingInquiry.getContainerNum()));//派送费
                    }
                    bookingInquiryResult.setPickUpDistance(zRDistance.toString());//距离
                    bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());
                    Map<String, String> map2 = new HashMap<>();
                    map2.put("orderUploadSite", zzCity);
                    map2.put("orderUnloadSite", dropStation);
                    map2.put("containerBelong", containerBelong);
                    map2.put("containerType", containerType);
                    map2.put("sameContainerType", sameContainerType);
                    map2.put("lineType", lineType);
                    map2.put("eastOrWest", eastOrWest);
                    map2.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
                    ZgRailDivision zgZZRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map2));
                    if(null!=zgZZRailDivision) {
                        bookingInquiryResult.setRailwayFees(String.valueOf(new BigDecimal(zgZZRailDivision.getRailCost()).multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//铁路费用
                        bookingInquiryResult.setRailwayExpiration(zgZZRailDivision.getValidEndDate());
                    } else {
                        bookingInquiryResult.setRailwayFees("0"); //铁路费用
                        bookingInquiryResult.setRailwayExpiration(
                                getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                                        bookingInquiry.getBookingTimeFlag())
                        );
                        // bookingInquiryResult.setRailRemark("相应站点铁路费用暂未维护，铁路费用无法计算");
                    }
                }
            }
        }
        bookingInquiryResult.setRailwayCurrencyType("$");//币种
        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());//下货站
        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
        bookingInquiryResult.setPickUpCurrencyType("$");//币种
        String railwayAging = getRailwayAging(
                bookingInquiry.getLineType(),
                zzCity,
                dropStation
        );
        bookingInquiryResult.setRailwayAging(railwayAging);
        return bookingInquiryResult;
    }

    /**
     * 郑俄散货去程、回程铁路运费计算
     */
    public BookingInquiryResult getZeSHQCRailFee(BookingInquiry bookingInquiry) {
        String uploadStation = bookingInquiry.getUploadStation();//上货站
        String dropStation = bookingInquiry.getDropStation();//下货站
        Map<String, String> map1 = new HashMap<>();
        if (("en").equals(bookingInquiry.getLanguage())) {
            uploadStation = iBusiSiteService.getBusiSiteByName(uploadStation, "en");
            dropStation = iBusiSiteService.getBusiSiteByName(dropStation, "en");
        }
        map1.put("orderUploadSite", uploadStation);
        map1.put("orderUnloadSite", dropStation);
        map1.put("eastOrWest", bookingInquiry.getEastOrWest());//去回程
        map1.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        ShRailDivision shRailDivision = getMinShRailFee(shRailDivisionMapper.selectShRailDivisionWithMap(map1));
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setInquiryId(bookingInquiry.getId());//询价id
        //散货铁路运费计算规则，去程运费+目的站收费（目的站基础费用+根据体积判断每立方收费标准）*总体积
        // 最终选用体积
        BigDecimal billableVolume = getBillableVolume(bookingInquiry);
        if (billableVolume.compareTo(new BigDecimal("0.5")) == -1) {
            billableVolume = new BigDecimal("0.5");
        }
        if (StringUtils.isNotNull(shRailDivision)) {
            //不为空计算
            String lcFreight = shRailDivision.getLclFreight();//运费
            BigDecimal bgLcF = new BigDecimal(lcFreight);
            // 直客运费正常费用，货代和贸易公司优惠30
            if (!bookingInquiry.getClientType().equals("1")) {
                bgLcF = new BigDecimal(lcFreight).subtract(new BigDecimal("30"));
            }
            BigDecimal totalRailFees = billableVolume.multiply(bgLcF).setScale(2, BigDecimal.ROUND_HALF_UP);//总铁路运费
            BigDecimal bgMaxVolumCost = new BigDecimal(shRailDivision.getMaxVolumeCost());
            totalRailFees = totalRailFees.add(bgMaxVolumCost); // 每票固定收费
            bookingInquiryResult.setRailwayFees(totalRailFees.toString());//铁路费用
            bookingInquiryResult.setRailwayFeedbackTime(new Date());//铁路运费反馈时间
            bookingInquiryResult.setRailwayExpiration(shRailDivision.getValidEndDate());//有效期
        } else {
            bookingInquiryResult.setRailwayFees("0"); //铁路费用
            bookingInquiryResult.setRailwayExpiration(
                    getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                            bookingInquiry.getBookingTimeFlag())
            );
        }
        bookingInquiryResult.setRailwayCurrencyType("$");//欧线、俄线和中越是美元，中亚是人民币
        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation()); //上货站
        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());//下货站
        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地（发货地）
        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//派送地（收货地）
        if (bookingInquiry.getLineType().equals("4") && bookingInquiry.getEastOrWest().equals("0")) {
            uploadStation = "郑州";
        }
        bookingInquiryResult.setRailwayAging(
                getRailwayAging(
                        bookingInquiry.getLineType(),
                        uploadStation,
                        dropStation));
        // 暂存计费体积
        bookingInquiryResult.setRailRemark(String.valueOf(billableVolume));
        return bookingInquiryResult;
    }

    /**
     *计算郑俄门到站整柜提货费 + 铁路
     */
    public BookingInquiryResult getZePickUpAndRailwayFees(BookingInquiry bookingInquiry){
        String zzCityAddress = "郑州国际陆港园区";
        String language = bookingInquiry.getLanguage();
        String senderProvince = bookingInquiry.getSenderProvince();
        String senderCity = bookingInquiry.getSenderCity();
        // 如果是英文且存在cityCode
        if (LanguageEnum.英文.value.equals(language) && StringUtils.isNotEmpty(bookingInquiry.getCityCode())) {
            senderCity = getCityName(bookingInquiry.getCityCode());
        }
        if ("河南省".equals(senderProvince) || "410000".equals(bookingInquiry.getProvinceCode())) { // 省内货源
            bookingInquiry.setUploadStation("郑州");
        } else { // 省外
            bookingInquiry.setUploadStation("工厂");
        }
        BookingInquiryResult bookingInquiryResult = getZGRailFee(bookingInquiry);
        // 查询维护的标准化费用
        ZgRussiaGoingFee param = new ZgRussiaGoingFee();
        param.setPickUpCity(senderCity.contains("市") ? senderCity.replaceAll("市", "") : senderCity);
        param.setContainerType(bookingInquiry.getContainerType());
        param.setCargoCollectionPoint("郑州");
        List<ZgRussiaGoingFee> russiaGoingFees = zgRussiaGoingFeeMapper.selectZePickFee(param);
        if (!CollectionUtils.isEmpty(russiaGoingFees)){
            // 赋值
            bookingInquiryResult.setPickUpDistance(StringUtils.valueOf(russiaGoingFees.get(0).getDistance()));//提货距离
            bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
            bookingInquiryResult.setPickUpCurrencyType("$");//币种
            long fee = russiaGoingFees.get(0).getPickGoodsFee();
            bookingInquiryResult.setPickUpFees(String.valueOf(fee*bookingInquiry.getContainerNum()));
            bookingInquiryResult.setPickUpExpiration(russiaGoingFees.get(0).getValidEndDate());
            // 判断整柜提货费是否维护，不是就转待审核
            bookingInquiryResult.setEnquiryState("3");
            return bookingInquiryResult;
        }
        //计算提货城市到集货点的距离
        Float shipPlaceAndZZDistance = getDistances(senderCity.replaceAll("市", "") +"火车站", zzCityAddress);
        // 赋值
        bookingInquiryResult.setPickUpDistance(shipPlaceAndZZDistance.toString());//提货距离
        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
        bookingInquiryResult.setPickUpCurrencyType("$");//币种

        BigDecimal bgZZ = new BigDecimal(shipPlaceAndZZDistance);
        if (bgZZ.compareTo(new BigDecimal("1000")) == 1) {
            bgZZ = bgZZ.subtract(new BigDecimal("1000"));
        } else if (!"河南省".equals(senderProvince) || !"410000".equals(bookingInquiry.getProvinceCode())){
            bookingInquiryResult.setPickUpFees("0");
            return bookingInquiryResult;
        }
        String singleFee = "1.5";
        if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
            singleFee = "1.64";
        }
        BigDecimal bgZZPickUPFee = bgZZ.multiply(new BigDecimal(singleFee)).setScale(1,BigDecimal.ROUND_HALF_UP);//到郑州的提货费
        if(bgZZPickUPFee.compareTo(new BigDecimal("100")) == 1){
            //郑欧线卡车提送货费规则，如果提货费大于100usd/车
            bookingInquiryResult.setPickUpFees(String.valueOf(bgZZPickUPFee.multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//提货费
        }else {
            //小于100，默认100usd/车
            bookingInquiryResult.setPickUpFees(String.valueOf(100*bookingInquiry.getContainerNum()));//提货费
        }
        bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());
        return bookingInquiryResult;

    }

    /**
     *计算中亚去程整柜提货费、
     */
    public BookingInquiryResult getZyPickUpFees(BookingInquiry bookingInquiry){
        String zzCity = "郑州国际陆港园区";
        String senderCity = bookingInquiry.getSenderCity();
        // 如果是英文且存在cityCode
        if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage()) && StringUtils.isNotEmpty(bookingInquiry.getCityCode())) {
            senderCity = getCityName(bookingInquiry.getCityCode());
        }
        //计算提货城市到集货点的距离
        Float shipPlaceAndZZDistance = getDistances(senderCity.replaceAll("市", "") + "火车站", zzCity);
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        // 赋值
        bookingInquiryResult.setPickUpDistance(shipPlaceAndZZDistance.toString());//提货距离
        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
        bookingInquiryResult.setPickUpCurrencyType("$");//币种

        BigDecimal bgZZ = new BigDecimal(shipPlaceAndZZDistance);
        String singleFee = "1.3";
        if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
            singleFee = "1.44";
        }
        BigDecimal bgZZPickUPFee = bgZZ.multiply(new BigDecimal(singleFee)).setScale(1,BigDecimal.ROUND_HALF_UP);//到郑州的提货费
        if(bgZZPickUPFee.compareTo(new BigDecimal("100")) == 1){
            //如果提货费大于100usd/车
            bookingInquiryResult.setPickUpFees(String.valueOf(bgZZPickUPFee.multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//提货费
        }else {
            //小于100，默认100usd/车
            bookingInquiryResult.setPickUpFees(String.valueOf(100*bookingInquiry.getContainerNum()));//提货费
        }
        bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());
        bookingInquiryResult.setEnquiryState("2");
        return bookingInquiryResult;
    }
    /**
     *计算中亚回程整柜派送费、
     */
    public BookingInquiryResult getZyDeliveryFee(BookingInquiry bookingInquiry){
        String zzCity = "郑州国际陆港园区";
        String receiveCity = bookingInquiry.getReceiveCity();
        // 如果是英文且存在cityCode
        if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage()) && StringUtils.isNotEmpty(bookingInquiry.getCityCode())) {
            receiveCity = getCityName(bookingInquiry.getCityCode());
        }
        //计算郑州陆港园区到送货地距离
        Float distances = getDistances(zzCity,receiveCity.replaceAll("市", "")+"火车站");
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        // 赋值
        bookingInquiryResult.setDeliveryDistance(distances.toString());//派送距离
        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//送货地
        bookingInquiryResult.setDeliveryCurrencyType("$");//币种
        String singleFee = "1.3";
        if(bookingInquiry.getContainerType().endsWith("RF") || bookingInquiry.getContainerType().endsWith("OT")){
            singleFee = "1.1";
        }
        if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
            singleFee = "1.44";
            if(bookingInquiry.getContainerType().endsWith("RF") || bookingInquiry.getContainerType().endsWith("OT")){
                singleFee = "1.24";
            }
        }
        BigDecimal bgZZ = new BigDecimal(distances);
        BigDecimal deliveryFee = bgZZ.multiply(new BigDecimal(singleFee)).setScale(1,BigDecimal.ROUND_HALF_UP);//派送费
        if(deliveryFee.compareTo(new BigDecimal("100")) == 1){
            //如果大于100usd/车
            bookingInquiryResult.setDeliveryFees(String.valueOf(deliveryFee.multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//派送费
        }else {
            //小于100，默认100usd/车
            bookingInquiryResult.setDeliveryFees(String.valueOf(100*bookingInquiry.getContainerNum()));//派送费
        }
        bookingInquiryResult.setDeliveryExpiration(getThirtyDaysLaterDate());
        return bookingInquiryResult;
    }

    /**
     * 中亚线路整柜铁路运费（查询条件：去回程和线路）
     */
    public BookingInquiryResult getZyZGRailFee(BookingInquiry bookingInquiry) {
        String lineType = bookingInquiry.getLineType();//线路
        String uploadStation = bookingInquiry.getUploadStation();//上货站
        String dropStation = bookingInquiry.getDropStation();//下货站
        if (("en").equals(bookingInquiry.getLanguage())) {
            uploadStation = iBusiSiteService.getBusiSiteByName(uploadStation, "en");
            dropStation = iBusiSiteService.getBusiSiteByName(dropStation, "en");
        }
//        if (uploadStation.contains("阿拉木图")) {
//            uploadStation = "阿拉木图";
//        }
//        if (dropStation.contains("阿拉木图")) {
//            dropStation = "阿拉木图";
//        }
        //箱属字段反了
        String containerBelong = bookingInquiry.getContainerBelong().equals("0") ? "1" : "0";
        String containerType = bookingInquiry.getContainerType();//箱型
        String sameContainerType = getSameContainerType(containerType);//箱型,20HC和20GP,40HC和40GP
        Integer containerNum = bookingInquiry.getContainerNum();
        // 20尺的按双数询
        if (containerType.startsWith("20")) {
            containerType = "2*" + containerType;
            containerNum = containerNum / 2;
        }
        String eastOrWest = bookingInquiry.getEastOrWest();//去回程
        Long inquiryId = bookingInquiry.getId();

        Map<String, String> map1 = new HashMap<>();
        map1.put("orderUploadSite", uploadStation);
        map1.put("orderUnloadSite", dropStation);
        map1.put("containerBelong", containerBelong);
        map1.put("containerType", containerType);
        map1.put("sameContainerType", sameContainerType);
        map1.put("lineType", lineType);
        map1.put("eastOrWest", eastOrWest);
        map1.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        ZgRailDivision zgRailDivision = getMinRailFee(zgRailDivisionMapper.selectZgRailDivisionWithMap(map1));
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        if(StringUtils.isNotNull(zgRailDivision)) {
            BigDecimal tlf = new BigDecimal(zgRailDivision.getRailCost());      //铁路费
            // 直客在报价基础上增加500元
            if (bookingInquiry.getClientType().equals("1")) {
                tlf = tlf.add(new BigDecimal("500"));
            }
            //更新到询价结果反馈表中
            bookingInquiryResult.setInquiryId(inquiryId);//询价id
            if (StringUtils.isNotNull(containerNum)) {
                BigDecimal totalRailFee = tlf.multiply(new BigDecimal(containerNum)).setScale(1, BigDecimal.ROUND_HALF_UP);
                bookingInquiryResult.setRailwayFees(totalRailFee.toString());// 铁路费用
            }else{
                bookingInquiryResult.setRailwayFees(tlf.toString());// 铁路费用
            }
            bookingInquiryResult.setRailwayExpiration(zgRailDivision.getValidEndDate());
        }else{
            bookingInquiryResult.setRailwayFees("0"); //铁路费用
            bookingInquiryResult.setRailwayExpiration(
                    getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                            bookingInquiry.getBookingTimeFlag())
            );
            // bookingInquiryResult.setRailRemark("相应站点铁路费用暂未维护，铁路费用无法计算");
        }
        bookingInquiryResult.setRailwayFeedbackTime(new Date());//铁路运费反馈时间
        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation()); //上货站
        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());//下货站
        bookingInquiryResult.setRailwayCurrencyType("￥");//欧线、俄线和中越是美元，中亚是人民币
        bookingInquiryResult.setRailwayAging(
                getRailwayAging(
                        lineType,
                        uploadStation,
                        dropStation)
        );
        return bookingInquiryResult;
    }
    /**
     * 中亚散货去程、回程铁路运费计算
     */
    public BookingInquiryResult getZySHQCRailFee(BookingInquiry bookingInquiry) {
        Map<String, String> map = new HashMap<>();
        String uploadStation = bookingInquiry.getUploadStation();//上货站
        String dropStation = bookingInquiry.getDropStation();//下货站
        if (("en").equals(bookingInquiry.getLanguage())) {
            uploadStation = iBusiSiteService.getBusiSiteByName(uploadStation, "en");
            dropStation = iBusiSiteService.getBusiSiteByName(dropStation, "en");
        }
//        if (uploadStation.contains("阿拉木图")) {
//            uploadStation = "阿拉木图";
//        }
//        if (dropStation.contains("阿拉木图")) {
//            dropStation = "阿拉木图";
//        }
        map.put("orderUploadSite", uploadStation);
        map.put("orderUnloadSite", dropStation);
        map.put("eastOrWest", bookingInquiry.getEastOrWest());//去回程
        map.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        ShRailDivision shRailDivision = getMinShRailFee(shRailDivisionMapper.selectShRailDivisionWithMap(map));
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setInquiryId(bookingInquiry.getId());//询价id
        //散货铁路运费计算规则，去程运费+目的站收费（目的站基础费用+根据体积判断每立方收费标准）*总体积
        // 最终选用体积
        BigDecimal billableVolume = getBillableVolume(bookingInquiry);
        if (billableVolume.compareTo(new BigDecimal("0.5")) == -1) {
            billableVolume = new BigDecimal("0.5");
        }
        if (StringUtils.isNotNull(shRailDivision)) {
            //不为空计算
            String lcFreight = shRailDivision.getLclFreight();//运费
            BigDecimal totalRailFees = billableVolume.multiply(new BigDecimal(lcFreight)).setScale(2,BigDecimal.ROUND_HALF_UP);//总铁路运费
            // 直客收固定费用
            if(bookingInquiry.getClientType().equals("1")) {
                BigDecimal bgMaxVolumCost = new BigDecimal(shRailDivision.getMaxVolumeCost());
                totalRailFees = totalRailFees.add(bgMaxVolumCost);
            }
            bookingInquiryResult.setRailwayFees(totalRailFees.toString());//铁路费用
            bookingInquiryResult.setRailwayFeedbackTime(new Date());//铁路运费反馈时间
            bookingInquiryResult.setRailwayExpiration(shRailDivision.getValidEndDate());
        }else{
            bookingInquiryResult.setRailwayFees("0"); //铁路费用
            bookingInquiryResult.setRailwayExpiration(
                    getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                            bookingInquiry.getBookingTimeFlag())
            );
            // bookingInquiryResult.setRailRemark("相应站点铁路费用暂未维护，铁路费用无法计算");
        }
        bookingInquiryResult.setRailwayCurrencyType("￥");//欧线、俄线和中越是美元，中亚是人民币
        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation()); //上货站
        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());//下货站
        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地（发货地）
        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//派送地（收货地）
        bookingInquiryResult.setRailwayAging(
                getRailwayAging(
                        bookingInquiry.getLineType(),
                        uploadStation,
                        dropStation));
        // 暂存计费体积
        bookingInquiryResult.setRailRemark(String.valueOf(billableVolume));
        return bookingInquiryResult;
    }

    /**
     *计算中越去程门到站整柜提货费 + 铁路
     */
    public BookingInquiryResult getZynPickUpAndRailwayFees(BookingInquiry bookingInquiry){
        String zzCity = "郑州国际陆港园区";
        String senderCity = bookingInquiry.getSenderCity();
        // 如果是英文且存在cityCode
        if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage()) && StringUtils.isNotEmpty(bookingInquiry.getCityCode())) {
            senderCity = getCityName(bookingInquiry.getCityCode());
        }
        bookingInquiry.setUploadStation("工厂");
        BookingInquiryResult bookingInquiryResult = getZGRailFee(bookingInquiry);
        bookingInquiry.setUploadStation("郑州");
        //计算提货城市到集货点的距离
        Float shipPlaceAndZZDistance = getDistances((senderCity.contains("市")?senderCity.replaceAll("市", "") : senderCity) +"火车站", zzCity);
        BigDecimal bgZZ = new BigDecimal(shipPlaceAndZZDistance);
        if (bgZZ.compareTo(new BigDecimal("500")) == 1) {
            bgZZ = bgZZ.subtract(new BigDecimal("500"));
        } else {
            bookingInquiryResult.setPickUpCurrencyType(null);//币种
//            bookingInquiryResult.setXxyoRemark("费用已包含在铁路报价费用中");
            return bookingInquiryResult;
        }
        // 赋值
        bookingInquiryResult.setPickUpDistance(shipPlaceAndZZDistance.toString());//提货距离
        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地
        bookingInquiryResult.setPickUpCurrencyType("$");//币种
        String singleFee = "1.1";
        if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
            singleFee = "1.24";
        }
        BigDecimal bgZZPickUPFee = bgZZ.multiply(new BigDecimal(singleFee)).setScale(1,BigDecimal.ROUND_HALF_UP);//到郑州的提货费
        if(bgZZPickUPFee.compareTo(new BigDecimal("100")) == 1){
            //郑欧线卡车提送货费规则，如果提货费大于100usd/车
            bookingInquiryResult.setPickUpFees(String.valueOf(bgZZPickUPFee.multiply(new BigDecimal(bookingInquiry.getContainerNum()))));//提货费
        }else {
            //小于100，默认100usd/车
            bookingInquiryResult.setPickUpFees(String.valueOf(100*bookingInquiry.getContainerNum()));//提货费
        }
        bookingInquiryResult.setPickUpExpiration(getThirtyDaysLaterDate());
        return bookingInquiryResult;

    }
    /**
     * 中越线路整柜铁路运费（查询条件：去回程和线路）
     */
    public BookingInquiryResult getZynZGRailFee(BookingInquiry bookingInquiry) {
        String lineType = bookingInquiry.getLineType();//线路
        String uploadStation = bookingInquiry.getUploadStation();//上货站
        String dropStation = bookingInquiry.getDropStation();//下货站
        //箱属字段反了
        String containerBelong = bookingInquiry.getContainerBelong().equals("0") ? "1" : "0";
        String containerType = bookingInquiry.getContainerType();//箱型
        String sameContainerType = getSameContainerType(containerType);
        Integer containerNum = bookingInquiry.getContainerNum();
        // 20尺的按双数询
        if (containerType.startsWith("20")) {
            containerType = "2*" + containerType;
            sameContainerType = "2*" + sameContainerType;
            containerNum = containerNum / 2;
        }
        String eastOrWest = bookingInquiry.getEastOrWest();//去回程
        Long inquiryId = bookingInquiry.getId();
        Map<String, String> map1 = new HashMap<>();
        if (("en").equals(bookingInquiry.getLanguage())) {
            uploadStation = iBusiSiteService.getBusiSiteByName(uploadStation, "en");
            dropStation = iBusiSiteService.getBusiSiteByName(dropStation, "en");
        }
        map1.put("orderUploadSite", uploadStation);
        map1.put("orderUnloadSite", dropStation);
        map1.put("containerBelong", containerBelong);
        map1.put("containerType", containerType);
        map1.put("sameContainerType", sameContainerType);
        map1.put("lineType", lineType);
        map1.put("eastOrWest", eastOrWest);
        map1.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        ZgRailDivision zgRailDivision = getMinRailFee(zgRailDivisionMapper.selectZynZgRailDivisionWithMap(map1));
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        if (StringUtils.isNotNull(zgRailDivision)) {
            BigDecimal tlf = new BigDecimal(zgRailDivision.getRailCost()); //铁路费
            // 直客在报价基础上增加100$
            if (bookingInquiry.getClientType().equals("1")) {
                tlf = tlf.add(new BigDecimal("100"));
            }
            //更新到询价结果反馈表中
            bookingInquiryResult.setInquiryId(inquiryId);//询价id
            if (StringUtils.isNotNull(containerNum)) {
                BigDecimal totalRailFee = tlf.multiply(new BigDecimal(containerNum)).setScale(1, BigDecimal.ROUND_HALF_UP);
                bookingInquiryResult.setRailwayFees(totalRailFee.toString());// 铁路费用
            }else{
                bookingInquiryResult.setRailwayFees(tlf.toString());// 铁路费用
            }
            bookingInquiryResult.setRailwayExpiration(zgRailDivision.getValidEndDate());
        }else{
            bookingInquiryResult.setRailwayFees("0"); //铁路费用
            bookingInquiryResult.setRailwayExpiration(
                    getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                            bookingInquiry.getBookingTimeFlag())
            );
            // bookingInquiryResult.setRailRemark("相应站点铁路费用暂未维护，铁路费用无法计算");
        }
        bookingInquiryResult.setRailwayFeedbackTime(new Date());//铁路运费反馈时间
        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation()); //上货站
        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());//下货站
        bookingInquiryResult.setRailwayCurrencyType("$");//欧线、俄线和中越是美元，中亚是人民币
        bookingInquiryResult.setRailwayAging(
                getRailwayAging(
                        lineType,
                        uploadStation,
                        dropStation)
        );
        return bookingInquiryResult;
    }

    /**
     * 中越散货去程、回程铁路运费计算
     */
    public BookingInquiryResult getZynSHQCRailFee(BookingInquiry bookingInquiry) {
        Map<String, String> map = new HashMap<>();
        String uploadStation = bookingInquiry.getUploadStation();//上货站
        String dropStation = bookingInquiry.getDropStation();//下货站
        if (("en").equals(bookingInquiry.getLanguage())) {
            uploadStation = iBusiSiteService.getBusiSiteByName(uploadStation, "en");
            dropStation = iBusiSiteService.getBusiSiteByName(dropStation, "en");
        }
        map.put("orderUploadSite", uploadStation);
        map.put("orderUnloadSite", dropStation);
        map.put("eastOrWest", bookingInquiry.getEastOrWest());//去回程
        map.put("bookingTimeFlag",bookingInquiry.getBookingTimeFlag()); // 当月次月
        ShRailDivision shRailDivision = getMinShRailFee(shRailDivisionMapper.selectZynShRailDivisionWithMap(map));
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setInquiryId(bookingInquiry.getId());//询价id
        //散货铁路运费计算规则，去程运费+目的站收费（目的站基础费用+根据体积判断每立方收费标准）*总体积
        // 最终选用体积
        BigDecimal billableVolume = getBillableVolume(bookingInquiry);
        if (billableVolume.compareTo(new BigDecimal("0.5")) == -1) {
            billableVolume = new BigDecimal("0.5");
        }
        if (StringUtils.isNotNull(shRailDivision)) {
            //不为空计算
            String lcFreight = shRailDivision.getLclFreight();//运费
            Long orderUnloadSiteBacost = shRailDivision.getOrderUnloadSiteBacost();//目的站收费
            Long orderUploadSiteCost = shRailDivision.getOrderUploadSiteCost();// 起运站收费
            BigDecimal bgLcF = new BigDecimal(lcFreight);
            // 目的站
            BigDecimal bgOrdunload = new BigDecimal("0");
            if (!ObjectUtils.isEmpty(orderUnloadSiteBacost)) {
                bgOrdunload = new BigDecimal(orderUnloadSiteBacost);
            }
            BigDecimal totalSHFee = new BigDecimal("0");
            totalSHFee = totalSHFee.add(bgLcF.add(bgOrdunload));

            BigDecimal totalRailFees = billableVolume.multiply(totalSHFee).setScale(2,BigDecimal.ROUND_HALF_UP);//总铁路运费
            // 起运站费用
            BigDecimal ordupload = new BigDecimal("0");
            if (!ObjectUtils.isEmpty(orderUploadSiteCost)) {
                ordupload = new BigDecimal(orderUploadSiteCost);
            }
            totalRailFees = totalRailFees.add(ordupload).add(new BigDecimal(shRailDivision.getMaxVolumeCost()));
            bookingInquiryResult.setRailwayFees(totalRailFees.toString());//铁路费用
            bookingInquiryResult.setRailwayFeedbackTime(new Date());//铁路运费反馈时间
            bookingInquiryResult.setRailwayExpiration(shRailDivision.getValidEndDate());
        }else{
            bookingInquiryResult.setRailwayFees("0"); //铁路费用
            bookingInquiryResult.setRailwayExpiration(
                    getRailwayExpiration(bookingInquiry.getLineType(),bookingInquiry.getGoodsType(),
                            bookingInquiry.getBookingTimeFlag())
            );
            // bookingInquiryResult.setRailRemark("相应站点铁路费用暂未维护，铁路费用无法计算");
        }
        bookingInquiryResult.setRailwayCurrencyType("$");//欧线、俄线和中越是美元，中亚是人民币
        bookingInquiryResult.setUploadStation(bookingInquiry.getUploadStation()); //上货站
        bookingInquiryResult.setDropStation(bookingInquiry.getDropStation());//下货站
        bookingInquiryResult.setPickUpAddress(bookingInquiry.getShipmentPlace());//提货地（发货地）
        bookingInquiryResult.setDeliveryAddress(bookingInquiry.getReceiptPlace());//派送地（收货地）
        bookingInquiryResult.setRailwayAging(
                getRailwayAging(
                        bookingInquiry.getLineType(),
                        uploadStation,
                        dropStation));
        //暂存计费体积
        bookingInquiryResult.setRailRemark(String.valueOf(billableVolume));
        return bookingInquiryResult;
    }

    /**
     * 获取铁路运输时效
     *
     * @param lineType
     * @param uploadStation
     * @param unloadStation
     * @return
     */
    public String getRailwayAging(String lineType, String uploadStation, String unloadStation) {
        BusiClasses busiClasses = new BusiClasses();
        busiClasses.setLineTypeid(Long.valueOf(lineType));
        busiClasses.setClassStationofdeparture(uploadStation);
        busiClasses.setClassStationofdestination(unloadStation);
        List<BusiClasses> busiClassesList = busiClassesMapper.selectBusiClassesList(busiClasses);
        if (CollectionUtils.isEmpty(busiClassesList)) {
            return null;
        }
        BusiClasses bc = busiClassesList.stream().max(
                Comparator.comparing(BusiClasses::getClassTransporttime,
                        (a,b)->{
                            if (StringUtils.isEmpty(a) && StringUtils.isEmpty(b)) {
                                return 0;
                            } else if (StringUtils.isEmpty(a) && StringUtils.isNotEmpty(b)) {
                                return -1;
                            } else if (StringUtils.isNotEmpty(a) && StringUtils.isEmpty(b)) {
                                return 1;
                            } else if (Integer.valueOf(a) > Integer.valueOf(b)){
                                return 1;
                            } else if (Integer.valueOf(a).equals(Integer.valueOf(b))){
                                return 0;
                            } else if (Integer.valueOf(a) < Integer.valueOf(b)) {
                                return -1;
                            } else {
                                return 0;
                            }
                        })
        ).get();
        return bc.getClassTransporttime();
    }

    /**
     * 获取铁路运输价格有效期
     *
     * @param lineType
     * @return
     */
    public Date getRailwayExpiration(String lineType,String goodsType,String bookingTimeFlag) {
        Date date = getThirtyDaysLaterDate();
        Map<String,String> map = new HashMap();
        map.put("lineType",lineType);
        map.put("bookingTimeFlag",bookingTimeFlag);
        if ("0".equals(goodsType)) {
            List<ZgRailDivision> zgRailDivisions = zgRailDivisionMapper.selectZgRailwayExpiration(map);
            if (!CollectionUtils.isEmpty(zgRailDivisions)) {
                date = zgRailDivisions.get(0).getValidEndDate();
            }
        } else {
            List<ShRailDivision> shRailDivisions = shRailDivisionMapper.selectShRailwayExpiration(map);
            if (!CollectionUtils.isEmpty(shRailDivisions)) {
                date = shRailDivisions.get(0).getValidEndDate();
            }
        }
        return date;
    }

    /**
     * 获取散货计费体积billable volume
     *
     * @param bookingInquiry
     * @return
     */
    public BigDecimal getBillableVolume(BookingInquiry bookingInquiry){
        // 新计算计费体积
        List<BookingInquiryGoodsDetails> goodsDetailsList = bookingInquiry.getBookingInquiryGoodsDetailsList();
        List<BigDecimal> list = new ArrayList<>();
        // 实测总体积
        BigDecimal measuredVolume = new BigDecimal(bookingInquiry.getTotalVolume());
        list.add(measuredVolume);
        // 是否重货
        String heavyValue = "425";
        // 中亚，中越 500
        if (bookingInquiry.getLineType().equals("2") || bookingInquiry.getLineType().equals("3")) {
            heavyValue = "500";
        }
        BigDecimal heavyCargoVolume = new BigDecimal(bookingInquiry.getTotalWeight()).
                divide(new BigDecimal(heavyValue),2, BigDecimal.ROUND_HALF_UP);
        list.add(heavyCargoVolume);
        // 是否易碎
        BigDecimal fragileVolume = new BigDecimal("0");
        if (bookingInquiry.getGoodsFragile().equals("1")) {
            fragileVolume = measuredVolume.multiply(new BigDecimal("1.3")).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        list.add(fragileVolume);
        // 是否不可堆叠
        BigDecimal nonStackableVolume = new BigDecimal("0");
        if (bookingInquiry.getIsStack().equals("0")) {
            for (BookingInquiryGoodsDetails goodsDetails : goodsDetailsList) {
                // 一种货物
                BigDecimal singleVolume;
                if (new BigDecimal(goodsDetails.getGoodsHeight()).compareTo(new BigDecimal("180")) == 1) {
                    singleVolume = new BigDecimal(goodsDetails.getGoodsLength()).
                            multiply(new BigDecimal(goodsDetails.getGoodsWidth())).
                            multiply(new BigDecimal(goodsDetails.getGoodsHeight()))
                            .divide(new BigDecimal("1000000"),4, BigDecimal.ROUND_HALF_UP)
                            .multiply(new BigDecimal(goodsDetails.getGoodsAmount()));
                } else {
                    singleVolume = new BigDecimal(goodsDetails.getGoodsLength()).
                            multiply(new BigDecimal(goodsDetails.getGoodsWidth())).
                            multiply(new BigDecimal("180"))
                            .divide(new BigDecimal("1000000"),4, BigDecimal.ROUND_HALF_UP)
                            .multiply(new BigDecimal(goodsDetails.getGoodsAmount()));
                }
                nonStackableVolume = nonStackableVolume.add(singleVolume);
            }
        }
        list.add(nonStackableVolume.setScale(2, BigDecimal.ROUND_HALF_UP));
        // 最终选用体积
        return list.stream().max(BigDecimal::compareTo).get();
    }

    /**
     * 判断货物尺寸重量是否可操作
     * 货物尺寸超出站点操作限制，需要联系业务部单询。
     * @param bookingInquiry
     * @return
     */
    public boolean isOperable(BookingInquiry bookingInquiry) {

        String station = "";
        // 去程 门到站或站到站
        if("0".equals(bookingInquiry.getEastOrWest()) &&
            ("1".equals(bookingInquiry.getBookingService()) ||
                "2".equals(bookingInquiry.getBookingService()))
        ){
            station = bookingInquiry.getDropStation();
        } else if ("1".equals(bookingInquiry.getEastOrWest()) &&
                ("3".equals(bookingInquiry.getBookingService()) ||
                        "2".equals(bookingInquiry.getBookingService()))
        ){
            station = bookingInquiry.getUploadStation();
        }
        // 没有国外站点不判断
        if (StringUtils.isEmpty(station)) {
            return Boolean.FALSE;
        }
        GoodsOperableEnum[] goodsOperableEnums = GoodsOperableEnum.values();
        boolean b = true;
        String length = "";
        String weight = "";
        for (GoodsOperableEnum goodsOperableEnum : goodsOperableEnums) {
            if (station.equals(goodsOperableEnum.getStation())) {
                b = false;
                length = goodsOperableEnum.getLength();
                weight = goodsOperableEnum.getWeight();
            }
        }
        // 没有限制不判断
        if (b) {
            return Boolean.FALSE;
        }
        Boolean result = false;
        // 货物集合
        List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList =
                bookingInquiry.getBookingInquiryGoodsDetailsList();
        for (BookingInquiryGoodsDetails bookingInquiryGoodsDetails : bookingInquiryGoodsDetailsList) {
            if (station.equals("杜伊斯堡")) {
                if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal("200")) == 1 ||
                    new BigDecimal(bookingInquiryGoodsDetails.getGoodsWidth()).compareTo(new BigDecimal("120")) == 1 ||
                    new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal(weight)) == 1
                ) {
                    result = true;
                }
            } else {
                if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal(length)) == 1 ||
                    new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal(weight)) == 1
                ) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 判断询价是否需要国内拼箱场站报价
     *
     * @param bookingInquiry
     * @return
     */
    public Boolean isNeedDomesticOrder(BookingInquiry bookingInquiry){

        // 1、单件重超5t，单件长超3.5m，单票数量超500件
        boolean a = true;
        // 2、去程整柜散货到堆场
        boolean b = true;
        // 3、回程整柜散货派送
        boolean c = true;

        if ("1".equals(bookingInquiry.getGoodsType())) { // 1
            if (new BigDecimal(bookingInquiry.getTotalAmount()).compareTo(new BigDecimal("500"))==1) {
                a = false;
            }
            List<BookingInquiryGoodsDetails> goodsDetails = bookingInquiry.getBookingInquiryGoodsDetailsList();
            for (BookingInquiryGoodsDetails goodsDetail : goodsDetails) {
                if (new BigDecimal(goodsDetail.getGoodsWeight()).compareTo(new BigDecimal("5000")) == 1) {
                    a = false;
                }
                if (new BigDecimal(goodsDetail.getGoodsLength()).compareTo(new BigDecimal("350"))==1) {
                    a = false;
                }
            }
        } else {
            if ("0".equals(bookingInquiry.getEastOrWest()) &&
                    ("1".equals(bookingInquiry.getDeliveryType()) || "0".equals(bookingInquiry.getDeliverySelfType()) )){ // 2
                b = false;
            }
            if ("1".equals(bookingInquiry.getEastOrWest()) && "1".equals(bookingInquiry.getDistributionType())) { // 3
                c = false;
            }
        }

        // 如果三个条件都不符合，就不发出去了
        if (a && b && c) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * 判断询价是否需要国外场站报价
     *
     * @param bookingInquiry
     * @return
     */
    public Boolean isNeedForeignOrder(BookingInquiry bookingInquiry){

        // 1、散货
        boolean a = true;
        // 2、回程整柜散货到堆场
        boolean b = true;
        // 3、去程整柜散货派送
        boolean c = true;

        if ("1".equals(bookingInquiry.getGoodsType())) { // 1
            a = false;
        } else {
            if ("1".equals(bookingInquiry.getEastOrWest()) &&
                    ("1".equals(bookingInquiry.getDeliveryType()) || "0".equals(bookingInquiry.getDeliverySelfType()) )){ // 2
                b = false;
            }
            if ("0".equals(bookingInquiry.getEastOrWest()) && "1".equals(bookingInquiry.getDistributionType())) { // 3
                c = false;
            }
        }

        // 如果三个条件都不符合，就不发出去了
        if (a && b && c) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * 获取一个月后的日期
     *
     * @return
     */
    public static Date getThirtyDaysLaterDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取最近有效期日期
     *
     * @return
     */
    public Date getValidDate(Date... date){
        List<Date> dateList = new ArrayList<>(Arrays.asList(date));
        dateList.removeAll(Collections.singleton(null));
        if (CollectionUtils.isEmpty(dateList)) {
            return null;
        }
        return dateList.stream().min(Comparator.comparing(Date::getTime)).get();
    }

    /**
     * 根据cityCode获取中文城市名
     *
     * @param cityCode
     * @return
     */
    public String getCityName(String cityCode){
        City city = pcaMapper.selectCity(cityCode);
        return city.getCityName();
    }
    /**
     * 根据提还箱地中文获取英文
     *
     * @param address
     * @return
     */
    public String getAddressEnName(String address){
        List<BusiBoxfee> busiBoxfees = busiBoxfeeMapper.selectBoxFeeByAddress(address);
        return busiBoxfees.get(0).getAddressEn();
    }
    /**
     * 根据提还箱地英文获取中文
     *
     * @param address
     * @return
     */
    public String getAddressName(String address){
        List<BusiBoxfee> busiBoxfees = busiBoxfeeMapper.selectBoxFeeByAddress(address);
        return busiBoxfees.get(0).getAddress();
    }

}
