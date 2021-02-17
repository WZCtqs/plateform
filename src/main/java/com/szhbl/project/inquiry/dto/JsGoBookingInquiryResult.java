package com.szhbl.project.inquiry.dto;

import lombok.Data;

/**
 * 去程集疏询价结果
 *
 * @author lzd
 * @date 2020-07-14
 */
@Data
public class JsGoBookingInquiryResult
{
    /** 集疏报价人*/
    private String jsBidder;
    /** 派送时效 */
    private String deliveryAging;
    /** 派送费用币种 */
    private String deliveryCurrencyType;
    /** 派送距离 */
    private String deliveryDistance;
    /** 派送费用 */
    private String deliveryFees;
    /** 派送备注 */
    private String deliveryRemark;
    /** 下货站 */
    private String dropStation;
    /** 询价id */
    private Long inquiryId;
    /** 集疏报价编码*/
    private String inquiryNum;
    /** 正常0，驳回1*/
    private String isNormal;
    /** 集疏备注*/
    private String jsRemark;


    /*private static final long serialVersionUID = 1L;
    //去程 DeliveryDistance、DeliveryAddress、DeliveryFees、DeliveryCurrencyType、DeliveryAging、DeliveryRemark、HxAddress
    //公用IsNormal、InquiryNum、Bidder、DropStation、UploadStation、JsRemark
    //铁路railwayFees、railwayCurrencyType、railwayAging、pickUpBoxFee、returnBoxFee、railRemark
    *//** 铁路运费 *//*
    private String railwayFees;

    *//** 铁路运费币种 *//*
    private String railwayCurrencyType;

    *//** 铁路时效 *//*
    private String railwayAging;

    *//** 提箱费 *//*
    private String pickUpBoxFee;

    *//** 还箱费 *//*
    private String returnBoxFee;

    *//**铁路运费备注*//*
    private String railRemark;

    *//** 集疏备注*//*
    private String jsRemark;

    *//** 上货站 *//*
    private String uploadStation;

    *//** 下货站 *//*
    private String dropStation;

    *//** 集疏报价人*//*
    private String jsBidder;

    *//** 集疏报价编码*//*
    private String inquiryNum;

    *//** 正常0，驳回1*//*
    private String isNormal;

    *//** 派送距离 *//*
    private String deliveryDistance;

    *//** 派送地 *//*
    private String deliveryAddress;

    *//** 派送费用 *//*
    private String deliveryFees;

    *//** 派送费用币种 *//*
    private String deliveryCurrencyType;

    *//** 派送时效 *//*
    private String deliveryAging;

    *//** 派送备注 *//*
    private String deliveryRemark;

    *//** 去程还箱地 *//*
    private String hxAddress;*/

}
