package com.szhbl.project.inquiry.mapper;

import com.szhbl.project.inquiry.domain.BookingInquiryResultRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 询价结果费用修改记录Mapper接口
 * 
 * @author szhbl
 * @date 2021-01-20
 */
@Repository
public interface BookingInquiryResultRecordMapper 
{
    /**
     * 查询询价结果费用修改记录
     * 
     * @param id 询价结果费用修改记录ID
     * @return 询价结果费用修改记录
     */
    public BookingInquiryResultRecord selectBookingInquiryResultRecordById(Long id);

    /**
     * 查询询价结果费用修改记录列表
     * 
     * @param bookingInquiryResultRecord 询价结果费用修改记录
     * @return 询价结果费用修改记录集合
     */
    public List<BookingInquiryResultRecord> selectBookingInquiryResultRecordList(BookingInquiryResultRecord bookingInquiryResultRecord);

    /**
     * 新增询价结果费用修改记录
     * 
     * @param bookingInquiryResultRecord 询价结果费用修改记录
     * @return 结果
     */
    public int insertBookingInquiryResultRecord(BookingInquiryResultRecord bookingInquiryResultRecord);

    /**
     * 修改询价结果费用修改记录
     * 
     * @param bookingInquiryResultRecord 询价结果费用修改记录
     * @return 结果
     */
    public int updateBookingInquiryResultRecord(BookingInquiryResultRecord bookingInquiryResultRecord);

    /**
     * 删除询价结果费用修改记录
     * 
     * @param id 询价结果费用修改记录ID
     * @return 结果
     */
    public int deleteBookingInquiryResultRecordById(Long id);

    /**
     * 批量删除询价结果费用修改记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBookingInquiryResultRecordByIds(Long[] ids);
}
