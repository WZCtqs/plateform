package com.szhbl.project.order.service;

import com.szhbl.project.order.domain.BookingInquiryApply;
import java.util.List;

/**
 * 订舱询价暂存Service接口
 * 
 * @author dps
 * @date 2020-05-20
 */
public interface IBookingInquiryApplyService 
{
    /**
     * 查询订舱询价暂存
     * 
     * @param id 订舱询价暂存ID
     * @return 订舱询价暂存
     */
    public BookingInquiryApply selectBookingInquiryApplyById(Long id);

    /**
     * 查询订舱询价暂存列表
     * 
     * @param bookingInquiryApply 订舱询价暂存
     * @return 订舱询价暂存集合
     */
    public List<BookingInquiryApply> selectBookingInquiryApplyList(BookingInquiryApply bookingInquiryApply);

    /**
     * 新增订舱询价暂存
     * 
     * @param bookingInquiryApply 订舱询价暂存
     * @return 结果
     */
    public int insertBookingInquiryApply(BookingInquiryApply bookingInquiryApply);

    /**
     * 修改订舱询价暂存
     * 
     * @param bookingInquiryApply 订舱询价暂存
     * @return 结果
     */
    public int updateBookingInquiryApply(BookingInquiryApply bookingInquiryApply);

    /**
     * 批量删除订舱询价暂存
     * 
     * @param ids 需要删除的订舱询价暂存ID
     * @return 结果
     */
    public int deleteBookingInquiryApplyByIds(Long[] ids);

    /**
     * 删除订舱询价暂存信息
     * 
     * @param id 订舱询价暂存ID
     * @return 结果
     */
    public int deleteBookingInquiryApplyById(Long id);
}
