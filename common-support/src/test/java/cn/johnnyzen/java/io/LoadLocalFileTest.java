package cn.johnnyzen.java.io;

import cn.hutool.core.util.ClassLoaderUtil;
import org.junit.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/8/13  12:57:58
 * @description: ...
 */
public class LoadLocalFileTest {

    @Test
    public void loadClassPathFileByBufferedReader() throws IOException {
        ClassLoader classLoader = ClassLoaderUtil.getContextClassLoader();

        //从 ClassLoader 对应的 classpath 下寻找对应文件
        InputStream inputStream = classLoader.getResourceAsStream("messages_zh_CN.properties");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //; new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            line = null;
        }
    }
}
