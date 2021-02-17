package com.szhbl.project.system.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.system.domain.DeptEvaluation;
import com.szhbl.project.system.domain.vo.DeptEvaluationExportSimple;
import com.szhbl.project.system.service.IDeptEvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 部门考核管理Controller
 *
 * @author szhbl
 * @date 2020-01-07
 */
@Api(tags = "考核管理接口")
@RestController
@RequestMapping("/system/evaluation")
public class DeptEvaluationController extends BaseController
{
    @Autowired
    private IDeptEvaluationService deptEvaluationService;

    /**
     * 查询部门考核管理列表
     */
    @ApiOperation("查询部门考核管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "评价开始时间"),
            @ApiImplicitParam(name = "endTime", value = "评价结束时间")
    })
    @PreAuthorize("@ss.hasPermi('system:evaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(Date startTime, Date endTime)
    {
        startPage();
        List<DeptEvaluation> list = deptEvaluationService.selectDeptEvaluationList(startTime, endTime);
        return getDataTable(list);
    }

    /**
     * 查询部门考核管理列表
     */
    @ApiOperation("查询用户评价信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "评价开始时间"),
            @ApiImplicitParam(name = "endTime", value = "评价结束时间")
    })
    @PreAuthorize("@ss.hasPermi('system:evaluation:userlist')")
    @GetMapping("/userlist")
    public TableDataInfo userlist(Date startTime, Date endTime)
    {
        startPage();
        List<DeptEvaluation> list = deptEvaluationService.selectDeptEvaluationList(startTime, endTime);
        return getDataTable(list);
    }

    /**
     * 导出部门考核管理列表（部门统计）
     */
    @ApiOperation("导出部门考核管理列表-部门统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "评价开始时间"),
            @ApiImplicitParam(name = "endTime",value = "评价结束时间")
    })
    @PreAuthorize("@ss.hasPermi('system:evaluationSimple:export')")
    @Log(title = "部门考核统计导出", businessType = BusinessType.EXPORT)
    @GetMapping("/exportSimple")
    public AjaxResult exportSimple(Date startTime, Date endTime)
    {
        List<DeptEvaluationExportSimple> list = deptEvaluationService.selectForExportSimple(startTime, endTime);
        ExcelUtil<DeptEvaluationExportSimple> util = new ExcelUtil<>(DeptEvaluationExportSimple.class);
        return util.exportExcel(list, "部门评分表");
    }

    /**
     * 导出部门考核管理列表（详细信息）
     */
    @ApiOperation("导出部门考核管理列表-详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "评价开始时间"),
            @ApiImplicitParam(name = "endTime",value = "评价结束时间")
    })
    @PreAuthorize("@ss.hasPermi('system:evaluation:export')")
    @Log(title = "部门考核详细信息导出", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Date startTime, Date endTime)
    {
        List<DeptEvaluation> list = deptEvaluationService.selectDeptEvaluationList(startTime, endTime);
        ExcelUtil<DeptEvaluation> util = new ExcelUtil<DeptEvaluation>(DeptEvaluation.class);
        return util.exportExcel(list, "部门评分表详细");
    }

    /**
     * 获取部门考核管理详细信息
     */
    @ApiOperation("获取单个考核详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考核数据id", required = true)
    })
    @PreAuthorize("@ss.hasPermi('system:evaluation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(deptEvaluationService.selectDeptEvaluationById(id));
    }

    /**
     * 新增部门考核管理
     */
    @ApiOperation("新增考核")
    @PreAuthorize("@ss.hasPermi('system:evaluation:add')")
    @Log(title = "部门考核管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeptEvaluation deptEvaluation)
    {
        return toAjax(deptEvaluationService.insertDeptEvaluation(deptEvaluation));
    }

    /**
     * 修改部门考核管理
     */
    @ApiOperation("修改考核信息")
    @ApiImplicitParam(name = "id",value = "考核数据id", required = true)
    @PreAuthorize("@ss.hasPermi('system:evaluation:edit')")
    @Log(title = "部门考核管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeptEvaluation deptEvaluation)
    {
        return toAjax(deptEvaluationService.updateDeptEvaluation(deptEvaluation));
    }

    /**
     * 删除部门考核管理
     */
    @ApiOperation("删除考核信息")
    @PreAuthorize("@ss.hasPermi('system:evaluation:remove')")
    @Log(title = "部门考核管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deptEvaluationService.deleteDeptEvaluationByIds(ids));
    }
}
