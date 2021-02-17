package com.szhbl.project.basic.controller;

import com.szhbl.common.utils.file.FileUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.basic.domain.DangerousGoodsImport;
import com.szhbl.project.basic.domain.DangerousGoods;
import com.szhbl.project.basic.service.IDangerousGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 危险品Controller
 * 
 * @author dps
 * @date 2020-01-15
 */
@Slf4j
@RestController
@RequestMapping("/basic/goods")
@Api(tags = "危险品管理")
public class DangerousGoodsController extends BaseController
{
    @Autowired
    private IDangerousGoodsService dangerousGoodsService;

    /**
     * 查询危险品列表
     */
//    @PreAuthorize("@ss.hasPermi('basic:goods:list')")
    @GetMapping("/goodsList")
    @ApiOperation("危险品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(DangerousGoods dangerousGoods)
    {
        startPage();
        List<DangerousGoods> list = dangerousGoodsService.selectDangerousGoodsList(dangerousGoods);
        return getDataTable(list);
    }

    /**
     * 获取危险品详细信息
     */
    //@PreAuthorize("@ss.hasPermi('risk:goods:query')")
    @GetMapping("/getInfo")
    @ApiOperation("危险品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "危险品id",name = "goodsId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(String goodsId)
    {
        return AjaxResult.success(dangerousGoodsService.selectDangerousGoodsById(goodsId));
    }

    /**
     * 新增危险品
     */
    @PreAuthorize("@ss.hasPermi('risk:goods:add')")
    @Log(title = "危险品", businessType = BusinessType.INSERT)
    @PostMapping("/goodsAdd")
    @ApiOperation("危险品新增")
    public AjaxResult add(@Validated @RequestBody DangerousGoods dangerousGoods) {
        return toAjax(dangerousGoodsService.insertDangerousGoods(dangerousGoods));
    }

    @GetMapping("/getnEnName")
    public String get(String name, String language) {
        return dangerousGoodsService.getCnEnName(name, language);
    }

    /**
     * 修改危险品
     */
    @PreAuthorize("@ss.hasPermi('risk:goods:edit')")
    @Log(title = "危险品", businessType = BusinessType.UPDATE)
    @PutMapping("/goodsEdit")
    @ApiOperation("危险品编辑")
    public AjaxResult edit(@RequestBody DangerousGoods dangerousGoods) {
        return toAjax(dangerousGoodsService.updateDangerousGoods(dangerousGoods));
    }

    /**
     * 删除危险品
     */
    @PreAuthorize("@ss.hasPermi('risk:goods:remove')")
    @Log(title = "危险品", businessType = BusinessType.DELETE)
    @GetMapping("/goodsDel")
    @ApiOperation("危险品删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "危险品id",name = "goodsIds",paramType = "query",required = true)
    })
    public AjaxResult remove(String[] goodsIds)
    {
        return toAjax(dangerousGoodsService.deleteDangerousGoodsByIds(goodsIds));
    }

    /**
     * 导出危险品列表
     */
    @PreAuthorize("@ss.hasPermi('basic:goods:export')")
    @Log(title = "危险品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DangerousGoods dangerousGoods)
    {
        List<DangerousGoods> list = dangerousGoodsService.selectDangerousGoodsList(dangerousGoods);
        ExcelUtil<DangerousGoods> util = new ExcelUtil<DangerousGoods>(DangerousGoods.class);
        return util.exportExcel(list, "goods");
    }

    /**
     * 危险品模板下载
     */
    @PreAuthorize("@ss.hasPermi('basic:dangerGoods:downloaddg')")
    @GetMapping("/downloadPT")
    @ApiOperation("危险品模板下载")
    public void downloadDG(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = "危险品模板.xlsx";
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
            log.error("危险品模板下载失败：{}",e.toString(),e.getStackTrace());
        } finally {
            //关闭文件输出流
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                System.gc();
            } catch (Exception e) {
                log.error("危险品模板下载--关闭文件输出流失败：{}",e.toString(),e.getStackTrace());
            }
        }
    }

    /**
     * 危险品导入
     */
    @PreAuthorize("@ss.hasPermi('basic:dangerGoods:importdg')")
    @Log(title = "危险品导入", businessType = BusinessType.EXPORT)
    @PostMapping("/dangerGoodsImport")
    @ApiOperation("危险品导入")
    @Transactional
    public AjaxResult dangerGoodsImport(@RequestParam MultipartFile file) throws Exception{
        ExcelUtil<DangerousGoodsImport> util = new ExcelUtil<>(DangerousGoodsImport.class);
        List<DangerousGoodsImport> list = util.importExcel(file.getInputStream());
        for(DangerousGoodsImport dangetgoods:list){
            DangerousGoods goods = new DangerousGoods();
            goods.setGoodsName(dangetgoods.getGoodsName());
            goods.setGoodsEnName(dangetgoods.getGoodsEnName());
            goods.setGoodsReport(dangetgoods.getGoodsReport());
            goods.setGoodsClearance(dangetgoods.getGoodsClearance());
            goods.setSpecialremark(dangetgoods.getSpecialremark());
            goods.setSpecialEnRemark(dangetgoods.getSpecialEnRemark());
            goods.setNoteLevel(dangetgoods.getNoteLevel());
            int a = dangerousGoodsService.insertDangerousGoods(goods);
            if (a==0){
                return AjaxResult.error("导入失败,请检查数据格式");
            }
        }
        return AjaxResult.success();
    }
}
