package com.szhbl.project.track.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 运踪_班列站到站对象 track_train
 * 
 * @author szhbl
 * @date 2020-03-16
 */
@Data
public class TrackTrain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 班列id */
    private String classId;

    /** 实际班列日期 */
    @Excel(name = "班列日期",height = 66,width = 12)
    private String actualClassDate;

    //开行班列
    @Excel(name = "口岸",height = 66,width = 15)
    private String classBlockTrain;

    /** 运踪录入时间 */
    @Excel(name = "运踪日期",height = 66,width = 17)
    private String trackTime;

    /** 到达/在/驶离 */
    @Excel(name = "状态",readConverterExp = "0=到达/arrive in,1=驶离/depart from,2=在/in",height = 66,width = 11)
    private String stateValue;

    /** 当前位置 */
    @Excel(name = "位置",height = 66,width = 13)
    private String currentLocation;

    /** 当前位置距站点一距离 */
    @Excel(name = "站点1距离",height = 66,width = 21)
    private String stationOneDistance;

    /** 当前位置距站点二距离 */
    @Excel(name = "站点2距离",height = 66,width = 21)
    private String stationTwoDistance;

    /** 当前位置距站点三距离 */
    @Excel(name = "站点3距离",height = 66,width = 21)
    private String stationThrDistance;

    /** 当前位置距站点四距离 */
    @Excel(name = "站点4距离",height = 66,width = 21)
    private String stationFouDistance;

    //预计到达时间
    @Excel(name = "预计到达时间",height = 66,width = 21)
    private String exceptTime;

    //预计到达时间
    @Excel(name = "对标分析结果",height = 66,width = 21)
    private String analyseResult;

    /** 区域/亚太欧亚欧洲 */
    @Excel(name = "区域",readConverterExp = "0=亚太段,1=欧亚段,2=欧洲段",height = 66,width = 21)
    private String district;

    /** 备注 */
    @Excel(name = "备注/运务异常情况",height = 66,width = 26)
    private String exportRemark;

    /** 更新日期 */
    @Excel(name = "更新日期",dateFormat="yyyy-MM-dd HH:mm:ss",height = 66,width = 14,wrapText = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    //班列日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String classDate;

    //班列编号
    private String classBh;

    //班列号
    private String classNum;

    //去回程0去1回
    private String classEastAndWest;

    /** 站点一名字 */
    private String stationOneName;

    /** 站点二名字 */
    private String stationTwoName;

    /** 站点三名字 */
    private String stationThrName;

    /** 站点四名字 */
    private String stationFouName;

    /** 运踪内容 */
    private String trackContents;

    /** 预计最早到港时间 */
    private String exceptEarlyTime;

    /** 预计最晚到港时间 */
    private String exceptLastTime;

    //是否发送vip
    private Integer isVip;

    /** 线路类型：0中欧 2中亚 3中越 4中俄   0*/
    private String lineTypeId;

    /** 邮箱 */
    private String email;

    /** 委托书编号 */
    private String orderNum;

    /** 拼整箱  0整柜 1拼箱 */
    private String consolidationType;

    /** 删除标志0正常1删除 */
    private Integer delFlag;
    /** 换装站 change_port*/
    private String changePort;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("classId", getClassId())
            .append("trackContents", getTrackContents())
            .append("actualClassDate", getActualClassDate())
            .append("currentLocation", getCurrentLocation())
            .append("trackTime", getTrackTime())
            .append("exceptEarlyTime", getExceptEarlyTime())
            .append("exceptLastTime", getExceptLastTime())
            .append("stateValue", getStateValue())
            .append("district", getDistrict())
            .append("stationOneName", getStationOneName())
            .append("stationOneDistance", getStationOneDistance())
            .append("stationTwoName", getStationTwoName())
            .append("stationTwoDistance", getStationTwoDistance())
            .append("stationThrName", getStationThrName())
            .append("stationThrDistance", getStationThrDistance())
            .append("stationFouName", getStationFouName())
            .append("stationFouDistance", getStationFouDistance())
            .append("isVip", getIsVip())
            .append("classDate", getClassDate())
            .append("classEastAndWest", getClassEastAndWest())
            .append("lineTypeId", getLineTypeId())
            .append("classBh", getClassBh())
            .append("classBlockTrain", getClassBlockTrain())
            .append("email", getEmail())
            .append("orderNum", getOrderNum())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("classNum", getClassNum())
            .append("exceptTime", getExceptTime())
            .append("exportRemark", getExportRemark())
            .append("changePort", getChangePort())
            .toString();
    }
}
