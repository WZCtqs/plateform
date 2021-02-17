package com.szhbl.project.inquiry.dto;

import lombok.Data;

/**
 * 集疏询价数据是否已经存在的判断 BookingInquiryJsCheck
 * 
 * @author lzd
 * @date 2020-07-13
 */
@Data
public class BookingInquiryJsCheck
{
    private static final long serialVersionUID = 1L;
    //ClientUnit、EastOrWest、GoodsType、DeliveryType、LineType、ReceiptPlace、ShipmentPlace、ContainerType、ContainerNum、PackageType、IsStack、TotalAmount、TotalVolume、
    // bookingInquiryGoodsDetailsList(GoodsName、GoodsAmount、GoodsWeight、GoodsLength、GoodsWidth、GoodsHeight)、Remark、InquiryTime、Id

    // 询价人（客户单位）,去回程, 整拼箱 回程提货方式 ,线路 ,收/发 货地 ,箱型箱量(整柜)
    // 包装方式、是否可堆叠、是否易碎、总数量、总重量、总体积、尺寸(数量/重量/长*宽*高/) ,询价备注
    /** 客户id，不能为空 */
    private String clientId;

    /** 去回程,0为去程 1为回程*/
    private String eastOrWest;

    /** 货物类型（0：整柜；1：拼箱） */
    private String goodsType;

    /**提货方式（0整柜到堆场，1散货到堆场）整柜和站到站同时选择
     时候才会出现这个选项 */
    private String deliveryType;

    /** 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄） */
    private String lineType;

    //国家
    private String country;
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
