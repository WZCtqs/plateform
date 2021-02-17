package com.szhbl.project.inquiry.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.common.annotation.PropertyMsg;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 询价反馈结果数据对象 booking_inquiry_result
 * 
 * @author jhm
 * @date 2020-04-08
 */
public class BookingInquiryResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 询价反馈结果id */
    private Long id;

    /** 询价id */
    @Excel(name = "询价id")
    private Long inquiryId;

    /** 上货站 */
    @Excel(name = "上货站")
    private String uploadStation;

    /** 下货站 */
    @Excel(name = "下货站")
    private String dropStation;

    /** 上货站备注 */
    @Excel(name = "上货站备注")
    private String uploadStationRemark;

    /** 下货站备注 */
    @Excel(name = "下货站备注")
    private String dropStationRemark;

    /** 提货地 */
    @Excel(name = "提货地")
    private String pickUpAddress;

    /** 提货距离 */
    @Excel(name = "提货距离")
    private String pickUpDistance;

    /**
     * 提货时效
     */
    @Excel(name = "提货时效")
    private String pickUpAging;

    /**
     * 提货费
     */
    @PropertyMsg("提货费")
    @Excel(name = "提货费")
    private String pickUpFees;

    /* 针对俄线去程整柜40HC、40GP展示客户提货费*/
    private String pickUpShowClientFees;

    /**
     * 提货费币种
     */
    @Excel(name = "提货费币种")
    private String pickUpCurrencyType;

    /**
     * 提货费有效期
     */
    @Excel(name = "提货费有效期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pickUpExpiration;

    /**
     * 提货备注
     */
    @Excel(name = "提货备注")
    private String pickUpRemark;

    /** 提货报价反馈时间 */
    @Excel(name = "提货报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pickUpFeedbackTime;

    /** 铁路运费 */
    @PropertyMsg("铁路运费")
    @Excel(name = "铁路运费")
    private String railwayFees;

    /** 铁路运费币种 */
    @Excel(name = "铁路运费币种")
    private String railwayCurrencyType;

    /** 铁路收费标准 */
    @Excel(name = "铁路收费标准")
    private String railwayCharges;

    /** 铁路费用有效期 */
    @Excel(name = "铁路费用有效期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date railwayExpiration;

    /** 铁路时效 */
    @Excel(name = "铁路时效")
    private String railwayAging;

    /** 铁路备注 */
    @Excel(name = "铁路备注")
    private String railwayRemark;

    /** 铁路报价反馈时间 */
    @Excel(name = "铁路报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date railwayFeedbackTime;

    /** 派送距离 */
    @Excel(name = "派送距离")
    private String deliveryDistance;

    /** 派送地 */
    @Excel(name = "派送地")
    private String deliveryAddress;

    /** 派送费用 */
    @PropertyMsg("派送费")
    @Excel(name = "派送费用")
    private String deliveryFees;

    /** 派送费用币种 */
    @Excel(name = "派送费用币种")
    private String deliveryCurrencyType;

    /** 派送时效 */
    @Excel(name = "派送时效")
    private String deliveryAging;

    /** 派送报价有效期 */
    @Excel(name = "派送报价有效期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date deliveryExpiration;

    /** 派送备注 */
    @Excel(name = "派送备注")
    private String deliveryRemark;

    /** 派送报价反馈时间 */
    @Excel(name = "派送报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryFeedbackTime;

    /** 删除标识（0未删除；1已删除） */
    private String delFlag;

    /** 确认标识（0未确认；1已确认） */
    @Excel(name = "确认标识", readConverterExp = "0=未确认；1已确认")
    private String confirmFlag;

    /** 确认时间 */
    @Excel(name = "确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmTime;

    /** 确认人 */
    @Excel(name = "确认人")
    private Long confirmUser;

    private BookingInquiry bookingInquiry;

    /** 集疏备注*/
    private String jsRemark;
    /** 集疏备注key值（转换中英文用）*/
    private String note;

    /** 提箱费 */
    @Excel(name = "提箱费")
    private String pickUpBoxFee;

    /**
     * 提箱费有效期
     */
    private Date pickUpBoxExpiration;

    /** 还箱费 */
    @Excel(name = "还箱费")
    private String returnBoxFee;

    /**
     * 还箱费有效期
     */
    private Date returnBoxExpiration;

    /** 理货费 */
    @Excel(name = "理货费")
    private String lhuFee;

    /** 超长超重费 */
    @Excel(name = "超长超重费")
    private String ccczFee;

    /** 提空箱费 */
    @Excel(name = "提空箱费")
    private String tkxFee;

    /** 加固费 */
    @Excel(name = "加固费")
    private String jgFee;

    /** 打托费 */
    @Excel(name = "打托费")
    private String dtFee;

    /** 装拆箱费 */
    @Excel(name = "装拆箱费")
    private String zcxFee;

    /** 总价 */
    private String totalFees;

    /** 箱行亚欧报价编码*/
    private String inquiryNumber;

    /** 集疏报价编码*/
    private String inquiryNum;

   /**询价状态 enquiry_state"=1（报价中）、2（待审核）、3（已报价)*/
   private String enquiryState;

    /** 去程还箱地 */
    @PropertyMsg("还箱地")
    private String hxAddress;

    /** 回程提箱地 */
    @PropertyMsg("提箱地")
    private String txAddress;

    /**铁路运费备注*/
    private String railRemark;

    /** 集疏报价人*/
    private String jsBidder;

    /** 消息队列集疏报价人*/
    private String bidder;

    /** 正常0，驳回1*/
    private String isNormal;

    /**箱型亚欧备注*/
    private String xxyoRemark;

    /**业务备注*/
    private String businessRemark;

    /** 询价id    again_inquiry_id*/
   /* private Long againInquiryId;

    public Long getAgainInquiryId() { return againInquiryId; }

    public void setAgainInquiryId(Long againInquiryId) { this.againInquiryId = againInquiryId; }*/
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInquiryId(Long inquiryId) 
    {
        this.inquiryId = inquiryId;
    }

    public Long getInquiryId() 
    {
        return inquiryId;
    }
    public void setUploadStation(String uploadStation) 
    {
        this.uploadStation = uploadStation;
    }

    public String getUploadStation() 
    {
        return uploadStation;
    }
    public void setDropStation(String dropStation) 
    {
        this.dropStation = dropStation;
    }

    public String getDropStation() 
    {
        return dropStation;
    }
    public void setUploadStationRemark(String uploadStationRemark) 
    {
        this.uploadStationRemark = uploadStationRemark;
    }

    public String getUploadStationRemark() 
    {
        return uploadStationRemark;
    }
    public void setDropStationRemark(String dropStationRemark) 
    {
        this.dropStationRemark = dropStationRemark;
    }

    public String getDropStationRemark() 
    {
        return dropStationRemark;
    }
    public void setPickUpAddress(String pickUpAddress) 
    {
        this.pickUpAddress = pickUpAddress;
    }

    public String getPickUpAddress() 
    {
        return pickUpAddress;
    }
    public void setPickUpDistance(String pickUpDistance) 
    {
        this.pickUpDistance = pickUpDistance;
    }

    public String getPickUpDistance() 
    {
        return pickUpDistance;
    }
    public void setPickUpAging(String pickUpAging) 
    {
        this.pickUpAging = pickUpAging;
    }

    public String getPickUpAging() {
        return pickUpAging;
    }

    public void setPickUpFees(String pickUpFees) {
        this.pickUpFees = pickUpFees;
    }

    public String getPickUpFees() {
        return pickUpFees;
    }

    public String getPickUpShowClientFees() {
        return pickUpShowClientFees;
    }

    public void setPickUpShowClientFees(String pickUpShowClientFees) {
        this.pickUpShowClientFees = pickUpShowClientFees;
    }

    public void setPickUpCurrencyType(String pickUpCurrencyType) {
        this.pickUpCurrencyType = pickUpCurrencyType;
    }

    public String getPickUpCurrencyType() 
    {
        return pickUpCurrencyType;
    }
    public void setPickUpExpiration(Date pickUpExpiration) 
    {
        this.pickUpExpiration = pickUpExpiration;
    }

    public Date getPickUpExpiration() 
    {
        return pickUpExpiration;
    }
    public void setPickUpRemark(String pickUpRemark) 
    {
        this.pickUpRemark = pickUpRemark;
    }

    public String getPickUpRemark() 
    {
        return pickUpRemark;
    }
    public void setPickUpFeedbackTime(Date pickUpFeedbackTime) 
    {
        this.pickUpFeedbackTime = pickUpFeedbackTime;
    }

    public Date getPickUpFeedbackTime() 
    {
        return pickUpFeedbackTime;
    }
    public void setRailwayFees(String railwayFees) 
    {
        this.railwayFees = railwayFees;
    }

    public String getRailwayFees() 
    {
        return railwayFees;
    }
    public void setRailwayCurrencyType(String railwayCurrencyType) 
    {
        this.railwayCurrencyType = railwayCurrencyType;
    }

    public String getRailwayCurrencyType() 
    {
        return railwayCurrencyType;
    }
    public void setRailwayCharges(String railwayCharges) 
    {
        this.railwayCharges = railwayCharges;
    }

    public String getRailwayCharges() 
    {
        return railwayCharges;
    }
    public void setRailwayExpiration(Date railwayExpiration) 
    {
        this.railwayExpiration = railwayExpiration;
    }

    public Date getRailwayExpiration() 
    {
        return railwayExpiration;
    }
    public void setRailwayAging(String railwayAging) 
    {
        this.railwayAging = railwayAging;
    }

    public String getRailwayAging() 
    {
        return railwayAging;
    }
    public void setRailwayRemark(String railwayRemark) 
    {
        this.railwayRemark = railwayRemark;
    }

    public String getRailwayRemark() 
    {
        return railwayRemark;
    }
    public void setRailwayFeedbackTime(Date railwayFeedbackTime) 
    {
        this.railwayFeedbackTime = railwayFeedbackTime;
    }

    public Date getRailwayFeedbackTime() 
    {
        return railwayFeedbackTime;
    }
    public void setDeliveryDistance(String deliveryDistance) 
    {
        this.deliveryDistance = deliveryDistance;
    }

    public String getDeliveryDistance() 
    {
        return deliveryDistance;
    }
    public void setDeliveryFees(String deliveryFees) 
    {
        this.deliveryFees = deliveryFees;
    }

    public String getDeliveryFees() 
    {
        return deliveryFees;
    }
    public void setDeliveryCurrencyType(String deliveryCurrencyType) 
    {
        this.deliveryCurrencyType = deliveryCurrencyType;
    }

    public String getDeliveryCurrencyType() 
    {
        return deliveryCurrencyType;
    }
    public void setDeliveryAging(String deliveryAging) 
    {
        this.deliveryAging = deliveryAging;
    }

    public String getDeliveryAging() 
    {
        return deliveryAging;
    }
    public void setDeliveryExpiration(Date deliveryExpiration) 
    {
        this.deliveryExpiration = deliveryExpiration;
    }

    public Date getDeliveryExpiration() 
    {
        return deliveryExpiration;
    }
    public void setDeliveryRemark(String deliveryRemark) 
    {
        this.deliveryRemark = deliveryRemark;
    }

    public String getDeliveryRemark() 
    {
        return deliveryRemark;
    }
    public void setDeliveryFeedbackTime(Date deliveryFeedbackTime) 
    {
        this.deliveryFeedbackTime = deliveryFeedbackTime;
    }

    public Date getDeliveryFeedbackTime() 
    {
        return deliveryFeedbackTime;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setConfirmFlag(String confirmFlag) 
    {
        this.confirmFlag = confirmFlag;
    }

    public String getConfirmFlag() 
    {
        return confirmFlag;
    }
    public void setConfirmTime(Date confirmTime) 
    {
        this.confirmTime = confirmTime;
    }

    public Date getConfirmTime() 
    {
        return confirmTime;
    }
    public void setConfirmUser(Long confirmUser) 
    {
        this.confirmUser = confirmUser;
    }

    public Long getConfirmUser() 
    {
        return confirmUser;
    }

    public String getJsBidder() { return jsBidder; }

    public void setJsBidder(String jsBidder) { this.jsBidder = jsBidder; }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inquiryId", getInquiryId())
            .append("uploadStation", getUploadStation())
            .append("dropStation", getDropStation())
            .append("uploadStationRemark", getUploadStationRemark())
            .append("dropStationRemark", getDropStationRemark())
            .append("pickUpAddress", getPickUpAddress())
            .append("pickUpDistance", getPickUpDistance())
            .append("pickUpAging", getPickUpAging())
            .append("pickUpFees", getPickUpFees())
            .append("pickUpCurrencyType", getPickUpCurrencyType())
            .append("pickUpExpiration", getPickUpExpiration())
            .append("pickUpRemark", getPickUpRemark())
            .append("pickUpFeedbackTime", getPickUpFeedbackTime())
            .append("railwayFees", getRailwayFees())
            .append("railwayCurrencyType", getRailwayCurrencyType())
            .append("railwayCharges", getRailwayCharges())
            .append("railwayExpiration", getRailwayExpiration())
            .append("railwayAging", getRailwayAging())
            .append("railwayRemark", getRailwayRemark())
            .append("railwayFeedbackTime", getRailwayFeedbackTime())
            .append("deliveryDistance", getDeliveryDistance())
            .append("deliveryFees", getDeliveryFees())
            .append("deliveryCurrencyType", getDeliveryCurrencyType())
            .append("deliveryAging", getDeliveryAging())
            .append("deliveryExpiration", getDeliveryExpiration())
            .append("deliveryRemark", getDeliveryRemark())
            .append("deliveryFeedbackTime", getDeliveryFeedbackTime())
            .append("delFlag", getDelFlag())
            .append("confirmFlag", getConfirmFlag())
            .append("confirmTime", getConfirmTime())
            .append("confirmUser", getConfirmUser())
            .append("jsBidder", getJsBidder())
            //.append("againInquiryId", getAgainInquiryId())
            .toString();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public BookingInquiry getBookingInquiry() {
        return bookingInquiry;
    }

    public void setBookingInquiry(BookingInquiry bookingInquiry) {
        this.bookingInquiry = bookingInquiry;
    }


    public String getJsRemark() {
        return jsRemark;
    }

    public void setJsRemark(String jsRemark) {
        this.jsRemark = jsRemark;
    }

    public String getReturnBoxFee() {
        return returnBoxFee;
    }

    public void setReturnBoxFee(String returnBoxFee) {
        this.returnBoxFee = returnBoxFee;
    }

    public String getLhuFee() {
        return lhuFee;
    }

    public void setLhuFee(String lhuFee) {
        this.lhuFee = lhuFee;
    }

    public String getCcczFee() {
        return ccczFee;
    }

    public void setCcczFee(String ccczFee) {
        this.ccczFee = ccczFee;
    }

    public String getTkxFee() {
        return tkxFee;
    }

    public void setTkxFee(String tkxFee) {
        this.tkxFee = tkxFee;
    }

    public String getJgFee() {
        return jgFee;
    }

    public void setJgFee(String jgFee) {
        this.jgFee = jgFee;
    }

    public String getDtFee() {
        return dtFee;
    }

    public void setDtFee(String dtFee) {
        this.dtFee = dtFee;
    }

    public String getZcxFee() {
        return zcxFee;
    }

    public void setZcxFee(String zcxFee) {
        this.zcxFee = zcxFee;
    }

    public String getInquiryNum() {
        return inquiryNum;
    }

    public void setInquiryNum(String inquiryNum) {
        this.inquiryNum = inquiryNum;
    }

    public String getEnquiryState() {
        return enquiryState;
    }

    public void setEnquiryState(String enquiryState) {
        this.enquiryState = enquiryState;
    }


    public String getHxAddress() {
        return hxAddress;
    }

    public void setHxAddress(String hxAddress) {
        this.hxAddress = hxAddress;
    }

    public String getTxAddress() {
        return txAddress;
    }

    public void setTxAddress(String txAddress) {
        this.txAddress = txAddress;
    }

    public String getRailRemark() {
        return railRemark;
    }

    public void setRailRemark(String railRemark) {
        this.railRemark = railRemark;
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal;
    }

    public String getXxyoRemark() {
        return xxyoRemark;
    }

    public void setXxyoRemark(String xxyoRemark) {
        this.xxyoRemark = xxyoRemark;
    }

    public String getBusinessRemark() {
        return businessRemark;
    }

    public void setBusinessRemark(String businessRemark) {
        this.businessRemark = businessRemark;
    }

    public String getPickUpBoxFee() {
        return pickUpBoxFee;
    }

    public void setPickUpBoxFee(String pickUpBoxFee) {
        this.pickUpBoxFee = pickUpBoxFee;
    }

    public String getInquiryNumber() {
        return inquiryNumber;
    }

    public void setInquiryNumber(String inquiryNumber) {
        this.inquiryNumber = inquiryNumber;
    }

    public String getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(String totalFees) {
        this.totalFees = totalFees;
    }

    public Date getPickUpBoxExpiration() {
        return pickUpBoxExpiration;
    }

    public void setPickUpBoxExpiration(Date pickUpBoxExpiration) {
        this.pickUpBoxExpiration = pickUpBoxExpiration;
    }

    public Date getReturnBoxExpiration() {
        return returnBoxExpiration;
    }

    public void setReturnBoxExpiration(Date returnBoxExpiration) {
        this.returnBoxExpiration = returnBoxExpiration;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
