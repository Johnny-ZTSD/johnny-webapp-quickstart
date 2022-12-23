package cn.johnnyzen.common.util.lang;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/23 18:18
 * @description ...
 */
public class BytesUtil {
    private static final Logger logger = LoggerFactory.getLogger(BytesUtil.class);

    // 16进制字符
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public BytesUtil() {
    }

    /**
     * byte 数组 转 String
     * @reference-doc
     *  [1] Java byte[] 和 String互相转换 - CSDN - https://blog.csdn.net/qq_19734597/article/details/115865372
     * @return
     */
//    public static String bytesToString(byte [] bytes){
    //方法1: by Base64.encode + Base64.decode
    //Base64 Encoded
//        String encoded = Base64.getEncoder().encodeToString(bytes);
    //Base64 Decoded
//        byte[] decoded = Base64.getDecoder().decode(encoded);
    //Verify original content
//        return new String(decoded);

    //方法2: new String(byte [])
//        return new String(bytes);
//    }
    public static String bytesToStr(byte[] byteArray) {
        String strDigest = null;
        if (byteArray != null) {
            strDigest = "";

            for (int i = 0; i < byteArray.length; ++i) {
                strDigest = strDigest + byteToHexStr(byteArray[i]);
            }
        }

        return strDigest;
    }

    public static byte [] toBytes(String str){
        return toBytes(str, StandardCharsets.UTF_8);
    }

    public static byte [] toBytes(String str, String charsetName) throws UnsupportedEncodingException {
        return str.getBytes(charsetName);
    }

    public static byte [] toBytes(String str, Charset charset) {
        return str.getBytes(charset);
    }

//    public static String bytesToHexStr(byte[] byteArray) {
//        StringBuffer sb = new StringBuffer();
//        //sb.append("[");
//        if (byteArray != null) {
//            for (int i = 0; i < byteArray.length; ++i) {
//                sb.append("0x").append(byteToHexStr(byteArray[i]));
//                if (i < byteArray.length - 1) {
//                    //sb.append(",");
//                }
//            }
//        }
//        //sb.append("]");
//        return sb.toString();
//    }

