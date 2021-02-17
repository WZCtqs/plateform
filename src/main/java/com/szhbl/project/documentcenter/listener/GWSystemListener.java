package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.framework.config.rabbit.order.GWSystemRabbitmqConfig;
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
import java.util.List;

/**
 * @author HP
 * @date 2020-03-18
 */
@Slf4j
@Component
public class GWSystemListener {

    @Autowired
    private IOrderDocumentService orderDocumentService;

    /**
     * 监听关务系统   报关单
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWSystemRabbitmqConfig.GW_SYSTEM_DECLARE_CUSTOMS_QUEUE_BLPT)
    public void getBoxingScheme(Channel channel, Message message) {
        try {
            log.info("获取关务系统报关单");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            List<OrderDocUrl> orderDocList = FastJsonUtils.json2List(new String(message.getBody()), OrderDocUrl.class);
            if (orderDocList.size() > 0) {
                orderDocumentService.deleteByOrderIdAndFileTypeKey(orderDocList.get(0).getOrderId(), DocumentsType.DECLARE_CUSTOMS_FILE);
                for (OrderDocUrl orderDoc : orderDocList) {
                    /*单证表添加数据*/
                    DocOrderDocument document = new DocOrderDocument();
                    document.setFormSystem("关务系统");
                    document.setOrderId(orderDoc.getOrderId());
                    document.setOrderNumber(orderDoc.getOrderNumber());
                    document.setContainerNo(orderDoc.getContainerNo());
                    document.setFileTypeKey(DocumentsType.DECLARE_CUSTOMS_FILE);
                    document.setFileUrl(orderDoc.getFileUrl());
                    document.setFileName(orderDoc.getFileName());
                    document.setUploadTime(new Date());
                    document.setCreateTime(new Date());
                    orderDocumentService.insertOrderDocument(document);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听关务系统--放行单
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWSystemRabbitmqConfig.GW_SYSTEM_CLEARANCEPAPER_QUEUE_XXYO)
    public void getFollowVehicle(Channel channel, Message message) {
        try {
            log.info("获取关务系统放行单");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            List<OrderDocUrl> orderDocList = FastJsonUtils.json2List(new String(message.getBody()), OrderDocUrl.class);
            if (orderDocList.size() > 0) {
                orderDocumentService.deleteByOrderIdAndFileTypeKey(orderDocList.get(0).getOrderId(), DocumentsType.CLEARANCE_PAPER_FILE);
                for (OrderDocUrl orderDoc : orderDocList) {
                    /*单证表添加数据*/
                    DocOrderDocument document = new DocOrderDocument();
                    document.setFormSystem("关务系统");
                    document.setOrderId(orderDoc.getOrderId());
                    document.setOrderNumber(orderDoc.getOrderNumber());
                    document.setContainerNo(orderDoc.getContainerNo());
                    document.setFileTypeKey(DocumentsType.CLEARANCE_PAPER_FILE);
                    document.setFileUrl(orderDoc.getFileUrl());
                    document.setFileName(orderDoc.getFileName());
                    document.setUploadTime(new Date());
                    document.setCreateTime(new Date());
                    orderDocumentService.insertOrderDocument(document);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听关务系统   口岸转关信息表
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWSystemRabbitmqConfig.GW_SYSTEM_PORTTRANS_QUEUE_BLPT)
    public void getPortTrans(Channel channel, Message message) {
        try {
            log.info("获取关务系统口岸转关信息表");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();
            if ("insert".equals(type)) {
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("关务系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setFileTypeKey(DocumentsType.PORT_TRANS_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            } else if ("delete".equals(type)) {
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(), DocumentsType.PORT_TRANS_FILE);
            } else if("update".equals(type)){
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(), DocumentsType.PORT_TRANS_FILE);
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("关务系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setFileTypeKey(DocumentsType.PORT_TRANS_FILE);
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
