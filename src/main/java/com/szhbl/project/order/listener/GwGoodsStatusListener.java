package com.szhbl.project.order.listener;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.trackzy.GwGoodsStatusRabbitmqConfig;
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
public class GwGoodsStatusListener {
    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    @Autowired
    private BusiZyOrderMapper busiZyOrderMapper;
    /**
     * 监听关务系统（报关单证、关务状态、运单是否上传，中文收货人名字，跟单部审核状态）
     * @param channel
     * @param message
     */
    @RabbitListener(queues = GwGoodsStatusRabbitmqConfig.Gw_GOODS_STATUS_QUEUE_BLPT)
    public void orderInfoListener(Channel channel, Message message){
        try {
            System.out.println("关务系统货物跟踪");
            log.debug("关务系统排舱body：{body}",new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            BusiZyInfo zyinfo = FastJsonUtils.json2Bean(new String(message.getBody()), BusiZyInfo.class);
            if(StringUtils.isNotNull(zyinfo)){
                String orderId = zyinfo.getOrder_ID();
                String xianghao = zyinfo.getXianghao();
                if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(xianghao)){
                    //托书id和箱号不为空，以箱号为单位，更新中亚表
                    BusiZyInfo zyadd = new BusiZyInfo();
                    zyadd.setOrderId(orderId);
                    zyadd.setXianghao(zyinfo.getXianghao());
                    if(StringUtils.isNotEmpty(zyinfo.getDeclareisready_gd())){ //报关单证
                        zyadd.setBaoguanDocu(zyinfo.getDeclareisready_gd());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getSuicheisready_gd())){ //随车单证
                        zyadd.setSuicheDocu(zyinfo.getSuicheisready_gd());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getPaicangExamine_gd())){  //跟单部审核状态
                        zyadd.setGdCheck(zyinfo.getPaicangExamine_gd());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getPaicangExaminedate_gd())){  //跟单部审核时间
                        zyadd.setGdtime(DateUtils.parseDate(zyinfo.getPaicangExaminedate_gd()));
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getPaicangRemark_gd())){ //跟单备注
                        zyadd.setGdRemark(zyinfo.getPaicangRemark_gd());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getContainerdeclareStatus())){  //关务状态
                        zyadd.setGuanwustate(zyinfo.getContainerdeclareStatus());
                    }else{
                        zyadd.setGuanwustateS("1");
                    }
                    if(StringUtils.isNotNull(zyinfo.getContainerDeclareCount())){  //票数
                        zyadd.setGwPiaoshu(zyinfo.getContainerDeclareCount());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getContainerDeclareRemark())){ //关务备注
                        zyadd.setGwRemark(zyinfo.getContainerDeclareRemark());
                    }else{
                        zyadd.setGwRemarkS("1");
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getDeclare_fenghao())){  //监管区更改后封号
                        zyadd.setGwfenghao(zyinfo.getDeclare_fenghao());
                    }
                    zyadd.setGwOperatime(DateUtils.getNowDate()); //报关操作时间
                    busiZyInfoService.updateBusiZyInfoByXh(zyadd);
                }
                if(StringUtils.isNotEmpty(orderId) && StringUtils.isEmpty(xianghao)){
                    //箱号为空，以托书为单位，更新托书货物表
                    BusiZyOrder zyOrderAdd = new BusiZyOrder();
                    zyOrderAdd.setOrderId(orderId);
                    if(StringUtils.isNotEmpty(zyinfo.getDeclarant())){  //报关负责人
                        zyOrderAdd.setBgPrincipal(zyinfo.getDeclarant());
                    }
                    if(StringUtils.isNotNull(zyinfo.getDeclareCount())){ //托书报关票数
                        zyOrderAdd.setBgVotesNumber(zyinfo.getDeclareCount());
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getDeclareStatus())){  //托书报关状态
                        zyOrderAdd.setBgProgress(zyinfo.getDeclareStatus());
                    }else{
                        zyOrderAdd.setBgProgressS("1");
                    }
                    if(StringUtils.isNotEmpty(zyinfo.getDeclareRemark())){  //托书报关备注
                        zyOrderAdd.setBgRemark(zyinfo.getDeclareRemark());
                    }else{
                        zyOrderAdd.setBgRemarkS("1");
                    }
                    zyOrderAdd.setCreateTime(DateUtils.getNowDate());
                    busiZyOrderMapper.updateBusiZyOrder(zyOrderAdd);
                }
            }
        }catch (IOException e) {
            log.error("监听关务系统排舱信息失败：{}",e.toString(),e.getStackTrace());
        }
    }
}
