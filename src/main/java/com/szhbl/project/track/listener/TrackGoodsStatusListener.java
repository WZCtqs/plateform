package com.szhbl.project.track.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.track.GoodsTrackDcz;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import com.szhbl.project.track.domain.TrackGoStation;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.service.ITrackGoStationService;
import com.szhbl.project.track.service.ITrackGoodsStatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TrackGoodsStatusListener {

    @Autowired
    private ITrackGoodsStatusService trackGoodsStatusService;
    @Autowired
    private IBusiZyInfoService busiZyInfoService;
    @Autowired
    private ITrackGoStationService trackGoStationService;
    @Autowired
    private CommonService commonService;

    /**
     * 监听拼箱智能场站箱舱匹配数据
     *   路由  gnczsend_inquiry_keys    交换机 gncz.system.inquiry.exchange
     * @param channel
     * @param message
     */
    @RabbitListener(queues = "gncz_system_sendqueue_toszbl_zgqd")
    public void getPxczXcpp(Channel channel, Message message) {
        try {
            log.debug("获取拼箱智能场站装柜清单箱舱匹配数据",new String(message.getBody()));
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);

            //        /// 包装方式  px_packing { get; set; / 数量 px_number
            //        /// 规格（长*宽*高*件数） px_box_type { get; set; }//规格（长*宽*高*件数）14 毛重 px_box_number { get; set; }//单件重量15 毛重
            //        /// 总重 px_LongAndWide { get; set; }//总重16 实际体积（m3） px_volume { get; set; }//实际体积（m3）
            //        /// 结算体积（m3）g px_Settlement_volume { get; set; }//结算体积（m3） 特殊货物收费（USD） px_car_number { get; set; }//特殊货物收费（USD）
            //        /// 其他费用人民币 px_otherCharge { get; set; }//其他费用人民币/ 备注 px_lead_number { get; set; }//备注
            //        //  public string px_Checkman { get; set; }//标签粘贴
            //        //  public string px_enter_time { get; set; }//接货员
            //        /// 箱号 xianghao { get; set; }//箱号
            //        /// 箱型 xiangxing { get; set; }//箱型
            //        /// 责任人 px_weight { get; set; }//责任人
            //        /// 到货情况 px_person_charge { get; set; }//到货情况
            //        /// 托书id order_id { get; set; }
            //        /// 舱位号 orderNumber { get; set; }
            //        /// 班列日期 ClassDate { get; set; }
            //        /// 是否删除 0否 1是 isDelect { get; set; }
            //        /// 班列编号 ClassNumber { get; set; }
            //        /// 入场日期 px_entry_date { get; set; }
            //        /// 入场时间 px_entry_time { get; set; }
            //        /// 进站日期/出场日期 px_enter_car { get; set; }
            //        /// 进站时间/出场时间 px_enter_lead_number { get; set; }
            log.debug("获取拼箱智能场站装柜清单箱舱匹配数据map-----------"+map);
            //箱号检验
            if(map.get("xianghao").trim().length()==11){
                TrackGoodsStatus trackGoodsStatus=new TrackGoodsStatus();
                if("0".equals(map.get("isDelect"))){//修改
                    trackGoodsStatus.setOrderId(map.get("order_id"));
                    trackGoodsStatus.setBoxNum(map.get("xianghao"));
                    trackGoodsStatus=trackGoodsStatusService.checkTgs(trackGoodsStatus);//根据订单id和箱号查看数据库是否有该条数据
                    if(trackGoodsStatus!=null){//有数据，修改
                        trackGoodsStatus.setGoodsVolume(map.get("px_Settlement_volume"));// 计费体积
                        //trackGoodsStatus.setIsBalance(map.get("px_Settlement_volume"));//是否偏载
                        trackGoodsStatus.setInStationDate(DateUtils.parseDate(map.get("px_enter_car")));//进站日期
                        //trackGoodsStatus.setIsStable(map.get("is_jg"));//是否加固
                        trackGoodsStatus.setInStationTime(map.get("px_enter_lead_number"));//进站时间
                        trackGoodsStatus.setInSpaceDate(DateUtils.parseDate(map.get("px_entry_date")));//入场日期
                        trackGoodsStatus.setInSpaceTime(map.get("px_entry_time"));//入场时间
                        trackGoodsStatus.setBoxType(map.get("xiangxing"));//箱型
                        trackGoodsStatus.setFromSystem("拼箱场站修改");
                        trackGoodsStatusService.updateTgs(trackGoodsStatus);
                    }else{//新增
                        trackGoodsStatus=new TrackGoodsStatus();
                        trackGoodsStatus.setOrderId(map.get("order_id"));
                        trackGoodsStatus.setBoxNum(map.get("xianghao"));
                        trackGoodsStatus.setGoodsVolume(map.get("px_Settlement_volume"));// 计费体积
                        //trackGoodsStatus.setIsBalance(map.get("px_Settlement_volume"));//是否偏载
                        // trackGoodsStatus.setIsStable(map.get("is_jg"));//是否加固
                        trackGoodsStatus.setInStationDate(DateUtils.parseDate(map.get("px_enter_car")));//进站日期
                        trackGoodsStatus.setInSpaceDate(DateUtils.parseDate(map.get("px_entry_date")));//入场日期
                        trackGoodsStatus.setInStationTime(map.get("px_enter_lead_number"));//进站时间
                        trackGoodsStatus.setInSpaceTime(map.get("px_entry_time"));//入场时间
                        trackGoodsStatus.setBoxType(map.get("xiangxing"));//箱型
                        trackGoodsStatus.setFromSystem("拼箱场站添加");
                        trackGoodsStatusService.insertXcppTgs(trackGoodsStatus);
                        BusiZyInfo zyinfo = new BusiZyInfo();
                        zyinfo.setTrackId(trackGoodsStatus.getId()); //货物状态表id
                        zyinfo.setOrderId(map.get("order_id")); //订单id
                        zyinfo.setOrderNumber(map.get("ordernumber")); //订单编号
                        zyinfo.setXianghao(map.get("xianghao"));//箱号
                        busiZyInfoService.insertBusiZyInfo(zyinfo);
                    }
                }else if("1".equals(map.get("isDelect"))){//删除
                    trackGoodsStatus.setOrderId(map.get("order_id"));
                    trackGoodsStatus.setDelFlag(1);
                    trackGoodsStatus.setBoxNum(map.get("xianghao"));
                    trackGoodsStatus.setFromSystem("拼箱场站删除");
                    trackGoodsStatusService.deleteXcppTgs(trackGoodsStatus);
                    busiZyInfoService.deleteZyInfoByTrack(map.get("order_id"),map.get("xianghao"));
                }
            }else{
                log.debug("获取拼箱智能场站装柜清单箱舱匹配数据箱号错误,箱号:"+map.get("xianghao"));
            }
        } catch (IOException e) {
            log.error("获取拼箱智能场站装柜清单箱舱匹配数据失败：{}",e.toString(),e.getStackTrace());
            throw new RuntimeException("获取拼箱智能场站装柜清单箱舱匹配数据失败");
        }
    }

    /*//@RabbitListener(queues = "gncz_system_sendqueue_toszbl_zgqd")
    public void getGwczXcpp(Channel channel, Message message) {
        getReturnData(channel,message,"获取国外场站箱舱匹配数据");
    }
    public void getReturnData(Channel channel, Message message,String systemName) {

    }*/

    /**
     * 获取国外场站去程整柜数据
     * @param channel
     * @param message
     */
   // @RabbitListener(queues = "gncz_system_sendqueue_toszbl_zgqd")
    public void getGoStataionAddress(Channel channel, Message message) {
        try {
            System.out.println("获取国外场站去程整柜数据");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            TrackGoStation trackGoStation= FastJsonUtils.json2Bean(new String(message.getBody()), TrackGoStation.class);
            List<TrackGoStation> list = trackGoStationService.selectByTgn(trackGoStation);
            if(list==null||list.size()==0){
                trackGoStationService.insertTgn(trackGoStation);
            }
        } catch (IOException e) {
            log.error("获取国外场站去程整柜数据失败：{}",e.toString(),e.getStackTrace());
            throw new RuntimeException("获取国外场站去程整柜数据失败");
        }
    }

   /* public static bool checkDigit(String containerNumber)
    {
        if (containerNumber == null || containerNumber.Trim().Length != 11)
        {
            return false;
        }
        Dictionary<string, int> mapofCode = new Dictionary<string, int>();
        mapofCode.Add("A", 10);
        mapofCode.Add("B", 12);
        mapofCode.Add("C", 13);
        mapofCode.Add("D", 14);
        mapofCode.Add("E", 15);
        mapofCode.Add("F", 16);
        mapofCode.Add("G", 17);
        mapofCode.Add("H", 18);
        mapofCode.Add("I", 19);
        mapofCode.Add("J", 20);
        mapofCode.Add("K", 21);
        mapofCode.Add("L", 23);
        mapofCode.Add("M", 24);
        mapofCode.Add("N", 25);
        mapofCode.Add("O", 26);
        mapofCode.Add("P", 27);
        mapofCode.Add("Q", 28);
        mapofCode.Add("R", 29);
        mapofCode.Add("S", 30);
        mapofCode.Add("T", 31);
        mapofCode.Add("U", 32);
        mapofCode.Add("V", 34);
        mapofCode.Add("W", 35);
        mapofCode.Add("X", 36);
        mapofCode.Add("Y", 37);
        mapofCode.Add("Z", 38);
        String constainerCode = containerNumber;
        int positon = 1;
        double sum = 0;
        try
        {
            for (int i = 0; i < constainerCode.Length - 1; i++)
            {
                string a = constainerCode.Substring(i, 1);
                if (mapofCode.ContainsKey(constainerCode.Substring(i, 1)))
                {
                    sum += Convert.ToDouble(mapofCode[constainerCode.Substring(i, 1)]) * Math.Pow(2, positon - 1);
                }
                else
                {
                    sum += Convert.ToDouble(constainerCode.Substring(i, 1))
                            * Math.Pow(2, positon - 1);
                }
                positon++;
            }
            double checkdigit = sum % 11 % 10;
            bool result = checkdigit == Convert.ToInt16(constainerCode.Substring(constainerCode.Length - 1,
                    1));
            return result;
        }
        catch (Exception ex)
        {
            bool result = false;
            return result;
        }
    }*/

    //checkDigit(dt.Rows[i]["xianghao"].ToString().Trim()) == true ? "箱号正确" : "箱号错误";


}
