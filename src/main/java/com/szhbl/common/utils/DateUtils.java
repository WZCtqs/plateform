package com.szhbl.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 *
 * @author szhbl
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd","yyyy-MM",
            "dd-MM-yyyy HH:mm:ss","dd-MM-yyyy HH:mm","dd-MM-yyyy","MM-yyyy",
            "yyyy/MM/dd HH:mm:ss","yyyy/MM/dd HH:mm","yyyy/MM/dd","yyyy/MM",
            "dd/MM/yyyy HH:mm:ss","dd/MM/yyyy HH:mm","dd/MM/yyyy","MM/yyyy",
            "yyyy.MM.dd HH:mm:ss","yyyy.MM.dd HH:mm","yyyy.MM.dd","yyyy.MM",
            "dd.MM.yyyy HH:mm:ss","dd.MM.yyyy HH:mm","dd.MM.yyyy","MM.yyyy"};
    private String format;

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date parseDatets(String ts){
        long lt = new Long(ts);
        return new Date(lt);
    }

    public static final String parseStringts(String ts){
        return parseDateToStr(YYYY_MM_DD, parseDatets(ts));
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    public static String timeStamp2Date2(String seconds) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static String parseStr(Date date)
    {
        if (date == null)
        {
            return null;
        }
        try
        {
            return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 计算日期N-a天日期
     */
    public static Date dataChange(Date date,int a){
        Long dateToLong = date.getTime();
        Long dateToLongA = dateToLong - (a * 24 * 60 * 60 * 1000);
        Date res = new Date(dateToLongA);
        return res;
    }

    /**
     * 计算日期N+a天日期
     */
    public static Date dataChangeAdd(Date date,int a){
        Long dateToLong = date.getTime();
        Long dateToLongA = dateToLong + (a * 24 * 60 * 60 * 1000);
        Date res = new Date(dateToLongA);
        return res;
    }

    /**
     * 计算n个月后第一天
     */
    public static Date getNextMonth(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    //获取指定日期下个月的第一天
    public static Date getNextMonthByDate(Date byDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(byDate);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 判断两个日期是否相等()
     */
    public static String isDateEquals(Date timeOld,Date time){
        String isEquals = "0"; //0相等 1不等
        Long timeS;
        Long timeE;
        if(StringUtils.isNotNull(timeOld)){
            timeS = timeOld.getTime();
        }else{
            timeS = Long.valueOf(0);
        }
        if(StringUtils.isNotNull(time)){
            timeE = timeOld.getTime();
        }else{
            timeE = Long.valueOf(0);
        }
        Long diff = timeE - timeS;
        if(diff == 0){
            isEquals = "0";
        }else{
            isEquals = "1";
        }
        return isEquals;
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static Long getDatePoorLong(Date endDate, Date nowDate)
    {
        // 获得两个时间的毫秒时间差异
        return endDate.getTime() - nowDate.getTime();
    }

    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        System.out.println(diff);
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static String format(Date date){
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
        return format.format(date);
    }

    /**
     * 得到一个时间延后或前移几天的时间
     * @param nowdate：时间
     * @param delay：前移或后延的天数
     * @return
     */
    public static String getNextDay(String nowdate, String delay) {
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        }catch(Exception e){
            return "";
        }
    }

    /**
     * 返回某个日期前后几天的日期
     * @param date
     * @param i
     * @return
     */
    public static Date getNextDay(Date date, int i) {
        Calendar cal=new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE)+i);
        return cal.getTime();
    }


    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取n天前后time 时间点
     * @param n  节点天数
     * @param time 节点时间
     * @param classDate 班列日期
     * @return
     */
    public static Date getPointTime(int n, String time, Date classDate){
        time = StringUtils.isEmpty(time) ? "00:00" : time;
        // 获取节点日期
        Date date = DateUtils.getNextDay(classDate, n);
        // 获取节点日期时间
        String dateString = DateUtils.dateTime(date) + " " + time;
        return DateUtils.parseDate(dateString);
    }

    /**
     * 计算两个时间相差天数精确到小数点后两位
     * @param startTime   开始时间
     * @param endTime 结束时间
     * @return 相差天数
     */
    public static String getDays(String startTime,String endTime){
        Date startDate = DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",parseDateToStr("yyyy-MM-dd HH:mm:ss",DateUtils.parseDate(startTime)));
        Date endDate = DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",parseDateToStr("yyyy-MM-dd HH:mm:ss",DateUtils.parseDate(endTime)));
        double time = endDate.getTime() - startDate.getTime();
        double betweenDays = time / (1000L*3600L*24L);
        return String.format("%.2f", betweenDays);
    }

    /**
     * 计算两个时间相差天数精确到小数点后两位
     * @param startTime   开始时间
     * @param endTime 结束时间
     * @return 相差天数
     */
    public static String getDays2(String startTime,String endTime){
        if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            return null;
        }else {
            Date startDate = DateUtils.dateTime("yyyy-MM-dd", startTime);
            Date endDate = DateUtils.dateTime("yyyy-MM-dd", endTime);
            double time = endDate.getTime() - startDate.getTime();
            double betweenDays = time / (1000L * 3600L * 24L);
            return String.format("%.2f", betweenDays);
        }
    }

    /**
     * 获取当前年份
     * @return
     */
    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    public static Integer getTodayYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static Integer getTodayMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH+1);
    }

    public static Integer getTodayDate(){
        Calendar calendar = Calendar.getInstance();
       return calendar.get(Calendar.DATE);
    }


    public static void main(String[] args) {
        Date date = parseDate("2020/10/1");
        String s=parseDateToStr("yyyy-MM-dd HH:mm:ss",DateUtils.parseDate("2020-09-19 01:00"));
        System.out.println(date);
        System.out.println(s);
    }

    /**
     * 转化有效时间 去程N-4,回程N-5
     *
     * @param classDate
     * @param eastOrWest
     * @return
     */
    public static Date convertEffectiveDate(Date classDate, String eastOrWest){

        LocalDate localDate = classDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String effectiveDate = "";
        int i = "0".equals(eastOrWest) ? 4 : 5;
        while (i > 0) {
            localDate = localDate.minusDays(1);
            if (localDate.getDayOfWeek() != DayOfWeek.SATURDAY && localDate.getDayOfWeek() != DayOfWeek.SUNDAY){
                i--;
                effectiveDate = localDate.toString();
            }
        }
        return DateUtils.parseDate(effectiveDate+" 12:00:00");
    }

}
