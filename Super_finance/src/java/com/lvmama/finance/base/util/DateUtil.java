package com.lvmama.finance.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 字符串转换到时间格式
	 * @param dateStr 需要转换的字符串
	 * @param formatStr 需要格式的目标字符串  举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException 转换异常
	 */
	public static Date stringToDate(String dateStr,String formatStr){
		DateFormat sdf=new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	} 
	/**
	 * 日期转字符串
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToString(Date date,String formatStr){
		DateFormat sdf=new SimpleDateFormat(formatStr);
		return sdf.format(date);
	} 
	
	/**
	 * 获取今天的日期，去掉时、分、秒
	 * @return
	 */
	public static Date getTodayDate(){
		return stringToDate(dateToString(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}
	
	
}
