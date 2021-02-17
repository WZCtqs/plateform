package com.szhbl.project.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szhbl.common.utils.ConvertUtils;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import com.szhbl.framework.config.rabbit.trackzy.OrderTrackDelConfig;
import com.szhbl.framework.config.rabbit.trackzy.PlatformTrackRabbitmqConfig;
import com.szhbl.framework.email.IMailService;
import com.szhbl.project.basic.domain.BaseGoodsnote;
import com.szhbl.project.basic.domain.DangerousGoods;
import com.szhbl.project.basic.mapper.BaseGoodsnoteMapper;
import com.szhbl.project.basic.mapper.DangerousGoodsMapper;
import com.szhbl.project.cabinarrangement.mapper.BusiShippingorderGoodsPcMapper;
import com.szhbl.project.cabinarrangement.mapper.BusiShippingorderPcMapper;
import com.szhbl.project.cabinarrangement.vo.px;
import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.documentcenter.service.IBusiStationService;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.track.OrderGoodsTrackDel;
import com.szhbl.project.order.domain.track.GoodsTrackMq;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.mapper.BusiGoodsTrackMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.*;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.mapper.TrackGoodsStatusMapper;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.mapper.BusiClassesMapper;
import com.szhbl.project.trains.service.IBusiClassesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.*;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private IBusiStationService busiStationService;
    @Autowired
    private IBusiClassesService busiClassesService;
    @Autowired
    private BusiShippingorderPcMapper busiShippingorderPcMapper;
    @Autowired
    private BusiShippingorderGoodsPcMapper busiShippingorderGoodsPcMapper;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private IBusiShippingorderApplyService busiShippingorderApplyService;
    @Autowired
    private IBusiShippingorderGoodsApplyService busiShippingorderGoodsApplyService;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private BaseGoodsnoteMapper baseGoodsnoteMapper;
    @Autowired
    private BusiClassesMapper busiClassesMapper;
    @Autowired
    private DangerousGoodsMapper dangerousGoodsMapper;
    @Autowired
    private IBusiShippingorderBackupService busiShippingorderBackupService;
    @Autowired
    private BusiGoodsTrackMapper busiGoodsTrackMapper;
    @Autowired
    private TrackGoodsStatusMapper trackGoodsStatusMapper;

    //托书信息对比（涉及询价）
    @Override
    public Map orderCompare(BusiShippingorders orderbackup, BusiShippingorderGoods goodsbackup, BusiShippingorders orderinfo, BusiShippingorderGoods goodsinfo)
    {
        String isPicktime = "1"; //提货时间是否修改 0是 1否
        String isClassCheck = "1"; //订舱组是否审核 0是 1否
        String ismailChange = "0"; //到站通知邮箱是否改变 0否 1是
        String isAddRecord = "0"; //是否需要插入订舱公告(提货时间修改，插入订舱公告) 0否 1是
        String editrecord = "";  //修改内容
        String classEastandwest = orderinfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
        String isconsolidation = orderinfo.getIsconsolidation();  //0整柜 1拼箱
        String bookingService = orderinfo.getBookingService(); //0门到门 1门到站 2站到站 3站到门
        BusiShippingorders orderold = busiShippingorderBackupService.selectBusiShippingorderBackupById(orderinfo.getOrderId());
        String language = orderinfo.getLanguage();
        Map orderres = new HashMap();
        if("en".equals(language)){
            String title  = "<th>Modified by："+orderinfo.getCreateusername()+",Modification time："+ DateUtils.parseStr(orderinfo.getCreatedate())+"</th>";
            //班列信息
            if(!StringUtils.equals(DateUtils.parseStr(orderinfo.getClassDate()),DateUtils.parseStr(orderbackup.getClassDate()))){
                editrecord = editrecord+"train date：from "+DateUtils.parseStr(orderbackup.getClassDate())+" to："+DateUtils.parseStr(orderinfo.getClassDate())+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(orderinfo.getOrderClassBh(),orderbackup.getOrderClassBh())){
                editrecord = editrecord+"Train number：from "+orderbackup.getOrderClassBh()+" to："+orderinfo.getOrderClassBh()+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(orderinfo.getClassNumber(),orderbackup.getClassNumber())){
                editrecord = editrecord+"train number：from "+orderbackup.getClassNumber()+" to："+orderinfo.getClassNumber()+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(orderinfo.getOrderNumber(),orderbackup.getOrderNumber())
                    && !(StringUtils.isBlank(orderinfo.getOrderNumber()) && StringUtils.isBlank(orderbackup.getOrderNumber()))){
                if(StringUtils.isNotNull(orderold)){
                    if(StringUtils.isNotEmpty(orderold.getOrderNumber())){
                        editrecord = editrecord+"Space number：from "+orderbackup.getOrderNumber()+" to："+orderinfo.getOrderNumber()+"<td>";
                        isAddRecord = "1";
                    }
                }
            }
            if(!StringUtils.equals(orderinfo.getZxNumber(),orderbackup.getZxNumber())
                    && !(StringUtils.isBlank(orderinfo.getZxNumber()) && StringUtils.isBlank(orderbackup.getZxNumber()))){
                editrecord = editrecord+"重箱舱位号：from "+orderbackup.getZxNumber()+" to："+orderinfo.getZxNumber()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getOrderUploadsite(),orderbackup.getOrderUploadsite())){
                editrecord = editrecord+"Loading station：from "+orderbackup.getOrderUploadsite()+" to："+orderinfo.getOrderUploadsite()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getOrderUnloadsite(),orderbackup.getOrderUnloadsite())){
                editrecord = editrecord+"Unloading station：from "+orderbackup.getOrderUnloadsite()+" to："+orderinfo.getOrderUnloadsite()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getBookingService(),orderbackup.getBookingService())){
                String BookingService1 = " empty ";
                String BookingService2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getBookingService())){
                    BookingService1 = (orderinfo.getBookingService()).equals("0")?"Door to Door":(orderinfo.getBookingService()).equals("1")?"Door to Station":(orderinfo.getBookingService()).equals("2")?"Station to Station":"Station to Door";
                }
                if(StringUtils.isNotEmpty(orderbackup.getBookingService())){
                    BookingService2 = (orderbackup.getBookingService()).equals("0")?"Door to Door":(orderbackup.getBookingService()).equals("1")?"Door to Station":(orderbackup.getBookingService()).equals("2")?"Station to Station":"Station to Door";
                }
                editrecord = editrecord+"Service：from "+BookingService2+" to："+BookingService1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getLimitation(),orderbackup.getLimitation())
                    && !(StringUtils.isBlank(orderinfo.getLimitation()) && StringUtils.isBlank(orderbackup.getLimitation()))){
                String limitation1 = " empty ";
                String limitation2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getLimitation())){
                    limitation1 = (orderinfo.getLimitation()).equals("0")?"Normal":"Urgent";
                }
                if(StringUtils.isNotEmpty(orderbackup.getLimitation())){
                    limitation2 = (orderbackup.getLimitation()).equals("0")?"Normal":"Urgent";
                }
                editrecord = editrecord+"Time：from "+limitation2+" to："+limitation1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getTruckType(),orderbackup.getTruckType())
                    && !(StringUtils.isBlank(orderinfo.getTruckType()) && StringUtils.isBlank(orderbackup.getTruckType()))){
                String truck1 = " empty ";
                String truck2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getTruckType())){
                    truck1 = (orderinfo.getTruckType()).equals("0")?"Ordinary Truck":(orderinfo.getTruckType()).equals("1")?"Dedicated Truck":"Transit Truck";
                }
                if(StringUtils.isNotEmpty(orderbackup.getTruckType())){
                    truck2 = (orderbackup.getTruckType()).equals("0")?"Ordinary Truck":(orderbackup.getTruckType()).equals("1")?"Dedicated Truck":"Transit Truck";
                }
                editrecord = editrecord+"Domestic road transport vehicles：from "+truck2+" to："+truck1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getStationid(),orderbackup.getStationid())
                    && !(StringUtils.isBlank(orderinfo.getStationid()) && StringUtils.isBlank(orderbackup.getStationid()))){
                if(StringUtils.isNotNull(orderold)){
                    if(StringUtils.isNotEmpty(orderold.getStationid())){
                        BusiStation stationInfoBack = busiStationService.selectBusiStationById(orderbackup.getStationid()); //旧id查询
                        BusiStation stationInfo = busiStationService.selectBusiStationById(orderinfo.getStationid()); //新id查询
                        String stationold = " empty ";
                        String station = " empty ";
                        if(StringUtils.isNotNull(stationInfoBack)){
                            stationold = stationInfoBack.getStatioin();
                        }
                        if(StringUtils.isNotNull(stationInfo)){
                            station = stationInfo.getStatioin();
                        }
                        editrecord = editrecord+"Station Address：from "+stationold+" to："+station+"<td>";
                    }
                }
            }
            if(!StringUtils.equals(orderinfo.getDictName(),orderbackup.getDictName())){
                editrecord = editrecord+"trade terms：from "+orderbackup.getDictName()+" to："+orderinfo.getDictName()+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getSitecost(),orderbackup.getSitecost())){
//            editrecord = editrecord+"站到站运费：：from "+orderbackup.getSitecost()+" to："+orderinfo.getSitecost()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getSitecostCurrency(),orderbackup.getSitecostCurrency())){
//            editrecord = editrecord+"站到站运费币种：：from "+orderbackup.getSitecostCurrency()+" to："+orderinfo.getSitecostCurrency()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getOrderIsticket(),orderbackup.getOrderIsticket())){
                String ticket1 = " empty ";
                String ticket2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getOrderIsticket())){
                    ticket1 = (orderinfo.getOrderIsticket()).equals("0")?" no  ":" yes ";
                }
                if(StringUtils.isNotEmpty(orderbackup.getOrderIsticket())){
                    ticket2 = (orderbackup.getOrderIsticket()).equals("0")?" no  ":" yes ";
                }
                editrecord = editrecord+" invoice or not：from "+ticket2+" to："+ticket1+"<td>";
            }
            if(orderinfo.getPutoffClass() != orderbackup.getPutoffClass()){
                String PutoffClass1 = " empty ";
                String PutoffClass2 = " empty ";
                if((orderinfo.getPutoffClass())!=null){
                    PutoffClass1 = (orderinfo.getPutoffClass())==0?" yes ":" no  ";
                }
                if((orderbackup.getPutoffClass())!=null){
                    PutoffClass2 = (orderbackup.getPutoffClass())==0?" yes ":" no  ";
                }
                editrecord = editrecord+" advanced train or not：from "+PutoffClass2+" to："+PutoffClass1+"<td>";
            }
            //订舱方
            if(!StringUtils.equals(orderinfo.getClientUnit(),orderbackup.getClientUnit())){
                editrecord = editrecord+"booking party：from "+orderbackup.getClientUnit()+" to："+orderinfo.getClientUnit()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientEmail(),orderbackup.getClientEmail())){
                editrecord = editrecord+"contact email：from "+orderbackup.getClientEmail()+" to："+orderinfo.getClientEmail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientContacts(),orderbackup.getClientContacts())){
                editrecord = editrecord+"contact person：from "+orderbackup.getClientContacts()+" to："+orderinfo.getClientContacts()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientTel(),orderbackup.getClientTel())){
                editrecord = editrecord+"contact phone：from "+orderbackup.getClientTel()+" to："+orderinfo.getClientTel()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientAddress(),orderbackup.getClientAddress())){
                editrecord = editrecord+"contact address：from "+orderbackup.getClientAddress()+" to："+orderinfo.getClientAddress()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientYwNumber(),orderbackup.getClientYwNumber())){
                editrecord = editrecord+"POA No：from "+orderbackup.getClientYwNumber()+" to："+orderinfo.getClientYwNumber()+"<td>";
            }
            //拼箱
            if(!StringUtils.equals(orderinfo.getGoodsCannotpileup(),orderbackup.getGoodsCannotpileup())
                    && !(StringUtils.isBlank(orderinfo.getGoodsCannotpileup()) && StringUtils.isBlank(orderbackup.getGoodsCannotpileup()))){
                if(isconsolidation.equals("1")){  //拼箱
                    String pileup1 = " empty ";
                    String pileup2 = " empty ";
                    if(StringUtils.isNotEmpty(orderinfo.getGoodsCannotpileup())){
                        pileup1 = (orderinfo.getGoodsCannotpileup()).equals("1")?" yes ":" no  ";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getGoodsCannotpileup())){
                        pileup2 = (orderbackup.getGoodsCannotpileup()).equals("1")?" yes ":" no  ";
                    }
                    editrecord = editrecord+"stackable or not：from "+pileup2+" to："+pileup1+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getGoodsFragile(),orderbackup.getGoodsFragile())
                    && !(StringUtils.isBlank(orderinfo.getGoodsFragile()) && StringUtils.isBlank(orderbackup.getGoodsFragile()))){
                if(isconsolidation.equals("1")){  //拼箱
                    String fragile1 = " empty ";
                    String fragile2 = " empty ";
                    if(StringUtils.isNotEmpty(orderinfo.getGoodsFragile())){
                        fragile1 = (orderinfo.getGoodsFragile()).equals("1")?" yes ":" no  ";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getGoodsFragile())){
                        fragile2 = (orderbackup.getGoodsFragile()).equals("1")?" yes ":" no  ";
                    }
                    editrecord = editrecord+"Fragile or not：from "+fragile2+" to："+fragile1+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getGoodsGeneral(),orderbackup.getGoodsGeneral())
                    && !(StringUtils.isBlank(orderinfo.getGoodsGeneral()) && StringUtils.isBlank(orderbackup.getGoodsGeneral()))){
                if(isconsolidation.equals("1")){  //拼箱
                    String general1 = " empty ";
                    String general2 = " empty ";
                    if(StringUtils.isNotEmpty(orderinfo.getGoodsGeneral())){
                        general1 = (orderinfo.getGoodsGeneral()).equals("1")?" yes ":" no  ";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getGoodsGeneral())){
                        general2 = (orderbackup.getGoodsGeneral()).equals("1")?" yes ":" no  ";
                    }
                    editrecord = editrecord+"overlong and overweight or not：from "+general2+" to："+general1+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getClientOrderBindingaddress(),orderbackup.getClientOrderBindingaddress())
                    && !(StringUtils.isBlank(orderinfo.getClientOrderBindingaddress()) && StringUtils.isBlank(orderbackup.getClientOrderBindingaddress()))){
                editrecord = editrecord+"place of customs declaration：from"+orderbackup.getClientOrderBindingaddress()+" to："+orderinfo.getClientOrderBindingaddress()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientOrderBindingway(),orderbackup.getClientOrderBindingway())){
                String bindingway1 = " empty ";
                String bindingway2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getClientOrderBindingway())){
                    bindingway1 = (orderinfo.getClientOrderBindingway()).equals("0")?" yes ":" no  ";
                }
                if(StringUtils.isNotEmpty(orderbackup.getClientOrderBindingway())){
                    bindingway2 = (orderbackup.getClientOrderBindingway()).equals("0")?" yes ":" no  ";
                }
                editrecord = editrecord+"Entrust ZIH for customs declaration：from "+bindingway2+" to："+bindingway1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientBgCost(),orderbackup.getClientBgCost())
                    && !(StringUtils.isBlank(orderinfo.getClientBgCost()) && StringUtils.isBlank(orderbackup.getClientBgCost()))){
                String bgcost1 = StringUtils.isNotEmpty(orderinfo.getClientBgCost())?orderinfo.getClientBgCost():" empty ";
                String bgcost2 = StringUtils.isNotEmpty(orderbackup.getClientBgCost())?orderbackup.getClientBgCost():" empty ";
                editrecord = editrecord+"Customs Declaration Fee (RMB)：from "+bgcost2+" to："+bgcost1+"<td>";
            }
            //整柜
            if(!StringUtils.equals(orderinfo.getContainerBoxamount(),orderbackup.getContainerBoxamount())){
                if(isconsolidation.equals("0")){
                    editrecord = editrecord+"Container Quantity：from "+orderbackup.getContainerBoxamount()+" to："+orderinfo.getContainerBoxamount()+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getContainerType(),orderbackup.getContainerType())){
                if(isconsolidation.equals("0")){
                    editrecord = editrecord+"ContainerType：from "+orderbackup.getContainerType()+" to："+orderinfo.getContainerType()+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getOrderAuditBelongto(),orderbackup.getOrderAuditBelongto())){
                if(isconsolidation.equals("0")){
                    String belongto1 = " empty ";
                    String belongto2 = " empty ";
                    if(StringUtils.isNotEmpty(orderinfo.getOrderAuditBelongto())){
                        belongto1 = (orderinfo.getOrderAuditBelongto()).equals("0")?"委托ZIH":(orderinfo.getOrderAuditBelongto()).equals("1")?"自备":(orderinfo.getOrderAuditBelongto()).equals("2")?"自备铁路箱":"自备非铁路箱";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getOrderAuditBelongto())){
                        belongto2 = (orderbackup.getOrderAuditBelongto()).equals("0")?"委托ZIH":(orderbackup.getOrderAuditBelongto()).equals("1")?"自备":(orderbackup.getOrderAuditBelongto()).equals("2")?"自备铁路箱":"自备非铁路箱";
                    }
                    editrecord = editrecord+"Container Ownership：from "+belongto2+" to："+belongto1+"<td>";
                }
            }
            //跟单
            if(!StringUtils.equals(orderinfo.getOrderMerchandiser(),orderbackup.getOrderMerchandiser())){
                editrecord = editrecord+"merchandiser：from "+orderbackup.getOrderMerchandiser()+" to："+orderinfo.getOrderMerchandiser()+"<td>";
            }
            //发货方
            if(!StringUtils.equals(orderinfo.getShipOrederName(),orderbackup.getShipOrederName())){
                editrecord = editrecord+"consignor：from "+orderbackup.getShipOrederName()+" to："+orderinfo.getShipOrederName()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrederContacts(),orderbackup.getShipOrederContacts())){
                editrecord = editrecord+"contact person：from "+orderbackup.getShipOrederContacts()+" to："+orderinfo.getShipOrederContacts()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrederPhone(),orderbackup.getShipOrederPhone())){
                editrecord = editrecord+"contact phone：from "+orderbackup.getShipOrederPhone()+" to："+orderinfo.getShipOrederPhone()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrederEmail(),orderbackup.getShipOrederEmail())){
                editrecord = editrecord+"e-mail：from "+orderbackup.getShipOrederEmail()+" to："+orderinfo.getShipOrederEmail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrederAddress(),orderbackup.getShipOrederAddress())){
                editrecord = editrecord+"contact address：from "+orderbackup.getShipOrederAddress()+" to："+orderinfo.getShipOrederAddress()+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getShipmentPlace(),orderbackup.getShipmentPlace())
