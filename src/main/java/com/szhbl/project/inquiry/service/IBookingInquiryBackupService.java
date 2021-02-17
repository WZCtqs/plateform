package com.szhbl.project.inquiry.service;

import com.szhbl.project.inquiry.domain.BookingInquiryBackup;
import java.util.List;

/**
 * 订舱询价草稿Service接口
 * 
 * @author szhbl
 * @date 2020-07-10
 */
public interface IBookingInquiryBackupService 
{
    /**
     * 查询订舱询价草稿
     * 
     * @param id 订舱询价草稿ID
     * @return 订舱询价草稿
     */
    public BookingInquiryBackup selectBookingInquiryBackupById(Long id);

    /**
     * 查询订舱询价草稿列表
     * 
     * @param bookingInquiryBackup 订舱询价草稿
     * @return 订舱询价草稿集合
     */
    public List<BookingInquiryBackup> selectBookingInquiryBackupList(BookingInquiryBackup bookingInquiryBackup);

    /**
     * 新增订舱询价草稿
     * 
     * @param bookingInquiryBackup 订舱询价草稿
     * @return 结果
     */
    public int insertBookingInquiryBackup(BookingInquiryBackup bookingInquiryBackup);

    /**
     * 修改订舱询价草稿
     * 
     * @param bookingInquiryBackup 订舱询价草稿
     * @return 结果
     */
    public int updateBookingInquiryBackup(BookingInquiryBackup bookingInquiryBackup);

    /**
     * 批量删除订舱询价草稿
     * 
     * @param ids 需要删除的订舱询价草稿ID
     * @return 结果
     */
    public int deleteBookingInquiryBackupByIds(Long[] ids);

    /**
     * 删除订舱询价草稿信息
     * 
     * @param id 订舱询价草稿ID
     * @return 结果
     */
    public int deleteBookingInquiryBackupById(Long id);
}
