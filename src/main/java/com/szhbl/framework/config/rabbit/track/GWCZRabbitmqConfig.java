package com.szhbl.framework.config.rabbit.track;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author HP
 */
@Configuration
public class GWCZRabbitmqConfig {

    /*---------------------------------国外场站运踪信息------------------------------------*/
    @Bean
    public TopicExchange gwczTrackTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(TrackRabbitmq.GWCZ_TRACK_TOPIC_EXCHANGE).durable(true).build();
    }
    /**
     * 国外场站运踪消息队列
     * @return
     */
    @Bean
    public Queue gwczTrackQueueBLPT(){
        return new Queue(TrackRabbitmq.GWCZ_TRACK_QUEUE_BLPT);
    }
    @Bean
    public Binding gwczTrackBindingBLPT(TopicExchange gwczTrackTopicExchange, Queue gwczTrackQueueBLPT){
        return BindingBuilder.bind(gwczTrackQueueBLPT).to(gwczTrackTopicExchange).with(TrackRabbitmq.GWCZ_TRACK_ROUTINGKEY);
    }
    /*---------------------------------国外场站运踪信息------------------------------------*/


}
