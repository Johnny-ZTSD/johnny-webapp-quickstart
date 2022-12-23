package cn.johnnyzen.common.util.lang;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/10/8  17:18:05
 * @description ...
 * @reference-doc
 */

public class ObjectUtil {
    public static <T> boolean isInstance(T bean, Class clazz){
        return clazz.isInstance(bean);
    }
}
