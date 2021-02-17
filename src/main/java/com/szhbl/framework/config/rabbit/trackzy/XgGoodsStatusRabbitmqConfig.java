package com.szhbl.framework.config.rabbit.trackzy;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XgGoodsStatusRabbitmqConfig {
    /*---------------------------------箱管箱况信息消息队列------------------------------*/

    public static final String XG_GOODS_STATUS_TOPIC_EXCHANGE = "xg.goods.status.topic.exchange";

    // 箱况、是否维修、是否更换铭牌、箱管部审核状态、是否发送邮件、箱损是否上传
    public static final String XG_GOODS_STATUS_QUEUE_BLPT = "xg_goods_status_queue_xgxt";
    public static final String XG_GOODS_STATUS_QUEUE_DKA = "xg_goods_status_queue_dka";
    public static final String XG_GOODS_STATUS_QUEUE_GW = "xg_goods_status_queue_gw";
    public static final String XG_GOODS_STATUS_ROUTINGKEY = "xg.goods.status.xk";

    //箱况、是否维修、是否更换铭牌、箱管部审核状态、是否发送邮件、箱损是否上传
    @Bean
    public TopicExchange xgGoodsTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(XG_GOODS_STATUS_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue xgGoodsStatusQueueBLPT() {
        return new Queue(XG_GOODS_STATUS_QUEUE_BLPT);
    }
    @Bean
    public Binding xgGoodsStatusBindingDKA(TopicExchange xgGoodsTopicExchange, Queue xgGoodsStatusQueueBLPT) {
        return BindingBuilder.bind(xgGoodsStatusQueueBLPT).to(xgGoodsTopicExchange)
                .with(XG_GOODS_STATUS_ROUTINGKEY);
    }

    @Bean
    public Queue xgGoodsStatusQueueDKA() {
        return new Queue(XG_GOODS_STATUS_QUEUE_DKA);
    }
    @Bean
    public Binding xgGoodsStatusBindingDKAXT(TopicExchange xgGoodsTopicExchange, Queue xgGoodsStatusQueueDKA) {
        return BindingBuilder.bind(xgGoodsStatusQueueDKA).to(xgGoodsTopicExchange)
                .with(XG_GOODS_STATUS_ROUTINGKEY);
    }

    @Bean
    public Queue xgGoodsStatusQueueGW() {
        return new Queue(XG_GOODS_STATUS_QUEUE_GW);
    }
    @Bean
    public Binding xgGoodsStatusBindingGW(TopicExchange xgGoodsTopicExchange, Queue xgGoodsStatusQueueGW) {
        return BindingBuilder.bind(xgGoodsStatusQueueGW).to(xgGoodsTopicExchange)
                .with(XG_GOODS_STATUS_ROUTINGKEY);
    }
}
