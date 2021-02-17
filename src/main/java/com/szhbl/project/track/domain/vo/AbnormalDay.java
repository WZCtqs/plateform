package com.szhbl.project.track.domain.vo;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

//异常箱日报导出vo
@Data
public class AbnormalDay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 班列日期  0*/
    @Excel(name="班列日期",height = 31,width = 12)
    private String classDate;

    /** 推荐人*/
    @Excel(name="推荐人",height = 31,width = 6)
    private String referee;

    /** 货物品名*/
    @Excel(name="货物品名",height = 31,width = 6)
    private String goodsName;

    /** 上下货站*/
    @Excel(name="上下货站",height = 31,width = 5)
    private String site;

    /** 箱型*/
    @Excel(name="箱型",height = 31,width = 5)
    private String boxType;

    /** 跟单员*/
    @Excel(name="跟单员",height = 31,width = 6)
    private String merchandiser;

    /** 箱号 */
    @Excel(name="箱号",height = 31,width = 11)
    private String boxNum;

    /**舱位号*/
    @Excel(name="舱位号",height = 31,width = 20)
    private String orderNum;

    /** 异常原因 */
    @Excel(name="异常原因",height = 31,width = 10)
    private String unloadReason;

    /** 异常发生地*/
    @Excel(name="异常发生地",height = 31,width = 8)
    private String abnormalSite;

    /** 异常发生时间*/
    @Excel(name="异常发生时间",height = 31,width = 15)
    private String unloadTime;

    /** 异常解决时间*/
    @Excel(name="异常解决时间",height = 31,width = 10)
    private String solveTime;

    /** 考核解决时效对比*/
    @Excel(name="考核解决时效对比",height = 31,width = 6)
    private String solveTimeCompare;

    /** 异常类型*/
    @Excel(name="异常类型",height = 31,width = 6)
    private String abnormalType;

    /** 对接责任人*/
    @Excel(name="对接责任人",height = 31,width = 6)
    private String chargePerson;

    /** 考核时间节点*/
    @Excel(name="考核时间节点",height = 31,width = 6)
    private String checkTime;

    /** 跟踪信息*/
    @Excel(name="跟踪信息",height = 31,width = 22)
    private String abnormalInformation;

    /** 是否脱离主班列0是1否 */
    @Excel(name="是否脱离主班列0是1否",readConverterExp ="0=是,1=否" ,height = 31,width = 4)
    private Integer isSeparate;

    /** 到站时间 */
    @Excel(name="到站时间",height = 31,width = 14,wrapText = true)
    private String arriveTime;

    /** 东向跟单员   回  */
    private String eMerchandiserId;
    /** 西向跟单员   去  */
    private String wMerchandiserId;
    /** 上货站     */
    private String uploadSite;
    /** 下货站     */
    private String unloadSite;
    /*** 0为去程 1为回程*/
    private String eastAndWest;
    /*** 始发站编号*/
    private String startBh;
    /*** 目的站编号*/
    private String endBh;
    /*** 班列编号*/
    private String classBh;

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
                ", arriveTime='" + arriveTime + '\'' +
                ", eMerchandiserId='" + eMerchandiserId + '\'' +
                ", wMerchandiserId='" + wMerchandiserId + '\'' +
                ", uploadSite='" + uploadSite + '\'' +
                ", unloadSite='" + unloadSite + '\'' +
                ", eastAndWest='" + eastAndWest + '\'' +
                ", startBh='" + startBh + '\'' +
                ", endBh='" + endBh + '\'' +
                ", classBh='" + classBh + '\'' +
                '}';
    }
}
