package com.szhbl.project.enquiry.listenner;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.handler.common.QuotationProcessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Component
public class InquiryListenner {

    @Autowired
    private QuotationProcessService quotationProcessService;

    //    @RabbitListener(queues = "business_quoter_queue_js2")
    @Transactional
    public void orderInfoListener(Channel channel, Message message){
        try {
            log.debug("接收集疏报价消息队列：{}",message);
            String body = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            log.debug("接收集疏报价消息内容：{}",body);
            BookingInquiryResult bookingInquiryResult = JSONObject.parseObject(message.getBody(), BookingInquiryResult.class);
            if (quotationProcessService.handlerJs(bookingInquiryResult)) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        }catch (IOException e) {
            log.error("确认消息失败，{}",e.getMessage());
        }
    }

}
