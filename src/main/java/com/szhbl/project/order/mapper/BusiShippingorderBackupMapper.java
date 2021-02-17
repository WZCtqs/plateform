package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BusiShippingorders;
import java.util.List;

/**
 * 订单备份Mapper接口
 * 
 * @author dps
 * @date 2020-03-24
 */
public interface BusiShippingorderBackupMapper 
{
    /**
     * 查询订单备份
     * 
     * @param orderId 订单备份ID
     * @return 订单备份
     */
    public BusiShippingorders selectBusiShippingorderBackupById(String orderId);

    /**
     * 查询订单备份列表
     * 
     * @param busiShippingorderBackup 订单备份
     * @return 订单备份集合
     */
    public List<BusiShippingorders> selectBusiShippingorderBackupList(BusiShippingorders busiShippingorderBackup);

    /**
     * 新增订单备份
     * 
     * @param busiShippingorderBackup 订单备份
     * @return 结果
     */
    public int insertBusiShippingorderBackup(BusiShippingorders busiShippingorderBackup);

    /**
     * 修改订单备份
     * 
     * @param busiShippingorderBackup 订单备份
     * @return 结果
     */
    public int updateBusiShippingorderBackup(BusiShippingorders busiShippingorderBackup);

    /**
     * 删除订单备份
     * 
     * @param orderId 订单备份ID
     * @return 结果
     */
    public int deleteBusiShippingorderBackupById(String orderId);

    /**
     * 批量删除订单备份
     * 
     * @param orderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiShippingorderBackupByIds(String[] orderIds);
}
