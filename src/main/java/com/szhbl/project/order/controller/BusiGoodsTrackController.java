package com.szhbl.project.order.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.file.FileUtils;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.track.*;
import com.szhbl.project.order.mapper.BusiGoodsTrackMapper;
import com.szhbl.project.order.mapper.BusiZyInfoMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.order.service.IBusiGoodsTrackService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 运踪_货物状态Controller
 *
 * @author dps
 * @date 2020-04-09
 */
@Slf4j
@RestController
@RequestMapping("/order/orderGoodsStatus")
@Api(tags = "进站跟踪")
public class BusiGoodsTrackController extends BaseController
{
    @Autowired
    private IBusiGoodsTrackService busiGoodsTrackService;

    @Autowired
    private BusiGoodsTrackMapper busiGoodsTrackMapper;

    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    @Autowired
    private BusiZyInfoMapper busiZyInfoMapper;

    @Autowired
    private CommonService commonService;
    /**
     * 订舱组进站查看—货物状态列表
     */
//    @PreAuthorize("@ss.hasPermi('order:orderGoodsStatus:list')")
    @GetMapping("/trackList")
    @ApiOperation("进站查看列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(BusiGoodsTrack busiGoodsTrack)
    {
        startPage();
        if(StringUtils.isNotEmpty(busiGoodsTrack.getOrderNumber())){
            busiGoodsTrack.setOrderNumber((busiGoodsTrack.getOrderNumber()).trim());
        }
        List<BusiGoodsTrack> list = busiGoodsTrackService.selectBusiGoodsTrackList(busiGoodsTrack);
        //系统有入场时间没有进站时间，就是已进场状态。如果有进站时间，就是已进站状态
        for(BusiGoodsTrack trackItem:list){
            //已入场/已进站
            Date inSpaceDate = trackItem.getInSpaceDate(); //入场日期
            Date inStationDate = trackItem.getInStationDate(); //进站日期
            if(StringUtils.isNull(inSpaceDate) && StringUtils.isNull(inStationDate)){
                trackItem.setTrackState("未入堆场");
            }
            if(StringUtils.isNotNull(inSpaceDate) && StringUtils.isNull(inStationDate)){
                trackItem.setTrackState("已入堆场");
            }
            if(StringUtils.isNotNull(inSpaceDate) && StringUtils.isNotNull(inStationDate)){
                trackItem.setTrackState("已进站");
            }
            //箱号校验
            Boolean result = commonService.checkDigit(trackItem.getBoxNum());
            if(result){
                trackItem.setBoxNumCheck("箱号正确");
            }else{
                trackItem.setBoxNumCheck("箱号错误");
            }
            //推荐人
            String clientTjr = trackItem.getClientTjr();
            if(StringUtils.isNotEmpty(clientTjr)){
                if(clientTjr.contains("/")){
                    clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                }
                trackItem.setClientTjr(clientTjr);
            }
            //箱型
            String containerTypeT = trackItem.getContainerType();
            if(StringUtils.isNotEmpty(containerTypeT)){
                containerTypeT = commonService.containerTypeName(containerTypeT);
                if(StringUtils.isNotEmpty(containerTypeT)){
                    trackItem.setContainerType(containerTypeT);
                }
            }
            //箱量
            if("1".equals(trackItem.getIsconsolidation())){
                trackItem.setContainerBoxamount("0");
            }
            String classEastandwest = trackItem.getClassEastandwest(); //0去程 1回程
            //关务状态
            String guanwustate = trackItem.getGuanwustate();
            List<Map> guanwustateList = new ArrayList<>();
            if(StringUtils.isNotEmpty(guanwustate)){
                if("0".equals(classEastandwest)){
                    guanwustate = getStatusWest(guanwustate);
                }
                if("1".equals(classEastandwest)){
                    guanwustate = getStatusEast(guanwustate);
                }
                if(StringUtils.isNotEmpty(guanwustate)){
                    String[] gwTxt = guanwustate.split("/");
                    if(gwTxt.length>0){
                        for(int p=0;p<gwTxt.length;p++){
                            Map gwmap = new HashMap();
                            String gwColor = getGwColor(gwTxt[p]);
                            gwmap.put("gwStatus",gwTxt[p]);
                            gwmap.put("gwColor",gwColor);
                            guanwustateList.add(gwmap);
                        }
                    }
                }
                trackItem.setGuanwustate(guanwustate);
                trackItem.setGuanwustateList(guanwustateList);
            }
            //托书报关状态
            String bgProgress = trackItem.getBgProgress();
            List<Map> bgProgressList = new ArrayList<>();
            if(StringUtils.isNotEmpty(bgProgress)){
                if("0".equals(classEastandwest)){
                    bgProgress = getStatusWest(bgProgress);
                }
                if("1".equals(classEastandwest)){
                    bgProgress = getStatusEast(bgProgress);
                }
                if(StringUtils.isNotEmpty(bgProgress)){
                    String[] bgTxt = bgProgress.split("/");
                    if(bgTxt.length>0){
                        for(int q=0;q<bgTxt.length;q++){
                            Map bgmap = new HashMap();
                            String bgColor = getGwColor(bgTxt[q]);
                            bgmap.put("bgStatus",bgTxt[q]);
                            bgmap.put("bgColor",bgColor);
                            bgProgressList.add(bgmap);
                        }
                    }
                }
                trackItem.setBgProgress(bgProgress);
                trackItem.setBgProgressList(bgProgressList);
            }
        }
        return getDataTable(list);
    }

    /**
     * 订舱组进站查看—箱量统计
     */
//    @PreAuthorize("@ss.hasPermi('order:orderGoodsStatus:query')")
    @GetMapping("/boxAmountTotal")
    @ApiOperation("箱量统计")
    public AjaxResult boxAmountTotal(BusiGoodsTrack busiGoodsTrack){
        Map boxAmount = new HashMap();
        if(StringUtils.isEmpty(busiGoodsTrack.getOrderNumber()) && StringUtils.isEmpty(busiGoodsTrack.getBoxNum()) && StringUtils.isEmpty(busiGoodsTrack.getClassBh())
        && StringUtils.isEmpty(busiGoodsTrack.getLineTypeid()) && StringUtils.isEmpty(busiGoodsTrack.getClassEastandwest()) && StringUtils.isEmpty(busiGoodsTrack.getIsconsolidation())
        && StringUtils.isEmpty(busiGoodsTrack.getIsRun()) && StringUtils.isNull(busiGoodsTrack.getClassDateStart()) && StringUtils.isNull(busiGoodsTrack.getClassDateEnd())
        && StringUtils.isNull(busiGoodsTrack.getActualClassDateValueStart()) && StringUtils.isNull(busiGoodsTrack.getActualClassDateValueEnd()) && StringUtils.isEmpty(busiGoodsTrack.getLieshu())
        && StringUtils.isEmpty(busiGoodsTrack.getIsexamline()) && StringUtils.isEmpty(busiGoodsTrack.getXgCheck()) && StringUtils.isEmpty(busiGoodsTrack.getRoadCheck())
        && StringUtils.isEmpty(busiGoodsTrack.getGdCheck()) && StringUtils.isEmpty(busiGoodsTrack.getClasszyNo())){
            boxAmount.put("20t","0");
            boxAmount.put("40t","0");
            boxAmount.put("45t","0");
            boxAmount.put("xl","0");
        }else{
            String isconsolidation = busiGoodsTrack.getIsconsolidation();  //0整柜 1拼箱
            StringBuffer t = new StringBuffer();
            StringBuffer f = new StringBuffer();
            StringBuffer ff = new StringBuffer();
            List<Map> maps = busiGoodsTrackService.selectAmount(busiGoodsTrack);
            maps.forEach(map -> {
                if (map.get("boxType").equals("20")) {
                    t.append(map.get("boxAmountSecond"));
                } else if (map.get("boxType").equals("40")) {
                    f.append(map.get("boxAmountSecond"));
                } else if (map.get("boxType").equals("45")) {
                    ff.append(map.get("boxAmountSecond"));
                }
            });
            boxAmount.put("20t",t.length() == 0? "0" : t);
            boxAmount.put("40t",f.length() == 0? "0" : f);
            boxAmount.put("45t",ff.length() == 0? "0" : ff);
            Double z1 = t.length()==0?0:Double.valueOf(t.toString())/2;
            Double z2 = f.length()==0?0:Double.valueOf(f.toString());
            Double z3 = ff.length()==0?0:Double.valueOf(ff.toString());
            Double xl = z1+z2+z3;
            boxAmount.put("xl",xl);
            //BusiGoodsTrack amountVW = busiGoodsTrackService.selectAmountVW(busiGoodsTrack);
        }
        return AjaxResult.success(boxAmount);
    }

    /**
     * 订舱组进站查看———发运时间编辑
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:edit')")
    @Log(title = "运踪_货物状态", businessType = BusinessType.UPDATE)
    @PutMapping("/runTimeAdd")
    @ApiOperation("发运时间编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "记录id(数组)",name = "idsArr",paramType = "query",dataType = "Long",required = true),
            @ApiImplicitParam(value = "发运时间",name = "actualClassDateValue",paramType = "query",dataType = "Date",required = true),
            @ApiImplicitParam(value = "是否加开 非P'' 是P",name = "isClassAdd",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "申请代码列数",name = "lieshu",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "修改人",name = "updateBy",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult runTimeAdd(@RequestBody BusiGoodsTrack busiGoodsTrack)
    {
        Long[] idsArr = busiGoodsTrack.getIdsArr();
        if(idsArr.length>0){
            for(int i=0;i<idsArr.length;i++){
                Long id = idsArr[i];
                BusiGoodsTrack trackInfo = busiGoodsTrackService.selectBusiGoodsTrackById(id.intValue());  //记录详情
                if(StringUtils.isNotNull(trackInfo)){
                    //线路类型：0中欧 2中亚 3中越 4中俄 ,0为去程 1为回程
                    String lineTypeid = trackInfo.getLineTypeid();
                    String classEastandwest = trackInfo.getClassEastandwest();
                    if(!(classEastandwest.equals("0") && lineTypeid.equals("2"))){
                        return AjaxResult.error("仅中亚去程托书可编辑");
                    }
                }
            }
            int result = busiGoodsTrackService.updateBusiGoodsTrack(busiGoodsTrack);
            if(result == 2){
                return AjaxResult.error("未查询到班列");
            }
            return toAjax(result);
        }else{
            return AjaxResult.error("请选择托书记录");
        }
    }

    /**
     * 订舱组进站查看—申请代码编辑
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:applyCode')")
    @Log(title = "申请代码编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/applyCode")
    @ApiOperation("申请代码编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "记录id",name = "id",paramType = "query",dataType = "Long",required = true),
            @ApiImplicitParam(value = "是否申请代码 0否 1是",name = "isApplyCode",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "修改人",name = "updateBy",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult applyCode(@RequestBody BusiGoodsTrack busiGoodsTrack)
    {
        BusiGoodsTrack trackInfo = busiGoodsTrackService.selectBusiGoodsTrackById(busiGoodsTrack.getId());  //记录详情
        if(StringUtils.isNotNull(trackInfo)){
            //线路类型：0中欧 2中亚 3中越 4中俄 ,0为去程 1为回程
            String lineTypeid = trackInfo.getLineTypeid();
            String classEastandwest = trackInfo.getClassEastandwest();
            if(lineTypeid.equals("2") && classEastandwest.equals("0")){ //中亚去程
                return toAjax(busiGoodsTrackService.updateApplyCode(busiGoodsTrack));
            }else{
                return AjaxResult.error("中亚去程托书可编辑");
            }
        }else{
            return AjaxResult.error("记录信息不存在");
        }
    }

    /**
     * 订舱组进站查看—模板下载
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:export0')")
    @GetMapping("/download")
    @ApiOperation("模板下载")
    public void downloadFile(HttpServletRequest request,
                               HttpServletResponse response) throws IOException{
        String name = "订舱组发运表.xlsx";
        ServletOutputStream out = null;
        try {
            InputStream fis = FileUtils.getResourcesFileInputStream("templates/"+name);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            response.setContentType("application/binary;charset=ISO8859-1");
            String fileName = java.net.URLEncoder.encode(name, "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            log.error("进站跟踪模板下载失败",e.toString(),e.getStackTrace());
        } finally {
            //关闭文件输出流
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                System.gc();
            } catch (Exception e) {
                log.error("进站跟踪模板下载失败",e.toString(),e.getStackTrace());
            }
        }
    }

    /**
     * 订舱组进站查看—导出进站跟踪列表
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:export1')")
    @Log(title = "导出进站跟踪列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportTrack")
    @ApiOperation("导出列表")
    public AjaxResult exportTrack(ExportTrack busiGoodsTrack)
    {
        List<ExportTrack> list = busiGoodsTrackService.selectExportTrackList(busiGoodsTrack);
        String strtime = "yyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdftime = new SimpleDateFormat(strtime);
        for(ExportTrack trackItem:list){
            //是否申请代码 0否 1是
            String isApplyCode = trackItem.getIsApplyCode();
            if(StringUtils.isNotEmpty(isApplyCode)){
                if(isApplyCode.equals("0")){
                    trackItem.setIsApplyCode("否");
                }
                if(isApplyCode.equals("1")){
                    trackItem.setIsApplyCode("是");
                }
            }
            //箱号校验
            if(StringUtils.isNotEmpty(trackItem.getBoxNum())){
                Boolean result = commonService.checkDigit(trackItem.getBoxNum());
                if(result){
                    trackItem.setBoxNumCheck("箱号正确");
                }else{
                    trackItem.setBoxNumCheck("箱号错误");
                }
            }
            String isconsolidation = trackItem.getIsconsolidation(); //0整柜 1拼箱
            if(StringUtils.isNotEmpty(isconsolidation)){
                //箱重
                if(isconsolidation.equals("0")){ //整柜
                    String containerType = trackItem.getContainerType();
                    Double goodskgs = 0.0;
                    if(StringUtils.isNotEmpty(trackItem.getGoodsKgs())){
                        goodskgs = Double.valueOf(trackItem.getGoodsKgs());
                    }
                    int boxamount = Integer.valueOf(trackItem.getContainerBoxamount());
                    if(StringUtils.isNotEmpty(containerType)){
                        int boxweigth = 0;
                        if(containerType.contains("40")){
                            int[] boxarr = {3700,3750,3820,3850,3900};
                            boxweigth = boxWeight(boxarr);
                        }
                        if(containerType.contains("45")){
                            boxweigth = 4800;
                        }
                        if(containerType.contains("20")){
                            int[] boxarr = {2100,2230,2500};
                            boxweigth = boxWeight(boxarr);
                        }
                        if(boxamount!=0){
                            String totalWeight = String.valueOf((int)Math.ceil(goodskgs/boxamount));
                            trackItem.setGoodsKgs(totalWeight);
                            trackItem.setBoxWeight(String.valueOf((int)Math.ceil(goodskgs/boxamount)+boxweigth));
                        }
                        trackItem.setContainerType(containerType);
                    }
                }
                if(isconsolidation.equals("1")){ //拼箱
                    trackItem.setGoodsKgs(trackItem.getGoodsKgs());
                    trackItem.setContainerType(trackItem.getBoxType());
                }
            }
            //箱型
            String containerTypeT = trackItem.getContainerType();
            if(StringUtils.isNotEmpty(containerTypeT)){
                containerTypeT = commonService.containerTypeName(containerTypeT);
                if(StringUtils.isNotEmpty(containerTypeT)){
                    trackItem.setContainerType(containerTypeT);
                }
            }
            //箱量
            if("1".equals(trackItem.getIsconsolidation())){
                trackItem.setContainerBoxamount("0");
            }
            //送货方式
            String shipOrderBinningway = trackItem.getShipOrderBinningway(); //0委托ZIH提货 1自送货  2铁路到货
            if(StringUtils.isNotEmpty(shipOrderBinningway)){
                String binningway = shipOrderBinningway.equals("0")?"委托ZIH提货":shipOrderBinningway.equals("1")?"自送货":"铁路到货";
                trackItem.setShipOrderBinningway(binningway);
            }
            //发送邮件
            String xgsend = trackItem.getFsyj();
            if(StringUtils.isNotEmpty(xgsend)){
                if(xgsend.equals("0")){
                    trackItem.setFsyj("未发送");
                }
                if(xgsend.equals("1")){
                    trackItem.setFsyj("已发送");
                }
            }
            //是否加固
            String isStable = trackItem.getIsStable();
            if(StringUtils.isNotEmpty(isStable)){
                if(isStable.equals("0")){
                    trackItem.setIsStable("否");
                }
                if(isStable.equals("1")){
                    trackItem.setIsStable("是");
                }
            }
            //是否偏载
            String isBalance = trackItem.getIsBalance();
            if(StringUtils.isNotEmpty(isBalance)){
                if(isBalance.equals("0")){
                    trackItem.setIsBalance("否");
                }
                if(isBalance.equals("1")){
                    trackItem.setIsBalance("是");
                }
            }
            String eastandwest = trackItem.getClassEastandwest(); //0西向 1东向
            if(StringUtils.isNotEmpty(eastandwest)){
                //跟单、税号
                if(eastandwest.equals("0")){
                    trackItem.setOrderMerchandiser(trackItem.getOrderMerchandiserW());
                    trackItem.setGoodsReport(trackItem.getGoodsInReport());
                }
                if(eastandwest.equals("1")){
                    trackItem.setOrderMerchandiser(trackItem.getOrderMerchandiser());
                    trackItem.setGoodsReport(trackItem.getGoodsReport());
                }
            }
            String classEastandwest = trackItem.getClassEastandwest(); //0去程 1回程
            //关务状态
            String guanwustate = trackItem.getGuanwustate();
            if(StringUtils.isNotEmpty(guanwustate)){
                if("0".equals(classEastandwest)){
                    guanwustate = getStatusWest(guanwustate);
                }
                if("1".equals(classEastandwest)){
                    guanwustate = getStatusEast(guanwustate);
                }
                trackItem.setGuanwustate(guanwustate);
            }
            //托书报关状态
            String bgProgress = trackItem.getBgProgress();
            if(StringUtils.isNotEmpty(bgProgress)){
                if("0".equals(classEastandwest)){
                    bgProgress = getStatusWest(bgProgress);
                }
                if("1".equals(classEastandwest)){
                    bgProgress = getStatusEast(bgProgress);
                }
                trackItem.setBgProgress(bgProgress);
            }
            //跟单状态
            String gdbshzt = trackItem.getGdbshzt();
            if(StringUtils.isNotEmpty(gdbshzt)){
                if("0".equals(gdbshzt)){
                    trackItem.setGdbshzt("是");
                }
                if("1".equals(gdbshzt)){
                    trackItem.setGdbshzt("否");
                }
            }
            //推荐人
            String clientTjr = trackItem.getClientTjr();
            if(StringUtils.isNotEmpty(clientTjr)){
                if(clientTjr.contains("/")){
                    clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                }
                if(clientTjr.contains("-")){
                    clientTjr = clientTjr.substring(clientTjr.lastIndexOf("-")+1);
                }
                trackItem.setClientTjr(clientTjr);
            }
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(date);
        ExcelUtil<ExportTrack> util = new ExcelUtil<ExportTrack>(ExportTrack.class);
        return util.exportExcel(list, "订舱组发运表"+time);
    }

    /**
     * 订舱组进站查看—导出进站跟踪列表(多联)
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:export2')")
    @Log(title = "导出进站跟踪列表(多联)", businessType = BusinessType.EXPORT)
    @GetMapping("/exportTrackDl")
    @ApiOperation("导出多联列表")
    public AjaxResult exportTrackDl(ExportTrackDl busiGoodsTrack)
    {
        List<ExportTrackDl> list = busiGoodsTrackService.selectExportTrackDlList(busiGoodsTrack);
        int i = 1;
        for(ExportTrackDl trackItem:list){
            //序号
            trackItem.setSort(i);
            //箱号校验
            if(StringUtils.isNotEmpty(trackItem.getBoxNum())){
                Boolean result = commonService.checkDigit(trackItem.getBoxNum());
                if(result){
                    trackItem.setBoxNumCheck("箱号正确");
                }else{
                    trackItem.setBoxNumCheck("箱号错误");
                }
            }
            //出口/过境
            String classEastandwest = trackItem.getClassEastandwest();
            if(StringUtils.isNotEmpty(classEastandwest)){
                if(classEastandwest.equals("0")){
                    trackItem.setIsInOut("出口");
                }
                if(classEastandwest.equals("1")){
                    trackItem.setIsInOut("进口");
                }
            }
            trackItem.setShipOrederAddress(trackItem.getShipOrederAddress()+"/"+trackItem.getShipOrederPhone());
            trackItem.setReceiveOrderAddress(trackItem.getReceiveOrderAddress()+"/"+trackItem.getReceiveOrderPhone());
            String isconsolidation = trackItem.getIsconsolidation(); //0整柜 1拼箱
            if(StringUtils.isNotEmpty(isconsolidation)){
                if(isconsolidation.equals("0")){  //整柜
                    String containerType = trackItem.getContainerType();
                    Double goodskgs = 0.0;
                    if(StringUtils.isNotEmpty(trackItem.getGoodsKgs())){
                        goodskgs = Double.valueOf(trackItem.getGoodsKgs());
                    }
                    int boxamount = Integer.valueOf(trackItem.getContainerBoxamount());
                    if(StringUtils.isNotEmpty(containerType)){
                        int boxweigth = 0;
                        if(containerType.contains("40")){
                            trackItem.setCarNum("1");
                            int[] boxarr = {3700,3750,3820,3850,3900};
                            boxweigth = boxWeight(boxarr);
                        }
                        if(containerType.contains("45")){
                            trackItem.setCarNum("1");
                            boxweigth = 4800;
                        }
                        if(containerType.contains("20")){
                            trackItem.setCarNum("0.5");
                            int[] boxarr = {2100,2230,2500};
                            boxweigth = boxWeight(boxarr);
                        }
                        if(boxamount!=0){
                            String totalWeight = (int)Math.ceil(goodskgs/boxamount)+"+"+boxweigth;
                            trackItem.setTotalWeight(totalWeight);
                        }
                    }
                    trackItem.setContainerType(containerType);
                }
                if(isconsolidation.equals("1")){ //拼箱
                    String boxtype = trackItem.getBoxType();
                    if(StringUtils.isNotEmpty(boxtype)){
                        if(boxtype.contains("4")){
                            trackItem.setCarNum("1");
                        }
                        if(boxtype.contains("2")){
                            trackItem.setCarNum("0.5");
                        }
                    }
                    trackItem.setTotalWeight(trackItem.getGoodsKgs());
                    trackItem.setContainerType(boxtype);
                }
            }
            //箱型
            String containerTypeT = trackItem.getContainerType();
            if(StringUtils.isNotEmpty(containerTypeT)){
                containerTypeT = commonService.containerTypeName(containerTypeT);
                trackItem.setContainerType(containerTypeT);
            }
            i++;
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(date);
        ExcelUtil<ExportTrackDl> util = new ExcelUtil<ExportTrackDl>(ExportTrackDl.class);
        return util.exportExcel(list, "多联导出表"+time);
    }

    /**
     * 订舱组进站查看—发运时间导入
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:upload')")
    @Log(title = "发运时间导入", businessType = BusinessType.EXPORT)
    @PostMapping("/importTrackTime")
    @ApiOperation("发运时间导入")
    @Transactional
    public AjaxResult importTrack(@RequestParam MultipartFile file) throws Exception{
        ExcelUtil<ImportTrackTime> util = new ExcelUtil<>(ImportTrackTime.class);
        List<ImportTrackTime> list = util.importExcel(file.getInputStream());
        for(ImportTrackTime trackItem:list){
            ImportTrackTime trackinfo = busiGoodsTrackMapper.selectBusiGoodsTrackByIdImport(trackItem.getOrderNumber());
            if(StringUtils.isNotNull(trackinfo)){
                String orderId = trackinfo.getOrderId(); //托书id
                //判断中亚去程托书
                trackinfo.setIsClassAdd(trackItem.getIsClassAdd());
                //线路类型：0中欧 2中亚 3中越 4中俄 ,0为去程 1为回程
                String lineTypeid = trackinfo.getLineTypeid();
                String classEastandwest = trackinfo.getClassEastandwest();
                if("2".equals(lineTypeid) && "0".equals(classEastandwest)){
                    //更新货物状态表
                    ImportTrackTime track = new ImportTrackTime();
                    String orderNumber = trackItem.getOrderNumber(); //托书编号
                    String boxNum = trackItem.getBoxNum(); //箱号
                    Date actualClassDateValue = trackItem.getActualClassDateValue(); //发运日期
                    String isClassAdd = trackItem.getIsClassAdd(); //是否加开（实际班列号）
                    String lieshu = trackItem.getLieshu(); //申请代码列数
                    String classzyNo = trackItem.getClasszyNo(); //计划班列号
                    //实际班列id
                    if(StringUtils.isNotNull(actualClassDateValue)){
                        //实际班列id
                        ImportTrackTime actualClasses = busiGoodsTrackMapper.selectActualClassesIdImport(trackinfo);
                        if(StringUtils.isNotNull(actualClasses)){
                            track.setActualClassId(actualClasses.getActualClassId());
                            //获取发运日期拼接是否加开的字符串
                            String mon=String .format("%tm", actualClassDateValue); //月份
                            String day=String .format("%td", actualClassDateValue); //日
                            String actualClassDate = mon+'.'+day+isClassAdd;
                            track.setActualClassDate(actualClassDate);
                            track.setActualClassDateValue(actualClassDateValue);
                            track.setIsRun("1");//发运状态 0未发运 1已发运
                        }
                    }
                    track.setOrderId(orderId);
                    track.setBoxNum(boxNum);
                    track.setOrderNumber(orderNumber);
                    track.setUpdateTime(DateUtils.getNowDate());
                    int resultGoods = busiGoodsTrackMapper.updateTrackByOrder(track);
                    if(resultGoods==0){
                        return AjaxResult.error("导入失败,请检查数据格式");
                    }
                    //更新中亚表
                    BusiZyInfo zyinfo = busiZyInfoMapper.selectZyInfoByOrder(orderId,boxNum);
                    if(StringUtils.isNotNull(zyinfo)){
                        BusiZyInfo zyupd = new BusiZyInfo();
                        String classzyNoBack = zyinfo.getClasszyNo();
                        if(StringUtils.isNotEmpty(classzyNoBack)){
                            if(!StringUtils.equals(classzyNo,classzyNoBack)){
                                String record = "";
                                record = "<th>修改时间："+DateUtils.parseStr(DateUtils.getNowDate())+"</th>";
                                record = record + "班列号由" +classzyNoBack+ "改为：" +classzyNo+ "<###>";
                                zyupd.setClasszynoRemark(record);
                            }
                        }
                        zyupd.setClasszyNo(classzyNo);
                        if(StringUtils.isEmpty(classzyNo)){
                            zyupd.setClasszyNoS("1");
                        }
                        if(StringUtils.isNotEmpty(classzyNo)){
                            zyupd.setClasszynoTime(DateUtils.getNowDate());
                        }
                        zyupd.setCreatedate(DateUtils.getNowDate());
                        zyupd.setOrderId(zyinfo.getOrderId());
                        zyupd.setXianghao(zyinfo.getXianghao());
                        zyupd.setLieshu(lieshu);
                        int resultZy = busiZyInfoMapper.updateBusiZyInfoByTrackOrder(zyupd);
                        if(resultZy==0){
                            return AjaxResult.error("导入失败,请检查数据格式");
                        }
                    }
                    //推送消息队列
                    commonService.trackInfoMQ(orderId,boxNum);
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * 订舱组进站查看—删除货物状态Long id
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:del')")
    @Log(title = "运踪_货物状态", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) throws JsonProcessingException {
        int result = 0;
        if(StringUtils.isNotNull(id)){
            OrderGoodsTrackDel trackInfo = busiGoodsTrackMapper.selectOrderIdByTrack(id.intValue());//查询删除记录的托书id和箱号
            int deltrack = busiGoodsTrackMapper.deleteBusiGoodsTrackByIdUpd(id.intValue());
            if(deltrack ==1){
                result = busiZyInfoMapper.deleteZyInfoByTrackId(id.intValue());
                if(result==1){
                    //发送消息队列
                    if(StringUtils.isNotNull(trackInfo)){
                        commonService.orderTrackDeleteMQ(trackInfo,"0");
                    }
                }
            }
        }
        return toAjax(result);
    }

    /**
     * 订舱组界面—删除货物状态Long[] ids
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:del')")
    @Log(title = "运踪_货物状态", businessType = BusinessType.DELETE)
    //@DeleteMapping("/{ids}")
    @PutMapping("/deleteTrack")
    @ApiOperation("进展跟踪记录删除")
    public AjaxResult remove(@RequestBody HashMap<String,Long[]> map)
    {
        Long[] idsArr = map.get("idsArr");
        return toAjax(busiGoodsTrackService.deleteBusiGoodsTrackByIds(idsArr));
    }

    /**
     * 订舱组进站查看/订舱组界面—货物状态详细信息
     */
    //@PreAuthorize("@ss.hasPermi('order:orderGoodsStatus:query')")
    @GetMapping("/getInfo")
    @ApiOperation("跟踪记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "记录id",name = "id",paramType = "query",dataType = "Long",required = true)
    })
    public AjaxResult getInfo(Integer id)
    {
        return AjaxResult.success(busiGoodsTrackService.selectBusiGoodsTrackById(id));
    }

