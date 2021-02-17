package com.szhbl.project.inquiry.dto;

import lombok.Data;

/**
 * 回程集疏询价结果 ComeBookingInquiryResult
 * 
 * @author lzd
 * @date 2020-07-14
 */
@Data
public class JsComeBookingInquiryResult
{

    /** 集疏报价人*/
    private String jsBidder;
    /** 询价id */
    private Long inquiryId;
    /** 集疏报价编码*/
    private String inquiryNum;
    /** 正常0，驳回1*/
    private String isNormal;
    /** 集疏备注*/
    private String jsRemark;
    /** 提货时效*/
    private String pickUpAging;
    /** 提货费币种*/
    private String pickUpCurrencyType;
    /** 提货距离 */
    private String pickUpDistance;
    /*** 提货费*/
    private String pickUpFees;
    /*** 提货备注*/
    private String pickUpRemark;
    /** 上货站 */
    private String uploadStation;

   /* private static final long serialVersionUID = 1L;
    // PickUpDistance、PickUpAddress、PickUpFees、PickUpCurrencyType、PickUpAging、PickUpRemark、TxAddress
    //IsNormal、InquiryNum、Bidder、DropStation、UploadStation、JsRemark
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

    *//** 提货距离 *//*
    private String pickUpDistance;

    *//** 提货地 *//*
    private String pickUpAddress;

    *//** * 提货费  *//*
    private String pickUpFees;

    *//*** 提货费币种 *//*
    private String pickUpCurrencyType;

    *//** * 提货时效 *//*
    private String pickUpAging;

    *//** * 提货备注 *//*
    private String pickUpRemark;

    *//** 回程提箱地 *//*
    private String txAddress;*/

}
