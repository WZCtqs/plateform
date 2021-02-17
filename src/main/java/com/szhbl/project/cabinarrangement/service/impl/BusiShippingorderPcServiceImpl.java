package com.szhbl.project.cabinarrangement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.cabinarrangement.mapper.BusiShippingorderPcMapper;
import com.szhbl.project.cabinarrangement.domain.BusiShippingorderPc;
import com.szhbl.project.cabinarrangement.service.IBusiShippingorderPcService;

/**
 * 订单Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-15
 */
@Service
public class BusiShippingorderPcServiceImpl implements IBusiShippingorderPcService
{
    @Autowired
    private BusiShippingorderPcMapper busiShippingorderPcMapper;

    /**
     * 查询订单
     * 
     * @param orderId 订单ID
     * @return 订单
     */
    @Override
    public BusiShippingorderPc selectBusiShippingorderById(String orderId)
    {
        return busiShippingorderPcMapper.selectBusiShippingorderById(orderId);
    }

    /**
     * 查询订单列表
     * 
     * @param busiShippingorderPc 订单
     * @return 订单
     */
    @Override
    public List<BusiShippingorderPc> selectBusiShippingorderList(BusiShippingorderPc busiShippingorderPc)
    {
        return busiShippingorderPcMapper.selectBusiShippingorderList(busiShippingorderPc);
    }

    /**
     * 新增订单
     * 
     * @param busiShippingorderPc 订单
     * @return 结果
     */
    @Override
    public int insertBusiShippingorder(BusiShippingorderPc busiShippingorderPc)
    {
        return busiShippingorderPcMapper.insertBusiShippingorder(busiShippingorderPc);
    }

    /**
     * 修改订单
     * 
     * @param busiShippingorderPc 订单
     * @return 结果
     */
    @Override
    public int updateBusiShippingorder(BusiShippingorderPc busiShippingorderPc)
    {
        return busiShippingorderPcMapper.updateBusiShippingorder(busiShippingorderPc);
    }

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderByIds(String[] orderIds)
    {
        return busiShippingorderPcMapper.deleteBusiShippingorderByIds(orderIds);
    }

    /**
     * 删除订单信息
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderById(String orderId)
    {
        return busiShippingorderPcMapper.deleteBusiShippingorderById(orderId);
    }
}
