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
import com.szhbl.project.customerservice.domain.DocOrderDocumentsh;
import com.szhbl.project.customerservice.service.IDocOrderDocumentshService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 单证文件Controller
 *
 * @author szhbl
 * @date 2020-03-27
 */
@RestController
@RequestMapping("/customerservice/document")
public class DocOrderDocumentshController extends BaseController
{
    @Autowired
    private IDocOrderDocumentshService docOrderDocumentshService;

    /**
     * 查询单证文件列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:document:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocOrderDocumentsh docOrderDocumentsh)
    {
        startPage();
        List<DocOrderDocumentsh> list = docOrderDocumentshService.selectDocOrderDocumentshList(docOrderDocumentsh);
        return getDataTable(list);
    }

    /**
     * 导出单证文件列表
     */
    @PreAuthorize("@ss.hasPermi('customerservice:document:export')")
    @Log(title = "单证文件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocOrderDocumentsh docOrderDocumentsh)
    {
        List<DocOrderDocumentsh> list = docOrderDocumentshService.selectDocOrderDocumentshList(docOrderDocumentsh);
        ExcelUtil<DocOrderDocumentsh> util = new ExcelUtil<DocOrderDocumentsh>(DocOrderDocumentsh.class);
        return util.exportExcel(list, "document");
    }

    /**
     * 获取单证文件详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:document:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(docOrderDocumentshService.selectDocOrderDocumentshById(id));
    }

    /**
     * 新增单证文件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:document:add')")
    @Log(title = "单证文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocOrderDocumentsh docOrderDocumentsh)
    {
        return toAjax(docOrderDocumentshService.insertDocOrderDocumentsh(docOrderDocumentsh));
    }

    /**
     * 修改单证文件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:document:edit')")
    @Log(title = "单证文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocOrderDocumentsh docOrderDocumentsh)
    {
        return toAjax(docOrderDocumentshService.updateDocOrderDocumentsh(docOrderDocumentsh));
    }

    /**
     * 删除单证文件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:document:remove')")
    @Log(title = "单证文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(docOrderDocumentshService.deleteDocOrderDocumentshByIds(ids));
    }
    @GetMapping(value = "/urlList")
    public AjaxResult urlList(String fileTypeKey,String orderId)
    {
        return AjaxResult.success(docOrderDocumentshService.selectUrl(fileTypeKey,orderId));
    }
}
