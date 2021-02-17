package com.szhbl.framework.config.rabbit.inquiry;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

//@Configuration
public class InquiryConfig {
    /*---------------------------------集输、箱型亚欧-----------------------------*/
    public static final String XXYO_SYSTEM__INQUIRY_EXCHANGE = "xxyo.system.inquiry.exchange";


    public static final String XXYO_SYSTEM_CLIENT_ROUTINGKEY = "xxyo.system.topic.inquiry.*";
    //箱型亚欧
    public static final String XXYO_SYSTEM_CLIENT_QUEUE_BLPT = "jsxxyo_system_inquiry_queue_px";


    //询价
    @Bean
    public TopicExchange sysInquiryTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(XXYO_SYSTEM__INQUIRY_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue inquiryXXYOQueueBLPT(){
        return new Queue(XXYO_SYSTEM_CLIENT_QUEUE_BLPT);
    }

    @Bean
    public Binding inquiryXXYOBindingPX(TopicExchange sysInquiryTopicExchange, Queue inquiryXXYOQueueBLPT){
        return BindingBuilder.bind(inquiryXXYOQueueBLPT).to(sysInquiryTopicExchange)
                .with(XXYO_SYSTEM_CLIENT_ROUTINGKEY);
    }









}
