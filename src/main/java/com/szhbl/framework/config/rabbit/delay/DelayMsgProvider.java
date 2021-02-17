package com.szhbl.framework.config.rabbit.delay;

import com.szhbl.project.test.delayqueue.MessagePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DelayMsgProvider {
    private static final Logger logger = LoggerFactory.getLogger(DelayMsgProvider.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 模板消息实现类
     * @param rabbitTemplate
     */
    public DelayMsgProvider(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMandatory(true);
    }

    public void sendMessage(MessagePojo messagePojo) throws Exception {
        if(messagePojo != null){
                //执行发送消息到指定队列
                CorrelationData correlationData = new CorrelationData(messagePojo.getMessageId());
                rabbitTemplate.convertAndSend(DelayContent.ORDER_TTL_EXCHANGE, DelayContent.ORDER_TTL_ROUTINGKEY,messagePojo, message -> {
                    //设置延时超时时间
                    message.getMessageProperties().setExpiration(String.valueOf(messagePojo.getDelayNow() * 1000));
                    return message;
                },correlationData);
                System.out.println("消息发送时间: "+new Date());
            }else{
                logger.warn("消息内容为空");
        }
    }
}
