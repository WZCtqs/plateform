package com.szhbl.common.enums;

/**
 * @Description : 语言
 * @Author : shahy
 * @Date: 2020-10-01 14:20
 */
public enum LanguageEnum {

    中文("zh"),
    英文("en");

    public String value;

    LanguageEnum(String value) {
        this.value = value;
    }
}
