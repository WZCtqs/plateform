package com.szhbl.project.backclient.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.backclient.domain.RailwayRequirement;
import com.szhbl.project.backclient.service.IRailwayRequirementService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 铁路要求Controller
 * 
 * @author szhbl
 * @date 2020-01-07
 */
@CrossOrigin
@RestController
@RequestMapping("/backclient/requirement")
@Api(tags = " 铁路相关要求",description = "客户端/平台端")
public class RailwayRequirementController extends BaseController
{
    @Autowired
    private IRailwayRequirementService railwayRequirementService;

    /**
     * 查询铁路要求列表
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:list')")
    @GetMapping("/list")
    @ApiOperation("平台查询铁路相关要求列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "0货物包装要求1集装箱使用要求2通用货物装箱加固规则3出口班列客户自备箱进展要求",name = "type",paramType = "query",dataType = "String",required = true)
         })
    public TableDataInfo list(RailwayRequirement railwayRequirement)
    {
        startPage();
        List<RailwayRequirement> list = railwayRequirementService.selectRailwayRequirementList(railwayRequirement);
        return getDataTable(list);
    }
    @GetMapping("/listClient")
    @ApiOperation("客户端查询铁路相关要求")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "0货物包装要求1集装箱使用要求2通用货物装箱加固规则3出口班列客户自备箱进展要求",name = "type",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "语言0中文1英文2俄语3德语",name = "language",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult listClient(RailwayRequirement railwayRequirement)
    {
        startPage();
        List<RailwayRequirement> list = railwayRequirementService.selectRailwayRequirementList(railwayRequirement);
        if (list.size()>0){
            if ("1".equals(railwayRequirement.getLanguage())){
                list.get(0).setContent(list.get(0).getEnContent());
            }
            return AjaxResult.success(list.get(0));
        }else {
           RailwayRequirement rr =  new RailwayRequirement();
           rr.setContent("暂无数据");
           return AjaxResult.success(rr);
        }
    }
    /**
     * 导出铁路要求列表
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:export')")
    @Log(title = "铁路要求", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RailwayRequirement railwayRequirement)
    {
        List<RailwayRequirement> list = railwayRequirementService.selectRailwayRequirementList(railwayRequirement);
        ExcelUtil<RailwayRequirement> util = new ExcelUtil<RailwayRequirement>(RailwayRequirement.class);
        return util.exportExcel(list, "requirement");
    }

    /**
     * 获取铁路要求详细信息
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(railwayRequirementService.selectRailwayRequirementById(id));
    }

    /**
     * 新增铁路要求
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:add')")
    @Log(title = "铁路要求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RailwayRequirement railwayRequirement)
    {
        //判断该语言下的该种类有无数据，有的话不能添加
        RailwayRequirement railwayRequirement1 = new RailwayRequirement();
        railwayRequirement1.setType(railwayRequirement.getType());
        List<RailwayRequirement> list = railwayRequirementService.selectRailwayRequirementList(railwayRequirement1);
        if (list.size()==0){
            return toAjax(railwayRequirementService.insertRailwayRequirement(railwayRequirement));
        }else {
            return AjaxResult.error("请不要重复添加同一类型数据！");
        }

    }

    /**
     * 修改铁路要求
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:edit')")
    @Log(title = "铁路要求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RailwayRequirement railwayRequirement)
    {
        RailwayRequirement railwayRequirement1 = new RailwayRequirement();
        railwayRequirement1.setType(railwayRequirement.getType());
        List<RailwayRequirement> list = railwayRequirementService.selectRailwayRequirementList(railwayRequirement1);
        //判断修改后的语言和类型有没有重复的
        if (list.size()==0){
            return toAjax(railwayRequirementService.updateRailwayRequirement(railwayRequirement));
        }else {
            //判断重复的是不是他自己
            if (list.get(0).getId()==railwayRequirement.getId()){
                return toAjax(railwayRequirementService.updateRailwayRequirement(railwayRequirement));
            }else {
                return toAjax(0);
            }
        }
    }

    /**
     * 删除铁路要求
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:remove')")
    @Log(title = "铁路要求", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(railwayRequirementService.deleteRailwayRequirementByIds(ids));
    }
}
