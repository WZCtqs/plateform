package com.szhbl.project.documentcenter.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

/**
 * 提箱号表对象 doc_order_pickcon_no
 *
 * @author szhbl
 * @date 2020-07-04
 */
@Data
public class DocOrderPickconNo extends BaseEntity {
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
    private String containerNum;

    /**
     * 提醒号
     */
    @Excel(name = "提醒号")
    private String pickNum;

    private String type;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("classNum", getClassNum())
                .append("containerNum", getContainerNum())
                .append("pickNum", getPickNum())
                .toString();
    }
}
