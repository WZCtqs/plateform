package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BusiShippingorderGoods;
import java.util.List;

/**
 * 订单商品备份Mapper接口
 * 
 * @author dps
 * @date 2020-03-24
 */
public interface BusiShippingorderGoodsBackupMapper 
{
    /**
     * 查询订单商品备份
     * 
     * @param goodsId 订单商品备份ID
     * @return 订单商品备份
     */
    public BusiShippingorderGoods selectBusiShippingorderGoodsBackupById(String goodsId);

    /**
     * 查询订单商品备份,根据托书id
     * 
     * @param orderId 订单ID
     * @return 订单商品备份
     */
    public BusiShippingorderGoods selectBusiShippingorderGoodsBackupByOrderId(String orderId);

    /**
     * 查询订单商品备份列表
     * 
     * @param busiShippingorderGoodsBackup 订单商品备份
     * @return 订单商品备份集合
     */
    public List<BusiShippingorderGoods> selectBusiShippingorderGoodsBackupList(BusiShippingorderGoods busiShippingorderGoodsBackup);

    /**
     * 新增订单商品备份
     * 
     * @param busiShippingorderGoodsBackup 订单商品备份
     * @return 结果
     */
    public int insertBusiShippingorderGoodsBackup(BusiShippingorderGoods busiShippingorderGoodsBackup);

    /**
     * 修改订单商品备份
     * 
     * @param busiShippingorderGoodsBackup 订单商品备份
     * @return 结果
     */
    public int updateBusiShippingorderGoodsBackup(BusiShippingorderGoods busiShippingorderGoodsBackup);

    /**
     * 删除订单商品备份
     * 
     * @param goodsId 订单商品备份ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsBackupById(String goodsId);

    /**
     * 批量删除订单商品备份
     * 
     * @param goodsIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsBackupByIds(String[] goodsIds);
}
