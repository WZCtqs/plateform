package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.BusiShippingorderGoodsshMapper;
import com.szhbl.project.customerservice.domain.BusiShippingorderGoodssh;
import com.szhbl.project.customerservice.service.IBusiShippingorderGoodsshService;

/**
 * 订单商品Service业务层处理
 * 
 * @author b
 * @date 2020-03-27
 */
@Service
public class BusiShippingorderGoodsshServiceImpl implements IBusiShippingorderGoodsshService 
{
    @Autowired
    private BusiShippingorderGoodsshMapper busiShippingorderGoodsshMapper;

    /**
     * 查询订单商品
     * 
     * @param goodsId 订单商品ID
     * @return 订单商品
     */
    @Override
    public BusiShippingorderGoodssh selectBusiShippingorderGoodsshById(Long goodsId)
    {
        return busiShippingorderGoodsshMapper.selectBusiShippingorderGoodsshById(goodsId);
    }

    /**
     * 查询订单商品列表
     * 
     * @param busiShippingorderGoodssh 订单商品
     * @return 订单商品
     */
    @Override
    public List<BusiShippingorderGoodssh> selectBusiShippingorderGoodsshList(BusiShippingorderGoodssh busiShippingorderGoodssh)
    {
        return busiShippingorderGoodsshMapper.selectBusiShippingorderGoodsshList(busiShippingorderGoodssh);
    }

    /**
     * 新增订单商品
     * 
     * @param busiShippingorderGoodssh 订单商品
     * @return 结果
     */
    @Override
    public int insertBusiShippingorderGoodssh(BusiShippingorderGoodssh busiShippingorderGoodssh)
    {
        return busiShippingorderGoodsshMapper.insertBusiShippingorderGoodssh(busiShippingorderGoodssh);
    }

    /**
     * 修改订单商品
     * 
     * @param busiShippingorderGoodssh 订单商品
     * @return 结果
     */
    @Override
    public int updateBusiShippingorderGoodssh(BusiShippingorderGoodssh busiShippingorderGoodssh)
    {
        return busiShippingorderGoodsshMapper.updateBusiShippingorderGoodssh(busiShippingorderGoodssh);
    }

    /**
     * 批量删除订单商品
     * 
     * @param goodsIds 需要删除的订单商品ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderGoodsshByIds(Long[] goodsIds)
    {
        return busiShippingorderGoodsshMapper.deleteBusiShippingorderGoodsshByIds(goodsIds);
    }

    /**
     * 删除订单商品信息
     * 
     * @param goodsId 订单商品ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderGoodsshById(Long goodsId)
    {
        return busiShippingorderGoodsshMapper.deleteBusiShippingorderGoodsshById(goodsId);
    }
}
