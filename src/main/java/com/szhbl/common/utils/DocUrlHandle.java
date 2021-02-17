package com.szhbl.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2020-10-14 17:34
 */
public class DocUrlHandle {
    /*平台端地址替换*/
    private final static String PLATEFORM = "http://szhbl.zih718.com:8083";
    private final static String ZXDC_PLATE = "https://szhbl.zih718.com/prod-api";
    /*客户端地址替换*/
    private final static String CLIENT = "http://zxdc.zih718.com:18084";
    private final static String ZXDC = "https://szhbl.zih718.com/clientapi";
    /*箱管地址替换*/
    private final static String XG = "http://xg.zih718.com:8080";
    private final static String ZXDC_XG = "https://szhbl.zih718.com/xiangguan";
    /*大口岸地址替换*/
    private final static String DKA = "http://117.159.2.173:8088";
    private final static String ZXDC_DKA = "https://szhbl.zih718.com/dakouan";
    /*拼箱地址替换*/
    private final static String PX = "http://42.228.11.184:8080";
    private final static String ZXDC_PX = "https://szhbl.zih718.com/pinxiang";
    /*国外场站地址替换*/
    private final static String GWCZ = "http://42.228.11.184:10094";
    private final static String ZXDC_GWCZ = "https://szhbl.zih718.com/changzhan";
    /*关务地址替换*/
    private final static String GW = "http://171.8.68.70:8084";
    private final static String ZXDC_GW = "https://szhbl.zih718.com/guanwu";
    /*箱型亚欧地址替换*/
    private final static String XXYO = "http://218.28.14.176:9080";
    private final static String ZXDC_XXYO = "https://szhbl.zih718.com/xiangxingyaou";

    public static String urlHandle(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        if (url.contains(PLATEFORM)) {
            return url.replace(PLATEFORM, ZXDC_PLATE);
        }
        if (url.contains(CLIENT)) {
            return url.replace(CLIENT, ZXDC);
        }
        if (url.contains(XG)) {
            return url.replace(XG, ZXDC_XG);
        }
        if (url.contains(DKA)) {
            return url.replace(DKA, ZXDC_DKA);
        }
        if (url.contains(PX)) {
            return url.replace(PX, ZXDC_PX);
        }
        if (url.contains(GWCZ)) {
            return url.replace(GWCZ, ZXDC_GWCZ);
        }
//        if(url.contains(GW)){
//            return url.replace(GW, ZXDC_GW);
//        }
//        if(url.contains(XXYO)){
//            return url.replace(XXYO, ZXDC_XXYO);
//        }
        return url;
    }
}
