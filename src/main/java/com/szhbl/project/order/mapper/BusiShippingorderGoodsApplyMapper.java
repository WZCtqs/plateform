package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BusiShippingorderGoods;

import java.util.List;

/**
 * 订单商品转待审核暂存Mapper接口
 * 
 * @author dps
 * @date 2020-04-13
 */
public interface BusiShippingorderGoodsApplyMapper 
{
    /**
     * 查询订单商品转待审核暂存
     * 
     * @param orderId 订单商品转待审核暂存ID
     * @return 订单商品转待审核暂存
     */
    public BusiShippingorderGoods selectBusiShippingorderGoodsApplyByOrderId(String orderId);

    /**
     * 查询订单商品转待审核暂存列表
     * 
     * @param busiShippingorderGoodsApply 订单商品转待审核暂存
     * @return 订单商品转待审核暂存集合
     */
    public List<BusiShippingorderGoods> selectBusiShippingorderGoodsApplyList(BusiShippingorderGoods busiShippingorderGoodsApply);

    /**
     * 新增订单商品转待审核暂存
     * 
     * @param busiShippingorderGoodsApply 订单商品转待审核暂存
     * @return 结果
     */
    public int insertBusiShippingorderGoodsApply(BusiShippingorderGoods busiShippingorderGoodsApply);

    /**
     * 修改订单商品转待审核暂存
     * 
     * @param busiShippingorderGoodsApply 订单商品转待审核暂存
     * @return 结果
     */
    public int updateBusiShippingorderGoodsApply(BusiShippingorderGoods busiShippingorderGoodsApply);

    /**
     * 删除订单商品转待审核暂存
     * 
     * @param orderId 订单商品转待审核暂存ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsApplyById(String orderId);

    /**
     * 批量删除订单商品转待审核暂存
     * 
     * @param goodsIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsApplyByIds(String[] goodsIds);
}
