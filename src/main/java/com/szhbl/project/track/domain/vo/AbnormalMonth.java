package com.szhbl.project.track.domain.vo;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

//异常箱周报/月报导出vo
@Data
public class AbnormalMonth extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 班列日期  0*/
    @Excel(name="班列日期")
    private String classDate;

    /** 推荐人*/
    @Excel(name="推荐人")
    private String referee;

    /** 货物品名*/
    @Excel(name="货物品名")
    private String goodsName;

    /** 箱号 */
    @Excel(name="箱号")
    private String boxNum;

    /** 异常原因 */
    @Excel(name="异常原因")
    private String unloadReason;

    /** 异常发生地*/
    @Excel(name="异常发生地")
    private String abnormalSite;

    /** 异常发生时间*/
    @Excel(name="异常发生时间")
    private String unloadTime;

    /** 异常类型*/
    @Excel(name="异常类型")
    private String abnormalType;

    /** 跟踪信息*/
    @Excel(name="解决对策及当前进度")
    private String abnormalInformation;

    /** 责任部门*/
    @Excel(name="责任部门")
    private String chargeDept;

    @Override
    public String toString() {
        return "TrackAbnormalBoxVo{" +
                "classDate='" + classDate + '\'' +
                ", referee='" + referee + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", boxNum='" + boxNum + '\'' +
                ", unloadReason='" + unloadReason + '\'' +
                ", abnormalSite='" + abnormalSite + '\'' +
                ", unloadTime='" + unloadTime + '\'' +
                ", abnormalType='" + abnormalType + '\'' +
                ", abnormalInformation='" + abnormalInformation + '\'' +
                ", chargeDept='" + chargeDept + '\'' +
                '}';
    }
}
