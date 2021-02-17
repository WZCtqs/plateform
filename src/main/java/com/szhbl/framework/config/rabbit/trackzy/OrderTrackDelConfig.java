package com.szhbl.framework.config.rabbit.trackzy;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderTrackDelConfig {
    /*---------------------------------发送各模块记录删除的消息------------------------------*/
    public static final String PT_ORDER_TRACK_DELETE_EXCHANGE = "pt.order.delete.exchange";

    // 排舱信息删除队列
    public static final String GW_PT_TRACK_DELETE_BLPT = "gw_pt_track_delete_queue"; //关务队列
    public static final String DKA_PT_TRACK_DELETE_BLPT = "dka_pt_track_delete_queue"; //大口岸队列
    public static final String DC_PT_TRACK_DELETE_BLPT = "dc_pt_track_delete_queue"; //订舱队列
    //排舱删除路由key
    public static final String PT_TRACK_DELETE_ROUTINGKEY = "pt.track.delete.pc";

    @Bean
    public TopicExchange ptOrderTrackDeleteExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(PT_ORDER_TRACK_DELETE_EXCHANGE).durable(true).build();
    }

    //排舱信息
    @Bean
    public Queue gwTrackDeleteQueue() {
        return new Queue(GW_PT_TRACK_DELETE_BLPT);
    }
    @Bean
    public Binding gwTrackDeleteBinding(TopicExchange ptOrderTrackDeleteExchange, Queue gwTrackDeleteQueue) {
        return BindingBuilder.bind(gwTrackDeleteQueue).to(ptOrderTrackDeleteExchange)
                .with(PT_TRACK_DELETE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaTrackDeleteQueue() {
        return new Queue(DKA_PT_TRACK_DELETE_BLPT);
    }
    @Bean
    public Binding dkaTrackDeleteBinding(TopicExchange ptOrderTrackDeleteExchange, Queue dkaTrackDeleteQueue) {
        return BindingBuilder.bind(dkaTrackDeleteQueue).to(ptOrderTrackDeleteExchange)
                .with(PT_TRACK_DELETE_ROUTINGKEY);
    }

    @Bean
    public Queue dcTrackDeleteQueue() {
        return new Queue(DC_PT_TRACK_DELETE_BLPT);
    }
    @Bean
    public Binding dcTrackDeleteBinding(TopicExchange ptOrderTrackDeleteExchange, Queue dcTrackDeleteQueue) {
        return BindingBuilder.bind(dcTrackDeleteQueue).to(ptOrderTrackDeleteExchange)
                .with(PT_TRACK_DELETE_ROUTINGKEY);
    }

}
