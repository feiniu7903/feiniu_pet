package com.lvmama.operate.util;

import java.util.ArrayList;
import java.util.List;

public class FrameUtil {

	/**
	 * 将集合通过joinStr连接起来
	 * @param iterable 需要连接的集合
	 * @param joinStr 连接符
	 * @param isRemoveNull 是否移除null的元素
	 * @return
	 */
	public static String concatIterable(Iterable<? extends Object> iterable,
			String joinStr,boolean isRemoveNull) {
		if (iterable == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Object object : iterable) {
			if(isRemoveNull && object==null){
				continue;
			}
			builder.append(object).append(joinStr);
		}
		return builder.length() >= 1 ? builder.substring(0,
				builder.length() - joinStr.length()) : builder.toString();
	}

	public static void main(String[] args) {
		List<String> aList = new ArrayList<String>();
		aList.add("你好");
		aList.add(null);
		aList.add("你好");
		System.out.println(concatIterable(aList, ",",true));
	}
}
