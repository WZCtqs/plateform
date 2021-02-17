package com.szhbl.project.track.domain.vo;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

//异常箱年报导出vo
@Data
public class AbnormalYear extends BaseEntity
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

    /** 上下货站*/
    @Excel(name="上下货站")
    private String site;

    /** 箱型*/
    @Excel(name="箱型")
    private String boxType;

    /** 跟单员*/
    @Excel(name="跟单员")
    private String merchandiser;

    /** 箱号 */
    @Excel(name="箱号")
    private String boxNum;

    /**舱位号*/
    @Excel(name="舱位号")
    private String orderNum;

    /** 异常原因 */
    @Excel(name="异常原因")
    private String unloadReason;

    /** 异常发生地*/
    @Excel(name="异常发生地")
    private String abnormalSite;

    /** 异常发生时间*/
    @Excel(name="异常发生时间")
    private String unloadTime;

    /** 异常解决时间*/
    @Excel(name="异常解决时间")
    private String solveTime;

    /** 考核解决时效对比*/
    @Excel(name="考核解决时效对比")
    private String solveTimeCompare;

    /** 异常类型*/
    @Excel(name="异常类型")
    private String abnormalType;

    /** 对接责任人*/
    @Excel(name="对接责任人")
    private String chargePerson;

    /** 考核时间节点*/
    @Excel(name="考核时间节点")
    private String checkTime;

    /** 跟踪信息*/
    @Excel(name="跟踪信息")
    private String abnormalInformation;

    /** 是否脱离主班列0是1否 */
    @Excel(name="是否脱离主班列0是1否")
    private Integer isSeparate;




    @Override
    public String toString() {
        return "TrackAbnormalBoxVo{" +
                "classDate='" + classDate + '\'' +
                ", referee='" + referee + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", site='" + site + '\'' +
                ", boxType='" + boxType + '\'' +
                ", merchandiser='" + merchandiser + '\'' +
                ", boxNum='" + boxNum + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", unloadReason='" + unloadReason + '\'' +
                ", abnormalSite='" + abnormalSite + '\'' +
                ", unloadTime='" + unloadTime + '\'' +
                ", solveTime='" + solveTime + '\'' +
                ", solveTimeCompare='" + solveTimeCompare + '\'' +
                ", abnormalType='" + abnormalType + '\'' +
                ", chargePerson='" + chargePerson + '\'' +
                ", checkTime='" + checkTime + '\'' +
                ", abnormalInformation='" + abnormalInformation + '\'' +
                ", isSeparate=" + isSeparate +
                '}';
    }
}