//                && !(StringUtils.isBlank(orderinfo.getShipmentPlace()) && StringUtils.isBlank(orderbackup.getShipmentPlace()))){
//            editrecord = editrecord+"发货地：：from "+orderbackup.getShipmentPlace()+" to："+orderinfo.getShipmentPlace()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getShipOrderBinningway(),orderbackup.getShipOrderBinningway())
                    && !(StringUtils.isBlank(orderinfo.getShipOrderBinningway()) && StringUtils.isBlank(orderbackup.getShipOrderBinningway()))){
                String shipway1 = " empty ";
                String shipway2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getShipOrderBinningway())){
                    shipway1 = (orderinfo.getShipOrderBinningway()).equals("0")?"ZIH delivery":"self delivery";
                }
                if(StringUtils.isNotEmpty(orderbackup.getShipOrderBinningway())){
                    shipway2 = (orderbackup.getShipOrderBinningway()).equals("0")?"ZIH delivery":"self delivery";
                }
                editrecord = editrecord+"：delivery type：from "+shipway2+" to："+shipway1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipThTypeId(),orderbackup.getShipThTypeId())
                    && !(StringUtils.isBlank(orderinfo.getShipThTypeId()) && StringUtils.isBlank(orderbackup.getShipThTypeId()))){
                String thtype1 = " empty ";
                String thtype2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getShipThTypeId())){
                    thtype1 = (orderinfo.getShipThTypeId()).equals("0")?"FCL to CY":"LCL to CY";
                }
                if(StringUtils.isNotEmpty(orderbackup.getShipThTypeId())){
                    thtype2 = (orderbackup.getShipThTypeId()).equals("0")?"FCL to CY":"LCL to CY";
                }
                editrecord = editrecord+"DeliveryMode：from "+thtype2+" to："+thtype1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipZsTypeId(),orderbackup.getShipZsTypeId())
                    && !(StringUtils.isBlank(orderinfo.getShipZsTypeId()) && StringUtils.isBlank(orderbackup.getShipZsTypeId()))){
                String zstype1 = " empty ";
                String zstype2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getShipZsTypeId())){
                    zstype1 = (orderinfo.getShipZsTypeId()).equals("0")?"LCL to CY":"FCL to CY";
                }
                if(StringUtils.isNotEmpty(orderbackup.getShipZsTypeId())){
                    zstype2 = (orderbackup.getShipZsTypeId()).equals("0")?"LCL to CY":"FCL to CY";
                }
                editrecord = editrecord+"self delivery type：from "+zstype2+" to："+zstype1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipFhSite(),orderbackup.getShipFhSite())
                    && !(StringUtils.isBlank(orderinfo.getShipFhSite()) && StringUtils.isBlank(orderbackup.getShipFhSite()))){
                String qcsite1= StringUtils.isNotEmpty(orderbackup.getShipFhSite())?orderbackup.getShipFhSite():" empty ";
                String qcsite2= StringUtils.isNotEmpty(orderinfo.getShipFhSite())?orderinfo.getShipFhSite():" empty ";
                editrecord = editrecord+"place of the pick up containers：from "+qcsite1+" to："+qcsite2+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipHyd(),orderbackup.getShipHyd())
                    && !(StringUtils.isBlank(orderinfo.getShipHyd()) && StringUtils.isBlank(orderbackup.getShipHyd()))){
                String hcsite1 = StringUtils.isNotEmpty(orderbackup.getShipHyd())?orderbackup.getShipHyd():" empty ";
                String hcsite2 = StringUtils.isNotEmpty(orderinfo.getShipHyd())?orderinfo.getShipHyd():" empty ";
                editrecord = editrecord+"place of the pick up containers：from "+hcsite1+" to："+hcsite2+"<td>";
            }
            if(!StringUtils.equals(DateUtils.parseStr(orderinfo.getShipOrderSendtime()),DateUtils.parseStr(orderbackup.getShipOrderSendtime()))
                    && !(StringUtils.isBlank(DateUtils.parseStr(orderinfo.getShipOrderSendtime())) && StringUtils.isBlank(DateUtils.parseStr(orderbackup.getShipOrderSendtime())))){
                editrecord = editrecord+"self delivery time：from "+DateUtils.parseStr(orderbackup.getShipOrderSendtime())+" to："+DateUtils.parseStr(orderinfo.getShipOrderSendtime())+"<td>";
            }
            if(!StringUtils.equals(DateUtils.parseStr(orderinfo.getShipOrderUnloadtime()),DateUtils.parseStr(orderbackup.getShipOrderUnloadtime()))
                    && !(StringUtils.isBlank(DateUtils.parseStr(orderinfo.getShipOrderUnloadtime())) && StringUtils.isBlank(DateUtils.parseStr(orderbackup.getShipOrderUnloadtime())))){
                editrecord = editrecord+"Pick up time：from "+DateUtils.parseStr(orderbackup.getShipOrderUnloadtime())+" to："+DateUtils.parseStr(orderinfo.getShipOrderUnloadtime())+"<td>";
                isAddRecord = "1";
//                if("0".equals(classEastandwest)){
//                    isPicktime = "0";
//                }
            }
            if(!StringUtils.equals(orderinfo.getReceiveXgCost(),orderbackup.getReceiveXgCost())
                    && !(StringUtils.isBlank(orderinfo.getReceiveXgCost()) && StringUtils.isBlank(orderbackup.getReceiveXgCost()))){
                editrecord = editrecord+"箱管费：from "+orderbackup.getReceiveXgCost()+" to："+orderinfo.getReceiveXgCost()+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getPickUpBoxFee(),orderbackup.getPickUpBoxFee())
//                && !(StringUtils.isBlank(orderinfo.getPickUpBoxFee()) && StringUtils.isBlank(orderbackup.getPickUpBoxFee()))){
//            editrecord = editrecord+"提箱费：：from "+orderbackup.getPickUpBoxFee()+" to："+orderinfo.getPickUpBoxFee()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getShipOrderUnloadcontacts(),orderbackup.getShipOrderUnloadcontacts())){
                editrecord = editrecord+"pick up contacts：from "+orderbackup.getShipOrderUnloadcontacts()+" to："+orderinfo.getShipOrderUnloadcontacts()+"<td>";
                isAddRecord = "1";
            }
            if(!StringUtils.equals(orderinfo.getShipOrderUnloadway(),orderbackup.getShipOrderUnloadway())){
                editrecord = editrecord+"phone：from "+orderbackup.getShipOrderUnloadway()+" to："+orderinfo.getShipOrderUnloadway()+"<td>";
                isAddRecord = "1";
            }
            if(!StringUtils.equals(orderinfo.getShipOrderUnloadwayEmail(),orderbackup.getShipOrderUnloadwayEmail())){
                editrecord = editrecord+"pick up email：from "+orderbackup.getShipOrderUnloadwayEmail()+" to："+orderinfo.getShipOrderUnloadwayEmail()+"<td>";
                isAddRecord = "1";
            }
            if(!StringUtils.equals(orderinfo.getShipOrderUnloadaddress(),orderbackup.getShipOrderUnloadaddress())){
                editrecord = editrecord+"pick up address：from "+orderbackup.getShipOrderUnloadaddress()+" to："+orderinfo.getShipOrderUnloadaddress()+"<td>";
                isAddRecord = "1";
            }
            if(!StringUtils.equals(orderinfo.getDetailedAddress(),orderbackup.getDetailedAddress())){
                editrecord = editrecord+"detailed pick up address：from "+orderbackup.getDetailedAddress()+" to："+orderinfo.getDetailedAddress()+"<td>";
                isAddRecord = "1";
            }
//        if(!StringUtils.equals(orderinfo.getShipThCostNo(),orderbackup.getShipThCostNo())
//                && !(StringUtils.isBlank(orderinfo.getShipThCostNo()) && StringUtils.isBlank(orderbackup.getShipThCostNo()))){
//            editrecord = editrecord+"提货费报价编号：：from "+orderbackup.getShipThCostNo()+" to："+orderinfo.getShipThCostNo()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getShipThCost(),orderbackup.getShipThCost())
//                && !(StringUtils.isBlank(orderinfo.getShipThCost()) && StringUtils.isBlank(orderbackup.getShipThCost()))){
//            editrecord = editrecord+"提货费用：：from "+orderbackup.getShipThCost()+" to："+orderinfo.getShipThCost()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getZxThcostCurrency(),orderbackup.getZxThcostCurrency())
//                && !(StringUtils.isBlank(orderinfo.getZxThcostCurrency()) && StringUtils.isBlank(orderbackup.getZxThcostCurrency()))){
//            editrecord = editrecord+"提货费币种：：from "+orderbackup.getZxThcostCurrency()+" to："+orderinfo.getZxThcostCurrency()+"<td>";
//        }
            //收货方
            if(!StringUtils.equals(orderinfo.getReceiveOrderName(),orderbackup.getReceiveOrderName())){
                editrecord = editrecord+"consignee：from "+orderbackup.getReceiveOrderName()+" to："+orderinfo.getReceiveOrderName()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderPhone(),orderbackup.getReceiveOrderPhone())){
                editrecord = editrecord+"phone：from "+orderbackup.getReceiveOrderPhone()+" to："+orderinfo.getReceiveOrderPhone()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderContacts(),orderbackup.getReceiveOrderContacts())){
                editrecord = editrecord+"contact：from "+orderbackup.getReceiveOrderContacts()+" to："+orderinfo.getReceiveOrderContacts()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderAddress(),orderbackup.getReceiveOrderAddress())){
                editrecord = editrecord+"Address：from "+orderbackup.getReceiveOrderAddress()+" to："+orderinfo.getReceiveOrderAddress()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderEmail(),orderbackup.getReceiveOrderEmail())){
                editrecord = editrecord+"email：from "+orderbackup.getReceiveOrderEmail()+" to："+orderinfo.getReceiveOrderEmail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderIsclearance(),orderbackup.getReceiveOrderIsclearance())){
                String Isclearance1 = " empty ";
                String Isclearance2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getReceiveOrderIsclearance())){
                    Isclearance1 = (orderinfo.getReceiveOrderIsclearance()).equals("1")?" yes " : " no  ";
                }
                if(StringUtils.isNotEmpty(orderbackup.getReceiveOrderIsclearance())){
                    Isclearance2 = (orderbackup.getReceiveOrderIsclearance()).equals("1")?" yes " : " no  ";
                }
                editrecord = editrecord+"：Entrust ZIH for customs clearance：from "+Isclearance2+" to："+Isclearance1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveQgCost(),orderbackup.getReceiveQgCost())
                    && !(StringUtils.isBlank(orderinfo.getReceiveQgCost()) && StringUtils.isBlank(orderbackup.getReceiveQgCost()))){
                String qgcost1 = StringUtils.isNotEmpty(orderinfo.getReceiveQgCost())?orderinfo.getReceiveQgCost():" empty ";
                String qgcost2 = StringUtils.isNotEmpty(orderbackup.getReceiveQgCost())?orderbackup.getReceiveQgCost():" empty ";
                editrecord = editrecord+"customs clearance fee：from "+qgcost2+" to："+qgcost1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderIspart(),orderbackup.getReceiveOrderIspart())){
                String Ispart1 = " empty ";
                String Ispart2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getReceiveOrderIspart())){
                    Ispart1 = (orderinfo.getReceiveOrderIspart()).equals("1")?" yes " : " no  ";
                }
                if(StringUtils.isNotEmpty(orderbackup.getReceiveOrderIspart())){
                    Ispart2 = (orderbackup.getReceiveOrderIspart()).equals("1")?" yes " : " no  ";
                }
                editrecord = editrecord+"delivery by ZIH：from "+Ispart2+" to："+Ispart1+"<td>";
            }
            if("0".equals(isconsolidation)){
                if(!StringUtils.equals(orderinfo.getDistributionType(),orderbackup.getDistributionType())
                        && !(StringUtils.isBlank(orderinfo.getDistributionType()) && StringUtils.isBlank(orderbackup.getDistributionType()))){
                    String Isparttype1 = " empty ";
                    String Isparttype2 = " empty ";
                    if(StringUtils.isNotEmpty(orderinfo.getDistributionType())){
                        Isparttype1 = (orderinfo.getDistributionType()).equals("1")?" 散货派送" : " 整柜派送  ";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getDistributionType())){
                        Isparttype2 = (orderbackup.getDistributionType()).equals("1")?" 散货派送 " : " 整柜派送  ";
                    }
                    editrecord = editrecord+"delivery type：from "+Isparttype2+" to："+Isparttype1+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderZihcontacts(),orderbackup.getReceiveOrderZihcontacts())){
                editrecord = editrecord+"delivery contact：from "+orderbackup.getReceiveOrderZihcontacts()+" to："+orderinfo.getReceiveOrderZihcontacts()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderZihtel(),orderbackup.getReceiveOrderZihtel())){
                editrecord = editrecord+"delivery phone：from "+orderbackup.getReceiveOrderZihtel()+" to："+orderinfo.getReceiveOrderZihtel()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderZihemail(),orderbackup.getReceiveOrderZihemail())){
                editrecord = editrecord+"e-mail：from "+orderbackup.getReceiveOrderZihemail()+" to："+orderinfo.getReceiveOrderZihemail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderPartaddress(),orderbackup.getReceiveOrderPartaddress())){
                editrecord = editrecord+"Actual delivery address：from "+orderbackup.getReceiveOrderPartaddress()+" to："+orderinfo.getReceiveOrderPartaddress()+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getReceiptPlace(),orderbackup.getReceiptPlace())
