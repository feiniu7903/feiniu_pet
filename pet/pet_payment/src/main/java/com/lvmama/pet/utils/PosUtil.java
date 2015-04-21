package com.lvmama.pet.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PosUtil {
	public final static String ENCODING = "UTF-8";
	public final static int HEAD_LENGTH = 4;
	public final static int MAC_LENGTH = 8;
	public final static String mackey="08912ab34cd56ef7";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64 加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * 根据开始和结束 添加null值.
	 * 
	 * @param begin
	 * @param end
	 * @param key
	 * @return
	 */
	public static String markStr(int begin, int end) {
		String str = "";
		if (end > begin) {
			for (int i = 0; i < end - begin; i++) {
				str = str + " ";
			}
		}
		return str;
	}

	/**
	 * 订单号格式为20位.
	 * 
	 * @param orderId
	 * @return
	 */
	public static String markOrderId(String orderId) {
		for (int i = 0; i < 20 - orderId.length(); i++) {
			orderId = " " + orderId;
		}
		System.out.println(orderId.length());
		return orderId;
	}

	/**
	 * 接受的数据内容除去MAC验证.
	 * 
	 * @param code
	 * @return
	 */
	public static String acceptMessage(String code) {
		return code.substring(0, code.length() - MAC_LENGTH);
	}

	/**
	 * 接受到要签名的内容.
	 * 
	 * @param code
	 * @return
	 */
	public static String acceptCode(String code) {
		return code.substring(HEAD_LENGTH, code.length() - MAC_LENGTH);
	}

	/**
	 * 查
	 * 
	 * @param message
	 * @return
	 */
	private static String notifyMessage(String mess) {
		byte[] c;
		String s = "";
		try {
			c = PosUtil.decryptBASE64(mess);
			s = new String(c, ENCODING);
		} catch (Exception e) {
			// log.info("accept code Exception !!");
		}
		return s;
	}

	/**
	 * 返回接受到的MAC签名内容.
	 * 
	 * @param code
	 * @return
	 */
	public static String acceptCodeSignature(String code) {
		return code.substring(code.length() - MAC_LENGTH, code.length());
	}

	/**
	 * 判断POS传过来的MAC和我们加密的MAC是否一致.
	 * @return
	 */
	public static boolean isSucc(String mess){
		String a1 = notifyMessage(mess);
		String mac = acceptCode(a1);
		return acceptCodeSignature(a1).equals(GetMac(mac, mackey));
	}
	

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813，
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String bytes2hex(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	
	public static byte[] des(byte[] key, byte[] source) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(56);
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(source);
	}

	

	public static String GetMac(String data, String key) {
		try {
			String xor = new String(hex2ascii(GetXorCode(data.getBytes())));
			String xor1 = xor.substring(0, 8).toUpperCase();
			String xor2 = xor.substring(8).toUpperCase();
			byte[] xor3 = xor(des(ascii2hex(key.getBytes()), xor1.getBytes()),
					xor2.getBytes());
			return new String(hex2ascii(des(ascii2hex(key.getBytes()), xor3)))
					.substring(0, 8).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] GetXorCode(byte data[]) {
		byte[] re = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		if (data == null)
			return null;
		int count = data.length / 8;
		int recount = data.length % 8;
		int i;
		for (i = 0; i < count; i++) {
			re = xor(re, sub(data, i * 8, 8));
		}
		if (recount != 0)
			re = xor(re, sub(data, i * 8, recount));
		return re;
	}

	public static byte[] ascii2hex(byte bs[]) {
		byte res[] = new byte[bs.length / 2];
		int i = 0;
		for (int n = bs.length; i < n; i += 2)
			res[i / 2] = (byte) Integer.parseInt(new String(bs, i, 2), 16);

		return res;
	}

	public static byte[] xor(byte a[], byte b[]) {
		byte[] re = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		int i;
		if (a.length < 8)
			return null;
		for (i = 0; i < (b.length > 8 ? 8 : b.length); i++)
			re[i] = (byte) (a[i] ^ b[i]);
		for (; i < 8; i++)
			re[i] = (byte) (a[i] ^ re[i]);
		return re;
	}

	public static byte[] sub(byte bs[], int first, int length) {
		length = first + length <= bs.length ? length : bs.length - first;
		byte nb[] = new byte[length];
		for (int i = 0; i < length; i++)
			nb[i] = bs[i + first];

		return nb;
	}

	/**
	 * add by chenchunjiang
	 * */
	public static byte[] hex2ascii(byte bs[]) {
		byte res[] = new byte[bs.length * 2];
		for (int i = 0; i < bs.length; i++) {
			int ti = bs[i];
			ti = ti >= 0 ? ti : ti + 256;
			String t = Integer.toHexString(ti);
			if (t.length() < 2)
				t = (new StringBuilder("0")).append(t).toString();
			res[i * 2] = (byte) t.charAt(0);
			res[i * 2 + 1] = (byte) t.charAt(1);
		}

		return res;
	}

	public static void main(String[] args) throws Exception {
		// String
		// a1="MTk1IDEwMDAxMTEyMjIzMzM0NDQ1NTUxVjAwMDAwMjAwMDAwMzFWMDAwMDAwMDE5NkU3OTI"+
		// "xODk2NUVCNzJDOTJBNTQ5REQ1QTMzMDExMiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA"+
		// "gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBDQ0ZGRDlCQQ==";
		// String a2="MzkwIDIwMDAzMDEzMTAwNzAxMTg1ODEzMzEyNDAwMjAwMDAwODgxNjMwMDEwICAwICAgICAgICAgICAgICAgICAgICAgICAgICAgI"+
		// "CAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgI"+
		// "CAxNzU2NDk3ODg5ICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg"+
		// "ICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg" +
		// "ICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA2QTE0MzUxRQ==";
		//
		// String a3="MzAwIDMwMDAzMDEzMTAwNzAxMTg1ODEzMzEyNDAwMzAwMDI2ODIwMTIwNDE2MTQxODQxMjAxMjA0MTYwMDA2NTEgICAgICAgICAgICAgICA"+
		// "gICAgICAgICAgICAgICAgICAgICAgICA5OTk5OTk5OTk5OTkwMCAgICAgICAgICAgICAgICAgIDAwMDEyNTExMDAwMDY3NjU5MCAgICAgICAgMDA"+
		// "wMDAwMDAwMDAwODk1NDA0MjAgIDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA"+
		// "gMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA5OEVENzQxMQ==";
		//
		// String aa = notifyMessage(a2);
		// // notifyMessage(a2);
		// // notifyMessage(a3);
		// System.out.println("解析:"+aa);
		// System.out.println("截取要加密的数据 :"+aa.substring(4,
		// aa.length()-MAC_LENGTH));
		// System.out.println("截取加密的数据 :"+aa.substring(aa.length()-MAC_LENGTH,aa.length()));
		// // String str=markMac(aa);

		// OrdPayment order =new OrdPayment();
		// System.out.println(order.toString().trim());null != obj
		// System.out.println(null == order);

		String a = "MTk1IDEwMDAxMTEyMjIzMzM0NDQ1NTUxVjAwMDAwMjAwMDAwMzFWMDAwMDAwMDE5NkU3OTI"
				+ "xODk2NUVCNzJDOTJBNTQ5REQ1QTMzMDExMiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA"
				+ "gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBDQ0ZGRDlCQQ==";
		String a1 = notifyMessage(a);
		String a2 = acceptCode(a1);
		System.err.println(GetMac(a2, mackey));

	}

}
