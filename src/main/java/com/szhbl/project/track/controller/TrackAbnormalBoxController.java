package com.szhbl.project.track.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.file.FileUtils;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.track.domain.vo.AbnormalDay;
import com.szhbl.project.track.domain.vo.AbnormalMonth;
import com.szhbl.project.track.domain.vo.AbnormalYear;
import com.szhbl.project.track.domain.vo.TrackAbnormalBoxVo;
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
import com.szhbl.project.track.domain.TrackAbnormalBox;
import com.szhbl.project.track.service.ITrackAbnormalBoxService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 运踪_异常箱Controller
 *
 * @author lzd
 * @date 2020-03-30
 */
@RestController
@RequestMapping("/track/abnormalBox")
@Api(tags = "异常箱跟踪")
public class TrackAbnormalBoxController extends BaseController
{
    @Autowired
    private ITrackAbnormalBoxService trackAbnormalBoxService;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;


    /**
     * 获取业务，跟单，客户 需要抄送的领导、相关部门邮箱
     */
//    @PreAuthorize("@ss.hasPermi('track:abnormalBox:query')")
    @GetMapping("/getEmails")
    @ApiOperation("获取业务，跟单，客户 需要抄送的领导、相关部门邮箱")
    public AjaxResult getEmails(String orderId,String isBcc)
    {
        return AjaxResult.success(trackAbnormalBoxService.getEmails(orderId,isBcc));
    }

    /**
     * 上传文件
     */
//    @PreAuthorize("@ss.hasPermi('track:goStation:upload')")
    @Log(title = "异常箱上传文件", businessType = BusinessType.IMPORT)
    @ApiOperation("上传文件,返回文件存储地址")
    @PostMapping("/upload")
    public AjaxResult uploadAnnex(@RequestParam MultipartFile file)throws Exception {
        if(file.isEmpty()){
            return AjaxResult.error("上传文件为空，请重新上传！");
        }
        return AjaxResult.success(FileUtils.getFilePath(file));
    }

    /**
     * 新增发送
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormalBox:query')")
    @PostMapping("/addAndSend")
    @ApiOperation("新增发送")
    public AjaxResult addAndSend(@RequestBody TrackAbnormalBox trackAbnormalBox)
    {
        return toAjax(trackAbnormalBoxService.addAndSend(trackAbnormalBox));
    }

    /**
     * 编辑发送
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormalBox:query')")
    @PostMapping("/editAndSend")
    @ApiOperation("编辑发送")
    public AjaxResult editAndSend(@RequestBody TrackAbnormalBox trackAbnormalBox)
    {
        return toAjax(trackAbnormalBoxService.editAndSend(trackAbnormalBox));
    }


    /**
     * 查询订单表的异常箱
     */
//    @PreAuthorize("@ss.hasPermi('track:abnormalBox:list')")
    @GetMapping("/orderList")
    @ApiOperation("查询订单表的异常箱")
    public TableDataInfo orderList(TrackAbnormalBoxVo trackAbnormalBoxVo)
    {
        startPage();
        List<TrackAbnormalBoxVo> list = trackAbnormalBoxService.selectOrderAbnormalBoxList(trackAbnormalBoxVo);
        return getDataTable(list);
    }


    /**
     * 根据orderid和箱号获取异常箱信息
     */
//    @PreAuthorize("@ss.hasPermi('track:abnormalBox:list')")
    @GetMapping("/list")
    @ApiOperation("跟踪信息，根据orderid和箱号获取异常箱的多个信息，弹框内容")
    public TableDataInfo list(TrackAbnormalBox trackAbnormalBox)
    {
        startPage();
        List<TrackAbnormalBox> list = trackAbnormalBoxService.selectTrackAbnormalBoxList(trackAbnormalBox);
        return getDataTable(list);
    }

