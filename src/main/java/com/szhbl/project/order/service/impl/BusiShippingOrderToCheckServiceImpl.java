package com.szhbl.project.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.basic.domain.DangerousGoods;
import com.szhbl.project.basic.mapper.DangerousGoodsMapper;
import com.szhbl.project.inquiry.domain.*;
import com.szhbl.project.inquiry.handler.common.CommonHandlerService;
import com.szhbl.project.inquiry.handler.common.InquiryProvider;
import com.szhbl.project.inquiry.mapper.*;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import com.szhbl.project.inquiry.service.IJsBackTimesetService;
import com.szhbl.project.order.domain.BusiOrderTocheckInquiry;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.handler.RailWayHandler;
import com.szhbl.project.order.handler.ToCheckNotice;
import com.szhbl.project.order.mapper.BusiOrderTocheckInquiryMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShipOrderService;
import com.szhbl.project.order.service.IBusiShippingOrderToCheckService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.trains.domain.BusiClasses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description : 客户托书转待审核申请服务实现 @Author : wangzhichao @Date: 2020-07-02 16:10
 */
@Slf4j
@Service
public class BusiShippingOrderToCheckServiceImpl implements IBusiShippingOrderToCheckService {
    @Autowired
    BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    BookingInquiryOrderMapper bookingInquiryOrderMapper;
    @Autowired
    private BookingInquiryMapper bookingInquiryMapper;
    @Autowired
    private DangerousGoodsMapper dangerousGoodsMapper;

    @Autowired
    private BookingInquiryBackupMapper bookingInquiryBackupMapper;
    @Autowired
    private BookingInquiryResultBackupMapper bookingInquiryResultBackupMapper;
    @Autowired
    private BookingInquiryGoodsDetailsBackupMapper goodsDetailsBackupMapper;
    @Autowired
    private BookingInquiryGoodsDetailsMapper bookingInquiryGoodsDetailsMapper;
    @Autowired
    private BusiOrderTocheckInquiryMapper busiOrderTocheckInquiryMapper;
    @Autowired
    private BookingInquiryResultMapper bookingInquiryResultMapper;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private IJsBackTimesetService jsBackTimesetService;
    @Autowired
    private InquiryProvider inquiryProvider;
    @Autowired
    ToCheckNotice toCheckNotice;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RailWayHandler railWayHandler;
    @Autowired
    private IBookingInquiryService bookingInquiryService;
    @Autowired
    private CommonHandlerService commonHandlerService;
    @Autowired
    private IBusiShipOrderService busiShipOrderService;
    @Autowired
    private GwczToCheckService gnwStationToCheckService;

