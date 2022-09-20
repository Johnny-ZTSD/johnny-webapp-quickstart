package cn.johnnyzen.common.request;

import cn.johnnyzen.common.util.debug.Print;
import cn.johnnyzen.common.util.io.net.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Connection;
import org.junit.Test;

import org.apache.http.client.CookieStore;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2020/11/11  21:20:11
 * @description: ...
 */

public class HttpClientUtilTest {
    @Test
    public void testGetCookies() throws Exception {
        // 旧版
        //HttpClient httpClient = new DefaultHttpClient();
        // execute get/post/put or whatever
        //httpClient.doGetPostPutOrWhatever();
        // get cookieStore
        //CookieStore cookieStore = httpClient.getCookieStore();
        // get Cookies
        //List<Cookie> cookies = cookieStore.getCookies();
        // process...

        // 新版
        /* init client */
        HttpClient http = null;
        CookieStore httpCookieStore = new BasicCookieStore();
        http = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();

        /* do stuff */
        HttpGet httpRequest = new HttpGet("https://www.baidu.com/");
        HttpResponse httpResponse = null;
        try {
            httpResponse = http.execute(httpRequest);
        } catch (Throwable error) {
            System.out.println(error);
            throw new RuntimeException(error);
        }
        /* check cookies */
        List<Cookie> cookies = httpCookieStore.getCookies();
        Print.print(cookies);
        System.out.println("httpResponse:"+httpResponse.getEntity().getContent());
        System.out.println("hellow");
    }

/*    @Test
    public void testRequest() throws IOException {
        HttpClientUtil  util =  new HttpClientUtil();
        Document doc =  util.getDocument("http://10.0.6.74:6080/j_spring_security_check");
        System.out.println(doc.body());
    }*/

    /**
     var xml = new XMLHttpRequest();
     xml.withCredentials = true; // 开启Cookie，启用会话机制
     var url = "http://10.0.6.74:6080/j_spring_security_check";
     xml.open('POST', url, true);
     xml.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=utf-8");
     xml.send("j_username=admin&j_password=admin");
     */
    @Test
    public void testGetCookies2() throws IOException {
        String url = "http://10.0.6.74:6080/j_spring_security_check?j_username=admin&j_password=admin";
        url = "https://www.jetbrains.com/idea/";
        String cookies = "";
        Map<String, String> params = null;
        /*
        params = new HashMap<>();
        params.put(" j_username", "admin");
        params.put("j_password", "admin");
         */
        Connection connection =  HttpClientUtil.getConnection(url, params, cookies);
        connection.header("Accept", "*/*");
        connection.header("Accept-Encoding", "gzip, deflate");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        connection.header("Cache-Control", "no-cache");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length","33");
        connection.header("Content-type", "application/json;charset=UTF-8");
        connection.header("Cookie", "RANGERADMINSESSIONID=A84A9933555E82AA7D22FC2F4D73694D");
        connection.header("Host", "10.0.6.74:6080");
        connection.header("Origin", "10.0.6.74:6080");
        connection.header("Pragma", "no-cache");
        connection.header("Referer", "http://10.0.6.74:6080/j_spring_security_check");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36");
        Print.print(connection.post().body());
        //connection.response().cookies();
    }
}
