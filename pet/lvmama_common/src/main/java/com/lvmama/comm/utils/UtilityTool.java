package com.lvmama.comm.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 多用途工具.
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class UtilityTool {
	/**
	 * 构造函数.
	 */
	private UtilityTool() {
	}

	/**
	 * 校验.
	 * 
	 * @param obj
	 *            要校验的对象
	 * @return <code>true</code>代表非<code>null</code>并且不为空字符串，<code>false</code>
	 *         代表为 <code>null</code>或为空字符串
	 */
	public static boolean isValid(final Object obj) {
		boolean flag = false;
		if (null != obj && !"".equals(obj.toString().trim())) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 是否为空.
	 * 
	 * @param obj
	 *            要校验的对象
	 * @return <code>true</code>代表非空，<code>false</code>
	 *         代表为空，字符串不能为空字符串，集合的size必须大于0
	 */
	public static boolean isNotNull(final Object obj) {
		boolean flag = false;
		if (isValid(obj) && (obj instanceof Map)) {
			if (((Map) obj).size() > 0) {
				flag = true;
			}
		} else if (isValid(obj) && (obj instanceof Set)) {
			if (((Set) obj).size() > 0) {
				flag = true;
			}
		} else if (isValid(obj) && (obj instanceof List)) {
			if (((List) obj).size() > 0) {
				flag = true;
			}
		} else if (isValid(obj) && (obj instanceof Deque)) {
			if (((Deque) obj).size() > 0) {
				flag = true;
			}
		} else if (isValid(obj)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return <code>yyyy-MM-dd HH:mm:ss</code>格式的日期字符串
	 */
	public static String formatDate(final Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 *            要格式化的日期
	 * @param format
	 *            指定格式
	 * @return 指定格式的日期字符串
	 */
	public static String formatDate(final Date date, final String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 格式化日期.
	 * 
	 * @param format
	 *            指定格式
	 * @return 指定格式的当前系统时间字符串
	 */
	public static String formatDate(final String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(UtilityTool.class);

	/**
	 * 消息加密.
	 * 
	 * @param message
	 *            消息
	 * @param algorithm
	 *            加密算法
	 * @param charsetName
	 *            字符集
	 * @return 加密后的消息，对于不支持的加密算法或字符集，返回消息本身
	 */
	public static String messageEncrypt(final String message,
			final String algorithm, final String charsetName) {
		String encryptedMessage = message;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(message.getBytes(charsetName));
			encryptedMessage = bytes2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			LOG.error(e);
			e.printStackTrace();
		}
		return encryptedMessage;
	}

	/**
	 * ZERO.
	 */
	private static final String ZERO = "0";

	/**
	 * bytes2Hex.
	 * 
	 * @param bytes
	 *            bytes
	 * @return String
	 */
	private static String bytes2Hex(final byte[] bytes) {
		final StringBuilder stringBuilder = new StringBuilder();
		String temp;
		for (int i = 0; i < bytes.length; i++) {
			temp = (Integer.toHexString(bytes[i] & 0xFF));
			if (temp.length() == 1) {
				stringBuilder.append(ZERO);
			}
			stringBuilder.append(temp);
		}
		return stringBuilder.toString();
	}
}
