package com.szhbl.project.inquiry.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.inquiry.domain.BookingInquiryOrder;
import com.szhbl.project.inquiry.mapper.BookingInquiryOrderMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询托书历史询价信息Service业务层处理
 *
 * @author szhbl
 * @date 2020-06-30
 */
@Service
public class BookingInquiryOrderServiceImpl implements IBookingInquiryOrderService {
    @Autowired
    private BookingInquiryOrderMapper bookingInquiryOrderMapper;

    /**
     * 查询查询托书历史询价信息列表
     *
     * @param bookingInquiryOrder 查询托书历史询价信息
     * @return 查询托书历史询价信息
     */
    @Override
    public List<BookingInquiryOrder> selectBookingInquiryOrderList(BookingInquiryOrder bookingInquiryOrder) {
        return bookingInquiryOrderMapper.selectBookingInquiryOrderList(bookingInquiryOrder);
    }

    @Override
    public BookingInquiryOrder selectPreInquiryOrder(String orderId) {
        return bookingInquiryOrderMapper.selectPreInquiryOrder(orderId);
    }

    /**
     * 新增查询托书历史询价信息
     *
     * @param bookingInquiryOrder 查询托书历史询价信息
     * @return 结果
     */
    @Override
    public int insertBookingInquiryOrder(BookingInquiryOrder bookingInquiryOrder) {
        bookingInquiryOrder.setCreateTime(DateUtils.getNowDate());
        return bookingInquiryOrderMapper.insertBookingInquiryOrder(bookingInquiryOrder);
    }

    /**
     * 修改查询托书历史询价信息
     *
     * @param bookingInquiryOrder 查询托书历史询价信息
     * @return 结果
     */
    @Override
    public int updateBookingInquiryOrder(BookingInquiryOrder bookingInquiryOrder) {
        bookingInquiryOrder.setUpdateTime(DateUtils.getNowDate());
        return bookingInquiryOrderMapper.updateBookingInquiryOrder(bookingInquiryOrder);
    }
}
