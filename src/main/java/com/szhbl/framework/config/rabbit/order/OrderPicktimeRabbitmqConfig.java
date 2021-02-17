package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderPicktimeRabbitmqConfig {
    /*---------------------------------托书提货时间消息队列------------------------------*/

    public static final String PLATFROM_PICKTIME_TOPIC_EXCHANGE = "platform.picktime.topic.exchange";

    // 托书提货时间
    public static final String PLATFROM_PICKTIME_QUEUE_BLPT = "platform_picktime_queue_jsgl";
    public static final String PLATFROM_PICKTIME_QUEUE_BLPT2 = "platform_picktime_queue_jsgl_test";
    public static final String PLATFROM_PICKTIME_ROUTINGKEY = "platform.picktime.status.jsgl";

    @Bean
    public TopicExchange picktimeTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(PLATFROM_PICKTIME_TOPIC_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue picktimeQueueBLPT(){
        return new Queue(PLATFROM_PICKTIME_QUEUE_BLPT);
    }
    @Bean
    public Binding picktimeBindingDKA(TopicExchange picktimeTopicExchange, Queue picktimeQueueBLPT){
        return BindingBuilder.bind(picktimeQueueBLPT).to(picktimeTopicExchange)
                .with(PLATFROM_PICKTIME_ROUTINGKEY);
    }
}
