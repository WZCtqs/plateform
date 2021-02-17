package com.szhbl.project.inquiry.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.inquiry.mapper.BookingInquiryGoodsDetailsBackupMapper;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetailsBackup;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsBackupService;

/**
 * 订舱询价-拼箱货物详情草稿Service业务层处理
 * 
 * @author szhbl
 * @date 2020-07-10
 */
@Service
public class BookingInquiryGoodsDetailsBackupServiceImpl implements IBookingInquiryGoodsDetailsBackupService 
{
    @Autowired
    private BookingInquiryGoodsDetailsBackupMapper bookingInquiryGoodsDetailsBackupMapper;

    /**
     * 查询订舱询价-拼箱货物详情草稿
     * 
     * @param id 订舱询价-拼箱货物详情草稿ID
     * @return 订舱询价-拼箱货物详情草稿
     */
    @Override
    public BookingInquiryGoodsDetailsBackup selectBookingInquiryGoodsDetailsBackupById(Long id)
    {
        return bookingInquiryGoodsDetailsBackupMapper.selectBookingInquiryGoodsDetailsBackupById(id);
    }

    /**
     * 查询订舱询价-拼箱货物详情草稿列表
     * 
     * @param bookingInquiryGoodsDetailsBackup 订舱询价-拼箱货物详情草稿
     * @return 订舱询价-拼箱货物详情草稿
     */
    @Override
    public List<BookingInquiryGoodsDetailsBackup> selectBookingInquiryGoodsDetailsBackupList(BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup)
    {
        return bookingInquiryGoodsDetailsBackupMapper.selectBookingInquiryGoodsDetailsBackupList(bookingInquiryGoodsDetailsBackup);
    }

    /**
     * 新增订舱询价-拼箱货物详情草稿
     * 
     * @param bookingInquiryGoodsDetailsBackup 订舱询价-拼箱货物详情草稿
     * @return 结果
     */
    @Override
    public int insertBookingInquiryGoodsDetailsBackup(BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup)
    {
        return bookingInquiryGoodsDetailsBackupMapper.insertBookingInquiryGoodsDetailsBackup(bookingInquiryGoodsDetailsBackup);
    }

    /**
     * 修改订舱询价-拼箱货物详情草稿
     * 
     * @param bookingInquiryGoodsDetailsBackup 订舱询价-拼箱货物详情草稿
     * @return 结果
     */
    @Override
    public int updateBookingInquiryGoodsDetailsBackup(BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup)
    {
        return bookingInquiryGoodsDetailsBackupMapper.updateBookingInquiryGoodsDetailsBackup(bookingInquiryGoodsDetailsBackup);
    }

    /**
     * 批量删除订舱询价-拼箱货物详情草稿
     * 
     * @param ids 需要删除的订舱询价-拼箱货物详情草稿ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryGoodsDetailsBackupByIds(Long[] ids)
    {
        return bookingInquiryGoodsDetailsBackupMapper.deleteBookingInquiryGoodsDetailsBackupByIds(ids);
    }

    /**
     * 删除订舱询价-拼箱货物详情草稿信息
     * 
     * @param id 订舱询价-拼箱货物详情草稿ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryGoodsDetailsBackupById(Long id)
    {
        return bookingInquiryGoodsDetailsBackupMapper.deleteBookingInquiryGoodsDetailsBackupById(id);
    }
}
