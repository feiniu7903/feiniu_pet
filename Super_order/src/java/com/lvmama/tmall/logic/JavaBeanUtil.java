package com.lvmama.tmall.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class JavaBeanUtil {
	public static Map<String, Map<Method, String>> getMethodMap = new HashMap<String, Map<Method, String>>();

	
	public static synchronized void init(Class<?> c) {
		if (!getMethodMap.containsKey(c.toString())) {
			Field[] fields;
			Method[] methods;
			Map<String, String> fieldNameMap;
			Map<Method, String> methodMap;

			fields = c.getDeclaredFields();
			methods = c.getMethods();
			fieldNameMap = new HashMap<String, String>();
			methodMap = new HashMap<Method, String>();
			for (Field field : fields) {
				fieldNameMap.put(
						"get" + firstLetterUpperCase(field.getName()),
						field.getName());
			}
			for (Method method : methods) {
				String methodName = method.getName();
				if (fieldNameMap.containsKey(methodName)) {
					methodMap.put(method, fieldNameMap.get(methodName));
				}
			}
			getMethodMap.put(c.toString(), methodMap);
		}
	}
	
	/**
	 * 打印JavaBean
	 * @param c
	 * @param obj
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void printJavaBean(Class<?> c, Object obj)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (obj == null) {
			return ;
		}
		if (!getMethodMap.containsKey(c.toString())) {
			init(c);
		}
		StringBuffer sb = new StringBuffer();
		Map<Method, String> map = getMethodMap.get(c.toString());
		boolean b = false;
		Set<Method> set = map.keySet();
		for (Method method : set) {
			if (b) {
				sb.append(", ");
			} else {
				b = true;
			}
			sb.append(map.get(method));
			sb.append("=");
			// System.out.println(methodName);
			Object p = method.invoke(obj);
			if (p != null) {
				sb.append(p.toString());
			}
		}
		System.out.println(sb.toString());
	}

	/**
	 * 打印JavaBean
	 * 
	 * @param c
	 * @param obj
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void printJavaBean2(Class<?> c, Object obj)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (obj == null) {
			return;
		}
		Field[] fields;
		Method[] methods;
		Map<String, String> fieldNameMap;
		StringBuilder sb = new StringBuilder();

		fields = c.getDeclaredFields();
		methods = c.getMethods();
		fieldNameMap = new HashMap<String, String>();
		for (Field field : fields) {
			fieldNameMap.put("get" + firstLetterUpperCase(field.getName()),
					field.getName());
		}

		boolean b = false;
		for (Method method : methods) {
			String methodName = method.getName();
			if (fieldNameMap.containsKey(methodName)) {
				if (b) {
					sb.append(", ");
				} else {
					b = true;
				}
				sb.append(fieldNameMap.get(methodName));
				sb.append("=");
				System.out.println(methodName);
				Object p = method.invoke(obj);
				if (p != null) {
					sb.append(p.toString());
				}
			}
		}
		System.out.println(sb.toString());
	}

	/**
	 * 首写字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterUpperCase(String str) {
		if (StringUtils.isNotBlank(str)) {
			if (str.length() == 1) {
				return str.toUpperCase();
			} else {
				return str.substring(0, 1).toUpperCase() + str.substring(1);
			}
		}
		return str;
	}
	
	public static Object getJavaBeanParam(Class<?> c, Object obj, String methodName) {
		Object o = null;
		try {
			Method method = c.getMethod(methodName);
			o = JavaBeanUtil.getJavaBeanParam(obj, method);
		} catch (Exception e) {
			System.err.println(methodName);
			e.printStackTrace();
		} 
		return o;
	}
	
	/**
	 * 以JSON格式输出
	 * @param response
	 */
	public static void response2Json(HttpServletResponse response,
			Object responseObject) {
		//将实体对象转换为JSON Object转换
		JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public static Object getJavaBeanParam(Object obj, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(obj);
	}
}
