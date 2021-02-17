package com.szhbl.project.inquiry.dto;

import lombok.Data;

/**
 * 集疏询价数据是否已经存在的判断 BookingInquiryJsCheck
 * 
 * @author lzd
 * @date 2020-07-13
 */
@Data
public class BookingInquiryGlCheck
{
    private static final long serialVersionUID = 1L;
    //询价人、去/回程、时效、普通/专车、提/送货地址、数量、重量、体积、//长宽高、是否易碎、是否可堆叠、是否超尺、是否超重、货物包装、//货物品名、备注、
    //加急备注、 车辆尺寸、

    /** 客户id，不能为空 */
    private String clientId;

    /** 去回程,0为去程 1为回程*/
    private String eastOrWest;

    /**时效（箱型亚欧，0普通，1加急） */
    private String limitation;

    /** 货物类型（0：整柜；1：拼箱） */
    private String goodsType;

    /** 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄） */
    private String lineType;

    /** 国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车 */
    private String truckType;

    /** 收货地 */
    private String receiptPlace;

    /** 发货地 */
    private String shipmentPlace;

    /** 箱型，不能为空 */
    private String containerType;

    /** 箱量，不能为空 */
    private Integer containerNum;

    /** 包装方式 */
    private String packageType;

    /** 是否可堆叠（0否：1是） */
    private String isStack;

    /**
     * 单件超长超重 1是0否
     */
    private String goodsGeneral;

    /**是否易碎（1是0否）*/
    private String goodsFragile;

    /** 总数量 */
    private String totalAmount;

    /** 总重量(kg) */
    private String totalWeight;

    /** 总体积(m³) */
    private String totalVolume;

    /*** 备注*/
    private String remark;
}
