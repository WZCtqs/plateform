package com.szhbl.project.customerservice.service;

import com.szhbl.project.customerservice.domain.BusiShippingordersh;
import com.szhbl.project.customerservice.vo.InfoVo;

import java.util.List;

/**
 * 订单Service接口
 * 
 * @author b
 * @date 2020-03-27
 */
public interface IBusiShippingordershService 
{
    /**
     * 查询订单
     * 
     * @param orderId 订单ID
     * @return 订单
     */
    public BusiShippingordersh selectBusiShippingordershById(String orderId);

    /**
     * 查询订单列表
     * 
     * @param busiShippingordersh 订单
     * @return 订单集合
     */
    public List<BusiShippingordersh> selectBusiShippingordershList(BusiShippingordersh busiShippingordersh);

    /**
     * 新增订单
     * 
     * @param busiShippingordersh 订单
     * @return 结果
     */
    public int insertBusiShippingordersh(BusiShippingordersh busiShippingordersh);

    /**
     * 修改订单
     * 
     * @param busiShippingordersh 订单
     * @return 结果
     */
    public int updateBusiShippingordersh(BusiShippingordersh busiShippingordersh);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    public int deleteBusiShippingordershByIds(String[] orderIds);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteBusiShippingordershById(String orderId);

    InfoVo selectInfo(String orderNum);

    String selectOrderId(String orderNum);
}
