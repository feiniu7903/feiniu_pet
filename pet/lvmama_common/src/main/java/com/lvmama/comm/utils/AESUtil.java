package com.lvmama.comm.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AESUtil {

	private static final String DECRYPT_MODE = "AES/CBC/NoPadding";

	private static SecretKeySpec aesKeySpec;

	private static IvParameterSpec ivSpec;

	public AESUtil(String key, String iv) {
		aesKeySpec = new SecretKeySpec(key.getBytes(), "AES");
		ivSpec = new IvParameterSpec(iv.getBytes());
	}

	public String decryptAES(String input) {
		try {
			Cipher cipher = Cipher.getInstance(DECRYPT_MODE);
			cipher.init(Cipher.DECRYPT_MODE, aesKeySpec, ivSpec);
			byte[] result = cipher.doFinal(Hex.decodeHex(input.toCharArray()));
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String encryptAES(String input) {
		try {
			Cipher cipher = Cipher.getInstance(DECRYPT_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, aesKeySpec, ivSpec);
			// 不满16位用0补齐
			byte[] temp = input.getBytes("utf-8");
			int len = temp.length;
			if (len % 16 != 0) {
				len = len + (16 - len % 16);
			}
			byte[] target = new byte[len];
			System.arraycopy(temp, 0, target, 0, temp.length);

			byte[] outText = cipher.doFinal(target);
			return Hex.encodeHexString(outText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		AESUtil aesUtil = new AESUtil("U+qFnPhJbiOTkX+e", "U+qFnPhJbiOTkX+e");
		String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?><CNResponse><HotelID>16929</HotelID><RoomID>52</RoomID><Type>4</Type><DatePhase><D><B></B><E></E></D></DatePhase></CNResponse>";
		String ciphertext = aesUtil.encryptAES(input);
		String plaintext = aesUtil.decryptAES(ciphertext);
		System.out.println(ciphertext);
		System.out.println(plaintext);
	}
}
