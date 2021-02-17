package com.szhbl.project.enquiry.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 郑欧整柜回程送货费用对象 zg_return_trip_fee
 * 
 * @author jhm
 * @date 2020-04-02
 */
public class ZgReturnTripFee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /**
     * 货源地（收货地），默认发货地郑州
     */
    @Excel(name = "货源地城市")
    private String receiptPlace;

    /** 还箱地 */
    @Excel(name = "还箱地")
    private String cityTrainStation;

    /** 还箱费 */
    private String returnBoxAddress;

    /** 距离 */
    @Excel(name = "百度地图距离")
    private Long distance;

    /** 送货费 */
    @Excel(name = "送货费")
    private Long deliveryFee;

    /** 白卡送货费 */
    @Excel(name = "白卡送货费")
    private Long whiteCardDeliveryFee;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "有效开始时间")
    public Date validStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "有效截止时间")
    public Date validEndDate;

    /**创建开始时间*/
    private String createStartTime;
    /**创建结束时间*/
    private String createEndTime;

    /** 国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车*/
    private String truckType;

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(Date validStartDate) {
        this.validStartDate = validStartDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }




    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCityTrainStation(String cityTrainStation) 
    {
        this.cityTrainStation = cityTrainStation;
    }

    public String getCityTrainStation() 
    {
        return cityTrainStation;
    }
    public void setReturnBoxAddress(String returnBoxAddress) 
    {
        this.returnBoxAddress = returnBoxAddress;
    }

    public String getReturnBoxAddress() 
    {
        return returnBoxAddress;
    }
    public void setDistance(Long distance) 
    {
        this.distance = distance;
    }

    public Long getDistance() 
    {
        return distance;
    }
    public void setDeliveryFee(Long deliveryFee) 
    {
        this.deliveryFee = deliveryFee;
    }

    public Long getDeliveryFee() 
    {
        return deliveryFee;
    }
    public void setWhiteCardDeliveryFee(Long whiteCardDeliveryFee) 
    {
        this.whiteCardDeliveryFee = whiteCardDeliveryFee;
    }

    public Long getWhiteCardDeliveryFee() 
    {
        return whiteCardDeliveryFee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("province", getProvince())
            .append("cityTrainStation", getCityTrainStation())
            .append("returnBoxAddress", getReturnBoxAddress())
            .append("distance", getDistance())
            .append("deliveryFee", getDeliveryFee())
            .append("whiteCardDeliveryFee", getWhiteCardDeliveryFee())
            .append("validStartDate", getValidStartDate())
            .append("validEndDate", getValidEndDate())
            .toString();
    }

    public String getReceiptPlace() {
        return receiptPlace;
    }

    public void setReceiptPlace(String receiptPlace) {
        this.receiptPlace = receiptPlace;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }
}
