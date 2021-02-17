package com.szhbl.project.inquiry.controller;

import com.szhbl.common.enums.LanguageEnum;
import com.szhbl.common.enums.ReturnAddressEnum;
import com.szhbl.common.utils.BeanChangeUtil;
import com.szhbl.common.utils.ServletUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.itext.InquiryWordExport;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.common.utils.poi.WestWordUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.framework.security.service.TokenService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.enquiry.domain.ZgReturnNonStandard;
import com.szhbl.project.enquiry.mapper.ZgReturnNonStandardMapper;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.domain.BookingInquiryResultRecord;
import com.szhbl.project.inquiry.form.BookingInquiryGoodDetailForm;
import com.szhbl.project.inquiry.handler.common.CommonHandlerService;
import com.szhbl.project.inquiry.service.IBookingInquiryGoodsDetailsService;
import com.szhbl.project.inquiry.service.IBookingInquiryResultRecordService;
import com.szhbl.project.inquiry.service.IBookingInquiryResultService;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import com.szhbl.project.system.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 询价反馈结果数据Controller
 *
 * @author jhm
 * @date 2020-04-08
 */
@RestController
@RequestMapping("/inquiry/result")
@Api(tags ="询价反馈结果数据")
public class BookingInquiryResultController extends BaseController
{
    @Autowired
    private IBookingInquiryService bookingInquiryService;
    @Autowired
    private IBookingInquiryResultService bookingInquiryResultService;
    @Autowired
    private IBookingInquiryGoodsDetailsService bookingInquiryGoodsDetailsService;
    @Autowired
    private ZgReturnNonStandardMapper zgReturnNonStandardMapper;
    @Autowired
    private CommonHandlerService commonHandlerService;
    @Autowired
    private IBookingInquiryResultRecordService bookingInquiryResultRecordService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询询价反馈结果数据列表
     */
//    @PreAuthorize("@ss.hasPermi('inquiry:result:list')")
    @GetMapping("/list")
    @ApiOperation("查询询价反馈结果数据列表")
    public TableDataInfo list(BookingInquiryResult bookingInquiryResult)
    {
        startPage();
        List<BookingInquiryResult> list = bookingInquiryResultService.selectBookingInquiryResultList(bookingInquiryResult);
        return getDataTable(list);
    }

    /**
     * 导出询价反馈结果数据列表
     */
    @Log(title = "询价反馈结果数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("询价反馈结果数据")
    public AjaxResult export(BookingInquiryResult bookingInquiryResult)
    {
        List<BookingInquiryResult> list = bookingInquiryResultService.selectBookingInquiryResultList(bookingInquiryResult);
        ExcelUtil<BookingInquiryResult> util = new ExcelUtil<BookingInquiryResult>(BookingInquiryResult.class);
        return util.exportExcel(list, "result");
    }

    /**
     * 获取询价反馈结果数据详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取询价反馈结果数据详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        BookingInquiryResult bookingInquiryResult = bookingInquiryResultService.selectBookingInquiryResultById(id);
        long inquiryId = bookingInquiryResult.getInquiryId();
        //根据id查询拼箱详细信息
        BookingInquiryGoodsDetails bookingInquiryGoodsDetails = new BookingInquiryGoodsDetails();
        bookingInquiryGoodsDetails.setInquiryId(inquiryId);
        List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList = bookingInquiryGoodsDetailsService.selectBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetails);
        BookingInquiry bookingInquiry = bookingInquiryResult.getBookingInquiry();
        bookingInquiry.setBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetailsList);
        // 俄线询价
       /* if (bookingInquiry.getLineType().equals("4") && bookingInquiry.getEastOrWest().equals("0") && (bookingInquiry.getContainerType().equals("40HC") ||
                bookingInquiry.getContainerType().equals("40GP"))) {

        }*/
        /*转待或再次订舱询价，查询托书班列日期*/
        if (bookingInquiry.getOrderId() != null) {
            Date classDate = bookingInquiryResultService.selectInquiryClassDateByOrderId(bookingInquiry.getOrderId());
            bookingInquiry.setClassDate(classDate);
        }
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        return AjaxResult.success(bookingInquiryResult);
    }

