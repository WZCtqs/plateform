package com.szhbl.project.customerservice.service;

import com.szhbl.project.customerservice.domain.BusiShippingorderGoodssh;
import java.util.List;

/**
 * 订单商品Service接口
 * 
 * @author b
 * @date 2020-03-27
 */
public interface IBusiShippingorderGoodsshService 
{
    /**
     * 查询订单商品
     * 
     * @param goodsId 订单商品ID
     * @return 订单商品
     */
    public BusiShippingorderGoodssh selectBusiShippingorderGoodsshById(Long goodsId);

    /**
     * 查询订单商品列表
     * 
     * @param busiShippingorderGoodssh 订单商品
     * @return 订单商品集合
     */
    public List<BusiShippingorderGoodssh> selectBusiShippingorderGoodsshList(BusiShippingorderGoodssh busiShippingorderGoodssh);

    /**
     * 新增订单商品
     * 
     * @param busiShippingorderGoodssh 订单商品
     * @return 结果
     */
    public int insertBusiShippingorderGoodssh(BusiShippingorderGoodssh busiShippingorderGoodssh);

    /**
     * 修改订单商品
     * 
     * @param busiShippingorderGoodssh 订单商品
     * @return 结果
     */
    public int updateBusiShippingorderGoodssh(BusiShippingorderGoodssh busiShippingorderGoodssh);

    /**
     * 批量删除订单商品
     * 
     * @param goodsIds 需要删除的订单商品ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsshByIds(Long[] goodsIds);

    /**
     * 删除订单商品信息
     * 
     * @param goodsId 订单商品ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsshById(Long goodsId);
}
