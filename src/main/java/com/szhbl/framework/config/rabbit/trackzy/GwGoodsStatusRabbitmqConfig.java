package com.szhbl.framework.config.rabbit.trackzy;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GwGoodsStatusRabbitmqConfig {
    /*---------------------------------关务信息消息队列------------------------------*/

    public static final String Gw_GOODS_STATUS_TOPIC_EXCHANGE = "gw.goods.status.topic.exchange";

    // 报关单证、报关状态、运单是否上传、中文收货名字，跟单审核状态
    public static final String Gw_GOODS_STATUS_QUEUE_BLPT = "gw_goods_status_queue_gwxt";
//    public static final String Gw_GOODS_STATUS_QUEUE_DKA = "gw_goods_status_queue_dka";
    public static final String Gw_GOODS_STATUS_QUEUE_XXYO = "gw_goods_status_queue_xxyo";
    public static final String Gw_GOODS_STATUS_ROUTINGKEY = "gw.goods.status.gw";
    public static final String Gw_GOODS_STATUS_NEW_ROUTINGKEY = "gw.goods.status_new.gw";

    //箱况、是否维修、是否更换铭牌、箱管部审核状态、是否发送邮件、箱损是否上传
    @Bean
    public TopicExchange gwGoodsTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(Gw_GOODS_STATUS_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue gwGoodsQueueBLPT() {
        return new Queue(Gw_GOODS_STATUS_QUEUE_BLPT);
    }

    @Bean
    public Binding gwGoodsBindingDKA(TopicExchange gwGoodsTopicExchange, Queue gwGoodsQueueBLPT) {
        return BindingBuilder.bind(gwGoodsQueueBLPT).to(gwGoodsTopicExchange)
                .with(Gw_GOODS_STATUS_NEW_ROUTINGKEY);
    }

//    @Bean
//    public Queue gwGoodsQueueDKA() {
//        return new Queue(Gw_GOODS_STATUS_QUEUE_DKA);
//    }

//    @Bean
//    public Binding gwGoodsBindingDKAxt(TopicExchange gwGoodsTopicExchange, Queue gwGoodsQueueDKA) {
//        return BindingBuilder.bind(gwGoodsQueueDKA).to(gwGoodsTopicExchange)
//                .with(Gw_GOODS_STATUS_ROUTINGKEY);
//    }

    @Bean
    public Queue gwGoodsQueueXXYO() {
        return new Queue(Gw_GOODS_STATUS_QUEUE_XXYO);
    }

    @Bean
    public Binding gwGoodsBindingXXYO(TopicExchange gwGoodsTopicExchange, Queue gwGoodsQueueXXYO) {
        return BindingBuilder.bind(gwGoodsQueueXXYO).to(gwGoodsTopicExchange)
                .with(Gw_GOODS_STATUS_ROUTINGKEY);
    }
}
