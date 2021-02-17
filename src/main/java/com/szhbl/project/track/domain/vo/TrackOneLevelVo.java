package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class TrackOneLevelVo {//一级运踪查询结果vo

    /** 委托书编号 */
    private String orderNum;

    /** 更新时间 */
    private String updateTime;

    /** 预计到达时间 */
    private String exceptTime;

    /** 发货地 上货站*/
    private String startAddress;

    /** 收货地 下货站*/
    private String endAddress;

    /** 下货站编码 */
    private String endAddressCode;

}