    /**
     * 新增询价反馈结果数据
     */
    @Log(title = "询价反馈结果数据", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增询价反馈结果数据")
    public AjaxResult add(@RequestBody BookingInquiryResult bookingInquiryResult)
    {
        return toAjax(bookingInquiryResultService.insertBookingInquiryResult(bookingInquiryResult));
    }

    /**
     * 修改询价反馈结果数据
     */
    @Log(title = "询价反馈结果数据", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改询价反馈结果数据")
    public AjaxResult edit(@RequestBody BookingInquiryResult bookingInquiryResult)
    {
        if (null != bookingInquiryResult.getInquiryId()) {
            BookingInquiry bookingInquiry =
                    bookingInquiryService.selectBookingInquiryById(bookingInquiryResult.getInquiryId());

            if (!("1".equals(bookingInquiry.getEastOrWest()) && "0".equals(bookingInquiry.getGoodsType()) &&
                ("2".equals(bookingInquiry.getLineType()) || "3".equals(bookingInquiry.getLineType()) ||"4".equals(bookingInquiry.getLineType()))) ){
                // 特种箱提箱地选择
                if(StringUtils.isNotEmpty(bookingInquiryResult.getTxAddress())) {
                    if ("0".equals(bookingInquiry.getEastOrWest()) &&
                            ("1".equals(bookingInquiry.getBookingService()) || "0".equals(bookingInquiry.getBookingService()))
                            && "0".equals(bookingInquiry.getGoodsType()) &&
                            (bookingInquiry.getContainerType().endsWith("RF")
                            || bookingInquiry.getContainerType().endsWith("OT")
                            || bookingInquiry.getContainerType().endsWith("HT")
                            || bookingInquiry.getContainerType().endsWith("MT")))
                    {
                        String address = ReturnAddressEnum.getAddress(bookingInquiryResult.getTxAddress());
                        if (StringUtils.isNotEmpty(address)) {
                            Float pickDistance = commonHandlerService.getDistances(bookingInquiry.getSenderCity()+"火车站",address);
                            String singleFee = "1.1";
                            if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
                                singleFee = "1.24";
                            }
                            BigDecimal pickFee = new BigDecimal(pickDistance)
                                    .multiply(new BigDecimal(singleFee))
                                    .multiply(new BigDecimal(bookingInquiry.getContainerNum()))
                                    .setScale(1,BigDecimal.ROUND_HALF_UP);
                            bookingInquiryResult.setPickUpFees(String.valueOf(pickFee.add(new BigDecimal(bookingInquiryResult.getPickUpFees()))));
                            if(LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())){
                                bookingInquiryResult.setTxAddress(commonHandlerService.getAddressEnName(bookingInquiryResult.getTxAddress()));
                            }
                            BusiBoxfee pickUpBoxfee = commonHandlerService.getBoxFeesAndEndTime2(
                                    bookingInquiryResult.getTxAddress(),
                                    bookingInquiry.getContainerType(),
                                    bookingInquiry.getContainerNum(),
                                    "0",
                                    bookingInquiry.getBookingTimeFlag());
                            if (!ObjectUtils.isEmpty(pickUpBoxfee)) {
                                bookingInquiryResult.setPickUpBoxFee(pickUpBoxfee.getCost());
                                bookingInquiryResult.setPickUpBoxExpiration(pickUpBoxfee.getEndTime());
                            }
                        }
                    }
                }

                // 如果还箱地选择了,插入新表
                if (StringUtils.isNotEmpty(bookingInquiryResult.getHxAddress())) {
                    ZgReturnNonStandard zgReturnNonStandard = new ZgReturnNonStandard();
                    // 还箱地对应收货地址
                    String province = bookingInquiry.getReceiveProvince();
                    String city = bookingInquiry.getReceiveCity();
                    // 如果存在cityCode
                    if (LanguageEnum.英文.value.equals(bookingInquiry.getLanguage()) && StringUtils.isNotEmpty(bookingInquiry.getCityCode())) {
                        city = commonHandlerService.getCityName(bookingInquiry.getCityCode());
                    }
                    province= province.contains("省")?province.substring(0,province.lastIndexOf("省")):province;
                    zgReturnNonStandard.setProvince(province);
                    city= city.contains("市")?city.substring(0,city.lastIndexOf("市")):city;
                    zgReturnNonStandard.setReceiptPlace(city);
                    zgReturnNonStandard.setReturnBoxAddress(bookingInquiryResult.getHxAddress());
                    zgReturnNonStandardMapper.insertZgReturnNonStandard(zgReturnNonStandard);
                    if("0".equals(bookingInquiry.getLineType())){
                        String address = ReturnAddressEnum.getAddress(bookingInquiryResult.getHxAddress());
                        if (StringUtils.isNotEmpty(address)) {
                            Float returnDistance = commonHandlerService.getDistances(city+"火车站",address);
                            String singleFee = "1.3";
                            if(bookingInquiry.getContainerType().endsWith("RF") || bookingInquiry.getContainerType().endsWith("OT")){
                                singleFee = "1.1";
                            }
                            if ("2".equals(bookingInquiry.getTruckType())) {// 白卡
                                singleFee = "1.44";
                                if(bookingInquiry.getContainerType().endsWith("RF") || bookingInquiry.getContainerType().endsWith("OT")){
                                    singleFee = "1.24";
                                }
                            }
                            BigDecimal returnFee = new BigDecimal(returnDistance)
                                    .multiply(new BigDecimal(singleFee))
                                    .multiply(new BigDecimal(bookingInquiry.getContainerNum()))
                                    .setScale(1,BigDecimal.ROUND_HALF_UP);
                            bookingInquiryResult.setDeliveryFees(String.valueOf(returnFee.add(new BigDecimal(bookingInquiryResult.getDeliveryFees()))));
                            if(LanguageEnum.英文.value.equals(bookingInquiry.getLanguage())){
                                bookingInquiryResult.setHxAddress(commonHandlerService.getAddressEnName(bookingInquiryResult.getHxAddress()));
                            }
                        }
                    }
                }
            }

        }
        bookingInquiryResult.setConfirmTime(new Date());
        // 比较变更内容
        BookingInquiryResult oldObj = bookingInquiryResultService.selectBookingInquiryResultById(bookingInquiryResult.getId());
        int i = bookingInquiryResultService.updateBookingInquiryResult(bookingInquiryResult);
        BookingInquiryResult newObj = bookingInquiryResultService.selectBookingInquiryResultById(bookingInquiryResult.getId());
        BeanChangeUtil<BookingInquiryResult> bc = new BeanChangeUtil<>();
        String c = bc.contrastObj(oldObj,newObj);
        if (StringUtils.isNotEmpty(c)) {
            // 存入修改记录
            BookingInquiryResultRecord bookingInquiryResultRecord = new BookingInquiryResultRecord();
            bookingInquiryResultRecord.setInquiryId(bookingInquiryResult.getInquiryId());
            bookingInquiryResultRecord.setInquiryResultId(bookingInquiryResult.getId());
            bookingInquiryResultRecord.setChangeRecord(c);
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            SysUser user = loginUser.getUser();
            bookingInquiryResultRecord.setUserId(user.getUserId());
            bookingInquiryResultRecord.setChangeTime(new Date());
            bookingInquiryResultRecordService.insertBookingInquiryResultRecord(bookingInquiryResultRecord);
        }
        return toAjax(i);
    }

