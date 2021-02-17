package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HP
 */
@Configuration
public class GWCZSystemRabbitmqConfig {
    /*---------------------------------国外场站系统-----------------------------*/
    public static final String GWCZ_SYSTEM_FILES_TOPIC_EXCHANGE = "gwcz.system.files.topic.exchange";

    // 运输计划
    public static final String GWCZ_SYSTEM_TRANSPORTPLAN_QUEUE_BLPT = "gwcz_system_transportplan_queue_blpt";
    public static final String GWCZ_SYSTEM_TRANSPORTPLAN_QUEUE_PX = "gwcz_system_transportplan_queue_px";
    public static final String GWCZ_SYSTEM_TRANSPORTPLAN_ROUTINGKEY = "gwcz.system.files.transportplan";

    // 拆箱代理表
    public static final String GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_BLPT = "gwcz_system_unpackingAgent_queue_blpt";
    public static final String GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_JS = "gwcz_system_unpackingAgent_queue_js";
//    public static final String GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_DKA = "gwcz_system_unpackingAgent_queue_dka";
    public static final String GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_XG = "gwcz_system_unpackingAgent_queue_xg";
    public static final String GWCZ_SYSTEM_UNPACKINGAGENT_ROUTINGKEY = "gwcz.system.files.unpackingAgent";

    //精简拆箱代理表
    public static final String GWCZ_SYSTEM_UNPACKAGENT_QUEUE_GW = "gwcz_system_unpackAgent_queue_gw";
    //    public static final String GWCZ_SYSTEM_UNPACKAGENT_QUEUE_DKA = "gwcz_system_unpackAgent_queue_dka";
    public static final String GWCZ_SYSTEM_UNPACKAGENT_ROUTINGKEY = "gwcz.system.files.unpackAgent";

    //装箱要求 boxing_require
    public static final String GWCZ_SYSTEM_BOXINGREQUIRE_QUEUE_BLPT = "gwcz_system_boxingRequire_queue_blpt";
    public static final String GWCZ_SYSTEM_BOXINGREQUIRE_QUEUE_PX = "gwcz_system_boxingRequire_queue_px";
    public static final String GWCZ_SYSTEM_BOXINGREQUIRE_ROUTINGKEY = "gwcz.system.files.boxingRequire";

    //到货信息表
    public static final String GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_BLPT = "gwcz_system_arrivalgoodslist_queue_blpt";
    public static final String GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_PX = "gwcz_system_arrivalgoodslist_queue_px";
    public static final String GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_DKA = "gwcz_system_arrivalgoodslist_queue_dka";
    public static final String GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_DKA1 = "gwcz_system_arrivalgoodslist_queue_dka1";
    public static final String GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_GW = "gwcz_system_arrivalgoodslist_queue_gw";
    public static final String GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_JS = "gwcz_system_arrivalgoodslist_queue_js";
    public static final String GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_XG = "gwcz_system_arrivalgoodslist_queue_xg";
    public static final String GWCZ_SYSTEM_ARRIVALGOODSLIST_ROUTINGKEY = "gwcz.system.files.arrivalgoodslist";

    //回程进站时间统计表
    public static final String GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_BLPT = "gwcz_system_arrivalStationtimetotal_queue_blpt";
    public static final String GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_PX = "gwcz_system_arrivalStationtimetotal_queue_px";
    public static final String GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_XG = "gwcz_system_arrivalStationtimetotal_queue_xg";
    public static final String GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_DC = "gwcz_system_arrivalStationtimetotal_queue_dc";
    public static final String GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_GW = "gwcz_system_arrivalStationtimetotal_queue_gw";
    public static final String GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_DKA = "gwcz_system_arrivalStationtimetotal_queue_dka";
    public static final String GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_ROUTINGKEY = "gwcz.system.files.arrivalStationtimetotal";

    //提箱号表
    public static final String GWCZ_SYSTEM_PICKCON_NO_QUEUE_BLPT = "gwcz_system_pickcon_no_queue_blpt";
    public static final String GWCZ_SYSTEM_PICKCON_NO_ROUTINGKEY = "gwcz.system.files.pickconNo";

