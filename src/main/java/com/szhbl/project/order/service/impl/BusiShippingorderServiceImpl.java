package com.szhbl.project.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.inquiry.XgCheckConfig;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import com.szhbl.framework.email.IMailService;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.service.IBusiClientsService;
import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.documentcenter.service.IBusiStationService;
import com.szhbl.project.documentcenter.service.IDocOrderService;
import com.szhbl.project.inquiry.domain.*;
import com.szhbl.project.inquiry.mapper.BookingInquiryGoodsDetailsMapper;
import com.szhbl.project.inquiry.mapper.BookingInquiryOrderMapper;
import com.szhbl.project.inquiry.service.IJsBackTimesetService;
import com.szhbl.project.monitor.domain.SysMessage;
import com.szhbl.project.monitor.service.ISysMessageService;
import com.szhbl.project.order.domain.*;
import com.szhbl.project.order.domain.vo.*;
import com.szhbl.project.order.handler.ToCheckNotice;
import com.szhbl.project.order.mapper.BusiOrderTocheckInquiryMapper;
import com.szhbl.project.order.mapper.BusiShipOrderMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.mapper.ShippingorderExaminfoMapper;
import com.szhbl.project.order.service.*;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.mapper.TrackGoodsStatusMapper;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.mapper.BusiClassesMapper;
import com.szhbl.project.trains.mapper.BusiSiteMapper;
import com.szhbl.project.trains.service.IBusiClassesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 订单Service业务层处理
 *
 * @author dps
 * @date 2020-01-21
 */
@Slf4j
@Service
public class BusiShippingorderServiceImpl implements IBusiShippingorderService {
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private BusiShipOrderMapper busiShipOrderMapper;
    @Autowired
    private IBusiShippingorderApplyService busiShippingorderApplyService;
    @Autowired
    private IBusiShippingorderBackupService busiShippingorderBackupService;
    @Autowired
    private IBusiShippingorderGoodsService busiShippingorderGoodsService;
    @Autowired
    private IBusiShippingorderGoodsApplyService busiShippingorderGoodsApplyService;
    @Autowired
    private IBusiShippingorderGoodsBackupService busiShippingorderGoodsBackupService;
    @Autowired
    private IShippingorderExaminfoService shippingorderExaminfoService;
    @Autowired
    private ShippingorderExaminfoMapper shippingorderExaminfoMapper;
    @Autowired
    private IBusiClientsService busiClientsService;
    @Autowired
    private IBusiClassesService busiClassesService;
    @Autowired
    private ISysMessageService messageService;
    @Autowired
    private IDocOrderService docOrderService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BusiClassesMapper busiClassesMapper;
    @Autowired
    private BookingInquiryOrderMapper bookingInquiryOrderMapper;
    @Autowired
    private BusiOrderTocheckInquiryMapper busiOrderTocheckInquiryMapper;
    @Autowired
    ToCheckNotice toCheckNotice;
    @Autowired
    private IJsBackTimesetService jsBackTimesetService;
    @Autowired
    private BusiSiteMapper busiSiteMapper;
    @Autowired
    private IBusiStationService busiStationService;
    @Autowired
    private BookingInquiryGoodsDetailsMapper inquiryGoodsDetailsMapper;
    @Autowired
    private TrackGoodsStatusMapper trackGoodsStatusMapper;

