package com.lvmama.hotel.model.longtengjielv;

import java.security.NoSuchAlgorithmException;

import com.lvmama.comm.utils.MD5;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 授权信息
 */
public class Authorization {
	private static Authorization authorization = new Authorization();
	
	/** 合作方或分销平台的AppID代码 */
	private String appID = WebServiceConstant.getProperties("longtengjielv.appID");
	/** 授权安全密钥（区分大小写） */
	private String securityKey = WebServiceConstant.getProperties("longtengjielv.securityKey");
	/** 登录账号，任意字符串(区分大小写) */
	private String userName = WebServiceConstant.getProperties("longtengjielv.userName");
	/** 密码（区分大小写）。用SHA1算法Hash并转换成Base64 */
	private String passWord = WebServiceConstant.getProperties("longtengjielv.passWord");
	/** 私钥签名（区分大小写） */
	private String signature = WebServiceConstant.getProperties("longtengjielv.signature");
	/** 预定编号 */
	private String memberNo = WebServiceConstant.getProperties("longtengjielv.memberNo");
	/** 预定密码 (MD5加密 ) */
	private String bookPassword = WebServiceConstant.getProperties("longtengjielv.bookPassword");
	/** AES/CBC/NoPadding128位模式数据加解密key */
	private String key = WebServiceConstant.getProperties("longtengjielv.key");
	/** AES/CBC/NoPadding128位模式数据加解密iv */
	private String iv = WebServiceConstant.getProperties("longtengjielv.iv");
	
	private Authorization() {}
	
	public static Authorization getInstance() {
		return authorization;
	}
	
	public String getAppID() {
		return appID;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public String getSignature() {
		return signature;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public String getBookPassword() throws NoSuchAlgorithmException {
		return MD5.encode(bookPassword);
	}

	public String getKey() {
		return key;
	}

	public String getIv() {
		return iv;
	}
}
