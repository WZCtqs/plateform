package com.szhbl.project.track.controller;

import java.util.List;

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
import com.szhbl.project.track.domain.TrackIcon;
import com.szhbl.project.track.service.ITrackIconService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 运踪图标Controller
 *
 * @author szhbl
 * @date 2020-03-20
 */
@RestController
@RequestMapping("/track/icon")
@Api(tags = "图标维护表")
public class TrackIconController extends BaseController
{
    @Autowired
    private ITrackIconService trackIconService;

    /**
     * 距离
     */
    //@GetMapping("/inquiry")
    //@ApiOperation("修改运踪图标")
   // public void inquiry(){trackIconService.inquery(); }

    /**
     * 查询运踪图标列表
     */
//    @PreAuthorize("@ss.hasPermi('track:icon:list')")
    @GetMapping("/list")
    @ApiOperation("查询运踪图标列表")
    public TableDataInfo list(TrackIcon trackIcon)
    {
        startPage();
        List<TrackIcon> list = trackIconService.selectTrackIconList(trackIcon);
        return getDataTable(list);
    }

    /**
     * 导出运踪图标列表
     */
    @PreAuthorize("@ss.hasPermi('yunFu:icon:export')")
    @Log(title = "运踪图标", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TrackIcon trackIcon)
    {
        List<TrackIcon> list = trackIconService.selectTrackIconList(trackIcon);
        ExcelUtil<TrackIcon> util = new ExcelUtil<TrackIcon>(TrackIcon.class);
        return util.exportExcel(list, "icon");
    }

    /**
     * 获取运踪图标详细信息
     */
//    @PreAuthorize("@ss.hasPermi('track:icon:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取运踪图标详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(trackIconService.selectTrackIconById(id));
    }

    /**
     * 新增运踪图标
     */
    @PreAuthorize("@ss.hasPermi('yunFu:icon:add')")
    @Log(title = "运踪图标", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增运踪图标")
    public AjaxResult add(@RequestBody TrackIcon trackIcon)
    {
        return toAjax(trackIconService.insertTrackIcon(trackIcon));
    }

    /**
     * 修改运踪图标
     */
    @PreAuthorize("@ss.hasPermi('yunFu:icon:edit')")
    @Log(title = "运踪图标", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改运踪图标")
    public AjaxResult edit(@RequestBody TrackIcon trackIcon)
    {
        return toAjax(trackIconService.updateTrackIcon(trackIcon));
    }

    /**
     * 删除运踪图标
     */
    @PreAuthorize("@ss.hasPermi('yunFu:icon:remove')")
    @Log(title = "运踪图标", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除运踪图标")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(trackIconService.deleteTrackIconByIds(ids));
    }
}
