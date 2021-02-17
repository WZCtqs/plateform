package com.szhbl.project.client.service.impl;

import java.util.List;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.common.exception.CustomException;
import com.szhbl.project.system.domain.SysDept;
import com.szhbl.project.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.client.mapper.SystemDictMapper;
import com.szhbl.project.client.domain.SystemDict;
import com.szhbl.project.client.service.ISystemDictService;

/**
 * 推荐人管理Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-07
 */
@Service
public class SystemDictServiceImpl implements ISystemDictService 
{
    @Autowired
    private SystemDictMapper systemDictMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询推荐人管理
     * 
     * @param id 推荐人管理ID
     * @return 推荐人管理
     */
    @Override
    public SystemDict selectSystemDictById(Long id)
    {
        return systemDictMapper.selectSystemDictById(id);
    }

    /**
     * 查询推荐人管理列表
     * 
     * @param systemDict 推荐人管理
     * @return 推荐人管理
     */
    @Override
    public List<SystemDict> selectSystemDictList(SystemDict systemDict)
    {
        return systemDictMapper.selectSystemDictList(systemDict);
    }

    /**
     * 新增推荐人管理
     * 
     * @param systemDict 推荐人管理
     * @return 结果
     */
    @Override
    public int insertSystemDict(SystemDict systemDict)
    {
        //获取本门父级id
        SysDept info = deptMapper.selectDeptById(systemDict.getFid());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new CustomException("部门停用，不允许新增");
        }
        systemDict.setAncestors(info.getAncestors() + "," + systemDict.getFid());
        return systemDictMapper.insertSystemDict(systemDict);
    }

    /**
     * 修改推荐人管理
     * 
     * @param systemDict 推荐人管理
     * @return 结果
     */
    @Override
    public int updateSystemDict(SystemDict systemDict)
    {
        return systemDictMapper.updateSystemDict(systemDict);
    }

    /**
     * 批量删除推荐人管理
     * 
     * @param ids 需要删除的推荐人管理ID
     * @return 结果
     */
    @Override
    public int deleteSystemDictByIds(Long[] ids)
    {
        return systemDictMapper.deleteSystemDictByIds(ids);
    }

    /**
     * 删除推荐人管理信息
     * 
     * @param id 推荐人管理ID
     * @return 结果
     */
    @Override
    public int deleteSystemDictById(Long id)
    {
        return systemDictMapper.deleteSystemDictById(id);
    }
}
