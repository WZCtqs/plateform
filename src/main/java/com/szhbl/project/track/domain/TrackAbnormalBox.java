package com.szhbl.project.track.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 运踪_异常箱对象 track_abnormal_box
 * 
 * @author lzd
 * @date 2020-03-30
 */
@Data
public class TrackAbnormalBox extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 订单id（舱位号） */
    private String orderId;

    /** 箱号 */
    private String boxNum;

    /** 下货原因 */
    private String unloadReason;

    /** 下货时间 */
    private String unloadTime;

    /** 解决时间 */
    private String solveTime;

    /** 加挂班列编号 */
    private String classNum;

    /** 是否脱离主班列0是1否 */
    private Integer isSeparate;

    /** 是否到站0到站1未到站 */
    private Integer isArrive;

    /** 异常类型 */
    private String abnormalType;

    /** 下货地点 */
    private String unloadSite;

    /** 跟踪时间 */
    private String inputTime;

    /** 对接负责人 */
    private String chargePerson;

    /** 真实原因 */
    private String realReason;

    /** 到站时间 */
    private String arriveTime;

    /** 查验类型0正常查验1异常查验 */
    private Integer inspectionType;

    /** 跟踪内容 */
    private String abnormalInformation;

    /** 收件邮箱 */
    private String receiveEmails;

    /** 密送邮箱 */
    private String secretEmails;

    /** 班列日期  0*/
    private String classDate;

    /** 去回程  0去1回*/
    private String goCome;

    /** 订单编号  */
    private String orderNum;
    /** 删除标志0正常1删除 */
    private Integer delFlag;

    //文件地址
    private String filePath;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("boxNum", getBoxNum())
            .append("unloadReason", getUnloadReason())
            .append("unloadTime", getUnloadTime())
            .append("solveTime", getSolveTime())
            .append("classNum", getClassNum())
            .append("isSeparate", getIsSeparate())
            .append("isArrive", getIsArrive())
            .append("abnormalType", getAbnormalType())
            .append("unloadSite", getUnloadSite())
            .append("inputTime", getInputTime())
            .append("chargePerson", getChargePerson())
            .append("realReason", getRealReason())
            .append("arriveTime", getArriveTime())
            .append("inspectionType", getInspectionType())
            .append("abnormalInformation", getAbnormalInformation())
            .append("receiveEmails", getReceiveEmails())
            .append("secretEmails", getSecretEmails())
            .append("classDate", getClassDate())
            .append("goCome", getGoCome())
            .append("orderNum", getOrderNum())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