    //提箱指令
    public static final String GWCZ_SYSTEM_PICKCON_COMMAND_QUEUE_BLPT = "gwcz_system_pickcon_command_queue_blpt";
    public static final String GWCZ_SYSTEM_PICKCON_COMMAND_ROUTINGKEY = "gwcz.system.files.pickconCommand";

    // 空箱数据
    public static final String GWCZ_SYSTEM_EMPTY_BOX_QUEUE_XG = "gwcz_system_empty_box_queue_xg";
    public static final String GWCZ_SYSTEM_EMPTY_BOX_ROUTINGKEY = "gwcz.system.files.emptybox";

    // 拆箱代理表
    @Bean
    public TopicExchange gwczTopicExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(GWCZ_SYSTEM_FILES_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue gwczTransportplanQueueBLPT() {
        return new Queue(GWCZ_SYSTEM_TRANSPORTPLAN_QUEUE_BLPT);
    }

    @Bean
    public Binding gwczTransportplanBindingPX(TopicExchange gwczTopicExchange, Queue gwczTransportplanQueueBLPT) {
        return BindingBuilder.bind(gwczTransportplanQueueBLPT).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_TRANSPORTPLAN_ROUTINGKEY);
    }

    @Bean
    public Queue gwczTransportplanQueuePX() {
        return new Queue(GWCZ_SYSTEM_TRANSPORTPLAN_QUEUE_PX);
    }

    @Bean
    public Binding gwczTransportplanBindingPXXT(TopicExchange gwczTopicExchange, Queue gwczTransportplanQueuePX) {
        return BindingBuilder.bind(gwczTransportplanQueuePX).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_TRANSPORTPLAN_ROUTINGKEY);
    }

    @Bean
    public Queue gwczUnpackingAgentQueueBLPT() {
        return new Queue(GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_BLPT);
    }

    @Bean
    public Binding gwczUnpackingAgentBindingPX(TopicExchange gwczTopicExchange, Queue gwczUnpackingAgentQueueBLPT) {
        return BindingBuilder.bind(gwczUnpackingAgentQueueBLPT).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_UNPACKINGAGENT_ROUTINGKEY);
    }

    @Bean
    public Queue gwczUnpackingAgentQueueJS() {
        return new Queue(GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_JS);
    }

    @Bean
    public Binding gwczUnpackingAgentBindingJS(TopicExchange gwczTopicExchange, Queue gwczUnpackingAgentQueueJS) {
        return BindingBuilder.bind(gwczUnpackingAgentQueueJS).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_UNPACKINGAGENT_ROUTINGKEY);
    }

//    @Bean
//    public Queue gwczUnpackingAgentQueueDKA() {
//        return new Queue(GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_DKA);
//    }

//    @Bean
//    public Binding gwczUnpackingAgentBindingDKA(TopicExchange gwczTopicExchange, Queue gwczUnpackingAgentQueueDKA) {
//        return BindingBuilder.bind(gwczUnpackingAgentQueueDKA).to(gwczTopicExchange)
//                .with(GWCZ_SYSTEM_UNPACKINGAGENT_ROUTINGKEY);
//    }

    @Bean
    public Queue gwczUnpackingAgentQueueXG() {
        return new Queue(GWCZ_SYSTEM_UNPACKINGAGENT_QUEUE_XG);
    }

    @Bean
    public Binding gwczUnpackingAgentBindingXG(TopicExchange gwczTopicExchange, Queue gwczUnpackingAgentQueueXG) {
        return BindingBuilder.bind(gwczUnpackingAgentQueueXG).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_UNPACKINGAGENT_ROUTINGKEY);
    }

    @Bean
    public Queue gwczUnpackAgentQueueGW() {
        return new Queue(GWCZ_SYSTEM_UNPACKAGENT_QUEUE_GW);
    }

    @Bean
    public Binding gwczUnpackingAgentBindingGW(TopicExchange gwczTopicExchange, Queue gwczUnpackAgentQueueGW) {
        return BindingBuilder.bind(gwczUnpackAgentQueueGW).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_UNPACKAGENT_ROUTINGKEY);
    }

