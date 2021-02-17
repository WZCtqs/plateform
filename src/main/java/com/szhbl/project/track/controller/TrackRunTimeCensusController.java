package com.szhbl.project.track.controller;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

import com.szhbl.project.track.domain.cencus.*;
import com.szhbl.project.track.domain.vo.TrackRunTimeCensusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;

import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.track.domain.TrackRunTimeCensus;
import com.szhbl.project.track.service.ITrackRunTimeCensusService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;


/**
 * 运踪_班列运行时间统计Controller
 *
 * @author lzd
 * @date 2020-04-08
 */
@RestController
@RequestMapping("/track/census")
@Api(tags = "班列运行时间统计")
public class TrackRunTimeCensusController extends BaseController
{
    @Autowired
    private ITrackRunTimeCensusService trackRunTimeCensusService;

    /**
     * 查询运踪_班列运行时间统计列表
     */
  /*  @PreAuthorize("@ss.hasPermi('track:census:list')")
    @GetMapping("/list")
    public TableDataInfo list(TrackRunTimeCensus trackRunTimeCensus)
    {
        startPage();
        List<TrackRunTimeCensus> list = trackRunTimeCensusService.selectTrackRunTimeCensusList(trackRunTimeCensus);
        return getDataTable(list);
    }*/

    //导出时间统计表
    @PreAuthorize("@ss.hasPermi('yunFu:census:export')")
    @Log(title = "运踪_班列运行时间统计", businessType = BusinessType.EXPORT)
    @GetMapping("/exportCensus")
    @ApiOperation("运行分析")
    public AjaxResult exportCensus(TrackRunTimeCensus trackRunTimeCensus)
    {
        String goCome="";
        String block="";
        List<TrackRunTimeCensus> list = trackRunTimeCensusService.selectTrackRunTimeCensusList(trackRunTimeCensus);
        List censusList=new ArrayList<GoErenhotCensus>();
        CensusVo census=null;
        for(int i=0;i<list.size();i++){
            census=new CensusVo();
            BeanUtils.copyProperties(list.get(i),census);
            censusList.add(census);
        }
        ExcelUtil<CensusVo> censusUtil = new ExcelUtil<CensusVo>(CensusVo.class);
        if("0".equals(trackRunTimeCensus.getClassEastAndWest())){
           goCome="去程";
        }else if("1".equals(trackRunTimeCensus.getClassEastAndWest())){
            goCome="回程";
        }
        if(StringUtils.isNotEmpty(trackRunTimeCensus.getClassBlockTrain())){
            block=trackRunTimeCensus.getClassBlockTrain();
        }
        return censusUtil.exportExcel(censusList, goCome+block+"运行分析");
    }
    /**
     * 导出运踪_班列运行时间统计列表
     */
    @PreAuthorize("@ss.hasPermi('yunFu:census:export')")
    @Log(title = "运踪_班列运行时间统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出")
    public AjaxResult export(TrackRunTimeCensus trackRunTimeCensus)
    {
        List<TrackRunTimeCensus> list = trackRunTimeCensusService.selectTrackRunTimeCensusList(trackRunTimeCensus);
        if ("0".equals(trackRunTimeCensus.getClassEastAndWest())&&("郑欧班列-山口".equals(trackRunTimeCensus.getClassBlockTrain())||"郑欧班列-山口".equals(trackRunTimeCensus.getClassBlockTrain()))) {
            ExcelUtil<TrackRunTimeCensus> util = new ExcelUtil<TrackRunTimeCensus>(TrackRunTimeCensus.class);
            return util.exportExcel(list, "去程郑欧班列-山口运行统计时间");
        }else if("0".equals(trackRunTimeCensus.getClassEastAndWest())&&"郑欧班列-二连".equals(trackRunTimeCensus.getClassBlockTrain())){
            List erenhotList=new ArrayList<GoErenhotCensus>();
            GoErenhotCensus erenhot=null;
            for(int i=0;i<list.size();i++){
                erenhot=new GoErenhotCensus();
                BeanUtils.copyProperties(list.get(i),erenhot);
                erenhotList.add(erenhot);
            }
            ExcelUtil<GoErenhotCensus> erenhotUtil = new ExcelUtil<GoErenhotCensus>(GoErenhotCensus.class);
            return erenhotUtil.exportExcel(erenhotList, "去程郑欧班列-二连运行统计时间");
        }else if("0".equals(trackRunTimeCensus.getClassEastAndWest())&&"郑欧班列-绥芬河".equals(trackRunTimeCensus.getClassBlockTrain())){
            List suifenheList=new ArrayList<GoSuifenheCensus>();
            GoSuifenheCensus suifenhe=null;
            for(int i=0;i<list.size();i++){
                suifenhe=new GoSuifenheCensus();
                BeanUtils.copyProperties(list.get(i),suifenhe);
                suifenheList.add(suifenhe);
            }
            ExcelUtil<GoSuifenheCensus> suifenheUtil = new ExcelUtil<GoSuifenheCensus>(GoSuifenheCensus.class);
            return suifenheUtil.exportExcel(suifenheList, "去程郑欧班列-绥芬河运行统计时间");
        }else if("0".equals(trackRunTimeCensus.getClassEastAndWest())&&"郑欧班列-霍尔果斯".equals(trackRunTimeCensus.getClassBlockTrain())){
            List khorgosList=new ArrayList<GoKhorgosCensus>();
            GoKhorgosCensus khorgos=null;
            for(int i=0;i<list.size();i++){
                khorgos=new GoKhorgosCensus();
                BeanUtils.copyProperties(list.get(i),khorgos);
                khorgosList.add(khorgos);
            }
            ExcelUtil<GoKhorgosCensus> khorgosUtil = new ExcelUtil<GoKhorgosCensus>(GoKhorgosCensus.class);
            return khorgosUtil.exportExcel(khorgosList, "去程郑欧班列-霍尔果斯运行统计时间");
        }else if("0".equals(trackRunTimeCensus.getClassEastAndWest())&&"中亚班列".equals(trackRunTimeCensus.getClassBlockTrain())){
            List asiaList=new ArrayList<GoAsiaCensus>();
            GoAsiaCensus asia=null;
            for(int i=0;i<list.size();i++){
                asia=new GoAsiaCensus();
                BeanUtils.copyProperties(list.get(i),asia);
                asiaList.add(asia);
            }
            ExcelUtil<GoAsiaCensus> asiaUtil = new ExcelUtil<GoAsiaCensus>(GoAsiaCensus.class);
            return asiaUtil.exportExcel(asiaList, "去程中亚班列运行统计时间");
        }else if("0".equals(trackRunTimeCensus.getClassEastAndWest())&&"越南".equals(trackRunTimeCensus.getClassBlockTrain())){
            List vietnamList=new ArrayList<GoVietnamCensus>();
            GoVietnamCensus vietnam=null;
            for(int i=0;i<list.size();i++){
                vietnam=new GoVietnamCensus();
                BeanUtils.copyProperties(list.get(i),vietnam);
                vietnamList.add(vietnam);
            }
            ExcelUtil<GoVietnamCensus> vietnamUtil = new ExcelUtil<GoVietnamCensus>(GoVietnamCensus.class);
            return vietnamUtil.exportExcel(vietnamList, "去程越南班列运行统计时间");
        }else if("1".equals(trackRunTimeCensus.getClassEastAndWest())&&("郑欧班列-山口".equals(trackRunTimeCensus.getClassBlockTrain())||"郑欧班列-山口".equals(trackRunTimeCensus.getClassBlockTrain()))){
            List comeShankouList=new ArrayList<ComeShankouCensus>();
            ComeShankouCensus comeShankou=null;
            for(int i=0;i<list.size();i++){
                comeShankou=new ComeShankouCensus();
                BeanUtils.copyProperties(list.get(i),comeShankou);
                comeShankouList.add(comeShankou);
            }
            ExcelUtil<ComeShankouCensus> comeShankouUtil = new ExcelUtil<ComeShankouCensus>(ComeShankouCensus.class);
            return comeShankouUtil.exportExcel(comeShankouList, "回程郑欧班列-山口运行统计时间");
        }else if("1".equals(trackRunTimeCensus.getClassEastAndWest())&&"郑欧班列-二连".equals(trackRunTimeCensus.getClassBlockTrain())){
            List comeErenhotList=new ArrayList<ComeErenhotCensus>();
            ComeErenhotCensus comeErenhot=null;
            for(int i=0;i<list.size();i++){
                comeErenhot=new ComeErenhotCensus();
                BeanUtils.copyProperties(list.get(i),comeErenhot);
                comeErenhotList.add(comeErenhot);
            }
            ExcelUtil<ComeErenhotCensus> comeErenhotUtil = new ExcelUtil<ComeErenhotCensus>(ComeErenhotCensus.class);
            return comeErenhotUtil.exportExcel(comeErenhotList, "回程郑欧班列-二连运行统计时间");
        }else if("1".equals(trackRunTimeCensus.getClassEastAndWest())&&"郑欧班列-绥芬河".equals(trackRunTimeCensus.getClassBlockTrain())){
            List comeSuifenheList=new ArrayList<ComeSuifenheCensus>();
            ComeSuifenheCensus comeSuifenhe=null;
            for(int i=0;i<list.size();i++){
                comeSuifenhe=new ComeSuifenheCensus();
                BeanUtils.copyProperties(list.get(i),comeSuifenhe);
                comeSuifenheList.add(comeSuifenhe);
            }
            ExcelUtil<ComeSuifenheCensus> suifenheUtil = new ExcelUtil<ComeSuifenheCensus>(ComeSuifenheCensus.class);
            return suifenheUtil.exportExcel(comeSuifenheList, "回程郑欧班列-绥芬河运行统计时间");
        }else{
            return AjaxResult.error("请选择东西向和开行班列");
        }
    }

