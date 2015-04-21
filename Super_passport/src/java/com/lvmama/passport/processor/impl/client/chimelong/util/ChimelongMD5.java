package com.lvmama.passport.processor.impl.client.chimelong.util;
import java.security.MessageDigest;
public class ChimelongMD5 {
	public static String getMD5(String value) {
		String temp = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(value.getBytes());
			temp = ChimelongUtils.bytesToHexString(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
}
