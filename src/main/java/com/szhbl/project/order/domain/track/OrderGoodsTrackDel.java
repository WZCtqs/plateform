package com.szhbl.project.order.domain.track;

import lombok.Data;

@Data
public class OrderGoodsTrackDel {
    //货物状态表id
    private Integer id;

    /** 订单id */
    private String orderId;

    /** 箱号 */
    private String boxNum;

    /** 删除类别 0排舱模块(以箱子和托书id为条件) */
    private String delType;
}
