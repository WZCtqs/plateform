package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.BusiShippingorderOneInstorage;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author szhbl
 * @date 2020-01-11
 */
public interface IBusiShippingorderOneInstorageService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param inId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public BusiShippingorderOneInstorage selectBusiShippingorderOneInstorageById(String inId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param busiShippingorderOneInstorage 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<BusiShippingorderOneInstorage> selectBusiShippingorderOneInstorageList(BusiShippingorderOneInstorage busiShippingorderOneInstorage);

    /**
     * 新增【请填写功能名称】
     *
     * @param busiShippingorderOneInstorage 【请填写功能名称】
     * @return 结果
     */
    public int insertBusiShippingorderOneInstorage(BusiShippingorderOneInstorage busiShippingorderOneInstorage);

    /**
     * 修改【请填写功能名称】
     *
     * @param busiShippingorderOneInstorage 【请填写功能名称】
     * @return 结果
     */
    public int updateBusiShippingorderOneInstorage(BusiShippingorderOneInstorage busiShippingorderOneInstorage);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param inIds 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteBusiShippingorderOneInstorageByIds(String[] inIds);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param inId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteBusiShippingorderOneInstorageById(String inId);
    /**
     * 根据orderId删除
     * @param orderId
     * @return
     */
    public int deleteByOrderId(String orderId);
}
