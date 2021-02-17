package com.szhbl.project.enquiry.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 散货铁路运费对象 sh_rail_division
 * 
 * @author jhm
 * @date 2020-03-14
 */
public class ShRailDivision extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 线路类型（0郑欧、1郑中亚、2郑俄、3郑东盟） */
    @Excel(name = "线路(0郑欧、2中亚、3中越、4中俄)")
    private String lineType;

    /** 上货站点 */
    @Excel(name = "上货站")
    private String orderUploadSite;

    /** 下货站点 */
    @Excel(name = "下货站")
    private String orderUnloadSite;

    /** 箱子类型 */
    public String containerType;

    /** 箱型值 */
    private String containerTypeValue;


    /** 起运站费用（散货  USD/CBM或者（USD/SET）） */
    @Excel(name = "起运站收费（USD/CBM）或者（USD/SET)")
    private Long orderUploadSiteCost;

    /** 散货运费单价,去回程运费都是这个字段 */
    @Excel(name = "散货运费单价")
    private String lclFreight;

    /** 去回程,0为去程 1为回程*/
    @Excel(name = "东/西向(0西向，1东向)")
    private String eastOrWest;

    /** 目的站基础费用（包含拆箱费、基本仓储费 */
    @Excel(name = "目的站基础费用")
    private Long orderUnloadSiteBacost;


    /** 每票固定收费方数小于等于临界值费用（USD/SET */
    @Excel(name = "每票固定收费方数小于等于临界值费用")
    private Long minVolumeCost;

    /** 每票固定收费方数大于最小值不超过最大值费用（USD/SET） */
    @Excel(name = "每票固定收费方数大于最小值不超过最大值费用")
    private Long middleVolumeCost;

    /** 每票固定收费方数大于临界最大值费用（USD/SET */
    @Excel(name = "每票固定收费方数大于临界最大值费用")
    private Long maxVolumeCost;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;


    /** 最小体积 */
    @Excel(name = "最小体积")
    private String minVolume;

    /** 最大体积 */
    @Excel(name = "最大体积")
    private String maxVolume;

    /**
     * 有效开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "有效开始日期",dateFormat = "yyyy-MM-dd")
    public Date validStartDate;


    /**
     * 有效结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "有效结束日期",dateFormat = "yyyy-MM-dd")
    public Date validEndDate;



    /**创建开始时间*/
    private String createStartTime;
    /**创建结束时间*/
    private String createEndTime;


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLineType(String lineType) 
    {
        this.lineType = lineType;
    }

    public String getLineType() 
    {
        return lineType;
    }
    public void setOrderUploadSite(String orderUploadSite) 
    {
        this.orderUploadSite = orderUploadSite;
    }

    public String getOrderUploadSite() 
    {
        return orderUploadSite;
    }
    public void setOrderUnloadSite(String orderUnloadSite) 
    {
        this.orderUnloadSite = orderUnloadSite;
    }

    public String getOrderUnloadSite() 
    {
        return orderUnloadSite;
    }
    public void setLclFreight(String lclFreight) 
    {
        this.lclFreight = lclFreight;
    }

    public String getLclFreight() 
    {
        return lclFreight;
    }
    public void setOrderUploadSiteCost(Long orderUploadSiteCost) 
    {
        this.orderUploadSiteCost = orderUploadSiteCost;
    }

    public Long getOrderUploadSiteCost() 
    {
        return orderUploadSiteCost;
    }
    public void setOrderUnloadSiteBacost(Long orderUnloadSiteBacost) 
    {
        this.orderUnloadSiteBacost = orderUnloadSiteBacost;
    }

    public Long getOrderUnloadSiteBacost() 
    {
        return orderUnloadSiteBacost;
    }
    public void setMinVolume(String minVolume) 
    {
        this.minVolume = minVolume;
    }

    public String getMinVolume() 
    {
        return minVolume;
    }
    public void setMaxVolume(String maxVolume) 
    {
        this.maxVolume = maxVolume;
    }

    public String getMaxVolume() 
    {
        return maxVolume;
    }
    public void setMinVolumeCost(Long minVolumeCost) 
    {
        this.minVolumeCost = minVolumeCost;
    }

    public Long getMinVolumeCost() 
    {
        return minVolumeCost;
    }
    public void setMiddleVolumeCost(Long middleVolumeCost) 
    {
        this.middleVolumeCost = middleVolumeCost;
    }

    public Long getMiddleVolumeCost() 
    {
        return middleVolumeCost;
    }
    public void setMaxVolumeCost(Long maxVolumeCost) 
    {
        this.maxVolumeCost = maxVolumeCost;
    }

    public Long getMaxVolumeCost() 
    {
        return maxVolumeCost;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("lineType", getLineType())
            .append("orderUploadSite", getOrderUploadSite())
            .append("orderUnloadSite", getOrderUnloadSite())
            .append("lclFreight", getLclFreight())
            .append("orderUploadSiteCost", getOrderUploadSiteCost())
            .append("orderUnloadSiteBacost", getOrderUnloadSiteBacost())
            .append("minVolume", getMinVolume())
            .append("maxVolume", getMaxVolume())
            .append("minVolumeCost", getMinVolumeCost())
            .append("middleVolumeCost", getMiddleVolumeCost())
            .append("maxVolumeCost", getMaxVolumeCost())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
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

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getEastOrWest() {
        return eastOrWest;
    }

    public void setEastOrWest(String eastOrWest) {
        this.eastOrWest = eastOrWest;
    }

    public String getContainerTypeValue() {
        return containerTypeValue;
    }

    public void setContainerTypeValue(String containerTypeValue) {
        this.containerTypeValue = containerTypeValue;
    }

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
}
