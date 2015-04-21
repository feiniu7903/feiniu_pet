/**
 * 
 */
package com.lvmama.train.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ConnectTimeoutException;
import com.lvmama.comm.bee.po.pub.ComJobContent;
import com.lvmama.comm.bee.service.com.ComJobContentService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.RspStatus;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.train.TrainTicketSign;


/**
 * @author yangbin
 *
 */
public class TrainClient {
	private static final Log logger =LogFactory.getLog(TrainClient.class);
	private static final String HTTP_METHOD = "POST";
	private final String KEY = WebServiceConstant.getProperties("train.account.userKey");

	/**
	 * 调用火车票请求接口，解析请求响应
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends TrainResponse> T execute(TrainRequest request) throws RuntimeException{
		T res =null;
		//获取所有请求的参数
		Map<String, String> map = request.getParam();
		
		
		try {
			String sign = new TrainTicketSign().sign(KEY, HTTP_METHOD, request.getBaseUrl(), map);
			map.put("sign", sign);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		printMap(map);
		try {
			res = (T)request.getClazz().newInstance();
		} catch (InstantiationException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		} catch (IllegalAccessException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		HttpResponseWrapper response=null;
		try {
			logger.info(request.getUrl());
			response = HttpsUtil.requestPostFormResponse2(request.getUrl(), map,60);
			int statusCode = response.getHttpResponse().getStatusLine().getStatusCode();
			String resStr = response.getResponseString();
			res.getRsp().setStatus(new RspStatus(statusCode, 
					response.getHttpResponse().getStatusLine().toString()));
			if(statusCode == Constant.HTTP_SUCCESS){
				res.setSuccess(true);
				logger.info(resStr);
				res.parse(resStr);
			}else{
				logger.info("请求失败：" + resStr);
				res.setSuccess(false);
//				res.getRsp().setStatus(new RspStatus(statusCode, 
//						response.getHttpResponse().getStatusLine().toString()));
				res.parseError(resStr);
			}
		}catch(ConnectTimeoutException e) {
			res.setCancelOrder(false);
			//记录一条失败信息到com_job_content,job跟进推送
			if(StringUtil.isNotEmptyString(request.getRequestType()) && request.getObjectId() != null) {
				ComJobContentService comJobContentService = (ComJobContentService)SpringBeanProxy.getBean("comJobContentService");
				ComJobContent comJobContent = new ComJobContent();
				comJobContent.setJobType(request.getRequestType());
				comJobContent.setObjectId(request.getObjectId());
				comJobContent.setObjectType("ORD_ORDER_TRAFFIC");
				comJobContent.setPlanTime(DateUtils.addMinutes(comJobContent.getCreateTime(), 3));
				comJobContentService.add(comJobContent);
			} else {
				e.printStackTrace();
			}
			res.setSuccess(false);
		}catch (Exception ex) {
			ex.printStackTrace();
			res.setSuccess(false);
		}finally{
			if(response != null) {
				response.close();
			}
		}
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void printMap(Map<String, String> map) {
		// TODO Auto-generated method stub
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	public static void main(String[] args) throws Exception {
		String url="http://121.199.40.9/v1/product/train_info_query";
		Map<String,String> map = new HashMap<String, String>();
		String str="merchant_id=600001&request_date=2013-11-16&request_type=all&train_id=K1122";
		String[] array = str.split("&");
		
		map.put("request_type", "all");
		map.put("request_date","2013-11-11");
		map.put("train_id", "T222");
		map.put("merchant_id","600001");
		String sign = new TrainTicketSign().sign("6ecd6d6d326eb22b9ead5fa986d6d9bf", "POST","/v1/product/train_info_query" , map);
		map.put("sign", sign);
//		map.put("sign","nQyjlzRGNX%2BxtP0m4dboO9CoMvk%3D");
		HttpResponseWrapper response = HttpsUtil.requestPostFormResponse2(url, map,60);
		System.out.println(response.getResponseString());
	}
}
