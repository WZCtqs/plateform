package com.szhbl.framework.config.rabbit.track;


public class TrackRabbitmq {

    /*---------------------------------箱管系统整柜操作时间消息队列(整柜)------------------------------*/
    /**
     * 整柜操作动态交换机
     */
    public static final String XG_BOXOPERATION_TOPIC_EXCHANGE = "xg.boxoperation.topic.exchange";
    /**
     * 整柜操作动态 绑定路由key
     */
    public static final String XG_BOXOPERATION_ROUTINGKEY = "xg.boxoperation.*";
    /**
     * 消息队列——箱管系统-班列平台系统
     */
    public static final String XG_BOXOPERATION_QUEUE_BLPT = "xg_boxoperation_queue_blpt";
    /*---------------------------------箱管系统整柜操作时间消息队列(整柜)------------------------------*/

    /*---------------------------------堆场系统监听箱号信息------------------------------*/
    public static final String XG_SYSTEM_CONTAINERINFO_QUEUE_DCXT = "xg_system_containerInfo_queue_dcxt";
    public static final String XG_SYSTEM_CONTAINERINFO_QUEUE_GWCZ = "xg_system_containerInfo_queue_gwcz";
    public static final String XG_SYSTEM_CONTAINERINFO_ROUTINGKEY = "xg.system.containerInfo.*";
    /*---------------------------------堆场系统监听箱号信息------------------------------*/

    /*---------------------------------箱行亚欧系统运踪消息队列（去程）------------------------------*/
    /**
     * 已派车+司机信息
     * 已提箱（整柜）+提箱时间
     * 已提货+提货时间
     * 运输中+预计送达时间
     * 已送达+送达时间
     */
    /**
     *箱行亚欧运踪动态交换机
     */
    public static final String XXYO_TRACK_TOPIC_EXCHANGE = "xxyo.track.topic.exchange";
    /**
     * 箱行亚欧运踪动态 绑定路由key
     */
    public static final String XXYO_TRACK_ROUTINGKEY = "xxyo.track.*";
    /**
     * 消息队列——箱行亚欧-班列平台系统
     */
    public static final String XXYO_TRACK_QUEUE_BLPT = "xxyo_track_queue_blpt";
    /*---------------------------------箱行亚欧系统运踪消息队列（去程）------------------------------*/

    /*---------------------------------拼箱场站运踪消息队列------------------------------*/
    /**
     * 入仓时间
     * 进站时间
     */
    /**
     *拼箱场站运踪动态交换机
     */
    public static final String PXCZ_TRACK_TOPIC_EXCHANGE = "pxcz.track.topic.exchange";
    /**
     * 拼箱场站运踪动态 绑定路由key
     */
    public static final String PXCZ_TRACK_ROUTINGKEY = "pxcz.track.*";
    /**
     * 消息队列——拼箱场站-班列平台系统
     */
    public static final String PXCZ_TRACK_QUEUE_BLPT = "pxcz_track_queue_blpt";
    /*---------------------------------拼箱场站运踪消息队列------------------------------*/

    /*---------------------------------关务系统运踪消息队列（去程）------------------------------*/
    /**
     * 问题沟通中
     * 草单已确认+确认时间
     * 正本已提供+提供时间
     * 已申报+申报时间
     * 布控+布控原因+布控时间
     * 放行+放行时间
     *
     *
     */
    /**
     *关务系统运踪动态交换机
     */
    public static final String GW_TRACK_TOPIC_EXCHANGE = "gw.track.topic.exchange";
    /**
     * 关务系统运踪动态 绑定路由key
     */
    public static final String GW_TRACK_ROUTINGKEY = "gw.track.*";
    /**
     * 消息队列——关务系统-班列平台系统
     */
    public static final String GW_TRACK_QUEUE_BLPT = "gw_track_queue_blpt";
    public static final String GW_TRACK_QUEUE_PX = "gw_track_queue_px";
    /*---------------------------------关务系统运踪消息队列（去程）------------------------------*/

