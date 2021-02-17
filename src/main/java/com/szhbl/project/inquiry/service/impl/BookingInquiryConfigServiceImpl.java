package com.szhbl.project.inquiry.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.inquiry.domain.BookingInquiryConfig;
import com.szhbl.project.inquiry.mapper.BookingInquiryConfigMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 询价系统设置Service业务层处理
 *
 * @author szhbl
 * @date 2020-10-09
 */
@Service
public class BookingInquiryConfigServiceImpl implements IBookingInquiryConfigService {
    @Autowired
    private BookingInquiryConfigMapper bookingInquiryConfigMapper;

    /**
     * 查询询价系统设置
     *
     * @param id 询价系统设置ID
     * @return 询价系统设置
     */
    @Override
    public BookingInquiryConfig selectBookingInquiryConfigById(Long id) {
        return bookingInquiryConfigMapper.selectBookingInquiryConfigById(id);
    }

    @Override
    public BookingInquiryConfig selectBookingInquiryConfigByKey(String typeKey, Integer lineType) {
        return bookingInquiryConfigMapper.selectBookingInquiryConfigByKey(typeKey, lineType);
    }

    /**
     * 查询询价系统设置列表
     *
     * @param bookingInquiryConfig 询价系统设置
     * @return 询价系统设置
     */
    @Override
    public List<BookingInquiryConfig> selectBookingInquiryConfigList(BookingInquiryConfig bookingInquiryConfig) {
        return bookingInquiryConfigMapper.selectBookingInquiryConfigList(bookingInquiryConfig);
    }

    /**
     * 新增询价系统设置
     *
     * @param bookingInquiryConfig 询价系统设置
     * @return 结果
     */
    @Override
    public int insertBookingInquiryConfig(BookingInquiryConfig bookingInquiryConfig) {
        bookingInquiryConfig.setCreateTime(DateUtils.getNowDate());
        return bookingInquiryConfigMapper.insertBookingInquiryConfig(bookingInquiryConfig);
    }

    /**
     * 修改询价系统设置
     *
     * @param bookingInquiryConfig 询价系统设置
     * @return 结果
     */
    @Override
    public int updateBookingInquiryConfig(BookingInquiryConfig bookingInquiryConfig) {
        bookingInquiryConfig.setUpdateTime(DateUtils.getNowDate());
        return bookingInquiryConfigMapper.updateBookingInquiryConfig(bookingInquiryConfig);
    }

    /**
     * 批量删除询价系统设置
     *
     * @param ids 需要删除的询价系统设置ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryConfigByIds(Long[] ids) {
        return bookingInquiryConfigMapper.deleteBookingInquiryConfigByIds(ids);
    }

    /**
     * 删除询价系统设置信息
     *
     * @param id 询价系统设置ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryConfigById(Long id) {
        return bookingInquiryConfigMapper.deleteBookingInquiryConfigById(id);
    }
}
