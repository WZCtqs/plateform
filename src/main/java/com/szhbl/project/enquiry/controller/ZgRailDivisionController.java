package com.szhbl.project.enquiry.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.file.FileUtils;
import com.szhbl.project.enquiry.dto.ZgRailDivisionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.enquiry.domain.ZgRailDivision;
import com.szhbl.project.enquiry.service.IZgRailDivisionService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 整柜铁路运费Controller
 *
 * @author jhm
 * @date 2020-03-13
 */
@Slf4j
@RestController
@RequestMapping("/cost/zgRailWayCost")
@Api(tags = "整柜铁路运费")
public class ZgRailDivisionController extends BaseController
{
    @Autowired
    private IZgRailDivisionService zgRailDivisionService;
    @Resource
    private ResourceLoader resourceLoader;
    /**
     * 查询整柜铁路运费列表
     */
//    @PreAuthorize("@ss.hasPermi('cost:zgRailWayCost:list')")
    @GetMapping("/list")
    @ApiOperation("查询整柜铁路运费列表")
    public TableDataInfo list(ZgRailDivision zgRailDivision)
    {
        startPage();
        List<ZgRailDivision> list = zgRailDivisionService.selectZgRailDivisionList(zgRailDivision);
        return getDataTable(list);
    }

    /**
     * 导出整柜铁路运费列表
     */
    @PreAuthorize("@ss.hasPermi('cost:zgRailWayCost:export')")
    @Log(title = "整柜铁路运费", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZgRailDivision zgRailDivision)
    {
        List<ZgRailDivision> list = zgRailDivisionService.selectZgRailDivisionList(zgRailDivision);
        for(ZgRailDivision zg:list){
            if(zg.getLineType().equals("0")){
                zg.setLineType("中欧");
            }else if(zg.getLineType().equals("2")){
                zg.setLineType("中亚");
            }else if(zg.getLineType().equals("3")){
                zg.setLineType("中越");
            }else{
                zg.setLineType("中俄");
            }
            if(zg.getIsContainer().equals("0")){
                zg.setIsContainer("SOC");
            }else{
                zg.setIsContainer("COC");
            }
            if(zg.getEastOrWest().equals("0")){
                zg.setEastOrWest("西向");
            }else{
                zg.setEastOrWest("东向");
            }

        }
        ExcelUtil<ZgRailDivision> util = new ExcelUtil<ZgRailDivision>(ZgRailDivision.class);
        return util.exportExcel(list, "整柜铁路运费");
    }

