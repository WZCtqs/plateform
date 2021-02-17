package com.szhbl.framework.config.rabbit.inquiry;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GnczInquiryConfig {

    public static final String GNCZ_SYSTEM_INQUIRY_EXCHANGE = "gncz.system.inquiry.exchange";
    public static final String GNCZ_SYSTEM_CLIENT_ROUTINGKEY = "gncz.system.topic.inquiry.*";
    // 国内场站
    public static final String GNCZ_SYSTEM_JS_QUEUE_BLPT = "gncz_system_inquiry_queue_px1";


    //国内场站询价
    @Bean
    public TopicExchange gNCZInquiryTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(GNCZ_SYSTEM_INQUIRY_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue inquiryGNCZQueueBLPT(){
        return new Queue(GNCZ_SYSTEM_JS_QUEUE_BLPT);
    }

    @Bean
    public Binding inquiryGNCZBindingPX(TopicExchange gNCZInquiryTopicExchange, Queue inquiryGNCZQueueBLPT){
        return BindingBuilder.bind(inquiryGNCZQueueBLPT).to(gNCZInquiryTopicExchange)
                .with(GNCZ_SYSTEM_CLIENT_ROUTINGKEY);
    }
}
