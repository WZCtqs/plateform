package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BusiShippingorders;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单转待审核暂存Mapper接口
 * 
 * @author dps
 * @date 2020-04-13
 */
@Repository
public interface BusiShippingorderApplyMapper 
{
    /**
     * 查询订单转待审核暂存
     * 
     * @param orderId 订单转待审核暂存ID
     * @return 订单转待审核暂存
     */
    public BusiShippingorders selectBusiShippingorderApplyById(String orderId);

    /**
     * 查询订单转待审核暂存列表
     * 
     * @param busiShippingorderApply 订单转待审核暂存
     * @return 订单转待审核暂存集合
     */
    public List<BusiShippingorders> selectBusiShippingorderApplyList(BusiShippingorders busiShippingorderApply);

    /**
     * 新增订单转待审核暂存
     * 
     * @param busiShippingorderApply 订单转待审核暂存
     * @return 结果
     */
    public int insertBusiShippingorderApply(BusiShippingorders busiShippingorderApply);

    /**
     * 修改订单转待审核暂存
     * 
     * @param busiShippingorderApply 订单转待审核暂存
     * @return 结果
     */
    public int updateBusiShippingorderApply(BusiShippingorders busiShippingorderApply);

    /**
     * 删除订单转待审核暂存
     * 
     * @param orderId 订单转待审核暂存ID
     * @return 结果
     */
    public int deleteBusiShippingorderApplyById(String orderId);

    /**
     * 批量删除订单转待审核暂存
     * 
     * @param orderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiShippingorderApplyByIds(String[] orderIds);
}
