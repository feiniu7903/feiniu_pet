package com.lvmama.passport.processor.impl.client.xichen;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import com.lvmama.passport.processor.impl.client.newland.MD5;
import com.lvmama.passport.processor.impl.client.xichen.model.OrderResponse;
import com.lvmama.passport.processor.impl.client.xichen.model.SubmitOrderBean;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.Md5;
import com.lvmama.passport.utils.WebServiceConstant;

public class XiChenClient {
	private static  Log log = LogFactory.getLog(XiChenClient.class);
	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SO_TIMEOUT = 180000;
	private static final String CHARACTER_ENCODING = "utf-8";
	
	//下单
	public static String applyCodeRequest(SubmitOrderBean bean) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		String unixTimestamp =String.valueOf(System.currentTimeMillis()/1000);
		params.put("username",bean.getUsername());
		params.put("time",unixTimestamp);
		params.put("tid",bean.getTid());
		params.put("num",bean.getNum());
		params.put("name",bean.getName());
		params.put("idcard",bean.getIdcard());
		params.put("phone",bean.getPhone());
		params.put("needmsg","0");//是否需要返回短信内容默认是0不需要
		String md5key=XiChenClient.makeSign(params);
		String targetUrl =WebServiceConstant.getProperties("xichen.order.send.url")
		+ "?username=" +bean.getUsername()
		+ "&time=" + unixTimestamp 
		+ "&tid=" +bean.getTid()
		+ "&num=" + bean.getNum()
		+ "&name=" +bean.getName() 
		+ "&idcard="+bean.getIdcard()
		+ "&phone=" +bean.getPhone()
		+ "&needmsg=0"
		+ "&key=" + md5key;
		log.info("xichen.order.send.url:"+targetUrl);
		String result = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		return result;
	}
	
	//查询订单信息
	public static String orderRequest(String orderId) throws Exception {
		String unixTimestamp =String.valueOf(System.currentTimeMillis()/1000);
		String username=WebServiceConstant.getProperties("xichen.username");
		Map<String, String> data = new LinkedHashMap<String, String>();
		data.put("username",username);
		data.put("time",unixTimestamp);
		data.put("orderid",orderId);
		String sign=makeSign(data);
		String targetUrl = WebServiceConstant.getProperties("xichen.order.search.url")
				+ "?username="+ username
				+ "&time="+ unixTimestamp 
				+ "&orderid="+ orderId 
				+ "&key=" + sign;
		String result = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		return result;
	}
	
	//解析订单返回信息
	public static OrderResponse parseOrderResponse(String response) throws Exception{
		JSONObject orderResponse = new JSONObject(response);
		OrderResponse res=new OrderResponse();
		if (orderResponse.has("errorNo")) {
			String errorNo=orderResponse.getString("errorNo");
			String msg=orderResponse.getString("msg");
			res.setErrorMsg(msg);
			res.setErrorNo(errorNo);
			if(StringUtils.equals(errorNo,"0")){
			String orderId=orderResponse.getString("orderid");
			String code=orderResponse.getString("code");
			res.setOrderId(orderId);
			res.setCode(code);
			}
			return res;
		}
		return null;
	}
	
	
	/**
	 * 生成签名信息
	 * @return
	 */
	public static String makeSign(Map<String, String> paramMap) {
		Iterator<String> keys = paramMap.keySet().iterator();
		StringBuffer buffer = new StringBuffer();
		while(keys.hasNext()) {
			String key = (String) keys.next();
			String value = paramMap.get(key);
			buffer.append(value);
		}
		String pass=WebServiceConstant.getProperties("xichen.key");
		String md5pass=MD5.encode(pass);
		buffer.append(md5pass);
		log.info("sign before md5 value:"+buffer.toString());
		String sign = Md5.encode(buffer.toString());
		return sign;
	}
}