    /*---------------------------------国外场站运踪消息队列------------------------------*/
    /**
     *   去程
     * 已发送提箱资料+发送提箱资料时间
     * 已提箱+提箱时间
     * 运输中+预计送达时间
     * 已送达+实际送达时间
     * 已签收+签收时间
     *
     * 已拆箱完成+预计可提货时间
     * 已提货+司机信息+提货时间
     *
     *   回程
     * 已入仓+入仓时间
     * 已进站+进站时间
     */
    /**
     *国外场站运踪动态交换机
     */
    public static final String GWCZ_TRACK_TOPIC_EXCHANGE = "gwcz.track.topic.exchange";
    /**
     * 国外场站运踪动态 绑定路由key
     */
    public static final String GWCZ_TRACK_ROUTINGKEY = "gwcz.track.*";
    /**
     * 消息队列——国外场站-班列平台系统
     */
    public static final String GWCZ_TRACK_QUEUE_BLPT = "gwcz_track_queue_blpt";
    /*---------------------------------国外场站运踪消息队列------------------------------*/


    /*---------------------------------集疏系统运踪消息队列------------------------------*/
    /**
     *
     *    去程
     * 已发送提箱资料+发送提箱资料时间
     * 已提箱+提箱时间
     * 运输中+预计送达时间
     * 已送达+实际送达时间
     * 已签收+签收时间
     *
     * 运输中+预计送达时间
     * 已送达+实际送达时间
     * 已签收+签收时间
     *
     *
     *     回程
     * 已派车+司机信息
     * 已提箱（整柜）+箱号+提箱时间
     * 已提货+提货时间
     * 运输中+预计送达时间
     * 已送达+实际送达时间
     */
    /**
     *集疏系统运踪动态交换机
     */
    public static final String JS_TRACK_TOPIC_EXCHANGE = "js.track.topic.exchange";
    /**
     * 集疏系统运踪动态 绑定路由key
     */
    public static final String JS_TRACK_ROUTINGKEY = "js.track.*";
    /**
     * 消息队列——集疏系统-班列平台系统
     */
    public static final String JS_TRACK_QUEUE_BLPT = "js_track_queue_blpt";
    /*---------------------------------集疏系统运踪消息队列------------------------------*/

    /*---------------------------------平台订舱运踪消息队列------------------------------*/
    /**
     *
     * 草稿+时间/客户端
     * 取消托书+操作时间
     * 待审核+操作时间
     * 审核通过（托书编号+订舱成功+操作时间
     * 审核失败+审核失败原因+操作时间
     * 转待审核+操作时间
     * 转待审核失败+审核失败原因+操作时间
     */
    /**
     *平台订舱运踪动态交换机
     */
    public static final String PTDC_TRACK_TOPIC_EXCHANGE = "ptdc.track.topic.exchange";
    /**
     * 平台订舱运踪动态 绑定路由key
     */
    public static final String PTDC_TRACK_ROUTINGKEY = "ptdc.track.*";
    /**
     * 消息队列——班列平台订舱-班列平台运踪
     */
    public static final String PTDC_TRACK_QUEUE_BLPT = "ptdc_track_queue_blpt";
    /*---------------------------------平台订舱运踪消息队列------------------------------*/


    public static final String XG_BOXOPERATION_QUEUE_RETURN = "xg_boxoperation_queue_return_blpt";
    public static final String XG_BOXOPERATION_QUEUE_RETURN_JS = "xg_boxoperation_queue_return_js";
    public static final String XG_BOXOPERATION_QUEUE_PICK = "xg_boxoperation_queue_pick_blpt";
    public static final String XG_BOXOPERATION_QUEUE_DCJS = "xg_boxoperation_queue_pick_dcjs";
    public static final String XG_BOXOPERATION_QUEUE_GW = "xg_boxoperation_queue_pick_gw";

    public static final String XG_BOXOPERATION_RETURN_ROUTINGKEY = "xg.boxoperation.return.*";
    public static final String XG_BOXOPERATION_PICK_ROUTINGKEY = "xg.boxoperation.pick.*";
}
