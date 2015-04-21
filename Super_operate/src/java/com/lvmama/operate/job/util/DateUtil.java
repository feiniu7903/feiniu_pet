package com.lvmama.operate.job.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 日期转换工具类,常用的日期转换方法都有
 * 
 * @author Ready 2012-10-17
 */
public class DateUtil {
	public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
	public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
	public static final String PATTERN_MMdd = "MM-dd";
	public static final String PATTERN_yyMMdd = "yy/MM/dd";
	public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_HH_mm_ss = "HH:mm:ss";

	public static final long DAY_IN_MILLISECOND = 24 * 60 * 60 * 1000;

	public static Date addDay(Date date, int day) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, day);
	}

	public String getDurationOfTwoTime(Calendar start, Calendar end) {
		String str = "";
		Long duration = ((end.getTime().getTime() - start.getTime().getTime())) / 1000;

		if (duration > 0) {

		}
		return str;
	}

	public static Date addWeek(Date date, int week) {
		return org.apache.commons.lang.time.DateUtils.addWeeks(date, week);
	}

	public static Date addMonth(Date date, int month) {
		return org.apache.commons.lang.time.DateUtils.addMonths(date, month);
	}

	public static Date getDate(Date date) {
		return parseDate(format(date, "yyyyMMdd"), "yyyyMMdd");
	}

	/**
	 * 获取系统当前日期
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	public static Date addTime(Date date, int hours, int mins, int seconds) {
		Date d = org.apache.commons.lang.time.DateUtils.addHours(date, hours);
		d = org.apache.commons.lang.time.DateUtils.addMinutes(d, mins);
		return org.apache.commons.lang.time.DateUtils.addSeconds(d, seconds);
	}

	public static Date getPreMonthDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getTime();
	}

	public static Date getPreYearDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year - 1);
		return calendar.getTime();
	}

	public static int get(Date date, int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateString, String pattern) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(dateString,
					new String[] { pattern });
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 
	 * @param date
	 * @param patterns
	 * @return
	 */
	public static Date parseDate(String dateString, String[] patterns) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(dateString,
					patterns);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp parseDateToTimestamp(String dateString) {
		if (dateString == null || "".equals(dateString))
			return null;
		try {
			Date date = parseDate(dateString, PATTERN_yyyy_MM_dd);
			java.sql.Timestamp dateTime = new java.sql.Timestamp(date.getTime());
			return dateTime;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTimestamp(Timestamp t, String sFmt) {
		if (t == null)
			return "";
		t.setNanos(0);
		DateFormat ft = new SimpleDateFormat(sFmt);
		String str = "";
		try {
			str = ft.format(t);
		} catch (NullPointerException e) {
		}
		return str;

	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date newDate(int year, int month, int date) {
		return parseDate("" + year + month + date, month >= 10 ? "yyyyMMdd"
				: "yyyyMdd");
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static String format(int year, int month, int date, String pattern) {
		return DateFormatUtils.format(newDate(year, month, date), pattern);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, PATTERN_yyyy_MM_dd_HH_mm);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 判断两个日期的大小,d1>d2返回1,d1=d1返回0,d1<d2返回-1
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compareDate(Date d1, Date d2) {
		if (d1.getTime() == d2.getTime()) {
			return 0;
		}
		return d1.getTime() - d2.getTime() > 0 ? 1 : -1;
	}

	/**
	 * 比较两个时间的时分秒部分,格式HH24_mm_ss
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static int compareTime(String t1, String t2) {
		Date d1 = DateUtil.parseDate("2000-01-01 " + t1,
				PATTERN_yyyy_MM_dd_HH_mm_ss);
		Date d2 = DateUtil.parseDate("2000-01-01 " + t2,
				PATTERN_yyyy_MM_dd_HH_mm_ss);
		return compareDate(d1, d2);
	}

	/**
	 * compare the two dates, and return the subtraction between d1 and d2(d1 -
	 * d2) result > 0 when d1 > d2 and result < 0 when d1 < d2
	 * 
	 * @param d1
	 *            Date
	 * @param d2
	 *            Date
	 * @return int
	 */
	public static int compareDateOnDay(Date d1, Date d2) {
		if (d1.getTime() == d2.getTime())
			return 0;
		d1 = org.apache.commons.lang.time.DateUtils.truncate(d1, Calendar.DATE);
		d2 = org.apache.commons.lang.time.DateUtils.truncate(d2, Calendar.DATE);
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		return (int) ((l1 - l2) / DAY_IN_MILLISECOND);
	}

	/**
	 * compare the two dates, and return the subtraction between the dates'
	 * month always return > 0
	 * 
	 * @param d1
	 *            Date
	 * @param d2
	 *            Date
	 * @return int
	 */
	public static int compareDateOnMonth(Date d1, Date d2) {
		if (d1.getTime() == d2.getTime()) {
			return 0;
		}
		int flag = -1;
		// compare the dates, and put the smaller before
		if (d1.getTime() > d2.getTime()) {
			Date temp = d1;
			d1 = d2;
			d2 = temp;
			flag = 1;
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int y1 = c1.get(Calendar.YEAR);
		int y2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int months = 0;
		if (y1 == y2) {

			months = month2 - month1;

		} else {

			months = (y2 - y1 - 1) * 12 + (12 - month1) + month2;

		}

		return months * flag;
	}

	/**
	 * judge the year whether is leap year
	 * 
	 * @param year
	 *            int year
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return true;
		}
		return false;

	}

	/**
	 * return the days of the year gevin
	 * 
	 * @param year
	 *            int year
	 * @return int
	 */
	public static int getYearDays(int year) {
		if (isLeapYear(year)) {
			return 366;
		}
		return 365;
	}

	/**
	 * judge whether the two dates are in same day
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		return org.apache.commons.lang.time.DateUtils.isSameDay(date1, date2);
	}

	public static Date truncate(Date d, int field) {
		return org.apache.commons.lang.time.DateUtils.truncate(d, field);
	}

	public static boolean isLastDayOfMonth(Date date) {
		return isFirstDayOfMonth(addDay(date, 1));
	}

	public static boolean isFirstDayOfMonth(Date date) {
		return get(date, Calendar.DAY_OF_MONTH) == 1;
	}

	// add
	public static Date getLastMonthDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date getLastMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date getFirstMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date getFirstMonthDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	@SuppressWarnings("static-access")
	public static Date getFirstWeekDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(calendar.DAY_OF_WEEK, 1);
		return addDay(calendar.getTime(), 1);
	}

	@SuppressWarnings("static-access")
	public static Date getFirstWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.DAY_OF_WEEK, 1);
		return addDay(calendar.getTime(), 1);
	}

	@SuppressWarnings("static-access")
	public static Date getLastWeekDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(calendar.DAY_OF_WEEK, 7);
		return addDay(calendar.getTime(), 1);
	}

	@SuppressWarnings("static-access")
	public static Date getLastWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.DAY_OF_WEEK, 7);
		return addDay(calendar.getTime(), 1);
	}

	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回和date后面的月份中和dayOfMonth具有相同day_of_month的日期
	 * 
	 * @param date
	 * @param dayOfMonth
	 * @return
	 */
	public static Date getNextSameDayOfMonth(Date date, int dayOfMonth) {
		Date tempDate = DateUtil.addDay(date, 1);
		if (DateUtil.getDayOfMonth(tempDate) == dayOfMonth) {
			return tempDate;
		} else {
			return getNextSameDayOfMonth(tempDate, dayOfMonth);
		}
	}

	/**
	 * 获取和date具有相同的dayOfMonth的最近的日期(在date之后日期)
	 * 
	 * @param date
	 * @param dayOfMonth
	 * @return
	 */
	public static Date getMinDinSameDayOfMonth(Date date, int dayOfMonth) {
		int lastDayOfMonth = DateUtil.getDayOfMonth(DateUtil
				.getLastMonthDay(date));
		int dayOfMontOfDate = DateUtil.getDayOfMonth(date);
		if (dayOfMontOfDate <= dayOfMonth && dayOfMonth <= lastDayOfMonth) {
			return DateUtil.addDay(date, dayOfMonth - dayOfMontOfDate);
		} else {
			return getNextSameDayOfMonth(date, dayOfMonth);
		}
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getInterval(Date d1, Date d2) {
		int betweenDays = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}

		return betweenDays;
	}

	public static List<Date> getDateList(Date startDate, Date endDate) {
		List<Date> dates = new ArrayList<Date>();

		int betweenDays = DateUtil.getInterval(startDate, endDate);
		for (int i = 0; i <= betweenDays; i++) {
			Date day = DateUtil.addDay(startDate, i);

			if (day.getTime() >= startDate.getTime()
					&& day.getTime() <= endDate.getTime()) {
				dates.add(day);
			}
		}

		return dates;
	}

	public static int getMonthInterval(Date startDate, Date endDate) {
		int betweenMonths = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(startDate);
		c2.setTime(endDate);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(startDate);
		}

		int y1 = c1.get(Calendar.YEAR);
		int y2 = c2.get(Calendar.YEAR);

		int m1 = c1.get(Calendar.MONTH);
		int m2 = c2.get(Calendar.MONTH);

		if (y2 > y1) {
			betweenMonths += (y2 - y1) * 12;
		}
		betweenMonths += (m2 - m1);

		return betweenMonths;
	}

	public static int getWeekInterval(Date startDate, Date endDate) {
		int betweenWeeks = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(startDate);
		c2.setTime(endDate);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(startDate);
		}

		int y1 = c1.get(Calendar.YEAR);
		int y2 = c2.get(Calendar.YEAR);

		int w1 = c1.get(Calendar.WEEK_OF_YEAR);
		int w2 = c2.get(Calendar.WEEK_OF_YEAR);

		betweenWeeks += (w2 - w1);
		int betweenYears = y2 - y1;
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenWeeks += c1.getMaximum(Calendar.WEEK_OF_YEAR);
		}

		return betweenWeeks;
	}

	public static int getDaysBetween(java.util.Calendar d1,
			java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	public static void main(String[] args) {
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();

		calStart.setTime(DateUtil.parseDate("2010-07-01",
				DateUtil.PATTERN_yyyy_MM_dd));
		calEnd.setTime(DateUtil.parseDate("2010-07-05",
				DateUtil.PATTERN_yyyy_MM_dd));

		System.out.println(DateUtil.getDaysBetween(calStart, calEnd));

	}

	public static String switchToWeekDay(int day) {
		switch (day - 1) {
		case 0:
			return "星期日";

		case 1:
			return "星期一";

		case 2:
			return "星期二";

		case 3:
			return "星期三";

		case 4:
			return "星期四";

		case 5:
			return "星期五";

		case 6:
			return "星期六";

		default:
			return "";
		}
	}

	/**
	 * 
	 * @param duration
	 *            格式为 hh:MM:ss:FF
	 * @return 返回总帧数 1秒25帧
	 */
	public static int getFrameFromString(String hhmmssStringduration) {
		int frame = 0;
		String[] str = hhmmssStringduration.split(":");
		// hh:MM:ss:FF
		Calendar cal = Calendar.getInstance();
		Long start = cal.getTime().getTime();
		cal.add(Calendar.HOUR, Integer.valueOf(str[0]));
		cal.add(Calendar.MINUTE, Integer.valueOf(str[1]));
		cal.add(Calendar.SECOND, Integer.valueOf(str[2]));
		Long end = cal.getTime().getTime();
		frame = ((end.intValue() - start.intValue()) / 1000) * 25
				+ Integer.valueOf(str[3]).intValue();
		return frame;
	}

	public static String getEndTiemFormDuartion(String playDate,
			String startDate, Long duration) {
		String[] str = startDate.split(":");
		// 格式化
		for (int i = 0; i < str.length; i++) {
			String s = str[i];
			if (s.length() == 1) {
				s = "0" + s;
			}
			str[i] = s;
		}
		String tmpStartDate = str[0] + ":" + str[1] + ":" + str[2];
		@SuppressWarnings("unused")
		String frame = str[3];

		long hour = duration / 90000;
		duration = duration % 90000;

		long min = duration / 1500;
		duration = duration % 1500;

		long sec = duration / 25;
		duration = duration % 25;

		Calendar cal = Calendar.getInstance();
		Date dt = DateUtil.parseDate(playDate + " " + tmpStartDate,
				DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
		cal.setTime(dt);
		cal.add(Calendar.HOUR, Integer.valueOf(String.valueOf(hour)));
		cal.add(Calendar.MINUTE, Integer.valueOf(String.valueOf(min)));
		cal.add(Calendar.SECOND, Integer.valueOf(String.valueOf(sec)));
		String tmp = DateUtil.format(cal.getTime(), DateUtil.PATTERN_HH_mm_ss);
		tmp = tmp + ":" + duration;
		return tmp;
	}

	/**
	 * 返回 1970-01-01 00:00:00
	 * 
	 * @return
	 */
	public static Date getBeginingDate() {
		return parseDate("1970-01-01 00:00:00", PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	public static String getHHmmssStringFormDuartion(Long duration) {
		long hour = duration / 90000;
		duration = duration % 90000;

		long min = duration / 1500;
		duration = duration % 1500;

		long sec = duration / 25;
		duration = duration % 25;

		String dur = ":0";
		if (duration.intValue() < 10) { // duration 是各位
			dur = dur + duration;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR, Integer.valueOf(String.valueOf(hour)));
		cal.set(Calendar.MINUTE, Integer.valueOf(String.valueOf(min)));
		cal.set(Calendar.SECOND, Integer.valueOf(String.valueOf(sec)));
		String tmp = DateUtil.format(cal.getTime(), DateUtil.PATTERN_HH_mm_ss);
		tmp = tmp + dur;
		return tmp;
	}

	/**
	 * 转换字符类型yyyy-MM-dd HH:mm:ss.ttt 转换为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param target
	 * @return
	 */
	public static String strDateFormat(String target) {
		if (target == null || "".equals(target.trim())) {
			return "";
		} else {
			Calendar c = Calendar.getInstance();

			try {
				c.setTime(new SimpleDateFormat(PATTERN_yyyy_MM_dd_HH_mm_ss)
						.parse(target));

			} catch (ParseException e) {

				e.printStackTrace();
			}

			return format(c.getTime());
		}
	}

}