package com.szhbl.project.order.controller;

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
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.service.IBusiShippingorderGoodsService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 订单商品Controller
 *
 * @author dps
 * @date 2020-03-24
 */
@RestController
@RequestMapping("/order/goods")
public class BusiShippingorderGoodsController extends BaseController
{
    @Autowired
    private IBusiShippingorderGoodsService busiShippingorderGoodsService;

    /**
     * 查询订单商品列表
     */
//    @PreAuthorize("@ss.hasPermi('order:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiShippingorderGoods busiShippingorderGoods)
    {
        startPage();
        List<BusiShippingorderGoods> list = busiShippingorderGoodsService.selectBusiShippingorderGoodsList(busiShippingorderGoods);
        return getDataTable(list);
    }

    /**
     * 导出订单商品列表
     */
    @PreAuthorize("@ss.hasPermi('order:goods:export')")
    @Log(title = "订单商品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiShippingorderGoods busiShippingorderGoods)
    {
        List<BusiShippingorderGoods> list = busiShippingorderGoodsService.selectBusiShippingorderGoodsList(busiShippingorderGoods);
        ExcelUtil<BusiShippingorderGoods> util = new ExcelUtil<BusiShippingorderGoods>(BusiShippingorderGoods.class);
        return util.exportExcel(list, "goods");
    }

    /**
     * 获取订单商品详细信息
     */
//    @PreAuthorize("@ss.hasPermi('order:goods:query')")
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") String goodsId)
    {
        return AjaxResult.success(busiShippingorderGoodsService.selectBusiShippingorderGoodsById(goodsId));
    }

    /**
     * 新增订单商品
     */
    @PreAuthorize("@ss.hasPermi('order:goods:add')")
    @Log(title = "订单商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiShippingorderGoods busiShippingorderGoods)
    {
        return toAjax(busiShippingorderGoodsService.insertBusiShippingorderGoods(busiShippingorderGoods));
    }

    /**
     * 修改订单商品
     */
    @PreAuthorize("@ss.hasPermi('order:goods:edit')")
    @Log(title = "订单商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiShippingorderGoods busiShippingorderGoods)
    {
        return toAjax(busiShippingorderGoodsService.updateBusiShippingorderGoods(busiShippingorderGoods));
    }

    /**
     * 删除订单商品
     */
    @PreAuthorize("@ss.hasPermi('order:goods:remove')")
    @Log(title = "订单商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable String[] goodsIds)
    {
        return toAjax(busiShippingorderGoodsService.deleteBusiShippingorderGoodsByIds(goodsIds));
    }
}
