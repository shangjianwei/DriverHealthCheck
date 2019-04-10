package com.check.driver.driverhealthcheck.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * FieldSamplingSystemStaff
 *
 * @author sjw
 * @date 2018/8/10.
 */
public class TimeUtils {
    /**
     * @param date 传入毫秒
     * @return 时:分
     */
    public static String date2HourAMin(Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            return hour + ":" + min;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 时间转年月日
     *
     * @param time
     * @return
     */
    public static String date2YMD(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));

        return calendar.get(Calendar.YEAR)
                + "-" + (calendar.get(Calendar.MONTH) + 1)
                + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
    }


    public static String showTime(long show, long old) {
        return longToDate(show, old);
    }

    /**
     * 1、当天的消息，以每5分钟为一个跨度的显示时间；
     * 2、消息超过1天、小于1周，显示星期+收发消息的时间；
     * 3、消息大于1周，显示手机收发时间的日期。
     *
     * @param
     * @return
     */
    private static String longToDate(long long1, long long2) {
        long now = System.currentTimeMillis();
        int day = (int) (now / 1000 / 60 / 60 / 24 - long1 / 1000 / 60 / 60 / 24);
        //5分钟之内的
        if (long1 - long2 < 300000) {
            return "";
        }

        //
        if (day > 7) {
            Date date = new Date(long1);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sd.format(date);
        } else {
            switch (day) {
                //如果是0这则说明是今天,显示时间
                case 0:
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    return sdf.format(long1);
                //如果是1说明是昨天,显示昨天+时间
                case 1:
                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                    return "昨天" + sdf1.format(long1);
                //如果是1说明是前天,显示前天+时间
                case 2:
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                    return "前天" + sdf2.format(long1);
                //结果大于2就只显示年月日
                default:
                    SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
                    return getWeekOfDate(long1) + sdf3.format(long1);
            }
        }


    }


    private static String getWeekOfDate(long time) {

        Date date = new Date(time);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(date.toString() + w);
//
        return weekDays[w];
    }
}
