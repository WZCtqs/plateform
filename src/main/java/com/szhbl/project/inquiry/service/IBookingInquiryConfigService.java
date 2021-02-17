package com.szhbl.project.inquiry.service;

import com.szhbl.project.inquiry.domain.BookingInquiryConfig;

import java.util.List;

/**
 * 询价系统设置Service接口
 *
 * @author szhbl
 * @date 2020-10-09
 */
public interface IBookingInquiryConfigService {
    /**
     * 查询询价系统设置
     *
     * @param id 询价系统设置ID
     * @return 询价系统设置
     */
    public BookingInquiryConfig selectBookingInquiryConfigById(Long id);

    public BookingInquiryConfig selectBookingInquiryConfigByKey(String typeKey, Integer lineType);

    /**
     * 查询询价系统设置列表
     *
     * @param bookingInquiryConfig 询价系统设置
     * @return 询价系统设置集合
     */
    public List<BookingInquiryConfig> selectBookingInquiryConfigList(BookingInquiryConfig bookingInquiryConfig);

    /**
     * 新增询价系统设置
     *
     * @param bookingInquiryConfig 询价系统设置
     * @return 结果
     */
    public int insertBookingInquiryConfig(BookingInquiryConfig bookingInquiryConfig);

    /**
     * 修改询价系统设置
     *
     * @param bookingInquiryConfig 询价系统设置
     * @return 结果
     */
    public int updateBookingInquiryConfig(BookingInquiryConfig bookingInquiryConfig);

    /**
     * 批量删除询价系统设置
     *
     * @param ids 需要删除的询价系统设置ID
     * @return 结果
     */
    public int deleteBookingInquiryConfigByIds(Long[] ids);

    /**
     * 删除询价系统设置信息
     *
     * @param id 询价系统设置ID
     * @return 结果
     */
    public int deleteBookingInquiryConfigById(Long id);
}
