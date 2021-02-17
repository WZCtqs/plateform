package com.szhbl.project.backclient.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 轮播图对象 loop_image
 * 
 * @author szhbl
 * @date 2020-01-07
 */
public class LoopImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String picture;

    /** 跳转链接 */
    @Excel(name = "跳转链接")
    private String url;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 是否显示0是1否 */
    @Excel(name = "是否显示0是1否")
    private String isDisplay;

    /** 顺序 */
    @Excel(name = "顺序")
    private String order;

    /** 语言0中文1英文 */
    @Excel(name = "语言0中文1英文")
    private String language;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPicture(String picture) 
    {
        this.picture = picture;
    }

    public String getPicture() 
    {
        return picture;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
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
            .append("picture", getPicture())
            .append("url", getUrl())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("isDisplay", getIsDisplay())
            .append("order", getOrder())
            .append("language", getLanguage())
            .toString();
    }
}
