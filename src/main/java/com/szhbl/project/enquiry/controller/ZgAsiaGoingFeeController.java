package com.szhbl.project.enquiry.controller;

import com.szhbl.common.utils.CommonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.enquiry.domain.ZgAsiaGoingFee;
import com.szhbl.project.enquiry.service.IZgAsiaGoingFeeService;
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
 * 郑亚、郑越去程整柜提货费Controller
 *
 * @author szhbl
 * @date 2020-09-03
 */
@RestController
@RequestMapping("/enquiry/zgAsiaFee")
public class ZgAsiaGoingFeeController extends BaseController {

    @Autowired
    private IZgAsiaGoingFeeService zgAsiaGoingFeeService;

    @Resource
    private ResourceLoader resourceLoader;

    /**
     * 查询郑亚、郑越去程整柜提货费列表
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgAsiaFee:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZgAsiaGoingFee zgAsiaGoingFee) {
        startPage();
        List<ZgAsiaGoingFee> list = zgAsiaGoingFeeService.selectZgAsiaGoingFeeList(zgAsiaGoingFee);
        return getDataTable(list);
    }

    /**
     * 导出郑亚、郑越去程整柜提货费列表
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgAsiaFee:export')")
    @Log(title = "郑亚、郑越去程整柜提货费", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZgAsiaGoingFee zgAsiaGoingFee) {
        List<ZgAsiaGoingFee> list = zgAsiaGoingFeeService.selectZgAsiaGoingFeeList(zgAsiaGoingFee);
        ExcelUtil<ZgAsiaGoingFee> util = new ExcelUtil<ZgAsiaGoingFee>(ZgAsiaGoingFee.class);
        return util.exportExcel(list, "郑亚-郑越去程整柜提货费");
    }

    /**
     * 下载模板
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgAsiaFee:download')")
    @GetMapping("/downLoadExcel")
    @ApiOperation(value = "下载郑亚-郑越提货费用导入模板")
    public void downModel(HttpServletRequest request, HttpServletResponse response) {
        String filename = "郑亚-郑越去程整柜提货费导入模板.xlsx";
        String path = "templates/郑亚-郑越去程整柜提货费导入模板.xlsx";
        CommonUtils.downloadThymeleaf(resourceLoader, filename, path, request, response);
    }

    /**
     * 导入
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgAsiaFee:import')")
    @PostMapping("/importExcel")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("导入俄线提货费")
    public AjaxResult importExcel(@RequestParam MultipartFile file) throws Exception {

        ExcelUtil<ZgAsiaGoingFee> util = new ExcelUtil<>(ZgAsiaGoingFee.class);
        List<ZgAsiaGoingFee> list = util.importExcel(file.getInputStream());
        int total = list.size();
        int successNum = 0;
        int errorNum = 0;//
        int hang = 1;//当前是第几行 2 begin
        int kong = 0;//多少空行
        StringBuilder sb = new StringBuilder();
        for (ZgAsiaGoingFee zgAsiaGoingFee : list) {
            hang++;
            if (StringUtils.isEmpty(zgAsiaGoingFee.getPickUpCity())
                    && StringUtils.isEmpty(zgAsiaGoingFee.getCargoCollectionPoint())
                    && StringUtils.isEmpty(zgAsiaGoingFee.getContainerType())
                    && StringUtils.isEmpty(zgAsiaGoingFee.getLineType())
                    && zgAsiaGoingFee.getPickGoodsFee() == null) {
                kong++;
                continue;
            }
            if (StringUtils.isEmpty(zgAsiaGoingFee.getPickUpCity())
                    || StringUtils.isEmpty(zgAsiaGoingFee.getCargoCollectionPoint())
                    || StringUtils.isEmpty(zgAsiaGoingFee.getContainerType())
                    || StringUtils.isEmpty(zgAsiaGoingFee.getLineType())
                    || zgAsiaGoingFee.getPickGoodsFee() == null) {
                errorNum++;
                sb.append("第" + hang + "行必填项不能为空；");
                continue;
            }
            if (!"郑亚".equals(zgAsiaGoingFee.getLineType().trim()) && !"郑越".equals(zgAsiaGoingFee.getLineType().trim())) {
                sb.append("第" + hang + "行线路填写不正确；");
                errorNum++;
                continue;
            } else {
                String[] conTypes = zgAsiaGoingFee.getContainerType().split("、");
                for (int i = 0; i < conTypes.length; i++) {
                    if (StringUtils.isEmpty(conTypes[i])) {
                        continue;
                    }
                    zgAsiaGoingFee.setContainerType(conTypes[i].trim().replace("\n", ""));
                    zgAsiaGoingFeeService.insertZgAsiaGoingFee(zgAsiaGoingFee);
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
     * 获取郑亚、郑越去程整柜提货费详细信息
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgAsiaFee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(zgAsiaGoingFeeService.selectZgAsiaGoingFeeById(id));
    }

    /**
     * 新增郑亚、郑越去程整柜提货费
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgAsiaFee:add')")
    @Log(title = "郑亚、郑越去程整柜提货费", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZgAsiaGoingFee zgAsiaGoingFee) {
        return toAjax(zgAsiaGoingFeeService.insertZgAsiaGoingFee(zgAsiaGoingFee));
    }

    /**
     * 修改郑亚、郑越去程整柜提货费
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgAsiaFee:edit')")
    @Log(title = "郑亚、郑越去程整柜提货费", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZgAsiaGoingFee zgAsiaGoingFee) {
        List<ZgAsiaGoingFee> zgAsiaGoingFeeList = zgAsiaGoingFeeService.selectZgAsiaGoingFeeList(zgAsiaGoingFee);
        if (zgAsiaGoingFeeList.size() == 0) {
            // 不存在数据的话，直接更新
            return toAjax(zgAsiaGoingFeeService.updateZgAsiaGoingFee(zgAsiaGoingFee));
        } else if (!zgAsiaGoingFeeList.get(0).getId().equals(zgAsiaGoingFee.getId())) {
            // 存在数据但是id不是当前id，说明修改后的数据已在数据库存在
            return AjaxResult.error("已存在该提货地到集货地" + zgAsiaGoingFee.getContainerType() + "提货费用设置");
        } else {
            // id一直的情况，更新当前数据
            return toAjax(zgAsiaGoingFeeService.updateZgAsiaGoingFee(zgAsiaGoingFee));
        }
    }

    /**
     * 删除郑亚、郑越去程整柜提货费
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgAsiaFee:remove')")
    @Log(title = "郑亚、郑越去程整柜提货费", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(zgAsiaGoingFeeService.deleteZgAsiaGoingFeeByIds(ids));
    }
}
