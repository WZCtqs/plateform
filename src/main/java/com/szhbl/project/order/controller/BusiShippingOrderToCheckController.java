package com.szhbl.project.order.controller;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.inquiry.convert.BookingInquiryGoodDetailFormConvert;
import com.szhbl.project.inquiry.domain.BookingInquiryBackup;
import com.szhbl.project.inquiry.domain.BookingInquiryOrder;
import com.szhbl.project.inquiry.form.BookingInquiryGoodDetailForm;
import com.szhbl.project.inquiry.service.IBookingInquiryOrderService;
import com.szhbl.project.order.domain.BusiOrderColumn;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.service.IBusiShippingOrderToCheckService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.domain.BusiSite;
import com.szhbl.project.trains.service.IBusiSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description : 客户托书转待审核申请
 * @Author : wangzhichao
 * @Date: 2020-07-02 16:05
 */
@Api(tags = "户托书转待审核申请")
@RestController
@RequestMapping("/order/toCheck")
public class BusiShippingOrderToCheckController {

    @Autowired
    private IBusiShippingOrderToCheckService toCheckService;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private IBusiSiteService busiSiteService;
    @Autowired
    IBookingInquiryOrderService bookingInquiryOrderService;

    @ApiOperation("转待审核第一步提交")
    @PostMapping("/inquiry")
    public AjaxResult toCheck(@RequestBody BookingInquiryGoodDetailForm bookingInquiryGoodDetailForm) {
        //询价、箱型、箱量、不能为空
        BookingInquiryBackup bookingInquiryBackup = BookingInquiryGoodDetailFormConvert.convertBackup(bookingInquiryGoodDetailForm);
        if (StringUtils.isEmpty(bookingInquiryBackup.getClientType())) {
            bookingInquiryBackup.setClientType("0");
        }
        //转待审核时，判断托书状态是否正确
        if ("0".equals(bookingInquiryGoodDetailForm.getBookingAgin())) {
            BusiOrderColumn busiOrderColumn = busiShippingorderService.selectBusiOrderColumnByOrderId(bookingInquiryBackup.getOrderId());
            if (!"1".equals(busiOrderColumn.getExamline())) {
                return AjaxResult.success("status error");
            }
        }
        // 插入order-inquiry表
        BookingInquiryOrder inquiryOrder = new BookingInquiryOrder();
        inquiryOrder.setOrderId(bookingInquiryBackup.getOrderId());
        inquiryOrder.setInquiryResultId(bookingInquiryBackup.getInquiryResultId());
        inquiryOrder.setCreateTime(new Date());
        bookingInquiryOrderService.insertBookingInquiryOrder(inquiryOrder);
        return toCheckService.busiShippingOrderToCheck(bookingInquiryBackup);
    }

    @ApiOperation("转待审核最后一步提交")
    @PostMapping("/inquiryAll")
    public AjaxResult inquiryAll(@RequestBody BusiShippingorders busiShippingorders) {
        //询价、箱型、箱量、不能为空
     /*   BookingInquiryBackup bookingInquiryBackup = BookingInquiryGoodDetailFormConvert.convertBackup(busiShippingorders);
        if (StringUtils.isEmpty(bookingInquiryBackup.getClientType())) {
            bookingInquiryBackup.setClientType("0");
        }
        return toCheckService.busiShippingOrderToCheck(bookingInquiryBackup);*/
     //询价并发送班列和订舱数据

     return toCheckService.inquiryAndSendCoData(busiShippingorders);
    }

    /**
     * 客户驳回转待审核询价结果
     *
     * @param orderId
     * @param inquiryResultId
     * @return
     */
    @GetMapping("/bhToCheck")
    public AjaxResult bhToCheck(String orderId, String inquiryResultId, Long inquiryId) {
        return toCheckService.bhToCheck(orderId, inquiryResultId, inquiryId);
    }

    /**
     * 客户确认转待审核询价结果
     *
     * @param orderId
     * @return
     */
    @GetMapping("/confirmToCheck")
    public AjaxResult confirmToCheck(String orderId, String expire) {
        return toCheckService.confirmToCheck(orderId, expire);
    }


