package com.lvmama.comm.utils;

import java.util.Hashtable;
import java.util.Map;

/**
 * 安全工具.
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public abstract class SecurityTool {
	/**
	 * map.
	 */
	private static Map<String, String> map = new Hashtable<String, String>();
	/**
	 * fill map.
	 */
	static {
		map.put("'", "\"\"");
		map.put("%", "[%]");
		//map.put("_", "[_]");
		map.put("\\^", "[^]");
		map.put("\\[\\]", "[[]");
	}

	/**
	 * 防范SQL注入.
	 * 
	 * @param str
	 *            存在风险的字符串
	 * @return 使用安全字符串替换危险字符串
	 */
	public static String preventSqlInjection(final String str) {
		String securityStr = str;
		for (String key : map.keySet()) {
			securityStr = securityStr.replaceAll(key, map.get(key));
		}
		return securityStr;
	}
}
