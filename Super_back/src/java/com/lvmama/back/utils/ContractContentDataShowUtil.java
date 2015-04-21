package com.lvmama.back.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ContractContentDataShowUtil {
	/**
	 * 根据模板，数据进行组装合同或补充条款，同时删除已使用的值
	 * @param template
	 * @param data
	 * @param usedKeys
	 * @param prefix
	 * @return
	 * @throws Exception
	 */
	public static String composeMessage(final String template,final Map<String,Object> data,final String prefix) throws Exception {  
		List<String> usedKeys = new ArrayList<String>();
		String regex = "\\$\\{(.+?)\\}";   
		Pattern pattern = Pattern.compile(regex);   
		Matcher matcher = pattern.matcher(template); 
		
		/*  
		 * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序  
		 * 存储起来。  
		 */  
		StringBuffer sb = new StringBuffer();   
		while (matcher.find()) {   
			String name = matcher.group(1);//键名   
			String value = null == data.get(name) ? "" : Object2String(data.get(name));//键值   
			if(areaList().contains(name)){
				value = "<textarea name="+addPrefix(prefix,name)+" style=\"width: 600px; height: 100px;\">"+value+"</textarea>";
			}else if(hideList().contains(name)){
				value = "<input type=\"hidden\" name="+addPrefix(prefix,name)+" value='"+value+"'/>";
			}else if(fixedList().contains(name)){
				value = "<input type=\"hidden\" value='"+value+"'  name="+addPrefix(prefix,name)+"/><input type=\"text\" value='"+value+"'  readOnly=\"true\" disabled=\"disabled\"/>";
			}else if(finalList().contains(name)){
				value = "<input type=\"hidden\" name="+addPrefix(prefix,name)+" value='"+value+"'/>"+value;
			}else if(onlyShowList().contains(name)){
			}else{
				value = "<input type=\"text\" name="+addPrefix(prefix,name)+" value='"+value+"'/>";
			}
			if(!"template".equals(name)){
				usedKeys.add(name);
			}
			matcher.appendReplacement(sb, value);   
		}   
		   
		matcher.appendTail(sb);   
		for(String key:usedKeys){
			data.remove(key);
		}
		return sb.toString();   
	}	
	
	private static final String addPrefix(final String prefix,final String key){
		return "\""+prefix+"['"+key+"']"+"\"";
	}
	/**
	 * 输入框
	 * @return
	 */
	private static final List<String> areaList(){
		List<String> list = new ArrayList<String>();
		list.add("alltouristInfo");
		list.add("allTraveller");
		list.add("allProductItem");
		list.add("addition");
		list.add("description");
		list.add("description_1");
		list.add("travelExpensesDetail");
		return list;
	}
	
	/**
	 * 隐藏
	 * @return
	 */
	private static final List<String> hideList(){
		List<String> list = new ArrayList<String>();
		list.add("eContractTemplate");
		return list;
	}
	
	private static final List<String> fixedList(){
		List<String> list = new ArrayList<String>();
		list.add("contractNo");
		list.add("orderId");
		list.add("signDate");
		return list;
	}
	private static final List<String> finalList(){
		List<String> list = new ArrayList<String>();
		return list;
	}
	private static final List<String> onlyShowList(){
		List<String> list = new ArrayList<String>();
		list.add("complement");
		list.add("template");
		list.add("imageUrl");
		return list;
	}
	public static final String Object2String(Object obj){
		if(null==obj){
			return "";
		}else if(obj instanceof String){
			return (String)obj;
		}else if(obj instanceof Integer || obj instanceof Double || obj instanceof Float){
			return String.valueOf(obj);
		}else if(obj instanceof String[]){
			return String.valueOf(((String[])obj)[0]);
		}else if(obj instanceof List){
			return String.valueOf(((List)obj).get(0));
		}
		return String.valueOf(obj);
	}
}