    /**
     * 订舱组进站查看/订舱组界面—班列号编辑/订舱备注编辑
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:editbh')")
    @Log(title = "运踪_货物状态", businessType = BusinessType.UPDATE)
    @PutMapping("/classBhAdd")
    @ApiOperation("班列号编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "记录id(数组)",name = "idsArr",paramType = "query",dataType = "Long",required = true),
            @ApiImplicitParam(value = "班列号/订舱备注",name = "classzyNo",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "修改人",name = "updateBy",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult classBhAdd(@RequestBody BusiGoodsTrack busiGoodsTrack){
        Long[] idsArr = busiGoodsTrack.getIdsArr();
        if(idsArr.length>0){
            return toAjax(busiGoodsTrackService.updateClassBhAdd(busiGoodsTrack));
        }else{
            return AjaxResult.error("请选择托书记录");
        }
    }

    /**
     * 订舱组进站查看/订舱组界面—班列号修改记录查询，订舱备注修改记录查询
     */
    //@PreAuthorize("@ss.hasPermi('order:orderGoodsStatus:query')")
    @GetMapping("/classBhEecord")
    @ApiOperation("班列号修改记录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "记录id",name = "id",paramType = "query",dataType = "Long",required = true)
    })
    public TableDataInfo classBhEecord(Long id){
        Long trackId = id;
        List<Map> historyList = new ArrayList<>();
        BusiZyInfo zyInfo = busiZyInfoService.selectBusiZyInfoByTrack(trackId);
        if(StringUtils.isNotNull(zyInfo)){
            String editRecord = zyInfo.getClasszynoRemark();
            if(StringUtils.isNotEmpty(editRecord)){
                if(!editRecord.equals("null")){
                    //截去末尾符号
                    editRecord = StringUtils.substring(editRecord,0,-5);
                    String[] recordF = editRecord.split("<###>");
                    for(int i = 0;i <recordF.length; i++){
                        String recordEvery = recordF[i];
                        String title = recordEvery.substring(recordEvery.indexOf("<th>")+4, recordEvery.lastIndexOf("</th>")); //标题
                        String listStr = recordEvery.substring(title.length()+9, recordEvery.length()); //修改记录
                        Map recordObj = new HashMap();
                        recordObj.put("title",title);
                        recordObj.put("list",listStr);
                        historyList.add(recordObj);
                    }
                }
            }
        }
        return getDataTable(historyList);
    }

    /**
     * 订舱组界面—货物状态列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderDcz:list')")
    @GetMapping("/trackDczList")
    @ApiOperation("订舱组界面查看列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo trackDczList(GoodsTrackDcz busiGoodsTrack)
    {
        startPage();
        if(StringUtils.isNotEmpty(busiGoodsTrack.getOrderNumber())){
            busiGoodsTrack.setOrderNumber((busiGoodsTrack.getOrderNumber()).trim());
        }
        busiGoodsTrack.setType("0");
        List<GoodsTrackDcz> list = busiGoodsTrackService.selectBusiGoodsTrackDczList(busiGoodsTrack);
        for(GoodsTrackDcz trackItem:list){
            //箱号校验
            Boolean result = commonService.checkDigit(trackItem.getBoxNum());
            if(result){
                trackItem.setBoxNumCheck("箱号正确");
            }else{
                trackItem.setBoxNumCheck("箱号错误");
            }
            //推荐人
            String clientTjr = trackItem.getClientTjr();
            if(StringUtils.isNotEmpty(clientTjr)){
                if(clientTjr.contains("/")){
                    clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                }
                trackItem.setClientTjr(clientTjr);
            }
            //箱重
            String isconsolidation = trackItem.getIsconsolidation(); //0整柜 1拼箱
            if(StringUtils.isNotEmpty(isconsolidation)){
                if(isconsolidation.equals("0")){  //整柜
                    String boxType = trackItem.getContainerType();
                    Double goodskgs = 0.0;
                    if(StringUtils.isNotEmpty(trackItem.getGoodsKgs())){
                        goodskgs = Double.valueOf(trackItem.getGoodsKgs());
                    }
                    int boxamount = Integer.valueOf(trackItem.getContainerBoxamount());
                    if(StringUtils.isNotEmpty(boxType)){
                        int boxweigth = 0;
                        if(boxType.contains("40")){
                            int[] boxarr = {3700,3750,3820,3850,3900};
                            boxweigth = boxWeight(boxarr);
                        }
                        if(boxType.contains("45")){
                            boxweigth = 4800;
                        }
                        if(boxType.contains("20")){
                            int[] boxarr = {2100,2230,2500};
                            boxweigth = boxWeight(boxarr);
                        }
                        if(boxamount!=0){
                            String totalWeight = String.valueOf((int)Math.ceil(goodskgs/boxamount)+boxweigth);
                            trackItem.setBoxWeight(totalWeight);
                        }
                    }
                }
                if(isconsolidation.equals("1")){ //拼箱
                    trackItem.setBoxWeight(trackItem.getGoodsKgs());
                }
            }
//            //箱型
//            String containerType = trackItem.getContainerType();
//            if(StringUtils.isNotEmpty(containerType)){
//                containerType = commonService.containerTypeName(containerType);
//                if(StringUtils.isNotEmpty(containerType)){
//                    trackItem.setContainerType(containerType);
//                }
//            }
            //箱量
            if("1".equals(trackItem.getIsconsolidation())){
                trackItem.setContainerBoxamount("0");
            }
            //随车状态
            String suichestate = trackItem.getSuichestate(); //随车状态 0已审核 1已审核特殊单证 2已审核项目多 3未审核特殊单证
            if(StringUtils.isNotEmpty(suichestate)){
                suichestate = "0".equals(suichestate)?"已审核":"1".equals(suichestate)?"已审核特殊单证":"2".equals(suichestate)?"已审核多项目":"未审核特殊单证";
                trackItem.setSuichestate(suichestate);
            }
            String classEastandwest = trackItem.getClassEastandwest(); //0去程 1回程
            //关务状态(默认0：单证未提供，1：草单未出，2：草单未确认，3：正本未提供，4.重箱未进站，5.正在报关，6.报关单放行，7.报关单查验，8.查验异常正处理，9.查验已放行，10.出区异常待放行，11.出区异常已放行 )
            String guanwustate = trackItem.getGuanwustate();
            List<Map> guanwustateList = new ArrayList<>();
            if(StringUtils.isNotEmpty(guanwustate)){
                if("0".equals(classEastandwest)){
                    guanwustate = getStatusWest(guanwustate);
                }
                if("1".equals(classEastandwest)){
                    guanwustate = getStatusEast(guanwustate);
                }
                if(StringUtils.isNotEmpty(guanwustate)){
                    String[] gwTxt = guanwustate.split("/");
                    if(gwTxt.length>0){
                        for(int p=0;p<gwTxt.length;p++){
                            Map gwmap = new HashMap();
                            String gwColor = getGwColor(gwTxt[p]);
                            gwmap.put("gwStatus",gwTxt[p]);
                            gwmap.put("gwColor",gwColor);
                            guanwustateList.add(gwmap);
                        }
                    }
                }
                trackItem.setGuanwustate(guanwustate);
                trackItem.setGuanwustateList(guanwustateList);
            }
            //托书报关状态
            String bgProgress = trackItem.getBgProgress();
            List<Map> bgProgressList = new ArrayList<>();
            if(StringUtils.isNotEmpty(bgProgress)){
                if("0".equals(classEastandwest)){
                    bgProgress = getStatusWest(bgProgress);
                }
                if("1".equals(classEastandwest)){
                    bgProgress = getStatusEast(bgProgress);
                }
                if(StringUtils.isNotEmpty(bgProgress)){
                    String[] bgTxt = bgProgress.split("/");
                    if(bgTxt.length>0){
                        for(int q=0;q<bgTxt.length;q++){
                            Map bgmap = new HashMap();
                            String bgColor = getGwColor(bgTxt[q]);
                            bgmap.put("bgStatus",bgTxt[q]);
                            bgmap.put("bgColor",bgColor);
                            bgProgressList.add(bgmap);
                        }
                    }
                }
                trackItem.setBgProgress(bgProgress);
                trackItem.setBgProgressList(bgProgressList);
            }
            //到货方式
            String shipOrderBinningway = trackItem.getShipOrderBinningway();
            if(StringUtils.isNotEmpty(shipOrderBinningway)){
                shipOrderBinningway = "0".equals(shipOrderBinningway)?"委托ZIH提货":"自送货";
            }else{
                shipOrderBinningway = "";
            }
            trackItem.setShipOrderBinningway(shipOrderBinningway);
        }
        return getDataTable(list);
    }

    /**
     * 订舱组界面—箱量统计
     */
    @GetMapping("/boxAmountTotalDcz")
    @ApiOperation("箱量统计")
    public AjaxResult boxAmountTotalDcz(GoodsTrackDcz busiGoodsTrack){
        Map boxAmount = new HashMap();
        if(StringUtils.isEmpty(busiGoodsTrack.getClassBh())){
            boxAmount.put("20t","0");
            boxAmount.put("40t","0");
            boxAmount.put("45t","0");
            boxAmount.put("20p","0");
            boxAmount.put("40p","0");
            boxAmount.put("45p","0");
            boxAmount.put("xl","0");
        }else{
            String isconsolidation = busiGoodsTrack.getIsconsolidation();  //0整柜 1拼箱
            //整柜数据
            StringBuffer t = new StringBuffer();
            StringBuffer f = new StringBuffer();
            StringBuffer ff = new StringBuffer();
            List<Map> maps = busiGoodsTrackService.selectAmountDcz(busiGoodsTrack);
            maps.forEach(map -> {
                if (map.get("boxType").equals("20")) {
                    t.append(map.get("boxAmountSecond"));
                } else if (map.get("boxType").equals("40")) {
                    f.append(map.get("boxAmountSecond"));
                } else if (map.get("boxType").equals("45")) {
                    ff.append(map.get("boxAmountSecond"));
                }
            });
            //拼箱数据
            StringBuffer tp = new StringBuffer();
            StringBuffer fp = new StringBuffer();
            StringBuffer ffp = new StringBuffer();
            List<Map> amountVW = busiGoodsTrackService.selectAmountVWDcz(busiGoodsTrack);
            amountVW.forEach(map -> {
                if (map.get("boxType").equals("20")) {
                    tp.append(map.get("boxAmountSecond"));
                } else if (map.get("boxType").equals("40")) {
                    fp.append(map.get("boxAmountSecond"));
                } else if (map.get("boxType").equals("45")) {
                    ffp.append(map.get("boxAmountSecond"));
                }
            });
            //总箱量
            Double z1 = t.length()==0?0:Double.valueOf(t.toString())/2;
            Double z2 = f.length()==0?0:Double.valueOf(f.toString());
            Double z3 = ff.length()==0?0:Double.valueOf(ff.toString());
            Double p1 = tp.length()==0?0:Double.valueOf(tp.toString())/2;
            Double p2 = fp.length()==0?0:Double.valueOf(fp.toString());
            Double p3 = ffp.length()==0?0:Double.valueOf(ffp.toString());
            Double xl = 0.0;
            if(StringUtils.isEmpty(isconsolidation)){
                boxAmount.put("20t",t.length() == 0? "0" : t);
                boxAmount.put("40t",f.length() == 0? "0" : f);
                boxAmount.put("45t",ff.length() == 0? "0" : ff);
                boxAmount.put("20p",tp.length() == 0? "0" : tp);
                boxAmount.put("40p",fp.length() == 0? "0" : fp);
                boxAmount.put("45p",ffp.length() == 0? "0" : ffp);
                xl = z1+z2+z3+p1+p2+p3;
            }
            else if(isconsolidation.equals("0")){
                boxAmount.put("20t",t.length() == 0? "0" : t);
                boxAmount.put("40t",f.length() == 0? "0" : f);
                boxAmount.put("45t",ff.length() == 0? "0" : ff);
                boxAmount.put("20p","0");
                boxAmount.put("40p","0");
                boxAmount.put("45p","0");
                xl = z1+z2+z3;
            }
            else if(isconsolidation.equals("1")){
                boxAmount.put("20t","0");
                boxAmount.put("40t","0");
                boxAmount.put("45t","0");
                boxAmount.put("20p",tp.length() == 0? "0" : tp);
                boxAmount.put("40p",fp.length() == 0? "0" : fp);
                boxAmount.put("45p",ffp.length() == 0? "0" : ffp);
                xl = p1+p2+p3;
            }
            boxAmount.put("xl",xl);
        }
        return AjaxResult.success(boxAmount);
    }

    /**
     * 订舱组界面—列表导出
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorderszo:export1')")
    @Log(title = "订舱组界面—列表导出表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportTrackZo")
    @ApiOperation("订舱组界面—列表导出")
    public AjaxResult exportTrackZo(GoodsTrackDcz busiGoodsTrack)
    {
        busiGoodsTrack.setType("1");
        List<GoodsTrackDcz> list = busiGoodsTrackService.selectBusiGoodsTrackDczList(busiGoodsTrack);
        int i = 1;
        for(GoodsTrackDcz trackItem:list){
            trackItem.setSort(i);
            //箱型
            String containerType = trackItem.getContainerType();
            if(StringUtils.isNotEmpty(containerType)){
                containerType = commonService.containerTypeName(containerType);
                if(StringUtils.isNotEmpty(containerType)){
                    trackItem.setContainerType(containerType);
                }
            }
            //箱量
            if("1".equals(trackItem.getIsconsolidation())){
                trackItem.setContainerBoxamount("0");
            }
            //推荐人
            String clientTjr = trackItem.getClientTjr();
            if(StringUtils.isNotEmpty(clientTjr)){
                if(clientTjr.contains("/")){
                    clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                }
                trackItem.setClientTjr(clientTjr);
            }
            //随车状态
            String suichestate = trackItem.getSuichestate(); //随车状态 0已审核 1已审核特殊单证 2已审核项目多 3未审核特殊单证
            if(StringUtils.isNotEmpty(suichestate)){
                suichestate = "0".equals(suichestate)?"已审核":"1".equals(suichestate)?"已审核特殊单证":"2".equals(suichestate)?"已审核多项目":"未审核特殊单证";
                trackItem.setSuichestate(suichestate);
            }
            String classEastandwest = trackItem.getClassEastandwest(); //0去程 1回程
            //关务状态(默认0：单证未提供，1：草单未出，2：草单未确认，3：正本未提供，4.重箱未进站，5.正在报关，6.报关单放行，7.报关单查验，8.查验异常正处理，9.查验已放行，10.出区异常待放行，11.出区异常已放行 )
            String guanwustate = trackItem.getGuanwustate();
            if(StringUtils.isNotEmpty(guanwustate)){
                if("0".equals(classEastandwest)){
                    guanwustate = getStatusWest(guanwustate);
                }
                if("1".equals(classEastandwest)){
                    guanwustate = getStatusEast(guanwustate);
                }
                trackItem.setGuanwustate(guanwustate);
            }
            //托书报关状态
            String bgProgress = trackItem.getBgProgress();
            if(StringUtils.isNotEmpty(bgProgress)){
                if("0".equals(classEastandwest)){
                    bgProgress = getStatusWest(bgProgress);
                }
                if("1".equals(classEastandwest)){
                    bgProgress = getStatusEast(bgProgress);
                }
                trackItem.setBgProgress(bgProgress);
            }
            i++;
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(date);
        ExcelUtil<GoodsTrackDcz> util = new ExcelUtil<GoodsTrackDcz>(GoodsTrackDcz.class);
        return util.exportExcel(list, "订舱组信息查看"+time);
    }

    /**
     * 订舱组界面—舱位号批量修改
     */
    @PreAuthorize("@ss.hasPermi('classOrder:dczorders:editbl')")
    @Log(title = "订舱组界面-舱位号修改", businessType = BusinessType.UPDATE)
    @PutMapping("/orderBlEdit")
    @ApiOperation("订舱组界面-舱位号修改")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "托书id(数组)",name = "orderIdsArr",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "班列日期",name = "classDate",paramType = "query",dataType = "date",required = true),
            @ApiImplicitParam(value = "班列编号",name = "classBh",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "修改人",name = "updateBy",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult orderBlEdit(@RequestBody BusiGoodsTrack busiGoodsTrack){
        String[] orderIdsArr = busiGoodsTrack.getOrderIdsArr();
        if(orderIdsArr.length>0){
            if(StringUtils.isEmpty(busiGoodsTrack.getClassBh())){
                return AjaxResult.error("请输入更改后的班列编号");
            }
            int result = busiGoodsTrackService.orderBlEdit(busiGoodsTrack);
            if(result==2){
                return AjaxResult.error("新班列不存在");
            }
            return toAjax(result);
        }else{
            return AjaxResult.error("请选择托书记录");
        }
    }

    //去程报关状态
    private String getStatusWest(String gwCode){
        String gwStatusShow = "";
        String gwStatus = "";
        if(StringUtils.isNotEmpty(gwCode)){
            String[] gwCodeArr = gwCode.split(",");
            for(int i=0; i<gwCodeArr.length;i++){
                switch (gwCodeArr[i].trim())
                {
//                    case "0":
//                        gwStatus += "单证未提供/"; break;
                    case "1":
                        gwStatus += "草单未出/"; break;
                    case "2":
                        gwStatus += "草单未确认/"; break;
                    case "3":
                        gwStatus += "正本未提供/"; break;
                    case "4":
                        gwStatus += "重箱未进站/"; break;
                    case "5":
                        gwStatus += "正在报关/"; break;
                    case "6":
                        gwStatus += "报关单放行/"; break;
                    case "7":
                        gwStatus += "报关单查验/"; break;
                    case "8":
                        gwStatus += "查验异常待处理/"; break;
                    case "9":
                        gwStatus += "查验已放行(人工)/"; break;
                    case "10":
                        gwStatus += "出区异常待放行/"; break;
                    case "11":
                        gwStatus += "出区异常已放行/"; break;
                    case "12":
                        gwStatus += "正本已提供/"; break;
                    case "13":
                        gwStatus += "单证已提供（报关资料）/"; break;
                    case "14":
                        gwStatus += "正在审单/"; break;
                    case "15":
                        gwStatus += "报关单查验(人工)/"; break;
                    case "16":
                        gwStatus += "报关单查验(机检)/"; break;
                    case "17":
                        gwStatus += "报关单查验(人工加机检)/"; break;
                    case "18":
                        gwStatus += "查验已放行(机检)/"; break;
                    case "19":
                        gwStatus += "查验已放行(人工和机检)/"; break;
                    case "20":
                        gwStatus += "报关问题大/"; break;
                    default:
                        gwStatus += ""; break;
                }
            }
        }
        if(StringUtils.isNotEmpty(gwStatus)){
            gwStatus = StringUtils.substring(gwStatus,0,-1); //截掉末尾斜杠
//            String[] flagArr = {"报关单放行","报关单查验","查验已放行","出区异常待放行","出区异常已放行"};
//            List<String> flagList = Arrays.asList(flagArr);
//            String[] gwStatusArr = gwStatus.split("/");
//            if(gwStatusArr.length>0){
//                for(int m=0; m<gwStatusArr.length;m++){
//                    if(flagList.contains(gwStatusArr[m])){
//                        gwStatusShow = gwStatusShow + gwStatusArr[m] +"/";
//                    }
//                }
//                gwStatusShow = StringUtils.substring(gwStatusShow,0,-1); //截掉末尾斜杠
//            }
        }
        return gwStatus;
    }

    //回程报关状态
    private String getStatusEast(String gwCode){
        String gwStatusShow = "";
        String gwStatus = "";
        if(StringUtils.isNotEmpty(gwCode)){
            String[] gwCodeArr = gwCode.split(",");
            for(int i=0; i<gwCodeArr.length;i++){
                switch (gwCodeArr[i].trim())
                {
//                    case "0":
//                        gwStatus += "单证未提供/"; break;
//                    case "1":
//                        gwStatus += "单证已提供/"; break;
                    case "2":
                        gwStatus += "正在审单/"; break;
                    case "3":
                        gwStatus += "问题未回复/"; break;
                    case "4":
                        gwStatus += "草单未确认/"; break;
                    case "5":
                        gwStatus += "正本未提供/"; break;
                    case "6":
                        gwStatus += "具备清关条件/"; break;
                    case "7":
                        gwStatus += "转关提前申报未出号/"; break;
                    case "8":
                        gwStatus += "申报未出号/"; break;
                    case "9":
                        gwStatus += "转关提前申报出号未缴税/"; break;
                    case "10":
                        gwStatus += "出号未缴税/"; break;
                    case "11":
                        gwStatus += "保证金未审批/"; break;
                    case "12":
                        gwStatus += "暂进未审批/"; break;
                    case "13":
                        gwStatus += "转关提前申报报关单查验/"; break;
                    case "14":
                        gwStatus += "报关单查验/"; break;
                    case "15":
                        gwStatus += "转关提前申报海关系统已放行/"; break;
                    case "16":
                        gwStatus += "海关系统已放行/"; break;
                    case "17":
                        gwStatus += "海关系统已放行（目的地查验）/"; break;
                    case "18":
                        gwStatus += "ATA未核注/"; break;
                    case "19":
                        gwStatus += "ATA已核注/"; break;
                    case "20":
                        gwStatus += "客户要求暂不申报/"; break;
                    case "21":
                        gwStatus += "核注清单未申报/"; break;
                    case "22":
                        gwStatus += "具备转关条件/"; break;
                    case "23":
                        gwStatus += "客户要求暂不办理转关/"; break;
                    case "24":
                        gwStatus += "转关已放行/"; break;
                    case "25":
                        gwStatus += "转关提前申报未接单/"; break;
                    case "26":
                        gwStatus += "已申报未接单/"; break;
                    case "27":
                        gwStatus += "海关系统未放行（目的地查验）/"; break;
                    case "28":
                        gwStatus += "通关一体化/"; break;
                    case "29":
                        gwStatus += "下货/"; break;
                    case "30":
                        gwStatus += "其他报关行申报/"; break;
                    case "31":
                        gwStatus += "关封未核销/"; break;
                    default:
                        gwStatus += ""; break;
                }
            }
        }
        if(StringUtils.isNotEmpty(gwStatus)){
            gwStatus = StringUtils.substring(gwStatus,0,-1); //截掉末尾斜杠
//            String[] flagArr = {"报关单放行","报关单查验","查验已放行","出区异常待放行","出区异常已放行"};
//            List<String> flagList = Arrays.asList(flagArr);
//            String[] gwStatusArr = gwStatus.split("/");
//            if(gwStatusArr.length>0){
//                for(int m=0; m<gwStatusArr.length;m++){
//                    if(flagList.contains(gwStatusArr[m])){
//                        gwStatusShow = gwStatusShow + gwStatusArr[m] +"/";
//                    }
//                }
//                gwStatusShow = StringUtils.substring(gwStatusShow,0,-1); //截掉末尾斜杠
//            }
        }
        return gwStatus;
    }

    //单证颜色标识 0黑色 1红色 2蓝色 3绿色
    private String getGwColor(String gwStatusTxt){
        String colorFlag = "0";
        if(StringUtils.isNotEmpty(gwStatusTxt)){
            switch (gwStatusTxt)
            {
                case "报关单查验(人工)":colorFlag = "1";break;
                case "报关单查验(机检)":colorFlag = "1";break;
                case "报关单查验(人工加机检)":colorFlag = "1";break;
                case "查验异常待处理":colorFlag = "1";break;
                case "出区异常待放行":colorFlag = "1";break;
                case "报关单查验":colorFlag = "1";break;
                case "查验已放行(人工)":colorFlag = "2";break;
                case "查验已放行(机检)":colorFlag = "2";break;
                case "查验已放行(人工和机检)":colorFlag = "2";break;
                case "出区异常已放行":colorFlag = "2";break;
                case "报关单放行":colorFlag = "3";break;
                //default: colorFlag = "0"; break;
            }
        }
        return colorFlag;
    }



    public int boxWeight(int[] arr){
        int item = (int)(Math.random()*(3));
        return arr[item];
    }

    /**
     * 新增运踪_货物状态
     */
    @PreAuthorize("@ss.hasPermi('order:orderGoodsStatus:add')")
    @Log(title = "运踪_货物状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiGoodsTrack busiGoodsTrack)
    {
        return toAjax(busiGoodsTrackService.insertBusiGoodsTrack(busiGoodsTrack));
    }
}
