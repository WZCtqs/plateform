package com.szhbl.project.customerservice.controller;

import java.util.List;

import com.szhbl.project.customerservice.domain.BusiClassessh;
import com.szhbl.project.customerservice.service.IBusiClassesshService;
import com.szhbl.project.customerservice.service.ITrackGoodsStatusshService;
import com.szhbl.project.customerservice.vo.InfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import com.szhbl.project.customerservice.domain.BusiShippingordersh;
import com.szhbl.project.customerservice.service.IBusiShippingordershService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 订单Controller
 *
 * @author b
 * @date 2020-03-27
 */
@RestController
@RequestMapping("/customerservice/shippingorder")
@Api(tags = " 售后",description = "信息查询")
public class BusiShippingordershController extends BaseController
{
    @Autowired
    private IBusiShippingordershService busiShippingordershService;
    @Autowired
    private ITrackGoodsStatusshService trackGoodsStatusshService;
    @Autowired
    private IBusiClassesshService busiClassesshService;

    /**
     * 查询订单列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:shippingorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiShippingordersh busiShippingordersh)
    {
        startPage();
        List<BusiShippingordersh> list = busiShippingordershService.selectBusiShippingordershList(busiShippingordersh);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('customerservice:shippingorder:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiShippingordersh busiShippingordersh)
    {
        List<BusiShippingordersh> list = busiShippingordershService.selectBusiShippingordershList(busiShippingordersh);
        ExcelUtil<BusiShippingordersh> util = new ExcelUtil<BusiShippingordersh>(BusiShippingordersh.class);
        return util.exportExcel(list, "shippingorder");
    }

    /**
     * 获取订单详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:shippingorder:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") String orderId)
    {
        return AjaxResult.success(busiShippingordershService.selectBusiShippingordershById(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('customerservice:shippingorder:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiShippingordersh busiShippingordersh)
    {
        return toAjax(busiShippingordershService.insertBusiShippingordersh(busiShippingordersh));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('customerservice:shippingorder:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiShippingordersh busiShippingordersh)
    {
        return toAjax(busiShippingordershService.updateBusiShippingordersh(busiShippingordersh));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('customerservice:shippingorder:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable String[] orderIds)
    {
        return toAjax(busiShippingordershService.deleteBusiShippingordershByIds(orderIds));
    }

    //售后模信息查询
    @GetMapping("/getInfoMsg")
    @ApiOperation("根据舱位号查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "舱位号",name = "orderNumber",paramType = "query",dataType = "String",required = true)
           })
    public AjaxResult getInfoMsg(String orderNumber)
    {
        InfoVo infoVo = busiShippingordershService.selectInfo(orderNumber);
        if (infoVo!=null){
           BusiClassessh busiClassessh = busiClassesshService.selectBusiClassesshById(infoVo.getClassId());
           infoVo.setTime(busiClassessh.getClassTransporttime());
        }
        return AjaxResult.success(infoVo);
    }
}
