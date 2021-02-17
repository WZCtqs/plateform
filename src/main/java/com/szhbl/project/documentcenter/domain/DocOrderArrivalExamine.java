package com.szhbl.project.documentcenter.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 关检到货审核表对象 doc_order_arrival_examine
 *
 * @author szhbl
 * @date 2020-08-17
 */
@Data
public class DocOrderArrivalExamine extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
//    private Long id;

    /**
     * orderid
     */
    @Excel(name = "orderid")
    private String orderId;

    /**
     * 委托书编号
     */
    @Excel(name = "委托书编号")
    private String ordernumber;

    /**
     * 审核人、负责人
     */
    @Excel(name = "审核人、负责人")
    private String Areviewer;

    /**
     * 审核通过时间
     */
    private String AuditTime;

    /**
     * 单据审核情况
     */
    @Excel(name = "单据审核情况")
    private String AuditSituation;

    /**
     * 到货情况
     */
    @Excel(name = "到货情况")
    private String ArrivalSituation;

    /**
     * 审核到货情况
     */
    @Excel(name = "审核到货情况")
    private String AuditArrivalSituation;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String CreateUser;


    private Date CreateTime;

    /**
     * 测量尺寸
     */
    @Excel(name = "测量尺寸")
    private String Measurement;

    private String Remark;

    private String type;

    @Override
    public String toString() {
        return "DocOrderArrivalExamine{" +
                ", orderId='" + orderId + '\'' +
                ", ordernumber='" + ordernumber + '\'' +
                ", Areviewer='" + Areviewer + '\'' +
                ", AuditTime=" + AuditTime +
                ", AuditSituation='" + AuditSituation + '\'' +
                ", ArrivalSituation='" + ArrivalSituation + '\'' +
                ", AuditArrivalSituation='" + AuditArrivalSituation + '\'' +
                ", CreateUser='" + CreateUser + '\'' +
                ", Measurement='" + Measurement + '\'' +
                ", Remark='" + Remark + '\'' +
                '}';
    }
}
