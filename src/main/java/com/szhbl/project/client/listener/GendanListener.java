package com.szhbl.project.client.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.framework.config.rabbit.client.CustomerRabbitConfig;
import com.szhbl.project.client.domain.BusiGendan;
import com.szhbl.project.client.service.IBusiGendanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class GendanListener {


    @Autowired
    private IBusiGendanService busiGendanService;

    @RabbitListener(queues = CustomerRabbitConfig.GW_CLIENT_GENDAN_QUEUE_BLPT)
    public void getGendan(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("关务跟单员信息：" + new String(message.getBody()));
            BusiGendan gendan = FastJsonUtils.json2Bean(new String(message.getBody()), BusiGendan.class);
            if (gendan.getType().equals("insert")) {
                busiGendanService.insertBusiGendan(gendan);
            } else if (gendan.getType().equals("update")) {
                busiGendanService.updateBusiGendanByUserId(gendan);
            } else if (gendan.getType().equals("delete")) {
                busiGendanService.deleteBusiGendanByUserId(gendan.getUserid());
            }
        } catch (IOException e) {
            log.error("GendanListener失败：{}",e.toString(),e.getStackTrace());
        }
    }
}