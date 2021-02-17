package com.szhbl.project.track.controller;

import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackStationToDoor;
import com.szhbl.project.track.service.ITrackStationToDoorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 站到门/门到站Controller
 * 
 * @author szhbl
 * @date 2020-01-08
 */
@RestController
@RequestMapping("/track/stationToDoor")
@Api(tags = "站到门/门到站")
public class TrackStationToDoorController extends BaseController
{
    @Autowired
    private ITrackStationToDoorService trackStationToDoorService;

    /**
     * 新增站到门/门到站数据
     */
    @PreAuthorize("@ss.hasPermi('track:stationToDoor:add')")
    @Log(title = "站到门/门到站", businessType = BusinessType.OTHER)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody TrackStationToDoor tstd) {
        return trackStationToDoorService.addTstd(tstd);
    }

/*    *//**
     * 查询站到门/门到站
     *//*
    @PreAuthorize("@ss.hasPermi('track:stationToDoor:list')")
    @GetMapping("/list")
    @ApiOperation("站到门/门到站")
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

    *//**
     * 修改站到门/门到站
     *//*
    @ApiOperation(value = "修改站到门/门到站，需要id")
    @PreAuthorize("@ss.hasPermi('track:stationToDoor:edit')")
    @Log(title = "站到门/门到站", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrackAddress trackAddress) {
        return trackAddressService.updateTa(trackAddress);
    }
*/
    /**
     * 删除站到门/门到站
     */
    @ApiOperation(value = "删除站到门/门到站")
    @PreAuthorize("@ss.hasPermi('track:stationToDoor:remove')")
    @Log(title = "站到门/门到站", businessType = BusinessType.DELETE)
    @DeleteMapping("/id")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(trackStationToDoorService.deleteTstd(id));
    }

}
