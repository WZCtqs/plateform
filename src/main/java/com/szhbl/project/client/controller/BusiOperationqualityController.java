package com.szhbl.project.client.controller;

import java.util.Date;
import java.util.List;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.client.domain.BusiOperationquality;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.client.service.IBusiOperationqualityService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 操作质量投诉Controller
 *
 * @author jhm
 * @date 2020-01-13
 */
@RestController
@RequestMapping("/clients/operationquality")
public class BusiOperationqualityController extends BaseController
{
    @Autowired
    private IBusiOperationqualityService busiOperationqualityService;

    /**
     * 查询操作质量投诉列表
     */
//    @PreAuthorize("@ss.hasPermi('clients:operationquality:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiOperationquality busiOperationquality)
    {
        startPage();
        List<BusiOperationquality> list = busiOperationqualityService.selectBusiOperationqualityList(busiOperationquality);
        return getDataTable(list);
    }

    /**
     * 导出操作质量投诉列表
     */
    @PreAuthorize("@ss.hasPermi('clients:operationquality:export')")
    @Log(title = "操作质量投诉", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiOperationquality busiOperationquality)
    {
        String deptCode = busiOperationquality.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (busiOperationquality.getDeptCode().contains("YWB")) {
                if (busiOperationquality.getDeptCode().length() > 15) {
                    busiOperationquality.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    busiOperationquality.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    busiOperationquality.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    busiOperationquality.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    busiOperationquality.setReadType("5");
                } else {
                    busiOperationquality.setReadType("1");
                }
            }
        }
        List<BusiOperationquality> list = busiOperationqualityService.selectBusiOperationqualityList(busiOperationquality);
        ExcelUtil<BusiOperationquality> util = new ExcelUtil<BusiOperationquality>(BusiOperationquality.class);
        String date = DateUtils.getDate();
        return util.exportExcel(list, date+"客户满意度调查");
    }

    /**
     * 获取操作质量投诉详细信息
     */
//    @PreAuthorize("@ss.hasPermi('clients:operationquality:query')")
    @GetMapping(value = "/{qualityId}")
    public AjaxResult getInfo(@PathVariable("qualityId") String qualityId)
    {
        return AjaxResult.success(busiOperationqualityService.selectBusiOperationqualityById(qualityId));
    }

    /**
     * 新增操作质量投诉
     */
    @PreAuthorize("@ss.hasPermi('clients:operationquality:add')")
    @Log(title = "操作质量投诉", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiOperationquality busiOperationquality)
    {
        busiOperationquality.setOperator(SecurityUtils.getUsername());
        return toAjax(busiOperationqualityService.insertBusiOperationquality(busiOperationquality));
    }

    /**
     * 修改操作质量投诉
     */
    @PreAuthorize("@ss.hasPermi('clients:operationquality:edit')")
    @Log(title = "操作质量投诉", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiOperationquality busiOperationquality)
    {
        busiOperationquality.setOperator(SecurityUtils.getUsername());
        busiOperationquality.setSatisfactionDate(new Date());
        return toAjax(busiOperationqualityService.updateBusiOperationquality(busiOperationquality));
    }

    /**
     * 删除操作质量投诉
     */
    @PreAuthorize("@ss.hasPermi('clients:operationquality:remove')")
    @Log(title = "操作质量投诉", businessType = BusinessType.DELETE)
	@DeleteMapping("/{qualityIds}")
    public AjaxResult remove(@PathVariable String[] qualityIds)
    {
        return toAjax(busiOperationqualityService.deleteBusiOperationqualityByIds(qualityIds));
    }
}
