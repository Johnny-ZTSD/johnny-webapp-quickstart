package cn.johnnyzen.common.util.i18n;

import cn.hutool.core.lang.ResourceClassLoader;
import cn.johnnyzen.common.util.debug.Print;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/8/13  02:31:10
 * @description: ...
 * @reference-doc
 *  Spring junit 做单元测试，报 Failed to load ApplicationContext 错误 - CSDN - https://blog.csdn.net/dearLHB/article/details/46290983
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml", "classpath*:application*.xml" })
public class LocalMessageTest {

    private static final Logger LOG = LoggerFactory.getLogger(LocalMessageTest.class);

    @Autowired
    I18nAutoConfiguration i18nAutoConfiguration;

    @Autowired
    LocalMessageUtil localMessageUtil;

    @Test
    public void test(){
        String msg = LocalMessageUtil.get("opt.log.function.name.query");
        Print.print("msg: " + msg);
    }

    /**
     * 最简单的 i18n 国际化实现方式
     */
    @Test
    public void testI18NLocale() throws IOException {
//        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver(new DefaultResourceLoader(this.beanClassLoader));
//        Pattern pattern = Pattern.compile("messages_(.+?)\\.properties");
//        Resource[] resources = patternResolver.getResources("classpath*:i18n/messages*.properties");




        //从 ClassLoader 对应的 classpath 下寻找对应文件
//        InputStream inputStream = classLoader.getResourceAsStream("messages_zh_CN.properties");

//        URL resource = classLoader.getResource("messages_zh_CN.properties");
//        File file = new File(resource.getFile());
//        FileReader fileReader = new FileReader(file);
//        try (Scanner sc = new Scanner(new FileReader(filePath))) {




//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //; new BufferedReader(new InputStreamReader(System.in));
//        String line = null;
//        while((line = bufferedReader.readLine()) != null) {
//            System.out.println(line);
//            line = null;
//        }

        //ResourceBundle propertyResourceBundle = new PropertyResourceBundle(inputStream);

        //String value = propertyResourceBundle.getString("opt.log.function.name.export");
        //LOG.debug(value);
    }
}
