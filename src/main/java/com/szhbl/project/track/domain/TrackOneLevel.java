package com.szhbl.project.track.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 运踪一级节点对象 track_one_level
 * 
 * @author lzd
 * @date 2020-03-23
 */
@Data
public class TrackOneLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单id */
    private String orderId;

    /** 中文名字 */
    @Excel(name = "中文名字")
    private String nameZh;

    /** 英文名字 */
    @Excel(name = "英文名字")
    private String nameEn;

    /** 状态null未进行  1已进行  2异常 */
    @Excel(name = "状态null未进行  1已进行  2异常")
    private Integer state;

    /** 节点时间 */
    @Excel(name = "节点时间")
    private String time;

    /** 顺序 */
    private Integer sort;

    /** 图标 */
    private String icon;

    /** 删除标志0正常1删除 */
    private Integer delFlag;
    //来源系统
    private String fromSystem;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("nameZh", getNameZh())
            .append("nameEn", getNameEn())
            .append("state", getState())
            .append("time", getTime())
            .append("sort", getSort())
            .append("icon", getIcon())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("fromSystem", getFromSystem())
            .toString();
    }
}
