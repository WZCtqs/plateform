package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HP
 */
@Configuration
public class XGSystemRabbitmqConfig {
    /*---------------------------------箱管系统放箱单、还箱单消息队列------------------------------*/

    public static final String XG_SYSTEM_FILES_TOPIC_EXCHANGE = "xg.system.files.topic.exchange";

    // 提箱单
    public static final String XG_SYSTEM_PICKCON_QUEUE_BLPT = "xg_system_pickcon_queue_blpt";
    public static final String XG_SYSTEM_PICKCON_QUEUE_XXYO = "xg_system_pickcon_queue_xxyo";
    public static final String XG_SYSTEM_PICKCON_QUEUE_GWCZ = "xg_system_pickcon_queue_gwcz";
    public static final String XG_SYSTEM_PICKCON_ROUTINGKEY = "xg.system.files.pickcon";
    // 还箱单
    public static final String XG_SYSTEM_RETURNCON_QUEUE_BLPT = "xg_system_returncon_queue_blpt";
    public static final String XG_SYSTEM_RETURNCON_QUEUE_XXYO = "xg_system_returncon_queue_xxyo";
    public static final String XG_SYSTEM_RETURNCON_QUEUE_GWCZ = "xg_system_returncon_queue_gwcz";
    public static final String XG_SYSTEM_RETURNCON_QUEUE_PX = "xg_system_returncon_queue_px";
    public static final String XG_SYSTEM_RETURNCON_ROUTINGKEY = "xg.system.files.returncon";

    // 海关信
    public static final String XG_SYSTEM_CUSTOMSLETTER_QUEUE_BLPT = "xg_system_customsletter_queue_blpt";
    public static final String XG_SYSTEM_CUSTOMSLETTER_ROUTINGKEY = "xg.system.files.customsletter";

    public static final String XG_SYSTEM_CONTAINER_INFO_QUEUE_DKA = "xg_system_container_info_queue_dka";
    public static final String XG_SYSTEM_CONTAINER_INFO_ROUTINGKEY = "xg.system.files.containerInfo";

    // 提还箱单  箱管-->堆场
    public static final String XG_SYSTEM_PICKRETURNNO_QUEUE_DCXT = "xg_system_pickReturnNo_queue_dcxt";
    public static final String XG_SYSTEM_PICKRETURNNO_ROUTINGKEY = "xg.system.pickReturnSn.*";

    @Bean
    public TopicExchange xgConPickreturnTopicExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(XG_SYSTEM_FILES_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue xgConPickQueueBLPT() {
        return new Queue(XG_SYSTEM_PICKCON_QUEUE_BLPT);
    }

    @Bean
    public Queue xgConPickQueueXXYO() {
        return new Queue(XG_SYSTEM_PICKCON_QUEUE_XXYO);
    }

    @Bean
    public Queue xgConReturnQueueBLPT() {
        return new Queue(XG_SYSTEM_RETURNCON_QUEUE_BLPT);
    }

    @Bean
    public Queue xgConReturnQueueXXYO() {
        return new Queue(XG_SYSTEM_RETURNCON_QUEUE_XXYO);
    }

    @Bean
    public Queue xgConPickQueueGWCZ() {
        return new Queue(XG_SYSTEM_PICKCON_QUEUE_GWCZ);
    }

    @Bean
    public Binding xgConPickBindingGWCZ(TopicExchange xgConPickreturnTopicExchange, Queue xgConPickQueueGWCZ) {
        return BindingBuilder.bind(xgConPickQueueGWCZ).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_PICKCON_ROUTINGKEY);
    }

