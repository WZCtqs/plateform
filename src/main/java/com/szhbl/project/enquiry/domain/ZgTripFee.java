package com.szhbl.project.enquiry.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 郑欧线整柜去程费用对象 zg_trip_fee
 * 
 * @author jhm
 * @date 2020-04-01
 */
public class ZgTripFee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 提货城市 */
    @Excel(name = "提货城市")
    private String pickUpCity;

    /** 集货点 */
    @Excel(name = "集货点")
    private String cargoCollectionPoint;

    /** $column.columnComment */
    @Excel(name = "距离")
    private Long distance;

    /** 标准化提货费 */
    @Excel(name = "标准化后的提货费")
    private Long bzhPickUpCharge;

    /** 箱型 */
    @Excel(name = "箱型值(多个箱型以、隔开)")
    private String containerType;

    /** 单位 */
//    @Excel(name = "单位")
    private String pickUnit;


    /** 到货城市 */
    private String arrivalCity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "有效开始时间")
    public Date validStartDate;

    /**
     * 有效结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "有效截止时间")
    public Date validEndDate;

    @JsonFormat(pattern ="yyyy-MM-dd HH:ss:mm",timezone = "GMT+8")
    private Date createTime;
    /**创建开始时间*/
    private String createStartTime;
    /**创建结束时间*/
    private String createEndTime;

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

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPickUpCity(String pickUpCity) 
    {
        this.pickUpCity = pickUpCity;
    }

    public String getPickUpCity() 
    {
        return pickUpCity;
    }
    public void setCargoCollectionPoint(String cargoCollectionPoint) 
    {
        this.cargoCollectionPoint = cargoCollectionPoint;
    }

    public String getCargoCollectionPoint() 
    {
        return cargoCollectionPoint;
    }
    public void setDistance(Long distance) 
    {
        this.distance = distance;
    }

    public Long getDistance() 
    {
        return distance;
    }
    public void setBzhPickUpCharge(Long bzhPickUpCharge) 
    {
        this.bzhPickUpCharge = bzhPickUpCharge;
    }

    public Long getBzhPickUpCharge() 
    {
        return bzhPickUpCharge;
    }
    public void setPickUnit(String pickUnit) 
    {
        this.pickUnit = pickUnit;
    }

    public String getPickUnit() 
    {
        return pickUnit;
    }

    public void setArrivalCity(String arrivalCity) 
    {
        this.arrivalCity = arrivalCity;
    }

    public String getArrivalCity() 
    {
        return arrivalCity;
    }
    public void setContainerType(String containerType) 
    {
        this.containerType = containerType;
    }

    public String getContainerType() 
    {
        return containerType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pickUpCity", getPickUpCity())
            .append("cargoCollectionPoint", getCargoCollectionPoint())
            .append("distance", getDistance())
            .append("bzhPickUpCharge", getBzhPickUpCharge())
            .append("pickUnit", getPickUnit())
            .append("arrivalCity", getArrivalCity())
            .append("containerType", getContainerType())
            .append("validStartDate", getValidStartDate())
            .append("validEndDate", getValidEndDate())
            .toString();
    }
}
