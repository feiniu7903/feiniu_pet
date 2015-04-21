package com.lvmama.finance.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
/**
 * Finance上下文
 * @author yanggan
 *
 */
public class FinanceContext {

	private final static ThreadLocal<Map<String, Object>> financeStackLocal = new ThreadLocal<Map<String, Object>>();

	public static Map<String, Object> getFinanceStack() {
		return financeStackLocal.get();
	}

	public static void setFinanceStack(Map<String, Object> stack) {
		financeStackLocal.set(stack);
	}

	public static Object findValue(String key) {
		return getFinanceStack().get(key);
	}

	public static void putValue(String key, Object value) {
		getFinanceStack().put(key, value);
	}

	public static Model getModel() {
		return (Model) findValue("model");
	}

	@SuppressWarnings("rawtypes")
	public static Map getCookie() {
		return (Map) findValue("cookieMaps");
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) findValue("request");
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) findValue("response");
	}

	public static HttpSession getSession() {
		return (HttpSession) (getRequest().getSession());
	}

	public static PageSearchContext getPageSearchContext() {
		return (PageSearchContext) findValue("PageSearchContext");
	}

	public static void remove() {
		financeStackLocal.remove();
	}
}
