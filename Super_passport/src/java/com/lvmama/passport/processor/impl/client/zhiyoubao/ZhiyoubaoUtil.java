package com.lvmama.passport.processor.impl.client.zhiyoubao;

public class ZhiyoubaoUtil {
	public static StringBuilder buildElement(String tag ,String value){
		StringBuilder str = new StringBuilder();
		if(value==null)value="";
		str.append("<").append(tag).append(">").append(value).append("</").append(tag).append(">");
		return str;
	}
}
