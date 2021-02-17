package com.szhbl.project.order.handler;

import com.szhbl.framework.config.rabbit.order.OrderToCheckRabbitmqConfig;
import com.szhbl.project.order.domain.BusiOrderTocheckInquiry;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.domain.vo.TOCheckOrder;
import com.szhbl.project.order.mapper.ShippingorderExaminfoMapper;
import com.szhbl.project.order.service.IBusiOrderTocheckInquiryService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @Description : 转待审核通知子系统处理
 * @Author : wangzhichao
 * @Date: 2020-07-18 14:16
 */
@Slf4j
@Component
public class ToCheckNotice {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ShippingorderExaminfoMapper shippingorderExaminfoMapper;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private IBusiOrderTocheckInquiryService tocheckInquiryService;

    /**
     * 发送全部子系统消息队列
     *
     * @param orderId
     * @param status
     * @param updateBy
     */
    public void noticeSonSystem(String orderId, String status, String updateBy) {
        ShippingorderExaminfo shippingorderExaminfo = shippingorderExaminfoMapper.selectShippingorderExaminfoByOrderId(orderId);
        // 获取托书信息
        BusiShippingorders shippingorders = busiShippingorderService.selectForTocheckNoticeByOrderId(orderId);
        BusiOrderTocheckInquiry tocheckInquiry = tocheckInquiryService.selectTocheckInquiryByOrderId(orderId);
        TOCheckOrder toCheckOrder = new TOCheckOrder();
        toCheckOrder.setOrderNumber(shippingorders.getOrderNumber());
        toCheckOrder.setClassDate(shippingorders.getClassDate());
        toCheckOrder.setOrderId(orderId);
        toCheckOrder.setStatus(status);
        toCheckOrder.setUpdateTime(new Date());
        toCheckOrder.setUpdateBy(updateBy);
        toCheckOrder.setGwczResult(tocheckInquiry.getGwczResult());
        toCheckOrder.setUpdateContent(null == shippingorderExaminfo ? "" : shippingorderExaminfo.getEditRecord());
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(OrderToCheckRabbitmqConfig.ORDER_TOCHECK_DIRECT_EXCHANGE,
                OrderToCheckRabbitmqConfig.ORDER_TOCHECK_ROUTINGKEY,
                toCheckOrder, correlationData);
    }
}
