package com.szhbl.project.cabinarrangement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 排舱管理对象 busi_classes
 * 
 * @author dps
 * @date 2020-01-14
 */
public class BusiClassesPc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 班列id，主键 */
    private String classId;

    /** 班列编号 */
    @Excel(name = "班列编号")
    private String classBh;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 */
    @Excel(name = "线路类型：0是中欧2是中亚3是中越4是中俄")
    private Long lineTypeid;

    /** 线路ID */
    @Excel(name = "线路ID")
    private String lineId;

    /** 班列模板ID */
    @Excel(name = "班列模板ID")
    private String classTId;

    /** 班列号 */
    @Excel(name = "班列号")
    private String classNumber;

    /** 开行班列 */
    @Excel(name = "开行班列")
    private String classBlocktrain;

    /** 班列类型 */
    @Excel(name = "班列类型")
    private String classClasstype;

    /** 0为去(西向) 1为回(东向） */
    @Excel(name = "0为去(西向) 1为回(东向）")
    private String classEastandwest;

    /** 始发站编号 */
    @Excel(name = "始发站编号")
    private String classStationofdeparture;

    /** 目的站编号 */
    @Excel(name = "目的站编号")
    private String classStationofdestination;

    /** 运行时间 */
    @Excel(name = "运行时间")
    private String classTransporttime;

    /** 班列是否接受申请 1是 0否 */
    @Excel(name = "班列是否接受申请 1是 0否")
    private String ispublicly;

    /** 舱位总数 */
    @Excel(name = "舱位总数")
    private Long classSpacenumber;

    /** 整箱舱位数 */
    @Excel(name = "整箱舱位数")
    private Long zxcnt;

    /** 拼箱状态（0是未满1是已满） */
    @Excel(name = "拼箱状态", readConverterExp = "0=是未满1是已满")
    private String pxstate;

    /** 拼箱体积数 */
    @Excel(name = "拼箱体积数")
    private Long pxcnt;

    /** 拼箱重量数 */
    @Excel(name = "拼箱重量数")
    private Long pxkgs;

    /** 计划开车时间 */
    @Excel(name = "计划开车时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date classStime;

    /** 计划到站时间 */
    @Excel(name = "计划到站时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date classEtime;

    /** 车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列 */
    @Excel(name = "车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列")
    private String classState;

    /** 整柜随机接货站点编号 */
    @Excel(name = "整柜随机接货站点编号")
    private String receiveSiteFull;

    /** 拼箱随机接货站点编号 */
    @Excel(name = "拼箱随机接货站点编号")
    private String receiveSiteLcl;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    private String createuserid;

    /** 创建用户姓名 */
    @Excel(name = "创建用户姓名")
    private String createusername;

    /** 费用是否可修改 0可修改 1不可修改（默认可修改） */
    @Excel(name = "费用是否可修改 0可修改 1不可修改", readConverterExp = "默=认可修改")
    private Long costentryState;

    /** 当天录运踪的时间（每天都在更新） */
    @Excel(name = "当天录运踪的时间", readConverterExp = "每=天都在更新")
    private String distributionTime;

    /** 控制结算模块箱号录入节点 0可修改 1不可修改（默认可修改） */
    @Excel(name = "控制结算模块箱号录入节点 0可修改 1不可修改", readConverterExp = "默=认可修改")
    private Long xhentryState;

    /** 管理部控制拼箱录入拼箱体积表0是不可修改，1是可修改（默认不可修改） */
    @Excel(name = "管理部控制拼箱录入拼箱体积表0是不可修改，1是可修改", readConverterExp = "默=认不可修改")
    private Long pxentryState;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date sStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date sEndTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date eStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date eEndTime;

    private String zxState;
    //en zh
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getZxState() {
        return zxState;
    }

    public void setZxState(String zxState) {
        this.zxState = zxState;
    }

    public Date getsStartTime() {
        return sStartTime;
    }

    public void setsStartTime(Date sStartTime) {
        this.sStartTime = sStartTime;
    }

    public Date getsEndTime() {
        return sEndTime;
    }

    public void setsEndTime(Date sEndTime) {
        this.sEndTime = sEndTime;
    }

    public Date geteStartTime() {
        return eStartTime;
    }

    public void seteStartTime(Date eStartTime) {
        this.eStartTime = eStartTime;
    }

    public Date geteEndTime() {
        return eEndTime;
    }

    public void seteEndTime(Date eEndTime) {
        this.eEndTime = eEndTime;
    }

    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getClassId()
    {
        return classId;
    }
    public void setClassBh(String classBh) 
    {
        this.classBh = classBh;
    }

    public String getClassBh() 
    {
        return classBh;
    }
    public void setLineTypeid(Long lineTypeid) 
    {
        this.lineTypeid = lineTypeid;
    }

    public Long getLineTypeid() 
    {
        return lineTypeid;
    }
    public void setLineId(String lineId)
    {
        this.lineId = lineId;
    }

    public String getLineId()
    {
        return lineId;
    }
    public void setClassTId(String classTId) 
    {
        this.classTId = classTId;
    }

    public String getClassTId() 
    {
        return classTId;
    }
    public void setClassNumber(String classNumber) 
    {
        this.classNumber = classNumber;
    }

    public String getClassNumber() 
    {
        return classNumber;
    }
    public void setClassBlocktrain(String classBlocktrain) 
    {
        this.classBlocktrain = classBlocktrain;
    }

    public String getClassBlocktrain() 
    {
        return classBlocktrain;
    }
    public void setClassClasstype(String classClasstype) 
    {
        this.classClasstype = classClasstype;
    }

    public String getClassClasstype() 
    {
        return classClasstype;
    }
    public void setClassEastandwest(String classEastandwest) 
    {
        this.classEastandwest = classEastandwest;
    }

    public String getClassEastandwest() 
    {
        return classEastandwest;
    }
    public void setClassStationofdeparture(String classStationofdeparture) 
    {
        this.classStationofdeparture = classStationofdeparture;
    }

    public String getClassStationofdeparture() 
    {
        return classStationofdeparture;
    }
    public void setClassStationofdestination(String classStationofdestination) 
    {
        this.classStationofdestination = classStationofdestination;
    }

    public String getClassStationofdestination() 
    {
        return classStationofdestination;
    }
    public void setClassTransporttime(String classTransporttime) 
    {
        this.classTransporttime = classTransporttime;
    }

    public String getClassTransporttime() 
    {
        return classTransporttime;
    }
    public void setIspublicly(String ispublicly) 
    {
        this.ispublicly = ispublicly;
    }

    public String getIspublicly() 
    {
        return ispublicly;
    }
    public void setClassSpacenumber(Long classSpacenumber) 
    {
        this.classSpacenumber = classSpacenumber;
    }

    public Long getClassSpacenumber() 
    {
        return classSpacenumber;
    }
    public void setZxcnt(Long zxcnt) 
    {
        this.zxcnt = zxcnt;
    }

    public Long getZxcnt() 
    {
        return zxcnt;
    }
    public void setPxstate(String pxstate) 
    {
        this.pxstate = pxstate;
    }

    public String getPxstate() 
    {
        return pxstate;
    }
    public void setPxcnt(Long pxcnt) 
    {
        this.pxcnt = pxcnt;
    }

    public Long getPxcnt() 
    {
        return pxcnt;
    }
    public void setPxkgs(Long pxkgs) 
    {
        this.pxkgs = pxkgs;
    }

    public Long getPxkgs() 
    {
        return pxkgs;
    }
    public void setClassStime(Date classStime) 
    {
        this.classStime = classStime;
    }

    public Date getClassStime() 
    {
        return classStime;
    }
    public void setClassEtime(Date classEtime) 
    {
        this.classEtime = classEtime;
    }

    public Date getClassEtime() 
    {
        return classEtime;
    }
    public void setClassState(String classState) 
    {
        this.classState = classState;
    }

    public String getClassState() 
    {
        return classState;
    }
    public void setReceiveSiteFull(String receiveSiteFull) 
    {
        this.receiveSiteFull = receiveSiteFull;
    }

    public String getReceiveSiteFull() 
    {
        return receiveSiteFull;
    }
    public void setReceiveSiteLcl(String receiveSiteLcl) 
    {
        this.receiveSiteLcl = receiveSiteLcl;
    }

    public String getReceiveSiteLcl() 
    {
        return receiveSiteLcl;
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
    public void setCostentryState(Long costentryState) 
    {
        this.costentryState = costentryState;
    }

    public Long getCostentryState() 
    {
        return costentryState;
    }
    public void setDistributionTime(String distributionTime) 
    {
        this.distributionTime = distributionTime;
    }

    public String getDistributionTime() 
    {
        return distributionTime;
    }
    public void setXhentryState(Long xhentryState) 
    {
        this.xhentryState = xhentryState;
    }

    public Long getXhentryState() 
    {
        return xhentryState;
    }
    public void setPxentryState(Long pxentryState) 
    {
        this.pxentryState = pxentryState;
    }

    public Long getPxentryState() 
    {
        return pxentryState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classId", getClassId())
            .append("classBh", getClassBh())
            .append("lineTypeid", getLineTypeid())
            .append("lineId", getLineId())
            .append("classTId", getClassTId())
            .append("classNumber", getClassNumber())
            .append("classBlocktrain", getClassBlocktrain())
            .append("classClasstype", getClassClasstype())
            .append("classEastandwest", getClassEastandwest())
            .append("classStationofdeparture", getClassStationofdeparture())
            .append("classStationofdestination", getClassStationofdestination())
            .append("classTransporttime", getClassTransporttime())
            .append("ispublicly", getIspublicly())
            .append("classSpacenumber", getClassSpacenumber())
            .append("zxcnt", getZxcnt())
            .append("pxstate", getPxstate())
            .append("pxcnt", getPxcnt())
            .append("pxkgs", getPxkgs())
            .append("classStime", getClassStime())
            .append("classEtime", getClassEtime())
            .append("classState", getClassState())
            .append("receiveSiteFull", getReceiveSiteFull())
            .append("receiveSiteLcl", getReceiveSiteLcl())
            .append("createdate", getCreatedate())
            .append("createuserid", getCreateuserid())
            .append("createusername", getCreateusername())
            .append("remark", getRemark())
            .append("costentryState", getCostentryState())
            .append("distributionTime", getDistributionTime())
            .append("xhentryState", getXhentryState())
            .append("pxentryState", getPxentryState())
            .toString();
    }
}
