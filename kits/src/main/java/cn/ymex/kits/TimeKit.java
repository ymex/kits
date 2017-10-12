/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 */
package cn.ymex.kits;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 提供有限的 时间/单体格式相关转换
 */
@SuppressLint("SimpleDateFormat")
public class TimeKit {
    private static String DEFAULT_TIME_ZONE = "GMT+08";

    /**
     * 判断用户的设备时区是否为某一时区
     *
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule;
        if (TimeZone.getDefault() == TimeZone.getTimeZone(DEFAULT_TIME_ZONE)) {
            defaultVaule = true;
        } else {
            defaultVaule = false;
        }
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间
     *
     * @return
     */
    public static Date transformTime(Date date, TimeZone oldZone,
                                     TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;

    }

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    public final static ThreadLocal<SimpleDateFormat> dateFormaterWithTime = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 日期格式：yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    /**
     * 格式化日期时间
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTime(Date date) {
        return dateFormaterWithTime.get().format(date);
    }

    /**
     * 格式化日期时间
     *
     * @param milliseconds milliseconds
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTime(long milliseconds) {
        return dateFormaterWithTime.get().format(new Date(milliseconds));
    }

    /**
     * 格式化日期时间
     *
     * @param milliseconds String
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTime(String milliseconds) {
        long time = Letter.toNum(milliseconds, 0L);
        return formatDateTime(time);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy-MM-dd
     */
    public static String formatDate(Date date) {
        return dateFormater.get().format(date);
    }

    /**
     * 格式化日期
     *
     * @param milliseconds long
     * @return yyyy-MM-dd
     */
    public static String formatDate(long milliseconds) {
        return dateFormater.get().format(new Date(milliseconds));
    }

    /**
     * 格式化日期
     *
     * @param milliseconds String
     * @return yyyy-MM-dd
     */
    public static String formatDate(String milliseconds) {
        long time = Letter.toNum(milliseconds, 0L);
        return formatDate(time);
    }

    /**
     * 将字符串转为日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormaterWithTime.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String semanticTime(String sdate) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater.get().format(cal.getTime());
        String paramDate = dateFormater.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater.get().format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater.get().format(today);
            String timeDate = dateFormater.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回long类型的今天的日期
     *
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
