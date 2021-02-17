package com.szhbl.project.order.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiShippingorderBackupMapper;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.service.IBusiShippingorderBackupService;

/**
 * 订单备份Service业务层处理
 * 
 * @author dps
 * @date 2020-03-24
 */
@Service
public class BusiShippingorderBackupServiceImpl implements IBusiShippingorderBackupService 
{
    @Autowired
    private BusiShippingorderBackupMapper busiShippingorderBackupMapper;


    /**
     * 查询订单备份
     * 
     * @param orderId 订单备份ID
     * @return 订单备份
     */
    @Override
    public BusiShippingorders selectBusiShippingorderBackupById(String orderId)
    {
        return busiShippingorderBackupMapper.selectBusiShippingorderBackupById(orderId);
    }

    /**
     * 查询订单备份列表
     * 
     * @param busiShippingorderBackup 订单备份
     * @return 订单备份
     */
    @Override
    public List<BusiShippingorders> selectBusiShippingorderBackupList(BusiShippingorders busiShippingorderBackup)
    {
        return busiShippingorderBackupMapper.selectBusiShippingorderBackupList(busiShippingorderBackup);
    }

    /**
     * 新增订单备份
     * 
     * @param busiShippingorderBackup 订单备份
     * @return 结果
     */
    @Override
    public int insertBusiShippingorderBackup(BusiShippingorders busiShippingorderBackup)
    {
        return busiShippingorderBackupMapper.insertBusiShippingorderBackup(busiShippingorderBackup);
    }

    /**
     * 修改订单备份
     * 
     * @param busiShippingorderBackup 订单备份
     * @return 结果
     */
    @Override
    public int updateBusiShippingorderBackup(BusiShippingorders busiShippingorderBackup)
    {
        return busiShippingorderBackupMapper.updateBusiShippingorderBackup(busiShippingorderBackup);
    }

    /**
     * 批量删除订单备份
     * 
     * @param orderIds 需要删除的订单备份ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderBackupByIds(String[] orderIds)
    {
        return busiShippingorderBackupMapper.deleteBusiShippingorderBackupByIds(orderIds);
    }

    /**
     * 删除订单备份信息
     * 
     * @param orderId 订单备份ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderBackupById(String orderId)
    {
        return busiShippingorderBackupMapper.deleteBusiShippingorderBackupById(orderId);
    }
}
