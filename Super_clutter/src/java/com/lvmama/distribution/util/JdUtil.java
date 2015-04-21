package com.lvmama.distribution.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.lvmama.comm.utils.MD5;
import com.lvmama.distribution.model.jd.Body;
import com.lvmama.distribution.model.jd.Head;
import com.lvmama.distribution.model.jd.Order;
import com.lvmama.distribution.model.jd.Product;
import com.lvmama.distribution.model.jd.Request;
import com.lvmama.distribution.model.jd.Response;
import com.lvmama.distribution.model.jd.Result;
import com.lvmama.distribution.model.jd.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 京东工具类
 * @author gaoxin
 *
 */

public class JdUtil {
	
	/**
	 * XML元素构造工具方法
	 * @param tag
	 * @param obj
	 * @return
	 */
	public static String buildXmlElement(String tag , Object obj){
		StringBuilder xmlStr = new StringBuilder();
		if (obj == null) {
			obj = "";
		}
		xmlStr.append("<").append(tag).append(">").append(obj.toString()).append("</").append(tag).append(">");
		return xmlStr.toString();
	}
	/**
	 * 添加检查元素为空的XML元素构造工具方法
	 * @param tag
	 * @param obj
	 * @return
	 */
	public static String buildXmlElementInCheck(String tag , Object obj){
		StringBuilder xmlStr = new StringBuilder();
		if (obj == null) {
			xmlStr.append("");
		}else{
			xmlStr.append("<").append(tag).append(">").append(obj.toString()).append("</").append(tag).append(">");
		}
		return xmlStr.toString();
	}
	
	/**
	 * XML元素构造工具方法
	 * @param tag
	 * @param obj
	 * @return
	 */
	public static String buildXmlElementInCDATA(String tag , String str){
		StringBuilder xmlStr = new StringBuilder();
		if (str == null) {
			str = "";
		}else{
			xmlStr.append("<").append(tag).append(">").append("<![CDATA[").append(str.toString()).append("]]>").append("</").append(tag).append(">");
		}
		return xmlStr.toString();
	}
	
