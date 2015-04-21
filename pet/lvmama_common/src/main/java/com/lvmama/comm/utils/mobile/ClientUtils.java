package com.lvmama.comm.utils.mobile;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * 移动端
 */
public class ClientUtils {

	/**
	 * 随机生成幸运码 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String[] getLuckyCode(int start, int end) {
		Random r = new Random();
		int temp1, temp2;
		int length = end - start+1;
		int[] send = new int[length];
		for(int i = start;i <= end;i++) {
			send[i - start] = i;
		}
		
		int len = send.length;
		String returnValue[] = new String[length];
		for (int i = 0; i < length; i++) {
			temp1 = Math.abs(r.nextInt()) % len;
			returnValue[i] = intToFixLengthString(send[temp1]);
			temp2 = send[temp1];
			send[temp1] = send[len - 1];
			send[len - 1] = temp2;
			len--;
		}
		return returnValue;
	}
	
	/**
	 * 不足6位前面补0
	 * @param code
	 * @return
	 */
	public static String intToFixLengthString(int code) {
		NumberFormat formatter = new DecimalFormat("000000");
		return formatter.format(code);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//getLuckyCode(2000,300000);
	}

	

}
