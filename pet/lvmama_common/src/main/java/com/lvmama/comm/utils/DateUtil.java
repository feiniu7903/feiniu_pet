package com.lvmama.comm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author yuzhibing
 * 
 */
public class DateUtil {
	public static String[] MONTHS = new String[] { "一月", "二月", "三月", "四月",
			"五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };

	private static int[] DOMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };
	private static int[] lDOMonth = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };

	public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
	public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
	public static final String PATTERN_MMdd = "MM-dd";
	public static final String PATTERN_yyMMdd = "yy/MM/dd";
	public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_HH_mm_ss = "HH:mm:ss";

	/**
	 * 取日期是一个月中的几号.
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Object day = cal.get(Calendar.DATE);
		if (day != null) {
			return Integer.valueOf(day.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 判断两个时间的大小.
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean isCompareTime(Date startTime, Date endTime) {
		if (endTime.getTime() > startTime.getTime()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 取日期是月份.
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		Object day = cal.get(Calendar.MONTH);
		if (day != null) {
			return Integer.valueOf(day.toString());
		} else {
			return 0;
		}
	}

	public static int getDaysOfmonth(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		if ((cal.get(Calendar.YEAR) % 4) == 0) {
			if ((cal.get(Calendar.YEAR) % 100) == 0
					&& (cal.get(Calendar.YEAR) % 400) != 0)
				return DOMonth[cal.get(Calendar.MONTH)];
			return lDOMonth[cal.get(Calendar.MONTH)];
		} else
			return DOMonth[cal.get(Calendar.MONTH)];
	}

	public static Calendar getClearCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date getDateAfterDays(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, duration);
		return cal.getTime();
	}

	public static Date getDateBeforeHours(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -duration);
		return cal.getTime();
	}

	public static Date getDateAfterMinutes(long duration) {
		long curr = System.currentTimeMillis();
		curr = curr + duration * 60 * 1000;
		return new Date(curr);
	}

	public static String formatDate(Date date, String format) {
		return getFormatDate(date, format);
	}

	/**
	 * 格式时间
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatDate(Date date, String format) {
		if (date != null) {
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(date);
		} else {
			return null;
		}
	}

	public static String getZHDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return "星期一";
		case Calendar.TUESDAY:
			return "星期二";
		case Calendar.WEDNESDAY:
			return "星期三";
		case Calendar.THURSDAY:
			return "星期四";
		case Calendar.FRIDAY:
			return "星期五";
		case Calendar.SATURDAY:
			return "星期六";
		case Calendar.SUNDAY:
			return "星期日";
		default:
			return "";
		}
	}

	public static void main(String args[]) throws ParseException {
		/*
		 * Date endDate= new Date(); Calendar c = Calendar.getInstance();
		 * c.add(c.DATE, -5); List<Date> list =DateUtil.getDateList(c.getTime(),
		 * endDate); SimpleDateFormat sf = new SimpleDateFormat(); for (Date
		 * date : list) { //System.out.println(sf.format(date)); }
		 */
		// System.out.println(clean(new Date()));
		// System.out.println(dsDay_Date(new Date(),1));
		// System.out.println(getFormatDate(new Date(),"yyyyMMddHHmmss"));
		//
		// System.out.println("==="+getAfterDay(new Date()));
		//
		// System.out.println("-------"+getDaysBetween(new
		// Date(),getAfterDay(new Date())));

		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date1=accurateToDay(new Date());
		// Date date2=accurateToDay(sdf.parse("2013-01-07 12:12:12"));
		// System.out.println(sdf.format(date1)+"   "+sdf.format(date2));
		//
		//
		// System.out.println(date1.compareTo(date2)==0);
		Date date=new Date();
		Date begin = DateUtil.stringToDate("2014-5-9 09:00:00", "yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.stringToDate("2014-5-19 23:59:59", "yyyy-MM-dd HH:mm:ss");
		if (date.getTime() < begin.getTime()
				|| date.getTime() > end.getTime()) {
			 System.out.println(DateUtil.getFormatDate(end, "yyyy-MM-dd HH:mm:ss"));
		}
	}

	/**
	 * 当前日期小时相加或相减所得日期（+,-）操作,输入一个日期得到天数加减后的日期。
	 * 
	 * @param cal
	 * @return
	 */
	public static Date DsDay_Hour(Date date, Integer hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);

		return cal.getTime();
	}

	/**
	 * 把参数时间精确到天
	 * 
	 * @param cal
	 * @return
	 */

	public static Date accurateToDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return clearDateAfterDay(cal);
	}

	public static int getDayOfWeek(Calendar cal) {// 得到每月1号是星期几
		cal.set(Calendar.DATE, 1);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static Date getTheMiddle(Date date, int plus) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.DATE, 1);
		cal.add(cal.MONTH, plus);
		return cal.getTime();
	}

	public static Map<String, Object> getBeginAndEndDateByDate(Date date) {

		Calendar calClearDate = Calendar.getInstance();
		calClearDate.setTime(date);
		calClearDate.set(calClearDate.DATE, 1);
		date = calClearDate.getTime();
		Map<String, Object> map = new HashMap<String, Object>();
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int dayOfWeek = getDayOfWeek(cal);
		cal.set(Calendar.DATE, -(dayOfWeek - 2));
		map.put("beginDate", cal.getTime());
		cal.add(Calendar.DATE, 21);
		map.put("currPageDate", cal.getTime());
		cal.add(Calendar.DATE, 20);
		map.put("endDate", cal.getTime());
		return map;
	}

	/**
	 * 根据格式获取日期字符串.
	 * 
	 * @param format
	 * @param aDate
	 * @return
	 */
	public static String getDateTime(String format, Date aDate) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String returnValue = df.format(aDate);
		return returnValue;
	}

	/**
	 * 当前日期分钟相加或相减所得日期（+,-）操作,输入一个日期得到分钟加减后的日期。
	 * 
	 * @param months
	 * @return
	 */
	public static Date DsDay_Minute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int minutes = calendar.get(Calendar.MINUTE);
		calendar.set(Calendar.MINUTE, minutes + minute);
		return calendar.getTime();
	}

	/**
	 * 清理所有天后面的日期时间
	 * 
	 * @param c
	 * @return
	 */
	public static Date clearDateAfterDay(Calendar c) {
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		return c.getTime();
	}

	/**
	 * String转Date
	 * 
	 * @param sdate
	 *            日期字符串
	 * @param fmString
	 *            指定日期格式
	 * @return
	 */
	public static Date toDate(String sdate, String fmString) {
		DateFormat df = new SimpleDateFormat(fmString);
		try {
			return df.parse(sdate);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式不正确 ");
		}
	}

	/**
	 * 根据出生日期得到年龄
	 */
	public static int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow < monthBirth
				|| (monthNow == monthBirth && dayOfMonthNow < dayOfMonthBirth)) {
			age--;
		}
		return age;
	}

	// 计算两个日期之间有多少天
	public static int getDaysBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 当前日期相加或相减所得日期（+,-）操作
	 * 
	 * @param months
	 * @return Date
	 */
	public static Date dsDay_Date(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, days + day);
		Date cc = calendar.getTime();
		return cc;
	}

	/**
	 * 取年.
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatYear(Date date) {
		String str = null;
		if (date != null) {
			str = DateFormatUtils.format(date, "yyyy");
		}
		return str;
	}

	public static List<Date> getDateList(Date beginDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		List<Date> dateList = new ArrayList<Date>();

		dateList.add(beginDate);

		while (true) {
			if (clean(beginDate).equals(endDate)) {
				break;
			}
			calendar.add(Calendar.DATE, 1);
			Date currentDate = calendar.getTime();
			dateList.add(currentDate);
			if (currentDate.after(endDate)
					|| clean(currentDate).equals(clean(endDate))) {
				break;
			}

		}
		return dateList;
	}

	/**
	 * 得到前一天晚上6点钟.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getYestoday18Hour() throws ParseException {
		Date d = new Date();
		SimpleDateFormat simpleOldDate = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		ca.setTime(simpleOldDate.parse(simpleOldDate.format(d)));
		ca.add(Calendar.DATE, -1);
		ca.add(Calendar.HOUR, 18);
		return ca.getTime();
	}

	private static Date clean(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getAfterDay(Date date) {
		SimpleDateFormat simpleOldDate = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
		} catch (ParseException e) {
			ca.setTime(getDayStart(new Date()));
		}
		ca.add(Calendar.DATE, 1);
		return ca.getTime();

	}

	public static Date getBeforeDay(Date date) {
		SimpleDateFormat simpleOldDate = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
		} catch (ParseException e) {
			ca.setTime(getDayStart(new Date()));
		}
		ca.add(Calendar.DATE, -1);
		return ca.getTime();

	}

	/**
	 * 返回 该日期的开始处
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getDayEnd(Date date) {
		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 得到当前时间的23点.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEndBeforeOneH(Date date) {
		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		c.set(Calendar.MILLISECOND, 000);
		return c.getTime();
	}

	/**
	 * date1是否早于date2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean inAdvance(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		return date1.getTime() < date2.getTime();
	}

	/**
	 * 将日期和时分的两个时间合并到一起
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date mergeDateTime(Date date, Date time) {
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		Calendar timeCalendar = Calendar.getInstance();
		timeCalendar.setTime(time);
		dateCalendar.set(Calendar.HOUR, timeCalendar.get(Calendar.HOUR));
		dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
		dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		return dateCalendar.getTime();
	}

	public static Date getDateByStr(String dateStr, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date stringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 比较2个日期 前一个日期至少比后一个日期大一天以上
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDateLessOneDayMore(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		if (c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR)) {
			return true;
		} else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.MONTH) > c2.get(Calendar.MONTH)) {
			return true;
		} else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& c1.get(Calendar.DATE) > c2.get(Calendar.DATE)) {
			return true;
		}
		return false;
	}

	/**
	 * 将日期加年.
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date mergeDateTimeAddYear(Date date, Integer years) {
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		dateCalendar.set(dateCalendar.YEAR, dateCalendar.get(Calendar.YEAR)
				+ years);
		return dateCalendar.getTime();
	}

	/**
	 * 将日期加月.
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date mergeDateTimeAddMonth(Date date, Integer Months) {
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		dateCalendar.set(dateCalendar.MONTH, dateCalendar.get(Calendar.MONTH)
				+ Months);
		return dateCalendar.getTime();
	}

	public static Long getMinBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		Long min = 0L;
		if (d1.getTimeInMillis() > d2.getTimeInMillis()) {
			min = (d1.getTimeInMillis() - d2.getTimeInMillis()) / (1000 * 60);
		} else {
			min = (d2.getTimeInMillis() - d1.getTimeInMillis()) / (1000 * 60);
		}
		return min;
	}

	/**
	 * 相差秒数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long getMillisBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		Long min = 0L;
		if (d1.getTimeInMillis() > d2.getTimeInMillis()) {
			min = (d1.getTimeInMillis() - d2.getTimeInMillis())/1000;
		} else {
			min = (d2.getTimeInMillis() - d1.getTimeInMillis())/1000;
		}
		return min;
	}

	/**
	 * 主要是用于FAX_STRATEGY.
	 * 
	 * @param date
	 * @param faxStrategy
	 * @return
	 * @see 此方法由com.lvmama.common.vo.Constant.FAX_STRATEGY.valueOf(faxStrategy).
	 *      sendTime(Date)替代.
	 */
	@Deprecated
	public static Date getSpecNameDate(Date date, String faxStrategy) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		if (Constant.FAX_STRATEGY.VISIT_DAY_8.name().equals(faxStrategy)) {
			c.set(Calendar.HOUR_OF_DAY, 8);
			return c.getTime();
		} else if (Constant.FAX_STRATEGY.PREVIOUS_ONEDAY_12.name().equals(
				faxStrategy)) {
			c.add(Calendar.DATE, -1);
			c.set(Calendar.HOUR_OF_DAY, 12);
			return c.getTime();
		} else if (Constant.FAX_STRATEGY.PREVIOUS_ONEDAY_16.name().equals(
				faxStrategy)) {
			c.add(Calendar.DATE, -1);
			c.set(Calendar.HOUR_OF_DAY, 16);
			return c.getTime();
		} else {
			return new Date();
		}
	}

	/**
	 * 获取今天的日期，去掉时、分、秒
	 */
	public static Date getTodayYMDDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);// 24小时制
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	/**
	 * 得到今天几点
	 * @return
	 */
	public static int getTodayHourDate(){
		Calendar cal = Calendar.getInstance();
 	    int hour=cal.get(Calendar.HOUR_OF_DAY);
 	    return hour;
	}

	/**
	 * 获取今天的日期yyyy-MM-dd格式
	 * 
	 * @return
	 */
	public static Date getTodayDate() {
		return stringToDate(formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * 将分钟转成小时数,有小误差
	 */
	public static float convertToHours(Long minutes) {
		if (minutes == null) {
			return 0f;
		}
		String fStr = (new Float(minutes + "") / 60) + "";
		String res = fStr.substring(0, fStr.lastIndexOf(".") + 2);
		return Float.parseFloat(res);
	}

	/**
	 * 将小时数转为分钟,有小误差
	 */
	public static long convertToMinutes(Float hours) {
		if (hours == null) {
			return 0l;
		}
		long h = hours.intValue() * 60;
		Float f = (hours - new Float(hours.intValue())) * 60;
		String fStr = f.toString();
		return h + Long.parseLong(fStr.substring(0, fStr.indexOf(".")));
	}

	/**
	 * 年月日时分秒的日期转只有年月日的日期
	 */
	public static Date toYMDDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);// 24小时制
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 本月第一天
	 */
	public static Date getFirstdayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 本月最后一天
	 */
	public static Date getLastdayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		date = cal.getTime();
		return date;
	}

	/**
	 * date 减去当前日期 . 剩余0天0时0分
	 * 
	 * @return str
	 */
	public static String getRemainTimeByCurrentDate(Date date) {
		String str = "剩余0天0时0分";
		if (null != date) {
			Date d = new Date();
			long seconds = (date.getTime() - d.getTime()) / 1000;
			if (seconds > 0) { // 秒
				long day = seconds / (3600 * 24); // 天数
				long house = (seconds % (3600 * 24)) / 3600; // 小时
				long min = (seconds % (3600)) / 60;// 分
				return "剩余" + day + "天" + house + "时" + min + "分";
			}

		}
		return str;
	}

	/**
	 * 得到一个时间的具体日期 例如：两个月后的前一个星期 month = 3 ; day = -7
	 * 
	 * @param DATE
	 * @return DATE
	 * @author zhongshuangxi
	 */
	public static Date getTimesByTimes(int Month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, Month);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 返回当前时间 毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getDateTime(String date) {
		if (StringUtils.isEmpty(date)) {
			return 0l;
		}
		Date d = toDate(date, "yyyy-MM-dd");
		return d.getTime();
	}
	
	/**
	 * 比较2个日期 前一个日期至少比后一个日期大一天以上
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String compareDate(Date date1,Date date2){
		if(date1==null || date2==null){
			return null;
		}
		if(date1.getTime()-date2.getTime()<0){
			return null;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		String str="";
		if(c1.get(Calendar.DATE)-c2.get(Calendar.DATE)>0){
			str = str + (c1.get(Calendar.DATE)-c2.get(Calendar.DATE)) + "天";
		}
		if(c1.get(Calendar.HOUR)-c2.get(Calendar.HOUR)>0){
			str = str + (c1.get(Calendar.HOUR)-c2.get(Calendar.HOUR)) + "小时";
		}
		if(c1.get(Calendar.MINUTE)-c2.get(Calendar.MINUTE)>0){
			str = str + (c1.get(Calendar.MINUTE)-c2.get(Calendar.MINUTE)) + "分";
		}
		return str;	
	}
	
	/**
	 * 在特定的日期上，加入特定的时间点，返回新的日期
	 * @param date 特定日期
	 * @param time 给入的时间点
	 * 				  time 的格式举例：09:00
	 * @return
	 */
	public static Date defineDate(Date date, String time) throws Exception{
		try {
			String []strs = time.split(":");
			int hour =Integer.parseInt(strs[0]);
			int minute = Integer.parseInt(strs[1]);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.set(Calendar.MINUTE, minute);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			return c.getTime();
		} catch (Exception e) {
			throw new Exception("给入的时间点参数格式错误！");
		}
	}
}
