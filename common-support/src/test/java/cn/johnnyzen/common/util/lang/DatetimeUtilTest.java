package cn.johnnyzen;

import cn.johnnyzen.common.util.lang.DatetimeUtil;
import org.junit.Test;

import java.util.Date;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/9/27  02:33:28
 * @description ...
 * @reference-doc
 */

public class DatetimeUtilTest {
    @Test
    public void test(){
        System.out.println(DatetimeUtil.calendarToString(DatetimeUtil.longToCalendar(DatetimeUtil.currentTimeMillis()), "yyyy-MM-dd hh:mm:ss"));
        System.out.println(DatetimeUtil.dateToLong(new Date()));//1618802845551 (length=13)
        System.out.println(DatetimeUtil.longToDate(DatetimeUtil.dateToLong(new Date())));//Mon Apr 19 11:27:25 CST 2021
    }
}
