package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.framework.config.rabbit.order.EmailRabbitmqConfig;
import com.szhbl.framework.email.IMailService;
import com.szhbl.project.documentcenter.domain.DocOrder;
import com.szhbl.project.documentcenter.service.IDocOrderService;
import com.szhbl.project.monitor.domain.SysMessage;
import com.szhbl.project.monitor.service.ISysMessageService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class EmailListener {

    @Autowired
    private ISysMessageService sysMessageService;

    @Autowired
    private IDocOrderService docOrderService;

    @Autowired
    private IMailService mailService;

    @RabbitListener(queues = EmailRabbitmqConfig.EMAIL_QUEUE)
    public void sendEmail(Channel channel, Message message) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //接收者，消息内容
        Map map = FastJsonUtils.json2Map(new String(message.getBody()));
        String toEmail = map.get("toEmail").toString();
        String subject = map.get("subject").toString();
        String content = map.get("content").toString();
        String orderId = map.get("orderId").toString();
        String fileTypeKey = map.get("fileTypeKey").toString();
        Long messageId = Long.parseLong(map.get("messageId").toString());
        SysMessage msg = new SysMessage();
        DocOrder docOrder = new DocOrder();
        try{
            String result = mailService.sendHtmlMail(toEmail, subject, content);
            msg.setId(messageId);
            if(result.equals("发送成功")){
                msg.setEmailStatus(0);
            } else {
                msg.setEmailfail(result);
            }
            docOrder.setFileTypeKey(fileTypeKey);
            docOrder.setOrderId(orderId);
        }finally{
            msg.setEmailStatus(1);
            docOrder.setEmailStatus(1);
            sysMessageService.updateMessage(msg);
            docOrderService.updateActualSupply(docOrder);
        }
    }
}
