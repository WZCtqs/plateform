package com.szhbl.project.trains.controller;

import java.util.List;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.project.trains.service.IBusiClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
import com.szhbl.project.trains.domain.BusiClassesTemplate;
import com.szhbl.project.trains.form.ClassesTemplateForm;
import com.szhbl.project.trains.service.IBusiClassesTemplateService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 班列模板Controller
 *
 * @author szhbl
 * @date 2020-01-10
 */
@RestController
@RequestMapping("/trains/template")
@Api(tags = "班列模板管理")
public class BusiClassesTemplateController extends BaseController
{
    @Autowired
    private IBusiClassesTemplateService busiClassesTemplateService;

    @Autowired
    private IBusiClassesService busiClassesService;

    /**
     * 查询班列模板列表
     */
//    @PreAuthorize("@ss.hasPermi('trains:template:list')")
    @GetMapping("/temlist")
    @ApiOperation("班列模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(ClassesTemplateForm templateForm)
    {
        BusiClassesTemplate busiClassesTemplate =new BusiClassesTemplate();
        BeanUtils.copyProperties(templateForm,busiClassesTemplate);
        startPage();
        List<BusiClassesTemplate> list = busiClassesTemplateService.selectBusiClassesTemplateList(busiClassesTemplate);
        return getDataTable(list);
    }

    /**
     * 获取班列模板详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:template:query')")
    @GetMapping("/getInfo")
    @ApiOperation("班列模板详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "模板id",name = "classTId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(String classTId)
    {
        return AjaxResult.success(busiClassesTemplateService.selectBusiClassesTemplateById(classTId));
    }

    /**
     * 新增班列模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:add')")
    @Log(title = "班列模板", businessType = BusinessType.INSERT)
    @PostMapping("/temAdd")
    @ApiOperation("班列模板新增")
    public AjaxResult add(@Validated @RequestBody ClassesTemplateForm templateForm)
    {
        BusiClassesTemplate busiClassesTemplate =new BusiClassesTemplate();
        BeanUtils.copyProperties(templateForm,busiClassesTemplate);
        if (UserConstants.NOT_UNIQUE.equals(busiClassesTemplateService.checkNumberUnique(busiClassesTemplate.getClassTNumber())))
        {
            return AjaxResult.error("新增模板'" + busiClassesTemplate.getClassTNumber() + "'失败，模板号已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(busiClassesTemplateService.checkTrainCnUnique(busiClassesTemplate.getClassTBlocktrainCn())))
        {
            return AjaxResult.error("新增模板'" + busiClassesTemplate.getClassTBlocktrainCn() + "'失败，模板中文名称已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(busiClassesTemplateService.checkTrainEnUnique(busiClassesTemplate.getClassTBlocktrainEn())))
        {
            return AjaxResult.error("新增模板'" + busiClassesTemplate.getClassTBlocktrainEn() + "'失败，模板英文名称已存在");
        }
        return toAjax(busiClassesTemplateService.insertBusiClassesTemplate(busiClassesTemplate));
    }

    /**
     * 修改班列模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:edit')")
    @Log(title = "班列模板", businessType = BusinessType.UPDATE)
    @PutMapping("/temEdit")
    @ApiOperation("班列模板编辑")
    public AjaxResult edit(@RequestBody ClassesTemplateForm templateForm)
    {
        BusiClassesTemplate busiClassesTemplate =new BusiClassesTemplate();
        BeanUtils.copyProperties(templateForm,busiClassesTemplate);
        if (UserConstants.NOT_UNIQUE.equals(busiClassesTemplateService.checkNumberUniqueUpd(busiClassesTemplate)))
        {
            return AjaxResult.error("编辑模板'" + busiClassesTemplate.getClassTNumber() + "'失败，模板号已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(busiClassesTemplateService.checkTrainCnUniqueUpd(busiClassesTemplate)))
        {
            return AjaxResult.error("编辑模板'" + busiClassesTemplate.getClassTBlocktrainCn() + "'失败，模板中文名称已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(busiClassesTemplateService.checkTrainEnUniqueUpd(busiClassesTemplate)))
        {
            return AjaxResult.error("编辑模板'" + busiClassesTemplate.getClassTBlocktrainEn() + "'失败，模板英文名称已存在");
        }
        return toAjax(busiClassesTemplateService.updateBusiClassesTemplate(busiClassesTemplate));
    }

    /**
     * 删除班列模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:remove')")
    @Log(title = "班列模板", businessType = BusinessType.DELETE)
    @GetMapping("/temDel")
    @ApiOperation("班列模板删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列模板id",name = "classTId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult remove(String classTId)
    {
        if (busiClassesService.hasChildByTemId(classTId))
        {
            return AjaxResult.error("存在下级班列模板,不允许删除");
        }
        return toAjax(busiClassesTemplateService.deleteBusiClassesTemplateById(classTId));
    }

    /**
     * 导出班列模板列表
     */
    @PreAuthorize("@ss.hasPermi('system:template:export')")
    @Log(title = "班列模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiClassesTemplate busiClassesTemplate)
    {
        List<BusiClassesTemplate> list = busiClassesTemplateService.selectBusiClassesTemplateList(busiClassesTemplate);
        ExcelUtil<BusiClassesTemplate> util = new ExcelUtil<BusiClassesTemplate>(BusiClassesTemplate.class);
        return util.exportExcel(list, "template");
    }

}
