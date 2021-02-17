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
import com.szhbl.project.basic.domain.BaseGoodsnote;
import com.szhbl.project.basic.service.IBaseGoodsnoteService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 特殊单证物品Controller
 * 
 * @author dps
 * @date 2020-01-15
 */
@RestController
@RequestMapping("/basic/goodsnote")
@Api(tags = "特殊单证物品管理")
public class BaseGoodsnoteController extends BaseController
{
    @Autowired
    private IBaseGoodsnoteService baseGoodsnoteService;

    /**
     * 查询特殊单证物品列表
     */
//    @PreAuthorize("@ss.hasPermi('basic:goodsnote:list')")
    @GetMapping("/noteList")
    @ApiOperation("特殊单证物品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(BaseGoodsnote baseGoodsnote)
    {
        startPage();
        List<BaseGoodsnote> list = baseGoodsnoteService.selectBaseGoodsnoteList(baseGoodsnote);
        return getDataTable(list);
    }

    /**
     * 获取特殊单证物品详细信息
     */
    @PreAuthorize("@ss.hasPermi('risk:goodsnote:query')")
    @GetMapping("/getInfo")
    @ApiOperation("特殊单证物品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "特殊单证物品id",name = "id",paramType = "query",dataType = "Long",required = true)
    })
    public AjaxResult getInfo(Long id)
    {
        return AjaxResult.success(baseGoodsnoteService.selectBaseGoodsnoteById(id));
    }

    /**
     * 新增特殊单证物品
     */
    @PreAuthorize("@ss.hasPermi('risk:goodsnote:add')")
    @Log(title = "特殊单证物品", businessType = BusinessType.INSERT)
    @PostMapping("/noteAdd")
    @ApiOperation("特殊单证物品新增")
    public AjaxResult add(@Validated @RequestBody BaseGoodsnote baseGoodsnote)
    {
        return toAjax(baseGoodsnoteService.insertBaseGoodsnote(baseGoodsnote));
    }

    /**
     * 修改特殊单证物品
     */
    @PreAuthorize("@ss.hasPermi('risk:goodsnote:edit')")
    @Log(title = "特殊单证物品", businessType = BusinessType.UPDATE)
    @PutMapping("/noteEdit")
    @ApiOperation("特殊单证物品编辑")
    public AjaxResult edit(@RequestBody BaseGoodsnote baseGoodsnote)
    {
        return toAjax(baseGoodsnoteService.updateBaseGoodsnote(baseGoodsnote));
    }

    /**
     * 删除特殊单证物品
     */
    @PreAuthorize("@ss.hasPermi('risk:goodsnote:remove')")
    @Log(title = "特殊单证物品", businessType = BusinessType.DELETE)
    @GetMapping("/noteDel")
    @ApiOperation("特殊单证物品删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "特殊单证物品id",name = "ids",paramType = "query",required = true)
    })
    public AjaxResult remove(Long[] ids)
    {
        return toAjax(baseGoodsnoteService.deleteBaseGoodsnoteByIds(ids));
    }

    /**
     * 导出特殊单证物品列表
     */
    @PreAuthorize("@ss.hasPermi('basic:goodsnote:export')")
    @Log(title = "特殊单证物品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BaseGoodsnote baseGoodsnote)
    {
        List<BaseGoodsnote> list = baseGoodsnoteService.selectBaseGoodsnoteList(baseGoodsnote);
        ExcelUtil<BaseGoodsnote> util = new ExcelUtil<BaseGoodsnote>(BaseGoodsnote.class);
        return util.exportExcel(list, "goodsnote");
    }
}
