package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HP
 */
@Configuration
public class PXCZSystemRabbitmqConfig {
    /*---------------------------------拼箱场站系统——>平台：装箱方案-----------------------------*/
    public static final String PX_SYSTEM_FILES_TOPIC_EXCHANGE = "px.system.files.topic.exchange";
    // 装箱方案
    public static final String PX_SYSTEM_BOXINGSCHEME_QUEUE_BLPT = "px_system_boxingScheme_queue_blpt";
    public static final String PX_SYSTEM_BOXINGSCHEME_QUEUE_GW = "px_system_boxingScheme_queue_gw";
    public static final String PX_SYSTEM_BOXINGSCHEME_ROUTINGKEY = "px.system.files.boxingScheme";

    // 拼箱到货信息
    public static final String PX_SYSTEM_ARRIVALGOODS_QUEUE_BLPT = "px_system_arrivalGoods_queue_blpt";
    public static final String PX_SYSTEM_ARRIVALGOODS_QUEUE_GWCZ = "px_system_arrivalGoods_queue_gwcz";
    public static final String PX_SYSTEM_ARRIVALGOODS_QUEUE_GW = "px_system_arrivalGoods_queue_gw";
    public static final String PX_SYSTEM_ARRIVALGOODS_ROUTINGKEY = "px.system.files.arrivalGoods";

    //boxing_list_size_file 装柜清单（件重尺）
    public static final String PX_SYSTEM_BOXINGLISTSIZE_QUEUE_BLPT = "px_system_boxingListSize_queue_blpt";
    public static final String PX_SYSTEM_BOXINGLISTSIZE_QUEUE_DKA = "px_system_boxingListSize_queue_dka";
    public static final String PX_SYSTEM_BOXINGLISTSIZE_QUEUE_GW = "px_system_boxingListSize_queue_gw";
    public static final String PX_SYSTEM_BOXINGLISTSIZE_QUEUE_GWCZ = "px_system_boxingListSize_queue_gwcz";
    public static final String PX_SYSTEM_BOXINGLISTSIZE_QUEUE_XG = "px_system_boxingListSize_queue_xg";
    public static final String PX_SYSTEM_BOXINGLISTSIZE_QUEUE_JS = "px_system_boxingListSize_queue_js";
    public static final String PX_SYSTEM_BOXINGLISTSIZE_QUEUE_XXYO = "px_system_boxingListSize_queue_xxyo";
    public static final String PX_SYSTEM_BOXINGLISTSIZE_ROUTINGKEY = "px.system.files.boxingListSize";

    /*集装箱装箱体积*/
    public static final String PX_SYSTEM_CONTAINERVOLUME_QUEUE_DKA = "px_system_containervolume_queue_dka";
    public static final String PX_SYSTEM_CONTAINERVOLUME_ROUTINGKEY = "px.system.files.containervolume";

    //boxing_list_file 装柜清单
    public static final String PX_SYSTEM_BOXINGLIST_QUEUE_BLPT = "px_system_boxingList_queue_blpt";
    public static final String PX_SYSTEM_BOXINGLIST_ROUTINGKEY = "px.system.files.boxingList";

    //yard_loaded_in_file 堆场重箱进站表
    public static final String PX_SYSTEM_YARDLOADEDIN_QUEUE_BLPT = "px_system_yardLoadedin_queue_blpt";
    public static final String PX_SYSTEM_YARDLOADEDIN_QUEUE_GW = "px_system_yardLoadedin_queue_gw";
    public static final String PX_SYSTEM_YARDLOADEDIN_QUEUE_DKA = "px_system_yardLoadedin_queue_dka";
    public static final String PX_SYSTEM_YARDLOADEDIN_QUEUE_XG = "px_system_yardLoadedin_queue_xg";
    public static final String PX_SYSTEM_YARDLOADEDIN_QUEUE_JS = "px_system_yardLoadedin_queue_js";
    public static final String PX_SYSTEM_YARDLOADEDIN_QUEUE_GWCZ = "px_system_yardLoadedin_queue_gwcz";
    public static final String PX_SYSTEM_YARDLOADEDIN_ROUTINGKEY = "px.system.files.yardLoadedin";

