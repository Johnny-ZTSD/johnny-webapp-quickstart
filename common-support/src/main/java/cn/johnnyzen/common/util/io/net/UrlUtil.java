package cn.johnnyzen.common.util.io.net;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/12 11:53
 * @description ...
 */
public class UrlUtil {
    public static final String PARAM_PROTOCOL = "protocol";
    public static final String PARAM_HOST = "host";
    public static final String PARAM_PORT = "port";
    public static final String PARAM_PATH = "path";
    public static final String PARAM_QUERY = "query";

    /**
     * 解析 url
     * @param url
     *  eg: "http://10.37.18.178:9200/_opendistro/_sql?format=json";
     * @throws IOException
     * @return map
     */
    public static Map<String, String> parseUrl(String url) throws IOException {
//        String httpUrl = "http://10.37.18.178:9200/_opendistro/_sql?format=json";
        Map<String, String> map = new LinkedHashMap<>();
        URL urlObject = new URL(url);
        map.put(PARAM_PROTOCOL, urlObject.getProtocol());
        map.put(PARAM_HOST, urlObject.getHost());
        map.put(PARAM_PORT, String.valueOf(urlObject.getPort()));
        map.put(PARAM_PATH, urlObject.getPath());
        map.put(PARAM_QUERY, urlObject.getQuery());

        //认证信息
        //map.put("authority", urlObject.getAuthority());
        //map.put("userInfo", urlObject.getUserInfo());
        //引用信息
        //map.put("ref", urlObject.getRef());
        //map.put("content", urlObject.getContent());
//        Print.print(map);
        return map;
    }
}
