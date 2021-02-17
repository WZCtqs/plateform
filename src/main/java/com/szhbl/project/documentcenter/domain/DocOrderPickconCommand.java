package com.szhbl.project.documentcenter.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

/**
 * 提箱指令对象 doc_order_pickcon_command
 *
 * @author szhbl
 * @date 2020-08-13
 */
public class DocOrderPickconCommand extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 班列编号
     */
    @Excel(name = "班列编号")
    private String classNum;

    /**
     * 箱号
     */
    @Excel(name = "箱号")
    private String containerNo;

    /**
     * 文件名
     */
    @Excel(name = "文件名")
    private String fileName;

    /**
     * 文件地址
     */
    @Excel(name = "文件地址")
    private String fileUrl;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("classNum", getClassNum())
                .append("containerNo", getContainerNo())
                .append("fileName", getFileName())
                .append("fileUrl", getFileUrl())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
