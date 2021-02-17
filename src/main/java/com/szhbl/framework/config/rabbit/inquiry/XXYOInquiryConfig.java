package com.szhbl.framework.config.rabbit.inquiry;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XXYOInquiryConfig {
    /*---------------------------------集输、箱型亚欧-----------------------------*/
    public static final String XXYO_SYSTEM__INQUIRY_EXCHANGE = "xxyo.inquiry.exchange";


    public static final String XXYO_SYSTEM_CLIENT_ROUTINGKEY = "xxyo.topic.inquiry.*";
    //箱型亚欧
    public static final String XXYO_SYSTEM_CLIENT_QUEUE_BLPT = "xxyo_inquiry_queue_px";


    //询价
    @Bean
    public TopicExchange xxyoInquiryTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(XXYO_SYSTEM__INQUIRY_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue inquiryXXYOQueueBLPT(){
        return new Queue(XXYO_SYSTEM_CLIENT_QUEUE_BLPT);
    }

    @Bean
    public Binding inquiryXXYOBindingPX(TopicExchange xxyoInquiryTopicExchange, Queue inquiryXXYOQueueBLPT){
        return BindingBuilder.bind(inquiryXXYOQueueBLPT).to(xxyoInquiryTopicExchange)
                .with(XXYO_SYSTEM_CLIENT_ROUTINGKEY);
    }
}
