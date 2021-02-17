package com.szhbl.project.inquiry.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * 询价结果费用修改记录对象 booking_inquiry_result_record
 * 
 * @author szhbl
 * @date 2021-01-20
 */
public class BookingInquiryResultRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 修改记录id */
    private Long id;

    /** 询价id */
    @Excel(name = "询价id")
    private Long inquiryId;

    /** 询价结果id */
    @Excel(name = "询价结果id")
    private Long inquiryResultId;

    /** 修改记录 */
    @Excel(name = "修改记录")
    private String changeRecord;

    /** 操作用户id */
    @Excel(name = "操作用户id")
    private Long userId;

    private String userName;
    private Date inquiryTime;
    private String clientUnit;

    /** 部门编号*/
    private String deptCode;

    /** 变更时间 */
    @Excel(name = "变更时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date changeTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInquiryId(Long inquiryId) 
    {
        this.inquiryId = inquiryId;
    }

    public Long getInquiryId() 
    {
        return inquiryId;
    }
    public void setInquiryResultId(Long inquiryResultId) 
    {
        this.inquiryResultId = inquiryResultId;
    }

    public Long getInquiryResultId() 
    {
        return inquiryResultId;
    }
    public void setChangeRecord(String changeRecord) 
    {
        this.changeRecord = changeRecord;
    }

    public String getChangeRecord() 
    {
        return changeRecord;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setChangeTime(Date changeTime) 
    {
        this.changeTime = changeTime;
    }

    public Date getChangeTime() 
    {
        return changeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inquiryId", getInquiryId())
            .append("inquiryResultId", getInquiryResultId())
            .append("changeRecord", getChangeRecord())
            .append("userId", getUserId())
            .append("changeTime", getChangeTime())
            .toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Date getInquiryTime() {
        return inquiryTime;
    }

    public void setInquiryTime(Date inquiryTime) {
        this.inquiryTime = inquiryTime;
    }

    public String getClientUnit() {
        return clientUnit;
    }

    public void setClientUnit(String clientUnit) {
        this.clientUnit = clientUnit;
    }
}