    /**
     * 获取整柜铁路运费详细信息
     */
//    @PreAuthorize("@ss.hasPermi('cost:zgRailWayCost:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取整柜铁路运费详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "整柜铁路运费id",name = "id",paramType = "query",dataType = "Long",required = true)
    })
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(zgRailDivisionService.selectZgRailDivisionById(id));
    }

    /**
     * 新增整柜铁路运费
     */
    @PreAuthorize("@ss.hasPermi('cost:zgRailWayCost:add')")
    @Log(title = "整柜铁路运费", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增整柜铁路运费")
    public AjaxResult add(@RequestBody ZgRailDivision zgRailDivision)
    {
        List<ZgRailDivision> zgRailDivisionList = zgRailDivisionService.selectZgRailDivisionListWithZG(zgRailDivision);
        if(zgRailDivisionList.size()==0){

            return toAjax(zgRailDivisionService.insertZgRailDivision(zgRailDivision));

        }else{
            return AjaxResult.error("已存在相同箱型、东西向、线路、铁路运费的数据，请勿重复插入");
        }

    }

    /**
     * 修改整柜铁路运费
     */
    @PreAuthorize("@ss.hasPermi('cost:zgRailWayCost:edit')")
    @Log(title = "整柜铁路运费", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改整柜铁路运费")
    public AjaxResult edit(@RequestBody ZgRailDivision zgRailDivision)
    {
        return toAjax(zgRailDivisionService.updateZgRailDivision(zgRailDivision));
    }

    /**
     * 删除整柜铁路运费
     */
    @PreAuthorize("@ss.hasPermi('cost:zgRailWayCost:remove')")
    @Log(title = "整柜铁路运费", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除整柜铁路运费")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(zgRailDivisionService.deleteZgRailDivisionByIds(ids));
    }




    /**
     * 下载模板
     */
    @PreAuthorize("@ss.hasPermi('basic:zgdivision:export')")
    @GetMapping("/downLoadExcel")
    @ApiOperation(value = "下载整柜铁路运费导入模板")
    public void downModel(HttpServletRequest request, HttpServletResponse response) {
        String filename = "整柜铁路运费导入模板.xlsx";
       // String path = "templates/整柜铁路运费导入模板.xlsx";
     //   String name = "特种箱提还箱平衡费模板.xlsx";
        ServletOutputStream out = null;
        try {
            InputStream fis = FileUtils.getResourcesFileInputStream("templates/"+filename);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            response.setContentType("application/binary;charset=ISO8859-1");
            String fileName = java.net.URLEncoder.encode(filename, "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            log.error("整柜铁路运费Controller下载模板失败：{}",e.toString(),e.getStackTrace());
        } finally {
            //关闭文件输出流
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                System.gc();
            } catch (Exception e) {
                log.error("关闭文件输出流失败：{}",e.toString(),e.getStackTrace());
            } finally {
            }
        }
      /*  CommonUtils.downloadThymeleaf(resourceLoader,filename,path,request,response);*/
    }

        /**
     * 导入
     */
    @PreAuthorize("@ss.hasPermi('basic:zgdivision:import')")
    @PostMapping("/importExcel")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("导入整柜铁路运费")
    public AjaxResult importExcel(@RequestParam MultipartFile excelFile) throws Exception {

        ExcelUtil<ZgRailDivisionDto> util = new ExcelUtil<>(ZgRailDivisionDto.class);
        List<ZgRailDivisionDto> list = util.importExcel(excelFile.getInputStream());
        int total = list.size();
        int successNum = 0;
        int errorNum = 0;
        int hang=1;//当前是第几行 2 begin
        int kong=0;//多少空行
        System.out.println("list"+list);

        StringBuilder sb = new StringBuilder();
        for (ZgRailDivisionDto zg : list) {
            hang++;
            ZgRailDivision zgRailDivision = new ZgRailDivision();
            if(StringUtils.isEmpty(zg.getLineType()) && StringUtils.isEmpty(zg.getContainerTypeValue()) && StringUtils.isEmpty(zg.getContainerType()) && StringUtils.isEmpty(zg.getOrderUnloadSite()) && StringUtils.isEmpty(zg.getOrderUploadSite()) && StringUtils.isEmpty(zg.getEastOrWest()) ){
                kong++;

                continue;
            }
            if(StringUtils.isEmpty(zg.getLineType()) || StringUtils.isEmpty(zg.getContainerTypeValue()) || StringUtils.isEmpty(zg.getContainerType()) || StringUtils.isEmpty(zg.getOrderUnloadSite()) || StringUtils.isEmpty(zg.getOrderUploadSite()) || StringUtils.isEmpty(zg.getEastOrWest()) ){
                errorNum++;
                sb.append("第"+hang+"行必填项不能为空；");
                continue;
            }else {
                //插入之前，判断是否有相同数据存在
                BeanUtils.copyProperties(zg,zgRailDivision);
                zgRailDivision.setOrderUploadSite(zgRailDivision.getOrderUploadSite().trim().replace("\n",""));
                zgRailDivision.setOrderUnloadSite(zgRailDivision.getOrderUnloadSite().trim().replace("\n",""));
                List<ZgRailDivision> zgRailDivisionList = zgRailDivisionService.selectZgRailDivisionListWithZG(zgRailDivision);
                if(zgRailDivisionList.size()==0){
                    zgRailDivisionService.insertZgRailDivision(zgRailDivision);
                    successNum++;
                }else {
//                    sb.append(zgRailDivision.getOrderUploadSite()+","+zgRailDivision.getOrderUnloadSite()+"、"+"线路"+zgRailDivision.getLineType()+","+"东西向"+zgRailDivision.getEastOrWest()+"箱型值"+zgRailDivision.getContainerTypeValue()+",数据已存在,请勿重复插入");
//                    continue;
                    zgRailDivision.setId(zgRailDivisionList.get(0).getId());
                    zgRailDivisionService.updateZgRailDivision(zgRailDivision);
                    successNum++;
                }

            }


        }
        if (successNum == 0) {
            return AjaxResult.error("全部导入失败！，请重新正确填写：" + sb.toString());
        } else if (successNum +kong< total && successNum > 0) {
            return AjaxResult.error("部分导入成功, "+"有 " + errorNum +  "条不合格数据：" + sb.toString()+"有 " + kong +  "条空行,已忽略");
        } else {

            return AjaxResult.success("全部导入成功，"+"有 " + kong +  "条空行,已忽略");
        }

    }


}
