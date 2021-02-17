package com.szhbl.framework.config.rabbit.track;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author HP
 */
@Configuration
public class JSRabbitmqConfig {

    /*---------------------------------集疏系统运踪信息------------------------------------*/
    @Bean
    public TopicExchange jsTrackTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(TrackRabbitmq.JS_TRACK_TOPIC_EXCHANGE).durable(true).build();
    }
    /**
     * 集疏系统运踪消息队列
     * @return
     */
    @Bean
    public Queue jsTrackQueueBLPT(){
        return new Queue(TrackRabbitmq.JS_TRACK_QUEUE_BLPT);
    }
    @Bean
    public Binding jsTrackBindingBLPT(TopicExchange jsTrackTopicExchange, Queue jsTrackQueueBLPT){
        return BindingBuilder.bind(jsTrackQueueBLPT).to(jsTrackTopicExchange).with(TrackRabbitmq.JS_TRACK_ROUTINGKEY);
    }
    /*---------------------------------集疏系统运踪信息------------------------------------*/


}
