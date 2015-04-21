package com.lvmama.passport.processor.impl.client.chimelong.util;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ChimelongEncrypt {

	/** 利用JDK提供的KEY生成器生成密钥 */
	public static String genKey() throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance("AES"); // 获取密匙生成器
		kg.init(128); // 初始化
		SecretKey key = kg.generateKey(); // 生成密匙，可用多种方法来保存密匙
		return ChimelongUtils.bytesToHexString(key.getEncoded());
	}

	/** 利用MD5对任何字符串签名，以生成128位密钥 */
	public static String genKey(String value) {
		return ChimelongMD5.getMD5(value);
	}

	private String key;

	public ChimelongEncrypt() {

	}

	public ChimelongEncrypt(String key) {
		setKey(key);
	}

	/** 设置Key,必须保证KEY为128位 */
	public void setKey(String key) {
		if (key.getBytes().length != 32)
			throw new java.lang.IllegalArgumentException("the size of key must have 128bit length");
		this.key = key;
	}
	public String getKey(){
		return this.key;
	}

	/** 从128位密钥中还原KEY */
	private SecretKey buildKey() {
		byte[] b = ChimelongUtils.hexStringToByte(key);
		SecretKeySpec spec = new SecretKeySpec(b, "AES");
		return spec;
	}

	private byte[] encode(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cp = Cipher.getInstance("AES"); // 创建密码器
		cp.init(Cipher.ENCRYPT_MODE, buildKey()); // 初始化

		byte[] ptext = str.getBytes("UTF8");
		ptext = cp.doFinal(ptext); // 加密
		return ptext;
	}

	public String strEncode(String str) throws Exception {
		return ChimelongUtils.bytesToHexString(encode(str));
	}

	private String decode(byte[] ctext) throws Exception {
		Cipher cp = Cipher.getInstance("AES"); // 创建密码器
		cp.init(Cipher.DECRYPT_MODE, buildKey()); // 初始化
		byte[] ptext = cp.doFinal(ctext); // 解密
	
		return new String(ptext, "UTF8"); // 重新显示明文
	}

	public String strDecode(String str) throws Exception {
		return decode(ChimelongUtils.hexStringToByte(str));
	}

	public static void main(String[] args) throws Exception {
		ChimelongEncrypt st = new ChimelongEncrypt(ChimelongEncrypt.genKey("111111"));
		String cstr = st.strEncode("0001|2009-01-01 00:00:00 |2009-01-01 00:00:00 ");
		System.out.println("密文:" + cstr);
	}
	



}

