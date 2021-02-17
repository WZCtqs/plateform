package com.szhbl.project.customerservice.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 问题反馈附件对象 problem_file
 * 
 * @author b
 * @date 2020-04-07
 */
public class ProblemFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String fileId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String problemId;

    /** 附件名 */
    @Excel(name = "附件名")
    private String fileName;

    /** 附件url */
    @Excel(name = "附件url")
    private String fileUrl;

    /** 0投诉文件1回复文件2补传资料 */
    @Excel(name = "0投诉文件1回复文件2补传资料")
    private String type;

    /** 删除标识 0未删除 1已删除 */
    private String delFlag;

    public void setFileId(String fileId) 
    {
        this.fileId = fileId;
    }

    public String getFileId() 
    {
        return fileId;
    }
    public void setProblemId(String problemId) 
    {
        this.problemId = problemId;
    }

    public String getProblemId() 
    {
        return problemId;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileUrl(String fileUrl) 
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() 
    {
        return fileUrl;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
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
            .append("fileId", getFileId())
            .append("problemId", getProblemId())
            .append("fileName", getFileName())
            .append("fileUrl", getFileUrl())
            .append("type", getType())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
