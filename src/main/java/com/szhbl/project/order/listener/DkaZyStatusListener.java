package com.szhbl.project.order.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.trackzy.DkaGoodsStatusRabbitmqConfig;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.service.IBusiZyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DkaZyStatusListener {
    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    @RabbitListener(queues = DkaGoodsStatusRabbitmqConfig.DKA_ZY_ORDEREXAMINE_QUEUE_BLPT)
    public void orderInfoListener(Channel channel, Message message){
        //监听大口岸运单审核状态
        try{
            System.out.println("大口岸运单审核状态");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            BusiZyInfo zyinfo = FastJsonUtils.json2Bean(new String(message.getBody()), BusiZyInfo.class);
            if(StringUtils.isNotNull(zyinfo)){
                String orderId = zyinfo.getOrderId(); //托书id
                String container = zyinfo.getContainer(); //箱号
                if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(container)){
                    BusiZyInfo zyAdd = new BusiZyInfo();
                    zyAdd.setOrderId(zyinfo.getOrderId());
                    zyAdd.setXianghao(zyinfo.getContainer());
                    if(StringUtils.isNotEmpty(zyinfo.getIs_yundanSate())){
                        zyAdd.setIsApplynum(zyinfo.getIs_yundanSate());
                    }
                    if(StringUtils.isNotNull(zyinfo.getDl_checktime())){
                        zyAdd.setDlChecktime(zyinfo.getDl_checktime());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getYd_Operator())){
                        zyAdd.setYdOperator(zyinfo.getYd_Operator());
                    }
                    busiZyInfoService.updateBusiZyInfoByXh(zyAdd);
                }
            }
        }catch (IOException e) {
            log.error("监听大口岸系统运单审核失败：{}",e.toString(),e.getStackTrace());
        }
    }
}
