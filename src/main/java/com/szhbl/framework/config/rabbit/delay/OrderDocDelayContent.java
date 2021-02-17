package com.szhbl.framework.config.rabbit.delay;

public class OrderDocDelayContent {
    /**
     * ttl(延时)消息交换机名称
     */
    public static final String DOC_ORDER_TTL_EXCHANGE = "doc.order.ttl.exchange";

    /**
     * ttl(延时)路由key
     */
    public static final String DOC_ORDER_TTL_ROUTINGKEY = "doc.order.ttl.routingkey";

    /**
     * ttl(延时)消息队列名称
     */
    public static final String DOC_ORDER_TTL_QUEUE = "doc.order.ttl.queue";


    /**
     * 消息二次转发的路由key
     */
    public static final String DOC_ORDER_DLX_ROUTINGKEY = "doc.order.dlx.routingkey";

    /**
     * 消息二次转发的队列名称
     */
    public static final String DOC_ORDER_DLX_QUEUE = "doc.order.dlx.queue";
}
