package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class TrackGoodsStatusVo {//货物状态表查询vo

    /**
     * 委托书编号
     */
    private String orderNum;

    /**
     * 箱号
     */
    private String boxNum;

    /**
     * 实际班列日期
     */
    private String actualClassDate;

      /**
     * 班列日期
     */
    private String classDate;

    /**
     * 路线
     */
    private int lineType;


}
