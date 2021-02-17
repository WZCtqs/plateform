package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.pdf.PdfConstants;
import com.szhbl.framework.config.rabbit.order.DKASystemRabbitmqConfig;
import com.szhbl.project.documentcenter.domain.DocOrderArrivalExamine;
import com.szhbl.project.documentcenter.domain.DocOrderConexamine;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.DocOrderFollowVehicleExamine;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.domain.vo.OrderDocUrl;
import com.szhbl.project.documentcenter.domain.vo.OrderDocUrlDka;
import com.szhbl.project.documentcenter.service.IDocOrderArrivalExamineService;
import com.szhbl.project.documentcenter.service.IDocOrderConexamineService;
import com.szhbl.project.documentcenter.service.IDocOrderFollowVehicleExamineService;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import com.szhbl.project.monitor.domain.SysMessage;
import com.szhbl.project.monitor.service.ISysMessageService;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.service.IBusiZyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
 * @date 2020-03-18
 */
@Slf4j
@Component
public class DKASystemListener {

    @Autowired
    private IOrderDocumentService orderDocumentService;

    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    @Autowired
    private IDocOrderArrivalExamineService arrivalExamineService;

    @Autowired
    private IDocOrderFollowVehicleExamineService followVehicleExamineService;

    @Autowired
    private IDocOrderConexamineService conexamineService;

    @Autowired
    private ISysMessageService sysMessageService;

