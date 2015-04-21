package com.lvmama.comm.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.Constant;

public final class UserUserUtil {
	/**
	 * 指数
	 */
	private static final int INDEX = 10;
	/**
	 * 明文密码前后截短长度
	 */
	private static final int TRUNK_LEN = 8;
	/**
	 * 静默注册时USER后面的数字的长度
	 */
	private static final int USERNAME_LENGTH = 8;

	/**
	 * 系统生成默认的用户
	 * @return 默认用户
	 */
	public static synchronized UserUser genDefaultUser() {
		UserUser user = getNewUser();

		user.setUserName("lv" + new Random().nextInt((int) Math.pow(INDEX, USERNAME_LENGTH)));
		resetPassword(user);
		return user;
	}

	/**
	 * 直接使用手机号生成的默认用户（用户名是lv+手机号、密码是手机号后6位）
	 * @param mobile 手机号
	 * @return 默认用户
	 */
	public static synchronized UserUser genDefaultUserByMobile(final String mobile) {
		UserUser user = getNewUser();
		user.setMobileNumber(mobile);
		user.setUserName("lv" + mobile);
		resetPassword(user);
		return user;
	}

	/**
	 * 校验码生成器
	 * @return 校验码
	 */
	public static synchronized String authenticationCodeGenerator() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Constant.AUTHENTICATION_CODE_LENGTH; i++) {
			sb.append(Constant.AUTHENTICATION_CODE_ELEMENT.charAt(
					random.nextInt(Constant.AUTHENTICATION_CODE_ELEMENT.length())));
		}
		return sb.toString();
	}

	/**
	 * 明文密码生成器
	 * @return 明文密码
	 */
	public static synchronized String passwordGenerator() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Constant.PASSWORD_LENGTH; i++) {
			sb.append(Constant.PASSWORD_ELEMENT.charAt(random.nextInt(Constant.PASSWORD_ELEMENT.length())));
		}
		return sb.toString();
	}

	/**
	 * 密码加密
	 * @param password 明文密码
	 * @return  加密后的密码
	 * @throws NoSuchAlgorithmException NoSuchAlgorithmException
	 */
	public static synchronized String encodePassword(final String password) throws NoSuchAlgorithmException {
		String password1 = "";
		if (StringUtils.isEmpty(password)) {
			password1 = passwordGenerator();
		} else {
			password1 = password;
		}
		password1 = new MD5().code(password1);
		return password1.substring(TRUNK_LEN, password1.length() - TRUNK_LEN);
	}

	/**
	 * 重置密码
	 * @param user 用户
	 * @return 用户
	 */
	public static synchronized UserUser resetPassword(final UserUser user) {
		if (null == user.getMobileNumber()) {
			user.setRealPass(passwordGenerator());
		} else {
			user.setRealPass(user.getMobileNumber().substring(user.getMobileNumber().length()-6, user.getMobileNumber().length()));
		}
		try {
			user.setUserPassword(encodePassword(user.getRealPass()));
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 获得新用户对象
	 * @return
	 */
	private static UserUser getNewUser() {
		UserUser user = new UserUser();
		String userNo = new UUIDGenerator().generate().toString();

		user.setUserNo(userNo);
		user.setGroupId(Constant.USER_CHANNEL.GP_FRONT.name());
		user.setNickName(user.getUserName());
		user.setIsEmailChecked("N");
		user.setIsMobileChecked("N");
		user.setIsLocked("N");
		user.setIsValid("Y");
		user.setCreatedDate(new Date());
		user.setPoint(0L);
		//user.setLastLoginDate(new Date());
		user.setUpdatedDate(new Date());
		user.setNameIsUpdate("N");//默认初次注册后的登录用户名可以修改一次
		user.setImageUrl("/uploads/header/default-photo.gif");
		return user;
	}
}
