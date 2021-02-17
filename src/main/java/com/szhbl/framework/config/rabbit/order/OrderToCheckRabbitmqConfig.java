package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description : 转待审核发送子系统通知消息队列配置
 * @Author : wangzhichao
 * @Date: 2020-07-18 10:26
 */
@Configuration
public class OrderToCheckRabbitmqConfig {

    public static final String ORDER_TOCHECK_DIRECT_EXCHANGE = "order.tocheck.direct.exchange";

    public static final String ORDER_TOCHECK_ROUTINGKEY = "order.tocheck.notice";

    public static final String ORDER_TOCHECK_QUEUE_JS = "order_tocheck_queue_js";
    public static final String ORDER_TOCHECK_QUEUE_XXYO = "order_tocheck_queue_xxyo";
    public static final String ORDER_TOCHECK_QUEUE_PX = "order_tocheck_queue_px";
    public static final String ORDER_TOCHECK_QUEUE_XG = "order_tocheck_queue_xg";
    public static final String ORDER_TOCHECK_QUEUE_GWCZ = "order_tocheck_queue_gwcz";
    public static final String ORDER_TOCHECK_QUEUE_DKA = "order_tocheck_queue_dka";

    @Bean
    public DirectExchange directTocheckExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(ORDER_TOCHECK_DIRECT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue jsTocheckQueue() {
        return new Queue(ORDER_TOCHECK_QUEUE_JS);
    }

//    @Bean
//    public Binding jsTocheckBinding(DirectExchange directTocheckExchange, Queue jsTocheckQueue) {
//        return BindingBuilder.bind(jsTocheckQueue).to(directTocheckExchange)
//                .with(ORDER_TOCHECK_ROUTINGKEY);
//    }

    @Bean
    public Queue xxyoTocheckQueue() {
        return new Queue(ORDER_TOCHECK_QUEUE_XXYO);
    }

//    @Bean
//    public Binding xxyoTocheckBinding(DirectExchange directTocheckExchange, Queue xxyoTocheckQueue) {
//        return BindingBuilder.bind(xxyoTocheckQueue).to(directTocheckExchange)
//                .with(ORDER_TOCHECK_ROUTINGKEY);
//    }

    @Bean
    public Queue pxTocheckQueue() {
        return new Queue(ORDER_TOCHECK_QUEUE_PX);
    }

//    @Bean
//    public Binding pxTocheckBinding(DirectExchange directTocheckExchange, Queue pxTocheckQueue) {
//        return BindingBuilder.bind(pxTocheckQueue).to(directTocheckExchange)
//                .with(ORDER_TOCHECK_ROUTINGKEY);
//    }

    @Bean
    public Queue xgTocheckQueue() {
        return new Queue(ORDER_TOCHECK_QUEUE_XG);
    }

    @Bean
    public Binding xgTocheckBinding(DirectExchange directTocheckExchange, Queue xgTocheckQueue) {
        return BindingBuilder.bind(xgTocheckQueue).to(directTocheckExchange)
                .with(ORDER_TOCHECK_ROUTINGKEY);
    }

    @Bean
    public Queue gwczTocheckQueue() {
        return new Queue(ORDER_TOCHECK_QUEUE_GWCZ);
    }

    @Bean
    public Binding gwczTocheckBinding(DirectExchange directTocheckExchange, Queue gwczTocheckQueue) {
        return BindingBuilder.bind(gwczTocheckQueue).to(directTocheckExchange)
                .with(ORDER_TOCHECK_ROUTINGKEY);
    }

    @Bean
    public Queue dkaTocheckQueue() {
        return new Queue(ORDER_TOCHECK_QUEUE_DKA);
    }

//    @Bean
//    public Binding dkaTocheckBinding(DirectExchange directTocheckExchange, Queue dkaTocheckQueue) {
//        return BindingBuilder.bind(dkaTocheckQueue).to(directTocheckExchange)
//                .with(ORDER_TOCHECK_ROUTINGKEY);
//    }
}
