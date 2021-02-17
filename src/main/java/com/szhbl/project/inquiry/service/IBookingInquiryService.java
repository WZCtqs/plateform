package com.szhbl.project.inquiry.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.inquiry.domain.BookingInquiry;

import java.util.List;

/**
 * 订舱询价Service接口
 * 
 * @author jhm
 * @date 2020-04-01
 */
public interface IBookingInquiryService 
{
    /**
     * 查询订舱询价
     * 
     * @param id 订舱询价ID
     * @return 订舱询价
     */
    public BookingInquiry selectBookingInquiryById(Long id);

    /**
     * 查询订舱询价列表
     * 
     * @param bookingInquiry 订舱询价
     * @return 订舱询价集合
     */
    public List<BookingInquiry> selectBookingInquiryList(BookingInquiry bookingInquiry);

    /**
     * 新增订舱询价
     * 
     * @param bookingInquiry 订舱询价
     * @return 结果
     */
    public AjaxResult insertBookingInquiry(BookingInquiry bookingInquiry);

    /**
     * 修改订舱询价
     * 
     * @param bookingInquiry 订舱询价
     * @return 结果
     */
    public int updateBookingInquiry(BookingInquiry bookingInquiry);

    /**
     * 修改订舱询价
     *
     * @param bookingInquiry 订舱询价
     * @return 结果
     */
    public int updateBIWithDate(BookingInquiry bookingInquiry);

    /**
     * 批量删除订舱询价
     * 
     * @param ids 需要删除的订舱询价ID
     * @return 结果
     */
    public int deleteBookingInquiryByIds(Long[] ids);

    /**
     * 删除订舱询价信息
     * 
     * @param id 订舱询价ID
     * @return 结果
     */
    public int deleteBookingInquiryById(Long id);


}
