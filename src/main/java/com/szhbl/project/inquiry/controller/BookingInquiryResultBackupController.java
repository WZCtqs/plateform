package com.szhbl.project.inquiry.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.inquiry.domain.*;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsBackupService;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsService;
import com.szhbl.project.inquiry.service.IBookingInquiryResultBackupService;
import com.szhbl.project.inquiry.service.IBookingInquiryResultService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 询价反馈结果数据草稿Controller
 *
 * @author szhbl
 * @date 2020-07-10
 */
@RestController
@RequestMapping("/inquiry/inquiryResultBackup")
public class BookingInquiryResultBackupController extends BaseController {
    @Autowired
    private IBookingInquiryResultBackupService bookingInquiryResultBackupService;
    @Autowired
    private IBookingInquiryGoodsDetailsService bookingInquiryGoodsDetailsService;
    @Autowired
    private IBookingInquiryResultService bookingInquiryResultService;
    @Autowired
    private IBookingInquiryGoodsDetailsBackupService bookingInquiryGoodsDetailsBackupService;

    /**
     * 查询询价反馈结果数据草稿列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BookingInquiryResultBackup bookingInquiryResultBackup) {
        startPage();
        List<BookingInquiryResultBackup> list =
                bookingInquiryResultBackupService.selectBookingInquiryResultBackupList(
                        bookingInquiryResultBackup);
        return getDataTable(list);
    }

    /**
     * 导出询价反馈结果数据草稿列表
     */
    @Log(title = "询价反馈结果数据草稿", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BookingInquiryResultBackup bookingInquiryResultBackup) {
        List<BookingInquiryResultBackup> list =
                bookingInquiryResultBackupService.selectBookingInquiryResultBackupList(
                        bookingInquiryResultBackup);
        ExcelUtil<BookingInquiryResultBackup> util =
                new ExcelUtil<BookingInquiryResultBackup>(BookingInquiryResultBackup.class);
        return util.exportExcel(list, "inquiryBackup");
    }

    /**
     * 获取询价反馈结果数据详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取询价反馈结果数据详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id, String newInquiry) {
//        if ("1".equals(newInquiry)) {
        if (1 == 1) {
            BookingInquiryResultBackup bookingInquiryResultBackup =
                    bookingInquiryResultBackupService.selectBookingInquiryResultBackupById(id);
            long inquiryId = bookingInquiryResultBackup.getInquiryId();
            // 根据id查询拼箱详细信息
            BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup = new BookingInquiryGoodsDetailsBackup();
            bookingInquiryGoodsDetailsBackup.setInquiryId(inquiryId);
            List<BookingInquiryGoodsDetailsBackup> bookingInquiryGoodsDetailsList =
                    bookingInquiryGoodsDetailsBackupService.selectBookingInquiryGoodsDetailsBackupList(
                            bookingInquiryGoodsDetailsBackup);
            BookingInquiryBackup bookingInquiryBackup = bookingInquiryResultBackup.getBookingInquiry();
            bookingInquiryBackup.setBookingInquiryGoodsDetailsBackupList(bookingInquiryGoodsDetailsList);
            bookingInquiryResultBackup.setBookingInquiry(bookingInquiryBackup);
            return AjaxResult.success(bookingInquiryResultBackup);
        } else {
            BookingInquiryResult bookingInquiryResult =
                    bookingInquiryResultService.selectBookingInquiryResultById(id);
            long inquiryId = bookingInquiryResult.getInquiryId();
            // 根据id查询拼箱详细信息
            BookingInquiryGoodsDetails bookingInquiryGoodsDetails = new BookingInquiryGoodsDetails();
            bookingInquiryGoodsDetails.setInquiryId(inquiryId);
            List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList =
                    bookingInquiryGoodsDetailsService.selectBookingInquiryGoodsDetailsList(
                            bookingInquiryGoodsDetails);
            BookingInquiry bookingInquiry = bookingInquiryResult.getBookingInquiry();
            bookingInquiry.setBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetailsList);
            bookingInquiryResult.setBookingInquiry(bookingInquiry);
            return AjaxResult.success(bookingInquiryResult);
        }
    }

    /**
     * 新增询价反馈结果数据草稿
     */
    @Log(title = "询价反馈结果数据草稿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookingInquiryResultBackup bookingInquiryResultBackup) {
        return toAjax(
                bookingInquiryResultBackupService.insertBookingInquiryResultBackup(
                        bookingInquiryResultBackup));
    }

    /**
     * 修改询价反馈结果数据草稿
     */
    @Log(title = "询价反馈结果数据草稿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookingInquiryResultBackup bookingInquiryResultBackup) {
        return toAjax(
                bookingInquiryResultBackupService.updateBookingInquiryResultBackup(
                        bookingInquiryResultBackup));
    }

    /**
     * 删除询价反馈结果数据草稿
     */
    @Log(title = "询价反馈结果数据草稿", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookingInquiryResultBackupService.deleteBookingInquiryResultBackupByIds(ids));
    }
}
