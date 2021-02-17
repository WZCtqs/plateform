package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.framework.config.rabbit.order.XGSystemRabbitmqConfig;
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
 * @date 2020-03-24
 */
@Slf4j
@Component
public class XGSystemListener {

    @Autowired
    private IOrderDocumentService orderDocumentService;

    /**
     * 监听箱管系统   提箱单消息文件
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = XGSystemRabbitmqConfig.XG_SYSTEM_PICKCON_QUEUE_BLPT)
    public void getPickConFile(Message message, Channel channel) {
        try {
            log.info("获取箱管系统 -提箱单");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();
            if ("insert".equals(type)) {
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("箱管系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setFileTypeKey(DocumentsType.PCIK_CONTAINER_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            } else if ("delete".equals(type)) {
                orderDocumentService.deleteByFileNameAndOrderIdAndKey(orderDoc.getFileName(), orderDoc.getOrderId(), DocumentsType.PCIK_CONTAINER_FILE);
            } else if("update".equals(type)){
                orderDocumentService.deleteByFileNameAndOrderIdAndKey(orderDoc.getFileName(), orderDoc.getOrderId(), DocumentsType.PCIK_CONTAINER_FILE);
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("箱管系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setFileTypeKey(DocumentsType.PCIK_CONTAINER_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听箱管系统   还箱单消息文件
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = XGSystemRabbitmqConfig.XG_SYSTEM_RETURNCON_QUEUE_BLPT)
    public void getReturnConFile(Message message, Channel channel) {
        try {
            log.info("获取箱管系统-还箱单");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();
            if ("insert".equals(type)) {
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("箱管系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setFileTypeKey(DocumentsType.RETURN_CON_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            } else if ("delete".equals(type)) {
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(), DocumentsType.RETURN_CON_FILE);
            } else if("update".equals(type)){
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(), DocumentsType.RETURN_CON_FILE);
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("箱管系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setFileTypeKey(DocumentsType.RETURN_CON_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听箱管系统 海关信
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = XGSystemRabbitmqConfig.XG_SYSTEM_CUSTOMSLETTER_QUEUE_BLPT)
    public void getCustomerLetterFile(Message message, Channel channel) {
        try {
            log.info("获取箱管系统放箱单-海关信");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();
            if ("insert".equals(type)) {
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("箱管系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setFileTypeKey(DocumentsType.CUSTOMS_LETTER_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            } else if ("delete".equals(type)) {
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(), DocumentsType.CUSTOMS_LETTER_FILE);
            } else if("update".equals(type)){
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(), DocumentsType.CUSTOMS_LETTER_FILE);
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("箱管系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setFileTypeKey(DocumentsType.CUSTOMS_LETTER_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
