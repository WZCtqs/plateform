package com.szhbl.project.system.controller;

import java.util.List;

import com.szhbl.project.documentcenter.domain.DocDocumentsType;
import com.szhbl.project.documentcenter.mapper.DocDocumentsTypeMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.szhbl.project.system.domain.MessageSet;
import com.szhbl.project.system.service.IMessageSetService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 消息设置Controller
 * 
 * @author lzd
 * @date 2020-04-17
 */
@RestController
@RequestMapping("/system/messageSet")
@Api("消息设置")
public class MessageSetController extends BaseController
{
    @Autowired
    private IMessageSetService messageSetService;

    @Autowired
    private DocDocumentsTypeMapper docDocumentsTypeMapper;

    /**
     * 查询消息设置列表
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:msgInstall:list')")
    @GetMapping("/list")
    @ApiOperation("查询消息设置列表")
    public TableDataInfo list(MessageSet messageSet)
    {
        startPage();
        List<MessageSet> list = messageSetService.selectMessageSetList(messageSet);
        return getDataTable(list);
    }

    /**
     * 查询单证类型名称
     */
    @PreAuthorize("@ss.hasPermi('system:messageSet:list')")
    @GetMapping("/documentList")
    @ApiOperation("查询单证类型名称,不传参数，返回值fileTypeText为单证类型名称")
    public List documentList()
    {
        DocDocumentsType docDocumentsType=new DocDocumentsType();
        docDocumentsType.setIsSystem(1);
        List<DocDocumentsType> list = docDocumentsTypeMapper.selectAllDocDocumentsTypeList(docDocumentsType);
        return list;
    }

    /**
     * 导出消息设置列表
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:msgInstall:export')")
    @Log(title = "消息设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MessageSet messageSet)
    {
        List<MessageSet> list = messageSetService.selectMessageSetList(messageSet);
        ExcelUtil<MessageSet> util = new ExcelUtil<MessageSet>(MessageSet.class);
        return util.exportExcel(list, "messageSet");
    }

    /**
     * 获取消息设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:msgInstall:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("根据id获取消息设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(messageSetService.selectMessageSetById(id));
    }

    /**
     * 新增消息设置
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:msgInstall:add')")
    @Log(title = "消息设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增消息设置")
    public AjaxResult add(@RequestBody MessageSet messageSet)
    {
        return toAjax(messageSetService.insertMessageSet(messageSet));
    }

    /**
     * 修改消息设置
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:msgInstall:edit')")
    @Log(title = "消息设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改消息设置")
    public AjaxResult edit(@RequestBody MessageSet messageSet)
    {
        return toAjax(messageSetService.updateMessageSet(messageSet));
    }

    /**
     * 删除消息设置
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:msgInstall:remove')")
    @Log(title = "消息设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除消息设置")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(messageSetService.deleteMessageSetByIds(ids));
    }
}
