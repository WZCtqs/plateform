package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author : wangzhichao
 * @description : 国外场站-回程到货信息
 * @date: 2020-06-22 12:23
 */
@Data
public class GWCZArrivalGoods {
    private Long id;

    private String OrderId;

    private String ClassNumber;
    /**
     * 舱位号
     */
    private String OrderNumber;

    /**
     * 货物中文品名
     */
    private String GoodsChsName;

    /**
     * 货物英文品名
     */
    private String GoodsEnName;

    /**
     * 发货人
     */
    private String Shipper;

    /**
     * 收货人
     */
    private String Receiver;

    /**
     * 订舱方
     */
    private String BookCabin;

    /**
     * 包装
     */
    private String Packing;

    /**
     * 数量
     */
    private String GoodsNum;

    /**
     * 集装箱号
     */
    private String ContainerNum;

    /**
     * 货物尺寸
     */
    private String MeasuSpecifi;

    /**
     * 体积
     */
    private String MeasuVolum;

    /**
     * 毛重
     */
    private String MeasuGroWei;

    /**
     * 是否可堆叠
     */
    private String goods_cannotPileUp;

    /**
     * 到堆场时间
     */
    private String ArriveTime;

    /**
     * 实际到货
     */
    private String ArriveInfoactual;

    /**
     * 委托PB报关
     */
    private String CustomsType;

    /**
     * 测量尺寸
     */
    private String MeasureDimention;

    /**
     * 备注
     */
    private String Remark;

    /**
     * 跟单员
     */
    private String Merchandiser;

    /**
     * 业务员
     */
    private String Referrer;

    /**
     * 到货方式
     */
    private String DhType;

    private Date createTime;

    private String type;
    // 总重
    private String pxWeight;
    // 实际体积
    private String pxVolume;
    // 结算体积
    private String pxSettlementVolume;
}
