package com.szhbl.project.backclient.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 新闻信息对象 news_message
 * 
 * @author szhbl
 * @date 2020-01-07
 */
public class NewsMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 摘要 */
    @Excel(name = "摘要")
    private String abstracts;

    /** 内容 */
    @Excel(name = "内容")
    private String concent;

    /** 形象图 */
    @Excel(name = "形象图")
    private String picture;

    /** 0班列公告1班列新闻2开行时刻 */
    @Excel(name = "0班列公告1班列新闻2开行时刻")
    private String type;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 是否显示0是1否 */
    @Excel(name = "是否显示0是1否")
    private String isDisplay;

    /** 是否置顶0是1否 */
    @Excel(name = "是否置顶0是1否")
    private String isTop;

    /** 顺序 */
    @Excel(name = "顺序")
    private String order;

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
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getAbstracts()
    {
        return abstracts;
    }
    public void setConcent(String concent) 
    {
        this.concent = concent;
    }

    public String getConcent() 
    {
        return concent;
    }
    public void setPicture(String picture) 
    {
        this.picture = picture;
    }

    public String getPicture() 
    {
        return picture;
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
    public void setIsDisplay(String isDisplay) 
    {
        this.isDisplay = isDisplay;
    }

    public String getIsDisplay() 
    {
        return isDisplay;
    }
    public void setIsTop(String isTop) 
    {
        this.isTop = isTop;
    }

    public String getIsTop() 
    {
        return isTop;
    }
    public void setOrder(String order) 
    {
        this.order = order;
    }

    public String getOrder() 
    {
        return order;
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
            .append("title", getTitle())
            .append("abstract", getAbstracts())
            .append("concent", getConcent())
            .append("picture", getPicture())
            .append("type", getType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("isDisplay", getIsDisplay())
            .append("isTop", getIsTop())
            .append("order", getOrder())
            .append("language", getLanguage())
            .toString();
    }
}
