package com.szhbl.project.customerservice.controller;

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
import com.szhbl.project.customerservice.domain.BusiShippingorderGoodssh;
import com.szhbl.project.customerservice.service.IBusiShippingorderGoodsshService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 订单商品Controller
 *
 * @author b
 * @date 2020-03-27
 */
@RestController
@RequestMapping("/customerservice/goods")
public class BusiShippingorderGoodsshController extends BaseController
{
    @Autowired
    private IBusiShippingorderGoodsshService busiShippingorderGoodsshService;

    /**
     * 查询订单商品列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiShippingorderGoodssh busiShippingorderGoodssh)
    {
        startPage();
        List<BusiShippingorderGoodssh> list = busiShippingorderGoodsshService.selectBusiShippingorderGoodsshList(busiShippingorderGoodssh);
        return getDataTable(list);
    }

    /**
     * 导出订单商品列表
     */
    @PreAuthorize("@ss.hasPermi('customerservice:goods:export')")
    @Log(title = "订单商品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiShippingorderGoodssh busiShippingorderGoodssh)
    {
        List<BusiShippingorderGoodssh> list = busiShippingorderGoodsshService.selectBusiShippingorderGoodsshList(busiShippingorderGoodssh);
        ExcelUtil<BusiShippingorderGoodssh> util = new ExcelUtil<BusiShippingorderGoodssh>(BusiShippingorderGoodssh.class);
        return util.exportExcel(list, "goods");
    }

    /**
     * 获取订单商品详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:goods:query')")
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") Long goodsId)
    {
        return AjaxResult.success(busiShippingorderGoodsshService.selectBusiShippingorderGoodsshById(goodsId));
    }

    /**
     * 新增订单商品
     */
    @PreAuthorize("@ss.hasPermi('customerservice:goods:add')")
    @Log(title = "订单商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiShippingorderGoodssh busiShippingorderGoodssh)
    {
        return toAjax(busiShippingorderGoodsshService.insertBusiShippingorderGoodssh(busiShippingorderGoodssh));
    }

    /**
     * 修改订单商品
     */
    @PreAuthorize("@ss.hasPermi('customerservice:goods:edit')")
    @Log(title = "订单商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiShippingorderGoodssh busiShippingorderGoodssh)
    {
        return toAjax(busiShippingorderGoodsshService.updateBusiShippingorderGoodssh(busiShippingorderGoodssh));
    }

    /**
     * 删除订单商品
     */
    @PreAuthorize("@ss.hasPermi('customerservice:goods:remove')")
    @Log(title = "订单商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable Long[] goodsIds)
    {
        return toAjax(busiShippingorderGoodsshService.deleteBusiShippingorderGoodsshByIds(goodsIds));
    }
}
