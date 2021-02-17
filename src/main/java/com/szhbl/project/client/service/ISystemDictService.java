package com.szhbl.project.client.service;

import com.szhbl.project.client.domain.SystemDict;
import java.util.List;

/**
 * 推荐人管理Service接口
 * 
 * @author szhbl
 * @date 2020-01-07
 */
public interface ISystemDictService 
{
    /**
     * 查询推荐人管理
     * 
     * @param id 推荐人管理ID
     * @return 推荐人管理
     */
    public SystemDict selectSystemDictById(Long id);

    /**
     * 查询推荐人管理列表
     * 
     * @param systemDict 推荐人管理
     * @return 推荐人管理集合
     */
    public List<SystemDict> selectSystemDictList(SystemDict systemDict);

    /**
     * 新增推荐人管理
     * 
     * @param systemDict 推荐人管理
     * @return 结果
     */
    public int insertSystemDict(SystemDict systemDict);

    /**
     * 修改推荐人管理
     * 
     * @param systemDict 推荐人管理
     * @return 结果
     */
    public int updateSystemDict(SystemDict systemDict);

    /**
     * 批量删除推荐人管理
     * 
     * @param ids 需要删除的推荐人管理ID
     * @return 结果
     */
    public int deleteSystemDictByIds(Long[] ids);

    /**
     * 删除推荐人管理信息
     * 
     * @param id 推荐人管理ID
     * @return 结果
     */
    public int deleteSystemDictById(Long id);
}
