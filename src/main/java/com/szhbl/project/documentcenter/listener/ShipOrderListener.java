package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.pdf.PdfConstants;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Map;

/**
 * @author HP
 */
@Slf4j
@Component
public class ShipOrderListener {

    @Autowired
    private IOrderDocumentService orderDocumentService;

    /**
     * 监听拼箱场站系统装箱方案消息文件
     * @param channel
     * @param message
     */
//    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_FILES_QUEUE_BLPT)
    public void getBoxingScheme(Channel channel, Message message) {
        try {
            log.info("获取拼箱场站系统装箱方案");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            Map map = message.getMessageProperties().getHeaders();
            String fileName = map.get("fileName").toString();
            String orderId = map.get("orderId").toString();
            String orderNumber = map.get("orderNumber").toString();
            byte[] body = message.getBody();
            // 装箱方案地址
            String path = PdfConstants.getBoxingSchemePath();
            // 单证类型 key
            String fileTypeKey = DocumentsType.BOXING_SCHEME_FILE;
            File paths = new File(path);
            if(!paths.exists()){
                paths.mkdirs();
            }
            path = path + fileName;
            File file = new File(path);
            if(file.exists()){
                file.delete();
            }
            Files.copy(new ByteArrayInputStream(body), file.toPath());
            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("拼箱场站系统");
            document.setOrderId(orderId);
            document.setOrderNumber(orderNumber);
            document.setFileTypeKey(fileTypeKey);
            document.setFileUrl(path);
            document.setUploadTime(new Date());
            document.setCreateTime(new Date());
            orderDocumentService.insertOrderDocument(document);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    /**
     * 监听大口岸系统消息文件
     * @param channel
     * @param message
     */
//    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_FILES_QUEUE_BLPT)
//    public void getDkaFiles(Channel channel, Message message) {
//        try {
//            log.info("获取大口岸系统文件");
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//            Map map = message.getMessageProperties().getHeaders();
//            String fileName = map.get("fileName").toString();
//            String orderId = map.get("orderId").toString();
//            String orderNumber = map.get("orderNumber").toString();
//            String fileTypeKey = map.get("fileTypeKey").toString();
//            byte[] body = message.getBody();
//            // 大口岸文件地址
//            String path = PdfConstants.DKA_FILE_PATH;
//            File paths = new File(path);
//            if(!paths.exists()){
//                paths.mkdirs();
//            }
//            path = path + fileName;
//            File file = new File(path);
//            if(file.exists()){
//                file.delete();
//            }
//            Files.copy(new ByteArrayInputStream(body), file.toPath());
//            /*单证表添加数据*/
//            DocOrderDocument document = new DocOrderDocument();
//            document.setFormSystem("大口岸系统");
//            document.setOrderId(orderId);
//            document.setOrderNumber(orderNumber);
//            document.setFileTypeKey(fileTypeKey);
//            document.setFileUrl(path);
//            document.setUploadTime(new Date());
//            orderDocumentService.insertOrderDocument(document);
//        } catch (IOException e) {
//            log.error(e.getMessage(),e);
//        }
//    }


//    @RabbitListener(queues = ShipOrderRabbitmq.ORDER_NOTICE_PDF_QUEUE_JS)
    public void sss(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            log.info("集输系统--获取订舱系统入货通知书");
            byte[] body = message.getBody();
            String fileName = message.getMessageProperties().getHeaders().get("orderNumber").toString();
            String path = "G:/test/file/new/js/" + fileName + ".pdf";
            File f = new File(path);
            Files.copy(new ByteArrayInputStream(body), f.toPath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
//    @RabbitListener(queues = ShipOrderRabbitmq.ORDER_NOTICE_PDF_QUEUE_XXYO)
    public void ssss(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            log.info("箱型亚欧系统--获取订舱系统入货通知书");
            byte[] body = message.getBody();
            String fileName = message.getMessageProperties().getHeaders().get("orderNumber").toString();
            String path = "G:/test/file/new/xxyo/" + fileName + ".pdf";
            File f = new File(path);
            Files.copy(new ByteArrayInputStream(body), f.toPath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