//                && !(StringUtils.isBlank(orderinfo.getReceiptPlace()) && StringUtils.isBlank(orderbackup.getReceiptPlace()))){
//            editrecord = editrecord+"收货地：：from "+orderbackup.getReceiptPlace()+" to："+orderinfo.getReceiptPlace()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getReceiveHxAddress(),orderbackup.getReceiveHxAddress())
                    && !(StringUtils.isBlank(orderinfo.getReceiveHxAddress()) && StringUtils.isBlank(orderbackup.getReceiveHxAddress()))){
                String hxsite1 = StringUtils.isNotEmpty(orderbackup.getReceiveHxAddress())?orderbackup.getReceiveHxAddress():" empty ";
                String hxsite2 = StringUtils.isNotEmpty(orderinfo.getReceiveHxAddress())?orderinfo.getReceiveHxAddress():" empty ";
                editrecord = editrecord+"place of return containers：from "+hxsite1+" to："+hxsite2+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getReceiveShCostId(),orderbackup.getReceiveShCostId())
//                && !(StringUtils.isBlank(orderinfo.getReceiveShCostId()) && StringUtils.isBlank(orderbackup.getReceiveShCostId()))){
//            editrecord = editrecord+"送货报价编号：：from "+orderbackup.getReceiveShCostId()+" to："+orderinfo.getReceiveShCostId()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getReceiveShCost(),orderbackup.getReceiveShCost())
//                && !(StringUtils.isBlank(orderinfo.getReceiveShCost()) && StringUtils.isBlank(orderbackup.getReceiveShCost()))){
//            editrecord = editrecord+"送货费用：：from "+orderbackup.getReceiveShCost()+" to："+orderinfo.getReceiveShCost()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getShCostcurrency(),orderbackup.getShCostcurrency())
//                && !(StringUtils.isBlank(orderinfo.getShCostcurrency()) && StringUtils.isBlank(orderbackup.getShCostcurrency()))){
//            editrecord = editrecord+"送货费币种：：from "+orderbackup.getShCostcurrency()+" to："+orderinfo.getShCostcurrency()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getReturnBoxFee(),orderbackup.getReturnBoxFee())
//                && !(StringUtils.isBlank(orderinfo.getReturnBoxFee()) && StringUtils.isBlank(orderbackup.getReturnBoxFee()))){
//            editrecord = editrecord+"还箱费：：from "+orderbackup.getReturnBoxFee()+" to："+orderinfo.getReturnBoxFee()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getReceiveOrderReceiveemail(),orderbackup.getReceiveOrderReceiveemail())){
                editrecord = editrecord+"E-mail to receive tracking information：from "+orderbackup.getReceiveOrderReceiveemail()+" to："+orderinfo.getReceiveOrderReceiveemail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderMail(),orderbackup.getReceiveOrderMail())){
                editrecord = editrecord+"E-mail to receive arrival notice：from "+orderbackup.getReceiveOrderMail()+" to："+orderinfo.getReceiveOrderMail()+"<td>";
                ismailChange = "1";
            }
            //俄线
            if(!StringUtils.equals(orderinfo.getConsignorc(),orderbackup.getConsignorc())){
                editrecord = editrecord+"hinese shipper：from "+orderbackup.getConsignorc()+" to："+orderinfo.getConsignorc()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getEduty(),orderbackup.getEduty())){
                editrecord = editrecord+"ИНН TFN：from "+orderbackup.getEduty()+" to："+orderinfo.getEduty()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getEtxName(),orderbackup.getEtxName())){
                editrecord = editrecord+"The name of the company or individual who bears the costs of the supervision zone：from "+orderbackup.getEtxName()+" to："+orderinfo.getEtxName()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getEconsignorstate(),orderbackup.getEconsignorstate())){
                editrecord = editrecord+"shipper statment：from "+orderbackup.getEconsignorstate()+" to："+orderinfo.getEconsignorstate()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getEtxCompany(),orderbackup.getEtxCompany())){
                editrecord = editrecord+"Name of actual consignee：from "+orderbackup.getEtxCompany()+" to："+orderinfo.getEtxCompany()+"<td>";
            }
            //货物
            if(!StringUtils.equals(goodsinfo.getGoodsMark(),goodsbackup.getGoodsMark())){
                editrecord = editrecord+"Mark：from "+goodsbackup.getGoodsMark()+" to："+goodsinfo.getGoodsMark()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsName(),goodsbackup.getGoodsName())){
                editrecord = editrecord+"Cargo descriptions in Chinese：from "+goodsbackup.getGoodsName()+" to："+goodsinfo.getGoodsName()+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsEnName(),goodsbackup.getGoodsEnName())){
                editrecord = editrecord+"Cargo descriptions in English：from "+goodsbackup.getGoodsEnName()+" to："+goodsinfo.getGoodsEnName()+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsReport(),goodsbackup.getGoodsReport())){
                editrecord = editrecord+"Customs Declaration HS in Europe：from "+goodsbackup.getGoodsReport()+" to："+goodsinfo.getGoodsReport()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsInReport(),goodsbackup.getGoodsInReport())){
                editrecord = editrecord+"Customs Declaration HS in China：from "+goodsbackup.getGoodsInReport()+" to："+goodsinfo.getGoodsInReport()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsClearance(),goodsbackup.getGoodsClearance())){
                editrecord = editrecord+"Customs Clearance HS in China：from "+goodsbackup.getGoodsClearance()+" to："+goodsinfo.getGoodsClearance()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsOutClearance(),goodsbackup.getGoodsOutClearance())){
                editrecord = editrecord+"Customs Clearance HS in Europe：from "+goodsbackup.getGoodsOutClearance()+" to："+goodsinfo.getGoodsOutClearance()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsPacking(),goodsbackup.getGoodsPacking())){
                editrecord = editrecord+"outer packing form：from "+goodsbackup.getGoodsPacking()+" to："+goodsinfo.getGoodsPacking()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsNumber(),goodsbackup.getGoodsNumber())){
                editrecord = editrecord+"quantity of outer packing：from "+goodsbackup.getGoodsNumber()+" to："+goodsinfo.getGoodsNumber()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsCbm(),goodsbackup.getGoodsCbm())){
                editrecord = editrecord+"Total Volume：from "+goodsbackup.getGoodsCbm()+" to："+goodsinfo.getGoodsCbm()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsKgs(),goodsbackup.getGoodsKgs())){
                editrecord = editrecord+"Total Weight：from "+goodsbackup.getGoodsKgs()+" to："+goodsinfo.getGoodsKgs()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsStandard(),goodsbackup.getGoodsStandard())){
                editrecord = editrecord+"规格：from "+goodsbackup.getGoodsStandard()+" to："+goodsinfo.getGoodsStandard()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getRemark(),goodsbackup.getRemark())
                    && !(StringUtils.isBlank(goodsinfo.getRemark()) && StringUtils.isBlank(goodsbackup.getRemark()))){
                String remark1 = StringUtils.isNotEmpty(goodsinfo.getRemark())?goodsinfo.getRemark():" empty ";
                String remark2 = StringUtils.isNotEmpty(goodsbackup.getRemark())?goodsbackup.getRemark():" empty ";
                editrecord = editrecord+"remarks：from "+remark2+" to："+remark1+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsbz(),goodsbackup.getGoodsbz())
                    && !(StringUtils.isBlank(goodsinfo.getGoodsbz()) && StringUtils.isBlank(goodsbackup.getGoodsbz()))){
                if(StringUtils.isNotNull(orderold)){
                    String goodsbz1 = StringUtils.isNotEmpty(goodsinfo.getGoodsbz())?goodsinfo.getGoodsbz():" empty ";
                    String goodsbz2 = StringUtils.isNotEmpty(goodsbackup.getGoodsbz())?goodsbackup.getGoodsbz():" empty ";
                    editrecord = editrecord+"goods remarks：from "+goodsbz2+" to："+goodsbz1+"<td>";
                }
            }
            if(!StringUtils.equals(goodsinfo.getHsbz(),goodsbackup.getHsbz())
                    && !(StringUtils.isBlank(goodsinfo.getHsbz()) && StringUtils.isBlank(goodsbackup.getHsbz()))){
                if(StringUtils.isNotNull(orderold)){
                    String hsbz1 = StringUtils.isNotEmpty(goodsinfo.getHsbz())?goodsinfo.getHsbz():" empty ";
                    String hsbz2 = StringUtils.isNotEmpty(goodsbackup.getHsbz())?goodsbackup.getHsbz():" empty ";
                    editrecord = editrecord+"HS remarks：from "+hsbz2+" to："+hsbz1+"<td>";
                }
            }
            if(!StringUtils.equals(goodsinfo.getGoodsIsscheme(),goodsbackup.getGoodsIsscheme())){
                String Isscheme1 = " empty ";
                String Isscheme2 = " empty ";
                if(StringUtils.isNotEmpty(goodsinfo.getGoodsIsscheme())){
                    Isscheme1 = (goodsinfo.getGoodsIsscheme()).equals("0")?" no  " : " yes ";
                }
                if(StringUtils.isNotEmpty(goodsbackup.getGoodsIsscheme())){
                    Isscheme2 = (goodsbackup.getGoodsIsscheme()).equals("0")?" no  " : " yes ";
                }
                editrecord = editrecord+"need loading plan or not：from "+Isscheme2+" to："+Isscheme1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrderIsdispatch(),orderbackup.getShipOrderIsdispatch())){
                String Isdispatch1 = " empty ";
                String Isdispatch2 = " empty ";
                if(StringUtils.isNotEmpty(orderinfo.getShipOrderIsdispatch())){
                    Isdispatch1 = (orderinfo.getShipOrderIsdispatch()).equals("1")?" yes " : " no  ";
                }
                if(StringUtils.isNotEmpty(orderbackup.getShipOrderIsdispatch())){
                    Isdispatch2 = (orderbackup.getShipOrderIsdispatch()).equals("1")?" yes " : " no  ";
                }
                editrecord = editrecord+" need supervisor or not：from "+Isdispatch2+" to："+Isdispatch1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipJzCost(),orderbackup.getShipJzCost())
                    && !(StringUtils.isBlank(orderinfo.getShipJzCost()) && StringUtils.isBlank(orderbackup.getShipJzCost()))){
                editrecord = editrecord+"supervisor charges：from "+orderbackup.getShipJzCost()+" to："+orderinfo.getShipJzCost()+"<td>";
            }
            if(StringUtils.isNotEmpty(editrecord)){
                editrecord =title + editrecord +"<###>";
                editrecord = StringUtils.replace(editrecord,"null"," empty ");
            }
        }else{
            String title  = "<th>修改人："+orderinfo.getCreateusername()+",修改时间："+ DateUtils.parseStr(orderinfo.getCreatedate())+"</th>";
            //班列信息
            if(!StringUtils.equals(DateUtils.parseStr(orderinfo.getClassDate()),DateUtils.parseStr(orderbackup.getClassDate()))){
                editrecord = editrecord+"班列日期：由"+DateUtils.parseStr(orderbackup.getClassDate())+"修改为："+DateUtils.parseStr(orderinfo.getClassDate())+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(orderinfo.getOrderClassBh(),orderbackup.getOrderClassBh())){
                editrecord = editrecord+"班列编号：由"+orderbackup.getOrderClassBh()+"修改为"+orderinfo.getOrderClassBh()+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(orderinfo.getClassNumber(),orderbackup.getClassNumber())){
                editrecord = editrecord+"班列号：由"+orderbackup.getClassNumber()+"修改为"+orderinfo.getClassNumber()+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(orderinfo.getOrderNumber(),orderbackup.getOrderNumber())
                    && !(StringUtils.isBlank(orderinfo.getOrderNumber()) && StringUtils.isBlank(orderbackup.getOrderNumber()))){
                if(StringUtils.isNotNull(orderold)){
                    if(StringUtils.isNotEmpty(orderold.getOrderNumber())){
                        editrecord = editrecord+"委托书编号：由"+orderbackup.getOrderNumber()+"修改为"+orderinfo.getOrderNumber()+"<td>";
                        isAddRecord = "1";
                    }
                }
            }
            if(!StringUtils.equals(orderinfo.getZxNumber(),orderbackup.getZxNumber())
                    && !(StringUtils.isBlank(orderinfo.getZxNumber()) && StringUtils.isBlank(orderbackup.getZxNumber()))){
                editrecord = editrecord+"重箱舱位号：由"+orderbackup.getZxNumber()+"修改为"+orderinfo.getZxNumber()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getOrderUploadsite(),orderbackup.getOrderUploadsite())){
                editrecord = editrecord+"上货站：由"+orderbackup.getOrderUploadsite()+"修改为"+orderinfo.getOrderUploadsite()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getOrderUnloadsite(),orderbackup.getOrderUnloadsite())){
                editrecord = editrecord+"下货站：由"+orderbackup.getOrderUnloadsite()+"修改为"+orderinfo.getOrderUnloadsite()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getBookingService(),orderbackup.getBookingService())){
                String BookingService1 = "空";
                String BookingService2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getBookingService())){
                    BookingService1 = (orderinfo.getBookingService()).equals("0")?"门到门":(orderinfo.getBookingService()).equals("1")?"门到站":(orderinfo.getBookingService()).equals("2")?"站到站":"站到门";
                }
                if(StringUtils.isNotEmpty(orderbackup.getBookingService())){
                    BookingService2 = (orderbackup.getBookingService()).equals("0")?"门到门":(orderbackup.getBookingService()).equals("1")?"门到站":(orderbackup.getBookingService()).equals("2")?"站到站":"站到门";
                }
                editrecord = editrecord+"服务：由"+BookingService2+"修改为"+BookingService1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getLimitation(),orderbackup.getLimitation())
                    && !(StringUtils.isBlank(orderinfo.getLimitation()) && StringUtils.isBlank(orderbackup.getLimitation()))){
                String limitation1 = "空";
                String limitation2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getLimitation())){
                    limitation1 = (orderinfo.getLimitation()).equals("0")?"普通":"加急";
                }
                if(StringUtils.isNotEmpty(orderbackup.getLimitation())){
                    limitation2 = (orderbackup.getLimitation()).equals("0")?"普通":"加急";
                }
                editrecord = editrecord+"时效：由"+limitation2+"修改为"+limitation1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getTruckType(),orderbackup.getTruckType())
                    && !(StringUtils.isBlank(orderinfo.getTruckType()) && StringUtils.isBlank(orderbackup.getTruckType()))){
                String truck1 = "空";
                String truck2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getTruckType())){
                    truck1 = (orderinfo.getTruckType()).equals("0")?"普通车":(orderinfo.getTruckType()).equals("1")?"普通卡车":"白卡专车";
                }
                if(StringUtils.isNotEmpty(orderbackup.getTruckType())){
                    truck2 = (orderbackup.getTruckType()).equals("0")?"普通车":(orderbackup.getTruckType()).equals("1")?"普通卡车":"白卡专车";
                }
                editrecord = editrecord+"国内公路运输车辆：由"+truck2+"修改为"+truck1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getStationid(),orderbackup.getStationid())
                    && !(StringUtils.isBlank(orderinfo.getStationid()) && StringUtils.isBlank(orderbackup.getStationid()))){
                if(StringUtils.isNotNull(orderold)){
                    if(StringUtils.isNotEmpty(orderold.getStationid())){
                        BusiStation stationInfoBack = busiStationService.selectBusiStationById(orderbackup.getStationid()); //旧id查询
                        BusiStation stationInfo = busiStationService.selectBusiStationById(orderinfo.getStationid()); //新id查询
                        String stationold = "空";
                        String station = "空";
                        if(StringUtils.isNotNull(stationInfoBack)){
                            stationold = stationInfoBack.getStatioin();
                        }
                        if(StringUtils.isNotNull(stationInfo)){
                            station = stationInfo.getStatioin();
                        }
                        editrecord = editrecord+"车站/堆场地址：由"+stationold+"修改为"+station+"<td>";
                    }
                }
            }
            if(!StringUtils.equals(orderinfo.getDictName(),orderbackup.getDictName())){
                editrecord = editrecord+"贸易条款：由"+orderbackup.getDictName()+"修改为"+orderinfo.getDictName()+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getSitecost(),orderbackup.getSitecost())){
//            editrecord = editrecord+"站到站运费：由"+orderbackup.getSitecost()+"修改为"+orderinfo.getSitecost()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getSitecostCurrency(),orderbackup.getSitecostCurrency())){
//            editrecord = editrecord+"站到站运费币种：由"+orderbackup.getSitecostCurrency()+"修改为"+orderinfo.getSitecostCurrency()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getOrderIsticket(),orderbackup.getOrderIsticket())){
                String ticket1 = "空";
                String ticket2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getOrderIsticket())){
                    ticket1 = (orderinfo.getOrderIsticket()).equals("0")?"否":"是";
                }
                if(StringUtils.isNotEmpty(orderbackup.getOrderIsticket())){
                    ticket2 = (orderbackup.getOrderIsticket()).equals("0")?"否":"是";
                }
                editrecord = editrecord+"是否需要发票：由"+ticket2+"修改为"+ticket1+"<td>";
            }
            if(orderinfo.getPutoffClass() != orderbackup.getPutoffClass()){
                String PutoffClass1 = "空";
                String PutoffClass2 = "空";
                if((orderinfo.getPutoffClass())!=null){
                    PutoffClass1 = (orderinfo.getPutoffClass())==0?"是":"否";
                }
                if((orderbackup.getPutoffClass())!=null){
                    PutoffClass2 = (orderbackup.getPutoffClass())==0?"是":"否";
                }
                editrecord = editrecord+"是否可提前班列：由"+PutoffClass2+"修改为"+PutoffClass1+"<td>";
            }
            //订舱方
            if(!StringUtils.equals(orderinfo.getClientUnit(),orderbackup.getClientUnit())){
                editrecord = editrecord+"订舱方：由"+orderbackup.getClientUnit()+"修改为"+orderinfo.getClientUnit()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientEmail(),orderbackup.getClientEmail())){
                editrecord = editrecord+"订舱方邮箱：由"+orderbackup.getClientEmail()+"修改为"+orderinfo.getClientEmail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientContacts(),orderbackup.getClientContacts())){
                editrecord = editrecord+"订舱方联系人：由"+orderbackup.getClientContacts()+"修改为"+orderinfo.getClientContacts()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientTel(),orderbackup.getClientTel())){
                editrecord = editrecord+"订舱方联系方式：由"+orderbackup.getClientTel()+"修改为"+orderinfo.getClientTel()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientAddress(),orderbackup.getClientAddress())){
                editrecord = editrecord+"订舱方地址：由"+orderbackup.getClientAddress()+"修改为"+orderinfo.getClientAddress()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientYwNumber(),orderbackup.getClientYwNumber())){
                editrecord = editrecord+"业务编号：由"+orderbackup.getClientYwNumber()+"修改为"+orderinfo.getClientYwNumber()+"<td>";
            }
            //拼箱
            if(!StringUtils.equals(orderinfo.getGoodsCannotpileup(),orderbackup.getGoodsCannotpileup())
                    && !(StringUtils.isBlank(orderinfo.getGoodsCannotpileup()) && StringUtils.isBlank(orderbackup.getGoodsCannotpileup()))){
                if(isconsolidation.equals("1")){  //拼箱
                    String pileup1 = "空";
                    String pileup2 = "空";
                    if(StringUtils.isNotEmpty(orderinfo.getGoodsCannotpileup())){
                        pileup1 = (orderinfo.getGoodsCannotpileup()).equals("1")?"是":"否";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getGoodsCannotpileup())){
                        pileup2 = (orderbackup.getGoodsCannotpileup()).equals("1")?"是":"否";
                    }
                    editrecord = editrecord+"可推叠(货物属性)：由"+pileup2+"修改为"+pileup1+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getGoodsFragile(),orderbackup.getGoodsFragile())
                    && !(StringUtils.isBlank(orderinfo.getGoodsFragile()) && StringUtils.isBlank(orderbackup.getGoodsFragile()))){
                if(isconsolidation.equals("1")){  //拼箱
                    String fragile1 = "空";
                    String fragile2 = "空";
                    if(StringUtils.isNotEmpty(orderinfo.getGoodsFragile())){
                        fragile1 = (orderinfo.getGoodsFragile()).equals("1")?"是":"否";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getGoodsFragile())){
                        fragile2 = (orderbackup.getGoodsFragile()).equals("1")?"是":"否";
                    }
                    editrecord = editrecord+"易碎品(货物属性)：由"+fragile2+"修改为"+fragile1+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getGoodsGeneral(),orderbackup.getGoodsGeneral())
                    && !(StringUtils.isBlank(orderinfo.getGoodsGeneral()) && StringUtils.isBlank(orderbackup.getGoodsGeneral()))){
                if(isconsolidation.equals("1")){  //拼箱
                    String general1 = "空";
                    String general2 = "空";
                    if(StringUtils.isNotEmpty(orderinfo.getGoodsGeneral())){
                        general1 = (orderinfo.getGoodsGeneral()).equals("1")?"是":"否";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getGoodsGeneral())){
                        general2 = (orderbackup.getGoodsGeneral()).equals("1")?"是":"否";
                    }
                    editrecord = editrecord+"单件超长超重(货物属性)：由"+general2+"修改为"+general1+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getClientOrderBindingaddress(),orderbackup.getClientOrderBindingaddress())
                    && !(StringUtils.isBlank(orderinfo.getClientOrderBindingaddress()) && StringUtils.isBlank(orderbackup.getClientOrderBindingaddress()))){
                editrecord = editrecord+"报关地点：由"+orderbackup.getClientOrderBindingaddress()+"修改为"+orderinfo.getClientOrderBindingaddress()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientOrderBindingway(),orderbackup.getClientOrderBindingway())){
                String bindingway1 = "空";
                String bindingway2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getClientOrderBindingway())){
                    bindingway1 = (orderinfo.getClientOrderBindingway()).equals("0")?"是":"否";
                }
                if(StringUtils.isNotEmpty(orderbackup.getClientOrderBindingway())){
                    bindingway2 = (orderbackup.getClientOrderBindingway()).equals("0")?"是":"否";
                }
                editrecord = editrecord+"委托ZIH报关：由"+bindingway2+"修改为"+bindingway1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getClientBgCost(),orderbackup.getClientBgCost())
                    && !(StringUtils.isBlank(orderinfo.getClientBgCost()) && StringUtils.isBlank(orderbackup.getClientBgCost()))){
                String bgcost1 = StringUtils.isNotEmpty(orderinfo.getClientBgCost())?orderinfo.getClientBgCost():"空";
                String bgcost2 = StringUtils.isNotEmpty(orderbackup.getClientBgCost())?orderbackup.getClientBgCost():"空";
                editrecord = editrecord+"报关费用：由"+bgcost2+"修改为"+bgcost1+"<td>";
            }
            //整柜
            if(!StringUtils.equals(orderinfo.getContainerBoxamount(),orderbackup.getContainerBoxamount())){
                if(isconsolidation.equals("0")){
                    editrecord = editrecord+"箱量：由"+orderbackup.getContainerBoxamount()+"修改为"+orderinfo.getContainerBoxamount()+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getContainerType(),orderbackup.getContainerType())){
                if(isconsolidation.equals("0")){
                    editrecord = editrecord+"箱型：由"+orderbackup.getContainerType()+"修改为"+orderinfo.getContainerType()+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getOrderAuditBelongto(),orderbackup.getOrderAuditBelongto())){
                if(isconsolidation.equals("0")){
                    String belongto1 = "空";
                    String belongto2 = "空";
                    if(StringUtils.isNotEmpty(orderinfo.getOrderAuditBelongto())){
                        belongto1 = (orderinfo.getOrderAuditBelongto()).equals("0")?"委托ZIH":(orderinfo.getOrderAuditBelongto()).equals("1")?"自备":(orderinfo.getOrderAuditBelongto()).equals("2")?"自备铁路箱":"自备非铁路箱";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getOrderAuditBelongto())){
                        belongto2 = (orderbackup.getOrderAuditBelongto()).equals("0")?"委托ZIH":(orderbackup.getOrderAuditBelongto()).equals("1")?"自备":(orderbackup.getOrderAuditBelongto()).equals("2")?"自备铁路箱":"自备非铁路箱";
                    }
                    editrecord = editrecord+"箱属：由"+belongto2+"修改为"+belongto1+"<td>";
                }
            }
            //跟单
            if(!StringUtils.equals(orderinfo.getOrderMerchandiser(),orderbackup.getOrderMerchandiser())){
                editrecord = editrecord+"跟单员：由"+orderbackup.getOrderMerchandiser()+"修改为："+orderinfo.getOrderMerchandiser()+"<td>";
            }
            //发货方
            if(!StringUtils.equals(orderinfo.getShipOrederName(),orderbackup.getShipOrederName())){
                editrecord = editrecord+"发货方：由"+orderbackup.getShipOrederName()+"修改为"+orderinfo.getShipOrederName()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrederContacts(),orderbackup.getShipOrederContacts())){
                editrecord = editrecord+"发货方联系人：由"+orderbackup.getShipOrederContacts()+"修改为"+orderinfo.getShipOrederContacts()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrederPhone(),orderbackup.getShipOrederPhone())){
                editrecord = editrecord+"发货方联系方式：由"+orderbackup.getShipOrederPhone()+"修改为"+orderinfo.getShipOrederPhone()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrederEmail(),orderbackup.getShipOrederEmail())){
                editrecord = editrecord+"发货方邮箱：由"+orderbackup.getShipOrederEmail()+"修改为"+orderinfo.getShipOrederEmail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrederAddress(),orderbackup.getShipOrederAddress())){
                editrecord = editrecord+"发货方地址：由"+orderbackup.getShipOrederAddress()+"修改为"+orderinfo.getShipOrederAddress()+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getShipmentPlace(),orderbackup.getShipmentPlace())
