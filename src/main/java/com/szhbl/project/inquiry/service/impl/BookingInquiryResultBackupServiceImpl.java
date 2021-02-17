package com.szhbl.project.inquiry.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.inquiry.mapper.BookingInquiryResultBackupMapper;
import com.szhbl.project.inquiry.domain.BookingInquiryResultBackup;
import com.szhbl.project.inquiry.service.IBookingInquiryResultBackupService;

/**
 * 询价反馈结果数据草稿Service业务层处理
 * 
 * @author szhbl
 * @date 2020-07-10
 */
@Service
public class BookingInquiryResultBackupServiceImpl implements IBookingInquiryResultBackupService 
{
    @Autowired
    private BookingInquiryResultBackupMapper bookingInquiryResultBackupMapper;

    /**
     * 查询询价反馈结果数据草稿
     * 
     * @param id 询价反馈结果数据草稿ID
     * @return 询价反馈结果数据草稿
     */
    @Override
    public BookingInquiryResultBackup selectBookingInquiryResultBackupById(Long id)
    {
        return bookingInquiryResultBackupMapper.selectBookingInquiryResultBackupById(id);
    }

    /**
     * 查询询价反馈结果数据草稿列表
     * 
     * @param bookingInquiryResultBackup 询价反馈结果数据草稿
     * @return 询价反馈结果数据草稿
     */
    @Override
    public List<BookingInquiryResultBackup> selectBookingInquiryResultBackupList(BookingInquiryResultBackup bookingInquiryResultBackup)
    {
        return bookingInquiryResultBackupMapper.selectBookingInquiryResultBackupList(bookingInquiryResultBackup);
    }

    /**
     * 新增询价反馈结果数据草稿
     * 
     * @param bookingInquiryResultBackup 询价反馈结果数据草稿
     * @return 结果
     */
    @Override
    public int insertBookingInquiryResultBackup(BookingInquiryResultBackup bookingInquiryResultBackup)
    {
        return bookingInquiryResultBackupMapper.insertBookingInquiryResultBackup(bookingInquiryResultBackup);
    }

    /**
     * 修改询价反馈结果数据草稿
     * 
     * @param bookingInquiryResultBackup 询价反馈结果数据草稿
     * @return 结果
     */
    @Override
    public int updateBookingInquiryResultBackup(BookingInquiryResultBackup bookingInquiryResultBackup)
    {
        return bookingInquiryResultBackupMapper.updateBookingInquiryResultBackup(bookingInquiryResultBackup);
    }

    /**
     * 批量删除询价反馈结果数据草稿
     * 
     * @param ids 需要删除的询价反馈结果数据草稿ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryResultBackupByIds(Long[] ids)
    {
        return bookingInquiryResultBackupMapper.deleteBookingInquiryResultBackupByIds(ids);
    }

    /**
     * 删除询价反馈结果数据草稿信息
     * 
     * @param id 询价反馈结果数据草稿ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryResultBackupById(Long id)
    {
        return bookingInquiryResultBackupMapper.deleteBookingInquiryResultBackupById(id);
    }
}
