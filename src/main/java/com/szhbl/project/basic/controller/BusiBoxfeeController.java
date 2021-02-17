package com.szhbl.project.basic.controller;

import java.io.*;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szhbl.common.utils.ConvertUtils;
import com.szhbl.common.utils.file.FileUtils;
import com.szhbl.common.utils.http.HttpClientUtil;
import com.szhbl.project.basic.domain.boxfee.BoxResponse;
import com.szhbl.project.basic.domain.boxfee.BoxfeeP;
import com.szhbl.project.basic.domain.boxfee.BoxfeeT;
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
import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.basic.service.IBusiBoxfeeService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 提还箱地和费用规则Controller
 *
 * @author dps
 * @date 2020-01-15
 */
@Slf4j
@RestController
@RequestMapping("/basic/boxfee")
@Api(tags = "提还箱地和费用管理")
public class BusiBoxfeeController extends BaseController
{
    @Autowired
    private IBusiBoxfeeService busiBoxfeeService;

    /**
     * 获取箱型接口
     */
//    @PreAuthorize("@ss.hasPermi('linesite:info:query')")
    @GetMapping("/boxType")
    @ApiOperation("获取箱型接口")
    public AjaxResult boxType()
    {
        String getst = HttpClientUtil.doGet("http://xg.zih718.com:8080/conteasy/container/all_container_type");
        //根据箱型类别对数据分组
        Map<Object, List> resultMap = new HashMap<>();
        JSONArray stArray =  JSONObject.parseArray(getst);
        if(stArray.size()>0){
            for(int i=0;i<stArray.size();i++){
                JSONObject boxObj = (JSONObject)stArray.get(i);
                if(resultMap.containsKey(boxObj.get("isT"))){
                    //map中存在此id，将数据存放当前key的map中
                    resultMap.get(boxObj.get("isT")).add(boxObj);
                }else{
                    //map中不存在，新建key，用来存放数据
                    List grouplist = new ArrayList();
                    grouplist.add(boxObj);
                    resultMap.put(boxObj.get("isT"),grouplist);
                }
            }
        }
        return AjaxResult.success(resultMap);
    }

