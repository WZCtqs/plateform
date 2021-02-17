package com.szhbl.project.documentcenter.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.order.PXCZSystemRabbitmqConfig;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.*;
import com.szhbl.project.documentcenter.service.*;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.mapper.BusiZyInfoMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.service.ITrackGoodsStatusService;
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
public class PxczSystemListener {

    @Autowired
    private IOrderDocumentService orderDocumentService;

    @Autowired
    private IDocInBoxCheckService docInBoxCheckService;

    @Autowired
    private IDocBoxingListService docBoxingListService;

    @Autowired
    private IDocYardLoadedInService docYardLoadedInService;

    @Autowired
    private IDocPxGoodsInOutService docPxGoodsInOutService;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private ITrackGoodsStatusService trackGoodsStatusService;

    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    @Autowired
    private CommonService commonService;
    @Autowired
    private BusiZyInfoMapper busiZyInfoMapper;

    /**
     * 监听拼箱场站系统装箱方案消息文件
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_BOXINGSCHEME_QUEUE_BLPT)
    public void getBoxingScheme(Channel channel, Message message) {
        try {
            log.info("获取拼箱场站系统装箱方案");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();
            /*单证表添加数据*/
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("拼箱系统");
            document.setOrderId(orderDoc.getOrderId());
            document.setOrderNumber(orderDoc.getOrderNumber());
            document.setContainerNo(orderDoc.getContainerNo());
            document.setFileTypeKey(DocumentsType.BOXING_SCHEME_FILE);
            document.setFileUrl(orderDoc.getFileUrl());
            document.setFileName(orderDoc.getFileName());
            document.setUploadTime(new Date());
            document.setCreateTime(new Date());
            orderDocumentService.insertOrderDocument(document);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听拼箱场站系统--拼箱到货信息
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_ARRIVALGOODS_QUEUE_BLPT)
    public void getFollowVehicle(Channel channel, Message message) {
        try {
            log.info("获取拼箱场站系统拼箱到货信息");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            PxInBoxCheck pxInBoxCheck = FastJsonUtils.json2Bean(new String(message.getBody()), PxInBoxCheck.class);
            // 删除原数据
            docInBoxCheckService.deleteDocInBoxCheckByOrderId(pxInBoxCheck.getOrder_id());
            // 新增数据
            BusiShippingorders shippingorders = busiShippingorderService.selectBusiShippingorderByIdOld(pxInBoxCheck.getOrder_id());
            if (null != shippingorders) {
                pxInBoxCheck.setCreateTime(new Date());
                docInBoxCheckService.insertDocInBoxCheck(pxInBoxCheck);
                // 获取订舱信息
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("拼箱场站系统");
                document.setOrderNumber(shippingorders.getOrderNumber());
                document.setOrderId(pxInBoxCheck.getOrder_id());
                document.setOrderNumber(shippingorders.getOrderNumber());
                document.setFileTypeKey(DocumentsType.PX_ARRIVAL_GOODS_FILE);
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听拼箱场站系统--装柜清单（件重尺）
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_BOXINGLISTSIZE_QUEUE_BLPT)
    public void getBoxingListSize(Channel channel, Message message) {
        try {
            log.info("获取拼箱场站系统装柜清单（件重尺）");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            PxBoxingList pxBoxingList = FastJsonUtils.json2Bean(new String(message.getBody()), PxBoxingList.class);
            // 删除原数据
            docBoxingListService.deleteByOrderIdXiangHao(pxBoxingList.getOrder_id(), pxBoxingList.getXianghao());
            if ("0".equals(pxBoxingList.getIsDelect())) {
                // 新增数据
                BusiShippingorders shippingorders = busiShippingorderService.selectBusiShippingorderByIdOld(pxBoxingList.getOrder_id());
                if (null != shippingorders) {
                    pxBoxingList.setCreateTime(new Date());
                    docBoxingListService.insertDocBoxingList(pxBoxingList);
                    // 获取订舱信息
                    /*单证表添加数据*/
                    DocOrderDocument document = new DocOrderDocument();
                    document.setFormSystem("拼箱场站系统");
                    document.setOrderNumber(shippingorders.getOrderNumber());
                    document.setOrderId(pxBoxingList.getOrder_id());
                    document.setOrderNumber(shippingorders.getOrderNumber());
                    document.setFileTypeKey(DocumentsType.BOXING_LIST_SIZE_FILE);
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
     * 监听拼箱场站系统--堆场重箱进站表
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_YARDLOADEDIN_QUEUE_BLPT)
    public void getYardLoadedin(Channel channel, Message message) {
        try {
            log.info("拼箱场站系统--堆场重箱进站表");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            PxYardLoadedIn pxYardLoadedIn = FastJsonUtils.json2Bean(new String(message.getBody()), PxYardLoadedIn.class);

            // 新增数据
            BusiShippingorders shippingorders =
                    busiShippingorderService.selectBusiShippingorderByIdOld(pxYardLoadedIn.getOrder_id());

            if ("1".equals(pxYardLoadedIn.getIsDelect())) {
                orderDocumentService.deleteByConAndOrderIdAndKey(pxYardLoadedIn.getXianghao(), pxYardLoadedIn.getOrder_id(), DocumentsType.YARD_LOADED_IN_FILE);
                // 删除原数据
                docYardLoadedInService.deleteDocYardLoadedInByOrderIdAndConno(pxYardLoadedIn.getOrder_id(), pxYardLoadedIn.getXianghao());
                TrackGoodsStatus trackGoodsStatus = new TrackGoodsStatus();
                trackGoodsStatus.setOrderId(pxYardLoadedIn.getOrder_id());
                trackGoodsStatus.setDelFlag(1);
                trackGoodsStatus.setBoxNum(pxYardLoadedIn.getXianghao());
                trackGoodsStatus.setFromSystem("拼箱场站系统删除");
                trackGoodsStatusService.deleteXcppTgs(trackGoodsStatus);
                busiZyInfoService.deleteZyInfoByTrack(
                        pxYardLoadedIn.getOrder_id(), pxYardLoadedIn.getXianghao());
                // 委托zih提货，删除保存到单证表的装箱照信息数据
                if (shippingorders != null && shippingorders.getShipOrderBinningway().equals("0")) {
                    DocOrderDocument deleteParam = new DocOrderDocument();
                    deleteParam.setOrderId(shippingorders.getOrderId());
                    deleteParam.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
                    deleteParam.setContainerNo(pxYardLoadedIn.getXianghao().trim());
                    deleteParam.setFormSystem("拼箱");
                    orderDocumentService.deleteByDocument(deleteParam);
                }
                // 更新客户自送货信息状态
                DocOrderDocument documentr = new DocOrderDocument();
                documentr.setOrderId(pxYardLoadedIn.getOrder_id());
                documentr.setContainerNo(pxYardLoadedIn.getXianghao().trim());
                documentr.setFormSystem("1");
                documentr.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
                documentr.setBoxingphotoStatus(0);
                documentr.setContainerStatus(0);
                documentr.setUploadTime(new Date());
                orderDocumentService.updateBoxPhotoStatus(documentr);
            } else {
                if (null != shippingorders) {
                    pxYardLoadedIn.setCreateTime(new Date());
                    orderDocumentService.deleteByConAndOrderIdAndKey(pxYardLoadedIn.getXianghao(), pxYardLoadedIn.getOrder_id(), DocumentsType.YARD_LOADED_IN_FILE);
                    docYardLoadedInService.deleteDocYardLoadedInByOrderIdAndConno(pxYardLoadedIn.getOrder_id(), pxYardLoadedIn.getXianghao());
                    docYardLoadedInService.insertDocYardLoadedIn(pxYardLoadedIn);
                    // 获取订舱信息
                    /*单证表添加数据*/
                    DocOrderDocument document = new DocOrderDocument();
                    document.setFormSystem("拼箱场站系统");
                    document.setOrderNumber(shippingorders.getOrderNumber());
                    document.setOrderId(pxYardLoadedIn.getOrder_id());
                    document.setContainerNo(pxYardLoadedIn.getXianghao());
                    document.setContainerType(pxYardLoadedIn.getXiangxing());
                    document.setFileTypeKey(DocumentsType.YARD_LOADED_IN_FILE);
                    document.setUploadTime(new Date());
                    document.setCreateTime(new Date());
                    orderDocumentService.insertOrderDocument(document);
                    // 委托zih提货，保存到单证表的装箱照信息数据
                    if (shippingorders.getShipOrderBinningway().equals("0")) {
                        DocOrderDocument boxingDoc = new DocOrderDocument();
                        boxingDoc.setOrderId(shippingorders.getOrderId());
                        boxingDoc.setOrderNumber(shippingorders.getOrderNumber());
                        boxingDoc.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
                        boxingDoc.setContainerNo(pxYardLoadedIn.getXianghao().trim());
                        boxingDoc.setContainerType(pxYardLoadedIn.getXiangxing());
                        boxingDoc.setSealnumber(pxYardLoadedIn.getSealNum());
                        boxingDoc.setContainerStatus(2);
//                        boxingDoc.setBoxingphotoStatus(2);
                        boxingDoc.setFormSystem("拼箱");
                        boxingDoc.setUploadTime(new Date());
                        boxingDoc.setCreateTime(new Date());
                        orderDocumentService.insertOrderDocument(boxingDoc);
                    }
                    if (StringUtils.isNotEmpty(pxYardLoadedIn.getPx_entry_date())) {
                        // 更新客户自送货信息状态
                        DocOrderDocument documentr = new DocOrderDocument();
                        documentr.setOrderId(pxYardLoadedIn.getOrder_id());
                        documentr.setContainerNo(pxYardLoadedIn.getXianghao().trim());
                        documentr.setFormSystem("1");
                        documentr.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
//                        documentr.setBoxingphotoStatus(2);
                        documentr.setContainerStatus(2);
                        document.setUploadTime(new Date());
                        orderDocumentService.updateBoxPhotoStatus(documentr);
                    }
                    //箱号检验
                    if (pxYardLoadedIn.getXianghao().trim().length() == 11) {
                        TrackGoodsStatus trackGoodsStatus = new TrackGoodsStatus();
                        // 修改
                        if ("0".equals(pxYardLoadedIn.getIsDelect())) {
                            trackGoodsStatus.setOrderId(pxYardLoadedIn.getOrder_id());
                            trackGoodsStatus.setBoxNum(pxYardLoadedIn.getXianghao());
                            trackGoodsStatus = trackGoodsStatusService.checkTgs(trackGoodsStatus);
                            if (trackGoodsStatus != null) { // 有数据，修改
                                trackGoodsStatus.setInStationDate(
                                        DateUtils.parseDate(pxYardLoadedIn.getPx_enter_car())); // 进站日期
                                trackGoodsStatus.setInStationTime(pxYardLoadedIn.getPx_enter_lead_number()); // 进站时间
                                trackGoodsStatus.setInSpaceDate(
                                        DateUtils.parseDate(pxYardLoadedIn.getPx_entry_date())); // 入场日期
                                trackGoodsStatus.setInSpaceTime(pxYardLoadedIn.getPx_entry_time()); // 入场时间

                                trackGoodsStatus.setBoxType(pxYardLoadedIn.getXiangxing());
                                trackGoodsStatus.setFromSystem("拼箱场站系统");
                                trackGoodsStatusService.updateTgs(trackGoodsStatus);
                                //更新正面吊测偏结果
                                BusiZyInfo zyinfoupd = new BusiZyInfo();
                                zyinfoupd.setOrderId(pxYardLoadedIn.getOrder_id()); // 订单id
                                zyinfoupd.setXianghao(pxYardLoadedIn.getXianghao()); // 箱号
                                zyinfoupd.setCepianResult(pxYardLoadedIn.getCepian_result()); //正面吊测偏结果
                                busiZyInfoMapper.updateZmdcByXh(zyinfoupd);
                            } else { // 新增
                                trackGoodsStatus = new TrackGoodsStatus();
                                trackGoodsStatus.setInStationDate(
                                        DateUtils.parseDate(pxYardLoadedIn.getPx_enter_car())); // 进站日期
                                trackGoodsStatus.setInStationTime(pxYardLoadedIn.getPx_enter_lead_number()); // 进站时间
                                trackGoodsStatus.setInSpaceDate(
                                        DateUtils.parseDate(pxYardLoadedIn.getPx_entry_date())); // 入场日期
                                trackGoodsStatus.setInSpaceTime(pxYardLoadedIn.getPx_entry_time()); // 入场时间

                                trackGoodsStatus.setOrderId(pxYardLoadedIn.getOrder_id());
                                trackGoodsStatus.setBoxNum(pxYardLoadedIn.getXianghao());
                                trackGoodsStatus.setBoxType(pxYardLoadedIn.getXiangxing());
                                trackGoodsStatus.setFromSystem("拼箱场站系统");
                                if ("0".equals(shippingorders.getClassEastandwest())) {
                                    trackGoodsStatusService.insertXcppTgs(trackGoodsStatus);
                                    BusiZyInfo zyinfo = new BusiZyInfo();
                                    zyinfo.setTrackId(trackGoodsStatus.getId()); // 货物状态表id
                                    zyinfo.setOrderId(pxYardLoadedIn.getOrder_id()); // 订单id
                                    zyinfo.setOrderNumber(shippingorders.getOrderNumber()); // 订单编号
                                    zyinfo.setXianghao(pxYardLoadedIn.getXianghao()); // 箱号
                                    zyinfo.setCepianResult(pxYardLoadedIn.getCepian_result()); //正面吊测偏结果
                                    busiZyInfoService.insertBusiZyInfo(zyinfo);
                                }
                            }
                        }
                    }else{
                        log.info("拼箱场站系统--堆场重箱进站表箱号错误，箱号："+pxYardLoadedIn.getXianghao());
                    }
                }
            }
        } catch (IOException e) {
            log.error("拼箱场站系统--堆场重箱进站表失败：{}",e.getMessage(), e);
        }
    }

    /**
     * 监听拼箱场站系统--拼箱出入库表
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_PXGOODSINOUT_QUEUE_BLPT)
    public void getPxGoodsInOut(Channel channel, Message message) {
        try {
            log.info("拼箱场站系统--拼箱出入库表");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            PxGoodsInOut pxGoodsInOut = FastJsonUtils.json2Bean(new String(message.getBody()), PxGoodsInOut.class);
            // 删除原数据
            docPxGoodsInOutService.deleteDocPxGoodsInOutByOrderId(pxGoodsInOut.getOrder_id());
            // 新增数据
            BusiShippingorders shippingorders = busiShippingorderService.selectBusiShippingorderByIdOld(pxGoodsInOut.getOrder_id());
            if (null != shippingorders) {
                pxGoodsInOut.setCreateTime(new Date());
                docPxGoodsInOutService.insertDocPxGoodsInOut(pxGoodsInOut);
                // 获取订舱信息
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("拼箱场站系统");
                document.setOrderNumber(shippingorders.getOrderNumber());
                document.setOrderId(pxGoodsInOut.getOrder_id());
                document.setOrderNumber(shippingorders.getOrderNumber());
                document.setFileTypeKey(DocumentsType.PX_GOODS_INOUT_FILE);
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听拼箱系统 接货照
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_GETGOODSPHOTO_QUEUE_BLPT)
    public void getGetGoodsFile(Message message, Channel channel) {
        try {
            log.info("获取拼箱系统 -接货照");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();
            if ("insert".equals(type)) {
                /*单证表添加数据*/
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("拼箱系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setContainerType(orderDoc.getContainerType());
                document.setFileTypeKey(DocumentsType.GETGOODS_PHOTO_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            } else if("delete".equals(type)){
                orderDocumentService.deleteByOrderIdAndUrl(orderDoc.getOrderId(), DocumentsType.GETGOODS_PHOTO_FILE, orderDoc.getFileUrl());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听拼箱系统 装箱照
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_BOXINGPHOTO_QUEUE_BLPT)
    public void getBoxingPhotoFile(Message message, Channel channel) {
        try {
            log.info("获取拼箱系统 -装箱照");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();
            /*单证表添加数据*/
            if ("insert".equals(type)) {
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("拼箱系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setContainerType(orderDoc.getContainerType());
                document.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            } else if ("delete".equals(type)) {
                orderDocumentService.deleteByOrderIdAndUrl(orderDoc.getOrderId(), DocumentsType.BOXING_PHOTO_FILE, orderDoc.getFileUrl());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 监听拼箱系统 拆箱照
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = PXCZSystemRabbitmqConfig.PX_SYSTEM_UNBOXINGPHOTO_QUEUE_BLPT)
    public void getUNBoxingPhotoFile(Message message, Channel channel) {
        try {
            log.info("获取拼箱系统 -拆箱照");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(new String(message.getBody()));
            OrderDocUrl orderDoc = FastJsonUtils.json2Bean(new String(message.getBody()), OrderDocUrl.class);
            String type = orderDoc.getType();
            /*单证表添加数据*/
            if ("insert".equals(type)) {
                DocOrderDocument document = new DocOrderDocument();
                document.setFormSystem("拼箱系统");
                document.setOrderId(orderDoc.getOrderId());
                document.setOrderNumber(orderDoc.getOrderNumber());
                document.setContainerNo(orderDoc.getContainerNo());
                document.setContainerType(orderDoc.getContainerType());
                document.setFileTypeKey(DocumentsType.UNBOXING_PHOTO_FILE);
                document.setFileUrl(orderDoc.getFileUrl());
                document.setFileName(orderDoc.getFileName());
                document.setUploadTime(new Date());
                document.setCreateTime(new Date());
                orderDocumentService.insertOrderDocument(document);
            } else if ("delete".equals(type)) {
                orderDocumentService.deleteByOrderIdAndUrl(orderDoc.getOrderId(), DocumentsType.UNBOXING_PHOTO_FILE, orderDoc.getFileUrl());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
