package com.szhbl.project.inquiry.controller;

import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.inquiry.domain.BookingInquiryOrder;
import com.szhbl.project.inquiry.service.IBookingInquiryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查询托书历史询价信息Controller
 *
 * @author szhbl
 * @date 2020-06-30
 */
@RestController
@RequestMapping("/inquiry/inquiryOrder")
public class BookingInquiryOrderController extends BaseController {
    @Autowired
    private IBookingInquiryOrderService bookingInquiryOrderService;

    /**
     * 查询查询托书历史询价信息列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookingInquiryOrder bookingInquiryOrder) {
        startPage();
        List<BookingInquiryOrder> list = bookingInquiryOrderService.selectBookingInquiryOrderList(bookingInquiryOrder);
        return getDataTable(list);
    }

    /**
     * 新增查询托书历史询价信息
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryOrder:add')")
    @Log(title = "查询托书历史询价信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookingInquiryOrder bookingInquiryOrder) {
        return toAjax(bookingInquiryOrderService.insertBookingInquiryOrder(bookingInquiryOrder));
    }

    /**
     * 修改查询托书历史询价信息
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryOrder:edit')")
    @Log(title = "查询托书历史询价信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookingInquiryOrder bookingInquiryOrder) {
        return toAjax(bookingInquiryOrderService.updateBookingInquiryOrder(bookingInquiryOrder));
    }

}
