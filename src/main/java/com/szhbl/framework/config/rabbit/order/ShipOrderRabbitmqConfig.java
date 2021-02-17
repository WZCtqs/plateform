package com.szhbl.framework.config.rabbit.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author HP
 */
@Configuration
public class ShipOrderRabbitmqConfig {
    /*---------------------------------数字化班列平台托书订单------------------------------------*/
    /**
     * 声明订单动态topic交换机
     * @return
     */
    @Bean
    public TopicExchange orderDynamicTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(ShipOrderRabbitmq.ORDER_DYNAMIC_TOPIC_EXCHANGE).durable(true).build();
    }
    /**
     * 大口岸消息队列
     * @return
     */
    @Bean
    public Queue orderDynamicQueueDKA(){
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_DKA);
    }
    @Bean
    public Binding orderDynamicBindingDKA(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueDKA){
        return BindingBuilder.bind(orderDynamicQueueDKA).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }
    /**
     * 箱管消息队列
     * @return
     */
    @Bean
    public Queue orderDynamicQueueXG(){
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_XG);
    }
    @Bean
    public Binding orderDynamicBindingXG(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueXG){
        return BindingBuilder.bind(orderDynamicQueueXG).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }
    /**
     * 集输消息队列
     * @return
     */
    @Bean
    public Queue orderDynamicQueueJS(){
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_JS);
    }
    @Bean
    public Binding orderDynamicBindingJS(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueJS){
        return BindingBuilder.bind(orderDynamicQueueJS).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }
    /**
     * 箱行亚欧消息队列
     * @return
     */
    @Bean
    public Queue orderDynamicQueueXXYO(){
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_XXYO);
    }
    @Bean
    public Binding orderDynamicBindingXXYO(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueXXYO){
        return BindingBuilder.bind(orderDynamicQueueXXYO).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }
    /**
     * 关务消息队列
     * @return
     */
    @Bean
    public Queue orderDynamicQueueGW(){
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_GW);
    }
    @Bean
    public Binding orderDynamicBindingGW(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueGW){
        return BindingBuilder.bind(orderDynamicQueueGW).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }
    /**
     * 订舱消息队列
     * @return
     */
    @Bean
    public Queue orderDynamicQueueDC() {
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_DC);
    }

    @Bean
    public Binding orderDynamicBindingDC(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueDC) {
        return BindingBuilder.bind(orderDynamicQueueDC).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }

    /**
     * 订舱消息队列
     *
     * @return
     */
    @Bean
    public Queue orderDynamicQueuePXCZ() {
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_PXCZ);
    }

    @Bean
    public Binding orderDynamicBindingPXCZ(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueuePXCZ) {
        return BindingBuilder.bind(orderDynamicQueuePXCZ).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }

    /**
     * 订舱消息队列
     *
     * @return
     */
    @Bean
    public Queue orderDynamicQueueGWCZ() {
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_GWCZ);
    }

    @Bean
    public Binding orderDynamicBindingGWCZ(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueGWCZ) {
        return BindingBuilder.bind(orderDynamicQueueGWCZ).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }

    /**
     * 订舱消息队列
     *
     * @return
     */
    @Bean
    public Queue orderDynamicQueueTEST() {
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_TEST);
    }

//    @Bean
//    public Binding orderDynamicBindingTEST(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueTEST) {
//        return BindingBuilder.bind(orderDynamicQueueTEST).to(orderDynamicTopicExchange)
//                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
//    }

    /**
     * 运踪获取托书信息消息队列
     *
     * @return
     */
    @Bean
    public Queue orderDynamicQueueYZ() {
        return new Queue(ShipOrderRabbitmq.ORDER_DYNAMIC_QUEUE_YZ);
    }

    @Bean
    public Binding orderDynamicBindingYZ(TopicExchange orderDynamicTopicExchange, Queue orderDynamicQueueYZ){
        return BindingBuilder.bind(orderDynamicQueueYZ).to(orderDynamicTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_DYNAMIC_ROUTINGKEY);
    }
    /*---------------------------------数字化班列平台托书订单------------------------------------*/

    /*---------------------------------入货通知书生成通知------------------------------*/
    @Bean
    public TopicExchange orderNoticePDFTopiceXchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(ShipOrderRabbitmq.ORDER_NOTICE_PDF_TOPIC_EXCHANGE).durable(true).build();
    }

    //    @Bean
//    public Queue orderNoticePDFQueueXXYO(){
//        return new Queue(ShipOrderRabbitmq.ORDER_NOTICE_PDF_QUEUE_XXYO);
//    }
    @Bean
    public Queue orderNoticePDFQueueJS() {
        return new Queue(ShipOrderRabbitmq.ORDER_NOTICE_PDF_QUEUE_JS);
    }

    //    @Bean
