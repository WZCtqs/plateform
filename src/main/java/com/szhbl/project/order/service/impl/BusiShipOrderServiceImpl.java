package com.szhbl.project.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.inquiry.XgCheckConfig;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.mapper.BusiClientsMapper;
import com.szhbl.project.inquiry.mapper.BookingInquiryOrderMapper;
import com.szhbl.project.order.domain.BusiOrderTocheckInquiry;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.*;
import com.szhbl.project.order.mapper.BusiOrderTocheckInquiryMapper;
import com.szhbl.project.order.mapper.BusiShipOrderMapper;
import com.szhbl.project.order.mapper.BusiShippingorderGoodsMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.AsyncService;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShipOrderService;
import com.szhbl.project.order.service.IBusiShippingorderGoodsService;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.domain.BusiSite;
import com.szhbl.project.trains.mapper.BusiClassesMapper;
import com.szhbl.project.trains.mapper.BusiSiteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BusiShipOrderServiceImpl implements IBusiShipOrderService {
    @Autowired
    private BusiShipOrderMapper busiShipOrderMapper;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private IBusiShippingorderGoodsService busiShippingorderGoodsService;
    @Autowired
    private BusiShippingorderGoodsMapper busiShippingorderGoodsMapper;
    @Autowired
    private BusiClassesMapper busiClassesMapper;
    @Autowired
    private BusiSiteMapper busiSiteMapper;
    @Autowired
    private BusiClientsMapper busiClientsMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private BusiOrderTocheckInquiryMapper busiOrderTocheckInquiryMapper;

    /**
     * 国外场站拼箱订舱信息处理
     *
     * @param gwczBookingVo
     * @return
     */
    @Transactional
    @Override
    public boolean gwczAdd(GwczBookingVo gwczBookingVo) {

        log.debug("新增国外场站拼箱订舱数据 ：\r\n {}", gwczBookingVo);

        BusiClasses busiClasses = busiClassesMapper.selectBusiClassesByBh(gwczBookingVo.getClassNumber());
        if (ObjectUtils.isEmpty(busiClasses)) {
            log.debug("没有这个班列信息,班列编号：{}", gwczBookingVo.getClassNumber());
            return true;
        }
        // 如果是新增
        if("update".equals(gwczBookingVo.getDatatype())) {
            // 查询是否已有托书
            ShippingOrder param = new ShippingOrder();
            param.setOrderClassBh(gwczBookingVo.getClassNumber());
            param.setZxNumber(gwczBookingVo.getId());
            List<ShippingOrder> busiShippingorders = busiShippingorderMapper.selectPxList(param);
            if (ObjectUtils.isEmpty(busiShippingorders)) {
                log.debug("没有需要更新的拼箱订舱托书，\r\n {}", gwczBookingVo);
                return Boolean.TRUE;
            }
            Date date = new Date();
            for (ShippingOrder shippingOrder : busiShippingorders) {
                BusiShippingorders busiShippingorder = new BusiShippingorders();
                busiShippingorder.setOrderId(shippingOrder.getOrderId());
                //更新订单表箱量
                busiShippingorder.setContainerBoxamount("0");
                //托书操作人信息
                busiShippingorder.setIsexamline("3");
                busiShippingorder.setTjTime(date);
                busiShippingorderMapper.cancelBusiShippingorder(busiShippingorder);
                //推送消息队列
                ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(shippingOrder.getOrderId());
                if (!ObjectUtils.isEmpty(orderInfoRabbmq)) {
                    String messagetype = "6"; //取消托书
                    try {
                        commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return insertPxOrder(gwczBookingVo,busiClasses);

    }

    /**
     * 拼箱订舱新增
     *
     * @param gwczBookingVo
     * @return
     */
    public Boolean insertPxOrder(GwczBookingVo gwczBookingVo, BusiClasses busiClasses) {

        String PX = "陆港拼箱";
        // 新增数据
        BusiShippingorders orderIns = new BusiShippingorders();
        // 暂存拼箱数据id
        orderIns.setZxNumber(gwczBookingVo.getId());
        // 审核状态
        orderIns.setIsexamline("0");
        // 站到站
        orderIns.setBookingService("2");
        //  0拼箱 1独立箱
        orderIns.setIsconsolidation("0");
        // 箱属
        orderIns.setOrderAuditBelongto("0");
        // 提交时间
        orderIns.setTjTime(new Date());
        orderIns.setTjFTime(new Date());
        // 去回程
        orderIns.setClassEastandwest(busiClasses.getClassEastandwest());
        // 线路id
        orderIns.setLineTypeid(String.valueOf(busiClasses.getLineTypeid()));
        // 是否可提前班列（0是1否）
        orderIns.setPutoffClass(Long.valueOf(1));
        // 班列id
        orderIns.setClassId(String.valueOf(busiClasses.getClassId()));
        // 班列号
        orderIns.setClassNumber(busiClasses.getClassNumber());
        // 班列编号
        orderIns.setOrderClassBh(busiClasses.getClassBh());
        // 班列发车日
        orderIns.setClassDate(busiClasses.getClassStime());

        // 贸易方式
        orderIns.setDictId(Long.valueOf(2097));
        orderIns.setDictName("其他贸易");
        // 是否需要发票 0否 1是
        orderIns.setOrderIsticket("1");
        // 客户id
        orderIns.setClientId("660ebb56-1be9-47cf-a45b-f1de355291c8");
        BusiClients busiClients = busiClientsMapper.selectBusiClientsById(orderIns.getClientId());
        // 创建时间
        orderIns.setOrderAuditCreatedate(new Date());
        // 创建人姓名
        if(StringUtils.isEmpty(gwczBookingVo.getZihtjr())){
            orderIns.setOrderAuditUsername(busiClients.getClientContacts());
        } else {
            orderIns.setOrderAuditUsername(gwczBookingVo.getZihtjr());
        }
        // 邮箱
        String emails = busiClients.getClientEmail();
        if (!StringUtils.isEmpty(gwczBookingVo.getEmail())) {
            emails = gwczBookingVo.getEmail();
        }
        // 客户推荐人
        orderIns.setClientTjrId(busiClients.getClientTjrId());
        orderIns.setClientTjr(busiClients.getClientTjr());
        orderIns.setOrderMerchandiser("操作一");
//        if (busiClasses.getClassEastandwest().equals("0")) {//xi
//            orderIns.setOrderMerchandiser(busiClients.getwMerchandiser());//西向跟单员
//            orderIns.setOrderMerchandiserId(busiClients.getwMerchandiserId());//跟单员id
//        }
//        if (busiClasses.getClassEastandwest().equals("1")) {//dong
//            orderIns.setOrderMerchandiser(busiClients.geteMerchandiser());
//            orderIns.setOrderMerchandiserId(busiClients.geteMerchandiserId());
//        }
        // 订舱方名称
        orderIns.setClientUnit(busiClients.getClientUnit());
        // 订舱方联系人
        orderIns.setClientContacts(busiClients.getClientContacts());
        // 订舱方联系方式
        orderIns.setClientTel(busiClients.getClientTel());
        // 订舱方邮箱
        orderIns.setClientEmail(busiClients.getClientEmail());
        // 订舱方地址
        orderIns.setClientAddress(busiClients.getClientAddress());
        // 委托zih报关：0是 1否
        orderIns.setClientOrderBindingway("0");
        // 报关地点
        if ("0".equals(orderIns.getClassEastandwest())) {
            orderIns.setClientOrderBindingaddress("郑州");
        } else if ("1".equals(orderIns.getClassEastandwest()) &&
                "0".equals(orderIns.getLineTypeid())) {
            if ("汉堡".equals(orderIns.getOrderUploadsite())) {
                orderIns.setClientOrderBindingaddress("汉堡");
            } else if ("慕尼黑".equals(orderIns.getOrderUploadsite())) {
                orderIns.setClientOrderBindingaddress("慕尼黑");
            } else {
                orderIns.setClientOrderBindingaddress("属地");
            }
        } else {
            orderIns.setClientOrderBindingaddress("属地");
        }

        // 发货方
        orderIns.setShipOrederName(PX);
        // 发货方联系人
        orderIns.setShipOrederContacts(PX);
        // 发货方邮箱
        orderIns.setShipOrederEmail(emails);
        // 发货方联系电话
        orderIns.setShipOrederPhone(PX);
        // 发货方地址
        orderIns.setShipOrederAddress(PX);
        // 委托ZIH提货 0是 1否  2铁路到货
        orderIns.setShipOrderBinningway("1");

        // 提货方式（整箱到车站，散货到堆场
        // 委托ZIH提货（0是整箱到车站，1是散货到堆场）针对整柜
        orderIns.setShipThTypeId("0");
        orderIns.setShipThType("整箱到车站");
        /**
         * 发货方自送货方式 0散货到堆场 1整箱到车站
         */
        orderIns.setShipZsTypeId("1");
        orderIns.setShipZsType("整箱到车站");

        Calendar c = Calendar.getInstance();
        c.setTime(orderIns.getClassDate());   //设置时间
        c.set(Calendar.HOUR_OF_DAY,23);
        // 自送货时间
        orderIns.setShipOrderSendtime(c.getTime());
        // 提货时间
        orderIns.setShipOrderUnloadtime(c.getTime());
        // 提货地址
        orderIns.setShipOrderUnloadaddress(PX);
        // 到货城市receiveHyd
        // orderIns.setReceiveHyd();

        // 分拨方式
        // 提送货详细地址
        orderIns.setDetailedAddress(PX);
        // 收货方名称
        orderIns.setReceiveOrderName(PX);
        // 收货方联系人
        orderIns.setReceiveOrderContacts(PX);
        // 到站通知提货邮箱
        orderIns.setReceiveOrderMail(emails);
        // 收货方联系电话
        orderIns.setReceiveOrderPhone(PX);
        // 收货方邮箱
        orderIns.setReceiveOrderEmail(emails);
        // 通讯地址
        orderIns.setReceiveOrderAddress(PX);
        // 是否由ZIH代理清关  0否 1是
        orderIns.setReceiveOrderIsclearance("1");
        // 是否由ZIH代理送货  0否 1是
        orderIns.setReceiveOrderIspart("1");
        // 送货地址
        orderIns.setReceiveOrderPartaddress(PX);
        // 送货分拨联系人
        orderIns.setReceiveOrderZihcontacts(PX);
        // 送货分拨邮箱
        orderIns.setReceiveOrderZihemail(emails);
        // 送货分拨联系电话
        orderIns.setReceiveOrderZihtel(PX);
        // 在途信息接收邮箱
        orderIns.setReceiveOrderReceiveemail(emails);

        // 上下货站信息集合
        List<SiteInfoVo> siteInfoVos = gwczBookingVo.getTypeNumData();
        for (SiteInfoVo siteInfoVo: siteInfoVos) {
            // 上下货站信息查询
            BusiSite up = busiSiteMapper.selectSiteByName(siteInfoVo.getUploadSite());
            BusiSite down = busiSiteMapper.selectSiteByName(siteInfoVo.getUnloadSite());
            // 上货站
            orderIns.setOrderUploadsite(siteInfoVo.getUploadSite());
            if (!ObjectUtils.isEmpty(up)) {
                // 上货站编码
                orderIns.setOrderUploadcode(up.getCode());
            }
            // 下货站
            orderIns.setOrderUnloadsite(siteInfoVo.getUnloadSite());
            if (!ObjectUtils.isEmpty(down)) {
                // 下货站编码
                orderIns.setOrderUnloadcode(down.getCode());
            }
            // 提箱地
            if (busiClasses.getClassEastandwest().equals("0")) {
                orderIns.setShipHyd(siteInfoVo.getTixiang());
            } else {
                orderIns.setShipFhSite(siteInfoVo.getTixiang());
            }
            // 还箱地
            orderIns.setReceiveHxAddress(siteInfoVo.getHuanxiang());

            // 箱型箱量数据
            List<ContainerInfoVo> containerInfoVos = siteInfoVo.getTypenum();
            for (ContainerInfoVo containerInfoVo : containerInfoVos) {
                orderIns.setContainerType(containerInfoVo.getType());
                orderIns.setContainerTypeval(containerInfoVo.getType());
                orderIns.setContainerBoxamount(containerInfoVo.getNum());
                // 特种箱需要箱管先审核
//                if (commonService.isSpecialBox(containerInfoVo.getType())) {
//                    orderIns.setIsexamline("7");
//                } else {
//                    orderIns.setIsexamline("0");
//                }
                orderIns.setOrderId(IdUtils.randomUUID());
                busiShippingorderMapper.insertBusiShippingorder(orderIns);
                //商品 （关联订单表的）
                BusiShippingorderGoods busiShippingorderGoods = new BusiShippingorderGoods();
                busiShippingorderGoods.setGoodsId(IdUtils.randomUUID());
                // 订单ID
                busiShippingorderGoods.setOrderId(orderIns.getOrderId());
                // 唛头
                busiShippingorderGoods.setGoodsMark(PX);
                // 货品中文名称
                busiShippingorderGoods.setGoodsName(PX);
                // 货品英文名称
                busiShippingorderGoods.setGoodsEnName(PX);
                // 国内清关HS
                busiShippingorderGoods.setGoodsClearance(PX);
                // 国外清关HS
                busiShippingorderGoods.setGoodsOutClearance(PX);
                // 国内报关HS
                busiShippingorderGoods.setGoodsInReport(PX);
                // 国外报关HS
                busiShippingorderGoods.setGoodsReport(PX);
                // 最外层包装形式
                busiShippingorderGoods.setGoodsPacking(PX);
                // 最外层包装数量
                busiShippingorderGoods.setGoodsNumber("0");
                // 体积
                busiShippingorderGoods.setGoodsCbm("0");
                // 重量
                busiShippingorderGoods.setGoodsKgs("0");
                // 规格
                busiShippingorderGoods.setGoodsStandard("0");
                // 是否需要装箱方案：0需要 1不需要
                busiShippingorderGoods.setGoodsIsscheme("1");
                // 创建时间
                busiShippingorderGoods.setCreatedate(new Date());
                busiShippingorderGoodsMapper.insertBusiShippingorderGoods(busiShippingorderGoods);
//                if(commonService.isSpecialBox(containerInfoVo.getType())){  //是特种箱
//                    ShippingOrder orderCheckInfo = new ShippingOrder();
//                    BeanUtils.copyProperties(orderIns,orderCheckInfo);
//                    orderCheckInfo.setType("1");
//                    orderCheckInfo.setOrderId(orderIns.getOrderId());
//                    CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//                    MessageProperties header = new MessageProperties();
//                    header.getHeaders().put("__TypeId__","Object");
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    Message message = null;
//                    try {
//                        message = new Message(objectMapper.writeValueAsBytes(orderCheckInfo), header);
//                        rabbitTemplate.convertAndSend(XgCheckConfig.XG_SYSTEM_CHECK_EXCHANGE, "xg.system.topic.check.add", message,correlationData);
//                        log.debug("审核信息-----箱管发送成功：{}",message.getBody());
//                    } catch (JsonProcessingException e) {
//                        log.error("审核信息-----箱管发送失败：{}，{}",e.getMessage(),e.getStackTrace());
//                    }
//
//                }
            }
        }

        return Boolean.TRUE;
    }

    /**
     * 查询托书箱量
     */
    @Override
    public String orderZgCount(String orderId){
        return busiShipOrderMapper.orderZgCount(orderId);
    }

    /**
     * 托书修改整柜箱量
     */
    @Override
    public int zgCountChange(BusiShippingorders busiShippingorder){
        int result = 0;
        String orderId = busiShippingorder.getOrderId();
        String boxAmount = busiShippingorder.getContainerBoxamount();//新提交的箱量
        if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(boxAmount)){
            BusiShippingorders orderinfo = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId); //托书原数据
            if(StringUtils.isNotNull(orderinfo)){
                if(StringUtils.equals(boxAmount,orderinfo.getContainerBoxamount())){
                    return 2;//新输入箱量与原箱量一致
                }
                if(!"0".equals(orderinfo.getIsconsolidation())){
                    return 3;//判断是否是整柜托书
                }
                String inquiryRecordId = orderinfo.getInquiryRecordId(); //询价结果id
                if(StringUtils.isNotEmpty(inquiryRecordId)){
                    inquiryResultZx inquiryInfo = busiShipOrderMapper.inquiryPirce(inquiryRecordId);
                    if(StringUtils.isNotNull(inquiryInfo)){
                        Integer containerNum = inquiryInfo.getContainerNum(); //询价箱量
                        BusiShippingorders orderUpd = new BusiShippingorders();
                        orderUpd.setOrderId(orderId);
                        orderUpd.setContainerBoxamount(boxAmount);
                        orderUpd.setShipThCost(commonService.zgPrice(inquiryInfo.getPickUpFees(),containerNum,boxAmount)); //提货费
                        orderUpd.setSitecost(commonService.zgPrice(inquiryInfo.getRailwayFees(),containerNum,boxAmount));//铁路运费
                        orderUpd.setReceiveShCost(commonService.zgPrice(inquiryInfo.getDeliveryFees(),containerNum,boxAmount)); //送货费
                        orderUpd.setPickUpBoxFee(commonService.zgBoxFee(inquiryInfo.getPickUpBoxFee(),containerNum,boxAmount));//提箱费
                        orderUpd.setReturnBoxFee(commonService.zgBoxFee(inquiryInfo.getReturnBoxFee(),containerNum,boxAmount));//还箱费
                        orderUpd.setCreateuserid(busiShippingorder.getCreateuserid());
                        orderUpd.setCreateusername(busiShippingorder.getCreateusername());
                        orderUpd.setCreatedate(DateUtils.getNowDate());
                        orderUpd.setTjTime(DateUtils.getNowDate());
                        orderUpd.setIsupdate("1");
                        result = busiShippingorderMapper.updateBusiShippingorder(orderUpd);
                        if(result==1){
                            //更新修改记录
                            BusiShippingorderGoods goodsinfo = busiShippingorderGoodsService.selectBusiShippingorderGoodsByOrderId(orderId);//商品表数据
                            String editrecordTotal = goodsinfo.getGoodsHistoryEditrecord();
                            String title = "<th>修改人："+busiShippingorder.getCreateusername()+",修改时间："+DateUtils.parseStr(DateUtils.getNowDate())+"</th>";
                            String editrecord = "";
                            if(!StringUtils.equals(boxAmount,orderinfo.getContainerBoxamount())){
                                editrecord = editrecord+"箱量：由"+orderinfo.getContainerBoxamount()+"修改为"+boxAmount+"<td>";
                            }
                            if(StringUtils.isNotEmpty(editrecord)){
                                editrecord = title + editrecord +"<###>";
                                editrecordTotal = editrecordTotal+editrecord;
                                //更新货物表修改记录
                                BusiShippingorderGoods updgoods = new BusiShippingorderGoods();
                                updgoods.setOrderId(orderId);
                                updgoods.setGoodsHistoryEditrecord(editrecordTotal);
                                busiShippingorderGoodsService.updateBusiShippingorderGoods(updgoods);
                            }
                            //推送消息队列
                            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                            if(StringUtils.isNotNull(orderInfoRabbmq)){
                                String messagetype = "7";//托书修改
                                try {
                                    commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                                } catch (JsonProcessingException e) {
                                    log.error("平台箱量修改失败",e.toString(),e.getStackTrace());
                                }
                            }
                            //生成配舱通知书
                            asyncService.createOrderNotice(orderId);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 已审核托书再次订舱-新增托书记录
     */
    @Override
    public int insertBusiShippingorder(BusiShippingorders busiShippingorder){
        int result = 0;
        String orderId = busiShippingorder.getOrderId();//订单id
        //商品信息
        BusiShippingorderGoods busiShippingGoods = commonService.orderGoodsInfo(busiShippingorder);
        //最新上下货站编号
        if(StringUtils.isNotEmpty(busiShippingorder.getOrderUploadsite())){
            busiShippingorder.setOrderUploadcode(busiSiteMapper.getCodeByName(busiShippingorder.getOrderUploadsite()));
        }
        if(StringUtils.isNotEmpty(busiShippingorder.getOrderUnloadsite())){
            busiShippingorder.setOrderUnloadcode(busiSiteMapper.getCodeByName(busiShippingorder.getOrderUnloadsite()));
        }
        //判断托书状态
        String isexamline = "";
        BusiOrderTocheckInquiry checkResult = busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(orderId);
        if(StringUtils.isNotNull(checkResult)){
            int isXgCheck = checkResult.getXgExamine(); //是否需要箱管审核报价 0是 1否
            int isJsCheck = checkResult.getJsExamine(); //集疏审核
            int isXxyoCheck = checkResult.getXxyoExamine(); //箱行亚欧审核
            int isYwCheck = checkResult.getYwExamine(); //业务审核
            int isClientCheck = checkResult.getClientExamine(); //客户确认
            if(isXgCheck==0){
                isexamline = "7";  //箱管部未审核
            }else if(isXxyoCheck==0 || isJsCheck==0 || isYwCheck==0){
                isexamline = "14";  //订舱报价中
            }else if(isClientCheck==0){
                isexamline = "15"; //订舱确认中
            }else{
                isexamline = "0"; //待审核
            }
            busiShippingorder.setIsexamline(isexamline);
            busiShippingorder.setOrderAuditCreatedate(DateUtils.getNowDate());
            busiShippingorder.setTjTime(DateUtils.getNowDate());
            busiShippingorder.setTjFTime(DateUtils.getNowDate());
            busiShippingorder.setCreatedate(DateUtils.getNowDate());
            busiShippingorder.setExameTime(null);
            busiShippingorder.setIsupdate("0");
            busiShippingorder.setOrderNumber("");
            busiShippingorder.setStationid("");
            busiShippingorder.setTurncount(Long.valueOf(0));
            busiShippingorder.setTotalturncount(Long.valueOf(0));
            busiShippingorder.setTotalturncountavg(Long.valueOf(0));
            busiShippingorder.setClientOrderRemarks("0");//特殊备注 再次订舱
            int resultOrder = busiShippingorderMapper.insertBusiShippingorder(busiShippingorder);
            if(resultOrder==1){
                //插入商品表
                busiShippingGoods.setOrderId(orderId);
                busiShippingGoods.setCreatedate(DateUtils.getNowDate());
                busiShippingGoods.setGoodsHistoryEditrecord("");
                int resultGoods = busiShippingorderGoodsService.insertBusiShippingorderGoods(busiShippingGoods);
                if(resultGoods==1){
                    result=1;
                    //推送消息队列
                    ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId); //托书信息
                    if(StringUtils.isNotNull(orderInfoRabbmq)){
                        orderInfoRabbmq.setIsDelete("0");//删除标识 0否 1是
                        String messagetype = "0";
                        try {
                            commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                        } catch (JsonProcessingException e) {
                            log.error("再次订舱失败",e.toString(),e.getStackTrace());
                        }
                    }
                    //给跟单、业务发送邮件
                    String content = "再次订舱";
                    asyncService.orderSendEmailIn(orderId,content);
                }
            }
        }
        return result;
    }

    /**
     * 再次订舱最后一步，更新托书中最新询价信息
     */
    public int insertOrderInquiry(String orderId){
        int result = 0;
        BusiShippingorders orderinfo = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId); //托书数据
        if(StringUtils.isNotNull(orderinfo)){
            String bookingService = orderinfo.getBookingService(); //0门到门 1门到站 2站到站 3站到门
            String classEastandwest = orderinfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
            inquiryResultZx inquiryInfo = busiShipOrderMapper.inquiryNum(orderinfo.getInquiryRecordId()); //询价数据
            if (StringUtils.isNotNull(inquiryInfo)){
                BusiShippingorders orderUpd = new BusiShippingorders();
                orderUpd.setOrderId(orderId);
                if("0".equals(classEastandwest)){
                    //下货站
                    if(StringUtils.isNotEmpty(inquiryInfo.getDropStation())){
                        orderUpd.setOrderUnloadcode(busiSiteMapper.getCodeByName(inquiryInfo.getDropStation()));
                        orderUpd.setOrderUnloadsite(inquiryInfo.getDropStation());
                    }
                    //提箱地
                    if("0".equals(bookingService) || "1".equals(bookingService)){
                        orderUpd.setShipFhSite(inquiryInfo.getTxAddress());
                        orderUpd.setPickUpBoxFee(inquiryInfo.getPickUpBoxFee());
                    }
                }
                if("1".equals(classEastandwest)){
                    //上货站
                    if(StringUtils.isNotEmpty(inquiryInfo.getUploadStation())){
                        orderUpd.setOrderUploadcode(busiSiteMapper.getCodeByName(inquiryInfo.getUploadStation()));
                        orderUpd.setOrderUploadsite(inquiryInfo.getUploadStation());
                    }
                    //提箱地
                    if("0".equals(bookingService) || "1".equals(bookingService)){
                        orderUpd.setShipHyd(inquiryInfo.getTxAddress());
                        orderUpd.setPickUpBoxFee(inquiryInfo.getPickUpBoxFee());
                    }
                }
                //还箱地
                if("0".equals(bookingService) || "3".equals(bookingService)){
                    orderUpd.setReceiveHxAddress(inquiryInfo.getHxAddress());
                    orderUpd.setReturnBoxFee(inquiryInfo.getReturnBoxFee());
                }
                orderUpd.setShipThCostNo(inquiryInfo.getShipThCostNo());//提货报价编号
                orderUpd.setShipThCost(inquiryInfo.getPickUpFees());//提货费
                orderUpd.setZxThcostCurrency(inquiryInfo.getPickUpCurrencyType());//提货费币种
                orderUpd.setReceiveShCostId(inquiryInfo.getReceiveShCostId());//送货报价编号
                orderUpd.setReceiveShCost(inquiryInfo.getDeliveryFees());//送货费
                orderUpd.setShCostcurrency(inquiryInfo.getDeliveryCurrencyType());//送货费币种
                orderUpd.setSitecost(inquiryInfo.getRailwayFees());//铁路运费
                orderUpd.setSitecostCurrency(inquiryInfo.getRailwayCurrencyType());//铁路运费币种
                orderUpd.setTjTime(DateUtils.getNowDate());
                result = busiShipOrderMapper.updateOrderinquiry(orderUpd);
            }
        }
        return result;
    }

    /**
     * 未审核托书再次订舱
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    @Override
    public int insertShiporder(BusiShippingorders busiShippingorder) throws JsonProcessingException
    {
        int result = 0;
        String classId = busiShippingorder.getClassId();
        if(StringUtils.isNotEmpty(classId)){
            //判断班列是否已发车
            BusiClasses classInfo = busiClassesMapper.selectBusiClassesById(classId);
            if(StringUtils.isNotNull(classInfo)){
                String classState = classInfo.getClassState();
                if(!"0".equals(classState)){
                    return 4;
                }
            }else{
                return 5;
            }
            //判断是否可订舱
            Boolean isChange = commonService.isSpace(classId,busiShippingorder);
            if(!isChange){
                return 2;
            }
        }else{
            return 5;
        }
        //商品信息
        BusiShippingorderGoods busiShippingGoods = commonService.orderGoodsInfo(busiShippingorder);
        //查询该托书信息
        String orderid=busiShippingorder.getOrderId();
        ShippingOrder orderinfo = busiShippingorderMapper.selectBusiShippingorderById(orderid);
        if(StringUtils.isNotNull(orderinfo)){
            String isconsolidation = orderinfo.getIsconsolidation(); //0整柜 1拼箱
            String containerType = busiShippingorder.getContainerType();
            //插入托书表
            busiShippingorder.setIsexamline("0");
            //特种箱箱管审核
            if("0".equals(isconsolidation)){  //整柜
                if(StringUtils.isNotEmpty(containerType)){
                    if(commonService.isSpecialBox(containerType)){
                        busiShippingorder.setIsexamline("7");
                    }
                }
            }
            busiShippingorder.setOrderId(IdUtils.randomUUID());
            busiShippingorder.setOrderAuditCreatedate(DateUtils.getNowDate());
            busiShippingorder.setTjTime(DateUtils.getNowDate());
            busiShippingorder.setTjFTime(DateUtils.getNowDate());
            busiShippingorder.setCreatedate(DateUtils.getNowDate());
            busiShippingorder.setExameTime(null);
            busiShippingorder.setIsupdate("0");
            busiShippingorder.setOrderNumber("");
            busiShippingorder.setStationid("");
            busiShippingorder.setTurncount(Long.valueOf(0));
            busiShippingorder.setTotalturncount(Long.valueOf(0));
            busiShippingorder.setClientOrderRemarks("1"); //0已审核托书再次订舱 1未审核托书再次订舱
            //整柜箱量是否修改
            if("0".equals(isconsolidation)){
                String inquiryRecordId = orderinfo.getInquiryRecordId();//询价结果id
                inquiryResultZx inquiryInfo = busiShipOrderMapper.inquiryPirce(inquiryRecordId);
                if(StringUtils.isNotNull(inquiryInfo)){
                    Integer containerNum = inquiryInfo.getContainerNum(); //询价箱量
                    String boxAmountOld = orderinfo.getContainerBoxamount(); //托书原箱量
                    Integer boxAmountOldInt = StringUtils.isNotEmpty(boxAmountOld)?Integer.valueOf(boxAmountOld):0;
                    String boxAmount = busiShippingorder.getContainerBoxamount(); //新提交箱量
                    Integer boxAmountInt = StringUtils.isNotEmpty(boxAmount)?Integer.valueOf(boxAmount):0;
                    busiShippingorder.setContainerBoxamount(boxAmount);
                    if(boxAmountInt!=boxAmountOldInt){ //箱量改变
                        //价格
                        busiShippingorder.setShipThCost(commonService.zgPrice(inquiryInfo.getPickUpFees(),containerNum,boxAmount)); //提货费
                        busiShippingorder.setSitecost(commonService.zgPrice(inquiryInfo.getRailwayFees(),containerNum,boxAmount));//铁路运费
                        busiShippingorder.setReceiveShCost(commonService.zgPrice(inquiryInfo.getDeliveryFees(),containerNum,boxAmount)); //送货费
                        busiShippingorder.setPickUpBoxFee(commonService.zgBoxFee(inquiryInfo.getPickUpBoxFee(),containerNum,boxAmount));//提箱费
                        busiShippingorder.setReturnBoxFee(commonService.zgBoxFee(busiShippingorder.getReturnBoxFee(),containerNum,boxAmount));//还箱费
                    }
                }
            }
            int resultOrder = busiShippingorderMapper.insertBusiShippingorder(busiShippingorder);
            if(resultOrder==1){
                //插入商品表
                String orderId = busiShippingorder.getOrderId();
                busiShippingGoods.setOrderId(orderId);
                busiShippingGoods.setCreatedate(DateUtils.getNowDate());
                busiShippingGoods.setGoodsHistoryEditrecord("");
                int resultGoods = busiShippingorderGoodsService.insertBusiShippingorderGoods(busiShippingGoods);
                //特种箱箱管审核
                if("0".equals(isconsolidation)){  //整柜
                    if(StringUtils.isNotEmpty(containerType)){
                        if(commonService.isSpecialBox(containerType)){  //是特种箱
                            ShippingOrder orderCheckInfo = new ShippingOrder();
                            BeanUtils.copyProperties(busiShippingorder,orderCheckInfo);
                            orderCheckInfo.setType("1");
                            orderCheckInfo.setOrderId(busiShippingorder.getOrderId());
                            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                            MessageProperties header = new MessageProperties();
                            header.getHeaders().put("__TypeId__","Object");
                            ObjectMapper objectMapper = new ObjectMapper();
                            Message message = new Message(objectMapper.writeValueAsBytes(orderCheckInfo), header);
                            rabbitTemplate.convertAndSend(XgCheckConfig.XG_SYSTEM_CHECK_EXCHANGE, "xg.system.topic.check.add", message,correlationData);
                            log.debug("审核信息-----箱管发送成功：{}");
                        }
                    }
                }
                if(resultGoods==1){
                    result=1;
                    //推送消息队列
                    ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId); //托书信息
                    if(StringUtils.isNotNull(orderInfoRabbmq)){
                        orderInfoRabbmq.setIsDelete("0");//删除标识 0否 1是
                        String messagetype = "0";
                        commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                    }
                    //发送邮件
                    String content = "再次订舱";
                    //给跟单、业务发送邮件
                    asyncService.orderSendEmailIn(orderId,content);
                }
            }
        }
        return result;
    }

}
