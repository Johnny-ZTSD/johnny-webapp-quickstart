package cn.johnnyzen.common.util.lang;

import javax.servlet.ServletContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/8/13  13:05:58
 * @description: ...
 */

public class ClassLoaderUtil {
    /**
     * 获取 ServletContext 的 classLoader
     * @description 默认从 WebAPP 根目录下取资源，Tomcat下 path 是否以’/'开头无所谓，当然这和具体的 Web Server 容器实现有关。
     * @param servletContext
     * @return
     */
    public static ClassLoader getClassLoader(ServletContext servletContext){
        return servletContext.getClassLoader();
    }

    /**
     * 获取 线程 对应的 classLoader
     * @sample Thread.currentThread().getContextClassLoader(); // 使用当前线程的ClassLoader
     * @param thread
     * @return
     */
    public static ClassLoader getClassLoader(Thread thread){
        return thread.getContextClassLoader();
    }

    /**
     * 获取当前实例对象 对应的 classLoader
     * @sample this.getClass.getClassLoader();  // 使用当前类的ClassLoader
     * @param object
     * @return
     */
    public static ClassLoader getClassLoader(Object object){
        return object.getClass().getClassLoader();
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoaderUtil.class.getClassLoader();
            if (null == classLoader) {
                classLoader = getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 获取 系统级 ClassLoader
     * @description
     *  System ClassLoader与 根 ClassLoader 并不一样。 JVM 下 System ClassLoader 通常为 App ClassLoader
     *  ClassLoader.getSystemClassLoader() // 使用系统 ClassLoader (系统的入口点所使用的ClassLoader)
     * @return
     */
    public static ClassLoader getSystemClassLoader() {
        return System.getSecurityManager() == null ? ClassLoader.getSystemClassLoader() : (ClassLoader)AccessController.doPrivileged((PrivilegedAction<ClassLoader>) ClassLoader::getSystemClassLoader);
    }


    public static ClassLoader getContextClassLoader() {
        return System.getSecurityManager() == null ? Thread.currentThread().getContextClassLoader() : (ClassLoader) AccessController.doPrivileged((PrivilegedAction<ClassLoader>) () -> {
            return Thread.currentThread().getContextClassLoader();
        });
    }
}
