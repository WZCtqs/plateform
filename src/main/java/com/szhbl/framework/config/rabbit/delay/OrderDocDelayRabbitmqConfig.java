package com.szhbl.framework.config.rabbit.delay;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderDocDelayRabbitmqConfig {
    /*--------------------------------延迟消息队列----------------------------------*/
    /**
     * TTL 消息交换机配置
     * @return
     */
    @Bean
    public DirectExchange messageDocTTLDirectExchange(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(OrderDocDelayContent.DOC_ORDER_TTL_EXCHANGE)
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
    public Queue messageDocTTLQueue(){
        return QueueBuilder.durable(OrderDocDelayContent.DOC_ORDER_TTL_QUEUE)
                .withArgument("x-dead-letter-exchange", OrderDocDelayContent.DOC_ORDER_TTL_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", OrderDocDelayContent.DOC_ORDER_DLX_ROUTINGKEY)
                .build();
    }

    /**
     * 消息二次转发的队列配置
     * @return
     */
    @Bean
    public Queue messageDocDLXQueue(){
        return new Queue(OrderDocDelayContent.DOC_ORDER_DLX_QUEUE);
    }

    /**
     * 延时交换机与延时消息队列的绑定 -routingkey
     * @param messageDocTTLDirectExchange
     * @param messageDocTTLQueue
     * @return
     */
    @Bean
    public Binding messageDocTTLBinding(DirectExchange messageDocTTLDirectExchange, Queue messageDocTTLQueue){
        return BindingBuilder
                .bind(messageDocTTLQueue)
                .to(messageDocTTLDirectExchange)
                .with(OrderDocDelayContent.DOC_ORDER_TTL_ROUTINGKEY);
    }

    /**
     * 延时交换机与二次转发消队列的绑定 - routingkey
     * @param messageDocTTLDirectExchange
     * @param messageDocDLXQueue
     * @return
     */
    @Bean
    public Binding messageDocDLXBinding(DirectExchange messageDocTTLDirectExchange, Queue messageDocDLXQueue){
        return BindingBuilder
                .bind(messageDocDLXQueue)
                .to(messageDocTTLDirectExchange)
                .with(OrderDocDelayContent.DOC_ORDER_DLX_ROUTINGKEY);
    }
}
