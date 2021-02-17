package com.szhbl.project.inquiry.controller;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.inquiry.convert.BookingInquiryGoodDetailFormConvert;
import com.szhbl.project.inquiry.domain.BookingInquiryBackup;
import com.szhbl.project.inquiry.form.BookingInquiryGoodDetailForm;
import com.szhbl.project.inquiry.service.IBookingInquiryBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订舱询价草稿Controller
 *
 * @author szhbl
 * @date 2020-07-10
 */
@RestController
@RequestMapping("/inquiry/inquiryBackup")
public class BookingInquiryBackupController extends BaseController {
    @Autowired
    private IBookingInquiryBackupService bookingInquiryBackupService;

    /**
     * 查询订舱询价草稿列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookingInquiryBackup bookingInquiryBackup) {
        startPage();
        List<BookingInquiryBackup> list = bookingInquiryBackupService.selectBookingInquiryBackupList(bookingInquiryBackup);
        return getDataTable(list);
    }

    /**
     * 导出订舱询价草稿列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:export')")
    @Log(title = "订舱询价草稿", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BookingInquiryBackup bookingInquiryBackup) {
        List<BookingInquiryBackup> list = bookingInquiryBackupService.selectBookingInquiryBackupList(bookingInquiryBackup);
        ExcelUtil<BookingInquiryBackup> util = new ExcelUtil<BookingInquiryBackup>(BookingInquiryBackup.class);
        return util.exportExcel(list, "inquiryBackup");
    }

    /**
     * 获取订舱询价草稿详细信息
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookingInquiryBackupService.selectBookingInquiryBackupById(id));
    }

    /**
     * 新增订舱询价草稿
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:add')")
    @Log(title = "订舱询价草稿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookingInquiryGoodDetailForm bookingInquiryGoodDetailForm) {
        BookingInquiryBackup bookingInquiryBackup = BookingInquiryGoodDetailFormConvert.convertBackup(bookingInquiryGoodDetailForm);
        if (StringUtils.isEmpty(bookingInquiryBackup.getClientType())) {
            bookingInquiryBackup.setClientType("0");
        }

        return toAjax(bookingInquiryBackupService.insertBookingInquiryBackup(bookingInquiryBackup));
    }

    /**
     * 修改订舱询价草稿
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:edit')")
    @Log(title = "订舱询价草稿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookingInquiryBackup bookingInquiryBackup) {
        return toAjax(bookingInquiryBackupService.updateBookingInquiryBackup(bookingInquiryBackup));
    }

    /**
     * 删除订舱询价草稿
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryBackup:remove')")
    @Log(title = "订舱询价草稿", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookingInquiryBackupService.deleteBookingInquiryBackupByIds(ids));
    }
}
