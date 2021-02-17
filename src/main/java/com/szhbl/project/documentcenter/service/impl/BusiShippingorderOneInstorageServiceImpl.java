package com.szhbl.project.documentcenter.service.impl;

import java.util.List;

import com.szhbl.project.documentcenter.domain.BusiShippingorderOneInstorage;
import com.szhbl.project.documentcenter.mapper.BusiShippingorderOneInstorageMapper;
import com.szhbl.project.documentcenter.service.IBusiShippingorderOneInstorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author szhbl
 * @date 2020-01-11
 */
@Service
public class BusiShippingorderOneInstorageServiceImpl implements IBusiShippingorderOneInstorageService
{
    @Resource
    private BusiShippingorderOneInstorageMapper instorageMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param inId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public BusiShippingorderOneInstorage selectBusiShippingorderOneInstorageById(String inId)
    {
        return instorageMapper.selectBusiShippingorderOneInstorageById(inId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param busiShippingorderOneInstorage 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<BusiShippingorderOneInstorage> selectBusiShippingorderOneInstorageList(BusiShippingorderOneInstorage busiShippingorderOneInstorage)
    {
        return instorageMapper.selectBusiShippingorderOneInstorageList(busiShippingorderOneInstorage);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param busiShippingorderOneInstorage 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertBusiShippingorderOneInstorage(BusiShippingorderOneInstorage busiShippingorderOneInstorage)
    {
        return instorageMapper.insertBusiShippingorderOneInstorage(busiShippingorderOneInstorage);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param busiShippingorderOneInstorage 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateBusiShippingorderOneInstorage(BusiShippingorderOneInstorage busiShippingorderOneInstorage)
    {
        return instorageMapper.updateBusiShippingorderOneInstorage(busiShippingorderOneInstorage);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param inIds 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderOneInstorageByIds(String[] inIds)
    {
        return instorageMapper.deleteBusiShippingorderOneInstorageByIds(inIds);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param inId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderOneInstorageById(String inId)
    {
        return instorageMapper.deleteBusiShippingorderOneInstorageById(inId);
    }
    /**
     * 根据orderId删除
     * @param orderId
     * @return
     */
    @Override
    public int deleteByOrderId(String orderId){
        return instorageMapper.deleteByOrderId(orderId);
    }
}
