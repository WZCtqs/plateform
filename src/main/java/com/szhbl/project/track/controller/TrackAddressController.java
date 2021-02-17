package com.szhbl.project.track.controller;

import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.track.domain.TrackAddress;
import com.szhbl.project.track.service.ITrackAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 位置地名管理Controller
 *
 * @author szhbl
 * @date 2020-01-08
 */
@RestController
@RequestMapping("/track/address")
@Api(tags = "位置地名管理")
public class TrackAddressController extends BaseController
{
    @Autowired
    private ITrackAddressService trackAddressService;

    /**
     * 新增位置地名
     */
    @PreAuthorize("@ss.hasPermi('addressDefend:address:add')")
    @Log(title = "位置地名管理", businessType = BusinessType.INSERT)
    @ApiOperation("新增位置地名")
    @PostMapping
    public AjaxResult add(@RequestBody TrackAddress trackAddress) {
        return trackAddressService.addTa(trackAddress);
    }

    /**
     * 查询位置地名
     */
//    @PreAuthorize("@ss.hasPermi('track:address:list')")
    @GetMapping("/list")
    @ApiOperation("查询位置地名管理")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "中文名",name = "nameCh",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "英文名",name = "nameEn",paramType = "query",dataType = "String")
    })
    public TableDataInfo list(TrackAddress trackAddress){
        trackAddress.setSelectType(1);
        startPage();
        List<TrackAddress> list = trackAddressService.selectByTa(trackAddress);
        return getDataTable(list);
    }

    /**
     *  根据id查询位置地名
     */
//    @PreAuthorize("@ss.hasPermi('track:address:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(trackAddressService.selectById(id));
    }

    /**
     *  根据中文名查询位置地名
     */
    //@PreAuthorize("@ss.hasPermi('track:address:query')")
    @GetMapping("/getAdress")
    public AjaxResult getAdress( String name)
    {
        return trackAddressService.selectByName(name);
    }


    /**
     * 修改位置地名
     */
    @ApiOperation(value = "修改位置地名管理，需要id")
    @PreAuthorize("@ss.hasPermi('addressDefend:address:edit')")
    @Log(title = "位置地名管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrackAddress trackAddress) {
        return trackAddressService.updateTa(trackAddress);
    }

    /**
     * 删除位置地名
     */
    @ApiOperation(value = "删除位置地名")
    @PreAuthorize("@ss.hasPermi('addressDefend:address:remove')")
    @Log(title = "位置地名管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(trackAddressService.deleteTa(ids));
    }

}
