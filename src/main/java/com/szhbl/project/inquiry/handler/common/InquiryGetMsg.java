package com.szhbl.project.inquiry.handler.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 主动获取队列消息
 */
@Slf4j
@Component
public class InquiryGetMsg {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean getJsMsg() throws IOException {
        ConnectionFactory factory = rabbitTemplate.getConnectionFactory();

        Connection connection = factory.createConnection();

        Channel channel = connection.createChannel(true);

        GetResponse response = channel.basicGet("business_quoter_queue_js2",false);

//        channel.basicAck(response .getEnvelope().getDeliveryTag() ,false);

        String message = new String(response.getBody(), "UTF-8");

        return Boolean.TRUE;
    }

}
