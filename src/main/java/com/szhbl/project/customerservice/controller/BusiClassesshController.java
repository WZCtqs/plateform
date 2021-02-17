package com.szhbl.project.customerservice.controller;

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
import com.szhbl.project.customerservice.domain.BusiClassessh;
import com.szhbl.project.customerservice.service.IBusiClassesshService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 排舱管理Controller
 *
 * @author b
 * @date 2020-03-27
 */
@RestController
@RequestMapping("/customerservice/classes")
public class BusiClassesshController extends BaseController
{
    @Autowired
    private IBusiClassesshService busiClassesshService;

    /**
     * 查询排舱管理列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:classes:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiClassessh busiClassessh)
    {
        startPage();
        List<BusiClassessh> list = busiClassesshService.selectBusiClassesshList(busiClassessh);
        return getDataTable(list);
    }

    /**
     * 导出排舱管理列表
     */
    @PreAuthorize("@ss.hasPermi('customerservice:classes:export')")
    @Log(title = "排舱管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiClassessh busiClassessh)
    {
        List<BusiClassessh> list = busiClassesshService.selectBusiClassesshList(busiClassessh);
        ExcelUtil<BusiClassessh> util = new ExcelUtil<BusiClassessh>(BusiClassessh.class);
        return util.exportExcel(list, "classes");
    }

    /**
     * 获取排舱管理详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:classes:query')")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") String classId)
    {
        return AjaxResult.success(busiClassesshService.selectBusiClassesshById(classId));
    }

    /**
     * 新增排舱管理
     */
    @PreAuthorize("@ss.hasPermi('customerservice:classes:add')")
    @Log(title = "排舱管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiClassessh busiClassessh)
    {
        return toAjax(busiClassesshService.insertBusiClassessh(busiClassessh));
    }

    /**
     * 修改排舱管理
     */
    @PreAuthorize("@ss.hasPermi('customerservice:classes:edit')")
    @Log(title = "排舱管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiClassessh busiClassessh)
    {
        return toAjax(busiClassesshService.updateBusiClassessh(busiClassessh));
    }

    /**
     * 删除排舱管理
     */
    @PreAuthorize("@ss.hasPermi('customerservice:classes:remove')")
    @Log(title = "排舱管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{classIds}")
    public AjaxResult remove(@PathVariable String[] classIds)
    {
        return toAjax(busiClassesshService.deleteBusiClassesshByIds(classIds));
    }
}
