package com.szhbl.project.inquiry.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 订舱询价对象 booking_inquiry
 * 
 * @author jhm
 * @date 2020-04-01
 */
public class BookingInquiry extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 发货地省份 */
    @Excel(name = "发货地省份")
    private String senderProvince;
    /** 发货地城市 */
    @Excel(name = "发货地城市")
    private String senderCity;
    /** 发货地区域 */
    @Excel(name = "发货地区域")
    private String senderArea;
    /** 发货地 */
    @Excel(name = "发货地")
    private String shipmentPlace;

    /** 上货站 */
    @Excel(name = "上货站")
    private String uploadStation;

    /** 收货地省份 */
    @Excel(name = "收货地省份")
    private String receiveProvince;
    /** 收货地城市 */
    @Excel(name = "收货地城市")
    private String receiveCity;
    /** 收货地区域 */
    @Excel(name = "收货地区域")
    private String receiveArea;
    /** 收货地 */
    @Excel(name = "收货地")
    private String receiptPlace;

    /** 下货站 */
    @Excel(name = "下货站")
    private String dropStation;

    /** 服务（0：门到门；1：门到站；2：站到站；3：站到门） */
    @Excel(name = "服务", readConverterExp = "0=：门到门；1：门到站；2：站到站；3：站到门")
    private String bookingService;

    /** 是否委托ZIH提货 （0是 1否  2铁路到货）不能为空 */
    @Excel(name = "是否委托zih提货", readConverterExp = "0=：是；1：否")
    private String isPickUp;

    /** 是否由ZIH代理送货  （0否 1是），不能为空 */
    @Excel(name = "是否委托zih送货", readConverterExp = "0=：否；1：是")
    private String isDelivery;

    /** 是否委托zih代理报关（0是 1否），不能为空 */
    @Excel(name = "是否委托zih代理报关", readConverterExp = "0=：是；1：否")
    private String isOrderCustoms;

    /** 是否由ZIH代理清关 （0否 1是） ，不能为空 */
    @Excel(name = "是否委托zih代理清关", readConverterExp = "0=：否；1：是")
    private String isClearCustoms;

    /** 箱属（0：zih；1：自备） */
    @Excel(name = "箱属", readConverterExp = "0=：zih；1：自备")
    private String containerBelong;

    /** 货物类型（0：整柜；1：拼箱） */
    @Excel(name = "货物类型", readConverterExp = "0=：整柜；1：拼箱")
    private String goodsType;

    /** 箱型，不能为空 */
    @Excel(name = "箱型，")
    private String containerType;

    /** 箱量，不能为空 */
    @Excel(name = "箱量")
    private Integer containerNum;

    /** 包装方式 */
    @Excel(name = "包装方式")
    private String packageType;

    /** 是否可堆叠（0否：1是） */
    @Excel(name = "是否可堆叠", readConverterExp = "0=否：1是")
    private String isStack;

    /** 总数量 */
    @Excel(name = "总数量")
    private String totalAmount;

    /** 总重量(kg) */
    @Excel(name = "总重量(kg)")
    private String totalWeight;

    /** 总体积(m³) */
    @Excel(name = "总体积(m³)")
    private String totalVolume;

    /** 计费体积(m³) */
    @Excel(name = "计费体积(m³)")
    private String billableVolume;

    /** 询价时间 */
    @Excel(name = "询价时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inquiryTime;

    /** 客户id，不能为空 */
    @Excel(name = "客户id")
    private String clientId;

    /** 删除标识（0未删除；1：已删除） */
    private String delFlag;

    /** 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄） */
    @Excel(name = "线路线路类型", readConverterExp = "0=郑欧（中欧")
    private String lineType;

    /** 提箱地 */
    @Excel(name = "提箱地")
    private String txAddress;
    /** 还箱地 */
    @Excel(name = "还箱地")
    private String hxAddress;

    /** 去回程,0为去程 1为回程*/
    private String eastOrWest;

    /** 上货站编号 */
    @Excel(name = "上货站编号")
    private String uploadStationNum;

    /** 下货站编号 */
    @Excel(name = "下货站编号")
    private String dropStationNum;

    /** 询价编号 */
    @Excel(name = "询价编号")
    private String inquiryNumber;


    /** 箱行亚欧报价编码*/
    private String  roadNum;

    /** 集疏报价编码*/
    private String jsNum;

    /** 集疏报价人*/
    private String jsBidder;

    /**
     * 拼箱物品详细信息，长、宽、高、数量、单件货物重量
     */
    private List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList;

    /**
     * 拼箱物品详细信息，长、宽、高、数量、单件货物重量
     */
    private String goodsDetails;
    /**
     * 客户类型 0货代 1直客 2贸易公司
     */
    private String clientType;
    /**
     * 客户单位名称
     */
    private String clientUnit;
    /**
     * 备注
     */
    private String remark;



    /** 国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车 */
   private String truckType;

   /**时效（箱型亚欧，0普通，1加急） */
   private String limitation;

    /**客户自送货方式（1整柜到堆场，0散货到堆场） */
    private String deliverySelfType;

    /**提货方式（0整柜到堆场，1散货到堆场）整柜和站到站同时选择
     时候才会出现这个选项 */
    private String deliveryType;

    /** 分拨方式（0整柜派送，1拆箱散货派送） */
    private String distributionType;

    /** 询价时间*/
    private Date createTime;

   /**是否易碎（1是0否）*/

   private String goodsFragile;

    /**
     * 单件超长超重 1是0否
     */
    private String goodsGeneral;

    /**询价状态（报价中）、2（待审核）、3（已报价）*/
    private String inquiryState;

    /**询价开始日期*/
    private String inquiryStartTime;
    /**询价结束日期**/
    private String inquiryEndTime;

    /** 部门编号*/
    private String deptCode;

    /***/
    public String readType;

    /**托书id*/
    private String orderId;

    /**还箱地id*/
    private String hxAddressId;

    /** 清关费用标准 */
    @Excel(name = "清关费用标准")
    private String qgfy;

    /** 场站费用标准 */
    @Excel(name = "场站费用标准")
    private String czfy;

    /**
     * 驳回原因
     */
    @Excel(name = "驳回原因")
    private String turndownReason;

    /**
     * 业务员数据权限 id
     */
    private String clientTjrId;

    /**
     * 前台显示语言 zh-en
     */
    private String language;

    /**
     *询价结果id
     */
    private String inquiryResultId;
    /**
     *是否转待审核询价0是1否
     */
    private Integer isToCheck;
    /**
     * 客户子账号id
     */
    private String khId;
    //国家
    private String country;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validDate;

    /** 订单号（委托书编号）（舱位号） */
    private String orderNumber;
    /** 班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;
    /**
     * 预期订舱时间（0当月1次月）
     */
    private String bookingTimeFlag;

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof BookingInquiry)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        final BookingInquiry other = (BookingInquiry) obj;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.lineType, other.getLineType());
        builder.append(this.eastOrWest, other.getEastOrWest());
        builder.append(this.goodsType, other.getGoodsType());
        builder.append(this.bookingService, other.getBookingService());
        builder.append(this.uploadStation, other.getUploadStation());
        builder.append(this.dropStation, other.getDropStation());
        builder.append(this.deliverySelfType, other.getDeliverySelfType());
        builder.append(this.isOrderCustoms, other.getIsOrderCustoms());
        builder.append(this.isClearCustoms, other.getIsClearCustoms());
        builder.append(this.containerType, other.getContainerType());
        builder.append(this.containerNum, other.getContainerNum());
        builder.append(this.containerBelong, other.getContainerBelong());
        builder.append(this.hxAddress, other.getHxAddress());
        builder.append(this.packageType, other.getPackageType());
        builder.append(this.isStack, other.getIsStack());
        builder.append(this.goodsFragile, other.getGoodsFragile());
        builder.append(this.totalAmount, other.getTotalAmount());
        builder.append(this.totalWeight, other.getTotalWeight());
        builder.append(this.totalVolume, other.getTotalVolume());
        builder.append(this.remark, other.getRemark());
        return builder.isEquals();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getClientTjrId() {
        return clientTjrId;
    }

    public void setClientTjrId(String clientTjrId) {
        this.clientTjrId = clientTjrId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setShipmentPlace(String shipmentPlace) 
    {
        this.shipmentPlace = shipmentPlace;
    }

    public String getShipmentPlace() 
    {
        return shipmentPlace;
    }
    public void setUploadStation(String uploadStation) 
    {
        this.uploadStation = uploadStation;
    }

    public String getUploadStation() 
    {
        return uploadStation;
    }
    public void setReceiptPlace(String receiptPlace) 
    {
        this.receiptPlace = receiptPlace;
    }

    public String getReceiptPlace() 
    {
        return receiptPlace;
    }
    public void setDropStation(String dropStation) 
    {
        this.dropStation = dropStation;
    }

    public String getDropStation() 
    {
        return dropStation;
    }
    public void setBookingService(String bookingService) 
    {
        this.bookingService = bookingService;
    }

    public String getBookingService() 
    {
        return bookingService;
    }
    public void setIsPickUp(String isPickUp) 
    {
        this.isPickUp = isPickUp;
    }

    public String getIsPickUp() 
    {
        return isPickUp;
    }
    public void setIsDelivery(String isDelivery) 
    {
        this.isDelivery = isDelivery;
    }

    public String getIsDelivery() 
    {
        return isDelivery;
    }
    public void setIsOrderCustoms(String isOrderCustoms) 
    {
        this.isOrderCustoms = isOrderCustoms;
    }

    public String getIsOrderCustoms() 
    {
        return isOrderCustoms;
    }
    public void setIsClearCustoms(String isClearCustoms) 
    {
        this.isClearCustoms = isClearCustoms;
    }

    public String getIsClearCustoms() 
    {
        return isClearCustoms;
    }
    public void setContainerBelong(String containerBelong) 
    {
        this.containerBelong = containerBelong;
    }

    public String getContainerBelong() 
    {
        return containerBelong;
    }
    public void setGoodsType(String goodsType) 
    {
        this.goodsType = goodsType;
    }

    public String getGoodsType() 
    {
        return goodsType;
    }
    public void setContainerType(String containerType) 
    {
        this.containerType = containerType;
    }

    public String getContainerType() 
    {
        return containerType;
    }
    public void setContainerNum(Integer containerNum) 
    {
        this.containerNum = containerNum;
    }

    public Integer getContainerNum() 
    {
        return containerNum;
    }
    public void setPackageType(String packageType) 
    {
        this.packageType = packageType;
    }

    public String getPackageType() 
    {
        return packageType;
    }
    public void setIsStack(String isStack) 
    {
        this.isStack = isStack;
    }

    public String getIsStack() 
    {
        return isStack;
    }
    public void setTotalAmount(String totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmount() 
    {
        return totalAmount;
    }
    public void setTotalWeight(String totalWeight) 
    {
        this.totalWeight = totalWeight;
    }

    public String getTotalWeight() 
    {
        return totalWeight;
    }
    public void setTotalVolume(String totalVolume) 
    {
        this.totalVolume = totalVolume;
    }

    public String getTotalVolume() 
    {
        return totalVolume;
    }
    public void setInquiryTime(Date inquiryTime) 
    {
        this.inquiryTime = inquiryTime;
    }

    public Date getInquiryTime() 
    {
        return inquiryTime;
    }
    public void setClientId(String clientId) 
    {
        this.clientId = clientId;
    }

    public String getClientId() 
    {
        return clientId;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setLineType(String lineType) 
    {
        this.lineType = lineType;
    }

    public String getLineType() 
    {
        return lineType;
    }
    public void setHxAddress(String hxAddress) 
    {
        this.hxAddress = hxAddress;
    }

    public String getHxAddress() 
    {
        return hxAddress;
    }
    public void setEastOrWest(String eastOrWest) 
    {
        this.eastOrWest = eastOrWest;
    }

    public String getEastOrWest() 
    {
        return eastOrWest;
    }
    public void setUploadStationNum(String uploadStationNum) 
    {
        this.uploadStationNum = uploadStationNum;
    }

    public String getUploadStationNum() 
    {
        return uploadStationNum;
    }
    public void setDropStationNum(String dropStationNum) 
    {
        this.dropStationNum = dropStationNum;
    }

    public String getDropStationNum() 
    {
        return dropStationNum;
    }
    public void setInquiryNumber(String inquiryNumber) 
    {
        this.inquiryNumber = inquiryNumber;
    }

    public String getInquiryNumber() 
    {
        return inquiryNumber;
    }

    public String getInquiryResultId() {
        return inquiryResultId;
    }

    public void setInquiryResultId(String inquiryResultId) {
        this.inquiryResultId = inquiryResultId;
    }

    public String getRoadNum() {
        return roadNum;
    }

    public void setRoadNum(String roadNum) {
        this.roadNum = roadNum;
    }

    public String getJsNum() {
        return jsNum;
    }

    public void setJsNum(String jsNum) {
        this.jsNum = jsNum;
    }

    public String getJsBidder() {
        return jsBidder;
    }

    public void setJsBidder(String jsBidder) {
        this.jsBidder = jsBidder;
    }

    public Integer getIsToCheck() {
        return isToCheck;
    }

    public void setIsToCheck(Integer isToCheck) {
        this.isToCheck = isToCheck;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }
    public String getKhId() {
        return khId;
    }

    public void setKhId(String khId) {
        this.khId = khId;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("senderProvince", getSenderProvince())
            .append("senderCity", getSenderCity())
            .append("senderArea", getSenderArea())
            .append("shipmentPlace", getShipmentPlace())
            .append("uploadStation", getUploadStation())
            .append("receiveProvince", getReceiveProvince())
            .append("receiveCity", getReceiveCity())
            .append("receiveArea", getReceiveArea())
            .append("receiptPlace", getReceiptPlace())
            .append("dropStation", getDropStation())
            .append("bookingService", getBookingService())
            .append("isPickUp", getIsPickUp())
            .append("isDelivery", getIsDelivery())
            .append("isOrderCustoms", getIsOrderCustoms())
            .append("isClearCustoms", getIsClearCustoms())
            .append("containerBelong", getContainerBelong())
            .append("goodsType", getGoodsType())
            .append("containerType", getContainerType())
            .append("containerNum", getContainerNum())
            .append("packageType", getPackageType())
            .append("isStack", getIsStack())
            .append("totalAmount", getTotalAmount())
            .append("totalWeight", getTotalWeight())
            .append("totalVolume", getTotalVolume())
            .append("inquiryTime", getInquiryTime())
            .append("clientId", getClientId())
            .append("delFlag", getDelFlag())
            .append("lineType", getLineType())
            .append("txAddress", getTxAddress())
            .append("hxAddress", getHxAddress())
            .append("eastOrWest", getEastOrWest())
            .append("uploadStationNum", getUploadStationNum())
            .append("dropStationNum", getDropStationNum())
            .append("inquiryNumber", getInquiryNumber())
            .append("turndownReason", getTurndownReason())
            .append("goodsDetails", getGoodsDetails())
            .append("orderId", getOrderId())
            .append("jsNum", getJsNum())
            .append("khid", getKhId())
            .append("country", getCountry())
            .append("remark", getRemark())
            .append("bookingTimeFlag", getBookingTimeFlag())
            .toString();
    }

    public List<BookingInquiryGoodsDetails> getBookingInquiryGoodsDetailsList() {
        return bookingInquiryGoodsDetailsList;
    }

    public void setBookingInquiryGoodsDetailsList(List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList) {
        this.bookingInquiryGoodsDetailsList = bookingInquiryGoodsDetailsList;
    }

    public String getClientUnit() {
        return clientUnit;
    }

    public void setClientUnit(String clientUnit) {
        this.clientUnit = clientUnit;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getLimitation() {
        return limitation;
    }

    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGoodsFragile() {
        return goodsFragile;
    }

    public void setGoodsFragile(String goodsFragile) {
        this.goodsFragile = goodsFragile;
    }

    public String getInquiryState() {
        return inquiryState;
    }

    public void setInquiryState(String inquiryState) {
        this.inquiryState = inquiryState;
    }


    public String getInquiryStartTime() {
        return inquiryStartTime;
    }

    public void setInquiryStartTime(String inquiryStartTime) {
        this.inquiryStartTime = inquiryStartTime;
    }

    public String getInquiryEndTime() {
        return inquiryEndTime;
    }

    public void setInquiryEndTime(String inquiryEndTime) {
        this.inquiryEndTime = inquiryEndTime;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getHxAddressId() {
        return hxAddressId;
    }

    public void setHxAddressId(String hxAddressId) {
        this.hxAddressId = hxAddressId;
    }

    public String getQgfy() {
        return qgfy;
    }

    public void setQgfy(String qgfy) {
        this.qgfy = qgfy;
    }

    public String getCzfy() {
        return czfy;
    }

    public void setCzfy(String czfy) {
        this.czfy = czfy;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    public String getReceiveProvince() {
        return receiveProvince;
    }

    public void setReceiveProvince(String receiveProvince) {
        this.receiveProvince = receiveProvince;
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity;
    }

    public String getReceiveArea() {
        return receiveArea;
    }

    public void setReceiveArea(String receiveArea) {
        this.receiveArea = receiveArea;
    }

    public String getTurndownReason() {
        return turndownReason;
    }

    public void setTurndownReason(String turndownReason) {
        this.turndownReason = turndownReason;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public String getTxAddress() {
        return txAddress;
    }

    public void setTxAddress(String txAddress) {
        this.txAddress = txAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliverySelfType() {
        return deliverySelfType;
    }

    public void setDeliverySelfType(String deliverySelfType) {
        this.deliverySelfType = deliverySelfType;
    }


    public String getGoodsGeneral() {
        return goodsGeneral;
    }

    public void setGoodsGeneral(String goodsGeneral) {
        this.goodsGeneral = goodsGeneral;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getBookingTimeFlag() {
        return bookingTimeFlag;
    }

    public void setBookingTimeFlag(String bookingTimeFlag) {
        this.bookingTimeFlag = bookingTimeFlag;
    }

    public String getBillableVolume() {
        return billableVolume;
    }

    public void setBillableVolume(String billableVolume) {
        this.billableVolume = billableVolume;
    }

    public String getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }
}
