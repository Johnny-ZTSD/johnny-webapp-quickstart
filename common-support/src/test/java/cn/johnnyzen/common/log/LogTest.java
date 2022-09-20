package cn.johnnyzen.common.log;

import org.junit.Test;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2020/11/11  23:29:46
 * @description: ...
 */

public class LogTest {
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void testSlf4j(){
        Logger logger = LoggerFactory.getLogger(Object.class);
        logger.error("123");
    }

    @Test
    public void testLog1() {
        // 直接打印字符串
        logger.info("姓名:凤思高举,密码:******");

        String username = "凤思高举";
        String password = "******";

        // 拼接字符串写法
        logger.info("姓名:" + username + ",密码:" + password);

        // 使用MessageFormat的写法
        logger.info(MessageFormat.format("姓名:{0},密码:{1}", username, password));

    }
}
