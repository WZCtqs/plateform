package com.szhbl.project.track.controller;

import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.track.domain.TrackRookieSite;
import com.szhbl.project.track.service.ITrackRookieSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜鸟站点管理Controller
 *
 * @author szhbl
 * @date 2020-01-08
 */
@RestController
@RequestMapping("/track/rookie")
@Api(tags = "菜鸟站点管理")
public class TrackRookieSiteController extends BaseController
{
    @Autowired
    private ITrackRookieSiteService trackRookieSiteService;

    /**
     * 新增菜鸟站点
     */
    @PreAuthorize("@ss.hasPermi('yunFu:rookieSite:add')")
    @Log(title = "菜鸟站点管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增菜鸟站点管理")
    public AjaxResult add(@RequestBody TrackRookieSite trackRookieSite) {
        return trackRookieSiteService.addTrs(trackRookieSite);
    }

    /**
     * 查询菜鸟站点
     */
//    @PreAuthorize("@ss.hasPermi('track:rookie:list')")
    @GetMapping("/list")
    @ApiOperation("查询菜鸟站点管理")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "中文名",name = "nameCh",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "英文名",name = "nameEn",paramType = "query",dataType = "String")
    })
    public TableDataInfo list(TrackRookieSite trackRookieSite){
        startPage();
        List<TrackRookieSite> list = trackRookieSiteService.selectByTrs(trackRookieSite);
        return getDataTable(list);
    }

    /**
     * 查询菜鸟站点
     */
//    @PreAuthorize("@ss.hasPermi('track:rookie:query')")
    @ApiOperation("根据id查询菜鸟站点管理")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(trackRookieSiteService.selectById(id));

    }

    /**
     * 修改菜鸟站点
     */
    @ApiOperation(value = "修改位置地名管理，需要id")
    @PreAuthorize("@ss.hasPermi('yunFu:rookieSite:edit')")
    @Log(title = "菜鸟站点管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrackRookieSite trackRookieSite) {
        return trackRookieSiteService.updateTrs(trackRookieSite);
    }

    /**
     * 删除菜鸟站点
     */
    @ApiOperation(value = "删除位置地名")
    @PreAuthorize("@ss.hasPermi('yunFu:rookieSite:remove')")
    @Log(title = "位置地名管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {
        return toAjax(trackRookieSiteService.deleteTa(id));
    }

}
