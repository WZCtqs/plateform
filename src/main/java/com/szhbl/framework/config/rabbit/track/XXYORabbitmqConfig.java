package com.szhbl.framework.config.rabbit.track;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author HP
 */
@Configuration
public class XXYORabbitmqConfig {

    /*---------------------------------箱行亚欧系统公路运输信息------------------------------------*/
    @Bean
    public TopicExchange xxyoTrackTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(TrackRabbitmq.XXYO_TRACK_TOPIC_EXCHANGE).durable(true).build();
    }
    /**
     * 箱行亚欧公路运输消息队列
     * @return
     */
    @Bean
    public Queue xxyoTrackQueueBLPT(){
        return new Queue(TrackRabbitmq.XXYO_TRACK_QUEUE_BLPT);
    }
    @Bean
    public Binding xxyoTrackBindingBLPT(TopicExchange xxyoTrackTopicExchange, Queue xxyoTrackQueueBLPT){
        return BindingBuilder.bind(xxyoTrackQueueBLPT).to(xxyoTrackTopicExchange).with(TrackRabbitmq.XXYO_TRACK_ROUTINGKEY);
    }
    /*---------------------------------箱行亚欧系统公路运输信息------------------------------------*/


}
