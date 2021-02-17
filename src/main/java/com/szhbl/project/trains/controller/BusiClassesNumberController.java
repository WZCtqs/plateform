package com.szhbl.project.trains.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.trains.domain.BusiClassesNumber;
import com.szhbl.project.trains.service.IBusiClassesNumberService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 班列号Controller
 *
 * @author dps
 * @date 2020-03-13
 */
@RestController
@RequestMapping("/trains/Number")
@Api(tags = "班列号管理")
public class BusiClassesNumberController extends BaseController
{
    @Autowired
    private IBusiClassesNumberService busiClassesNumberService;

    /**
     * 查询班列号列表
     */
//    @PreAuthorize("@ss.hasPermi('trains:Number:list')")
    @GetMapping("/list")
    @ApiOperation("班列号列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(BusiClassesNumber busiClassesNumber)
    {
        startPage();
        List<BusiClassesNumber> list = busiClassesNumberService.selectBusiClassesNumberList(busiClassesNumber);
        return getDataTable(list);
    }

    /**
     * 获取班列号详细信息
     */
//    @PreAuthorize("@ss.hasPermi('trains:Number:query')")
    @GetMapping("/getInfo")
    @ApiOperation("班列号详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列号id",name = "classTId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(String classTId)
    {
        return AjaxResult.success(busiClassesNumberService.selectBusiClassesNumberById(classTId));
    }

    /**
     * 新增班列号
     */
    @PreAuthorize("@ss.hasPermi('trains:Number:add')")
    @Log(title = "班列号", businessType = BusinessType.INSERT)
    @PostMapping("/numberAdd")
    @ApiOperation("班列号新增 ")
    public AjaxResult add(@Validated @RequestBody BusiClassesNumber busiClassesNumber)
    {
//        if(UserConstants.NOT_UNIQUE.equals(busiClassesNumberService.checkNumberUnique(busiClassesNumber.getClassTNumber()))){
//            return AjaxResult.error("新增班列号'" + busiClassesNumber.getClassTNumber() + "'失败，该班列号已存在");
//        }
        return toAjax(busiClassesNumberService.insertBusiClassesNumber(busiClassesNumber));
    }

    /**
     * 修改班列号
     */
    @PreAuthorize("@ss.hasPermi('trains:Number:edit')")
    @Log(title = "班列号", businessType = BusinessType.UPDATE)
    @PutMapping("/numberEdit")
    @ApiOperation("班列号编辑")
    public AjaxResult edit(@RequestBody BusiClassesNumber busiClassesNumber)
    {
//        if(UserConstants.NOT_UNIQUE.equals(busiClassesNumberService.checkNumberUpdUnique(busiClassesNumber))){
//            return AjaxResult.error("修改班列号'" + busiClassesNumber.getClassTNumber() + "'失败，该班列号已存在");
//        }
        return toAjax(busiClassesNumberService.updateBusiClassesNumber(busiClassesNumber));
    }

    /**
     * 删除班列号
     */
    @PreAuthorize("@ss.hasPermi('trains:Number:remove')")
    @Log(title = "班列号", businessType = BusinessType.DELETE)
    @GetMapping("/numberDel")
    @ApiOperation("班列号删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列号id",name = "classTIds",paramType = "String",required = true)
    })
    public AjaxResult remove(String[] classTIds)
    {
        return toAjax(busiClassesNumberService.deleteBusiClassesNumberByIds(classTIds));
    }

    /**
     * 导出班列号列表
     */
    @PreAuthorize("@ss.hasPermi('trains:Number:export')")
    @Log(title = "班列号", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiClassesNumber busiClassesNumber)
    {
        List<BusiClassesNumber> list = busiClassesNumberService.selectBusiClassesNumberList(busiClassesNumber);
        ExcelUtil<BusiClassesNumber> util = new ExcelUtil<BusiClassesNumber>(BusiClassesNumber.class);
        return util.exportExcel(list, "Number");
    }
}
