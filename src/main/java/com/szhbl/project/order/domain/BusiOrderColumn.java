package com.szhbl.project.order.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description : 自送货托书信息查询
 * @Author : wangzhichao
 * @Date: 2020-12-23 14:47
 */
@Data
public class BusiOrderColumn implements Serializable {

    //托书id
    private String orderId;
    //舱位号
    private String orderNumber;
    //去回程
    private String classEastandwest;
    //整拼箱
    private String isConsolidation;
    // 箱量
    private Integer containerCount;
    //是否委托zih提货
    private String shipOrderBinningWay;
    // 托书状态
    private String examline;

}
