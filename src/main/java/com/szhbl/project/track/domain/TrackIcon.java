package com.szhbl.project.track.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 运踪图标对象 track_icon
 * 
 * @author szhbl
 * @date 2020-03-20
 */
public class TrackIcon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 类型0去程1回程 */
    @Excel(name = "类型0去程1回程")
    private Integer goCome;

    /** 整拼箱 0整柜 1拼箱 */
    @Excel(name = "整拼箱 0整柜 1拼箱")
    private Integer consolidationType;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 中文名字 */
    @Excel(name = "中文名字")
    private String nameZh;

    /** 英文名字 */
    @Excel(name = "英文名字")
    private String nameEn;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 删除标志0正常1删除 */
    private Integer delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGoCome(Integer goCome) 
    {
        this.goCome = goCome;
    }

    public Integer getGoCome() 
    {
        return goCome;
    }
    public void setConsolidationType(Integer consolidationType) 
    {
        this.consolidationType = consolidationType;
    }

    public Integer getConsolidationType() 
    {
        return consolidationType;
    }
    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }
    public void setNameZh(String nameZh) 
    {
        this.nameZh = nameZh;
    }

    public String getNameZh() 
    {
        return nameZh;
    }
    public void setNameEn(String nameEn) 
    {
        this.nameEn = nameEn;
    }

    public String getNameEn() 
    {
        return nameEn;
    }
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("goCome", getGoCome())
            .append("consolidationType", getConsolidationType())
            .append("icon", getIcon())
            .append("nameZh", getNameZh())
            .append("nameEn", getNameEn())
            .append("sort", getSort())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
