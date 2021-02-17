package com.szhbl.framework.config.rabbit.client;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerRabbitConfig {
    /*---------------------------------客户、用户信息-----------------------------*/
    public static final String DC_SYSTEM__CLIENT_EXCHANGE = "dc.system.user.exchange";

    //客户信息
    public static final String DC_SYSTEM_CLIENT_ROUTINGKEY = "dc.system.topic.client.*";
    public static final String DC_SYSTEM_CLIENT_QUEUE_BLPT = "dka_system_client_queue_px"; //订舱系统
    public static final String DZ_SYSTEM_CLIENT_QUEUE_BLPT = "dz_system_client_queue_dz"; //单证系统



    //用户信息
    public static final String DC_SYSTEM_SYSUSER_ROUTINGKEY = "dc.system.topic.sysuser.*";
    public static final String DC_SYSTEM_SYSUSER_QUEUE_BLPT = "dc_system_sysuser_queue_px";



   //客户
    @Bean
    public TopicExchange sysClientTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(DC_SYSTEM__CLIENT_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue clientQueueBLPT(){
        return new Queue(DC_SYSTEM_CLIENT_QUEUE_BLPT);
    }
    @Bean
    public Binding clientBindingPX(TopicExchange sysClientTopicExchange, Queue clientQueueBLPT){
        return BindingBuilder.bind(clientQueueBLPT).to(sysClientTopicExchange)
                .with(DC_SYSTEM_CLIENT_ROUTINGKEY);
    }

    @Bean
    public Queue clientQueueBLPTDz(){
        return new Queue(DZ_SYSTEM_CLIENT_QUEUE_BLPT);
    }
    @Bean
    public Binding clientBindingDZ(TopicExchange sysClientTopicExchange, Queue clientQueueBLPTDz){
        return BindingBuilder.bind(clientQueueBLPTDz).to(sysClientTopicExchange)
                .with(DC_SYSTEM_CLIENT_ROUTINGKEY);
    }

    //平台用户
    @Bean
    public Queue sysUserQueueBLPT(){
        return new Queue(DC_SYSTEM_SYSUSER_QUEUE_BLPT);
    }

    @Bean
    public Binding sysUserBindingPX(TopicExchange clientTopicExchange, Queue sysUserQueueBLPT){
        return BindingBuilder.bind(sysUserQueueBLPT).to(clientTopicExchange)
                .with(DC_SYSTEM_SYSUSER_ROUTINGKEY);
    }

    /** 关务系统发送跟单员信息*/
    public static final String GW_TOPIC_GENDAN_EXCHANGE = "gw.topic.gendan.exchange";
    public static final String GW_TOPIC_CLIENT_ROUTINGKEY = "gw.topic.client.gendan";
    public static final String GW_CLIENT_GENDAN_QUEUE_BLPT = "gw_client_gendan_queue_blpt";
    @Bean
    public TopicExchange gwClientTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(GW_TOPIC_GENDAN_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue gwClientQueueBLPT(){
        return new Queue(GW_CLIENT_GENDAN_QUEUE_BLPT);
    }

    @Bean
    public Binding gwClientBindingBLPT(TopicExchange gwClientTopicExchange, Queue gwClientQueueBLPT){
        return BindingBuilder.bind(gwClientQueueBLPT).to(gwClientTopicExchange)
                .with(GW_TOPIC_CLIENT_ROUTINGKEY);
    }


}
