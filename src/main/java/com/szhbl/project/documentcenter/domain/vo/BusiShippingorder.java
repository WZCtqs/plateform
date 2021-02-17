package com.szhbl.project.documentcenter.domain.vo;

import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 订单对象 busi_shippingorder
 *
 * @author HP
 * @date 2020-01-14
 */
@Data
public class BusiShippingorder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String clientYwNumber;
    /**
     * 订单主键id
     */
    private String orderId;
    /**
     * 订单编号、舱位号
     */
    private String orderNumber;
    /**
     * 订单编号、舱位号
     */
    private String classEastAndWest;
    /**
     * 班列日期
     */
    private Date classDate;
    /**
     * 上货站
     */
    private String orderUploadsite;
    /**
     * 下货站
     */
    private String orderUnloadsite;

    /**
     * 跟单姓名
     */
    private String orderMerchandiser;

    private String orderMerchandiserId;

    /**
     * 客户单位
     */
    private String clientUnit;
    /**
     * 客户邮箱
     */
    private String clientEmail;

    /**
     * 是否报关
     */
    private String clientOrderBindingWay;

    /**
     * 是否拼箱
     */
    private String isConsolidation;

    /**
     * 箱量
     */
    private String containerBoxamount;

    /**
     * 箱型
     */
    private String containerType;

    /**
     * 发货方单位
     */
    private String shipOrederName;

    /**
     *收货单位
     */
    private String receiveOrderName;

    /**
     * 是否可堆叠（1是0否2仅可自叠）
     */
    private String goodsCannotpileup;
    /**
     * 唛头
     */
    private String goodsMark;
    /**
     * 货品名称
     */
    private String goodsName;
    /**
     * 最外层包装形式
     */
    private String goodsPacking;
    /**
     * 最外层包装数量
     */
    private String goodsNumber;
    /**
     * 规格
     */
    private String goodsStandard;
    /**
     * 重量
     */
    private String goodsKGS;
    /**
     * 体积
     */
    private String goodsCBM;
    /**
     *  车站id
     */
    private String stationID;
    /**
     *  车站名称
     */
    private String statioin;
    /**
     *  截港时间
     */
    private String cuntofftime;
    /**
     * 整箱地址
     */
    private String zxAddress;
    /**
     * 拼箱地址
     */
    private String pxAddress;
    /**
     * 车站地址
     */
    private String pxYstype;
    /**
     * 联系方式
     */
    private String contacts;

    private String clientTjr;

    // 整箱报关截港时间
    private String fclCustomsTime;

    // 整箱不报关截港时间
    private String fclCustomsNotTime;

    // 拼箱报关截港时间
    private String lclCustomsTime;

    // 拼箱不报关截港时间
    private String lclCustomsNotTime;

    @Override
    public String toString() {
        return "BusiShippingorder{" +
                "orderId='" + orderId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", classEastAndWest='" + classEastAndWest + '\'' +
                ", classDate=" + classDate +
                ", orderUploadsite='" + orderUploadsite + '\'' +
                ", orderUnloadsite='" + orderUnloadsite + '\'' +
                ", orderMerchandiser='" + orderMerchandiser + '\'' +
                ", clientUnit='" + clientUnit + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", clientOrderBindingWay='" + clientOrderBindingWay + '\'' +
                ", isConsolidation='" + isConsolidation + '\'' +
                ", containerBoxamount='" + containerBoxamount + '\'' +
                ", containerType='" + containerType + '\'' +
                ", shipOrederName='" + shipOrederName + '\'' +
                ", receiveOrderName='" + receiveOrderName + '\'' +
                ", goodsCannotpileup='" + goodsCannotpileup + '\'' +
                ", goodsMark='" + goodsMark + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPacking='" + goodsPacking + '\'' +
                ", goodsNumber='" + goodsNumber + '\'' +
                ", goodsStandard='" + goodsStandard + '\'' +
                ", goodsKGS='" + goodsKGS + '\'' +
                ", goodsCBM='" + goodsCBM + '\'' +
                ", stationID='" + stationID + '\'' +
                ", statioin='" + statioin + '\'' +
                ", cuntofftime='" + cuntofftime + '\'' +
                ", zxAddress='" + zxAddress + '\'' +
                ", pxAddress='" + pxAddress + '\'' +
                ", pxYstype='" + pxYstype + '\'' +
                ", contacts='" + contacts + '\'' +
                '}';
    }
}
