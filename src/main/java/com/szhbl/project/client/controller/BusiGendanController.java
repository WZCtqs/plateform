package com.szhbl.project.client.controller;

import java.util.List;

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
import com.szhbl.project.client.domain.BusiGendan;
import com.szhbl.project.client.service.IBusiGendanService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 客户跟单员Controller
 * 
 * @author szhbl
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/client/gendan")
public class BusiGendanController extends BaseController
{
    @Autowired
    private IBusiGendanService busiGendanService;

    /**
     * 查询客户跟单员列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BusiGendan busiGendan)
    {
        startPage();
        List<BusiGendan> list = busiGendanService.selectBusiGendanList(busiGendan);
        return getDataTable(list);
    }

    /**
     * 获取客户跟单员详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busiGendanService.selectBusiGendanById(id));
    }

    /**
     * 新增客户跟单员
     */
    @Log(title = "客户跟单员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiGendan busiGendan)
    {
        return toAjax(busiGendanService.insertBusiGendan(busiGendan));
    }

    /**
     * 修改客户跟单员
     */
    @Log(title = "客户跟单员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiGendan busiGendan)
    {
        return toAjax(busiGendanService.updateBusiGendanByUserId(busiGendan));
    }

    /**
     * 删除客户跟单员
     */
    @Log(title = "客户跟单员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busiGendanService.deleteBusiGendanByIds(ids));
    }
}
