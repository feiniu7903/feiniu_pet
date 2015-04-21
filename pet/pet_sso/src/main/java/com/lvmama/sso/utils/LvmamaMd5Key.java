package com.lvmama.sso.utils;

public class LvmamaMd5Key {

	public final static String LVMAMA_MD5_KEY = "32uwae8weufaes8udf89432";
	
	public static boolean check(String userId, String sMd5Str) {
		String t = encode(userId);
		if (t.equalsIgnoreCase(sMd5Str)) {
			return true;
		}else{
			return false;
		}
	} 
	
	public static String encode(String userId) {
		MD5Code md5 = new MD5Code();
		String res = md5.getMD5ofStr(userId+LvmamaMd5Key.LVMAMA_MD5_KEY);
		return res;
	}
	
}
