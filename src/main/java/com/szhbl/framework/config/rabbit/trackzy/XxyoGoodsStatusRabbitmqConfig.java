package com.szhbl.framework.config.rabbit.trackzy;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxyoGoodsStatusRabbitmqConfig {
    /*---------------------------------箱行亚欧托书信息消息队列------------------------------*/

    public static final String XXYO_GOODS_STATUS_TOPIC_EXCHANGE = "xxyo.goods.status.topic.exchange";

    // 是否办好票、公路部审核状态
    public static final String XXYO_GOODS_STATUS_QUEUE_BLPT = "xxyo_goods_status_queue_xgxt";
    public static final String XXYO_GOODS_STATUS_QUEUE_DKA = "xxyo_goods_status_queue_dka";
    public static final String XXYO_GOODS_STATUS_QUEUE_GW = "xxyo_goods_status_queue_gw";
    public static final String XXYO_GOODS_STATUS_QUEUE_GWCZ = "xxyo_goods_status_queue_gwcz";
    public static final String XXYO_GOODS_STATUS_ROUTINGKEY = "xxyo.goods.status.xk";

    //是否办好票、公路部审核状态
    @Bean
    public TopicExchange xgGoodsStatusTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(XXYO_GOODS_STATUS_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue xgGoodsQueueBLPT() {
        return new Queue(XXYO_GOODS_STATUS_QUEUE_BLPT);
    }

    @Bean
    public Binding xgGoodsBindingDKA(TopicExchange xgGoodsStatusTopicExchange, Queue xgGoodsQueueBLPT) {
        return BindingBuilder.bind(xgGoodsQueueBLPT).to(xgGoodsStatusTopicExchange)
                .with(XXYO_GOODS_STATUS_ROUTINGKEY);
    }

    @Bean
    public Queue xgGoodsQueueDKAXT() {
        return new Queue(XXYO_GOODS_STATUS_QUEUE_DKA);
    }

    @Bean
    public Binding xgGoodsBindingDKAXT(TopicExchange xgGoodsStatusTopicExchange, Queue xgGoodsQueueDKAXT) {
        return BindingBuilder.bind(xgGoodsQueueDKAXT).to(xgGoodsStatusTopicExchange)
                .with(XXYO_GOODS_STATUS_ROUTINGKEY);
    }

    @Bean
    public Queue xgGoodsQueueGW() {
        return new Queue(XXYO_GOODS_STATUS_QUEUE_GW);
    }

    @Bean
    public Binding xgGoodsBindingGW(TopicExchange xgGoodsStatusTopicExchange, Queue xgGoodsQueueGW) {
        return BindingBuilder.bind(xgGoodsQueueGW).to(xgGoodsStatusTopicExchange)
                .with(XXYO_GOODS_STATUS_ROUTINGKEY);
    }

    @Bean
    public Queue xgGoodsQueueGWCZ() {
        return new Queue(XXYO_GOODS_STATUS_QUEUE_GWCZ);
    }

    @Bean
    public Binding xgGoodsBindingGWCZ(TopicExchange xgGoodsStatusTopicExchange, Queue xgGoodsQueueGWCZ) {
        return BindingBuilder.bind(xgGoodsQueueGWCZ).to(xgGoodsStatusTopicExchange)
                .with(XXYO_GOODS_STATUS_ROUTINGKEY);
    }
}