//                && !(StringUtils.isBlank(orderinfo.getShipmentPlace()) && StringUtils.isBlank(orderbackup.getShipmentPlace()))){
//            editrecord = editrecord+"发货地：由"+orderbackup.getShipmentPlace()+"修改为"+orderinfo.getShipmentPlace()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getShipOrderBinningway(),orderbackup.getShipOrderBinningway())
                    && !(StringUtils.isBlank(orderinfo.getShipOrderBinningway()) && StringUtils.isBlank(orderbackup.getShipOrderBinningway()))){
                String shipway1 = "空";
                String shipway2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getShipOrderBinningway())){
                    shipway1 = (orderinfo.getShipOrderBinningway()).equals("0")?"委托ZIH提货":"自送货";
                }
                if(StringUtils.isNotEmpty(orderbackup.getShipOrderBinningway())){
                    shipway2 = (orderbackup.getShipOrderBinningway()).equals("0")?"委托ZIH提货":"自送货";
                }
                editrecord = editrecord+"由ZIH提货：由"+shipway2+"修改为"+shipway1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipThTypeId(),orderbackup.getShipThTypeId())
                    && !(StringUtils.isBlank(orderinfo.getShipThTypeId()) && StringUtils.isBlank(orderbackup.getShipThTypeId()))){
                String thtype1 = "空";
                String thtype2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getShipThTypeId())){
                    thtype1 = (orderinfo.getShipThTypeId()).equals("0")?"整箱到车站":"散货到堆场";
                }
                if(StringUtils.isNotEmpty(orderbackup.getShipThTypeId())){
                    thtype2 = (orderbackup.getShipThTypeId()).equals("0")?"整箱到车站":"散货到堆场";
                }
                editrecord = editrecord+"提货方式：由"+thtype2+"修改为"+thtype1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipZsTypeId(),orderbackup.getShipZsTypeId())
                    && !(StringUtils.isBlank(orderinfo.getShipZsTypeId()) && StringUtils.isBlank(orderbackup.getShipZsTypeId()))){
                String zstype1 = "空";
                String zstype2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getShipZsTypeId())){
                    zstype1 = (orderinfo.getShipZsTypeId()).equals("0")?"散货到堆场":"整箱到车站";
                }
                if(StringUtils.isNotEmpty(orderbackup.getShipZsTypeId())){
                    zstype2 = (orderbackup.getShipZsTypeId()).equals("0")?"散货到堆场":"整箱到车站";
                }
                editrecord = editrecord+"送货方式：由"+zstype2+"修改为"+zstype1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipFhSite(),orderbackup.getShipFhSite())
                    && !(StringUtils.isBlank(orderinfo.getShipFhSite()) && StringUtils.isBlank(orderbackup.getShipFhSite()))){
                String qcsite1= StringUtils.isNotEmpty(orderbackup.getShipFhSite())?orderbackup.getShipFhSite():"空";
                String qcsite2= StringUtils.isNotEmpty(orderinfo.getShipFhSite())?orderinfo.getShipFhSite():"空";
                editrecord = editrecord+"去程发货提箱地：由"+qcsite1+"修改为"+qcsite2+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipHyd(),orderbackup.getShipHyd())
                    && !(StringUtils.isBlank(orderinfo.getShipHyd()) && StringUtils.isBlank(orderbackup.getShipHyd()))){
                String hcsite1 = StringUtils.isNotEmpty(orderbackup.getShipHyd())?orderbackup.getShipHyd():"空";
                String hcsite2 = StringUtils.isNotEmpty(orderinfo.getShipHyd())?orderinfo.getShipHyd():"空";
                editrecord = editrecord+"回程发货提箱地：由"+hcsite1+"修改为"+hcsite2+"<td>";
            }
            if(!StringUtils.equals(DateUtils.parseStr(orderinfo.getShipOrderSendtime()),DateUtils.parseStr(orderbackup.getShipOrderSendtime()))
                    && !(StringUtils.isBlank(DateUtils.parseStr(orderinfo.getShipOrderSendtime())) && StringUtils.isBlank(DateUtils.parseStr(orderbackup.getShipOrderSendtime())))){
                editrecord = editrecord+"自送货时间：由"+DateUtils.parseStr(orderbackup.getShipOrderSendtime())+"修改为"+DateUtils.parseStr(orderinfo.getShipOrderSendtime())+"<td>";
            }
            if(!StringUtils.equals(DateUtils.parseStr(orderinfo.getShipOrderUnloadtime()),DateUtils.parseStr(orderbackup.getShipOrderUnloadtime()))
                    && !(StringUtils.isBlank(DateUtils.parseStr(orderinfo.getShipOrderUnloadtime())) && StringUtils.isBlank(DateUtils.parseStr(orderbackup.getShipOrderUnloadtime())))){
                editrecord = editrecord+"提货时间：由"+DateUtils.parseStr(orderbackup.getShipOrderUnloadtime())+"修改为"+DateUtils.parseStr(orderinfo.getShipOrderUnloadtime())+"<td>";
                isAddRecord = "1";
//                if("0".equals(classEastandwest)){
//                    isPicktime = "0";
//                }
            }
            if(!StringUtils.equals(orderinfo.getReceiveXgCost(),orderbackup.getReceiveXgCost())
                    && !(StringUtils.isBlank(orderinfo.getReceiveXgCost()) && StringUtils.isBlank(orderbackup.getReceiveXgCost()))){
                editrecord = editrecord+"箱管费：由"+orderbackup.getReceiveXgCost()+"修改为"+orderinfo.getReceiveXgCost()+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getPickUpBoxFee(),orderbackup.getPickUpBoxFee())
