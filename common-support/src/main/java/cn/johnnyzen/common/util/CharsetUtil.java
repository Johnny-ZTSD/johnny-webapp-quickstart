package cn.johnnyzen.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/9/12  09:05:24
 * @description: 编码集工具
 */

public class CharsetUtil {
    private final static Logger LOG = LoggerFactory.getLogger(CharsetUtil.class);

    /**
     * char 转 ASCII 码对应的(Int)值
     * @sample
     *  input: 'A'
     *  output: 65
     */
    public static int charToAsciiValue(char ch){
        return (int) ch;
    }

    public static char asciiValueToChar(int asciiValue){
        return (char) asciiValue;
    }

    public static void main(String[] args) {
        LOG.debug(String.valueOf(charToAsciiValue('A')));
        LOG.debug(String.valueOf(asciiValueToChar(97)));
    }
}
