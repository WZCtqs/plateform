package com.szhbl.project.inquiry.dto;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import lombok.Data;

import java.util.Date;

@Data
public class BookingInquiryAndResult {
    /** 询价id */
    @Excel(name = "询价id")
    private Long inquiryId;

    /** 上货站 */
    @Excel(name = "上货站")
    private String uploadStation;

    /** 下货站 */
    @Excel(name = "下货站")
    private String dropStation;

    /** 上货站备注 */
    @Excel(name = "上货站备注")
    private String uploadStationRemark;

    /** 下货站备注 */
    @Excel(name = "下货站备注")
    private String dropStationRemark;

    /** 提货地 */
    @Excel(name = "提货地")
    private String pickUpAddress;

    /** 提货距离 */
    @Excel(name = "提货距离")
    private String pickUpDistance;

    /** 提货时效 */
    @Excel(name = "提货时效")
    private String pickUpAging;

    /** 提货费 */
    @Excel(name = "提货费")
    private String pickUpFees;

    /** 提货费币种 */
    @Excel(name = "提货费币种")
    private String pickUpCurrencyType;

    /** 提货费有效期 */
    @Excel(name = "提货费有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pickUpExpiration;

    /** 提货备注 */
    @Excel(name = "提货备注")
    private String pickUpRemark;

    /** 提货报价反馈时间 */
    @Excel(name = "提货报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pickUpFeedbackTime;

    /** 铁路运费 */
    @Excel(name = "铁路运费")
    private String railwayFees;

    /** 铁路运费币种 */
    @Excel(name = "铁路运费币种")
    private String railwayCurrencyType;

    /** 铁路收费标准 */
    @Excel(name = "铁路收费标准")
    private String railwayCharges;

    /** 铁路费用有效期 */
    @Excel(name = "铁路费用有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date railwayExpiration;

    /** 铁路时效 */
    @Excel(name = "铁路时效")
    private String railwayAging;

    /** 铁路备注 */
    @Excel(name = "铁路备注")
    private String railwayRemark;

    /** 铁路报价反馈时间 */
    @Excel(name = "铁路报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date railwayFeedbackTime;

    /** 派送距离 */
    @Excel(name = "派送距离")
    private String deliveryDistance;

    /** 派送地 */
    @Excel(name = "派送地")
    private String deliveryAddress;

    /** 派送费用 */
    @Excel(name = "派送费用")
    private String deliveryFees;

    /** 派送费用币种 */
    @Excel(name = "派送费用币种")
    private String deliveryCurrencyType;

    /** 派送时效 */
    @Excel(name = "派送时效")
    private String deliveryAging;

    /** 派送报价有效期 */
    @Excel(name = "派送报价有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryExpiration;

    /** 派送备注 */
    @Excel(name = "派送备注")
    private String deliveryRemark;

    /** 派送报价反馈时间 */
    @Excel(name = "派送报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryFeedbackTime;

    /** 删除标识（0未删除；1已删除） */
    private String delFlag;

    /** 确认标识（0未确认；1已确认） */
    @Excel(name = "确认标识", readConverterExp = "0=未确认；1已确认")
    private String confirmFlag;

    /** 确认时间 */
    @Excel(name = "确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmTime;

    /** 确认人 */
    @Excel(name = "确认人")
    private Long confirmUser;

    private BookingInquiry bookingInquiry;

    /** 集疏备注*/
    private String jsRemark;

    /** 还箱费 */
    @Excel(name = "还箱费")
    private String returnBoxFee;

    /** 理货费 */
    @Excel(name = "理货费")
    private String lhuFee;

    /** 超长超重费 */
    @Excel(name = "超长超重费")
    private String ccczFee;

    /** 提空箱费 */
    @Excel(name = "提空箱费")
    private String tkxFee;

    /** 加固费 */
    @Excel(name = "加固费")
    private String jgFee;

    /** 打托费 */
    @Excel(name = "打托费")
    private String dtFee;

    /** 装拆箱费 */
    @Excel(name = "装拆箱费")
    private String zcxFee;

    /** 集疏报价编码*/
    private String inquiryNum;

    private String enquiryState;
}
