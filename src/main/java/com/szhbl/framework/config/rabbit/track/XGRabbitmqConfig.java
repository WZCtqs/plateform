package com.szhbl.framework.config.rabbit.track;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author HP
 */
@Configuration
public class XGRabbitmqConfig {

    /*---------------------------------箱管系统整柜操作时间信息------------------------------------*/
    @Bean
    public TopicExchange xgBoxoperationTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(TrackRabbitmq.XG_BOXOPERATION_TOPIC_EXCHANGE).durable(true).build();
    }
    /**
     * 箱管系统整柜操作消息队列
     * @return
     */
    @Bean
    public Queue xgBoxoperationQueueBLPT(){
        return new Queue(TrackRabbitmq.XG_BOXOPERATION_QUEUE_BLPT);
    }
    @Bean
    public Binding xgBoxoperationBindingBLPT(TopicExchange xgBoxoperationTopicExchange, Queue xgBoxoperationQueueBLPT){
        return BindingBuilder.bind(xgBoxoperationQueueBLPT).to(xgBoxoperationTopicExchange).with(TrackRabbitmq.XG_BOXOPERATION_ROUTINGKEY);
    }
    /*---------------------------------箱管系统整柜操作时间信息------------------------------------*/

    @Bean
    public Queue xgBoxoperationQueueReturnBLPT() {
        return new Queue(TrackRabbitmq.XG_BOXOPERATION_QUEUE_RETURN);
    }

    @Bean
    public Binding xgBoxoperationReturnBindingBLPT(TopicExchange xgBoxoperationTopicExchange, Queue xgBoxoperationQueueReturnBLPT) {
        return BindingBuilder.bind(xgBoxoperationQueueReturnBLPT).to(xgBoxoperationTopicExchange).with(TrackRabbitmq.XG_BOXOPERATION_RETURN_ROUTINGKEY);
    }

    @Bean
    public Queue xgBoxoperationQueueReturnJS() {
        return new Queue(TrackRabbitmq.XG_BOXOPERATION_QUEUE_RETURN_JS);
    }

    @Bean
    public Binding xgBoxoperationReturnBindingJS(TopicExchange xgBoxoperationTopicExchange, Queue xgBoxoperationQueueReturnJS) {
        return BindingBuilder.bind(xgBoxoperationQueueReturnJS).to(xgBoxoperationTopicExchange).with(TrackRabbitmq.XG_BOXOPERATION_RETURN_ROUTINGKEY);
    }

    @Bean
    public Queue xgBoxoperationQueuePickBLPT() {
        return new Queue(TrackRabbitmq.XG_BOXOPERATION_QUEUE_PICK);
    }

    @Bean
    public Binding xgBoxoperationPickBindingBLPT(TopicExchange xgBoxoperationTopicExchange, Queue xgBoxoperationQueuePickBLPT) {
        return BindingBuilder.bind(xgBoxoperationQueuePickBLPT).to(xgBoxoperationTopicExchange).with(TrackRabbitmq.XG_BOXOPERATION_PICK_ROUTINGKEY);
    }

    @Bean
    public Queue xgBoxoperationQueuePickDCJS() {
        return new Queue(TrackRabbitmq.XG_BOXOPERATION_QUEUE_DCJS);
    }

    @Bean
    public Binding xgBoxoperationPickBindingDCJS(TopicExchange xgBoxoperationTopicExchange, Queue xgBoxoperationQueuePickDCJS) {
        return BindingBuilder.bind(xgBoxoperationQueuePickDCJS).to(xgBoxoperationTopicExchange).with(TrackRabbitmq.XG_BOXOPERATION_PICK_ROUTINGKEY);
    }

    @Bean
    public Queue xgBoxoperationQueuePickGW() {
        return new Queue(TrackRabbitmq.XG_BOXOPERATION_QUEUE_GW);
    }

    @Bean
    public Binding xgBoxoperationPickBindingGW(TopicExchange xgBoxoperationTopicExchange, Queue xgBoxoperationQueuePickGW) {
        return BindingBuilder.bind(xgBoxoperationQueuePickGW).to(xgBoxoperationTopicExchange).with(TrackRabbitmq.XG_BOXOPERATION_PICK_ROUTINGKEY);
    }

    @Bean
    public Queue xgContainerInfoQueueDCXT() {
        return new Queue(TrackRabbitmq.XG_SYSTEM_CONTAINERINFO_QUEUE_DCXT);
    }

    @Bean
    public Binding xgContainerInfoBindingDCXT(TopicExchange xgBoxoperationTopicExchange, Queue xgContainerInfoQueueDCXT) {
        return BindingBuilder.bind(xgContainerInfoQueueDCXT).to(xgBoxoperationTopicExchange).with(TrackRabbitmq.XG_SYSTEM_CONTAINERINFO_ROUTINGKEY);
    }
    @Bean
    public Queue xgContainerInfoQueueGWCZ() {
        return new Queue(TrackRabbitmq.XG_SYSTEM_CONTAINERINFO_QUEUE_GWCZ);
    }

    @Bean
    public Binding xgContainerInfoBindingGWCZ(TopicExchange xgBoxoperationTopicExchange, Queue xgContainerInfoQueueGWCZ) {
        return BindingBuilder.bind(xgContainerInfoQueueGWCZ).to(xgBoxoperationTopicExchange).with(TrackRabbitmq.XG_SYSTEM_CONTAINERINFO_ROUTINGKEY);
    }
}
