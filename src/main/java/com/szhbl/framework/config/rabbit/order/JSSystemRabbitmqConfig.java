package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HP
 */
@Configuration
public class JSSystemRabbitmqConfig {
    /*---------------------------------集输系统消息队列------------------------------*/

    public static final String JS_SYSTEM_FILES_TOPIC_EXCHANGE = "js.system.files.topic.exchange";

    // 签收单
    public static final String JS_SYSTEM_RECEIPTGOODS_ROUTINGKEY = "js.system.receiptGoods.*";
    // 签收单
    public static final String JS_SYSTEM_RECEIPTGOODS_QUEUE_BLPT = "js_system_receiptGoods_queue_blpt";

    public static final String JS_SYSTEM_RECEIPTGOODS_QUEUE_GW = "js_system_receiptGoods_queue_gw";

    @Bean
    public TopicExchange jsFileTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(JS_SYSTEM_FILES_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue jsReceiptGoodsQueueBLPT(){
        return new Queue(JS_SYSTEM_RECEIPTGOODS_QUEUE_BLPT);
    }
    
    @Bean
    public Queue jsReceiptGoodsQueueGW(){
        return new Queue(JS_SYSTEM_RECEIPTGOODS_QUEUE_GW);
    }

    @Bean
    public Binding jsReceiptGoodsBinding(TopicExchange jsFileTopicExchange, Queue jsReceiptGoodsQueueBLPT){
        return BindingBuilder.bind(jsReceiptGoodsQueueBLPT).to(jsFileTopicExchange)
                .with(JS_SYSTEM_RECEIPTGOODS_ROUTINGKEY);
    }
    @Bean
    public Binding jsReceiptGoodsBindingGW(TopicExchange jsFileTopicExchange, Queue jsReceiptGoodsQueueGW){
        return BindingBuilder.bind(jsReceiptGoodsQueueGW).to(jsFileTopicExchange)
                .with(JS_SYSTEM_RECEIPTGOODS_ROUTINGKEY);
    }
    /*---------------------------------集输系统消息队列------------------------------*/
}
