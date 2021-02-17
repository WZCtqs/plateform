package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.DocOrderArrivalExamine;
import com.szhbl.project.documentcenter.service.IDocOrderArrivalExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关检到货审核表Controller
 *
 * @author szhbl
 * @date 2020-08-17
 */
@RestController
@RequestMapping("/arrivalExamine")
public class DocOrderArrivalExamineController extends BaseController {
    @Autowired
    private IDocOrderArrivalExamineService docOrderArrivalExamineService;

    /**
     * 查询关检到货审核表列表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:arrivalExamine:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocOrderArrivalExamine docOrderArrivalExamine) {
        startPage();
        List<DocOrderArrivalExamine> list = docOrderArrivalExamineService.selectDocOrderArrivalExamineList(docOrderArrivalExamine);
        return getDataTable(list);
    }

    /**
     * 导出关检到货审核表列表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:arrivalExamine:export')")
    @Log(title = "关检到货审核表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocOrderArrivalExamine docOrderArrivalExamine) {
        List<DocOrderArrivalExamine> list = docOrderArrivalExamineService.selectDocOrderArrivalExamineList(docOrderArrivalExamine);
        ExcelUtil<DocOrderArrivalExamine> util = new ExcelUtil<DocOrderArrivalExamine>(DocOrderArrivalExamine.class);
        return util.exportExcel(list, "arrivalExamine");
    }

    /**
     * 获取关检到货审核表详细信息
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:arrivalExamine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(docOrderArrivalExamineService.selectDocOrderArrivalExamineById(id));
    }

    /**
     * 新增关检到货审核表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:arrivalExamine:add')")
    @Log(title = "关检到货审核表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocOrderArrivalExamine docOrderArrivalExamine) {
        return toAjax(docOrderArrivalExamineService.insertDocOrderArrivalExamine(docOrderArrivalExamine));
    }

    /**
     * 修改关检到货审核表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:arrivalExamine:edit')")
    @Log(title = "关检到货审核表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocOrderArrivalExamine docOrderArrivalExamine) {
        return toAjax(docOrderArrivalExamineService.updateDocOrderArrivalExamine(docOrderArrivalExamine));
    }

    /**
     * 删除关检到货审核表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:arrivalExamine:remove')")
    @Log(title = "关检到货审核表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(docOrderArrivalExamineService.deleteDocOrderArrivalExamineByIds(ids));
    }
}
