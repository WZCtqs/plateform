package com.szhbl.project.customerservice.controller;

import java.util.List;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.project.customerservice.service.IBusiShippingordershService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.szhbl.project.customerservice.domain.CompensationTracking;
import com.szhbl.project.customerservice.service.ICompensationTrackingService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 赔款跟踪Controller
 *
 * @author bxt
 * @date 2020-03-30
 */
@RestController
@RequestMapping("/customerservice/tracking")
public class CompensationTrackingController extends BaseController
{
    @Autowired
    private ICompensationTrackingService compensationTrackingService;
    @Autowired
    private IBusiShippingordershService busiShippingordershService;

    /**
     * 查询赔款跟踪列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:tracking:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompensationTracking compensationTracking)
    {
        startPage();
       /* LoginUser loginUser = SecurityUtils.getLoginUser();
        if ("1".equals(loginUser.getUser().getReferenceType())){
            compensationTracking.setClientTjrId(loginUser.getUser().getTjrId());
        }*/
        compensationTracking.setDeptCode(SecurityUtils.getLoginUser().getUser().getDeptCode());
        compensationTracking.setUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        List<CompensationTracking> list = compensationTrackingService.selectCompensationTrackingList(compensationTracking);
        return getDataTable(list);
    }

    /**
     * 导出赔款跟踪列表
     */
    @PreAuthorize("@ss.hasPermi('afterSaleManage:tracking:export')")
    @Log(title = "赔款跟踪", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CompensationTracking compensationTracking)
    {
        List<CompensationTracking> list = compensationTrackingService.selectCompensationTrackingList(compensationTracking);
        /*for (CompensationTracking c:list){
            if ("0".equals(c.getComplaintsType())){
                c.setComplaintsType("货损");
            }else if ("1".equals(c.getComplaintsType())){
                c.setComplaintsType("包装破损 ");
            }else if ("2".equals(c.getComplaintsType())){
                c.setComplaintsType("货少 ");
            }else if ("3".equals(c.getComplaintsType())){
                c.setComplaintsType("延期 ");
            }else if ("4".equals(c.getComplaintsType())){
                c.setComplaintsType("费用争议 ");
            }else if ("5".equals(c.getComplaintsType())){
                c.setComplaintsType("操作失误 ");
            }else if ("6".equals(c.getComplaintsType())){
                c.setComplaintsType("其他 ");
            }
        }*/
        ExcelUtil<CompensationTracking> util = new ExcelUtil<CompensationTracking>(CompensationTracking.class);
        return util.exportExcel(list, "tracking");
    }

    /**
     * 获取赔款跟踪详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:tracking:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(compensationTrackingService.selectCompensationTrackingById(id));
    }

    /**
     * 新增赔款跟踪
     */
    @PreAuthorize("@ss.hasPermi('customerservice:tracking:add')")
    @Log(title = "赔款跟踪", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompensationTracking compensationTracking)
    {
        return toAjax(compensationTrackingService.insertCompensationTracking(compensationTracking));
    }

    /**
     * 修改赔款跟踪
     */
    @PreAuthorize("@ss.hasPermi('customerservice:tracking:edit')")
    @Log(title = "赔款跟踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompensationTracking compensationTracking)
    {
        return toAjax(compensationTrackingService.updateCompensationTracking(compensationTracking));
    }

    /**
     * 删除赔款跟踪
     */
    @PreAuthorize("@ss.hasPermi('customerservice:tracking:remove')")
    @Log(title = "赔款跟踪", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(compensationTrackingService.deleteCompensationTrackingByIds(ids));
    }
    /*导入afterSaleManage:tracking:import*/
    @PreAuthorize("@ss.hasPermi('afterSaleManage:tracking:import')")
    @PostMapping("/importData")
    @Transactional
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<CompensationTracking> util = new ExcelUtil<CompensationTracking>(CompensationTracking.class);
        List<CompensationTracking> list = util.importExcel(file.getInputStream());
        for (CompensationTracking c:list){
            CompensationTracking c1 = new CompensationTracking();
            c1.setOrderNumber(c.getOrderNumber());
            List<CompensationTracking> list1 = compensationTrackingService.selectCompensationTrackingList(c1);
            if (list1.size()==0) {
                c.setOrderId(busiShippingordershService.selectOrderId(c.getOrderNumber()));
                int a = compensationTrackingService.insertCompensationTracking(c);
                if (a==0){
                    return AjaxResult.error("导入失败,请检查数据格式");
                }
            }
        }
        return AjaxResult.success();
    }
}
