package com.szhbl.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 还箱地对应堆场地址
 * @Author: shy
 * @Date: 2020/6/19
 */
@Getter
@ToString
public enum ReturnAddressEnum {


    /**
     * 沈阳中远海运物流有限公司
     */
    SHENYANG("沈阳","沈阳市大东区辉山大街2号"),
    /**
     * 天津中外运集装箱发展有限公司
     */
    TIANJIN("天津","天津自贸试验区（天津港保税区）海滨大道3360号，（原塘沽区临港路）港外博达堆场"),
    /**
     * 青岛珉钧北站
     */
    QINGDAO("青岛","珉钧北站地址： 青岛市黄岛区千山北路  大运重卡 路东"),
    /**
     * 郑州陆港一堆场
     */
    ZHENGZHOU("郑州","郑州市经济技术开发区经北四路146号（郑州陆港公司园区）"),
    /**
     * 中集振洋（上海）物流有限公司
     */
    SHANGHAI("上海","上海市浦东新区华东路1950号甲"),
    /**
     * 宁波中创物流堆场（二堆场）
     */
    NINGBO("宁波","北仑区柴桥镇后所村中创二堆场（原华光不锈钢厂内）"),
    /**
     * 厦门中外运裕丰冷冻工程有限公司
     */
    XIAMEN("厦门","厦门市现代物流园区寨山西路200号"),
    /**
     * 广州思信兴堆场
     */
    GUANGZHOU("广州","广州市黄埔区石化北路“思信-裕通仓””"),
    /**
     * 西安易通集装箱堆场
     */
    XIAN("西安","西安市凤城十二路出口加工区A区易通集装箱堆場"),
    /**
     * 连云港东鸿
     */
    LIANYUNGANG("连云港","连云港经济开发区东方大道108号"),
    /**
     * 镇江新区华航集装箱有限公司
     */
    ZHENJIANG("镇江","镇江新区大港北山路与荞麦山路交界（镇江华航集装箱堆场）"),
    /**
     * 武汉中港物流有限公司
     */
    WUHAN("武汉","湖北省武汉市新洲区阳逻平江西路特9号"),
    /**
     * 深圳
     */
    SHENZHEN("深圳","深圳市南山区妈湾大道1068号");


    private String city;

    private String address;

    ReturnAddressEnum(String city, String address){
        this.city = city;
        this.address = address;
    }

    public static String getAddress(String city) {
        ReturnAddressEnum[] returnAddressEnums = values();
        for (ReturnAddressEnum returnAddressEnum : returnAddressEnums) {
            if (returnAddressEnum.getCity().equals(city)) {
                return returnAddressEnum.getAddress();
            }
        }
        return null;
    }

}
