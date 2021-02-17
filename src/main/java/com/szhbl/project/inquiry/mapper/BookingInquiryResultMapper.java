package com.szhbl.project.inquiry.mapper;

import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 询价反馈结果数据Mapper接口
 *
 * @author jhm
 * @date 2020-04-08
 */
@Repository
public interface BookingInquiryResultMapper {
    /**
     * 查询询价反馈结果数据
     *
     * @param id 询价反馈结果数据ID
     * @return 询价反馈结果数据
     */
    public BookingInquiryResult selectBookingInquiryResultById(Long id);

    /**
     * 查询询价反馈结果数据列表
     *
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 询价反馈结果数据集合
     */
    public List<BookingInquiryResult> selectBookingInquiryResultList(BookingInquiryResult bookingInquiryResult);

    /**
     * 新增询价反馈结果数据
     *
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 结果
     */
    public int insertBookingInquiryResult(BookingInquiryResult bookingInquiryResult);

    /**
     * 修改询价反馈结果数据
     *
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 结果
     */
    public int updateBookingInquiryResult(BookingInquiryResult bookingInquiryResult);

    /**
     * 删除询价反馈结果数据
     *
     * @param id 询价反馈结果数据ID
     * @return 结果
     */
    public int deleteBookingInquiryResultById(Long id);

    /**
     * 批量删除询价反馈结果数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBookingInquiryResultByIds(Long[] ids);

    /**
     * 查询是否存在相同的询价数据
     *
     * @param inquiryId
     * @return
     */
    public List<BookingInquiryResult> selectBookingInquiryResultWithInquiryId(Long inquiryId);

    /**
     * 更新询价结果
     *
     * @param bookingInquiryResult
     * @return
     */
    public int updateBookingInquiryResultWithInquiryId(BookingInquiryResult bookingInquiryResult);

    /**
     * 根据询价编号查询询价结果
     *
     * @param inquiryNum
     * @return
     */
    public BookingInquiryResult selectBookingInquiryResultByInquiryNum(String inquiryNum);

    /**
     * 根据订单id获取集疏报价编码
     *
     * @param orderId
     * @return
     */
    public String getJsNumByOrderId(String orderId);

    /**
     * 置空部分数据
     *
     * @param bookingInquiryResult 询价反馈结果数据ID
     * @return 结果
     */
    public int updateBookingInquiryResultById(BookingInquiryResult bookingInquiryResult);

    String getLastInquiryHxd(String orderId);

    Date selectInquiryClassDateByOrderId(String orderId);
}
