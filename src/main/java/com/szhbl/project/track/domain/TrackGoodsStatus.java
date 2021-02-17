package com.szhbl.project.track.domain;


import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel.Type;

import java.util.Date;

/**
 * 货物状态 track_goods_status
 *
 * @author szhbl
 * @date 2020-01-10
 */
@Data
public class TrackGoodsStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /*** id */
    private Long id;

    /*** 实际班列id*/
    private String actualClassId;

    /*** 订单id（舱位号id）*/
    private String orderId;

    /** 班列日期   0  1*/
    @Excel(name = "班列日期",type=Type.EXPORT)
    private String classDate;

    /** 推荐人     1*/
    @Excel(name = "推荐人",type=Type.EXPORT)
    private String referee;

    /** 货物品名     1*/
    @Excel(name = "货物品名",type=Type.EXPORT)
    private String goodsName;

    /** 站点     2*/
    @Excel(name = "上下货站",type=Type.EXPORT)
    private String site;

    /** 箱型     1*/
    @Excel(name = "箱型",type=Type.EXPORT)
    private String boxType;

    /** 跟单员     2*/
    @Excel(name = "跟单员",type=Type.EXPORT)
    private String merchandiser;

    /*** 箱号  0  1*/
    @Excel(name = "箱号")
    private String boxNum;

    /** 是否上车*/
    private Integer isGetin;

    /** 实际班列日期*/
    @Excel(name = "实际班列日期",type=Type.IMPORT)
    private String actualClassDate;

    /** 是否正常箱*/
    private Integer isNormal;

    /** 班列编号   0  1*/
    private String classNum;

    /**订单号（舱位号）  0  1*/
    @Excel(name = "舱位号")
    private String orderNum;

    /** 线路类型：0中欧 2中亚 3中越 4中俄   0 */
    private String lineTypeId;

    /** 东向跟单员   回  1*/
    private String eMerchandiserId;
    /** 西向跟单员   去  1*/
    private String wMerchandiserId;

    /** 上货站     1*/
    private String uploadSite;

    /** 下货站     1*/
    private String unloadSite;

    /*** 在途邮箱信息 1*/
    @Excel(name = "在途信息接收邮箱",type=Type.EXPORT)
    private String intransitMail;

    /*** 0为去程 1为回程*/
    private String eastAndWest;

    /*** 批量修改id*/
    private String ids;

    /**
     * 删除标志,0存在，1删除
     */
    private int  delFlag;


    /*** 计费体积*/
    private String goodsVolume;
    //is_balance是否偏载 0否 1是
    private String isBalance;
    //is_stable是否加固 0否 1是
    private String isStable;
    //in_station_date进站日期
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    private Date inStationDate;
    //in_station_time进站时间
    private String inStationTime;
    //in_space_date入场日期
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    private Date inSpaceDate;
    //in_space_time入场时间
    private String inSpaceTime;
    //abnormal_time 异常时间
    private Date abnormalTime;
    //batch_time 分批时间1
    private Date batchTime;
    //batch_time 分批实际班列日期1
    private String batchDate;
    //batch_time 分批时间2
    private Date batchTime2;
    //batch_time 分批实际班列日期2
    private String batchDate2;
    //batch_time 分批时间3
    private Date batchTime3;
    //batch_time 分批实际班列日期3
    private String batchDate3;
    //batch_time 分批时间4
    private Date batchTime4;
    //batch_time 分批实际班列日期4
    private String batchDate4;
    /** 箱型*/

    /** 封号 */
    private String sealNum;

    private String fromSystem;
    //是否随主班列0是1否
    private Integer withMain;
    //整拼箱
    private String isConsolidation;
    //订单类型 0国内 1国外
    private String orderType;
    //是否委托ZIH提货 ship_order_binningWay 委托ZIH提货 0是 1否  2铁路到货
    private String shipOrderBinningway;
    //是否委托ZIH送货 receive_order_isPart 是否由ZIH代理送货  0否 1是
    private String receiveOrderIspart;

    @Override
    public String toString() {
        return "TrackGoodsStatus{" +
                "id=" + id +
                ", actualClassId='" + actualClassId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", classDate='" + classDate + '\'' +
                ", referee='" + referee + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", site='" + site + '\'' +
                ", boxType='" + boxType + '\'' +
                ", merchandiser='" + merchandiser + '\'' +
                ", boxNum='" + boxNum + '\'' +
                ", isGetin=" + isGetin +
                ", actualClassDate='" + actualClassDate + '\'' +
                ", isNormal=" + isNormal +
                ", classNum='" + classNum + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", lineTypeId='" + lineTypeId + '\'' +
                ", eMerchandiserId='" + eMerchandiserId + '\'' +
                ", wMerchandiserId='" + wMerchandiserId + '\'' +
                ", uploadSite='" + uploadSite + '\'' +
                ", unloadSite='" + unloadSite + '\'' +
                ", intransitMail='" + intransitMail + '\'' +
                ", eastAndWest='" + eastAndWest + '\'' +
                ", ids='" + ids + '\'' +
                ", delFlag=" + delFlag +
                ", goodsVolume='" + goodsVolume + '\'' +
                ", isBalance='" + isBalance + '\'' +
                ", isStable='" + isStable + '\'' +
                ", inStationDate=" + inStationDate +
                ", inStationTime='" + inStationTime + '\'' +
                ", inSpaceDate=" + inSpaceDate +
                ", inSpaceTime='" + inSpaceTime + '\'' +
                ", sealNum='" + sealNum + '\'' +
                ", fromSystem='" + fromSystem + '\'' +
                ", abnormalTime='" + abnormalTime + '\'' +
                ", batchTime='" + batchTime + '\'' +
                '}';
    }
}
