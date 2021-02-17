package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HP
 * @date 2020-03-18
 */
@Configuration
public class GWSystemRabbitmqConfig {
    /*---------------------------------关务系统系统消息队列------------------------------*/

    public static final String GW_SYSTEM_FILES_TOPIC_EXCHANGE = "gw.system.files.topic.exchange";

    // 报关单
    public static final String GW_SYSTEM_DECLARE_CUSTOMS_ROUTINGKEY = "gw.system.declareCustoms.*";

    public static final String GW_SYSTEM_DECLARE_CUSTOMS_QUEUE_BLPT = "gw_system_declareCustoms_queue_blpt";

    public static final String GW_SYSTEM_DECLARE_CUSTOMS_QUEUE_DKA = "gw_system_declareCustoms_queue_dka";

    // 放行单
    public static final String GW_SYSTEM_CLEARANCEPAPER_ROUTINGKEY = "gw.system.clearancePaper.*";

    public static final String GW_SYSTEM_CLEARANCEPAPER_QUEUE_BLPT = "gw_system_clearancePaper_queue_blpt";

    public static final String GW_SYSTEM_CLEARANCEPAPER_QUEUE_XXYO = "gw_system_clearancePaper_queue_xxyo";

    // 口岸转关信息表
    public static final String GW_SYSTEM_PORTTRANS_ROUTINGKEY = "gw.system.porttrans.*";

    public static final String GW_SYSTEM_PORTTRANS_QUEUE_BLPT = "gw_system_porttrans_queue_blpt";

    // 托书报关信息
    public static final String GW_SYSTEM_DECLAREMSG_ROUTINGKEY = "gw.system.declareMsg.*";

    public static final String GW_SYSTEM_DECLAREMSG_QUEUE_DKA = "gw_system_declareMsg_queue_dka";

    // 箱号报关信息
    public static final String GW_SYSTEM_CONDECLAREMSG_ROUTINGKEY = "gw.system.conDeclareMsg.*";

    public static final String GW_SYSTEM_CONDECLAREMSG_QUEUE_DKA = "gw_system_conDeclareMsg_queue_dka";

    // 箱号报关信息
    public static final String GW_SYSTEM_FOLLOWVEHICLE_ROUTINGKEY = "gw.system.followVehicle.*";

    public static final String GW_SYSTEM_FOLLOWVEHICLE_QUEUE_DKA = "gw_system_followVehicle_queue_dka";

    //随车问题反馈
    public static final String GW_SYSTEM_FOLLOWVEHICLE_BF_ROUTINGKEY = "gw.system.followVehicleBF";
    public static final String GW_SYSTEM_FOLLOWVEHICLE_BF_QUEUE_DKA = "gw_system_followVehicleBF_queue_dka";

