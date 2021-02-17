package com.szhbl.project.inquiry.domain;

import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 询价反馈结果数据草稿对象 booking_inquiry_result_backup
 *
 * @author szhbl
 * @date 2020-07-10
 */
@Data
public class BookingInquiryResultBackup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 询价反馈结果id
     */
    private Long id;

    /**
     * 询价id
     */
    private Long inquiryId;

    /**
     * 上货站
     */
    private String uploadStation;

    /**
     * 下货站
     */
    private String dropStation;

    /**
     * 上货站备注
     */
    private String uploadStationRemark;

    /**
     * 下货站备注
     */
    private String dropStationRemark;

    /**
     * 提货地
     */
    private String pickUpAddress;

    /**
     * 提货距离
     */
    private String pickUpDistance;

    /**
     * 提货时效
     */
    private String pickUpAging;

    /**
     * 提货费
     */
    private String pickUpFees;

    /**
     * 提货费币种
     */
    private String pickUpCurrencyType;

    /**
     * 提货费有效期
     */
    private Date pickUpExpiration;

    /**
     * 提货备注
     */
    private String pickUpRemark;

    /**
     * 提货报价反馈时间
     */
    private Date pickUpFeedbackTime;

    /**
     * 铁路运费
     */
    private String railwayFees;

    /**
     * 铁路运费币种
     */
    private String railwayCurrencyType;

    /**
     * 铁路收费标准
     */
    private String railwayCharges;

    /**
     * 铁路费用有效期
     */
    private Date railwayExpiration;

    /**
     * 铁路时效
     */
    private String railwayAging;

    /**
     * 铁路备注
     */
    private String railwayRemark;

    /**
     * 铁路报价反馈时间
     */
    private Date railwayFeedbackTime;

    /**
     * 派送地
     */
    private String deliveryAddress;

    /**
     * 派送距离
     */
    private String deliveryDistance;

    /**
     * 派送费用
     */
    private String deliveryFees;

    /**
     * 派送费用币种
     */
    private String deliveryCurrencyType;

    /**
     * 派送时效
     */
    private String deliveryAging;

    /**
     * 派送报价有效期
     */
    private Date deliveryExpiration;

    /**
     * 派送备注
     */
    private String deliveryRemark;

    /**
     * 派送报价反馈时间
     */
    private Date deliveryFeedbackTime;

    /**
     * 删除标识（0未删除；1已删除）
     */
    private String delFlag;

    /**
     * 确认标识（0未确认；1已确认）
     */
    private String confirmFlag;

    /**
     * 确认时间
     */
    private Date confirmTime;

    /**
     * 确认人
     */
    private Long confirmUser;

    /**
     * 集疏备注
     */
    private String jsRemark;

    /**
     * 还箱费
     */
    private String returnBoxFee;
    /**
     * 还箱费有效期
     */
    private Date returnBoxExpiration;

    /**
     * 提箱费
     */
    private String pickUpBoxFee;
    /**
     * 提箱费有效期
     */
    private Date pickUpBoxExpiration;

    /**
     * 理货费
     */
    private String lhuFee;

    /**
     * 超长超重费
     */
    private String ccczFee;

    /**
     * 提空箱费
     */
    private String tkxFee;

    /**
     * 加固费
     */
    private String jgFee;

    /**
     * 打托费
     */
    private String dtFee;

    /**
     * 装拆箱费
     */
    private String zcxFee;

    /**
     * 公路报价编码
     */
    private String inquiryNumber;

    /**
     * 集疏报价编码
     */
    private String inquiryNum;

    /**
     * 1（报价中）、2（待审核）、3（已报价）、4（驳回）
     */
    private String enquiryState;

    /**
     * 还箱地
     */
    private String hxAddress;

    /**
     * 提箱地
     */
    private String txAddress;

    /**
     * 铁路费用备注
     */
    private String railRemark;

    /**
     * 箱型亚欧备注
     */
    private String xxyoRemark;

    /**
     * 业务备注
     */
    private String businessRemark;

    /**
     * 集输报价人
     */
    private String jsBidder;

    /**
     * 国内超长超重费
     */
    private String domesticOverFee;

    /**
     * 是否需要国内场站报价（0:不需要；1:需要）
     */
    private String domesticFlag;

    /**
     * 国内超长超重费币种（默认美元）
     */
    private String domesticOverFeeCurrencyType;

    /**
     * 国内抛物理货费
     */
    private String domesticTallyFee;

    /**
     * 国内抛物理货费币种（默认人民币)
     */
    private String domesticTallyFeeCurrencyType;

    /**
     * 国内装箱费
     */
    private String domesticPackFee;

    /**
     * 国内装箱费币种
     */
    private String domesticPackFeeCurrencyType;

    /**
     * 国内拆箱费
     */
    private String domesticUnPackFee;

    /**
     * 国内拆箱费币种
     */
    private String domesticUnPackFeeCurrencyType;

    /**
     * 国内拼箱场站报价单号
     */
    private String domesticNumber;

    /**
     * 国内拼箱场站报价备注
     */
    private String domesticRemark;

    /**
     * 国内拼箱场站报价反馈时间
     */
    private Date domesticFeedbackTime;

    /**
     * 是否需要国外场站报价（0:不需要；1:需要）
     */
    private String foreignFlag;

    /**
     * 国外超长超重费
     */
    private String foreignOverFee;

    /**
     * 国外超长超重费币种（默认美元）
     */
    private String foreignOverFeeCurrencyType;

    /**
     * 国外抛物理货费
     */
    private String foreignTallyFee;

    /**
     * 国外抛物理货费币种（默认人民币)
     */
    private String foreignTallyFeeCurrencyType;

    /**
     * 国外装箱费
     */
    private String foreignPackFee;

    /**
     * 国外装箱费币种
     */
    private String foreignPackFeeCurrencyType;

    /**
     * 国外拆箱费
     */
    private String foreignUnPackFee;

    /**
     * 国外拆箱费币种
     */
    private String foreignUnPackFeeCurrencyType;

    /**
     * 国外拼箱场站报价单号
     */
    private String foreignNumber;

    /**
     * 国外拼箱场站报价备注
     */
    private String foreignRemark;

    /**
     * 国外拼箱场站报价反馈时间
     */
    private Date foreignFeedbackTime;

    private BookingInquiryBackup bookingInquiry;

}
