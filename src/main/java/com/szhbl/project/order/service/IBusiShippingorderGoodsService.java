package com.szhbl.project.order.service;

import com.szhbl.project.order.domain.BusiShippingorderGoods;
import java.util.List;

/**
 * 订单商品Service接口
 * 
 * @author dps
 * @date 2020-03-24
 */
public interface IBusiShippingorderGoodsService 
{
    /**
     * 查询订单商品
     * 
     * @param goodsId 订单商品ID
     * @return 订单商品
     */
    public BusiShippingorderGoods selectBusiShippingorderGoodsById(String goodsId);

    /**
     * 查询订单商品，根据托书id
     * 
     * @param orderId 订单ID
     * @return 订单商品
     */
    public BusiShippingorderGoods selectBusiShippingorderGoodsByOrderId(String orderId);

    /**
     * 查询订单商品列表
     * 
     * @param busiShippingorderGoods 订单商品
     * @return 订单商品集合
     */
    public List<BusiShippingorderGoods> selectBusiShippingorderGoodsList(BusiShippingorderGoods busiShippingorderGoods);

    /**
     * 新增订单商品
     * 
     * @param busiShippingorderGoods 订单商品
     * @return 结果
     */
    public int insertBusiShippingorderGoods(BusiShippingorderGoods busiShippingorderGoods);

    /**
     * 修改订单商品
     * 
     * @param busiShippingorderGoods 订单商品
     * @return 结果
     */
    public int updateBusiShippingorderGoods(BusiShippingorderGoods busiShippingorderGoods);

    /**
     * 批量删除订单商品
     * 
     * @param goodsIds 需要删除的订单商品ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsByIds(String[] goodsIds);

    /**
     * 删除订单商品信息
     * 
     * @param goodsId 订单商品ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsById(String goodsId);
}
