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
import com.szhbl.project.cabinarrangement.domain.BusiShippingorderGoodsPc;
import com.szhbl.project.cabinarrangement.service.IBusiShippingorderGoodsPcService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 订单商品Controller
 *
 * @author szhbl
 * @date 2020-01-15
 */
@RestController
@RequestMapping("/cabinarrangement/goods")
public class BusiShippingorderGoodsPcController extends BaseController
{
    @Autowired
    private IBusiShippingorderGoodsPcService busiShippingorderGoodsService;

    /**
     * 查询订单商品列表
     */
//    @PreAuthorize("@ss.hasPermi('cabinarrangement:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiShippingorderGoodsPc busiShippingorderGoodsPc)
    {
        startPage();
        List<BusiShippingorderGoodsPc> list = busiShippingorderGoodsService.selectBusiShippingorderGoodsList(busiShippingorderGoodsPc);
        return getDataTable(list);
    }

    /**
     * 导出订单商品列表
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:goods:export')")
    @Log(title = "订单商品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiShippingorderGoodsPc busiShippingorderGoodsPc)
    {
        List<BusiShippingorderGoodsPc> list = busiShippingorderGoodsService.selectBusiShippingorderGoodsList(busiShippingorderGoodsPc);
        ExcelUtil<BusiShippingorderGoodsPc> util = new ExcelUtil<BusiShippingorderGoodsPc>(BusiShippingorderGoodsPc.class);
        return util.exportExcel(list, "goods");
    }

    /**
     * 获取订单商品详细信息
     */
//    @PreAuthorize("@ss.hasPermi('cabinarrangement:goods:query')")
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") String goodsId)
    {
        return AjaxResult.success(busiShippingorderGoodsService.selectBusiShippingorderGoodsById(goodsId));
    }

    /**
     * 新增订单商品
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:goods:add')")
    @Log(title = "订单商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiShippingorderGoodsPc busiShippingorderGoodsPc)
    {
        return toAjax(busiShippingorderGoodsService.insertBusiShippingorderGoods(busiShippingorderGoodsPc));
    }

    /**
     * 修改订单商品
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:goods:edit')")
    @Log(title = "订单商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiShippingorderGoodsPc busiShippingorderGoodsPc)
    {
        return toAjax(busiShippingorderGoodsService.updateBusiShippingorderGoods(busiShippingorderGoodsPc));
    }

    /**
     * 删除订单商品
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:goods:remove')")
    @Log(title = "订单商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable String[] goodsIds)
    {
        return toAjax(busiShippingorderGoodsService.deleteBusiShippingorderGoodsByIds(goodsIds));
    }
}
