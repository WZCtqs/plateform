package com.szhbl.project.system.mapper;

import com.szhbl.project.system.domain.DeptEvaluation;
import com.szhbl.project.system.domain.vo.DeptEvaluationExportSimple;

import java.util.Date;
import java.util.List;

/**
 * 部门考核管理Mapper接口
 *
 * @author szhbl
 * @date 2020-01-07
 */
public interface DeptEvaluationMapper
{
    /**
     * 查询部门考核管理
     *
     * @param id 部门考核管理ID
     * @return 部门考核管理
     */
    public DeptEvaluation selectDeptEvaluationById(Long id);

    /**
     * 查询部门考核管理列表
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 部门考核管理集合
     */
    public List<DeptEvaluation> selectDeptEvaluationList(Date startTime, Date endTime);

    /**
     * 按照部门统计导出
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 部门统计集合
     */
    public List<DeptEvaluationExportSimple> selectForExportSimple(Date startTime, Date endTime);

    /**
     * 新增部门考核管理
     *
     * @param deptEvaluation 部门考核管理
     * @return 结果
     */
    public int insertDeptEvaluation(DeptEvaluation deptEvaluation);

    /**
     * 修改部门考核管理
     *
     * @param deptEvaluation 部门考核管理
     * @return 结果
     */
    public int updateDeptEvaluation(DeptEvaluation deptEvaluation);

    /**
     * 删除部门考核管理
     *
     * @param id 部门考核管理ID
     * @return 结果
     */
    public int deleteDeptEvaluationById(Long id);

    /**
     * 批量删除部门考核管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDeptEvaluationByIds(Long[] ids);
}