    /**
     * 监听大口岸系统 SMGS
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_SMGS_QUEUE_BLPT)
    public void getBoxingScheme(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统SMGS");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrlDka orderDoc =
                    FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrlDka.class);
            // 查询舱位号对应的orderId
            String[] numbers = orderDoc.getOrderNumbers().split(",");
            for (int i = 0; i < numbers.length; i++) {
                if (StringUtils.isNotEmpty(numbers[i])) {
                    OrderDocUrl order = orderDocumentService.getOrderIdByNumbers(numbers[i]);
                    if (order != null) {
                        String type = orderDoc.getType();
                        if ("insert".equals(type)) {
                            /*单证表添加数据*/
                            DocOrderDocument document = new DocOrderDocument();
                            document.setFormSystem("大口岸系统");
                            document.setOrderId(order.getOrderId());
                            document.setOrderNumber(numbers[i]);
                            document.setContainerNo(orderDoc.getContainerNo());
                            document.setFileTypeKey(DocumentsType.SMGS_WAYBILL_FILE);
                            document.setFileUrl(orderDoc.getFileUrl());
                            document.setFileName(orderDoc.getFileName());
                            document.setUploadTime(new Date());
                            document.setCreateTime(new Date());
                            orderDocumentService.insertOrderDocument(document);
                        } else if ("delete".equals(type)) {
                            orderDocumentService.deleteByConAndOrderIdAndKey(
                                    orderDoc.getContainerNo(), order.getOrderId(), DocumentsType.SMGS_WAYBILL_FILE);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听大口岸系统--CIM
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_CIM_QUEUE_BLPT)
    public void getFollowVehicle(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统CIM");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrlDka orderDoc =
                    FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrlDka.class);
            // 查询舱位号对应的orderId
            String[] numbers = orderDoc.getOrderNumbers().split(",");
            for (int i = 0; i < numbers.length; i++) {
                if (StringUtils.isNotEmpty(numbers[i])) {
                    OrderDocUrl order = orderDocumentService.getOrderIdByNumbers(numbers[i]);
                    if (order != null) {
                        String type = orderDoc.getType();
                        if ("insert".equals(type)) {
                            /*单证表添加数据*/
                            DocOrderDocument document = new DocOrderDocument();
                            document.setFormSystem("大口岸系统");
                            document.setOrderId(order.getOrderId());
                            document.setOrderNumber(numbers[i]);
                            document.setContainerNo(orderDoc.getContainerNo());
                            document.setFileTypeKey(DocumentsType.CIM_WAYBILL_FILE);
                            document.setFileUrl(orderDoc.getFileUrl());
                            document.setFileName(orderDoc.getFileName());
                            document.setUploadTime(new Date());
                            document.setCreateTime(new Date());
                            orderDocumentService.insertOrderDocument(document);
                        } else if ("delete".equals(type)) {
                            orderDocumentService.deleteByConAndOrderIdAndKey(
                                    orderDoc.getContainerNo(), order.getOrderId(), DocumentsType.CIM_WAYBILL_FILE);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听大口岸系统--正式随车文件
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_QUEUE_BLPT)
    public void getFollowVehicleFormal(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统正式随车文件");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrlDka orderDoc =
                    FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrlDka.class);
            // 查询舱位号对应的orderId
            String[] numbers = orderDoc.getOrderNumbers().split(",");
            for (int i = 0; i < numbers.length; i++) {
                if (StringUtils.isNotEmpty(numbers[i])) {
                    OrderDocUrl order = orderDocumentService.getOrderIdByNumbers(numbers[i]);

                    if (order != null) {
                        String type = orderDoc.getType();
                        if ("insert".equals(type)) {
                            /*单证表添加数据*/
                            DocOrderDocument document = new DocOrderDocument();
                            document.setFormSystem("大口岸系统");
                            document.setOrderId(order.getOrderId());
                            document.setOrderNumber(numbers[i]);
                            document.setContainerNo(orderDoc.getContainerNo());
                            document.setFileTypeKey(DocumentsType.FOLLOW_VEHICLE_FORMAL_FILE);
                            document.setFileUrl(orderDoc.getFileUrl());
                            document.setFileName(orderDoc.getFileName());
                            document.setUploadTime(new Date());
                            document.setCreateTime(new Date());
                            orderDocumentService.insertOrderDocument(document);
                            BusiZyInfo zyadd = new BusiZyInfo();
                            zyadd.setOrderId(order.getOrderId());
                            zyadd.setXianghao(numbers[i]);
                            zyadd.setSuicheDocu("是");
                            busiZyInfoService.updateBusiZyInfoByXh(zyadd);
                        } else if ("delete".equals(type)) {
                            orderDocumentService.deleteByConAndOrderIdAndKey(
                                    orderDoc.getContainerNo(),
                                    order.getOrderId(),
                                    DocumentsType.FOLLOW_VEHICLE_FORMAL_FILE);
                            BusiZyInfo zyadd = new BusiZyInfo();
                            zyadd.setOrderId(order.getOrderId());
                            zyadd.setXianghao(numbers[i]);
                            zyadd.setSuicheDocu("否");
                            busiZyInfoService.updateBusiZyInfoByXh(zyadd);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听大口岸系统--提单（提单草单，正式提单（正本提单、电放提单））
     * 1提单草单，2电放提单，3正本提单,4是站到站分拨提单5是站到门分拨提单
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_LADINGBILL_QUEUE_BLPTSYSTEM)
    public void getLadingBillFormal(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统  提单文件");
            log.info(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            OrderDocUrlDka orderDoc =
                    FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrlDka.class);
            // 查询舱位号对应的orderId
            OrderDocUrl order = orderDocumentService.getOrderIdByNumbers(orderDoc.getOrderNumber());

            if (order != null) {
                String type = orderDoc.getType();
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("大口岸系统");
                document.setOrderId(order.getOrderId());
                document.setOrderNumber(order.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                switch (type) {
                    case "1":
                        document.setFileTypeKey(DocumentsType.LADING_BILL_DRAFT_FILE);
                        break;
                    case "2":
                        document.setFileTypeKey(DocumentsType.LADING_BILL_FORMAL_FILE);
                        break;
                    case "3":
                        document.setFileTypeKey(DocumentsType.LADING_BILL_FORMAL_FILE);
                        break;
                }
                document.setFileUrl(orderDoc.getUrl());
                document.setFileName(orderDoc.getUrl().substring(orderDoc.getUrl().lastIndexOf('/') + 1));
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                if ("1".equals(orderDoc.getIsDelete())) {
                    if ("1,2,3,4,5".equals(type)) {
                        // 删除正式或电放提单
                        orderDocumentService.deleteByOrderIdAndFileTypeKey(order.getOrderId(), DocumentsType.LADING_BILL_FORMAL_FILE);
                        orderDocumentService.deleteByOrderIdAndFileTypeKey(order.getOrderId(), DocumentsType.LADING_BILL_DRAFT_FILE);
                    }
                } else {
                    // 新增
                    orderDocumentService.deleteByOrderIdAndFileTypeKey(order.getOrderId(), document.getFileTypeKey());
                    orderDocumentService.insertOrderDocument(document);
                    if (type.equals("2") || type.equals("3")) {
                        SysMessage sysMessage = new SysMessage();
                        sysMessage.setClientId(order.getClientId());
                        sysMessage.setOrderId(order.getOrderId());
                        sysMessage.setOrderNumber(order.getOrderNumber());
                        sysMessage.setMessageTitle("提单制作提醒");
                        sysMessage.setMessageContent("托书编号为: " + order.getOrderNumber() + "的" + (type.equals("2") ? "电放" : "正本") + "提单已生成，请及时下载");
                        sysMessage.setMessageType("单证");
                        sysMessage.setCreateTime(new Date());
                        sysMessageService.insertMessage(sysMessage);
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听大口岸系统--关检到货审核
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_BLPT)
    public void getArrivalExamine(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统  关检到货审核");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            DocOrderArrivalExamine orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), DocOrderArrivalExamine.class);
            String type = orderDoc.getType();
            arrivalExamineService.deleteDocOrderArrivalExamineByOrderId(orderDoc.getOrderId());
            arrivalExamineService.insertDocOrderArrivalExamine(orderDoc);
            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("大口岸系统");
            document.setOrderId(orderDoc.getOrderId());
            document.setOrderNumber(orderDoc.getOrdernumber());
            document.setFileTypeKey(DocumentsType.GJ_ARRIVAL_EXAMINE_FILE);
            document.setUploadTime(new Date());
            document.setCreateTime(new Date());
            orderDocumentService.insertOrderDocument(document);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听大口岸系统--托书随车审核
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_FOLLOWVEHICLE_EXAMINE_QUEUE_BLPT)
    public void getFollowVehicleExamine(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统  托书随车审核");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            DocOrderFollowVehicleExamine orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), DocOrderFollowVehicleExamine.class);
            followVehicleExamineService.deleteDocOrderFollowVehicleExamineByOrderId(orderDoc.getOrderId());
            followVehicleExamineService.insertDocOrderFollowVehicleExamine(orderDoc);
            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("大口岸系统");
            document.setOrderId(orderDoc.getOrderId());
            document.setOrderNumber(orderDoc.getOrderNumber());
            document.setFileTypeKey(DocumentsType.FOLLOWVEHICLE_EXAMINE_FILE);
            document.setUploadTime(new Date());
            document.setCreateTime(new Date());
            orderDocumentService.insertOrderDocument(document);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听大口岸系统--箱号随车审核
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_CONFOLLOWVEHICLE_EXAMINE_QUEUE_BLPT)
    public void getConFollowVehicleExamine(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统  箱号随车审核");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            DocOrderConexamine orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), DocOrderConexamine.class);
            conexamineService.deleteDocOrderConexamineByOrderId(orderDoc.getOrderId());
            conexamineService.insertDocOrderConexamine(orderDoc);
            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("大口岸系统");
            document.setOrderId(orderDoc.getOrderId());
//            document.setOrderNumber(orderDoc.getOrdernumber());
            document.setFileTypeKey(DocumentsType.CON_FOLLOWVEHICLE_EXAMINE_FILE);
            document.setUploadTime(new Date());
            document.setCreateTime(new Date());
            orderDocumentService.insertOrderDocument(document);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听大口岸系统--提箱情况表文件
     *
     * @param channel
     * @param message
     */
//    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_PICKCONMSG_QUEUE_BLPT)
    public void getPickConMsg(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统提箱情况表文件");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            Map map = message.getMessageProperties().getHeaders();
            String fileName = map.get("fileName").toString();
            String orderId = map.get("orderId").toString();
            String orderNumber = map.get("orderNumber").toString();
            byte[] body = message.getBody();
            // 提箱情况表地址
            String path = PdfConstants.DKA_PICK_CON_MSG_PATH;
            // 单证类型 key
            String fileTypeKey = DocumentsType.PICK_CON_MSG_FILE;
            File paths = new File(path);
            if (!paths.exists()) {
                paths.mkdirs();
            }
            path = path + fileName;
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            Files.copy(new ByteArrayInputStream(body), file.toPath());
            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("大口岸系统");
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
     * 监听大口岸系统--ATB文件(大口岸新系统对接)
     *
     * @param channel
     * @param message
     */
    //    @RabbitListener(queues = DKASystemRabbitmqConfig.DKA_SYSTEM_ATB_QUEUE_BLPT)
    //    @RabbitListener(queues = "atb_text")
    public void getATB(Channel channel, Message message) {
        try {
            log.info("获取大口岸系统 ATB文件");
            String body = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            body = StringUtils.substring(body, 0, body.length());
            log.info(body);
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(body, OrderDocUrl.class);
            String type = orderDoc.getType();
            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("大口岸系统");
            // todo 班列id
            document.setOrderId(orderDoc.getOrderId());
            //            document.setOrderNumber(orderNumber);
            document.setContainerNo(orderDoc.getContainerNo());
            document.setFileTypeKey(DocumentsType.ATB_FILE);
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
