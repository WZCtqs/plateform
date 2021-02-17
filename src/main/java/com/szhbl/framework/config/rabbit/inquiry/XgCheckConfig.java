package com.szhbl.framework.config.rabbit.inquiry;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XgCheckConfig {

    public static final String XG_SYSTEM_CHECK_EXCHANGE = "xg.system.check.exchange";
    public static final String XG_SYSTEM_CHECK_ROUTINGKEY = "xg.system.topic.check.*";
    //箱管特种箱审核
    public static final String XG_SYSTEM_CHECK_QUEUE_BLPT = "xg_system_check_queue_blpt";


    //箱管特种箱审核
    @Bean
    public TopicExchange xgInquiryTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(XG_SYSTEM_CHECK_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue inquiryXgQueueBLPT(){
        return new Queue(XG_SYSTEM_CHECK_QUEUE_BLPT);
    }

    @Bean
    public Binding inquiryXgBindingPX(TopicExchange xgInquiryTopicExchange, Queue inquiryXgQueueBLPT){
        return BindingBuilder.bind(inquiryXgQueueBLPT).to(xgInquiryTopicExchange)
                .with(XG_SYSTEM_CHECK_ROUTINGKEY);
    }
}
