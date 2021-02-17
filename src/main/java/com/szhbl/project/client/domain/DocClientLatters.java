package com.szhbl.project.client.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.web.domain.BaseEntity;
import com.szhbl.project.client.VO.ProblemFileVo;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 客户维护长期电放保函对象 doc_client_latters
 *
 * @author szhbl
 * @date 2020-10-01
 */
public class DocClientLatters extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 客户id
     */
    private String clientId;

    /**
     * 电放保函文件名
     */
    private String latterName;

    /**
     * 电放保函url连接
     */
    private String latterUrl;

    /**
     * 有效开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date activeStartTime;

    /**
     * 有效结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date activeEndTime;

    /**
     * 删除标志
     */
    private Integer delFlag;

    /**
     * 上传时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadTime;

    private List<ProblemFileVo> urls;

    public List<ProblemFileVo> getUrls() {
        return urls;
    }

    public void setUrls(List<ProblemFileVo> urls) {
        this.urls = urls;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setLatterName(String latterName) {
        this.latterName = latterName;
    }

    public String getLatterName() {
        return latterName;
    }

    public void setLatterUrl(String latterUrl) {
        this.latterUrl = latterUrl;
    }

    public String getLatterUrl() {
        return latterUrl;
    }

    public Date getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(Date activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public Date getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(Date activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("clientId", getClientId())
                .append("latterName", getLatterName())
                .append("latterUrl", getLatterUrl())
                .append("startTime", getActiveStartTime())
                .append("endTime", getActiveEndTime())
                .append("delFlag", getDelFlag())
                .append("uploadTime", getUploadTime())
                .toString();
    }
}
