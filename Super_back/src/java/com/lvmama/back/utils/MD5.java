package com.lvmama.back.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	
	public static String transString2MD5(String str){
		try {
			String passMD5 = new MD5().code(str);
			String rst=passMD5.substring(8, passMD5.length() - 8);
			return rst;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return str;
		}
	}
	
	public String code(String str) throws NoSuchAlgorithmException{
		MessageDigest alga;
		String myinfo = str;
		alga = MessageDigest.getInstance("MD5");
		alga.update(myinfo.getBytes());
		byte[] digesta = alga.digest();
		String hs = "";
		String stmp = "";
		for (int n = 0; n < digesta.length; n++) {
			stmp = (java.lang.Integer.toHexString(digesta[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
	
	public static String codeByUTF8(String plainText) throws Exception{
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("utf-8"));
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

}
