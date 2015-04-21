package com.lvmama.comm.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

/**
 * DES算法
 */
public class DES {
	private static Logger logger = Logger.getLogger(DES.class);
	/**
	 * 
	 * @return DES算法密钥
	 */
	public static byte[] generateKey() throws Exception{
		try {

			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// 生成一个DES算法的KeyGenerator对象
			KeyGenerator kg = KeyGenerator.getInstance("DES");
			kg.init(sr);

			// 生成密钥
			SecretKey secretKey = kg.generateKey();

			// 获取密钥数据
			byte[] key = secretKey.getEncoded();

			return key;
		} catch (NoSuchAlgorithmException e) {
			logger.error("DES算法，生成密钥出错!");
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * 加密函数
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return 返回加密后的数据
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {

		try {

			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// using DES in ECB mode
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

			// 执行加密操作
			byte encryptedData[] = cipher.doFinal(data);

			return encryptedData;
		} catch (Exception e) {
			logger.error("DES算法，加密数据出错!");
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * 解密函数
	 * 
	 * @param data
	 *            解密数据
	 * @param key
	 *            密钥
	 * @return 返回解密后的数据
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception{
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;

			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// using DES in ECB mode
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

			// 正式执行解密操作
			byte decryptedData[] = cipher.doFinal(data);

			return decryptedData;
		} catch (Exception e) {
			logger.error("DES算法，解密出错!");
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * 加密函数
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return 返回加密后的数据
	 */
	public static byte[] CBCEncrypt(byte[] data, byte[] key, byte[] iv) throws Exception {

		try {
			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// 若采用NoPadding模式，data长度必须是8的倍数
			// Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

			// 用密匙初始化Cipher对象
			IvParameterSpec param = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);

			// 执行加密操作
			byte encryptedData[] = cipher.doFinal(data);

			return encryptedData;
		} catch (Exception e) {
			logger.error("DES算法，加密数据出错!");
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * 解密函数
	 * 
	 * @param data
	 *            解密数据
	 * @param key
	 *            密钥
	 * @return 返回解密后的数据
	 */
	public static byte[] CBCDecrypt(byte[] data, byte[] key, byte[] iv)throws Exception {
		try {
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// using DES in CBC mode
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// 若采用NoPadding模式，data长度必须是8的倍数
			// Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

			// 用密匙初始化Cipher对象
			IvParameterSpec param = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, param);

			// 正式执行解密操作
			byte decryptedData[] = cipher.doFinal(data);

			return decryptedData;
		} catch (Exception e) {
			logger.error("DES算法，解密出错!");
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * 艺龙信用卡号加密 DES对称加密算法CBC模式--加密
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return toHexString(cipher.doFinal(message.getBytes("UTF-8")));
	}

	/**
	 * 艺龙信用卡号解密 DES对称加密算法CBC模式--解密
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String message, String key) throws Exception {

		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}

	public static void main(String[] args) {
		try {
			String key = "12345678";
			String result = DES.encrypt("12345#6789012345", key);
			System.out.println(result);
			System.out
					.println("8e519cf90bf4240f7f653afff4f6d658f5e402a4ff2581a7"
							.equals(result));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
