package com.szhbl.project.track.controller;

import java.util.List;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.project.track.domain.vo.TrackQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import com.szhbl.project.track.domain.TrackOneLevel;
import com.szhbl.project.track.service.ITrackOneLevelService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 运踪一级节点Controller
 *
 * @author lzd
 * @date 2020-03-23
 */
@RestController
@RequestMapping("/track/oneLevel")
@Api(tags = "一级节点")
public class TrackOneLevelController extends BaseController
{
    @Autowired
    private ITrackOneLevelService trackOneLevelService;

    /**
     * 运踪查询
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:trackList')")
    @GetMapping("/trackList")
    @ApiOperation("最外面页面的运踪查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "去回程0为去(西向) 1为回(东向）",name = "classEastAndWest",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "班列日期",name = "classDate",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "委托书编号",name = "orderNumber",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "箱号",name = "boxNum",paramType = "query",dataType = "String"),
    })
    public TableDataInfo trackList(TrackQueryVo trackVo)
    {
        startPage();
        trackVo.setDeptCode(SecurityUtils.getLoginUser().getUser().getDeptCode());
        trackVo.setUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        List<TrackQueryVo> list = trackOneLevelService.selectTrackList(trackVo);
        return getDataTable(list);
    }



    /**
     * 查询运踪一级节点列表
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(TrackOneLevel trackOneLevel)
    {
        startPage();
        List<TrackOneLevel> list = trackOneLevelService.selectTrackOneLevelList(trackOneLevel);
        return getDataTable(list);
    }

    /**
     * 导出运踪一级节点列表
     */
    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:export')")
    @Log(title = "运踪一级节点", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TrackOneLevel trackOneLevel)
    {
        List<TrackOneLevel> list = trackOneLevelService.selectTrackOneLevelList(trackOneLevel);
        ExcelUtil<TrackOneLevel> util = new ExcelUtil<TrackOneLevel>(TrackOneLevel.class);
        return util.exportExcel(list, "oneLevel");
    }

    /**
     * 获取运踪一级节点详细信息
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(trackOneLevelService.selectTrackOneLevelById(id));
    }
    /**
     * 根据orderId查询一级节点
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:query')")
    @GetMapping(value = "details")
    @ApiOperation("根据orderId查询一级节点")
    public AjaxResult details(String orderId,String boxNum)
    {
        return AjaxResult.success(trackOneLevelService.selectTolByOrderId(orderId,boxNum));
    }

    /**
     * 根据orderId查询发货地和收货地
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:query')")
    @GetMapping(value = "getAddress")
    @ApiOperation("根据orderId查询发货地和收货地")
    public AjaxResult getAddress(String orderId)
    {
        return AjaxResult.success(trackOneLevelService.selectAddrssByOrderId(orderId));
    }

    /**
     * 根据classId查询班列运踪
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:query')")
    @GetMapping(value = "trainList")
    @ApiOperation("根据classId，orderId，boxNum查询班列运踪")
    public AjaxResult trainList(String classId,String orderId,String boxNum)
    {
        return AjaxResult.success(trackOneLevelService.selectTrainListByClassId(classId,orderId,boxNum));
    }

    /**
     * 邮件发送记录查询
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:query')")
    @GetMapping(value = "emailsRecord")
    @ApiOperation("邮件发送记录查询")
    public AjaxResult emailsRecord(String orderNum)
    {
        return AjaxResult.success(trackOneLevelService.selectEmailRecords(orderNum));
    }

    /**
     * 新增运踪一级节点
     */
    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:add')")
    @Log(title = "运踪一级节点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TrackOneLevel trackOneLevel)
    {
        return toAjax(trackOneLevelService.insertTrackOneLevel(trackOneLevel));
    }

    /**
     * 修改运踪一级节点
     */
    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:edit')")
    @Log(title = "运踪一级节点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrackOneLevel trackOneLevel)
    {
        return toAjax(trackOneLevelService.updateTrackOneLevel(trackOneLevel));
    }

    /**
     * 删除运踪一级节点
     */
    @PreAuthorize("@ss.hasPermi('yunFu:oneLevel:remove')")
    @Log(title = "运踪一级节点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(trackOneLevelService.deleteTrackOneLevelByIds(ids));
    }
}
