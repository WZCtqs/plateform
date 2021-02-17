package com.szhbl.project.test.delayqueue;

import com.rabbitmq.client.Channel;
import com.szhbl.common.constant.Constants;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.framework.config.rabbit.delay.DelayContent;
import com.szhbl.framework.config.rabbit.delay.DelayMsgProvider;
import com.szhbl.framework.config.rabbit.delay.OrderDocDelayMsgProvider;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import com.szhbl.project.documentcenter.domain.DocDocumentsType;
import com.szhbl.project.documentcenter.domain.DocOrder;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.DocOrderMsg2;
import com.szhbl.project.documentcenter.service.*;
import com.szhbl.project.monitor.domain.SysMessage;
import com.szhbl.project.monitor.service.ISysMessageService;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.service.IBusiShippingorderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DelayMsgProvider messageProvider;

    @Autowired
    private OrderDocDelayMsgProvider orderDocDelayMsgProvider;

    @Autowired
    private IOrderDocumentService orderDocumentService;

    @Autowired
    private IDocDocumentsTypeService documentsTypeService;

    @Autowired
    private IDocNewRemindService docNewRemindService;

    @Autowired
    private ISysMessageService sysMessageService;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private IDocOrderSortService docOrderSortService;

    @Autowired
    private IDocOrderService docOrderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = DelayContent.ORDER_TTL_QUEUE)//监听的是延时队列
    public void handler(Channel channel, Message message) {
        try {
            MessagePojo messagePojo = FastJsonUtils.json2Bean(new String(message.getBody()), MessagePojo.class);
            Integer redisMessageId = (Integer) redisTemplate.opsForValue().get("consumed:" + messagePojo.getMessageId());
            if (redisMessageId == null) {
                //basicAck(deliveryTag,multiple)
                //deliveryTag：唯一标识ID
                //multiple：是否批量处理.true:将一次性ack所有小于deliveryTag的消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                redisTemplate.opsForValue().set("consumed:" + messagePojo.getMessageId(), 1, 10, TimeUnit.HOURS);
                // 判断消息是否存在剩余延迟时间
                if (messagePojo.getDelayNext() > 0) {
                    System.out.println("剩余：" + messagePojo.getDelayNext());
                    System.out.println(messagePojo.getMessage().toString());
                    sendDelayMsg(messagePojo.getMessage(), messagePojo.getDelayNext());
                    System.out.println("再次延迟发送");
                } else {
                    DocOrderMsg2 docOrderMsg2 = FastJsonUtils.json2Bean(messagePojo.getMessage().toString(), DocOrderMsg2.class);
                    System.out.println("消费的消息: " + DateUtils.timeStamp2Date(docOrderMsg2.getClassDate()));
                    System.out.println("消费消息时间： " + new Date());
                    // 1.查询订单下该单证是否已经存在
                    DocOrderDocument document = new DocOrderDocument();
                    document.setOrderId(docOrderMsg2.getOrderId());
                    document.setFileTypeKey(docOrderMsg2.getFileTypeKey());
                    int docCount = orderDocumentService.getCountByOrderAndTypeKey(document);
                    if (docCount == 0) {
                        // 2.判断单证是否为已审核状态(查询审核通过的订单)
                        BusiShippingorders shippingorders = busiShippingorderService.selectBusiShippingorderByIdOld(document.getOrderId());
                        String email = shippingorders.getClientEmail();
                        if ("1".equals(shippingorders.getIsexamline())) {
                            // 判断sort是否一致
                            int sort = docOrderSortService.getSortByOrderidDocType(docOrderMsg2.getOrderId(), docOrderMsg2.getFileTypeKey());
                            if (sort == docOrderMsg2.getSort()) {
                                sendMessageMode(docOrderMsg2, email);
                                // 判断是否需要发送延迟一天消息
                                // 根据单证的提醒时间和班列日期计算提醒时间
                                DocOrder docOrder = docOrderService.selectByOrderidTypeKey(docOrderMsg2.getOrderId(), docOrderMsg2.getFileTypeKey());
                                String reminddate = DateUtils.dateTime(docOrder.getNormalSupplyNode());
                                Long time = Calendar.getInstance().getTimeInMillis() + 86400000L;
                                String tomorrow = DateUtils.timeStamp2Date2(time + "");
                                // 如果当天+1 ==理论发送提醒时间，表明该消息是提前一天发送
                                if (reminddate.equals(tomorrow)) {
                                    sendDelayMsg(messagePojo.getMessage(), 86400L);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("确认消费异常", e);
        }
    }

    public void handler1(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (IOException e) {
            logger.error("确认消费异常", e);
        }
    }

    public void sendMessageMode(DocOrderMsg2 docOrderMsg2, String email) {
        // 3.1 不存在新提醒设置，进行提醒发送
        // 保存发送结果
        SysMessage sysMessage = new SysMessage();
        sysMessage.setOrderNumber(docOrderMsg2.getOrderNumber().trim());
        sysMessage.setOrderId(docOrderMsg2.getOrderId());
        sysMessage.setMessageTitle(docOrderMsg2.getFileTypeText() + " : " + docOrderMsg2.getOrderNumber());
        sysMessage.setMessageType("单证");
        sysMessage.setFileTypeKey(docOrderMsg2.getFileTypeKey());
        sysMessage.setMessageContent("舱位号为\""
                + docOrderMsg2.getOrderNumber()
                + "\"的"
                + docOrderMsg2.getFileTypeText()
                + "还未上传/填写，请尽快提供！");
        // 3.1.1 查询发送提醒方式
        // 查询发送方式
        if (0 == 0) {
            // 存在消息提醒设置
            if (2 == 2) {
                // 站内消息推送
                //查询单证来源系统
                DocDocumentsType documentsType = documentsTypeService.getTypeByTypeKey(docOrderMsg2.getFileTypeKey());
                if (2 == documentsType.getFileFrom()) {
                    // 来源：子系统
                    String sysFlag = documentsType.getFileFromText();
                    String queueRoutingKey = "";
                    switch (sysFlag) {
                        case "xg":
                            queueRoutingKey = ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_XG;
                            break;
                        case "js":
                            queueRoutingKey = ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_JS;
                            break;
                        case "xxyo":
                            queueRoutingKey = ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_XXYO;
                            break;
                        case "dka":
                            queueRoutingKey = ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_DKA;
                            break;
                        case "px":
                            queueRoutingKey = ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_PX;
                            break;
                        case "gw":
                            queueRoutingKey = ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_GW;
                            break;
                        case "gwcz":
                            queueRoutingKey = ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_GWCZ;
                            break;
                    }
                    CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                    MessageProperties messageProperties = new MessageProperties();
                    messageProperties.getHeaders().put("orderNumber", docOrderMsg2.getOrderNumber());
                    Message message = new Message(sysMessage.getMessageContent().getBytes(), messageProperties);
//                    rabbitTemplate.convertAndSend(ShipOrderRabbitmq.BLPT_DOCMESSAGE_TOPIC_EXCHANGE,
//                            queueRoutingKey, message, correlationData);
                } else if (1 == documentsType.getFileFrom()) {
                    // 来源：客户端，查询客户id
                    String clientId = orderDocumentService.getClientId(docOrderMsg2.getOrderId());
                    sysMessage.setClientId(clientId);
                    /*判断是否已发送*/
                    if (sysMessageService.countOrderFileKey(docOrderMsg2.getOrderNumber(), docOrderMsg2.getFileTypeKey()) == 0) {
                        sysMessageService.insertMessage(sysMessage);
//                        Map map = new HashMap();
//                        map.put("toEmail", email);
//                        map.put("subject", sysMessage.getMessageTitle());
//                        map.put("content", sysMessage.getMessageContent());
//                        map.put("messageId", sysMessage.getId());
//                        map.put("orderId", sysMessage.getOrderId());
//                        map.put("fileTypeKey", sysMessage.getFileTypeKey());
                    }
                    //todo 屏蔽发送客户邮件单证提醒
//                    rabbitTemplate.convertAndSend(EmailRabbitmqConfig.EMAIL_EXCHANGE, EmailRabbitmqConfig.EMAIL_ROUTINGKEY, map);
                }
            }
//            if (1 == 1) {
//                // email发送
//                Map map = new HashMap();
//                map.put("toEmail", email);
//                map.put("subject", sysMessage.getMessageTitle());
//                map.put("content", sysMessage.getMessageContent());
//                map.put("messageId", sysMessage.getId());
//                map.put("orderId", sysMessage.getOrderId());
//                map.put("fileTypeKey", sysMessage.getFileTypeKey());
//                rabbitTemplate.convertAndSend(EmailRabbitmqConfig.EMAIL_EXCHANGE, EmailRabbitmqConfig.EMAIL_ROUTINGKEY, map);
//            }
        }
    }

    /**
     * 发送延迟消息
     *
     * @param msg   消息对象
     * @param delay 延迟时间
     * @throws Exception
     */
    public void sendDelayMsg(Object msg, Long delay){
        // 统计每天发送量并作为延迟秒数附加
//        String delayKey = Constants.RABBITMQ + ":" + DateUtils.dateTime();
//        redisTemplate.opsForValue().increment(delayKey);
        // 自增消息id
        String delayMessageKey = Constants.RABBITMQ + ":" + "delayMessageId";
        redisTemplate.opsForValue().increment(delayMessageKey);

        MessagePojo pojo = new MessagePojo();
        pojo.setMessage(msg);
        pojo.setMessageId(redisTemplate.opsForValue().get(delayMessageKey).toString());

        // 判断秒数是否大于 864,000‬ （10天秒数：10d*24h*60m*60s）
        if (delay > Constants.TENDAY_SECOND) {
            // 大于10天的话。延迟10天，并将延迟数 减10天记入消息
            pojo.setDelayNow(Constants.TENDAY_SECOND);
            pojo.setDelayNext(delay - Constants.TENDAY_SECOND);
        } else {
            pojo.setDelayNow(delay);
            pojo.setDelayNext(0L);
        }
        try {
            messageProvider.sendMessage(pojo);
        } catch (Exception e) {
            logger.error("再次发送异常", e);
        }
    }

    public static void main(String[] args) throws ParseException {
        Long time = Calendar.getInstance().getTimeInMillis() - 86400000L;
        String date = DateUtils.timeStamp2Date2(time+"");
        System.out.println(date);
    }
}
