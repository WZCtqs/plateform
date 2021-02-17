package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class TrackIntransitMailVo {//在途邮箱查询vo

    /**
     * 订单表id
     */
    private String orderId;

    /**
     * 舱位号
     */
    private String orderNum;

      /**
     * 在途邮箱信息
     */
    private String intransitMail;

}
