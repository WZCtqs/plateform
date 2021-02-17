package com.szhbl.project.backclient.mapper;

import com.szhbl.project.backclient.domain.RailwayRequirement;
import java.util.List;

/**
 * 铁路要求Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-07
 */
public interface RailwayRequirementMapper 
{
    /**
     * 查询铁路要求
     * 
     * @param id 铁路要求ID
     * @return 铁路要求
     */
    public RailwayRequirement selectRailwayRequirementById(Long id);

    /**
     * 查询铁路要求列表
     * 
     * @param railwayRequirement 铁路要求
     * @return 铁路要求集合
     */
    public List<RailwayRequirement> selectRailwayRequirementList(RailwayRequirement railwayRequirement);

    /**
     * 新增铁路要求
     * 
     * @param railwayRequirement 铁路要求
     * @return 结果
     */
    public int insertRailwayRequirement(RailwayRequirement railwayRequirement);

    /**
     * 修改铁路要求
     * 
     * @param railwayRequirement 铁路要求
     * @return 结果
     */
    public int updateRailwayRequirement(RailwayRequirement railwayRequirement);

    /**
     * 删除铁路要求
     * 
     * @param id 铁路要求ID
     * @return 结果
     */
    public int deleteRailwayRequirementById(Long id);

    /**
     * 批量删除铁路要求
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRailwayRequirementByIds(Long[] ids);
}
