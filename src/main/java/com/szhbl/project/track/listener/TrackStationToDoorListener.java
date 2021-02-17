package com.szhbl.project.track.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.track.TrackRabbitmq;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.mapper.BusiZyInfoMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.domain.TrackOneLevel;
import com.szhbl.project.track.domain.TrackTwoLevel;
import com.szhbl.project.track.domain.vo.TrackJsVo;
import com.szhbl.project.track.service.ITrackGoodsStatusService;
import com.szhbl.project.track.service.ITrackOneLevelService;
import com.szhbl.project.track.service.ITrackTwoLevelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TrackStationToDoorListener {

    @Autowired
    private ITrackOneLevelService trackOneLevelService;

    @Autowired
    private ITrackTwoLevelService trackTwoLevelService;

    @Autowired
    private ITrackGoodsStatusService trackGoodsStatusService;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private IBusiZyInfoService busiZyInfoService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BusiZyInfoMapper busiZyInfoMapper;

    private Integer state=1;


    /**
     * 监听拼箱场站拼箱的进站时间运踪信息
     * @param channel
     * @param message
     */
    @RabbitListener(queues = "px_system_yardLoadedin_px_queue_blpt")
    public void getPxInstationTime(Channel channel, Message message) {
        try {
            //拼箱的进站时间 px_enter_car 进站日期  px_enter_lead_number 进站时间，箱号 xianghao，订单号id order_id
            log.debug("获取拼箱的进站时间运踪信息body:{}",new String(message.getBody()));
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);
            log.debug("获取拼箱的进站时间运踪信息map：{}",map);
            String orderId = map.get("order_id");
            ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
            String fromSystem="拼箱场站系统";
            String type=map.get("isDelect");
            String inStationTime="";
            if("0".equals(type)){
                type="insert";
            }else{
                type="delete";
            }
            if(shippingOrder!=null){
                if("0".equals(shippingOrder.getClassEastandwest())&&"1".equals(shippingOrder.getIsconsolidation())){//去拼
                    if(StringUtils.isNotEmpty(map.get("px_enter_car"))){
                        inStationTime=map.get("px_enter_car");
                        if(StringUtils.isNotEmpty(map.get("px_enter_lead_number"))){
                            inStationTime=map.get("px_enter_car")+" "+map.get("px_enter_lead_number");
                        }
                        handleTwoLevel(orderId,"已进站","has been into the station",state,"1",inStationTime,15,null,null,null,type,fromSystem);
                    }
                    //更新排舱表
                    String xianghao = map.get("xianghao");
                    if(StringUtils.isNotEmpty(xianghao)){
                        if(xianghao.trim().length()==11){
                            if ("0".equals(map.get("isDelect"))){
                                TrackGoodsStatus trackGoodsStatus = new TrackGoodsStatus();
                                trackGoodsStatus.setOrderId(orderId);
                                trackGoodsStatus.setBoxNum(xianghao);
                                trackGoodsStatus.setInStationDate(DateUtils.parseDate(map.get("px_enter_car"))); // 进站日期
                                trackGoodsStatus.setInStationTime(map.get("px_enter_lead_number")); // 进站时间
                                trackGoodsStatus.setInSpaceDate(DateUtils.parseDate(map.get("px_entry_date"))); // 入场日期
                                trackGoodsStatus.setInSpaceTime(map.get("px_entry_time")); // 入场时间
                                trackGoodsStatus.setBoxType(map.get("xiangxing"));
                                trackGoodsStatus.setFromSystem("拼箱场站系统");
                                TrackGoodsStatus isTrack = new TrackGoodsStatus();
                                isTrack.setOrderId(orderId);
                                isTrack.setBoxNum(xianghao);
                                isTrack = trackGoodsStatusService.checkTgs(isTrack);
                                if(StringUtils.isNotNull(isTrack)){
                                    //修改
                                    trackGoodsStatus.setId(isTrack.getId());
                                    trackGoodsStatusService.updateTgs(trackGoodsStatus);
                                    //更新正面吊测偏结果
                                    BusiZyInfo zyinfoupd = new BusiZyInfo();
                                    zyinfoupd.setOrderId(orderId); // 订单id
                                    zyinfoupd.setXianghao(xianghao); // 箱号
                                    zyinfoupd.setCepianResult(map.get("cepian_result")); //正面吊测偏结果
                                    busiZyInfoMapper.updateZmdcByXh(zyinfoupd);
                                }else{
                                    //新增
                                    trackGoodsStatusService.insertXcppTgs(trackGoodsStatus);
                                    BusiZyInfo zyinfo = new BusiZyInfo();
                                    zyinfo.setTrackId(trackGoodsStatus.getId()); // 货物状态表id
                                    zyinfo.setOrderId(orderId); // 订单id
                                    zyinfo.setOrderNumber(shippingOrder.getOrderNumber()); // 订单编号
                                    zyinfo.setXianghao(xianghao); // 箱号
                                    zyinfo.setCepianResult(map.get("cepian_result")); //正面吊测偏结果
                                    busiZyInfoService.insertBusiZyInfo(zyinfo);
                                }
                            }
                        }
                    }
                }
            }
            else{
                log.debug("获取拼箱的进站时间运踪信息舱位查不到，订单id为----"+orderId);
            }
        } catch (IOException e) {
            log.error("监听监听拼箱场站拼箱的进站时间运踪信息：{}",e.toString(),e.getStackTrace());
        }
    }
    /**
     * 监听箱管系统提箱时间
     * @param channel
     * @param message
     */
    @RabbitListener(queues ="xg_boxoperation_queue_pick_blpt")
    public void getBoxOperationReleaseBoxTime(Channel channel, Message message) {
        try {
            log.debug("获取箱管系统放箱时间body: {}",new String(message.getBody()));
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);
            log.debug("获取箱管系统放箱时间map：{}",map);
            //Map{containerNo=CICU1032061, releaseBoxTime=2019-10-28, orderId=a40d4e76-e53e-4211-9e86-6a844b5e76b9, type=update} insert delete returnBoxTime 出场时间/进站时间
            String orderId = map.get("orderId");
            String boxNum = map.get("containerNo").trim();
            String releaseBoxTime = map.get("releaseBoxTime");
            String type = map.get("type");
            String fromSystem="箱管系统";
            //String returnBoxTime = map.get("returnBoxTime");
            ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
            if(shippingOrder!=null){
                if("0".equals(shippingOrder.getIsconsolidation())){//整
                    handleOneLevel(orderId,"已放箱","container released",state,releaseBoxTime,2,type,fromSystem);
                    handleTwoLevel(orderId,"已放箱","container released",state,"0",releaseBoxTime,8,2,null,null,type,fromSystem);
                }else if("1".equals(shippingOrder.getIsconsolidation())){//拼
                    handleTwoLevel(orderId,"已放箱","container released",state,"1",releaseBoxTime,8,null,null,null,type,fromSystem);
                }
                //箱号检验
                if(boxNum.length()==11&&"1".equals(shippingOrder.getClassEastandwest())&&"0".equals(shippingOrder.getIsconsolidation())){
                    TrackGoodsStatus trackGoodsStatus=new TrackGoodsStatus();
                    trackGoodsStatus.setOrderId(orderId);
                    trackGoodsStatus.setBoxNum(boxNum);
                    trackGoodsStatus=trackGoodsStatusService.checkTgs(trackGoodsStatus);//根据订单id和箱号查看数据库是否有该条数据
                    if(trackGoodsStatus==null){//新增
                        if(type.equals("insert")||type.equals("update")){
                            trackGoodsStatus=new TrackGoodsStatus();
                            trackGoodsStatus.setOrderId(orderId);
                            trackGoodsStatus.setBoxNum(boxNum);
                            trackGoodsStatus.setInStationDate(DateUtils.parseDate(releaseBoxTime));
                            trackGoodsStatus.setBoxType(shippingOrder.getContainerType());//箱型
                            trackGoodsStatus.setFromSystem("箱管新增");
                            trackGoodsStatusService.insertXcppTgs(trackGoodsStatus);
                            BusiZyInfo zyinfo = new BusiZyInfo();
                            zyinfo.setTrackId(trackGoodsStatus.getId()); //货物状态表id
                            zyinfo.setOrderId(orderId); //订单id
                            zyinfo.setOrderNumber(shippingOrder.getOrderNumber()); //订单编号
                            zyinfo.setXianghao(boxNum);//箱号
                            busiZyInfoService.insertBusiZyInfo(zyinfo);
                        }
                    }else{//修改
                        if(type.equals("update")){
                            trackGoodsStatus.setInStationDate(DateUtils.parseDate(releaseBoxTime));
                            trackGoodsStatus.setBoxType(shippingOrder.getContainerType());
                            trackGoodsStatus.setFromSystem("箱管修改");
                            trackGoodsStatusService.updateTgs(trackGoodsStatus);
                        }else if(type.equals("delete")){
                            trackGoodsStatus.setDelFlag(1);
                            trackGoodsStatus.setFromSystem("箱管删除");
                            trackGoodsStatusService.deleteXcppTgs(trackGoodsStatus);
                            busiZyInfoService.deleteZyInfoByTrack(orderId,boxNum);
                        }
                    }
                }else{
                    log.info("获取箱管系统放箱时间箱号错误,箱号:"+boxNum);
                }

            }
        } catch (IOException e) {
            log.error("监听箱管系统放箱时间失败：{}",e.toString(),e.getStackTrace());
        }
    }

    /**
     * 监听箱管系统还箱时间
     * @param channel
     * @param message
     */
    @RabbitListener(queues ="xg_boxoperation_queue_return_blpt")
    public void getBoxOperationReturnTime(Channel channel, Message message) {
        try {
            log.debug("获取箱管系统还箱时间body：{}",new String(message.getBody()));
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);
            log.debug("获取箱管系统还箱时间map：{}",map);
            //Map{containerNo=CICU1032061, releaseBoxTime=2019-10-28, orderId=a40d4e76-e53e-4211-9e86-6a844b5e76b9, type=update} insert delete returnBoxTime 进场时间
            String orderId = map.get("orderId");
            String boxNum = map.get("containerNo").trim();
            String returnBoxTime = map.get("returnBoxTime");
            String type = map.get("type");
            String fromSystem="箱管系统";
            //String releaseBoxTime = map.get("releaseBoxTime");
            ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
            if(shippingOrder!=null){
                if("0".equals(shippingOrder.getClassEastandwest())&&"0".equals(shippingOrder.getIsconsolidation())){//去整
                    handleOneLevel(orderId,"已还箱","have returned container",state,returnBoxTime,7,type,fromSystem);
                    handleTwoLevel(orderId,"已还箱","have returned container",state,"0",returnBoxTime,30,7,null,null,type,fromSystem);
                }else if("1".equals(shippingOrder.getClassEastandwest())&&"0".equals(shippingOrder.getIsconsolidation())){//回整
                    handleOneLevel(orderId,"已还箱","have returned container",state,returnBoxTime,7,type,fromSystem);
                    handleTwoLevel(orderId,"已还箱","have returned container",state,"0",returnBoxTime,32,7,null,null,type,fromSystem);
                }
                //箱号检验
                if(boxNum.length()==11&&"1".equals(shippingOrder.getClassEastandwest())&&"0".equals(shippingOrder.getIsconsolidation())){
                    TrackGoodsStatus trackGoodsStatus=new TrackGoodsStatus();
                    trackGoodsStatus.setOrderId(map.get("orderId"));
                    trackGoodsStatus.setBoxNum(map.get("containerNo"));
                    trackGoodsStatus=trackGoodsStatusService.checkTgs(trackGoodsStatus);//根据订单id和箱号查看数据库是否有该条数据
                    if(trackGoodsStatus==null){//新增
                        if(type.equals("insert")||type.equals("update")){
                            trackGoodsStatus=new TrackGoodsStatus();
                            trackGoodsStatus.setOrderId(orderId);
                            trackGoodsStatus.setBoxNum(boxNum);
                            trackGoodsStatus.setInSpaceDate(DateUtils.parseDate(returnBoxTime));//入场日期
                            trackGoodsStatus.setBoxType(shippingOrder.getContainerType());//箱型
                            trackGoodsStatus.setFromSystem("箱管新增");
                            trackGoodsStatusService.insertXcppTgs(trackGoodsStatus);
                            BusiZyInfo zyinfo = new BusiZyInfo();
                            zyinfo.setTrackId(trackGoodsStatus.getId()); //货物状态表id
                            zyinfo.setOrderId(orderId); //订单id
                            zyinfo.setOrderNumber(shippingOrder.getOrderNumber()); //订单编号
                            zyinfo.setXianghao(boxNum);//箱号
                            busiZyInfoService.insertBusiZyInfo(zyinfo);
                        }
                    }else{//修改
                        if(type.equals("update")){
                            trackGoodsStatus.setInSpaceDate(DateUtils.parseDate(returnBoxTime));//入场日期
                            trackGoodsStatus.setBoxType(shippingOrder.getContainerType());
                            trackGoodsStatus.setFromSystem("箱管修改");
                            trackGoodsStatusService.updateTgs(trackGoodsStatus);
                        }else if(type.equals("delete")){
                            trackGoodsStatus.setDelFlag(1);
                            trackGoodsStatus.setFromSystem("箱管删除");
                            trackGoodsStatusService.deleteXcppTgs(trackGoodsStatus);
                            busiZyInfoService.deleteZyInfoByTrack(orderId,boxNum);
                        }
                    }
                }else{
                    log.info("获取箱管系统还箱时间箱号错误,箱号:"+boxNum);
                }
            }
        } catch (IOException e) {
           log.error(" 监听箱管系统还箱时间失败：{}",e.toString(),e.getStackTrace());
        }
    }

    /**
     * 监听箱行亚欧系统公路运输信息
     * @param channel
     * @param message
     */
    @RabbitListener(queues = "xxyo_track_queue_blpt")
    //@RabbitListener(queues = "queue2")
    public void getRoadtransit(Channel channel, Message message) {
        try {
            log.debug("获取箱行亚欧运踪信息body：{}",new String(message.getBody()));
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody(),"utf-8"));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);
           // Map<String,String> mapinfo = FastJsonUtils.json2Bean(new String(message.getBody(),"utf-8"), HashMap.class);
            log.debug("获取箱行亚欧运踪信息map：{}",map);
            String orderId=map.get("orderId");
            String type=map.get("type");
            String fromSystem="箱行亚欧";
            String remark="";
            String remarkEn="";
            //String xxyoRemark=map.get("xxyoRemark");
            /*  orderId 订单id
             boxNum  箱号
             goCome  去回程
             sendCarTime 派车时间
             driverInformation 司机信息
             carryBoxTime 提箱时间
             carryGoodsTime 提货时间
             expectArriveTime 预计送达时间
             arriveTime 实际送达时间
             signTime 签收时间
             type inset delete
             xxyoRemark 不为空的话就是异常
             goodsType 0整柜  1散柜
            */
            if(StringUtils.isNotEmpty(orderId)){
                if("0".equals(map.get("goCome"))&&"0".equals(map.get("goodsType"))){//去整
                    if(StringUtils.isNotEmpty(map.get("sendCarTime"))||StringUtils.isNotEmpty(map.get("driverInformation"))){
                        if(StringUtils.isEmpty(map.get("driverInformation"))){
                            remark="司机信息: 暂无";
                            remarkEn="driver's information: N/A";
                        }else{
                            remark="司机信息:"+map.get("driverInformation");
                            remarkEn="driver's information:"+map.get("driverInformation");
                        }
                        handleTwoLevel(orderId,"已派车","have sent truck",state,"1",map.get("sendCarTime"),9,2,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("carryBoxTime"))){
                        if(StringUtils.isEmpty(map.get("boxNum"))){
                            remark="箱号: 暂无";
                            remarkEn="ContainerNumber: N/A";
                        }else{
                            remark="箱号:"+map.get("boxNum");
                            remarkEn="ContainerNumber:"+map.get("boxNum");
                        }
                        handleTwoLevel(orderId,"已提箱","have picked up",state,"1",map.get("carryBoxTime"),10,2,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("carryGoodsTime"))){
                        if("insert".equals(type)){
                            handleOneLevel(orderId,"已提货","have picked up cargo",state,map.get("carryGoodsTime"),2,"update",fromSystem);
                        }else{
                            handleOneLevel(orderId,"已提货","have picked up cargo",state,map.get("carryGoodsTime"),2,type,fromSystem);
                        }
                        handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",map.get("carryGoodsTime"),11,2,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("expectArriveTime"))){
                        handleTwoLevel(orderId,"预计送达时间","estimated time of delivery",state,"1",map.get("expectArriveTime"),12,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("arriveTime"))){
                        handleTwoLevel(orderId,"已送达","have been delivered",state,"1",map.get("arriveTime"),13,null,null,null,type,fromSystem);
                    }
                }else if("0".equals(map.get("goCome"))&&"1".equals(map.get("goodsType"))){//去拼
                    if(StringUtils.isNotEmpty(map.get("sendCarTime"))||StringUtils.isNotEmpty(map.get("driverInformation"))){
                        if(StringUtils.isEmpty(map.get("driverInformation"))){
                            remark="司机信息: 暂无";
                            remarkEn="driver's information: N/A";
                        }else{
                            remark="司机信息:"+map.get("driverInformation");
                            remarkEn="driver's information:"+map.get("driverInformation");
                        }
                        handleTwoLevel(orderId,"已派车","have sent truck",state,"1",map.get("sendCarTime"),9,null,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("carryBoxTime"))){
                        if(StringUtils.isEmpty(map.get("boxNum"))){
                            remark="箱号: 暂无";
                            remarkEn="ContainerNumber: N/A";
                        }else{
                            remark="箱号:"+map.get("boxNum");
                            remarkEn="ContainerNumber:"+map.get("boxNum");
                        }
                        handleTwoLevel(orderId,"已提箱","have picked up",state,"1",map.get("carryBoxTime"),10,null,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("carryGoodsTime"))){
                        handleOneLevel(orderId,"已提货","have picked up cargo",state,map.get("carryGoodsTime"),2,type,fromSystem);
                        handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",map.get("carryGoodsTime"),11,2,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("expectArriveTime"))){
                        handleTwoLevel(orderId,"预计送达时间","estimated time of delivery",state,"1",map.get("expectArriveTime"),12,2,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("arriveTime"))){
                        handleTwoLevel(orderId,"已送达","have been delivered",state,"1",map.get("arriveTime"),13,2,null,null,type,fromSystem);
                    }
                }else if("1".equals(map.get("goCome"))&&"0".equals(map.get("goodsType"))){//回整
                    if(StringUtils.isNotEmpty(map.get("carryGoodsTime"))){
                        handleOneLevel(orderId,"已提货","have picked up cargo",state,map.get("carryGoodsTime"),6,type,fromSystem);
                        if(StringUtils.isEmpty(map.get("driverInformation"))){
                            remark="司机信息: 暂无";
                            remarkEn="driver's information: N/A";
                        }else{
                            remark="司机信息:"+map.get("driverInformation");
                            remarkEn="driver's information:"+map.get("driverInformation");
                        }
                        handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",map.get("carryGoodsTime"),29,6,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("arriveTime"))){
                        if("insert".equals(type)){
                            handleOneLevel(orderId,"已送达","have been delivered",state,map.get("arriveTime"),6,"update",fromSystem);
                        }else{
                            handleOneLevel(orderId,"已送达","have been delivered",state,map.get("arriveTime"),6,type,fromSystem);
                        }
                        handleTwoLevel(orderId,"已送达","have been delivered",state,"0",map.get("arriveTime"),30,6,null,null,type,fromSystem);
                    }
                    /*if(StringUtils.isNotEmpty(map.get("signTime"))){
                        handleTwoLevel(orderId,"已签收","delivery note signed",state,"1",map.get("signTime"),31,null,null,null,type,fromSystem);
                    }*/
                }else if("1".equals(map.get("goCome"))&&"1".equals(map.get("goodsType"))){//回拼
                    if(StringUtils.isNotEmpty(map.get("carryGoodsTime"))){
                        handleOneLevel(orderId,"已提货","have picked up cargo",state,map.get("carryGoodsTime"),6,type,fromSystem);
                        if(StringUtils.isEmpty(map.get("driverInformation"))){
                            remark="司机信息: 暂无";
                            remarkEn="driver's information: N/A";
                        }else{
                            remark="司机信息:"+map.get("driverInformation");
                            remarkEn="driver's information:"+map.get("driverInformation");
                        }
                        handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",map.get("carryGoodsTime"),31,6,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("arriveTime"))){
                        if("insert".equals(type)){
                            handleOneLevel(orderId,"已送达","have been delivered",state,map.get("arriveTime"),6,"update",fromSystem);
                        }else{
                            handleOneLevel(orderId,"已送达","have been delivered",state,map.get("arriveTime"),6,type,fromSystem);
                        }
                        handleTwoLevel(orderId,"已送达","have been delivered",state,"0",map.get("arriveTime"),32,6,null,null,type,fromSystem);
                    }
                    /*if(StringUtils.isNotEmpty(map.get("signTime"))){
                        handleTwoLevel(orderId,"已签收","delivery note signed",state,"1",map.get("signTime"),33,null,null,null,type,fromSystem);
                    }*/
                }
            }
        } catch (IOException e) {
            log.error("监听箱行亚欧系统公路运输信息失败：{}",e.toString(),e.getStackTrace());
        }
    }

    /**
     * 监听拼箱场站运踪信息
     * @param channel
     * @param message
     */
    @RabbitListener(queues = "gncz_system_sendqueue_todz")
    public void getStoreoperation(Channel channel, Message message) {
        try {
            log.debug("获取拼箱场站运踪信息body：{}",new String(message.getBody()));
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);
            log.debug("获取拼箱场站运踪信息map：{}",map);
             /* orderId { get; set; }//订单id
             dection { get; set; }//方向0去程1回程
             wholeorhalf { get; set; }//拼箱整柜0拼箱1整柜
             receivetime{ get; set; }//入仓时间
             isDelect { get; set; }//0修改1删除
             entertime { get; set; }//进站时间
             centerpickuptime { get; set; }//从中心场提货时间
             pickuptime { get; set; }//从堆场提货时间
             packingtime { get; set; }//拆箱时间
             platenum { get; set; }//车牌号
             px_box_type { get; set; }//规格（长*宽*高*件数）14
             px_LongAndWide { get; set; }//单据重量
             xianghao  箱号 */
            String goCome=map.get("dection");
            String woh=map.get("wholeorhalf");
            String type=map.get("isDelect");
            String fromSystem="拼箱场站";
            String remark="";
            String remarkEn="";
            if("0".equals(type)){
                type="insert";
            }else{
                type="delete";
            }
            String orderId=map.get("order_id");
            if("0".equals(goCome)&&"1".equals(woh)){//去整
                if(StringUtils.isNotEmpty(map.get("receivetime"))){
                    handleOneLevel(orderId,"已入仓","cargo has been to the warehouse",state,map.get("receivetime"),3,type,fromSystem);
                    handleTwoLevel(orderId,"已入仓","cargo has been to the warehouse",state,"0",map.get("receivetime"),14,3,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(map.get("entertime"))){
                    if("insert".equals(type)){
                        handleOneLevel(orderId,"已进站","has been into the station",state,map.get("entertime"),3,"update",fromSystem);
                    }else{
                        handleOneLevel(orderId,"已进站","has been into the station",state,map.get("entertime"),3,type,fromSystem);
                    }
                    handleTwoLevel(orderId,"已进站","has been into the station",state,"0",map.get("entertime"),15,3,null,null,type,fromSystem);
                }
            }else if("0".equals(goCome)&&"0".equals(woh)){//去拼
                if(StringUtils.isNotEmpty(map.get("receivetime"))){
                    if("insert".equals(type)){
                        handleOneLevel(orderId,"已入仓","cargo has been to the warehouse",state,map.get("receivetime"),2,"update",fromSystem);
                    }else{
                        handleOneLevel(orderId,"已入仓","cargo has been to the warehouse",state,map.get("receivetime"),2,type,fromSystem);
                    }
                    if(StringUtils.isEmpty(map.get("px_box_type"))){
                        remark="规格: 暂无";
                        remarkEn="specifications: N/A";
                    }else{
                        remark="规格:"+map.get("px_box_type");
                        remarkEn="specifications:"+map.get("px_box_type");
                    }
                    if(StringUtils.isEmpty(map.get("px_LongAndWide"))){
                        remark=remark+"/重量: 暂无";
                        remarkEn=remarkEn+"/weight: N/A";
                    }else{
                        remark=remark+"/重量:"+map.get("px_LongAndWide");
                        remarkEn=remarkEn+"/weight:"+map.get("px_LongAndWide");
                    }
                    handleTwoLevel(orderId,"已入仓","cargo has been to the warehouse",state,"0",map.get("receivetime"),14,2,remark,remarkEn,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(map.get("entertime"))){
                    handleTwoLevel(orderId,"已进站","has been into the station",state,"1",map.get("entertime"),15,null,null,null,type,fromSystem);
                }
            }else if("1".equals(goCome)&&"1".equals(woh)){//回整
                if(StringUtils.isNotEmpty(map.get("centerpickuptime"))){
                    handleOneLevel(orderId,"已提货","have picked up cargo",state,map.get("centerpickuptime"),6,type,fromSystem);
                    if(StringUtils.isEmpty(map.get("platenum"))){
                        remark="司机信息: 暂无";
                        remarkEn="driver's information: N/A";
                    }else{
                        remark="司机信息:"+map.get("platenum");
                        remarkEn="driver's information:"+map.get("platenum");
                    }
                    handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",map.get("centerpickuptime"),29,6,remark,remarkEn,type,fromSystem);
                }
            }else if("1".equals(goCome)&&"0".equals(woh)){//回拼
                if(StringUtils.isNotEmpty(map.get("centerpickuptime"))){
                    handleTwoLevel(orderId,"已提货(中心站提货)","have picked up cargo (from central staion)",state,"1",map.get("centerpickuptime"),29,5,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(map.get("packingtime"))){
                    handleOneLevel(orderId,"已拆箱","finished devanning ",state,map.get("packingtime"),5,type,fromSystem);
                    handleTwoLevel(orderId,"已拆箱","finished devanning ",state,"0",map.get("packingtime"),30,5,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(map.get("pickuptime"))){
                    handleOneLevel(orderId,"已提货(堆场提货)","have picked up cargo (from the yard)",state,map.get("pickuptime"),6,type,fromSystem);
                    if(StringUtils.isEmpty(map.get("platenum"))){
                        remark="司机信息: 暂无";
                        remarkEn="driver's information: N/A";
                    }else{
                        remark="司机信息:"+map.get("platenum");
                        remarkEn="driver's information:"+map.get("platenum");
                    }
                    handleTwoLevel(orderId,"已提货(堆场提货)","have picked up cargo (from the yard)",state,"0",map.get("pickuptime"),31,6,remark,remarkEn,type,fromSystem);
                }
            }
        } catch (IOException e) {
            log.error("监听拼箱场站运踪信息失败：{}",e.toString(),e.getStackTrace());
        }
    }

    /**
     * 监听关务系统运踪信息
     * @param channel
     * @param message
     */
    @RabbitListener(queues = TrackRabbitmq.GW_TRACK_QUEUE_BLPT)
    public void getGwTrack(Channel channel, Message message) {
        try {
            log.debug("获取关务系统运踪信息body：{}",new String(message.getBody()));
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);
            log.debug("获取关务系统运踪信息map：{}",map);
            String orderId = map.get("order_Id");
            String type=map.get("type");
            String fromSystem="关务系统";
            String remark="";
            String remarkEn="";
            //date=null, reason=null, payTaxTime=null, releaseTime=null, inspectionTime=null, layoutTime=null, documentCheckTime=2020-05-23 16:29:44, type=insert, declareStatus=1,
            // problemCommunicateTime=null,strawSureTime=null, applyTime=null, order_Id=1e0013644f094394bc9c0b07fbaec15d, originalProvideTime=null, documentCheckResult=null}
            //problemCommunicateTime 问题沟通中
            //strawSureTime 草单已确认
            //originalProvideTime 正本已提供
            //applyTime 已申报
            //payTaxTime 已缴税
            //weightAbnormal  重量异常
            //layoutTime 布控
            //inspectionTime 查验
            //releaseTime 已报关通过
            //documentCheckTime 单证审核通过时间
            //documentCheckResult 单证审核结果
            ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
            if(shippingOrder!=null){
                String goCome = shippingOrder.getClassEastandwest();
                String woh = shippingOrder.getIsconsolidation();
                if("0".equals(goCome)&&"0".equals(woh)){//去整
                    if(StringUtils.isNotEmpty(map.get("problemCommunicateTime"))){
                        handleTwoLevel(orderId,"问题沟通中","problems on communication",state,"1",map.get("problemCommunicateTime"),17,3,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("strawSureTime"))){
                        handleTwoLevel(orderId,"草单已确认","draft has been confirmed",state,"1",map.get("strawSureTime"),18,3,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("originalProvideTime"))){
                        handleTwoLevel(orderId,"正本已提供","the original has been provided",state,"1",map.get("originalProvideTime"),19,3,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("applyTime"))){
                        handleTwoLevel(orderId,"已申报","declared",state,"1",map.get("applyTime"),20,3,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("layoutTime"))){
                        if(StringUtils.isEmpty(map.get("layoutReason"))){
                            remark="布控原因: 暂无";
                            remarkEn="inspection reason: N/A";
                        }else{
                            remark="布控原因:"+map.get("layoutReason");
                            remarkEn="inspection reason:"+map.get("layoutReason");
                        }
                        handleTwoLevel(orderId,"布控","inspection",state,"1",map.get("layoutTime"),21,3,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("releaseTime"))){
                        if("insert".equals(type)){
                            handleOneLevel(orderId,"已报关通过","finished customs declaration",state,map.get("releaseTime"),3,"update",fromSystem);
                        }else{
                            handleOneLevel(orderId,"已报关通过","finished customs declaration",state,map.get("releaseTime"),3,type,fromSystem);
                        }
                        handleTwoLevel(orderId,"已报关通过","finished customs declaration",state,"0",map.get("releaseTime"),22,3,null,null,type,fromSystem);
                    }
                }else if("0".equals(goCome)&&"1".equals(woh)){//去拼
                    if(StringUtils.isNotEmpty(map.get("problemCommunicateTime"))){
                        handleTwoLevel(orderId,"问题沟通中","problems on communication",state,"1",map.get("problemCommunicateTime"),17,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("strawSureTime"))){
                        handleTwoLevel(orderId,"草单已确认","draft has been confirmed",state,"1",map.get("strawSureTime"),18,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("originalProvideTime"))){
                        handleTwoLevel(orderId,"正本已提供","the original has been provided",state,"1",map.get("originalProvideTime"),19,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("applyTime"))){
                        handleTwoLevel(orderId,"已申报","declared",state,"1",map.get("applyTime"),20,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("layoutTime"))){
                        if(StringUtils.isEmpty(map.get("layoutReason"))){
                            remark="布控原因: 暂无";
                            remarkEn="inspection reason: N/A";
                        }else{
                            remark="布控原因:"+map.get("layoutReason");
                            remarkEn="inspection reason:"+map.get("layoutReason");
                        }
                        handleTwoLevel(orderId,"布控","inspection",state,"1",map.get("layoutTime"),21,null,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("releaseTime"))){
                        handleOneLevel(orderId,"已报关通过","finished customs declaration",state,map.get("releaseTime"),3,type,fromSystem);
                        handleTwoLevel(orderId,"已报关通过","finished customs declaration",state,"0",map.get("releaseTime"),22,3,null,null,type,fromSystem);
                    }
                }else if("1".equals(goCome)){//回程
                    if(StringUtils.isNotEmpty(map.get("documentCheckTime"))){
                        if("0".equals(woh)){
                            if("insert".equals(type)){
                                handleOneLevel(orderId,"单证已审核通过","documents approved",state,map.get("documentCheckTime"),3,"update",fromSystem);
                            }else{
                                handleOneLevel(orderId,"单证已审核通过","documents approved",state,map.get("documentCheckTime"),3,type,fromSystem);
                            }
                            if(StringUtils.isEmpty(map.get("documentCheckResult"))){
                                remark="审核结果: 暂无";
                                remarkEn="audit result: N/A";
                            }else{
                                remark="审核结果:"+map.get("documentCheckResult");
                                remarkEn="audit result:"+map.get("documentCheckResult");
                            }
                            handleTwoLevel(orderId,"单证已审核通过","documents approved",state,"0",map.get("documentCheckTime"),17,3,remark,remarkEn,type,fromSystem);
                        }else{
                            handleOneLevel(orderId,"单证已审核通过","documents approved",state,map.get("documentCheckTime"),3,type,fromSystem);
                            if(StringUtils.isEmpty(map.get("documentCheckResult"))){
                                remark="审核结果: 暂无";
                                remarkEn="audit result: N/A";
                            }else{
                                remark="审核结果:"+map.get("documentCheckResult");
                                remarkEn="audit result:"+map.get("documentCheckResult");
                            }
                            handleTwoLevel(orderId,"单证已审核通过","documents approved",state,"0",map.get("documentCheckTime"),17,3,remark,remarkEn,type,fromSystem);
                        }
                    }
                    if(StringUtils.isNotEmpty(map.get("problemCommunicateTime"))){
                        handleTwoLevel(orderId,"问题沟通中","problems on communication",state,"1",map.get("problemCommunicateTime"),20,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("strawSureTime"))){
                        handleTwoLevel(orderId,"草单已确认","draft has been confirmed",state,"1",map.get("strawSureTime"),21,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("originalProvideTime"))){
                        handleTwoLevel(orderId,"正本已提供","the original has been provided",state,"1",map.get("originalProvideTime"),22,null,null,null,type,fromSystem);
                    }
                    /*if(StringUtils.isNotEmpty(map.get("applyTime"))){
                        handleTwoLevel(orderId,"已申报","declared",state,"1",map.get("applyTime"),23,null,null,null,type,fromSystem);
                    }*/
                    if(StringUtils.isNotEmpty(map.get("payTaxTime"))){
                        handleTwoLevel(orderId,"待缴税","to pay tariffs",state,"1",map.get("payTaxTime"),24,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("layoutTime"))){
                        if(StringUtils.isEmpty(map.get("layoutReason"))){
                            remark="布控原因: 暂无";
                            remarkEn="inspection reason: N/A";
                        }else{
                            remark="布控原因:"+map.get("layoutReason");
                            remarkEn="inspection reason:"+map.get("layoutReason");
                        }
                        handleTwoLevel(orderId,"布控","inspection",state,"1",map.get("layoutTime"),25,null,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("weightAbnormal"))){
                        handleTwoLevel(orderId,"重量异常","abnormal weight",state,"1",map.get("weightAbnormal"),26,null,null,null,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("inspectionTime"))){
                        if(StringUtils.isEmpty(map.get("inspectionReason"))){
                            remark="查验原因: 暂无";
                            remarkEn="inspection reason: N/A";
                        }else{
                            remark="查验原因:"+map.get("inspectionReason");
                            remarkEn="inspection reason:"+map.get("inspectionReason");
                        }
                        handleTwoLevel(orderId,"查验","inspection",state,"1",map.get("inspectionTime"),27,null,remark,remarkEn,type,fromSystem);
                    }
                    if(StringUtils.isNotEmpty(map.get("releaseTime"))){
                        handleOneLevel(orderId,"海关已放行","customs released",state,map.get("releaseTime"),5,type,fromSystem);
                        handleTwoLevel(orderId,"海关已放行","customs released",state,"0",map.get("releaseTime"),28,5,null,null,type,fromSystem);
                    }
                }
            }
        } catch (IOException e) {
            log.error("监听关务系统运踪信息失败：{}",e.toString(),e.getStackTrace());
        }
    }

    /**
     * 监听国外场站运踪信息
     * @param channel
     * @param message
     */
    @RabbitListener(queues = TrackRabbitmq.GWCZ_TRACK_QUEUE_BLPT)
    public void getGwczTrack(Channel channel, Message message) {
        try {
            log.debug("获取国外场站运踪信息body：{}",new String(message.getBody()));
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);
            log.debug("获取国外场站运踪信息map：{}",map);
            String orderNum = map.get("orderId");
            String fromSystem = "国外场站";
            if(StringUtils.isNotEmpty(orderNum)){
                BusiShippingorders busiShippingorder=busiShippingorderService.selectBusiShippingorderByOrderNumber(orderNum);
                if(busiShippingorder!=null){
                    String orderId=busiShippingorder.getOrderId();
                    String goCome = map.get("goCome");
                    String woh = map.get("woh");
                    String type=map.get("type");
                    String remark="";
                    String remarkEn="";
                    /**
                     * //carryBoxInformationTime 已发提箱资料
                     * expectReachTime 预计送达时间
                     * reachTime 已送达
                     * signTime 已签收
                     * devanningTime 已拆箱完成
                     * //expectCarryCargoTime 预计提货时间
                     * //carryCargoTime 提货时间
                     * driverMessage 司机信息
                     * inStoreTime 入仓时间
                     * inStationTime 进站时间
                     * carryBoxTime 提箱时间
                     */
                    //没有整柜，只有拼箱
                    if("0".equals(goCome)&&"0".equals(woh)){//去整
                        if(StringUtils.isNotEmpty(map.get("carryBoxInformationTime"))){
                            handleOneLevel(orderId,"已发提箱资料","the pick-up information have been sent",state,map.get("carryBoxInformationTime"),5,type,fromSystem);
                            handleTwoLevel(orderId,"已发提箱资料","the pick-up information have been sent",state,"0",map.get("carryBoxInformationTime"),24,5,null,null,type,fromSystem);
                        }
                        if(StringUtils.isNotEmpty(map.get("carryBoxTime"))){
                            handleOneLevel(orderId,"已提箱","have picked up",state,map.get("carryBoxTime"),6,type,fromSystem);
                            handleTwoLevel(orderId,"已提箱","have picked up",state,"0",map.get("carryBoxTime"),26,6,null,null,type,fromSystem);
                        }
                   /* if(StringUtils.isNotEmpty(map.get("expectReachTime"))){
                        handleTwoLevel(orderId,"预计送达时间","YJSDSJ",state,"1",map.get("expectReachTime"),27,6,null,type);
                    }
                    if(StringUtils.isNotEmpty(map.get("reachTime"))){
                        if("insert".equals(type)){
                            handleOneLevel(orderId,"已送达","YSD",state,map.get("reachTime"),6,"update");
                        }else{
                            handleOneLevel(orderId,"已送达","YSD",state,map.get("reachTime"),6,type);
                        }
                        handleTwoLevel(orderId,"已送达","YSD",state,"0",map.get("reachTime"),28,6,null,type);
                    }
                    if(StringUtils.isNotEmpty(map.get("signTime"))){
                        handleTwoLevel(orderId,"已签收","YJTXSJ",state,"1",map.get("signTime"),29,null,null,type);
                    }*/
                    }else if("0".equals(goCome)&&"1".equals(woh)){//去拼
                        if(StringUtils.isNotEmpty(map.get("expectCarryCargoTime"))){
                            handleOneLevel(orderId,"已拆箱完成","finished devanning",state,map.get("expectCarryCargoTime"),5,type,fromSystem);
                            handleTwoLevel(orderId,"已拆箱完成","finished devanning",state,"0",map.get("expectCarryCargoTime"),24,5,null,null,type,fromSystem);
                        }
                        if(StringUtils.isNotEmpty(map.get("carryCargoTime"))){
                            handleOneLevel(orderId,"已提货","have picked up cargo",state,map.get("carryCargoTime"),6,type,fromSystem);
                            if(StringUtils.isEmpty(map.get("driverMessage"))){
                                remark="司机信息: 暂无";
                                remarkEn="driver's information: N/A";
                            }else{
                                remark="司机信息:"+map.get("driverMessage");
                                remarkEn="driver's information"+map.get("driverMessage");
                            }
                            handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",map.get("carryCargoTime"),26,6,remark,remarkEn,type,fromSystem);
                        }
                    }/*else if("1".equals(goCome)&&"0".equals(woh)){//回整
                        if(StringUtils.isNotEmpty(map.get("inStoreTime"))){
                            handleTwoLevel(orderId,"已入仓","YRC",state,"0",map.get("inStoreTime"),15,null,null,type,fromSystem);
                        }
                        if(StringUtils.isNotEmpty(map.get("inStationTime"))){
                            handleOneLevel(orderId,"已进站","YJZ",state,map.get("inStationTime"),3,type);
                            handleTwoLevel(orderId,"已进站","YJZ",state,"0",map.get("inStationTime"),16,3,null,type);
                        }
                    }*/
                    else if("1".equals(goCome)&&"1".equals(woh)){//回拼
                        if(StringUtils.isNotEmpty(map.get("inStoreTime"))){
                            if("insert".equals(type)){
                                handleOneLevel(orderId,"已入仓","cargo has been to the warehouse",state,map.get("inStoreTime"),2,"update",fromSystem);
                            }else{
                                handleOneLevel(orderId,"已入仓","cargo has been to the warehouse",state,map.get("inStoreTime"),2,type,fromSystem);
                            }
                            handleTwoLevel(orderId,"已入仓","cargo has been to the warehouse",state,"0",map.get("inStoreTime"),15,2,null,null,type,fromSystem);
                        }
                /*if(StringUtils.isNotEmpty(map.get("inStationTime"))){
                    if("insert".equals(type)){
                        handleOneLevel(orderId,"已进站","YJZ",state,map.get("inStationTime"),2,"update");
                    }else{
                        handleOneLevel(orderId,"已进站","YJZ",state,map.get("inStationTime"),2,type);
                    }
                    handleTwoLevel(orderId,"已进站","YJZ",state,"0",map.get("inStationTime"),16,2,null,type);
                }*/
                    }
                }
            }
        } catch (IOException e) {
            log.error("监听国外场站运踪信息失败：{}",e.toString(),e.getStackTrace());
        }
    }

    /**
     * 监听集疏系统运踪信息
     * @param channel
     * @param message
     */
    @RabbitListener(queues = "js_track_queue_blpt")
    public void getJsTrack(Channel channel, Message message) {
        try {
            log.debug("获取集疏系统运踪信息body：{}",new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            TrackJsVo trackJsVo = FastJsonUtils.json2Bean(new String(message.getBody()), TrackJsVo.class);
            String orderId=trackJsVo.getOrderId();
            String type="insert";
            String fromSystem="集疏系统";
            ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
            log.debug("获取集疏系统运踪信息shippingOrder：{}",shippingOrder);
            log.debug("获取集疏系统运踪信息trackJsVo：{}",trackJsVo);
            String remark="";
            String remarkEn="";
            //    订单 orderId;
            //    订单编号  orderNumber;
            //    箱号  containerNo;
            //    车牌号 plateNumber;
            //     * 预计提货时间   planPickTime;
            //     * 提货时间 realPickTime;
            //     * 预计送达时间  planArriveTime;
            //     * 送达时间   realArriveTime;
            //     * 还箱时间  realReturnTime;
            //      podDate 签收时间
            //      pickupBoxTime 回程提箱时间
            if("0".equals(shippingOrder.getClassEastandwest())&&"0".equals(shippingOrder.getIsconsolidation())){//去整
                if(StringUtils.isNotEmpty(trackJsVo.getPlanPickTime())){
                    handleTwoLevel(orderId,"预计提箱时间","estimated pickup time",state,"0",trackJsVo.getPlanPickTime(),25,null,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getRealPickTime())){
                    handleOneLevel(orderId,"已提箱","have picked up",state,trackJsVo.getRealPickTime(),6,type,fromSystem);
                    handleTwoLevel(orderId,"已提箱","have picked up",state,"0",trackJsVo.getRealPickTime(),26,6,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getPlanArriveTime())){
                    handleTwoLevel(orderId,"预计送达时间","estimated time of delivery",state,"1",trackJsVo.getPlanArriveTime(),27,6,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getRealArriveTime())){
                    handleOneLevel(orderId,"已送达","have been delivered",state,trackJsVo.getRealArriveTime(),6,"update",fromSystem);
                    handleTwoLevel(orderId,"已送达","have been delivered",state,"0",trackJsVo.getRealArriveTime(),28,6,null,null,type,fromSystem);
                }
               /* if(StringUtils.isNotEmpty(trackJsVo.getPodDate())){
                    handleTwoLevel(orderId,"已签收","delivery note signed",state,"1",trackJsVo.getPodDate(),29,null,null,null,type,fromSystem);
                }*/
            }else if("0".equals(shippingOrder.getClassEastandwest())&&"1".equals(shippingOrder.getIsconsolidation())){//去拼
                if(StringUtils.isNotEmpty(trackJsVo.getPlanPickTime())){
                    handleTwoLevel(orderId,"预计提货时间","estimated time of picking up cargo",state,"0",trackJsVo.getPlanPickTime(),25,null,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getRealPickTime())){
                    handleOneLevel(orderId,"已提货","have picked up cargo",state,trackJsVo.getRealPickTime(),6,type,fromSystem);
                    if(StringUtils.isEmpty(trackJsVo.getPlateNumber())){
                        remark="车牌号: 暂无";
                        remarkEn="Truck plate No: N/A";
                    }else{
                        remark="车牌号:"+trackJsVo.getPlateNumber();
                        remarkEn="Truck plate No"+trackJsVo.getPlateNumber();
                    }
                    handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",trackJsVo.getRealPickTime(),26,6,remark,remarkEn,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getPlanArriveTime())){
                    handleTwoLevel(orderId,"预计送达时间","estimated time of delivery",state,"1",trackJsVo.getPlanArriveTime(),27,6,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getRealArriveTime())){
                    handleOneLevel(orderId,"已送达","have been delivered",state,trackJsVo.getRealArriveTime(),6,"update",fromSystem);
                    handleTwoLevel(orderId,"已送达","have been delivered",state,"0",trackJsVo.getRealArriveTime(),28,6,null,null,type,fromSystem);
                }
               /* if(StringUtils.isNotEmpty(trackJsVo.getPodDate())){
                    handleTwoLevel(orderId,"已签收","delivery note signed",state,"1",trackJsVo.getPodDate(),29,null,null,null,type,fromSystem);
                }*/
            }else if("1".equals(shippingOrder.getClassEastandwest())&&"0".equals(shippingOrder.getIsconsolidation())){//回整
                /*if(StringUtils.isNotEmpty(trackJsVo.getPickupBoxTime())){
                    handleTwoLevel(orderId,"已提箱","YTH",state,"1",trackJsVo.getPickupBoxTime(),10,2,"箱号:"+trackJsVo.getContainerNo(),type);
                }*/
                if(StringUtils.isNotEmpty(trackJsVo.getPlanPickTime())){
                    handleTwoLevel(orderId,"预计提货时间","estimated time of picking up cargo",state,"0",trackJsVo.getPlanPickTime(),11,2,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getRealPickTime())){
                    handleOneLevel(orderId,"已提货","have picked up cargo",state,trackJsVo.getRealPickTime(),2,"update",fromSystem);
                    handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",trackJsVo.getRealPickTime(),12,2,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getPlanArriveTime())){
                    handleTwoLevel(orderId,"预计送达时间","estimated time of delivery",state,"1",trackJsVo.getPlanArriveTime(),13,null,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getRealArriveTime())){
                    handleTwoLevel(orderId,"已送达","have been delivered",state,"1",trackJsVo.getRealArriveTime(),14,null,null,null,type,fromSystem);
                }/*else if(StringUtils.isNotEmpty(trackJsVo.getRealReturnTime())){

                }*/
            }else if("1".equals(shippingOrder.getClassEastandwest())&&"1".equals(shippingOrder.getIsconsolidation())){//回拼
               /* if(StringUtils.isNotEmpty(trackJsVo.getPickupBoxTime())){
                    handleTwoLevel(orderId,"已提箱","YTH",state,"1",trackJsVo.getPickupBoxTime(),10,null,"箱号:"+trackJsVo.getContainerNo(),type);
                }*/
                if(StringUtils.isNotEmpty(trackJsVo.getPlanPickTime())){
                    handleTwoLevel(orderId,"预计提货时间","estimated time of picking up cargo",state,"0",trackJsVo.getPlanPickTime(),11,null,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getRealPickTime())){
                    handleOneLevel(orderId,"已提货","have picked up cargo",state,trackJsVo.getRealPickTime(),2,type,fromSystem);
                    handleTwoLevel(orderId,"已提货","have picked up cargo",state,"0",trackJsVo.getRealPickTime(),12,2,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getPlanArriveTime())){
                    handleTwoLevel(orderId,"预计送达时间","estimated time of delivery",state,"1",trackJsVo.getPlanArriveTime(),13,2,null,null,type,fromSystem);
                }
                if(StringUtils.isNotEmpty(trackJsVo.getRealArriveTime())){
                    handleTwoLevel(orderId,"已送达","have been delivered",state,"1",trackJsVo.getRealArriveTime(),14,2,null,null,type,fromSystem);
                }/*else if(StringUtils.isNotEmpty(trackJsVo.getRealReturnTime())){

                }*/
            }
        } catch (IOException e) {
            log.error("监听集疏系统运踪信息失败：{}",e.toString(),e.getStackTrace());
        }
    }

    /**
     * 监听平台订舱运踪信息
     * @param channel
     * @param message
     */
    @RabbitListener(queues = "order_dynamic_queue_yz")
    public void getPtdcTrack(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.debug("获取平台订舱运踪信息shippingOrder：{}",new String(message.getBody()));
            ShippingOrder shippingOrder = FastJsonUtils.json2Bean(new String(message.getBody()), ShippingOrder.class);
            String orderId=shippingOrder.getOrderId();
            String exameTime=shippingOrder.getExameTime()==null?null:DateUtils.parseDateToStr("yyyy-MM-dd",shippingOrder.getExameTime());
            String creatTime=shippingOrder.getCreatedate()==null?null:DateUtils.parseDateToStr("yyyy-MM-dd",shippingOrder.getCreatedate());
            String examInfo=shippingOrder.getExamInfo();
            String orderNum=shippingOrder.getOrderNumber();
            String type="insert";
            String fromSystem="平台推送";
            String messageType=shippingOrder.getMessageType();
            //0待审核 1审核通过 2审核失败 3转待审核中 4转待审核成功 5转待审核失败 6取消托书 7托书修改 8删除
            //1245用examTime,别的用creattime
            /*if("0".equals(messageType)){
                handleTwoLevel(orderId,"待审核","unaudited",state,"1",creatTime,3,1,null,null,type,fromSystem);
            }else */if("1".equals(messageType)){
                handleOneLevel(orderId,"已订舱成功","space booked successfully",state,exameTime,1,type,fromSystem);
                handleTwoLevel(orderId,"审核通过","approved",state,"0",exameTime,4,1,"托书编号:"+orderNum,"entrustNumbers:"+orderNum,type,fromSystem);
            }/*else if("2".equals(messageType)){
                handleOneLevel(orderId,"订舱失败","audit failure",state,exameTime,1,type,fromSystem);
                handleTwoLevel(orderId,"审核失败","audit failure",state,"1",exameTime,5,1,"审核失败原因:"+examInfo,"audit failure reason:"+examInfo,type,fromSystem);
            }else if("3".equals(messageType)){
                handleTwoLevel(orderId,"转待审核","transfer to audit",state,"1",creatTime,6,1,null,null,type,fromSystem);
            }else if("4".equals(messageType)){
                handleOneLevel(orderId,"已订舱成功","space booked successfully",state,exameTime,1,type,fromSystem);
                handleTwoLevel(orderId,"转待审核成功","transfer to audit approval ",state,"0",exameTime,7,1,"托书编号:"+orderNum,"entrustNumbers:"+orderNum,type,fromSystem);
            }else if("5".equals(messageType)){
                handleTwoLevel(orderId,"转待审核失败","transfer to audit failure",state,"1",exameTime,7,1,"转待审核失败原因:"+examInfo,"transfer to audit failure reason:"+examInfo,type,fromSystem);
            }else if("6".equals(messageType)){
                handleTwoLevel(orderId,"取消托书","wtsCancle",state,"1",creatTime,2,1,null,null,type,fromSystem);
            }*/else if("7".equals(messageType)){
                Long shId =selectTwoId(orderId,4);
                if(shId!=null){
                    handleTwoLevel(orderId,"审核通过","approved",state,"0",exameTime,4,1,"托书编号:"+orderNum,"entrustNumbers:"+orderNum,"update",fromSystem);
                }
                Long zdshId =selectTwoId(orderId,7);
                if(zdshId!=null){
                    handleTwoLevel(orderId,"审核成功","transfer to audit approval",state,"0",exameTime,7,1,"托书编号:"+orderNum,"entrustNumbers:"+orderNum,"update",fromSystem);
                }
            }else if("8".equals(messageType)){
                trackOneLevelService.deleteTrackOneLevelByOrderId(orderId);
                trackTwoLevelService.deleteTrackTwoLevelByOrderId(orderId);
            }
        } catch (IOException e) {
            log.error("监听平台订舱运踪信息失败：{}",e.toString(),e.getStackTrace());
        }
    }

   public void handleOneLevel(String orderId,String nameZh,String nameEn,Integer state,
                           String time,Integer sort,String type,String fromSystem) {
       Long oneId =selectOneId(orderId,sort);
       ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
       int i=0;

       TrackOneLevel trackOneLevel=new TrackOneLevel();
       trackOneLevel.setOrderId(orderId);
       trackOneLevel.setNameZh(nameZh);
       trackOneLevel.setNameEn(nameEn);
       trackOneLevel.setState(state);
       trackOneLevel.setTime(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",DateUtils.parseDate(time)));
       trackOneLevel.setSort(sort);
       trackOneLevel.setFromSystem(fromSystem);

       if(oneId!=null){
           TrackOneLevel oneLevel=trackOneLevelService.selectTrackOneLevelById(oneId);
           //去整  已放箱/已提货  已入仓/已进站/已报关通过 已提箱/已送达
           if("0".equals(shippingOrder.getClassEastandwest())&&"0".equals(shippingOrder.getIsconsolidation())){
               //该一级节点已经有最后节点时间,不能进行更新和插入
               if("已放箱".equals(nameZh)&&"已提货".equals(oneLevel.getNameZh())){
                   i=1;
               }
               if("已入仓".equals(nameZh)&&("已进站".equals(oneLevel.getNameZh())||"已报关通过".equals(oneLevel.getNameZh()))){
                   i=1;
               }
               if("已提箱".equals(nameZh)&&"已送达".equals(oneLevel.getNameZh())){
                   i=1;
               }
               if("已进站".equals(nameZh)&&"已报关通过".equals(oneLevel.getNameZh())){
                   i=1;
               }
           }
           //去拼 已提货/已入仓  已提货/已送达
           else if("0".equals(shippingOrder.getClassEastandwest())&&"1".equals(shippingOrder.getIsconsolidation())){
               //该一级节点已经有最后节点时间,不能进行更新和插入
               if("已提货".equals(nameZh)&&("已入仓".equals(oneLevel.getNameZh())||"已送达".equals(oneLevel.getNameZh()))){
                   i=1;
               }
           }
           //回整 已放箱/已提货  已进站/单证审核通过  清关中/海关已放行  已提货/已送达
           else if("1".equals(shippingOrder.getClassEastandwest())&&"0".equals(shippingOrder.getIsconsolidation())){
               //该一级节点已经有最后节点时间,不能进行更新和插入
               if("已放箱".equals(nameZh)&&"已提货".equals(oneLevel.getNameZh())){
                   i=1;
               }
               if("已进站".equals(nameZh)&&"单证审核通过".equals(oneLevel.getNameZh())){
                   i=1;
               }
               if("清关中".equals(nameZh)&&"海关已放行".equals(oneLevel.getNameZh())){
                   i=1;
               }
               if("已提货".equals(nameZh)&&"已送达".equals(oneLevel.getNameZh())){
                   i=1;
               }
           }
           //回拼 已提货/已入仓/已进站  清关中/海关已放行/已拆箱 已提货/已送达
           else if("1".equals(shippingOrder.getClassEastandwest())&&"1".equals(shippingOrder.getIsconsolidation())){
               //该一级节点已经有最后节点时间,不能进行更新和插入
               if("已提货".equals(nameZh)&&("已入仓".equals(oneLevel.getNameZh())||"已进站".equals(oneLevel.getNameZh()))){
                   i=1;
               }
               if("清关中".equals(nameZh)&&("海关已放行".equals(oneLevel.getNameZh())||"已拆箱".equals(oneLevel.getNameZh()))){
                   i=1;
               }
               if("已提货".equals(nameZh)&&"已送达".equals(oneLevel.getNameZh())){
                   i=1;
               }
               if("已入仓".equals(nameZh)&&"已进站".equals(oneLevel.getNameZh())){
                   i=1;
               }
               if("海关已放行".equals(nameZh)&&"已拆箱".equals(oneLevel.getNameZh())){
                   i=1;
               }
           }
       }

        if("insert".equals(type)&&i==0){
            if(oneId!=null){
                trackOneLevel.setId(oneId);
                trackOneLevelService.updateTrackOneLevel(trackOneLevel);
            }else{
                trackOneLevelService.insertTrackOneLevel(trackOneLevel);
            }
        }else if("update".equals(type)&&i==0){
            if(oneId!=null){
                trackOneLevel.setId(oneId);
                trackOneLevelService.updateTrackOneLevel(trackOneLevel);
            }else{
                trackOneLevelService.insertTrackOneLevel(trackOneLevel);
            }

        }else if("delete".equals(type)&&oneId!=null){
            trackOneLevelService.deleteTrackOneLevelById(oneId);
        }
    }

    public void handleTwoLevel(String orderId,String nameZh,String nameEn,Integer state,String isCustom,
                            String time,Integer twoSort,Integer oneSort,String remark,String remarkEn,String type,String fromSystem) {
        Long oneId =selectOneId(orderId,oneSort);
        Long twoId =selectTwoId(orderId,twoSort);

        TrackTwoLevel trackTwoLevel=new TrackTwoLevel();
        trackTwoLevel.setOrderId(orderId);
        trackTwoLevel.setNameZh(nameZh);
        trackTwoLevel.setNameEn(nameEn);
        trackTwoLevel.setState(state);
        trackTwoLevel.setIsCustom(isCustom);
        trackTwoLevel.setTime(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",DateUtils.parseDate(time)));
        trackTwoLevel.setOneId(oneId);
        trackTwoLevel.setSort(twoSort);
        trackTwoLevel.setRemark(remark);
        trackTwoLevel.setRemarkEn(remarkEn);
        trackTwoLevel.setFromSystem(fromSystem);

        if("insert".equals(type)){
            if(twoId!=null){
                trackTwoLevel.setId(twoId);
                trackTwoLevelService.updateTrackTwoLevel(trackTwoLevel);
            }else{
                trackTwoLevelService.insertTrackTwoLevel(trackTwoLevel);
            }
            if(state==2&&oneId!=null){
                TrackOneLevel trackOneLevel=new TrackOneLevel();
                trackOneLevel.setState(2);
                trackOneLevel.setId(oneId);
                trackOneLevelService.updateTrackOneLevel(trackOneLevel);
            }
        }else if("update".equals(type)){
            if(twoId!=null){
                trackTwoLevel.setId(twoId);
                trackTwoLevelService.updateTrackTwoLevel(trackTwoLevel);
            }else{
                trackTwoLevelService.insertTrackTwoLevel(trackTwoLevel);
            }
        }else if("delete".equals(type)){
            trackTwoLevelService.deleteTrackTwoLevelById(twoId);
        }
    }

    public Long selectOneId(String orderId,Integer sort) {
        if(orderId!=null&&sort!=null){
            TrackOneLevel trackOneLevel=new TrackOneLevel();
            trackOneLevel.setSort(sort);
            trackOneLevel.setOrderId(orderId);
            Long oneId =trackOneLevelService.selectOneId(trackOneLevel);
            return oneId;
        }else{
            return null;
        }
    }

    public Long selectTwoId(String orderId,Integer sort) {
        TrackTwoLevel trackTwoLevel=new TrackTwoLevel();
        trackTwoLevel.setOrderId(orderId);
        trackTwoLevel.setSort(sort);
        Long twoId =trackTwoLevelService.selectTwoId(trackTwoLevel);
        return twoId;
    }
}
