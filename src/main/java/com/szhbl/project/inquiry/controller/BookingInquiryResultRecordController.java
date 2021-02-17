package com.szhbl.project.inquiry.controller;

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
import com.szhbl.project.inquiry.domain.BookingInquiryResultRecord;
import com.szhbl.project.inquiry.service.IBookingInquiryResultRecordService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 询价结果费用修改记录Controller
 * 
 * @author szhbl
 * @date 2021-01-20
 */
@RestController
@RequestMapping("/inquiry/record")
public class BookingInquiryResultRecordController extends BaseController
{
    @Autowired
    private IBookingInquiryResultRecordService bookingInquiryResultRecordService;

    /**
     * 查询询价结果费用修改记录列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookingInquiryResultRecord bookingInquiryResultRecord)
    {
        startPage();
        List<BookingInquiryResultRecord> list = bookingInquiryResultRecordService.selectBookingInquiryResultRecordList(bookingInquiryResultRecord);
        return getDataTable(list);
    }

    /**
     * 导出询价结果费用修改记录列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:record:export')")
    @Log(title = "询价结果费用修改记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BookingInquiryResultRecord bookingInquiryResultRecord)
    {
        List<BookingInquiryResultRecord> list = bookingInquiryResultRecordService.selectBookingInquiryResultRecordList(bookingInquiryResultRecord);
        ExcelUtil<BookingInquiryResultRecord> util = new ExcelUtil<BookingInquiryResultRecord>(BookingInquiryResultRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 获取询价结果费用修改记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('inquiry:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bookingInquiryResultRecordService.selectBookingInquiryResultRecordById(id));
    }

    /**
     * 新增询价结果费用修改记录
     */
    @PreAuthorize("@ss.hasPermi('inquiry:record:add')")
    @Log(title = "询价结果费用修改记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookingInquiryResultRecord bookingInquiryResultRecord)
    {
        return toAjax(bookingInquiryResultRecordService.insertBookingInquiryResultRecord(bookingInquiryResultRecord));
    }

    /**
     * 修改询价结果费用修改记录
     */
    @PreAuthorize("@ss.hasPermi('inquiry:record:edit')")
    @Log(title = "询价结果费用修改记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookingInquiryResultRecord bookingInquiryResultRecord)
    {
        return toAjax(bookingInquiryResultRecordService.updateBookingInquiryResultRecord(bookingInquiryResultRecord));
    }

    /**
     * 删除询价结果费用修改记录
     */
    @PreAuthorize("@ss.hasPermi('inquiry:record:remove')")
    @Log(title = "询价结果费用修改记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookingInquiryResultRecordService.deleteBookingInquiryResultRecordByIds(ids));
    }
}
