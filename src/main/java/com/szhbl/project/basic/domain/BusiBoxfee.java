package com.szhbl.project.basic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 提还箱地和费用规则对象 busi_boxfee
 * 
 * @author dps
 * @date 2020-01-15
 */
public class BusiBoxfee
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String boxId;

    /** 集装箱类型：0普通箱 1特种箱 */
    @Excel(name = "集装箱类型：0普通箱 1特种箱")
    @ApiModelProperty(value = "集装箱类型：0普通箱 1特种箱")
    private String boxType;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 */
    @Excel(name = "线路：0中欧2中亚3中越4中俄,英文逗号隔开")
    private String lineTypeid;

    /** 选择类型：0,提箱；1,还箱 */
    @Excel(name = "选择类型：0,提箱；1,还箱")
    @ApiModelProperty(value = "选择类型：0,提箱；1,还箱")
    private String addressType;

    /** 提/还箱地点中文 */
    @Excel(name = "提/还箱地点中文")
    @ApiModelProperty(value = "提/还箱地点中文")
    private String address;

    /** 提/还箱地点英文 */
    @Excel(name = "提/还箱地点英文")
    @ApiModelProperty(value = "提/还箱地点英文")
    private String addressEn;

    /** 箱型 */
    @Excel(name = "箱型")
    @ApiModelProperty(value = "箱型")
    private String containerType;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty(value = "状态 0禁用 1启用")
    private String state;

    /** 平衡费用 */
    @Excel(name = "平衡费用")
    @ApiModelProperty(value = "平衡费用")
    private String cost;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;

    /** 截止时间 */
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "截止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    /** 创建人id */
    @Excel(name = "创建人id")
    @ApiModelProperty(value = "创建人id")
    private String createuserid;

    /** 创建人用户名 */
    @Excel(name = "创建人用户名")
    @ApiModelProperty(value = "创建人用户名")
    private String createusername;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date sStartTime;

    /** 1-国内 0-国外 */
    @Excel(name = "1-国内 0-国外")
    @ApiModelProperty(value = "1-国内 0-国外")
    private String intra;

    public void setIntra(String intra){this.intra = intra;}
    public String getIntra(){return intra;}

    public void setBoxId(String boxId) 
    {
        this.boxId = boxId;
    }

    public String getBoxId()
    {
        return boxId;
    }

    public void setLineTypeid(String lineTypeid)
    {
        this.lineTypeid = lineTypeid;
    }
    public String getLineTypeid()
    {
        return lineTypeid;
    }

    public void setBoxType(String boxType)
    {
        this.boxType = boxType;
    }
    public String getBoxType() 
    {
        return boxType;
    }

    public void setAddressType(String addressType) 
    {
        this.addressType = addressType;
    }

    public String getAddressType() 
    {
        return addressType;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setAddressEn(String addressEn) 
    {
        this.addressEn = addressEn;
    }

    public String getAddressEn() 
    {
        return addressEn;
    }
    public void setContainerType(String containerType) 
    {
        this.containerType = containerType;
    }

    public String getContainerType() 
    {
        return containerType;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setCost(String cost) 
    {
        this.cost = cost;
    }

    public String getCost() 
    {
        return cost;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }
    public Date getStartTime() 
    {
        return startTime;
    }

    public void setEndTime(Date endTime){this.endTime = endTime; }
    public Date getEndTime(){return endTime;}

    public void setCreateuserid(String createuserid) 
    {
        this.createuserid = createuserid;
    }

    public String getCreateuserid() 
    {
        return createuserid;
    }
    public void setCreateusername(String createusername) 
    {
        this.createusername = createusername;
    }

    public String getCreateusername() 
    {
        return createusername;
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
            .append("boxId", getBoxId())
            .append("boxType", getBoxType())
            .append("addressType", getAddressType())
            .append("lineTypeid", getLineTypeid())
            .append("address", getAddress())
            .append("addressEn", getAddressEn())
            .append("containerType", getContainerType())
            .append("intra", getIntra())
            .append("state", getState())
            .append("cost", getCost())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("createuserid", getCreateuserid())
            .append("createusername", getCreateusername())
            .append("createdate", getCreatedate())
            .toString();
    }
}
