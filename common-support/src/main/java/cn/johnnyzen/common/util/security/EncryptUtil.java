package cn.johnnyzen.common.util.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp-data-service-parent
 * @create-date 2022/8/19 17:58
 * @description
 * @referenc-doc
 *  [1] Java实现MD5加密 - CSDN - https://blog.csdn.net/weixin_43161811/article/details/84140900
 */
public class EncryptUtil {
    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * 带秘钥加密
     * @param text 明文
     * @param key 密钥
     * @return 密文
     */
    public static String md5WithKey(String text, String key) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text + key);
        return md5str;
    }

    /**
     * 不带秘钥加密
     * @param text 明文
     * @return
     * @throws Exception
     */
    public static String md5(String text) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text);
        return md5str;
    }

    /**
     * MD5验证方法
     * @description 根据传入的密钥进行验证
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     */
    public static boolean verifyMd5WithKey(String text, String key, String md5) throws Exception {
        String md5str = md5WithKey(text, key);
        if (md5str.equalsIgnoreCase(md5)) {
            logger.debug("pass to md5 verify!");
            return true;
        }
        return false;
    }
}
