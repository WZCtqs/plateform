package com.szhbl.project.inquiry.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.inquiry.mapper.BookingInquiryGoodsDetailsMapper;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsService;

/**
 * 订舱询价-拼箱货物详情Service业务层处理
 * 
 * @author jhm
 * @date 2020-04-03
 */
@Service
public class BookingInquiryGoodsDetailsServiceImpl implements IBookingInquiryGoodsDetailsService 
{
    @Autowired
    private BookingInquiryGoodsDetailsMapper bookingInquiryGoodsDetailsMapper;

    /**
     * 查询订舱询价-拼箱货物详情
     * 
     * @param id 订舱询价-拼箱货物详情ID
     * @return 订舱询价-拼箱货物详情
     */
    @Override
    public BookingInquiryGoodsDetails selectBookingInquiryGoodsDetailsById(Long id)
    {
        return bookingInquiryGoodsDetailsMapper.selectBookingInquiryGoodsDetailsById(id);
    }

    /**
     * 查询订舱询价-拼箱货物详情列表
     * 
     * @param bookingInquiryGoodsDetails 订舱询价-拼箱货物详情
     * @return 订舱询价-拼箱货物详情
     */
    @Override
    public List<BookingInquiryGoodsDetails> selectBookingInquiryGoodsDetailsList(BookingInquiryGoodsDetails bookingInquiryGoodsDetails)
    {
        return bookingInquiryGoodsDetailsMapper.selectBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetails);
    }

    /**
     * 新增订舱询价-拼箱货物详情
     * 
     * @param bookingInquiryGoodsDetails 订舱询价-拼箱货物详情
     * @return 结果
     */
    @Override
    public int insertBookingInquiryGoodsDetails(BookingInquiryGoodsDetails bookingInquiryGoodsDetails)
    {
        return bookingInquiryGoodsDetailsMapper.insertBookingInquiryGoodsDetails(bookingInquiryGoodsDetails);
    }

    /**
     * 修改订舱询价-拼箱货物详情
     * 
     * @param bookingInquiryGoodsDetails 订舱询价-拼箱货物详情
     * @return 结果
     */
    @Override
    public int updateBookingInquiryGoodsDetails(BookingInquiryGoodsDetails bookingInquiryGoodsDetails)
    {
        return bookingInquiryGoodsDetailsMapper.updateBookingInquiryGoodsDetails(bookingInquiryGoodsDetails);
    }

    /**
     * 批量删除订舱询价-拼箱货物详情
     * 
     * @param ids 需要删除的订舱询价-拼箱货物详情ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryGoodsDetailsByIds(Long[] ids)
    {
        return bookingInquiryGoodsDetailsMapper.deleteBookingInquiryGoodsDetailsByIds(ids);
    }

    /**
     * 删除订舱询价-拼箱货物详情信息
     * 
     * @param id 订舱询价-拼箱货物详情ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryGoodsDetailsById(Long id)
    {
        return bookingInquiryGoodsDetailsMapper.deleteBookingInquiryGoodsDetailsById(id);
    }

    @Override
    public List<BookingInquiryGoodsDetails> selectBookingInquiryGoodsDetailsWithInquiryId(Long inquiryId) {
        return bookingInquiryGoodsDetailsMapper.selectBookingInquiryGoodsDetailsWithInquiryId(inquiryId);
    }


}
