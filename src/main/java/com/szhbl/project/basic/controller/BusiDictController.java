package com.szhbl.project.basic.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.basic.domain.BusiDict;
import com.szhbl.project.basic.service.IBusiDictService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 贸易方式Controller
 *
 * @author dps
 * @date 2020-03-31
 */
@RestController
@RequestMapping("/basic/busidict")
@Api(tags = "订单页贸易方式")
public class BusiDictController extends BaseController
{
    @Autowired
    private IBusiDictService busiDictService;

    /**
     * 订单页面贸易方式
     */
//    @PreAuthorize("@ss.hasPermi('basic:busidict:list')")
    @GetMapping("/listMy")
    @ApiOperation("订单模块贸易方式")
    public TableDataInfo listMy(BusiDict busiDict)
    {
        startPage();
        List<BusiDict> list = busiDictService.selectBusiDictListMy(busiDict);
        return getDataTable(list);
    }

    /**
     * 查询贸易方式列表
     */
//    @PreAuthorize("@ss.hasPermi('basic:busidict:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiDict busiDict)
    {
        startPage();
        List<BusiDict> list = busiDictService.selectBusiDictList(busiDict);
        return getDataTable(list);
    }

    /**
     * 导出贸易方式列表
     */
    @PreAuthorize("@ss.hasPermi('basic:busidict:export')")
    @Log(title = "贸易方式", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiDict busiDict)
    {
        List<BusiDict> list = busiDictService.selectBusiDictList(busiDict);
        ExcelUtil<BusiDict> util = new ExcelUtil<BusiDict>(BusiDict.class);
        return util.exportExcel(list, "busidict");
    }

    /**
     * 获取贸易方式详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:busidict:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busiDictService.selectBusiDictById(id));
    }

    /**
     * 新增贸易方式
     */
    @PreAuthorize("@ss.hasPermi('basic:busidict:add')")
    @Log(title = "贸易方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiDict busiDict)
    {
        return toAjax(busiDictService.insertBusiDict(busiDict));
    }

    /**
     * 修改贸易方式
     */
    @PreAuthorize("@ss.hasPermi('basic:busidict:edit')")
    @Log(title = "贸易方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiDict busiDict)
    {
        return toAjax(busiDictService.updateBusiDict(busiDict));
    }

    /**
     * 删除贸易方式
     */
    @PreAuthorize("@ss.hasPermi('basic:busidict:remove')")
    @Log(title = "贸易方式", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busiDictService.deleteBusiDictByIds(ids));
    }
}
