package com.szhbl.project.inquiry.controller;

import com.szhbl.common.utils.BeanChangeUtil;
import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.redis.RedisCache;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.inquiry.convert.BookingInquiryGoodDetailFormConvert;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.form.BookingInquiryGoodDetailForm;
import com.szhbl.project.inquiry.handler.common.InquiryProvider;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsService;
import com.szhbl.project.inquiry.service.IBookingInquiryResultService;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 订舱询价Controller
 *
 * @author jhm
 * @date 2020-04-01
 */
@RestController
@RequestMapping("/inquiry/order")
@Api(tags = "订舱询价")
public class BookingInquiryController extends BaseController {
    @Autowired
    private IBookingInquiryService bookingInquiryService;
    @Autowired
    private IBookingInquiryGoodsDetailsService bookingInquiryGoodsDetailsService;
    @Autowired
    private IBookingInquiryResultService bookingInquiryResultService;
    @Autowired
    private InquiryProvider inquiryProvider;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询订舱询价列表
     */
    @GetMapping("/list")
    @ApiOperation("查询订舱询价列表")
    public TableDataInfo list(BookingInquiry bookingInquiry) {
        startPage();
        String tjrId = SecurityUtils.getLoginUser().getUser().getTjrId();
        if (!"0".equals(tjrId)) {
            bookingInquiry.setClientTjrId(tjrId);
        }
        List<BookingInquiry> list = bookingInquiryService.selectBookingInquiryList(bookingInquiry);
        return getDataTable(list);
    }

    /**
     * 导出订舱询价列表
     */
    @Log(title = "订舱询价", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出订舱询价列表")
    public AjaxResult export(BookingInquiry bookingInquiry)
    {
        List<BookingInquiry> list = bookingInquiryService.selectBookingInquiryList(bookingInquiry);
        ExcelUtil<BookingInquiry> util = new ExcelUtil<BookingInquiry>(BookingInquiry.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 获取订舱询价详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取订舱询价详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        BookingInquiry bookingInquiry = bookingInquiryService.selectBookingInquiryById(id);
        //根据询价查询对应的询价详情
        bookingInquiry.setBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetailsService.selectBookingInquiryGoodsDetailsWithInquiryId(bookingInquiry.getId()));
        return AjaxResult.success(bookingInquiry);
    }

    /**
     * 新增订舱询价
     */
    @Log(title = "订舱询价", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增订舱询价")
    public AjaxResult add(@RequestBody BookingInquiryGoodDetailForm bookingInquiryGoodDetailForm)
    {
        //拦截判断同一个客户询价短时间内是否相同
        BookingInquiryGoodDetailForm old = redisCache.getCacheObject("BOOKING_INQUIRY:"+bookingInquiryGoodDetailForm.getClientId());
        if (StringUtils.isNotNull(old)) {
            BeanChangeUtil<BookingInquiryGoodDetailForm> bc = new BeanChangeUtil<>();
            String c = bc.contrastObj(old,bookingInquiryGoodDetailForm);
            if (StringUtils.isNotEmpty(c)) {
                logger.debug(bookingInquiryGoodDetailForm.getClientUnit() + "一分钟内询价的数据差异，{}",c);
            } else {
                return AjaxResult.error("en".equals(bookingInquiryGoodDetailForm.getLanguage()) ?
                        "There are repeated inquiries within a short time, please try again later":"短时间内有重复询价，请稍后再试");
            }
        }
        redisCache.setCacheObject("BOOKING_INQUIRY:"+bookingInquiryGoodDetailForm.getClientId(),bookingInquiryGoodDetailForm,1, TimeUnit.MINUTES);

        BookingInquiry bookingInquiry = BookingInquiryGoodDetailFormConvert.convert(bookingInquiryGoodDetailForm);
        if (StringUtils.isEmpty(bookingInquiry.getClientType())) {
            bookingInquiry.setClientType("0");
        }
        return bookingInquiryService.insertBookingInquiry(bookingInquiry);
    }

    /**
     * 修改订舱询价
     */
//    @PreAuthorize("@ss.hasPermi('inquiry:order:edit')")
    @Log(title = "订舱询价", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改订舱询价")
    public AjaxResult edit(@RequestBody BookingInquiry bookingInquiry) {
        return toAjax(bookingInquiryService.updateBookingInquiry(bookingInquiry));
    }

    /**
     * 删除订舱询价
     */
    @Log(title = "订舱询价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除订舱询价")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookingInquiryService.deleteBookingInquiryByIds(ids));
    }

    /**
     * 判断该询价下面的询价方案是否都已报完价
     */
    @GetMapping("/judge")
    @ApiOperation("判断询价是否已经报完(主要是还箱地)")
    public AjaxResult judgeInquiry(Long id) {
        BookingInquiry bookingInquiry = bookingInquiryService.selectBookingInquiryById(id);
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setInquiryId(id);
        List<BookingInquiryResult> list = bookingInquiryResultService.selectBookingInquiryResultList(bookingInquiryResult);
        // 整柜
        if ("0".equals(bookingInquiry.getGoodsType())) {
            for (BookingInquiryResult bookingInquiryResult1 : list) {
                // 判断铁路运费是否为0
                if ("0".equals(bookingInquiryResult1.getRailwayFees())) {
                    return AjaxResult.error("有询价方案铁路运费为0，请确认");
                }
                // 判断还箱地
                if ("0".equals(bookingInquiry.getContainerBelong()) &&
                        ("0".equals(bookingInquiry.getBookingService()) ||
                        ("1".equals(bookingInquiry.getEastOrWest()) && "3".equals(bookingInquiry.getBookingService())))) {
                    if (StringUtils.isEmpty(bookingInquiryResult1.getHxAddress())) {
                        return AjaxResult.error("有询价方案还箱地未选择，请确认");
                    }
                }
                // 判断特种箱提箱地（冷藏箱、开顶箱）
                if ("0".equals(bookingInquiry.getContainerBelong()) &&
                        ("0".equals(bookingInquiry.getEastOrWest()) &&
                        ("1".equals(bookingInquiry.getBookingService()) || "0".equals(bookingInquiry.getBookingService())) &&
                        (bookingInquiry.getContainerType().endsWith("RF") || bookingInquiry.getContainerType().endsWith("OT"))))
                {
                    if (StringUtils.isEmpty(bookingInquiryResult1.getTxAddress())) {
                        return AjaxResult.error("有询价方案提箱地未选择，请确认");
                    }
                }
            }
        }
        // 包含集疏报价数据
        if (
            "0".equals(bookingInquiry.getBookingService()) ||
            ("0".equals(bookingInquiry.getEastOrWest()) && "3".equals(bookingInquiry.getBookingService())) ||
            ("1".equals(bookingInquiry.getEastOrWest()) && "1".equals(bookingInquiry.getBookingService()))
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            list.forEach(bookingInquiryResult1 -> {
                stringBuilder.append(bookingInquiryResult1.getInquiryNum()).append(",");
            });
            inquiryProvider.jsSendQuoter(stringBuilder.deleteCharAt(stringBuilder.length()-1).toString());
        }
        return AjaxResult.success();
    }

}