//                && !(StringUtils.isBlank(orderinfo.getPickUpBoxFee()) && StringUtils.isBlank(orderbackup.getPickUpBoxFee()))){
//            editrecord = editrecord+"提箱费：由"+orderbackup.getPickUpBoxFee()+"修改为"+orderinfo.getPickUpBoxFee()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getShipOrderUnloadcontacts(),orderbackup.getShipOrderUnloadcontacts())){
                editrecord = editrecord+"提货联系人：由"+orderbackup.getShipOrderUnloadcontacts()+"修改为"+orderinfo.getShipOrderUnloadcontacts()+"<td>";
                isAddRecord = "1";
            }
            if(!StringUtils.equals(orderinfo.getShipOrderUnloadway(),orderbackup.getShipOrderUnloadway())){
                editrecord = editrecord+"提货联系方式：由"+orderbackup.getShipOrderUnloadway()+"修改为"+orderinfo.getShipOrderUnloadway()+"<td>";
                isAddRecord = "1";
            }
            if(!StringUtils.equals(orderinfo.getShipOrderUnloadwayEmail(),orderbackup.getShipOrderUnloadwayEmail())){
                editrecord = editrecord+"提货联系邮箱：由"+orderbackup.getShipOrderUnloadwayEmail()+"修改为"+orderinfo.getShipOrderUnloadwayEmail()+"<td>";
                isAddRecord = "1";
            }
            if(!StringUtils.equals(orderinfo.getShipOrderUnloadaddress(),orderbackup.getShipOrderUnloadaddress())){
                editrecord = editrecord+"提货地址：由"+orderbackup.getShipOrderUnloadaddress()+"修改为"+orderinfo.getShipOrderUnloadaddress()+"<td>";
                isAddRecord = "1";
            }
            if(!StringUtils.equals(orderinfo.getDetailedAddress(),orderbackup.getDetailedAddress())){
                editrecord = editrecord+"详细地址：由"+orderbackup.getDetailedAddress()+"修改为"+orderinfo.getDetailedAddress()+"<td>";
                isAddRecord = "1";
            }
//        if(!StringUtils.equals(orderinfo.getShipThCostNo(),orderbackup.getShipThCostNo())
//                && !(StringUtils.isBlank(orderinfo.getShipThCostNo()) && StringUtils.isBlank(orderbackup.getShipThCostNo()))){
//            editrecord = editrecord+"提货费报价编号：由"+orderbackup.getShipThCostNo()+"修改为"+orderinfo.getShipThCostNo()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getShipThCost(),orderbackup.getShipThCost())
//                && !(StringUtils.isBlank(orderinfo.getShipThCost()) && StringUtils.isBlank(orderbackup.getShipThCost()))){
//            editrecord = editrecord+"提货费用：由"+orderbackup.getShipThCost()+"修改为"+orderinfo.getShipThCost()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getZxThcostCurrency(),orderbackup.getZxThcostCurrency())
//                && !(StringUtils.isBlank(orderinfo.getZxThcostCurrency()) && StringUtils.isBlank(orderbackup.getZxThcostCurrency()))){
//            editrecord = editrecord+"提货费币种：由"+orderbackup.getZxThcostCurrency()+"修改为"+orderinfo.getZxThcostCurrency()+"<td>";
//        }
            //收货方
            if(!StringUtils.equals(orderinfo.getReceiveOrderName(),orderbackup.getReceiveOrderName())){
                editrecord = editrecord+"收货方：由"+orderbackup.getReceiveOrderName()+"修改为"+orderinfo.getReceiveOrderName()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderPhone(),orderbackup.getReceiveOrderPhone())){
                editrecord = editrecord+"收货方联系方式：由"+orderbackup.getReceiveOrderPhone()+"修改为"+orderinfo.getReceiveOrderPhone()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderContacts(),orderbackup.getReceiveOrderContacts())){
                editrecord = editrecord+"收货方联系人：由"+orderbackup.getReceiveOrderContacts()+"修改为"+orderinfo.getReceiveOrderContacts()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderAddress(),orderbackup.getReceiveOrderAddress())){
                editrecord = editrecord+"收货方地址：由"+orderbackup.getReceiveOrderAddress()+"修改为"+orderinfo.getReceiveOrderAddress()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderEmail(),orderbackup.getReceiveOrderEmail())){
                editrecord = editrecord+"收货方邮箱：由"+orderbackup.getReceiveOrderEmail()+"修改为"+orderinfo.getReceiveOrderEmail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderIsclearance(),orderbackup.getReceiveOrderIsclearance())){
                String Isclearance1 = "空";
                String Isclearance2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getReceiveOrderIsclearance())){
                    Isclearance1 = (orderinfo.getReceiveOrderIsclearance()).equals("1")?"是" : "否";
                }
                if(StringUtils.isNotEmpty(orderbackup.getReceiveOrderIsclearance())){
                    Isclearance2 = (orderbackup.getReceiveOrderIsclearance()).equals("1")?"是" : "否";
                }
                editrecord = editrecord+"由ZIH代理清关：由"+Isclearance2+"修改为"+Isclearance1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveQgCost(),orderbackup.getReceiveQgCost())
                    && !(StringUtils.isBlank(orderinfo.getReceiveQgCost()) && StringUtils.isBlank(orderbackup.getReceiveQgCost()))){
                String qgcost1 = StringUtils.isNotEmpty(orderinfo.getReceiveQgCost())?orderinfo.getReceiveQgCost():"空";
                String qgcost2 = StringUtils.isNotEmpty(orderbackup.getReceiveQgCost())?orderbackup.getReceiveQgCost():"空";
                editrecord = editrecord+"清关费用：由"+qgcost2+"修改为"+qgcost1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderIspart(),orderbackup.getReceiveOrderIspart())){
                String Ispart1 = "空";
                String Ispart2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getReceiveOrderIspart())){
                    Ispart1 = (orderinfo.getReceiveOrderIspart()).equals("1")?"是" : "否";
                }
                if(StringUtils.isNotEmpty(orderbackup.getReceiveOrderIspart())){
                    Ispart2 = (orderbackup.getReceiveOrderIspart()).equals("1")?"是" : "否";
                }
                editrecord = editrecord+"是否由ZIH代理送货：由"+Ispart2+"修改为"+Ispart1+"<td>";
            }
            if("0".equals(isconsolidation)){
                if(!StringUtils.equals(orderinfo.getDistributionType(),orderbackup.getDistributionType())
                        && !(StringUtils.isBlank(orderinfo.getDistributionType()) && StringUtils.isBlank(orderbackup.getDistributionType()))){
                    String Isparttype1 = "空";
                    String Isparttype2 = "空";
                    if(StringUtils.isNotEmpty(orderinfo.getDistributionType())){
                        Isparttype1 = (orderinfo.getDistributionType()).equals("1")?"散货派送" : "整柜派送";
                    }
                    if(StringUtils.isNotEmpty(orderbackup.getDistributionType())){
                        Isparttype2 = (orderbackup.getDistributionType()).equals("1")?"散货派送" : "整柜派送";
                    }
                    editrecord = editrecord+"送货方式：由 "+Isparttype2+"修改为："+Isparttype1+"<td>";
                }
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderZihcontacts(),orderbackup.getReceiveOrderZihcontacts())){
                editrecord = editrecord+"送货分拨联系人：由"+orderbackup.getReceiveOrderZihcontacts()+"修改为"+orderinfo.getReceiveOrderZihcontacts()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderZihtel(),orderbackup.getReceiveOrderZihtel())){
                editrecord = editrecord+"送货分拨联系电话：由"+orderbackup.getReceiveOrderZihtel()+"修改为"+orderinfo.getReceiveOrderZihtel()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderZihemail(),orderbackup.getReceiveOrderZihemail())){
                editrecord = editrecord+"送货分拨联系邮箱：由"+orderbackup.getReceiveOrderZihemail()+"修改为"+orderinfo.getReceiveOrderZihemail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderPartaddress(),orderbackup.getReceiveOrderPartaddress())){
                editrecord = editrecord+"送货地址：由"+orderbackup.getReceiveOrderPartaddress()+"修改为"+orderinfo.getReceiveOrderPartaddress()+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getReceiptPlace(),orderbackup.getReceiptPlace())
//                && !(StringUtils.isBlank(orderinfo.getReceiptPlace()) && StringUtils.isBlank(orderbackup.getReceiptPlace()))){
//            editrecord = editrecord+"收货地：由"+orderbackup.getReceiptPlace()+"修改为"+orderinfo.getReceiptPlace()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getReceiveHxAddress(),orderbackup.getReceiveHxAddress())
                    && !(StringUtils.isBlank(orderinfo.getReceiveHxAddress()) && StringUtils.isBlank(orderbackup.getReceiveHxAddress()))){
                String hxsite1 = StringUtils.isNotEmpty(orderbackup.getReceiveHxAddress())?orderbackup.getReceiveHxAddress():"空";
                String hxsite2 = StringUtils.isNotEmpty(orderinfo.getReceiveHxAddress())?orderinfo.getReceiveHxAddress():"空";
                editrecord = editrecord+"收货还箱地：由"+hxsite1+"修改为"+hxsite2+"<td>";
            }
