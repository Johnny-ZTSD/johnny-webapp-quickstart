package cn.johnnyzen.common.util.spring;

import org.springframework.context.ApplicationContext;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-date 2022/7/12 20:10
 * @description spring context 工具类
 */
public class SpringContextUtil {
    private static ApplicationContext applicationContext;

    //获取上下文
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    //设置上下文
    public static void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clz){
        return applicationContext.getBean(name, clz);
    }

    public static <T> T getBean(Class<?> clz){
        return (T) applicationContext.getBean(clz);
    }
}
