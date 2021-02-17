package com.szhbl.project.customerservice.mapper;

import com.szhbl.project.customerservice.domain.CompensationTracking;
import java.util.List;

/**
 * 赔款跟踪Mapper接口
 * 
 * @author bxt
 * @date 2020-03-30
 */
public interface CompensationTrackingMapper 
{
    /**
     * 查询赔款跟踪
     * 
     * @param id 赔款跟踪ID
     * @return 赔款跟踪
     */
    public CompensationTracking selectCompensationTrackingById(Long id);

    /**
     * 查询赔款跟踪列表
     * 
     * @param compensationTracking 赔款跟踪
     * @return 赔款跟踪集合
     */
    public List<CompensationTracking> selectCompensationTrackingList(CompensationTracking compensationTracking);

    /**
     * 新增赔款跟踪
     * 
     * @param compensationTracking 赔款跟踪
     * @return 结果
     */
    public int insertCompensationTracking(CompensationTracking compensationTracking);

    /**
     * 修改赔款跟踪
     * 
     * @param compensationTracking 赔款跟踪
     * @return 结果
     */
    public int updateCompensationTracking(CompensationTracking compensationTracking);

    /**
     * 删除赔款跟踪
     * 
     * @param id 赔款跟踪ID
     * @return 结果
     */
    public int deleteCompensationTrackingById(Long id);

    /**
     * 批量删除赔款跟踪
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCompensationTrackingByIds(Long[] ids);
}
