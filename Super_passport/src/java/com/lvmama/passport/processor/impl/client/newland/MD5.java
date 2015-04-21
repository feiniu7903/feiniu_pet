package com.lvmama.passport.processor.impl.client.newland;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author clj
 */
public class MD5 {
	public static String encode(String inStr) {
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] md5Bytes = md.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	// 测试
	public static void main(String[] args) {
		String s = "";
		s=MD5.encode("2101201011900001");

		System.out.print(s.toUpperCase());
	}
}
