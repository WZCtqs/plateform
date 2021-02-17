package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.order.XXYOSystemRabbitmqConfig;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.domain.vo.OrderDocUrlXxyo;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 *
 * @author HP
 */
@Slf4j
@Component
public class XxyoSystemListener {

    @Autowired
    private IOrderDocumentService orderDocumentService;

    /**
     * 监听箱行亚欧系统--签收单
     * @param channel
     * @param message
     */
    @RabbitListener(queues = XXYOSystemRabbitmqConfig.XXYO_SYSTEM_RECEIPTGOODS_QUEUE_BLPT)
    public void getFollowVehicle(Channel channel, Message message) {
        try {
            log.info("箱行亚欧系统 - 签收单");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrlXxyo orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrlXxyo.class);
            String type = orderDoc.getType();
            if("insert".equals(type)) {
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("箱行亚欧系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                if (StringUtils.isNotEmpty(orderDoc.getReceiptGoods())) {
                    document.setFileTypeKey(DocumentsType.RECEIPT_GOODS_FILE);
                    document.setFileUrl(orderDoc.getReceiptGoods());
                    document.setFileName(orderDoc.getReceiptGoods().substring(orderDoc.getReceiptGoods().lastIndexOf('/') + 1));
                    orderDocumentService.insertOrderDocument(document);
                }
                if (StringUtils.isNotEmpty(orderDoc.getArrivalStation())) {
                    String[] list = orderDoc.getArrivalStation().split(";");
                    for (String url : list) {
                        document.setFileTypeKey(DocumentsType.ARRIVAL_STATION_PHOTO_FILE);
                        document.setFileUrl(url);
                        document.setFileName(url.substring(url.lastIndexOf('/') + 1));
                        orderDocumentService.insertOrderDocument(document);
                    }
                }
                if (StringUtils.isNotEmpty(orderDoc.getBoxingPhoto())) {
                    String[] list = orderDoc.getBoxingPhoto().split(";");
                    for (String url : list) {
                        document.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
                        document.setFileUrl(url);
                        document.setFileName(url.substring(url.lastIndexOf('/') + 1));
                        orderDocumentService.insertOrderDocument(document);
                    }
                }
            } else if ("delete".equals(type)){
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(),DocumentsType.RECEIPT_GOODS_FILE);
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(),DocumentsType.ARRIVAL_STATION_PHOTO_FILE);
                orderDocumentService.deleteByConAndOrderIdAndKey(orderDoc.getContainerNo(), orderDoc.getOrderId(),DocumentsType.BOXING_PHOTO_FILE);
            }

        } catch (IOException e) {
            log.info("异常：" + e.getMessage());
            log.error(e.getMessage(), e);
        }
    }
}
