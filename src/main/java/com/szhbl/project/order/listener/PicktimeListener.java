package com.szhbl.project.order.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.order.OrderPicktimeRabbitmqConfig;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import com.szhbl.framework.email.IMailService;
import com.szhbl.project.inquiry.domain.BookingInquiryOrder;
import com.szhbl.project.inquiry.mapper.BookingInquiryOrderMapper;
import com.szhbl.project.order.domain.BusiOrderTocheckInquiry;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.handler.ToCheckNotice;
import com.szhbl.project.order.mapper.BusiOrderTocheckInquiryMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class PicktimeListener {
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private IBusiShippingorderApplyService busiShippingorderApplyService;
    @Autowired
    private IBusiShippingorderBackupService busiShippingorderBackupService;
    @Autowired
    private IBusiShippingorderGoodsService busiShippingorderGoodsService;
    @Autowired
    private IBusiShippingorderGoodsApplyService busiShippingorderGoodsApplyService;
    @Autowired
    private IBusiShippingorderGoodsBackupService busiShippingorderGoodsBackupService;
    @Autowired
    private IShippingorderExaminfoService shippingorderExaminfoService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private BookingInquiryOrderMapper bookingInquiryOrderMapper;
    @Autowired
    private BusiOrderTocheckInquiryMapper busiOrderTocheckInquiryMapper;
    @Autowired
    ToCheckNotice toCheckNotice;

    /**
     * 监听提货时间
     * @param channel
     * @param message
     */
    @RabbitListener(queues= OrderPicktimeRabbitmqConfig.PLATFROM_PICKTIME_QUEUE_BLPT)
    public void orderInfoListener(Channel channel, Message message){
        try{
            System.out.println("监听提货时间审核结果");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println(new String(message.getBody()));
            Map<String,String> checkinfo = FastJsonUtils.json2Bean(new String(message.getBody(),"utf-8"), HashMap.class);
            if(StringUtils.isNotNull(checkinfo)){
                String orderId = checkinfo.get("orderId");
                if(StringUtils.isNotEmpty(orderId)){
                    //判断状态
                    BusiOrderTocheckInquiry checkResult = busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(orderId);
                    if(StringUtils.isNotNull(checkResult)){
                        int isDcCheck = checkResult.getDcExamine();//订舱审核 0是 1否
                        int isDczCheck = checkResult.getDczExamine(); //提货时间、班列订舱审核 0是 1否
                        String checkState = checkinfo.get("checkState"); //审核状态 0同意 1驳回
                        String checkRemark = checkinfo.get("checkRemark"); //审核原因
                        int result = 0;
                        String content="";
                        BusiShippingorders orderinfoback = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId); //订单表原数据
                        BusiShippingorders orderTem = busiShippingorderApplyService.selectBusiShippingorderApplyById(orderId);  //订单表暂存数据
                        if(StringUtils.isNotNull(orderinfoback) && StringUtils.isNotNull(orderTem)){
                            String eastandwest = orderinfoback.getClassEastandwest();
                            //审核通过
                            if("0".equals(checkState)){
                                //不涉及订舱审核，直接更新托书信息
                                if(isDcCheck==1 && isDczCheck==1){
                                    result = busiShippingorderService.updateOrderTem(orderId, "", "","");
                                    if(result == 1){
                                        //子系统推送
                                        toCheckNotice.noticeSonSystem(orderId, "1", "订舱组同意转待审核申请");
                                    }
                                }
                                //涉及重新询价，把托书状态更新为转待审核中，等待订舱组审核
                                if(isDcCheck==0 || isDczCheck==0){
                                    BusiShippingorders orderUpd = new BusiShippingorders();
                                    orderUpd.setOrderId(orderId);
                                    orderUpd.setIsexamline("4");
                                    result = busiShippingorderMapper.updateBusiShippingorder(orderUpd);
                                }
                            }
                            //审核不通过，托书修改为草稿状态，询价改为原询价信息
                            if("1".equals(checkState)){
                                BusiShippingorders orderUpd = new BusiShippingorders();
                                orderUpd.setOrderId(orderId);
                                orderUpd.setIsexamline("5");
                                orderUpd.setCreatedate(DateUtils.getNowDate());
                                BookingInquiryOrder inquiryRecordResult = bookingInquiryOrderMapper.selectPreInquiryOrder(orderId);
                                if(StringUtils.isNotNull(inquiryRecordResult)){
                                    orderUpd.setInquiryRecordId(String.valueOf(inquiryRecordResult.getInquiryResultId()));
                                }
                                result = busiShippingorderMapper.updateBusiShippingorder(orderUpd);
                                if(result==1){
                                    asyncService.createOrderNotice(orderId);//生成配舱通知书
                                    commonService.deleteTem(orderId);//删除暂存表信息，可以再次提交
                                    //更新订舱公告表
                                    ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                                    orderExaminfo.setOrderId(orderId);
                                    if("0".equals(checkState)){ //审核通过
                                        orderExaminfo.setExamType("0");
                                    }
                                    if("1".equals(checkState)){ //审核不通过
                                        if(StringUtils.isNotEmpty(checkRemark)){
                                            if("0".equals(eastandwest)){
                                                orderExaminfo.setRefuseInfo("公路部驳回原因："+checkRemark+"；");
                                            }
                                            if("1".equals(eastandwest)){
                                                orderExaminfo.setRefuseInfo("集疏部驳回原因："+checkRemark+"；");
                                            }
                                        }
                                        orderExaminfo.setExamType("1");
                                        if(StringUtils.isNotEmpty(checkRemark)){
                                            orderExaminfo.setIsread("0");
                                        }
                                    }
                                    int resultexam = shippingorderExaminfoService.updateShippingorderExaminfo(orderExaminfo);
                                    //推送消息队列
                                    ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                                    if(StringUtils.isNotNull(orderInfoRabbmq)){
                                        String messageType = "";
                                        if("0".equals(checkState)){
                                            messageType = "4"; //转待审核成功
                                        }
                                        if("1".equals(checkState)){
                                            messageType = "5"; //转待审核失败
                                        }
                                        commonService.orderInfoMQ(orderInfoRabbmq,messageType); //推送消息队列
                                    }
//                                    //给集疏、公路发送邮件提醒
//                                    String binningwayOld = orderinfoback.getShipOrderBinningway(); //委托ZIH提货 0是 1否
//                                    String binningway = orderTem.getShipOrderBinningway(); //委托ZIH提货 0是 1否
//                                    if("1".equals(eastandwest)&&(binningwayOld.equals("0")||binningway.equals("0"))){
//                                        if(!binningway.equals(binningwayOld)){
//                                            String[] sendEmails  = {"lihj@zih718.com","sunyk@zih718.com"};
//                                            commonService.sendEmailJs(sendEmails,orderInfoRabbmq,binningwayOld);
//                                        }
//                                    }
                                    //给客户、跟单、业务发送邮件提醒
                                    content = "托书转待审核失败";
                                    asyncService.orderSendEmail(orderId,content);
                                }
                            }
                        }
                    }
                }
            }
        }catch (IOException e) {
            log.error("监听提货时间审核结果失败：{}",e.toString(),e.getStackTrace());
        }
    }

}