    /**
     * 查询提还箱地和费用规则列表
     */
//    @PreAuthorize("@ss.hasPermi('basic:boxfee:query')")
    @GetMapping("/boxfeeList")
    @ApiOperation("费用列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(BusiBoxfee busiBoxfee)
    {
        startPage();
        List<BusiBoxfee> list = busiBoxfeeService.selectBusiBoxfeeList(busiBoxfee);
        return getDataTable(list);
    }

    /**
     * 获取提还箱地和费用规则详细信息
     */
//    @PreAuthorize("@ss.hasPermi('basic:boxfee:query')")
    @GetMapping("/getInfo")
    @ApiOperation("费用详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "费用id",name = "boxId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(String boxId)
    {
        return AjaxResult.success(busiBoxfeeService.selectBusiBoxfeeById(boxId));
    }

    /**
     * 新增提还箱地和费用规则
     */
    @PreAuthorize("@ss.hasPermi('basic:boxfee:add')")
    @Log(title = "提还箱地和费用规则", businessType = BusinessType.INSERT)
    @PostMapping("/boxfeeAdd")
    @ApiOperation("费用新增")
    public AjaxResult add(@RequestBody BusiBoxfee busiBoxfee)
    {
        return toAjax(busiBoxfeeService.insertBusiBoxfee(busiBoxfee));
    }

    /**
     * 修改提还箱地和费用规则
     */
    @PreAuthorize("@ss.hasPermi('basic:boxfee:edit')")
    @Log(title = "提还箱地和费用规则", businessType = BusinessType.UPDATE)
    @PutMapping("/boxfeeEdit")
    @ApiOperation("费用编辑")
    public AjaxResult edit(@RequestBody BusiBoxfee busiBoxfee)
    {
        return toAjax(busiBoxfeeService.updateBusiBoxfee(busiBoxfee));
    }

    /**
     * 删除提还箱地和费用规则
     */
    @PreAuthorize("@ss.hasPermi('basic:boxfee:remove')")
    @Log(title = "提还箱地和费用规则", businessType = BusinessType.DELETE)
    @GetMapping("/boxfeeDel")
    @ApiOperation("费用删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "费用id",name = "boxIds",paramType = "query",required = true)
    })
    public AjaxResult remove(String[] boxIds)
    {
        return toAjax(busiBoxfeeService.deleteBusiBoxfeeByIds(boxIds));
    }

    /**
     * 模板下载
     */
    @PreAuthorize("@ss.hasPermi('basic:boxfee:downloadth')")
    @GetMapping("/downloadTH")
    @ApiOperation("特种箱提还箱平衡费模板下载")
    public void downloadTH(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = "特种箱提还箱平衡费模板.xlsx";
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
            log.error("特种箱提还箱平衡费模板下载失败：{}",e.toString(),e.getStackTrace());
        } finally {
            //关闭文件输出流
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                System.gc();
            } catch (Exception e) {
                log.error("特种箱提还箱平衡费模板下载--关闭文件输出流失败：{}",e.toString(),e.getStackTrace());
            }
        }
    }

    /**
     * 模板下载
     */
    @PreAuthorize("@ss.hasPermi('basic:boxfee:downloadpt')")
    @GetMapping("/downloadPT")
    @ApiOperation("普通箱提还箱平衡费模板下载")
    public void downloadPT(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = "普通箱提还箱平衡费模板.xlsx";
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
            log.error("普通箱提还箱平衡费模板下载失败：{}",e.toString(),e.getStackTrace());
        } finally {
            //关闭文件输出流
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                System.gc();
            } catch (Exception e) {
                log.error("普通箱提还箱平衡费模板下载--关闭文件输出流失败：{}",e.toString(),e.getStackTrace());
            }
        }
    }

    /**
     * 特种箱提还箱平衡费导入
     */
    @PreAuthorize("@ss.hasPermi('basic:boxfee:importt')")
    @Log(title = "特种箱提还箱平衡费导入", businessType = BusinessType.EXPORT)
    @PostMapping("/boxfeeExportT")
    @ApiOperation("特种箱提还箱平衡费导入")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "开始时间",name = "startTime",paramType = "query",dataType = "Date",required = true),
            @ApiImplicitParam(value = "结束时间",name = "endTime",paramType = "query",dataType = "Date",required = true)
    })
    @Transactional
    public AjaxResult boxfeeExportT(@RequestParam MultipartFile file, Date startTime, Date endTime) throws Exception{
        ExcelUtil<BoxfeeT> util = new ExcelUtil<>(BoxfeeT.class);
        List<BoxfeeT> list = util.importExcel(file.getInputStream());
        for(BoxfeeT feeItem:list){
            String[] boxtype = {"20OT","20HOT","40OT","40HOT","20HT","40HT","40RF","45RF"};
            String[] cost = {feeItem.getSs(),feeItem.getSs(),feeItem.getFf(),feeItem.getFf(),feeItem.getSf(),feeItem.getSf(),feeItem.getFff(),feeItem.getFff()};
            for(int i=0;i<boxtype.length;i++){
                BoxfeeT boxfee = new BoxfeeT();
                boxfee.setBoxType("1");  //0普通箱 1特种箱
                boxfee.setAddressType(feeItem.getAddressType()); //0提箱 1还箱
                boxfee.setLineTypeid(feeItem.getLineTypeid()); //线路
                boxfee.setIntra(feeItem.getIntra());
                boxfee.setAddress(feeItem.getAddress().trim().replace("\n",""));  //中文地址
                boxfee.setAddressEn(feeItem.getAddressEn().trim().replace("\n","")); //英文地址
                boxfee.setContainerType(boxtype[i]); //箱型
                boxfee.setCost(cost[i]); //费用
                if ("——".equals(cost[i])) {
                    boxfee.setState("0");
                } else {
                    boxfee.setState("1"); //0禁用 1启用
                }
                boxfee.setStartTime(startTime);
                boxfee.setEndTime(endTime);
                int a = busiBoxfeeService.insertBusiBoxfeeT(boxfee);
                if (a==0){
                    return AjaxResult.error("导入失败,请检查数据格式");
                }
            }

        }
        return AjaxResult.success();
    }

    /**
     * 普通箱提还箱平衡费导入
     */
    @PreAuthorize("@ss.hasPermi('basic:boxfee:importp')")
    @Log(title = "普通箱提还箱平衡费导入", businessType = BusinessType.EXPORT)
    @PostMapping("/boxfeeExportP")
    @ApiOperation("普通箱提还箱平衡费导入")
    @Transactional
    public AjaxResult boxfeeExportP(@RequestParam MultipartFile file, Date startTime, Date endTime) throws Exception{
        ExcelUtil<BoxfeeP> util = new ExcelUtil<>(BoxfeeP.class);
        List<BoxfeeP> list = util.importExcel(file.getInputStream());
        for(BoxfeeP feeItem:list){
            String[] boxtype = {"20HC","20GP","40HC","40GP"};
            String[] cost = {feeItem.getSs(),feeItem.getSs(),feeItem.getFf(),feeItem.getFf()};
            for(int i=0;i<boxtype.length;i++){
                BoxfeeP boxfee = new BoxfeeP();
                boxfee.setBoxType("0");  //0普通箱 1特种箱
                boxfee.setAddressType(feeItem.getAddressType()); //0提箱 1还箱
                boxfee.setLineTypeid(feeItem.getLineTypeid()); //线路类型
                boxfee.setIntra(feeItem.getIntra());
                boxfee.setAddress(feeItem.getAddress().trim().replace("\n",""));  //中文地址
                boxfee.setAddressEn(feeItem.getAddressEn().trim().replace("\n","")); //英文地址
                boxfee.setContainerType(boxtype[i]); //箱型
                boxfee.setCost(cost[i]); //费用
                if ("——".equals(cost[i])) {
                    boxfee.setState("0");
                } else {
                    boxfee.setState("1"); //0禁用 1启用
                }
                boxfee.setStartTime(startTime);
                boxfee.setEndTime(endTime);
                int a = busiBoxfeeService.insertBusiBoxfeeP(boxfee);
                if (a==0){
                    return AjaxResult.error("导入失败,请检查数据格式");
                }
            }

        }
        return AjaxResult.success();
    }

    /**
     * 导出提还箱地和费用规则列表
     */
    @PreAuthorize("@ss.hasPermi('basic:boxfee:export')")
    @Log(title = "提还箱地和费用规则", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiBoxfee busiBoxfee)
    {
        List<BusiBoxfee> list = busiBoxfeeService.selectBusiBoxfeeList(busiBoxfee);
        ExcelUtil<BusiBoxfee> util = new ExcelUtil<BusiBoxfee>(BusiBoxfee.class);
        return util.exportExcel(list, "boxfee");
    }


    /**
     * 查询国内还箱地及还箱平衡费
     *
     * @param containerType
     * @param containerNum
     * @return
     */
    @GetMapping(value = "/addressList")
    public AjaxResult addressList(String containerType,Integer containerNum) {
        List<BusiBoxfee> list= busiBoxfeeService.getAddressList(containerType);
        List<BoxResponse> responses = new ArrayList<>();
        for (BusiBoxfee box:list) {
            BoxResponse response=new BoxResponse();
            response.setId(box.getBoxId());
            response.setName(box.getAddress());
            StringBuilder sb = new StringBuilder(box.getCost().substring(0,1) + " ");
            int i = ConvertUtils.reInt(box.getCost()) * containerNum;
            response.setCost(sb.append(i).toString());
            responses.add(response);
        }
        return AjaxResult.success(responses);
    }
    /**
     * 查询国内提箱地及提箱平衡费
     *
     * @param containerType
     * @param containerNum
     * @return
     */
    @GetMapping(value = "/pickList")
    public AjaxResult pickList(String containerType,Integer containerNum,String bookingTimeFlag) {
        List<BusiBoxfee> list= busiBoxfeeService.getPickUpList(containerType,bookingTimeFlag);
        List<BoxResponse> responses = new ArrayList<>();
        for (BusiBoxfee box:list) {
            BoxResponse response=new BoxResponse();
            response.setId(box.getBoxId());
            response.setName(box.getAddress());
            StringBuilder sb = new StringBuilder(box.getCost().substring(0,1) + " ");
            int i = ConvertUtils.reInt(box.getCost()) * containerNum;
            response.setCost(sb.append(i).toString());
            responses.add(response);
        }
        return AjaxResult.success(responses);
    }
}
