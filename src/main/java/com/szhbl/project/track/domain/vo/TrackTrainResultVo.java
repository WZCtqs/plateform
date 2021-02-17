package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class TrackTrainResultVo {//运踪查询页面最下面班列运踪数据vo

    /*** 班列id*/
    private String classId;

    /*** 运踪时间*/
    private String trackTime;

    /***更新时间*/
    private String updateTime;

    /*** 状态*/
    private String state;

    /*** 地理位置/当前位置*/
    private String currentLocation;

    /** 站点一名字 */
    private String stationOneName;

    /** 当前位置距站点一距离 */
    private String stationOneDistance;

    /** 站点二名字 */
    private String stationTwoName;

    /** 当前位置距站点二距离 */
    private String stationTwoDistance;

    /** 站点三名字 */
    private String stationThrName;

    /** 当前位置距站点三距离 */
    private String stationThrDistance;

    /** 站点四名字 */
    private String stationFouName;

    /** 当前位置距站点四距离 */
    private String stationFouDistance;

    /** 到站时间 */
    private String exceptTime;

    //出入境日期
    private String portDate;

    //换装日期
    private String changeDate;

    //换装车号
    private String changeNum;

    //运踪备注
    private String remark;

    //去回程
    private String goCome;

    //开行班列
    private String block;

    //站点中
    private String chPort;

    //站点英
    private String enPort;

    //线路类型0是中欧2是中亚3是中越4是中俄
    private String lineType;

    //中亚境外运踪内容
    private String abroadContents;
}