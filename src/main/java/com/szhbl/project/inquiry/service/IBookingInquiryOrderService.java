package com.szhbl.project.inquiry.service;

import com.szhbl.project.inquiry.domain.BookingInquiryOrder;

import java.util.List;

/**
 * 查询托书历史询价信息Service接口
 *
 * @author szhbl
 * @date 2020-06-30
 */
public interface IBookingInquiryOrderService {
    /**
     * 查询查询托书历史询价信息列表
     *
     * @param bookingInquiryOrder 查询托书历史询价信息
     * @return 查询托书历史询价信息集合
     */
    public List<BookingInquiryOrder> selectBookingInquiryOrderList(BookingInquiryOrder bookingInquiryOrder);

    BookingInquiryOrder selectPreInquiryOrder(String orderId);

    /**
     * 新增查询托书历史询价信息
     *
     * @param bookingInquiryOrder 查询托书历史询价信息
     * @return 结果
     */
    public int insertBookingInquiryOrder(BookingInquiryOrder bookingInquiryOrder);

    /**
     * 修改查询托书历史询价信息
     *
     * @param bookingInquiryOrder 查询托书历史询价信息
     * @return 结果
     */
    public int updateBookingInquiryOrder(BookingInquiryOrder bookingInquiryOrder);
}
