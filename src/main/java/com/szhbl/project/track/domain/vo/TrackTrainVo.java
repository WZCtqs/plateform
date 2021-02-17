package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class TrackTrainVo {//班列运踪vo

    /**
     * 班列id
     */
    private String classId;

    /**
     * 开行班列
     */
    private String classBlockTrain;

    /**
     * 班列日期
     */
    private String classSTime;

    /**
     * 始发站
     */
    private String classStationOfDeparture;

    /**
     * 目的站
     */
    private String classStationOfDestination;

    /**
     * 线路类型：0是中欧2是中亚3是中越4是中俄
     */
    private String lineTypeid;

    /**
     * 线路类型：0是中欧2是中亚3是中越4是中俄
     */
    private String[] lineTypeids;

    /**
     * 班列编号
     */
    private String classBh;

    /**
     * 班列号
     */
    private String classNumber;

    /**
     * 运踪时间
     */
    private String distributionTime;

    /**
     * 班列类型
     */
    private String classClassType;

    /**
     * 往返0为去(西向) 1为回(东向）
     */
    private String classEastAndWest;

    /**
     * 到站时间
     */
    private String classETime;

    /**
     *当天是否录入运踪0已录入1未录入
     */
    private Integer isEdit;

}
