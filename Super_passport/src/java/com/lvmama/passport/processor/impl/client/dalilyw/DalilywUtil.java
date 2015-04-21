package com.lvmama.passport.processor.impl.client.dalilyw;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lvmama.comm.utils.MD5;
import com.lvmama.passport.processor.impl.client.dalilyw.model.HttpResponse;
import com.lvmama.passport.processor.impl.client.dalilyw.model.OrderResponse;
import com.lvmama.passport.utils.WebServiceConstant;

public class DalilywUtil {
	private static final  Log log = LogFactory.getLog(DalilywUtil.class);
	public static HttpResponse saveOrderRequest(Map<String,Object> params,String uri) throws Exception{
		Map<String,String> header =(Map<String,String>)params.get("header");
		Map<String,String> query =(Map<String,String>)params.get("query");
		HttpClient client = new HttpClient();
		HttpMethod httpmethod = new PostMethod(uri);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			httpmethod.addRequestHeader(entry.getKey(), (String) entry.getValue());
		}
		System.out.println("saveOrderRequest Headerparams:"+header.toString());
		String queryString="";
		for (Map.Entry<String, String> entry : query.entrySet()) {
			queryString+=entry.getKey()+"="+(String) entry.getValue()+"&";
		}
		queryString = queryString.substring(0, queryString.lastIndexOf("&"));
		System.out.println("saveOrderRequest params:"+queryString);
		httpmethod.setQueryString(URIUtil.encodeQuery(queryString));
		int code = client.executeMethod(httpmethod);
		HttpResponse httpRes = new HttpResponse();
		httpRes.setCode(code);
		httpRes.setResponseBody(httpmethod.getResponseBodyAsString());
		log.info("saveOrderRequest result:"+httpRes.getResponseBody());
		return httpRes;
	}
	
	//使用账户余客支付订单消费
	public static HttpResponse balancepay(String method,String orderNo)throws Exception{
		String appKey=WebServiceConstant.getProperties("dalilyw.appkey");
		String appSecret=WebServiceConstant.getProperties("dalilyw.appsecret");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String unixTimestamp =formatter.format(System.currentTimeMillis());
		Map<String,String> header = new HashMap<String, String>();
		header.put("appKey",appKey);
		header.put("appSecret",appSecret);
		header.put("method",method);
		header.put("timeStamp", unixTimestamp);
		header.put("version", "1.0");
		header.put("checksign", MD5.encode(orderNo));
		HttpClient client = new HttpClient();
		HttpMethod httpmethod = new PostMethod(WebServiceConstant.getProperties("dalilyw.url")+"/member/order/balancepay");
		for (Map.Entry<String, String> entry : header.entrySet()) {
			httpmethod.addRequestHeader(entry.getKey(), (String) entry.getValue());
		}
		httpmethod.setQueryString(URIUtil.encodeQuery("orderno="+orderNo));
		int code = client.executeMethod(httpmethod);
		HttpResponse httpRes=new HttpResponse();
		httpRes.setCode(code);
		httpRes.setResponseBody(httpmethod.getResponseBodyAsString());
		log.info("balancepayRequest result:"+httpRes.getResponseBody());
		return httpRes;
	}
	
	//订单消费退款到企业总账余额
	public static HttpResponse refundbalance(String method,String orderNo)throws Exception{
		String appKey=WebServiceConstant.getProperties("dalilyw.appkey");
		String appSecret=WebServiceConstant.getProperties("dalilyw.appsecret");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String unixTimestamp =formatter.format(System.currentTimeMillis());
		Map<String,String> header = new HashMap<String, String>();
		header.put("appKey",appKey);
		header.put("appSecret",appSecret);
		header.put("method",method);
		header.put("timeStamp", unixTimestamp);
		header.put("version", "1.0");
		header.put("checksign", MD5.encode(orderNo));
		HttpClient client = new HttpClient();
		HttpMethod httpmethod = new PostMethod(WebServiceConstant.getProperties("dalilyw.url")+"/member/order/refundbalance");
		for (Map.Entry<String, String> entry : header.entrySet()) {
			httpmethod.addRequestHeader(entry.getKey(), (String) entry.getValue());
		}
		httpmethod.setQueryString(URIUtil.encodeQuery("orderno="+orderNo));
		int code = client.executeMethod(httpmethod);
		HttpResponse httpRes=new HttpResponse();
		httpRes.setCode(code);
		httpRes.setResponseBody(httpmethod.getResponseBodyAsString());
		log.info("refundbalanceRequest result:"+httpRes.getResponseBody());
		return httpRes;
	}
	
	
	public static HttpResponse cancelRequest(String method,String orderNo)throws Exception{
		String appKey=WebServiceConstant.getProperties("dalilyw.appkey");
		String appSecret=WebServiceConstant.getProperties("dalilyw.appsecret");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String unixTimestamp =formatter.format(System.currentTimeMillis());
		Map<String,String> header = new HashMap<String, String>();
		header.put("appKey",appKey);
		header.put("appSecret",appSecret);
		header.put("method",method);
		header.put("timeStamp", unixTimestamp);
		header.put("version", "1.0");
		HttpClient client = new HttpClient();
		HttpMethod httpmethod = new PostMethod(WebServiceConstant.getProperties("dalilyw.url")+"/member/order/cancel");
		for (Map.Entry<String, String> entry : header.entrySet()) {
			httpmethod.addRequestHeader(entry.getKey(), (String) entry.getValue());
		}
		httpmethod.setQueryString(URIUtil.encodeQuery("orderno="+orderNo));
		int code = client.executeMethod(httpmethod);
		HttpResponse httpRes=new HttpResponse();
		httpRes.setCode(code);
		httpRes.setResponseBody(httpmethod.getResponseBodyAsString());
		log.info("cancelRequestRequest result:"+httpRes.getResponseBody());
		return httpRes;
	}
	
	//查询订单接口
	public static HttpResponse orderRequest(String method,String orderNo) throws Exception {
		String appKey=WebServiceConstant.getProperties("dalilyw.appkey");
		String appSecret=WebServiceConstant.getProperties("dalilyw.appsecret");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String unixTimestamp =formatter.format(System.currentTimeMillis());
		Map<String,String> header = new HashMap<String, String>();
		header.put("appKey",appKey);
		header.put("appSecret",appSecret);
		header.put("method",method);
		header.put("timeStamp", unixTimestamp);
		header.put("version", "1.0");
		HttpClient client = new HttpClient();
		HttpMethod httpmethod = new GetMethod(WebServiceConstant.getProperties("dalilyw.url")+"/member/order/view/"+orderNo);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			httpmethod.addRequestHeader(entry.getKey(), (String) entry.getValue());
		}
		int code = client.executeMethod(httpmethod);
		HttpResponse httpRes=new HttpResponse();
		httpRes.setCode(code);
		httpRes.setResponseBody(httpmethod.getResponseBodyAsString());
		log.info("orderViewRequest result:"+httpRes.getResponseBody());
		return httpRes;
	}
	
	//景区产品获取
	public static String ScenicList(String method,String grade) throws Exception {
		String appKey=WebServiceConstant.getProperties("dalilyw.appkey");
		String appSecret=WebServiceConstant.getProperties("dalilyw.appsecret");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String unixTimestamp =formatter.format(System.currentTimeMillis());
		String scenicUrl=WebServiceConstant.getProperties("dalilyw.url")+"/scenic/list";
		Map<String,String> header = new HashMap<String, String>();
		header.put("appKey",appKey);
		header.put("appSecret",appSecret);
		header.put("method",method);
		header.put("timeStamp", unixTimestamp);
		header.put("version", "1.0");
		String response= null;
		HttpClient client = new HttpClient();
		HttpMethod getMethod = new GetMethod(scenicUrl);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			getMethod.addRequestHeader(entry.getKey(), (String) entry.getValue());
		}
		getMethod.setQueryString(URIUtil.encodeQuery("grade="+grade+"&orderType=topDesc"));
		int code = client.executeMethod(getMethod);
		System.out.println("ScenicList code:"+code);
		System.out.println(scenicUrl);
		if (code == HttpStatus.SC_OK) {
		response = getMethod.getResponseBodyAsString();
		}
		return response;

	}
	
	/**
	 * 生成签名信息
	 * @return
	 */
	public static String makeSign(Map<String, String> params)throws Exception {
		ArrayList<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		boolean first = true;
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
				if (first) {
					prestr = prestr + key + "=" + value;
					first = false;
				} else {
					prestr = prestr + "&" + key + "=" + value;
				}
		}
		String sign =MD5.encode32(prestr);
		return sign;
	}
	
	/**
	 * 解析查询订单状态返回信息
	 */
	public static OrderResponse parseOrderResponse(String response) throws Exception {
		JSONObject result = new JSONObject(response);
		String data=result.getString("data");
		String status=result.getString("success");
		OrderResponse orderRes=null;
		if(StringUtils.equals(status, "true")){
			if (result.has("data")) {
				JSONObject order = new JSONObject(data);
				orderRes=new OrderResponse();
				orderRes.setStatus(status);
				if (order.has("order_code")) {
					orderRes.setOrder_code(order.getString("order_code"));
				}
				if (order.has("order_no")) {
					orderRes.setOrder_no(order.getString("order_no"));
				}	
			}
		}else{
			if (result.has("data")) {
				orderRes=new OrderResponse();
				orderRes.setStatus(status);
				orderRes.setMsg(data);
			}
		}
		return orderRes;
	}
	
	//解析取消订单的返回信息
	public static OrderResponse parseCancelResponse(String response) throws Exception {
		JSONObject result = new JSONObject(response);
		String data=result.getString("data");
		String status=result.getString("success");
		OrderResponse orderRes=new OrderResponse();
		orderRes.setStatus(status);
		orderRes.setMsg(data);
		return orderRes;
	}
	
	//解析退款
	public static OrderResponse parserefundbalanceResponse(String response) throws Exception {
		JSONObject result = new JSONObject(response);
		String success=result.getString("success");
		String state=result.getString("state");
		String message=result.getString("message");
		OrderResponse orderRes=new OrderResponse();
		orderRes.setStatus(success);
		orderRes.setState(state);
		orderRes.setMsg(message);
		return orderRes;
	}
	
	//解析订单查询结果
	public static List<OrderResponse> parseOrderListResponse(String response) throws Exception {
		JSONObject result = new JSONObject(response);
		String status=result.getString("success");
		List<OrderResponse> orderlist=new ArrayList<OrderResponse>();
		if(StringUtils.equals(status, "true")){
			String data=result.getString("data");
			JSONObject rsList = new JSONObject(data);
			JSONArray jsonArray = new JSONArray(rsList.getString("order_list"));
			for(int i=0;i<jsonArray.length();i++){
				JSONObject order = jsonArray.getJSONObject(i);
				OrderResponse orderRes=new OrderResponse();
				orderRes.setStatus(status);
				if (order.has("statusname")) {
					orderRes.setStatusName(order.getString("statusname"));
				}
				orderlist.add(orderRes);
			}
		}
		return orderlist;
	}
}
