package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HP
 */
@Configuration
public class DKASystemRabbitmqConfig {
    /*---------------------------------大口岸系统-----------------------------*/
    public static final String DKA_SYSTEM_FILES_TOPIC_EXCHANGE = "dka.system.files.topic.exchange";

    // SMGS运单
    public static final String DKA_SYSTEM_SMGS_ROUTINGKEY = "dka.system.smgs.*";
    public static final String DKA_SYSTEM_SMGS_QUEUE_BLPT = "dka_system_smgs_queue_blpt";
    public static final String DKA_SYSTEM_SMGS_QUEUE_GW = "dka_system_smgs_queue_gw";
    public static final String DKA_SYSTEM_SMGS_QUEUE_GWCZ = "dka_system_smgs_queue_gwcz";
    public static final String DKA_SYSTEM_SMGS_QUEUE_JS = "dka_system_smgs_queue_js";

    // CIM运单
    public static final String DKA_SYSTEM_CIM_ROUTINGKEY = "dka.system.cim.*";
    public static final String DKA_SYSTEM_CIM_QUEUE_BLPT = "dka_system_cim_queue_blpt";

    // 正式随车文件
    public static final String DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_ROUTINGKEY = "dka.system.followVehicleFormal.*";
    public static final String DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_QUEUE_BLPT = "dka_system_followVehicleFormal_queue_blpt";
    public static final String DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_QUEUE_GWCZ = "dka_system_followVehicleFormal_queue_gwcz";
    public static final String DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_QUEUE_JS = "dka_system_followVehicleFormal_queue_js";

    // ATB文件
    public static final String DKA_SYSTEM_ATB_ROUTINGKEY = "dka.system.atb.*";
    public static final String DKA_SYSTEM_ATB_QUEUE_BLPT = "dka_system_atb_queue_blpt";

    // 提单文件
    public static final String DKA_SYSTEM_LADINGBILLFORMAL_ROUTINGKEY = "dka.system.ladingBillFormal.*";
    public static final String DKA_SYSTEM_LADINGBILLFORMAL_QUEUE_BLPT = "dka_system_ladingBillFormal_queue_blpt";
//    public static final String DKA_SYSTEM_LADINGBILL_QUEUE_BLPT = "labUrl_queue_client";
    public static final String DKA_SYSTEM_LADINGBILL_QUEUE_BLPTSYSTEM = "labUrl_queue_client_blpt";
    //    public static final String DKA_SYSTEM_LADINGBILL_QUEUE_GWCZ = "labUrl_queue_gwcz";
    public static final String DKA_SYSTEM_LADINGBILL_QUEUE_GWCZSYSTEM = "labUrl_queue_gwczsystem";
    public static final String DKA_SYSTEM_LADINGBILL_QUEUE_JSSYSTEM = "labUrl_queue_jssystem";

    // 提箱情况表
    public static final String DKA_SYSTEM_PICKCONMSG_ROUTINGKEY = "dka.system.pickConMsg.*";
    public static final String DKA_SYSTEM_PICKCONMSG_QUEUE_BLPT = "dka_system_pickConMsg_queue_blpt";

    // 关检到货审核情况表
    public static final String DKA_SYSTEM_ARRIVALEXAMINE_ROUTINGKEY = "dka.system.arrivalExamine";
    public static final String DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_BLPT = "dka_system_arrivalExamine_queue_blpt";
    public static final String DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_GWCZ = "dka_system_arrivalExamine_queue_gwcz";
    public static final String DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_GW = "dka_system_arrivalExamine_queue_gw";
    public static final String DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_PX = "dka_system_arrivalExamine_queue_px";
    public static final String DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_JS = "dka_system_arrivalExamine_queue_js";

    // 托书随车审核
    public static final String DKA_SYSTEM_FOLLOWVEHICLE_EXAMINE_ROUTINGKEY = "dka.system.followVehicleExamine";
    public static final String DKA_SYSTEM_FOLLOWVEHICLE_EXAMINE_QUEUE_BLPT = "dka_system_followVehicleExamine_queue_blpt";
    public static final String DKA_SYSTEM_FOLLOWVEHICLE_EXAMINE_QUEUE_GW = "dka_system_followVehicleExamine_queue_gw";
    // 箱号随车审核
    public static final String DKA_SYSTEM_CONFOLLOWVEHICLE_EXAMINE_ROUTINGKEY = "dka.system.conFollowVehicleExamine";
    public static final String DKA_SYSTEM_CONFOLLOWVEHICLE_EXAMINE_QUEUE_BLPT = "dka_system_conFollowVehicleExamine_queue_blpt";
    public static final String DKA_SYSTEM_CONFOLLOWVEHICLE_EXAMINE_QUEUE_GW = "dka_system_conFollowVehicleExamine_queue_gw";

