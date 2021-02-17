package com.szhbl.project.client.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 操作质量投诉对象 busi_operationquality
 *
 * @author jhm
 * @date 2020-01-13
 */
public class BusiOperationquality extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String qualityId;

    /** 订舱id */
    private String orderId;

    /** 委托书编号 */
    @Excel(name = "委托书编号")
    private String orderNumber;

    /** 客户id */
    private String clientId;

    /** 客户单位 */
    @Excel(name = "客户单位")
    private String clientUnit;

    /** 亚欧部 */
    @Excel(name = "亚欧部")
    private String organizationCode;

    /** 评价部门 */
    @Excel(name = "评价部门")
    private String organizationName;

    /** 填报人（推荐人） */
    @Excel(name = "填报人", readConverterExp = "推=荐人")
    private String clientTjr;

    /** 国内公路服务占比10% */
    @Excel(name = "国内公路服务占比10%")
    private String roadService;

    /** 国内场站服务占比10% */
    @Excel(name = "国内场站服务占比10%")
    private String instationService;

    /** 国内关务服务占比10% */
    @Excel(name = "国内关务服务占比10%")
    private String incustomsService;

    /** 铁路运输服务占比30% */
    @Excel(name = "铁路运输服务占比30%")
    private String railageService;

    /** 国外关务服务占比5% */
    @Excel(name = "国外关务服务占比5%")
    private String outcustomsService;

    /** 国外场站服务占比10% */
    @Excel(name = "国外场站服务占比10%")
    private String outstationService;

    /** 国外集疏服务占比10% */
    @Excel(name = "国外集疏服务占比10%")
    private String jsService;

    /** 国内外集装箱服务占比5% */
    @Excel(name = "国内外集装箱服务占比5%")
    private String containerService;

    /** 信息反馈服务占比10% */
    @Excel(name = "信息反馈服务占比10%")
    private String msgService;

    /** 满意度 */
    @Excel(name = "满意度")
    private String satisfactionDegree;

    /** 备注 */
    @Excel(name = "备注")
    private String satisfactionRemake;

    /** 评价日期 */
    @Excel(name = "评价日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date satisfactionDate;

    /** $column.columnComment */
    @Excel(name = "评价人")
    private String operator;
    /** 查询评价开始日期 */
    private String startDate;
    /** 查询评价结束日期 */
    private String endDate;

    /**
     * 部门编号
     */
    public String deptCode;

    public String readType;//0，按对应部门编号精准查询,1,2,3,4,5,6

    public void setQualityId(String qualityId)
    {
        this.qualityId = qualityId;
    }

    public String getQualityId()
    {
        return qualityId;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber()
    {
        return orderNumber;
    }
    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientId()
    {
        return clientId;
    }
    public void setClientUnit(String clientUnit)
    {
        this.clientUnit = clientUnit;
    }

    public String getClientUnit()
    {
        return clientUnit;
    }
    public void setOrganizationCode(String organizationCode)
    {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationCode()
    {
        return organizationCode;
    }
    public void setOrganizationName(String organizationName)
    {
        this.organizationName = organizationName;
    }

    public String getOrganizationName()
    {
        return organizationName;
    }
    public void setClientTjr(String clientTjr)
    {
        this.clientTjr = clientTjr;
    }

    public String getClientTjr()
    {
        return clientTjr;
    }
    public void setRoadService(String roadService)
    {
        this.roadService = roadService;
    }

    public String getRoadService()
    {
        return roadService;
    }
    public void setInstationService(String instationService)
    {
        this.instationService = instationService;
    }

    public String getInstationService()
    {
        return instationService;
    }
    public void setIncustomsService(String incustomsService)
    {
        this.incustomsService = incustomsService;
    }

    public String getIncustomsService()
    {
        return incustomsService;
    }
    public void setRailageService(String railageService)
    {
        this.railageService = railageService;
    }

    public String getRailageService()
    {
        return railageService;
    }
    public void setOutcustomsService(String outcustomsService)
    {
        this.outcustomsService = outcustomsService;
    }

    public String getOutcustomsService()
    {
        return outcustomsService;
    }
    public void setOutstationService(String outstationService)
    {
        this.outstationService = outstationService;
    }

    public String getOutstationService()
    {
        return outstationService;
    }
    public void setJsService(String jsService)
    {
        this.jsService = jsService;
    }

    public String getJsService()
    {
        return jsService;
    }
    public void setContainerService(String containerService)
    {
        this.containerService = containerService;
    }

    public String getContainerService()
    {
        return containerService;
    }
    public void setMsgService(String msgService)
    {
        this.msgService = msgService;
    }

    public String getMsgService()
    {
        return msgService;
    }
    public void setSatisfactionDegree(String satisfactionDegree)
    {
        this.satisfactionDegree = satisfactionDegree;
    }

    public String getSatisfactionDegree()
    {
        return satisfactionDegree;
    }
    public void setSatisfactionRemake(String satisfactionRemake)
    {
        this.satisfactionRemake = satisfactionRemake;
    }

    public String getSatisfactionRemake()
    {
        return satisfactionRemake;
    }
    public void setSatisfactionDate(Date satisfactionDate)
    {
        this.satisfactionDate = satisfactionDate;
    }

    public Date getSatisfactionDate()
    {
        return satisfactionDate;
    }
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    public String getDeptCode() {
        return deptCode;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }
    public String getReadType() {
        return readType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("qualityId", getQualityId())
            .append("orderId", getOrderId())
            .append("orderNumber", getOrderNumber())
            .append("clientId", getClientId())
            .append("clientUnit", getClientUnit())
            .append("organizationCode", getOrganizationCode())
            .append("organizationName", getOrganizationName())
            .append("clientTjr", getClientTjr())
            .append("roadService", getRoadService())
            .append("instationService", getInstationService())
            .append("incustomsService", getIncustomsService())
            .append("railageService", getRailageService())
            .append("outcustomsService", getOutcustomsService())
            .append("outstationService", getOutstationService())
            .append("jsService", getJsService())
            .append("containerService", getContainerService())
            .append("msgService", getMsgService())
            .append("satisfactionDegree", getSatisfactionDegree())
            .append("satisfactionRemake", getSatisfactionRemake())
            .append("satisfactionDate", getSatisfactionDate())
            .append("operator", getOperator())
            .append("deptCode", getDeptCode())
            .append("readType", getReadType())
            .toString();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
