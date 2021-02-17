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
import com.szhbl.project.backclient.domain.NewsMessage;
import com.szhbl.project.backclient.service.INewsMessageService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 新闻信息Controller
 * 
 * @author szhbl
 * @date 2020-01-07
 */
@CrossOrigin
@RestController
@RequestMapping("/backclient/message")
@Api(tags = " 新闻模块",description = "客户端/平台端")
public class NewsMessageController extends BaseController
{
    @Autowired
    private INewsMessageService newsMessageService;

    /**
     * 查询新闻信息列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:message:list')")
    @GetMapping("/list")
    @ApiOperation("平台查询新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "0班列公告1班列新闻2开行时刻",name = "type",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "是否显示0是1否",name = "isDisplay",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "语言0中文1英文2俄语3德语",name = "language",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo list(NewsMessage newsMessage)
    {
        startPage();
        List<NewsMessage> list = newsMessageService.selectNewsMessageList(newsMessage);
        return getDataTable(list);
    }
    @GetMapping("/listClient")
    @ApiOperation("用户端查询新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "0班列公告1班列新闻2开行时刻",name = "type",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "语言0中文1英文2俄语3德语",name = "language",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo listClient(NewsMessage newsMessage)
    {
        startPage();
        newsMessage.setIsDisplay("0");
        List<NewsMessage> list = newsMessageService.selectNewsMessageList(newsMessage);
        return getDataTable(list);
    }

    /**
     * 导出新闻信息列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:message:export')")
    @Log(title = "新闻信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(NewsMessage newsMessage)
    {
        List<NewsMessage> list = newsMessageService.selectNewsMessageList(newsMessage);
        ExcelUtil<NewsMessage> util = new ExcelUtil<NewsMessage>(NewsMessage.class);
        return util.exportExcel(list, "message");
    }

    /**
     * 获取新闻信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('backclient:message:query')")
    @GetMapping(value = "getDetail")
    @ApiOperation("平台端查询新闻详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "新闻id",name = "id",paramType = "query",dataType = "Long",required = true)
    })
    public AjaxResult getDetail( Long id) {
        return AjaxResult.success(newsMessageService.selectNewsMessageById(id));
    }
    @GetMapping(value = "clientGetDetail")
    @ApiOperation("用户端查询新闻详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "新闻id",name = "id",paramType = "query",dataType = "Long",required = true)
    })
    public AjaxResult clientGetDetail(Long id)
    {
        return AjaxResult.success(newsMessageService.selectNewsMessageById(id));
    }

    /**
     * 新增新闻信息
     */
    @PreAuthorize("@ss.hasPermi('backclient:message:add')")
    @Log(title = "新闻信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewsMessage newsMessage)
    {
        return toAjax(newsMessageService.insertNewsMessage(newsMessage));
    }

    /**
     * 修改新闻信息
     */
    @PreAuthorize("@ss.hasPermi('backclient:message:edit')")
    @Log(title = "新闻信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsMessage newsMessage)
    {
        System.out.println(newsMessage.getAbstracts());
        return toAjax(newsMessageService.updateNewsMessage(newsMessage));
    }

    /**
     * 删除新闻信息
     */
    @PreAuthorize("@ss.hasPermi('backclient:message:remove')")
    @Log(title = "新闻信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsMessageService.deleteNewsMessageByIds(ids));
    }

    /**
     * 官网新增新闻信息
     */
    @PostMapping("/gwAdd")
    public AjaxResult gwAdd(NewsMessage newsMessage)
    {
        return toAjax(newsMessageService.insertNewsMessage(newsMessage));
    }
}
