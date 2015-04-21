package com.lvmama.comm.utils;

import org.apache.commons.lang3.StringUtils;

public class ReplaceEnter {

	public static String replaceEnterRn(String content,String ContentType){
		if(StringUtils.isEmpty(content))return "";
		String cont = "";
		if("MANAGERRECOMMEND".equals(ContentType)){
			String[] str = content.split("\n");
			if(str!=null&&str.length>0){
				for (int i = 0; i < str.length; i++) {
					cont += "<li>"+str[i]+"</li>";
				}
			}
		}else {
			cont = content.replaceAll("\n", "<br/>");
		}
		return cont;
	}
	
	public static String replaceEnterBr(String content){
		if(StringUtils.isNotEmpty(content))
			return content.replaceAll("<br/>", "\n");
		return "";
	}
}
