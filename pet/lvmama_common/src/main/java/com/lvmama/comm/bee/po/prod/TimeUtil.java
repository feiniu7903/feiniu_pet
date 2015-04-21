/**
 * 
 */
package com.lvmama.comm.bee.po.prod;

/**
 * @author yangbin
 *
 */
public class TimeUtil {
	
	public static final String TOKEN="===";

	/**
	 * 转换最少天与最多天的数量到字符串.
	 * @param min
	 * @param max
	 * @return
	 */
	public static String conver(Time min,Time max){
		StringBuffer sb=new StringBuffer();
		sb.append(min.toString());
		sb.append(TOKEN);
		sb.append(max.toString());
		return sb.toString();
	}
}
