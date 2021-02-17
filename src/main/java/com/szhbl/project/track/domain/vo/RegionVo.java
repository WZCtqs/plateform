package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class RegionVo {//计算市级城市到郑州和武汉的距离vo

    //      订单id
        private String id;
    //      订单编号
        private String mername;
    //      箱号
        private String zzDistance;

        private String whDistance;
        private String zzFees;
        private String whFees;
}
