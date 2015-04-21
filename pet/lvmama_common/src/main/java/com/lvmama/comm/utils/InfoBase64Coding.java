package com.lvmama.comm.utils;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class InfoBase64Coding {
	private static BASE64Encoder encoder = new BASE64Encoder();
	private static BASE64Decoder decoder = new BASE64Decoder();

	// 密钥
	private static byte[] key = { 106, 107, 108, 115, 103, 102, 106, 107, 108,
			109, 122, 118, 109, 44, 62, 97, 51, 102, 106, 107, 108, 64, 115,
			102, 52, 105, 119, 105, 111, 101, 114, 50, 46, 59, 51, 52, 57, 55,
			50, 51, 102, 115, 100, 102, 102 };

	// 加密
	public static String encrypt(String str) {
		String str_src = new String(str);
		byte[] src = str_src.getBytes();
		int j = key.length;
		int buflen = src.length;
		byte[] encstring = new byte[buflen];

		String result = null;
		try {
			Integer x = null;
			for (int i = 0; i < buflen; i++) {
				int k = i / j;
				if (i >= j) {
					x = new Integer(src[i] ^ key[i - j * k]) + i % j;
					encstring[i] = x.byteValue();
				} else {
					x = new Integer(src[i] ^ key[i]) + i;
					encstring[i] = x.byteValue();
				}
			}
			result = encode(encstring);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 解密
	public static String decrypt(String str) {
		byte[] src = decode(str);
		int j = key.length;
		int length = src.length;
		byte[] decstring = new byte[length];
		Integer y = null;
		try {
			for (int i = 0; i < length; i++) {
				int k = i / j;
				if (i >= j) {
					y = new Integer((src[i] - i % j) ^ key[i - j * k]);
					decstring[i] = y.byteValue();
				} else {
					y = new Integer((src[i] - i) ^ key[i]);
					decstring[i] = y.byteValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = new String(decstring);
		return result;
	}

	// base64编码
	// 输入为byte数组，输出为字符串
	private static String encode(byte[] src) {
		String result = null;
		try {
			result = encoder.encode(src);
		} catch (Exception e) {
		}
		return result;
	}

	// base64解码
	// 输入为字符串，输出为byte数组
	private static byte[] decode(String src) {
		byte[] result = null;
		try {
			result = decoder.decodeBuffer(src);
		} catch (IOException e) {
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			// String result="W1phFRgHER8SEiUQFO/6ugTBzOWqnc5rHnMqKV1bTXw=";

			//String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			//String enxml = encrypt(xml);
			//System.out.println(enxml);
			System.out.println(decrypt("VlUWIQ9LIhUmJx0kDx4qXy1nWl4dQyYgaBkzKW5kRYWINy05Ki40mDFAR4OGPgMDGTgXEQ0kXFJR"+
					"aCkcXxFobXJsiFl2HXJocFxOOX5riWl4eGwyNk07QC2EVgkPBActC1xgZ01PayYUYRFnanBoiVxs"+
					"IF8uIzkeWnwwKGd4eYN8ni8wK4OAWlthRlVfSyQVCh0dNVpYEx1rGzAuh2NnG2llYSgwIh8wKHxz"+
					"kXNofYBsfoV1AxA6DRsIWl4NEj4kXyUaYBtlbW1pjVptJXJpd3ttYy0hc3mdei40hC9HK4MAiKK6"+
					"UkwNEQ0RXFBkOlpaFB1rNywcOhdvIB8fKCY0WXUwKSMoMScqmj0vPi5AVkUFBAcIHikQXFAfGE9j"+
					"FWIjIWhDIkc8lDlTQ0hUVZWHkDE+f3uCfyk7Oi6EVgkhQAxdERYJDR5VTlxbPmxpaC4hTh0gWCE1"+
					"cntoYS0hbHRwcX1seTJ2gjY1CAMCGQ0YDRARKE5SZCIaXxBvZWxsiGVgcR8vGx8dOmZjenhpKzBD"+
					"ozxAKz+EVkUwHwckWg=="));
		} catch (Exception e) {
		}
	}
}
