package com.szhbl.framework.config.rabbit.trackzy;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DkaGoodsStatusRabbitmqConfig {
    /*---------------------------------监听大口岸排舱信息------------------------------*/

    public static final String DKA_GOODS_STATUS_TOPIC_EXCHANGE = "dka.goods.status.topic.exchange";

    // 排舱信息
    public static final String DKA_GOODS_STATUS_QUEUE_BLPT = "dka_goods_status_queue_platform"; //平台队列
    public static final String DKA_GOODS_STATUS_ROUTINGKEY = "dka.goods.status.pc";


    // 中亚运单
    public static final String DKA_ZY_ORDEREXAMINE_QUEUE_BLPT = "dka_zy_orderexamine_queue_blpt"; //运单审核结果平台队列
    public static final String DKA_ZY_ORDEREXAMINE_ROUTINGKEY = "dka.zy.orderexamine";

    //排舱信息
    @Bean
    public TopicExchange dkaGoodsStatusTopicExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(DKA_GOODS_STATUS_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue dkaGoodsQueueBLPT() {
        return new Queue(DKA_GOODS_STATUS_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaGoodsBindingDKA(TopicExchange dkaGoodsStatusTopicExchange, Queue dkaGoodsQueueBLPT) {
        return BindingBuilder.bind(dkaGoodsQueueBLPT).to(dkaGoodsStatusTopicExchange)
                .with(DKA_GOODS_STATUS_ROUTINGKEY);
    }

    @Bean
    public Queue dkaZyOrderexamineQueueBLPT() {
        return new Queue(DKA_ZY_ORDEREXAMINE_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaZyOrderexamineBindingDKA(TopicExchange dkaGoodsStatusTopicExchange, Queue dkaZyOrderexamineQueueBLPT) {
        return BindingBuilder.bind(dkaZyOrderexamineQueueBLPT).to(dkaGoodsStatusTopicExchange)
                .with(DKA_ZY_ORDEREXAMINE_ROUTINGKEY);
    }
}