	/**
	 * 解析京东服务的响应报文
	 * @param responseXmlStr
	 * @return Response
	 */
	public static Response getResponseXml(String responseXmlStr){
		XStream xStream=new XStream(new DomDriver());
		xStream.alias("response", Response.class);
		xStream.alias("body", Body.class);
		xStream.alias("head", Head.class);
		xStream.aliasField("head", Response.class, "head");
		xStream.aliasField("version", Head.class, "version");
		xStream.aliasField("messageId", Head.class, "messageId");
		xStream.aliasField("signed", Head.class, "signed");
		xStream.aliasField("body", Response.class, "body");
		xStream.aliasField("result", Body.class, "result");
		xStream.aliasField("isSuccess", Result.class, "isSuccess");
		xStream.aliasField("errorCode", Result.class, "errorCode");
		xStream.aliasField("errorMsg", Result.class, "errorMsg");
		xStream.aliasField("dealTime", Result.class, "dealTime");
		Response response = (Response)xStream.fromXML(responseXmlStr);
		return response;
	}
	
	
	/**
	 * 解析京东下单请求报文
	 * @param requestXmlStr
	 * @return Request
	 */
	public static Request getRequestOrder(String requestXmlStr) throws Exception{
		XStream xStream=getRequestHead();
		xStream.alias("order", Order.class);
		xStream.aliasField("body", Request.class, "body");
		xStream.aliasField("order", Body.class, "order");
		xStream.aliasField("orderId", Order.class, "orderId");
		xStream.aliasField("count", Order.class, "count");
		xStream.aliasField("settlementPrice", Order.class, "settlementPrice");
		xStream.aliasField("isSendSms", Order.class, "isSendSms");
		xStream.aliasField("productId", Order.class, "productId");
		xStream.aliasField("branchId", Order.class, "branchId");
		xStream.aliasField("payType", Order.class, "payType");
		xStream.aliasField("validTimeBegin", Order.class, "validTimeBegin");
		xStream.aliasField("validTimeEnd", Order.class, "validTimeEnd");
		xStream.aliasField("inDate", Order.class, "inDate");
		xStream.aliasField("user", Order.class, "user");
		xStream.aliasField("name", User.class, "name");
		xStream.aliasField("mobile", User.class, "mobile");
		xStream.aliasField("sex", User.class, "sex");
		xStream.aliasField("idCard", User.class, "idCard");
		xStream.aliasField("feature", Order.class, "feature");
		Request request=(Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	/**
	 * 解析京东订单查询报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getQueryOrder(String requestXmlStr) throws Exception{
		XStream xStream=getRequestHead();
		xStream.alias("order", Order.class);
		xStream.aliasField("body", Request.class, "body");
		xStream.aliasField("order", Body.class, "order");
		xStream.aliasField("orderId", Order.class, "orderId");
		Request request=(Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 解析京东重发短信请求报文
	 * @param requestXmlStr
	 * @return Request
	 */
	public static Request getReSendSMS(String requestXmlStr)throws Exception{
		XStream xStream=getRequestHead();
		xStream.alias("order", Order.class);
		xStream.aliasField("body", Request.class, "body");
		xStream.aliasField("order", Body.class, "order");
		xStream.aliasField("orderId", Order.class, "orderId");
		xStream.aliasField("feature", Order.class, "feature");
		Request request=(Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	
	/**
	 * 解析京东退票请求报文
	 * @param requestXmlStr
	 * @return Request
	 */
	public static Request getRefundTicket(String requestXmlStr) throws Exception{
		XStream xStream=getRequestHead();
		xStream.alias("order", Order.class);
		xStream.aliasField("body", Request.class, "body");
		xStream.aliasField("order", Body.class, "order");
		xStream.aliasField("orderId", Order.class, "orderId");
		xStream.aliasField("refundCount", Order.class, "refundCount");
		xStream.aliasField("feature", Order.class, "feature");
		Request request=(Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 公共请求 head部分解析
	 * @return
	 */
	private static XStream getRequestHead() throws Exception{
		XStream xStream=new XStream(new DomDriver());
		xStream.alias("request", Request.class);
		xStream.alias("head", Head.class);
		xStream.alias("body", Body.class);
		xStream.aliasField("head", Request.class, "head");
		xStream.aliasField("version", Head.class, "version");
		xStream.aliasField("messageId", Head.class, "messageId");
		xStream.aliasField("partnerCode", Head.class, "partnerCode");
		xStream.aliasField("proxyId", Head.class, "proxyId");
		xStream.aliasField("timeStamp", Head.class, "timeStamp");
		xStream.aliasField("signed", Head.class, "signed");	
		return xStream;
	}
	
	/**
	 * 解析京东查询每日价格请求报文
	 * @param requestXmlStr
	 * @return Request
	 */
	public static Request getDailyPrices(String requestXmlStr)throws Exception{
		XStream xStream=getRequestHead();
		xStream.alias("order", Order.class);
		xStream.aliasField("body", Request.class, "body");
		xStream.aliasField("product", Body.class, "product");
		
		xStream.aliasField("productId", Product.class, "productId");
		xStream.aliasField("branchId", Product.class, "branchId");
		xStream.aliasField("beginDate", Product.class, "validTimeBegin");
		xStream.aliasField("endDate", Product.class, "validTimeEnd");
		xStream.aliasField("feature", Product.class, "feature");
		Request request=(Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	/**
	 * 退票响应
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String getIsSuccess(String xml)throws Exception{
		Document doc;
		String isSuccess="false";
		doc = DocumentHelper.parseText(xml); // 将响应字符串转化为XML
		Element rootElt = doc.getRootElement(); // 获取根节点
		Iterator results=rootElt.element("body").elementIterator("result");
		while(results.hasNext()){
			Element res = (Element)results.next();
			isSuccess = res.elementTextTrim("isSuccess");
		}
		 return isSuccess;
	}
	
	
	
	/**
	 * Base64编码
	 * @param bstr
	 * @return String 10.
	 */
	public static String encode(byte[] bstr) {
		String edata = null;
		try {
			edata = (new BASE64Encoder()).encodeBuffer(bstr).trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return edata;
	}

	/**
	 * Base64解码
	 * 
	 * @param str
	 * @return string
	 */
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bt;
	}
	
	/**
	 * 生成消息签名
	 */
	public static String getSigned(String md5Str){
		String signed = null;
		try {
			signed = JdUtil.encode(MD5.getByte(md5Str));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return signed;
	}

}
