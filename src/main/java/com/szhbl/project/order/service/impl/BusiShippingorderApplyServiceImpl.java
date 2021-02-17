package com.szhbl.project.order.service.impl;

import java.util.List;

import com.szhbl.project.order.domain.BusiShippingorders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiShippingorderApplyMapper;
import com.szhbl.project.order.service.IBusiShippingorderApplyService;

/**
 * 订单转待审核暂存Service业务层处理
 * 
 * @author dps
 * @date 2020-04-13
 */
@Service
public class BusiShippingorderApplyServiceImpl implements IBusiShippingorderApplyService 
{
    @Autowired
    private BusiShippingorderApplyMapper busiShippingorderApplyMapper;

    /**
     * 查询订单转待审核暂存
     * 
     * @param orderId 订单转待审核暂存ID
     * @return 订单转待审核暂存
     */
    @Override
    public BusiShippingorders selectBusiShippingorderApplyById(String orderId)
    {
        return busiShippingorderApplyMapper.selectBusiShippingorderApplyById(orderId);
    }

    /**
     * 查询订单转待审核暂存列表
     * 
     * @param busiShippingorderApply 订单转待审核暂存
     * @return 订单转待审核暂存
     */
    @Override
    public List<BusiShippingorders> selectBusiShippingorderApplyList(BusiShippingorders busiShippingorderApply)
    {
        return busiShippingorderApplyMapper.selectBusiShippingorderApplyList(busiShippingorderApply);
    }

    /**
     * 新增订单转待审核暂存
     * 
     * @param busiShippingorderApply 订单转待审核暂存
     * @return 结果
     */
    @Override
    public int insertBusiShippingorderApply(BusiShippingorders busiShippingorderApply)
    {
        return busiShippingorderApplyMapper.insertBusiShippingorderApply(busiShippingorderApply);
    }

    /**
     * 修改订单转待审核暂存
     * 
     * @param busiShippingorderApply 订单转待审核暂存
     * @return 结果
     */
    @Override
    public int updateBusiShippingorderApply(BusiShippingorders busiShippingorderApply)
    {
        return busiShippingorderApplyMapper.updateBusiShippingorderApply(busiShippingorderApply);
    }

    /**
     * 批量删除订单转待审核暂存
     * 
     * @param orderIds 需要删除的订单转待审核暂存ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderApplyByIds(String[] orderIds)
    {
        return busiShippingorderApplyMapper.deleteBusiShippingorderApplyByIds(orderIds);
    }

    /**
     * 删除订单转待审核暂存信息
     * 
     * @param orderId 订单转待审核暂存ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderApplyById(String orderId)
    {
        return busiShippingorderApplyMapper.deleteBusiShippingorderApplyById(orderId);
    }
}
