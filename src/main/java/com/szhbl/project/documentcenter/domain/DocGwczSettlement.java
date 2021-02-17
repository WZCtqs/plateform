package com.szhbl.project.documentcenter.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

/**
 * gwczSettlement对象 doc_gwcz_settlement
 *
 * @author szhbl
 * @date 2020-12-07
 */
public class DocGwczSettlement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 托书id
     */
    @Excel(name = "托书id")
    private String orderId;

    /**
     * 托书编号
     */
    @Excel(name = "托书编号")
    private String orderNumber;

    /**
     * 箱号
     */
    @Excel(name = "箱号")
    private String containerNo;

    /**
     * 拼箱体积
     */
    @Excel(name = "拼箱体积")
    private Double pxVolume;

    /**
     * 拼箱计费体积
     */
    @Excel(name = "拼箱计费体积")
    private Double pxSettlementVolume;

    /**
     * 拼箱重量
     */
    @Excel(name = "拼箱重量")
    private Double pxWeight;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setPxVolume(Double pxVolume) {
        this.pxVolume = pxVolume;
    }

    public Double getPxVolume() {
        return pxVolume;
    }

    public void setPxSettlementVolume(Double pxSettlementVolume) {
        this.pxSettlementVolume = pxSettlementVolume;
    }

    public Double getPxSettlementVolume() {
        return pxSettlementVolume;
    }

    public void setPxWeight(Double pxWeight) {
        this.pxWeight = pxWeight;
    }

    public Double getPxWeight() {
        return pxWeight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("orderNumber", getOrderNumber())
                .append("containerNo", getContainerNo())
                .append("pxVolume", getPxVolume())
                .append("pxSettlementVolume", getPxSettlementVolume())
                .append("pxWeight", getPxWeight())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
