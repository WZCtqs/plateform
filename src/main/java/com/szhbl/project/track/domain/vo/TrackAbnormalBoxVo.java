package com.szhbl.project.track.domain.vo;

import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 异常箱查询vo
 * 
 * @author lzd
 * @date 2020-03-30
 */
@Data
public class TrackAbnormalBoxVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id*/
    private String id;

    /** 订单id*/
    private String orderId;

    /** 委托书编号 0*/
    private String orderNumber;

    /** 箱号 0*/
    private String boxNum;

    /** 班列日期  0*/
    private String classDate;

    /** 货物品名*/
    private String goodsName;

    /** 货物品名*/
    private String goodsEnName;

    /** 上货站 */
    private String upSite;

    /**往返 0去程1回程  0*/
    private String classEastAndWest;

    /** 整拼箱 0整柜 1拼箱 */
    private String consolidationType;

    /** 新增日期 0*/
    private String startTime;

    /** 新增日期 0*/
    private String endTime;

    /** 更新日期 0*/
    private String inputTime;

    /** 线路类型：0中欧 2中亚 3中越 4中俄   0*/
    private String lineTypeId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
