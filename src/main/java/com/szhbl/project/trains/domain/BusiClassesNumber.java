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
 * 班列号对象 Busi_Classes_Number
 * 
 * @author dps
 * @date 2020-03-13
 */
public class BusiClassesNumber
{
    private static final long serialVersionUID = 1L;

    /** 班列号id,主键 */
    @ApiModelProperty(value = "线路id")
    private String classTId;

    /** 班列号 */
    @Excel(name = "班列号")
    @ApiModelProperty(value = "班列号")
    @NotNull(message = "班列号不能为空")
    private String classTNumber;

    /** 线路类型：0中欧 2中亚 3中越 4中俄 */
    @Excel(name = "线路类型：0中欧 2中亚 3中越 4中俄")
    @ApiModelProperty(value = "线路类型：0中欧 2中亚 3中越 4中俄")
    @NotNull(message = "线路类型不能为空")
    private String lineTypeid;

    /** 去回程：0往 1返 */
    @Excel(name = "去回程：0往 1返")
    @ApiModelProperty(value = "去回程：0往 1返")
    @NotNull(message = "去回程不能为空")
    private String classTEastandwest;

    /** 0禁用 1启用 */
    @Excel(name = "0禁用 1启用")
    @ApiModelProperty(value = "0禁用 1启用")
    private String state;

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

    public void setClassTId(String classTId)
    {
        this.classTId = classTId;
    }

    public String getClassTId()
    {
        return classTId;
    }
    public void setClassTNumber(String classTNumber) 
    {
        this.classTNumber = classTNumber;
    }

    public String getClassTNumber() 
    {
        return classTNumber;
    }
    public void setLineTypeid(String lineTypeid) 
    {
        this.lineTypeid = lineTypeid;
    }

    public String getLineTypeid() 
    {
        return lineTypeid;
    }
    public void setClassTEastandwest(String classTEastandwest) 
    {
        this.classTEastandwest = classTEastandwest;
    }

    public String getClassTEastandwest() 
    {
        return classTEastandwest;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
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
            .append("classTId", getClassTId())
            .append("classTNumber", getClassTNumber())
            .append("lineTypeid", getLineTypeid())
            .append("classTEastandwest", getClassTEastandwest())
            .append("state", getState())
            .append("updatename", getUpdatename())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
