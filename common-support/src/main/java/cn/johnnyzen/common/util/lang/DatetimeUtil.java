package cn.johnnyzen.common.util.lang;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Johnny Zen
 * @create-time 2018/11/4  00:39:44
 * @description A Tools of Datetime Conversion & Handle
 * Calendar / Date / (datetime) String / Long / Timestamp
 * @reference-doc
 *  [0] java中String Date Timestamp Calendar 之间的关系及转换 - CSDN - https://blog.csdn.net/haqer0825/article/details/7034920
 *  [1] JAVA字符串转日期或日期转字符串 - CNBLOGS - https://www.cnblogs.com/zhengwanmeixiansen/p/7391411.html
 *  [2] java 不同时区转换 - CSDN - https://blog.csdn.net/qq_41344974/article/details/125505290
 */

public class DatetimeUtil {
    /**
     * 获得当前系统毫秒数 (1970-01-01 到现在的毫秒数)
     * @return
     */
    public static long currentTimeMillis(){
        return System.currentTimeMillis();
    }

    /**
     * Calendar to String
     * @param calendar
     * @param format such as "yyyy-MM-dd"
     * @return java.lang.String
     */
    public static String calendarToString(Calendar calendar, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    /**
     * Calendar to Date
     * @param calendar
     * @return java.util.Date
     */
    public static Date calendarToDate(Calendar calendar){
        Date date = calendar.getTime();
        return date;
    }

    public static Timestamp calendarToTimestamp(Calendar calendar){
        return dateToTimestamp(calendarToDate(calendar));
    }

    /**
     * Date to Calendar
     * @param date
     * @return
     */
    public static Calendar dateToCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Timestamp dateToTimestamp(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(df.format(date));
    }

    /**
     * Long to Date
     * @param date
     * @return Eg: 1618802845551(13位)
     */
    public static Long dateToLong(Date date){
        return date.getTime();
    }

    /**
     * Date to String
     * @param date
     * @param format such as:"yyyy-MM-dd"
     * @return
     */
    public static String dateToString(Date date, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * String to Calendar
     * @param dateStr
     * @param formatOfStr such as:"yyyy-MM-dd"
     * @return
     * @throws ParseException
     */
    public static Calendar stringToCalendar(String dateStr,String formatOfStr) throws ParseException {
        SimpleDateFormat sdf= new SimpleDateFormat(formatOfStr);
        Date date =sdf.parse(dateStr);
        return dateToCalendar(date);
    }

    /**
     * String to Date
     * @param dateStr such as : "2021-03-30T01:22:48.000Z" or "2021-03-30"
     * @param formatOfStr such as : "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" or "yyyy-MM-dd"
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dateStr,String formatOfStr) throws ParseException {
        SimpleDateFormat sdf= new SimpleDateFormat(formatOfStr);
        return sdf.parse(dateStr);
    }

    /**
     * String to Timestamp
     * @param dateStr format: yyyy-[m]m-[d]d hh:mm:ss[.f...]
     *  such as:"2019-1-14 08:11:00"
     * @return
     */
    public static Timestamp stringToTimestamp(String dateStr){
        return Timestamp.valueOf(dateStr);
    }

    /**
     * String to Long
     * @param dateStr format: yyyy-[m]m-[d]d hh:mm:ss[.f...]
     *      such as:"2019-1-14 08:11:00"
     * @return
     */
    public static Long stringToLong(String dateStr){
        return timestampToLong(stringToTimestamp(dateStr));
    }


    /**
     *
     *@param datetimeStr
     * eg:
     *  "2022-10-0611:49:00"
     *  "2022-10-0611:49"
     *  "2022-10-08T07:01:40.751Z"
     *@param format
     * eg:
     *  "yyyy-MM-ddhh:mm:ss"
     *  "yyyy-MM-ddhh:mm"
     *  "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" | T代表可替换的任意字符, Z代表时区
     *@param timeZone [选填参数] 允许为 null
     * 可选值有:
     *  "UTC" / "Asia/Shanghai" / "GMT" / "GMT+08:00" / ...
     *@return
     * eg:
     *  1665028140000L
     *  1665028140000L
     *  1665212500751L [timeZone=UTC] 1665183700751L [timeZone=null]
     *@reference-doc
     * [1]java不同时区转换 - CSDN - https://blog.csdn.net/qq_41344974/article/details/125505290
     */
    public static long stringToLong(String datetimeStr,String format,String timeZone){
        SimpleDateFormat formatSDF = new SimpleDateFormat(format);//suchas"yyyyMMddhhmmss"
//newSimpleDateFormat(format,Locale.getDefault());//suchas"yyyyMMddhhmmss"
        if(!StringUtils.isEmpty(timeZone)){
            formatSDF.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        Date date=null;
        try{
            date = formatSDF.parse(datetimeStr);
            return date.getTime();
        }catch(ParseException e){
            e.printStackTrace();
        }
        return -1;
    }

    public static long stringToLong(String datetimeStr,String format){
        return stringToLong(datetimeStr, format, null);
    }

    /**
     * Timestamp to String
     * @param timestamp
     * @param format such as:"yyyy-MM-dd"
     * @return
     */
    public static String timestampToString(Timestamp timestamp, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format);
        return sdf.format(timestamp);
    }

    public static Calendar timestampToCalendar(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        return calendar;
    }

    public static Long timestampToLong(Timestamp timestamp){
        return timestamp.getTime();
    }

    /**
     * Long to Date
     * @param time such as : 1618802845551 (length=13)
     *  such as : long time = System.currentTimeMillis();  获得当前系统毫秒数 (1970-01-01 到现在的毫秒数)
     * @return
     */
    public static Date longToDate(long time){
        return new Date(time);
    }

    /**
     * long to Calendar
     * @param time such as : 1618802845551 (length=13)
     */
    public static Calendar longToCalendar(long time){
        return dateToCalendar(longToDate(time));
    }

    public static Timestamp longToTimestamp(long time) {
        return new Timestamp(time);
    }

    /**
     * Millisecond(Long) to DateJSONString(String)
     * @param ms such as: 1614056148699L
     * @return  such as : {"day":18681,"hour":4,"minute":55,"second":48}
     */
    public static Map<String, Long> millisecondToDateMap(long ms){
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        Map<String, Long> resultMap = new HashMap<>();

        if(day >= 0) {
            resultMap.put("day", day);
        }
        if(hour >= 0) {
            resultMap.put("hour", hour);
        }
        if(minute >= 0) {
            resultMap.put("minute", minute);
        }
        if(second >= 0) {
            resultMap.put("second", second);
        }
        return resultMap;
    }
}
