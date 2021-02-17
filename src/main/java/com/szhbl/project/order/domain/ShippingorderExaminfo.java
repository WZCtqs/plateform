package com.szhbl.project.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 订单转待审核信息(订舱公告)对象 shippingorder_examinfo
 * 
 * @author dps
 * @date 2020-04-01
 */
public class ShippingorderExaminfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告id */
    @ApiModelProperty(value = "公告id")
    private String examId;

    /** 订单id */
    @Excel(name = "订单id")
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /** 公告时间 */
    @Excel(name = "公告时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "公告时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date examDate;

    /** 公告内容 */
    @Excel(name = "公告内容")
    @ApiModelProperty(value = "公告内容")
    private String examInfo;

    /** 修改记录 */
    @Excel(name = "修改记录")
    @ApiModelProperty(value = "修改记录")
    private String editRecord;

    /** 接收人邮箱 */
    @Excel(name = "接收人邮箱")
    @ApiModelProperty(value = "接收人邮箱")
    private String examSedemail;

    /** 创建人id */
    @Excel(name = "创建人id")
    @ApiModelProperty(value = "创建人id")
    private String createuserid;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    @ApiModelProperty(value = "创建人姓名")
    private String createusername;

    /** 0 审核失败 1转待审核后台 2转待审核前台客户 */
    @Excel(name = "（插入为2，失败为1，成功为0）")
    @ApiModelProperty(value = "（插入为2，失败为1，成功为0）")
    private String examType;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "托书编号", readConverterExp = "委=托书编号")
    @ApiModelProperty(value = "订单号 托书编号")
    private String orderNumber;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "班列日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;

    /** 班列日期N-5*/
    @Excel(name = "班列日期N-5", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "班列日期N-5")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateFive;

    /** 班列日期N-4 */
    @Excel(name = "班列日期N-4", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "班列日期N-4")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateFour;

    /** 操作截止时间 */
    @Excel(name = "操作截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "操作截止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateEnd;

    /** 0为去程 1为回程 */
    @Excel(name = "0为去程 1为回程")
    @ApiModelProperty(value = "0为去程 1为回程")
    private String classEastandwest;

    /** 0未审核 1已审核通过2已审核未通过 3已取消的委托4转待审核  5草稿 6转待失败 */
    @Excel(name = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    @ApiModelProperty(value = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    private String isexamline;

    /** 委托ZIH提货 0是 1否  2铁路到货 */
    @Excel(name = "委托ZIH提货 0是 1否  2铁路到货")
    @ApiModelProperty(value = "委托ZIH提货 0是 1否  2铁路到货")
    private String shipOrderBinningway;

    /**  整拼箱 0整柜 1拼箱 */
    @Excel(name = " 整拼箱 0整柜 1拼箱")
    @ApiModelProperty(value = "整拼箱 0整柜 1拼箱")
    private String isconsolidation;

    /** 托书操作创建时间 */
    @Excel(name = "托书操作创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "托书操作创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

    /**  发车前/发车后更改 */
    @Excel(name = " 修改状态 0发车前更改 1发车后更改")
    @ApiModelProperty(value = "修改状态 0发车前更改 1发车后更改")
    private String changeType;

    /**  查询类别 */
    @Excel(name = " 查询类别")
    @ApiModelProperty(value = "查询类别")
    private String searchType;

    /** 1有修改记录0没有修改记录 */
    @Excel(name = "1有修改记录0没有修改记录")
    @ApiModelProperty(value = "1有修改记录0没有修改记录")
    private String isupdate;

    /** 订舱方名称 */
    @Excel(name = "订舱方名称")
    @ApiModelProperty(value = "订舱方名称")
    private String clientUnit;

    /** 审核操作时间 */
    @Excel(name = "审核操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "审核操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date exameTime;

    /** 是否已读 0否 1是*/
    @Excel(name = "是否已读 0否 1是")
    @ApiModelProperty(value = "是否已读 0否 1是")
    private String isread;

    /** 驳回原因*/
    @Excel(name = "驳回原因")
    @ApiModelProperty(value = "驳回原因")
    private String refuseInfo;

    /** 箱管部驳回原因*/
    @Excel(name = "箱管部驳回原因")
    @ApiModelProperty(value = "箱管部驳回原因")
    private String xgRefuseInfo;

    /** 线路类型：0中欧 2中亚 3中越 4中俄 */
    @Excel(name = "线路类型：0中欧 2中亚 3中越 4中俄")
    private String lineTypeid;

    public void setXgRefuseInfo(String xgRefuseInfo)
    {
        this.xgRefuseInfo = xgRefuseInfo;
    }
    public String getXgRefuseInfo()
    {
        return xgRefuseInfo;
    }

    public void setRefuseInfo(String refuseInfo)
    {
        this.refuseInfo = refuseInfo;
    }
    public String getRefuseInfo()
    {
        return refuseInfo;
    }

    public void setIsread(String isread)
    {
        this.isread = isread;
    }
    public String getIsread()
    {
        return isread;
    }

    public void setExameTime(Date exameTime) 
    {
        this.exameTime = exameTime;
    }
    public Date getExameTime() 
    {
        return exameTime;
    }


    public void setExamId(String examId)
    {
        this.examId = examId;
    }

    public String getExamId()
    {
        return examId;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setExamDate(Date examDate) 
    {
        this.examDate = examDate;
    }

    public Date getExamDate() 
    {
        return examDate;
    }
    public void setExamInfo(String examInfo) 
    {
        this.examInfo = examInfo;
    }
    public String getExamInfo() 
    {
        return examInfo;
    }

    public void setEditRecord(String editRecord)
    {
        this.editRecord = editRecord;
    }
    public String getEditRecord()
    {
        return editRecord;
    }

    public void setExamSedemail(String examSedemail) 
    {
        this.examSedemail = examSedemail;
    }

    public String getExamSedemail() 
    {
        return examSedemail;
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
    public void setExamType(String examType) 
    {
        this.examType = examType;
    }

    public String getExamType() 
    {
        return examType;
    }

    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }
    public String getOrderNumber()
    {
        return orderNumber;
    }

    public void setClassDate(Date classDate)
    {
        this.classDate = classDate;
    }
    public Date getClassDate()
    {
        return classDate;
    }

    public void setClassDateFive(Date classDateFive)
    {
        this.classDateFive = classDateFive;
    }
    public Date getClassDateFive()
    {
        return classDateFive;
    }

    public void setClassDateFour(Date classDateFour)
    {
        this.classDateFour = classDateFour;
    }
    public Date getClassDateFour()
    {
        return classDateFour;
    }

    public void setClassDateEnd(Date classDateEnd)
    {
        this.classDateEnd = classDateEnd;
    }
    public Date getClassDateEnd()
    {
        return classDateEnd;
    }

    public void setClassEastandwest(String classEastandwest)
    {
        this.classEastandwest = classEastandwest;
    }
    public String getClassEastandwest()
    {
        return classEastandwest;
    }

    public void setIsexamline(String isexamline)
    {
        this.isexamline = isexamline;
    }
    public String getIsexamline()
    {
        return isexamline;
    }

    public void setShipOrderBinningway(String shipOrderBinningway)
    {
        this.shipOrderBinningway = shipOrderBinningway;
    }
    public String getShipOrderBinningway()
    {
        return shipOrderBinningway;
    }

    public void setIsconsolidation(String isconsolidation)
    {
        this.isconsolidation = isconsolidation;
    }
    public String getIsconsolidation()
    {
        return isconsolidation;
    }

    public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }
    public Date getCreatedate()
    {
        return createdate;
    }

    public void setChangeType(String changeType)
    {
        this.changeType = changeType;
    }
    public String getChangeType()
    {
        return changeType;
    }

    public void setSearchType(String searchType)
    {
        this.searchType = searchType;
    }
    public String getSearchType()
    {
        return searchType;
    }

    public void setIsupdate(String isupdate) 
    {
        this.isupdate = isupdate;
    }
    public String getIsupdate() 
    {
        return isupdate;
    }

    public void setClientUnit(String clientUnit)
    {
        this.clientUnit = clientUnit;
    }
    public String getClientUnit()
    {
        return clientUnit;
    }

    public void setLineTypeid(String lineTypeid)
    {
        this.lineTypeid = lineTypeid;
    }
    public String getLineTypeid()
    {
        return lineTypeid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("examId", getExamId())
            .append("orderId", getOrderId())
            .append("examDate", getExamDate())
            .append("examInfo", getExamInfo())
            .append("editRecord", getEditRecord())
            .append("examSedemail", getExamSedemail())
            .append("createuserid", getCreateuserid())
            .append("createusername", getCreateusername())
            .append("examType", getExamType())
            .append("remark", getRemark())
            .append("orderNumber", getOrderNumber())
            .append("classDate", getClassDate())
            .append("classDateFive", getClassDateFive())
            .append("classDateFour", getClassDateFour())
            .append("classDateEnd", getClassDateEnd())
            .append("classEastandwest", getClassEastandwest())
            .append("isexamline", getIsexamline())
            .append("shipOrderBinningway", getShipOrderBinningway())
            .append("isconsolidation", getIsconsolidation())
            .append("createdate", getCreatedate())
            .append("changeType", getChangeType())
            .append("searchType", getSearchType())
            .append("isupdate", getIsupdate())
            .append("clientUnit", getClientUnit())
            .append("exameTime", getExameTime())
            .append("isread", getIsread())
            .append("refuseInfo", getRefuseInfo())
            .append("xgRefuseInfo", getXgRefuseInfo())
            .append("lineTypeid", getLineTypeid())
            .toString();
    }
}
