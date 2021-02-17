package com.szhbl.project.inquiry.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetailsBackup;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订舱询价-拼箱货物详情草稿Controller
 *
 * @author szhbl
 * @date 2020-07-10
 */
@RestController
@RequestMapping("/inquiry/inquiryGoodsDetailsBackup")
public class BookingInquiryGoodsDetailsBackupController extends BaseController {
    @Autowired
    private IBookingInquiryGoodsDetailsBackupService bookingInquiryGoodsDetailsBackupService;

    /**
     * 查询订舱询价-拼箱货物详情草稿列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup) {
        startPage();
        List<BookingInquiryGoodsDetailsBackup> list = bookingInquiryGoodsDetailsBackupService.selectBookingInquiryGoodsDetailsBackupList(bookingInquiryGoodsDetailsBackup);
        return getDataTable(list);
    }

    /**
     * 导出订舱询价-拼箱货物详情草稿列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:export')")
    @Log(title = "订舱询价-拼箱货物详情草稿", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup) {
        List<BookingInquiryGoodsDetailsBackup> list = bookingInquiryGoodsDetailsBackupService.selectBookingInquiryGoodsDetailsBackupList(bookingInquiryGoodsDetailsBackup);
        ExcelUtil<BookingInquiryGoodsDetailsBackup> util = new ExcelUtil<BookingInquiryGoodsDetailsBackup>(BookingInquiryGoodsDetailsBackup.class);
        return util.exportExcel(list, "inquiryBackup");
    }

    /**
     * 获取订舱询价-拼箱货物详情草稿详细信息
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookingInquiryGoodsDetailsBackupService.selectBookingInquiryGoodsDetailsBackupById(id));
    }

    /**
     * 新增订舱询价-拼箱货物详情草稿
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:add')")
    @Log(title = "订舱询价-拼箱货物详情草稿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup) {
        return toAjax(bookingInquiryGoodsDetailsBackupService.insertBookingInquiryGoodsDetailsBackup(bookingInquiryGoodsDetailsBackup));
    }

    /**
     * 修改订舱询价-拼箱货物详情草稿
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:edit')")
    @Log(title = "订舱询价-拼箱货物详情草稿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup) {
        return toAjax(bookingInquiryGoodsDetailsBackupService.updateBookingInquiryGoodsDetailsBackup(bookingInquiryGoodsDetailsBackup));
    }

    /**
     * 删除订舱询价-拼箱货物详情草稿
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:remove')")
    @Log(title = "订舱询价-拼箱货物详情草稿", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookingInquiryGoodsDetailsBackupService.deleteBookingInquiryGoodsDetailsBackupByIds(ids));
    }
}
