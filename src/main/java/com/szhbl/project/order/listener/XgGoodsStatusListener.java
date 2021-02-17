package com.szhbl.project.order.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.trackzy.XgGoodsStatusRabbitmqConfig;
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
public class XgGoodsStatusListener {

    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    /**
     * 监听箱管系统排舱信息（箱况、是否维修、是否更换铭牌、箱管部审核状态、是否发送邮件、箱损是否上传）
     * @param channel
     * @param message
     */
    @RabbitListener(queues = XgGoodsStatusRabbitmqConfig.XG_GOODS_STATUS_QUEUE_BLPT)
    public void orderInfoListener(Channel channel, Message message){
        try {
            System.out.println("箱管系统货物跟踪");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            BusiZyInfo zyinfo = FastJsonUtils.json2Bean(new String(message.getBody()), BusiZyInfo.class);
            if(StringUtils.isNotNull(zyinfo)){
                BusiZyInfo zyadd = new BusiZyInfo();
                zyadd.setOrderId(zyinfo.getOrderId());
                zyadd.setXianghao(zyinfo.getXianghao());
                if(StringUtils.isNotEmpty(zyinfo.getXgXiangkuang())){ //箱况
                    zyadd.setXgXiangkuang(zyinfo.getXgXiangkuang());
                }
                if(StringUtils.isNotEmpty(zyinfo.getXgIsrepair())){  //是否维修
                    zyadd.setXgIsrepair(zyinfo.getXgIsrepair());
                }
                if(StringUtils.isNotEmpty(zyinfo.getXgIsmingpai())){ //是否更换铭牌
                    zyadd.setXgIsmingpai(zyinfo.getXgIsmingpai());
                }
                if(StringUtils.isNotEmpty(zyinfo.getXgCheck())){  //箱管审核状态
                    zyadd.setXgCheck(zyinfo.getXgCheck());
                }
                if(StringUtils.isNotNull(zyinfo.getXgtime())){ //箱管审核时间
                    zyadd.setXgtime(zyinfo.getXgtime());
                }
                if(StringUtils.isNotEmpty(zyinfo.getXgSend())){  //箱管是否发送邮件
                    zyadd.setXgSend(zyinfo.getXgSend());
                }
                if(StringUtils.isNotEmpty(zyinfo.getXgXsstate())){ //箱损是否上传
                    zyadd.setXgXsstate(zyinfo.getXgXsstate());
                }
                if(StringUtils.isNotNull(zyinfo.getMaxTotalweight())){  //箱子最大运营总重量
                    zyadd.setMaxTotalweight(zyinfo.getMaxTotalweight());
                }
                if(StringUtils.isNotNull(zyinfo.getMaxLoad())){   //箱子最大载重
                    zyadd.setMaxLoad(zyinfo.getMaxLoad());
                }
                if(StringUtils.isNotEmpty(zyinfo.getIsoCode())){  //箱型的ios代码
                    zyadd.setIsoCode(zyinfo.getIsoCode());
                }
                if(StringUtils.isNotNull(zyinfo.getContainerKgs())){  //箱重
                    zyadd.setContainerKgs(zyinfo.getContainerKgs());
                }
                busiZyInfoService.updateBusiZyInfoByXh(zyadd);
            }
        }catch (IOException e) {
            log.error("测试箱管系统排舱信息失败：{}",e.toString(),e.getStackTrace());
        }
    }
}
