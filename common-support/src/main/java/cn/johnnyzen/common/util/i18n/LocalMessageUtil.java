package cn.johnnyzen.common.util.i18n;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 国际化工具
 */
//@Component
public class LocalMessageUtil {

    @Resource
    private LocalMessage message;
    private static LocalMessage localMessage;

    @PostConstruct
    private void init() {
        localMessage = message;
    }

    public static String get(String key) {
        return localMessage.get(key);
    }

    public static String get(String key, Object... args) {
        return localMessage.get(key, args);
    }

    public static String get(String key, Object[] args, String def) {
        return localMessage.get(key, args, def);
    }

    public static String getOrDef(String key, String def) {
        return localMessage.getOrDef(key, def);
    }

    public static String getOrDef(String key, String def, Object... args) {
        return localMessage.get(key, def, args);
    }
}