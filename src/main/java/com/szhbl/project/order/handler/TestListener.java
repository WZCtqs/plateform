package com.szhbl.project.order.handler;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @Description : 测试监听
 * @Author : wangzhichao
 * @Date: 2020-07-18 15:00
 */
@Slf4j
@Component
public class TestListener {

    //@RabbitListener(queues = OrderToCheckRabbitmqConfig.ORDER_TOCHECK_CHANGE_QUEUE_JS)
    public void jsListener(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("js: " + new String(message.getBody()));
        } catch (IOException e) {
            log.error("jsListener发送失败：{}",e.toString(),e.getStackTrace());
        }
    }

    //@RabbitListener(queues = OrderToCheckRabbitmqConfig.ORDER_TOCHECK_CHANGE_QUEUE_XXYO)
    public void xxyoListener(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("xxyo: " + new String(message.getBody()));
        } catch (IOException e) {
            log.error("xxyoListener发送失败：{}",e.toString(),e.getStackTrace());
        }
    }

//    @RabbitListener(queues = OrderToCheckRabbitmqConfig.ORDER_TOCHECK_CHANGE_QUEUE_XG)
    public void xgListener(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("xg: " + new String(message.getBody()));
        } catch (IOException e) {
            log.error("xgListener发送失败：{}",e.toString(),e.getStackTrace());
        }
    }

    //@RabbitListener(queues = OrderToCheckRabbitmqConfig.ORDER_TOCHECK_CHANGE_QUEUE_GWCZ)
    public void gwczListener(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("gwcz: " + new String(message.getBody()));
        } catch (IOException e) {
            log.error("gwczListener发送失败：{}",e.toString(),e.getStackTrace());
        }
    }

   // @RabbitListener(queues = OrderToCheckRabbitmqConfig.ORDER_TOCHECK_CHANGE_QUEUE_PX)
    public void pxListener(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("px: " + new String(message.getBody()));
        } catch (IOException e) {
            log.error("pxListener发送失败：{}",e.toString(),e.getStackTrace());
        }
    }
}
