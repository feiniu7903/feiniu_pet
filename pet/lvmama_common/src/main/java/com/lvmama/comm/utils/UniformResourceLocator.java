/*
 * Copyright © 2009 www.lvmama.com. 景域旅游运营集团版权所有.
 */
package com.lvmama.comm.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘松竹
 */
public final class UniformResourceLocator {
	/**
	 * log.
	 */
	private static Log log = LogFactory.getLog(UniformResourceLocator.class);
	/**
	 * UTF-8.
	 */
	private static final String UTF8 = "UTF-8";
	/**
	 * ISO-8859-1.
	 */
	private static final String ISO88591 = "ISO-8859-1";

	/**
	 * 构造方法.
	 */
	private UniformResourceLocator() {
	}

	/**
	 * 编码.
	 *
	 * @param str
	 *            需要编码的URL
	 * @return String
	 */
	public static String encode(final String str) {
		String encode = str;
		try {
			encode = URLEncoder.encode(str, UTF8);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return encode;
	}

	/**
	 * 解码.
	 *
	 * @param str
	 *            需要解码的URL
	 * @return String
	 */
	public static String decode(final String str) {
		String decode = str;
		try {
			decode = URLDecoder.decode(
					new String(str.getBytes(ISO88591), UTF8), UTF8);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return decode;
	}

	/**
	 * 只对URL中的QueryString进行编码.
	 *
	 * @param str
	 *            需要编码的URL
	 * @return String
	 */
	public static String encodeQueryStringOnly(final String str) {
		String encode = str;
		int indexOf = str.indexOf("?");
		try {
			if (-1 < indexOf && str.length() > indexOf) {
				encode = str.substring(0, indexOf + 1)
						+ URLEncoder.encode(str.substring(indexOf + 1), UTF8);
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return encode;
	}
}
