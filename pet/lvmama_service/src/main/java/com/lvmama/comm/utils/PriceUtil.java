package com.lvmama.comm.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PriceUtil {

	/**
	 * 把钱转换成分
	 * @param price
	 * @return
	 */
	public static long convertToFen(float price){
		return  PriceUtil.convertToFen(Float.toString(price));
	}
	/**
	 * 把钱转换成分
	 * @param price
	 * @return
	 */
	public static long convertToFen(String price){
		return  new BigDecimal(price).multiply(new BigDecimal(100f)).longValue();
	}
	/**
	 * 格式化订单金额.
	 * <pre>
	 * 格式：整数位不前补零,小数位补齐2位即：不超过10位整数位+1位小数点+2位小数
	 * 无效格式如123，.10，1.1
	 * 有效格式如1.00，0.10
	 * </pre>
	 * @param amount 以分为单位的金额
	 * @return
	 */
	public static String trans2YuanStr(Long amount){
		final int MULTIPLIER = 100;
		String amountYuan = new BigDecimal(amount).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
		return amountYuan;
	}
	
	/**
	 * 得到整形元
	 * @param ammount
	 * @return
	 */
	public static Long getLongPriceYuan(Long ammount){
		return Long.valueOf(ammount/100);
	}
	
	public static long convertToFen(BigDecimal price){
		return price.multiply(new BigDecimal(100)).longValue();
	}
	
	/**
	 * 
	 * @param price
	 * @return
	 */
	public static float convertToYuan(final Long price) {
		if(price==null){
			return 0f;
		}
		BigDecimal p = new BigDecimal(price);
		return p.divide(new BigDecimal(100),2,BigDecimal.ROUND_UP).floatValue();
	}

	public static String formatDecimal(Object price) {
		DecimalFormat df = new DecimalFormat("####.00");
		String strPrice = df.format(price);
		return strPrice;
	}
	
	public static float convertToYuan(final BigDecimal price){
		return convertToYuan(price.longValue());
	}
	
	/**
	 * 把字符串钱转换成分
	 * @param price
	 * @return
	 */
	public static long moneyConvertLongPrice(String price){
		return PriceUtil.convertToFen(new BigDecimal(price));
	}
		
	public static String moneyConvertStr(long price){
		BigDecimal bigDecimal = new BigDecimal(price);
		return (bigDecimal.floatValue()/100)+"";
	}
	
	public static Long convertToHoursForDistribution(Long minutes) {
		int min = Integer.parseInt(minutes.toString());
		int lesMin = min%60;
		if(lesMin != 0) {
			return minutes/60 + 1;
		} else {
			return minutes/60;
		}
	}

}
