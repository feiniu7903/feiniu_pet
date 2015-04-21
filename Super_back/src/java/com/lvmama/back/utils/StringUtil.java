package com.lvmama.back.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author <a href="mailto:matt@raibledesigns.com">huangl</a>
 */
public final class StringUtil {
	public StringUtil() {
	}
	
	/**
	 * Validation Mobile Number
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		return com.lvmama.comm.utils.StringUtil.validMobileNumber(mobiles);
	}
	/**
	 * 避免出现生成xml出现问题。控制输入的特殊字符
	 * @param value
	 * @return
	 */
	public static boolean hasIllegalCharacter(String value){
		return StringUtils.containsAny(value, '<','>','&');
	}

	/**
	 * 判断传入的字符串是否为空串
	 * 
	 * @return
	 */
	public static boolean isEmptyString(String str) {
		return str == null ? true : str.trim().equals("") ? true : false;
	}

	/**
	 * 替换模板中的变量。变量的标识符为${}。 例如，模板中${name}变量将会被Map列表中键名为name的键值替换，如果Map列表中不存在所需要 的键名，则会被替换成空。
	 * 
	 * @param template
	 *            模板
	 * @param data
	 *            参数列表
	 * @return
	 * @throws Exception
	 */
	public static String composeMessage(String template, Map<String, Object> data) throws Exception {
		String regex = "\\$\\{(.+?)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(template);

		/*
		 * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
		 */
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String name = matcher.group(1);// 键名
			String value = null == data.get(name) ? "" : data.get(name).toString();// 键值
			if (value == null) {
				value = "";
			} else {
				value = value.replaceAll("\\$", "\\\\\\$");
				//value = value.replaceAll("\\", "\\\\");
			}

			matcher.appendReplacement(sb, value);
		}

		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * converts text string to html string (convert \r\n, \r, \n, \t, space)
	 * @param text
	 * @return
	 */
	public static String convertTextStringToHtmlString(String text)
	{
		if(null == text || "".equals(text))
		{
			return text;
		}
		text = text.replaceAll("\r\n", "<br/>");
		text = text.replaceAll("\r", "<br/>");
		text = text.replaceAll("\n", "<br/>");
		text = text.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		text = text.replaceAll(" ", "&nbsp;");
		return text;
	}
}
