package com.lvmama.passport.overseaschinatown;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密解密
 * @author jim
 * @create 2013-3-22
 */
public class DesSecret {

	/**
	 * 密钥，长度必须是8的倍数 
	 */
	private static final String  SECRET_KEY = "DEFAULT_";
	/**
	 * 算法
	 */
	private final static String ALGORITHM = "DES";	

	/**
	 * 加密
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return	  返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * @param src	数据源
	 * @param key	密钥，长度必须是8的倍数
	 * @return		返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	/**
	 * 二行制转字符串
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
	/**
	 * 解密
	 * @param cipherText 密文
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public final static String decrypt(String cipherText) throws UnsupportedEncodingException, Exception {
		return new String(decrypt(hex2byte(cipherText.getBytes("UTF-8")), SECRET_KEY.getBytes()));
	}
	
	/**
	 * 解密
	 *@author jim
	 *@create 2013-3-22
	 * @param cipherText	密文
	 * @param secretKey	密钥
	 * @return
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	public final static String decrypt(String cipherText, String secretKey) throws UnsupportedEncodingException, Exception {
		return new String(decrypt(hex2byte(cipherText.getBytes("UTF-8")), secretKey.getBytes()),"UTF-8");
	}

	/**
	 * 加密
	 * @param password	没有加密的文本
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public final static String encrypt(String plainText) throws UnsupportedEncodingException, Exception {
		return byte2hex(encrypt(plainText.getBytes("UTF-8"), SECRET_KEY.getBytes()));
	}
	
	/**
	 * 加密 
	 *@author jim
	 *@create 2013-3-22  
	 * @param plainText	没有加密的文本
	 * @param secretKey	密钥
	 * @return
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	public final static String encrypt(String plainText, String secretKey) throws UnsupportedEncodingException, Exception {
		return byte2hex(encrypt(plainText.getBytes("UTF-8"), secretKey.getBytes()));
	}
}