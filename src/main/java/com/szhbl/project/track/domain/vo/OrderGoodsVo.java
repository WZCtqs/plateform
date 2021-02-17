package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class OrderGoodsVo {//货品查询vo

    /**
     * 货物品名
     */
    private String goodsName;

    /**
     * HS编码
     */
    private String hsCode;

    /**
     * 往返0为去(西向) 1为回(东向）
     */
    private String classEastAndWest;

    /**
     * 委托书编号
     */
    private String orderNum;

    /**
     * 班列日期
     */
    private String classDate;

    /**
     *中文品名
     */
    private String goodsZhName;

    /**
     *英文品名
     */
    private String goodsEnName;

    //去程就是国内报关HS，国外清关HS
    //回程就是国内清关HS，国外报关HS

    /**
     *国内HS编码
     */
    private String inCode;

    /**
     *国外HS编码
     */
    private String outCode;

    /** 国内清关HS */
    private String goodsClearance;

    /** 国外清关HS */
    private String goodsOutClearance;

    /** 国内报关HS */
    private String goodsInReport;

    /** 国外报关HS */
    private String goodsReport;

}