    // 重箱进站表-拼箱
    public static final String PX_SYSTEM_YARDLOADEDIN_PX_QUEUE_BLPT = "px_system_yardLoadedin_px_queue_blpt";
    public static final String PX_SYSTEM_YARDLOADEDIN_PX_QUEUE_GW = "px_system_yardLoadedin_px_queue_gw";
    public static final String PX_SYSTEM_YARDLOADEDIN_PX_ROUTINGKEY = "px.system.files.yardLoadedinPx";


    // 拼箱出入库表
    public static final String PX_SYSTEM_PXGOODSINOUT_QUEUE_BLPT = "px_system_pxGoodsInout_queue_blpt";
    public static final String PX_SYSTEM_PXGOODSINOUT_ROUTINGKEY = "px.system.files.pxGoodsInout";

    // 接货照
    public static final String PX_SYSTEM_GETGOODSPHOTO_QUEUE_BLPT = "px_system_getGoodsPhoto_queue_blpt";
    public static final String PX_SYSTEM_GETGOODSPHOTO_QUEUE_GWCZ = "px_system_getGoodsPhoto_queue_gwcz";
    public static final String PX_SYSTEM_GETGOODSPHOTO_QUEUE_JS = "px_system_getGoodsPhoto_queue_js";
    public static final String PX_SYSTEM_GETGOODSPHOTO_ROUTINGKEY = "px.system.files.getGoodsPhoto";

    // 装箱照
    public static final String PX_SYSTEM_BOXINGPHOTO_QUEUE_BLPT = "px_system_boxingPhoto_queue_blpt";
    public static final String PX_SYSTEM_BOXINGPHOTO_QUEUE_GWCZ = "px_system_boxingPhoto_queue_gwcz";
    public static final String PX_SYSTEM_BOXINGPHOTO_QUEUE_DKA = "px_system_boxingPhoto_queue_dka";
//    public static final String PX_SYSTEM_BOXINGPHOTO_QUEUE_XXYO = "px_system_boxingPhoto_queue_xxyo";
    public static final String PX_SYSTEM_BOXINGPHOTO_ROUTINGKEY = "px.system.files.boxingPhoto";

    // 拆箱照
    public static final String PX_SYSTEM_UNBOXINGPHOTO_QUEUE_BLPT = "px_system_unboxingPhoto_queue_blpt";
    public static final String PX_SYSTEM_UNBOXINGPHOTO_ROUTINGKEY = "px.system.files.unboxingPhoto";

    // 装箱方案
    @Bean
    public TopicExchange pxBoxingSchemeTopicExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(PX_SYSTEM_FILES_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue pxContainervolumeQueueDKA() {
        return new Queue(PX_SYSTEM_CONTAINERVOLUME_QUEUE_DKA);
    }

//    @Bean
//    public Binding pxContainervolumeBindingDKA(TopicExchange pxBoxingSchemeTopicExchange, Queue pxContainervolumeQueueDKA) {
//        return BindingBuilder.bind(pxContainervolumeQueueDKA).to(pxBoxingSchemeTopicExchange)
//                .with(PX_SYSTEM_CONTAINERVOLUME_ROUTINGKEY);
//    }

    @Bean
    public Queue pxBoxingSchemeQueueBLPT() {
        return new Queue(PX_SYSTEM_BOXINGSCHEME_QUEUE_BLPT);
    }

    @Bean
    public Binding pxBoxingSchemeBindingDKA(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingSchemeQueueBLPT) {
        return BindingBuilder.bind(pxBoxingSchemeQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGSCHEME_ROUTINGKEY);
    }

    @Bean
    public Queue pxBoxingSchemeQueueGW() {
        return new Queue(PX_SYSTEM_BOXINGSCHEME_QUEUE_GW);
    }

    @Bean
    public Binding pxBoxingSchemeBindingGW(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingSchemeQueueGW) {
        return BindingBuilder.bind(pxBoxingSchemeQueueGW).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGSCHEME_ROUTINGKEY);
    }

