package com.szhbl.project.order.domain;

import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 托书转待审核询价前判断结果对象 busi_order_tocheck_inquiry
 *
 * @author szhbl
 * @date 2020-07-16
 */
@Data
public class BusiOrderTocheckInquiry extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 询价id
     */
    private Long inquiryId;

    /**
     * 询价结果id
     */
    private Long inquiryResultId;

    /**
     * 是否箱管审核
     */
    private Integer xgExamine;

    /**
     * 是否公路报价
     */
    private Integer xxyoExamine;

    /**
     * 是否集输报价
     */
    private Integer jsExamine;

    /**
     * 是否订舱审核
     */
    private Integer dcExamine;

    /**
     * 集输—公路提货时间审核
     */
    private Integer jsXxyoExamine;

    /**
     * 订舱组审核（班列-品名）
     */
    private Integer dczExamine;

    /**
     * 客户确认
     */
    private Integer clientExamine;

    private Integer ywExamine;

    /**
     * 是否新询价
     */
    private Integer newInquiry;

    private String gwczResult;

    private Integer hxdXgExamine;
    /*国内场站是否询价0：是，1：否*/
    private Integer gnInquiry;

    /*国外场站是否询价0：是，1：否*/
    private Integer gwInquiry;

    @Override
    public String toString() {
        return "BusiOrderTocheckInquiry{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", inquiryId=" + inquiryId +
                ", inquiryResultId=" + inquiryResultId +
                ", xgExamine=" + xgExamine +
                ", xxyoExamine=" + xxyoExamine +
                ", jsExamine=" + jsExamine +
                ", dcExamine=" + dcExamine +
                ", jsXxyoExamine=" + jsXxyoExamine +
                ", dczExamine=" + dczExamine +
                ", clientExamine=" + clientExamine +
                ", newInquiry=" + newInquiry +
                '}';
    }
}
