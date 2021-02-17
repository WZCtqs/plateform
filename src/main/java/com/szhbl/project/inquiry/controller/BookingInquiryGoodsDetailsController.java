package com.szhbl.project.inquiry.controller;

import java.util.List;

import io.swagger.annotations.Api;
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
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 订舱询价-拼箱货物详情Controller
 *
 * @author jhm
 * @date 2020-04-03
 */
@RestController
@RequestMapping("/inquiry/goodsDetails")
@Api(tags = "订舱询价-拼箱货物详情")
public class BookingInquiryGoodsDetailsController extends BaseController
{
    @Autowired
    private IBookingInquiryGoodsDetailsService bookingInquiryGoodsDetailsService;

    /**
     * 查询订舱询价-拼箱货物详情列表
     */
//    @PreAuthorize("@ss.hasPermi('inquiry:goodsDetails:list')")
    @GetMapping("/list")
    @ApiOperation("查询订舱询价-拼箱货物详情列表")
    public TableDataInfo list(BookingInquiryGoodsDetails bookingInquiryGoodsDetails)
    {
        startPage();
        List<BookingInquiryGoodsDetails> list = bookingInquiryGoodsDetailsService.selectBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetails);
        return getDataTable(list);
    }

    /**
     * 导出订舱询价-拼箱货物详情列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:goodsDetails:export')")
    @Log(title = "订舱询价-拼箱货物详情", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出订舱询价-拼箱货物详情列表")
    public AjaxResult export(BookingInquiryGoodsDetails bookingInquiryGoodsDetails)
    {
        List<BookingInquiryGoodsDetails> list = bookingInquiryGoodsDetailsService.selectBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetails);
        ExcelUtil<BookingInquiryGoodsDetails> util = new ExcelUtil<BookingInquiryGoodsDetails>(BookingInquiryGoodsDetails.class);
        return util.exportExcel(list, "goodsDetails");
    }

    /**
     * 获取订舱询价-拼箱货物详情详细信息
     */
//    @PreAuthorize("@ss.hasPermi('inquiry:goodsDetails:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取订舱询价-拼箱货物详情详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bookingInquiryGoodsDetailsService.selectBookingInquiryGoodsDetailsById(id));
    }

    /**
     * 新增订舱询价-拼箱货物详情
     */
    @PreAuthorize("@ss.hasPermi('inquiry:goodsDetails:add')")
    @Log(title = "订舱询价-拼箱货物详情", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增订舱询价-拼箱货物详情")
    public AjaxResult add(@RequestBody BookingInquiryGoodsDetails bookingInquiryGoodsDetails)
    {
        return toAjax(bookingInquiryGoodsDetailsService.insertBookingInquiryGoodsDetails(bookingInquiryGoodsDetails));
    }

    /**
     * 修改订舱询价-拼箱货物详情
     */
    @PreAuthorize("@ss.hasPermi('inquiry:goodsDetails:edit')")
    @Log(title = "订舱询价-拼箱货物详情", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改订舱询价-拼箱货物详情")
    public AjaxResult edit(@RequestBody BookingInquiryGoodsDetails bookingInquiryGoodsDetails)
    {
        return toAjax(bookingInquiryGoodsDetailsService.updateBookingInquiryGoodsDetails(bookingInquiryGoodsDetails));
    }

    /**
     * 删除订舱询价-拼箱货物详情
     */
    @PreAuthorize("@ss.hasPermi('inquiry:goodsDetails:remove')")
    @Log(title = "订舱询价-拼箱货物详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除订舱询价-拼箱货物详情")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookingInquiryGoodsDetailsService.deleteBookingInquiryGoodsDetailsByIds(ids));
    }
}
