package com.szhbl.project.order.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 排舱托书信息对象 busi_zy_order
 * 
 * @author dps
 * @date 2020-08-20
 */
@Data
public class BusiZyOrder
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 托书id */
    @Excel(name = "托书id")
    private String orderId;

    /** 报关负责人 */
    @Excel(name = "报关负责人")
    private String bgPrincipal;

    /** 托书报关票数 */
    @Excel(name = "托书报关票数")
    private String bgVotesNumber;

    /** 托书报关状态 */
    @Excel(name = "托书报关状态")
    private String bgProgress;
    private String bgProgressS;

    /** 托书报关备注 */
    @Excel(name = "托书报关备注")
    private String bgRemark;
    private String bgRemarkS;

    /** 随车负责人 */
    @Excel(name = "随车负责人")
    private String scPrincipal;

    /** 托书随车备注 */
    @Excel(name = "托书随车备注")
    private String scremark;

    private Date createTime;

    private String createBy;
}
