package cn.chonor.final_pro.Date;

import java.util.Calendar;

/**
 * Created by Chonor on 2017/12/29.
 */

public class GetTime {
    private static Calendar calendar;
    public GetTime(){
    }

    /**
     * 获取年份
     * @return String 年
     */
    public static String GetYeat(){
        calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 获取月份
     * @return 月
     */
    public static String GetMonth(){
        calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        return String.valueOf(month+1);
    }

    /**
     * 获取日期
     * @return  日期
     */
    public static String GetDay(){
        calendar=Calendar.getInstance();
        int date=calendar.get(Calendar.DATE);
        return String.valueOf(date);
    }

    /**
     * 获取星期
     * @return "1"............."7" 显示时转换为中文
     */
    public static String GetWeek(){
        calendar=Calendar.getInstance();
        boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
        int week=calendar.get(Calendar.DAY_OF_WEEK);
        if(isFirstSunday){
            week=week-1;
            if(week==0)week=7;
        }
        return String.valueOf(week);
    }
}
