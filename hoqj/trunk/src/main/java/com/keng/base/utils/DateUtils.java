package com.keng.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    /**
     * 获取两个时间的时间差
     * 
     * @return 相差时差
     * 
     */
    public static long dateDiff(Date frm, Date to, long unit) {
        // 获得两个时间的毫秒时间差异
        long diff = to.getTime() - frm.getTime();
        return diff / unit;
    }


    /**
     * 将时间段根据定长切分为多个部分</br> </br> {@code segmentingDate("20150701120000","20150701120001",2000)=["20150701120000"]}</br>
     * {@code segmentingDate("20150701120000","20150701120002",2000)=
     *          ["20150701120000","20150701120002"]}</br> {@code segmentingDate("20150701120000","20150701120003",2000)=
     *          ["20150701120000","20150701120002"]}</br> {@code segmentingDate("20150701120000","20150701120004",2000)=
     *          ["20150701120000","20150701120002","20150701120004"]}</br>
     * 
     * @param frm
     * @param to
     * @param unit 毫秒单位
     * @return
     */
    public static Date[] segmentingDate(Date frm, Date to, long unit) {
        int diff = (int) (((to.getTime() - frm.getTime()) / (double) unit) + 1);
        long lTime = frm.getTime();
        Date[] segments = new Date[diff];
        for (int i = 0; i < diff; i++) {
            segments[i] = new Date(lTime);
            lTime += unit;
        }
        return segments;
    }

    /**
     * 字符串转换为Date
     * 
     * @param str 格式为：Tue Mar 23 16:49:40 CST 2010
     * @param pattern 格式：EEE MMM dd HH:mm:ss zzz yyyy
     * @param locale　Locale.US
     * @return
     */
    public static Date parse(String str, String pattern, Locale locale) {
        if (str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期
     * 
     * @param date
     * @param pattern
     * @param locale
     * @return
     */
    public static String format(Date date, String pattern, Locale locale) {
        if (date == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern, locale).format(date);
    }

    public static Date parse(String str, String pattern) {
        return parse(str, pattern, Locale.CHINA);
    }

    public static String format(Date date, String pattern) {
        return format(date, pattern, Locale.CHINA);
    }

    public static String getToday(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 获取系统当前时间字符串，格式为：yyyyMMddHHmmssSSS
     * 
     * @return
     */
    public static String getCurrentTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dateFormat.format(new Date());
    }
}
