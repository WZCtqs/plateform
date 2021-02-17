package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.framework.config.rabbit.order.JSSystemRabbitmqConfig;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.domain.vo.OrderDocUrl;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author HP
 */
@Slf4j
@Component
public class JSSystemListener {

    @Autowired
    private IOrderDocumentService orderDocumentService;

    /**
     * 监听集输系统--签收单
     * @param channel
     * @param message
     */
    @RabbitListener(queues = JSSystemRabbitmqConfig.JS_SYSTEM_RECEIPTGOODS_QUEUE_BLPT)
    public void getFollowVehicle(Channel channel, Message message) {
        try {
            log.info("监听集输系统--签收单");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();

            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("集输系统");
            document.setOrderId(orderDoc.getOrderId());
            document.setOrderNumber(orderDoc.getOrderNumber());
            document.setContainerNo(orderDoc.getContainerNo());
            document.setFileTypeKey(DocumentsType.RECEIPT_GOODS_FILE);
            document.setFileUrl(orderDoc.getFileUrl());
            document.setFileName(orderDoc.getFileName());
            document.setUploadTime(new Date());
            document.setCreateTime(new Date());
            orderDocumentService.insertOrderDocument(document);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
