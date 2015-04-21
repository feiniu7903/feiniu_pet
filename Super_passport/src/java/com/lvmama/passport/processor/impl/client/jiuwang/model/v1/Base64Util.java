package com.lvmama.passport.processor.impl.client.jiuwang.model.v1;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Base64转换工具类
 * 
 * @author 
 * 
 */
public class Base64Util {

    /**
     * 将 s进行 BASE64 编码
     * 
     * @param s
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String getBASE64(String s) {
        if (s == null) {
            return null;
        }
        return new BASE64Encoder().encode(s.getBytes());
    }

    /**
     * 将 BASE64 编码的字符串 s进行解码
     * 
     * @param s
     * @return
     */
    public static String getFromBASE64(String s) {
        if (s == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Base64Util.getBASE64("颐和园"));
        System.out
                .println(Base64Util
                        .getFromBASE64("PG9yZGVyPjxvcmRlcklkPjEzMjkwMTQ8L29yZGVySWQ+PHN0YXR1cz5OT1JNQUw8L3N0YXR1cz48 cGF5bWVudFN0YXR1cz5VTlBBWTwvcGF5bWVudFN0YXR1cz48L29yZGVyPg=="));
    }
    
    public static String encodeToString(byte[] byteArray){
        return new BASE64Encoder().encode(byteArray);
    }
    
    public static byte[] decode(String planString) {
        try {
            return new BASE64Decoder().decodeBuffer(planString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
