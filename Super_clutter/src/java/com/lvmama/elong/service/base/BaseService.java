package com.lvmama.elong.service.base;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.DateFormatDeserializer;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.elong.model.EnumLocal;
import com.lvmama.elong.utils.BaseRequst;
import com.lvmama.elong.utils.Http;
import com.lvmama.elong.utils.Tool;


public abstract class BaseService<T,T2> {
	public final Logger logger = Logger.getLogger(this.getClass());
	public static ParserConfig parserConfig = new com.alibaba.fastjson.parser.ParserConfig(); 
	static { 
		DateFormatDeserializer d = new DateFormatDeserializer();
		
		parserConfig.putDeserializer(Date.class, d);
	} 
	
	
	public Class < T2 >  T2Class = null;
	@SuppressWarnings("unchecked")
	public BaseService(){
		ParameterizedType type =(ParameterizedType) (getClass().getGenericSuperclass());
		
		 java.lang.reflect.Type[]  types = type.getActualTypeArguments(); 
		 if(types!= null && types.length >1) {
			 T2Class  =  (Class < T2 > ) types[ 1 ];
		 }
		
	}
	
	
	public abstract String method() ;
	//public abstract T getConditon();
	public abstract boolean isRequiredSSL();
	
	
	public String format = "json";
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

	public String appUser = Constant.getInstance().getValue("elong.hotel.app.user");
	public String appKey = Constant.getInstance().getValue("elong.hotel.app.key");
	public String appSecret = Constant.getInstance().getValue("elong.hotel.app.secret");
	
	public String responseData;
	
	public T2 getResult(T t) {
		
			if(appUser.equals("")) {
				System.out.println("please set appUser and keys.");
				return null;
			}
			T2 result = null;
	    
	    try {
	    	
	    	//T t = this.getConditon();
	    	BaseRequst<T> req = new BaseRequst<T>();
	    	req.Version = 1.08;
	    	req.Local = EnumLocal.zh_CN;
	    	req.Request = t;
	    	
			String str = null;
			if(format.equals("xml")) {
				str = this.objectToXml(req);
			}else {
				str = this.objectToJson(req);
			}
			
			//str = this.objectToXml(req);
			logger.debug(str);
	        
	        long epoch = System.currentTimeMillis()/1000;
	        String sig = Tool.md5(epoch + Tool.md5(str + appKey) + appSecret);
	        
	        
	        
	        String url = "";
	        if(this.isRequiredSSL()){
	        	url += "https://api.elong.com/rest?format="+ this.format +"&method=";
		        url += this.method();
		        url += "&user="+ appUser +"&timestamp=";
		        url += epoch;
		        url += "&signature=";
		        url += sig;
		        url += "&data=" + Tool.encodeUri(str);
	        }else{
	        	url += "http://api.elong.com/rest?format="+ this.format +"&method=";
		        url += this.method();
		        url += "&user="+ appUser +"&timestamp=";
		        url += epoch;
		        url += "&signature=";
		        url += sig;
		        url += "&data=" + Tool.encodeUri(str);
	        }
	        
	        logger.info(url);
	        
	        if(this.isRequiredSSL()){
	        	responseData = HttpsUtil.requestGet(url);
	        }else{
	        	responseData = Http.Send("GET", url, "");
	        }
			
			responseData = responseData.trim();
			responseData = responseData.replaceAll("\n", "");
			responseData = responseData.replaceAll("0001-01-01T00:00:00", "2001-01-01T00:00:00");
			
			if(format.equals("xml")) {
				responseData = responseData.replaceFirst("Result", "ApiResult");
				responseData = responseData.substring(0, responseData.length() - "/Result>".length()) + "/ApiResult>";
			}
			
			logger.debug(responseData);
	        
	        result = this.jsonToObject(responseData);
	        
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return result;
	    
	}
	
	
	public String objectToJson(Object value) {
		
		String str = null;
		
		str = JSON.toJSONStringWithDateFormat(value, "yyyy-MM-dd HH:mm:ss");
		
		return str;
	}
	
	@SuppressWarnings("unchecked")
	public T2 jsonToObject(String str) {
		
		return (T2)JSON.parseObject(str, T2Class ,Feature.AllowISO8601DateFormat);
	}
	
	@SuppressWarnings("unchecked")
	public T2 xmlToObject(String str) {
		JAXBContext context = null;		
        T2 obj = null;
        
		try {
			context = JAXBContext.newInstance(T2Class);
			StringReader reader = new StringReader(str);
	        Unmarshaller unmar = context.createUnmarshaller();
			obj = (T2)unmar.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	public String objectToXml(Object value) {
		String str = null;
		
		JAXBContext context = null;
	    
	    StringWriter writer = null;
		try {
			
		    context = JAXBContext.newInstance(value.getClass());
			Marshaller mar = context.createMarshaller();
	        writer = new StringWriter();
	        mar.marshal(value, writer);
	        str = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}finally {
			if(context != null) context = null;
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				writer = null;
			}
		}
	    
        return str;
	}
	public String getResponseData() {
		return responseData;
	}
	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}
}
