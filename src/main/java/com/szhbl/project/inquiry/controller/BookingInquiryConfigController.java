package com.szhbl.project.inquiry.controller;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.inquiry.domain.BookingInquiryConfig;
import com.szhbl.project.inquiry.service.IBookingInquiryConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 询价系统设置Controller
 *
 * @author szhbl
 * @date 2020-10-09
 */
@RestController
@RequestMapping("/inquiryConfig")
public class BookingInquiryConfigController extends BaseController {
    @Autowired
    private IBookingInquiryConfigService bookingInquiryConfigService;

    /**
     * 查询询价系统设置列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookingInquiryConfig bookingInquiryConfig) {
        startPage();
        List<BookingInquiryConfig> list = bookingInquiryConfigService.selectBookingInquiryConfigList(bookingInquiryConfig);
        return getDataTable(list);
    }

    /**
     * 导出询价系统设置列表
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryConfig:export')")
    @Log(title = "询价系统设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BookingInquiryConfig bookingInquiryConfig) {
        List<BookingInquiryConfig> list = bookingInquiryConfigService.selectBookingInquiryConfigList(bookingInquiryConfig);
        ExcelUtil<BookingInquiryConfig> util = new ExcelUtil<BookingInquiryConfig>(BookingInquiryConfig.class);
        return util.exportExcel(list, "inquiryConfig");
    }

    /**
     * 获取询价系统设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookingInquiryConfigService.selectBookingInquiryConfigById(id));
    }

    /**
     * 获取询价系统设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryConfig:query')")
    @GetMapping("/getNextMonthInquiryConfig")
    public AjaxResult getNextMonthInquiryInfo() {
        BookingInquiryConfig param = new BookingInquiryConfig();
        param.setTypeKey("next_month_inquiry");
        List<BookingInquiryConfig> list = bookingInquiryConfigService.selectBookingInquiryConfigList(param);
        for (BookingInquiryConfig config : list) {
            config.setActive(config.getTypeValue() == 0 ? true : false);
        }
        return AjaxResult.success(list);
    }

    // lineType: 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄）
    @GetMapping("/getConfigForClient")
    public AjaxResult getNextMonthInquiryConfigForClient(Integer lineType) {
        if(lineType == null){
            lineType = 0;
        }
        return AjaxResult.success(bookingInquiryConfigService.selectBookingInquiryConfigByKey("next_month_inquiry", lineType));
    }

    /**
     * 新增询价系统设置
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryConfig:add')")
    @Log(title = "询价系统设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookingInquiryConfig bookingInquiryConfig) {
        return toAjax(bookingInquiryConfigService.insertBookingInquiryConfig(bookingInquiryConfig));
    }

    /**
     * 修改询价系统设置
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryConfig:edit')")
    @Log(title = "询价系统设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookingInquiryConfig bookingInquiryConfig) {
        bookingInquiryConfig.setUpdateBy(SecurityUtils.getUsername());
        bookingInquiryConfig.setUpdateTime(new Date());
        return toAjax(bookingInquiryConfigService.updateBookingInquiryConfig(bookingInquiryConfig));
    }

    /**
     * 删除询价系统设置
     */
    @PreAuthorize("@ss.hasPermi('inquiry:inquiryConfig:remove')")
    @Log(title = "询价系统设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookingInquiryConfigService.deleteBookingInquiryConfigByIds(ids));
    }
}
