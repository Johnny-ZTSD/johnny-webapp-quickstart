package cn.johnnyzen;

import org.junit.Test;

import java.io.IOException;
import java.util.Base64;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2021/8/27  12:27:40
 * @description: ...
 */

public class Base64Test {
    /**
     * Base64
     */
    public static void base64(String str) {
        byte[] bytes = str.getBytes();

        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Base 64 加密后：" + encoded);

        //Base64 解密
        byte[] decoded = Base64.getDecoder().decode(encoded);

        String decodeStr = new String(decoded);
        System.out.println("Base 64 解密后：" + decodeStr);

        System.out.println();
    }

    @Test
    public void testBase64() throws IOException {
        String str = "hello world!";

        base64(str);
/*
    Base 64 加密后：aGVsbG8gd29ybGQh
    Base 64 解密后：hello world!
*/

        String encodedStr = Base64Utils.encodeBase64(str);
        System.out.println("[编码]java.util.Base64/sun.misc.BASE64En(De)coder:" + encodedStr);
        System.out.println("[解码]java.util.Base64/sun.misc.BASE64En(De)coder:" + Base64Utils.decodeBase64(encodedStr));
/*
[编码]java.util.Base64/sun.misc.BASE64En(De)coder:aGVsbG8gd29ybGQh
[解码]java.util.Base64/sun.misc.BASE64En(De)coder:hello world!
*/
    }
}

class Base64Utils {
    public static String encodeBase64(String str){
        byte[] bytes = str.getBytes();
        return Base64.getEncoder().encodeToString(bytes);//java.util.Base64
        //return (new BASE64Encoder()).encodeBuffer(bytes);//sun.misc.BASE64Encoder
    }

    public static String decodeBase64(String encodedStr) throws IOException {
        return new String(Base64.getDecoder().decode(encodedStr)); //java.util.Base64
        //return new String( (new BASE64Decoder()).decodeBuffer(encodedStr) );//sun.misc.BASE64Decoder
    }
}
