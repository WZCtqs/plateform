package com.szhbl.common.enums;

/**
 * @Description : 集疏时效
 * @Author : shahy
 * @Date: 2020-10-01 14:20
 */
public enum JsAgingEnum {

    ONE("1-2个工作日"),
    TWO("3个工作日");

    public String value;

    JsAgingEnum(String value) {
        this.value = value;
    }
}
