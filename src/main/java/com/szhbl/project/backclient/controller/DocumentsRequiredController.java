package com.szhbl.project.backclient.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.backclient.domain.DocumentsRequired;
import com.szhbl.project.backclient.service.IDocumentsRequiredService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 单证要求Controller
 * 
 * @author bxt
 * @date 2020-01-07
 */
@CrossOrigin
@RestController
@RequestMapping("/backclient/required")
@Api(tags = " 单证相关要求",description = "客户端/平台端")
public class DocumentsRequiredController extends BaseController
{
    @Autowired
    private IDocumentsRequiredService documentsRequiredService;

    /**
     * 查询单证要求列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:required:list')")
    @GetMapping("/list")
    @ApiOperation("平台查询单证相关要求列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "0进口单证相关要求1出口单证相关要求2铁路相关运单要求",name = "type",paramType = "query",dataType = "String",required = true)
               })
    public TableDataInfo list(DocumentsRequired documentsRequired)
    {
        startPage();
        List<DocumentsRequired> list = documentsRequiredService.selectDocumentsRequiredList(documentsRequired);
        return getDataTable(list);
    }
    @GetMapping("/listClient")
    @ApiOperation("客户端查询单证相关要求")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "0进口单证相关要求1出口单证相关要求2铁路相关运单要求",name = "type",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "语言0中文1英文2俄语3德语",name = "language",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult listClient(DocumentsRequired documentsRequired)
    {
        List<DocumentsRequired> list = documentsRequiredService.selectDocumentsRequiredList(documentsRequired);
        if (list.size()>0){
            if ("1".equals(documentsRequired.getLanguage())){
                list.get(0).setContent(list.get(0).getEnContent());
            }
            return AjaxResult.success(list.get(0));
        }else {
            DocumentsRequired doc = new DocumentsRequired();
            doc.setContent("暂无数据");
            return AjaxResult.success(doc);
        }
    }

    /**
     * 导出单证要求列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:required:export')")
    @Log(title = "单证要求", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocumentsRequired documentsRequired)
    {
        List<DocumentsRequired> list = documentsRequiredService.selectDocumentsRequiredList(documentsRequired);
        ExcelUtil<DocumentsRequired> util = new ExcelUtil<DocumentsRequired>(DocumentsRequired.class);
        return util.exportExcel(list, "required");
    }

    /**
     * 获取单证要求详细信息
     */
    @PreAuthorize("@ss.hasPermi('backclient:required:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(documentsRequiredService.selectDocumentsRequiredById(id));
    }

    /**
     * 新增单证要求
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:add')")
    @Log(title = "单证要求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocumentsRequired documentsRequired)
    {
        DocumentsRequired documentsRequired1 = new DocumentsRequired();
        documentsRequired1.setType(documentsRequired.getType());
        List<DocumentsRequired> list = documentsRequiredService.selectDocumentsRequiredList(documentsRequired1);
        if (list.size()==0){
            return toAjax(documentsRequiredService.insertDocumentsRequired(documentsRequired));
        }else {
            return AjaxResult.error("请不要重复添加同一类型数据！");
        }
    }

    /**
     * 修改单证要求
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:edit')")
    @Log(title = "单证要求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocumentsRequired documentsRequired)
    {
        DocumentsRequired documentsRequired1 = new DocumentsRequired();
        documentsRequired1.setType(documentsRequired.getType());
        List<DocumentsRequired> list = documentsRequiredService.selectDocumentsRequiredList(documentsRequired1);
        //判断是否有重复的单证要求
        if (list.size()==0){
            return toAjax(documentsRequiredService.updateDocumentsRequired(documentsRequired));
        }else {
            //有的话是不是当前数据本身
            if (list.get(0).getId()==documentsRequired.getId()){
                return toAjax(documentsRequiredService.updateDocumentsRequired(documentsRequired));
            }else {
                return toAjax(0);
            }

        }

    }

    /**
     * 删除单证要求
     */
    @PreAuthorize("@ss.hasPermi('portalManage:requirement:remove')")
    @Log(title = "单证要求", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(documentsRequiredService.deleteDocumentsRequiredByIds(ids));
    }
}