    @Override
    public AjaxResult busiShippingOrderToCheck(BookingInquiryBackup newBookingInquiry) {
        log.info(newBookingInquiry.toString());
        BookingInquiry oldBookingInquiry =
                bookingInquiryMapper.selectBookingInquiryById(newBookingInquiry.getId());
        oldBookingInquiry.setBookingInquiryGoodsDetailsList(
                bookingInquiryGoodsDetailsMapper.selectBookingInquiryGoodsDetailsWithInquiryId(newBookingInquiry.getId()));

        String lineType = newBookingInquiry.getLineType(); // 线路
        String goodsType = newBookingInquiry.getGoodsType(); // 整柜/拼箱
        StringBuffer msg = new StringBuffer();
        // 判断中亚、中越20尺箱数量
        if ((lineType.equals("2") || lineType.equals("3"))
                && goodsType.equals("0")
                && newBookingInquiry.getContainerType().startsWith("20")) {
            int num = newBookingInquiry.getContainerNum();
            if (num % 2 != 0) {
                return AjaxResult.error("该线路20尺箱需订双数，请确认");
            }
        }
        List<BookingInquiryGoodsDetailsBackup> newbookingInquiryGoodsDetailsList =
                newBookingInquiry.getBookingInquiryGoodsDetailsBackupList();
        if (goodsType.equals("1") || "1".equals(newBookingInquiry.getDeliveryType()) || "0".equals(newBookingInquiry.getDeliverySelfType())) {

            if (newbookingInquiryGoodsDetailsList.size() == 0) {
                return AjaxResult.error("拼箱必填数据不存在，请确认已填写");
            } else {
                /*判断危险品-超长超重*/
                Map dangerousMap =
                        dangerous(
                                newBookingInquiry.getBookingInquiryGoodsDetailsBackupList(),
                                newBookingInquiry.getLanguage());
                Boolean over = (Boolean) dangerousMap.get("over");
                StringBuilder dangerous = (StringBuilder) dangerousMap.get("dangerous");
                if (over) {
                    msg.append("货物超长超重，请联系业务部确认是否可以操作。");
                }
                if (dangerous.length() > 0) {
                    return AjaxResult.error(
                            "货物包含危险品(" + dangerous.substring(0, dangerous.length() - 1) + ")，不可询价");
                }
            }
        }
        // 获取原来询价结果信息
        BookingInquiryResult oldResult = bookingInquiryResultMapper.selectBookingInquiryResultById(newBookingInquiry.getInquiryResultId());
        oldBookingInquiry.setTxAddress(oldResult.getTxAddress());
        oldBookingInquiry.setHxAddress(oldResult.getHxAddress());
        oldBookingInquiry.setUploadStation(oldResult.getUploadStation());
        oldBookingInquiry.setDropStation(oldResult.getDropStation());
        newBookingInquiry.setUploadStation(StringUtils.isEmpty(newBookingInquiry.getUploadStation()) ? oldResult.getUploadStation() : newBookingInquiry.getUploadStation());
        newBookingInquiry.setDropStation(StringUtils.isEmpty(newBookingInquiry.getDropStation()) ? oldResult.getDropStation() : newBookingInquiry.getDropStation());
        newBookingInquiry.setHxAddress(StringUtils.isEmpty(newBookingInquiry.getHxAddress()) ? oldBookingInquiry.getHxAddress() : newBookingInquiry.getHxAddress());
        newBookingInquiry.setTxAddress(StringUtils.isEmpty(newBookingInquiry.getTxAddress()) ? oldBookingInquiry.getTxAddress() : newBookingInquiry.getTxAddress());
        /*询价判断*/
        Map map = compareInquiry(oldBookingInquiry, newBookingInquiry, oldResult);
        /*询价判断*/
        boolean gonglu = (Boolean) map.get("gonglu");
        boolean jishu = (Boolean) map.get("jishu");
        boolean dcExamine = (Boolean) map.get("dcExamine");
        boolean xgExamine = (Boolean) map.get("xgExamine");
        int hxdXgExamine = (int) map.get("hxdXgExamine");
        int[] gwcz = (int[]) map.get("gwcz");
        BookingInquiryResultBackup resultBackup = (BookingInquiryResultBackup) map.get("resultBackup");
        newBookingInquiry = (BookingInquiryBackup) map.get("inquiryBp");
        newBookingInquiry.setBookingInquiryGoodsDetailsBackupList(newbookingInquiryGoodsDetailsList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("gonglu", gonglu);
        jsonObject.put("jishu", jishu);
        jsonObject.put("dcExamine", dcExamine);
        jsonObject.put("xgExamine", xgExamine);

        BusiOrderTocheckInquiry tocheckInquiry = new BusiOrderTocheckInquiry();
        tocheckInquiry.setCreateTime(new Date());
        /*是否为新的询价，都新增草稿表*/
        newBookingInquiry.setInquiryResultId(null);
        if (StringUtils.isNotEmpty(newBookingInquiry.getInquiryState())
                && "2".equals(newBookingInquiry.getInquiryState())) {
        } else if (!jishu && !gonglu) {
            newBookingInquiry.setInquiryState("3");
        } else {
            newBookingInquiry.setInquiryState("1");
        }
        /*有效期*/
        newBookingInquiry.setValidDate(
                commonHandlerService.getValidDate(
                        resultBackup.getPickUpExpiration(),
                        resultBackup.getRailwayExpiration(),
                        resultBackup.getDeliveryExpiration(),
                        resultBackup.getPickUpBoxExpiration(),
                        resultBackup.getReturnBoxExpiration()
                )
        );

        /*国内外场站是否报价判断*/
//        GoodToCheckResult gnwResult = gnwStationToCheckService.judgeStationInquiry(oldBookingInquiry, newBookingInquiry, oldResult, resultBackup);
        // 判断表：0：需要。1：不需要
//        tocheckInquiry.setGnInquiry(gnwResult.isGn() ? 0 : 1);
//        tocheckInquiry.setGwInquiry(gnwResult.isGw() ? 0 : 1);
//        resultBackup = gnwResult.getInquiryResultBackup();
        bookingInquiryBackupMapper.insertBookingInquiryBackup(newBookingInquiry);
        resultBackup.setInquiryId(newBookingInquiry.getId());
        resultBackup.setUploadStation(StringUtils.isEmpty(resultBackup.getUploadStation()) ? oldResult.getUploadStation() : resultBackup.getUploadStation());
        resultBackup.setDropStation(StringUtils.isEmpty(resultBackup.getDropStation()) ? oldResult.getDropStation() : resultBackup.getDropStation());
        bookingInquiryResultBackupMapper.insertBookingInquiryResultBackup(resultBackup);

        tocheckInquiry.setInquiryId(newBookingInquiry.getId());
        tocheckInquiry.setInquiryResultId(resultBackup.getId());
        if (goodsType.equals("1")
                || "1".equals(newBookingInquiry.getDeliveryType())
                || "0".equals(newBookingInquiry.getDeliverySelfType())
                || "1".equals(newBookingInquiry.getDistributionType())
        ) {
            for (BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetails : newbookingInquiryGoodsDetailsList) {
                bookingInquiryGoodsDetails.setInquiryId(newBookingInquiry.getId());
                //插入拼箱数据
                goodsDetailsBackupMapper.insertBookingInquiryGoodsDetailsBackup(bookingInquiryGoodsDetails);
            }
        }
        if (!gonglu && !jishu && !dcExamine && !xgExamine) {
            jsonObject.put("newInquiry", false);
            tocheckInquiry.setNewInquiry(1);
        } else {
            tocheckInquiry.setNewInquiry(0);
            jsonObject.put("newInquiry", true);
        }
        jsonObject.put("inquiryId", newBookingInquiry.getId());
        jsonObject.put("inquiryResultId", resultBackup.getId());
        tocheckInquiry.setOrderId(newBookingInquiry.getOrderId());
        tocheckInquiry.setXgExamine(xgExamine ? 0 : 1);
        tocheckInquiry.setJsExamine(jishu ? 0 : 1);
        tocheckInquiry.setDcExamine(dcExamine ? 0 : 1);
        tocheckInquiry.setXxyoExamine(gonglu ? 0 : 1);
        tocheckInquiry.setHxdXgExamine(hxdXgExamine);
        tocheckInquiry.setClientExamine((jishu || gonglu) ? 0 : 1);
        tocheckInquiry.setYwExamine(newBookingInquiry.getInquiryState().equals("2") ? 0 : 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gwcz.length; i++) {
            if (i < gwcz.length - 1) {
                sb.append(gwcz[i] + ",");
            } else {
                sb.append(gwcz[i]);
            }
        }
        tocheckInquiry.setGwczResult(sb.toString());
        busiOrderTocheckInquiryMapper.insertBusiOrderTocheckInquiry(tocheckInquiry);
        jsonObject.put("msg", msg.toString());
        return AjaxResult.success(jsonObject);
    }

    @Override
    @Transactional
    public AjaxResult bhToCheck(String orderId, String inquiryResultId, Long inquiryId) {
        Map IsExamline = busiShippingorderService.selectIsExamlineByOrderId(orderId);
        BusiShippingorders busiShippingorders = new BusiShippingorders();
        busiShippingorders.setOrderId(orderId);
        if (IsExamline.get("IsExamline").equals("15") || IsExamline.get("IsExamline").equals("14")) {
            busiShippingorders.setIsexamline("3");
            if (IsExamline.get("IsConsolidation").equals("0")) {
                busiShippingorders.setContainerBoxamount("0");
            }
        } else {
            busiShippingorders.setIsexamline("5");
        }
        busiShippingorderMapper.updateBusiShippingorder(busiShippingorders);
        //更新询价状态为驳回
        BookingInquiry bookingInquiry = new BookingInquiry();
        bookingInquiry.setId(inquiryId);
        bookingInquiry.setInquiryState("4");
        bookingInquiryMapper.updateBookingInquiry(bookingInquiry);
        ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
        if (StringUtils.isNotNull(orderInfoRabbmq)) {
            if("3".equals(busiShippingorders.getIsexamline())){
                commonService.sendEmailJsBh(orderInfoRabbmq);
            }
            String messagetype = "5"; // 转待审核失败
            try {
                commonService.orderInfoMQ(orderInfoRabbmq, messagetype); // 推送消息队列
            } catch (JsonProcessingException e) {
                log.error("客户驳回报价失败", e.toString(), e.getStackTrace());
            }
        }
        if (!IsExamline.get("IsExamline").equals("15")) {
            commonService.deleteTem(orderId);
            toCheckNotice.noticeSonSystem(orderId, "5", "客户驳回转待审核报价");
            // 恢复之前的询价结果信息
            BookingInquiryOrder inquiryOrder = bookingInquiryOrderMapper.selectPreInquiryOrder(orderId);
            if (inquiryOrder != null) {
                return AjaxResult.success(
                        busiShippingorderMapper.updateInquiryResultId(
                                orderId, inquiryOrder.getInquiryResultId()));
            }
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult confirmToCheck(String orderId, String expire) {
        Map IsExamline = busiShippingorderService.selectIsExamlineByOrderId(orderId);
        if (!IsExamline.get("IsExamline").equals("15")) {
            // 获取是否需要订舱审核
            BusiOrderTocheckInquiry tocheckInquiry = busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(orderId);
            if (tocheckInquiry.getDcExamine() == 0 || tocheckInquiry.getDczExamine() == 0) {
                // 需要审核更改状态-4
                BusiShippingorders shippingorders = new BusiShippingorders();
                shippingorders.setOrderId(orderId);
                shippingorders.setIsexamline("4");
                busiShippingorderMapper.updateBusiShippingorder(shippingorders);
            } else {
                // 不需要审核改托书
                try {
                    busiShippingorderService.updateOrderTem(orderId, "", "", "");
                } catch (JsonProcessingException e) {
                    log.error("客户确认报价失败", e.toString(), e.getStackTrace());
                }
            }
            toCheckNotice.noticeSonSystem(orderId, "4", "客户确认转待审核报价");
        } else {
            BusiShippingorders shippingorders = new BusiShippingorders();
            shippingorders.setOrderId(orderId);
            shippingorders.setIsexamline("0");
            if ("1".equals(expire)) {
                shippingorders.setIsexamline("5");
            }
            busiShippingorderMapper.updateBusiShippingorder(shippingorders);
            busiShipOrderService.insertOrderInquiry(orderId);
        }
        ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
        if (StringUtils.isNotNull(orderInfoRabbmq)) {
            String messagetype = "4"; //转待审核失败
            try {
                commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
            } catch (JsonProcessingException e) {
                log.error("客户确认报价失败", e.toString(), e.getStackTrace());
            }
        }
        return AjaxResult.success();
    }

    //询价和订舱班列数据发送
    @Override
    public AjaxResult inquiryAndSendCoData(BusiShippingorders busiShippingorders) {
        /*再次订舱生成新的orderid*/
        String newOrderId="";
        if("1".equals(busiShippingorders.getTjType())){
            newOrderId=IdUtils.randomUUID();
        }
        //查询是否包含危险品
        String goodsName = busiShippingorders.getGoodsName();
        String goodsEnName = busiShippingorders.getGoodsEnName();
        String language = busiShippingorders.getLanguage();
        if(StringUtils.isNotEmpty(goodsName)){
            DangerousGoods dangoods = dangerousGoodsMapper.selectDangerousGoodsByNameByLevel(goodsName,goodsEnName);
            if(StringUtils.isNotNull(dangoods)){
                if("en".equals(language)){
                    return AjaxResult.error(dangoods.getSpecialEnRemark());
                }else{
                    return AjaxResult.error(dangoods.getSpecialremark());
                }
            }
        }
        //校验跟单员是否为空
        if(StringUtils.isEmpty(busiShippingorders.getOrderMerchandiserId()) && StringUtils.isEmpty(busiShippingorders.getOrderMerchandiser())){
            if("en".equals(language)){
                return AjaxResult.error("Merchandiser cannot be empty");
            }else{
                return AjaxResult.error("跟单员不能为空");
            }
        }
        //查询判断结果
        BusiOrderTocheckInquiry tocheckInquiry = busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(busiShippingorders.getOrderId());
        /*再次订舱进行新增判断结果*/
        if("1".equals(busiShippingorders.getTjType())){
            BusiOrderTocheckInquiry newTocheckInquiry=new BusiOrderTocheckInquiry();
            BeanUtils.copyProperties(tocheckInquiry, newTocheckInquiry);
            newTocheckInquiry.setOrderId(newOrderId);
            newTocheckInquiry.setCreateTime(DateUtils.getNowDate());
            busiOrderTocheckInquiryMapper.insertBusiOrderTocheckInquiry(newTocheckInquiry);
        }
        //需要箱管审核
        if (0 == tocheckInquiry.getXgExamine()) {
            ShippingOrder shippingOrder=new ShippingOrder();
            BeanUtils.copyProperties(busiShippingorders, shippingOrder);
            shippingOrder.setType("0");
            shippingOrder.setHxdExamine("1");
            /*再次订舱发送新的orderid*/
            if("1".equals(busiShippingorders.getTjType())){
                shippingOrder.setOrderId(newOrderId);
                shippingOrder.setOrderNumber(null);
                shippingOrder.setType("2");
            }
            inquiryProvider.xgPush(shippingOrder);
        }
        //不需要箱管审核生成新的询价和询价结果数据
        else{
            //询价草稿数据
            BookingInquiryBackup bookingInquiryBackup=bookingInquiryBackupMapper.selectBookingInquiryBackupById(busiShippingorders.getInquiryId());
            BookingInquiry bookingInquiry = new BookingInquiry();
            BeanUtils.copyProperties(bookingInquiryBackup, bookingInquiry);
            bookingInquiry.setIsToCheck(0);
            bookingInquiry.setInquiryTime(new Date());
            /*再次订舱询价存新的orderid*/
            if("1".equals(busiShippingorders.getTjType())){
                bookingInquiry.setOrderId(newOrderId);
            }
            bookingInquiryMapper.insertBookingInquiry(bookingInquiry);
            bookingInquiry.setJsNum(bookingInquiryResultMapper.getJsNumByOrderId(bookingInquiryBackup.getOrderId()));
            ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(bookingInquiryBackup.getOrderId());
            bookingInquiry.setClassDate(busiShippingorders.getClassDate());
            bookingInquiry.setOrderNumber(shippingOrder.getOrderNumber());
            //询价结果草稿数据
            BookingInquiryResultBackup bookingInquiryResultBackup=bookingInquiryResultBackupMapper.selectBookingInquiryResultBackupById(Long.valueOf(busiShippingorders.getInquiryRecordId()));
            BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
            BeanUtils.copyProperties(bookingInquiryResultBackup, bookingInquiryResult);
            bookingInquiryResult.setInquiryId(bookingInquiry.getId());
            bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
            //添加有效期
            bookingInquiryService.updateBIWithDate(bookingInquiry);
            //更新托书表询价结果id,
            /*再次订舱不用更新*/
            if("1".equals(busiShippingorders.getTjType())){
                busiShippingorders.setInquiryRecordId(bookingInquiryResult.getId().toString());
            }else{
                BusiShippingorders busiShippingorder=new BusiShippingorders();
                busiShippingorder.setInquiryRecordId(bookingInquiryResult.getId().toString());
                busiShippingorder.setOrderId(busiShippingorders.getOrderId());
                busiShippingorderMapper.updateBusiShippingorder(busiShippingorder);
            }
            //拼箱草稿数据
            BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup=new BookingInquiryGoodsDetailsBackup();
            bookingInquiryGoodsDetailsBackup.setInquiryId(busiShippingorders.getInquiryId());
            List<BookingInquiryGoodsDetailsBackup> bookingInquiryGoodsDetailsBackupList = goodsDetailsBackupMapper.selectBookingInquiryGoodsDetailsBackupList(bookingInquiryGoodsDetailsBackup);
            List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList =new ArrayList<>();
            if(StringUtils.isNotEmpty(bookingInquiryGoodsDetailsBackupList)){
                BookingInquiryGoodsDetails bookingInquiryGoodsDetails=null;
                for (BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackups : bookingInquiryGoodsDetailsBackupList) {
                    bookingInquiryGoodsDetails=new BookingInquiryGoodsDetails();
                    BeanUtils.copyProperties(bookingInquiryGoodsDetailsBackups, bookingInquiryGoodsDetails);
                    bookingInquiryGoodsDetails.setInquiryId(bookingInquiry.getId());
                    //插入拼箱数据
                    bookingInquiryGoodsDetailsList.add(bookingInquiryGoodsDetails);
                    bookingInquiryGoodsDetailsMapper.insertBookingInquiryGoodsDetails(bookingInquiryGoodsDetails);
                }
                //添加发送子系统询价的拼箱数据
                bookingInquiry.setBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetailsList);
            }

            //去程
            if("0".equals(bookingInquiry.getEastOrWest())){
                switch (bookingInquiry.getBookingService()){
                    case "0":
                        if(StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())){
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        if(StringUtils.isEmpty(bookingInquiryResult.getDeliveryFees())){
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        break;
                    case "1":
                        if(StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())){
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        break;
                    case "3":
                        if(StringUtils.isEmpty(bookingInquiryResult.getDeliveryFees())){
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        break;
                }
            }
            //回程
            else if("1".equals(bookingInquiry.getEastOrWest())){
                switch (bookingInquiry.getBookingService()){
                    case "0":
                        if (StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())) {
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        if (StringUtils.isEmpty(bookingInquiryResult.getDeliveryFees())) {
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        break;
                    case "1":
                        if(StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())){
                            inquiryProvider.jsPush(bookingInquiry);
                        }
                        break;
                    case "3":
                        if (StringUtils.isEmpty(bookingInquiryResult.getDeliveryFees())) {
                            inquiryProvider.xxyoPush(bookingInquiry);
                        }
                        break;
                }
            }
        }
        //数据发送
        int i=0;
        try {
            /*再次订舱*/
            if("1".equals(busiShippingorders.getTjType())){
                //busiShippingorders新的订单id，新的询价结果id
                busiShippingorders.setOrderId(newOrderId);
                i=busiShipOrderService.insertBusiShippingorder(busiShippingorders);
            }
            //转待审核
            else{
                ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(busiShippingorders.getOrderId());
                busiShippingorders.setInquiryRecordId(shippingOrder.getInquiryRecordId());
                i=busiShippingorderService.orderCheckApplyXj(busiShippingorders);
            }
        } catch (JsonProcessingException e) {
            log.error("转待审核托书修改信息提交失败",e.toString(),e.getStackTrace());
        }
        if(i==0){
            return AjaxResult.error("提交失败");
        }else{
            toCheckNotice.noticeSonSystem(busiShippingorders.getOrderId(), "4", "客户提交转待审核");
            return AjaxResult.success("提交成功");
        }
    }

    /**
     * 保存托书当前询价结果id到托书询价关联表
     *
     * @param orderId
     */
    public void saveOldInquiryResultId(String orderId) {
        // 根据托书id获取当前询价结果id
        Long inquiryResultId = busiShippingorderMapper.selectInquiryResultIdByOrderId(orderId);
        if (inquiryResultId != null) {
            // 保存当前询价结果id到询价-托书结果表
            BookingInquiryOrder inquiryOrder = new BookingInquiryOrder();
            inquiryOrder.setInquiryResultId(inquiryResultId);
            inquiryOrder.setOrderId(orderId);
            bookingInquiryOrderMapper.insertBookingInquiryOrder(inquiryOrder);
        }
    }

    /**
     * 比较转待审核询价信息
     *
     * @param oldInquiry
     * @param inquiry
     */
    public Map<String, Object> compareInquiry(
            BookingInquiry oldInquiry, BookingInquiryBackup inquiry, BookingInquiryResult oldResult) {
        if (oldInquiry.getDeliveryType().equals("0")) {
            oldInquiry.setDistributionType(StringUtils.isEmpty(oldInquiry.getDistributionType()) ? "0" : oldInquiry.getDistributionType());
        }
        Map<String, Object> map = new HashMap<>();
        int gwcz[] = {0, 0, 0, 0, 0};//{-门到站，+门到站，上下货站，提还箱地，到站通知邮箱}
        // 线路-东西向-整拼箱 不变
        /* 服务类型*/
        if (inquiry.getBookingService().equals(oldInquiry.getBookingService())) {
            /*根据服务类型判断  地址-委托  信息*/
            switch (oldInquiry.getBookingService()) {
                // 0：门到门
                case "0":
                    map = compareInquiryDoorToDoor(oldInquiry, inquiry, oldResult, gwcz);
                    break;
                // 1：门到站
                case "1":
                    map = compareInquiryDoorToStation(oldInquiry, inquiry, oldResult, gwcz);
                    break;
                // 2：站到站
                case "2":
                    map = compareInquiryStationToStation(oldInquiry, inquiry, gwcz);
                    break;
                // 3：站到门
                case "3":
                    map = compareInquiryStationToDoor(oldInquiry, inquiry, oldResult, gwcz);
                    break;
            }
            return map;
        } else {
            boolean gonglu = false;
            boolean jishu = false;
            switch (oldInquiry.getBookingService()) {
                case "0": //门到门
                    switch (inquiry.getBookingService()) {
                        case "1"://门到站
                            // 减少站到门
                            break;
                        case "2"://站到站
                            // 减少站到门
                            // 减少门到站
                            gwcz[1] = 1;
                            break;
                        case "3"://站到门
                            // 减少门到站
                            gwcz[1] = 1;
                            break;
                    }
                    break;
                case "1"://门到站
                    switch (inquiry.getBookingService()) {
                        case "0"://门到门
                            // 增加站到门
                            if (inquiry.getEastOrWest().equals("0")) {
                                jishu = true;
                            } else {
//                                gonglu = true;
                            }
                            break;
                        case "2"://站到站
                            // 减少门到站
                            gwcz[1] = 1;
                            break;
                        case "3"://站到门
                            // 减少门到站
                            gwcz[1] = 1;
                            // 增加站到门
                            if (inquiry.getEastOrWest().equals("0")) {
                                jishu = true;
                            } else {
//                                gonglu = true;
                            }
                            break;
                    }
                    break;
                case "2"://站到站
                    switch (inquiry.getBookingService()) {
                        case "0"://门到门
                            // 增加门到站
                            gwcz[0] = 1;
                            // 增加站到门
                            jishu = true;
//                            gonglu = true;
                            break;
                        case "1"://门到站
                            // 增加门到站
                            gwcz[0] = 1;
                            if (inquiry.getEastOrWest().equals("1")) {
                                jishu = true;
                            } else {
//                                gonglu = true;
                            }
                            break;
                        case "3"://站到门
                            // 增加站到门
                            if (inquiry.getEastOrWest().equals("0")) {
                                jishu = true;
                            } else {
//                                gonglu = true;
                            }
                            break;
                    }
                    break;
                case "3"://站到门
                    switch (inquiry.getBookingService()) {
                        case "0"://门到门
                            // 增加门到站
                            gwcz[0] = 1;
                            if (inquiry.getEastOrWest().equals("1")) {
                                jishu = true;
                            } else {
//                                gonglu = true;
                            }
                            break;
                        case "1"://门到站
                            // 减少站到门
                            // 增加门到站
                            gwcz[0] = 1;
                            if (inquiry.getEastOrWest().equals("1")) {
                                jishu = true;
                            } else {
//                                gonglu = true;
                            }
                            break;
                        case "2"://站到站
                            // 减少站到门
                            break;
                    }
                    break;
            }
            switch (inquiry.getBookingService()) {
                // 0：门到门
                case "0":
                    map = compareInquiryDoorToDoor(oldInquiry, inquiry, oldResult, gwcz);
                    break;
                // 1：门到站
                case "1":
                    map = compareInquiryDoorToStation(oldInquiry, inquiry, oldResult, gwcz);
                    break;
                // 2：站到站
                case "2":
                    map = compareInquiryStationToStation(oldInquiry, inquiry, gwcz);
                    break;
                // 3：站到门
                case "3":
                    map = compareInquiryStationToDoor(oldInquiry, inquiry, oldResult, gwcz);
                    break;
            }
            boolean gonglu1 = (Boolean) map.get("gonglu");
            boolean jishu1 = (Boolean) map.get("jishu");
            boolean dcExamine1 = (Boolean) map.get("dcExamine");
            int hxdXgExamine = (int) map.get("hxdXgExamine");
            boolean xgExamine1 = (Boolean) map.get("xgExamine");
            gwcz = (int[]) map.get("gwcz");
            BookingInquiryBackup inquiryBackup = (BookingInquiryBackup) map.get("inquiryBp");
            BookingInquiryResultBackup resultBackup = (BookingInquiryResultBackup) map.get("resultBackup");
            Map map1 = new HashMap();
            map1.put("gonglu", gonglu || gonglu1);
            map1.put("jishu", jishu || jishu1);
            map1.put("dcExamine", dcExamine1);
            map1.put("xgExamine", xgExamine1);
            map1.put("hxdXgExamine", hxdXgExamine);
            map1.put("resultBackup", resultBackup);
            map1.put("gwcz", gwcz);
            map1.put("inquiryBp", inquiryBackup);
            return map1;
        }
    }

    /**
     * 判断“门到门”服务信息
     *
     * @param oldInquiry
     * @param inquiry
     * @param gwcz
     * @return
     */
    public Map<String, Object> compareInquiryDoorToDoor(
            BookingInquiry oldInquiry, BookingInquiryBackup inquiry, BookingInquiryResult oldResult, int[] gwcz) {
        //gwcz：{-门到站，+门到站，上下货站，提还箱地，到站通知邮箱}
        /*注. true: 没有改变，false:改变*/
        //        影响询价的信息：
        //        1、服务更改
        //        2、上下货站
        boolean stationChange = true;
        //        3、提货地、收货地
        boolean pickAdd = true;
        boolean SendAdd = true;
        //        4、整柜 箱量增加减少 特种箱
        /*0：不变，-n 减少n个，+n 增加n个*/
        int conNum = 0;
        /*0:不变。1：普->普,2：普->特,3：特->普,4：特->特*/
        int conType = 0;
        //        5、提还箱地
        boolean pickReturnCon = true;
        //        6、拼箱货品信息
        /*综合判断货量、包装、车辆*/
        boolean goods = true;
        boolean picking = true;
        boolean goodsSend = true;
        //        7、提货方式
        boolean deliveryType = true;
        //        8、派货方式
        boolean distributionType = true;
        //        9、回程报关
        boolean eastDeclare = true;

        int hxdXgExamine = 1;

        inquiry.setHxAddress(oldResult.getHxAddress());
        // 发货地 省市区详细地址
        // 回程判断报关
        if ("0".equals(inquiry.getEastOrWest())) {
            // 去程
            if (!((inquiry.getSenderProvince() + "").equals((oldInquiry.getSenderProvince() + ""))
                    && (inquiry.getSenderCity() + "").equals((oldInquiry.getSenderCity() + ""))
                    && (inquiry.getSenderArea() + "").equals((oldInquiry.getSenderArea() + ""))
                    && (inquiry.getShipmentPlace() + "").equals((oldInquiry.getShipmentPlace() + "")))) {
                pickAdd = false;
            }
            // 收货地
            if (!((inquiry.getReceiptPlace() + "").equals(oldInquiry.getReceiptPlace() + ""))) {
                SendAdd = false;
            }
        } else {
            if (!inquiry.getIsOrderCustoms().equals(oldInquiry.getIsOrderCustoms())) {
                eastDeclare = false;
            }
            if (!((inquiry.getShipmentPlace() + "").equals(oldInquiry.getShipmentPlace() + ""))) {
                pickAdd = false;
            }
            // 收货地
            if (!((inquiry.getReceiveProvince() + "").equals((oldInquiry.getReceiveProvince() + ""))
                    && (inquiry.getReceiveCity() + "").equals((oldInquiry.getReceiveCity() + ""))
                    && (inquiry.getReceiveArea() + "").equals((oldInquiry.getReceiveArea() + ""))
                    && (inquiry.getReceiptPlace() + "").equals((oldInquiry.getReceiptPlace() + "")))) {
                SendAdd = false;
            }
        }
        if ("0".equals(inquiry.getGoodsType())) {
            if ("0".equals(oldInquiry.getBookingService()) || "1".equals(oldInquiry.getBookingService())) {
                // 整柜时 判断提货方式
                deliveryType = inquiry.getDeliveryType().equals(oldInquiry.getDeliveryType());
            }
//            if ("2".equals(oldInquiry.getBookingService()) || "3".equals(oldInquiry.getBookingService())) {
//                // 整柜时 判断提货方式
//                deliveryType = inquiry.getDeliveryType().equals(oldInquiry.getDeliverySelfType());
//            }
            /*箱型、箱量、箱属*/
            conNum = conNum(oldInquiry, inquiry);
            conType = conType(oldInquiry, inquiry);
            // 散货到堆场
            if ("1".equals(inquiry.getDeliveryType())) {
                /*包装方式、是否堆叠、是否易碎*/
                /*合计 体积、重量、宽度*/
                if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                    goods = false;
                }
                if (!picking(oldInquiry, inquiry)) {
                    picking = false;
                }
            }
            // 整柜时，判断派送方式
            if ("0".equals(inquiry.getEastOrWest())) {
                //去程处理
                switch (inquiry.getDistributionType()) {
                    //整柜派送
                    case "0":
                        //散货->整柜
                        if ("1".equals(oldInquiry.getDistributionType())) {
                            //需要集输报价
                            distributionType = false;
                            hxdXgExamine = 0;
                        }
                        break;
                    //散货派送
                    case "1":
                        //整柜->散货
                        if ("0".equals(oldInquiry.getDistributionType())) {
                            distributionType = false;
                            inquiry.setHxAddress(inquiry.getDropStation());
                            if (!oldInquiry.getHxAddress().equals(inquiry.getHxAddress())) {
                                pickReturnCon = false;
                            }
                        } else {
                            //判断散货信息
                            //包装方式、是否堆叠、是否易碎
                            //合计 体积、重量、宽度
                            if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                            if (!picking(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                            //国内公路运输车辆、时效
                            if (!truckTransTime(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                        }
                        break;
                }
            } else {
                //回程处理
                switch (inquiry.getDistributionType()) {
                    //整柜派送
                    case "0":
                        //散货->整柜
                        if ("1".equals(oldInquiry.getDistributionType())) {
                            //需要公路报价
                            distributionType = false;
                            hxdXgExamine = 0;
                            inquiry.setInquiryState("2");
                        }
                        break;
                    //散货派送
                    case "1":
                        //整柜->散货
                        if ("0".equals(oldInquiry.getDistributionType())) {
                            distributionType = false;
                            inquiry.setHxAddress(inquiry.getDropStation());
                            if (!oldInquiry.getHxAddress().equals(inquiry.getHxAddress())) {
                                pickReturnCon = false;
                            }
                        } else {
                            //判断散货信息
                            //包装方式、是否堆叠、是否易碎
                            //合计 体积、重量、宽度
                            if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                            if (!picking(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                            //国内公路运输车辆、时效
                            if (!truckTransTime(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                        }
                        break;
                }
            }
        } else {
            // 拼箱
            /*包装方式、是否堆叠、是否易碎*/
            /*合计 体积、重量、宽度*/
            if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                goods = false;
            }
            if (!picking(oldInquiry, inquiry)) {
                picking = false;
            }
            /* 国内公路运输车辆、时效*/
            if (!truckTransTime(oldInquiry, inquiry)) {
                goods = false;
            }
        }
        /** *********询价标识*********** */
        boolean gonglu = false;
        boolean jishu = false;

        if ("0".equals(inquiry.getEastOrWest())) {
            // 去程
            if (!distributionType) {
                jishu = true;
                log.info("jishu派送更改:" + inquiry.getOrderId());
            }
            if (!goodsSend) {
                jishu = true;
                log.info("jishu散货货品派送更改:" + inquiry.getOrderId());
            }
        } else {
            // 回程
            if ((!distributionType) && ("0".equals(oldInquiry.getDistributionType()))) {
                log.info("gonglu派送更改:" + inquiry.getOrderId());
                gonglu = true;
            }
            if (!goodsSend) {
                gonglu = true;
                log.info("gonglu散货货品派送更改:" + inquiry.getOrderId());
            }
        }

        /*提货方式改变 为散货到堆场*/
        if ((!deliveryType) && "1".equals(inquiry.getDeliveryType())) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 去程
                gonglu = true;
            } else {
                // 回程
                jishu = true;
            }
        }
        if ("1".equals(inquiry.getEastOrWest())) {
            if (!deliveryType) {
                jishu = true;
            }
        }
        /*提货地改变*/
        if (!pickAdd) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 去程
                if ("1".equals(inquiry.getGoodsType())
                        || ("1".equals(inquiry.getDeliveryType()) && "0".equals(inquiry.getGoodsType()))) {
                    gonglu = true;
                }
            } else {
                // 回程
                jishu = true;
            }
        }
        /*送货地改变*/
        if (!SendAdd) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 去程
                jishu = true;
            } else {
                // 回程
                if ("1".equals(inquiry.getGoodsType())
                        || ("1".equals(inquiry.getDistributionType()) && "0".equals(inquiry.getGoodsType()))) {
                    gonglu = true;
                }
            }
        }
        if (!goods || !picking) {
            jishu = true;
            gonglu = true;
        }
        if (conType == 2 || conType == 3) {
            jishu = true;
        }
        /** *********订舱组审核标识*********** */
        boolean dcExamine = false;
        if (!(stationChange && deliveryType && pickReturnCon)) {
            dcExamine = true;
        }
        if (conNum != 0 || conType == 2 || conType == 4 || isSpecialCon(inquiry.getContainerType()) || !goods) {
            dcExamine = true;
        }
        if (!SendAdd) {
            dcExamine = true;
        }
        if (!eastDeclare) {
            dcExamine = true;
        }
        if (!distributionType) {
            dcExamine = true;
        }

        /** *********箱管审核标识*********** */
        // 如果是再次订舱，提还箱地变化不进行标识（避免发送箱管审核）
        boolean xg_pickreturn_con = pickReturnCon;
        if ("1".equals(inquiry.getBookingAgin())) {
            xg_pickreturn_con = true;
        }
        boolean xgExamine = false;
        if ((!xg_pickreturn_con) || isSpecialCon(inquiry.getContainerType())) {
            xgExamine = true;
        }
        inquiry.setTxAddress(oldResult.getTxAddress());
        BookingInquiryResultBackup resultBackup = railWayHandler.getRailWayFeeDTD(inquiry);
        if ("0".equals(inquiry.getEastOrWest())) {
            if (!gonglu) {
                if (resultBackup.getPickUpExpiration() == null) {
                    resultBackup.setPickUpExpiration(DateUtils.getNextDay(DateUtils.getNowDate(), 30));
                }
                //拼箱、散货
                if (("0".equals(inquiry.getGoodsType()) && "1".equals(inquiry.getDeliveryType()))
                        || "1".equals(inquiry.getGoodsType())) {
                    resultBackup.setPickUpAddress(oldResult.getPickUpAddress());
                    resultBackup.setPickUpDistance(oldResult.getPickUpDistance());
                    resultBackup.setPickUpAging(oldResult.getPickUpAging());
                    resultBackup.setPickUpFees(oldResult.getPickUpFees());
                    resultBackup.setPickUpCurrencyType(oldResult.getPickUpCurrencyType());
                    resultBackup.setPickUpExpiration(oldResult.getPickUpExpiration());
                    resultBackup.setPickUpRemark(oldResult.getPickUpRemark());
                    resultBackup.setPickUpFeedbackTime(oldResult.getPickUpFeedbackTime());
                    resultBackup.setInquiryNumber(oldResult.getInquiryNumber());
                    resultBackup.setEnquiryState(oldResult.getEnquiryState());
                    resultBackup.setXxyoRemark(oldResult.getXxyoRemark());
                } else {
                    // 整柜
                    resultBackup.setPickUpRemark(oldResult.getPickUpRemark());
//                    resultBackup.setInquiryNumber(oldResult.getInquiryNumber());
                }
            } else {
                resultBackup.setPickUpFees(null);
            }
            if (!jishu) {
                if (resultBackup.getDeliveryExpiration() == null) {
                    resultBackup.setDeliveryExpiration(DateUtils.getNextDay(DateUtils.getNowDate(), 30));
                }
                // 散货拼箱
                if (("0".equals(inquiry.getGoodsType()) && "1".equals(inquiry.getDistributionType()))
                        || "1".equals(inquiry.getGoodsType())) {
                    resultBackup.setDeliveryFees(oldResult.getDeliveryFees());
                } else {
                    // 整柜
                    resultBackup.setDeliveryFees(getFeeByConNum(oldResult.getDeliveryFees(), oldInquiry.getContainerNum(), inquiry.getContainerNum()));
                }
                resultBackup.setDeliveryAddress(oldResult.getDeliveryAddress());
                resultBackup.setDeliveryDistance(oldResult.getDeliveryDistance());
                resultBackup.setDeliveryAging(oldResult.getDeliveryAging());
                resultBackup.setDeliveryCurrencyType(oldResult.getDeliveryCurrencyType());
                resultBackup.setDeliveryExpiration(oldResult.getDeliveryExpiration());
                resultBackup.setDeliveryRemark(oldResult.getDeliveryRemark());
                resultBackup.setDeliveryFeedbackTime(oldResult.getDeliveryFeedbackTime());
                resultBackup.setInquiryNum(oldResult.getInquiryNum());
                resultBackup.setJsRemark(oldResult.getJsRemark());
            } else {
                resultBackup.setDeliveryFees(null);
            }
        } else {
            if (!gonglu) {
                if (resultBackup.getDeliveryExpiration() == null) {
                    resultBackup.setDeliveryExpiration(DateUtils.getNextDay(DateUtils.getNowDate(), 30));
                }
                //拼箱、散货
                if (("0".equals(inquiry.getGoodsType()) && "1".equals(inquiry.getDistributionType()))
                        || "1".equals(inquiry.getGoodsType())) {
                    resultBackup.setDeliveryAddress(oldResult.getDeliveryAddress());
                    resultBackup.setDeliveryDistance(oldResult.getDeliveryDistance());
                    resultBackup.setDeliveryAging(oldResult.getDeliveryAging());
                    resultBackup.setDeliveryFees(oldResult.getDeliveryFees());
                    resultBackup.setDeliveryCurrencyType(oldResult.getDeliveryCurrencyType());
                    resultBackup.setDeliveryExpiration(oldResult.getDeliveryExpiration());
                    resultBackup.setDeliveryRemark(oldResult.getDeliveryRemark());
                    resultBackup.setDeliveryFeedbackTime(oldResult.getDeliveryFeedbackTime());
                    resultBackup.setInquiryNumber(oldResult.getInquiryNumber());
                    resultBackup.setEnquiryState(oldResult.getEnquiryState());
                    resultBackup.setXxyoRemark(oldResult.getXxyoRemark());
                } else {
                    // 整柜
                    resultBackup.setDeliveryRemark(oldResult.getDeliveryRemark());
//                    resultBackup.setInquiryNumber(oldResult.getInquiryNumber());
                }
            } else {
                resultBackup.setDeliveryFees(null);
            }
            if (!jishu) {
                if (resultBackup.getPickUpExpiration() == null) {
                    resultBackup.setPickUpExpiration(DateUtils.getNextDay(DateUtils.getNowDate(), 30));
                }
                if (("0".equals(inquiry.getGoodsType()) && "1".equals(inquiry.getDeliveryType()))
                        || "1".equals(inquiry.getGoodsType())) {
                    resultBackup.setPickUpFees(oldResult.getPickUpFees());
                } else {
                    // 整柜
                    resultBackup.setPickUpFees(getFeeByConNum(oldResult.getPickUpFees(), oldInquiry.getContainerNum(), inquiry.getContainerNum()));
                }
                resultBackup.setPickUpAddress(oldResult.getPickUpAddress());
                resultBackup.setPickUpDistance(oldResult.getPickUpDistance());
                resultBackup.setPickUpAging(oldResult.getPickUpAging());
                resultBackup.setPickUpCurrencyType(oldResult.getPickUpCurrencyType());
                resultBackup.setPickUpExpiration(oldResult.getPickUpExpiration());
                resultBackup.setPickUpRemark(oldResult.getPickUpRemark());
                resultBackup.setPickUpFeedbackTime(oldResult.getPickUpFeedbackTime());
                resultBackup.setInquiryNum(oldResult.getInquiryNum());
                resultBackup.setJsRemark(oldResult.getJsRemark());
            } else {
                resultBackup.setPickUpFees(null);
            }
        }
        System.out.println(
                String.format(
                        "上下货站:%b, 箱量：%d, 箱型：%b, 特种：%b, 提还箱地：%b, 提货地：%b, 送货地：%b, 物品信息：%b, 提货方式：%b",
                        stationChange,
                        conNum,
                        conType,
                        isSpecialCon(inquiry.getContainerType()),
                        pickReturnCon,
                        pickAdd,
                        SendAdd,
                        goods,
                        deliveryType));
        if (!stationChange) {
            gwcz[2] = 1;
        }
        if (!pickReturnCon) {
            gwcz[3] = 1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("gonglu", gonglu);
        map.put("jishu", jishu);
        map.put("dcExamine", dcExamine);
        map.put("xgExamine", xgExamine);
        map.put("hxdXgExamine", hxdXgExamine);
        map.put("resultBackup", resultBackup);
        map.put("gwcz", gwcz);
        map.put("inquiryBp", resultBackup.getBookingInquiry());
        return map;
    }

    /**
     * 判断“门到站”服务信息
     *
     * @param oldInquiry
     * @param inquiry
     * @param gwcz
     * @return
     */
    public Map<String, Object> compareInquiryDoorToStation(
            BookingInquiry oldInquiry, BookingInquiryBackup inquiry, BookingInquiryResult oldResult, int[] gwcz) {
        //gwcz：{-门到站，+门到站，上下货站，提还箱地，到站通知邮箱}
        /*注. true: 没有改变，false:改变*/
        //        影响询价的信息：
        //        1、服务更改
        //        2、上下货站
        boolean stationChange = true;
        //        3、提货地、收货地
        boolean pickSendAdd = true;
        //        4、整柜 箱量增加减少 特种箱
        /*0：不变，-n 减少n个，+n 增加n个*/
        int conNum = 0;
        /*0:不变。1：普->普,2：普->特,3：特->普,4：特->特*/
        int conType = 0;
        //        5、提还箱地
        boolean pickReturnCon = true;
        //        6、拼箱货品信息
        /*综合判断货量、包装、车辆*/
        boolean goods = true;
        boolean picking = true;
        //        7、提货方式
        boolean deliveryType = true;

        //        9、回程报关
        boolean eastDeclare = true;
        // 下货站
        if (!(inquiry.getDropStation() + "").equals((oldInquiry.getDropStation() + ""))) {
            stationChange = false;
        }
        if ("0".equals(inquiry.getEastOrWest())) {
            // 发货地 省市区详细地址
            if (!((inquiry.getSenderProvince() + "").equals((oldInquiry.getSenderProvince() + ""))
                    && (inquiry.getSenderCity() + "").equals((oldInquiry.getSenderCity() + ""))
                    && (inquiry.getSenderArea() + "").equals((oldInquiry.getSenderArea() + ""))
                    && (inquiry.getShipmentPlace() + "").equals((oldInquiry.getShipmentPlace() + "")))) {
                pickSendAdd = false;
            }
        } else {
            if (!inquiry.getIsOrderCustoms().equals(oldInquiry.getIsOrderCustoms())) {
                eastDeclare = false;
            }
            // 发货地 省市区详细地址
            if (!((inquiry.getShipmentPlace() + "").equals(oldInquiry.getShipmentPlace() + ""))) {
                pickSendAdd = false;
            }
        }
        if ("0".equals(inquiry.getGoodsType())) {
            if ("0".equals(oldInquiry.getBookingService()) || "1".equals(oldInquiry.getBookingService())) {
                // 整柜时 判断提货方式
                deliveryType = inquiry.getDeliveryType().equals(oldInquiry.getDeliveryType());
            }
//            if ("2".equals(oldInquiry.getBookingService()) || "3".equals(oldInquiry.getBookingService())) {
//                // 整柜时 判断提货方式
//                deliveryType = inquiry.getDeliveryType().equals(oldInquiry.getDeliverySelfType());
//            }
            /*箱型、箱量、箱属*/
            conNum = conNum(oldInquiry, inquiry);
            conType = conType(oldInquiry, inquiry);
            /*还箱地*/
            if (!(inquiry.getHxAddress() + "").equals((oldInquiry.getHxAddress() + ""))) {
                pickReturnCon = false;
            }
            // 散货到堆场
            if ("1".equals(inquiry.getDeliveryType())) {
                /*包装方式、是否堆叠、是否易碎*/
                /*合计 体积、重量、宽度*/
                if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                    goods = false;
                }
                if (!picking(oldInquiry, inquiry)) {
                    picking = false;
                }
            }
        } else {
            // 拼箱
            /*包装方式、是否堆叠、是否易碎*/
            /*合计 体积、重量、宽度*/
            if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                goods = false;
            }
            if (!picking(oldInquiry, inquiry)) {
                picking = false;
            }
            /* 国内公路运输车辆、时效*/
            if (!truckTransTime(oldInquiry, inquiry)) {
                goods = false;
            }
        }
        /** *********询价标识*********** */
        boolean gonglu = false;
        boolean jishu = false;

        /*提货方式改变 为散货到堆场*/
        if ((!deliveryType) && "1".equals(inquiry.getDeliveryType())) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 去程
                gonglu = true;
            } else {
                // 回程
                jishu = true;
            }
        }
        if ("1".equals(inquiry.getEastOrWest())) {
            if (!deliveryType) {
                jishu = true;
            }
        }
        /*提货地改变*/
        if (!pickSendAdd) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 去程
                if ("1".equals(inquiry.getGoodsType())
                        || ("1".equals(inquiry.getDeliveryType()) && "0".equals(inquiry.getGoodsType()))) {
                    gonglu = true;
                }
            } else {
                // 回程
                jishu = true;
            }
        }
        if (!goods || !picking) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 去程
                gonglu = true;
            } else {
                // 回程
                jishu = true;
            }
        }
        if (conType == 2 || conType == 3) {
            if ("1".equals(inquiry.getEastOrWest())) {
                // 回程
                jishu = true;
            }
        }
        /** *********订舱组审核标识*********** */
        boolean dcExamine = false;
        if (!(stationChange && deliveryType && pickReturnCon)) {
            dcExamine = true;
        }
        if (conNum != 0 || conType == 2 || conType == 4 || isSpecialCon(inquiry.getContainerType()) || !goods) {
            dcExamine = true;
        }
        if (!eastDeclare) {
            dcExamine = true;
        }