    // 拼箱到货信息
    @Bean
    public Queue pxArrivalGoodsQueueBLPT(){
        return new Queue(PX_SYSTEM_ARRIVALGOODS_QUEUE_BLPT);
    }
    @Bean
    public Binding pxArrivalGoodsBindingDKA(TopicExchange pxBoxingSchemeTopicExchange, Queue pxArrivalGoodsQueueBLPT){
        return BindingBuilder.bind(pxArrivalGoodsQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_ARRIVALGOODS_ROUTINGKEY);
    }
    @Bean
    public Queue pxArrivalGoodsQueueGWCZ(){
        return new Queue(PX_SYSTEM_ARRIVALGOODS_QUEUE_GWCZ);
    }
    @Bean
    public Binding pxArrivalGoodsBindingGWCZ(TopicExchange pxBoxingSchemeTopicExchange, Queue pxArrivalGoodsQueueGWCZ){
        return BindingBuilder.bind(pxArrivalGoodsQueueGWCZ).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_ARRIVALGOODS_ROUTINGKEY);
    }
    @Bean
    public Queue pxArrivalGoodsQueueGW(){
        return new Queue(PX_SYSTEM_ARRIVALGOODS_QUEUE_GW);
    }
    @Bean
    public Binding pxArrivalGoodsBindingGW(TopicExchange pxBoxingSchemeTopicExchange, Queue pxArrivalGoodsQueueGW){
        return BindingBuilder.bind(pxArrivalGoodsQueueGW).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_ARRIVALGOODS_ROUTINGKEY);
    }
    //boxing_list_size_file 装柜清单（件重尺）
    @Bean
    public Queue pxBoxingListSizeQueueBLPT(){
        return new Queue(PX_SYSTEM_BOXINGLISTSIZE_QUEUE_BLPT);
    }
    @Bean
    public Binding pxBoxingListSizeBindingBLPT(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingListSizeQueueBLPT){
        return BindingBuilder.bind(pxBoxingListSizeQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGLISTSIZE_ROUTINGKEY);
    }

    @Bean
    public Queue pxBoxingListSizeQueueDKA(){
        return new Queue(PX_SYSTEM_BOXINGLISTSIZE_QUEUE_DKA);
    }
    @Bean
    public Binding pxBoxingListSizeBindingDKA(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingListSizeQueueDKA){
        return BindingBuilder.bind(pxBoxingListSizeQueueDKA).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGLISTSIZE_ROUTINGKEY);
    }
    @Bean
    public Queue pxBoxingListSizeQueueGW(){
        return new Queue(PX_SYSTEM_BOXINGLISTSIZE_QUEUE_GW);
    }
    @Bean
    public Binding pxBoxingListSizeBindingGW(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingListSizeQueueGW){
        return BindingBuilder.bind(pxBoxingListSizeQueueGW).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGLISTSIZE_ROUTINGKEY);
    }

    @Bean
    public Queue pxBoxingListSizeQueueGWCZ() {
        return new Queue(PX_SYSTEM_BOXINGLISTSIZE_QUEUE_GWCZ);
    }

    @Bean
    public Binding pxBoxingListSizeBindingGWCZ(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingListSizeQueueGWCZ) {
        return BindingBuilder.bind(pxBoxingListSizeQueueGWCZ).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGLISTSIZE_ROUTINGKEY);
    }

    @Bean
    public Queue pxBoxingListSizeQueueXG() {
        return new Queue(PX_SYSTEM_BOXINGLISTSIZE_QUEUE_XG);
    }

    @Bean
    public Binding pxBoxingListSizeBindingXG(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingListSizeQueueXG) {
        return BindingBuilder.bind(pxBoxingListSizeQueueXG).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGLISTSIZE_ROUTINGKEY);
    }

    @Bean
    public Queue pxBoxingListSizeQueueJS() {
        return new Queue(PX_SYSTEM_BOXINGLISTSIZE_QUEUE_JS);
    }

    @Bean
    public Binding pxBoxingListSizeBindingJS(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingListSizeQueueJS) {
        return BindingBuilder.bind(pxBoxingListSizeQueueJS).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGLISTSIZE_ROUTINGKEY);
    }

    //boxing_list_file 装柜清单
    @Bean
    public Queue pxBoxingListQueueBLPT() {
        return new Queue(PX_SYSTEM_BOXINGLIST_QUEUE_BLPT);
    }

    @Bean
    public Binding pxBoxingListBindingDKA(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingListQueueBLPT) {
        return BindingBuilder.bind(pxBoxingListQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGLIST_ROUTINGKEY);
    }

    //yard_loaded_in_file 堆场重箱进站表
    @Bean
    public Queue pxYardLoadedinQueueBLPT() {
        return new Queue(PX_SYSTEM_YARDLOADEDIN_QUEUE_BLPT);
    }

    @Bean
    public Binding pxYardLoadedinBindingBLPT(TopicExchange pxBoxingSchemeTopicExchange, Queue pxYardLoadedinQueueBLPT) {
        return BindingBuilder.bind(pxYardLoadedinQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_YARDLOADEDIN_ROUTINGKEY);
    }

    @Bean
    public Queue pxYardLoadedinQueueGW() {
        return new Queue(PX_SYSTEM_YARDLOADEDIN_QUEUE_GW);
    }

    @Bean
    public Binding pxYardLoadedinBindingGW(TopicExchange pxBoxingSchemeTopicExchange, Queue pxYardLoadedinQueueGW) {
        return BindingBuilder.bind(pxYardLoadedinQueueGW).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_YARDLOADEDIN_ROUTINGKEY);
    }

    @Bean
    public Queue pxYardLoadedinQueueDKA() {
        return new Queue(PX_SYSTEM_YARDLOADEDIN_QUEUE_DKA);
    }

    @Bean
    public Binding pxYardLoadedinBindingDKA(TopicExchange pxBoxingSchemeTopicExchange, Queue pxYardLoadedinQueueDKA) {
        return BindingBuilder.bind(pxYardLoadedinQueueDKA).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_YARDLOADEDIN_ROUTINGKEY);
    }

    @Bean
    public Queue pxYardLoadedinQueueXG() {
        return new Queue(PX_SYSTEM_YARDLOADEDIN_QUEUE_XG);
    }

    @Bean
    public Binding pxYardLoadedinBindingXG(TopicExchange pxBoxingSchemeTopicExchange, Queue pxYardLoadedinQueueXG) {
        return BindingBuilder.bind(pxYardLoadedinQueueXG).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_YARDLOADEDIN_ROUTINGKEY);
    }

    @Bean
    public Queue pxYardLoadedinQueueJS() {
        return new Queue(PX_SYSTEM_YARDLOADEDIN_QUEUE_JS);
    }

    @Bean
    public Binding pxYardLoadedinBindingJS(TopicExchange pxBoxingSchemeTopicExchange, Queue pxYardLoadedinQueueJS) {
        return BindingBuilder.bind(pxYardLoadedinQueueJS).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_YARDLOADEDIN_ROUTINGKEY);
    }

    @Bean
    public Queue pxYardLoadedinQueueGWCZ() {
        return new Queue(PX_SYSTEM_YARDLOADEDIN_QUEUE_GWCZ);
    }

    @Bean
    public Binding pxYardLoadedinBindingGWCZ(TopicExchange pxBoxingSchemeTopicExchange, Queue pxYardLoadedinQueueGWCZ) {
        return BindingBuilder.bind(pxYardLoadedinQueueGWCZ).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_YARDLOADEDIN_ROUTINGKEY);
    }

    @Bean
    public Queue pxGoodsInoutQueueBLPT() {
        return new Queue(PX_SYSTEM_PXGOODSINOUT_QUEUE_BLPT);
    }

    @Bean
    public Binding gwPxGoodsInoutBinding(TopicExchange pxBoxingSchemeTopicExchange, Queue pxGoodsInoutQueueBLPT) {
        return BindingBuilder.bind(pxGoodsInoutQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_PXGOODSINOUT_ROUTINGKEY);
    }

    @Bean
    public Queue pxGetGoodsPhotoQueueBLPT(){
        return new Queue(PX_SYSTEM_GETGOODSPHOTO_QUEUE_BLPT);
    }

    @Bean
    public Binding gwPxGetGoodsPhotoBinding(TopicExchange pxBoxingSchemeTopicExchange, Queue pxGetGoodsPhotoQueueBLPT){
        return BindingBuilder.bind(pxGetGoodsPhotoQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_GETGOODSPHOTO_ROUTINGKEY);
    }

    @Bean
    public Queue pxGetGoodsPhotoQueueGWCZ() {
        return new Queue(PX_SYSTEM_GETGOODSPHOTO_QUEUE_GWCZ);
    }

    @Bean
    public Binding gwPxGetGoodsPhotoBindingGWCZ(TopicExchange pxBoxingSchemeTopicExchange, Queue pxGetGoodsPhotoQueueGWCZ) {
        return BindingBuilder.bind(pxGetGoodsPhotoQueueGWCZ).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_GETGOODSPHOTO_ROUTINGKEY);
    }

    @Bean
    public Queue pxGetGoodsPhotoQueueJS() {
        return new Queue(PX_SYSTEM_GETGOODSPHOTO_QUEUE_JS);
    }

    @Bean
    public Binding gwPxGetGoodsPhotoBindingJS(TopicExchange pxBoxingSchemeTopicExchange, Queue pxGetGoodsPhotoQueueJS) {
        return BindingBuilder.bind(pxGetGoodsPhotoQueueJS).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_GETGOODSPHOTO_ROUTINGKEY);
    }

    @Bean
    public Queue pxBoxingPhotoQueueBLPT() {
        return new Queue(PX_SYSTEM_BOXINGPHOTO_QUEUE_BLPT);
    }

    @Bean
    public Binding gwPxBoxingPhotoBinding(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingPhotoQueueBLPT) {
        return BindingBuilder.bind(pxBoxingPhotoQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGPHOTO_ROUTINGKEY);
    }

    @Bean
    public Queue pxBoxingPhotoQueueGWCZ() {
        return new Queue(PX_SYSTEM_BOXINGPHOTO_QUEUE_GWCZ);
    }
