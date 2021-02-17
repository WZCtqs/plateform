package com.szhbl.project.order.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.trackzy.DkaGoodsStatusRabbitmqConfig;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.BusiZyOrder;
import com.szhbl.project.order.mapper.BusiZyOrderMapper;
import com.szhbl.project.order.service.IBusiZyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DkaGoodsStatusListener {
    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    @Autowired
    private BusiZyOrderMapper busiZyOrderMapper;

    /**
     * 监听大口岸系统排舱信息
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DkaGoodsStatusRabbitmqConfig.DKA_GOODS_STATUS_QUEUE_BLPT)
    public void orderInfoListener(Channel channel, Message message){
        try{
            System.out.println("大口岸随车备注");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            BusiZyInfo zyinfo = FastJsonUtils.json2Bean(new String(message.getBody()), BusiZyInfo.class);
            if(StringUtils.isNotNull(zyinfo)){
                String orderId = zyinfo.getOrderId(); //托书id
                String container = zyinfo.getContainer(); //箱号
                if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(container)){
                    //托书id和箱号不为空，以箱号为单位，更新中亚表
                    BusiZyInfo zyAdd = new BusiZyInfo();
                    zyAdd.setOrderId(zyinfo.getOrderId());
                    zyAdd.setXianghao(zyinfo.getContainer());
                    if(StringUtils.isNotEmpty(zyinfo.getConStatus_suiche())){  //箱号随车状态
                        zyAdd.setSuichestate(zyinfo.getConStatus_suiche());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getConAreviewer_suiche())) {  //随车审核人
                        zyAdd.setScOperator(zyinfo.getConAreviewer_suiche());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getConRemark_suiche())){  //随车备注
                        zyAdd.setScRemark(zyinfo.getConRemark_suiche());
                    }
                    zyAdd.setScOperatime(DateUtils.getNowDate()); //随车组操作时间
                    busiZyInfoService.updateBusiZyInfoByXh(zyAdd);
                }
                if(StringUtils.isNotEmpty(orderId) && StringUtils.isEmpty(container)){
                    //箱号为空，以托书为单位，更新托书货物表
                    BusiZyOrder zyOrderAdd = new BusiZyOrder();
                    zyOrderAdd.setOrderId(orderId);
                    if(StringUtils.isNotEmpty(zyinfo.getAreviewer_suiche())){ //托书随车负责人
                        zyOrderAdd.setScPrincipal(zyinfo.getAreviewer_suiche());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getRemark_suiche())){  //托书随车备注
                        zyOrderAdd.setScremark(zyinfo.getRemark_suiche());
                    }
                    zyOrderAdd.setCreateTime(DateUtils.getNowDate());
                    busiZyOrderMapper.updateBusiZyOrder(zyOrderAdd);
                }
            }
        }catch (IOException e) {
            log.error("监听大口岸系统排舱信息失败：{}",e.toString(),e.getStackTrace());
        }
    }
}
