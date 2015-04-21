package com.lvmama.clutter.web;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MemcachedUtil;
@Results({
	@Result(name="success",location="/WEB-INF/pages/alipay/casher_list.html",type="freemarker"),
	@Result(name="error",location="/WEB-INF/pages/alipay/error.html",type="freemarker")
})
/**
 * 支付
 * @author qinzubo
 *
 */
public class AlipayChannelAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3358087477169430506L;
	private static final String MEMCACH_KEY = "alipay_channel_choose";
	/**
	 * 这里根据商户号和private_key 生成的sign 数据 生成的静态url 地址
	 */
	private static final String url ="https://mapi.alipay.com/gateway.do?service=mobile.merchant.paychannel&sign=493128ad923269deddd09cb37b031b1c&partner=2088001842589142&sign_type=MD5";
	
	List<Map<String, Object>> payChanelList = new ArrayList<Map<String,Object>>();
	private String orderId; // 订单id
	private String payPath; // 支付方式
	private String firstChannel; // 渠道
	private String alipayWapUrl;// 支付url 
	private String casherParttern;


	@Action("/alipay/paychannel")
	public void getPaychannel() throws Exception{
		String result = HttpsUtil.requestGet(url);
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder = factory.newDocumentBuilder(); 
	     
	        Document document = builder.parse(new InputSource(new ByteArrayInputStream(result.getBytes("gb2312"))));  
	        Element element = document.getDocumentElement();  
	          
	        NodeList nodes = element.getElementsByTagName("result");
	        
	        if(nodes.getLength()>0){
	        	 Element resultElement = (Element) nodes.item(0); 
	        	 String json = resultElement.getTextContent();
	        	 JSONObject object = JSONObject.fromObject(json);
	        	 Map<String,Object> map = object;
	        	 Map<String,Object> payChannleResultMap =  (Map<String, Object>) map.get("payChannleResult");
	        	 System.out.println(map);
	        }
	}
	
	/**
	 * 支付页面;
	 * @return 
	 */
	@Action("/alipay/pament_parttern")
	public String getPaymentParttern() {
		String key = MEMCACH_KEY+payPath;
		try{
			alipayWapUrl = ClutterConstant.getAlipayWapUrl();
			Object tobject = MemcachedUtil.getInstance().get(key);
			if(null != tobject) {
				payChanelList = (List<Map<String, Object>>) tobject;
				return "success";
			}
			
			String result = HttpsUtil.requestGet(url);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new ByteArrayInputStream(result.getBytes("gb2312"))));
			Element element = document.getDocumentElement();
			NodeList nodes = element.getElementsByTagName("result");
			if (nodes.getLength() > 0) {
				Element resultElement = (Element) nodes.item(0);
				String json = resultElement.getTextContent();
				JSONObject object = JSONObject.fromObject(json);
				Map<String, Object> map = object;
				Map<String, Object> payChannleResultMap = (Map<String, Object>) map.get("payChannleResult");
				List<Map<String, Object>> channelList = (List<Map<String, Object>>)((Map<String,Object>)payChannleResultMap.get("supportedPayChannelList")).get("supportTopPayChannel");
				if(!channelList.isEmpty()) {
					for(int i = 0;i < channelList.size();i++) {
						Map<String, Object> m = channelList.get(i);
						if(payPath.equals(m.get("cashierCode").toString())) {
							payChanelList = (List<Map<String, Object>>)((Map<String,Object>)m.get("supportSecPayChannelList")).get("supportSecPayChannel");
							// 缓存1天
							MemcachedUtil.getInstance().set(key, 86400,payChanelList );
							continue;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	
	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getPayPath() {
		return payPath;
	}

	

	public String getFirstChannel() {
		return firstChannel;
	}

	public void setFirstChannel(String firstChannel) {
		this.firstChannel = firstChannel;
	}
	public void setPayPath(String payPath) {
		this.payPath = payPath;
	}
	public List<Map<String, Object>> getPayChanelList() {
		return payChanelList;
	}

	public void setPayChanelList(List<Map<String, Object>> payChanelList) {
		this.payChanelList = payChanelList;
	}


	public String getAlipayWapUrl() {
		return alipayWapUrl;
	}

	public void setAlipayWapUrl(String alipayWapUrl) {
		this.alipayWapUrl = alipayWapUrl;
	}
	public String getCasherParttern() {
		if("CREDITCARD".equals(payPath)) {
			return "信用卡";
		} else if("DEBITCARD".equals(payPath)) {
			return "储蓄卡";
		}
		return "";
	}

	public void setCasherParttern(String casherParttern) {
		this.casherParttern = casherParttern;
	}

}
