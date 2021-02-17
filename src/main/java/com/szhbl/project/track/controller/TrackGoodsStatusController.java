package com.szhbl.project.track.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.domain.vo.OrderGoodsVo;
import com.szhbl.project.track.service.ITrackGoodsStatusService;
import com.szhbl.framework.web.controller.BaseController;
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
 * 获取状态表Controller
 *
 * @author szhbl
 * @date 2020-01-08
 */
@RestController
@RequestMapping("/track/goodsStatus")
@Api(tags = "货物状态表")
public class TrackGoodsStatusController extends BaseController
{
    @Autowired
    private ITrackGoodsStatusService trackGoodsStatusService;

    /**
     * 导入货物状态表
     */
    @PreAuthorize("@ss.hasPermi('track:goodsStatus:import')")
    @Log(title = "货物状态表", businessType = BusinessType.IMPORT)
    //@ApiOperation("导入货物状态表")
    @PostMapping("/import")
    public AjaxResult importData(@RequestParam MultipartFile file)throws Exception {
       if(file.isEmpty()){
            return AjaxResult.error("上传文件为空，请重新上传！");
        }
        return trackGoodsStatusService.importData(file);
    }

    /**
     * 导出运踪_货物状态--以舱位号为单位，标记是否发车列表
     */
    @PreAuthorize("@ss.hasPermi('track:goodsStatus:export')")
    @Log(title = "导出运踪_货物状态", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TrackGoodsStatus trackGoodsStatus)
    {
        List<TrackGoodsStatus> list = trackGoodsStatusService.selectGoodsStatusList(trackGoodsStatus);
        ExcelUtil<TrackGoodsStatus> util = new ExcelUtil<TrackGoodsStatus>(TrackGoodsStatus.class);
        return util.exportExcel(list, "箱舱匹配");
    }


    /**
     * 查询货物状态表
     */
//    @PreAuthorize("@ss.hasPermi('track:goodsStatus:list')")
    @GetMapping("/list")
    @ApiOperation("货物状态表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "委托书编号",name = "orderNum",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "箱号",name = "boxNum",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "班列日期",name = "classDate",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "线路类型：0是中欧2是中亚3是中越4是中俄",name = "lineTypeId",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "班列编号",name = "classNum",paramType = "query",dataType = "String")
    })
    public TableDataInfo list(TrackGoodsStatus tgs){
        startPage();
        List<TrackGoodsStatus> list = trackGoodsStatusService.selectGoodsStatusList(tgs);
        return getDataTable(list);
    }


//    @PreAuthorize("@ss.hasPermi('track:goodsStatus:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("根据id进行货物状态表查询")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(trackGoodsStatusService.selectById(id));
    }

    /**
     * 修改货物状态表
     */
    @ApiOperation(value = "修改货物状态表,根据id修改")
    @PreAuthorize("@ss.hasPermi('track:goodsStatus:edit')")
    @Log(title = "货物状态表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrackGoodsStatus Tgs)
    {
        return toAjax(trackGoodsStatusService.updateTgs(Tgs));
    }

    /**
     * 批量修改货物状态表
     */
    @ApiOperation(value = "修改货物状态表,根据ids修改")
    @PreAuthorize("@ss.hasPermi('track:goodsStatus:edit')")
    @Log(title = "货物状态表", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult update(@RequestBody TrackGoodsStatus Tgs)
    {
        String[] ids=Tgs.getIds().split(",");
        for(int i=0;i<ids.length;i++){
            Tgs.setId(Long.valueOf(ids[i]));
            Tgs.setBoxNum(null);
            Tgs.setOrderId(null);
            trackGoodsStatusService.updateTgs(Tgs);
        }
        return toAjax(ids.length);
    }

//    @PreAuthorize("@ss.hasPermi('track:goodsStatus:list')")
    @GetMapping("/goodsList")
    @ApiOperation("货物品名查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "货物品名",name = "goodsName",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "HS编码",name = "hsCode",paramType = "query",dataType = "String")
    })
    public TableDataInfo goodsList(OrderGoodsVo ogv){
        startPage();
        List<OrderGoodsVo> list = trackGoodsStatusService.selectGoodsList(ogv);
        return getDataTable(list);
    }


/*    //@PreAuthorize("@ss.hasPermi('track:goodsStatus:query')")
    @PostMapping
    public AjaxResult add(@RequestBody TrackGoodsStatus Tgs)
    {
        trackGoodsStatusService.insertXcppTgs(Tgs);
        System.out.println("trackId-----------------"+Tgs.getId());
        System.out.println("Long.valueOf(trackId)-----------------"+Tgs.getId());
        return AjaxResult.success(Tgs.getId());
    }*/
}
