package com.szhbl.project.inquiry.service;

import com.szhbl.project.inquiry.domain.BookingInquiryResult;

import java.util.Date;
import java.util.List;

/**
 * 询价反馈结果数据Service接口
 * 
 * @author jhm
 * @date 2020-04-08
 */
public interface IBookingInquiryResultService 
{
    /**
     * 查询询价反馈结果数据
     * 
     * @param id 询价反馈结果数据ID
     * @return 询价反馈结果数据
     */
    public BookingInquiryResult selectBookingInquiryResultById(Long id);

    /**
     * 查询询价反馈结果数据列表
     * 
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 询价反馈结果数据集合
     */
    public List<BookingInquiryResult> selectBookingInquiryResultList(BookingInquiryResult bookingInquiryResult);

    /**
     * 新增询价反馈结果数据
     * 
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 结果
     */
    public int insertBookingInquiryResult(BookingInquiryResult bookingInquiryResult);

    /**
     * 修改询价反馈结果数据
     * 
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 结果
     */
    public int updateBookingInquiryResult(BookingInquiryResult bookingInquiryResult);

    /**
     * 批量删除询价反馈结果数据
     * 
     * @param ids 需要删除的询价反馈结果数据ID
     * @return 结果
     */
    public int deleteBookingInquiryResultByIds(Long[] ids);

    /**
     * 删除询价反馈结果数据信息
     * 
     * @param id 询价反馈结果数据ID
     * @return 结果
     */
    public int deleteBookingInquiryResultById(Long id);


    List<BookingInquiryResult> selectBookingInquiryResultWithInquiryId(Long inquiryId);

    /**
     * 更新询价结果
     *
     * @param bookingInquiryResult
     * @return
     */
    int updateBookingInquiryResultWithInquiryId(BookingInquiryResult bookingInquiryResult);

    String getLastInquiryHxd(String orderId);

    Date selectInquiryClassDateByOrderId(String orderId);
}
