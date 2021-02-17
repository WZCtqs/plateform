package com.szhbl.project.basic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 汇率对象 busi_moneyrete
 * 
 * @author dps
 * @date 2020-01-15
 */
public class BusiMoneyrete
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String moneyrateId;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "班列日期")
    private Date classTime;

    /** 美元转人民币 */
    @Excel(name = "美元转人民币")
    @ApiModelProperty(value = "美元转人民币")
    private Double usdtormbe;

    /** 欧元转人民币 */
    @Excel(name = "欧元转人民币")
    @ApiModelProperty(value = "欧元转人民币")
    private Double eurtormbe;

    /** 欧元转美金 */
    @Excel(name = "欧元转美金")
    @ApiModelProperty(value = "欧元转美金")
    private Double euttousde;

    /** 卢布转人民币 */
    @Excel(name = "卢布转人民币")
    @ApiModelProperty(value = "卢布转人民币")
    private Double rurtormbe;

    /** 操作人 */
    @Excel(name = "操作人")
    @ApiModelProperty(value = "操作部门|姓名")
    private String rateoperator;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date operationtime;

    public void setMoneyrateId(String moneyrateId) 
    {
        this.moneyrateId = moneyrateId;
    }

    public String getMoneyrateId() 
    {
        return moneyrateId;
    }
    public void setClassTime(Date classTime) 
    {
        this.classTime = classTime;
    }

    public Date getClassTime() 
    {
        return classTime;
    }
    public void setUsdtormbe(Double usdtormbe) 
    {
        this.usdtormbe = usdtormbe;
    }

    public Double getUsdtormbe() 
    {
        return usdtormbe;
    }
    public void setEurtormbe(Double eurtormbe) 
    {
        this.eurtormbe = eurtormbe;
    }

    public Double getEurtormbe() 
    {
        return eurtormbe;
    }
    public void setEuttousde(Double euttousde) 
    {
        this.euttousde = euttousde;
    }

    public Double getEuttousde() 
    {
        return euttousde;
    }
    public void setRurtormbe(Double rurtormbe) 
    {
        this.rurtormbe = rurtormbe;
    }

    public Double getRurtormbe() 
    {
        return rurtormbe;
    }
    public void setRateoperator(String rateoperator) 
    {
        this.rateoperator = rateoperator;
    }

    public String getRateoperator() 
    {
        return rateoperator;
    }
    public void setOperationtime(Date operationtime) 
    {
        this.operationtime = operationtime;
    }

    public Date getOperationtime() 
    {
        return operationtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("moneyrateId", getMoneyrateId())
            .append("classTime", getClassTime())
            .append("usdtormbe", getUsdtormbe())
            .append("eurtormbe", getEurtormbe())
            .append("euttousde", getEuttousde())
            .append("rurtormbe", getRurtormbe())
            .append("rateoperator", getRateoperator())
            .append("operationtime", getOperationtime())
            .toString();
    }
}
