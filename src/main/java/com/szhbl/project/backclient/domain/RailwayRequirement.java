package com.szhbl.project.backclient.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 铁路要求对象 railway_requirement
 *
 * @author szhbl
 * @date 2020-04-02
 */
public class RailwayRequirement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 中文内容 */
    @Excel(name = "中文内容")
    private String content;

    /** 英文内容 */
    @Excel(name = "英文内容")
    private String enContent;

    /** 0货物包装要求1集装箱使用要求2通用货物装箱加固规则3出口班列客户自备箱进展要求 */
    @Excel(name = "0货物包装要求1集装箱使用要求2通用货物装箱加固规则3出口班列客户自备箱进展要求")
    private String type;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 语言0中文1英文2俄语3德语 */
    @Excel(name = "语言0中文1英文2俄语3德语")
    private String language;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setEnContent(String enContent)
    {
        this.enContent = enContent;
    }

    public String getEnContent()
    {
        return enContent;
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
    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getLanguage()
    {
        return language;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("content", getContent())
                .append("enContent", getEnContent())
                .append("type", getType())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .append("language", getLanguage())
                .toString();
    }
}