    //随车问题反馈
    public static final String DKA_SYSTEM_FOLLOWVEHICLE_BF_ROUTINGKEY = "dka.system.followVehicleBF";
    public static final String DKA_SYSTEM_FOLLOWVEHICLE_BF_QUEUE_GW = "dka_system_followVehicleBF_queue_gw";

    //随车件数毛重
    public static final String DKA_SYSTEM_FOLLOWVEHICLEData_BF_ROUTINGKEY = "dka.system.followVehicleData";
    public static final String DKA_SYSTEM_FOLLOWVEHICLEData_BF_QUEUE_GW = "dka_system_followVehicleData_queue_gw";
    public static final String DKA_SYSTEM_FOLLOWVEHICLEData_BF_QUEUE_GWCZ = "dka_system_followVehicleData_queue_gwcz";

    //空箱
    public static final String DKA_SYSTEM_CONTAINERNO_ROUTINGKEY = "dka.system.containerNo";
    public static final String DKA_SYSTEM_CONTAINERNO_QUEUE_XG = "dka_system_containerNo_queue_xg";

    @Bean
    public TopicExchange dkaTopicExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(DKA_SYSTEM_FILES_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue dkaSMGSQueueBLPT() {
        return new Queue(DKA_SYSTEM_SMGS_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaSMGSBindingPX(TopicExchange dkaTopicExchange, Queue dkaSMGSQueueBLPT){
        return BindingBuilder.bind(dkaSMGSQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_SMGS_ROUTINGKEY);
    }

    @Bean
    public Queue dkaSMGSQueueGW() {
        return new Queue(DKA_SYSTEM_SMGS_QUEUE_GW);
    }

    @Bean
    public Binding dkaSMGSBindingGW(TopicExchange dkaTopicExchange, Queue dkaSMGSQueueGW) {
        return BindingBuilder.bind(dkaSMGSQueueGW).to(dkaTopicExchange)
                .with(DKA_SYSTEM_SMGS_ROUTINGKEY);
    }

    @Bean
    public Queue dkaSMGSQueueGWCZ() {
        return new Queue(DKA_SYSTEM_SMGS_QUEUE_GWCZ);
    }

    @Bean
    public Binding dkaSMGSBindingGWCZ(TopicExchange dkaTopicExchange, Queue dkaSMGSQueueGWCZ) {
        return BindingBuilder.bind(dkaSMGSQueueGWCZ).to(dkaTopicExchange)
                .with(DKA_SYSTEM_SMGS_ROUTINGKEY);
    }

    @Bean
    public Queue dkaSMGSQueueJS() {
        return new Queue(DKA_SYSTEM_SMGS_QUEUE_JS);
    }

    @Bean
    public Binding dkaSMGSBindingJS(TopicExchange dkaTopicExchange, Queue dkaSMGSQueueJS) {
        return BindingBuilder.bind(dkaSMGSQueueJS).to(dkaTopicExchange)
                .with(DKA_SYSTEM_SMGS_ROUTINGKEY);
    }

    @Bean
    public Queue dkaCIMQueueBLPT() {
        return new Queue(DKA_SYSTEM_CIM_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaCIMBindingPX(TopicExchange dkaTopicExchange, Queue dkaCIMQueueBLPT) {
        return BindingBuilder.bind(dkaCIMQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_CIM_ROUTINGKEY);
    }

    @Bean
    public Queue dkaFollowVehicleFormalQueueBLPT() {
        return new Queue(DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaFollowVehicleFormalBindingPX(TopicExchange dkaTopicExchange, Queue dkaFollowVehicleFormalQueueBLPT) {
        return BindingBuilder.bind(dkaFollowVehicleFormalQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_ROUTINGKEY);
    }

    @Bean
    public Queue dkaFollowVehicleFormalQueueGWCZ() {
        return new Queue(DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_QUEUE_GWCZ);
    }

    @Bean
    public Binding dkaFollowVehicleFormalBindingGWCZ(TopicExchange dkaTopicExchange, Queue dkaFollowVehicleFormalQueueGWCZ) {
        return BindingBuilder.bind(dkaFollowVehicleFormalQueueGWCZ).to(dkaTopicExchange)
                .with(DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_ROUTINGKEY);
    }

    @Bean
    public Queue dkaFollowVehicleFormalQueueJS() {
        return new Queue(DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_QUEUE_JS);
    }

    @Bean
    public Binding dkaFollowVehicleFormalBindingJS(TopicExchange dkaTopicExchange, Queue dkaFollowVehicleFormalQueueJS) {
        return BindingBuilder.bind(dkaFollowVehicleFormalQueueJS).to(dkaTopicExchange)
                .with(DKA_SYSTEM_FOLLOW_VEHICLE_FORMAL_ROUTINGKEY);
    }


    @Bean
    public Queue dkaATBQueueBLPT() {
        return new Queue(DKA_SYSTEM_ATB_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaATBBindingPX(TopicExchange dkaTopicExchange, Queue dkaATBQueueBLPT) {
        return BindingBuilder.bind(dkaATBQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_ATB_ROUTINGKEY);
    }

    @Bean
    public Queue dkaLadingBillFormalQueueBLPT(){
        return new Queue(DKA_SYSTEM_LADINGBILLFORMAL_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaLadingBillFormalBindingPX(TopicExchange dkaTopicExchange, Queue dkaLadingBillFormalQueueBLPT){
        return BindingBuilder.bind(dkaLadingBillFormalQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_LADINGBILLFORMAL_ROUTINGKEY);
    }

    @Bean
    public Queue dkaPickConMsgQueueBLPT() {
        return new Queue(DKA_SYSTEM_PICKCONMSG_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaPickConMsgBindingPX(TopicExchange dkaTopicExchange, Queue dkaPickConMsgQueueBLPT) {
        return BindingBuilder.bind(dkaPickConMsgQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_PICKCONMSG_ROUTINGKEY);
    }

    @Bean
    public Queue dkaArrivalExamineQueueBLPT() {
        return new Queue(DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaArrivalExamineBindingBLPT(TopicExchange dkaTopicExchange, Queue dkaArrivalExamineQueueBLPT) {
        return BindingBuilder.bind(dkaArrivalExamineQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_ARRIVALEXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaFollowVehicleExamineQueueGW() {
        return new Queue(DKA_SYSTEM_FOLLOWVEHICLE_EXAMINE_QUEUE_GW);
    }

    @Bean
    public Binding dkaFollowVehicleExamineBindingGW(TopicExchange dkaTopicExchange, Queue dkaFollowVehicleExamineQueueGW) {
        return BindingBuilder.bind(dkaFollowVehicleExamineQueueGW).to(dkaTopicExchange)
                .with(DKA_SYSTEM_FOLLOWVEHICLE_EXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaFollowVehicleExamineQueueBLPT() {
        return new Queue(DKA_SYSTEM_FOLLOWVEHICLE_EXAMINE_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaFollowVehicleExamineBindingBLPT(TopicExchange dkaTopicExchange, Queue dkaFollowVehicleExamineQueueBLPT) {
        return BindingBuilder.bind(dkaFollowVehicleExamineQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_FOLLOWVEHICLE_EXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaConFollowVehicleExamineQueueGW() {
        return new Queue(DKA_SYSTEM_CONFOLLOWVEHICLE_EXAMINE_QUEUE_GW);
    }

    @Bean
    public Binding dkaConFollowVehicleExamineBindingGW(TopicExchange dkaTopicExchange, Queue dkaConFollowVehicleExamineQueueGW) {
        return BindingBuilder.bind(dkaConFollowVehicleExamineQueueGW).to(dkaTopicExchange)
                .with(DKA_SYSTEM_CONFOLLOWVEHICLE_EXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaConFollowVehicleExamineQueueBLPT() {
        return new Queue(DKA_SYSTEM_CONFOLLOWVEHICLE_EXAMINE_QUEUE_BLPT);
    }

    @Bean
    public Binding dkaConFollowVehicleExamineBindingBLPT(TopicExchange dkaTopicExchange, Queue dkaConFollowVehicleExamineQueueBLPT) {
        return BindingBuilder.bind(dkaConFollowVehicleExamineQueueBLPT).to(dkaTopicExchange)
                .with(DKA_SYSTEM_CONFOLLOWVEHICLE_EXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaPickConMsgQueueGWCZ() {
        return new Queue(DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_GWCZ);
    }

    @Bean
    public Binding dkaArrivalExamineBindingGWCZ(TopicExchange dkaTopicExchange, Queue dkaPickConMsgQueueGWCZ) {
        return BindingBuilder.bind(dkaPickConMsgQueueGWCZ).to(dkaTopicExchange)
                .with(DKA_SYSTEM_ARRIVALEXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaPickConMsgQueueGW() {
        return new Queue(DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_GW);
    }

    @Bean
    public Binding dkaArrivalExamineBindingGW(TopicExchange dkaTopicExchange, Queue dkaPickConMsgQueueGW) {
        return BindingBuilder.bind(dkaPickConMsgQueueGW).to(dkaTopicExchange)
                .with(DKA_SYSTEM_ARRIVALEXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaPickConMsgQueuePX() {
        return new Queue(DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_PX);
    }

    @Bean
    public Binding dkaArrivalExamineBindingPX(TopicExchange dkaTopicExchange, Queue dkaPickConMsgQueuePX) {
        return BindingBuilder.bind(dkaPickConMsgQueuePX).to(dkaTopicExchange)
                .with(DKA_SYSTEM_ARRIVALEXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaPickConMsgQueueJS() {
        return new Queue(DKA_SYSTEM_ARRIVALEXAMINE_QUEUE_JS);
    }

    @Bean
    public Binding dkaArrivalExamineBindingJS(TopicExchange dkaTopicExchange, Queue dkaPickConMsgQueueJS) {
        return BindingBuilder.bind(dkaPickConMsgQueueJS).to(dkaTopicExchange)
                .with(DKA_SYSTEM_ARRIVALEXAMINE_ROUTINGKEY);
    }

    @Bean
    public Queue dkaFollowVehicleBFQueueGW() {
        return new Queue(DKA_SYSTEM_FOLLOWVEHICLE_BF_QUEUE_GW);
    }

    @Bean
    public Binding dkaFollowVehicleBFBindingGW(TopicExchange dkaTopicExchange, Queue dkaFollowVehicleBFQueueGW) {
        return BindingBuilder.bind(dkaFollowVehicleBFQueueGW).to(dkaTopicExchange)
                .with(DKA_SYSTEM_FOLLOWVEHICLE_BF_ROUTINGKEY);
    }

    @Bean
    public Queue dkaFollowVehicleBFQueueGWDATA() {
        return new Queue(DKA_SYSTEM_FOLLOWVEHICLEData_BF_QUEUE_GW);
    }

    @Bean
    public Binding dkaFollowVehicleDATABindingGW(TopicExchange dkaTopicExchange, Queue dkaFollowVehicleBFQueueGWDATA) {
        return BindingBuilder.bind(dkaFollowVehicleBFQueueGWDATA).to(dkaTopicExchange)
                .with(DKA_SYSTEM_FOLLOWVEHICLEData_BF_ROUTINGKEY);
    }

    @Bean
    public Queue dkaFollowVehicleBFQueueGWCZDATA() {
        return new Queue(DKA_SYSTEM_FOLLOWVEHICLEData_BF_QUEUE_GWCZ);
    }

    @Bean
    public Binding dkaFollowVehicleDATABindingGWCZ(TopicExchange dkaTopicExchange, Queue dkaFollowVehicleBFQueueGWCZDATA) {
        return BindingBuilder.bind(dkaFollowVehicleBFQueueGWCZDATA).to(dkaTopicExchange)
                .with(DKA_SYSTEM_FOLLOWVEHICLEData_BF_ROUTINGKEY);
    }

    @Bean
    public Queue dkaContainerNoQueueXG() {
        return new Queue(DKA_SYSTEM_CONTAINERNO_QUEUE_XG);
    }

    @Bean
    public Binding dkaContainerNoBindingXG(TopicExchange dkaTopicExchange, Queue dkaContainerNoQueueXG) {
        return BindingBuilder.bind(dkaContainerNoQueueXG).to(dkaTopicExchange)
                .with(DKA_SYSTEM_CONTAINERNO_ROUTINGKEY);
    }
    /*---------------------------------大口岸系统-----------------------------*/


//    labInfo_exchange direct url_key labUrl_queue_client
//    labInfo_exchange direct url_key labUrl_queue_merchandiser

//    labInfo_exchange  direct labInfo_key labInfo_queue_client
//    labInfo_exchange direct labInfo_key labInfo_queue_merchandiser

//    labInfo_exchange direct examLetter_key labExamLetter_queue_client
//    labInfo_exchange direct examLetter_key labExamLetter_queue_merchandiser

    @Bean
    public DirectExchange labInfoExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange("labInfo_exchange").durable(true).build();
    }

    /*-------1--------*/
//    @Bean
//    public Queue labUrlQueueClientBLPT() {
//        return new Queue(DKA_SYSTEM_LADINGBILL_QUEUE_BLPT);
//    }

//    @Bean
//    public Binding labUrlQueueClientBinding(DirectExchange labInfoExchange, Queue labUrlQueueClientBLPT) {
//        return BindingBuilder.bind(labUrlQueueClientBLPT).to(labInfoExchange)
//                .with("url_key");
//    }

    @Bean
    public Queue newLabUrlQueueClientBLPT() {
        return new Queue(DKA_SYSTEM_LADINGBILL_QUEUE_BLPTSYSTEM);
    }

    @Bean
    public Binding newLabUrlQueueClientBinding(DirectExchange labInfoExchange, Queue newLabUrlQueueClientBLPT) {
        return BindingBuilder.bind(newLabUrlQueueClientBLPT).to(labInfoExchange)
                .with("url_key_blpt");
    }

    @Bean
    public Queue newLabUrlQueueClientGWCZ() {
        return new Queue(DKA_SYSTEM_LADINGBILL_QUEUE_GWCZSYSTEM);
    }

    @Bean
    public Binding newLabUrlQueueGWCZBinding(DirectExchange labInfoExchange, Queue newLabUrlQueueClientGWCZ) {
        return BindingBuilder.bind(newLabUrlQueueClientGWCZ).to(labInfoExchange)
                .with("url_key_blpt");
    }

    @Bean
    public Queue newLabUrlQueueClientJS() {
        return new Queue(DKA_SYSTEM_LADINGBILL_QUEUE_JSSYSTEM);
    }

    @Bean
    public Binding newLabUrlQueueJSBinding(DirectExchange labInfoExchange, Queue newLabUrlQueueClientJS) {
        return BindingBuilder.bind(newLabUrlQueueClientJS).to(labInfoExchange)
                .with("url_key_blpt");
    }

    /*-------2--------*/
    @Bean
    public Queue labUrlQueueMerchandiserBLPT() {
        return new Queue("labUrl_queue_merchandiser");
    }

    @Bean
    public Binding labUrlQueueMerchandiserBinding(DirectExchange labInfoExchange, Queue labUrlQueueMerchandiserBLPT) {
        return BindingBuilder.bind(labUrlQueueMerchandiserBLPT).to(labInfoExchange)
                .with("url_key");
    }
    /*-------3--------*/
    @Bean
    public Queue labInfoQueueClientBLPT(){
        return new Queue("labInfo_queue_client_test");
    }

    @Bean
    public Binding labInfoQueueClientBinding(DirectExchange labInfoExchange, Queue labInfoQueueClientBLPT){
        return BindingBuilder.bind(labInfoQueueClientBLPT).to(labInfoExchange)
                .with("labInfo_key");
    }
    /*-------4--------*/
    @Bean
    public Queue labInfoQueueMerchandiserBLPT(){
        return new Queue("labInfo_queue_merchandiser");
    }

    @Bean
    public Binding labInfoQueueMerchandiserBinding(DirectExchange labInfoExchange, Queue labInfoQueueMerchandiserBLPT) {
        return BindingBuilder.bind(labInfoQueueMerchandiserBLPT).to(labInfoExchange)
                .with("labInfo_key");
    }

    /*-------5--------*/
//    @Bean
//    public Queue labExamLetterClientBLPT(){
//        return new Queue("labExamLetter_queue_client");
//    }
//
//    @Bean
//    public Binding labExamLetterClientBinding(DirectExchange labInfoExchange, Queue labExamLetterClientBLPT){
//        return BindingBuilder.bind(labExamLetterClientBLPT).to(labInfoExchange)
//                .with("examLetter_key");
//    }
    /*-------6--------*/
//    @Bean
//    public Queue labExamLetterMerchandiserBLPT() {
//        return new Queue("labExamLetter_queue_merchandiser");
//    }
//
//    @Bean
//    public Binding labExamLetterMerchandiserBinding(DirectExchange labInfoExchange, Queue labExamLetterMerchandiserBLPT) {
//        return BindingBuilder.bind(labExamLetterMerchandiserBLPT).to(labInfoExchange)
//                .with("examLetter_key");
//    }

//    @Bean
//    public Queue labUrlQueueClientGWCZ() {
//        return new Queue(DKA_SYSTEM_LADINGBILL_QUEUE_GWCZ);
//    }

//    @Bean
//    public Binding labUrlQueueClientBindingGWCZ(DirectExchange labInfoExchange, Queue labUrlQueueClientGWCZ) {
//        return BindingBuilder.bind(labUrlQueueClientGWCZ).to(labInfoExchange)
//                .with("url_key");
//    }
}
