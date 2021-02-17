package com.szhbl.project.cabinarrangement.service;

import com.szhbl.project.cabinarrangement.domain.BusiShippingorderGoodsPc;
import java.util.List;

/**
 * 订单商品Service接口
 * 
 * @author szhbl
 * @date 2020-01-15
 */
public interface IBusiShippingorderGoodsPcService
{
    /**
     * 查询订单商品
     * 
     * @param goodsId 订单商品ID
     * @return 订单商品
     */
    public BusiShippingorderGoodsPc selectBusiShippingorderGoodsById(String goodsId);

    /**
     * 查询订单商品列表
     * 
     * @param busiShippingorderGoodsPc 订单商品
     * @return 订单商品集合
     */
    public List<BusiShippingorderGoodsPc> selectBusiShippingorderGoodsList(BusiShippingorderGoodsPc busiShippingorderGoodsPc);

    /**
     * 新增订单商品
     * 
     * @param busiShippingorderGoodsPc 订单商品
     * @return 结果
     */
    public int insertBusiShippingorderGoods(BusiShippingorderGoodsPc busiShippingorderGoodsPc);

    /**
     * 修改订单商品
     * 
     * @param busiShippingorderGoodsPc 订单商品
     * @return 结果
     */
    public int updateBusiShippingorderGoods(BusiShippingorderGoodsPc busiShippingorderGoodsPc);

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
