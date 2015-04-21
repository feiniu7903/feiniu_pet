/**
 * 
 */
package com.lvmama.shholiday;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.FreeMarkerConfiguration;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.shholiday.response.AbstractResponse;

import freemarker.template.TemplateException;

/**
 * @author yangbin
 *
 */
public class ShholidayClient {

	private String userId;
	private String password;
	private String unique;
	private String externalUserID;
	private String externalUserName;
	private FreeMarkerConfiguration configuration;
	
	private final static Log LOG=LogFactory.getLog(ShholidayClient.class);
	
	/**
	 * 请求操作
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Response> T execute(Request request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("header", createRequestHeader(request));
		map.put("body", request.createBody());
		String requextXml;
		try {
			requextXml = configuration.getContent(getTemplateName(request), map);
			Map<String,String> requestParas = new HashMap<String, String>();
			requestParas.put("request", requextXml);
			LOG.info(request.getRequestURI());
			String responseXml=HttpsUtil.requestPostForm(request.getRequestURI(), requestParas);
			LOG.info("responseXml "+responseXml);
			T res = (T)request.getResponseClazz().newInstance();
			res.parse(responseXml);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T)createDefaultResponse(request);
	}
	
	private Response createDefaultResponse(Request request){
		Response res=null;
		try {
			res = request.getResponseClazz().newInstance();
			if(res instanceof AbstractResponse){
				((AbstractResponse) res).setSuccess(false);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	private Map<String,Object> createRequestHeader(Request request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("timeStamp", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("externalUserID", externalUserID);
		map.put("externalUserName", externalUserName);
		map.put("uniqueID", unique);
		map.put("transactionName", request.getTransactionName());
		return map;
	}
	private String getTemplateName(Request request){
		return request.getTransactionName().toLowerCase()+".xml";
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public void setExternalUserID(String externalUserID) {
		this.externalUserID = externalUserID;
	}

	public void setExternalUserName(String externalUserName) {
		this.externalUserName = externalUserName;
	}

	public ShholidayClient() {
		super();
		configuration =new FreeMarkerConfiguration(getClass(), "/com/lvmama/shholiday/template");
	}
	
}
