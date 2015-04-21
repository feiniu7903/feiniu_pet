package com.lvmama.sso.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Brian
 *
 */
public final class DateUtil {
	/**
	 * 一天的毫秒数
	 */
    private static final long DAY_MIL = 24 * 60 * 60 * 1000L;

	/**
	 * 比较两个日期的相差天数，会严格按照24小时制计算相差天数
	 * @param firstDate 开始日期
	 * @param secondDate 结束日期
	 * @return 相差天数
	 */
	public static int getIntervalDays(final Date firstDate, final Date secondDate) {
		if (null == firstDate || null == secondDate) {
			return -1;
		}
		long intervalMilli = secondDate.getTime() - firstDate.getTime();
		return (int) (intervalMilli / DAY_MIL);
	}

	/**
	 * 比较两个日期的相差天数，忽略24小时制
	 * @param firstDate 开始日期
	 * @param secondDate 结束日期
	 * @return 相差天数
	 */
	public static int getIntervalDaysIgnoreHours(final Date firstDate, final Date secondDate) {
	       Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(firstDate);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(secondDate);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       return day2 - day1;
	    }

}
