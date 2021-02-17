package com.szhbl.project.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 赔款跟踪对象 compensation_tracking
 * 
 * @author bxt
 * @date 2020-03-30
 */
public class CompensationTracking extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    @Excel(name = "业务部")
    private String ywb;
    /** 客户推荐人 */
    private String clientTjr;

    /** 客户推荐人id */
    private String clientTjrId;

    /** 总助 */
    @Excel(name = "负责人")
    private String general;

    /** 投诉日期 */
    @Excel(name = "投诉日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date complaintDate;

    /** 舱位号 */
    @Excel(name = "舱位号")
    private String orderNumber;

    /** 订单id */

    private String orderId;

    /** 订舱方名称 */
    @Excel(name = "客户/合作方名称")
    private String clientUnit;

    /** 结案时间 */
    @Excel(name = "结案时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;



    /** 赔偿我司金额 */
    @Excel(name = "赔偿我司金额")
    private String income;

    /** 我司赔偿金额 */
    @Excel(name = "我司赔偿金额")
    private String expenditure;

    /** 结算情况跟踪 */
    @Excel(name = "结算情况跟踪")
    private String settlementSituation;

    /** $column.columnComment */
    @Excel(name = "折算人民币")
    private String rmb;

    /** 向客户索赔 */
    @Excel(name = "向客户索赔")
    private String demage;
    /** 投诉类型:0货损1包装破损2货少3延期4费用争议5操作失误6其他 */
    @Excel(name = "赔款类型")
    private String complaintsType;
    /** 责任部门 */
    @Excel(name = "责任部门")
    private String department;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;


    /** 运费支付0是1否 */

    private String freightPay;
    /** 是否投诉过期0是1否 */

    private String isOverdue;

    @Excel(name = "备注")
    private String remark;

    /**
     * 数据权限查询条件
     */
    private String deptCode;
    private String readType;
    private String userId;

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getYwb() {
        return ywb;
    }

    public void setYwb(String ywb) {
        this.ywb = ywb;
    }

    public String getFreightPay() {
        return freightPay;
    }

    public void setFreightPay(String freightPay) {
        this.freightPay = freightPay;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClientTjr(String clientTjr) 
    {
        this.clientTjr = clientTjr;
    }

    public String getClientTjr() 
    {
        return clientTjr;
    }
    public void setClientTjrId(String clientTjrId) 
    {
        this.clientTjrId = clientTjrId;
    }

    public String getClientTjrId() 
    {
        return clientTjrId;
    }
    public void setGeneral(String general) 
    {
        this.general = general;
    }

    public String getGeneral() 
    {
        return general;
    }
    public void setComplaintDate(Date complaintDate) 
    {
        this.complaintDate = complaintDate;
    }

    public Date getComplaintDate() 
    {
        return complaintDate;
    }
    public void setOrderNumber(String orderNumber) 
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() 
    {
        return orderNumber;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setClientUnit(String clientUnit) 
    {
        this.clientUnit = clientUnit;
    }

    public String getClientUnit() 
    {
        return clientUnit;
    }
    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }
    public void setComplaintsType(String complaintsType) 
    {
        this.complaintsType = complaintsType;
    }

    public String getComplaintsType() 
    {
        return complaintsType;
    }
    public void setIncome(String income) 
    {
        this.income = income;
    }

    public String getIncome() 
    {
        return income;
    }
    public void setExpenditure(String expenditure) 
    {
        this.expenditure = expenditure;
    }

    public String getExpenditure() 
    {
        return expenditure;
    }
    public void setSettlementSituation(String settlementSituation) 
    {
        this.settlementSituation = settlementSituation;
    }

    public String getSettlementSituation() 
    {
        return settlementSituation;
    }
    public void setRmb(String rmb) 
    {
        this.rmb = rmb;
    }

    public String getRmb() 
    {
        return rmb;
    }
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setDemage(String demage) 
    {
        this.demage = demage;
    }

    public String getDemage() 
    {
        return demage;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clientTjr", getClientTjr())
            .append("clientTjrId", getClientTjrId())
            .append("general", getGeneral())
            .append("complaintDate", getComplaintDate())
            .append("orderNumber", getOrderNumber())
            .append("orderId", getOrderId())
            .append("clientUnit", getClientUnit())
            .append("endDate", getEndDate())
            .append("complaintsType", getComplaintsType())
            .append("income", getIncome())
            .append("expenditure", getExpenditure())
            .append("settlementSituation", getSettlementSituation())
            .append("rmb", getRmb())
            .append("department", getDepartment())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("demage", getDemage())
            .toString();
    }
}
