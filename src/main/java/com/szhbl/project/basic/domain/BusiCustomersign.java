package com.szhbl.project.basic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 * 客户标记对象 busi_customersign
 * 
 * @author dps
 * @date 2020-03-16
 */
public class BusiCustomersign
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty(value = "标记客户id")
    private String signId;

    /** 收发货人名称 */
    @Excel(name = "标记客户名称")
    @ApiModelProperty(value = "标记客户名称")
    @NotNull(message = "标记客户不能为空")
    private String signName;

    /** 标记企业类型： 0收货方 1发货方 */
    @Excel(name = "标记企业类型： 0收货方 1发货方")
    @ApiModelProperty(value = "标记企业类型： 0收货方 1发货方")
    @NotNull(message = "标记企业类型不能为空")
    private String signType;

    /** 标记理由 */
    @Excel(name = "标记理由")
    @ApiModelProperty(value = "标记理由")
    private String signReason;

    /** 标记0否1是 默认是 */
    @Excel(name = "标记0否1是 默认是")
    @ApiModelProperty(value = "标记0否1是 默认是")
    private String isSign;

    /** 标记时间 */
    @Excel(name = "标记时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "标记时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

    public void setSignId(String signId) 
    {
        this.signId = signId;
    }

    public String getSignId() 
    {
        return signId;
    }
    public void setSignName(String signName) 
    {
        this.signName = signName;
    }

    public String getSignName() 
    {
        return signName;
    }
    public void setSignType(String signType) 
    {
        this.signType = signType;
    }

    public String getSignType() 
    {
        return signType;
    }
    public void setSignReason(String signReason) 
    {
        this.signReason = signReason;
    }

    public String getSignReason() 
    {
        return signReason;
    }
    public void setIsSign(String isSign) 
    {
        this.isSign = isSign;
    }

    public String getIsSign() 
    {
        return isSign;
    }
    public void setCreatedate(Date createdate) 
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("signId", getSignId())
            .append("signName", getSignName())
            .append("signType", getSignType())
            .append("signReason", getSignReason())
            .append("isSign", getIsSign())
            .append("createdate", getCreatedate())
            .toString();
    }
}
