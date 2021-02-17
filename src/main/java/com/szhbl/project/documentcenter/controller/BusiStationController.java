package com.szhbl.project.documentcenter.controller;

import java.util.Date;
import java.util.List;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.documentcenter.service.IBusiStationService;
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
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 场站地址Controller
 *
 * @author szhbl
 * @date 2020-01-08
 */
@RestController
@RequestMapping("/document/station")
public class BusiStationController extends BaseController
{
    @Autowired
    private IBusiStationService busiStationService;

    /**
     * 查询场站地址列表
     */
//    @PreAuthorize("@ss.hasPermi('document:station:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiStation busiStation)
    {
        startPage();
        List<BusiStation> list = busiStationService.selectBusiStationList(busiStation);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('document:station:add')")
    @GetMapping("/sitelist")
    public AjaxResult sitelist()
    {
        return AjaxResult.success(busiStationService.selectBusiSiteList());
    }

    /**
     * 导出场站地址列表
     */
    @PreAuthorize("@ss.hasPermi('document:station:export')")
    @Log(title = "场站地址", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiStation busiStation)
    {
        List<BusiStation> list = busiStationService.selectBusiStationList(busiStation);
        ExcelUtil<BusiStation> util = new ExcelUtil<BusiStation>(BusiStation.class);
        return util.exportExcel(list, "station");
    }

    /**
     * 获取场站地址详细信息
     */
//    @PreAuthorize("@ss.hasPermi('document:station:query')")
    @GetMapping(value = "/{stationId}")
    public AjaxResult getInfo(@PathVariable("stationId") String stationId)
    {
        return AjaxResult.success(busiStationService.selectBusiStationById(stationId));
    }

    /**
     * 新增场站地址
     */
    @PreAuthorize("@ss.hasPermi('document:station:add')")
    @Log(title = "场站地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiStation busiStation)
    {
        busiStation.setCreateuser(SecurityUtils.getUsername());
        busiStation.setCreatedate(new Date());
        return toAjax(busiStationService.insertBusiStation(busiStation));
    }

    /**
     * 修改场站地址
     */
    @PreAuthorize("@ss.hasPermi('document:station:edit')")
    @Log(title = "场站地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiStation busiStation)
    {
        busiStation.setUpdateBy(SecurityUtils.getUsername());
        busiStation.setUpdateTime(new Date());
        return toAjax(busiStationService.updateBusiStation(busiStation));
    }

    /**
     * 删除场站地址
     */
    @PreAuthorize("@ss.hasPermi('document:station:remove')")
    @Log(title = "场站地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{stationIds}")
    public AjaxResult remove(@PathVariable String[] stationIds)
    {
        return toAjax(busiStationService.deleteBusiStationByIds(stationIds));
    }

    /**
     * 更新场站地址是否启用状态
     *
     * @param busiStation 需要更新的数据
     * @return 结果
     */
    @PreAuthorize("@ss.hasAnyPermi('document:station:isenabled')")
    @Log(title = "场站地址更改状态",businessType = BusinessType.UPDATE)
    @PutMapping("/changeStationEnable")
    public AjaxResult changeStationEnable(@RequestBody BusiStation busiStation){
        return toAjax(busiStationService.updateBusiStation(busiStation));
    }
}
