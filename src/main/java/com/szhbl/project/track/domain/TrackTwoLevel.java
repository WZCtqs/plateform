package com.szhbl.project.track.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 运踪二级节点对象 track_two_level
 * 
 * @author lzd
 * @date 2020-03-23
 */
@Data
public class TrackTwoLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单id */
    private String orderId;

    /** 一级节点id */
    private Long oneId;

    /** 是否客户端二级节点0是1否 */
    private String isCustom;

    /** 中文名字 */
    @Excel(name = "中文名字")
    private String nameZh;

    /** 英文名字 */
    @Excel(name = "英文名字")
    private String nameEn;

    /** 状态null未进行  1已进行  2异常 */
    @Excel(name = "状态null未进行  1已进行  2异常")
    private Integer state;

    /** 时间 */
    @Excel(name = "时间")
    private String time;

    /** 顺序 */
    private Integer sort;

    /** 备注英文 remark_en*/
    private String remarkEn;

    /** 删除标志0正常1删除 */
    private Integer delFlag;

    //来源系统
    private String fromSystem;

    @Override
    public String toString() {
        return "TrackTwoLevel{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", oneId=" + oneId +
                ", isCustom='" + isCustom + '\'' +
                ", nameZh='" + nameZh + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", state=" + state +
                ", time='" + time + '\'' +
                ", sort=" + sort +
                ", remarkEn=" + remarkEn +
                ", delFlag=" + delFlag +
                ", fromSystem='" + fromSystem + '\'' +
                '}';
    }
}
