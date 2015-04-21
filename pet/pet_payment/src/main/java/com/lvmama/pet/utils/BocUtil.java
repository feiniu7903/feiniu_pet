package com.lvmama.pet.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BocUtil {
	
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
	public static String formatOrderAmount(Long amount){
		final int MULTIPLIER = 100;
		String amountYuan = new BigDecimal(amount).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
		return amountYuan;
	}
	
	/**
	 * 格式化订单创建时间.
	 * <pre>
	 * 格式：YYYYMMDD24HHMMSS 其中时间为24小时格式，如下午3点15表示为151500
	 * </pre>
	 * @param creatTime 订单创建时间.
	 * @return
	 */
	public static String formatOrderTime(Date creatTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
		return sdf.format(creatTime);
	}
}
