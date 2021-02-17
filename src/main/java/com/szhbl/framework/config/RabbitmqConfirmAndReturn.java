package com.szhbl.framework.config;

import com.szhbl.project.system.domain.SysFailMq;
import com.szhbl.project.system.service.ISysFailMqService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @Description : 生产者确认是否发送成功
 * @Author : wangzhichao
 * 1. 如果消息没有到exchange,则confirm回调,ack=false
 * 2. 如果消息到达exchange,则confirm回调,ack=true
 * 3. exchange到queue成功,则不回调return
 * 4. exchange到queue失败,则回调return
 * @Date: 2020-12-08 08:27
 */
@Component
public class RabbitmqConfirmAndReturn implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private ISysFailMqService sysFailMqService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        //指定 ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        //指定 ReturnCallback
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * ConfirmCallback 只确认消息是否正确到达 Exchange 中
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息发送成功" + correlationData);
        } else {
            System.out.println("消息发送失败:" + cause);
        }
    }

    /**
     * ReturnCallback   消息没有正确到达队列时触发回调，如果正确到达队列不执行
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
                                String exchange, String routingKey) {
        SysFailMq mq = new SysFailMq();
        mq.setBody(new String(message.getBody()));
        mq.setCode(replyCode);
        mq.setText(replyText);
        mq.setExchange(exchange);
        mq.setRoutingkey(routingKey);
        mq.setCreateTime(new Date());
        sysFailMqService.insertSysFailMq(mq);
    }
}
