package com.szhbl.project.cabinarrangement.service;

import com.szhbl.project.cabinarrangement.domain.BusiShippingorderPc;
import java.util.List;

/**
 * 订单Service接口
 * 
 * @author szhbl
 * @date 2020-01-15
 */
public interface IBusiShippingorderPcService
{
    /**
     * 查询订单
     * 
     * @param orderId 订单ID
     * @return 订单
     */
    public BusiShippingorderPc selectBusiShippingorderById(String orderId);

    /**
     * 查询订单列表
     * 
     * @param busiShippingorderPc 订单
     * @return 订单集合
     */
    public List<BusiShippingorderPc> selectBusiShippingorderList(BusiShippingorderPc busiShippingorderPc);

    /**
     * 新增订单
     * 
     * @param busiShippingorderPc 订单
     * @return 结果
     */
    public int insertBusiShippingorder(BusiShippingorderPc busiShippingorderPc);

    /**
     * 修改订单
     * 
     * @param busiShippingorderPc 订单
     * @return 结果
     */
    public int updateBusiShippingorder(BusiShippingorderPc busiShippingorderPc);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    public int deleteBusiShippingorderByIds(String[] orderIds);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteBusiShippingorderById(String orderId);
}
