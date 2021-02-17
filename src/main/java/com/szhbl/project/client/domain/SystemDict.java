package com.szhbl.project.client.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 推荐人管理对象 system_dict
 * 
 * @author szhbl
 * @date 2020-01-07
 */
public class SystemDict extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 父id */
    @Excel(name = "父id")
    private Long fid;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;

    /** 名称 */
    @Excel(name = "名称")
    private String mcheng;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 排序 */
    @Excel(name = "排序")
    private String sort;

    /** 0  中文  1 英文 */
    @Excel(name = "0  中文  1 英文")
    private String yuyan;

    /** $column.columnComment */
    @Excel(name = "0  中文  1 英文")
    private String westMerchandiser;

    /** $column.columnComment */
    @Excel(name = "0  中文  1 英文")
    private Long westMerchandiserId;

    /** $column.columnComment */
    @Excel(name = "0  中文  1 英文")
    private String eastMerchandiser;

    /** $column.columnComment */
    @Excel(name = "0  中文  1 英文")
    private Long eastMerchandiserId;

    /** 邮箱以分号隔开 */
    @Excel(name = "邮箱以分号隔开")
    private String mails;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFid(Long fid) 
    {
        this.fid = fid;
    }

    public Long getFid() 
    {
        return fid;
    }
    public void setAncestors(String ancestors) 
    {
        this.ancestors = ancestors;
    }

    public String getAncestors() 
    {
        return ancestors;
    }
    public void setMcheng(String mcheng) 
    {
        this.mcheng = mcheng;
    }

    public String getMcheng() 
    {
        return mcheng;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setSort(String sort) 
    {
        this.sort = sort;
    }

    public String getSort() 
    {
        return sort;
    }
    public void setYuyan(String yuyan) 
    {
        this.yuyan = yuyan;
    }

    public String getYuyan() 
    {
        return yuyan;
    }
    public void setWestMerchandiser(String westMerchandiser) 
    {
        this.westMerchandiser = westMerchandiser;
    }

    public String getWestMerchandiser() 
    {
        return westMerchandiser;
    }
    public void setWestMerchandiserId(Long westMerchandiserId) 
    {
        this.westMerchandiserId = westMerchandiserId;
    }

    public Long getWestMerchandiserId() 
    {
        return westMerchandiserId;
    }
    public void setEastMerchandiser(String eastMerchandiser) 
    {
        this.eastMerchandiser = eastMerchandiser;
    }

    public String getEastMerchandiser() 
    {
        return eastMerchandiser;
    }
    public void setEastMerchandiserId(Long eastMerchandiserId) 
    {
        this.eastMerchandiserId = eastMerchandiserId;
    }

    public Long getEastMerchandiserId() 
    {
        return eastMerchandiserId;
    }
    public void setMails(String mails) 
    {
        this.mails = mails;
    }

    public String getMails() 
    {
        return mails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fid", getFid())
            .append("ancestors", getAncestors())
            .append("mcheng", getMcheng())
            .append("state", getState())
            .append("sort", getSort())
            .append("yuyan", getYuyan())
            .append("westMerchandiser", getWestMerchandiser())
            .append("westMerchandiserId", getWestMerchandiserId())
            .append("eastMerchandiser", getEastMerchandiser())
            .append("eastMerchandiserId", getEastMerchandiserId())
            .append("mails", getMails())
            .toString();
    }
}
