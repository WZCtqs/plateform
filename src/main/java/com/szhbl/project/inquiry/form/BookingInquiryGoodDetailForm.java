package com.szhbl.project.inquiry.form;

import com.szhbl.common.annotation.PropertyMsg;
import lombok.Data;

import java.util.Date;

@Data
public class BookingInquiryGoodDetailForm {
    /** 主键 */
    private Long id;
    /** 发货地省份 */
    @PropertyMsg("发货地省份")
    private String senderProvince;
    /** 发货地城市 */
    @PropertyMsg("发货地城市")
    private String senderCity;
    /** 发货地区域 */
    @PropertyMsg("发货地区域")
    private String senderArea;
    /** 发货地 */
    @PropertyMsg("发货地")
    private String shipmentPlace;

    /** 上货站 */
    @PropertyMsg("上货站")
    private String uploadStation;
    /** 收货地省份 */
    @PropertyMsg("收货地省份")
    private String receiveProvince;
    /** 收货地城市 */
    @PropertyMsg("收货地城市")
    private String receiveCity;
    /** 收货地区域 */
    @PropertyMsg("收货地区域")
    private String receiveArea;
    /** 收货地 */
    @PropertyMsg("收货地")
    private String receiptPlace;

    /** 下货站 */
    @PropertyMsg("下货站")
    private String dropStation;

    /** 服务（0：门到门；1：门到站；2：站到站；3：站到门） */
    @PropertyMsg("服务")
    private String bookingService;

    /** 是否委托zih提货（0：是；1：否） */
    @PropertyMsg("是否委托zih提货")
    private String isPickUp;

    /** 是否由ZIH代理送货  （0否 1是），不能为空 */
    @PropertyMsg("是否由ZIH代理送货")
    private String isDelivery;

    /** 是否委托zih代理报关（0：是；1：否） */
    @PropertyMsg("是否委托zih代理报关")
    private String isOrderCustoms;

    /** 是否委托zih代理清关（0：是；1：否） */
    @PropertyMsg("是否委托zih代理清关")
    private String isClearCustoms;

    /** 箱属（0：zih；1：自备） */
    @PropertyMsg("箱属")
    private String containerBelong;

    /** 货物类型（0：整柜；1：拼箱） */
    @PropertyMsg("货物类型")
    private String goodsType;

    /** 箱型 */
    @PropertyMsg("箱型")
    private String containerType;

    /** 箱量 */
    @PropertyMsg("箱量")
    private Integer containerNum;

    /** 包装方式 */
    @PropertyMsg("包装方式")
    private String packageType;

    /** 是否可堆叠（0否：1是） */
    @PropertyMsg("是否可堆叠")
    private String isStack;

    /** 总数量 */
    @PropertyMsg("总数量")
    private String totalAmount;

    /** 总重量(kg) */
    @PropertyMsg("总重量")
    private String totalWeight;

    /** 总体积(m³) */
    @PropertyMsg("总体积")
    private String totalVolume;

    /** 询价时间 */
    private Date inquiryTime;

    /** 客户id */
    @PropertyMsg("客户id")
    private String clientId;

    /** 删除标识（0未删除；1：已删除） */
    private String delFlag;

    /** 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄） */
    @PropertyMsg("线路")
    private String lineType;

    /** 提箱地 */
    @PropertyMsg("提箱地")
    private String txAddress;

    /** 还箱地 */
    @PropertyMsg("还箱地")
    private String hxAddress;

    /** 去回程,0为去程 1为回程*/
    @PropertyMsg("去回程")
    private String eastOrWest;

    /** 上货站编号 */
    private String uploadStationNum;

    /** 下货站编号 */
    private String dropStationNum;

    @PropertyMsg("货物信息")
    private String bookingInquiryGoodsDetails;
    /**
     * 客户类型 0货代 1直客 2贸易公司
     */
    @PropertyMsg("客户类型")
    private String clientType;

    /**
     * 客户单位名称
     */
    @PropertyMsg("客户单位名称")
    private String clientUnit;
    /**
     * 备注
     */
    @PropertyMsg("备注")
    private String remark;

    /** 国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车 */
    @PropertyMsg("国内公路运输车辆类型")
    private String truckType;
    /**时效（箱型亚欧，0普通，1加急） */
    @PropertyMsg("国内公路运输时效")
    private String limitation;

    /**客户自送货方式（1整柜到堆场，0散货到堆场） */
    @PropertyMsg("客户自送货方式")
    private String deliverySelfType;

    /**提货方式（0整柜到堆场，1散货到堆场）整柜和站到站同时选择
     时候才会出现这个选项 */
    @PropertyMsg("提货方式")
    private String deliveryType;

    /** 分拨方式（0整柜派送，1拆箱散货派送） */
    @PropertyMsg("分拨方式")
    private String distributionType;
    /**
     * 是否易碎（1是0否）
     */
    @PropertyMsg("是否易碎")
    private String goodsFragile;
    /**
     * 单件超长超重 1是0否
     */
    @PropertyMsg("单件是否超长超重")
    private String goodsGeneral;

    /**
     * 还箱地id
     */
    private String hxAddressId;

    public BookingInquiryGoodDetailForm() {
    }

    /**
     * 前台显示语言 zh-en
     */
    @PropertyMsg("前台显示语言")
    private String language;

    @PropertyMsg("询价结果id")
    private Long inquiryResultId;

    @PropertyMsg("订单id")
    private String orderId;


    /**
     * 还箱费
     */
    @PropertyMsg("还箱费")
    private String returnBoxFee;

    /**
     * 提箱费
     */
    @PropertyMsg("提箱费")
    private String pickUpBoxFee;

    /**
     * 客户子账号id
     */
    @PropertyMsg("客户子账号id")
    private String khId;
    //国家
    @PropertyMsg("国家")
    private String country;
    @PropertyMsg("省份编码")
    private String provinceCode;
    @PropertyMsg("城市编码")
    private String cityCode;
    @PropertyMsg("区域编码")
    private String areaCode;

    /**
     * 预期订舱时间（0当月1次月)
     */
    @PropertyMsg("预期订舱时间（0当月1次月)")
    private String bookingTimeFlag;

    private String bookingAgin;
}
