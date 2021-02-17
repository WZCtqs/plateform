package com.szhbl.project.trains.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.trains.domain.BusiSite;
import com.szhbl.project.trains.service.IBusiSiteService;
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
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.form.ClassesForm;
import com.szhbl.project.trains.service.IBusiClassesService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 班列Controller
 *
 * @author dps
 * @date 2020-01-13
 */
@RestController
@RequestMapping("/trains/classes")
@Api(tags = "班列管理")
public class BusiClassesController extends BaseController
{
    @Autowired
    private IBusiClassesService busiClassesService;

    @Autowired
    private IBusiSiteService busiSiteService;

    /**
     * 查询班列列表
     */
//    @PreAuthorize("@ss.hasPermi('trains:classes:list')")
    @GetMapping("/classesList")
    @ApiOperation("班列列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(ClassesForm classesForm)
    {
        BusiClasses busiClasses =new BusiClasses();
        BeanUtils.copyProperties(classesForm,busiClasses);
        startPage();
        List<BusiClasses> list = busiClassesService.selectBusiClassesList(busiClasses);
        return getDataTable(list);
    }

    /**
     * 获取班列详细信息
     */
//    @PreAuthorize("@ss.hasPermi('trains:classes:query')")
    @GetMapping("/getInfo")
    @ApiOperation("班列详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列id",name = "classId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(String classId)
    {
        Map mapclass = new HashMap();
        //查询到班列信息
        BusiClasses classes = busiClassesService.selectBusiClassesById(classId);
        mapclass.put("classesinfo",classes);
        if(StringUtils.isNotNull(classes)){
            //查询线路下站点信息
            List<BusiSite> sitelist = busiSiteService.selectBusiSiteListByLine(classes.getLineId());
            mapclass.put("siteinfo",sitelist);
        }
        return AjaxResult.success(mapclass);
    }

    /**
     * 根据班列编号获取班列信息
     */
//    @PreAuthorize("@ss.hasPermi('trains:classes:query')")
    @GetMapping("/getInfoByBh")
    @ApiOperation("根据班列编号获取班列信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列编号",name = "classBh",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfoByBh(String classBh){
        return AjaxResult.success(busiClassesService.selectBusiClassesByBh(classBh));
    }

    /**
     * 新增班列
     */
    @PreAuthorize("@ss.hasPermi('trains:classes:add')")
    @Log(title = "班列", businessType = BusinessType.INSERT)
    @PostMapping("/classesAdd")
    @ApiOperation("班列新增")
    public AjaxResult add(@Validated @RequestBody ClassesForm classesForm)
    {
        BusiClasses busiClasses =new BusiClasses();
        BeanUtils.copyProperties(classesForm,busiClasses);
        if (UserConstants.NOT_UNIQUE.equals(busiClassesService.checkclassBhUnique(busiClasses.getClassBh())))
        {
            return AjaxResult.error("新增班列'" + busiClasses.getClassBh() + "'失败，班列编号已存在");
        }
        return toAjax(busiClassesService.insertBusiClasses(busiClasses));
    }
    /**
     * 班列编号是否存在
     */
//    @PreAuthorize("@ss.hasPermi('trains:classes:query')")
    @GetMapping("/isClasses")
    @ApiOperation("班列编号是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列编号",name = "classBh",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult isClasses(String classBh){
        if (UserConstants.NOT_UNIQUE.equals(busiClassesService.checkclassBhUnique(classBh))){
            return AjaxResult.error("班列编号已存在");
        }else{
            return AjaxResult.success("班列编号可用");
        }
    }


    /**
     * 修改班列
     */
    @PreAuthorize("@ss.hasPermi('trains:classes:edit')")
    @Log(title = "班列", businessType = BusinessType.UPDATE)
    @PutMapping("/classesEdit")
    @ApiOperation("班列编辑")
    public AjaxResult edit(@RequestBody BusiClasses busiClasses)
    {
        if (UserConstants.NOT_UNIQUE.equals(busiClassesService.checkclassBhUniqueUpd(busiClasses)))
        {
            return AjaxResult.error("编辑班列'" + busiClasses.getClassBh() + "'失败，班列编号已存在");
        }
        return toAjax(busiClassesService.updateBusiClasses(busiClasses));
    }

    /**
     * 编辑拼箱修改状态
     */
    @PreAuthorize("@ss.hasPermi('trains:classes:modifiable')")
    @Log(title = "班列", businessType = BusinessType.UPDATE)
    @ApiOperation("拼箱状态是否可修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId",value = "班列id"),
            @ApiImplicitParam(name = "pxentryState",value = "拼箱体积表 0是不可修改，1是可修改（默认不可修改）")
    })
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(String classId,Long pxentryState){
        return toAjax(busiClassesService.updatePxStatus(classId,pxentryState));
    }

    /**
     * 删除班列
     */
    @PreAuthorize("@ss.hasPermi('trains:classes:remove')")
    @Log(title = "班列", businessType = BusinessType.DELETE)
	@GetMapping("/classIds")
    @ApiOperation("班列删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列id",name = "classIds",paramType = "query",required = true)
    })
    public AjaxResult remove(String[] classIds)
    {
        return toAjax(busiClassesService.deleteBusiClassesByIds(classIds));
    }

    /**
     * 导出班列列表
     */
    @PreAuthorize("@ss.hasPermi('trains:classes:export')")
    @Log(title = "班列", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiClasses busiClasses)
    {
        List<BusiClasses> list = busiClassesService.selectBusiClassesList(busiClasses);
        ExcelUtil<BusiClasses> util = new ExcelUtil<BusiClasses>(BusiClasses.class);
        return util.exportExcel(list, "classes");
    }
}
