package com.ejingtong.help;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.Log;

public class Tools {

	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// yyyy-MM-dd HH:mm:ss 参考格式
	public static String getFormateDate(String strFormate) {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormate);
		return sdf.format(new Date());
	}

	public static String getCurrentDate() {
		return getFormateDate("yyyy-MM-dd HH:mm:ss");
	}

	//重启手机
	public static void reStart(){
		try {
			Runtime.getRuntime().exec("su -c reboot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// MD5加密，32位
	public static String MD5(String str) {
//		Log.i("", "md5md5前：" + str);
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
//		Log.i("", "md5md5后：" + hexValue.toString());
		return hexValue.toString();
	}
}