//
//    @Bean
//    public Binding gwPxBoxingPhotoBindingGWCZ(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingPhotoQueueGWCZ){
//        return BindingBuilder.bind(pxBoxingPhotoQueueGWCZ).to(pxBoxingSchemeTopicExchange)
//                .with(PX_SYSTEM_BOXINGPHOTO_ROUTINGKEY);
//    }

    @Bean
    public Queue pxBoxingPhotoQueueDKA() {
        return new Queue(PX_SYSTEM_BOXINGPHOTO_QUEUE_DKA);
    }

    @Bean
    public Binding gwPxBoxingPhotoBindingDKA(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingPhotoQueueDKA) {
        return BindingBuilder.bind(pxBoxingPhotoQueueDKA).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_BOXINGPHOTO_ROUTINGKEY);
    }

//    @Bean
//    public Queue pxBoxingPhotoQueueXXYO() {
//        return new Queue(PX_SYSTEM_BOXINGPHOTO_QUEUE_XXYO);
//    }

//    @Bean
//    public Binding gwPxBoxingPhotoBindingXXYO(TopicExchange pxBoxingSchemeTopicExchange, Queue pxBoxingPhotoQueueXXYO) {
//        return BindingBuilder.bind(pxBoxingPhotoQueueXXYO).to(pxBoxingSchemeTopicExchange)
//                .with(PX_SYSTEM_BOXINGPHOTO_ROUTINGKEY);
//    }

    @Bean
    public Queue pxUNBoxingPhotoQueueBLPT() {
        return new Queue(PX_SYSTEM_UNBOXINGPHOTO_QUEUE_BLPT);
    }

    @Bean
    public Binding gwPxUNBoxingPhotoBinding(TopicExchange pxBoxingSchemeTopicExchange, Queue pxUNBoxingPhotoQueueBLPT) {
        return BindingBuilder.bind(pxUNBoxingPhotoQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_UNBOXINGPHOTO_ROUTINGKEY);
    }
    /*---------------------------------拼箱场站装箱方案消息队列------------------------------*/

    /*重箱进站表-拼箱*/
    @Bean
    public Queue pxYardLoadedinPXQueueGW() {
        return new Queue(PX_SYSTEM_YARDLOADEDIN_PX_QUEUE_GW);
    }

    @Bean
    public Binding pxYardLoadedinPXBindingGW(TopicExchange pxBoxingSchemeTopicExchange, Queue pxYardLoadedinPXQueueGW) {
        return BindingBuilder.bind(pxYardLoadedinPXQueueGW).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_YARDLOADEDIN_PX_ROUTINGKEY);
    }

    @Bean
    public Queue pxYardLoadedinPXQueueBLPT() {
        return new Queue(PX_SYSTEM_YARDLOADEDIN_PX_QUEUE_BLPT);
    }

    @Bean
    public Binding pxYardLoadedinPXBindingBLPT(TopicExchange pxBoxingSchemeTopicExchange, Queue pxYardLoadedinPXQueueBLPT) {
        return BindingBuilder.bind(pxYardLoadedinPXQueueBLPT).to(pxBoxingSchemeTopicExchange)
                .with(PX_SYSTEM_YARDLOADEDIN_PX_ROUTINGKEY);
    }
}
