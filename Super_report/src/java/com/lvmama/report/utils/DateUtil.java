/**
 * 
 */
package com.lvmama.report.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author yangbin
 *
 */
public abstract class DateUtil {

	/**
	 * 返回 该日期的开始处
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date)
	{
		if(date==null)
			return null;
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	public static Date getDayEnd(Date date)
	{
		if(date==null)
			return null;
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MILLISECOND, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
}
