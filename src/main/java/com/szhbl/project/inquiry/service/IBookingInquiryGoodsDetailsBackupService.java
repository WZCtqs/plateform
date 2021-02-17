package com.szhbl.project.inquiry.service;

import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetailsBackup;
import java.util.List;

/**
 * 订舱询价-拼箱货物详情草稿Service接口
 * 
 * @author szhbl
 * @date 2020-07-10
 */
public interface IBookingInquiryGoodsDetailsBackupService 
{
    /**
     * 查询订舱询价-拼箱货物详情草稿
     * 
     * @param id 订舱询价-拼箱货物详情草稿ID
     * @return 订舱询价-拼箱货物详情草稿
     */
    public BookingInquiryGoodsDetailsBackup selectBookingInquiryGoodsDetailsBackupById(Long id);

    /**
     * 查询订舱询价-拼箱货物详情草稿列表
     * 
     * @param bookingInquiryGoodsDetailsBackup 订舱询价-拼箱货物详情草稿
     * @return 订舱询价-拼箱货物详情草稿集合
     */
    public List<BookingInquiryGoodsDetailsBackup> selectBookingInquiryGoodsDetailsBackupList(BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup);

    /**
     * 新增订舱询价-拼箱货物详情草稿
     * 
     * @param bookingInquiryGoodsDetailsBackup 订舱询价-拼箱货物详情草稿
     * @return 结果
     */
    public int insertBookingInquiryGoodsDetailsBackup(BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup);

    /**
     * 修改订舱询价-拼箱货物详情草稿
     * 
     * @param bookingInquiryGoodsDetailsBackup 订舱询价-拼箱货物详情草稿
     * @return 结果
     */
    public int updateBookingInquiryGoodsDetailsBackup(BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup);

    /**
     * 批量删除订舱询价-拼箱货物详情草稿
     * 
     * @param ids 需要删除的订舱询价-拼箱货物详情草稿ID
     * @return 结果
     */
    public int deleteBookingInquiryGoodsDetailsBackupByIds(Long[] ids);

    /**
     * 删除订舱询价-拼箱货物详情草稿信息
     * 
     * @param id 订舱询价-拼箱货物详情草稿ID
     * @return 结果
     */
    public int deleteBookingInquiryGoodsDetailsBackupById(Long id);
}
