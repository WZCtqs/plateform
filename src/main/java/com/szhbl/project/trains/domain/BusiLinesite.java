package com.szhbl.project.trains.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 线路对象 busi_linesite
 * 
 * @author dps
 * @date 2020-01-11
 */
public class BusiLinesite
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "线路id")
    private String id;

    /** 路线ID ，0中欧 2中亚 3中越 4中俄 */
    @Excel(name = "路线ID ，0中欧 2中亚 3中越 4中俄")
    @ApiModelProperty(value = "路线ID ，0中欧 2中亚 3中越 4中俄")
    @NotNull(message = "线路类型不能为空")
    private Long typeid;

    /** 线路中文名 */
    @Excel(name = "线路中文名")
    @ApiModelProperty(value = "线路中文名")
    @NotNull(message = "班列中文名称不能为空")
    private String nameCn;

    /** 线路英文名 */
    @Excel(name = "线路英文名")
    @ApiModelProperty(value = "线路英文名")
    @NotNull(message = "班列英文名称不能为空")
    private String nameEn;

    /** 始发站代码 */
    @Excel(name = "始发站代码")
    @ApiModelProperty(value = "始发站代码")
    @NotNull(message = "始发站不能为空")
    private String classTStationofdepartureCode;

    /** 口岸代码 */
    @Excel(name = "口岸代码")
    @ApiModelProperty(value = "口岸代码")
    @NotNull(message = "口岸不能为空")
    private String classTPort;

    /** 目的站代码 */
    @Excel(name = "目的站代码")
    @ApiModelProperty(value = "目的站代码")
    @NotNull(message = "目的站不能为空")
    private String classTStationofdestinationCode;

    /** 始发站代码 */
    @Excel(name = "始发站")
    @ApiModelProperty(value = "始发站")
    private String classTStationofdeparture;

    /** 口岸代码 */
    @Excel(name = "口岸")
    @ApiModelProperty(value = "口岸")
    private String classPort;

    /** 目的站代码 */
    @Excel(name = "目的站")
    @ApiModelProperty(value = "目的站")
    private String classTStationofdestination;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty(value = "排序")
    private Long sort;

    /** 站点编号 */
    @Excel(name = "站点编号")
    @ApiModelProperty(value = "站点编号，英文逗号隔开")
    private String siteCodes;

    /** 站点编号 */
    @Excel(name = "站点编号数组")
    @ApiModelProperty(value = "站点编号数组")
    private String[] siteCodesArr;

    /** 0禁用  1启用 */
    @Excel(name = "0禁用  1启用")
    @ApiModelProperty(value = "状态：0禁用  1启用")
    private String state;

    /** 0往 1返 */
    @Excel(name = "0往 1返")
    @ApiModelProperty(value = "0往 1返")
    private String classTEastandwest;

    /** 运行时间 */
    @Excel(name = "运行时间")
    @ApiModelProperty(value = "运行时间")
    private Long classTTransporttime;

    /** 0未删除 1删除 */
    @ApiModelProperty(value = "删除：0未删除 1删除")
    private String delFlag;

    /** 修改人 */
    @Excel(name = "修改人")
    @ApiModelProperty(value = "修改人")
    private String updatename;

    /** 修改时间 */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updatetime;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setTypeid(Long typeid) 
    {
        this.typeid = typeid;
    }

    public Long getTypeid() 
    {
        return typeid;
    }
    public void setNameCn(String nameCn) 
    {
        this.nameCn = nameCn;
    }

    public String getNameCn() 
    {
        return nameCn;
    }

    public void setNameEn(String nameEn) 
    {
        this.nameEn = nameEn;
    }
    public String getNameEn() 
    {
        return nameEn;
    }

    public void setClassTStationofdepartureCode(String classTStationofdepartureCode)
    {
        this.classTStationofdepartureCode = classTStationofdepartureCode;
    }
    public String getClassTStationofdepartureCode()
    {
        return classTStationofdepartureCode;
    }

    public void setClassTStationofdeparture(String classTStationofdeparture)
    {
        this.classTStationofdeparture = classTStationofdeparture;
    }
    public String getClassTStationofdeparture()
    {
        return classTStationofdeparture;
    }

    public void setClassTPort(String classTPort)
    {
        this.classTPort = classTPort;
    }
    public String getClassTPort()
    {
        return classTPort;
    }

    public void setClassPort(String classPort)
    {
        this.classPort = classPort;
    }
    public String getClassPort()
    {
        return classPort;
    }

    public void setClassTStationofdestinationCode(String classTStationofdestinationCode)
    {
        this.classTStationofdestinationCode = classTStationofdestinationCode;
    }
    public String getClassTStationofdestinationCode()
    {
        return classTStationofdestinationCode;
    }

    public void setClassTStationofdestination(String classTStationofdestination)
    {
        this.classTStationofdestination = classTStationofdestination;
    }
    public String getClassTStationofdestination()
    {
        return classTStationofdestination;
    }

    public void setSort(Long sort) 
    {
        this.sort = sort;
    }
    public Long getSort() 
    {
        return sort;
    }

    public void setSiteCodesArr(String[] siteCodesArr)
    {
        this.siteCodesArr = siteCodesArr;
    }
    public String[] getSiteCodesArr()
    {
        return siteCodesArr;
    }

    public void setSiteCodes(String siteCodes)
    {
        this.siteCodes = siteCodes;
    }
    public String getSiteCodes()
    {
        return siteCodes;
    }

    public void setState(String state) 
    {
        this.state = state;
    }
    public String getState() 
    {
        return state;
    }

    public void setClassTEastandwest(String classTEastandwest)
    {
        this.classTEastandwest = classTEastandwest;
    }
    public String getClassTEastandwest()
    {
        return classTEastandwest;
    }

    public void setClassTTransporttime(Long classTTransporttime)
    {
        this.classTTransporttime = classTTransporttime;
    }

    public Long getClassTTransporttime()
    {
        return classTTransporttime;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }
    public String getDelFlag() 
    {
        return delFlag;
    }

    public void setUpdatename(String updatename)
    {
        this.updatename = updatename;
    }
    public String getUpdatename()
    {
        return updatename;
    }

    public void setUpdatetime(Date updatetime){this.updatetime = updatetime; }
    public Date getUpdatetime(){return updatetime;}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typeid", getTypeid())
            .append("nameCn", getNameCn())
            .append("nameEn", getNameEn())
            .append("classTStationofdepartureCode", getClassTStationofdepartureCode())
            .append("classTPort", getClassTPort())
            .append("classTStationofdestinationCode", getClassTStationofdestinationCode())
            .append("classTStationofdeparture", getClassTStationofdeparture())
            .append("classPort", getClassPort())
            .append("classTStationofdestination", getClassTStationofdestination())
            .append("sort", getSort())
            .append("siteCodes", getSiteCodes())
            .append("siteCodesArr", getSiteCodesArr())
            .append("state", getState())
            .append("classTEastandwest", getClassTEastandwest())
            .append("classTTransporttime", getClassTTransporttime())
            .append("delFlag", getDelFlag())
            .append("updatename", getUpdatename())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
