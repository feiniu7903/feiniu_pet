package com.lvmama.comm.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlObjectUtil {
	public static Object xml2Bean(String xml,Object obj){
		XStream xStream = new XStream(new DomDriver());
		Object object =null;
		try{
			object =xStream.fromXML(xml, obj);
			if(object instanceof Map){
				return map2Type((Map)object);
			}
		}catch(Exception e){
			object = ContractComplementXMLUtil.analyticContractAdditionXML(xml);
		}
		return object;
	}
	public static Object xml2Bean(String xml,String cname){
		Object obj = null;
		try {
			obj = Class.forName(cname);
		} catch (ClassNotFoundException e) {
			obj = new java.util.HashMap<String,Object>();
		}
		return xml2Bean(xml,obj);
	}
	public static String bean2Xml(Object obj){
		XStream xStream = new XStream(new DomDriver());
		return xStream.toXML(obj);
	}
	public static final String Object2String(Object obj){
		if(null==obj){
			return "";
		}else if(obj instanceof String){
			return (String)obj;
		}else if(obj instanceof Integer || obj instanceof Double || obj instanceof Float){
			return String.valueOf(obj);
		}else if(obj instanceof String[]){
			if(((String[])obj).length>0)
				return String.valueOf(((String[])obj)[0]);
			else
				return "";
		}else if(obj instanceof List){
			if(((List)obj).size()>0)
			return String.valueOf(((List)obj).get(0));
			else
				return "";
		}
		return String.valueOf(obj);
	}
	public static final Map<String,String> map2Type(Map<String,Object> map){
		Map<String,String> resultMap = new HashMap<String,String>();
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			Object value=map.get(key);
			resultMap.put(key, Object2String(value));
		}
		return resultMap;
	}
	public static final Map<String,Object> mapEntry2Object(Map<String,String> map){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			Object value=map.get(key);
			resultMap.put(key, value);
		}
		return resultMap;
	}
}
