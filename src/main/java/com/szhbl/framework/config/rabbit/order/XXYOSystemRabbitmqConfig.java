package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HP
 */
@Configuration
public class XXYOSystemRabbitmqConfig {
    /*---------------------------------箱行亚欧系统消息队列------------------------------*/

    public static final String XXYO_SYSTEM_FILES_TOPIC_EXCHANGE = "xxyo.system.files.topic.exchange";

    // 进站集装箱拍照
    public static final String XXYO_SYSTEM_ARRIVALPHOTO_ROUTINGKEY = "xxyo.system.arrivalPhoto.*";
    // 进站集装箱拍照
    public static final String XXYO_SYSTEM_ARRIVALPHOTO_QUEUE_BLPT = "xxyo_system_arrivalPhoto_queue_blpt";
    // 进站集装箱拍照
    public static final String XXYO_SYSTEM_ARRIVALPHOTO_QUEUE_DKA = "xxyo_system_arrivalPhoto_queue_dka";

    // 签收单
    public static final String XXYO_SYSTEM_RECEIPTGOODS_ROUTINGKEY = "xxyo.system.receiptGoods.*";
    // 签收单
    public static final String XXYO_SYSTEM_RECEIPTGOODS_QUEUE_BLPT = "xxyo_system_receiptGoods_queue_blpt";
    // 签收单
    public static final String XXYO_SYSTEM_RECEIPTGOODS_QUEUE_GW = "xxyo_system_receiptGoods_queue_gw";
    // 签收单
    public static final String XXYO_SYSTEM_RECEIPTGOODS_QUEUE_DKA = "xxyo_system_receiptGoods_queue_dka";

    @Bean
    public TopicExchange xxyoFileTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(XXYO_SYSTEM_FILES_TOPIC_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue xxyoArrivalPhotoQueueBLPT(){
        return new Queue(XXYO_SYSTEM_ARRIVALPHOTO_QUEUE_BLPT);
    }

    @Bean
    public Queue xxyoArrivalPhotoQueueDKA(){
        return new Queue(XXYO_SYSTEM_ARRIVALPHOTO_QUEUE_DKA);
    }

    @Bean
    public Binding xxyoArrivalPhotoBinding(TopicExchange xxyoFileTopicExchange, Queue xxyoArrivalPhotoQueueBLPT){
        return BindingBuilder.bind(xxyoArrivalPhotoQueueBLPT).to(xxyoFileTopicExchange)
                .with(XXYO_SYSTEM_ARRIVALPHOTO_ROUTINGKEY);
    }

    @Bean
    public Binding xxyoArrivalPhotoBindingDKA(TopicExchange xxyoFileTopicExchange, Queue xxyoArrivalPhotoQueueDKA){
        return BindingBuilder.bind(xxyoArrivalPhotoQueueDKA).to(xxyoFileTopicExchange)
                .with(XXYO_SYSTEM_ARRIVALPHOTO_ROUTINGKEY);
    }

    @Bean
    public Queue xxyoReceiptGoodsQueueBLPT(){
        return new Queue(XXYO_SYSTEM_RECEIPTGOODS_QUEUE_BLPT);
    }

    @Bean
    public Binding xxyoReceiptGoodsBinding(TopicExchange xxyoFileTopicExchange, Queue xxyoReceiptGoodsQueueBLPT){
        return BindingBuilder.bind(xxyoReceiptGoodsQueueBLPT).to(xxyoFileTopicExchange)
                .with(XXYO_SYSTEM_RECEIPTGOODS_ROUTINGKEY);
    }


    @Bean
    public Queue xxyoReceiptGoodsQueueGW(){
        return new Queue(XXYO_SYSTEM_RECEIPTGOODS_QUEUE_GW);
    }

    @Bean
    public Binding xxyoReceiptGoodsBindingGW(TopicExchange xxyoFileTopicExchange, Queue xxyoReceiptGoodsQueueGW){
        return BindingBuilder.bind(xxyoReceiptGoodsQueueGW).to(xxyoFileTopicExchange)
                .with(XXYO_SYSTEM_RECEIPTGOODS_ROUTINGKEY);
    }

    @Bean
    public Queue xxyoReceiptGoodsQueueDKA(){
        return new Queue(XXYO_SYSTEM_RECEIPTGOODS_QUEUE_DKA);
    }

    @Bean
    public Binding xxyoReceiptGoodsBindingDKA(TopicExchange xxyoFileTopicExchange, Queue xxyoReceiptGoodsQueueDKA){
        return BindingBuilder.bind(xxyoReceiptGoodsQueueDKA).to(xxyoFileTopicExchange)
                .with(XXYO_SYSTEM_RECEIPTGOODS_ROUTINGKEY);
    }
    /*---------------------------------箱行亚欧系统消息队列------------------------------*/
}
