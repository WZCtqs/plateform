package com.szhbl.project.basic.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.basic.domain.BusiMoneyrete;
import com.szhbl.project.basic.service.IBusiMoneyreteService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 汇率Controller
 *
 * @author dps
 * @date 2020-01-15
 */
@RestController
@RequestMapping("/basic/moneyrete")
@Api(tags = "汇率管理")
public class BusiMoneyreteController extends BaseController
{
    @Autowired
    private IBusiMoneyreteService busiMoneyreteService;

    /**
     * 查询汇率列表
     */
//    @PreAuthorize("@ss.hasPermi('basic:moneyrete:list')")
    @GetMapping("/moneyList")
    @ApiOperation("汇率列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(BusiMoneyrete busiMoneyrete)
    {
        startPage();
        List<BusiMoneyrete> list = busiMoneyreteService.selectBusiMoneyreteList(busiMoneyrete);
        return getDataTable(list);
    }

    /**
     * 获取汇率详细信息
     */
//    @PreAuthorize("@ss.hasPermi('basic:moneyrete:query')")
    @GetMapping("/getInfo")
    @ApiOperation("汇率详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "汇率id",name = "moneyrateId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(String moneyrateId)
    {
        return AjaxResult.success(busiMoneyreteService.selectBusiMoneyreteById(moneyrateId));
    }

    /**
     * 新增汇率
     */
    @PreAuthorize("@ss.hasPermi('basic:moneyrete:add')")
    @Log(title = "汇率", businessType = BusinessType.INSERT)
    @PostMapping("/moneyAdd")
    @ApiOperation("汇率新增")
    public AjaxResult add(@RequestBody BusiMoneyrete busiMoneyrete)
    {
        return toAjax(busiMoneyreteService.insertBusiMoneyrete(busiMoneyrete));
    }

    /**
     * 修改汇率
     */
    @PreAuthorize("@ss.hasPermi('basic:moneyrete:edit')")
    @Log(title = "汇率", businessType = BusinessType.UPDATE)
    @PutMapping("/moneyEdit")
    @ApiOperation("汇率编辑")
    public AjaxResult edit(@RequestBody BusiMoneyrete busiMoneyrete)
    {
        return toAjax(busiMoneyreteService.updateBusiMoneyrete(busiMoneyrete));
    }

    /**
     * 删除汇率
     */
    @PreAuthorize("@ss.hasPermi('basic:moneyrete:remove')")
    @Log(title = "汇率", businessType = BusinessType.DELETE)
    @GetMapping("/moneyDel")
    @ApiOperation("汇率删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "汇率id",name = "moneyrateIds",paramType = "query",required = true)
    })
    public AjaxResult remove(String[] moneyrateIds)
    {
        return toAjax(busiMoneyreteService.deleteBusiMoneyreteByIds(moneyrateIds));
    }

    /**
     * 导出汇率列表
     */
    @PreAuthorize("@ss.hasPermi('basic:moneyrete:export')")
    @Log(title = "汇率", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiMoneyrete busiMoneyrete)
    {
        List<BusiMoneyrete> list = busiMoneyreteService.selectBusiMoneyreteList(busiMoneyrete);
        ExcelUtil<BusiMoneyrete> util = new ExcelUtil<BusiMoneyrete>(BusiMoneyrete.class);
        return util.exportExcel(list, "moneyrete");
    }
}
