package com.szhbl.project.inquiry.service;

import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import java.util.List;

/**
 * 订舱询价-拼箱货物详情Service接口
 * 
 * @author jhm
 * @date 2020-04-03
 */
public interface IBookingInquiryGoodsDetailsService 
{
    /**
     * 查询订舱询价-拼箱货物详情
     * 
     * @param id 订舱询价-拼箱货物详情ID
     * @return 订舱询价-拼箱货物详情
     */
    public BookingInquiryGoodsDetails selectBookingInquiryGoodsDetailsById(Long id);

    /**
     * 查询订舱询价-拼箱货物详情列表
     * 
     * @param bookingInquiryGoodsDetails 订舱询价-拼箱货物详情
     * @return 订舱询价-拼箱货物详情集合
     */
    public List<BookingInquiryGoodsDetails> selectBookingInquiryGoodsDetailsList(BookingInquiryGoodsDetails bookingInquiryGoodsDetails);

    /**
     * 新增订舱询价-拼箱货物详情
     * 
     * @param bookingInquiryGoodsDetails 订舱询价-拼箱货物详情
     * @return 结果
     */
    public int insertBookingInquiryGoodsDetails(BookingInquiryGoodsDetails bookingInquiryGoodsDetails);

    /**
     * 修改订舱询价-拼箱货物详情
     * 
     * @param bookingInquiryGoodsDetails 订舱询价-拼箱货物详情
     * @return 结果
     */
    public int updateBookingInquiryGoodsDetails(BookingInquiryGoodsDetails bookingInquiryGoodsDetails);

    /**
     * 批量删除订舱询价-拼箱货物详情
     * 
     * @param ids 需要删除的订舱询价-拼箱货物详情ID
     * @return 结果
     */
    public int deleteBookingInquiryGoodsDetailsByIds(Long[] ids);

    /**
     * 删除订舱询价-拼箱货物详情信息
     * 
     * @param id 订舱询价-拼箱货物详情ID
     * @return 结果
     */
    public int deleteBookingInquiryGoodsDetailsById(Long id);

    public List<BookingInquiryGoodsDetails> selectBookingInquiryGoodsDetailsWithInquiryId(Long inquiryId);
}
