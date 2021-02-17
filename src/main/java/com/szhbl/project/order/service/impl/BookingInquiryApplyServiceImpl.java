package com.szhbl.project.order.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BookingInquiryApplyMapper;
import com.szhbl.project.order.domain.BookingInquiryApply;
import com.szhbl.project.order.service.IBookingInquiryApplyService;

/**
 * 订舱询价暂存Service业务层处理
 * 
 * @author dps
 * @date 2020-05-20
 */
@Service
public class BookingInquiryApplyServiceImpl implements IBookingInquiryApplyService 
{
    @Autowired
    private BookingInquiryApplyMapper bookingInquiryApplyMapper;

    /**
     * 查询订舱询价暂存
     * 
     * @param id 订舱询价暂存ID
     * @return 订舱询价暂存
     */
    @Override
    public BookingInquiryApply selectBookingInquiryApplyById(Long id)
    {
        return bookingInquiryApplyMapper.selectBookingInquiryApplyById(id);
    }

    /**
     * 查询订舱询价暂存列表
     * 
     * @param bookingInquiryApply 订舱询价暂存
     * @return 订舱询价暂存
     */
    @Override
    public List<BookingInquiryApply> selectBookingInquiryApplyList(BookingInquiryApply bookingInquiryApply)
    {
        return bookingInquiryApplyMapper.selectBookingInquiryApplyList(bookingInquiryApply);
    }

    /**
     * 新增订舱询价暂存
     * 
     * @param bookingInquiryApply 订舱询价暂存
     * @return 结果
     */
    @Override
    public int insertBookingInquiryApply(BookingInquiryApply bookingInquiryApply)
    {
        bookingInquiryApply.setCreateTime(DateUtils.getNowDate());
        return bookingInquiryApplyMapper.insertBookingInquiryApply(bookingInquiryApply);
    }

    /**
     * 修改订舱询价暂存
     * 
     * @param bookingInquiryApply 订舱询价暂存
     * @return 结果
     */
    @Override
    public int updateBookingInquiryApply(BookingInquiryApply bookingInquiryApply)
    {
        return bookingInquiryApplyMapper.updateBookingInquiryApply(bookingInquiryApply);
    }

    /**
     * 批量删除订舱询价暂存
     * 
     * @param ids 需要删除的订舱询价暂存ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryApplyByIds(Long[] ids)
    {
        return bookingInquiryApplyMapper.deleteBookingInquiryApplyByIds(ids);
    }

    /**
     * 删除订舱询价暂存信息
     * 
     * @param id 订舱询价暂存ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryApplyById(Long id)
    {
        return bookingInquiryApplyMapper.deleteBookingInquiryApplyById(id);
    }
}
