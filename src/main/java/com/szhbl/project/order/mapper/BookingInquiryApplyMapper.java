package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BookingInquiryApply;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订舱询价暂存Mapper接口
 * 
 * @author dps
 * @date 2020-05-20
 */
@Repository
public interface BookingInquiryApplyMapper 
{
    /**
     * 查询订舱询价暂存
     * 
     * @param id 订舱询价暂存ID
     * @return 订舱询价暂存
     */
    public BookingInquiryApply selectBookingInquiryApplyById(Long id);
    public BookingInquiryApply selectBookingInquiryApplyByIds(String orderId);

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
     * 删除订舱询价暂存
     * 
     * @param id 订舱询价暂存ID
     * @return 结果
     */
    public int deleteBookingInquiryApplyById(Long id);
    public int deleteBookingInquiryApplyByOrderId(String orderId);


    /**
     * 批量删除订舱询价暂存
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBookingInquiryApplyByIds(Long[] ids);
}
