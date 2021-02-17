package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class GwTrackTrainVo {//关务班列运踪vo

    // Data STATEVALUE.Split('/')[1]，EN_LOCATION，ARRIVETIMESTART，ARRIVETIMEEND，INFO_DATE
    //"DataAbnormal":"LOGGIN_ABNORMALINFO":"","UNLOAD_RE":"","LOGGING_DATE":""

    /*** 班列id*/
    private String CLASS_ID;

    /*** 状态state*/
    private String  STATEVALUE;

    /*** 地理位置/当前位置*/
    private String EN_LOCATION;

    /** 预计最早到港时间  exceptEarlyTime*/
    private String ARRIVETIMESTART;

    /** 预计最晚到港时间  exceptLastTime*/
    private String ARRIVETIMEEND;

    /*** 运踪时间*/
    private String INFO_DATE;


    /** 异常信息*/
    private String LOGGIN_ABNORMALINFO;
    /** 下货原因*/
    private String UNLOAD_RE;
    /** 跟踪日期*/
    private String LOGGING_DATE;
}
