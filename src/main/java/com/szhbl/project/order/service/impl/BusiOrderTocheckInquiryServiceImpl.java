package com.szhbl.project.order.service.impl;

import com.szhbl.project.order.domain.BusiOrderTocheckInquiry;
import com.szhbl.project.order.mapper.BusiOrderTocheckInquiryMapper;
import com.szhbl.project.order.service.IBusiOrderTocheckInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 托书转待审核询价前判断结果Service业务层处理
 *
 * @author szhbl
 * @date 2020-07-16
 */
@Service
public class BusiOrderTocheckInquiryServiceImpl implements IBusiOrderTocheckInquiryService {
    @Autowired
    private BusiOrderTocheckInquiryMapper busiOrderTocheckInquiryMapper;

    /**
     * 查询托书转待审核询价前判断结果
     *
     * @param id 托书转待审核询价前判断结果ID
     * @return 托书转待审核询价前判断结果
     */
    @Override
    public BusiOrderTocheckInquiry selectBusiOrderTocheckInquiryById(Long id) {
        return busiOrderTocheckInquiryMapper.selectBusiOrderTocheckInquiryById(id);
    }

    /**
     * 查询托书转待审核询价前判断结果列表
     *
     * @param busiOrderTocheckInquiry 托书转待审核询价前判断结果
     * @return 托书转待审核询价前判断结果
     */
    @Override
    public List<BusiOrderTocheckInquiry> selectBusiOrderTocheckInquiryList(BusiOrderTocheckInquiry busiOrderTocheckInquiry) {
        return busiOrderTocheckInquiryMapper.selectBusiOrderTocheckInquiryList(busiOrderTocheckInquiry);
    }

    /**
     * 新增托书转待审核询价前判断结果
     *
     * @param busiOrderTocheckInquiry 托书转待审核询价前判断结果
     * @return 结果
     */
    @Override
    public int insertBusiOrderTocheckInquiry(BusiOrderTocheckInquiry busiOrderTocheckInquiry) {
        return busiOrderTocheckInquiryMapper.insertBusiOrderTocheckInquiry(busiOrderTocheckInquiry);
    }

    /**
     * 修改托书转待审核询价前判断结果
     *
     * @param busiOrderTocheckInquiry 托书转待审核询价前判断结果
     * @return 结果
     */
    @Override
    public int updateBusiOrderTocheckInquiry(BusiOrderTocheckInquiry busiOrderTocheckInquiry) {
        return busiOrderTocheckInquiryMapper.updateBusiOrderTocheckInquiry(busiOrderTocheckInquiry);
    }

    /**
     * 批量删除托书转待审核询价前判断结果
     *
     * @param ids 需要删除的托书转待审核询价前判断结果ID
     * @return 结果
     */
    @Override
    public int deleteBusiOrderTocheckInquiryByIds(Long[] ids) {
        return busiOrderTocheckInquiryMapper.deleteBusiOrderTocheckInquiryByIds(ids);
    }

    /**
     * 删除托书转待审核询价前判断结果信息
     *
     * @param id 托书转待审核询价前判断结果ID
     * @return 结果
     */
    @Override
    public int deleteBusiOrderTocheckInquiryById(Long id) {
        return busiOrderTocheckInquiryMapper.deleteBusiOrderTocheckInquiryById(id);
    }

    @Override
    public BusiOrderTocheckInquiry selectTocheckInquiryByOrderId(String orderId) {
        return busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(orderId);
    }
}