//        if (!pickSendAdd) {
//            dcExamine = true;
//        }

        /** *********箱管审核标识*********** */
        // 如果是再次订舱，提还箱地变化不进行标识（避免发送箱管审核）
        boolean xg_pickreturn_con = pickReturnCon;
        if ("1".equals(inquiry.getBookingAgin())) {
            xg_pickreturn_con = true;
        }
        boolean xgExamine = false;
        if ((!xg_pickreturn_con) || isSpecialCon(inquiry.getContainerType())) {
            xgExamine = true;
        }
        inquiry.setTxAddress(oldResult.getTxAddress());
        BookingInquiryResultBackup resultBackup = railWayHandler.getRailWayFeeDTS(inquiry);
        if ("0".equals(inquiry.getEastOrWest())) {
            if (!gonglu) {
                if (resultBackup.getPickUpExpiration() == null) {
                    resultBackup.setPickUpExpiration(DateUtils.getNextDay(DateUtils.getNowDate(), 30));
                }
                //拼箱、散货
                if (("0".equals(inquiry.getGoodsType()) && "1".equals(inquiry.getDeliveryType()))
                        || "1".equals(inquiry.getGoodsType())) {
                    resultBackup.setPickUpAddress(oldResult.getPickUpAddress());
                    resultBackup.setPickUpDistance(oldResult.getPickUpDistance());
                    resultBackup.setPickUpAging(oldResult.getPickUpAging());
                    resultBackup.setPickUpFees(oldResult.getPickUpFees());
                    resultBackup.setPickUpCurrencyType(oldResult.getPickUpCurrencyType());
                    resultBackup.setPickUpExpiration(oldResult.getPickUpExpiration());
                    resultBackup.setPickUpRemark(oldResult.getPickUpRemark());
                    resultBackup.setPickUpFeedbackTime(oldResult.getPickUpFeedbackTime());
                    resultBackup.setInquiryNumber(oldResult.getInquiryNumber());
                    resultBackup.setEnquiryState(oldResult.getEnquiryState());
                    resultBackup.setXxyoRemark(oldResult.getXxyoRemark());
                } else {
                    // 整柜
                    resultBackup.setPickUpRemark(oldResult.getPickUpRemark());
//                    resultBackup.setInquiryNumber(oldResult.getInquiryNumber());
                }
            } else {
                resultBackup.setPickUpFees(null);
            }
        } else {
            if (!jishu) {
                if (resultBackup.getPickUpExpiration() == null) {
                    resultBackup.setPickUpExpiration(DateUtils.getNextDay(DateUtils.getNowDate(), 30));
                }
                if (("0".equals(inquiry.getGoodsType()) && "1".equals(inquiry.getDistributionType()))
                        || "1".equals(inquiry.getGoodsType())) {
                    resultBackup.setPickUpFees(oldResult.getPickUpFees());
                } else {
                    // 整柜
                    resultBackup.setPickUpFees(getFeeByConNum(oldResult.getPickUpFees(), oldInquiry.getContainerNum(), inquiry.getContainerNum()));
                }
                resultBackup.setPickUpAddress(oldResult.getPickUpAddress());
                resultBackup.setPickUpDistance(oldResult.getPickUpDistance());
                resultBackup.setPickUpAging(oldResult.getPickUpAging());
                resultBackup.setPickUpCurrencyType(oldResult.getPickUpCurrencyType());
                resultBackup.setPickUpExpiration(oldResult.getPickUpExpiration());
                resultBackup.setPickUpRemark(oldResult.getPickUpRemark());
                resultBackup.setPickUpFeedbackTime(oldResult.getPickUpFeedbackTime());
                resultBackup.setInquiryNum(oldResult.getInquiryNum());
                resultBackup.setJsRemark(oldResult.getJsRemark());
            } else {
                resultBackup.setPickUpFees(null);
            }
        }
        System.out.println(
                String.format(
                        "上下货站:%b, 箱量：%d, 箱型：%b, 特种：%b, 提还箱地：%b, 提送货地：%b, 物品信息：%b, 提货方式：%b",
                        stationChange,
                        conNum,
                        conType,
                        isSpecialCon(inquiry.getContainerType()),
                        pickReturnCon,
                        pickSendAdd,
                        goods,
                        deliveryType));
        if (!stationChange) {
            gwcz[2] = 1;
        }
        if (!pickReturnCon) {
            gwcz[3] = 1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("gonglu", gonglu);
        map.put("jishu", jishu);
        map.put("dcExamine", dcExamine);
        map.put("xgExamine", xgExamine);
        map.put("hxdXgExamine", 1);
        map.put("resultBackup", resultBackup);
        map.put("gwcz", gwcz);
        map.put("inquiryBp", resultBackup.getBookingInquiry());
        return map;
    }

    /**
     * 判断“站到站”服务信息
     *
     * @param oldInquiry
     * @param inquiry
     * @param gwcz
     * @return
     */
    public Map<String, Object> compareInquiryStationToStation(
            BookingInquiry oldInquiry, BookingInquiryBackup inquiry, int[] gwcz) {
        //gwcz：{-门到站，+门到站，上下货站，提还箱地，到站通知邮箱}
        BookingInquiryResultBackup resultBackup = railWayHandler.getRailWayFeeSTS(inquiry);
        /*注. true: 没有改变，false:改变*/
        //        影响询价的信息：
        //        1、服务更改
        //        2、上下货站
        boolean uploadStation = true;
        boolean dropStation = true;
        //        3、提货地、收货地
        boolean pickSendAdd = true;
        //        4、整柜 箱量增加减少 特种箱
        /*0：不变，-n 减少n个，+n 增加n个*/
        int conNum = 0;
        /*0:不变。1：普->普,2：普->特,3：特->普,4：特->特*/
        int conType = 0;
        //        5、提还箱地
        boolean pickReturnCon = true;
        //        6、拼箱货品信息
        /*综合判断货量、包装、车辆*/
        boolean goods = true;
        //        7、自送货方式
        boolean deliverySelf = true;
        //        9、报关
        boolean eastDeclare = true;
        // 上货站下货站
        if (!(inquiry.getUploadStation() + "").equals(oldInquiry.getUploadStation() + "")) {
            uploadStation = false;
        }
        if (!(inquiry.getDropStation() + "").equals(oldInquiry.getDropStation() + "")) {
            dropStation = false;
        }
        if (inquiry.getEastOrWest().equals("1")) {
            if (!inquiry.getIsOrderCustoms().equals(oldInquiry.getIsOrderCustoms())) {
                eastDeclare = false;
            }
        }
        if ("0".equals(inquiry.getGoodsType())) {
//            if ("0".equals(oldInquiry.getBookingService()) || "1".equals(oldInquiry.getBookingService())) {
//                // 整柜时 判断提货方式
//                deliverySelf = inquiry.getDeliverySelfType().equals(oldInquiry.getDeliveryType());
//            }
            if ("2".equals(oldInquiry.getBookingService()) || "3".equals(oldInquiry.getBookingService())) {
                // 整柜时 判断提货方式
                deliverySelf = inquiry.getDeliverySelfType().equals(oldInquiry.getDeliverySelfType());
            }
            /*还箱地*/
            if (!(inquiry.getHxAddress() + "").equals((oldInquiry.getHxAddress() + ""))) {
                pickReturnCon = false;
            }
            /*箱型、箱量、箱属*/
            conNum = conNum(oldInquiry, inquiry);
            conType = conType(oldInquiry, inquiry);
            // 整柜到堆场
            if ("1".equals(inquiry.getDeliverySelfType())) {
                /*提箱地*/
                /*提箱地*/
                if (!(inquiry.getTxAddress() + "").equals((oldInquiry.getTxAddress() + ""))) {
                    pickReturnCon = false;
                }
            }
            // 散货到堆场
            if ("0".equals(inquiry.getDeliverySelfType())) {
                /*包装方式、是否堆叠、是否易碎*/
                /*合计 体积、重量、宽度*/
                if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                    goods = false;
                }
            }
        } else {
            // 拼箱
            /*包装方式、是否堆叠、是否易碎*/
            /*合计 体积、重量、宽度*/
            if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                goods = false;
            }
        }

        /** *********询价标识*********** */
        boolean gonglu = false;
        boolean jishu = false;

        /** *********订舱组审核标识*********** */
        boolean dcExamine = false;
        if (!(uploadStation && deliverySelf && pickReturnCon)) {
            dcExamine = true;
        }
        if (!eastDeclare) {
            dcExamine = true;
        }
        /*去程整柜，站到门/门到门，减少站到门，改成站到站*/
        int sd = 0;
        if ("0".equals(inquiry.getEastOrWest()) && "0".equals(inquiry.getGoodsType())
                && ("3".equals(oldInquiry.getBookingService()) || "0".equals(oldInquiry.getBookingService()))) {
            sd = 1;
        }
        if ((!dropStation)) {
            dcExamine = true;
        }
        if (conNum != 0 || conType == 2 || conType == 4 || isSpecialCon(inquiry.getContainerType()) || !goods) {
            dcExamine = true;
        }

        /** *********箱管审核标识*********** */

        // 如果是再次订舱，提还箱地变化不进行标识（避免发送箱管审核）
        boolean xg_pickreturn_con = pickReturnCon;
        if ("1".equals(inquiry.getBookingAgin())) {
            xg_pickreturn_con = true;
        }
        boolean xgExamine = false;
        if ((!xg_pickreturn_con) || isSpecialCon(inquiry.getContainerType())) {
            xgExamine = true;
        }
        System.out.println(
                String.format(
                        "上下货站:%b, 箱量：%d, 箱型：%b, 特种：%b, 提还箱地：%b, 物品信息：%b, 自送货方式：%b",
                        uploadStation,
                        conNum,
                        conType,
                        isSpecialCon(inquiry.getContainerType()),
                        pickReturnCon,
                        goods,
                        deliverySelf));
        if (!uploadStation) {
            gwcz[2] = 1;
        }
        if (!dropStation) {
            gwcz[2] = 1;
        }
        if (!pickReturnCon) {
            gwcz[3] = 1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("gonglu", gonglu);
        map.put("jishu", jishu);
        map.put("dcExamine", dcExamine);
        map.put("xgExamine", xgExamine);
        map.put("hxdXgExamine", 1);
        map.put("resultBackup", resultBackup);
        map.put("gwcz", gwcz);
        map.put("inquiryBp", resultBackup.getBookingInquiry());
        return map;
    }

    /**
     * 判断“站到门”服务信息
     *
     * @param oldInquiry
     * @param inquiry
     * @param gwcz
     * @return
     */
    public Map<String, Object> compareInquiryStationToDoor(
            BookingInquiry oldInquiry, BookingInquiryBackup inquiry, BookingInquiryResult oldResult, int[] gwcz) {
        //gwcz：{-门到站，+门到站，上下货站，提还箱地，到站通知邮箱}
        BookingInquiryBackup ib = new BookingInquiryBackup();
        /*注. true: 没有改变，false:改变*/
        //        影响询价的信息：
        //        1、服务更改
        //        2、上下货站
        boolean stationChange = true;
        //        3、提货地、收货地
        boolean pickSendAdd = true;
        //        4、整柜 箱量增加减少 特种箱
        /*0：不变，-n 减少n个，+n 增加n个*/
        int conNum = 0;
        /*0:不变。1：普->普,2：普->特,3：特->普,4：特->特*/
        int conType = 0;
        //        5、提还箱地
        boolean pickReturnCon = true;
        //        6、拼箱货品信息
        /*综合判断货量、包装、车辆*/
        boolean goods = true;
        boolean picking = true;
        boolean goodsSend = true;
        //        7、自送货方式
        boolean deliverySelfType = true;
        //        8、派货方式
        boolean distributionType = true;
        //        9、报关
        boolean eastDeclare = true;
        int hxdXgExamine = 1;

        inquiry.setHxAddress(oldResult.getHxAddress());
        // 上货站
        if (!inquiry.getUploadStation().equals(oldInquiry.getUploadStation())) {
            stationChange = false;
        }
        // 收货地
        if ("0".equals(inquiry.getEastOrWest())) {
            if (!(inquiry.getReceiptPlace() + "").equals((oldInquiry.getReceiptPlace() + ""))) {
                pickSendAdd = false;
            }
        } else {
            if (!inquiry.getIsOrderCustoms().equals(oldInquiry.getIsOrderCustoms())) {
                eastDeclare = false;
            }
            // 收货地
            if (!((inquiry.getReceiveProvince() + "").equals((oldInquiry.getReceiveProvince() + ""))
                    && (inquiry.getReceiveCity() + "").equals((oldInquiry.getReceiveCity() + ""))
                    && (inquiry.getReceiveArea() + "").equals((oldInquiry.getReceiveArea() + ""))
                    && (inquiry.getReceiptPlace() + "").equals((oldInquiry.getReceiptPlace() + "")))) {
                pickSendAdd = false;
            }
        }
        if ("0".equals(inquiry.getGoodsType())) {
//            if ("0".equals(oldInquiry.getBookingService()) || "1".equals(oldInquiry.getBookingService())) {
//                // 整柜时 判断提货方式
//                deliverySelfType = inquiry.getDeliverySelfType().equals(oldInquiry.getDeliveryType());
//            }
            if ("2".equals(oldInquiry.getBookingService()) || "3".equals(oldInquiry.getBookingService())) {
                // 整柜时 判断提货方式
                deliverySelfType = inquiry.getDeliverySelfType().equals(oldInquiry.getDeliverySelfType());
            }
            ib.setDeliverySelfType(inquiry.getDeliverySelfType());
            /*箱型、箱量、箱属*/
            conNum = conNum(oldInquiry, inquiry);
            conType = conType(oldInquiry, inquiry);
            // 整柜到堆场
            if ("1".equals(inquiry.getDeliverySelfType())) {
                /*提箱地*/
                if (!(inquiry.getTxAddress() + "").equals((oldInquiry.getTxAddress() + ""))) {
                    pickReturnCon = false;
                }
            }
            // 散货到堆场
            if ("0".equals(inquiry.getDeliverySelfType())) {
                /*包装方式、是否堆叠、是否易碎*/
                /*合计 体积、重量、宽度*/
                if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                    goods = false;
                }
                if (!picking(oldInquiry, inquiry)) {
                    picking = false;
                }
            }
            // 整柜时，判断派送方式
            if ("0".equals(inquiry.getEastOrWest())) {
                //*去程处理*//
                switch (inquiry.getDistributionType()) {
                    //*整柜派送*//
                    case "0":
                        //散货->整柜
                        if ("1".equals(oldInquiry.getDistributionType())) {
                            //*需要集输报价*//
                            distributionType = false;
                            hxdXgExamine = 0;
                        }
                        break;
                    //*散货派送*//
                    case "1":
                        //整柜->散货
                        if ("0".equals(oldInquiry.getDistributionType())) {
                            distributionType = false;
                            inquiry.setHxAddress(inquiry.getDropStation());
                            if (!oldInquiry.getHxAddress().equals(inquiry.getHxAddress())) {
                                pickReturnCon = false;
                            }
                        } else {
                            //*判断散货信息*//
                            //*包装方式、是否堆叠、是否易碎*//
                            //*合计 体积、重量、宽度*//
                            if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                            if (!picking(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                            //* 国内公路运输车辆、时效*//
                            if (!truckTransTime(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                        }
                        break;
                }
            } else {
                //*回程处理*//
                switch (inquiry.getDistributionType()) {
                    //*整柜派送*//
                    case "0":
                        //散货->整柜
                        if ("1".equals(oldInquiry.getDistributionType())) {
                            //*需要公路报价*//
                            distributionType = false;
                            hxdXgExamine = 0;
                            inquiry.setInquiryState("2");
                        }
                        break;
                    //*散货派送*//
                    case "1":
                        //整柜->散货
                        if ("0".equals(oldInquiry.getDistributionType())) {
                            distributionType = false;
                            inquiry.setHxAddress(inquiry.getDropStation());
                            if (!oldInquiry.getHxAddress().equals(inquiry.getHxAddress())) {
                                pickReturnCon = false;
                            }
                        } else {
                            //*判断散货信息*//*
                            //*包装方式、是否堆叠、是否易碎*//
                            //*合计 体积、重量、宽度*//*
                            if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                            if (!picking(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                            //* 国内公路运输车辆、时效*//
                            if (!truckTransTime(oldInquiry, inquiry)) {
                                goodsSend = false;
                            }
                        }
                        break;
                }
            }
        } else {
            /*包装方式、是否堆叠、是否易碎*/
            /*合计 体积、重量、宽度*/
            if (!pickingAndGoodsTotal(oldInquiry, inquiry)) {
                goods = false;
            }
            if (!picking(oldInquiry, inquiry)) {
                picking = false;
            }
        }
        /** *********询价标识*********** */
        boolean gonglu = false;
        boolean jishu = false;

        if ("0".equals(inquiry.getEastOrWest())) {
            // 去程
            if (!distributionType) {
                jishu = true;
                log.info("jishu派送更改:" + inquiry.getOrderId());
            }
            if (!goodsSend) {
                jishu = true;
                log.info("jishu散货货品派送更改:" + inquiry.getOrderId());
            }
        } else {
            // 回程
            if ((!distributionType) && ("0".equals(oldInquiry.getDistributionType()))) {
                gonglu = true;
                log.info("gonglu派送更改:" + inquiry.getOrderId());
            }
            if (!goodsSend) {
                gonglu = true;
                log.info("gonglu散货货品派送更改:" + inquiry.getOrderId());
            }
        }
        /*提货方式改变 为散货到堆场*/
//        if ((!deliverySelfType) && "1".equals(inquiry.getDeliveryType())) {
//            if ("0".equals(inquiry.getEastOrWest())) {
//                // 去程
//                jishu = true;
//            } else {
//                // 回程
//                gonglu = true;
//            }
//        }
//        if ("0".equals(inquiry.getEastOrWest())) {
//            if (!deliverySelfType) {
//                jishu = true;
//            }
//        }
        if (!pickSendAdd) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 去程
                jishu = true;
            } else {
                // 回程
                if ("1".equals(inquiry.getGoodsType())
                        || ("1".equals(inquiry.getDeliveryType()) && "0".equals(inquiry.getGoodsType()))) {
                    gonglu = true;
                }
            }
        }
        if (!goods || !picking) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 去程
                jishu = true;
            } else {
                // 回程
                gonglu = true;
            }
        }
        if (conType == 2 || conType == 3) {
            if ("0".equals(inquiry.getEastOrWest())) {
                // 回程
                jishu = true;
            }
        }
        /** *********订舱组审核标识*********** */
        boolean dcExamine = false;
        if (!(stationChange && deliverySelfType && pickReturnCon)) {
            dcExamine = true;
        }
        if (conNum != 0 || conType == 2 || conType == 4 || isSpecialCon(inquiry.getContainerType()) || !goods) {
            dcExamine = true;
        }
        if (!eastDeclare) {
            dcExamine = true;
        }
        if (!distributionType) {
            dcExamine = true;
        }
        /*去程整柜，站到站、门到站，增加站到门*/
        int sd = 0;
        if ("0".equals(inquiry.getEastOrWest()) && "0".equals(inquiry.getGoodsType())
                && ("2".equals(oldInquiry.getBookingService()) || "1".equals(oldInquiry.getBookingService()))) {
            sd = 1;
        }
        /*不是增加站到门并且更改送货地的情况下，订舱审核*/
        if ((!pickSendAdd) && sd == 0) {
            dcExamine = true;
        }

        /** *********箱管审核标识*********** */

        // 如果是再次订舱，提还箱地变化不进行标识（避免发送箱管审核）
        boolean xg_pickreturn_con = pickReturnCon;
        if ( "1".equals(inquiry.getBookingAgin())) {
            xg_pickreturn_con = true;
        }
        boolean xgExamine = false;
        if ((!xg_pickreturn_con) || isSpecialCon(inquiry.getContainerType())) {
            xgExamine = true;
        }
        BookingInquiryResultBackup resultBackup = railWayHandler.getRailWayFeeSTD(inquiry);
        if ("1".equals(inquiry.getEastOrWest())) {
            if (!gonglu) {
                if (resultBackup.getDeliveryExpiration() == null) {
                    resultBackup.setDeliveryExpiration(DateUtils.getNextDay(DateUtils.getNowDate(), 30));
                }
                //拼箱、散货
                if (("0".equals(inquiry.getGoodsType()) && "1".equals(inquiry.getDistributionType()))
                        || "1".equals(inquiry.getGoodsType())) {
                    resultBackup.setDeliveryAddress(oldResult.getDeliveryAddress());
                    resultBackup.setDeliveryDistance(oldResult.getDeliveryDistance());
                    resultBackup.setDeliveryAging(oldResult.getDeliveryAging());
                    resultBackup.setDeliveryFees(oldResult.getDeliveryFees());
                    resultBackup.setDeliveryCurrencyType(oldResult.getDeliveryCurrencyType());
                    resultBackup.setDeliveryExpiration(oldResult.getDeliveryExpiration());
                    resultBackup.setDeliveryRemark(oldResult.getDeliveryRemark());
                    resultBackup.setDeliveryFeedbackTime(oldResult.getDeliveryFeedbackTime());
                    resultBackup.setInquiryNumber(oldResult.getInquiryNumber());
                    resultBackup.setEnquiryState(oldResult.getEnquiryState());
                    resultBackup.setXxyoRemark(oldResult.getXxyoRemark());
                } else {
                    // 整柜
                    resultBackup.setDeliveryRemark(oldResult.getDeliveryRemark());
//                    resultBackup.setInquiryNumber(oldResult.getInquiryNumber());
                }
            } else {
                resultBackup.setDeliveryFees(null);
            }
        } else {
            if (!jishu) {
                if (resultBackup.getDeliveryExpiration() == null) {
                    resultBackup.setDeliveryExpiration(DateUtils.getNextDay(DateUtils.getNowDate(), 30));
                }
                // 散货拼箱
                if (("0".equals(inquiry.getGoodsType()) && "1".equals(inquiry.getDistributionType()))
                        || "1".equals(inquiry.getGoodsType())) {
                    resultBackup.setDeliveryFees(oldResult.getDeliveryFees());
                } else {
                    // 整柜
                    resultBackup.setDeliveryFees(getFeeByConNum(oldResult.getDeliveryFees(), oldInquiry.getContainerNum(), inquiry.getContainerNum()));
                }
                resultBackup.setDeliveryAddress(oldResult.getDeliveryAddress());
                resultBackup.setDeliveryDistance(oldResult.getDeliveryDistance());
                resultBackup.setDeliveryAging(oldResult.getDeliveryAging());
                resultBackup.setDeliveryCurrencyType(oldResult.getDeliveryCurrencyType());
                resultBackup.setDeliveryExpiration(oldResult.getDeliveryExpiration());
                resultBackup.setDeliveryRemark(oldResult.getDeliveryRemark());
                resultBackup.setDeliveryFeedbackTime(oldResult.getDeliveryFeedbackTime());
                resultBackup.setInquiryNum(oldResult.getInquiryNum());
                resultBackup.setJsRemark(oldResult.getJsRemark());
            } else {
                resultBackup.setDeliveryFees(null);
            }
        }
        System.out.println(
                String.format(
                        "上下货站:%b, 箱量：%d, 箱型：%b, 特种：%b, 提还箱地：%b, 提送货地：%b, 物品信息：%b, 自送货方式：%b",
                        stationChange,
                        conNum,
                        conType,
                        isSpecialCon(inquiry.getContainerType()),
                        pickReturnCon,
                        pickSendAdd,
                        goods,
                        deliverySelfType));
        if (!stationChange) {
            gwcz[2] = 1;
        }
        if (!pickReturnCon) {
            gwcz[3] = 1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("gonglu", gonglu);
        map.put("jishu", jishu);
        map.put("dcExamine", dcExamine);
        map.put("hxdXgExamine", hxdXgExamine);
        map.put("xgExamine", xgExamine);
        map.put("resultBackup", resultBackup);
        map.put("gwcz", gwcz);
        map.put("inquiryBp", resultBackup.getBookingInquiry());
        return map;
    }

    /**
     * 比较客户提货方式
     */
    public boolean deliveryType(String oldInquiry, String inquiry) {
        if (inquiry.equals(oldInquiry)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较 箱量箱属箱型
     */
    public boolean container(BookingInquiry oldInquiry, BookingInquiryBackup inquiry) {
        if (inquiry.getContainerType().equals(oldInquiry.getContainerType())
                && inquiry.getContainerBelong().equals(oldInquiry.getContainerBelong())
                && inquiry.getContainerNum().equals(oldInquiry.getContainerNum())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断箱型改变
     */
    public int conType(BookingInquiry oldInquiry, BookingInquiryBackup inquiry) {
        if (inquiry.getContainerType().equals(oldInquiry.getContainerType())) {
            // 0：不变
            return 0;
        } else if (!isSpecialCon(oldInquiry.getContainerType())
                && !isSpecialCon(inquiry.getContainerType())) {
            // 普->普
            return 1;
        } else if (!isSpecialCon(oldInquiry.getContainerType())
                && isSpecialCon(inquiry.getContainerType())) {
            // 普->特
            return 2;
        } else if (isSpecialCon(oldInquiry.getContainerType())
                && !isSpecialCon(inquiry.getContainerType())) {
            // 特->普
            return 3;
        } else {
            // 特->特
            return 4;
        }
    }

    /**
     * 判断箱量改变
     */
    public int conNum(BookingInquiry oldInquiry, BookingInquiryBackup inquiry) {
        return inquiry.getContainerNum() - oldInquiry.getContainerNum();
    }

    /**
     * 比较 包装[包装方式、易碎、堆叠]、[合计]
     */
    public boolean pickingAndGoodsTotal(BookingInquiry oldInquiry, BookingInquiryBackup inquiry) {
        boolean flag = true;
        if (!(new BigDecimal(inquiry.getTotalVolume()).compareTo(new BigDecimal(StringUtils.isEmpty(oldInquiry.getTotalVolume()) ? "0" : oldInquiry.getTotalVolume())) == 0)
                || !(new BigDecimal(inquiry.getTotalWeight()).compareTo(new BigDecimal(StringUtils.isEmpty(oldInquiry.getTotalWeight()) ? "0" : oldInquiry.getTotalWeight())) == 0)
                || !(new BigDecimal(inquiry.getTotalAmount()).compareTo(new BigDecimal(StringUtils.isEmpty(oldInquiry.getTotalAmount()) ? "0" : oldInquiry.getTotalAmount())) == 0)
        ) {
            flag = false;
        }
        return flag;
    }

    public boolean picking(BookingInquiry oldInquiry, BookingInquiryBackup inquiry) {
        boolean flag = true;
        if (!inquiry.getIsStack().equals(oldInquiry.getIsStack())
                || !inquiry.getGoodsFragile().equals(oldInquiry.getGoodsFragile())
                || !inquiry.getPackageType().equals(oldInquiry.getPackageType())) {
            flag = false;
        }
        return flag;
    }

    /**
     * 国内公路运输车辆、时效
     */
    public boolean truckTransTime(BookingInquiry oldInquiry, BookingInquiryBackup inquiry) {
        /* 国内公路运输车辆*/
        /*时效*/
        if (!inquiry.getTruckType().equals(oldInquiry.getTruckType())
                || !inquiry.getLimitation().equals(oldInquiry.getLimitation())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断拼箱物品信息是否危险品和超长超重
     *
     * @param bookingInquiryGoodsDetailsList
     * @param language
     * @return
     */
    public Map dangerous(
            List<BookingInquiryGoodsDetailsBackup> bookingInquiryGoodsDetailsList, String language) {
        StringBuilder sb0 = new StringBuilder();
        Boolean over = false;
        for (BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetails :
                bookingInquiryGoodsDetailsList) {
            if (StringUtils.isNotEmpty(bookingInquiryGoodsDetails.getGoodsName())) {
                String noteLevel = isDangerousGoods(bookingInquiryGoodsDetails.getGoodsName(), language);
                if ("0".equals(noteLevel)) {
                    sb0.append(bookingInquiryGoodsDetails.getGoodsName()).append("、");
                }
            }
            // 单件超长或超重（长11m,   宽 2.2m  , 高2.5，重量超过15吨）
            if (new BigDecimal(bookingInquiryGoodsDetails.getGoodsLength()).compareTo(new BigDecimal("1200"))==1 ||
                    new BigDecimal(bookingInquiryGoodsDetails.getGoodsWidth()).compareTo(new BigDecimal("220"))==1 ||
                    new BigDecimal(bookingInquiryGoodsDetails.getGoodsHeight()).compareTo(new BigDecimal("250"))==1 ||
                    new BigDecimal(bookingInquiryGoodsDetails.getGoodsWeight()).compareTo(new BigDecimal("15000"))==1) {
                over = true;
            }
        }
        Map map = new HashMap();
        map.put("dangerous", sb0);
        map.put("over", over);
        return map;
    }

    /**
     * 判断货物品名是否为危险品
     *
     * @param goodsName
     * @return
     */
    public String isDangerousGoods(String goodsName, String language) {
        DangerousGoods dangerousGoods = dangerousGoodsMapper.getCnEnName(goodsName, language);
        if (StringUtils.isNotNull(dangerousGoods)) {
            return dangerousGoods.getNoteLevel();
        } else if (goodsName.contains("混合")
                || goodsName.contains("液")
                || goodsName.contains("剂")
                || goodsName.contains("粉")) {
            return "1";
        }
        return "2";
    }

    /**
     * 判断箱型是否是特种箱
     */
    public static boolean isSpecialCon(String conType) {
        // 20HOT:20尺超高开顶箱, 20HT:20尺挂衣箱, 20OT:20尺普高开顶箱, 40HOT:40尺超高开顶箱,
        // 40HT:40尺挂衣箱, 40MT:40尺分层箱, 40OT:40尺普高开顶箱, 40RF:40尺冷藏箱, 45RF:45尺冷藏箱,
        String[] cons = {"20HOT", "20HT", "20OT", "40HOT", "40HT", "40MT", "40OT", "40RF", "45RF"};
        boolean flag = false;
        for (int i = 0; i < cons.length; i++) {
            if (cons[i].equals(conType)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 托书修改时获取班列信息
     *
     * @param busiShippingorder 订单
     * @return 班列
     */
    @Override
    public List<BusiClasses> selectZXList(BusiShippingorders busiShippingorder) {
        List<BusiClasses> busiClassesList = busiShippingorderMapper.selectZXList(busiShippingorder);
        BusiClasses busiClasses = busiShippingorderMapper.selectClassByOrderId(busiShippingorder.getOrderId(), busiShippingorder.getLanguage());
        boolean has = false;
        for (BusiClasses busiClass : busiClassesList) {
            busiClass.setIsChoose("1");  //0不可选 1可选
            if (busiClasses.getClassId().equals(busiClass.getClassId())) {
                has = true;
            }
        }
        if (busiShippingorder.getClassEastandwest().equals("1")) {
            String uploadStation = busiShippingorder.getOrderUploadsite();
            String country = busiShippingorder.getCountry();
            JsBackTimeset timeset = jsBackTimesetService.selectByStationPickCountry(uploadStation, country);
            if (null != timeset) {
                Integer fcl = timeset.getFcl();
                for (BusiClasses busiClass : busiClassesList) {
                    if (!isEnoughTime(busiClass.getClassStime(), fcl)) {
                        if(!StringUtils.equals(busiClass.getClassId(),busiClasses.getClassId())){ //解决未修改提货信息时，原班列不可选的问题
                            busiClass.setIsChoose("0");
                        }
                    } else {
                        busiClass.setIsChoose("1");
                    }
                }
            }
        }
        if(!"1".equals(busiShippingorder.getTjType())){
            if (!has) {
                if(StringUtils.isNotNull(busiClasses)){
                    ShippingOrder orderinfo = busiShippingorderMapper.selectBusiShippingorderById(busiShippingorder.getOrderId());
                    String orderUpload = StringUtils.isNotNull(orderinfo)?orderinfo.getOrderUploadcode():"";
                    String orderUnload = StringUtils.isNotNull(orderinfo)?orderinfo.getOrderUnloadcode():"";
                    String siteCodeO = ""; //托书原去程下货站 回程上货站
                    String siteCode = ""; //去程下货站 回程上货站
                    String isChoose = "1";
                    if("0".equals(busiShippingorder.getClassEastandwest())){
                        siteCode = busiShippingorder.getOrderUnloadcode();
                        siteCodeO = orderUnload;
                    }
                    if("1".equals(busiShippingorder.getClassEastandwest())){
                        siteCode = busiShippingorder.getOrderUploadcode();
                        siteCodeO = orderUpload;
                    }
                    String siteCodes = busiClasses.getReceiveSiteFull(); //整柜上下货站点
                    if(StringUtils.isNotEmpty(siteCode) && StringUtils.isNotEmpty(siteCodes)){
                        String[] siteCodesArr = siteCodes.split(",");
                        List<String> siteCodeList = Arrays.asList(siteCodesArr);
                        if(siteCodeList.contains(siteCode)){
                            isChoose = "1";
                        }else{
                            isChoose = "0";
                        }
                    }
                    if("0".equals(isChoose)){
                        if(StringUtils.equals(siteCode,siteCodeO)){
                            isChoose = "1";  //上下货站未修改，原班列可选择
                        }
                    }
                    busiClasses.setIsChoose(isChoose);
//                if(busiClasses.getClassSpacenumber()>0 && ("0".equals(busiClasses.getClassState()))){  //因为箱量增加原因查不出来
//                    busiClasses.setIsChoose("0");
//                }else{
//                    busiClasses.setIsChoose("1");  //因为班列设置原因查不出来
//                }
                    busiClassesList.add(busiClasses);
                }
            }
        }
        return busiClassesList;
    }

    @Override
    public List<BusiClasses> selectPXList(BusiShippingorders busiShippingorder) {
        List<BusiClasses> busiClassesList = busiShippingorderMapper.selectPXList(busiShippingorder);
        BusiClasses busiClasses = busiShippingorderMapper.selectClassByOrderId(busiShippingorder.getOrderId(), busiShippingorder.getLanguage());
        boolean has = false;
        for (BusiClasses busiClass : busiClassesList) { //0不可选 1可选
            busiClass.setIsChoose("1");
            if (busiClasses.getClassId().equals(busiClass.getClassId())) {
                has = true;
            }
        }
        if (busiShippingorder.getClassEastandwest().equals("1")) {
            String uploadStation = busiShippingorder.getUploadStation();
            String country = busiShippingorder.getCountry();
            JsBackTimeset timeset = jsBackTimesetService.selectByStationPickCountry(uploadStation, country);
            if (null != timeset) {
                Integer lcl = timeset.getFcl();
                for (BusiClasses busiClass : busiClassesList) {
                    if (!isEnoughTime(busiClass.getClassStime(), lcl)) {
                        if(!StringUtils.equals(busiClass.getClassId(),busiClasses.getClassId())){ //解决未修改提货信息时，原班列不可选的问题
                            busiClass.setIsChoose("0");
                        }
                    } else {
                        busiClass.setIsChoose("1");
                    }
                }
            }
        }
        if(!"1".equals(busiShippingorder.getTjType())){
            if (!has) {
                if(StringUtils.isNotNull(busiClasses)){
                    ShippingOrder orderinfo = busiShippingorderMapper.selectBusiShippingorderById(busiShippingorder.getOrderId());
                    String orderUpload = StringUtils.isNotNull(orderinfo)?orderinfo.getOrderUploadcode():"";
                    String orderUnload = StringUtils.isNotNull(orderinfo)?orderinfo.getOrderUnloadcode():"";
                    String siteCodeO = ""; //托书原去程下货站 回程上货站
                    String siteCode = ""; //去程下货站 回程上货站
                    String isChoose = "1";
                    if("0".equals(busiShippingorder.getClassEastandwest())){
                        siteCode = busiShippingorder.getOrderUnloadcode();
                        siteCodeO = orderUnload;
                    }
                    if("1".equals(busiShippingorder.getClassEastandwest())){
                        siteCode = busiShippingorder.getOrderUploadcode();
                        siteCodeO = orderUpload;
                    }
                    String siteCodes = busiClasses.getReceiveSiteLcl();
                    if(StringUtils.isNotEmpty(siteCode) && StringUtils.isNotEmpty(siteCodes)){
                        String[] siteCodesArr = siteCodes.split(",");
                        List<String> siteCodeList = Arrays.asList(siteCodesArr);
                        if(siteCodeList.contains(siteCode)){
                            isChoose = "1";
                        }else{
                            isChoose = "0";
                        }
                    }
                    if("0".equals(isChoose)){
                        if(StringUtils.equals(siteCode,siteCodeO)){
                            isChoose = "1";  //上下货站未修改，原班列可选择
                        }
                    }
                    busiClasses.setIsChoose(isChoose);
//                if("0".equals(busiClasses.getPxstate()) && ("0".equals(busiClasses.getClassState()))){ //0是未满1是已满,
//                    busiClasses.setIsChoose("0");  //因为货量增加原因查不出来
//                }else{
//                    busiClasses.setIsChoose("1");  //因为班列设置原因查不出来
//                }
                    busiClassesList.add(busiClasses);
                }
            }
        }
        return busiClassesList;
    }

    @Override
    public BusiShippingorders selectOrderByInquiryRecord(String inquiryRecordId) {
        return busiShippingorderMapper.selectOrderByInquiryRecord(inquiryRecordId);
    }

    @Override
    public BusiShippingorders selectOrderByInquiryRecordBackup(String inquiryRecordId) {
        return busiShippingorderMapper.selectOrderByInquiryRecordBackup(inquiryRecordId);
    }

    public boolean isEnoughTime(Date date, int days) {
        Long times = DateUtils.getDatePoorLong(date, new Date());
        return times > days * 24 * 3600 * 1000;
    }

    public String getFeeByConNum(String oldFee, Integer oldNum, Integer newNum) {
        return new BigDecimal(oldFee).divide(new BigDecimal(oldNum), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(newNum)).toString();
    }

    public int getNumeric(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String money = m.replaceAll("").trim();
        return StringUtils.isEmpty(money) ? 0 : Integer.parseInt(money);
    }

    public String getCurrency(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        int money = getNumeric(str);
        return str.replace(money + "", "").trim();
    }
}
