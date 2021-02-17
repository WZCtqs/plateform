package com.szhbl.common.utils;

public class AutoOrderNumber {
    /**
     * 返回托书编号
     *
     * @return 托书编号
     */
    public static String orderNumber(String classBh,String zp,String site,int orderType,int isPower){
        String orderNumber = "" ;
        orderNumber = classBh;
        //orderType 0为空箱子，1为拼箱大柜 2为一般订单
        if(orderType == 0){
            orderNumber += "KX";
        }
        if(orderType == 1){
            orderNumber += "PX";
        }
        //zp 0为整柜，1为拼箱
        if(zp.equals("0")){
            orderNumber += "F";
        }
        if(zp.equals("1")){
            orderNumber += "L";
        }
        //site 去0(西向)为下货站，回1(东向)为上货站
        switch (site){
            case "49_20066": orderNumber += "H";//汉堡
                break;
            case "49_80200": orderNumber += "Mun";//慕尼黑
                break;
            case "32_20182": orderNumber += "Lie";//列日
                break;
            case "31_999025":orderNumber += "Rot";//鹿特丹
                break;
            case "32_2018": orderNumber += "Ant";//安特卫普
                break;
            case "48_21500": orderNumber += "M";//马拉
                break;
            case "48_00686": orderNumber += "W";//华沙
                break;
            case "375_220072": orderNumber += "B";//布列斯特
                break;
            case "48_60111": orderNumber += "Poz";//波兹南
                break;
            case "49_47044": orderNumber += "Dui";//杜伊斯堡
                break;
            case "46_0464555": orderNumber += "Hel";//赫尔辛堡
                break;
            case "358_00002": orderNumber += "Helsinki";//赫尔辛基
                break;
            case "39_20121": orderNumber += "Mil";//米兰
                break;
            case "33_75001": orderNumber += "Par";//巴黎
                break;
            case "33_750002":orderNumber += "Lyo";//里昂
                break;
            case "420_10003": orderNumber += "Pra";//布拉格
                break;
            case "36_1007": orderNumber += "Bud";//布达佩斯
                break;
            case "007_190121": orderNumber += "StP";//圣彼得堡
                break;
            case "7_109807": orderNumber += "Mos";//莫斯科
                break;
            case "375_220071": orderNumber += "Min";//明斯克
                break;
            case "230506": orderNumber += "Kup";//库帕夫纳
                break;
            case "183502": orderNumber += "Vor";//莫斯科 沃尔西诺站
                break;
            case "007-3": orderNumber += "Kra";//克拉斯诺亚尔斯克
                break;
            case "007-6": orderNumber += "Irk";//伊尔库茨克
                break;
            case "007-2": orderNumber += "Chy";//丘诺亚尔
                break;
            case "707701":orderNumber += "Alt";//阿腾科里
                break;
            case "700308":orderNumber += "Zts";//热特苏
                break;
            case "998_05":orderNumber += "Med";//梅杰乌Medeu
                break;
            case "998_02":orderNumber += "Ser";//谢尔盖利Sergeli
                break;
            case "998_01":orderNumber += "Chu";//丘库尔赛Chukursay
                break;
            case "998":orderNumber += "Tas";//塔什干Tashkent
                break;
            case "86_012600": orderNumber += "Ere";//二连
                break;
            case "86_833418": orderNumber += "Ala";//阿拉山口
                break;
            case "86_835221": orderNumber += "Kho";//霍尔果斯Khorgos
                break;
            case "86_021400": orderNumber += "Man";//满洲里
                break;
            case "007_256": orderNumber += "Pin";//凭祥
                break;
            case "006_565": orderNumber += "Han";//河内
                break;
            //case "48_000000": orderNumber += "KTN";//库特诺
            //    break;
            //case "327_050000":orderNumber += "Almaty";//阿拉木图
            //    break;
            case "998_04": orderNumber += "Ata2";//阿拉木图2
                break;
            case "998_03": orderNumber += "Ata1";//阿拉木图1
                break;
            case "249": orderNumber += "SF";//绥芬河
                break;
            case "32_20183": orderNumber += "Gen";//亨克
                break;
            case "007-23": orderNumber += "Zab";//后贝加尔斯克
                break;
            case "48_00032": orderNumber += "Kat";//卡托维兹
                break;
            default:
                break;
        }
        int point =  (int) (Math.random() * 300);
        if(point==0){
            point = point+1;
        }
        if(point<10){
            orderNumber += "0";
        }
        //point = point + 300;
        orderNumber += point;
        return orderNumber;
    }
}
