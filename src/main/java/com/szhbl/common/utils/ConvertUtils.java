package com.szhbl.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字转换
 */
public class ConvertUtils {

    /**
     * 含有字符的字符串转int
     * @param a
     * @return
     */
    public static int reInt(String a) {
        String reg="[^0-9]";//定义正则表达式即：0-9的所有数
        //Pattern调用静态方法compile返回Pattern实例。
        //也可以说是将正则表达式定义为编译规则
        Pattern pattern= Pattern.compile(reg);
        //此处代表是通过编译规则筛选字符串a里的所有数字
        Matcher matcher=pattern.matcher(a);
        //去掉所有的空格和空串
        Integer c=Integer.parseInt(matcher.replaceAll("").trim());
        return c;//将最终得到的 整数返回前台
    }

    /**
     * 含有字符的字符串转float
     *
     * @param a
     * @return
     */
    public static float reFloat(String a) {
        String reg="[^\\d.]*";
        //Pattern调用静态方法compile返回Pattern实例。
        //也可以说是将正则表达式定义为编译规则
        Pattern pattern= Pattern.compile(reg);
        //此处代表是通过编译规则筛选字符串a里的所有数字
        Matcher matcher=pattern.matcher(a);
        //去掉所有的空格和空串
        Float c=Float.parseFloat(matcher.replaceAll("").trim());
        return c;//将最终得到的 整数返回前台
    }

    /**
     * 处理json字符串中value多余的双引号， 将多余的双引号替换为中文双引号
     * @param s
     * @return
     */
    public static String toJsonString(String s) {
        char[] tempArr = s.toCharArray();
        int tempLength = tempArr.length;
        for (int i = 0; i < tempLength; i++) {
            if (tempArr[i] == ':' && tempArr[i + 1] == '"') {
                for (int j = i + 2; j < tempLength; j++) {
                    if (tempArr[j] == '"') {
                        if (tempArr[j + 1] != ',' && tempArr[j + 1] != '}') {
                            tempArr[j] = '”'; // 将value中的 双引号替换为中文双引号
                        } else if (tempArr[j + 1] == ',' || tempArr[j + 1] == '}') {
                            break;
                        }
                    }
                }
            }
        }
        return new String(tempArr);
    }

}
