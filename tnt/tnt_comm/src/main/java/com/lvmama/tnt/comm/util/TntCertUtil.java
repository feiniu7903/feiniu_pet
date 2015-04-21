package com.lvmama.tnt.comm.util;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;

public final class TntCertUtil {
	/**
	 * 明文密码前后截短长度
	 */
	private static final int TRUNK_LEN = 8;

	/**
	 * 校验码生成器
	 * 
	 * @return 校验码
	 */
	public static synchronized String authenticationCodeGenerator() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Constant.AUTHENTICATION_CODE_LENGTH; i++) {
			sb.append(Constant.AUTHENTICATION_CODE_ELEMENT.charAt(random
					.nextInt(Constant.AUTHENTICATION_CODE_ELEMENT.length())));
		}
		return sb.toString();
	}

	/**
	 * 明文密码生成器
	 * 
	 * @return 明文密码
	 */
	public static synchronized String passwordGenerator() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Constant.PASSWORD_LENGTH; i++) {
			sb.append(Constant.PASSWORD_ELEMENT.charAt(random
					.nextInt(Constant.PASSWORD_ELEMENT.length())));
		}
		return sb.toString();
	}

	/**
	 * 密码加密
	 * 
	 * @param password
	 *            明文密码
	 * @return 加密后的密码
	 * @throws NoSuchAlgorithmException
	 *             NoSuchAlgorithmException
	 */
	public static synchronized String encodePassword(final String password)
			throws NoSuchAlgorithmException {
		String password1 = "";
		if (StringUtils.isEmpty(password)) {
			password1 = passwordGenerator();
		} else {
			password1 = password;
		}
		password1 = new MD5().code(password1);
		return password1.substring(TRUNK_LEN, password1.length() - TRUNK_LEN);
	}

	public static synchronized String serialGenerator() {
		return passwordGenerator();
	}

}
