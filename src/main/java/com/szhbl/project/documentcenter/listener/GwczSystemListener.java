package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.order.GWCZSystemRabbitmqConfig;
import com.szhbl.project.documentcenter.domain.*;
import com.szhbl.project.documentcenter.domain.vo.BusiShippingorder;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.domain.vo.GWCZArrivalGoods;
import com.szhbl.project.documentcenter.domain.vo.OrderDocUrl;
import com.szhbl.project.documentcenter.service.*;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.listener.TrackStationToDoorListener;
import com.szhbl.project.track.service.ITrackGoodsStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HP
 */
@Slf4j
@Component
public class GwczSystemListener {
    @Autowired
    TrackStationToDoorListener trackStationToDoorListener;

    @Autowired
    private IOrderDocumentService orderDocumentService;

    @Autowired
    private IDocOrderInstationService instationService;

    @Autowired
    private IOrderNoticeFileService noticeFileService;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private ITrackGoodsStatusService trackGoodsStatusService;

    @Autowired
    private IDocOrderUnpackingagentService unpackingagentService;

    @Autowired
    private IDocGwczArrivalGoodsService gwczArrivalGoodsService;

    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    @Autowired
    private IDocOrderPickconNoService pickconNoService;

    @Autowired
    private IDocOrderPickconCommandService pickconCommandService;
    @Autowired
    private CommonService commonService;

