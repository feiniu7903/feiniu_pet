package com.lvmama.search.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	public static void outputJson(List list, HttpServletResponse response) throws IOException{
		JSONArray json = JSONArray.fromObject(list);
		response.getWriter().print(json.toString());
	}
	
	public static void outputJson(Object obj, HttpServletResponse response) throws IOException{
		JSONObject json = JSONObject.fromObject(obj);
		
		response.getWriter().print(json.toString());
	}
	
	
public static String  convert(String value){
		
		if(value==null){
			value="0";
		}
		double d=new Double(value)*100;
		DecimalFormat df=new DecimalFormat("0.00");
		String returnval=df.format(d)+"%";
		
		return returnval;
		
	}
	
	public static String readUrl(String key) {
		String propFileName = new File("config.properties").getName();
		int index = propFileName.indexOf(".properties");
		if (index > 0) {
			propFileName = propFileName.substring(0, index);
		}
		ResourceBundle rsb = ResourceBundle.getBundle(propFileName, Locale.US);
		String url = rsb.getString(key);
		return url;
	}
}
