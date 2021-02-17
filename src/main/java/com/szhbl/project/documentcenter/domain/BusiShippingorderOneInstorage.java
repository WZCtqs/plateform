package com.szhbl.project.documentcenter.domain;

import lombok.Data;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 【请填写功能名称】对象 busi_shippingorder_one_instorage
 *
 * @author szhbl
 * @date 2020-01-11
 */
@Data
public class BusiShippingorderOneInstorage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer inId;

    /** orderid */
    @Excel(name = "orderid")
    private String orderId;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String classId;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String inIsdocument;

    /** $column.columnComment */
    @Excel(name = "orderid", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inDatenode;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String inSite;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String inContacts;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String inPhone;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String inDept;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String inDumptel;

    /** $column.columnComment */
    @Excel(name = "orderid", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inDumpjobtime;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String inCw;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String issend;

    /** $column.columnComment */
    @Excel(name = "orderid", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String createuserid;

    /** $column.columnComment */
    @Excel(name = "orderid")
    private String createusername;

}