    /**
     * 转待审核获取班列信息
     */
    @GetMapping("/getClassZd")
    @ApiOperation("转待审核获取班列信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "语言(en,cn)", name = "language", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "询价结果id", name = "inquiryRecordId", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "是否重新询价", name = "isInquiry", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "类型", name = "tjType", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "托书id", name = "orderId", paramType = "query", dataType = "String", required = true)
    })
    public List<BusiClasses> getClassZd(String language, String inquiryRecordId, String isInquiry, String orderId,String tjType){
        List<BusiClasses> classList = null;
        if(StringUtils.isNotEmpty(language) && StringUtils.isNotEmpty(inquiryRecordId) && StringUtils.isNotEmpty(isInquiry) &&StringUtils.isNotEmpty(orderId)){
            ShippingOrder orderInfo = busiShippingorderService.selectBusiShippingorderById(orderId);
            if(StringUtils.isNotNull(orderInfo)){
                BusiShippingorders busiShippingorder = new BusiShippingorders();
                if (false) { //是否重新询价 0否 1是
//                if("0".equals(isInquiry)){ //是否重新询价 0否 1是
                    busiShippingorder = toCheckService.selectOrderByInquiryRecord(inquiryRecordId);
                }
//                if ("1".equals(isInquiry)) {
                if (true) {
                    busiShippingorder = toCheckService.selectOrderByInquiryRecordBackup(inquiryRecordId);
                }
                if (null != busiShippingorder) {
                    busiShippingorder.setLanguage(language);
                    busiShippingorder.setTjType(tjType);
                    String isconsolidation = busiShippingorder.getIsconsolidation();  //整拼箱 0整柜 1拼箱
                    if (StringUtils.isNotNull(busiShippingorder)) {
                        //计算箱量差
                        Double boxNumDiff = 0.0;
                        if ("0".equals(isconsolidation)) { //整柜
                            Double boxNumn = 0.0; //新箱型箱量
                            String boxTypen = busiShippingorder.getContainerType();
                            Double boxNum = 0.0; //原箱型箱量
                            String boxtype = orderInfo.getContainerType();
                            if(StringUtils.isNotEmpty(boxTypen)){
                                if(StringUtils.isNotEmpty(busiShippingorder.getContainerBoxamount())){
                                    if(boxTypen.contains("20")){
                                        boxNumn = Double.valueOf(busiShippingorder.getContainerBoxamount())/2;
                                    }else{
                                        boxNumn = Double.valueOf(busiShippingorder.getContainerBoxamount());
                                    }
                                }
                            }
                            if(StringUtils.isNotEmpty(boxtype)){
                                if(StringUtils.isNotEmpty(orderInfo.getContainerBoxamount())){
                                    if(boxtype.contains("20")){
                                        boxNum = Double.valueOf(orderInfo.getContainerBoxamount())/2;
                                    }else{
                                        boxNum = Double.valueOf(orderInfo.getContainerBoxamount());
                                    }
                                }
                            }
                            Double diff = boxNumn - boxNum;
                            if(diff>0){ //箱量增加
                                boxNumDiff = diff;
                            }
                            busiShippingorder.setBoxNumDiff(boxNumDiff);
                        }
                        //计算体积差
                        Double volumeDiff = 0.0;
                        //计算重量差
                        Double weightDiff = 0.0;
                        if("1".equals(isconsolidation)){
                            //体积差
                            Double volumen = 0.0; //新体积
                            if(StringUtils.isNotEmpty(busiShippingorder.getGoodsCbm())){
                                volumen = Double.valueOf(busiShippingorder.getGoodsCbm());
                            }
                            Double volume = 0.0;  //原体积
                            if(StringUtils.isNotEmpty(orderInfo.getGoodsCbm())){
                                volume = Double.valueOf(orderInfo.getGoodsCbm());
                            }
                            Double diffv = volumen - volume;
                            if(diffv>0){
                                volumeDiff = diffv;
                            }
                            busiShippingorder.setVolumeDiff(volumeDiff);
                            //重量差
                            Double weightn = 0.0; //新重量
                            if(StringUtils.isNotEmpty(busiShippingorder.getGoodsKgs())){
                                weightn = Double.valueOf(busiShippingorder.getGoodsKgs());
                            }
                            Double weight = 0.0; //原重量
                            if(StringUtils.isNotEmpty(orderInfo.getGoodsKgs())){
                                weight = Double.valueOf(orderInfo.getGoodsKgs());
                            }
                            Double diffw = weightn - weight;
                            if(diffw>0){
                                weightDiff = diffw;
                            }
                            busiShippingorder.setWeightDiff(weightDiff);
                        }
                        //查询上下货站编号
                        if(StringUtils.isNotEmpty(busiShippingorder.getOrderUploadsite())){
                            BusiSite siteupload = busiSiteService.selectSiteByName(busiShippingorder.getOrderUploadsite());
                            if(StringUtils.isNotNull(siteupload)){
                                busiShippingorder.setOrderUploadcode(siteupload.getCode());  //上货站编号
                            }
                        }
                        if(StringUtils.isNotEmpty(busiShippingorder.getOrderUnloadsite())){
                            BusiSite siteunload =  busiSiteService.selectSiteByName(busiShippingorder.getOrderUnloadsite());
                            if(StringUtils.isNotNull(siteunload)){
                                busiShippingorder.setOrderUnloadcode(siteunload.getCode());
                            }
                        }
                        //当月、次月
                        String bookingTimeFlag = busiShippingorder.getBookingTimeFlag(); //0当月1次月
                        Date inquiryTime = busiShippingorder.getInquiryTime(); //询价时间
                        if(StringUtils.isNotEmpty(bookingTimeFlag) && StringUtils.isNotNull(inquiryTime)){
                            busiShippingorder.setNextMonth(DateUtils.getNextMonthByDate(inquiryTime));
                        }
                        //根据询价信息查询班列列表
                        if(StringUtils.isNotEmpty(isconsolidation)){
                            busiShippingorder.setOrderId(orderId);
                            if (isconsolidation.equals("0")) {  //整柜
                                classList = toCheckService.selectZXList(busiShippingorder);
                            }
                            if (isconsolidation.equals("1")) {  //拼箱
                                classList = toCheckService.selectPXList(busiShippingorder);
                            }
                        }
                    }
                }
            }
        }
        return classList;
    }

}
