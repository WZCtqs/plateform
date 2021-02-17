package com.szhbl.project.enquiry.listenner;

import com.rabbitmq.client.Channel;
import com.szhbl.project.inquiry.service.IBookingInquiryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class GnczListenner {

    @Autowired
    private IBookingInquiryService iBookingInquiryService;

    //@RabbitListener(queues = "PxCalculateFree")
    public void orderInfoListener(Channel channel, Message message){
        try {
            System.out.println("测试国内场站询价消息队列");
            System.out.println("1111"+message);
            String body = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            Map map = message.getMessageProperties().getHeaders();
            System.out.println("国内场站接收消息"+body);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //接收箱型亚欧数据，更新
           /* BookingInquiry bookingInquiry = FastJsonUtils.json2Bean(body, BookingInquiry.class);
            System.out.println("---==="+bookingInquiry);

           */
           /* bookingInquiry.setInquiryState("2");
            iBookingInquiryService.updateBookingInquiry(bookingInquiry);*/
        }catch (IOException e) {
            log.error("测试国内场站询价消息队列：{}",e.toString(),e.getStackTrace());
        }
    }
}
