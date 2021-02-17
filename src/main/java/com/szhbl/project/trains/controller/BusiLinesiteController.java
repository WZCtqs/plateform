package com.szhbl.project.trains.controller;

import java.util.List;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.project.trains.service.IBusiClassesTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.trains.domain.BusiLinesite;
import com.szhbl.project.trains.service.IBusiLinesiteService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 线路Controller
 *
 * @author dps
 * @date 2020-01-11
 */
@RestController
@RequestMapping("/trains/linesite")
@Api(tags = "线路管理")
public class BusiLinesiteController extends BaseController
{
    @Autowired
    private IBusiLinesiteService busiLinesiteService;

    @Autowired
    private IBusiClassesTemplateService busiClassesTemplateService;

    /**
     * 查询线路列表
     */
//    @PreAuthorize("@ss.hasPermi('trains:linesite:list')")
    @GetMapping("/list")
    @ApiOperation("线路列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(BusiLinesite busiLinesite)
    {
        startPage();
        List<BusiLinesite> list = busiLinesiteService.selectBusiLinesiteList(busiLinesite);
        return getDataTable(list);
    }

    /**
     * 获取线路详细信息
     */
//    @PreAuthorize("@ss.hasPermi('trains:linesite:query')")
    @GetMapping("/getInfo")
    @ApiOperation("线路详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "线路id",name = "id",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(String id)
    {
        return AjaxResult.success(busiLinesiteService.selectBusiLinesiteById(id));
    }

    /**
     * 线路新增
     */
    @PreAuthorize("@ss.hasPermi('trains:linesite:add')")
    @Log(title = "线路", businessType = BusinessType.INSERT)
    @PostMapping("/lineAdd")
    @ApiOperation("线路新增")
    public AjaxResult add(@Validated @RequestBody BusiLinesite busiLinesite)
    {
        if (UserConstants.NOT_UNIQUE.equals(busiLinesiteService.checkNameCnUnique(busiLinesite.getNameCn())))
        {
            return AjaxResult.error("新增线路'" + busiLinesite.getNameCn() + "'失败，线路中文名称已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(busiLinesiteService.checkNameEnUnique(busiLinesite.getNameEn())))
        {
            return AjaxResult.error("新增线路'" + busiLinesite.getNameEn() + "'失败，线路英文名称已存在");
        }
        return toAjax(busiLinesiteService.insertBusiLinesite(busiLinesite));
    }

    /**
     * 修改线路
     */
    @PreAuthorize("@ss.hasPermi('trains:linesite:edit')")
    @Log(title = "线路", businessType = BusinessType.UPDATE)
    @PutMapping("/lineEdit")
    @ApiOperation("线路编辑")
    public AjaxResult edit(@RequestBody BusiLinesite busiLinesite)
    {
        if (UserConstants.NOT_UNIQUE.equals(busiLinesiteService.checkNameCnUpdUnique(busiLinesite)))
        {
            return AjaxResult.error("修改线路'" + busiLinesite.getNameCn() + "'失败，线路中文名称已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(busiLinesiteService.checkNameEnUpdUnique(busiLinesite)))
        {
            return AjaxResult.error("修改线路'" + busiLinesite.getNameEn() + "'失败，线路英文名称已存在");
        }
        return toAjax(busiLinesiteService.updateBusiLinesite(busiLinesite));
    }

    /**
     * 线路站点状态修改 0禁用 1启用
     */
    @PreAuthorize("@ss.hasPermi('trains:linesite:edit')")
    @Log(title = "线路状态编辑", businessType = BusinessType.UPDATE)
    @ApiOperation("线路状态编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "线路id"),
            @ApiImplicitParam(name = "state",value = "状态：0禁用 1启用")
    })
    @GetMapping("/changeStatus")
    public AjaxResult changeStatus(String id,String state)
    {
        return toAjax(busiLinesiteService.updateLineStatus(id,state));
    }

    /**
     * 删除线路
     */
    @PreAuthorize("@ss.hasPermi('trains:linesite:remove')")
    @Log(title = "线路", businessType = BusinessType.DELETE)
    @GetMapping("/lineDel")
    @ApiOperation("线路删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "线路id",name = "id",paramType = "query",required = true)
    })
    public AjaxResult remove(String[] id)
    {
        return toAjax(busiLinesiteService.deleteBusiLinesiteByIds(id));
    }

    /**
     * 导出线路列表
     */
    @PreAuthorize("@ss.hasPermi('trains:linesite:export')")
    @Log(title = "线路", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiLinesite busiLinesite)
    {
        List<BusiLinesite> list = busiLinesiteService.selectBusiLinesiteList(busiLinesite);
        ExcelUtil<BusiLinesite> util = new ExcelUtil<BusiLinesite>(BusiLinesite.class);
        return util.exportExcel(list, "linesite");
    }
}
