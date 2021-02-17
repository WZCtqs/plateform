package com.szhbl.project.client.listener;

import com.alibaba.fastjson.JSON;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.client.domain.BusiClients;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
@Slf4j
@Component
public class ClientListener1 {
    //@RabbitListener(queues = CustomerRabbitConfig.DC_SYSTEM_CLIENT_QUEUE_BLPT)
    public void orderInfoListener(Channel channel, Message message){
        try {
            System.out.println("测试客户消息队列");
            String body = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            body = StringUtils.substring(body,1, body.length()-1);
            System.out.println(body);
            BusiClients busiClients = FastJsonUtils.json2Bean(body, BusiClients.class);
            System.out.println("---==="+busiClients);
            Map map = message.getMessageProperties().getHeaders();
            System.out.println(JSON.toJSONString(map));
        }catch (IOException e) {
            log.error("测试客户消息队列失败：{}",e.toString(),e.getStackTrace());
        }
    }
}
