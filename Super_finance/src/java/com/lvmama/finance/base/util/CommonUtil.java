package com.lvmama.finance.base.util;

import java.math.BigDecimal;

/**
 * 公用工具类 
 * 
 * @author yanggan
 *
 */
public class CommonUtil {

	/**
	 * 两个double数值相减（解决精度问题）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double doubleSubtract(double d1,double d2){
		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
		BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
		return bd1.subtract(bd2).doubleValue();
	}
}
