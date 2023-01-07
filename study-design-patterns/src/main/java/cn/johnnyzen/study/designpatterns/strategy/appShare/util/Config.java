package cn.johnnyzen.strategy.appShare.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/9/1  08:19:05
 * @description:
 *
 * Config.getConfig().get("key")
 */


public class Config {
    private static final Config CONFIG = new Config("/share.yml");

    private Map<String, Object> root;

    private Config(String... fileNames) {
        Map<String, Object> map = new HashMap<String, Object>(0);
        for (String fileName : fileNames) {
            Map<String, Object> conf = this.getRoot(fileName);
            map = this.marge(map, conf);
        }
        root = map;
    }

    /**
     *
     * @return
     */
    public static Config getConfig() {
        return CONFIG;
    }

    /**
     * @param key
     * @return
     */
    private Object getValue(String key) {
        Object value = root.get(key);

        if (value == null) {
            throw new RuntimeException(String.format(
                    "The value corresponding to the key is not set.key[%s]",
                    key));
        }

        return value;
    }

    /**
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Object value = getValue(key);
        return value.toString();
    }

    public Object getOj(String key) {
        Object value = getValue(key);
        return value;
    }


    /**
     * @param resouce
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getRoot(String resouce) {
        InputStream in = null;
        try {
            in = Config.class.getResourceAsStream(resouce);
            return (Map<String, Object>) new Yaml().load(in);
        } catch (Exception err) {
            throw new RuntimeException(
                    "It failed in reading the configuration file.", err);
        }
    }

    /**
     * @param map1
     * @param map2
     * @return
     */
    private Map<String, Object> marge(Map<String, Object> map1,
                                      Map<String, Object> map2) {

        Map<String, Object> marged = new HashMap<String, Object>(map1);

        for (String key : map2.keySet()) {
            if (marged.containsKey(key)) {
                throw new RuntimeException(String.format(
                        "The key overlaps. [%s]", key));
            }
            marged.put(key, map2.get(key));
        }

        return marged;
    }
}