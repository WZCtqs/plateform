package com.szhbl.framework.config.rabbit.order;


/**
 * @author HP
 */
public class ShipOrderRabbitmq {

    /*-------------------------------托书订单发送消息队列配置--------------------------------*/
    /**
     * 订单动态交换机
     */
    public static final String ORDER_DYNAMIC_TOPIC_EXCHANGE = "order.dynamic.topic.exchange";
    /**
     * 订单动态 绑定路由key
     */
    public static final String ORDER_DYNAMIC_ROUTINGKEY = "order.dynamic.*";
    /**
     * 消息队列——箱管系统
     */
    public static final String ORDER_DYNAMIC_QUEUE_XG = "order_dynamic_queue_xg";
    /**
     * 消息队列——大口岸系统
     */
    public static final String ORDER_DYNAMIC_QUEUE_DKA = "order_dynamic_queue_dka";
    /**
     * 消息队列——集输系统
     */
    public static final String ORDER_DYNAMIC_QUEUE_JS = "order_dynamic_queue_js";
    /**
     * 消息队列——箱型亚欧系统
     */
    public static final String ORDER_DYNAMIC_QUEUE_XXYO = "order_dynamic_queue_xxyo";
    /**
     * 消息队列——关务系统
     */
    public static final String ORDER_DYNAMIC_QUEUE_GW = "order_dynamic_queue_gw";
    /**
     * 消息队列——订舱系统
     */
    public static final String ORDER_DYNAMIC_QUEUE_DC = "order_dynamic_queue_dcxt";
    /**
     * 消息队列——国外场站
     */
    public static final String ORDER_DYNAMIC_QUEUE_GWCZ = "order_dynamic_queue_gwcz";
    /**
     * 消息队列——拼箱场站系统
     */
    public static final String ORDER_DYNAMIC_QUEUE_PXCZ = "order_dynamic_queue_pxcz";
    /**
     * 消息队列——测试
     */
    public static final String ORDER_DYNAMIC_QUEUE_TEST = "order_dynamic_queue_js_test";
    /**
     * 消息队列——运踪使用
     */
    public static final String ORDER_DYNAMIC_QUEUE_YZ = "order_dynamic_queue_yz";

    /*-----------------------------------班列信息---------------------------------------*/
    /**
     * 班列信息动态交换机
     */
    public static final String ORDER_CLASS_TOPIC_EXCHANGE = "order.class.topic.exchange";
    /**
     * 班列信息 绑定路由key
     */
    public static final String ORDER_CLASS_ROUTINGKEY = "order.class.message";
    /*订舱系统*/
    public static final String ORDER_CLASS_QUEUE_DC = "order_class_queue_dc";
    /*大口岸系统*/
    public static final String ORDER_CLASS_QUEUE_DKA = "order_class_queue_dka";
    /*箱管系统*/
    public static final String ORDER_CLASS_QUEUE_XG = "order_class_queue_xg";
    /*国外场站系统*/
    public static final String ORDER_CLASS_QUEUE_GWCZ = "order_class_queue_gwcz";
    /*拼箱场站系统*/
    public static final String ORDER_CLASS_QUEUE_PX = "order_class_queue_px";
    /*关务系统*/
    public static final String ORDER_CLASS_QUEUE_GW = "order_class_queue_gw";
    /*-----------------------------------班列信息---------------------------------------*/

    /*-----------------------------------提货时间审核---------------------------------------*/
    //提货时间交换机
    public static final String ORDER_PICKTIME_TOPIC_EXCHANGE = "order.picktime.topic.exchange";
    //订单动态 绑定路由key
    public static final String ORDER_PICKTIME_ROUTINGKEY = "order.picktime.*";
    //集疏部队列
    public static final String ORDER_PICKTIME_QUEUE_JS = "order_picktime_queue_js";
    //箱型亚欧队列
    public static final String ORDER_PICKTIME_QUEUE_XXYO = "order_picktime_queue_xxyo";
    /*-----------------------------------提货时间审核---------------------------------------*/

    /*---------------------------------入货通知书生成通知------------------------------*/
    /**
     * 箱型亚欧系统
     */
    public static final String ORDER_NOTICE_PDF_QUEUE_XXYO = "order_notice_pdf_queue_xxyo";
    /**
     * 集输系统
     */
    public static final String ORDER_NOTICE_PDF_QUEUE_JS = "order_notice_pdf_queue_js";
    /**
     * 交换机
     */
    public static final String ORDER_NOTICE_PDF_TOPIC_EXCHANGE = "order.notice.pdf.topic.exchange";
    /**
     * 绑定路由key
     */
    public static final String ORDER_NOTICE_PDF_ROUTINGKEY = "order.notice.pdf.create";
    /*---------------------------------入货通知书生成通知------------------------------*/

