package com.szhbl.framework.config.rabbit.track;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author HP
 */
@Configuration
public class GWRabbitmqConfig {

    /*---------------------------------关务系统运踪信息------------------------------------*/
    @Bean
    public TopicExchange gwTrackTopicExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(TrackRabbitmq.GW_TRACK_TOPIC_EXCHANGE).durable(true).build();
    }

    /**
     * 关务系统运踪消息队列
     *
     * @return
     */
    @Bean
    public Queue gwTrackQueueBLPT() {
        return new Queue(TrackRabbitmq.GW_TRACK_QUEUE_BLPT);
    }

    @Bean
    public Binding gwTrackBindingBLPT(TopicExchange gwTrackTopicExchange, Queue gwTrackQueueBLPT) {
        return BindingBuilder.bind(gwTrackQueueBLPT).to(gwTrackTopicExchange).with(TrackRabbitmq.GW_TRACK_ROUTINGKEY);
    }

    @Bean
    public Queue gwTrackQueuePX() {
        return new Queue(TrackRabbitmq.GW_TRACK_QUEUE_PX);
    }

//    @Bean
//    public Binding gwTrackBindingPX(TopicExchange gwTrackTopicExchange, Queue gwTrackQueuePX) {
//        return BindingBuilder.bind(gwTrackQueuePX).to(gwTrackTopicExchange).with(TrackRabbitmq.GW_TRACK_ROUTINGKEY);
//    }
    /*---------------------------------关务系统运踪信息------------------------------------*/


}
