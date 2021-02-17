package com.szhbl.project.customerservice.service.impl;

import java.util.List;

import com.szhbl.project.customerservice.vo.InfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.BusiShippingordershMapper;
import com.szhbl.project.customerservice.domain.BusiShippingordersh;
import com.szhbl.project.customerservice.service.IBusiShippingordershService;

/**
 * 订单Service业务层处理
 * 
 * @author b
 * @date 2020-03-27
 */
@Service
public class BusiShippingordershServiceImpl implements IBusiShippingordershService 
{
    @Autowired
    private BusiShippingordershMapper busiShippingordershMapper;

    /**
     * 查询订单
     * 
     * @param orderId 订单ID
     * @return 订单
     */
    @Override
    public BusiShippingordersh selectBusiShippingordershById(String orderId)
    {
        return busiShippingordershMapper.selectBusiShippingordershById(orderId);
    }

    /**
     * 查询订单列表
     * 
     * @param busiShippingordersh 订单
     * @return 订单
     */
    @Override
    public List<BusiShippingordersh> selectBusiShippingordershList(BusiShippingordersh busiShippingordersh)
    {
        return busiShippingordershMapper.selectBusiShippingordershList(busiShippingordersh);
    }

    /**
     * 新增订单
     * 
     * @param busiShippingordersh 订单
     * @return 结果
     */
    @Override
    public int insertBusiShippingordersh(BusiShippingordersh busiShippingordersh)
    {
        return busiShippingordershMapper.insertBusiShippingordersh(busiShippingordersh);
    }

    /**
     * 修改订单
     * 
     * @param busiShippingordersh 订单
     * @return 结果
     */
    @Override
    public int updateBusiShippingordersh(BusiShippingordersh busiShippingordersh)
    {
        return busiShippingordershMapper.updateBusiShippingordersh(busiShippingordersh);
    }

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingordershByIds(String[] orderIds)
    {
        return busiShippingordershMapper.deleteBusiShippingordershByIds(orderIds);
    }

    /**
     * 删除订单信息
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingordershById(String orderId)
    {
        return busiShippingordershMapper.deleteBusiShippingordershById(orderId);
    }

    @Override
    public InfoVo selectInfo(String orderNum) {
        return busiShippingordershMapper.selectInfo(orderNum);
    }

    @Override
    public String selectOrderId(String orderNum) {
        return busiShippingordershMapper.selectOrderId(orderNum);
    }
}
