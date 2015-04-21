package com.lvmama.comm.utils.edm;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashUtil {

     private static final char[] HEX = {
          '0', '1', '2', '3', '4', '5', '6', '7', '8', 
          '9', 'a', 'b', 'c', 'd', 'e', 'f'
     };
     
     private static String toHexString(byte[] bytes) {
          int length = bytes.length;
          StringBuffer buffer = new StringBuffer(length * 2);
          int x = 0, n1 = 0, n2 = 0;
          for (int i = 0; i < length; i++) {
               if (0 <= bytes[i])
                    x = bytes[i];
               else
                    x = 256 + bytes[i];
               n1 = x >> 4;
               n2 = x & 0x0f;
               buffer.append(HEX[n1]).append(HEX[n2]);
          }
          return buffer.toString();
     }
     
     /**
      * Make MD5 diaguest.
      * @param s
      * @return
      */
     public static String toMD5(String s) {
          try {
               MessageDigest md = MessageDigest.getInstance("MD5");
               byte[] buf;
               try {
                    buf = md.digest(s.getBytes("UTF-8"));
               } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
                    buf = md.digest(s.getBytes());
               }
               return toHexString(buf).toUpperCase();
          } catch (NoSuchAlgorithmException e) {
               throw new RuntimeException(e);
          }
     } 

}
