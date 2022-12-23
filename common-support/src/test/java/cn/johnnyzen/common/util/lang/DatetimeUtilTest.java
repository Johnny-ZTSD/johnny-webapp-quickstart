package cn.johnnyzen.common.util.lang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/9/27  02:33:28
 * @description ...
 * @reference-doc
 */

public class DatetimeUtilTest {
    private final static Logger LOG = LoggerFactory.getLogger(DatetimeUtilTest.class);

    @Test
    public void test(){
        LOG.debug(DatetimeUtil.calendarToString(DatetimeUtil.longToCalendar(DatetimeUtil.currentTimeMillis()), "yyyy-MM-dd hh:mm:ss"));
        LOG.debug(DatetimeUtil.dateToLong(new Date()).toString());//1618802845551 (length=13)
        LOG.debug(DatetimeUtil.longToDate(DatetimeUtil.dateToLong(new Date())).toString());//Mon Apr 19 11:27:25 CST 2021
    }
}
