package com.szhbl.framework.config.rabbit.delay;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayRabbitmqConfig {
    /*--------------------------------延迟消息队列----------------------------------*/
    /**
     * TTL 消息交换机配置
     * @return
     */
    @Bean
    public DirectExchange messageTTLDirectExchange(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(DelayContent.ORDER_TTL_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * TTL 消息队列配置
     * durable 为持久队列创建构建器
     * withArgument 最终队列将包含用于声明队列的参数。
     * build 建立最终队列。
     * @return
     */
    @Bean
    public Queue messageTTLQueue(){
        return QueueBuilder.durable(DelayContent.ORDER_TTL_QUEUE)
                .withArgument("x-dead-letter-exchange", DelayContent.ORDER_TTL_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DelayContent.ORDER_DLX_ROUTINGKEY)
                .build();
    }

    /**
     * 消息二次转发的队列配置
     * @return
     */
    @Bean
    public Queue messageDLXQueue(){
        return new Queue(DelayContent.ORDER_DLX_QUEUE);
    }

    /**
     * 延时交换机与延时消息队列的绑定 -routingkey
     * @param messageTTLDirectExchange
     * @param messageTTLQueue
     * @return
     */
    @Bean
    public Binding messageTTLBinding(DirectExchange messageTTLDirectExchange, Queue messageTTLQueue){
        return BindingBuilder
                .bind(messageTTLQueue)
                .to(messageTTLDirectExchange)
                .with(DelayContent.ORDER_TTL_ROUTINGKEY);
    }

    /**
     * 延时交换机与二次转发消队列的绑定 - routingkey
     * @param messageTTLDirectExchange
     * @param messageDLXQueue
     * @return
     */
    @Bean
    public Binding messageDLXBinding(DirectExchange messageTTLDirectExchange, Queue messageDLXQueue){
        return BindingBuilder
                .bind(messageDLXQueue)
                .to(messageTTLDirectExchange)
                .with(DelayContent.ORDER_DLX_ROUTINGKEY);
    }
}
