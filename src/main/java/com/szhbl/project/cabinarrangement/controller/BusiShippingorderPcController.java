package com.szhbl.project.cabinarrangement.controller;

import java.util.List;
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
import com.szhbl.project.cabinarrangement.domain.BusiShippingorderPc;
import com.szhbl.project.cabinarrangement.service.IBusiShippingorderPcService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 订单Controller
 *
 * @author szhbl
 * @date 2020-01-15
 */
@RestController
@RequestMapping("/cabinarrangement/shippingorder")
public class BusiShippingorderPcController extends BaseController
{
    @Autowired
    private IBusiShippingorderPcService busiShippingorderService;

    /**
     * 查询订单列表
     */
//    @PreAuthorize("@ss.hasPermi('cabinarrangement:shippingorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiShippingorderPc busiShippingorderPc)
    {
        startPage();
        List<BusiShippingorderPc> list = busiShippingorderService.selectBusiShippingorderList(busiShippingorderPc);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:shippingorder:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiShippingorderPc busiShippingorderPc)
    {
        List<BusiShippingorderPc> list = busiShippingorderService.selectBusiShippingorderList(busiShippingorderPc);
        ExcelUtil<BusiShippingorderPc> util = new ExcelUtil<BusiShippingorderPc>(BusiShippingorderPc.class);
        return util.exportExcel(list, "shippingorder");
    }

    /**
     * 获取订单详细信息
     */
//    @PreAuthorize("@ss.hasPermi('cabinarrangement:shippingorder:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") String orderId)
    {
        return AjaxResult.success(busiShippingorderService.selectBusiShippingorderById(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:shippingorder:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiShippingorderPc busiShippingorderPc)
    {
        return toAjax(busiShippingorderService.insertBusiShippingorder(busiShippingorderPc));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:shippingorder:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiShippingorderPc busiShippingorderPc)
    {
        return toAjax(busiShippingorderService.updateBusiShippingorder(busiShippingorderPc));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:shippingorder:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable String[] orderIds)
    {
        return toAjax(busiShippingorderService.deleteBusiShippingorderByIds(orderIds));
    }
}