    /**
     * 将byte数组转换成16进制字符串
     * @param src
     * @return
     * @reference-doc
     * [1] java byte数组与16进制间相互转换的示例 - jb51 -  https://www.jb51.net/article/198049.htm
     * [2] Java 16进制字符串和字节数组转换的几种方法 - CSDN - https://blog.csdn.net/qq_21071977/article/details/115241001
     */
    public static String bytesToHexStr(byte[] src) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * 16进制字符串 转换为 byte []
     * @param hexStr
     * @return byte []
     * @reference-doc
     *  [1] java byte数组与16进制间相互转换的示例 - jb51 -  https://www.jb51.net/article/198049.htm
     */
    public static byte[] hexStrToBytes(String hexStr) {
        if(hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static String byteToHexStr(byte mByte) {
        char[] digit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[]{digit[mByte >>> 4 & 15], digit[mByte & 15]};
        String s = new String(tempArr);
        return s;
    }

    public static String intToHexStr(int v) {
        String hexStr = Integer.toHexString(v);
        if (hexStr.length() % 2 != 0) {
            hexStr = "0" + hexStr;
        }

        return hexStr;
    }

    public static byte[] getByteArray(String hexStr) {
        if (!StringUtils.isEmpty(hexStr) && hexStr.length() % 2 == 0) {
            byte[] data = null;

            try {
                char[] chars = hexStr.toCharArray();
                data = Hex.decodeHex(chars);
            } catch (DecoderException var3) {
                logger.error(var3.getMessage(), var3);
            }

            return data;
        } else {
            return null;
        }
    }

    public static byte[] getByteArray(InputStream in) {
        byte[] data = null;

        try {
            byte[] buffer = new byte[1024];

            int len;
            for (; (len = in.read(buffer)) > 0; data = addArray(data, buffer, len)) {
            }
        } catch (IOException var4) {
            logger.error(var4.getMessage(), var4);
        }

        return data;
    }

    public static byte[] addArray(byte[] source, byte[] addedAry) {
        return addArray(source, addedAry, addedAry.length);
    }

    public static byte[] addArray(byte[] source, byte[] addedAry, int availableAddedAryLength) {
        if (addedAry == null) {
            return source;
        } else {
            byte[] buf = new byte[(source == null ? 0 : source.length) + availableAddedAryLength];
            int i;
            if (source != null) {
                for (i = 0; i < source.length; ++i) {
                    buf[i] = source[i];
                }
            }

            for (i = 0; i < availableAddedAryLength; ++i) {
                buf[(source == null ? 0 : source.length) + i] = addedAry[i];
            }

            return buf;
        }
    }

    public static int contains(byte[] source, byte[] data) {
        if (source == null) {
            return -1;
        } else if (data == null) {
            return -1;
        } else if (source.length >= data.length && data.length != 0) {
            int pos = -1;

            for (int i = 0; i <= source.length - data.length; ++i) {
                boolean flag = true;

                for (int j = 0; j < data.length; ++j) {
                    if (source[i + j] != data[j]) {
                        flag = false;
                    }
                }

                if (flag) {
                    pos = i;
                    break;
                }
            }

            return pos;
        } else {
            return -1;
        }
    }

    public static boolean startWith(byte[] source, byte[] startData) {
        if (source == null) {
            return false;
        } else if (startData == null) {
            return false;
        } else if (source.length >= startData.length && startData.length != 0) {
            boolean flag = true;

            for (int i = 0; i < startData.length; ++i) {
                if (source[i] != startData[i]) {
                    flag = false;
                    break;
                }
            }

            return flag;
        } else {
            return false;
        }
    }

    public static boolean endWith(byte[] source, byte[] endData) {
        if (source == null) {
            return false;
        } else if (endData == null) {
            return false;
        } else if (source.length >= endData.length && endData.length != 0) {
            boolean flag = true;

            for (int i = 0; i < endData.length; ++i) {
                if (source[source.length - endData.length + i] != endData[i]) {
                    flag = false;
                    break;
                }
            }

            return flag;
        } else {
            return false;
        }
    }

    public static byte[] removeStart(byte[] source, int len) {
        if (source == null) {
            return null;
        } else if (source.length < len) {
            return null;
        } else {
            byte[] buf = new byte[source.length - len];

            for (int i = 0; i < buf.length; ++i) {
                buf[i] = source[i + len];
            }

            return buf;
        }
    }

    public static byte[] removeEnd(byte[] source, int len) {
        if (source == null) {
            return null;
        } else if (source.length < len) {
            return null;
        } else {
            byte[] buf = new byte[source.length - len];

            for (int i = 0; i < buf.length; ++i) {
                buf[i] = source[i];
            }

            return buf;
        }
    }

    public static byte[] subByteArray(byte[] data, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new IndexOutOfBoundsException("beginIndex must bigger than 0,but it is " + beginIndex + "");
        } else if (endIndex >= data.length) {
            throw new IndexOutOfBoundsException("endIndex must smaller than data lenght " + data.length + ",but it is " + endIndex + "");
        } else {
            byte[] buf = new byte[endIndex - beginIndex + 1];

            for (int i = beginIndex; i <= endIndex; ++i) {
                buf[i - beginIndex] = data[i];
            }

            return buf;
        }
    }

    public static byte[] remove(byte[] data, int start, int size) {
        if (data == null) {
            return null;
        } else if (start >= data.length) {
            throw new IndexOutOfBoundsException("start must smaller than data's length:'" + data.length + "',but it is " + start + "");
        } else if (start + size > data.length) {
            throw new IndexOutOfBoundsException("start+size must smaller than data's length:'" + data.length + "',but they are " + start + "+" + size + "");
        } else {
            byte[] buf = new byte[data.length - size];

            int i;
            for (i = 0; i < start; ++i) {
                buf[i] = data[i];
            }

            for (i = start + size; i < data.length; ++i) {
                buf[i - size] = data[i];
            }

            return buf;
        }
    }

    public static byte[] removeContains(byte[] source, byte[] data) {
        int pos;
        if (source == null) {
            return null;
        } else if (data == null) {
            return source;
        } else {
            if ((pos = contains(source, data)) == -1) {
                return source;
            } else if (source.length < data.length) {
                return null;
            } else {
                byte[] buf = new byte[source.length - data.length];

                int i;
                for (i = 0; i < pos; ++i) {
                    buf[i] = source[i];
                }

                for (i = pos + data.length; i < source.length; ++i) {
                    buf[i - data.length] = source[i];
                }

                return buf;
            }
        }
    }
}