    /**
     * 监听国外场站系统   装箱要求
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWCZSystemRabbitmqConfig.GWCZ_SYSTEM_BOXINGREQUIRE_QUEUE_BLPT)
    public void getBoxingScheme(Channel channel, Message message) {
        try {
            log.info("获取国外场站系统装箱要求");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            List<BusiShippingorder> shippingorders = noticeFileService.selectOrderByClassBh(orderDoc.getOrderNumber());
            List<DocOrderDocument> list = new ArrayList<>();
            if(shippingorders.size() > 0) {
                for (BusiShippingorder o : shippingorders) {
                    /*单证表添加数据*/
                    DocOrderDocument document = new DocOrderDocument();
                    document.setFormSystem("国外场站系统");
                    document.setOrderId(o.getOrderId());
                    document.setOrderNumber(o.getOrderNumber());
                    document.setFileTypeKey(DocumentsType.BOXING_REQUIRE_FILE);
                    document.setFileUrl(orderDoc.getFileUrl());
                    document.setFileName(orderDoc.getFileName());
                    document.setUploadTime(new Date());
                    document.setCreateTime(new Date());
                    list.add(document);
                }
                orderDocumentService.insertDocumentMatch(list);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听国外场站系统--拆箱代理表
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWCZSystemRabbitmqConfig.GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_BLPT)
    public void getFollowVehicle(Channel channel, Message message) {
        try {
            log.info("获取国外场站系统拆箱代理表");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            List<DocOrderUnpackingagent> unpackingagents = FastJsonUtils.json2List(new String(message.getBody()), DocOrderUnpackingagent.class);
            log.info(unpackingagents.size() + "");
            List<DocOrderDocument> list = new ArrayList<>();
            Date date = new Date();
            for (DocOrderUnpackingagent agent: unpackingagents) {
                agent.setCreateTime(date);
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("国外场站系统");
                document.setOrderId(agent.getOrderId());
                document.setOrderNumber(agent.getOrderNumber());
                document.setFileTypeKey(DocumentsType.DEVANNING_AGENT_FILE);
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                list.add(document);
                // 删除原来数据
                unpackingagentService.deleteDocOrderUnpackingagentByOrderId(agent.getOrderId());
            }
            // 托书单证表新增
            orderDocumentService.insertDocumentMatch(list);
            unpackingagentService.insertDocOrderUnpackingagentMatch(unpackingagents);

//            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
//            List<BusiShippingorder> shippingorders = noticeFileService.selectOrderByClassBh(orderDoc.getOrderNumber());
//            List<DocOrderDocument> list = new ArrayList<>();
//            if(shippingorders.size() > 0) {
//                for (BusiShippingorder o : shippingorders) {
//                    /*单证表添加数据*/
//                    DocOrderDocument document = new DocOrderDocument();
//                    document.setFormSystem("国外场站系统");
//                    document.setOrderId(o.getOrderId());
//                    document.setOrderNumber(o.getOrderNumber());
//                    document.setFileTypeKey(DocumentsType.DEVANNING_AGENT_FILE);
//                    document.setFileUrl(orderDoc.getFileUrl());
//                    document.setFileName(orderDoc.getFileName());
//                    document.setUploadTime(new Date());
//                    list.add(document);
//                }
//                orderDocumentService.insertDocumentMatch(list);
//            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听国外场站系统--运输计划
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWCZSystemRabbitmqConfig.GWCZ_SYSTEM_TRANSPORTPLAN_QUEUE_BLPT)
    public void getTransportPlan(Channel channel, Message message) {
        try {
            log.info("获取国外场站系统运输计划");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            List<BusiShippingorder> shippingorders = noticeFileService.selectOrderByClassBh(orderDoc.getOrderNumber());
            List<DocOrderDocument> list = new ArrayList<>();
            if(shippingorders.size() > 0) {
                for (BusiShippingorder o : shippingorders) {
                    /*单证表添加数据*/
                    DocOrderDocument document = new DocOrderDocument();
                    document.setFormSystem("国外场站系统");
                    document.setOrderId(o.getOrderId());
                    document.setOrderNumber(o.getOrderNumber());
                    document.setFileTypeKey(DocumentsType.TRANSPORT_PLAN_FILE);
                    document.setFileUrl(orderDoc.getFileUrl());
                    document.setFileName(orderDoc.getFileName());
                    document.setUploadTime(new Date());
                    document.setCreateTime(new Date());
                    orderDocumentService.deleteByOrderIdAndFileTypeKey(o.getOrderId(), DocumentsType.TRANSPORT_PLAN_FILE);
                    list.add(document);
                }
                orderDocumentService.insertDocumentMatch(list);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听国外场站系统--到货信息表
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWCZSystemRabbitmqConfig.GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_BLPT)
//    @RabbitListener(queues = "gwcz_system_arrivalgoodslist_queue_test")
    public void getArrivalGoodsList(Channel channel, Message message) {
        try {
            log.info("获取国外场站系统到货信息表");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            GWCZArrivalGoods arrivalGoods = FastJsonUtils.json2Bean(new String(message.getBody()), GWCZArrivalGoods.class);
            BusiShippingorders shippingorders = busiShippingorderService.selectBusiShippingorderByIdOld(arrivalGoods.getOrderId());
            if (null != shippingorders) {
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("国外场站系统");
                document.setOrderId(arrivalGoods.getOrderId());
                document.setOrderNumber(arrivalGoods.getOrderNumber());
                document.setFileTypeKey(DocumentsType.ARRIVAL_GOODS_LIST_FILE);
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.deleteByOrderIdAndFileTypeKey(
                        arrivalGoods.getOrderId(), DocumentsType.ARRIVAL_GOODS_LIST_FILE);
                orderDocumentService.insertOrderDocument(document);

                gwczArrivalGoodsService.deleteByOrderId(arrivalGoods.getOrderId());
                arrivalGoods.setCreateTime(document.getUploadTime());
                arrivalGoods.setOrderId(arrivalGoods.getOrderId());
                gwczArrivalGoodsService.insertGwczArrivalGoods(arrivalGoods);

                if ("1".equals(shippingorders.getClassEastandwest()) && "0".equals(shippingorders.getIsconsolidation())) { // 整柜
                    trackStationToDoorListener.handleTwoLevel(arrivalGoods.getOrderId(), "已入仓", "cargo has been to the warehouse", 1, "0", arrivalGoods.getArriveTime(), 15, null, null, null, "insert", "国外场站");
                } else if ("1".equals(shippingorders.getClassEastandwest()) && "1".equals(shippingorders.getIsconsolidation())) { // 回拼
                    if (StringUtils.isNotEmpty(arrivalGoods.getArriveTime())) {
                        trackStationToDoorListener.handleOneLevel(arrivalGoods.getOrderId(), "已入仓", "cargo has been to the warehouse", 1, arrivalGoods.getArriveTime(), 2, "update", "国外场站");
                        trackStationToDoorListener.handleTwoLevel(arrivalGoods.getOrderId(), "已入仓", "cargo has been to the warehouse", 1, "0", arrivalGoods.getArriveTime(), 15, 2, null, null, "insert", "国外场站");
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听-回程进站时间统计表
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWCZSystemRabbitmqConfig.GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_BLPT)
    public void getArrivalStatonTimeTotal(Channel channel, Message message) {
        try {
            log.info("获取回程进站时间统计表");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            DocOrderInstation instation = FastJsonUtils.json2Bean(new String(message.getBody()), DocOrderInstation.class);
            if (StringUtils.isEmpty(instation.getOrderId())) {
                OrderDocUrl order = orderDocumentService.getOrderIdByNumbers(instation.getOrderNumber());
                if (null != order) {
                    instation.setOrderId(order.getOrderId());
                    instation.setOrderNumber(order.getOrderNumber());
                }
            }
            // 删除原数据
            instationService.deleteDocOrderInstationByOrderId(instation.getOrderId());
            // 新增数据
            instation.setCreateTime(new Date());
            instationService.insertDocOrderInstation(instation);
            // 获取订舱信息
            BusiShippingorders shippingorders = busiShippingorderService.selectBusiShippingorderByIdOld(instation.getOrderId());
            String fromSystem = "";
            switch (instation.getFromSystem()) {
                case "js":
                    fromSystem = "集输系统";
                    DocOrderDocument document = new DocOrderDocument();
                    document.setFormSystem("班列平台");
                    document.setOrderNumber(shippingorders.getOrderNumber());
                    document.setOrderId(instation.getOrderId());
                    document.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
                    document.setContainerNo(instation.getContainerNo().trim());
                    document.setSealnumber(instation.getSealnum().trim());
                    document.setContainerStatus(2);
                    document.setBoxingphotoStatus(0);
                    document.setUploadTime(new Date());
                    document.setCreateTime(new Date());
                    orderDocumentService.insertOrderDocument(document);
                    break;
                case "gw":
                    fromSystem = "关务系统";
                    break;
                case "gwcz":
                    fromSystem = "国外场站系统";
                    if (StringUtils.isNotEmpty(instation.getInstationTime())) {
                        // 更新客户自送货信息状态
                        DocOrderDocument documentr = new DocOrderDocument();
                        documentr.setOrderId(instation.getOrderId());
                        documentr.setContainerNo(instation.getContainerNo().trim());
                        documentr.setFormSystem("1");
                        documentr.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
                        documentr.setContainerStatus(2);
                        documentr.setUploadTime(new Date());
                        orderDocumentService.updateBoxPhotoStatus(documentr);
                    }
                    break;
            }
            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem(fromSystem);
            document.setOrderNumber(shippingorders.getOrderNumber());
            document.setOrderId(instation.getOrderId());
            document.setFileTypeKey(DocumentsType.ARRIVAL_STATON_TIME_TOTAL_FILE);
            document.setUploadTime(new Date());
            document.setCreateTime(new Date());
            orderDocumentService.insertOrderDocument(document);

            //箱号检验
            if (instation.getContainerNo().trim().length() == 11) {
                Thread.sleep(2000);
                TrackGoodsStatus trackGoodsStatus = new TrackGoodsStatus();
                trackGoodsStatus.setOrderId(instation.getOrderId());
                trackGoodsStatus.setBoxNum(instation.getContainerNo());
                trackGoodsStatus = trackGoodsStatusService.checkTgs(trackGoodsStatus);
                if (trackGoodsStatus != null) {//有数据，修改
                    trackGoodsStatus.setBoxType(instation.getContainerType());
                    if (StringUtils.isNotEmpty(instation.getSealnum())) {
                        trackGoodsStatus.setSealNum(instation.getSealnum());
                    }
                    trackGoodsStatus.setFromSystem(fromSystem);
                    if (StringUtils.isNotEmpty(instation.getInstationTime())) {
                        String inStationDate=DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",DateUtils.parseDate(instation.getInstationTime()));
                        trackGoodsStatus.setInStationDate(DateUtils.parseDate(inStationDate.substring(0, 10)));//进站日期
                        if(instation.getInstationTime().length()>10){
                            trackGoodsStatus.setInStationTime(inStationDate.substring(11));//进站时间
                        }
                        if ("1".equals(shippingorders.getClassEastandwest()) && "0".equals(shippingorders.getIsconsolidation())) {//整柜
                            trackStationToDoorListener.handleOneLevel(instation.getOrderId(), "已进站", "has been into the station", 1, instation.getInstationTime(), 3, "update",fromSystem);
                            trackStationToDoorListener.handleTwoLevel(instation.getOrderId(), "已进站", "has been into the station", 1, "0", instation.getInstationTime(), 16, 3, null,  null,"update",fromSystem);
                        } else if ("1".equals(shippingorders.getClassEastandwest()) && "1".equals(shippingorders.getIsconsolidation())) {//拼箱
                            trackStationToDoorListener.handleOneLevel(instation.getOrderId(), "已进站", "has been into the station", 1, instation.getInstationTime(), 2, "update",fromSystem);
                            trackStationToDoorListener.handleTwoLevel(instation.getOrderId(), "已进站", "has been into the station", 1, "0", instation.getInstationTime(), 16, 2, null,  null,"update",fromSystem);
                        }
                    }
                    trackGoodsStatusService.updateTgs(trackGoodsStatus);
                }else {//新增
                    trackGoodsStatus = new TrackGoodsStatus();
                    trackGoodsStatus.setOrderId(instation.getOrderId());
                    trackGoodsStatus.setBoxNum(instation.getContainerNo());
                    trackGoodsStatus.setBoxType(instation.getContainerType());
                    trackGoodsStatus.setSealNum(instation.getSealnum());
                    trackGoodsStatus.setFromSystem(fromSystem);
                    if (StringUtils.isNotEmpty(instation.getInstationTime())) {
                        String inStationDate=DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",DateUtils.parseDate(instation.getInstationTime()));
                        trackGoodsStatus.setInStationDate(DateUtils.parseDate(inStationDate.substring(0, 10)));//进站日期
                        if(instation.getInstationTime().length()>10){
                            trackGoodsStatus.setInStationTime(inStationDate.substring(11));//进站时间
                        }
                        if ("1".equals(shippingorders.getClassEastandwest()) && "0".equals(shippingorders.getIsconsolidation())) {//整柜
                            trackStationToDoorListener.handleOneLevel(instation.getOrderId(), "已进站", "has been into the station", 1, instation.getInstationTime(), 3, "insert",fromSystem);
                            trackStationToDoorListener.handleTwoLevel(instation.getOrderId(), "已进站", "has been into the station", 1, "0", instation.getInstationTime(), 16, 3, null,  null,"insert",fromSystem);
                        } else if ("1".equals(shippingorders.getClassEastandwest()) && "1".equals(shippingorders.getIsconsolidation())) {//拼箱
                            trackStationToDoorListener.handleOneLevel(instation.getOrderId(), "已进站", "has been into the station", 1, instation.getInstationTime(), 2, "update",fromSystem);
                            trackStationToDoorListener.handleTwoLevel(instation.getOrderId(), "已进站", "has been into the station", 1, "0", instation.getInstationTime(), 16, 2, null,  null,"insert",fromSystem);
                        }
                    }
                    if("1".equals(shippingorders.getClassEastandwest())){
                        trackGoodsStatusService.insertXcppTgs(trackGoodsStatus);
                        BusiZyInfo zyinfo = new BusiZyInfo();
                        zyinfo.setTrackId(trackGoodsStatus.getId()); //货物状态表id
                        zyinfo.setOrderId(instation.getOrderId()); //订单id
                        zyinfo.setOrderNumber(shippingorders.getOrderNumber()); //订单编号
                        zyinfo.setXianghao(instation.getContainerNo());//箱号
                        busiZyInfoService.insertBusiZyInfo(zyinfo);
                    }
                }
            }else{
                log.info("获取回程进站时间统计表箱号错误,箱号:"+instation.getContainerNo());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听国外场站系统--提箱号表
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWCZSystemRabbitmqConfig.GWCZ_SYSTEM_PICKCON_NO_QUEUE_BLPT)
    public void getPickConNo(Channel channel, Message message) {
        try {
            log.info("获取国外场站系统  提箱号表");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            DocOrderPickconNo orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), DocOrderPickconNo.class);
            orderDoc.setUpdateTime(new Date());
            String type = orderDoc.getType();
            if ("insert".equals(type)) {
                pickconNoService.insertDocOrderPickconNo(orderDoc);
            } else if ("update".equals(type)) {
                pickconNoService.updateDocOrderPickconNo(orderDoc);
            } else if ("delete".equals(type)) {
                pickconNoService.deleteDocOrderPickconNo(orderDoc.getClassNum(), orderDoc.getContainerNum());
            }
            /*单证表添加数据*/
//            DocOrderDocument document = new DocOrderDocument();
//            document.setFormSystem("国外场站系统");
//            document.setOrderId(orderDoc.getOrderId());
//            document.setOrderNumber(orderDoc.getOrderNumber());
//            document.setContainerNo(orderDoc.getContainerNo());
//            document.setFileTypeKey(DocumentsType.PICK_CON_NO_FILE);
//            document.setFileUrl(orderDoc.getFileUrl());
//            document.setFileName(orderDoc.getFileName());
//            document.setUploadTime(new Date());
//            orderDocumentService.insertOrderDocument(document);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听国外场站系统--提箱指令
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GWCZSystemRabbitmqConfig.GWCZ_SYSTEM_PICKCON_COMMAND_QUEUE_BLPT)
    public void getPickConCommand(Channel channel, Message message) {
        try {
            log.info("获取国外场站系统 提箱指令");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            // 更新数据
            DocOrderPickconCommand pickconCommand = new DocOrderPickconCommand();
            BeanUtils.copyProperties(orderDoc, pickconCommand);
            pickconCommand.setUpdateTime(new Date());
            int i = pickconCommandService.updateDocOrderPickconCommand(pickconCommand);
            if (i == 0) {
                pickconCommandService.insertDocOrderPickconCommand(pickconCommand);
            }
            // todo 暂无法关联托书
            /*单证表添加数据*/
//            DocOrderDocument document = new DocOrderDocument();
//            document.setFormSystem("国外场站系统");
//            document.setOrderId(orderDoc.getOrderId());
//            document.setOrderNumber(orderDoc.getOrderNumber());
//            document.setContainerNo(orderDoc.getContainerNo());
//            document.setFileTypeKey(DocumentsType.PICK_CON_COMMAND_FILE);
//            document.setFileUrl(orderDoc.getFileUrl());
//            document.setFileName(orderDoc.getFileName());
//            document.setUploadTime(new Date());
//            orderDocumentService.insertOrderDocument(document);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
