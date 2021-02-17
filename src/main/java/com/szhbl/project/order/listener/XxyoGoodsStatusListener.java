package com.szhbl.project.order.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.trackzy.XxyoGoodsStatusRabbitmqConfig;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.service.IBusiZyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.openxmlformats.schemas.drawingml.x2006.main.STAdjCoordinate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class XxyoGoodsStatusListener {

    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    /**
     * 监听箱行亚欧排舱信息（是否办好票、公路部审核状态）
     * @param channel
     * @param message
     */
    @RabbitListener(queues = XxyoGoodsStatusRabbitmqConfig.XXYO_GOODS_STATUS_QUEUE_BLPT)
    public void orderInfoListener(Channel channel, Message message){
        try {
            System.out.println("箱行亚欧系统货物跟踪");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            Map<String,String> mapinfo = FastJsonUtils.json2Bean(new String(message.getBody(),"utf-8"), HashMap.class);
            if(StringUtils.isNotNull(mapinfo)){
                BusiZyInfo zyadd = new BusiZyInfo();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(StringUtils.isNotEmpty(mapinfo.get("orderId")) && StringUtils.isNotEmpty(mapinfo.get("containerNo"))){
                    zyadd.setOrderId(mapinfo.get("orderId"));
                    zyadd.setXianghao(mapinfo.get("containerNo"));
                    if(StringUtils.isNotEmpty(mapinfo.get("ticketState"))){  //是否办好票 0办好票 1未办好
                        if((mapinfo.get("ticketState")).equals("0")){
                            zyadd.setRoadIsbill("否");
                            zyadd.setRoadCheck("否");
                            zyadd.setRoadtime(DateUtils.getNowDate());
                        }
                        if((mapinfo.get("ticketState")).equals("1")){
                            zyadd.setRoadIsbill("是");
                            zyadd.setRoadCheck("是");
                            zyadd.setRoadtime(DateUtils.getNowDate());
                        }
                    }
                    if(StringUtils.isNotEmpty(mapinfo.get("planunloadtime"))){ //公路预计到场时间
                        zyadd.setPlanunloadtime(sdf.parse(mapinfo.get("planunloadtime")));
                    }
                    if(StringUtils.isNotEmpty(mapinfo.get("fenghao"))){  //封号
                        zyadd.setFenghao(mapinfo.get("fenghao"));
                    }
                    if(StringUtils.isNotEmpty(mapinfo.get("fhtime"))){  //封号更新时间
                        zyadd.setFhtime(sdf.parse(mapinfo.get("fhtime")));
                    }
                    if(StringUtils.isNotEmpty(mapinfo.get("shifenghao"))){  //施封号
                        zyadd.setShifenghao(mapinfo.get("shifenghao"));
                    }
                    if(StringUtils.isNotEmpty(mapinfo.get("goodsname"))){  //公路部上传的办票货物品名
                        zyadd.setGoodsname(mapinfo.get("goodsname"));
                    }
                    busiZyInfoService.updateBusiZyInfoByXh(zyadd);
                }
            }
        }catch (IOException | ParseException e) {
            log.error("监听箱行亚欧（是否办好票、公路部审核状态）失败：{}",e.toString(),e.getStackTrace());
        }
    }


}
