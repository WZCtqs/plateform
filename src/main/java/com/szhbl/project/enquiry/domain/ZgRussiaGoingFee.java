package com.szhbl.project.enquiry.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 俄线提货费对象 zg_russia_going_fee
 *
 * @author szhbl
 * @date 2020-07-10
 */
public class ZgRussiaGoingFee extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 提货城市
     */
    @Excel(name = "提货城市")
    private String pickUpCity;

    /**
     * 集货地
     */
    @Excel(name = "集货点")
    private String cargoCollectionPoint;

    /**
     * 距离
     */
    @Excel(name = "距离")
    private Long distance;

    /**
     * 提货费
     */
    @Excel(name = "提货费")
    private Long pickGoodsFee;

    //    @Excel(name = "显示提货费")
    private Long pickGoodsShowFee;

    /**
     * 单位
     */
//    @Excel(name = "单位")
    private String pickUnit;

    /**
     * 箱型
     */
    @Excel(name = "箱型值(多个箱型以、隔开)")
    private String containerType;

    /**
     * 有效开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "有效开始时间")
    private Date validStartDate;

    /**
     * 有效结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "有效截止时间")
    private Date validEndDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPickUpCity(String pickUpCity) {
        this.pickUpCity = pickUpCity;
    }

    public String getPickUpCity() {
        return pickUpCity;
    }

    public void setCargoCollectionPoint(String cargoCollectionPoint) {
        this.cargoCollectionPoint = cargoCollectionPoint;
    }

    public String getCargoCollectionPoint() {
        return cargoCollectionPoint;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getDistance() {
        return distance;
    }

    public void setPickGoodsFee(Long pickGoodsFee) {
        this.pickGoodsFee = pickGoodsFee;
    }

    public Long getPickGoodsFee() {
        return pickGoodsFee;
    }

    public Long getPickGoodsShowFee() {
        return pickGoodsShowFee;
    }

    public void setPickGoodsShowFee(Long pickGoodsShowFee) {
        this.pickGoodsShowFee = pickGoodsShowFee;
    }

    public void setPickUnit(String pickUnit) {
        this.pickUnit = pickUnit;
    }

    public String getPickUnit() {
        return pickUnit;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setValidStartDate(Date validStartDate) {
        this.validStartDate = validStartDate;
    }

    public Date getValidStartDate() {
        return validStartDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("pickUpCity", getPickUpCity())
                .append("cargoCollectionPoint", getCargoCollectionPoint())
                .append("distance", getDistance())
                .append("pickGoodsFee", getPickGoodsFee())
                .append("pickGoodsShowFee", getPickGoodsShowFee())
                .append("pickUnit", getPickUnit())
                .append("containerType", getContainerType())
                .append("validStartDate", getValidStartDate())
                .append("validEndDate", getValidEndDate())
                .append("createTime", getCreateTime())
                .toString();
    }
}
