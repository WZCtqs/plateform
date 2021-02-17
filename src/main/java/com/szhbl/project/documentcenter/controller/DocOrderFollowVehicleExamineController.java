package com.szhbl.project.documentcenter.controller;

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
import com.szhbl.project.documentcenter.domain.DocOrderFollowVehicleExamine;
import com.szhbl.project.documentcenter.service.IDocOrderFollowVehicleExamineService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 大口岸随车审核Controller
 * 
 * @author szhbl
 * @date 2020-08-19
 */
@RestController
@RequestMapping("/document/followVehicleExamine")
public class DocOrderFollowVehicleExamineController extends BaseController
{
    @Autowired
    private IDocOrderFollowVehicleExamineService docOrderFollowVehicleExamineService;

    /**
     * 查询大口岸随车审核列表
     */
    @PreAuthorize("@ss.hasPermi('document:followVehicleExamine:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocOrderFollowVehicleExamine docOrderFollowVehicleExamine)
    {
        startPage();
        List<DocOrderFollowVehicleExamine> list = docOrderFollowVehicleExamineService.selectDocOrderFollowVehicleExamineList(docOrderFollowVehicleExamine);
        return getDataTable(list);
    }

    /**
     * 导出大口岸随车审核列表
     */
    @PreAuthorize("@ss.hasPermi('document:followVehicleExamine:export')")
    @Log(title = "大口岸随车审核", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocOrderFollowVehicleExamine docOrderFollowVehicleExamine)
    {
        List<DocOrderFollowVehicleExamine> list = docOrderFollowVehicleExamineService.selectDocOrderFollowVehicleExamineList(docOrderFollowVehicleExamine);
        ExcelUtil<DocOrderFollowVehicleExamine> util = new ExcelUtil<DocOrderFollowVehicleExamine>(DocOrderFollowVehicleExamine.class);
        return util.exportExcel(list, "followVehicleExamine");
    }

    /**
     * 获取大口岸随车审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('document:followVehicleExamine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(docOrderFollowVehicleExamineService.selectDocOrderFollowVehicleExamineById(id));
    }

    /**
     * 新增大口岸随车审核
     */
    @PreAuthorize("@ss.hasPermi('document:followVehicleExamine:add')")
    @Log(title = "大口岸随车审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocOrderFollowVehicleExamine docOrderFollowVehicleExamine)
    {
        return toAjax(docOrderFollowVehicleExamineService.insertDocOrderFollowVehicleExamine(docOrderFollowVehicleExamine));
    }

    /**
     * 修改大口岸随车审核
     */
    @PreAuthorize("@ss.hasPermi('document:followVehicleExamine:edit')")
    @Log(title = "大口岸随车审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocOrderFollowVehicleExamine docOrderFollowVehicleExamine)
    {
        return toAjax(docOrderFollowVehicleExamineService.updateDocOrderFollowVehicleExamine(docOrderFollowVehicleExamine));
    }

    /**
     * 删除大口岸随车审核
     */
    @PreAuthorize("@ss.hasPermi('document:followVehicleExamine:remove')")
    @Log(title = "大口岸随车审核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(docOrderFollowVehicleExamineService.deleteDocOrderFollowVehicleExamineByIds(ids));
    }
}
