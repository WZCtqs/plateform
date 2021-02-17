package com.szhbl.project.client.service;

import com.szhbl.project.client.domain.BusiOperationquality;

import java.util.List;

/**
 * 操作质量投诉Service接口
 * 
 * @author jhm
 * @date 2020-01-13
 */
public interface IBusiOperationqualityService 
{
    /**
     * 查询操作质量投诉
     * 
     * @param qualityId 操作质量投诉ID
     * @return 操作质量投诉
     */
    public BusiOperationquality selectBusiOperationqualityById(String qualityId);

    /**
     * 查询操作质量投诉列表
     * 
     * @param busiOperationquality 操作质量投诉
     * @return 操作质量投诉集合
     */
    public List<BusiOperationquality> selectBusiOperationqualityList(BusiOperationquality busiOperationquality);

    /**
     * 新增操作质量投诉
     * 
     * @param busiOperationquality 操作质量投诉
     * @return 结果
     */
    public int insertBusiOperationquality(BusiOperationquality busiOperationquality);

    /**
     * 修改操作质量投诉
     * 
     * @param busiOperationquality 操作质量投诉
     * @return 结果
     */
    public int updateBusiOperationquality(BusiOperationquality busiOperationquality);

    /**
     * 批量删除操作质量投诉
     * 
     * @param qualityIds 需要删除的操作质量投诉ID
     * @return 结果
     */
    public int deleteBusiOperationqualityByIds(String[] qualityIds);

    /**
     * 删除操作质量投诉信息
     * 
     * @param qualityId 操作质量投诉ID
     * @return 结果
     */
    public int deleteBusiOperationqualityById(String qualityId);



}
