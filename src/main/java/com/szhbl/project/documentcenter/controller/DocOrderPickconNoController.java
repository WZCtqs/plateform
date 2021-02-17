package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.documentcenter.domain.DocOrderPickconNo;
import com.szhbl.project.documentcenter.service.IDocOrderPickconNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提箱号表Controller
 *
 * @author szhbl
 * @date 2020-07-04
 */
@RestController
@RequestMapping("/documentcenter/pickconNo")
public class DocOrderPickconNoController extends BaseController {
    @Autowired
    private IDocOrderPickconNoService docOrderPickconNoService;


    /**
     * 导出提箱号表列表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:pickconNo:export')")
    @Log(title = "提箱号表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocOrderPickconNo docOrderPickconNo) {
        List<DocOrderPickconNo> list = docOrderPickconNoService.selectDocOrderPickconNoList(docOrderPickconNo);
        ExcelUtil<DocOrderPickconNo> util = new ExcelUtil<DocOrderPickconNo>(DocOrderPickconNo.class);
        return util.exportExcel(list, "pickconNo");
    }

    /**
     * 获取提箱号表详细信息
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:pickconNo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(docOrderPickconNoService.selectDocOrderPickconNoById(id));
    }

    /**
     * 新增提箱号表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:pickconNo:add')")
    @Log(title = "提箱号表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocOrderPickconNo docOrderPickconNo) {
        return toAjax(docOrderPickconNoService.insertDocOrderPickconNo(docOrderPickconNo));
    }

    /**
     * 修改提箱号表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:pickconNo:edit')")
    @Log(title = "提箱号表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocOrderPickconNo docOrderPickconNo) {
        return toAjax(docOrderPickconNoService.updateDocOrderPickconNo(docOrderPickconNo));
    }

    /**
     * 删除提箱号表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:pickconNo:remove')")
    @Log(title = "提箱号表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(docOrderPickconNoService.deleteDocOrderPickconNoByIds(ids));
    }
}
