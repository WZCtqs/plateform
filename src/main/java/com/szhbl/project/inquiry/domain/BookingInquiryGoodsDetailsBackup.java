package com.szhbl.project.inquiry.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

/**
 * 订舱询价-拼箱货物详情草稿对象 booking_inquiry_goods_details_backup
 *
 * @author szhbl
 * @date 2020-07-10
 */
public class BookingInquiryGoodsDetailsBackup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 询价id
     */
    @Excel(name = "询价id")
    private Long inquiryId;

    /**
     * 货物品名
     */
    @Excel(name = "货物品名")
    private String goodsName;

    /**
     * 货物数量
     */
    @Excel(name = "货物数量")
    private String goodsAmount;

    /**
     * 货物重量
     */
    @Excel(name = "货物重量")
    private String goodsWeight;

    /**
     * 长(mm)
     */
    @Excel(name = "长(mm)")
    private String goodsLength;

    /**
     * 宽(mm)
     */
    @Excel(name = "宽(mm)")
    private String goodsWidth;

    /**
     * 高(mm)
     */
    @Excel(name = "高(mm)")
    private String goodsHeight;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setInquiryId(Long inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsWeight(String goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsLength(String goodsLength) {
        this.goodsLength = goodsLength;
    }

    public String getGoodsLength() {
        return goodsLength;
    }

    public void setGoodsWidth(String goodsWidth) {
        this.goodsWidth = goodsWidth;
    }

    public String getGoodsWidth() {
        return goodsWidth;
    }

    public void setGoodsHeight(String goodsHeight) {
        this.goodsHeight = goodsHeight;
    }

    public String getGoodsHeight() {
        return goodsHeight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("inquiryId", getInquiryId())
                .append("goodsName", getGoodsName())
                .append("goodsAmount", getGoodsAmount())
                .append("goodsWeight", getGoodsWeight())
                .append("goodsLength", getGoodsLength())
                .append("goodsWidth", getGoodsWidth())
                .append("goodsHeight", getGoodsHeight())
                .toString();
    }
}
