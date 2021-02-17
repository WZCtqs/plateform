package com.szhbl.project.order.service.impl;

import java.util.List;

import com.szhbl.common.utils.IdUtils;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiShippingorderGoodsApplyMapper;
import com.szhbl.project.order.service.IBusiShippingorderGoodsApplyService;

/**
 * 订单商品转待审核暂存Service业务层处理
 * 
 * @author dps
 * @date 2020-04-13
 */
@Service
public class BusiShippingorderGoodsApplyServiceImpl implements IBusiShippingorderGoodsApplyService 
{
    @Autowired
    private BusiShippingorderGoodsApplyMapper busiShippingorderGoodsApplyMapper;

    /**
     * 查询订单商品转待审核暂存
     * 
     * @param orderId 订单商品转待审核暂存ID
     * @return 订单商品转待审核暂存
     */
    @Override
    public BusiShippingorderGoods selectBusiShippingorderGoodsApplyByOrderId(String orderId)
    {
        return busiShippingorderGoodsApplyMapper.selectBusiShippingorderGoodsApplyByOrderId(orderId);
    }

    /**
     * 查询订单商品转待审核暂存列表
     * 
     * @param busiShippingorderGoodsApply 订单商品转待审核暂存
     * @return 订单商品转待审核暂存
     */
    @Override
    public List<BusiShippingorderGoods> selectBusiShippingorderGoodsApplyList(BusiShippingorderGoods busiShippingorderGoodsApply)
    {
        return busiShippingorderGoodsApplyMapper.selectBusiShippingorderGoodsApplyList(busiShippingorderGoodsApply);
    }

    /**
     * 新增订单商品转待审核暂存
     * 
     * @param busiShippingorderGoodsApply 订单商品转待审核暂存
     * @return 结果
     */
    @Override
    public int insertBusiShippingorderGoodsApply(BusiShippingorderGoods busiShippingorderGoodsApply)
    {
        busiShippingorderGoodsApply.setGoodsId(IdUtils.randomUUID());
        return busiShippingorderGoodsApplyMapper.insertBusiShippingorderGoodsApply(busiShippingorderGoodsApply);
    }

    /**
     * 修改订单商品转待审核暂存
     * 
     * @param busiShippingorderGoodsApply 订单商品转待审核暂存
     * @return 结果
     */
    @Override
    public int updateBusiShippingorderGoodsApply(BusiShippingorderGoods busiShippingorderGoodsApply)
    {
        return busiShippingorderGoodsApplyMapper.updateBusiShippingorderGoodsApply(busiShippingorderGoodsApply);
    }

    /**
     * 批量删除订单商品转待审核暂存
     * 
     * @param goodsIds 需要删除的订单商品转待审核暂存ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderGoodsApplyByIds(String[] goodsIds)
    {
        return busiShippingorderGoodsApplyMapper.deleteBusiShippingorderGoodsApplyByIds(goodsIds);
    }

    /**
     * 删除订单商品转待审核暂存信息
     * 
     * @param orderId 订单商品转待审核暂存ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderGoodsApplyById(String orderId)
    {
        return busiShippingorderGoodsApplyMapper.deleteBusiShippingorderGoodsApplyById(orderId);
    }
}