//    @Bean
//    public Queue gwczUnpackAgentQueueDKA() {
//        return new Queue(GWCZ_SYSTEM_UNPACKAGENT_QUEUE_DKA);
//    }

//    @Bean
//    public Binding gwczUnpackAgentBindingDKA(TopicExchange gwczTopicExchange, Queue gwczUnpackAgentQueueDKA) {
//        return BindingBuilder.bind(gwczUnpackAgentQueueDKA).to(gwczTopicExchange)
//                .with(GWCZ_SYSTEM_UNPACKAGENT_ROUTINGKEY);
//    }

    //装箱要求 boxing_require
    @Bean
    public Queue gwczBoxingRequireQueueBLPT() {
        return new Queue(GWCZ_SYSTEM_BOXINGREQUIRE_QUEUE_BLPT);
    }

    @Bean
    public Binding gwczBoxingRequireBindingBLPT(TopicExchange gwczTopicExchange, Queue gwczBoxingRequireQueueBLPT) {
        return BindingBuilder.bind(gwczBoxingRequireQueueBLPT).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_BOXINGREQUIRE_ROUTINGKEY);
    }

    @Bean
    public Queue gwczBoxingRequireQueuePX(){
        return new Queue(GWCZ_SYSTEM_BOXINGREQUIRE_QUEUE_PX);
    }

    @Bean
    public Binding gwczBoxingRequireBindingPX(TopicExchange gwczTopicExchange, Queue gwczBoxingRequireQueuePX){
        return BindingBuilder.bind(gwczBoxingRequireQueuePX).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_BOXINGREQUIRE_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalgoodslistQueueBLPT() {
        return new Queue(GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_BLPT);
    }

    @Bean
    public Binding gwczArrivalgoodslistBindingBLPT(TopicExchange gwczTopicExchange, Queue gwczArrivalgoodslistQueueBLPT) {
        return BindingBuilder.bind(gwczArrivalgoodslistQueueBLPT).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALGOODSLIST_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalgoodslistQueuePX() {
        return new Queue(GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_PX);
    }

    @Bean
    public Binding gwczArrivalgoodslistBindingPX(TopicExchange gwczTopicExchange, Queue gwczArrivalgoodslistQueuePX) {
        return BindingBuilder.bind(gwczArrivalgoodslistQueuePX).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALGOODSLIST_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalgoodslistQueueDKA() {
        return new Queue(GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_DKA);
    }

    @Bean
    public Binding gwczArrivalgoodslistBindingDKA(TopicExchange gwczTopicExchange, Queue gwczArrivalgoodslistQueueDKA) {
        return BindingBuilder.bind(gwczArrivalgoodslistQueueDKA).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALGOODSLIST_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalgoodslistQueueDKA1() {
        return new Queue(GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_DKA1);
    }

    @Bean
    public Binding gwczArrivalgoodslistBindingDKA1(TopicExchange gwczTopicExchange, Queue gwczArrivalgoodslistQueueDKA1) {
        return BindingBuilder.bind(gwczArrivalgoodslistQueueDKA1).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALGOODSLIST_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalgoodslistQueueGW() {
        return new Queue(GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_GW);
    }

    @Bean
    public Binding gwczArrivalgoodslistBindingGW(TopicExchange gwczTopicExchange, Queue gwczArrivalgoodslistQueueGW) {
        return BindingBuilder.bind(gwczArrivalgoodslistQueueGW).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALGOODSLIST_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalgoodslistQueueJS() {
        return new Queue(GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_JS);
    }

    @Bean
    public Binding gwczArrivalgoodslistBindingJS(TopicExchange gwczTopicExchange, Queue gwczArrivalgoodslistQueueJS) {
        return BindingBuilder.bind(gwczArrivalgoodslistQueueJS).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALGOODSLIST_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalgoodslistQueueXG() {
        return new Queue(GWCZ_SYSTEM_ARRIVALGOODSLIST_QUEUE_XG);
    }

    @Bean
    public Binding gwczArrivalgoodslistBindingXG(TopicExchange gwczTopicExchange, Queue gwczArrivalgoodslistQueueXG) {
        return BindingBuilder.bind(gwczArrivalgoodslistQueueXG).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALGOODSLIST_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalStationtimetotalQueueBLPT() {
        return new Queue(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_BLPT);
    }

    @Bean
    public Binding gwczArrivalStationtimetotalBindingPX(TopicExchange gwczTopicExchange, Queue gwczArrivalStationtimetotalQueueBLPT) {
        return BindingBuilder.bind(gwczArrivalStationtimetotalQueueBLPT).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalStationtimetotalQueuePX() {
        return new Queue(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_PX);
    }

    @Bean
    public Binding gwczArrivalStationtimetotalBindingPXXT(TopicExchange gwczTopicExchange, Queue gwczArrivalStationtimetotalQueuePX) {
        return BindingBuilder.bind(gwczArrivalStationtimetotalQueuePX).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalStationtimetotalQueueXG() {
        return new Queue(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_XG);
    }

    @Bean
    public Binding gwczArrivalStationtimetotalBindingXG(TopicExchange gwczTopicExchange, Queue gwczArrivalStationtimetotalQueueXG) {
        return BindingBuilder.bind(gwczArrivalStationtimetotalQueueXG).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalStationtimetotalQueueDC() {
        return new Queue(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_DC);
    }

    @Bean
    public Binding gwczArrivalStationtimetotalBindingDC(TopicExchange gwczTopicExchange, Queue gwczArrivalStationtimetotalQueueDC) {
        return BindingBuilder.bind(gwczArrivalStationtimetotalQueueDC).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalStationtimetotalQueueGW() {
        return new Queue(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_GW);
    }

    @Bean
    public Binding gwczArrivalStationtimetotalBindingGW(TopicExchange gwczTopicExchange, Queue gwczArrivalStationtimetotalQueueGW) {
        return BindingBuilder.bind(gwczArrivalStationtimetotalQueueGW).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_ROUTINGKEY);
    }

    @Bean
    public Queue gwczArrivalStationtimetotalQueueDKA() {
        return new Queue(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_QUEUE_DKA);
    }

    @Bean
    public Binding gwczArrivalStationtimetotalBindingDKA(TopicExchange gwczTopicExchange, Queue gwczArrivalStationtimetotalQueueDKA) {
        return BindingBuilder.bind(gwczArrivalStationtimetotalQueueDKA).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_ARRIVALSTATONTIMETOTAL_ROUTINGKEY);
    }


    @Bean
    public Queue gwczPickconNoQueueBLPT() {
        return new Queue(GWCZ_SYSTEM_PICKCON_NO_QUEUE_BLPT);
    }

    @Bean
    public Binding gwczPickconNoBindingPX(TopicExchange gwczTopicExchange, Queue gwczPickconNoQueueBLPT) {
        return BindingBuilder.bind(gwczPickconNoQueueBLPT).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_PICKCON_NO_ROUTINGKEY);
    }

    @Bean
    public Queue gwczPickconCommandQueueBLPT() {
        return new Queue(GWCZ_SYSTEM_PICKCON_COMMAND_QUEUE_BLPT);
    }

    @Bean
    public Binding gwczPickconCommandBindingPX(TopicExchange gwczTopicExchange, Queue gwczPickconCommandQueueBLPT) {
        return BindingBuilder.bind(gwczPickconCommandQueueBLPT).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_PICKCON_COMMAND_ROUTINGKEY);
    }

    @Bean
    public Queue gwczEmptyBoxQueueXG() {
        return new Queue(GWCZ_SYSTEM_EMPTY_BOX_QUEUE_XG);
    }

    @Bean
    public Binding gwczEmptyBoxBindingXG(TopicExchange gwczTopicExchange, Queue gwczEmptyBoxQueueXG) {
        return BindingBuilder.bind(gwczEmptyBoxQueueXG).to(gwczTopicExchange)
                .with(GWCZ_SYSTEM_EMPTY_BOX_ROUTINGKEY);
    }

    /*---------------------------------国外场站系统-----------------------------*/
}