//    public Binding orderNoticePDFBindingXXYO(TopicExchange orderNoticePDFTopiceXchange, Queue orderNoticePDFQueueXXYO){
//        return BindingBuilder.bind(orderNoticePDFQueueXXYO).to(orderNoticePDFTopiceXchange)
//                .with(ShipOrderRabbitmq.ORDER_NOTICE_PDF_ROUTINGKEY);
//    }
    @Bean
    public Binding orderNoticePDFBindingJS(TopicExchange orderNoticePDFTopiceXchange, Queue orderNoticePDFQueueJS) {
        return BindingBuilder.bind(orderNoticePDFQueueJS).to(orderNoticePDFTopiceXchange)
                .with(ShipOrderRabbitmq.ORDER_NOTICE_PDF_ROUTINGKEY);
    }
    /*---------------------------------入货通知书生成通知------------------------------*/

    /*---------------------------------客户端系统——>平台系统：报关材料------------------------------*/
    @Bean
    public TopicExchange clientTopicExchange() {
        return (TopicExchange) ExchangeBuilder
                .topicExchange(ShipOrderRabbitmq.CLIENT_TOPIC_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue clientCustomerApplyQueueBLPT(){
        return new Queue(ShipOrderRabbitmq.CLIENT_CUSTOMER_APPLE_QUEUE);
    }
    @Bean
    public Binding clientCustomerApplyBindingPX(TopicExchange clientTopicExchange, Queue clientCustomerApplyQueueBLPT){
        return BindingBuilder.bind(clientCustomerApplyQueueBLPT).to(clientTopicExchange)
                .with(ShipOrderRabbitmq.CLIENT_CUSTOMER_APPLE_ROUTINGKEY);
    }
    /*---------------------------------客户端系统——>平台系统：报关材料------------------------------*/

    /*---------------------------------客户端系统——>平台系统：报关材料正本------------------------------*/
    @Bean
    public Queue clientCustomerApplyOriginalQueueBLPT(){
        return new Queue(ShipOrderRabbitmq.CLIENT_CUSTOMER_APPLE_ORIGINAL_QUEUE);
    }
    @Bean
    public Binding clientCustomerApplyOriginalBindingPX(TopicExchange clientTopicExchange, Queue clientCustomerApplyOriginalQueueBLPT){
        return BindingBuilder.bind(clientCustomerApplyOriginalQueueBLPT).to(clientTopicExchange)
                .with(ShipOrderRabbitmq.CLIENT_CUSTOMER_APPLE_ORIGINAL_ROUTINGKEY);
    }
    /*---------------------------------客户端系统——>平台系统：报关材料正本------------------------------*/

    /*---------------------------------客户端系统——>平台系统：提货时间------------------------------*/
    @Bean
    public Queue pickGoodsTimeQueueBLPT() {
        return new Queue(ShipOrderRabbitmq.PICK_GOODSTIME_QUEUE);
    }

    @Bean
    public Binding pickGoodsTimeBindingPX(TopicExchange clientTopicExchange, Queue pickGoodsTimeQueueBLPT) {
        return BindingBuilder.bind(pickGoodsTimeQueueBLPT).to(clientTopicExchange)
                .with(ShipOrderRabbitmq.PICK_GOODSTIME_ROUTINGKEY);
    }

    @Bean
    public Queue pickGoodsTimeQueueXG() {
        return new Queue(ShipOrderRabbitmq.PICK_GOODSTIME_QUEUE_XG);
    }

    @Bean
    public Binding pickGoodsTimeBindingXG(TopicExchange clientTopicExchange, Queue pickGoodsTimeQueueXG) {
        return BindingBuilder.bind(pickGoodsTimeQueueXG).to(clientTopicExchange)
                .with(ShipOrderRabbitmq.PICK_GOODSTIME_ROUTINGKEY);
    }
    /*---------------------------------客户端系统——>平台系统：提货时间------------------------------*/

    /*---------------------------------客户端系统——>平台系统：提单草单------------------------------*/
    @Bean
    public Queue ladingBillDraftQueueBLPT() {
        return new Queue(ShipOrderRabbitmq.LADING_BILL_DRAFT_QUEUE_BLPT);
    }
    @Bean
    public Binding ladingBillDraftBindingBLPT(TopicExchange clientTopicExchange, Queue ladingBillDraftQueueBLPT){
        return BindingBuilder.bind(ladingBillDraftQueueBLPT).to(clientTopicExchange)
                .with(ShipOrderRabbitmq.LADING_BILL_DRAFT_ROUTINGKEY);
    }
    @Bean
    public Queue ladingBillDraftQueueDKA(){
        return new Queue(ShipOrderRabbitmq.LADING_BILL_DRAFT_QUEUE_DKA);
    }
    @Bean
    public Binding ladingBillDraftBindingDKA(TopicExchange clientTopicExchange, Queue ladingBillDraftQueueDKA){
        return BindingBuilder.bind(ladingBillDraftQueueDKA).to(clientTopicExchange)
                .with(ShipOrderRabbitmq.LADING_BILL_DRAFT_ROUTINGKEY);
    }
    /*---------------------------------客户端系统——>平台系统：提单草单------------------------------*/

    /*---------------------------------客户端系统——>平台系统：电放保函------------------------------*/
    @Bean
    public Queue letterGuaranteeQueueBLPT(){
        return new Queue(ShipOrderRabbitmq.LETTER_GUARANTEE_QUEUE_BLPT);
    }
    @Bean
    public Binding letterGuaranteeBindingPX(TopicExchange clientTopicExchange, Queue letterGuaranteeQueueBLPT){
        return BindingBuilder.bind(letterGuaranteeQueueBLPT).to(clientTopicExchange)
                .with(ShipOrderRabbitmq.LETTER_GUARANTEE_ROUTINGKEY);
    }
    /*---------------------------------客户端系统——>平台系统：电放保函------------------------------*/

    /*---------------------------------客户端系统——>平台系统：电放保函------------------------------*/
    @Bean
    public Queue declareCustomsEUQueueBLPT(){
        return new Queue(ShipOrderRabbitmq.DECLARE_CUSTOMS_EU_QUEUE);
    }
    @Bean
    public Binding declareCustomsEUBindingPX(TopicExchange clientTopicExchange, Queue declareCustomsEUQueueBLPT){
        return BindingBuilder.bind(declareCustomsEUQueueBLPT).to(clientTopicExchange)
                .with(ShipOrderRabbitmq.DECLARE_CUSTOMS_EU_ROUTINGKEY);
    }
    /*---------------------------------客户端系统——>平台系统：电放保函------------------------------*/

    /*---------------------------------班列信息------------------------------*/
    /**
     * 声明班列信息topic交换机
     * @return
     */
    @Bean
    public TopicExchange orderClassTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(ShipOrderRabbitmq.ORDER_CLASS_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue classQueueDC(){
        return new Queue(ShipOrderRabbitmq.ORDER_CLASS_QUEUE_DC);
    }
    @Bean
    public Binding classBindingDC(TopicExchange orderClassTopicExchange, Queue classQueueDC){
        return BindingBuilder.bind(classQueueDC).to(orderClassTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_CLASS_ROUTINGKEY);
    }

    @Bean
    public Queue classQueueDKA(){
        return new Queue(ShipOrderRabbitmq.ORDER_CLASS_QUEUE_DKA);
    }
    @Bean
    public Binding classBindingDKA(TopicExchange orderClassTopicExchange, Queue classQueueDKA){
        return BindingBuilder.bind(classQueueDKA).to(orderClassTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_CLASS_ROUTINGKEY);
    }

    @Bean
    public Queue classQueueXG(){
        return new Queue(ShipOrderRabbitmq.ORDER_CLASS_QUEUE_XG);
    }
    @Bean
    public Binding classBindingXG(TopicExchange orderClassTopicExchange, Queue classQueueXG){
        return BindingBuilder.bind(classQueueXG).to(orderClassTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_CLASS_ROUTINGKEY);
    }

    @Bean
    public Queue classQueueGWCZ(){
        return new Queue(ShipOrderRabbitmq.ORDER_CLASS_QUEUE_GWCZ);
    }

    @Bean
    public Binding classBindingGWCZ(TopicExchange orderClassTopicExchange, Queue classQueueGWCZ) {
        return BindingBuilder.bind(classQueueGWCZ).to(orderClassTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_CLASS_ROUTINGKEY);
    }

    @Bean
    public Queue classQueuePX() {
        return new Queue(ShipOrderRabbitmq.ORDER_CLASS_QUEUE_PX);
    }
//    @Bean
//    public Binding classBindingPX(TopicExchange orderClassTopicExchange, Queue classQueuePX){
//        return BindingBuilder.bind(classQueuePX).to(orderClassTopicExchange)
//                .with(ShipOrderRabbitmq.ORDER_CLASS_ROUTINGKEY);
//    }

    @Bean
    public Queue classQueueGW() {
        return new Queue(ShipOrderRabbitmq.ORDER_CLASS_QUEUE_GW);
    }

    @Bean
    public Binding classBindingGW(TopicExchange orderClassTopicExchange, Queue classQueueGW) {
        return BindingBuilder.bind(classQueueGW).to(orderClassTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_CLASS_ROUTINGKEY);
    }
    /*---------------------------------班列信息------------------------------*/

    /**
     * 托书提货时间topic交换机
     * @return
     */
    @Bean
    public TopicExchange orderPicktimeTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(ShipOrderRabbitmq.ORDER_PICKTIME_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue picktimeQueueJS(){
        return new Queue(ShipOrderRabbitmq.ORDER_PICKTIME_QUEUE_JS);
    }
    @Bean
    public Binding picktimeBindingJS(TopicExchange orderPicktimeTopicExchange, Queue picktimeQueueJS){
        return BindingBuilder.bind(picktimeQueueJS).to(orderPicktimeTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_PICKTIME_ROUTINGKEY);
    }

    @Bean
    public Queue picktimeQueueXXYO(){
        return new Queue(ShipOrderRabbitmq.ORDER_PICKTIME_QUEUE_XXYO);
    }
    @Bean
    public Binding picktimeBindingXXYO(TopicExchange orderPicktimeTopicExchange, Queue picktimeQueueXXYO){
        return BindingBuilder.bind(picktimeQueueXXYO).to(orderPicktimeTopicExchange)
                .with(ShipOrderRabbitmq.ORDER_PICKTIME_ROUTINGKEY);
    }
    /*---------------------------------托书提货时间------------------------------*/

    @Bean
    public TopicExchange blptDocmessageTopicExchange(){
        return (TopicExchange) ExchangeBuilder
                .topicExchange(ShipOrderRabbitmq.BLPT_DOCMESSAGE_TOPIC_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue blptDocmessageXG(){
        return new Queue(ShipOrderRabbitmq.BLPT_DOCMESSAGE_XG);
    }
    @Bean
    public Queue blptDocmessageXXYO(){
        return new Queue(ShipOrderRabbitmq.BLPT_DOCMESSAGE_XXYO);
    }
    @Bean
    public Queue blptDocmessageJS(){
        return new Queue(ShipOrderRabbitmq.BLPT_DOCMESSAGE_JS);
    }
    @Bean
    public Queue blptDocmessageDKA(){
        return new Queue(ShipOrderRabbitmq.BLPT_DOCMESSAGE_DKA);
    }
    @Bean
    public Queue blptDocmessageGW(){
        return new Queue(ShipOrderRabbitmq.BLPT_DOCMESSAGE_GW);
    }
    @Bean
    public Queue blptDocmessagePX(){
        return new Queue(ShipOrderRabbitmq.BLPT_DOCMESSAGE_PX);
    }
    @Bean
    public Queue blptDocmessageGWCZ(){
        return new Queue(ShipOrderRabbitmq.BLPT_DOCMESSAGE_GWCZ);
    }
    @Bean
    public Binding blptDocmessageBindingXG(TopicExchange blptDocmessageTopicExchange, Queue blptDocmessageXG){
        return BindingBuilder.bind(blptDocmessageXG).to(blptDocmessageTopicExchange)
                .with(ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_XG);
    }
    @Bean
    public Binding blptDocmessageBindingXXYO(TopicExchange blptDocmessageTopicExchange, Queue blptDocmessageXXYO){
        return BindingBuilder.bind(blptDocmessageXXYO).to(blptDocmessageTopicExchange)
                .with(ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_XXYO);
    }
    @Bean
    public Binding blptDocmessageBindingJS(TopicExchange blptDocmessageTopicExchange, Queue blptDocmessageJS){
        return BindingBuilder.bind(blptDocmessageJS).to(blptDocmessageTopicExchange)
                .with(ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_JS);
    }
    @Bean
    public Binding blptDocmessageBindingDKA(TopicExchange blptDocmessageTopicExchange, Queue blptDocmessageDKA){
        return BindingBuilder.bind(blptDocmessageDKA).to(blptDocmessageTopicExchange)
                .with(ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_DKA);
    }
    @Bean
    public Binding blptDocmessageBindingGW(TopicExchange blptDocmessageTopicExchange, Queue blptDocmessageGW){
        return BindingBuilder.bind(blptDocmessageGW).to(blptDocmessageTopicExchange)
                .with(ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_GW);
    }
    @Bean
    public Binding blptDocmessageBindingGWCZ(TopicExchange blptDocmessageTopicExchange, Queue blptDocmessageGWCZ){
        return BindingBuilder.bind(blptDocmessageGWCZ).to(blptDocmessageTopicExchange)
                .with(ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_GWCZ);
    }
    @Bean
    public Binding blptDocmessageBindingPX(TopicExchange blptDocmessageTopicExchange, Queue blptDocmessagePX){
        return BindingBuilder.bind(blptDocmessagePX).to(blptDocmessageTopicExchange)
                .with(ShipOrderRabbitmq.BLPT_DOCMESSAGE_ROUTINGKEY_PX);
    }
    /* 消息提醒消息队列设置 */
}
