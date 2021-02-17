package com.szhbl.project.order.controller;

import com.szhbl.common.utils.CommonUtils;
import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.order.domain.BusiCargobase;
import com.szhbl.project.order.domain.vo.CargobaseEast;
import com.szhbl.project.order.domain.vo.CargobaseQuery;
import com.szhbl.project.order.domain.vo.CargobaseWest;
import com.szhbl.project.order.service.IBusiCargobaseService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 货物知识库Controller
 *
 * @author szhbl
 * @date 2020-08-18
 */
@Slf4j
@RestController
@RequestMapping("/order/cargobase")
public class BusiCargobaseController extends BaseController {
    @Autowired
    private IBusiCargobaseService busiCargobaseService;

    @Autowired
    ResourceLoader resourceLoader;

    /**
     * 查询货物知识库列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('order:cargobase:query')")
    public TableDataInfo list(BusiCargobase busiCargobase) {
        startPage();
        List<BusiCargobase> list = busiCargobaseService.selectBusiCargobaseList(busiCargobase);
        return getDataTable(list);
    }

    /**
     * 查询货物知识库列表
     */
    @GetMapping("/wechatList")
    public TableDataInfo listForWeChat(CargobaseQuery query) {
        BusiCargobase busiCargobase = new BusiCargobase();
        busiCargobase.setEastorwest(query.getEastorwest());
        busiCargobase.setCargoName(query.getCargo_Name());
        busiCargobase.setCargoReport(query.getCargo_Report());
        busiCargobase.setCountryOrigin(query.getCountryOrigin());
        busiCargobase.setUnloadSite(query.getUnloadSite());
        startPage();
        List<BusiCargobase> list = busiCargobaseService.selectBusiCargobaseList(busiCargobase);
        List<CargobaseQuery> cargobaseQueries = new ArrayList<>();
        for (BusiCargobase b : list) {
            CargobaseQuery c = new CargobaseQuery();
            c.setCargo_Name(b.getCargoName());
            c.setCargo_Report(b.getCargoReport());
            c.setCountryOrigin(b.getCountryOrigin());
            c.setEastorwest(b.getEastorwest());
            c.setUnloadSite(b.getUnloadSite());
            c.setSearchResult(b.getSearchResult());
            c.setIsRailway(b.getIsRailway());
            cargobaseQueries.add(c);
        }
        return getDataTable(cargobaseQueries);
    }

    /**
     * 导出货物知识库列表
     */
    @PreAuthorize("@ss.hasPermi('order:cargobase:export')")
    @Log(title = "货物知识库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiCargobase busiCargobase) {
        List<BusiCargobase> list = busiCargobaseService.selectBusiCargobaseList(busiCargobase);
        ExcelUtil<BusiCargobase> util = new ExcelUtil<BusiCargobase>(BusiCargobase.class);
        return util.exportExcel(list, "cargobase");
    }

