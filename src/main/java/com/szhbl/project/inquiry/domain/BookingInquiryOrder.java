package com.szhbl.project.inquiry.domain;

import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 查询托书历史询价信息对象 booking_inquiry_order
 *
 * @author szhbl
 * @date 2020-06-30
 */
@Data
public class BookingInquiryOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String orderId;

    private Long inquiryResultId;


}
