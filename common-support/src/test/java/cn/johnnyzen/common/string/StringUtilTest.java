package cn.johnnyzen.common.string;

import cn.johnnyzen.common.util.lang.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/8/13  09:45:09
 * @description: ...
 */

public class StringUtilTest {
    private static final Logger LOG = LoggerFactory.getLogger(StringUtilTest.class);

    //@Test
    public void testReplace(){
        //String str = "指的是如何使应用程序能够同时支持多种语言 的 问 题。对 我 国 这 样 的 非 英 语国 家 而 汉 字 又 有 多 种 编 码 方 式 的 国 家 而 言 具 有 现 实 意 义。 本 文 将 对 用java 编 制i18n 程 序 的 方 法 作 一 介 绍。";

        String filePath = "E:\\Projects\\github\\johnny-webapp-quickstart\\common-support\\src\\main\\java\\cn\\johnnyzen\\common\\util\\i18n\\i18n.md";

        LOG.debug("\n" + StringUtil.trimBlankForMarkdwonFile(filePath) );
    }
}
