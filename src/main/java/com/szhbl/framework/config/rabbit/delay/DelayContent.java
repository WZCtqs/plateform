package com.szhbl.framework.config.rabbit.delay;

public class DelayContent {
    /**
     * ttl(延时)消息交换机名称
     */
    public static final String ORDER_TTL_EXCHANGE = "order.ttl.exchange";

    /**
     * ttl(延时)路由key
     */
    public static final String ORDER_TTL_ROUTINGKEY = "order.ttl.routingkey";

    /**
     * ttl(延时)消息队列名称
     */
    public static final String ORDER_TTL_QUEUE = "order.ttl.queue";


    /**
     * 消息二次转发的路由key
     */
    public static final String ORDER_DLX_ROUTINGKEY = "order.dlx.routingkey";

    /**
     * 消息二次转发的队列名称
     */
    public static final String ORDER_DLX_QUEUE = "order.dlx.queue";
}