    @Bean
    public TopicExchange gwFileTopicExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(GW_SYSTEM_FILES_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue gwDeclareCustomsQueueBLPT() {
        return new Queue(GW_SYSTEM_DECLARE_CUSTOMS_QUEUE_BLPT);
    }

    @Bean
    public Binding gwDeclareCustomsBinding(TopicExchange gwFileTopicExchange, Queue gwDeclareCustomsQueueBLPT){
        return BindingBuilder.bind(gwDeclareCustomsQueueBLPT).to(gwFileTopicExchange)
                .with(GW_SYSTEM_DECLARE_CUSTOMS_ROUTINGKEY);
    }

    @Bean
    public Queue gwDeclareCustomsQueueDKA(){
        return new Queue(GW_SYSTEM_DECLARE_CUSTOMS_QUEUE_DKA);
    }

    @Bean
    public Binding gwDeclareCustomsBindingDKA(TopicExchange gwFileTopicExchange, Queue gwDeclareCustomsQueueDKA){
        return BindingBuilder.bind(gwDeclareCustomsQueueDKA).to(gwFileTopicExchange)
                .with(GW_SYSTEM_DECLARE_CUSTOMS_ROUTINGKEY);
    }

    @Bean
    public Queue gwClearancePaperQueueBLPT() {
        return new Queue(GW_SYSTEM_CLEARANCEPAPER_QUEUE_BLPT);
    }

    @Bean
    public Binding gwClearancePaperBinding(TopicExchange gwFileTopicExchange, Queue gwClearancePaperQueueBLPT) {
        return BindingBuilder.bind(gwClearancePaperQueueBLPT).to(gwFileTopicExchange)
                .with(GW_SYSTEM_CLEARANCEPAPER_ROUTINGKEY);
    }

    @Bean
    public Queue gwClearancePaperQueueXXYO() {
        return new Queue(GW_SYSTEM_CLEARANCEPAPER_QUEUE_XXYO);
    }

    @Bean
    public Binding gwClearancePaperBindingXXYO(TopicExchange gwFileTopicExchange, Queue gwClearancePaperQueueXXYO) {
        return BindingBuilder.bind(gwClearancePaperQueueXXYO).to(gwFileTopicExchange)
                .with(GW_SYSTEM_CLEARANCEPAPER_ROUTINGKEY);
    }

    @Bean
    public Queue gwPortTransQueueBLPT() {
        return new Queue(GW_SYSTEM_PORTTRANS_QUEUE_BLPT);
    }

    @Bean
    public Binding gwPortTransBinding(TopicExchange gwFileTopicExchange, Queue gwPortTransQueueBLPT) {
        return BindingBuilder.bind(gwPortTransQueueBLPT).to(gwFileTopicExchange)
                .with(GW_SYSTEM_PORTTRANS_ROUTINGKEY);
    }

    @Bean
    public Queue gwDeclareMsgQueueDKA() {
        return new Queue(GW_SYSTEM_DECLAREMSG_QUEUE_DKA);
    }

    @Bean
    public Binding gwDeclareMsgBindingDKA(TopicExchange gwFileTopicExchange, Queue gwDeclareMsgQueueDKA) {
        return BindingBuilder.bind(gwDeclareMsgQueueDKA).to(gwFileTopicExchange)
                .with(GW_SYSTEM_DECLAREMSG_ROUTINGKEY);
    }

    @Bean
    public Queue gwConDeclareMsgQueueDKA() {
        return new Queue(GW_SYSTEM_CONDECLAREMSG_QUEUE_DKA);
    }

    @Bean
    public Binding gwConDeclareMsgBindingDKA(TopicExchange gwFileTopicExchange, Queue gwConDeclareMsgQueueDKA) {
        return BindingBuilder.bind(gwConDeclareMsgQueueDKA).to(gwFileTopicExchange)
                .with(GW_SYSTEM_CONDECLAREMSG_ROUTINGKEY);
    }

    @Bean
    public Queue gwFollowVehicleQueueDKA() {
        return new Queue(GW_SYSTEM_FOLLOWVEHICLE_QUEUE_DKA);
    }

    @Bean
    public Binding gwFollowVehicleBindingDKA(TopicExchange gwFileTopicExchange, Queue gwFollowVehicleQueueDKA) {
        return BindingBuilder.bind(gwFollowVehicleQueueDKA).to(gwFileTopicExchange)
                .with(GW_SYSTEM_FOLLOWVEHICLE_ROUTINGKEY);
    }

    @Bean
    public Queue gwFollowVehicleBFQueueDKA() {
        return new Queue(GW_SYSTEM_FOLLOWVEHICLE_BF_QUEUE_DKA);
    }

    @Bean
    public Binding gwFollowVehicleBFBindingDKA(TopicExchange gwFileTopicExchange, Queue gwFollowVehicleBFQueueDKA) {
        return BindingBuilder.bind(gwFollowVehicleBFQueueDKA).to(gwFileTopicExchange)
                .with(GW_SYSTEM_FOLLOWVEHICLE_BF_ROUTINGKEY);
    }
    /*---------------------------------关务系统系统消息队列------------------------------*/
}