    @Bean
    public Binding xgConPickBindingDKA(TopicExchange xgConPickreturnTopicExchange, Queue xgConPickQueueBLPT) {
        return BindingBuilder.bind(xgConPickQueueBLPT).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_PICKCON_ROUTINGKEY);
    }

    @Bean
    public Binding xgConReturnBindingDKA(TopicExchange xgConPickreturnTopicExchange, Queue xgConReturnQueueBLPT) {
        return BindingBuilder.bind(xgConReturnQueueBLPT).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_RETURNCON_ROUTINGKEY);
    }

    @Bean
    public Binding xgConPickBindingXXYO(TopicExchange xgConPickreturnTopicExchange, Queue xgConPickQueueXXYO) {
        return BindingBuilder.bind(xgConPickQueueXXYO).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_PICKCON_ROUTINGKEY);
    }

    @Bean
    public Binding xgConReturnBindingXXYO(TopicExchange xgConPickreturnTopicExchange, Queue xgConReturnQueueXXYO) {
        return BindingBuilder.bind(xgConReturnQueueXXYO).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_RETURNCON_ROUTINGKEY);
    }

    @Bean
    public Queue xgConReturnQueueGWCZ() {
        return new Queue(XG_SYSTEM_RETURNCON_QUEUE_GWCZ);
    }

    @Bean
    public Binding xgConReturnBindingGWCZ(TopicExchange xgConPickreturnTopicExchange, Queue xgConReturnQueueGWCZ) {
        return BindingBuilder.bind(xgConReturnQueueGWCZ).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_RETURNCON_ROUTINGKEY);
    }

    @Bean
    public Queue xgConReturnQueuePX() {
        return new Queue(XG_SYSTEM_RETURNCON_QUEUE_PX);
    }

    @Bean
    public Binding xgConReturnBindingPX(TopicExchange xgConPickreturnTopicExchange, Queue xgConReturnQueuePX) {
        return BindingBuilder.bind(xgConReturnQueuePX).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_RETURNCON_ROUTINGKEY);
    }
    /*---------------------------------箱管系统放箱单、还箱单消息队列------------------------------*/

    @Bean
    public Queue xgCustomsletterQueueBLPT() {
        return new Queue(XG_SYSTEM_CUSTOMSLETTER_QUEUE_BLPT);
    }

    @Bean
    public Binding aaaa(TopicExchange xgConPickreturnTopicExchange, Queue xgCustomsletterQueueBLPT) {
        return BindingBuilder.bind(xgCustomsletterQueueBLPT).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_CUSTOMSLETTER_ROUTINGKEY);
    }

    @Bean
    public Queue xgContainerInfoQueueBLPT() {
        return new Queue(XG_SYSTEM_CONTAINER_INFO_QUEUE_DKA);
    }

    @Bean
    public Binding xgContainerInfoBindingDKA(TopicExchange xgConPickreturnTopicExchange, Queue xgContainerInfoQueueBLPT) {
        return BindingBuilder.bind(xgContainerInfoQueueBLPT).to(xgConPickreturnTopicExchange)
                .with(XG_SYSTEM_CONTAINER_INFO_ROUTINGKEY);
    }

    @Bean
    public Queue xgPickReturnNoQueueDCXT() {
        return new Queue(XG_SYSTEM_PICKRETURNNO_QUEUE_DCXT);
    }

    @Bean
    public Binding xgPickReturnNoBindingDCXT(TopicExchange xgConPickreturnTopicExchange, Queue xgPickReturnNoQueueDCXT) {
        return BindingBuilder.bind(xgPickReturnNoQueueDCXT).to(xgConPickreturnTopicExchange).with(XG_SYSTEM_PICKRETURNNO_ROUTINGKEY);
    }

    /*--------------------------------------堆场系统----------------------------------------*/
    public static final String DCXT_SYSTEM_CONTAINER_DIRECT_EXCHANGE = "dcxt.system.container.direct.exchange";

    // 堆场还箱进场
    public static final String DCXT_SYSTEM_RETURNCON_QUEUE_XG = "dcxt_system_returncon_queue_xg";
    public static final String DCXT_SYSTEM_RETURNCON_ROUTINGKEY = "dcxt.system.returncon";
    // 堆场提箱出场
    public static final String DCXT_SYSTEM_PICKCON_QUEUE_XG = "dcxt_system_pickcon_queue_xg";
    public static final String DCXT_SYSTEM_PICKCON_ROUTINGKEY = "dcxt.system.pickcon";

    @Bean
    public DirectExchange dcxtSystemContainerExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(DCXT_SYSTEM_CONTAINER_DIRECT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue dcxtSystemReturnConQueueXG() {
        return new Queue(DCXT_SYSTEM_RETURNCON_QUEUE_XG);
    }

    @Bean
    public Binding dcxtSystemReturnConBindingXG(DirectExchange dcxtSystemContainerExchange, Queue dcxtSystemReturnConQueueXG) {
        return BindingBuilder.bind(dcxtSystemReturnConQueueXG).to(dcxtSystemContainerExchange)
                .with(DCXT_SYSTEM_RETURNCON_ROUTINGKEY);
    }

    @Bean
    public Queue dcxtSystemPickConQueueXG() {
        return new Queue(DCXT_SYSTEM_PICKCON_QUEUE_XG);
    }

    @Bean
    public Binding dcxtSystemPickConBindingXG(DirectExchange dcxtSystemContainerExchange, Queue dcxtSystemPickConQueueXG) {
        return BindingBuilder.bind(dcxtSystemPickConQueueXG).to(dcxtSystemContainerExchange)
                .with(DCXT_SYSTEM_PICKCON_ROUTINGKEY);
    }
    /*--------------------------------------堆场系统----------------------------------------*/
}
