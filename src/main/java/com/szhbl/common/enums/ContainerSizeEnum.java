package com.szhbl.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 集装箱硬性限制尺寸
 * @Author: shy
 * @Date: 2020/6/19
 */
@Getter
@ToString
public enum ContainerSizeEnum {

    /**
     * 长(cm)
     */
    LENGTH("1190"),
    /**
     * 宽(cm)
     */
    WIDTH("230"),
    /**
     * 高(cm)
     */
    HEIGHT("255");


    private String size;

    ContainerSizeEnum(String size){
        this.size = size;
    }

}