    /**
     * 拼箱编辑，根据箱号查询最新一条记录
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormal:query')")
    @GetMapping("/detail")
    @ApiOperation("拼箱编辑，根据箱号查询最新一条记录")
    public AjaxResult detail(TrackAbnormalBox trackAbnormalBox)
    {
        return AjaxResult.success(trackAbnormalBoxService.selectByBoxNumAndClassDate(trackAbnormalBox));
    }

    /**
     * 导出异常箱日报运踪
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormalBox:export')")
    @Log(title = "运踪_异常箱", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDay")
    @ApiOperation("导出异常箱日报运踪")
    public AjaxResult exportDay(TrackAbnormalBoxVo trackAbnormalBoxVo)
    {
        String goCome="";
        if("0".equals(trackAbnormalBoxVo.getClassEastAndWest())){
            goCome="去程";
        }else if("1".equals(trackAbnormalBoxVo.getClassEastAndWest())){
            goCome="回程";
        }
        String date= DateUtils.parseDateToStr("yyyy-MM-dd",new Date());
        List<AbnormalDay> list = trackAbnormalBoxService.selectDayAbnormalBox(trackAbnormalBoxVo);
        ExcelUtil<AbnormalDay> util = new ExcelUtil<AbnormalDay>(AbnormalDay.class);
        return util.exportExcel(list, goCome+"异常箱日报运踪"+date);
    }

    /**
     * 导出异常箱周/月报运踪
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormalBox:export')")
    @Log(title = "运踪_异常箱", businessType = BusinessType.EXPORT)
    @GetMapping("/exportMonth")
    @ApiOperation("导出异常箱周报月报运踪")
    public AjaxResult exportMonth(TrackAbnormalBoxVo trackAbnormalBoxVo)
    {
        String goCome="";
        if("0".equals(trackAbnormalBoxVo.getClassEastAndWest())){
            goCome="去程";
        }else if("1".equals(trackAbnormalBoxVo.getClassEastAndWest())){
            goCome="回程";
        }
        List<AbnormalDay> list = trackAbnormalBoxService.selectDayAbnormalBox(trackAbnormalBoxVo);
        List monthList=new ArrayList<AbnormalMonth>();
        AbnormalMonth abnormalMonth=null;
        for(int i=0;i<list.size();i++){
            abnormalMonth=new AbnormalMonth();
            BeanUtils.copyProperties(list.get(i),abnormalMonth);
            monthList.add(abnormalMonth);
        }
        ExcelUtil<AbnormalMonth> util = new ExcelUtil<AbnormalMonth>(AbnormalMonth.class);
        return util.exportExcel(monthList, goCome+"异常箱周报月报运踪");
    }

    /**
     * 导出异常箱年报运踪
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormalBox:export')")
    @Log(title = "运踪_异常箱", businessType = BusinessType.EXPORT)
    @GetMapping("/exportYear")
    @ApiOperation("导出异常箱年报运踪")
    public AjaxResult exportYear(TrackAbnormalBoxVo trackAbnormalBoxVo)
    {
        String goCome="";
        if("0".equals(trackAbnormalBoxVo.getClassEastAndWest())){
            goCome="去程";
        }else if("1".equals(trackAbnormalBoxVo.getClassEastAndWest())){
            goCome="回程";
        }
        List<AbnormalDay> list = trackAbnormalBoxService.selectDayAbnormalBox(trackAbnormalBoxVo);
        List yearList=new ArrayList<AbnormalYear>();
        AbnormalYear abnormalYear=null;
        for(int i=0;i<list.size();i++){
            abnormalYear=new AbnormalYear();
            BeanUtils.copyProperties(list.get(i),abnormalYear);
            yearList.add(abnormalYear);
        }
        ExcelUtil<AbnormalYear> util = new ExcelUtil<AbnormalYear>(AbnormalYear.class);
        return util.exportExcel(yearList, goCome+"异常箱年报运踪");
    }


    /**
     * 获取运踪_异常箱详细信息
     */
//    @PreAuthorize("@ss.hasPermi('track:abnormalBox:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("根据id查询运踪_异常箱单个详情，弹框内容")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(trackAbnormalBoxService.selectTrackAbnormalBoxById(id));
    }

    /**
     * 获取智能运踪
     */
//    @PreAuthorize("@ss.hasPermi('track:abnormalBox:query')")
    @GetMapping(value = "/intellectTracking")
    @ApiOperation("获取智能运踪，弹框按钮")
    public AjaxResult intellectTracking(String classNum)
    {
        return AjaxResult.success(trackAbnormalBoxService.selectAbnormalInformation(classNum));
    }

    /**
     * 新增运踪_异常箱
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormal:add')")
    @Log(title = "运踪_异常箱", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增运踪_异常箱列")
    public AjaxResult add(@RequestBody TrackAbnormalBox trackAbnormalBox)
    {
        return toAjax(trackAbnormalBoxService.insertTrackAbnormalBox(trackAbnormalBox));
    }

    /**
     * 修改运踪_异常箱
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormal:edit')")
    @Log(title = "运踪_异常箱", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改运踪_异常箱列")
    public AjaxResult edit(@RequestBody TrackAbnormalBox trackAbnormalBox)
    {
        return toAjax(trackAbnormalBoxService.updateTrackAbnormalBox(trackAbnormalBox));
    }

    /**
     * 删除运踪_异常箱
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abnormal:remove')")
    @Log(title = "运踪_异常箱", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除运踪_异常箱列")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(trackAbnormalBoxService.deleteTrackAbnormalBoxByIds(ids));
    }

    /**
     * 查询订舱数据
     */
//    @PreAuthorize("@ss.hasPermi('track:abnormalBox:query')")
    @GetMapping("/order")
    @ApiOperation("订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult order( String orderId)
    {
        return AjaxResult.success(busiShippingorderService.selectBusiShippingorderById(orderId));
    }
}
