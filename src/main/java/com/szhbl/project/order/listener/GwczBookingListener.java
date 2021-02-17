
package com.szhbl.project.order.listener;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.szhbl.project.order.domain.vo.GwczBookingVo;
import com.szhbl.project.order.domain.vo.SiteInfoVo;
import com.szhbl.project.order.service.IBusiShipOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  拼箱大柜订舱
 */
@Slf4j
@Component
public class GwczBookingListener {

    @Autowired
    private IBusiShipOrderService busiShipOrderService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "px_dingcang_queue_gwcz", durable = "true"),
            exchange = @Exchange(value = "gwcz.order.topic.exchange", durable = "true", type = "topic", ignoreDeclarationExceptions = "true"),
            key = "gwcz.system.order.*"
    ))
    public void onMessage(Message message, Channel channel) {
        try {
            String s = new String(message.getBody(), "UTF-8");
            log.debug("国外场站拼箱订舱------>MQ接收到的数据："+s);
            GwczBookingVo gwczBookingVo = JSONObject.parseObject(message.getBody(),GwczBookingVo.class);
            String data1 = gwczBookingVo.getData1();
            List<SiteInfoVo> typeNumData = JSONObject.parseArray(data1, SiteInfoVo.class);
            gwczBookingVo.setTypeNumData(typeNumData);
            log.debug("国外场站拼箱订舱------>GwczBookingVo实体：\n{}",gwczBookingVo);

            // 订舱成功，确认消息
            if(busiShipOrderService.gwczAdd(gwczBookingVo)){
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            log.debug("国外场站拼箱订舱------>MQ接收到的数据：啊！我失败了" );
            log.error("国外场站拼箱订舱,{},\n{}",e.getMessage(), e.getStackTrace());
        }
    }

}
