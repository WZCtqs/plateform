package com.szhbl.project.client.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 客户满意度对象 busi_clientsatisfaction
 * 
 * @author jhm
 * @date 2020-01-13
 */
public class BusiClientsatisfaction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String satisfactionId;

    /** 客户id */
    @Excel(name = "客户id")
    private String clientId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String clientUnit;

    /** 亚欧部 */
    @Excel(name = "亚欧部")
    private String organizationCode;

    /** 评价部门 */
    @Excel(name = "评价部门")
    private String organizationName;

    /** 填报人 */
    @Excel(name = "填报人")
    private String clientTjr;

    /** 国内公路服务占比10% */
    @Excel(name = "国内公路服务占比10%")
    private Integer roadService;

    /** 国内场站服务占比10% */
    @Excel(name = "国内场站服务占比10%")
    private Integer instationService;

    /** 国内关务服务占比10% */
    @Excel(name = "国内关务服务占比10%")
    private Integer incustomsService;

    /** 铁路运输服务占比30% */
    @Excel(name = "铁路运输服务占比30%")
    private Integer railageService;

    /** 国外关务服务占比5% */
    @Excel(name = "国外关务服务占比5%")
    private Integer outcustomsService;

    /** 国外场站服务占比10% */
    @Excel(name = "国外场站服务占比10%")
    private Integer outstationService;

    /** 国外集疏服务占比10% */
    @Excel(name = "国外集疏服务占比10%")
    private Integer jsService;

    /** 国内外集装箱服务占比5% */
    @Excel(name = "国内外集装箱服务占比5%")
    private Integer containerService;

    /** 信息反馈服务占比10% */
    @Excel(name = "信息反馈服务占比10%")
    private Integer msgService;

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
    @Excel(name = "评价日期")
    private String operator;
    /** 查询评价开始日期 */
    private String startDate;
    /** 查询评价结束日期 */
    private String endDate;
    public void setSatisfactionId(String satisfactionId) 
    {
        this.satisfactionId = satisfactionId;
    }

    public String getSatisfactionId() 
    {
        return satisfactionId;
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
    public void setRoadService(Integer roadService) 
    {
        this.roadService = roadService;
    }

    public Integer getRoadService() 
    {
        return roadService;
    }
    public void setInstationService(Integer instationService) 
    {
        this.instationService = instationService;
    }

    public Integer getInstationService() 
    {
        return instationService;
    }
    public void setIncustomsService(Integer incustomsService) 
    {
        this.incustomsService = incustomsService;
    }

    public Integer getIncustomsService() 
    {
        return incustomsService;
    }
    public void setRailageService(Integer railageService) 
    {
        this.railageService = railageService;
    }

    public Integer getRailageService() 
    {
        return railageService;
    }
    public void setOutcustomsService(Integer outcustomsService) 
    {
        this.outcustomsService = outcustomsService;
    }

    public Integer getOutcustomsService() 
    {
        return outcustomsService;
    }
    public void setOutstationService(Integer outstationService) 
    {
        this.outstationService = outstationService;
    }

    public Integer getOutstationService() 
    {
        return outstationService;
    }
    public void setJsService(Integer jsService) 
    {
        this.jsService = jsService;
    }

    public Integer getJsService() 
    {
        return jsService;
    }
    public void setContainerService(Integer containerService) 
    {
        this.containerService = containerService;
    }

    public Integer getContainerService() 
    {
        return containerService;
    }
    public void setMsgService(Integer msgService) 
    {
        this.msgService = msgService;
    }

    public Integer getMsgService() 
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("satisfactionId", getSatisfactionId())
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
