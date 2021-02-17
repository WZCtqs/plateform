package com.szhbl.project.order.domain.vo;

import lombok.Data;

@Data
public class inquiryResultZx {

    private Long inquiryId; //询价id

    private Long inquiryResultId; //询价结果id

    private Integer containerNum; //箱量

    private String pickUpFees; //提货费

    private String pickUpCurrencyType; //提货费币种

    private String railwayFees; //铁路运费

    private String railwayCurrencyType; //铁路运费币种

    private String deliveryFees; //派送费用

    private String deliveryCurrencyType; //派送费用币种

    private String shipThCostNo; //提货费报价编号

    private String receiveShCostId; //送货报价编号

    private String pickUpBoxFee; //提箱费

    private String returnBoxFee; //还箱费

    private String txAddress;//提箱地

    private String hxAddress;//还箱地

    private String uploadStation;//上货站

    private String dropStation;//下货站

    private String domesticNumber; //国内拼箱场站报价单号

    private String foreignNumber; //国外拼箱场站报价单号

}
