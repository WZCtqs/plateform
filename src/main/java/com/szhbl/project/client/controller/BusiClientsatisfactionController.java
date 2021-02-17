package com.szhbl.project.client.controller;

import java.util.List;

import com.szhbl.project.client.service.IBusiClientsService;
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
import com.szhbl.project.client.domain.BusiClientsatisfaction;
import com.szhbl.project.client.service.IBusiClientsatisfactionService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 客户满意度Controller
 * 
 * @author jhm
 * @date 2020-01-13
 */
@RestController
@RequestMapping("/clients/clientsatisfaction")
public class BusiClientsatisfactionController extends BaseController
{
    @Autowired
    private IBusiClientsatisfactionService busiClientsatisfactionService;
    @Autowired
    private IBusiClientsService busiClientsService;
    /**
     * 查询客户满意度列表
     */
//    @PreAuthorize("@ss.hasPermi('clients:clientsatisfaction:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiClientsatisfaction busiClientsatisfaction)
    {
        startPage();
        List<BusiClientsatisfaction> list = busiClientsatisfactionService.selectBusiClientsatisfactionList(busiClientsatisfaction);
        return getDataTable(list);
    }


    /**
     * 导出客户满意度列表
     */
    @PreAuthorize("@ss.hasPermi('clients:clientsatisfaction:export')")
    @Log(title = "客户满意度", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiClientsatisfaction busiClientsatisfaction)
    {
        List<BusiClientsatisfaction> list = busiClientsatisfactionService.selectBusiClientsatisfactionList(busiClientsatisfaction);
        ExcelUtil<BusiClientsatisfaction> util = new ExcelUtil<BusiClientsatisfaction>(BusiClientsatisfaction.class);
        return util.exportExcel(list, "clientsatisfaction");
    }

    /**
     * 获取客户满意度详细信息
     */
    @PreAuthorize("@ss.hasPermi('clients:clientsatisfaction:query')")
    @GetMapping(value = "/{satisfactionId}")
    public AjaxResult getInfo(@PathVariable("satisfactionId") String satisfactionId)
    {
        return AjaxResult.success(busiClientsatisfactionService.selectBusiClientsatisfactionById(satisfactionId));
    }

    /**
     * 新增客户满意度
     */
    @PreAuthorize("@ss.hasPermi('clients:clientsatisfaction:add')")
    @Log(title = "客户满意度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiClientsatisfaction busiClientsatisfaction)
    {
        return toAjax(busiClientsatisfactionService.insertBusiClientsatisfaction(busiClientsatisfaction));
    }

    /**
     * 修改客户满意度
     */
    @PreAuthorize("@ss.hasPermi('clients:clientsatisfaction:edit')")
    @Log(title = "客户满意度", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiClientsatisfaction busiClientsatisfaction)
    {
        return toAjax(busiClientsatisfactionService.updateBusiClientsatisfaction(busiClientsatisfaction));
    }

    /**
     * 删除客户满意度
     */
    @PreAuthorize("@ss.hasPermi('clients:clientsatisfaction:remove')")
    @Log(title = "客户满意度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{satisfactionIds}")
    public AjaxResult remove(@PathVariable String[] satisfactionIds)
    {
        return toAjax(busiClientsatisfactionService.deleteBusiClientsatisfactionByIds(satisfactionIds));
    }
}