    /**
     * 导入货物知识库列表
     */
    @PreAuthorize("@ss.hasPermi('order:cargobase:import')")
    @Log(title = "货物知识库", businessType = BusinessType.EXPORT)
    @PostMapping("/importExcel")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importExcel(@RequestParam MultipartFile excelFile) throws Exception {
        boolean iswest = excelFile.getOriginalFilename().contains("去程");
        List<BusiCargobase> cargobaseList = new ArrayList<>();
        if (iswest) {
            ExcelUtil<CargobaseWest> util = new ExcelUtil<>(CargobaseWest.class);
            List<CargobaseWest> westList = util.importExcel(excelFile.getInputStream());
            for (CargobaseWest west : westList) {
                BusiCargobase busiCargobase = new BusiCargobase();
                BeanUtils.copyProperties(west, busiCargobase);
                busiCargobase.setEastorwest("0");
                cargobaseList.add(busiCargobase);
            }
        } else {
            ExcelUtil<CargobaseEast> util = new ExcelUtil<>(CargobaseEast.class);
            List<CargobaseEast> eastList = util.importExcel(excelFile.getInputStream());
            for (CargobaseEast east : eastList) {
                BusiCargobase busiCargobase = new BusiCargobase();
                BeanUtils.copyProperties(east, busiCargobase);
                busiCargobase.setEastorwest("1");
                cargobaseList.add(busiCargobase);
            }
        }
        int total = cargobaseList.size();
        int successNum = 0;
        int errorNum = 0;//
        int hang = 1;//当前是第几行 2 begin
        int kong = 0;//多少空行
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cargobaseList.size(); i++) {
            BusiCargobase cargobase = cargobaseList.get(i);
            cargobase.setCreateTime(new Date());
            cargobase.setCreateBy(SecurityUtils.getUsername());
            hang++;
            if (StringUtils.isEmpty(cargobase.getCargoName())
                    && StringUtils.isEmpty(cargobase.getCargoReport())
                    && StringUtils.isEmpty(cargobase.getIsRailway())
                    && StringUtils.isEmpty(cargobase.getSearchResult())) {
                if (iswest && StringUtils.isEmpty(cargobase.getUnloadSite())) {
                    kong++;
                    continue;
                }
                if (!iswest && StringUtils.isEmpty(cargobase.getCountryOrigin())) {
                    kong++;
                    continue;
                }
            }
            if (StringUtils.isEmpty(cargobase.getCargoName()) || StringUtils.isEmpty(cargobase.getCargoReport())) {
                errorNum++;
                sb.append("第" + hang + "行必填项不能为空；");
                continue;
            } else {
                busiCargobaseService.insertBusiCargobase(cargobase);
                successNum++;
            }
        }
        if (successNum == 0) {
            return AjaxResult.error("全部导入失败！，请重新正确填写：" + sb.toString());
        } else if (successNum + kong < total && successNum > 0) {
            return AjaxResult.error("部分导入成功, " + "有 " + errorNum + "条不合格数据：" + sb.toString() + "有 " + kong + "条空行,已忽略");
        } else {
            return AjaxResult.success("全部导入成功，" + "有 " + kong + "条空行,已忽略");
        }
    }

    /**
     * 获取货物知识库详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:cargobase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(busiCargobaseService.selectBusiCargobaseById(id));
    }

    /**
     * 新增货物知识库
     */
    @PreAuthorize("@ss.hasPermi('order:cargobase:add')")
    @Log(title = "货物知识库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiCargobase busiCargobase) {
        busiCargobase.setCreateBy(SecurityUtils.getUsername());
        busiCargobase.setCreateTime(new Date());
        return toAjax(busiCargobaseService.insertBusiCargobase(busiCargobase));
    }

    /**
     * 修改货物知识库
     */
    @PreAuthorize("@ss.hasPermi('order:cargobase:edit')")
    @Log(title = "货物知识库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiCargobase busiCargobase) {
        busiCargobase.setCreateBy(SecurityUtils.getUsername());
        busiCargobase.setCreateTime(new Date());
        return toAjax(busiCargobaseService.updateBusiCargobase(busiCargobase));
    }

    /**
     * 删除货物知识库
     */
    @PreAuthorize("@ss.hasPermi('order:cargobase:remove')")
    @Log(title = "货物知识库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(busiCargobaseService.deleteBusiCargobaseByIds(ids));
    }

    /**
     * 下载去程导入模板
     */
    @PreAuthorize("@ss.hasPermi('order:cargobase:download')")
    @GetMapping("/downLoadExcelWest")
    @ApiOperation(value = "下载货物知识库去程导入模板")
    public void downLoadExcelWest(HttpServletRequest request, HttpServletResponse response) {
        String filename = "货物知识库去程导入模板.xlsx";
        String path = "templates/cargobase/货物知识库去程导入模板.xlsx";
        CommonUtils.downloadThymeleaf(resourceLoader, filename, path, request, response);
    }

    /**
     * 下载回程导入模板
     */
    @PreAuthorize("@ss.hasPermi('order:cargobase:download')")
    @GetMapping("/downLoadExcelEast")
    @ApiOperation(value = "下载货物知识库回程导入模板")
    public void downLoadExcelEast(HttpServletRequest request, HttpServletResponse response) {
        String filename = "货物知识库回程导入模板.xlsx";
        String path = "templates/cargobase/货物知识库回程导入模板.xlsx";
        CommonUtils.downloadThymeleaf(resourceLoader, filename, path, request, response);
    }
}
