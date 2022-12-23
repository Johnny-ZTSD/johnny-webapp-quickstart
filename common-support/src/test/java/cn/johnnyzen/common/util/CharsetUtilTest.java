package cn.johnnyzen.common.util;

import cn.johnnyzen.common.util.lang.CharsetUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/9/27  02:21:21
 * @description ...
 * @reference-doc
 */

public class CharsetUtilTest {
    private final static Logger LOG = LoggerFactory.getLogger(CharsetUtil.class);

    @Test
    public void test() {
        LOG.debug(String.valueOf(CharsetUtil.charToAsciiValue('A')));
        LOG.debug(String.valueOf(CharsetUtil.asciiValueToChar(97)));
    }
}
