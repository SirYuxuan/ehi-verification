package com.yuxuan66.ehi.verification.utils;

/**
 * 字符串工具类
 *
 * @author Sir丶雨轩
 * @date 2018/12/11 13:48
 */
public class StrUtil {


    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 判断结果
     */
    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 判断结果
     */
    public static boolean isBlank(Object str) {
        return str == null || isBlank(str.toString());
    }

    /**
     * 字符串首字母大写
     *
     * @param str 字符串
     * @return 大写后的结果
     */
    public static String fristUp(String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();
        }
    }
}
