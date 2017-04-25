package com.txznet.launcher.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by TXZ-METEORLUO on 2017/1/19.
 */
public class DateUtil {
    public static final String HOUR12_MINUTE_FORMAT = "hh:mm";

    /**
     * 获取当前的星期
     *
     * @return
     */
    public static String getSimpleWeek() {
        return new SimpleDateFormat("E").format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrSimpleTime() {
        StringBuilder sb = new StringBuilder();
        sb.append(isAM() ? "上午" : "下午");
        sb.append(getTimeNow());
        return sb.toString();
    }

    public static String getTimeNow() {
        return new SimpleDateFormat(HOUR12_MINUTE_FORMAT).format(new Date(System.currentTimeMillis()));
    }

    public static boolean isAM() {
        GregorianCalendar gc = new GregorianCalendar();
        int val = gc.get(GregorianCalendar.AM_PM);
        return val == 0 ? true : false;
    }

    public static String getTimeDesc(int seconds) {
        int sec = seconds % 60;
        int mins = seconds / 60;
        int h = mins / 60;
        int rm = mins % 60;

        return h + ":" + rm + ":" + sec;
    }

    private static final Calendar CALENDAR = Calendar.getInstance();

    public static int getHour(long time) {
        CALENDAR.setTimeInMillis(time);
        int hour = CALENDAR.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public static boolean isNight() {
        int hour = getHour(System.currentTimeMillis());
        if (hour >= 6 && hour < 18) { // 白天
            return false;
        } else { // 夜晚
            return true;
        }
    }
}