package com.szhbl.project.basic.controller;

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
import com.szhbl.project.basic.domain.BusiCustomersign;
import com.szhbl.project.basic.service.IBusiCustomersignService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 客户标记Controller
 * 
 * @author dps
 * @date 2020-03-16
 */
@RestController
@RequestMapping("/basic/customersign")
@Api(tags = "客户标记管理")
public class BusiCustomersignController extends BaseController
{
    @Autowired
    private IBusiCustomersignService busiCustomersignService;

    /**
     * 查询客户标记列表
     */
//    @PreAuthorize("@ss.hasPermi('basic:customersign:list')")
    @GetMapping("/list")
    @ApiOperation("标记客户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(BusiCustomersign busiCustomersign)
    {
        startPage();
        List<BusiCustomersign> list = busiCustomersignService.selectBusiCustomersignList(busiCustomersign);
        return getDataTable(list);
    }
    public TableDataInfo listName()
    {
        startPage();
        List<String> list = busiCustomersignService.selectListName();
        return getDataTable(list);
    }

    /**
     * 获取客户标记详细信息
     */
    //@PreAuthorize("@ss.hasPermi('risk:customersign:query')")
    @GetMapping("/getInfo")
    @ApiOperation("标记客户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "标记客户id",name = "signId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(String signId)
    {
        return AjaxResult.success(busiCustomersignService.selectBusiCustomersignById(signId));
    }

    /**
     * 新增客户标记
     */
    @PreAuthorize("@ss.hasPermi('risk:customersign:add')")
    @Log(title = "客户标记", businessType = BusinessType.INSERT)
    @PostMapping("/signAdd")
    @ApiOperation("标记客户新增")
    public AjaxResult add(@Validated @RequestBody BusiCustomersign busiCustomersign)
    {
        return toAjax(busiCustomersignService.insertBusiCustomersign(busiCustomersign));
    }

    /**
     * 修改客户标记
     */
    @PreAuthorize("@ss.hasPermi('risk:customersign:edit')")
    @Log(title = "客户标记", businessType = BusinessType.UPDATE)
    @PutMapping("/signEdit")
    @ApiOperation("标记客户编辑")
    public AjaxResult edit(@RequestBody BusiCustomersign busiCustomersign)
    {
        return toAjax(busiCustomersignService.updateBusiCustomersign(busiCustomersign));
    }

    /**
     * 删除客户标记
     */
    @PreAuthorize("@ss.hasPermi('risk:customersign:remove')")
    @Log(title = "客户标记", businessType = BusinessType.DELETE)
    @GetMapping("/signDel")
    @ApiOperation("标记客户删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "标记客户id",name = "signIds",paramType = "query",required = true)
    })
    public AjaxResult remove(String[] signIds)
    {
        return toAjax(busiCustomersignService.deleteBusiCustomersignByIds(signIds));
    }

    /**
     * 导出客户标记列表
     */
    @PreAuthorize("@ss.hasPermi('basic:customersign:export')")
    @Log(title = "客户标记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiCustomersign busiCustomersign)
    {
        List<BusiCustomersign> list = busiCustomersignService.selectBusiCustomersignList(busiCustomersign);
        ExcelUtil<BusiCustomersign> util = new ExcelUtil<BusiCustomersign>(BusiCustomersign.class);
        return util.exportExcel(list, "customersign");
    }
}
