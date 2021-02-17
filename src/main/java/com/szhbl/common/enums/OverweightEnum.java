package com.szhbl.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 场站操作超重数据
 * 超重货物（超过21吨费用单询操作组，国内为拼箱部，国外操作一组）
 * @Author: shy
 * @Date: 2020/6/19
 */
@Getter
@ToString
public enum OverweightEnum {

    /**
     * 3吨＜G≤5吨
     */
    THREE_TO_FIVE("0","0","90"),

    /**
     * 5吨＜G≤10吨
     */
    FIVE_TO_TEN("1","100","200"),

    /**
     * 10吨＜G≤15吨
     */
    TEN_TO_FIFTEEN("2","200","300"),

    /**
     * 15吨＜G≤18吨
     */
    FIFTEEN_TO_EIGHTEEN("3","300","450"),

    /**
     * 18吨＜G≤21吨
     */
    EIGHTEEN_TO_TWENTY_ONE("4","500","600");


    /**
     * 范围
     */
    private String range;
    /**
     * 国内收费（USD/件）
     */
    private String domestic;
    /**
     * 国外收费（USD/件）
     */
    private String foreign;

    OverweightEnum(String range, String domestic, String foreign){
        this.range = range;
        this.domestic = domestic;
        this.foreign = foreign;
    }

}
