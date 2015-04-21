/**
 * 版权 深圳市泰久信息系统有限公司
 * 保留所有权利。
 */
package com.lvmama.passport.overseaschinatown;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *  @author jim
* @create 2013-3-22
*/
public class Base64 {

	private final static String DEFAULT_CHARSET = "UTF-8";

	/**
	 * base64解码
	 * 
	 * @param src
	 *            String 原字符串
	 * @param charset
	 *            字符集
	 * @return String base64解码后的字符串
	 */
	public static String decoder(String src, String charset) {
		try {
			BASE64Decoder de = new BASE64Decoder(); // base64解码
			String tmp = src.replaceAll(" ", "+");
			String ret = new String(de.decodeBuffer(tmp), charset);
			return ret;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * base64解码(字符集为UTF-8)
	 * 
	 * @param src
	 *            String 加密后的字符串
	 * @return String base64解码后的字符串
	 */
	public static String decoder(String src) {
		try {
			BASE64Decoder de = new BASE64Decoder(); // base64解码
			String tmp = src.replaceAll(" ", "+");
			String ret = new String(de.decodeBuffer(tmp), DEFAULT_CHARSET);
			return ret;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * base64编码
	 * 
	 * @param src
	 *            String 原字符串
	 * @param charset
	 *            字符集
	 * @return String base64编码后的字符串
	 */
	public static String encoder(String src, String charset) {
		BASE64Encoder en = new BASE64Encoder(); // base64编码
		try {
			String ret = en.encode(src.getBytes(charset));
			ret = Base64.trimRN(ret);
			return ret;
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * base64编码(字符集为UTF-8)
	 * 
	 * @param src
	 *            String 原字符串
	 * @return String base64编码后的字符串
	 */
	public static String encoder(String src) {
		BASE64Encoder en = new BASE64Encoder(); // base64编码
		try {
			String ret = en.encode(src.getBytes(DEFAULT_CHARSET));
			ret = Base64.trimRN(ret);
			return ret;
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 
	 * 功能：去除字符串的回车
	 * 
	 * @param src
	 * @return
	 */
	public static String trimRN(String src) {
		if (src == null || src.trim().equals("")) {
			return src;
		}
		Pattern p = Pattern.compile("\r|\n");
		Matcher m = p.matcher(src);
		String ret = m.replaceAll("");
		return ret;
	}
}
