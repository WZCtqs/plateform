package com.szhbl.framework.config.rabbit.track;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author HP
 */
@Configuration
public class PTDCRabbitmqConfig {

    /*---------------------------------平台订舱运踪信息------------------------------------*/
    @Bean
    public TopicExchange ptdcTrackTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(TrackRabbitmq.PTDC_TRACK_TOPIC_EXCHANGE).durable(true).build();
    }
    /**
     * 平台订舱运踪消息队列
     * @return
     */
    @Bean
    public Queue ptdcTrackQueueBLPT(){
        return new Queue(TrackRabbitmq.PTDC_TRACK_QUEUE_BLPT);
    }
    @Bean
    public Binding ptdcTrackBindingBLPT(TopicExchange ptdcTrackTopicExchange, Queue ptdcTrackQueueBLPT){
        return BindingBuilder.bind(ptdcTrackQueueBLPT).to(ptdcTrackTopicExchange).with(TrackRabbitmq.PTDC_TRACK_ROUTINGKEY);
    }
    /*---------------------------------平台订舱运踪信息------------------------------------*/

}