//        if(!StringUtils.equals(orderinfo.getReceiveShCostId(),orderbackup.getReceiveShCostId())
//                && !(StringUtils.isBlank(orderinfo.getReceiveShCostId()) && StringUtils.isBlank(orderbackup.getReceiveShCostId()))){
//            editrecord = editrecord+"送货报价编号：由"+orderbackup.getReceiveShCostId()+"修改为"+orderinfo.getReceiveShCostId()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getReceiveShCost(),orderbackup.getReceiveShCost())
//                && !(StringUtils.isBlank(orderinfo.getReceiveShCost()) && StringUtils.isBlank(orderbackup.getReceiveShCost()))){
//            editrecord = editrecord+"送货费用：由"+orderbackup.getReceiveShCost()+"修改为"+orderinfo.getReceiveShCost()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getShCostcurrency(),orderbackup.getShCostcurrency())
//                && !(StringUtils.isBlank(orderinfo.getShCostcurrency()) && StringUtils.isBlank(orderbackup.getShCostcurrency()))){
//            editrecord = editrecord+"送货费币种：由"+orderbackup.getShCostcurrency()+"修改为"+orderinfo.getShCostcurrency()+"<td>";
//        }
//        if(!StringUtils.equals(orderinfo.getReturnBoxFee(),orderbackup.getReturnBoxFee())
//                && !(StringUtils.isBlank(orderinfo.getReturnBoxFee()) && StringUtils.isBlank(orderbackup.getReturnBoxFee()))){
//            editrecord = editrecord+"还箱费：由"+orderbackup.getReturnBoxFee()+"修改为"+orderinfo.getReturnBoxFee()+"<td>";
//        }
            if(!StringUtils.equals(orderinfo.getReceiveOrderReceiveemail(),orderbackup.getReceiveOrderReceiveemail())){
                editrecord = editrecord+"在途信息接收邮箱：由"+orderbackup.getReceiveOrderReceiveemail()+"修改为"+orderinfo.getReceiveOrderReceiveemail()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getReceiveOrderMail(),orderbackup.getReceiveOrderMail())){
                editrecord = editrecord+"到站通知提货邮箱：由"+orderbackup.getReceiveOrderMail()+"修改为"+orderinfo.getReceiveOrderMail()+"<td>";
                ismailChange = "1";
            }
            //俄线
            if(!StringUtils.equals(orderinfo.getConsignorc(),orderbackup.getConsignorc())){
                editrecord = editrecord+"中文发货人：由"+orderbackup.getConsignorc()+"修改为"+orderinfo.getConsignorc()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getEduty(),orderbackup.getEduty())){
                editrecord = editrecord+"到货提箱公司税号：由"+orderbackup.getEduty()+"修改为"+orderinfo.getEduty()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getEtxName(),orderbackup.getEtxName())){
                editrecord = editrecord+"承担监管区费用的公司：由"+orderbackup.getEtxName()+"修改为"+orderinfo.getEtxName()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getEconsignorstate(),orderbackup.getEconsignorstate())){
                editrecord = editrecord+"发货人声明：由"+orderbackup.getEconsignorstate()+"修改为"+orderinfo.getEconsignorstate()+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getEtxCompany(),orderbackup.getEtxCompany())){
                editrecord = editrecord+"实际收货人名称：由"+orderbackup.getEtxCompany()+"修改为"+orderinfo.getEtxCompany()+"<td>";
            }
            //货物
            if(!StringUtils.equals(goodsinfo.getGoodsMark(),goodsbackup.getGoodsMark())){
                editrecord = editrecord+"唛头：由"+goodsbackup.getGoodsMark()+"修改为"+goodsinfo.getGoodsMark()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsName(),goodsbackup.getGoodsName())){
                editrecord = editrecord+"货品中文名称：由"+goodsbackup.getGoodsName()+"修改为"+goodsinfo.getGoodsName()+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsEnName(),goodsbackup.getGoodsEnName())){
                editrecord = editrecord+"货品英文名称：由"+goodsbackup.getGoodsEnName()+"修改为"+goodsinfo.getGoodsEnName()+"<td>";
                isClassCheck = "0";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsReport(),goodsbackup.getGoodsReport())){
                editrecord = editrecord+"国外报关HS：由"+goodsbackup.getGoodsReport()+"修改为"+goodsinfo.getGoodsReport()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsInReport(),goodsbackup.getGoodsInReport())){
                editrecord = editrecord+"国内报关HS：由"+goodsbackup.getGoodsInReport()+"修改为"+goodsinfo.getGoodsInReport()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsClearance(),goodsbackup.getGoodsClearance())){
                editrecord = editrecord+"国内清关HS：由"+goodsbackup.getGoodsClearance()+"修改为"+goodsinfo.getGoodsClearance()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsOutClearance(),goodsbackup.getGoodsOutClearance())){
                editrecord = editrecord+"国外清关HS：由"+goodsbackup.getGoodsOutClearance()+"修改为"+goodsinfo.getGoodsOutClearance()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsPacking(),goodsbackup.getGoodsPacking())){
                editrecord = editrecord+"最外层包装形式：由"+goodsbackup.getGoodsPacking()+"修改为"+goodsinfo.getGoodsPacking()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsNumber(),goodsbackup.getGoodsNumber())){
                editrecord = editrecord+"最外层包装数量：由"+goodsbackup.getGoodsNumber()+"修改为"+goodsinfo.getGoodsNumber()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsCbm(),goodsbackup.getGoodsCbm())){
                editrecord = editrecord+"总体积：由"+goodsbackup.getGoodsCbm()+"修改为"+goodsinfo.getGoodsCbm()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsKgs(),goodsbackup.getGoodsKgs())){
                editrecord = editrecord+"总重量：由"+goodsbackup.getGoodsKgs()+"修改为"+goodsinfo.getGoodsKgs()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsStandard(),goodsbackup.getGoodsStandard())){
                editrecord = editrecord+"规格：由"+goodsbackup.getGoodsStandard()+"修改为"+goodsinfo.getGoodsStandard()+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getRemark(),goodsbackup.getRemark())
                    && !(StringUtils.isBlank(goodsinfo.getRemark()) && StringUtils.isBlank(goodsbackup.getRemark()))){
                String remark1 = StringUtils.isNotEmpty(goodsinfo.getRemark())?goodsinfo.getRemark():"空";
                String remark2 = StringUtils.isNotEmpty(goodsbackup.getRemark())?goodsbackup.getRemark():"空";
                editrecord = editrecord+"托书备注：由"+remark2+"修改为"+remark1+"<td>";
            }
            if(!StringUtils.equals(goodsinfo.getGoodsbz(),goodsbackup.getGoodsbz())
                    && !(StringUtils.isBlank(goodsinfo.getGoodsbz()) && StringUtils.isBlank(goodsbackup.getGoodsbz()))){
                if(StringUtils.isNotNull(orderold)){
                    String goodsbz1 = StringUtils.isNotEmpty(goodsinfo.getGoodsbz())?goodsinfo.getGoodsbz():"空";
                    String goodsbz2 = StringUtils.isNotEmpty(goodsbackup.getGoodsbz())?goodsbackup.getGoodsbz():"空";
                    editrecord = editrecord+"货物备注：由"+goodsbz2+"修改为"+goodsbz1+"<td>";
                }
            }
            if(!StringUtils.equals(goodsinfo.getHsbz(),goodsbackup.getHsbz())
                    && !(StringUtils.isBlank(goodsinfo.getHsbz()) && StringUtils.isBlank(goodsbackup.getHsbz()))){
                if(StringUtils.isNotNull(orderold)){
                    String hsbz1 = StringUtils.isNotEmpty(goodsinfo.getHsbz())?goodsinfo.getHsbz():"空";
                    String hsbz2 = StringUtils.isNotEmpty(goodsbackup.getHsbz())?goodsbackup.getHsbz():"空";
                    editrecord = editrecord+"HS编码备注：由"+hsbz2+"修改为"+hsbz1+"<td>";
                }
            }
            if(!StringUtils.equals(goodsinfo.getGoodsIsscheme(),goodsbackup.getGoodsIsscheme())){
                String Isscheme1 = "空";
                String Isscheme2 = "空";
                if(StringUtils.isNotEmpty(goodsinfo.getGoodsIsscheme())){
                    Isscheme1 = (goodsinfo.getGoodsIsscheme()).equals("0")?"否" : "是";
                }
                if(StringUtils.isNotEmpty(goodsbackup.getGoodsIsscheme())){
                    Isscheme2 = (goodsbackup.getGoodsIsscheme()).equals("0")?"否" : "是";
                }
                editrecord = editrecord+"是否需要装箱方案：由"+Isscheme2+"修改为"+Isscheme1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipOrderIsdispatch(),orderbackup.getShipOrderIsdispatch())){
                String Isdispatch1 = "空";
                String Isdispatch2 = "空";
                if(StringUtils.isNotEmpty(orderinfo.getShipOrderIsdispatch())){
                    Isdispatch1 = (orderinfo.getShipOrderIsdispatch()).equals("1")?"是" : "否";
                }
                if(StringUtils.isNotEmpty(orderbackup.getShipOrderIsdispatch())){
                    Isdispatch2 = (orderbackup.getShipOrderIsdispatch()).equals("1")?"是" : "否";
                }
                editrecord = editrecord+"是否派监装员：由"+Isdispatch2+"修改为"+Isdispatch1+"<td>";
            }
            if(!StringUtils.equals(orderinfo.getShipJzCost(),orderbackup.getShipJzCost())
                    && !(StringUtils.isBlank(orderinfo.getShipJzCost()) && StringUtils.isBlank(orderbackup.getShipJzCost()))){
                editrecord = editrecord+"监装费用：由"+orderbackup.getShipJzCost()+"修改为"+orderinfo.getShipJzCost()+"<td>";
            }
            if(StringUtils.isNotEmpty(editrecord)){
                editrecord =title + editrecord +"<###>";
                editrecord = StringUtils.replace(editrecord,"null","空");
            }
        }
        orderres.put("editrecord",editrecord);
        orderres.put("isPicktime",isPicktime);
        orderres.put("isClassCheck",isClassCheck);
        orderres.put("ismailChange",ismailChange);
        orderres.put("isAddRecord",isAddRecord);
        return orderres;
    }

    // 从托书中筛选托书商品表信息
    @Override
    public BusiShippingorderGoods orderGoodsInfo(BusiShippingorders busiShippingorder){
        //获取商品信息
        BusiShippingorderGoods busiShippingGoods = new BusiShippingorderGoods();
        busiShippingGoods.setOrderId(busiShippingorder.getOrderId());
        busiShippingGoods.setGoodsMark(busiShippingorder.getGoodsMark());
        busiShippingGoods.setGoodsName(busiShippingorder.getGoodsName());
        busiShippingGoods.setGoodsEnName(busiShippingorder.getGoodsEnName());
        busiShippingGoods.setGoodsReport(busiShippingorder.getGoodsReport());
        busiShippingGoods.setGoodsClearance(busiShippingorder.getGoodsClearance());
        busiShippingGoods.setGoodsInReport(busiShippingorder.getGoodsInReport());
        busiShippingGoods.setGoodsOutClearance(busiShippingorder.getGoodsOutClearance());
        busiShippingGoods.setGoodsPacking(busiShippingorder.getGoodsPacking());
        busiShippingGoods.setGoodsNumber(busiShippingorder.getGoodsNumber());
        busiShippingGoods.setGoodsStandard(busiShippingorder.getGoodsStandard());
        busiShippingGoods.setGoodsKgs(busiShippingorder.getGoodsKgs());
        busiShippingGoods.setGoodsCbm(busiShippingorder.getGoodsCbm());
        busiShippingGoods.setGoodsIsscheme(busiShippingorder.getGoodsIsscheme());
        busiShippingGoods.setRemark(busiShippingorder.getRemark());
        busiShippingGoods.setRemarkS(busiShippingorder.getRemarkS());
        busiShippingGoods.setCreatedate(busiShippingorder.getCreatedate());
        busiShippingGoods.setGoodsbz(busiShippingorder.getGoodsbz());
        busiShippingGoods.setHsbz(busiShippingorder.getHsbz());
        busiShippingGoods.setRadioaction(busiShippingorder.getRadioaction());
        return busiShippingGoods;
    }

    //重新询价的信息存入托书
    public BusiShippingorders orderInfoByInquiry(BookingInquiryResult inquiryInfo,String classEastandwest){
        BusiShippingorders orderInfo = new BusiShippingorders();
        if("0".equals(classEastandwest)){  //去程
            orderInfo.setShipThCostNo(inquiryInfo.getInquiryNumber()); //提货报价编号
            orderInfo.setReceiveShCostId(inquiryInfo.getInquiryNum()); //送货报价编号
            orderInfo.setShipFhSite(inquiryInfo.getTxAddress()); //提箱地
        }
        if("1".equals(classEastandwest)){ //回程
            orderInfo.setShipThCostNo(inquiryInfo.getInquiryNum()); //提货报价编号
            orderInfo.setReceiveShCostId(inquiryInfo.getInquiryNumber()); //送货报价编号
            orderInfo.setShipHyd(inquiryInfo.getTxAddress());//提箱地
        }
        orderInfo.setReceiveHxAddress(inquiryInfo.getHxAddress()); //还箱地
        orderInfo.setShipThCost(inquiryInfo.getPickUpFees()); //提货费
        orderInfo.setZxThcostCurrency(inquiryInfo.getPickUpCurrencyType());
        orderInfo.setReceiveShCost(inquiryInfo.getDeliveryFees()); //送货费
        orderInfo.setShCostcurrency(inquiryInfo.getDeliveryCurrencyType());
        orderInfo.setPickUpBoxFee(inquiryInfo.getPickUpBoxFee()); //提箱平衡费
        orderInfo.setReturnBoxFee(inquiryInfo.getReturnBoxFee()); //还箱平衡费
        orderInfo.setSitecost(inquiryInfo.getRailwayFees()); //铁路运费
        orderInfo.setSitecostCurrency(inquiryInfo.getRailwayCurrencyType());
        return orderInfo;
    }

    //获取排舱信息
    @Override
    public Boolean isSpace(String classId, BusiShippingorders busiShippingorder){
        Boolean result = true;
        String isconsolidation = busiShippingorder.getIsconsolidation(); //0整柜 1拼箱
        if(StringUtils.isNotEmpty(isconsolidation)){
            String containerType = busiShippingorder.getContainerType();
            Double containerBoxamount = 0.0;
            if(StringUtils.isNotEmpty(busiShippingorder.getContainerBoxamount())){
                containerBoxamount = Double.valueOf(busiShippingorder.getContainerBoxamount());
            }
            Double goodsCbm = 0.0;
            Double goodsKgs = 0.0;
            if(StringUtils.isNotEmpty(busiShippingorder.getGoodsCbm())){
                goodsCbm = Double.valueOf(busiShippingorder.getGoodsCbm());
            }
            if(StringUtils.isNotEmpty(busiShippingorder.getGoodsKgs())){
                goodsKgs = Double.valueOf(busiShippingorder.getGoodsKgs());
            }
            //获取班列信息
            BusiClasses classInfo = busiClassesService.selectBusiClassesById(classId);
            if(StringUtils.isNull(classInfo)){
                result = false;
            }else{
                Long classSpacenumber = classInfo.getClassSpacenumber(); //舱位总数
                Long zxcnt = classInfo.getZxcnt(); //整箱舱位数
                String pxstate = classInfo.getPxstate(); //拼箱状态（0是未满1是已满）
                Double pxcnt = Double.valueOf(classInfo.getPxcnt()); //拼箱体积数
                Double pxkgs = Double.valueOf(classInfo.getPxkgs()); //拼箱重量数
                //整柜，比较箱量
                if(isconsolidation.equals("0")){
                    if(containerType.contains("20")){
                        containerBoxamount = containerBoxamount/2;
                    }
                    if(classSpacenumber==0){
                        result=false;
                    }else{
                        Integer zg = busiShippingorderPcMapper.zgCount(classId);
                        if(zg == null){
                            zg = 0;
                        }
                        Long boxcount = zxcnt - zg; //剩余整箱舱位数
                        if(boxcount<containerBoxamount || zg>classSpacenumber){
                            result = false;
                        }
                    }
                }
                //拼箱 比较体积重量
                if(isconsolidation.equals("1")){
                    if(pxstate.equals("1")){
                        result=false;
                    }else{
                        px px = busiShippingorderGoodsPcMapper.pxCount(classId);
                        Double boxkgs;
                        Double boxcbm;
                        if(StringUtils.isNotNull(px)){
                            boxkgs = pxkgs- px.getKg();
                            boxcbm = pxcnt - px.getVal();
                        }else{
                            boxkgs = pxkgs; //剩余重量
                            boxcbm = pxcnt; //剩余体积
                        }
                        if(boxkgs<goodsKgs || boxcbm<goodsCbm){
                            result = false;
                        }
                    }
                }
            }
        }else{
            result = false;
        }
        return result;
    }

    /**
     * 获取订单特殊备注（不用）
     */
    @Override
    public Map selectSpecialRemark(String goodsName,String goodsReport,String classId,String orderUnloadsite)
    {
        Map specialRemark = new HashMap();
        String nameRemark = "";
        String hsRemark = "";
        String dangerRemark = "";
        if(StringUtils.isNotEmpty(goodsName) && StringUtils.isNotEmpty(goodsReport) && StringUtils.isNotEmpty(classId) && StringUtils.isNotEmpty(orderUnloadsite)){
            //疑似危险品特殊备注
            DangerousGoods dangerInfo = dangerousGoodsMapper.selectDangerousGoodsByName(goodsName);
            if(StringUtils.isNotNull(dangerInfo)){
                dangerRemark = dangerInfo.getSpecialremark();
            }
            BusiClasses classInfo = busiClassesMapper.selectBusiClassesById(classId);
            if(StringUtils.isNotNull(classInfo)){
                String unloadsite = orderUnloadsite;//下货站
                String station = classInfo.getNameCn();
                String eastandwest = classInfo.getClassEastandwest(); //东西向 0西向 1东向
                String inhs = goodsReport;
                //货物名称特殊备注
                List<BaseGoodsnote> goodsNameRemarkList = baseGoodsnoteMapper.selectBaseGoodsnoteListByName(eastandwest,goodsName);
                for(BaseGoodsnote noteItem:goodsNameRemarkList){
                    if(("1".equals(noteItem.getUnloadsite())) && ("1".equals(noteItem.getStation()))){
                        nameRemark = nameRemark+noteItem.getSpecialnotes()+";";
                    }else{
                        if(!"1".equals(noteItem.getUnloadsite())){
                            if((noteItem.getUnloadsite()).contains(unloadsite)){
                                nameRemark = nameRemark+noteItem.getGoodsname()+":"+unloadsite+"站点"+noteItem.getSpecialnotes()+";";
                            }
                        }
                        if(!"1".equals(noteItem.getStation())){
                            if(station.contains(noteItem.getStation())){
                                nameRemark = nameRemark+noteItem.getGoodsname()+":此班列的"+noteItem.getStation()+"口岸"+noteItem.getSpecialnotes()+";";
                            }
                        }
                    }
                }
                //hs编码特殊备注
                List<BaseGoodsnote> hsNameRemarkList = baseGoodsnoteMapper.selectBaseGoodsnoteListByOrderHs(eastandwest,inhs);
                String ishs = "0";
                for(BaseGoodsnote hsItem:hsNameRemarkList){
                    if("0".equals(hsItem.getInhs())){
                        if("0".equals(ishs)){
                            hsRemark = hsRemark+hsItem.getInhs()+":"+hsItem.getSpecialnotes()+";";
                            ishs = "1";
                        }
                    }else{
                        if(("1".equals(hsItem.getUnloadsite())) && ("1".equals(hsItem.getStation()))){
                            hsRemark = hsRemark+hsItem.getInhs()+":"+hsItem.getSpecialnotes()+";";
                        }else{
                            if(!"1".equals(hsItem.getUnloadsite())){
                                if((hsItem.getUnloadsite()).contains(unloadsite)){
                                    hsRemark = hsRemark+hsItem.getGoodsname()+":"+unloadsite+"站点"+hsItem.getSpecialnotes()+";";
                                }
                            }
                            if(!"1".equals(hsItem.getStation())){
                                if(station.contains(hsItem.getStation())){
                                    hsRemark = hsRemark+hsItem.getGoodsname()+":此班列的"+hsItem.getStation()+hsItem.getSpecialnotes()+";";
                                }
                            }
                        }
                    }
                }
            }
        }
        specialRemark.put("nameRemark",nameRemark);
        specialRemark.put("hsRemark",hsRemark);
        specialRemark.put("dangerRemark",dangerRemark);
        return specialRemark;
    }

    /**
     * 获取订单品名特殊备注
     */
    @Override
    public String goodsSpecialRemark(BusiShippingorders busiShippingorder){
        String msg = "";
        String goodsName = busiShippingorder.getGoodsName();//品名
        String classId = busiShippingorder.getClassId(); //班列id
        String orderUnloadsite = busiShippingorder.getOrderUnloadsite(); //上货站
        String nameRemark = "";
        String dangerRemark = "";
        String language = busiShippingorder.getLanguage();
        if(StringUtils.isNotEmpty(goodsName) && StringUtils.isNotEmpty(classId) && StringUtils.isNotEmpty(orderUnloadsite)){
            //疑似危险品特殊备注
            DangerousGoods dangerInfo = dangerousGoodsMapper.selectDangerousGoodsByName(goodsName);
            if(StringUtils.isNotNull(dangerInfo)){
                if("en".equals(language)){
                    dangerRemark = dangerInfo.getSpecialEnRemark();
                }else{
                    dangerRemark = dangerInfo.getSpecialremark();
                }
                msg += dangerRemark;
            }
            //品名特殊备注
            BusiClasses classInfo = busiClassesMapper.selectBusiClassesById(classId);
            if(StringUtils.isNotNull(classInfo)){
                String unloadsite = orderUnloadsite;//下货站
                String station = classInfo.getNameCn();
                String eastandwest = classInfo.getClassEastandwest(); //东西向 0西向 1东向
                List<BaseGoodsnote> goodsNameRemarkList = baseGoodsnoteMapper.selectBaseGoodsnoteListByName(eastandwest,goodsName);
                if(goodsNameRemarkList.size()>0){
                    for(BaseGoodsnote noteItem:goodsNameRemarkList){
                        if(("1".equals(noteItem.getUnloadsite())) && ("1".equals(noteItem.getStation()))){
                            if("en".equals(language)){
                                nameRemark = nameRemark+noteItem.getSpecialnotesEn()+";";
                            }else{
                                nameRemark = nameRemark+noteItem.getSpecialnotes()+";";
                            }
                        }else{
                            if(!"1".equals(noteItem.getUnloadsite())){
                                if((noteItem.getUnloadsite()).contains(unloadsite)){
                                    if("en".equals(language)){
                                        nameRemark = nameRemark+noteItem.getGoodsname()+":"+noteItem.getSpecialnotesEn()+";";
                                    }else{
                                        nameRemark = nameRemark+noteItem.getGoodsname()+":"+unloadsite+"站点"+noteItem.getSpecialnotes()+";";
                                    }
                                }
                            }
                            if(!"1".equals(noteItem.getStation())){
                                if(station.contains(noteItem.getStation())){
                                    if("en".equals(language)){
                                        nameRemark = nameRemark+noteItem.getGoodsname()+":"+noteItem.getSpecialnotesEn()+";";
                                    }else{
                                        nameRemark = nameRemark+noteItem.getGoodsname()+":此班列的"+noteItem.getStation()+"口岸"+noteItem.getSpecialnotes()+";";
                                    }
                                }
                            }
                        }
                    }
                    msg += nameRemark;
                }
            }

        }
        return msg;
    }

    /**
     * 获取订单hs特殊备注
     */
    @Override
    public String hsSpecialRemark(BusiShippingorders busiShippingorder){
        String msg = "";
        String inhs = busiShippingorder.getGoodsReport();//hs编码
        String classId = busiShippingorder.getClassId(); //班列id
        String orderUnloadsite = busiShippingorder.getOrderUnloadsite(); //上下货站
        String hsRemark = "";
        String language = busiShippingorder.getLanguage();
        if(StringUtils.isNotEmpty(inhs) && StringUtils.isNotEmpty(classId) && StringUtils.isNotEmpty(orderUnloadsite)){
            BusiClasses classInfo = busiClassesMapper.selectBusiClassesById(classId);
            if(StringUtils.isNotNull(classInfo)){
                String unloadsite = orderUnloadsite;//下货站
                String station = classInfo.getNameCn();
                String eastandwest = classInfo.getClassEastandwest(); //东西向 0西向 1东向
                //hs编码特殊备注
                List<BaseGoodsnote> hsNameRemarkList = baseGoodsnoteMapper.selectBaseGoodsnoteListByOrderHs(eastandwest,inhs);
                if(hsNameRemarkList.size()>0){
                    String ishs = "0";
                    for(BaseGoodsnote hsItem:hsNameRemarkList){
                        if("0".equals(hsItem.getInhs())){
                            if("0".equals(ishs)){
                                if("en".equals(language)){
                                    hsRemark = hsRemark+hsItem.getInhs()+":"+hsItem.getSpecialnotesEn()+";";
                                }else{
                                    hsRemark = hsRemark+hsItem.getInhs()+":"+hsItem.getSpecialnotes()+";";
                                }
                                ishs = "1";
                            }
                        }else{
                            if(("1".equals(hsItem.getUnloadsite())) && ("1".equals(hsItem.getStation()))){
                                if("en".equals(language)){
                                    hsRemark = hsRemark+hsItem.getInhs()+":"+hsItem.getSpecialnotesEn()+";";
                                }else{
                                    hsRemark = hsRemark+hsItem.getInhs()+":"+hsItem.getSpecialnotes()+";";
                                }
                            }else{
                                if(!"1".equals(hsItem.getUnloadsite())){
                                    if((hsItem.getUnloadsite()).contains(unloadsite)){
                                        if("en".equals(language)){
                                            hsRemark = hsRemark+hsItem.getGoodsname()+":"+hsItem.getSpecialnotesEn()+";";
                                        }else{
                                            hsRemark = hsRemark+hsItem.getGoodsname()+":"+unloadsite+"站点"+hsItem.getSpecialnotes()+";";
                                        }
                                    }
                                }
                                if(!"1".equals(hsItem.getStation())){
                                    if(station.contains(hsItem.getStation())){
                                        if("en".equals(language)){
                                            hsRemark = hsRemark+hsItem.getGoodsname()+":"+hsItem.getStation()+hsItem.getSpecialnotesEn()+";";
                                        }else{
                                            hsRemark = hsRemark+hsItem.getGoodsname()+":此班列的"+hsItem.getStation()+hsItem.getSpecialnotes()+";";
                                        }
                                    }
                                }
                            }
                        }
                    }
                    msg = hsRemark;
                }
            }
        }

        return msg;
    }

    //集疏发送邮件
    @Override
    public void sendEmailJs(String[] sendEmails, ShippingOrder orderInfoRabbmq,String binningwayOld){
        String isconsolidation = orderInfoRabbmq.getIsconsolidation(); //0整柜 1拼箱
        String binningway = orderInfoRabbmq.getShipOrderBinningway(); //委托ZIH提货 0是 1否
        //推荐人
        String clientTjr = orderInfoRabbmq.getClientTjr();
        if(StringUtils.isNotEmpty(clientTjr)){
            if(clientTjr.contains("/")){
                clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
            }
            orderInfoRabbmq.setClientTjr(clientTjr);
        }
        //邮件内容
        String content = "班列日期："+DateUtils.parseStr(orderInfoRabbmq.getClassDate())+"<br/>"
                +"货品名称："+orderInfoRabbmq.getGoodsName()+"<br/>"
                +"推荐人："+orderInfoRabbmq.getClientTjr()+"<br/>"
                +"订舱单位："+orderInfoRabbmq.getClientUnit()+"<br/>"
                +"订舱人："+orderInfoRabbmq.getClientContacts()+"<br/>"
                +"提货费报价编号:"+orderInfoRabbmq.getShipThCostNo()+"<br/>";
        if(StringUtils.isNotEmpty(orderInfoRabbmq.getOrderNumber())){
            content = content + "舱位号："+orderInfoRabbmq.getOrderNumber()+"<br/>";
        }
        if(isconsolidation.equals("0")){  //整柜
            content = content + "箱型:整柜，箱量："+orderInfoRabbmq.getContainerBoxamount()+"<br/>";
        }
        if(isconsolidation.equals("1")){  //拼箱
            content = content + "箱型：拼箱，总体积(KGS):"+orderInfoRabbmq.getGoodsCbm()+",总重量(m³):"+orderInfoRabbmq.getGoodsKgs()+"<br/>";
        }
        String str = "";
        String strold = "";
        if("3".equals(binningwayOld)){  //取消托书(订舱取消、再次订舱取消)
            strold = binningway.equals("0")?"委托ZIH提货":"客户自送货";
            str =  "已取消";
            content = content + "该托书已取消";
        }else{ //修改托书
            strold = binningwayOld.equals("0")?"委托ZIH提货":"客户自送货";
            str = binningway.equals("0")?"委托ZIH提货":"客户自送货";
            content = content + "状态由" +strold+ "变为" +str;
        }
        for(int i=0;i<sendEmails.length;i++){
            try {
                iMailService.sendHtmlMail(sendEmails[i], "订舱系统提醒-客户委托书变更信息", "订舱系统提醒：<br/>" + content);
            }catch (Exception e){
                log.error("集疏邮箱发送失败",e.toString(),e.getStackTrace());
            }
        }
    }

    //再次订舱驳回时给集疏发送邮件
    @Override
    public void sendEmailJsBh(ShippingOrder orderInfo){
        if(StringUtils.isNotNull(orderInfo)){
            //更新提交时间
            BusiShippingorders ordUpd = new BusiShippingorders();
            ordUpd.setOrderId(orderInfo.getOrderId());
            ordUpd.setTjTime(DateUtils.getNowDate());
            busiShippingorderMapper.updateBusiShippingorder(ordUpd);
            //回程委托提货托书发送邮件
            if("1".equals(orderInfo.getClassEastandwest()) && ("0".equals(orderInfo.getShipOrderBinningway()))){
                String[] sendEmails  = {"lihj@zih718.com","sunyk@zih718.com"};
                String binningwayOld = "3"; //取消托书
                sendEmailJs(sendEmails,orderInfo,binningwayOld);
            }
        }
    }

    //删除转待审核暂存表
    @Override
    public void deleteTem(String orderId){
        busiShippingorderApplyService.deleteBusiShippingorderApplyById(orderId);
        busiShippingorderGoodsApplyService.deleteBusiShippingorderGoodsApplyById(orderId);
    }

    //推送托书消息队列
    @Override
    public void orderInfoMQ(ShippingOrder orderInfoRabbmq,String messageType) throws JsonProcessingException {
        String orderId = orderInfoRabbmq.getOrderId();
        //更新货物跟踪表
        TrackGoodsStatus trackGoodsStatus=new TrackGoodsStatus();
        trackGoodsStatus.setOrderId(orderId);
        trackGoodsStatus=trackGoodsStatusMapper.checkTgs(trackGoodsStatus);//根据订单id查看数据库是否有该条数据
        if(StringUtils.isNotNull(trackGoodsStatus) &&!("2".equals(orderInfoRabbmq.getLineTypeid())&&"0".equals(orderInfoRabbmq.getClassEastandwest()))){
            trackGoodsStatus=new TrackGoodsStatus();
            trackGoodsStatus.setOrderId(orderId);
            trackGoodsStatus.setActualClassId(orderInfoRabbmq.getClassId());
            trackGoodsStatusMapper.updateTgs(trackGoodsStatus);
        }
        orderInfoRabbmq.setIsDelete("0");//删除标识 0否 1是
        orderInfoRabbmq.setMessageType(messageType); //操作类型
        //托书箱号信息
        List<String> boxnum = busiShippingorderMapper.selectBoxNum(orderId);
        if(boxnum.size()>0){
            orderInfoRabbmq.setBoxNo(StringUtils.join(boxnum,","));
        }
        //托书业务部门信息
        String deptcode = busiShippingorderMapper.selectDeptCode(orderId);
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
            goodsStandard = StringUtils.substring(goodsStandard,0,-1); //截掉末尾分号
            orderInfoRabbmq.setGoodsStandard(goodsStandard);
            orderInfoRabbmq.setOneStandard(oneStandard);
            //orderInfoRabbmq.setGoodsInfoDetail(goodsInfoDetail(orderInfoRabbmq.getInquiryRecordId()));
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
        log.info("托书推送消息队列:" + DateUtils.getNowDate() + "---" + orderId);
    }

    //获取货物详细信息
    @Override
    public String goodsInfoDetail(String inquiryRecordId){
        StringBuilder goodsDetail = new StringBuilder();
        if(StringUtils.isNotEmpty(inquiryRecordId)){
            //货物规格
            List<BookingInquiryGoodsDetails> goodsLists = busiShippingorderMapper.selectGoodsInfo(inquiryRecordId);
            if(goodsLists.size()>0){
                goodsLists.forEach(goodsList->{
                    goodsDetail.append("品名:")
                    .append(goodsList.getGoodsName()).append("/")
                    .append(goodsList.getGoodsAmount()).append("件/")
                    .append(goodsList.getGoodsWeight()).append("kg/")
                    .append(goodsList.getGoodsLength()).append("cm*")
                    .append(goodsList.getGoodsWidth()).append("cm*")
                    .append(goodsList.getGoodsHeight()).append("cm;\r\n");
                });
            }
        }
        if(StringUtils.isNotEmpty(goodsDetail)){
            return goodsDetail.toString();
        }else{
            return null;
        }
    }

    //推送排舱货物状态消息队列
    @Override
    public void trackInfoMQ(String orderId,String boxNum) throws JsonProcessingException{
        if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(boxNum)){
            GoodsTrackMq trackInfo = busiGoodsTrackMapper.selectTrackInfoToMq(orderId,boxNum);
            if(StringUtils.isNotNull(trackInfo)){
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                MessageProperties header = new MessageProperties();
                header.getHeaders().put("__TypeId__","Object");
                ObjectMapper objectMapper = new ObjectMapper();
                Message message = new Message(objectMapper.writeValueAsBytes(trackInfo), header);
                rabbitTemplate.convertAndSend(PlatformTrackRabbitmqConfig.PLATFORM_GOODSSTATUS_TOPIC_EXCHANGE, "platform.goods.status", message,correlationData);
            }
        }
    }

    //判断是否是特种箱
    @Override
    public boolean isSpecialBox(String containerType){
        Boolean result = false;
        String[] specialBoxType = {"20HOT","20HT","20OT","40HOT","40HT","40MT","40OT","40RF","45RF"};
        List<String> boxList = Arrays.asList(specialBoxType);
        if(boxList.contains(containerType)){
            result = true;
        }
        return result;
    }

    //获取箱型名称
    @Override
    public String containerTypeName(String containerType){
        String name = "";
        if(StringUtils.isNotEmpty(containerType)){
            switch (containerType)
            {
                case "20GP":name = "20尺普箱";break;
                case "20HC":name = "20尺高箱";break;
                case "40GP":name = "40尺普箱";break;
                case "40HC":name = "40尺高箱";break;
                case "40HQ":name = "40尺高箱";break;
                case "45HC":name = "45尺高箱";break;
                case "20HOT":name = "20尺超高开顶箱";break;
                case "20HT":name = "20尺挂衣箱";break;
                case "20OT":name = "20尺普高开顶箱";break;
                case "40HOT":name = "40尺超高开顶箱";break;
                case "40HT":name = "40尺挂衣箱";break;
                case "40MT":name = "40尺分层箱";break;
                case "40OT":name = "40尺普高开顶箱";break;
                case "40RF":name = "40尺冷藏";break;
                case "45RF":name = "45尺冷藏箱";break;
                case "LCL":name = "拼箱";break;
            }
        }
        return name;
    }

    //箱号校验
    @Override
    public boolean checkDigit(String containerNumber){
        if(StringUtils.isNotEmpty(containerNumber)){
            containerNumber = containerNumber.trim();
        }
        if (containerNumber == null || containerNumber.length() != 11)
        {
            return false;
        }
        Map<String, Integer> mapofCode = new HashMap<String, Integer>();
        mapofCode.put("A", 10);
        mapofCode.put("B", 12);
        mapofCode.put("C", 13);
        mapofCode.put("D", 14);
        mapofCode.put("E", 15);
        mapofCode.put("F", 16);
        mapofCode.put("G", 17);
        mapofCode.put("H", 18);
        mapofCode.put("I", 19);
        mapofCode.put("J", 20);
        mapofCode.put("K", 21);
        mapofCode.put("L", 23);
        mapofCode.put("M", 24);
        mapofCode.put("N", 25);
        mapofCode.put("O", 26);
        mapofCode.put("P", 27);
        mapofCode.put("Q", 28);
        mapofCode.put("R", 29);
        mapofCode.put("S", 30);
        mapofCode.put("T", 31);
        mapofCode.put("U", 32);
        mapofCode.put("V", 34);
        mapofCode.put("W", 35);
        mapofCode.put("X", 36);
        mapofCode.put("Y", 37);
        mapofCode.put("Z", 38);
        String constainerCode = containerNumber;
        int positon = 1;
//        int sum = 0;
        double sum = 0;
        try
        {
            for (int i = 0; i < constainerCode.length() - 1; i++)
            {
                String a = constainerCode.substring(i, i+1);
                if (mapofCode.containsKey(a))
                {
                    sum += mapofCode.get(a) * 1.00 * Math.pow(2, positon - 1);
                }
                else
                {
                    sum += Double.parseDouble(a) * Math.pow(2, positon - 1);
                }
                positon++;
            }
            double checkdigit = sum % 11 % 10;
            Boolean result = checkdigit == Integer.valueOf(constainerCode.substring(constainerCode.length() - 1),16);
            return result;
        }
        catch (Exception ex)
        {
            Boolean result = false;
            return result;
        }
    }

    //计算整柜价格
    @Override
    public String zgPrice(String price,Integer containerNum,String boxAmount){
        String newPrice = "";
        if(StringUtils.isNotEmpty(boxAmount)){
            int boxAmountI = Integer.valueOf(boxAmount);
            if(StringUtils.isNotEmpty(price) && StringUtils.isNotNull(containerNum)){
                DecimalFormat df=new DecimalFormat(".##");
                newPrice = df.format(Double.valueOf(price)/containerNum*boxAmountI);
            }
        }
        return newPrice;
    }

    //计算提还箱费价格
    @Override
    public String zgBoxFee(String price,Integer containerNum,String boxAmount){
        String newPrice = "";
        if(StringUtils.isNotEmpty(boxAmount)){
            int boxAmountI = Integer.valueOf(boxAmount);
            if(StringUtils.isNotEmpty(price) && StringUtils.isNotNull(containerNum)){
                StringBuilder sb = new StringBuilder(price.substring(0,1) + " ");
                int PriceC = ConvertUtils.reInt(price);
                if(containerNum!=0){
                    newPrice = String.valueOf(PriceC/containerNum*boxAmountI);
                }
                newPrice = sb.append(newPrice).toString();
            }
        }
        return newPrice;
    }

    //判断是否增加转待审核次数(订单的去程N-4(这个4只能有工作日)  12:00之后，回程N-5(这个5只能有工作日)  12:00之后)
    @Override
    public String isZdCount(Date classDate,String classEastandwest){
        String isAdd = "0"; //是否增加一次 0否 1是
        if(StringUtils.isNotNull(classDate) && StringUtils.isNotEmpty(classEastandwest)){
            Calendar cal = Calendar.getInstance(); //从周日开始为1
            Date today = new Date(); //当前日期
            Long ttime = today.getTime(); //当前时间戳
            int dayn = 0;
            int dayActual = 0;
            if("0".equals(classEastandwest)){
                dayn = 4;
                dayActual = 4;
            }
            if("1".equals(classEastandwest)){
                dayn = 5;
                dayActual = 5;
            }
            //判断是否包含周六周日
            if(dayn>0){
                for(int i=0;i<=dayn;i++){
                    Date fristDate = DateUtils.dataChange(classDate,i);
                    cal.setTime(fristDate);
                    int fday = cal.get(Calendar.DAY_OF_WEEK);
                    if(fday==1||fday==7){
                        dayActual++;
                    }
                }
                //开始日期
                Date startDate = DateUtils.dataChange(classDate,dayActual);
                Long startTime = startDate.getTime() + (12 * 60 * 60 * 1000);
                if(ttime>=startTime){
                    isAdd = "1"; //当前时间大于开始时间，计算转待次数
                }
            }
        }
        return isAdd;
    }

    //推送排舱信息/托书信息删除消息队列
    @Override
    public void orderTrackDeleteMQ(OrderGoodsTrackDel orderTrackDel,String type) throws JsonProcessingException{
        if(StringUtils.isNotNull(orderTrackDel)){
            orderTrackDel.setDelType(type);
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            MessageProperties header = new MessageProperties();
            header.getHeaders().put("__TypeId__","Object");
            ObjectMapper objectMapper = new ObjectMapper();
            Message message = new Message(objectMapper.writeValueAsBytes(orderTrackDel), header);
            rabbitTemplate.convertAndSend(OrderTrackDelConfig.PT_ORDER_TRACK_DELETE_EXCHANGE, "pt.track.delete.pc", message,correlationData);
        }
    }

    public byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
        }
        return bytes;
    }

    //从托书中筛选询价表信息(不用)
    @Override
    public BookingInquiry inquiryInfo(BusiShippingorders busiShippingorder){
        BookingInquiry inquiryInfo = new BookingInquiry();
        inquiryInfo.setSenderProvince(busiShippingorder.getSenderProvince()); //
        inquiryInfo.setSenderCity(busiShippingorder.getSenderCity()); //
        inquiryInfo.setSenderArea(busiShippingorder.getSenderArea()); //
        inquiryInfo.setShipmentPlace(busiShippingorder.getShipmentPlace()); //
        inquiryInfo.setUploadStation(busiShippingorder.getOrderUploadsite());
        inquiryInfo.setReceiveProvince(busiShippingorder.getReceiveProvince()); //
        inquiryInfo.setReceiveCity(busiShippingorder.getReceiveCity()); //
        inquiryInfo.setReceiveArea(busiShippingorder.getReceiveArea()); //
        inquiryInfo.setReceiptPlace(busiShippingorder.getReceiptPlace()); //
        inquiryInfo.setDropStation(busiShippingorder.getOrderUnloadsite());
        inquiryInfo.setBookingService(busiShippingorder.getBookingService());
        inquiryInfo.setIsPickUp(busiShippingorder.getShipOrderBinningway());
        inquiryInfo.setIsDelivery(busiShippingorder.getReceiveOrderIspart());
        inquiryInfo.setIsOrderCustoms(busiShippingorder.getClientOrderBindingway());
        inquiryInfo.setIsClearCustoms(busiShippingorder.getReceiveOrderIsclearance());
        inquiryInfo.setContainerBelong(busiShippingorder.getOrderAuditBelongto());
        inquiryInfo.setGoodsType(busiShippingorder.getIsconsolidation());
        inquiryInfo.setContainerType(busiShippingorder.getContainerType());
        inquiryInfo.setContainerNum(Integer.valueOf(busiShippingorder.getContainerBoxamount()));
        inquiryInfo.setPackageType(busiShippingorder.getGoodsPacking());
        inquiryInfo.setIsStack(busiShippingorder.getGoodsCannotpileup());
        inquiryInfo.setTotalAmount(busiShippingorder.getTotalAmount()); //
        inquiryInfo.setTotalWeight(busiShippingorder.getTotalWeight()); //
        inquiryInfo.setTotalVolume(busiShippingorder.getTotalVolume()); //
        inquiryInfo.setInquiryTime(DateUtils.getNowDate());
        inquiryInfo.setClientId(busiShippingorder.getClientId());
        inquiryInfo.setLineType(busiShippingorder.getLineTypeid());
        inquiryInfo.setHxAddress(busiShippingorder.getReceiveHxAddress());
        inquiryInfo.setEastOrWest(busiShippingorder.getClassEastandwest());
        inquiryInfo.setUploadStationNum(busiShippingorder.getUploadStationNum()); //
        inquiryInfo.setDropStationNum(busiShippingorder.getDropStationNum()); //
        inquiryInfo.setRemark(busiShippingorder.getRemark());
        inquiryInfo.setTruckType(busiShippingorder.getTruckType());
        inquiryInfo.setLimitation(busiShippingorder.getLimitation());
        String Binningway = busiShippingorder.getShipOrderBinningway();
        if(StringUtils.isNotEmpty(Binningway)){
            if(Binningway.equals("0")){
                inquiryInfo.setDeliveryType(busiShippingorder.getShipThTypeId());
            }
            if(Binningway.equals("1")){
                String shipZsTypeId = busiShippingorder.getShipZsTypeId();
                if(StringUtils.isNotEmpty(shipZsTypeId)){
                    if(shipZsTypeId.equals("0")){
                        inquiryInfo.setDeliveryType("1");
                    }
                    if(shipZsTypeId.equals("1")){
                        inquiryInfo.setDeliveryType("0");
                    }
                }
            }
        }
        inquiryInfo.setClientType(busiShippingorder.getClientType()); //
        inquiryInfo.setClientUnit(busiShippingorder.getClientUnit());
        inquiryInfo.setGoodsFragile(busiShippingorder.getGoodsFragile());
        inquiryInfo.setInquiryState("1");
        inquiryInfo.setCreateTime(DateUtils.getNowDate()); //
        inquiryInfo.setHxAddressId(busiShippingorder.getHxAddressId()); //
        inquiryInfo.setQgfy(busiShippingorder.getQgfy()); //
        inquiryInfo.setCzfy(busiShippingorder.getCzfy()); //
        return inquiryInfo;
    }

}
