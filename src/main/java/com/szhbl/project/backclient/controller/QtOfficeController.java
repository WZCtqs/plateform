package com.szhbl.project.backclient.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.backclient.domain.QtOffice;
import com.szhbl.project.backclient.service.IQtOfficeService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 办事处信息Controller
 * 
 * @author szhbl
 * @date 2020-03-26
 */
@CrossOrigin
@RestController
@RequestMapping("/backclient/office")
public class QtOfficeController extends BaseController
{
    @Autowired
    private IQtOfficeService qtOfficeService;

    /**
     * 查询办事处信息列表
     */
    @PreAuthorize("@ss.hasPermi('portalManage:office:list')")
    @GetMapping("/list")
    public TableDataInfo list(QtOffice qtOffice)
    {
        startPage();
        List<QtOffice> list = qtOfficeService.selectQtOfficeList(qtOffice);
        return getDataTable(list);
    }
    @GetMapping("/officeList")
    //0中文1英文
    public AjaxResult officeList(String language)
    {
        QtOffice qtOffice = new QtOffice();
        qtOffice.setDelFlag("0");
        List<QtOffice> list = qtOfficeService.selectQtOfficeList(qtOffice);
        if ("1".equals(language)){
            for (QtOffice qtOffice1:list){
                qtOffice1.setOffice(qtOffice1.getEnOffice());
                qtOffice1.setArea(qtOffice1.getEnArea());
                qtOffice1.setContacts(qtOffice1.getEnContacts());
            }
        }
        return AjaxResult.success(list);
    }

    /**
     * 导出办事处信息列表
     */
    @PreAuthorize("@ss.hasPermi('portalManage:office:export')")
    @Log(title = "办事处信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QtOffice qtOffice)
    {
        List<QtOffice> list = qtOfficeService.selectQtOfficeList(qtOffice);
        ExcelUtil<QtOffice> util = new ExcelUtil<QtOffice>(QtOffice.class);
        return util.exportExcel(list, "office");
    }

    /**
     * 获取办事处信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('portalManage:office:query')")
    @GetMapping(value = "/{officeId}")
    public AjaxResult getInfo(@PathVariable("officeId") Integer officeId)
    {
        return AjaxResult.success(qtOfficeService.selectQtOfficeById(officeId));
    }

    /**
     * 新增办事处信息
     */
    @PreAuthorize("@ss.hasPermi('portalManage:office:add')")
    @Log(title = "办事处信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QtOffice qtOffice)
    {
        return toAjax(qtOfficeService.insertQtOffice(qtOffice));
    }

    /**
     * 修改办事处信息
     */
    @PreAuthorize("@ss.hasPermi('portalManage:office:edit')")
    @Log(title = "办事处信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QtOffice qtOffice)
    {
        return toAjax(qtOfficeService.updateQtOffice(qtOffice));
    }

    /**
     * 删除办事处信息
     */
    @PreAuthorize("@ss.hasPermi('portalManage:office:remove')")
    @Log(title = "办事处信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{officeIds}")
    public AjaxResult remove(@PathVariable Integer[] officeIds)
    {
        return toAjax(qtOfficeService.deleteQtOfficeByIds(officeIds));
    }
}
