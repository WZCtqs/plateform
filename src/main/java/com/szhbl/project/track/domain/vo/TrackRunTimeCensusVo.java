package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class TrackRunTimeCensusVo {//班列运行统计查询vo

    private String classId;

    /** 线路类型：0中欧 2中亚 3中越 4中俄   0*/
    private String lineTypeId;

    /*** 开行班列*/
    private String classBlockTrain;

    /*** 往返0为去(西向) 1为回(东向）*/
    private String classEastAndWest;

    /*** 班列日期 开始*/
    private String startTime;

    /*** 班列日期 结束*/
    private String endTime;

    /** 目的站名称 */
    private String classStationofdestinationName;

    /*** 班列编号*/
    private String classBh;

    /*** 班列号*/
    private String classNum;

    /** 编辑状态 */
    private Integer isEdit;

    /*** 始发站*/
    private String startStation;

    /*** 开车时间*/
    private String classSTime;

    /** 到站时间 */
    private String classETime;

    /** 目的站英文名 */
    private String endNameEn;

}
