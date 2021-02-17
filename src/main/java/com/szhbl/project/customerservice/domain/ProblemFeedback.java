package com.szhbl.project.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.project.customerservice.vo.FileVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 * 问题反馈对象 problem_feedback
 * 
 * @author b
 * @date 2020-04-07
 */
public class ProblemFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String problemId;

    /** $column.columnComment */

    private String clientId;

    /** 委托书编号 */
    @Excel(name = "委托书编号")
    private String orderNumber;

    /** 集装箱号 */
    @Excel(name = "集装箱号")
    private String containerNumber;

    /** 是否运费支付0是1否 */
    @Excel(name = "是否运费支付0是1否")
    private String isPay;

    /** 状态 0处理中 1已回复 2已结案 */
    @Excel(name = "状态 0处理中 1已回复 2已结案")
    private String status;

    /** 签收时间 */

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date signTime;
    /** 联系邮箱 */
    @Excel(name = "联系邮箱")
    private String email;

    /** 投诉内容 */
    @Excel(name = "投诉内容")
    private String problemContent;

    /** 客户要求 */
    @Excel(name = "客户要求")
    private String requirement;

    /** 投诉反馈 */
    @Excel(name = "投诉反馈")
    private String feedback;

    /** 删除标识 0未删除 1已删除 */
    private String delFlag;

    /**
     * 数据权限查询条件
     */
    private String deptCode;
    private String readType;
    private String userId;
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private List<FileVo> list;

    private String clientUnit;

    public String getClientUnit() {
        return clientUnit;
    }

    public void setClientUnit(String clientUnit) {
        this.clientUnit = clientUnit;
    }

    public List<FileVo> getList() {
        return list;
    }

    public void setList(List<FileVo> list) {
        this.list = list;
    }

    public void setProblemId(String problemId)
    {
        this.problemId = problemId;
    }

    public String getProblemId() 
    {
        return problemId;
    }
    public void setClientId(String clientId) 
    {
        this.clientId = clientId;
    }

    public String getClientId() 
    {
        return clientId;
    }
    public void setOrderNumber(String orderNumber) 
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() 
    {
        return orderNumber;
    }
    public void setContainerNumber(String containerNumber) 
    {
        this.containerNumber = containerNumber;
    }

    public String getContainerNumber() 
    {
        return containerNumber;
    }
    public void setIsPay(String isPay) 
    {
        this.isPay = isPay;
    }

    public String getIsPay() 
    {
        return isPay;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setSignTime(Date signTime) 
    {
        this.signTime = signTime;
    }

    public Date getSignTime() 
    {
        return signTime;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setProblemContent(String problemContent) 
    {
        this.problemContent = problemContent;
    }

    public String getProblemContent() 
    {
        return problemContent;
    }
    public void setRequirement(String requirement) 
    {
        this.requirement = requirement;
    }

    public String getRequirement() 
    {
        return requirement;
    }
    public void setFeedback(String feedback) 
    {
        this.feedback = feedback;
    }

    public String getFeedback() 
    {
        return feedback;
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
            .append("problemId", getProblemId())
            .append("clientId", getClientId())
            .append("orderNumber", getOrderNumber())
            .append("containerNumber", getContainerNumber())
            .append("isPay", getIsPay())
            .append("status", getStatus())
            .append("signTime", getSignTime())
            .append("email", getEmail())
            .append("problemContent", getProblemContent())
            .append("requirement", getRequirement())
            .append("feedback", getFeedback())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
