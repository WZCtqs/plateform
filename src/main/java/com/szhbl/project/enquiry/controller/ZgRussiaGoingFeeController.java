package com.szhbl.project.enquiry.controller;

import com.szhbl.common.utils.CommonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.enquiry.domain.ZgRussiaGoingFee;
import com.szhbl.project.enquiry.service.IZgRussiaGoingFeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 俄线提货费Controller
 *
 * @author szhbl
 * @date 2020-07-10
 */
@RestController
@RequestMapping("/enquiry/zgRussiaGoingFee")
public class ZgRussiaGoingFeeController extends BaseController {
    @Autowired
    private IZgRussiaGoingFeeService zgRussiaGoingFeeService;

    @Resource
    private ResourceLoader resourceLoader;

    /**
     * 查询俄线提货费列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ZgRussiaGoingFee zgRussiaGoingFee) {
        startPage();
        List<ZgRussiaGoingFee> list = zgRussiaGoingFeeService.selectZgRussiaGoingFeeList(zgRussiaGoingFee);
        return getDataTable(list);
    }

    /**
     * 导出俄线提货费列表
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgRussiaGoingFee:export')")
    @Log(title = "俄线提货费", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZgRussiaGoingFee zgRussiaGoingFee) {
        List<ZgRussiaGoingFee> list = zgRussiaGoingFeeService.selectZgRussiaGoingFeeList(zgRussiaGoingFee);
        ExcelUtil<ZgRussiaGoingFee> util = new ExcelUtil<ZgRussiaGoingFee>(ZgRussiaGoingFee.class);
        return util.exportExcel(list, "郑俄去程提货费");
    }

    /**
     * 获取俄线提货费详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(zgRussiaGoingFeeService.selectZgRussiaGoingFeeById(id));
    }

    /**
     * 新增俄线提货费
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgRussiaGoingFee:add')")
    @Log(title = "俄线提货费", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZgRussiaGoingFee zgRussiaGoingFee) {
        return toAjax(zgRussiaGoingFeeService.insertZgRussiaGoingFee(zgRussiaGoingFee));
    }

    /**
     * 下载模板
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgRussiaGoingFee:download')")
    @GetMapping("/downLoadExcel")
    @ApiOperation(value = "下载俄线提货费用导入模板")
    public void downModel(HttpServletRequest request, HttpServletResponse response) {
        String filename = "郑俄去程整柜提货费导入模板.xlsx";
        String path = "templates/郑俄去程整柜提货费导入模板.xlsx";
        CommonUtils.downloadThymeleaf(resourceLoader, filename, path, request, response);
    }

    /**
     * 导入
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgRussiaGoingFee:import')")
    @PostMapping("/importExcel")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("导入俄线提货费")
    public AjaxResult importExcel(@RequestParam MultipartFile file) throws Exception {

        ExcelUtil<ZgRussiaGoingFee> util = new ExcelUtil<>(ZgRussiaGoingFee.class);
        List<ZgRussiaGoingFee> list = util.importExcel(file.getInputStream());
        int total = list.size();
        int successNum = 0;
        int errorNum = 0;//
        int hang = 1;//当前是第几行 2 begin
        int kong = 0;//多少空行
        StringBuilder sb = new StringBuilder();
        for (ZgRussiaGoingFee zgRussiaGoingFee : list) {
            hang++;
            if (StringUtils.isEmpty(zgRussiaGoingFee.getPickUpCity())
                    && StringUtils.isEmpty(zgRussiaGoingFee.getCargoCollectionPoint())
                    && StringUtils.isEmpty(zgRussiaGoingFee.getContainerType())
                    && zgRussiaGoingFee.getPickGoodsFee() == null) {
                kong++;
                continue;
            }
            if (StringUtils.isEmpty(zgRussiaGoingFee.getPickUpCity())
                    || StringUtils.isEmpty(zgRussiaGoingFee.getCargoCollectionPoint())
                    || StringUtils.isEmpty(zgRussiaGoingFee.getContainerType())
                    || zgRussiaGoingFee.getPickGoodsFee() == null) {
                errorNum++;
                sb.append("第" + hang + "行必填项不能为空；");
                continue;
            } else {
                String[] conTypes = zgRussiaGoingFee.getContainerType().split("、");
                for (int i = 0; i < conTypes.length; i++) {
                    if (StringUtils.isEmpty(conTypes[i])) {
                        continue;
                    }
                    zgRussiaGoingFee.setContainerType(conTypes[i].trim().replace("\n", ""));
                    zgRussiaGoingFeeService.insertZgRussiaGoingFee(zgRussiaGoingFee);
                    if (i == conTypes.length - 1) {
                        successNum++;
                    }
                }
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
     * 修改俄线提货费
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgRussiaGoingFee:edit')")
    @Log(title = "俄线提货费", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZgRussiaGoingFee zgRussiaGoingFee) {
        List<ZgRussiaGoingFee> zgRussiaGoingFeeList = zgRussiaGoingFeeService.selectZgRussiaGoingFeeByCity(zgRussiaGoingFee);
        if (zgRussiaGoingFeeList.size() == 0) {
            // 不存在数据的话，直接更新
            return toAjax(zgRussiaGoingFeeService.updateZgRussiaGoingFee(zgRussiaGoingFee));
        } else if (!zgRussiaGoingFeeList.get(0).getId().equals(zgRussiaGoingFee.getId())) {
            // 存在数据但是id不是当前id，说明修改后的数据已在数据库存在
            return AjaxResult.error("已存在该提货地到集货地" + zgRussiaGoingFee.getContainerType() + "提货费用设置");
        } else {
            // id一直的情况，更新当前数据
            return toAjax(zgRussiaGoingFeeService.updateZgRussiaGoingFee(zgRussiaGoingFee));
        }
    }

    /**
     * 删除俄线提货费
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgRussiaGoingFee:remove')")
    @Log(title = "俄线提货费", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(zgRussiaGoingFeeService.deleteZgRussiaGoingFeeByIds(ids));
    }
}
