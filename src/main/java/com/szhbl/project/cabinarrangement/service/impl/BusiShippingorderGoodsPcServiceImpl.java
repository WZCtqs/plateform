package com.szhbl.project.cabinarrangement.service.impl;

import java.util.List;

import com.szhbl.project.cabinarrangement.domain.BusiShippingorderGoodsPc;
import com.szhbl.project.cabinarrangement.mapper.BusiShippingorderGoodsPcMapper;
import com.szhbl.project.cabinarrangement.service.IBusiShippingorderGoodsPcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 订单商品Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-15
 */
@Service
public class BusiShippingorderGoodsPcServiceImpl implements IBusiShippingorderGoodsPcService
{
    @Autowired
    private BusiShippingorderGoodsPcMapper busiShippingorderGoodsMapper;

    /**
     * 查询订单商品
     * 
     * @param goodsId 订单商品ID
     * @return 订单商品
     */
    @Override
    public BusiShippingorderGoodsPc selectBusiShippingorderGoodsById(String goodsId)
    {
        return busiShippingorderGoodsMapper.selectBusiShippingorderGoodsById(goodsId);
    }

    /**
     * 查询订单商品列表
     * 
     * @param busiShippingorderGoods 订单商品
     * @return 订单商品
     */
    @Override
    public List<BusiShippingorderGoodsPc> selectBusiShippingorderGoodsList(BusiShippingorderGoodsPc busiShippingorderGoods)
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
    public int insertBusiShippingorderGoods(BusiShippingorderGoodsPc busiShippingorderGoods)
    {
        return busiShippingorderGoodsMapper.insertBusiShippingorderGoods(busiShippingorderGoods);
    }

    /**
     * 修改订单商品
     * 
     * @param busiShippingorderGoods 订单商品
     * @return 结果
     */
    @Override
    public int updateBusiShippingorderGoods(BusiShippingorderGoodsPc busiShippingorderGoods)
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
