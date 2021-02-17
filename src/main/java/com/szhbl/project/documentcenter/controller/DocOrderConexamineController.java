package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.DocOrderConexamine;
import com.szhbl.project.documentcenter.service.IDocOrderConexamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 大口岸箱号随车审核Controller
 *
 * @author szhbl
 * @date 2020-08-19
 */
@RestController
@RequestMapping("/document/conFollowVehicleExamine")
public class DocOrderConexamineController extends BaseController {
    @Autowired
    private IDocOrderConexamineService docOrderConexamineService;

    /**
     * 查询大口岸箱号随车审核列表
     */
    @PreAuthorize("@ss.hasPermi('document:conFollowVehicleExamine:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocOrderConexamine docOrderConexamine) {
        startPage();
        List<DocOrderConexamine> list = docOrderConexamineService.selectDocOrderConexamineList(docOrderConexamine);
        return getDataTable(list);
    }

    /**
     * 导出大口岸箱号随车审核列表
     */
    @PreAuthorize("@ss.hasPermi('document:conFollowVehicleExamine:export')")
    @Log(title = "大口岸箱号随车审核", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocOrderConexamine docOrderConexamine) {
        List<DocOrderConexamine> list = docOrderConexamineService.selectDocOrderConexamineList(docOrderConexamine);
        ExcelUtil<DocOrderConexamine> util = new ExcelUtil<DocOrderConexamine>(DocOrderConexamine.class);
        return util.exportExcel(list, "conFollowVehicleExamine");
    }

    /**
     * 获取大口岸箱号随车审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('document:conFollowVehicleExamine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(docOrderConexamineService.selectDocOrderConexamineById(id));
    }

    /**
     * 新增大口岸箱号随车审核
     */
    @PreAuthorize("@ss.hasPermi('document:conFollowVehicleExamine:add')")
    @Log(title = "大口岸箱号随车审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocOrderConexamine docOrderConexamine) {
        return toAjax(docOrderConexamineService.insertDocOrderConexamine(docOrderConexamine));
    }

    /**
     * 修改大口岸箱号随车审核
     */
    @PreAuthorize("@ss.hasPermi('document:conFollowVehicleExamine:edit')")
    @Log(title = "大口岸箱号随车审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocOrderConexamine docOrderConexamine) {
        return toAjax(docOrderConexamineService.updateDocOrderConexamine(docOrderConexamine));
    }

    /**
     * 删除大口岸箱号随车审核
     */
    @PreAuthorize("@ss.hasPermi('document:conFollowVehicleExamine:remove')")
    @Log(title = "大口岸箱号随车审核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(docOrderConexamineService.deleteDocOrderConexamineByIds(ids));
    }
}
