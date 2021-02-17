package com.szhbl.project.track.controller;

import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.track.domain.TrackGoStation;
import com.szhbl.project.track.domain.vo.TrackIntransitMailVo;
import com.szhbl.project.track.service.ITrackGoStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 去程整柜场站地址Controller
 *
 * @author szhbl
 * @date 2020-01-08
 */
@RestController
@RequestMapping("/track/goStation")
@Api(tags = "去程整柜场站地址")
public class TrackGoStationController extends BaseController
{
    @Autowired
    private ITrackGoStationService trackGoStationService;

    /**
     * 导入去程整柜场站地址
     */
    @PreAuthorize("@ss.hasPermi('go:station:import')")
    @Log(title = "去程整柜场站地址", businessType = BusinessType.IMPORT)
    @ApiOperation("导入去程整柜场站地址")
    @PostMapping("/import")
    public AjaxResult importData(@RequestParam MultipartFile file)throws Exception {
       if(file.isEmpty()){
            return AjaxResult.error("上传文件为空，请重新上传！");
        }
        return trackGoStationService.importData(file);
    }

    /**
     * 查询去程整柜场站地址
     */
//    @PreAuthorize("@ss.hasPermi('track:goStation:list')")
    @GetMapping("/list")
    @ApiOperation("去程整柜场站地址查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "班列编号",name = "classNum",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "班列日期",name = "classDate",paramType = "query",dataType = "String"),
    })
    public TableDataInfo list(TrackGoStation Tgn){
        startPage();
        List<TrackGoStation> list = trackGoStationService.selectByTgn(Tgn);
        return getDataTable(list);
    }

    /**
     *  根据id查询去程整柜场站地址数据
     */
//    @PreAuthorize("@ss.hasPermi('track:goStation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(trackGoStationService.selectById(id));
    }

    /**
     * 修改去程整柜场站地址
     */
    @ApiOperation(value = "修改车站地址")
    @PreAuthorize("@ss.hasPermi('go:station:edit')")
    @Log(title = "去程整柜场站地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrackGoStation Tgn) {
        return toAjax(trackGoStationService.updateTgn(Tgn));
    }


    /**
     * 在途邮箱查询
     */
//    @PreAuthorize("@ss.hasPermi('track:mail:list')")
    @GetMapping("/mailList")
    @ApiOperation("在途邮箱查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "舱位号",name = "orderNum",paramType = "query",dataType = "String"),
    })
    public TableDataInfo mailList(TrackIntransitMailVo timVo){
        startPage();
        List<TrackIntransitMailVo> list = trackGoStationService.selectByTimVo(timVo);
        return getDataTable(list);
    }

    /**
     * 修改在途邮箱
     */
    @ApiOperation(value = "修改在途邮箱")
    @PreAuthorize("@ss.hasPermi('yunFu:station:edit')")
    @Log(title = "在途邮箱", businessType = BusinessType.UPDATE)
    @PutMapping("/editMail")
    public AjaxResult editMail(@RequestBody TrackIntransitMailVo timVo) {
        return toAjax(trackGoStationService.updateTimVo(timVo));
    }
}
