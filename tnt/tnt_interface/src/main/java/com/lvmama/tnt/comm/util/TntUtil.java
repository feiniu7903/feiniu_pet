package com.lvmama.tnt.comm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class TntUtil {

	public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";

	public static Long parserLong(String str) {
		Long value = null;
		if (str != null) {
			try {
				value = Long.parseLong(str);
			} catch (Exception e) {
				value = null;
				e.printStackTrace();
			}
		}
		return value;
	}

	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {

		}
		return "";
	}

	public static String formatDate(Date date) {
		return formatDate(date, PATTERN_yyyy_MM_dd);
	}

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

	public static Date stringToDate(String dateStr) {
		return stringToDate(dateStr, PATTERN_yyyy_MM_dd);
	}

	public static String getPageUrl(String url) {
		url = url == null ? "login" : url;
		return "/WEB-INF/pages/" + url + ".jsp";
	}

	/**
	 * 随机生成指定位数的随机数
	 * 
	 * @return
	 */
	public static String generateNumber(int Num_length) {
		String no = "";
		int[] defaultNums = new int[Num_length];
		for (int i = 0; i < defaultNums.length; i++) {
			defaultNums[i] = i;
		}
		Random random = new Random();
		int[] nums = new int[8];
		int canBeUsed = 10;
		for (int i = 0; i < nums.length; i++) {
			int index = random.nextInt(canBeUsed);
			nums[i] = defaultNums[index];
			int temp = defaultNums[index];
			defaultNums[index] = defaultNums[canBeUsed - 1];
			defaultNums[canBeUsed - 1] = temp;
			canBeUsed--;
		}
		if (nums.length > 0) {
			for (int i = 0; i < nums.length; i++) {
				no += nums[i];
			}
		}
		return no;
	}

	public static List<Long> getLongList(String orderIds) {
		List<Long> ordIds = null;
		if (orderIds != null) {
			String[] ids = orderIds.split(",");
			if (ids != null && ids.length > 0) {
				ordIds = new ArrayList<Long>();
				for (String id : ids) {
					ordIds.add(TntUtil.parserLong(id));
				}
			}
		}
		return ordIds;
	}

	public static Long eval(String expr) {
		try {
			ExpressionParser parser = new SpelExpressionParser();
			Expression exp = parser.parseExpression(expr);
			Object object = exp.getValue();
			if (object != null) {
				return (long) Double.parseDouble(object.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
