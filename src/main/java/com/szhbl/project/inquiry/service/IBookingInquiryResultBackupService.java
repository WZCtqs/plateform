package com.szhbl.project.inquiry.service;

import com.szhbl.project.inquiry.domain.BookingInquiryResultBackup;
import java.util.List;

/**
 * 询价反馈结果数据草稿Service接口
 * 
 * @author szhbl
 * @date 2020-07-10
 */
public interface IBookingInquiryResultBackupService 
{
    /**
     * 查询询价反馈结果数据草稿
     * 
     * @param id 询价反馈结果数据草稿ID
     * @return 询价反馈结果数据草稿
     */
    public BookingInquiryResultBackup selectBookingInquiryResultBackupById(Long id);

    /**
     * 查询询价反馈结果数据草稿列表
     * 
     * @param bookingInquiryResultBackup 询价反馈结果数据草稿
     * @return 询价反馈结果数据草稿集合
     */
    public List<BookingInquiryResultBackup> selectBookingInquiryResultBackupList(BookingInquiryResultBackup bookingInquiryResultBackup);

    /**
     * 新增询价反馈结果数据草稿
     * 
     * @param bookingInquiryResultBackup 询价反馈结果数据草稿
     * @return 结果
     */
    public int insertBookingInquiryResultBackup(BookingInquiryResultBackup bookingInquiryResultBackup);

    /**
     * 修改询价反馈结果数据草稿
     * 
     * @param bookingInquiryResultBackup 询价反馈结果数据草稿
     * @return 结果
     */
    public int updateBookingInquiryResultBackup(BookingInquiryResultBackup bookingInquiryResultBackup);

    /**
     * 批量删除询价反馈结果数据草稿
     * 
     * @param ids 需要删除的询价反馈结果数据草稿ID
     * @return 结果
     */
    public int deleteBookingInquiryResultBackupByIds(Long[] ids);

    /**
     * 删除询价反馈结果数据草稿信息
     * 
     * @param id 询价反馈结果数据草稿ID
     * @return 结果
     */
    public int deleteBookingInquiryResultBackupById(Long id);
}
