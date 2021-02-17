package com.szhbl.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtilsDailyReport {
    //根据固定的格式，将字符串转化为Date
    public static Date parseDate(String str, String ftm) {
        if (str == null){
            return null;
        }
        try {
            return new SimpleDateFormat(ftm).parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    //根据固定的格式，将日期转化为字符串
    public static String parseStrTo(Date date) {
        if (date == null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    //根据固定的格式，将日期转化为字符串
    public static String parseStr(Date date) {
        if (date == null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    //获取传入日期所在月的第一天
    public static Date getFirstDayMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int first = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, first);
        return cal.getTime();
    }

    //获取传入日期所在月的最后一天
    public static Date getLastDayMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    //获取传入日期当天0时
    public static Date getFirstDayTime(Date date){
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set( Calendar.HOUR_OF_DAY,00);
        start.set( Calendar.MINUTE, 0);
        start.set( Calendar.SECOND,0);
        return start.getTime();
    }

    //获取传入日期当天最后时间
    public static Date getLastDayTime(Date date){
        Calendar end = Calendar.getInstance();
        end.setTime(date);
        end.set(Calendar.HOUR_OF_DAY,23);
        end.set( Calendar.MINUTE,59);
        end.set(Calendar.SECOND,59);
        return end.getTime();
    }

    //所传入的日期加a天
    public static Date getDayAddOne(Date date,int a) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(cal.DATE,a); //把日期往后增加一天,整数  往后推,负数往前移动
        return cal.getTime();
    }

    //获取两个日期间的工作日天数
    public static int workDays(Date strStartDate, Date strEndDate) {
        int count = 0;
        if(null!=strStartDate && null!=strEndDate){
            Calendar cl1 = Calendar.getInstance();
            Calendar cl2 = Calendar.getInstance();
            cl1.setTime(strStartDate);
            cl2.setTime(strEndDate);
            while (cl1.compareTo(cl2) <= 0) {
                if (cl1.get(Calendar.DAY_OF_WEEK) != 7 && cl1.get(Calendar.DAY_OF_WEEK) != 1)
                    count++;
                cl1.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return count;
    }



}
