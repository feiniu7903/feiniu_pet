package com.lvmama.distribution.sweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.vo.Constant;

public abstract class BaseAction extends com.lvmama.comm.BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 804455222706700699L;
	/**
	 * 订单服务
	 */
	protected OrderService orderServiceProxy;

	/**
	 * 发送Ajax请求结果
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendAjaxResult(String json) {
		super.sendAjaxResultByJson(json);
	}
	
	 protected Map<String,String> formatQueryString(String queryString){
	     Map<String,String> map=new HashMap<String,String>();  
	     String args[]=queryString.split("&");
	     for(String s:args){
	          if(s.indexOf("=")>0){
	               String keys[]=s.split("=");

	               if(keys.length==2){
	                   map.put(keys[0], keys[1]);

	               }
	          }
	     }  
	     return map;
	 }
	 
	 protected static String getUrlParamsByMap(Map<String, String> map) {  
	        if (map == null) {  
	            return "";  
	        }  
	        StringBuffer sb = new StringBuffer();  
	        for (Map.Entry<String, String> entry : map.entrySet()) {  
	            sb.append(entry.getKey() + "=" + entry.getValue());  
	            sb.append("&");  
	        }  
	        String s = sb.toString();  
	        if (s.endsWith("&")) {  
	            s = StringUtils.substringBeforeLast(s, "&");  
	        }  
	        return s;  
	    }  
	 
	public void sendXmlResult(String xml) {
 
		this.getResponse().setContentType("text/xml;charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(xml);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置图片前缀 
	 */
	public void setImagePrefix() {
		getRequest().setAttribute("prefixPic", Constant.getInstance().getPrefixPic());
		getRequest().setAttribute("defaultPic", Constant.DEFAULT_PIC);
	}

	
	
	
	public void sendJsonResult(String xml) {

		this.getResponse().setContentType("text/html;charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(xml);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public String getBasePath() {
		String path = this.getRequest().getContextPath();
		return path;
	}

	
	protected void checkArgs(Queue<Object> requirArgList,Map<String,Object> resultMap){
		while(!requirArgList.isEmpty()){
			Object obj = requirArgList.poll();
			if(null==obj){
				resultMap.put("message", "无效参数");
				resultMap.put("code", "-1");
			}
		}
	}
	
	protected void setExceptionResult(Map<String,Object> resultMap){
		resultMap.put("message", "服务异常");
		resultMap.put("code", "-1");
	}
	
	
	
	protected Map<String,Object> putErrorMessage(Map<String,Object> resultMap,String message){
		resultMap.put("message", message==null?"":message);
		resultMap.put("code", "-1");
		return resultMap;
	}
	
	protected Map<String,Object> putLogicExceptionMessage(Map<String,Object> resultMap,String message){
		resultMap.put("message", message==null?"":message);
		resultMap.put("code", "-1");
		return resultMap;
	}
	
	protected Map<String,Object> putExceptionMessage(Map<String,Object> resultMap,String message){
		resultMap.put("errorMessage", message==null?"":message);
		resultMap.put("code", "-1");
		return resultMap;
	}
	
	protected Map<String,Object> putMessageWithErrorCode(Map<String,Object> resultMap,String message,String code){
		resultMap.put("message", message==null?"":message);
		resultMap.put("code", code);
		return resultMap;
	}
	
	public Map<String,Object> resultMapCreator(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("message", "");
		map.put("code","1");
		return map;
	}
	
	
	protected String sendResults(Map<String,Object> resultMap,Object object,boolean isList){
		if(isList){
			resultMap.put("datas", object==null||"".equals(object)?new ArrayList<Object>():object);
		} else {
			resultMap.put("data", object==null||"".equals(object)?new HashMap<String,String>():object);
		}
		JSONObject json = JSONObject.fromObject(resultMap);
		return json.toString();
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

}
