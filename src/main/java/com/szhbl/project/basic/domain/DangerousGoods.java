package com.szhbl.project.basic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 危险品对象 busi_shippingorder_dangerous_goods
 * 
 * @author dps
 * @date 2020-01-15
 */
public class DangerousGoods
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String goodsId;

    /** 物品名称中文 */
    @Excel(name = "物品名称")
    @ApiModelProperty(value = "物品名称")
    @NotNull(message = "物品名称不能为空")
    private String goodsName;

    /** 物品名称英文 */
    @Excel(name = "物品名称英文")
    private String goodsEnName;

    /** 报关hs编码 */
    @Excel(name = "报关hs编码")
    private String goodsReport;

    /** 清关hs编码 */
    @Excel(name = "清关hs编码")
    private String goodsClearance;

    /** 特殊备注 */
    @Excel(name = "特殊备注")
    private String specialremark;
    /** 特殊备注 */
    @Excel(name = "特殊备注")
    private String specialEnRemark;

    /** 等级 0危险品 1疑似危险品 */
    @Excel(name = "等级 0危险品 1疑似危险品")
    private String noteLevel;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

    public void setGoodsId(String goodsId) 
    {
        this.goodsId = goodsId;
    }

    public String getGoodsId() 
    {
        return goodsId;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }
    public String getGoodsName() 
    {
        return goodsName;
    }

    public void setGoodsEnName(String goodsEnName) 
    {
        this.goodsEnName = goodsEnName;
    }
    public String getGoodsEnName() 
    {
        return goodsEnName;
    }

    public void setGoodsReport(String goodsReport) {
        this.goodsReport = goodsReport;
    }
    public String getGoodsReport() {
        return goodsReport;
    }

    public void setGoodsClearance(String goodsClearance) {
        this.goodsClearance = goodsClearance;
    }
    public String getGoodsClearance() {
        return goodsClearance;
    }

    public void setSpecialremark(String specialremark)
    {
        this.specialremark = specialremark;
    }
    public String getSpecialremark()
    {
        return specialremark;
    }

    public void setNoteLevel(String noteLevel)
    {
        this.noteLevel = noteLevel;
    }
    public String getNoteLevel()
    {
        return noteLevel;
    }

    public void setCreatedate(Date createdate) 
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }

    public String getSpecialEnRemark() {
        return specialEnRemark;
    }

    public void setSpecialEnRemark(String specialEnRemark) {
        this.specialEnRemark = specialEnRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("goodsId", getGoodsId())
            .append("goodsName", getGoodsName())
            .append("goodsEnName", getGoodsEnName())
            .append("goodsReport", getGoodsReport())
            .append("goodsClearance", getGoodsClearance())
            .append("specialremark", getSpecialremark())
            .append("specialEnRemark", getSpecialEnRemark())
            .append("noteLevel", getNoteLevel())
            .append("createdate", getCreatedate())
            .toString();
    }
}