    /**
     * 查询订单列表
     *
     * @param busiShippingorder 订单
     * @return 订单
     */
    @Override
    public List<ShippingOrderList> selectBusiShippingorderList(ShippingOrderList busiShippingorder) {
        String deptCode = busiShippingorder.getDeptCode();  //登录者部门编号
        String organizationCode = busiShippingorder.getOrganizationCode();  //筛选条件部门编号
        String readType = null;
        if (StringUtils.isEmpty(organizationCode)) {
            readType = "1"; //部门查询条件为空，全查
        } else {
            readType = "2"; //部门查询条件不为空，按条件查询
        }
        if (deptCode.contains("YWB")) {  //业务部账号登录
            readType = "3"; //默认按照自己部门编号模糊查询
            if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                readType = "4";
            }
            if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                readType = "5";
            }
            if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                readType = "6";
            }
            if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                readType = "7";
            }
            if (deptCode.length() > 15) {  //部门编号大于15位
                readType = "8";   //业务部普通人员，根据自己推荐人id查询
            }
        }
        if(deptCode.contains("GDB")){  //跟单人员账号登录
            readType = "9";  //查询自己对应的客户
        }
        busiShippingorder.setReadType(readType);
        return busiShippingorderMapper.selectBusiShippingorderList(busiShippingorder);
    }

    /**
     * 委托书汇总订单列表
     *
     * @param busiShippingorder 订单
     * @return 订单
     */
    @Override
    public List<OrderImportList> selectOrderImportList(OrderImportList busiShippingorder)
    {
        String deptCode = busiShippingorder.getDeptCode();  //登录者部门编号
        String readType = null;
        if(StringUtils.isNotEmpty(busiShippingorder.getOrganizationCode())){
            readType = "2"; //部门查询条件不为空，按条件查询
        }
        if(deptCode.contains("YWB")){  //业务部账号登录
            readType = "3"; //默认按照自己部门编号模糊查询
            if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                readType = "4";
            }
            if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                readType = "5";
            }
            if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                readType = "6";
            }
            if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                readType = "7";
            }
            if (deptCode.length() > 15) {  //部门编号大于15位
                readType = "8";   //业务部普通人员，根据自己推荐人id查询
            }
        }
        if (deptCode.contains("GDB")) {  //跟单人员账号登录
            readType = "9";  //查询自己对应的客户
        }
        busiShippingorder.setReadType(readType);
        List<OrderImportList> lists = busiShipOrderMapper.selectOrderImportList(busiShippingorder);
        for (OrderImportList order : lists) {
            List<String> oneWeights = new ArrayList<>();
            List<String> standards = new ArrayList<>();
            String standard = "";
            String oneWeight = "";
            // 查询单件重量
            List<BookingInquiryGoodsDetails> goodsDetails = inquiryGoodsDetailsMapper.selectGoodsInfoByOrderId(order.getOrderId());
            for (BookingInquiryGoodsDetails detail : goodsDetails) {
                oneWeights.add(detail.getGoodsWeight() + "*" + detail.getGoodsAmount());
                standards.add(detail.getGoodsLength() + "*" + detail.getGoodsWidth() + "*" + detail.getGoodsHeight() + "*" + detail.getGoodsAmount());
                standard = standard + detail.getGoodsLength() + "*" + detail.getGoodsWidth() + "*" + detail.getGoodsHeight() + "*" + detail.getGoodsAmount() + String.valueOf((char) 10);
                oneWeight = oneWeight + detail.getGoodsWeight() + "*" + detail.getGoodsAmount() + String.valueOf((char) 10);
            }
            order.setOneWeights(oneWeights);
            order.setOneWeight(oneWeight);
            order.setGoodsStandard(standard);
            order.setGoodsStandards(standards);
        }
        return lists;
    }

    /**
     * 箱量统计
     *
     * @param busiShippingorder 订单
     * @return 订单集合
     */
    @Override
    public List<Map> selectAmount (ShippingOrderList busiShippingorder){
        String deptCode = busiShippingorder.getDeptCode();  //登录者部门编号
        String organizationCode = busiShippingorder.getOrganizationCode();  //筛选条件部门编号
        String readType = null;
        if(StringUtils.isEmpty(organizationCode)){
            readType = "1"; //部门查询条件为空，全查
        }else{
            readType = "2"; //部门查询条件不为空，按条件查询
        }
        if(deptCode.contains("YWB")){  //业务部账号登录
            readType = "3"; //默认按照自己部门编号模糊查询
            if(deptCode.equals("YWB1000100")){  //业务部职级人员，根据规则查询
                readType = "4";
            }
            if(deptCode.equals("YWB1000101")){  //业务部职级人员，根据规则查询
                readType = "5";
            }
            if(deptCode.equals("YWB1000200")){  //业务部职级人员，根据规则查询
                readType = "6";
            }
            if(deptCode.equals("YWB1000201")){  //业务部职级人员，根据规则查询
                readType = "7";
            }
            if(deptCode.length()>15){  //部门编号大于15位
                readType = "8";   //业务部普通人员，根据自己推荐人id查询
            }
        }
        if(deptCode.contains("GDB")){  //跟单人员账号登录
            readType = "9";  //查询自己对应的客户
        }
        busiShippingorder.setReadType(readType);
        return busiShippingorderMapper.selectAmount(busiShippingorder);
    }
    @Override
    public ShippingOrderList selectAmountVW(ShippingOrderList busiShippingorder){
        String deptCode = busiShippingorder.getDeptCode();  //登录者部门编号
        String organizationCode = busiShippingorder.getOrganizationCode();  //筛选条件部门编号
        String readType = null;
        if(StringUtils.isEmpty(organizationCode)){
            readType = "1"; //部门查询条件为空，全查
        }else{
            readType = "2"; //部门查询条件不为空，按条件查询
        }
        if(deptCode.contains("YWB")){  //业务部账号登录
            readType = "3"; //默认按照自己部门编号模糊查询
            if(deptCode.equals("YWB1000100")){  //业务部职级人员，根据规则查询
                readType = "4";
            }
            if(deptCode.equals("YWB1000101")){  //业务部职级人员，根据规则查询
                readType = "5";
            }
            if(deptCode.equals("YWB1000200")){  //业务部职级人员，根据规则查询
                readType = "6";
            }
            if(deptCode.equals("YWB1000201")){  //业务部职级人员，根据规则查询
                readType = "7";
            }
            if(deptCode.length()>15){  //部门编号大于15位
                readType = "8";   //业务部普通人员，根据自己推荐人id查询
            }
        }
        if(deptCode.contains("GDB")){  //跟单人员账号登录
            readType = "9";  //查询自己对应的客户
        }
        busiShippingorder.setReadType(readType);
        return busiShippingorderMapper.selectAmountVW(busiShippingorder);
    }

    /**
     * 查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    @Override
    public ShippingOrder selectBusiShippingorderById(String orderId)
    {
        return busiShippingorderMapper.selectBusiShippingorderById(orderId);
    }
    @Override
    public ShippingOrder selectBusiShippingorderTemById(String orderId)
    {
        return busiShippingorderMapper.selectBusiShippingorderTemById(orderId);
    }

    /**
     * 查询原订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    @Override
    public BusiShippingorders selectBusiShippingorderByIdOld(String orderId)
    {
        return busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId);
    }

    /**
     * 查询托书编号是否重复
     *
     * @param orderNumber 订单编号
     * @return 订单
     */
    @Override
    public BusiShippingorders selectBusiShippingorderByOrderNumber(String orderNumber)
    {
        return busiShippingorderMapper.selectBusiShippingorderByOrderNumber(orderNumber);
    }

    /**
     * 查询订单商品详情
     *
     * @param inquiryRecordId 询价ID
     * @return 订单
     */
    @Override
    public List<BookingInquiryGoodsDetails> selectGoodsInfo(String inquiryRecordId){
        return busiShippingorderMapper.selectGoodsInfo(inquiryRecordId);
    }

    /**
     * 查询订单询价详情
     *
     * @param inquiryRecordId 询价ID
     * @return 订单
     */
    @Override
    public BookingInquiry selectInquiryInfo(String inquiryRecordId){
        return busiShippingorderMapper.selectInquiryInfo(inquiryRecordId);
    }

    /**
     * 查询订单询价结果详情(暂时不用)
     *
     * @param inquiryRecordId 询价ID
     * @return 订单
     */
    @Override
    public BookingInquiryResult selectInquiryRecordInfo(String inquiryRecordId){
        return busiShippingorderMapper.selectInquiryRecordInfo(inquiryRecordId);
    }

    /**
     * 修改订单
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    @Override
    //@Transactional
    public int updateBusiShippingorder(BusiShippingorders busiShippingorder) throws JsonProcessingException {
        String content = "";
        int result = 0;
        String isAddRecord = "0"; //是否需要插入订舱公告 0否 1是
        //查询该托书信息
        String orderid=busiShippingorder.getOrderId();
        //查询订单原数据
        ShippingOrder orderinfo = busiShippingorderMapper.selectBusiShippingorderById(orderid);
        if(StringUtils.isNotNull(orderinfo)){
            //托书基本信息
            busiShippingorder.setCreatedate(DateUtils.getNowDate());
            String orderNumber = busiShippingorder.getOrderNumber();  //托书编号
            String isExamline = orderinfo.getIsexamline();  //原审核状态
            String examlineN = busiShippingorder.getIsexamline(); //托书提交状态
            String classEastandwest = orderinfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
            String isconsolidation = orderinfo.getIsconsolidation(); //0整柜 1拼箱
            String operateType = busiShippingorder.getOperateType();  //操作类型：0审核 1编辑
            String inquiryRecordId = orderinfo.getInquiryRecordId(); //询价结果id
            if(StringUtils.isNotEmpty(isconsolidation)){
                //商品信息
                BusiShippingorderGoods busiShippingGoods = commonService.orderGoodsInfo(busiShippingorder);
                if(StringUtils.isEmpty(busiShippingorder.getGoodsbz())){busiShippingGoods.setGoodsbzS("1");}
                if(StringUtils.isEmpty(busiShippingorder.getHsbz())){busiShippingGoods.setHsbzS("1");}
                //托书编号
                if(operateType.equals("0") && (busiShippingorder.getIsexamline()).equals("2")){ //审核不通过
                    busiShippingorder.setOrderNumber("");
                }
                if(StringUtils.isNotEmpty(busiShippingorder.getOrderNumber())){
                    busiShippingorder.setOrderNumber((busiShippingorder.getOrderNumber()).trim());
                }
                //整柜箱量是否修改
                if("0".equals(isconsolidation)){
                    inquiryResultZx inquiryInfo = busiShipOrderMapper.inquiryPirce(inquiryRecordId);
                    if(StringUtils.isNotNull(inquiryInfo)){
                        Integer containerNum = inquiryInfo.getContainerNum(); //询价箱量
                        String boxAmountOld = orderinfo.getContainerBoxamount(); //托书原箱量
                        Integer boxAmountOldInt = StringUtils.isNotEmpty(boxAmountOld)?Integer.valueOf(boxAmountOld):0;
                        String boxAmount = busiShippingorder.getContainerBoxamount(); //新提交箱量
                        Integer boxAmountInt = StringUtils.isNotEmpty(boxAmount)?Integer.valueOf(boxAmount):0;
                        String isAddCount = "0"; //箱量是否增加 0否 1是
                        String isChangeCount = "0"; //箱量是否修改 0否 1是
                        busiShippingorder.setContainerBoxamount(boxAmount);
                        if(StringUtils.isEmpty(boxAmountOld) && boxAmountInt!=containerNum){
                            isChangeCount = "1"; //提交托书，且箱量与询价箱量不等
                        }
                        if(StringUtils.isNotEmpty(boxAmountOld) && boxAmountInt!=boxAmountOldInt){
                            isChangeCount = "1"; //编辑托书，且箱量与托书原箱量不等
                        }
                        if("1".equals(isChangeCount)){ //箱量改变
                            //是否满足舱位(草稿提交，与询价箱量比较；托书编辑，与原箱量比较)
                            if(StringUtils.isNotEmpty(boxAmountOld)){
                                if(boxAmountInt>boxAmountOldInt){
                                    isAddCount = "1";
                                }
                            }else{
                                if(boxAmountInt>containerNum){
                                    isAddCount = "1";
                                }
                            }
                            if("1".equals(isAddCount)){
                                String classId = busiShippingorder.getClassId();
                                if(StringUtils.isNotEmpty(busiShippingorder.getContainerType())){
                                    Double boxAmountReal = Double.valueOf(boxAmountInt);
                                    if((busiShippingorder.getContainerType()).contains("20")){
                                        boxAmountReal = Double.valueOf(boxAmountInt)/2;
                                    }
                                    Double orderZgCount = busiShipOrderMapper.selectZgCount(classId);//查询班列已定的整柜数量
                                    Integer classZgCount = busiShipOrderMapper.selectClassZgCount(classId);//查询班列整柜总数
                                    Double maxCount = classZgCount - orderZgCount;
                                    if(maxCount<0 || maxCount<boxAmountReal){
                                        return 4;
                                    }
                                }
                            }
                            //价格
                            busiShippingorder.setShipThCost(commonService.zgPrice(inquiryInfo.getPickUpFees(),containerNum,boxAmount)); //提货费
                            busiShippingorder.setSitecost(commonService.zgPrice(inquiryInfo.getRailwayFees(),containerNum,boxAmount));//铁路运费
                            busiShippingorder.setReceiveShCost(commonService.zgPrice(inquiryInfo.getDeliveryFees(),containerNum,boxAmount)); //送货费
                            busiShippingorder.setPickUpBoxFee(commonService.zgBoxFee(inquiryInfo.getPickUpBoxFee(),containerNum,boxAmount));//提箱费
                            busiShippingorder.setReturnBoxFee(commonService.zgBoxFee(busiShippingorder.getReturnBoxFee(),containerNum,boxAmount));//还箱费
                        }
                    }
                }
                int resultgoods = 0;
                //特种箱推送箱管审核
                if("0".equals(isconsolidation)){  //整柜
                    String containerType = busiShippingorder.getContainerType();
                    if(StringUtils.isNotEmpty(containerType)){
                        Long totalturncount = orderinfo.getTotalturncount();
                        if("1".equals(operateType) && ("5".equals(isExamline)||"8".equals(isExamline)||"3".equals(isExamline)) && "0".equals(examlineN) && totalturncount==0){
                            //首次从草稿提交到待审核的特种箱托书，发送箱管审核
                            if(commonService.isSpecialBox(containerType)){  //是特种箱
                                busiShippingorder.setIsexamline("7");
                                ShippingOrder orderCheckInfo = new ShippingOrder();
                                BeanUtils.copyProperties(busiShippingorder,orderCheckInfo);
                                orderCheckInfo.setType("1");
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
                }
                //托书编号不为空 或者 托书编号为空的未审核(待审核、已审核未通过)订单记录修改信息
                BusiShippingorders orderbackup = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderid); //订单表原数据
                BusiShippingorderGoods goodsbackup = busiShippingorderGoodsService.selectBusiShippingorderGoodsByOrderId(orderid);//商品表原数据
                if(StringUtils.isNotNull(orderbackup) && StringUtils.isNotNull(goodsbackup)){
                    Map<String,String> editrecordMap = commonService.orderCompare(orderbackup,goodsbackup,busiShippingorder,busiShippingGoods);
                    String editRecord = editrecordMap.get("editrecord");//修改记录
                    //是否插入订舱公告
                    isAddRecord = editrecordMap.get("isAddRecord"); //是否需要插入订舱公告 0否 1是
                    //存入备份表
                    if(StringUtils.isNotEmpty(editRecord)){
                        //查询订单备份表是否有该订单
                        BusiShippingorders isOrderBackup = busiShippingorderBackupService.selectBusiShippingorderBackupById(orderid);
                        int saveorder;
                        if (StringUtils.isNotNull(isOrderBackup)) {  //已存在更新，未存在插入
                            saveorder = busiShippingorderBackupService.updateBusiShippingorderBackup(orderbackup);
                        }else{
                            saveorder = busiShippingorderBackupService.insertBusiShippingorderBackup(orderbackup);
                        }
                        //查询订单商品备份表是否有该订单
                        BusiShippingorderGoods isGoodsBackup = busiShippingorderGoodsBackupService.selectBusiShippingorderGoodsBackupByOrderId(orderid);
                        int savegoods;
                        if(StringUtils.isNotNull(isGoodsBackup)) {  //已存在更新，未存在插入
                            savegoods = busiShippingorderGoodsBackupService.updateBusiShippingorderGoodsBackup(goodsbackup);
                        }else{
                            savegoods = busiShippingorderGoodsBackupService.insertBusiShippingorderGoodsBackup(goodsbackup);
                        }
                    }
                    //更新托书表
                    busiShippingorder.setCreateuserid(busiShippingorder.getCreateuserid());
                    busiShippingorder.setCreateusername(busiShippingorder.getCreateusername());
                    if(operateType.equals("0")){  //审核时间
                        busiShippingorder.setExameTime(DateUtils.getNowDate());
                    }
                    //编辑
                    if("1".equals(operateType)){
                        busiShippingorder.setTjTime(DateUtils.getNowDate());
                        if(StringUtils.isNull(orderinfo.getExameTime()) && ("5".equals(isExamline)) && ("0".equals(examlineN))){
                            busiShippingorder.setTjFTime(DateUtils.getNowDate()); //首次提交托书时间
                        }
                    }
                    if(StringUtils.isNotEmpty(editRecord)){
                        busiShippingorder.setIsupdate("1");
                        busiShippingorder.setTjTime(DateUtils.getNowDate());
                    }
                    result = busiShippingorderMapper.updateBusiShippingorder(busiShippingorder);
                    //更新货物表
                    if(StringUtils.isNotEmpty(editRecord)){
                        String goodsHistoryEditrecord = goodsbackup.getGoodsHistoryEditrecord() + editRecord;
                        busiShippingGoods.setGoodsHistoryEditrecord(goodsHistoryEditrecord);
                    }
                    resultgoods = busiShippingorderGoodsService.updateBusiShippingorderGoods(busiShippingGoods);
                }else{
                    return 0;
                }
                if(result == 1 && resultgoods == 1){
                    if("0".equals(operateType)){
                        content = "托书审核";
                    }else{
                        content = "托书更改";
                    }
                    //如果审核且审核通过，生成配舱通知书
                    if(operateType.equals("0") && examlineN.equals("1")){
                        asyncService.createOrderNotice(orderid);
                    }
                    //如果审核且审核不通过，插入失败原因
                    if(operateType.equals("0") && examlineN.equals("2")){
                        ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                        orderExaminfo.setOrderId(orderid);
                        orderExaminfo.setExamDate(DateUtils.getNowDate());
                        orderExaminfo.setExamInfo(busiShippingorder.getExamInfo());
                        orderExaminfo.setCreateuserid(busiShippingorder.getCreateuserid());
                        orderExaminfo.setCreateusername(busiShippingorder.getCreateusername());
                        orderExaminfo.setExamType("2");  //插入为2，失败为1，成功为0
                        int resultexam = shippingorderExaminfoService.insertShippingorderExaminfo(orderExaminfo);
                    }
                    //编辑，推送消息队列
                    ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderid); //托书信息
                    if(StringUtils.isNotNull(orderInfoRabbmq)){
                        orderInfoRabbmq.setIsDelete("0");//删除标识 0否 1是
                        if(operateType.equals("0")){
                            if(examlineN.equals("1")){
                                orderInfoRabbmq.setMessageType("1"); //审核通过
                            }
                            if(examlineN.equals("2")){
                                orderInfoRabbmq.setMessageType("2"); //审核失败
                            }
                        }else{
                            orderInfoRabbmq.setMessageType("7"); //托书修改
                        }
                        //托书箱号信息
                        List<String> boxnum = busiShippingorderMapper.selectBoxNum(orderid);
                        if(boxnum.size()>0){
                            orderInfoRabbmq.setBoxNo(StringUtils.join(boxnum,","));
                        }
                        //托书业务部门信息
                        String deptcode = busiShippingorderMapper.selectDeptCode(orderid);
                        if(StringUtils.isNotEmpty(deptcode)){
                            orderInfoRabbmq.setOrderDeptName(deptcode);
                        }
                        String clientTjr = ""; //推荐人信息
                        if(StringUtils.isNotEmpty(orderInfoRabbmq.getClientTjr())){
                            clientTjr += orderInfoRabbmq.getClientTjr();
                            if(clientTjr.contains("/")){
                                clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                            }
                        }
                        //推荐人邮箱
                        String tjrId = orderInfoRabbmq.getClientTjrId();
                        if(StringUtils.isNotEmpty(tjrId)){
                            String tjrEmail = busiShippingorderMapper.selectOrderTjrEmail(tjrId);
                            if(StringUtils.isNotEmpty(tjrEmail)){
                                orderInfoRabbmq.setClientTjrEmail(tjrEmail);
                                clientTjr += "/";
                                clientTjr += tjrEmail;
                            }
                        }
                        //跟单邮箱
                        String merchandiserIds = orderInfoRabbmq.getOrderMerchandiserId();
                        if(StringUtils.isNotEmpty(merchandiserIds)){
                            String[] merchandiserId = merchandiserIds.trim().split("\\|");
                            List<String> merchandiserEmails = busiShippingorderMapper.selectOrderMerEmail(merchandiserId);
                            if(merchandiserEmails.size()>0){
                                orderInfoRabbmq.setOrderMerchandiserEmail(StringUtils.join(merchandiserEmails,","));
                                clientTjr += ";";
                                clientTjr += StringUtils.join(merchandiserEmails.toArray(),";");
                            }
                        }
                        orderInfoRabbmq.setClientTjr(clientTjr);
                        //货物规格
                        List<BookingInquiryGoodsDetails> goodsList = busiShippingorderMapper.selectGoodsInfo(orderInfoRabbmq.getInquiryRecordId());
                        if(goodsList.size()>0){
                            String goodsStandard = "";
                            String oneStandard = "";
                            for(BookingInquiryGoodsDetails goodsItem:goodsList){
                                String standard = goodsItem.getGoodsLength()+"*"+goodsItem.getGoodsWidth()+"*"+goodsItem.getGoodsHeight()+"*"+goodsItem.getGoodsAmount()+";";
                                String oneWeight = goodsItem.getGoodsWeight()+"*"+goodsItem.getGoodsAmount()+";";
                                goodsStandard += standard;
                                oneStandard += oneWeight;
                            }
                            goodsStandard = StringUtils.substring(goodsStandard,0,-1);
                            orderInfoRabbmq.setGoodsStandard(goodsStandard);
                            orderInfoRabbmq.setOneStandard(oneStandard);
                            //orderInfoRabbmq.setGoodsInfoDetail(commonService.goodsInfoDetail(orderInfoRabbmq.getInquiryRecordId()));
                        }
                        //修改记录
                        String editRecord = orderInfoRabbmq.getEditRecord();
                        if(StringUtils.isNotEmpty(editRecord)){
                            editRecord = StringUtils.substring(editRecord,4);
                            if(StringUtils.isNotEmpty(editRecord)){
                                editRecord = StringUtils.replace(editRecord,"<###>","<br/>");
                                editRecord = StringUtils.replace(editRecord,"<td>",";");
                            }
                            orderInfoRabbmq.setEditRecord(editRecord);
                        }
                        //体积重量
                        if(StringUtils.isEmpty(orderInfoRabbmq.getGoodsKgs())){orderInfoRabbmq.setGoodsKgs("0");}
                        if(StringUtils.isEmpty(orderInfoRabbmq.getGoodsCbm())){orderInfoRabbmq.setGoodsCbm("0");}
                        //口岸
                        if(StringUtils.isNotEmpty(orderInfoRabbmq.getLineName())){
                            String[] lineNameArr = (orderInfoRabbmq.getLineName()).split("-");
                            if(lineNameArr.length>2){
                                orderInfoRabbmq.setClassPort(lineNameArr[1]);
                            }
                        }
                        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                        MessageProperties header = new MessageProperties();
                        header.getHeaders().put("__TypeId__","Object");
                        ObjectMapper objectMapper = new ObjectMapper();
                        Message message = new Message(objectMapper.writeValueAsBytes(orderInfoRabbmq), header);
                        rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_DYNAMIC_TOPIC_EXCHANGE, "order.dynamic.check", message,correlationData);
                        log.info("托书推送消息队列:" + DateUtils.getNowDate() + "---" + orderid);
                        //更新货物跟踪表
                        if(!"2".equals(orderInfoRabbmq.getLineTypeid())&&!StringUtils.equals(orderinfo.getClassId(),orderInfoRabbmq.getClassId())){
                            TrackGoodsStatus trackGoodsStatus=new TrackGoodsStatus();
                            trackGoodsStatus.setOrderId(orderid);
                            trackGoodsStatus.setActualClassId(orderInfoRabbmq.getClassId());
                            trackGoodsStatusMapper.updateTgs(trackGoodsStatus);
                        }
                        //东向订单且委托zih提货邮件通知集疏部
                        String binningwayOld = orderinfo.getShipOrderBinningway();  //原托书
                        String binningway = busiShippingorder.getShipOrderBinningway();
                        if(classEastandwest.equals("1")&&(binningwayOld.equals("0")||binningway.equals("0"))){
                            if((!binningway.equals(binningwayOld))||("3".equals(orderinfo.getIsexamline())&&"0".equals(busiShippingorder.getIsexamline())&&"0".equals(binningwayOld))){
                                String[] sendEmails  = {"lihj@zih718.com","sunyk@zih718.com"};
                                //邮件内容
                                commonService.sendEmailJs(sendEmails,orderInfoRabbmq,binningwayOld);
                            }
                        }
                        //插入客户端消息表
                        if("0".equals(operateType)){  //0审核 1编辑
                            SysMessage sysmsg = new SysMessage();
                            sysmsg.setClientId(orderInfoRabbmq.getClientId());
                            sysmsg.setOrderNumber(orderInfoRabbmq.getOrderNumber());
                            sysmsg.setMessageTitle("托书审核进度更新");
                            sysmsg.setMessageType("审核");
                            if(examlineN.equals("1")){
                                sysmsg.setMessageContent("托书编号为"+orderInfoRabbmq.getOrderNumber()+"的托书已审核成功");
                            }
                            if(examlineN.equals("2")){
                                sysmsg.setMessageContent("您的托书审核失败");
                            }
                            sysmsg.setMsgStatus("0");
                            sysmsg.setCreateTime(DateUtils.getNowDate());
                            sysmsg.setDelFlag("0");
                            sysmsg.setOrderId(orderid);
                            messageService.insertMessage(sysmsg);
                        }
                    }
                    //插入订舱公告
                    if("1".equals(isAddRecord)){
                        //存入公告表
                        ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                        orderExaminfo.setOrderId(orderid);
                        orderExaminfo.setExamDate(DateUtils.getNowDate());
                        orderExaminfo.setExamInfo("舱位号/提货信息修改");
                        orderExaminfo.setCreateuserid(busiShippingorder.getCreateuserid());
                        orderExaminfo.setCreateusername(busiShippingorder.getCreateusername());
                        orderExaminfo.setExamType("2");  //插入为2，失败为1，成功为0
                        shippingorderExaminfoService.insertShippingorderExaminfo(orderExaminfo);
                    }else{
                        //托书审核时更新最新一条订舱公告审核时间
                        if("0".equals(operateType)){
                            ShippingorderExaminfo examInfoByOrder = shippingorderExaminfoMapper.selectShippingorderExaminfoByOrderId(orderid);
                            if(StringUtils.isNotNull(examInfoByOrder)){
                                if(StringUtils.isEmpty(examInfoByOrder.getEditRecord())){
                                    ShippingorderExaminfo examDate = new ShippingorderExaminfo();
                                    examDate.setExamId(examInfoByOrder.getExamId());
                                    examDate.setExamDate(DateUtils.getNowDate());
                                    shippingorderExaminfoMapper.addRemark(examDate);
                                }
                            }
                        }
                    }
                    //给客户、跟单、业务发送邮件提醒
                    if("0".equals(operateType)) {  //0审核 1编辑
                        asyncService.orderSendEmail(orderid, content);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 转待审核申请平台
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    @Override
    public int orderCheckApply(BusiShippingorders busiShippingorder) throws JsonProcessingException {
        int result = 0;
        String orderId = busiShippingorder.getOrderId();//订单id
        //更新订单表
        BusiShippingorders orderupd = new BusiShippingorders();
        orderupd.setOrderId(orderId);
        orderupd.setIsexamline("5");
        orderupd.setTurncount(busiShippingorder.getTurncount());
        orderupd.setTotalturncount(busiShippingorder.getTotalturncount());
        orderupd.setTotalturncountavg(busiShippingorder.getTotalturncountavg());
        orderupd.setDcGaidanState(busiShippingorder.getDcGaidanState());
        orderupd.setCreatedate(DateUtils.getNowDate());
        orderupd.setCreateuserid(busiShippingorder.getCreateuserid());
        orderupd.setCreateusername(busiShippingorder.getCreateusername());
        int resultorder = busiShippingorderMapper.orderCheckApply(orderupd);
        if(resultorder==1){
            result = 1;
            //存入公告表
            ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
            orderExaminfo.setOrderId(orderId);
            orderExaminfo.setExamDate(DateUtils.getNowDate());
            String examInfo = busiShippingorder.getExamType()+","+busiShippingorder.getExamInfo();
            orderExaminfo.setExamInfo(examInfo);
            orderExaminfo.setCreateuserid(busiShippingorder.getCreateuserid());
            orderExaminfo.setCreateusername(busiShippingorder.getCreateusername());
            orderExaminfo.setExamType("2");  //插入为2，失败为1，成功为0
            int resultexam = shippingorderExaminfoService.insertShippingorderExaminfo(orderExaminfo);
            //配舱通知书
            //docOrderService.deleteDocOrderByOrderId(orderId);
            String clientId = busiShippingorder.getClientId();
            //查询该客户三个月内转待总次数
            int totalexam = busiShippingorderMapper.selectTotalExam(clientId);
            //查询该客户三个月内订单总数
            int totalorder = busiShippingorderMapper.selectTotalOrder(clientId);
            //更新客户表
            DecimalFormat df = new DecimalFormat("0.00");
            String totalTurnCountAvg = "";
            if(totalexam!=0 && totalorder!=0){
                totalTurnCountAvg = df.format((float)totalexam/totalorder);
            }else{
                totalTurnCountAvg = "0";
            }
            BusiClients clientsObj = new BusiClients();
            clientsObj.setClientId(clientId);
            clientsObj.setTotalturncountavg(totalTurnCountAvg);
            int resultClient = busiClientsService.updateBusiClients(clientsObj);
            //推送消息队列
            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(busiShippingorder.getOrderId());
            if(StringUtils.isNotNull(orderInfoRabbmq)){
                orderInfoRabbmq.setIsDelete("0");//删除标识 0否 1是
                orderInfoRabbmq.setMessageType("3"); //转待审核中
                String messagetype = "3";
                commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
            }
            //给跟单、业务、客户发送邮件
            String content = "托书转待审核";
            asyncService.orderSendEmail(orderId,content);
        }else{
            return result;
        }
        return result;
    }

    /**
     * 转待审核申请（涉及询价）
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    @Override
    //@Transactional
    public int orderCheckApplyXj(BusiShippingorders busiShippingorder) throws JsonProcessingException {
        int result = 0; //托书更新状态
        try{
            String content = ""; //邮件发送内容
            String newsMsg = "";//消息表内容
            String orderId = busiShippingorder.getOrderId();//订单id
            //最新上下货站编号
            if(StringUtils.isNotEmpty(busiShippingorder.getOrderUploadsite())){
                busiShippingorder.setOrderUploadcode(busiSiteMapper.getCodeByName(busiShippingorder.getOrderUploadsite()));
            }
            if(StringUtils.isNotEmpty(busiShippingorder.getOrderUnloadsite())){
                busiShippingorder.setOrderUnloadcode(busiSiteMapper.getCodeByName(busiShippingorder.getOrderUnloadsite()));
            }
            String inquiryRecordId = busiShippingorder.getInquiryRecordId(); //询价结果id
            //判断状态
            BusiOrderTocheckInquiry checkResult = busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(orderId);
            if(StringUtils.isNotNull(checkResult)){
                int isXgCheck = checkResult.getXgExamine(); //是否需要箱管审核报价 0是 1否
                int isJsCheck = checkResult.getJsExamine(); //集疏审核
                int isXxyoCheck = checkResult.getXxyoExamine(); //箱行亚欧审核
                int isYwCheck = checkResult.getYwExamine(); //业务审核
                int isDcCheck = checkResult.getDcExamine();//订舱审核
                int isClientCheck = checkResult.getClientExamine(); //客户确认
                int newInquiry = checkResult.getNewInquiry(); //是否新询价 0是 1否
                //int isJsXxyoCheck = checkResult.getJsXxyoExamine() ; //提货时间
                BusiShippingorders orderinfoback = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId); //托书原数据
                BusiShippingorderGoods goodsinfoback = busiShippingorderGoodsService.selectBusiShippingorderGoodsByOrderId(orderId);//商品原数据
                if(StringUtils.isNotNull(orderinfoback) && StringUtils.isNotNull(goodsinfoback)){
                    String isexamline = "";
                    //托书基本信息
                    String binningwayOld = orderinfoback.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                    String binningway = busiShippingorder.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                    String classEastandwest = orderinfoback.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
                    //转待次数
                    Long turncount = orderinfoback.getTurncount();
                    Long totalturncount = orderinfoback.getTotalturncount();
                    Long totalturncountavg = StringUtils.isNotNull(orderinfoback.getTotalturncountavg())?orderinfoback.getTotalturncountavg():0;//订单的去程N-4 12:00之后，回程N-5 12:00之后转待审核次数
                    String isAddZd = commonService.isZdCount(orderinfoback.getClassDate(),classEastandwest); //是否增加一次 0否 1是
                    //获取商品信息
                    BusiShippingorderGoods busiShippingGoods = commonService.orderGoodsInfo(busiShippingorder);
                    //比较修改记录
                    busiShippingorder.setCreatedate(DateUtils.getNowDate());
                    Map<String,String> editrecordMap = commonService.orderCompare(orderinfoback,goodsinfoback,busiShippingorder,busiShippingGoods);
                    //int isPicktime = 1;
                    //if("0".equals(isJsXxyoCheck)){
                        //isPicktime = Integer.valueOf(editrecordMap.get("isPicktime")); //提货时间是否修改 0是 1否
                    //}
                    int isPicktime = Integer.valueOf(editrecordMap.get("isPicktime")); //提货时间是否需要审核 0是 1否
                    int isClassCheck = Integer.valueOf(editrecordMap.get("isClassCheck")); //订舱是否审核 0是 1否
                    int ismailChange = Integer.valueOf(editrecordMap.get("ismailChange")); //到站邮箱是否修改 0否 1是
                    String editRecord = editrecordMap.get("editrecord");//修改记录
                    //更新判断状态
                    String gwchResult = checkResult.getGwczResult();
                    if(StringUtils.isNotEmpty(gwchResult)){
                        gwchResult = StringUtils.substring(gwchResult,0,-1);
                        gwchResult += ismailChange;
                        checkResult.setGwczResult(gwchResult);
                    }
                    checkResult.setJsXxyoExamine(isPicktime);
                    checkResult.setDczExamine(isClassCheck);
                    busiOrderTocheckInquiryMapper.updateBusiOrderTocheckInquiry(checkResult);
                    //不需要子系统报价，不需要子系统审核，不需要订舱组审核
                    if(isXgCheck==1 && isJsCheck==1 && isXxyoCheck==1 && isYwCheck==1 && isDcCheck==1 &&isClientCheck==1 && isPicktime==1 && isClassCheck==1){
                        //直接审核通过
                        int resultorder = 0;
                        int resultgoods = 0;
                        //存入备份表
                        if(StringUtils.isNotEmpty(editRecord)){
                            //查询订单备份表是否有该订单
                            BusiShippingorders isOrderBackup = busiShippingorderBackupService.selectBusiShippingorderBackupById(orderId);
                            int saveorder;
                            if (StringUtils.isNotNull(isOrderBackup)) {  //已存在更新，未存在插入
                                saveorder = busiShippingorderBackupService.updateBusiShippingorderBackup(orderinfoback);
                            }else{
                                saveorder = busiShippingorderBackupService.insertBusiShippingorderBackup(orderinfoback);
                            }
                            //查询订单商品备份表是否有该订单
                            BusiShippingorderGoods isGoodsBackup = busiShippingorderGoodsBackupService.selectBusiShippingorderGoodsBackupByOrderId(orderId);
                            int savegoods;
                            if(StringUtils.isNotNull(isGoodsBackup)) {  //已存在更新，未存在插入
                                savegoods = busiShippingorderGoodsBackupService.updateBusiShippingorderGoodsBackup(goodsinfoback);
                            }else{
                                savegoods = busiShippingorderGoodsBackupService.insertBusiShippingorderGoodsBackup(goodsinfoback);
                            }
                        }
                        //更新托书表
                        busiShippingorder.setCreateuserid(busiShippingorder.getCreateuserid());
                        busiShippingorder.setCreateusername(busiShippingorder.getCreateusername());
                        busiShippingorder.setTjTime(DateUtils.getNowDate());
                        busiShippingorder.setTurncount(turncount+1);
                        busiShippingorder.setTotalturncount(totalturncount+1);
                        if("1".equals(isAddZd)){
                            busiShippingorder.setTotalturncountavg(totalturncountavg+1);
                        }
                        if(StringUtils.isNotEmpty(editRecord)){
                            busiShippingorder.setIsupdate("1");
                        }
                        busiShippingorder.setIsexamline("1");
                        //提还箱地、提还箱费用
                        if(StringUtils.isEmpty(busiShippingorder.getShipFhSite())){busiShippingorder.setShipFhSiteS("1");}
                        if(StringUtils.isEmpty(busiShippingorder.getShipHyd())){busiShippingorder.setShipHydS("1");}
                        if(StringUtils.isEmpty(busiShippingorder.getReceiveHxAddress())){busiShippingorder.setReceiveHxAddressS("1");}
                        if(StringUtils.isEmpty(busiShippingorder.getPickUpBoxFee())){busiShippingorder.setPickUpBoxFeeS("1");}
                        if(StringUtils.isEmpty(busiShippingorder.getReturnBoxFee())){busiShippingorder.setReturnBoxFeeS("1");}
                        if(StringUtils.isEmpty(busiShippingorder.getGoodsGeneral())){busiShippingorder.setGoodsGeneralS("1");}
                        if(StringUtils.isEmpty(busiShippingorder.getEconsignorstate())){busiShippingorder.setEconsignorstateS("1");}
                        busiShippingorder.setClientTjr(orderinfoback.getClientTjr());
                        resultorder = busiShippingorderMapper.updateBusiShippingorder(busiShippingorder);
                        //更新商品表
                        if(StringUtils.isNotEmpty(editRecord)){
                            String goodsHistoryEditrecord = goodsinfoback.getGoodsHistoryEditrecord() + editRecord;
                            busiShippingGoods.setGoodsHistoryEditrecord(goodsHistoryEditrecord);
                        }
                        if(StringUtils.isEmpty(busiShippingorder.getRemark())){busiShippingGoods.setRemarkS("1");}
                        if(StringUtils.isEmpty(busiShippingorder.getGoodsbz())){busiShippingGoods.setGoodsbzS("1");}
                        if(StringUtils.isEmpty(busiShippingorder.getHsbz())){busiShippingGoods.setHsbzS("1");}
                        resultgoods = busiShippingorderGoodsService.updateBusiShippingorderGoods(busiShippingGoods);
                        if(resultorder==1 && resultgoods ==1){
                            result = 1;
                            isexamline = "1";
                            //newsMsg = "转待审核申请已通过";
                            int inquryupd = updateOrderByInquiry(inquiryRecordId,orderId,classEastandwest);
                            //生成配舱通知书
                            asyncService.createOrderNotice(orderId);
                            content = "托书转待审核";
                            //东向委托陆港提货托书给集疏发送邮件
                            if(classEastandwest.equals("1")&&(binningwayOld.equals("0")||binningway.equals("0"))){
                                if(!binningway.equals(binningwayOld)){
                                    String[] sendEmails  = {"lihj@zih718.com","sunyk@zih718.com"};
                                    ShippingOrder orderJs = new ShippingOrder();
                                    BeanUtils.copyProperties(busiShippingorder,orderJs);
                                    commonService.sendEmailJs(sendEmails,orderJs,binningwayOld);
                                }
                            }
                        }
                    }else{
                        if(isXgCheck==0){
                            isexamline = "7";  //箱管部未审核
                        }else if(isXxyoCheck==0 || isJsCheck==0 || isYwCheck==0){
                            isexamline = "9"; //报价中
                        }else if(isClientCheck==0){
                            isexamline = "10"; //客户确认中
                        }else if(isPicktime==0){
                            if("0".equals(classEastandwest)){
                                isexamline = "11"; //去程公路审核提货时间
                            }else{
                                isexamline = "12"; //回程集疏审核提货时间
                            }
                        }else{
                            isexamline = "4"; //订舱审核
                        }
                        //更新订单表
                        BusiShippingorders orderupd = new BusiShippingorders();
                        orderupd.setOrderId(busiShippingorder.getOrderId());
                        orderupd.setIsexamline(busiShippingorder.getIsexamline());
                        orderupd.setIsexamline(isexamline);
                        orderupd.setTurncount(turncount+1);
                        orderupd.setTotalturncount(totalturncount+1);
                        if("1".equals(isAddZd)){
                            orderupd.setTotalturncountavg(totalturncountavg+1);
                        }
                        orderupd.setCreateuserid(busiShippingorder.getCreateuserid());
                        orderupd.setCreateusername(busiShippingorder.getCreateusername());
                        orderupd.setTjTime(DateUtils.getNowDate());
                        int resultorder = busiShippingorderMapper.orderCheckApply(orderupd);
                        if(resultorder==1){
                            //插入订单、订单商品暂存表
                            busiShippingorder.setClientTjr(orderinfoback.getClientTjr());
                            busiShippingorder.setTjTime(DateUtils.getNowDate());
                            int orderTemAdd = busiShippingorderApplyService.insertBusiShippingorderApply(busiShippingorder);
                            int goodsTemAdd = busiShippingorderGoodsApplyService.insertBusiShippingorderGoodsApply(busiShippingGoods);
                            if(orderTemAdd==1 && goodsTemAdd==1){
                                result =1;
                                //推送消息队列审核提货时间
                                if(isXgCheck==1 && isJsCheck==1 && isXxyoCheck==1 && isYwCheck==1 && isClientCheck==1 && isPicktime == 0){
                                    pickTimeCheckApply(orderId);
                                }
                                //删除配舱通知书
                                docOrderService.deleteDocOrderByOrderId(orderId);
                                content = "托书转待审核";
                            }else{
                                commonService.deleteTem(orderId);//删除暂存表信息，可以再次提交
                            }
                        }
                    }
                    if(result==1){
                        //插入订舱公告表
                        ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                        orderExaminfo.setOrderId(orderId);
                        orderExaminfo.setExamDate(DateUtils.getNowDate());
                        orderExaminfo.setExamInfo(busiShippingorder.getExamInfo());
                        orderExaminfo.setEditRecord(editRecord);
                        orderExaminfo.setCreateuserid(busiShippingorder.getCreateuserid());
                        orderExaminfo.setCreateusername(busiShippingorder.getCreateusername());
                        orderExaminfo.setExamType("2");  //插入为2，失败为1，成功为0
                        int resultexam = shippingorderExaminfoService.insertShippingorderExaminfo(orderExaminfo);
                        //子系统发送消息
                        toCheckNotice.noticeSonSystem(orderId, isexamline, "客户申请转待审核");
                        //给跟单、业务发送邮件提醒
                        asyncService.orderSendEmailIn(orderId,content);
                        //更新客户表三月内平均转待次数
                        String clientId = orderinfoback.getClientId();
                        int totalexam = busiShippingorderMapper.selectTotalExam(clientId); //查询该客户三个月内转待总次数
                        int totalorder = busiShippingorderMapper.selectTotalOrder(clientId); //查询该客户三个月内订单总数
                        DecimalFormat df = new DecimalFormat("0.00");  //计算平均转待次数
                        String totalTurnCountAvg = "";
                        if(totalexam!=0 && totalorder!=0){
                            totalTurnCountAvg = df.format((float)totalexam/totalorder);
                        }else{
                            totalTurnCountAvg = "0";
                        }
                        BusiClients clientsObj = new BusiClients();
                        clientsObj.setClientId(clientId);
                        clientsObj.setTotalturncountavg(totalTurnCountAvg);
                        int resultClient = busiClientsService.updateBusiClients(clientsObj); //更新客户表
                        //推送消息队列
                        ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(busiShippingorder.getOrderId());
                        if(StringUtils.isNotNull(orderInfoRabbmq)){
                            String messagetype = "3"; //转待审核申请
                            commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                        }
                    }
                }
            }
        }catch (RuntimeException e){
            log.error("客户端转待审核申请失败",e.toString(),e.getStackTrace());
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 更新转待审核次数
     */
    @Override
    public void updateTurnCount(){
        busiShippingorderMapper.updateTurnCount();
    }

    /**
     * 转待审核判断提货时间
     */
    public int picktimeCheck(BusiShippingorders busiShippingorder){
        int result = 1; //提货时间是否满足最短时间要求 0满足 1不满足
        String bookingService = busiShippingorder.getBookingService(); //服务 0：门到门；1：门到站；2：站到站；3：站到门
        if("0".equals(bookingService) || "1".equals(bookingService)){
            int jxcheck = 1; //是否还需要集疏公路审核提货时间 0需要 1不需要
            String orderId = busiShippingorder.getOrderId(); //托书id
            String isconsolidation = busiShippingorder.getIsconsolidation(); //0整柜 1拼箱
            Date pickTime  = busiShippingorder.getShipOrderUnloadtime(); //提货日期
            String eastAndWest = busiShippingorder.getClassEastandwest(); //去回程 0去程 1回程
            String pickAddress = busiShippingorder.getCountry(); //提货地址
            String uploadsite = busiShippingorder.getOrderUploadsite();  //上货站
            String checkType = ""; //提货方 0公路 1集疏
            if("0".equals(eastAndWest)){
                checkType = "0";
            }
            if("1".equals(eastAndWest)){
                checkType = "1";
            }
            JsBackTimeset timeset = jsBackTimesetService.selectByStationPickCountry(uploadsite, pickAddress);
            if(StringUtils.isNotNull(timeset)){
                if("0".equals(eastAndWest)){  //公路

                }
                if("1".equals(eastAndWest)){ //集疏
                    Integer minTime = 0;
                    if("0".equals(isconsolidation)){
                        minTime = timeset.getFcl(); //整柜
                    }else{
                        minTime = timeset.getLcl(); //拼箱
                    }
                    if (isEnoughTime(pickTime, minTime)) {  //时间差大于最短时间
                        result = 0;
                    }
                }
            }else{
                result = 0;
                jxcheck = 0; //地址所需时间未维护，需要子系统审核
            }
            //更新审核状态表
            BusiOrderTocheckInquiry checkResult = busiOrderTocheckInquiryMapper.selectTocheckInquiryByOrderId(orderId);
            checkResult.setJsXxyoExamine(jxcheck);
            busiOrderTocheckInquiryMapper.updateBusiOrderTocheckInquiry(checkResult);
        }else{
            result = 0;
        }
        return  result;
    }

    public boolean isEnoughTime(Date date, int days) {
        Long times = DateUtils.getDatePoorLong(date, new Date());
        return times > days * 24 * 3600 * 1000;
    }

    /**
     * 提货时间发送集疏公路审核
     */
    @Override
    public void pickTimeCheckApply(String orderId) throws JsonProcessingException{
        ShippingOrder orderCheckInfo = busiShippingorderMapper.selectBusiShippingorderTemById(orderId);
        Long inquiryRecordId = busiShippingorderMapper.selectInquiryResultIdByOrderId(orderId);//询价结果id
        String classEastandwest = orderCheckInfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
        if("0".equals(classEastandwest)){
            orderCheckInfo.setPicktimeCheck("1");//公路部审核
        }
        if("1".equals(classEastandwest)){
            orderCheckInfo.setPicktimeCheck("0");//集疏部审核
        }
        //货物规格
        if(StringUtils.isNotNull(inquiryRecordId)){
            List<BookingInquiryGoodsDetails> goodsList = busiShippingorderMapper.selectGoodsInfo(String.valueOf(inquiryRecordId));
            if(goodsList.size()>0){
                String goodsStandard = "";
                for(BookingInquiryGoodsDetails goodsItem:goodsList){
                    String standard = goodsItem.getGoodsLength()+"*"+goodsItem.getGoodsWidth()+"*"+goodsItem.getGoodsHeight()+"*"+goodsItem.getGoodsAmount()+";";
                    goodsStandard += standard;
                }
                goodsStandard = StringUtils.substring(goodsStandard,0,-1);
                orderCheckInfo.setGoodsStandard(goodsStandard);
                orderCheckInfo.setGoodsInfoDetail(commonService.goodsInfoDetail(String.valueOf(inquiryRecordId)));
            }
        }
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessageProperties header = new MessageProperties();
        header.getHeaders().put("__TypeId__","Object");
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = new Message(objectMapper.writeValueAsBytes(orderCheckInfo), header);
        rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_PICKTIME_TOPIC_EXCHANGE, "order.picktime.check", message,correlationData);
    }

    /**
     * 更新托书暂存信息
     */
    //@Transactional
    @Override
    public int updateOrderTem(String orderId,String dcGaidanState,String orderNumberNew,String stationidNew){
        int result = 0;
        String content = "";
        try{
            //查询修改记录
            ShippingorderExaminfo orderExam = shippingorderExaminfoService.selectShippingorderExaminfoByOrderId(orderId);
            BusiShippingorders orderinfoback = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId); //订单表原数据
            BusiShippingorderGoods goodsinfoback = busiShippingorderGoodsService.selectBusiShippingorderGoodsByOrderId(orderId);//商品表原数据
            if(StringUtils.isNotNull(orderinfoback) && StringUtils.isNotNull(goodsinfoback)){
                String classEastandwest = orderinfoback.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
                //判断是否修改托书编号 0未修改 1已修改
                String isChangeNumber = "0";
                if(StringUtils.isNotEmpty(orderNumberNew)){
                    if(!StringUtils.equals(orderNumberNew,orderinfoback.getOrderNumber())){
                        isChangeNumber = "1";
                    }
                }
                //判断提货、送货报价编号是否修改
                String inquiryRecordId = orderinfoback.getInquiryRecordId();
                String inquiryNumRecord = "";
                inquiryResultZx inquiryNumInfo = busiShipOrderMapper.inquiryNum(inquiryRecordId);
                if(StringUtils.isNotNull(inquiryNumInfo)){
                    if(!StringUtils.equals(inquiryNumInfo.getShipThCostNo(),orderinfoback.getShipThCostNo()) && !(StringUtils.isBlank(inquiryNumInfo.getShipThCostNo()) && StringUtils.isBlank(orderinfoback.getShipThCostNo()))){
                        inquiryNumRecord = "提货报价编号由"+orderinfoback.getShipThCostNo()+"修改为:"+inquiryNumInfo.getShipThCostNo()+"<td>";
                    }
                    if(!StringUtils.equals(inquiryNumInfo.getReceiveShCostId(),orderinfoback.getReceiveShCostId()) && !(StringUtils.isBlank(inquiryNumInfo.getReceiveShCostId()) && StringUtils.isBlank(orderinfoback.getReceiveShCostId()))){
                        inquiryNumRecord = inquiryNumRecord + "送货报价编号由"+orderinfoback.getReceiveShCostId()+"修改为:"+inquiryNumInfo.getReceiveShCostId()+"<td>";
                    }
//                    if(!StringUtils.equals(inquiryNumInfo.getDomesticNumber(),orderinfoback.getDomesticNumber()) && !(StringUtils.isBlank(inquiryNumInfo.getDomesticNumber()) && StringUtils.isBlank(orderinfoback.getDomesticNumber()))){
//                        inquiryNumRecord = inquiryNumRecord + "国内拼箱场站报价单号由"+orderinfoback.getDomesticNumber()+"修改为:"+inquiryNumInfo.getDomesticNumber()+"<td>";
//                    }
//                    if(!StringUtils.equals(inquiryNumInfo.getForeignNumber(),orderinfoback.getForeignNumber()) && !(StringUtils.isBlank(inquiryNumInfo.getForeignNumber()) && StringUtils.isBlank(orderinfoback.getForeignNumber()))){
//                        inquiryNumRecord = inquiryNumRecord + "国外拼箱场站报价单号由"+orderinfoback.getForeignNumber()+"修改为:"+inquiryNumInfo.getForeignNumber()+"<td>";
//                    }
                }
                //判断堆场地址是否修改
                String stationRecord = "";
                if(StringUtils.isNotEmpty(stationidNew)){
                    if(!StringUtils.equals(stationidNew,orderinfoback.getStationid())){
                        BusiStation stationInfoBack = busiStationService.selectBusiStationById(orderinfoback.getStationid()); //旧id查询
                        BusiStation stationInfo = busiStationService.selectBusiStationById(stationidNew); //新id查询
                        String stationold = StringUtils.isNotNull(stationInfoBack)?stationInfoBack.getStatioin():"空";
                        String station = StringUtils.isNotNull(stationInfo)?stationInfo.getStatioin():"空";
                        stationRecord = "车站/堆场地址由"+stationold+"修改为:"+station+"<td>";
                    }
                }
                //修改记录
                String editRecord ="";
                if(StringUtils.isNotNull(orderExam)){
                    editRecord = orderExam.getEditRecord();
                }
                //存入备份表
                if(StringUtils.isNotEmpty(editRecord)){
                    //查询订单备份表是否有该订单
                    BusiShippingorders isOrderBackup = busiShippingorderBackupService.selectBusiShippingorderBackupById(orderId);
                    int saveorder;
                    if (StringUtils.isNotNull(isOrderBackup)) {  //已存在更新，未存在插入
                        saveorder = busiShippingorderBackupService.updateBusiShippingorderBackup(orderinfoback);
                    }else{
                        saveorder = busiShippingorderBackupService.insertBusiShippingorderBackup(orderinfoback);
                    }
                    //查询订单商品备份表是否有该订单
                    BusiShippingorderGoods isGoodsBackup = busiShippingorderGoodsBackupService.selectBusiShippingorderGoodsBackupByOrderId(orderId);
                    int savegoods;
                    if(StringUtils.isNotNull(isGoodsBackup)) {  //已存在更新，未存在插入
                        savegoods = busiShippingorderGoodsBackupService.updateBusiShippingorderGoodsBackup(goodsinfoback);
                    }else{
                        savegoods = busiShippingorderGoodsBackupService.insertBusiShippingorderGoodsBackup(goodsinfoback);
                    }
                }
                //暂存表内容更新到订单表
                BusiShippingorders orderTem = busiShippingorderApplyService.selectBusiShippingorderApplyById(orderId);  //订单表暂存数据
                if(StringUtils.isNotNull(orderTem)){
                    //获取询价最新上下货站、提还箱地、提还箱费
                    String inqueryRecord = "";
                    if(StringUtils.isNotNull(inquiryNumInfo)){
                        String bookingService = orderTem.getBookingService(); //0门到门 1门到站 2站到站 3站到门
                        if("0".equals(classEastandwest)){
                            //提箱地、提箱费
                            if("0".equals(bookingService) || "1".equals(bookingService)){
                                if(!StringUtils.equals(inquiryNumInfo.getTxAddress(),orderinfoback.getShipFhSite()) && !(StringUtils.isBlank(inquiryNumInfo.getTxAddress()) && StringUtils.isBlank(orderinfoback.getShipFhSite()))){
                                    inqueryRecord = "提箱地由"+orderinfoback.getShipFhSite()+"修改为:"+inquiryNumInfo.getTxAddress()+"<td>";
                                }
                                orderTem.setShipFhSite(inquiryNumInfo.getTxAddress());
                                orderTem.setPickUpBoxFee(inquiryNumInfo.getPickUpBoxFee());
                            }
                            //下货站
                            if(StringUtils.isNotEmpty(inquiryNumInfo.getDropStation())){
                                if(!StringUtils.equals(inquiryNumInfo.getDropStation(),orderinfoback.getOrderUnloadsite())){
                                    inqueryRecord = "下货站由"+orderinfoback.getOrderUnloadsite()+"修改为:"+inquiryNumInfo.getDropStation()+"<td>";
                                }
                                orderTem.setOrderUnloadcode(busiSiteMapper.getCodeByName(inquiryNumInfo.getDropStation()));
                                orderTem.setOrderUnloadsite(inquiryNumInfo.getDropStation());
                            }
                        }
                        if("1".equals(classEastandwest)){
                            //提箱地、提箱费
                            if("0".equals(bookingService) || "1".equals(bookingService)){
                                if(!StringUtils.equals(inquiryNumInfo.getTxAddress(),orderinfoback.getShipHyd()) && !(StringUtils.isBlank(inquiryNumInfo.getTxAddress()) && StringUtils.isBlank(orderinfoback.getShipHyd()))){
                                    inqueryRecord = "提箱地由"+orderinfoback.getShipHyd()+"修改为:"+inquiryNumInfo.getTxAddress()+"<td>";
                                }
                                orderTem.setShipHyd(inquiryNumInfo.getTxAddress());
                                orderTem.setPickUpBoxFee(inquiryNumInfo.getPickUpBoxFee());
                            }
                            //上货站
                            if(StringUtils.isNotEmpty(inquiryNumInfo.getUploadStation())){
                                if(!StringUtils.equals(inquiryNumInfo.getUploadStation(),orderinfoback.getOrderUploadsite())){
                                    inqueryRecord = "上货站由"+orderinfoback.getOrderUploadsite()+"修改为:"+inquiryNumInfo.getUploadStation()+"<td>";
                                }
                                orderTem.setOrderUploadcode(busiSiteMapper.getCodeByName(inquiryNumInfo.getUploadStation()));
                                orderTem.setOrderUploadsite(inquiryNumInfo.getUploadStation());
                            }
                        }
                        //还箱地、还箱费(去程站到门、门到门)
                        if("0".equals(bookingService) || "3".equals(bookingService)){
                            if(!StringUtils.equals(inquiryNumInfo.getHxAddress(),orderinfoback.getReceiveHxAddress()) && !(StringUtils.isBlank(inquiryNumInfo.getHxAddress()) && StringUtils.isBlank(orderinfoback.getReceiveHxAddress()))){
                                inqueryRecord = "还箱地由"+orderinfoback.getReceiveHxAddress()+"修改为:"+inquiryNumInfo.getHxAddress()+"<td>";
                            }
                            orderTem.setReceiveHxAddress(inquiryNumInfo.getHxAddress());
                            orderTem.setReturnBoxFee(inquiryNumInfo.getReturnBoxFee());
                        }
                    }
                    String binningwayOld = orderinfoback.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                    String binningway = orderTem.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                    orderTem.setCreatedate(DateUtils.getNowDate());
                    orderTem.setTjTime(DateUtils.getNowDate());
                    orderTem.setIsexamline("1");
                    if(StringUtils.isNotEmpty(editRecord)||StringUtils.isNotEmpty(inquiryNumRecord)||StringUtils.isNotEmpty(stationRecord)||StringUtils.isNotEmpty(inqueryRecord)){
                        orderTem.setIsupdate("1");
                    }
                    orderTem.setExameTime(DateUtils.getNowDate());
                    orderTem.setDcGaidanState(dcGaidanState);
                    if("1".equals(isChangeNumber)){
                        orderTem.setOrderNumber(orderNumberNew.trim());
                    }
                    if(StringUtils.isNotEmpty(stationRecord)){
                        orderTem.setStationid(stationidNew);
                    }
                    //提还箱地、提还箱费用
                    if(StringUtils.isEmpty(orderTem.getShipFhSite())){orderTem.setShipFhSiteS("1");}
                    if(StringUtils.isEmpty(orderTem.getShipHyd())){orderTem.setShipHydS("1");}
                    if(StringUtils.isEmpty(orderTem.getReceiveHxAddress())){orderTem.setReceiveHxAddressS("1");}
                    if(StringUtils.isEmpty(orderTem.getPickUpBoxFee())){orderTem.setPickUpBoxFeeS("1");}
                    if(StringUtils.isEmpty(orderTem.getReturnBoxFee())){orderTem.setReturnBoxFeeS("1");}
                    if(StringUtils.isEmpty(orderTem.getGoodsGeneral())){orderTem.setGoodsGeneralS("1");}
                    if(StringUtils.isEmpty(orderTem.getEconsignorstate())){orderTem.setEconsignorstateS("1");}
                    //转待次数
                    orderTem.setTurncount(orderinfoback.getTurncount());
                    orderTem.setTotalturncount(orderinfoback.getTotalturncount());
                    orderTem.setTotalturncountavg(orderinfoback.getTotalturncountavg());
                    int resultorder = busiShippingorderMapper.updateBusiShippingorder(orderTem);
                    //暂存表内容更新到商品表
                    BusiShippingorderGoods goodsTem = busiShippingorderGoodsApplyService.selectBusiShippingorderGoodsApplyByOrderId(orderId); //商品暂存表数据
                    if(StringUtils.isNotEmpty(editRecord)){
                        //拼接舱位号修改记录
                        if("1".equals(isChangeNumber)){
                            String numberRecord = "托书编号由"+orderinfoback.getOrderNumber()+"修改为:"+orderNumberNew+"<td>";
                            editRecord = StringUtils.substring(editRecord,0,-5); //截掉末尾分隔符
                            editRecord = editRecord+numberRecord+"<###>";
                        }
                        //拼接报价编号修改记录
                        if(StringUtils.isNotEmpty(inquiryNumRecord)){
                            editRecord = StringUtils.substring(editRecord,0,-5); //截掉末尾分隔符
                            editRecord = editRecord+inquiryNumRecord+"<###>";
                        }
                        //拼接回程场站修改记录
                        if(StringUtils.isNotEmpty(stationRecord)){
                            editRecord = StringUtils.substring(editRecord,0,-5); //截掉末尾分隔符
                            editRecord = editRecord+stationRecord+"<###>";
                        }
                        //拼接最新询价信息修改记录
                        if(StringUtils.isNotEmpty(inqueryRecord)){
                            editRecord = StringUtils.substring(editRecord,0,-5); //截掉末尾分隔符
                            editRecord = editRecord+inqueryRecord+"<###>";
                        }
                        editRecord = StringUtils.replace(editRecord,"null","空");
                        String goodsHistoryEditrecord = goodsinfoback.getGoodsHistoryEditrecord()+editRecord;
                        goodsTem.setGoodsHistoryEditrecord(goodsHistoryEditrecord);
                    }
                    if(StringUtils.isEmpty(goodsTem.getRemark())){goodsTem.setRemarkS("1");}
                    if(StringUtils.isEmpty(goodsTem.getGoodsbz())){goodsTem.setGoodsbzS("1");}
                    if(StringUtils.isEmpty(goodsTem.getHsbz())){goodsTem.setHsbzS("1");}
                    int resultgoods = busiShippingorderGoodsService.updateBusiShippingorderGoods(goodsTem);
                    if(resultorder==1){
                        result =1;
                        //content = "您编号为："+orderTem.getOrderNumber()+"的托书转待审核按申请成功，并完成更新";
                        content = "托书转待审核完成";
                        //更新询价结果
                        int inquryupd = updateOrderByInquiry(inquiryRecordId,orderId,classEastandwest);
                        //生成配舱通知书
                        asyncService.createOrderNotice(orderId);
                        //删除暂存表信息，可以再次提交
                        commonService.deleteTem(orderId);
                        //更新订舱公告表
                        ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                        orderExaminfo.setOrderId(orderId);
                        orderExaminfo.setExamType("0");
                        orderExaminfo.setExamDate(DateUtils.getNowDate());
                        int resultexam = shippingorderExaminfoService.updateShippingorderExaminfo(orderExaminfo);
                        //推送消息队列
                        ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                        if(StringUtils.isNotNull(orderInfoRabbmq)){
                            String messagetype = "4";
                            commonService.orderInfoMQ(orderInfoRabbmq,messagetype);
                            //东向委托陆港提货托书给集疏发送邮件
                            if(classEastandwest.equals("1")&&(binningwayOld.equals("0")||binningway.equals("0"))){
                                if(!binningway.equals(binningwayOld)){
                                    String[] sendEmails  = {"lihj@zih718.com","sunyk@zih718.com"};
                                    commonService.sendEmailJs(sendEmails,orderInfoRabbmq,binningwayOld);
                                }
                            }
                            //插入客户端消息表
                            SysMessage sysmsg = new SysMessage();
                            sysmsg.setClientId(orderInfoRabbmq.getClientId());
                            sysmsg.setOrderNumber(orderInfoRabbmq.getOrderNumber());
                            sysmsg.setMessageTitle("托书转待审核进度更新");
                            sysmsg.setMessageType("转待审核");
                            sysmsg.setMessageContent("托书编号为"+orderInfoRabbmq.getOrderNumber()+"的托书已通过转待审核申请");
                            sysmsg.setMsgStatus("0");
                            sysmsg.setCreateTime(DateUtils.getNowDate());
                            sysmsg.setDelFlag("0");
                            sysmsg.setOrderId(orderId);
                            messageService.insertMessage(sysmsg);
                        }
                        //给跟单、业务、客户发送邮件
                        asyncService.orderSendEmail(orderId,content);
                    }
                }
            }
        }catch (Exception e){
            log.error("托书暂存信息更新失败：{}",e.toString(),e.getStackTrace());
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 根据询价结果更新暂存的托书信息
     */
    @Override
    public int updateOrderByInquiry(String inquiryRecordId, String orderId, String classEastandwest) {
        int result = 0;
        BookingInquiryResult inquiryInfo = busiShippingorderMapper.selectInquiryRecordInfo(inquiryRecordId);
        if (StringUtils.isNotNull(inquiryInfo)) {
            BusiShippingorders orderInfo = commonService.orderInfoByInquiry(inquiryInfo, classEastandwest);
            orderInfo.setOrderId(orderId);
            result = busiShippingorderMapper.updateorderInquiry(orderInfo);
        }
        return result;
    }

    /**
     * 更新公告表(驳回)
     */
    @Override
    public int updateOrderExam(String orderId, String refuseInfo) {
        ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
        orderExaminfo.setOrderId(orderId);
        orderExaminfo.setExamType("1");
        if(StringUtils.isNotEmpty(refuseInfo)){
            orderExaminfo.setIsread("0");
        }
        orderExaminfo.setRefuseInfo(refuseInfo + " ");
        int resultexam = shippingorderExaminfoService.updateShippingorderExaminfo(orderExaminfo);
        return resultexam;
    }

    /**
     * 更新公告表(箱管驳回 )
     */
    @Override
    public int updateOrderExamXg(String orderId,String refuseInfo){
        ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
        orderExaminfo.setOrderId(orderId);
        orderExaminfo.setExamType("1");
        orderExaminfo.setIsread("0");
        orderExaminfo.setXgRefuseInfo(refuseInfo);
        int resultexam = shippingorderExaminfoService.updateShippingorderExaminfo(orderExaminfo);
        return resultexam;
    }

    /**
     * 转待审核订单列表/撤舱审核订单列表
     *
     * @param checkorder 订单
     * @return 订单
     */
    @Override
    public List<CheckOrderList> selectCheckOrderList(CheckOrderList checkorder){
        String deptCode = checkorder.getDeptCode();  //登录者部门编号
        String organizationCode = checkorder.getOrganizationCode();  //筛选条件部门编号
        String readType = null;
        if(StringUtils.isEmpty(organizationCode)){
            readType = "1"; //部门查询条件为空，全查
        }else{
            readType = "2"; //部门查询条件不为空，按条件查询
        }
        if(deptCode.contains("YWB")){  //业务部账号登录
            readType = "3"; //默认按照自己部门编号模糊查询
            if(deptCode.equals("YWB1000100")){  //业务部职级人员，根据规则查询
                readType = "4";
            }
            if(deptCode.equals("YWB1000101")){  //业务部职级人员，根据规则查询
                readType = "5";
            }
            if(deptCode.equals("YWB1000200")){  //业务部职级人员，根据规则查询
                readType = "6";
            }
            if(deptCode.equals("YWB1000201")){  //业务部职级人员，根据规则查询
                readType = "7";
            }
            if(deptCode.length()>15){  //部门编号大于15位
                readType = "8";   //业务部普通人员，根据自己推荐人id查询
            }
        }
        checkorder.setReadType(readType);
        return busiShippingorderMapper.selectCheckOrderList(checkorder);
    }

    /**
     * 转待审核操作（涉及询价）
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    @Override
    //@Transactional
    public int orderCheckReplyXj(BusiShippingorders busiShippingorder) throws JsonProcessingException{
        int result = 0;
        String content="";
        try{
            //0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败 7询价中
            String orderId = busiShippingorder.getOrderId(); //订单id
            String isExamline = busiShippingorder.getIsexamline();
            String examInfo = busiShippingorder.getExamInfo();//审核意见
            String orderNumberNew = busiShippingorder.getOrderNumberNew(); //修改后的托书编号
            String dcGaidanState = busiShippingorder.getDcGaidanState(); //改单费
            String stationidNew = busiShippingorder.getStationidNew(); //回程场站地址
            BusiShippingorders orderinfoback = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId); //订单原表数据
            if(StringUtils.isNotNull(orderinfoback)){
                //托书基本信息
                String classEastandwest = orderinfoback.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
                String isconsolidation = orderinfoback.getIsconsolidation(); //0整柜 1拼箱
                String binningwayOld = orderinfoback.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                BusiShippingorders orderTem = busiShippingorderApplyService.selectBusiShippingorderApplyById(orderId);  //订单表暂存数据
                String binningway = "";
                //同意转待审核
                if(isExamline.equals("1")){
                    //1.同意修改，更新托书信息，状态：1审核通过
                    if(StringUtils.isNotNull(orderTem)){
                        binningway = orderTem.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                        result = updateOrderTem(orderId,dcGaidanState,orderNumberNew,stationidNew);
                        if(result == 1){
                            //子系统推送
                            toCheckNotice.noticeSonSystem(orderId, "1", "订舱组同意转待审核申请");
                        }
                    }else{
                        return 0;
                    }
                }
                //不同意转待审核
                if(isExamline.equals("3")){
                    //3.不同意修改，更新托书状态，状态:6转待审核失败
                    //托书状态更新
                    BusiShippingorders orderCancelInfo = new BusiShippingorders();
                    orderCancelInfo.setOrderId(orderId);
                    orderCancelInfo.setCreatedate(DateUtils.getNowDate());
                    orderCancelInfo.setCreateuserid(busiShippingorder.getCreateuserid());
                    orderCancelInfo.setCreateusername(busiShippingorder.getCreateusername());
                    orderCancelInfo.setExameTime(DateUtils.getNowDate());
                    orderCancelInfo.setTjTime(DateUtils.getNowDate());
                    orderCancelInfo.setIsexamline("5");
                    BookingInquiryOrder inquiryRecordResult = bookingInquiryOrderMapper.selectPreInquiryOrder(orderId);
                    if(StringUtils.isNotNull(inquiryRecordResult)){
                        orderCancelInfo.setInquiryRecordId(String.valueOf(inquiryRecordResult.getInquiryResultId()));
                    }
                    int resultorder = busiShippingorderMapper.updateBusiShippingorder(orderCancelInfo);
                    if(resultorder==1){
                        result = 1;
                        //子系统推送
                        toCheckNotice.noticeSonSystem(orderId, "5", "订舱组驳回转待审核申请");
                        content = "托书转待审核失败";
                        commonService.deleteTem(orderId);//删除暂存表信息，可以再次提交
                        //更新订舱公告表
                        String refuseInfo = "";
                        if(StringUtils.isNotEmpty(examInfo)){
                            refuseInfo = "订舱组驳回原因："+examInfo+" ";
                        }
                        int resultexam = updateOrderExam(orderId,refuseInfo);
                        //推送消息队列
                        ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(busiShippingorder.getOrderId());
                        if(StringUtils.isNotNull(orderInfoRabbmq)){
                            String messagetype = "6"; //转待审核失败
                            commonService.orderInfoMQ(orderInfoRabbmq,messagetype);
                            //插入客户端消息表
                            SysMessage sysmsg = new SysMessage();
                            sysmsg.setClientId(orderInfoRabbmq.getClientId());
                            sysmsg.setOrderNumber(orderInfoRabbmq.getOrderNumber());
                            sysmsg.setMessageTitle("托书转待审核进度更新");
                            sysmsg.setMessageType("转待审核");
                            sysmsg.setMessageContent("托书编号为"+orderInfoRabbmq.getOrderNumber()+"的托书转待审核失败");
                            sysmsg.setMsgStatus("0");
                            sysmsg.setCreateTime(DateUtils.getNowDate());
                            sysmsg.setDelFlag("0");
                            sysmsg.setOrderId(orderId);
                            messageService.insertMessage(sysmsg);
                        }
                        //给跟单、业务、客户发送邮件
                        asyncService.orderSendEmail(orderId,content);
                    }
                }
            }else{
                return 0;
            }
        }catch (RuntimeException e){
            log.error("转待审核订舱审核失败",e.toString(),e.getStackTrace());
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 修改舱位号
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    @Override
    public int orderSpaceEdit(BusiShippingorders busiShippingorder) throws JsonProcessingException {
        int result = 0;
        String isupd = "0";
        String isAddRecord = "0"; //是否需要插入订舱公告 0否 1是
        String orderid=busiShippingorder.getOrderId();  //订单号
        BusiShippingorders orderInfo = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderid);  //原托书信息
        if(StringUtils.isNotNull(orderInfo)){
            String classEastandwest = orderInfo.getClassEastandwest();  //0为去程 1为回程
            String isconsolidation = orderInfo.getIsconsolidation(); //0整柜 1拼箱
            String binningwayOld = orderInfo.getShipOrderBinningway(); //委托ZIH提货 0是 1否
            String orderNmuber = orderInfo.getOrderNumber(); //原托书编号
            //修改后托书编号
            if(StringUtils.isNotEmpty(busiShippingorder.getOrderNumberEdit())){
                busiShippingorder.setOrderNumber((busiShippingorder.getOrderNumberEdit()).trim());
            }
            String orderNumberEdit = busiShippingorder.getOrderNumberEdit();
            String orderClassBh = busiShippingorder.getOrderClassBh(); //修改后的班列编号
            String stationid = busiShippingorder.getStationid(); //修改后的车站id
            //比较托书编号是否修改
            if(StringUtils.isNotEmpty(orderNumberEdit)){
                //判断新托书编号是否重复
                String numFlag = "0"; //0不重复 1重复
                List<ShippingOrderList> orderList = busiShippingorderMapper.selectOrderByOrderNumberLike(orderNumberEdit);
                if(orderList.size()>0){
                    for(int i=0;i<orderList.size();i++){
                        ShippingOrderList orderItem = orderList.get(i);
                        if(!StringUtils.equals(orderid,orderItem.getOrderId())){
                            String num = orderItem.getOrderNumber().trim();
                            if(StringUtils.equals(orderNumberEdit,num)){
                                numFlag = "1";
                            }
                            if(num.contains("-")){
                                num = num.substring(0,num.indexOf("-"));
                            }
                            if(StringUtils.equals(orderNumberEdit,num)){
                                numFlag = "1";
                            }
                        }
                    }
                    if("1".equals(numFlag)){
                        result = 12;
                        return result;
                    }
                }
                if(!StringUtils.equals(orderNmuber,orderNumberEdit)){
                    //新托书编号不为空，且与原托书编号不相等
                    busiShippingorder.setOrderNumber(orderNumberEdit);
                    busiShippingorder.setIsupdate("1");
                    isAddRecord = "1"; //修改舱位号，插入订舱公告
                    isupd = "1";
                }
            }
            //根据班列编号查询班列
            if(StringUtils.isNotEmpty(orderClassBh)){
                BusiClasses classesInfo = busiClassesService.selectBusiClassesByBh(orderClassBh);
                if(StringUtils.isNotNull(classesInfo)){
                    //比较是否可修改班列信息
                    //查询订单原数据
                    String classIdOld = orderInfo.getClassId();
                    String classIdNew = String.valueOf(classesInfo.getClassId());
                    if(!StringUtils.equals(classIdOld,classIdNew)){
                        isupd = "1";
                    }
                    busiShippingorder.setClassId(String.valueOf(classesInfo.getClassId()));
                    busiShippingorder.setClassDate(classesInfo.getClassStime());
                    busiShippingorder.setOrderClassBh(classesInfo.getClassBh());
                    busiShippingorder.setClassNumber(classesInfo.getClassNumber());
                    busiShippingorder.setIsupdate("1");
                }else{
                    result = 11;
                    return result;
                }
            }
            //回程场站地址
            if(StringUtils.isNotEmpty(stationid)){
                if("1".equals(classEastandwest)){
                    if(!StringUtils.equals(orderInfo.getStationid(),stationid)){
                        isupd = "1";
                        busiShippingorder.setStationid(stationid);
                        busiShippingorder.setIsupdate("1");
                    }
                }else{
                    return 14;
                }
            }
            if(isupd.equals("1")){
                //更新订单表
                busiShippingorder.setCreatedate(DateUtils.getNowDate());
                busiShippingorder.setCreateuserid(busiShippingorder.getCreateuserid());
                busiShippingorder.setCreateusername(busiShippingorder.getCreateusername());
                busiShippingorder.setTjTime(DateUtils.getNowDate());
                result = busiShippingorderMapper.orderSpaceEdit(busiShippingorder);
                //更新订单备份表
                BusiShippingorders isOrderBackup = busiShippingorderBackupService.selectBusiShippingorderBackupById(orderid);
                if (StringUtils.isNotNull(isOrderBackup)) {  //已存在更新，未存在插入
                    busiShippingorderBackupService.updateBusiShippingorderBackup(orderInfo);
                }else{
                    busiShippingorderBackupService.insertBusiShippingorderBackup(orderInfo);
                }
                if(result == 1){
                    //对比修改信息并更新商品表字段
                    BusiShippingorderGoods goodsinfo = busiShippingorderGoodsService.selectBusiShippingorderGoodsByOrderId(orderid);//商品表数据
                    BusiShippingorders orderInfoNew = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderid);  //最新托书信息
                    String editrecordTotal = goodsinfo.getGoodsHistoryEditrecord();
                    String title = "<th>修改人："+orderInfoNew.getCreateusername()+",修改时间："+DateUtils.parseStr(orderInfoNew.getCreatedate())+"</th>";
                    String editrecord = "";
                    if(!StringUtils.equals(orderInfoNew.getOrderNumber(),orderInfo.getOrderNumber())){
                        editrecord = editrecord+"委托书编号：由"+orderInfo.getOrderNumber()+"修改为"+orderInfoNew.getOrderNumber()+"<td>";
                    }
                    if(!StringUtils.equals(DateUtils.parseStr(orderInfoNew.getClassDate()),DateUtils.parseStr(orderInfo.getClassDate()))){
                        editrecord = editrecord+"班列日期：由"+DateUtils.parseStr(orderInfo.getClassDate())+"修改为："+DateUtils.parseStr(orderInfoNew.getClassDate())+"<td>";
                    }
                    if(!StringUtils.equals(orderInfoNew.getOrderClassBh(),orderInfo.getOrderClassBh())){
                        editrecord = editrecord+"班列编号：由"+orderInfo.getOrderClassBh()+"修改为"+orderInfoNew.getOrderClassBh()+"<td>";
                    }
                    if(!StringUtils.equals(orderInfoNew.getClassNumber(),orderInfo.getClassNumber())){
                        editrecord = editrecord+"班列号：由"+orderInfo.getClassNumber()+"修改为"+orderInfoNew.getClassNumber()+"<td>";
                    }
                    if(!StringUtils.equals(orderInfoNew.getOrderUploadsite(),orderInfo.getOrderUploadsite())){
                        editrecord = editrecord+"上货站：由"+orderInfo.getOrderUploadsite()+"修改为"+orderInfoNew.getOrderUploadsite()+"<td>";
                    }
                    if(!StringUtils.equals(orderInfoNew.getOrderUnloadsite(),orderInfo.getOrderUnloadsite())){
                        editrecord = editrecord+"下货站：由"+orderInfo.getOrderUnloadsite()+"修改为"+orderInfoNew.getOrderUnloadsite()+"<td>";
                    }
                    if(!StringUtils.equals(orderInfoNew.getStationid(),orderInfo.getStationid())){
                        BusiStation stationInfoBack = busiStationService.selectBusiStationById(orderInfo.getStationid()); //旧id查询
                        BusiStation stationInfo = busiStationService.selectBusiStationById(orderInfoNew.getStationid()); //新id查询
                        String stationold = StringUtils.isNotNull(stationInfoBack)?stationInfoBack.getStatioin():"空";
                        String station = StringUtils.isNotNull(stationInfo)?stationInfo.getStatioin():"空";
                        editrecord = editrecord+"车站/堆场地址：由"+stationold+"修改为"+station+"<td>";
                    }
                    if(StringUtils.isNotEmpty(editrecord)){
                        editrecord = title + editrecord +"<###>";
                        editrecordTotal = editrecordTotal+editrecord;
                        //更新货物表修改记录
                        BusiShippingorderGoods updgoods = new BusiShippingorderGoods();
                        updgoods.setOrderId(orderid);
                        updgoods.setGoodsHistoryEditrecord(editrecordTotal);
                        busiShippingorderGoodsService.updateBusiShippingorderGoods(updgoods);
                    }
                    //生成配舱通知书
                    asyncService.createOrderNotice(orderid);
                    //修改舱位号插入订舱公告
                    if("1".equals(isAddRecord)){
                        ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                        orderExaminfo.setOrderId(orderid);
                        orderExaminfo.setExamDate(DateUtils.getNowDate());
                        orderExaminfo.setExamInfo("舱位号修改");
                        orderExaminfo.setCreateuserid(busiShippingorder.getCreateuserid());
                        orderExaminfo.setCreateusername(busiShippingorder.getCreateusername());
                        orderExaminfo.setExamType("2");  //插入为2，失败为1，成功为0
                        shippingorderExaminfoService.insertShippingorderExaminfo(orderExaminfo);
                    }
                    //推送消息队列
                    ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderid);
                    if(StringUtils.isNotNull(orderInfoRabbmq)){
                        String messagetype = "7";//托书修改
                        commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                        //东向委托陆港提货托书给集疏发送邮件
                        String binningway = orderInfoRabbmq.getShipOrderBinningway();
                        if(classEastandwest.equals("1")&&(binningwayOld.equals("0")||binningway.equals("0"))){
                            if(!binningway.equals(binningwayOld)){
                                String[] sendEmails  = {"lihj@zih718.com","sunyk@zih718.com"};
                                //邮件内容
                                commonService.sendEmailJs(sendEmails,orderInfoRabbmq,binningwayOld);
                            }
                        }
                    }
                }
            }else{
                //推送消息队列
                ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderid);
                if(StringUtils.isNotNull(orderInfoRabbmq)){
                    String messagetype = "7";//托书修改
                    commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                }
                result = 13;
            }
        }
        return result;
    }

    /**
     * 平台端取消订单
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    @Override
    public int cancelBusiShippingorder(BusiShippingorders busiShippingorder) throws JsonProcessingException {
        int result = 0;
        String orderid=busiShippingorder.getOrderId();  //订单号
        ShippingOrder orderInfo = busiShippingorderMapper.selectBusiShippingorderById(orderid);  //托书信息
        String isconsolidation = orderInfo.getIsconsolidation();  //0整柜 1拼箱
        if("0".equals(isconsolidation)){
            //更新订单表箱量
            busiShippingorder.setContainerBoxamount("0");
        }
        //托书操作人信息
        busiShippingorder.setCreatedate(DateUtils.getNowDate());
        busiShippingorder.setCreateuserid(busiShippingorder.getCreateuserid());
        busiShippingorder.setCreateusername(busiShippingorder.getCreateusername());
        busiShippingorder.setIsexamline("3");
        busiShippingorder.setTjTime(DateUtils.getNowDate());
        int resultorder = busiShippingorderMapper.cancelBusiShippingorder(busiShippingorder);
        if(resultorder==1){
            //配舱通知书
            docOrderService.deleteDocOrderByOrderId(orderid);
            result = 1;
            //推送消息队列
            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderid);
            if(StringUtils.isNotNull(orderInfoRabbmq)){
                String messagetype = "6"; //取消托书
                commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                //东向订单且委托zih提货邮件通知集疏部
                String classEastandwest = orderInfoRabbmq.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
                String shipOrderBinningway = orderInfoRabbmq.getShipOrderBinningway(); //委托ZIH提货 0是 1否  2铁路到货
                String isConsolidation = orderInfoRabbmq.getIsconsolidation();  //0整柜 1拼箱
                if(classEastandwest.equals("1") && ("0".equals(shipOrderBinningway))){
                    String[] sendEmails  = {"lihj@zih718.com","sunyk@zih718.com"};
                    String binningwayOld = "3"; //取消托书
                    //邮件内容
                    commonService.sendEmailJs(sendEmails,orderInfoRabbmq,binningwayOld);
                }
            }
            //给跟单、业务发送邮件
            String content = "撤舱成功";
            asyncService.orderSendEmailIn(orderid,content);
        }
        return result;
    }

    //客户端取消订单申请(撤舱)
    @Override
    public int orderCancelApply(BusiShippingorders busiShippingorder) {
        int result = 0;
        try {
            String orderid = busiShippingorder.getOrderId();
            String examInfo = busiShippingorder.getExamInfo();
            busiShippingorder.setTjTime(DateUtils.getNowDate());
            busiShippingorder.setIsexamline("13");
            result = busiShippingorderMapper.cancelBusiShippingorder(busiShippingorder);
            if(result==1){
                //插入公告表
                ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                orderExaminfo.setOrderId(orderid);
                orderExaminfo.setExamDate(DateUtils.getNowDate());
                orderExaminfo.setExamInfo(examInfo);
                orderExaminfo.setCreateuserid(busiShippingorder.getCreateuserid());
                orderExaminfo.setCreateusername(busiShippingorder.getCreateusername());
                orderExaminfo.setExamType("2");  //插入为2，失败为1，成功为0
                int resultexam = shippingorderExaminfoService.insertShippingorderExaminfo(orderExaminfo);
                //推送消息队列
                ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderid);
                if(StringUtils.isNotNull(orderInfoRabbmq)){
                    String messagetype = "6"; //取消托书
                    commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                    //给跟单、业务发送邮件
                    String content = "撤舱申请";
                    asyncService.orderSendEmailIn(orderid,content);
                }
            }
        }catch (Exception e) {
            log.error("撤舱申请失败",e.toString(),e.getStackTrace());
        }
        return result;
    }

    //客户端撤舱申请操作
    @Override
    public int cancelCheckReply(BusiShippingorders busiShippingorder){
        int result = 0;
        String content="";
        try{
            //0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败 7询价中
            String orderId = busiShippingorder.getOrderId(); //订单id
            String isExamline = busiShippingorder.getIsexamline(); //3同意撤舱 5不同意撤舱
            String examInfo = busiShippingorder.getExamInfo();//审核意见
            BusiShippingorders orderinfo = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId); //订单原表数据
            if(StringUtils.isNotNull(orderinfo)){
                //托书基本信息
                String classEastandwest = orderinfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
                String isconsolidation = orderinfo.getIsconsolidation(); //0整柜 1拼箱
                String binningway = orderinfo.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                //更新托书
                BusiShippingorders orderupd = new BusiShippingorders();
                //同意撤舱
                if("3".equals(isExamline)){
                    orderupd.setIsexamline("3");
                    if("0".equals(isconsolidation)){
                        //更新订单表箱量
                        orderupd.setContainerBoxamount("0");
                    }
                }
                //不同意撤舱
                if("5".equals(isExamline)){
                    orderupd.setIsexamline("5");
                }
                orderupd.setOrderId(orderId);
                orderupd.setCreateuserid(busiShippingorder.getCreateuserid());
                orderupd.setCreateusername(busiShippingorder.getCreateusername());
                orderupd.setExameTime(DateUtils.getNowDate());
                orderupd.setTjTime(DateUtils.getNowDate());
                orderupd.setCreatedate(DateUtils.getNowDate());
                int resultorder = busiShippingorderMapper.cancelBusiShippingorder(orderupd);
                if(resultorder==1){
                    result = 1;
                    //更新订舱公告表
                    String refuseInfo = "";
                    if(StringUtils.isNotEmpty(examInfo)){
                        refuseInfo = "撤舱驳回原因："+examInfo;
                    }
                    int resultexam = updateOrderExam(orderId,refuseInfo);
                    ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                    if(StringUtils.isNotNull(orderInfoRabbmq)){
                        //发送消息队列
                        String messagetype = "6"; //取消托书
                        commonService.orderInfoMQ(orderInfoRabbmq,messagetype);
                        //东向委托陆港提货托书给集疏发送邮件
                        if("3".equals(isExamline)){
                            if("1".equals(classEastandwest) && ("0".equals(binningway))){
                                String[] sendEmails  = {"lihj@zih718.com","sunyk@zih718.com"};
                                String binningwayOld = "3"; //取消托书
                                commonService.sendEmailJs(sendEmails,orderInfoRabbmq,binningwayOld);
                            }
                        }
                        //发送邮件
                        if("3".equals(isExamline)){
                            content = "撤舱成功";
                        }
                        if("5".equals(isExamline)){
                            content = "撤舱失败";
                            if(StringUtils.isNotEmpty(examInfo)){
                                content = content + "-驳回原因："+examInfo;
                            }
                        }
                        //给跟单、业务发送邮件
                        asyncService.orderSendEmailIn(orderId,content);
                        //插入客户端消息表
                        SysMessage sysmsg = new SysMessage();
                        sysmsg.setClientId(orderInfoRabbmq.getClientId());
                        sysmsg.setOrderNumber(orderInfoRabbmq.getOrderNumber());
                        sysmsg.setMessageTitle("撤舱审核");
                        sysmsg.setMessageType("撤舱审核");
                        sysmsg.setMessageContent("托书编号为"+orderInfoRabbmq.getOrderNumber()+"的托书"+content);
                        sysmsg.setMsgStatus("0");
                        sysmsg.setCreateTime(DateUtils.getNowDate());
                        sysmsg.setDelFlag("0");
                        sysmsg.setOrderId(orderId);
                        messageService.insertMessage(sysmsg);
                    }
                }
            }else{
                return 0;
            }
        }catch (Exception e){
            log.error("订舱审核撤舱失败",e.toString(),e.getStackTrace());
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderByIds(String[] orderIds) throws JsonProcessingException {
        int result = busiShippingorderMapper.deleteBusiShippingorderByIds(orderIds);
//        if(result>0){
//            String orderIdStr = StringUtils.join(orderIds,",");
//            ShippingOrder orderInfoRabbmq = new ShippingOrder();
//            orderInfoRabbmq.setOrderId(orderIdStr);
//            orderInfoRabbmq.setIsDelete("1");//删除标识 0否 1是
//            orderInfoRabbmq.setMessageType("8"); //托书删除
//            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//            MessageProperties header = new MessageProperties();
//            header.getHeaders().put("__TypeId__","Object");
//            ObjectMapper objectMapper = new ObjectMapper();
//            Message message = new Message(objectMapper.writeValueAsBytes(orderInfoRabbmq), header);
//            rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_DYNAMIC_TOPIC_EXCHANGE, "order.dynamic.check", message,correlationData);
//        }
        return result;
    }

    /**
     * 查询订单修改记录
     *
     * @param orderId 订单ID
     * @return 订单
     */
    @Override
    public BusiShippingorders selectHistoryEditRecord(String orderId)
    {
        return busiShippingorderMapper.selectHistoryEditRecord(orderId);
    }

    /**
     * 回程场站列表
     */
    @Override
    public List<BusiStation> selectStationList(String isconsolidation, String orderUploadcode){
        return busiShippingorderMapper.selectStationList(isconsolidation,orderUploadcode );
    }

    /**
     * 查询客户跟单邮箱
     */
    @Override
    public List<String> selectOrderMerEmail(String[] merchandiserId){
        return busiShippingorderMapper.selectOrderMerEmail(merchandiserId);
    }

    /**
     * 查询客户推荐人邮箱
     */
    @Override
    public String selectOrderTjrEmail(String tjrId){
        return busiShippingorderMapper.selectOrderTjrEmail(tjrId);
    }


    /**
     * 删除订单信息
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int deleteBusiShippingorderById(String orderId)
    {
        return busiShippingorderMapper.deleteBusiShippingorderById(orderId);
    }


    @Override
    public int updateIsExamlineByOrderId(String orderId, String IsExamline) {
        return busiShippingorderMapper.updateIsExamlineByOrderId(orderId, IsExamline);
    }

    @Override
    public int updateIsExamlineButNotBH(String orderId, String IsExamline) {
        return busiShippingorderMapper.updateIsExamlineButNotBH(orderId, IsExamline);
    }

    @Override
    public BusiShippingorders selectForTocheckNoticeByOrderId(String orderId) {
        return busiShippingorderMapper.selectForTocheckNoticeByOrderId(orderId);
    }

    @Override
    public BusiClasses selectClassByOrderId(String orderId, String language) {
        return busiShippingorderMapper.selectClassByOrderId(orderId, language);
    }

    @Override
    public Map selectIsExamlineByOrderId(String orderId) {
        return busiShippingorderMapper.selectIsExamlineByOrderId(orderId);
    }

    @Override
    public BusiOrderColumn selectBusiOrderColumnByOrderId(String orderId) {
        return busiShippingorderMapper.selectBusiOrderColumnByOrderId(orderId);
    }
}
