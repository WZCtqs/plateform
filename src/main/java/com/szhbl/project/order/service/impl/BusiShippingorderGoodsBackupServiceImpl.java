package com.szhbl.project.order.service.impl;

import java.util.List;

import com.szhbl.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiShippingorderGoodsBackupMapper;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.service.IBusiShippingorderGoodsBackupService;

/**
 * 订单商品备份Service业务层处理
 * 
 * @author dps
 * @date 2020-03-24
 */
@Service
public class BusiShippingorderGoodsBackupServiceImpl implements IBusiShippingorderGoodsBackupService 
{
    @Autowired
    private BusiShippingorderGoodsBackupMapper busiShippingorderGoodsBackupMapper;

    /**
     * 查询订单商品备份
     * 
     * @param goodsId 订单商品备份ID
     * @return 订单商品备份
     */
    @Override
    public BusiShippingorderGoods selectBusiShippingorderGoodsBackupById(String goodsId)
    {
        return busiShippingorderGoodsBackupMapper.selectBusiShippingorderGoodsBackupById(goodsId);
    }

    /**
     * 查询订单商品备份,根据托书id
     * 
     * @param orderId 订单ID
     * @return 订单商品备份
     */
    @Override
    public BusiShippingorderGoods selectBusiShippingorderGoodsBackupByOrderId(String orderId)
    {
        return busiShippingorderGoodsBackupMapper.selectBusiShippingorderGoodsBackupByOrderId(orderId);
    }

    /**
     * 查询订单商品备份列表
     * 
     * @param busiShippingorderGoodsBackup 订单商品备份
     * @return 订单商品备份
     */
    @Override
    public List<BusiShippingorderGoods> selectBusiShippingorderGoodsBackupList(BusiShippingorderGoods busiShippingorderGoodsBackup)
    {
        return busiShippingorderGoodsBackupMapper.selectBusiShippingorderGoodsBackupList(busiShippingorderGoodsBackup);
    }

    /**
     * 新增订单商品备份
     * 
     * @param busiShippingorderGoodsBackup 订单商品备份
     * @return 结果
     */
    @Override
    public int insertBusiShippingorderGoodsBackup(BusiShippingorderGoods busiShippingorderGoodsBackup)
    {
        busiShippingorderGoodsBackup.setGoodsId(IdUtils.randomUUID());
        return busiShippingorderGoodsBackupMapper.insertBusiShippingorderGoodsBackup(busiShippingorderGoodsBackup);
    }

    /**
     * 修改订单商品备份
     * 
     * @param busiShippingorderGoodsBackup 订单商品备份
     * @return 结果
     */
    @Override
    public int updateBusiShippingorderGoodsBackup(BusiShippingorderGoods busiShippingorderGoodsBackup)
    {
        return busiShippingorderGoodsBackupMapper.updateBusiShippingorderGoodsBackup(busiShippingorderGoodsBackup);
    }

    /**
     * 批量删除订单商品备份
     * 
     * @param goodsIds 需要删除的订单商品备份ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderGoodsBackupByIds(String[] goodsIds)
    {
        return busiShippingorderGoodsBackupMapper.deleteBusiShippingorderGoodsBackupByIds(goodsIds);
    }

    /**
     * 删除订单商品备份信息
     * 
     * @param goodsId 订单商品备份ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderGoodsBackupById(String goodsId)
    {
        return busiShippingorderGoodsBackupMapper.deleteBusiShippingorderGoodsBackupById(goodsId);
    }
}
