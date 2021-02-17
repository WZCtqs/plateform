package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class TrackJsVo {//接收集疏运踪消息队列vo

    //      订单id
        private String orderId;
    //      订单编号
        private String orderNumber;
    //      箱号
        private String containerNo;
    //      车牌号
        private String plateNumber;
    //      预计提货时间
        private String planPickTime;
    //      提货时间
        private String realPickTime;
    //      预计送达时间
        private String planArriveTime;
    //      送达时间
        private String realArriveTime;
    //      还箱时间
        private String realReturnTime;
    //      签收时间
        private String podDate;
    //      回程提箱时间
        private String pickupBoxTime;
    //      运踪对接人
    private String charge;
}
