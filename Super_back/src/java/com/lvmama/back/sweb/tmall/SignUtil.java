package com.lvmama.back.sweb.tmall;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


public class SignUtil {

	private static final String CHARSET_GBK = "GBK";
	/**
	 * 计算签名
	 * @param secret
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public  static String sign(String secret, Map<String,String> data) throws IOException {
		//把字典按Key的字母顺序排序
		Map<String, String> sortedParams = new TreeMap<String, String>();
		sortedParams.putAll(data);
		Set<Entry<String, String>> paramSet = sortedParams.entrySet();

		//把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder(secret);
		for (Entry<String, String> param : paramSet) {
			if (isNotEmpty(param.getKey(), param.getValue())) {
				query.append(param.getKey()).append(param.getValue());
			}
		}

		//使用MD5加密
		byte[] bytes = encryptMD5(query.toString());

		//把二进制转化为大写的十六进制
		return byte2hex(bytes);
	}
	
	private static byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes(CHARSET_GBK));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse.getMessage());
		}
		return bytes;
	}
	
	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public static boolean isNotEmpty(String... values) {

		boolean result = true;

		if (null == values || 0 == values.length) {
			return false;
		}

		if (null != values && 0 < values.length) {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}

		return result;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {

		boolean result = true;

		if (null != value) {
			int length = value.length();
			for (int i = 0; i < length; i++) {
				if (false == (Character.isWhitespace(value.charAt(i)))) {
					result = false;
				}
			}
		}

		return result;
	}
}
