package com.szhbl.project.system.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.system.domain.DeptEvaluation;
import com.szhbl.project.system.domain.vo.DeptEvaluationExportSimple;
import com.szhbl.project.system.mapper.DeptEvaluationMapper;
import com.szhbl.project.system.service.IDeptEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 部门考核管理Service业务层处理
 *
 * @author szhbl
 * @date 2020-01-07
 */
@Service
public class DeptEvaluationServiceImpl implements IDeptEvaluationService {
    @Autowired
    private DeptEvaluationMapper deptEvaluationMapper;

    /**
     * 查询部门考核管理
     *
     * @param id 部门考核管理ID
     * @return 部门考核管理
     */
    @Override
    public DeptEvaluation selectDeptEvaluationById(Long id) {
        return deptEvaluationMapper.selectDeptEvaluationById(id);
    }

    /**
     * 查询部门考核管理列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 部门考核管理
     */
    @Override
    public List<DeptEvaluation> selectDeptEvaluationList(Date startTime, Date endTime) {
        return deptEvaluationMapper.selectDeptEvaluationList(startTime, endTime);
    }

    /**
     * 按照部门统计导出
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 部门统计
     */
    @Override
    public List<DeptEvaluationExportSimple> selectForExportSimple(Date startTime, Date endTime) {
        return deptEvaluationMapper.selectForExportSimple(startTime, endTime);
    }

    /**
     * 新增部门考核管理
     *
     * @param deptEvaluation 部门考核管理
     * @return 结果
     */
    @Override
    public int insertDeptEvaluation(DeptEvaluation deptEvaluation) {
        deptEvaluation.setEvaluationTime(DateUtils.getNowDate());
        return deptEvaluationMapper.insertDeptEvaluation(deptEvaluation);
    }

    /**
     * 修改部门考核管理
     *
     * @param deptEvaluation 部门考核管理
     * @return 结果
     */
    @Override
    public int updateDeptEvaluation(DeptEvaluation deptEvaluation) {
        deptEvaluation.setEvaluationTime(DateUtils.getNowDate());
        return deptEvaluationMapper.updateDeptEvaluation(deptEvaluation);
    }

    /**
     * 批量删除部门考核管理
     *
     * @param ids 需要删除的部门考核管理ID
     * @return 结果
     */
    @Override
    public int deleteDeptEvaluationByIds(Long[] ids) {
        return deptEvaluationMapper.deleteDeptEvaluationByIds(ids);
    }

    /**
     * 删除部门考核管理信息
     *
     * @param id 部门考核管理ID
     * @return 结果
     */
    @Override
    public int deleteDeptEvaluationById(Long id) {
        return deptEvaluationMapper.deleteDeptEvaluationById(id);
    }
}
