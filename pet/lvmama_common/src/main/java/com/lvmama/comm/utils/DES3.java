package com.lvmama.comm.utils;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/** 
 * DESede对称加密算法.
 * 
 * @see DESede也就是所谓的Triple-DES,俗称3DES
 * @author qiuguobin
 */
public class DES3 {
	public static final String KEY_ALGORITHM = "DESede";
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
	
	/** 
	 * 生成密钥.
	 * 
	 * @see Java6只支持56位密钥
	 */
	public static String initkey() throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(168);
		SecretKey secretKey = kg.generateKey();
		return Base64.encodeBase64String(secretKey.getEncoded());
	}
	
	/** 
	 * 转换密钥.
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}
	
	/** 
	 * 加密数据.
	 * 
	 * @param data 待加密数据
	 * @param key 经过base64编码的密钥,使长度符合DES3规范
	 * @return 加密后的数据
	 */
	public static String encrypt(String data, String key) throws Exception {
		Key k = toKey(Base64.decodeBase64(key));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
	}
	
	/** 
	 * 解密数据.
	 * 
	 * @param data 待解密数据
	 * @param key 经过base64编码的密钥,使长度符合DES3规范
	 * @return 解密后的数据
	 */
	public static String decrypt(String data, String key) throws Exception {
		Key k = toKey(Base64.decodeBase64(key));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return new String(cipher.doFinal(Base64.decodeBase64(data)));
	}
	
	public static void main(String[] args) throws Exception {
		String source = "你好";
		System.out.println("原文: " + source);
		
		String key = initkey();
		System.out.println("密钥: " + key);
		
		String encryptData = encrypt(source, key);
		System.out.println("加密: " + encryptData);
		
		String decryptData = decrypt(encryptData, key);
		System.out.println("解密: " + decryptData);
	}
}
