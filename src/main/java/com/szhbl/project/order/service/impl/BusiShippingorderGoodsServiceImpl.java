package com.szhbl.project.order.service.impl;

import java.util.List;

import com.szhbl.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiShippingorderGoodsMapper;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.service.IBusiShippingorderGoodsService;

/**
 * 订单商品Service业务层处理
 * 
 * @author dps
 * @date 2020-03-24
 */
@Service
public class BusiShippingorderGoodsServiceImpl implements IBusiShippingorderGoodsService 
{
    @Autowired
    private BusiShippingorderGoodsMapper busiShippingorderGoodsMapper;

    /**
     * 查询订单商品
     * 
     * @param goodsId 订单商品ID
     * @return 订单商品
     */
    @Override
    public BusiShippingorderGoods selectBusiShippingorderGoodsById(String goodsId)
    {
        return busiShippingorderGoodsMapper.selectBusiShippingorderGoodsById(goodsId);
    }

    /**
     * 查询订单商品,根据托书id
     * 
     * @param orderId 订单ID
     * @return 订单商品
     */
    @Override
    public BusiShippingorderGoods selectBusiShippingorderGoodsByOrderId(String orderId)
    {
        return busiShippingorderGoodsMapper.selectBusiShippingorderGoodsByOrderId(orderId);
    }

    /**
     * 查询订单商品列表
     * 
     * @param busiShippingorderGoods 订单商品
     * @return 订单商品
     */
    @Override
    public List<BusiShippingorderGoods> selectBusiShippingorderGoodsList(BusiShippingorderGoods busiShippingorderGoods)
    {
        return busiShippingorderGoodsMapper.selectBusiShippingorderGoodsList(busiShippingorderGoods);
    }

    /**
     * 新增订单商品
     * 
     * @param busiShippingorderGoods 订单商品
     * @return 结果
     */
    @Override
    public int insertBusiShippingorderGoods(BusiShippingorderGoods busiShippingorderGoods)
    {
        busiShippingorderGoods.setGoodsId(IdUtils.randomUUID());
        return busiShippingorderGoodsMapper.insertBusiShippingorderGoods(busiShippingorderGoods);
    }

    /**
     * 修改订单商品
     * 
     * @param busiShippingorderGoods 订单商品
     * @return 结果
     */
    @Override
    public int updateBusiShippingorderGoods(BusiShippingorderGoods busiShippingorderGoods)
    {
        return busiShippingorderGoodsMapper.updateBusiShippingorderGoods(busiShippingorderGoods);
    }

    /**
     * 批量删除订单商品
     * 
     * @param goodsIds 需要删除的订单商品ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderGoodsByIds(String[] goodsIds)
    {
        return busiShippingorderGoodsMapper.deleteBusiShippingorderGoodsByIds(goodsIds);
    }

    /**
     * 删除订单商品信息
     * 
     * @param goodsId 订单商品ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderGoodsById(String goodsId)
    {
        return busiShippingorderGoodsMapper.deleteBusiShippingorderGoodsById(goodsId);
    }
}
