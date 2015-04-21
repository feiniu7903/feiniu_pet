package com.lvmama.clutter.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class DateUtil {
    private static Logger log = Logger.getLogger(DateUtil.class);
    
	public static String dateFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				log.info("dateUtil.date="+date);
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 返回字符型日期
	 */
	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			date = (java.util.Date) df.parse(dateStr);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 日期格式转换，如2012-12-12 转换成 2012年12月12日 formatString("2012-12-12","yyyy-MM-dd","yyyy年MM月dd日");
	 * @param date    日期字符串 
	 * @param srcData 原字符串格式
	 * @param desDate 目的字符串格式
	 * @return
	 */
	public static String formatString(String date,String srcData,String desDate) {
		try {
			Date d = DateUtil.parseDate(date,srcData);
			return DateUtil.dateFormat(d, desDate);
		}catch(Exception e){
			
		}
		return date;
	}
	
	/**
	 * String转Date
	 * @param sdate 日期字符串
	 * @param fmString 指定日期格式
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
	 * 日期加天数
	 */
	public static String getStrDayOfMonth(Date date,int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return dateFormat(cal.getTime(),"yyyy-MM-dd");
	}
	
	/**
	 * 获取月份的天数 ，比如date当前日期，day ：3  则返回20130803
	 */
	public static Date getDayOfMonth(Date date,int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return  cal.getTime();
	}
	
	/**
	 * 月份相加 
	 */
	public static Date getMontOfAdd(Date date,int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+month);
		return  cal.getTime();
	}
	
	/**
	 * 月份相加添加天数 
	 */
	public static Date getDateAddDay(Date date,int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+day);
		return  cal.getTime();
	}
	
	/**
	 * 添加小时数 
	 */
	public static Date getDateAddHour(Date date,int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+hours);
		return  cal.getTime();
	}
	
	/**
	 * 获取某个月的最大日期 . 
	 * @param date
	 * @return num 
	 */
	public static int getMaxDayOfMonth(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return  cal.getActualMaximum(Calendar.DAY_OF_MONTH);    
	}
	/**
	 * date 减去当前日期 . 剩余0天0时0分
	 * @return str
	 */
	public static String getRemainTimeByCurrentDate(Date date) {
		String str = "剩余0天0时0分"; 
		if(null != date ) {
			Date d = new Date();
			long seconds = (date.getTime() - d.getTime())/1000;
			if(seconds > 0) { // 秒
				long day = seconds/(3600*24); // 天数
				long house = (seconds%(3600*24)) /3600; // 小时
				long min = (seconds%(3600))/60;// 分
				return "剩" + day+"天"+house+"时"+min+"分";
			}
			
		}
		return str;
	}
	
	/**
	 * 获取当前时间秒数
	 * @return
	 */
	public static long getCurrentTimeLong() {
		return System.currentTimeMillis()/1000l;
	}
	
	
	public static float getMobileRefundCash(String productType,Long cashRefund) {
		if(null == cashRefund) {
			cashRefund = 0l;
		}
		return RefundUtils.getMobileRefundYuan(cashRefund,productType);
	} 
	
	/**
	 * 是否大于当前日期
	 * @param d    要比较的日期 
	 * @param day  天数 ，正数表示相加 ，负数减去某个天数.
	 * @return true  or  false 
	 */
	public static boolean afterCurrentDate(Date d,int day) {
		try {
			if(null == d) {
				return false;
			}
			// 如果d加一天在当前时间之后返回true . 
			return getDateAddDay(d,1).after(new Date());
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 返回当前时间 毫秒 
	 * @param date
	 * @return
	 */
	public static long getDateTime(String date) {
		if(StringUtils.isEmpty(date)) {
			return 0l;
		}
		Date d = com.lvmama.clutter.utils.DateUtil.parseDate(date,"yyyy-MM-dd");
		return d.getTime();
	}
	
	/**
	 * 返回当前时间 毫秒 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long getDateTime(String date,String format) {
		if(StringUtils.isEmpty(date)) {
			return 0l;
		}
		Date d = com.lvmama.clutter.utils.DateUtil.parseDate(date,format);
		return d.getTime();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date d = parseDate("2013-08-02","yyyy-MM-dd");
		getDateAddHour(d,1);
		System.out.print(dateFormat(d,"yyyy-MM-dd HH:mm:ss"));
		
		
		/*System.out.println(parseDate("2012-12-12","yyyy-MM-dd"));
		System.out.println(formatString("2012-12-12","yyyy-MM-dd","yyyy年MM月dd日"));
		
		System.out.print(getCurrentTimeLong());*/
	}

}
