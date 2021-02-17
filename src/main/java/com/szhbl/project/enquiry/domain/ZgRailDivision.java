package com.szhbl.project.enquiry.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 整柜铁路运费对象 zg_rail_division
 *
 * @author jhm
 * @date 2020-03-13
 */
public class ZgRailDivision extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄）） */
    @Excel(name = "线路类型")
    private String lineType;

    /** 上货站点 */
    @Excel(name = "上货站点")
    private String orderUploadSite;

    /** 下货站点 */
    @Excel(name = "下货站点")
    private String orderUnloadSite;

    /** 箱型值 */
    @Excel(name = "箱型值")
    private String containerTypeValue;

    /** 是否自备箱,（0是货主自备箱SOC，1是承运人自备箱COC） */
    @Excel(name = "是否自备箱,", readConverterExp = "0=是货主自备箱SOC，1是承运人自备箱COC")
    private String isContainer;

    /** 铁路运费 */
    @Excel(name = "铁路运费")
    private Long railCost;

    /** 0 整箱 1拼箱（不需要字段） */
    private String isConsolidation;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 箱子类型 */
    @Excel(name = "箱型")
    public String containerType;

    /**
     * 有效开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "有效开始日期")
    public Date validStartDate;


    /**
     * 有效结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "有效结束日期")
    public Date validEndDate;


    /** 去回程,0为去程 1为回程*/
    @Excel(name = "去/回程")
    private String eastOrWest;

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
    public void setContainerTypeValue(String containerTypeValue)
    {
        this.containerTypeValue = containerTypeValue;
    }

    public String getContainerTypeValue()
    {
        return containerTypeValue;
    }
    public void setIsContainer(String isContainer)
    {
        this.isContainer = isContainer;
    }

    public String getIsContainer()
    {
        return isContainer;
    }
    public void setRailCost(Long railCost)
    {
        this.railCost = railCost;
    }

    public Long getRailCost()
    {
        return railCost;
    }
    public void setIsConsolidation(String isConsolidation)
    {
        this.isConsolidation = isConsolidation;
    }

    public String getIsConsolidation()
    {
        return isConsolidation;
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
                .append("containerTypeValue", getContainerTypeValue())
                .append("isContainer", getIsContainer())
                .append("railCost", getRailCost())
                .append("isConsolidation", getIsConsolidation())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
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

    public String getEastOrWest() {
        return eastOrWest;
    }

    public void setEastOrWest(String eastOrWest) {
        this.eastOrWest = eastOrWest;
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
