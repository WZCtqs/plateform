package com.szhbl.project.inquiry.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.inquiry.domain.BookingInquiryBackup;
import com.szhbl.project.inquiry.mapper.BookingInquiryBackupMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订舱询价草稿Service业务层处理
 *
 * @author szhbl
 * @date 2020-07-10
 */
@Service
public class BookingInquiryBackupServiceImpl implements IBookingInquiryBackupService {
    @Autowired
    private BookingInquiryBackupMapper bookingInquiryBackupMapper;

    /**
     * 查询订舱询价草稿
     *
     * @param id 订舱询价草稿ID
     * @return 订舱询价草稿
     */
    @Override
    public BookingInquiryBackup selectBookingInquiryBackupById(Long id) {
        return bookingInquiryBackupMapper.selectBookingInquiryBackupById(id);
    }

    /**
     * 查询订舱询价草稿列表
     *
     * @param bookingInquiryBackup 订舱询价草稿
     * @return 订舱询价草稿
     */
    @Override
    public List<BookingInquiryBackup> selectBookingInquiryBackupList(BookingInquiryBackup bookingInquiryBackup) {
        return bookingInquiryBackupMapper.selectBookingInquiryBackupList(bookingInquiryBackup);
    }

    /**
     * 新增订舱询价草稿
     *
     * @param bookingInquiryBackup 订舱询价草稿
     * @return 结果
     */
    @Override
    public int insertBookingInquiryBackup(BookingInquiryBackup bookingInquiryBackup) {
        bookingInquiryBackup.setCreateTime(DateUtils.getNowDate());

        return bookingInquiryBackupMapper.insertBookingInquiryBackup(bookingInquiryBackup);
    }

    /**
     * 修改订舱询价草稿
     *
     * @param bookingInquiryBackup 订舱询价草稿
     * @return 结果
     */
    @Override
    public int updateBookingInquiryBackup(BookingInquiryBackup bookingInquiryBackup) {
        return bookingInquiryBackupMapper.updateBookingInquiryBackup(bookingInquiryBackup);
    }

    /**
     * 批量删除订舱询价草稿
     *
     * @param ids 需要删除的订舱询价草稿ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryBackupByIds(Long[] ids) {
        return bookingInquiryBackupMapper.deleteBookingInquiryBackupByIds(ids);
    }

    /**
     * 删除订舱询价草稿信息
     *
     * @param id 订舱询价草稿ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryBackupById(Long id) {
        return bookingInquiryBackupMapper.deleteBookingInquiryBackupById(id);
    }


}
