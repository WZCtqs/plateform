package com.szhbl.framework.config.rabbit.track;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author HP
 */
@Configuration
public class PXCZRabbitmqConfig {

    /*---------------------------------拼箱场站运踪信息------------------------------------*/
    @Bean
    public TopicExchange pxczTrackTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(TrackRabbitmq.PXCZ_TRACK_TOPIC_EXCHANGE).durable(true).build();
    }
    /**
     * 拼箱场站运踪消息队列
     * @return
     */
    @Bean
    public Queue pxczTrackQueueBLPT(){
        return new Queue(TrackRabbitmq.PXCZ_TRACK_QUEUE_BLPT);
    }
    @Bean
    public Binding pxczTrackBindingBLPT(TopicExchange pxczTrackTopicExchange, Queue pxczTrackQueueBLPT){
        return BindingBuilder.bind(pxczTrackQueueBLPT).to(pxczTrackTopicExchange).with(TrackRabbitmq.PXCZ_TRACK_ROUTINGKEY);
    }
    /*---------------------------------拼箱场站运踪信息------------------------------------*/


}