    /**
     * 从班列表查询运行统计时间
     */
//    @PreAuthorize("@ss.hasPermi('track:census:list')")
    @GetMapping("/list")
    @ApiOperation("从班列表查询运行统计时间")
    public TableDataInfo list(TrackRunTimeCensusVo trtcVo)
    {
        startPage();
        List<TrackRunTimeCensusVo> list = trackRunTimeCensusService.selectTrtcVoList(trtcVo);
        return getDataTable(list);
    }

    /**
     * 获取运踪_班列运行时间统计详细信息
     */
   /* @PreAuthorize("@ss.hasPermi('track:census:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(trackRunTimeCensusService.selectTrackRunTimeCensusById(id));
    }*/
    /**
     * 获取运踪_班列运行时间统计详细信息
     */
//    @PreAuthorize("@ss.hasPermi('track:census:query')")
    @GetMapping(value = "/details")
    @ApiOperation("查询运行统计时间详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列id",name = "classId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "班列开行时间",name = "classSTime",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "目的站英文名",name = "endNameEn",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "目的站中文名",name = "classStationofdestinationName",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "去回程",name = "classEastAndWest",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "开行班列",name = "classBlockTrain",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "是否编辑",name = "isEdit",paramType = "query",dataType = "Integer",required = true)
    })
    public AjaxResult getInfo(TrackRunTimeCensusVo trtcVo)
    {
        return AjaxResult.success(trackRunTimeCensusService.selectTrtc(trtcVo));
    }

    /**
     * 新增运踪_班列运行时间统计
     */
    @PreAuthorize("@ss.hasPermi('yunFu:census:add')")
    @Log(title = "运踪_班列运行时间统计", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增运踪")
    public AjaxResult add(@RequestBody TrackRunTimeCensus trackRunTimeCensus)
    {
        return toAjax(trackRunTimeCensusService.insertTrackRunTimeCensus(trackRunTimeCensus));
    }

    /**
     * 修改运踪_班列运行时间统计
     */
    @PreAuthorize("@ss.hasPermi('yunFu:census:edit')")
    @Log(title = "运踪_班列运行时间统计", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改运踪")
    public AjaxResult edit(@RequestBody TrackRunTimeCensus trackRunTimeCensus)
    {
        return toAjax(trackRunTimeCensusService.updateTrackRunTimeCensus(trackRunTimeCensus));
    }

    /**
     * 删除运踪_班列运行时间统计
     */
    @PreAuthorize("@ss.hasPermi('yunFu:census:remove')")
    @Log(title = "运踪_班列运行时间统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除运踪")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(trackRunTimeCensusService.deleteTrackRunTimeCensusByIds(ids));
    }
}
