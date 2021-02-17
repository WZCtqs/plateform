package com.szhbl.framework.config.rabbit.trackzy;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlatformTrackRabbitmqConfig {
    //发送排舱信息给子系统（发运时间、列数、班列号、中亚运单）
    public static final String PLATFORM_GOODSSTATUS_TOPIC_EXCHANGE = "platform.goodsstatus.topic.exchange";

    public static final String PLATFORM_GOODSSTATUS_ROUTINGKEY = "platform.goods.status"; //发运时间 班列号
    public static final String PLATFORM_ZYINFO_ROUTINGKEY = "platform.goods.status.zyinfo"; //中亚运单

    public static final String PLATFORM_GOODSSTATUS_QUEUE_DKA = "platform_goodsstatus_queue_dka";  //大口岸发运时间班列号
    public static final String PLATFORM_GOODSSTATUS_QUEUE_GW = "platform_goodsstatus_queue_gw"; //关务发运时间班列号
    public static final String PLATFORM_GOODSSTATUS_QUEUE_XG = "platform_goodsstatus_queue_xg"; //箱管发运时间班列号
    public static final String PLATFORM_GOODSSTATUS_QUEUE_PX = "platform_goodsstatus_queue_px"; //箱行亚欧发运时间班列号
    public static final String PLATFORM_GOODSSTATUS_QUEUE_DCJS = "platform_goodsstatus_queue_dcjs"; //结算系统发运时间班列号

    public static final String PLATFORM_ZYINFO_QUEUE_DKA = "platform_zyinfo_queue_dka";  //大口岸中亚运单
    public static final String PLATFORM_ZYINFO_QUEUE_GW = "platform_zyinfo_queue_gw";  //关务中亚运单

    @Bean
    public DirectExchange platformGoodsTopicExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(PLATFORM_GOODSSTATUS_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue dkaToPlatformQueue() {
        return new Queue(PLATFORM_GOODSSTATUS_QUEUE_DKA);
    }
    @Bean
    public Binding dkaToPlatformBinding(DirectExchange platformGoodsTopicExchange, Queue dkaToPlatformQueue) {
        return BindingBuilder.bind(dkaToPlatformQueue).to(platformGoodsTopicExchange)
                .with(PLATFORM_GOODSSTATUS_ROUTINGKEY);
    }

    @Bean
    public Queue gwToPlatformQueue() {
        return new Queue(PLATFORM_GOODSSTATUS_QUEUE_GW);
    }
    @Bean
    public Binding gwToPlatformBinding(DirectExchange platformGoodsTopicExchange, Queue gwToPlatformQueue) {
        return BindingBuilder.bind(gwToPlatformQueue).to(platformGoodsTopicExchange)
                .with(PLATFORM_GOODSSTATUS_ROUTINGKEY);
    }

    @Bean
    public Queue xgToPlatformQueue() {
        return new Queue(PLATFORM_GOODSSTATUS_QUEUE_XG);
    }
    @Bean
    public Binding xgToPlatformBinding(DirectExchange platformGoodsTopicExchange, Queue xgToPlatformQueue) {
        return BindingBuilder.bind(xgToPlatformQueue).to(platformGoodsTopicExchange)
                .with(PLATFORM_GOODSSTATUS_ROUTINGKEY);
    }

    @Bean
    public Queue pxToPlatformQueue() {
        return new Queue(PLATFORM_GOODSSTATUS_QUEUE_PX);
    }
    @Bean
    public Binding pxToPlatformBinding(DirectExchange platformGoodsTopicExchange, Queue pxToPlatformQueue) {
        return BindingBuilder.bind(pxToPlatformQueue).to(platformGoodsTopicExchange)
                .with(PLATFORM_GOODSSTATUS_ROUTINGKEY);
    }

    @Bean
    public Queue dcjsToPlatformQueue() {
        return new Queue(PLATFORM_GOODSSTATUS_QUEUE_DCJS);
    }
    @Bean
    public Binding dcjsToPlatformBinding(DirectExchange platformGoodsTopicExchange, Queue dcjsToPlatformQueue) {
        return BindingBuilder.bind(dcjsToPlatformQueue).to(platformGoodsTopicExchange)
                .with(PLATFORM_GOODSSTATUS_ROUTINGKEY);
    }

    @Bean
    public Queue dkaZyToPlatformQueue() {
        return new Queue(PLATFORM_ZYINFO_QUEUE_DKA);
    }
    @Bean
    public Binding dkaZyToPlatformBinding(DirectExchange platformGoodsTopicExchange, Queue dkaZyToPlatformQueue) {
        return BindingBuilder.bind(dkaZyToPlatformQueue).to(platformGoodsTopicExchange)
                .with(PLATFORM_ZYINFO_ROUTINGKEY);
    }

    @Bean
    public Queue gwZyToPlatformQueue() {
        return new Queue(PLATFORM_ZYINFO_QUEUE_GW);
    }
    @Bean
    public Binding gwZyToPlatformBinding(DirectExchange platformGoodsTopicExchange, Queue gwZyToPlatformQueue) {
        return BindingBuilder.bind(gwZyToPlatformQueue).to(platformGoodsTopicExchange)
                .with(PLATFORM_ZYINFO_ROUTINGKEY);
    }
}
