package com.szhbl.project.enquiry.controller;

import java.util.List;

import com.szhbl.common.utils.CommonUtils;
import com.szhbl.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.enquiry.domain.ShRailDivision;
import com.szhbl.project.enquiry.service.IShRailDivisionService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 散货铁路运费Controller
 *
 * @author jhm
 * @date 2020-03-14
 */
@RestController
@RequestMapping("/cost/shRailWayCost")
@Api(tags = "散货铁路运费管理")
public class ShRailDivisionController extends BaseController {
    @Autowired
    private IShRailDivisionService shRailDivisionService;
    @Resource
    private ResourceLoader resourceLoader;

    /**
     * 查询散货铁路运费列表
     */
//    @PreAuthorize("@ss.hasPermi('cost:shRailWayCost:list')")
    @GetMapping("/list")
    @ApiOperation("查询散货铁路运费列表")
    public TableDataInfo list(ShRailDivision shRailDivision) {
        startPage();
        List<ShRailDivision> list = shRailDivisionService.selectShRailDivisionList(shRailDivision);
        return getDataTable(list);
    }

    /**
     * 导出散货铁路运费列表
     */
    @PreAuthorize("@ss.hasPermi('basic:shRailWayCost:export')")
    @Log(title = "散货铁路运费", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出散货铁路运费列表")
    public AjaxResult export(ShRailDivision shRailDivision) {
        List<ShRailDivision> list = shRailDivisionService.selectShRailDivisionList(shRailDivision);
        for (ShRailDivision sh : list) {
            if (sh.getLineType().equals("0")) {
                sh.setLineType("中欧");
            } else if (sh.getLineType().equals("2")) {
                sh.setLineType("中亚");
            } else if (sh.getLineType().equals("3")) {
                sh.setLineType("中越");
            } else {
                sh.setLineType("中俄");
            }
            if (sh.getEastOrWest().equals("0")) {
                sh.setEastOrWest("西向");
            } else {
                sh.setEastOrWest("东向");
            }

        }
        ExcelUtil<ShRailDivision> util = new ExcelUtil<ShRailDivision>(ShRailDivision.class);
        return util.exportExcel(list, "散货铁路运费");
    }

    /**
     * 获取散货铁路运费详细信息
     */
//    @PreAuthorize("@ss.hasPermi('cost:shRailWayCost:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取散货铁路运费详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(shRailDivisionService.selectShRailDivisionById(id));
    }

    /**
     * 新增散货铁路运费
     */
    @PreAuthorize("@ss.hasPermi('cost:shRailWayCost:add')")
    @Log(title = "散货铁路运费", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增散货铁路运费")
    public AjaxResult add(@RequestBody ShRailDivision shRailDivision) {
        List<ShRailDivision> shRailDivisionList = shRailDivisionService.selectShRailDivisionListWithSH(shRailDivision);
        if (shRailDivisionList.size() == 0) {
            return toAjax(shRailDivisionService.insertShRailDivision(shRailDivision));
        } else {
            return AjaxResult.error("已存在相同箱型、东西向、线路、铁路运费的数据，请勿重复插入");
        }

    }

    /**
     * 修改散货铁路运费
     */
    @PreAuthorize("@ss.hasPermi('cost:shRailWayCost:edit')")
    @Log(title = "散货铁路运费", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改散货铁路运费")
    public AjaxResult edit(@RequestBody ShRailDivision shRailDivision) {
        return toAjax(shRailDivisionService.updateShRailDivision(shRailDivision));
    }

    /**
     * 删除散货铁路运费
     */
    @PreAuthorize("@ss.hasPermi('cost:shRailWayCost:remove')")
    @Log(title = "散货铁路运费", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除散货铁路运费")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(shRailDivisionService.deleteShRailDivisionByIds(ids));
    }

    /**
     * 导入
     */


    /**
     * 下载模板
     */
    @PreAuthorize("@ss.hasPermi('basic:shdivision:download')")
    @GetMapping("/downLoadExcel")
    @ApiOperation(value = "下载散货铁路运费导入模板")
    public void downModel(HttpServletRequest request, HttpServletResponse response) {
        String filename = "散货铁路运费导入模板.xlsx";
        String path = "templates/散货铁路运费导入模板.xlsx";
        CommonUtils.downloadThymeleaf(resourceLoader, filename, path, request, response);
    }


    /**
     * 导入
     */
    @PreAuthorize("@ss.hasPermi('basic:shdivision:import')")
    @PostMapping("/importExcel")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("导入散货铁路运费")
    public AjaxResult importExcel(@RequestParam MultipartFile excelFile) throws Exception {

        ExcelUtil<ShRailDivision> util = new ExcelUtil<>(ShRailDivision.class);
        List<ShRailDivision> list = util.importExcel(excelFile.getInputStream());
        int total = list.size();
        int successNum = 0;
        int errorNum = 0;//
        int hang=1;//当前是第几行 2 begin
        int kong=0;//多少空行
        StringBuilder sb = new StringBuilder();
        for (ShRailDivision shRailDivision : list) {
            hang++;
            if (StringUtils.isEmpty(shRailDivision.getLineType()) && StringUtils.isEmpty(shRailDivision.getOrderUnloadSite()) && StringUtils.isEmpty(shRailDivision.getOrderUploadSite()) && StringUtils.isEmpty(shRailDivision.getEastOrWest()) && StringUtils.isEmpty(shRailDivision.getLclFreight())) {
                kong++;
                continue;
            }
            if (StringUtils.isEmpty(shRailDivision.getLineType()) || StringUtils.isEmpty(shRailDivision.getOrderUnloadSite()) || StringUtils.isEmpty(shRailDivision.getOrderUploadSite()) || StringUtils.isEmpty(shRailDivision.getEastOrWest()) || StringUtils.isEmpty(shRailDivision.getLclFreight())) {
               errorNum++;
                sb.append("第"+hang+"行必填项不能为空；");
                continue;
            } else {
              //判断是否有相同数据存在
                shRailDivision.setOrderUnloadSite(shRailDivision.getOrderUnloadSite().trim().replace("\n",""));
                shRailDivision.setOrderUploadSite(shRailDivision.getOrderUploadSite().trim().replace("\n",""));
                List<ShRailDivision> shRailDivisionList = shRailDivisionService.selectShRailDivisionListWithSH(shRailDivision);
                if (shRailDivisionList.size() == 0) {
                    shRailDivisionService.insertShRailDivision(shRailDivision);
                    successNum++;
                } else {
//                    sb.append(shRailDivision.getOrderUploadSite() + "," + shRailDivision.getOrderUnloadSite() + "，" + "线路" + shRailDivision.getLineType() + "," + "东西向" + shRailDivision.getEastOrWest() + ",数据已存在,请勿重复插入");
//                    continue;
                    shRailDivision.setId(shRailDivisionList.get(0).getId());
                    shRailDivisionService.updateShRailDivision(shRailDivision);
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