    /**
     * 删除询价反馈结果数据
     */
    @Log(title = "询价反馈结果数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除询价反馈结果数据")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookingInquiryResultService.deleteBookingInquiryResultByIds(ids));
    }


    /**
     * 查询订舱询价列表
     */
    @GetMapping("/moreInquiryResult")
    @ApiOperation("获取询价方案")
    public AjaxResult list(Long inquiryId)
    {
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setInquiryId(inquiryId);
        List<BookingInquiryResult> list = bookingInquiryResultService.selectBookingInquiryResultList(bookingInquiryResult);
        return AjaxResult.success(list);
    }

    /**
     * 判断该询价下面的询价方案是否都已报完价
     */
    @GetMapping("/judgeInquiry")
    @ApiOperation("判断询价是否已经报完(主要是还箱地)")
    public AjaxResult judgeInquiry(Long inquiryId){
        BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
        bookingInquiryResult.setInquiryId(inquiryId);
        List<BookingInquiryResult> list = bookingInquiryResultService.selectBookingInquiryResultList(bookingInquiryResult);
        for (BookingInquiryResult bookingInquiryResult1 : list) {
            if (StringUtils.isEmpty(bookingInquiryResult1.getHxAddress())) {
                return AjaxResult.error("有询价方案还箱地未选择，请确认");
            }
        }
        return AjaxResult.success();
    }

    @Log(title = "询价结果", businessType = BusinessType.EXPORT)
    @GetMapping("/resultExport/{id}")
    public AjaxResult resultWordExport(@PathVariable("id") Long id) {
        BookingInquiryResult bookingInquiryResult = bookingInquiryResultService.selectBookingInquiryResultById(id);
        long inquiryId = bookingInquiryResult.getInquiryId();
        //根据id查询拼箱详细信息
        BookingInquiryGoodsDetails bookingInquiryGoodsDetails = new BookingInquiryGoodsDetails();
        bookingInquiryGoodsDetails.setInquiryId(inquiryId);
        List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList = bookingInquiryGoodsDetailsService.selectBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetails);
        BookingInquiry bookingInquiry = bookingInquiryResult.getBookingInquiry();
        if("0".equals(bookingInquiry.getEastOrWest())){
            return AjaxResult.success(WestWordUtil.toWord(bookingInquiryResult,bookingInquiry));
        }
        bookingInquiry.setBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetailsList);
        bookingInquiryResult.setBookingInquiry(bookingInquiry);
        InquiryWordExport wordExport = new InquiryWordExport();
        return wordExport.toWord(bookingInquiryResult);
    }

}
