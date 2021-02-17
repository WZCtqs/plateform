package com.szhbl.project.enquiry.listenner;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.inquiry.domain.*;
import com.szhbl.project.inquiry.handler.common.InquiryProvider;
import com.szhbl.project.inquiry.mapper.*;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import com.szhbl.project.order.domain.BusiOrderTocheckInquiry;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.handler.ToCheckNotice;
import com.szhbl.project.order.mapper.BusiOrderTocheckInquiryMapper;
import com.szhbl.project.order.mapper.BusiShippingorderApplyMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.mapper.ShippingorderExaminfoMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class XgCheckResultListenner {

    @Autowired
    BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    IBusiShippingorderService busiShippingorderService;
    @Autowired
    ToCheckNotice toCheckNotice;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BookingInquiryBackupMapper bookingInquiryBackupMapper;
    @Autowired
    private BookingInquiryMapper bookingInquiryMapper;
    @Autowired
    private BookingInquiryResultMapper bookingInquiryResultMapper;
    @Autowired
    private BusiOrderTocheckInquiryMapper busiOrderTocheckInquiryMapper;
    @Autowired
    private InquiryProvider inquiryProvider;
    @Autowired
    private BookingInquiryResultBackupMapper bookingInquiryResultBackupMapper;
    @Autowired
    private BookingInquiryGoodsDetailsBackupMapper goodsDetailsBackupMapper;
    @Autowired
    private BookingInquiryGoodsDetailsMapper bookingInquiryGoodsDetailsMapper;
    @Autowired
    private ShippingorderExaminfoMapper shippingorderExaminfoMapper;
    @Autowired
    private BusiShippingorderApplyMapper busiShippingorderApplyMapper;
    @Autowired
    private IBookingInquiryService bookingInquiryService;




//    @RabbitListener(queues = "xg_system_check_result_queue_blpt")
    public void orderInfoListener(Channel channel, Message message){
        try {
            log.debug("箱管系统特种箱审核结果:{body}",new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            Map<String, String> map = FastJsonUtils.json2Map(new String(message.getBody()));
            log.debug("特种箱审核结果map-----------------" + map);
            String examineFlag = map.get("examineFlag");//审核结果
            String failReason = map.get("failReason");//失败原因
            String inquiryId = map.get("inquiryId");//草稿表询价id
            String orderId = map.get("orderId");//订单id
            String type = map.get("type");//是否转待审核箱管审核 0转待审核，1新增订舱
            String hxdExamine = map.get("hxdExamine");//是否转待审核箱管审核 0转待审核，1新增订舱
            String status = "10";
            if ("0".equals(hxdExamine)) {
                Map IsExamline = busiShippingorderService.selectIsExamlineByOrderId(orderId);
                /*还箱地审核结果处理*/
                if ("7".equals(IsExamline.get("IsExamline"))) {
                    BusiShippingorders orderupd = new BusiShippingorders();
                    BookingInquiry inquiry = new BookingInquiry();
                    inquiry.setId(Long.valueOf(inquiryId));
                    orderupd.setOrderId(orderId);
                    if ("0".equals(examineFlag)) {
                        /*报价中*/
                        orderupd.setIsexamline("9");
                        /*询价状态*/
                        inquiry.setInquiryState("2");
                    } else {
                        /*询价状态*/
                        inquiry.setInquiryState("4");
                        /*草稿状态 */
                        orderupd.setIsexamline("5");
                        /*删除暂存*/
                        commonService.deleteTem(orderId);
                        /*更新公告*/
                        busiShippingorderService.updateOrderExam(orderId, failReason);
                    }
                    busiShippingorderMapper.updateBusiShippingorder(orderupd);
                    bookingInquiryMapper.updateBookingInquiry(inquiry);
                }
            } else {
                BusiShippingorders orderbackup = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId);
                if ("7".equals(orderbackup.getIsexamline())) {
                    if ("0".equals(examineFlag)) {//通过，进行下一步的询价，特种箱询价
                        //是转贷审核箱管审核
                        if ("0".equals(type)||"2".equals(type)) {
                            BusiOrderTocheckInquiry tocheckInquiry = busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(orderId);
                            //询价草稿数据
                            BookingInquiryBackup bookingInquiryBackup = bookingInquiryBackupMapper.selectBookingInquiryBackupById(Long.valueOf(inquiryId));
                            BookingInquiry bookingInquiry = new BookingInquiry();
                            BeanUtils.copyProperties(bookingInquiryBackup, bookingInquiry);
                            bookingInquiry.setIsToCheck(0);
                            bookingInquiry.setInquiryTime(new Date());
                            /*再次订舱询价存新的orderid*/
                            if("2".equals(type)){
                                bookingInquiry.setOrderId(orderId);
                            }
                            bookingInquiryMapper.insertBookingInquiry(bookingInquiry);
                            bookingInquiry.setJsNum(bookingInquiryResultMapper.getJsNumByOrderId(bookingInquiryBackup.getOrderId()));
                            ShippingOrder shippingOrder = busiShippingorderService.selectBusiShippingorderById(bookingInquiryBackup.getOrderId());
                            bookingInquiry.setClassDate(shippingOrder.getClassDate());
                            bookingInquiry.setOrderNumber(shippingOrder.getOrderNumber());
                            //询价结果草稿数据
                            BookingInquiryResultBackup checkBookingInquiryResultBackup = new BookingInquiryResultBackup();
                            checkBookingInquiryResultBackup.setInquiryId(Long.valueOf(inquiryId));
                            List<BookingInquiryResultBackup> bookingInquiryResultBackupList = bookingInquiryResultBackupMapper.selectBookingInquiryResultBackupList(checkBookingInquiryResultBackup);
                            BookingInquiryResultBackup bookingInquiryResultBackup = bookingInquiryResultBackupList.get(0);
                            BookingInquiryResult bookingInquiryResult = new BookingInquiryResult();
                            BeanUtils.copyProperties(bookingInquiryResultBackup, bookingInquiryResult);
                            bookingInquiryResult.setInquiryId(bookingInquiry.getId());
                            bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
                            //添加有效期
                            bookingInquiryService.updateBIWithDate(bookingInquiry);
                            //拼箱草稿数据
                            BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackup = new BookingInquiryGoodsDetailsBackup();
                            bookingInquiryGoodsDetailsBackup.setInquiryId(Long.valueOf(inquiryId));
                            List<BookingInquiryGoodsDetailsBackup> bookingInquiryGoodsDetailsBackupList = goodsDetailsBackupMapper.selectBookingInquiryGoodsDetailsBackupList(bookingInquiryGoodsDetailsBackup);
                            List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList = new ArrayList<>();
                            if (StringUtils.isNotEmpty(bookingInquiryGoodsDetailsBackupList)) {
                                BookingInquiryGoodsDetails bookingInquiryGoodsDetails = null;
                                for (BookingInquiryGoodsDetailsBackup bookingInquiryGoodsDetailsBackups : bookingInquiryGoodsDetailsBackupList) {
                                    bookingInquiryGoodsDetails = new BookingInquiryGoodsDetails();
                                    BeanUtils.copyProperties(bookingInquiryGoodsDetailsBackups, bookingInquiryGoodsDetails);
                                    bookingInquiryGoodsDetails.setInquiryId(bookingInquiry.getId());
                                    bookingInquiryGoodsDetailsList.add(bookingInquiryGoodsDetails);
                                    //插入拼箱数据
                                    bookingInquiryGoodsDetailsMapper.insertBookingInquiryGoodsDetails(bookingInquiryGoodsDetails);
                                }
                                //添加发送子系统询价的拼箱数据
                                bookingInquiry.setBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetailsList);
                            }
                            //去程
                            if ("0".equals(bookingInquiry.getEastOrWest())) {
                                switch (bookingInquiry.getBookingService()) {
                                    case "0":
                                        if (StringUtils.isEmpty(bookingInquiryResult.getDeliveryFees())) {
                                            inquiryProvider.jsPush(bookingInquiry);
                                        }
                                        if (StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())) {
                                            inquiryProvider.xxyoPush(bookingInquiry);
                                        }
                                        break;
                                    case "1":
                                        if (StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())) {
                                            inquiryProvider.xxyoPush(bookingInquiry);
                                        }
                                        break;
                                    case "3":
                                        if (StringUtils.isEmpty(bookingInquiryResult.getDeliveryFees())) {
                                            inquiryProvider.jsPush(bookingInquiry);
                                        }
                                        break;
                                }
                            }
                            //回程
                            else if ("1".equals(bookingInquiry.getEastOrWest())) {
                                switch (bookingInquiry.getBookingService()) {
                                    case "0":
                                        if (StringUtils.isEmpty(bookingInquiryResult.getDeliveryFees())) {
                                            inquiryProvider.xxyoPush(bookingInquiry);
                                        }
                                        if (StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())) {
                                            inquiryProvider.jsPush(bookingInquiry);
                                        }
                                        break;
                                    case "1":
                                        if (StringUtils.isEmpty(bookingInquiryResult.getPickUpFees())) {
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
                            //自动报价
                            if (tocheckInquiry.getJsExamine() == 1 && tocheckInquiry.getXxyoExamine() == 1 && tocheckInquiry.getYwExamine() == 1) {
                                /*if (tocheckInquiry.getJsXxyoExamine() == 0) {
                                    ShippingOrder orderInfo = busiShippingorderService.selectBusiShippingorderTemById(orderId);
                                    BusiShippingorders busiShippingorders = new BusiShippingorders();
                                    BeanUtils.copyProperties(orderInfo, busiShippingorders);
                                    busiShippingorderService.pickTimeCheckApply(orderId);
                                    if ("0".equals(bookingInquiry.getEastOrWest())) {
                                        status = "11";
                                    } else {
                                        status = "12";
                                    }
                                } else if (tocheckInquiry.getDcExamine() == 0 || tocheckInquiry.getDczExamine() == 0) {
                                    status = "4";
                                }*/

                                /*再次订舱  status=0*/
                                if("2".equals(type)){
                                    status="0";
                                }else{
                                    if(tocheckInquiry.getJsXxyoExamine()==0){
                                        ShippingOrder orderInfo = busiShippingorderService.selectBusiShippingorderTemById(orderId);
                                        BusiShippingorders busiShippingorders=new BusiShippingorders();
                                        BeanUtils.copyProperties(orderInfo,busiShippingorders);
                                        busiShippingorderService.pickTimeCheckApply(orderId);
                                        if("0".equals(bookingInquiry.getEastOrWest())){
                                            status="11";
                                        }else{
                                            status="12";
                                        }
                                    }else if(tocheckInquiry.getDcExamine()==0 || tocheckInquiry.getDczExamine()==0){
                                        status="4";
                                    }
                                }
                            }
                            //集疏或公路报价
                            else {
                                /*再次订舱  status=14*/
                                if("2".equals(type)){
                                    status="14";
                                }else{
                                    status="9";
                                }
                               // status = "9";
                            }
                            //更新托书表询价结果id
                            BusiShippingorders busiShippingorder = new BusiShippingorders();
                            busiShippingorder.setInquiryRecordId(bookingInquiryResult.getId().toString());
                            busiShippingorder.setOrderId(orderId);
                            busiShippingorder.setIsexamline(status);
                            busiShippingorderMapper.updateBusiShippingorder(busiShippingorder);
                            busiShippingorder.setIsexamline(null);
                            //busiShippingorderApplyMapper.updateBusiShippingorderApply(busiShippingorder);
                            /*再次订舱不需要*/
                            if("0".equals(type)){
                                busiShippingorderApplyMapper.updateBusiShippingorderApply(busiShippingorder);
                            }
                            toCheckNotice.noticeSonSystem(orderId, status, "箱管部审核通过");
                            //成功推送消息队列
                            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                            if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                String messagetype = "4"; //转待审核
                                commonService.orderInfoMQ(orderInfoRabbmq, messagetype);
                            }
                        }
                        //新订舱审核
                        else if ("1".equals(type)) {
                            //托书修改为待审核状态
                            BusiShippingorders orderupd = new BusiShippingorders();
                            orderupd.setOrderId(orderId);
                            orderupd.setIsexamline("0"); //待审核
                            busiShippingorderMapper.updateBusiShippingorder(orderupd);
                            //成功推送消息队列
                            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                            if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                String messagetype = "0"; //新订舱审核
                                commonService.orderInfoMQ(orderInfoRabbmq, messagetype);
                            }
                        }

                    } else if ("1".equals(examineFlag)) {//失败，询价表状态更新
                        if ("0".equals(type)||"2".equals(type)) {
                            toCheckNotice.noticeSonSystem(orderId, "8", "箱管部审核失败");
/*                    BookingInquiry bookingInquiry=new BookingInquiry();
                    bookingInquiry.setId(Long.valueOf(inquiryId));
                    bookingInquiry.setInquiryState("4");
                    bookingInquiry.setTurndownReason(failReason);
                    bookingInquiryService.updateBookingInquiry(bookingInquiry);*/
                            BusiShippingorders busiShippingorder = new BusiShippingorders();
                            busiShippingorder.setIsexamline("8");
                            busiShippingorder.setOrderId(orderId);
                            busiShippingorderMapper.updateBusiShippingorder(busiShippingorder);
                            //箱管驳回公告更新
                            busiShippingorderService.updateOrderExamXg(orderId, failReason);
                            //删除转待审核暂存表
                            if("0".equals(type)){
                            commonService.deleteTem(orderId);
                            }
                            //推送驳回消息队列
                            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                            if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                String messagetype = "5"; //转待审核失败
                                commonService.orderInfoMQ(orderInfoRabbmq, messagetype);
                            }
                        }
                        //新订舱审核
                        else if ("1".equals(type)) {
                            //托书修改为箱管审核失败
                            BusiShippingorders orderupd = new BusiShippingorders();
                            orderupd.setOrderId(orderId);
                            orderupd.setIsexamline("5"); //箱管审核失败，托书是草稿状态
                            busiShippingorderMapper.updateBusiShippingorder(orderupd);
                            //失败推送消息队列
                            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                            if (StringUtils.isNotNull(orderInfoRabbmq)) {
                                String messagetype = "0"; //审核
                                commonService.orderInfoMQ(orderInfoRabbmq, messagetype);
                            }
                            //插入公告表
                            ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                            orderExaminfo.setOrderId(orderId);
                            orderExaminfo.setExamDate(DateUtils.getNowDate());
                            orderExaminfo.setExamInfo(failReason);
                            orderExaminfo.setXgRefuseInfo(failReason);
                            orderExaminfo.setExamType("2");  //插入为2，失败为1，成功为0
                            orderExaminfo.setIsread("0"); //未读
                            int resultexam = shippingorderExaminfoMapper.insertShippingorderExaminfo(orderExaminfo);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("箱管审核结果接收失败：{}", e.toString(), e.getStackTrace());
        }
    }
}