package com.szhbl.project.order.domain;

import com.szhbl.project.inquiry.domain.BookingInquiryResultBackup;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description : 转待审核货物信息修改判断结果
 * @Author : wangzhichao
 * @Date: 2020-11-20 10:56
 */
@Data
public class GoodToCheckResult implements Serializable {
    //国内报价
    private boolean gn;
    //国外报价
    private boolean gw;

    private BookingInquiryResultBackup inquiryResultBackup;
}
