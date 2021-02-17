package com.szhbl.project.trains.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;

import java.util.Date;

/**
 * 班列模板对象 busi_classes_template
 * 
 * @author szhbl
 * @date 2020-01-10
 */
public class BusiClassesTemplate
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String classTId;

    /** $column.columnComment 班列号*/
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String classTNumber;

    /** 开行班列 */
    @Excel(name = "开行班列中文")
    private String classTBlocktrainCn;

    /** 开行班列 */
    @Excel(name = "开行班列英文")
    private String classTBlocktrainEn;

    /** $column.columnComment */
    @Excel(name = "班列类型")
    private String classTClasstype;

    /** 0往 1返 */
    @Excel(name = "0往 1返")
    private String classTEastandwest;

    /** 始发站代码 */
    @Excel(name = "始发站代码")
    private String classTStationofdepartureCode;

    /** 始发站 */
    @Excel(name = "始发站")
    private String classTStationofdeparture;

    /** 始发站英文 */
    @Excel(name = "始发站英文")
    private String classTStationofdepartureEn;

    /** 目的站代码 */
    @Excel(name = "目的站代码")
    private String classTStationofdestinationCode;

    /** 目的站 */
    @Excel(name = "目的站")
    private String classTStationofdestination;

    /** 目的站英文 */
    @Excel(name = "目的站英文")
    private String classTStationofdestinationEn;

    /** 开发频次 */
    @Excel(name = "开发频次")
    private String classTTransporttime;

    /** 经过站点 */
    @Excel(name = "经过站点")
    private String classTPath;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 创建用户id */
    @Excel(name = "创建用户id")
    private String createuserid;

    /** 创建用户名 */
    @Excel(name = "创建用户名")
    private String createusername;

    /** 起始站(code和名称) */
    @Excel(name = "起始站(code和名称)")
    private String classTSstie;

    /** 目的站(code和名称) */
    @Excel(name = "目的站(code和名称)")
    private String classTEstie;

    /** 路线id */
    @Excel(name = "路线id")
    private Long lineId;

    /** 路线名称 */
    @Excel(name = "路线名称")
    private String lineName;

    /** 0是中欧2是中亚3是中越4是中俄 */
    @Excel(name = "0是中欧2是中亚3是中越4是中俄")
    private Long lineTypeid;

    /** $column.columnComment */
    @Excel(name = "0是中欧2是中亚3是中越4是中俄")
    private String yuyan;

    /** 删除标志（0代表存在 1代表删除） */
    @Excel(name = "0代表存在 1代表删除")
    private String delFlag;

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
    public void setClassTBlocktrainCn(String classTBlocktrainCn)
    {
        this.classTBlocktrainCn = classTBlocktrainCn;
    }

    public String getClassTBlocktrainCn()
    {
        return classTBlocktrainCn;
    }

    public void setClassTBlocktrainEn(String classTBlocktrainEn)
    {
        this.classTBlocktrainEn = classTBlocktrainEn;
    }

    public String getClassTBlocktrainEn()
    {
        return classTBlocktrainEn;
    }
    public void setClassTClasstype(String classTClasstype) 
    {
        this.classTClasstype = classTClasstype;
    }

    public String getClassTClasstype() 
    {
        return classTClasstype;
    }
    public void setClassTEastandwest(String classTEastandwest) 
    {
        this.classTEastandwest = classTEastandwest;
    }

    public String getClassTEastandwest() 
    {
        return classTEastandwest;
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
    public void setClassTStationofdepartureEn(String classTStationofdepartureEn) 
    {
        this.classTStationofdepartureEn = classTStationofdepartureEn;
    }

    public String getClassTStationofdepartureEn() 
    {
        return classTStationofdepartureEn;
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
    public void setClassTStationofdestinationEn(String classTStationofdestinationEn) 
    {
        this.classTStationofdestinationEn = classTStationofdestinationEn;
    }

    public String getClassTStationofdestinationEn() 
    {
        return classTStationofdestinationEn;
    }
    public void setClassTTransporttime(String classTTransporttime) 
    {
        this.classTTransporttime = classTTransporttime;
    }

    public String getClassTTransporttime() 
    {
        return classTTransporttime;
    }
    public void setClassTPath(String classTPath) 
    {
        this.classTPath = classTPath;
    }

    public String getClassTPath() 
    {
        return classTPath;
    }
    public void setCreatedate(Date createdate) 
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }
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
    public void setClassTSstie(String classTSstie) 
    {
        this.classTSstie = classTSstie;
    }

    public String getClassTSstie() 
    {
        return classTSstie;
    }
    public void setClassTEstie(String classTEstie) 
    {
        this.classTEstie = classTEstie;
    }

    public String getClassTEstie() 
    {
        return classTEstie;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setLineName(String lineName) 
    {
        this.lineName = lineName;
    }

    public String getLineName() 
    {
        return lineName;
    }
    public void setLineTypeid(Long lineTypeid) 
    {
        this.lineTypeid = lineTypeid;
    }

    public Long getLineTypeid() 
    {
        return lineTypeid;
    }
    public void setYuyan(String yuyan) 
    {
        this.yuyan = yuyan;
    }

    public String getYuyan() 
    {
        return yuyan;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classTId", getClassTId())
            .append("classTNumber", getClassTNumber())
            .append("classTBlocktrainCn", getClassTBlocktrainCn())
            .append("classTBlocktrainEn", getClassTBlocktrainEn())
            .append("classTClasstype", getClassTClasstype())
            .append("classTEastandwest", getClassTEastandwest())
            .append("classTStationofdepartureCode", getClassTStationofdepartureCode())
            .append("classTStationofdeparture", getClassTStationofdeparture())
            .append("classTStationofdepartureEn", getClassTStationofdepartureEn())
            .append("classTStationofdestinationCode", getClassTStationofdestinationCode())
            .append("classTStationofdestination", getClassTStationofdestination())
            .append("classTStationofdestinationEn", getClassTStationofdestinationEn())
            .append("classTTransporttime", getClassTTransporttime())
            .append("classTPath", getClassTPath())
            .append("createdate", getCreatedate())
            .append("createuserid", getCreateuserid())
            .append("createusername", getCreateusername())
            .append("classTSstie", getClassTSstie())
            .append("classTEstie", getClassTEstie())
            .append("lineId", getLineId())
            .append("lineName", getLineName())
            .append("lineTypeid", getLineTypeid())
            .append("yuyan", getYuyan())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
