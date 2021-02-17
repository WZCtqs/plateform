package com.szhbl.project.enquiry.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * 郑亚、郑越去程整柜提货费对象 zg_asia_going_fee
 *
 * @author szhbl
 * @date 2020-09-03
 */
public class ZgAsiaGoingFee extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 线路：0:郑亚，1：郑越
     */
    @Excel(name = "线路(郑越、郑亚)")
    private String lineType;

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

    /**
     * 客户端显示
     */
    private Long pickGoodsShowFee;

    /**
     * 单位
     */
    private String pickUnit;

    /**
     * 箱型
     */
    @Excel(name = "箱型值(多个箱型以、隔开)")
    private String containerType;

    /**
     * 有效开始时间
     */
    @Excel(name = "有效开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date validStartDate;

    /**
     * 有效结束时间
     */
    @Excel(name = "有效截止时间", width = 30, dateFormat = "yyyy-MM-dd")
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

    public void setPickGoodsShowFee(Long pickGoodsShowFee) {
        this.pickGoodsShowFee = pickGoodsShowFee;
    }

    public Long getPickGoodsShowFee() {
        return pickGoodsShowFee;
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

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getLineType() {
        return lineType;
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
                .append("lineType", getLineType())
                .toString();
    }
}
