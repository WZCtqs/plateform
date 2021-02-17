package com.szhbl.project.track.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.project.track.domain.vo.TrackTrainVo;
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
import com.szhbl.project.track.domain.TrackTrain;
import com.szhbl.project.track.service.ITrackTrainService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 运踪_班列站到站Controller
 *
 * @author szhbl
 * @date 2020-03-16
 */
@RestController
@RequestMapping("/track/train")
@Api(tags = "班列运踪")
public class TrackTrainController extends BaseController
{
    @Autowired
    private ITrackTrainService trackTrainService;

    /**
     * 查询班列
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:train:list')")
    @GetMapping("/trainsList")
    @ApiOperation("查询班列")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "开行班列",name = "classBlockTrain",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "班列日期",name = "classSTime",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "始发站",name = "classStationOfDeparture",paramType = "query",dataType = "String"),
            @ApiImplicitParam(value = "目的站",name = "classStationOfDestination",paramType = "query",dataType = "String"),
    })
    public TableDataInfo trainsList(TrackTrainVo trainVo)
    {
        startPage();
        List<TrackTrainVo> list = trackTrainService.selectTrainsList(trainVo);
        return getDataTable(list);
    }

    /**
     * 根据班列id查询班列运踪
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:train:list')")
    @GetMapping("/list")
    @ApiOperation("根据班列id查询班列运踪")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "班列id",name = "classId",paramType = "query",dataType = "String",required = true),
    })
    public TableDataInfo list(TrackTrain trackTrain)
    {
        startPage();
        List<TrackTrain> list = trackTrainService.selectTrackTrainList(trackTrain);
        return getDataTable(list);
    }

    /**
     * 获取运踪_班列站到站详细信息
     */
//    @PreAuthorize("@ss.hasPermi('yunFu:train:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("根据id查询班列,弹框内容")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(trackTrainService.selectTrackTrainById(id));
    }

    /**
     * 新增运踪_班列站到站
     */
    @PreAuthorize("@ss.hasPermi('yunFu:train:add')")
    @Log(title = "运踪_班列站到站", businessType = BusinessType.INSERT)
    @ApiOperation("新增,弹框内容")
    @PostMapping
    public AjaxResult add(@RequestBody TrackTrain trackTrain)
    {
        return toAjax(trackTrainService.insertTrackTrain(trackTrain));
    }

    /**
     * 新增保存发送全部或者发送VIP
     */
    @PreAuthorize("@ss.hasPermi('yunFu:train:add')")
    @Log(title = "运踪_班列站到站", businessType = BusinessType.INSERT)
    @ApiOperation("新增保存发送全部或者发送VIP,isVip字段为0发送全部为1发送vip，int类型")
    @PostMapping("/addAndSend")
    public AjaxResult addAndSend(@RequestBody TrackTrain trackTrain)
    {
        return toAjax(trackTrainService.addAndSend(trackTrain));
    }

    /**
     * 编辑保存发送全部或者发送VIP
     */
    @PreAuthorize("@ss.hasPermi('yunFu:train:add')")
    @Log(title = "运踪_班列站到站", businessType = BusinessType.INSERT)
    @ApiOperation("编辑保存发送全部或者发送VIP,isVip字段为0发送全部为1发送vip，int类型")
    @PostMapping("/editAndSend")
    public AjaxResult editAndSend(@RequestBody TrackTrain trackTrain)
    {
        return toAjax(trackTrainService.editAndSend(trackTrain));
    }

    /**
     * 修改运踪_班列站到站
     */
    @PreAuthorize("@ss.hasPermi('yunFu:train:edit')")
    @Log(title = "运踪_班列站到站", businessType = BusinessType.UPDATE)
    @ApiOperation("修改,弹框内容")
    @PutMapping
    public AjaxResult edit(@RequestBody TrackTrain trackTrain)
    {
        return toAjax(trackTrainService.updateTrackTrain(trackTrain));
    }

    /**
     * 删除运踪_班列站到站
     */
    @PreAuthorize("@ss.hasPermi('yunFu:train:remove')")
    @Log(title = "删除运踪_班列站到站", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("根据班列运踪ids数组删除班列运踪")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(trackTrainService.deleteTrackTrainByIds(ids));
    }

    /**
     * 删除运踪_班列站到站
     */
    @PreAuthorize("@ss.hasPermi('yunFu:train:remove')")
    @Log(title = "删除运踪_班列站到站", businessType = BusinessType.DELETE)
    @DeleteMapping("/id")
    @ApiOperation("根据班列运踪id删除班列运踪")
    public AjaxResult delete(@PathVariable Integer id)
    {
        return toAjax(trackTrainService.deleteTrackTrainById(id));
    }

    /**
     * 导出运踪_班列站到站列表
     */
    @PreAuthorize("@ss.hasPermi('track:train:export')")
    @Log(title = "运踪_班列站到站", businessType = BusinessType.EXPORT)
    @GetMapping("/exportTrainList")
    public AjaxResult export(TrackTrain trackTrain)
    {
        String goCome="";
        if("0".equals(trackTrain.getClassEastAndWest())){
            goCome="去程";
        }else if("1".equals(trackTrain.getClassEastAndWest())){
            goCome="回程";
        }
        String date= DateUtils.parseDateToStr("yyyy-MM-dd",new Date());
        //对比分析结果添加，导出文件名改为去/回程在途班列跟踪日报加日期，数据自动换行，班列日期倒叙排序，-M,-P添加，获取班列号最新一条运踪数据
        //数据自动换行
        List<TrackTrain> list = trackTrainService.selectExportTrainList(trackTrain);
        ExcelUtil<TrackTrain> util = new ExcelUtil<TrackTrain>(TrackTrain.class);
        return util.exportExcel(list, goCome+"在途班列运行跟踪日报"+date);
    }

    /**
     * 查询班列运踪详情
     */
    @GetMapping("/getTrainList")
    public TableDataInfo getTrainList(TrackTrain trackTrain)
    {
        startPage();
        List<TrackTrain> list =trackTrainService.selectTrackTrainList(trackTrain);
        return getDataTable(list);

    }

    /**
     * 关务查询班列运踪详情
     */
    @GetMapping("/getGwTrainList")
    public Map<String, Object> getGwTrainList(String cwh)
    {
        return trackTrainService.selectGwTrackTrainList(cwh);
    }
}
