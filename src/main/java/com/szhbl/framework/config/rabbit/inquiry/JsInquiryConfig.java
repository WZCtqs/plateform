package com.szhbl.framework.config.rabbit.inquiry;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsInquiryConfig {

    public static final String JS_SYSTEM__INQUIRY_EXCHANGE = "js.system.inquiry.exchange";
    public static final String JS_SYSTEM_CLIENT_ROUTINGKEY = "js.system.topic.inquiry.*";
    //集输
    public static final String JS_SYSTEM_JS_QUEUE_BLPT = "js_system_inquiry_queue_px";


    //集疏询价
    @Bean
    public TopicExchange jsInquiryTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(JS_SYSTEM__INQUIRY_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue inquiryJSQueueBLPT(){
        return new Queue(JS_SYSTEM_JS_QUEUE_BLPT);
    }

    @Bean
    public Binding inquiryJSBindingPX(TopicExchange jsInquiryTopicExchange, Queue inquiryJSQueueBLPT){
        return BindingBuilder.bind(inquiryJSQueueBLPT).to(jsInquiryTopicExchange)
                .with(JS_SYSTEM_CLIENT_ROUTINGKEY);
    }


    public static final String JS_BUSINESS_QUOTER_EXCHANGE = "business.quoter.topic.exchange";

    public static final String JS_BUSINESS_QUOTER_ROUTINGKEY = "business_quoter_queue_js";
    //集输
    public static final String JS_BUSINESS_QUOTER_BLPT = "business_quoter_queue_js2";

    @Bean
    public TopicExchange jsBusinessQuoterTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(JS_BUSINESS_QUOTER_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue BusinessQuoterQueueBLPT(){
        return new Queue(JS_BUSINESS_QUOTER_BLPT);
    }

    @Bean
    public Binding BusinessQuoterBinding(TopicExchange jsBusinessQuoterTopicExchange, Queue BusinessQuoterQueueBLPT){
        return BindingBuilder.bind(BusinessQuoterQueueBLPT).to(jsBusinessQuoterTopicExchange)
                .with(JS_BUSINESS_QUOTER_ROUTINGKEY);
    }

    // ==============================发送客户通知集疏===============================

    public static final String JS_SEND_QUOTER_EXCHANGE = "send.quoter.topic.exchange";

    public static final String JS_SEND_QUOTER_ROUTINGKEY = "send_quoter_key_js";
    //集输
    public static final String JS_SEND_QUOTER_BLPT = "send_quoter_queue_js";

    @Bean
    public TopicExchange jsSendQuoterTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(JS_SEND_QUOTER_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue sendQuoterQueueBLPT(){
        return new Queue(JS_SEND_QUOTER_BLPT);
    }

    @Bean
    public Binding SendQuoterBinding(TopicExchange jsSendQuoterTopicExchange, Queue sendQuoterQueueBLPT){
        return BindingBuilder.bind(sendQuoterQueueBLPT).to(jsSendQuoterTopicExchange)
                .with(JS_SEND_QUOTER_ROUTINGKEY);
    }
}
