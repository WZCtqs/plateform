package com.szhbl.project.client.controller;

import java.util.List;
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
import com.szhbl.project.client.domain.SystemDict;
import com.szhbl.project.client.service.ISystemDictService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 推荐人管理Controller
 * 
 * @author szhbl
 * @date 2020-01-07
 */
@RestController
@RequestMapping("/clients/reference")
public class SystemDictController extends BaseController
{
    @Autowired
    private ISystemDictService systemDictService;

    /**
     * 查询推荐人管理列表
     */
    @PreAuthorize("@ss.hasPermi('clients:reference:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemDict systemDict)
    {
        startPage();
        List<SystemDict> list = systemDictService.selectSystemDictList(systemDict);
        return getDataTable(list);
    }

    /**
     * 导出推荐人管理列表
     */
    @PreAuthorize("@ss.hasPermi('clients:reference:export')")
    @Log(title = "推荐人管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SystemDict systemDict)
    {
        List<SystemDict> list = systemDictService.selectSystemDictList(systemDict);
        ExcelUtil<SystemDict> util = new ExcelUtil<SystemDict>(SystemDict.class);
        return util.exportExcel(list, "reference");
    }

    /**
     * 获取推荐人管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('clients:reference:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(systemDictService.selectSystemDictById(id));
    }

    /**
     * 新增推荐人管理
     */
    @PreAuthorize("@ss.hasPermi('clients:reference:add')")
    @Log(title = "推荐人管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemDict systemDict)
    {
        return toAjax(systemDictService.insertSystemDict(systemDict));
    }

    /**修改推荐人管理
     *
     */
    @PreAuthorize("@ss.hasPermi('client:reference:edit')")
    @Log(title = "推荐人管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemDict systemDict)
    {
        return toAjax(systemDictService.updateSystemDict(systemDict));
    }

    /**
     * 删除推荐人管理
     */
    @PreAuthorize("@ss.hasPermi('clients:reference:remove')")
    @Log(title = "推荐人管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemDictService.deleteSystemDictByIds(ids));
    }
}
