package com.szhbl.project.documentcenter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单—单证（订单所需单证）对象 doc_order
 *
 * @author hp
 * @date 2020-04-13
 */
@Data
public class DocOrder implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单id */
    private String orderId;

    /** 订单编号 */
    private String orderNumber;

    /** 班列日期 */
    private Date classDate;

    /** 托书审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date auditTime;

    /** 所需单证 */
    private String fileTypeKey;

    /** 所需单证 */
    private String fileTypeText;

    /**
     * 理论提供时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date normalSupplyNode;

    private Date actualSupplyTime;

    private Integer actualSupply;

    private Integer emailStatus;

    /**
     * 数据权限查询条件
     */
    private String tjr;
    private String tjrId;
    private Long userid;
    private String deptCode;
    public String readType;//0，按对应部门编号精准查询,1,2,3,4,5,6

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("orderNumber", getOrderNumber())
                .append("classDate", getClassDate())
                .append("auditTime", getAuditTime())
                .append("fileTypeKey", getFileTypeKey())
                .append("fileTypeText", getFileTypeText())
            .append("normalSupplyNode", getNormalSupplyNode())
            .toString();
    }
}
