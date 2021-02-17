package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.DocOrderPickconCommand;
import com.szhbl.project.documentcenter.service.IDocOrderPickconCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提箱指令Controller
 *
 * @author szhbl
 * @date 2020-08-13
 */
@RestController
@RequestMapping("/documentcenter/pickconCommand")
public class DocOrderPickconCommandController extends BaseController {
    @Autowired
    private IDocOrderPickconCommandService docOrderPickconCommandService;

    /**
     * 查询提箱指令列表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:DocOrderPickconCommand:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocOrderPickconCommand docOrderPickconCommand) {
        startPage();
        List<DocOrderPickconCommand> list = docOrderPickconCommandService.selectDocOrderPickconCommandList(docOrderPickconCommand);
        return getDataTable(list);
    }

    /**
     * 导出提箱指令列表
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:DocOrderPickconCommand:export')")
    @Log(title = "提箱指令", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocOrderPickconCommand docOrderPickconCommand) {
        List<DocOrderPickconCommand> list = docOrderPickconCommandService.selectDocOrderPickconCommandList(docOrderPickconCommand);
        ExcelUtil<DocOrderPickconCommand> util = new ExcelUtil<DocOrderPickconCommand>(DocOrderPickconCommand.class);
        return util.exportExcel(list, "DocOrderPickconCommand");
    }

    /**
     * 获取提箱指令详细信息
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:DocOrderPickconCommand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(docOrderPickconCommandService.selectDocOrderPickconCommandById(id));
    }

    /**
     * 新增提箱指令
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:DocOrderPickconCommand:add')")
    @Log(title = "提箱指令", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocOrderPickconCommand docOrderPickconCommand) {
        return toAjax(docOrderPickconCommandService.insertDocOrderPickconCommand(docOrderPickconCommand));
    }

    /**
     * 修改提箱指令
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:DocOrderPickconCommand:edit')")
    @Log(title = "提箱指令", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocOrderPickconCommand docOrderPickconCommand) {
        return toAjax(docOrderPickconCommandService.updateDocOrderPickconCommand(docOrderPickconCommand));
    }

    /**
     * 删除提箱指令
     */
    @PreAuthorize("@ss.hasPermi('documentcenter:DocOrderPickconCommand:remove')")
    @Log(title = "提箱指令", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(docOrderPickconCommandService.deleteDocOrderPickconCommandByIds(ids));
    }
}
