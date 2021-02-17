package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BusiOrderTocheckInquiry;

import java.util.List;

/**
 * 托书转待审核询价前判断结果Mapper接口
 *
 * @author szhbl
 * @date 2020-07-16
 */
public interface BusiOrderTocheckInquiryMapper {
    /**
     * 查询托书转待审核询价前判断结果
     *
     * @param id 托书转待审核询价前判断结果ID
     * @return 托书转待审核询价前判断结果
     */
    public BusiOrderTocheckInquiry selectBusiOrderTocheckInquiryById(Long id);

    /**
     * 查询托书转待审核询价前判断结果列表
     *
     * @param busiOrderTocheckInquiry 托书转待审核询价前判断结果
     * @return 托书转待审核询价前判断结果集合
     */
    public List<BusiOrderTocheckInquiry> selectBusiOrderTocheckInquiryList(BusiOrderTocheckInquiry busiOrderTocheckInquiry);

    /**
     * 新增托书转待审核询价前判断结果
     *
     * @param busiOrderTocheckInquiry 托书转待审核询价前判断结果
     * @return 结果
     */
    public int insertBusiOrderTocheckInquiry(BusiOrderTocheckInquiry busiOrderTocheckInquiry);

    /**
     * 修改托书转待审核询价前判断结果
     *
     * @param busiOrderTocheckInquiry 托书转待审核询价前判断结果
     * @return 结果
     */
    public int updateBusiOrderTocheckInquiry(BusiOrderTocheckInquiry busiOrderTocheckInquiry);

    /**
     * 删除托书转待审核询价前判断结果
     *
     * @param id 托书转待审核询价前判断结果ID
     * @return 结果
     */
    public int deleteBusiOrderTocheckInquiryById(Long id);

    /**
     * 批量删除托书转待审核询价前判断结果
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiOrderTocheckInquiryByIds(Long[] ids);

    /**
     * 根据orderid查询最新的询价判断结果
     *
     * @param orderId
     * @return
     */
    public BusiOrderTocheckInquiry selectTocheckInquiryByOrderId(String orderId);

    int updateByOrderId(BusiOrderTocheckInquiry busiOrderTocheckInquiry);
}
