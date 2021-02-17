package com.szhbl.project.backclient.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.backclient.mapper.RailwayRequirementMapper;
import com.szhbl.project.backclient.domain.RailwayRequirement;
import com.szhbl.project.backclient.service.IRailwayRequirementService;

/**
 * 铁路要求Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-07
 */
@Service
public class RailwayRequirementServiceImpl implements IRailwayRequirementService 
{
    @Autowired
    private RailwayRequirementMapper railwayRequirementMapper;

    /**
     * 查询铁路要求
     * 
     * @param id 铁路要求ID
     * @return 铁路要求
     */
    @Override
    public RailwayRequirement selectRailwayRequirementById(Long id)
    {
        return railwayRequirementMapper.selectRailwayRequirementById(id);
    }

    /**
     * 查询铁路要求列表
     * 
     * @param railwayRequirement 铁路要求
     * @return 铁路要求
     */
    @Override
    public List<RailwayRequirement> selectRailwayRequirementList(RailwayRequirement railwayRequirement)
    {
        return railwayRequirementMapper.selectRailwayRequirementList(railwayRequirement);
    }

    /**
     * 新增铁路要求
     * 
     * @param railwayRequirement 铁路要求
     * @return 结果
     */
    @Override
    public int insertRailwayRequirement(RailwayRequirement railwayRequirement)
    {
        railwayRequirement.setCreateTime(DateUtils.getNowDate());
        return railwayRequirementMapper.insertRailwayRequirement(railwayRequirement);
    }

    /**
     * 修改铁路要求
     * 
     * @param railwayRequirement 铁路要求
     * @return 结果
     */
    @Override
    public int updateRailwayRequirement(RailwayRequirement railwayRequirement)
    {
        railwayRequirement.setUpdateTime(DateUtils.getNowDate());
        return railwayRequirementMapper.updateRailwayRequirement(railwayRequirement);
    }

    /**
     * 批量删除铁路要求
     * 
     * @param ids 需要删除的铁路要求ID
     * @return 结果
     */
    @Override
    public int deleteRailwayRequirementByIds(Long[] ids)
    {
        return railwayRequirementMapper.deleteRailwayRequirementByIds(ids);
    }

    /**
     * 删除铁路要求信息
     * 
     * @param id 铁路要求ID
     * @return 结果
     */
    @Override
    public int deleteRailwayRequirementById(Long id)
    {
        return railwayRequirementMapper.deleteRailwayRequirementById(id);
    }
}