    /*---------------------------------客户端系统——>平台系统：报关材料-----------------------------*/
    public static final String CLIENT_TOPIC_EXCHANGE = "client.topic.exchange";

    public static final String CLIENT_CUSTOMER_APPLE_ROUTINGKEY = "client.customs.apply.*";

    public static final String CLIENT_CUSTOMER_APPLE_QUEUE = "client_customs_apply_queue";
    /*---------------------------------客户端系统——>平台系统：报关材料-----------------------------*/

    /*---------------------------------客户端系统——>平台系统：报关材料正本-----------------------------*/
    public static final String CLIENT_CUSTOMER_APPLE_ORIGINAL_ROUTINGKEY = "client.customs.apply.original";

    public static final String CLIENT_CUSTOMER_APPLE_ORIGINAL_QUEUE = "client_customs_apply_original_queue";
    /*---------------------------------客户端系统——>平台系统：报关材料正本-----------------------------*/

    /*---------------------------------客户端系统——>平台系统 提货时间-----------------------------*/
    public static final String PICK_GOODSTIME_ROUTINGKEY = "pick.goodstime.*";

    public static final String PICK_GOODSTIME_QUEUE = "pick_goodstime_queue";

    public static final String PICK_GOODSTIME_QUEUE_XG = "pick_goodstime_queue_xg";
    /*---------------------------------客户端系统——>平台系统：提货时间-----------------------------*/

    /*---------------------------------客户端系统——>平台系统、大口岸系统 提单草单填写-----------------------------*/
    public static final String LADING_BILL_DRAFT_ROUTINGKEY = "lading.bill.draft.*";

    public static final String LADING_BILL_DRAFT_QUEUE_BLPT = "lading_bill_draft_queue_blpt";

    public static final String LADING_BILL_DRAFT_QUEUE_DKA = "lading_bill_draft_queue_dka";
    /*---------------------------------客户端系统——>平台系统、大口岸系统 提单草单填写-----------------------------*/

    /*---------------------------------客户端系统——>平台系统  电放保函-----------------------------*/
    public static final String LETTER_GUARANTEE_ROUTINGKEY = "letter.guarantee.draft.*";

    public static final String LETTER_GUARANTEE_QUEUE_BLPT = "letter_guarantee_queue_blpt";
    /*---------------------------------客户端系统——>平台系统  电放保函-----------------------------*/

    /*---------------------------------客户端系统——>平台系统：报关材料-----------------------------*/
    public static final String DECLARE_CUSTOMS_EU_ROUTINGKEY = "declare.customs.eu.*";

    public static final String DECLARE_CUSTOMS_EU_QUEUE = "declare_customs_eu_queue";
    /*---------------------------------客户端系统——>平台系统：报关材料-----------------------------*/

    /* 消息提醒消息队列设置 */
    public static final String BLPT_DOCMESSAGE_TOPIC_EXCHANGE = "blpt.docmessage.topic.exchange";

    public static final String BLPT_DOCMESSAGE_XG = "blpt_docmessage_xg";
    public static final String BLPT_DOCMESSAGE_XXYO = "blpt_docmessage_xxyo";
    public static final String BLPT_DOCMESSAGE_GWCZ = "blpt_docmessage_gwcz";
    public static final String BLPT_DOCMESSAGE_GW = "blpt_docmessage_gw";
    public static final String BLPT_DOCMESSAGE_PX = "blpt_docmessage_px";
    public static final String BLPT_DOCMESSAGE_JS = "blpt_docmessage_js";
    public static final String BLPT_DOCMESSAGE_DKA = "blpt_docmessage_dka";

    public static final String BLPT_DOCMESSAGE_ROUTINGKEY_XG = "blpt.docmessage.xg";
    public static final String BLPT_DOCMESSAGE_ROUTINGKEY_XXYO = "blpt.docmessage.xxyo";
    public static final String BLPT_DOCMESSAGE_ROUTINGKEY_GWCZ = "blpt.docmessage.gwcz";
    public static final String BLPT_DOCMESSAGE_ROUTINGKEY_GW = "blpt.docmessage.gw";
    public static final String BLPT_DOCMESSAGE_ROUTINGKEY_PX = "blpt.docmessage.px";
    public static final String BLPT_DOCMESSAGE_ROUTINGKEY_JS = "blpt.docmessage.js";
    public static final String BLPT_DOCMESSAGE_ROUTINGKEY_DKA = "blpt.docmessage.dka";
    /* 消息提醒消息队列设置 */
}
