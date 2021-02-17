package com.szhbl.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 二级站仓库代理操作能力限制
 * @Author: shy
 * @Date: 2020/6/19
 */
@Getter
@ToString
public enum GoodsOperableEnum {

    /**
     * 米兰
     * EUR  "Via Bizzozzero 78  20032 Cormano (MI)"  20吨 长2.5米以内
     * Boxline  "VIA RUGACESIO 1 – 20090 SEGRATE (MI)" 6吨 长9米以内
     *
     * 统一按照6吨，2.5米来限制，超出单询
     */
    MILAN("米兰","6000","250"),

    /**
     * 布达佩斯
     * Boxline  "1186 Budapest Ipacsfa str.8." 7吨	 长11.9米以内
     */
    BUDAPEST("布达佩斯","7000","1190"),

    /**
     * 布拉格
     * CD  "Františka Diviše 988 Praha 10  CZECH REPUBLIC"  20吨 长11.9米以内
     */
    PRAGUE("布拉格","20000","900"),

    /**
     * 赫尔辛堡
     * HCC  "Helsingborgs Cargo Center Grustagsgatan 22 25464 Helsingborg" 2吨 长3米以内
     */
    HELSINGBORG("赫尔辛堡","2000","300"),

    /**
     * 巴黎
     * IFB（法国） "PLD Paris Nord Logistic Distribution 400 Rue de la Belle Étoile 95700 Roissy-en-France"
     * 2吨 长3米以内
     */
    PARIS("巴黎","2000","300"),

    /**
     * 里昂
     * Ziegler  "11 RUE CALMETTE ZI DE REVOISSON 69740 GENAS"  3吨 长3米以内
     */
    LYON("里昂","3000","300"),

    /**
     * 杜伊斯堡
     * SEACON LOGISTICS "Zum Container Terminal 1 Hafen-Nummer 3685，47119 Duisburg" 1.4吨 2.0*1.2米
     */
    DUISBURG("杜伊斯堡","1400","200*120");


    private String station;
    /**
     * 限重（kg）
     */
    private String weight;
    /**
     * 限长（cm）
     */
    private String length;

    GoodsOperableEnum(String station,String weight, String length){
        this.station = station;
        this.weight = weight;
        this.length = length;
    }

}
