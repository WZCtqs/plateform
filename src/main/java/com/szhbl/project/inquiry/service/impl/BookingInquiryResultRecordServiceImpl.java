package com.szhbl.project.inquiry.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.inquiry.mapper.BookingInquiryResultRecordMapper;
import com.szhbl.project.inquiry.domain.BookingInquiryResultRecord;
import com.szhbl.project.inquiry.service.IBookingInquiryResultRecordService;

/**
 * 询价结果费用修改记录Service业务层处理
 * 
 * @author szhbl
 * @date 2021-01-20
 */
@Service
public class BookingInquiryResultRecordServiceImpl implements IBookingInquiryResultRecordService 
{
    @Autowired
    private BookingInquiryResultRecordMapper bookingInquiryResultRecordMapper;

    /**
     * 查询询价结果费用修改记录
     * 
     * @param id 询价结果费用修改记录ID
     * @return 询价结果费用修改记录
     */
    @Override
    public BookingInquiryResultRecord selectBookingInquiryResultRecordById(Long id)
    {
        return bookingInquiryResultRecordMapper.selectBookingInquiryResultRecordById(id);
    }

    /**
     * 查询询价结果费用修改记录列表
     * 
     * @param bookingInquiryResultRecord 询价结果费用修改记录
     * @return 询价结果费用修改记录
     */
    @Override
    public List<BookingInquiryResultRecord> selectBookingInquiryResultRecordList(BookingInquiryResultRecord bookingInquiryResultRecord)
    {
        return bookingInquiryResultRecordMapper.selectBookingInquiryResultRecordList(bookingInquiryResultRecord);
    }

    /**
     * 新增询价结果费用修改记录
     * 
     * @param bookingInquiryResultRecord 询价结果费用修改记录
     * @return 结果
     */
    @Override
    public int insertBookingInquiryResultRecord(BookingInquiryResultRecord bookingInquiryResultRecord)
    {
        return bookingInquiryResultRecordMapper.insertBookingInquiryResultRecord(bookingInquiryResultRecord);
    }

    /**
     * 修改询价结果费用修改记录
     * 
     * @param bookingInquiryResultRecord 询价结果费用修改记录
     * @return 结果
     */
    @Override
    public int updateBookingInquiryResultRecord(BookingInquiryResultRecord bookingInquiryResultRecord)
    {
        return bookingInquiryResultRecordMapper.updateBookingInquiryResultRecord(bookingInquiryResultRecord);
    }

    /**
     * 批量删除询价结果费用修改记录
     * 
     * @param ids 需要删除的询价结果费用修改记录ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryResultRecordByIds(Long[] ids)
    {
        return bookingInquiryResultRecordMapper.deleteBookingInquiryResultRecordByIds(ids);
    }

    /**
     * 删除询价结果费用修改记录信息
     * 
     * @param id 询价结果费用修改记录ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryResultRecordById(Long id)
    {
        return bookingInquiryResultRecordMapper.deleteBookingInquiryResultRecordById(id);
    }
}
