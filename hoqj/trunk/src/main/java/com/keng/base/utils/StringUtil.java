/**
 * www.yingmob.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.keng.base.utils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Filename StringUtil.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * 
 * 
 * 
 */
public class StringUtil extends StringUtils {
    public static String toString(Object o) {
        return null == o ? null : o.toString();
    }

    public static String toString1(Object o) {
        return null == o ? "" : o.toString();
    }

    public static int toInt(Object o) {
        return o == null || StringUtils.isBlank(o.toString()) ? 0 : Integer.valueOf(o.toString());
    }

    public static double toDouble(Object o) {
        return o == null || StringUtils.isBlank(o.toString()) ? 0 : Double.valueOf(o.toString());
    }

    public static long toLong(Object o) {
        return o == null || StringUtils.isBlank(o.toString()) ? 0 : Long.valueOf(o.toString());
    }
    
    //===========================================================以下是联邦银行使用=================================================================
    /**
     * 断言字符串长度
     * @param str 字符串
     * @param size 长度
     */
    public static void assertSizeEq(String str, int size) {
        if (str.length() != size) {
            throw new IllegalArgumentException("[" + str + "]长度不正确（size=" + size + "）");
        }
    }

    /**
     * 字符串左对齐，填充空格
     * @param str 源字符串
     * @param size 输出字符长度
     * @return 左对齐后的字符串
     * @throws IllegalArgumentException 当源字符串大于总长度
     */
    public static String leftJustified(String str, int size) throws IllegalArgumentException {
        return leftJustified(str, size, " ");
    }

    /**
     * 字符串左对齐
     * @param str 源字符串
     * @param size 输出字符长度
     * @param padStr 填充字符
     * @return 左对齐后的字符串
     * @throws IllegalArgumentException 当源字符串大于总长度
     */
    public static String leftJustified(String str, int size, String padStr) throws IllegalArgumentException {
        if (str.length() > size) {
            throw new IllegalArgumentException("str长度大于size");
        }
        return StringUtils.rightPad(str, size, padStr);
    }

    /**
     * 字符串右对齐，填充空格
     * @param str 源字符串
     * @param size 输出字符长度
     * @return 右对齐后的字符串
     * @throws IllegalArgumentException 当源字符串大于总长度
     */
    public static String rightJustified(String str, int size) throws IllegalArgumentException {
        return rightJustified(str, size, " ");
    }

    /**
     * 字符串右对齐
     * @param str 源字符串
     * @param size 输出字符长度
     * @param padStr 填充字符
     * @return 右对齐后的字符串
     * @throws IllegalArgumentException 当源字符串大于总长度
     */
    public static String rightJustified(String str, int size, String padStr) throws IllegalArgumentException {
        if (str.length() > size) {
            throw new IllegalArgumentException("str长度大于size");
        }
        return StringUtils.leftPad(str, size, padStr);
    }
}
