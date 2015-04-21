package com.lvmama.passport.processor.impl.client.gmedia;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * @author clj
 */
public class MD5 {
	public static byte[] encode(byte[] origin) {
		byte[] buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			buf = md.digest(origin);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buf;
	}

	// 测试
	public static void main(String[] args) {
		String s="";
		try {
			s = new String(MD5.encode("11111111111".getBytes("utf-8")),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(s);
	}
}
