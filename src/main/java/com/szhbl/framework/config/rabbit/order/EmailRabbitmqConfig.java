package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailRabbitmqConfig {
    /**
     * 邮件发送消息队列
     */
    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String EMAIL_ROUTINGKEY = "email.send";
    public static final String EMAIL_QUEUE = "email_queue";


    public static final String EMAIL_LATTERS_ROUTINGKEY = "email.latters.send";
    public static final String EMAIL_LATTERS_QUEUE = "email_latters_queue";

    public static final String EMAIL_LATTERS_ROUTINGKEY_GD = "email.latters.gd";
    public static final String EMAIL_LATTERS_QUEUE_GD = "email_latters_queue_gd";

    @Bean
    public DirectExchange emailExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(EMAIL_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE).build();
    }

    @Bean
    public Binding emailBinding(DirectExchange emailExchange, Queue emailQueue) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(EMAIL_ROUTINGKEY);
    }

    @Bean
    public Queue emailLattersQueue() {
        return QueueBuilder.durable(EMAIL_LATTERS_QUEUE).build();
    }

    @Bean
    public Binding emailLattersBinding(DirectExchange emailExchange, Queue emailLattersQueue) {
        return BindingBuilder.bind(emailLattersQueue).to(emailExchange).with(EMAIL_LATTERS_ROUTINGKEY);
    }

    @Bean
    public Queue emailLattersQueueGD() {
        return QueueBuilder.durable(EMAIL_LATTERS_QUEUE_GD).build();
    }

    @Bean
    public Binding emailLattersBindingGD(DirectExchange emailExchange, Queue emailLattersQueueGD) {
        return BindingBuilder.bind(emailLattersQueueGD).to(emailExchange).with(EMAIL_LATTERS_ROUTINGKEY_GD);
    }
}
