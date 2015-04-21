package com.lvmama.distribution.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;

import com.alipay.util.Md5Encrypt;
import com.lvmama.distribution.model.lv.*;
import com.lvmama.comm.utils.Configuration;
import com.lvmama.comm.utils.MD5;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 分销文件解析类
 * @author gaoxin
 *
 */
public class DistributionUtil {
	private static Log log = LogFactory.getLog(DistributionUtil.class);
	private static Properties properties = null;

	/**
	 * 获取配置文件属性
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertiesByKey(String key) {
		String value = "";
		if (properties == null) {
			Configuration configuration = Configuration.getConfiguration();
			properties = configuration.getConfig("/distribution.properties");
		}
		value = properties.getProperty(key);
		return value.trim();
	}
	
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
	 * XML元素构造工具方法
	 * @param tag
	 * @param obj
	 * @return
	 */
	public static String buildXmlElementInCDATA(String tag , String str){
		StringBuilder xmlStr = new StringBuilder();
		if (str == null) {
			str = "";
		}
		xmlStr.append("<").append(tag).append(">").append("<![CDATA[").append(str.toString()).append("]]>").append("</").append(tag).append(">");
		return xmlStr.toString();
	}
	
	/**
	 * 生成请求信息
	 * @return
	 */
	public static String createRequest(String partnerCode , String xmlStr,String signed) {
		RequestHead head = new RequestHead(partnerCode);
		head.setSigned(signed);
		RequestBody body = new RequestBody(xmlStr);
		Request request= new Request(head, body);
		String xml = request.buildXmlStr();
		return xml;
	}
	
	/**
	 * 解析分销产品列表请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestProductList(String requestXmlStr){
		XStream xStream = getRequestHead();
		xStream.aliasField("parameter", RequestBody.class, "productListParameter");
		xStream.aliasField("currentPage", ProductListParameter.class, "currentPage");
		xStream.aliasField("pageSize", ProductListParameter.class, "pageSize");
		xStream.aliasField("productType", ProductListParameter.class, "productType");
		Request request = (Request)xStream.fromXML(requestXmlStr);
		return request;
	}

	/**
	 * 解析分销产品时间价格列表请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestProductPriceList(String requestXmlStr){
		XStream xStream = getRequestHead();
		xStream.aliasField("parameter", RequestBody.class, "productPriceListParameter");
		xStream.aliasField("currentPage", ProductPriceListParameter.class, "currentPage");
		xStream.aliasField("pageSize", ProductPriceListParameter.class, "pageSize");
		xStream.aliasField("beginDate", ProductPriceListParameter.class, "beginDate");
		xStream.aliasField("endDate", ProductPriceListParameter.class, "endDate");
		Request request = (Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 解析单个分销产品时间价格请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestProductPrice(String requestXmlStr){
		XStream xStream = getRequestHead();
		xStream.aliasField("parameter", RequestBody.class, "productPriceParameter");
		xStream.aliasField("productId", ProductPriceParameter.class, "productId");
		xStream.aliasField("beginDate", ProductPriceParameter.class, "beginDate");
		xStream.aliasField("endDate", ProductPriceParameter.class, "endDate");
		Request request = (Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 解析单个分销产品请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestProduct(String requestXmlStr){
		XStream xStream = getRequestHead();
		xStream.aliasField("parameter", RequestBody.class, "productParameter");
		xStream.aliasField("productId", ProductParameter.class, "productId");
		xStream.aliasField("beginDate", ProductParameter.class, "beginDate");
		xStream.aliasField("endDate", ProductParameter.class, "endDate");
		Request request = (Request)xStream.fromXML(requestXmlStr);
		return request;
	}

	/**
	 * 解析创建订单请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestCreateOrder(String requestXmlStr)throws Exception{
		XStream xStream = getRequestHead();
		xStream.alias("branch", Branch.class);
		xStream.alias("firstVisitCustomer", FirstCustomer.class);
		xStream.alias("otherVisitCustomer", OtherCustomer.class);
		xStream.alias("person", Person.class);
		
		xStream.aliasField("orderInfo", RequestBody.class, "orderInfo");
		xStream.aliasField("partnerOrderId", OrderInfo.class, "partnerOrderId");
		xStream.aliasField("productId", OrderInfo.class, "productId");
		xStream.aliasField("productBranch", OrderInfo.class, "productBranch");
		xStream.aliasField("firstVisitCustomer", OrderInfo.class, "firstVisitCustomer");
		xStream.aliasField("otherVisitCustomer", OrderInfo.class, "otherVisitCustomer");
		xStream.aliasField("orderMemo", OrderInfo.class, "orderMemo");
		xStream.addImplicitCollection(ProductBranch.class, "branchList");
		xStream.aliasField("branchId", Branch.class, "branchId");
		xStream.aliasField("quantity", Branch.class, "quantity");
		xStream.aliasField("visitDate", Branch.class, "visitDate");
		xStream.aliasField("sellPrice", Branch.class, "sellPrice");
		xStream.aliasField("name", FirstCustomer.class, "name");
		xStream.aliasField("pinyin", FirstCustomer.class, "pinyin");
		xStream.aliasField("mobile", FirstCustomer.class, "mobile");
		xStream.aliasField("credentials", FirstCustomer.class, "credentials");
		xStream.aliasField("credentialsType", FirstCustomer.class, "credentialsType");
		xStream.addImplicitCollection(OtherCustomer.class, "personList");
		xStream.aliasField("name", Person.class, "name");
		xStream.aliasField("pinyin", Person.class, "pinyin");
		xStream.aliasField("mobile", Person.class, "mobile");
		xStream.aliasField("credentials", Person.class, "credentials");
		xStream.aliasField("credentialsType", Person.class, "credentialsType");
		
		Request request = (Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 解析修改订单状态请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestUpdateOrderStatus(String requestXmlStr){
		XStream xStream = getRequestHead();
		xStream.aliasField("orderId", Order.class,"orderId");
		xStream.aliasField("status", Order.class,"status");
		xStream.aliasField("paymentStatus", Order.class,"paymentStatus");
		xStream.aliasField("paymentSerialno", Order.class,"paymentSerialno");
		xStream.aliasField("bankOrderId", Order.class,"bankOrderId");
		Request request = (Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 解析获取订单信息请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestOrder(String requestXmlStr) throws Exception{
		XStream xStream = getRequestHead();
		xStream.aliasField("order", Order.class, "order");
		xStream.aliasField("orderId", Order.class,"orderId");
		Request request = (Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 解析获取订单信息请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestResendCode(String requestXmlStr){
		XStream xStream = getRequestHead();
		xStream.aliasField("orderId", Order.class,"orderId");
		Request request = (Request)xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 解析查询产品上下架信息请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestProductOnLine(String requestXmlStr) {
		XStream xStream = getRequestHead();
		xStream.aliasField("parameter", RequestBody.class, "productParameter");
		xStream.aliasField("productId", ProductParameter.class, "productId");
		Request request = (Request) xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 解析查询订单审核状态信息请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestOrderApprove(String requestXmlStr) {
		XStream xStream = getRequestHead();
		xStream.aliasField("parameter", RequestBody.class, "order");
		xStream.aliasField("orderId", Order.class, "orderId");
		Request request = (Request) xStream.fromXML(requestXmlStr);
		return request;
	}
	/**
	 * 解析查询订单审核状态回传信息请求报文
	 * @param requestXmlStr
	 * @return
	 */
	public static Request getRequestOrderApproveCallBack(String requestXmlStr) {
		XStream xStream = getRequestHead();
		xStream.aliasField("parameter", RequestBody.class, "order");
		xStream.aliasField("orderId ", Order.class, "orderId ");
		Request request = (Request) xStream.fromXML(requestXmlStr);
		return request;
	}
	
	/**
	 * 退款响应解析对象
	 * @return
	 */
	public static Response getRefundRes(String responseXmlStr){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", Response.class);
		xStream.aliasField("header", Response.class, "head");
		xStream.aliasField("code", ResponseHead.class, "code");
		xStream.aliasField("describe", ResponseHead.class, "describe");
		Response response=(Response)xStream.fromXML(responseXmlStr);
		return response;
	}
	
	/**
	 * 构造公共解析对象
	 * @return
	 */
	private static XStream getRequestHead(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("request", Request.class);
		xStream.aliasField("header", Request.class, "requestHead");
		xStream.aliasField("body", Request.class, "requestBody");
		xStream.aliasField("version", RequestHead.class, "version");
		xStream.aliasField("partnerIp", RequestHead.class, "partnerIp");
		xStream.aliasField("partnerCode", RequestHead.class, "partnerCode");
		xStream.aliasField("signed", RequestHead.class, "signed");
		return xStream;
	}
	
	
	/**
	 * Base64编码
	 * 
	 * @param bstr
	 * @return String 10.
	 */
	public static String encode(byte[] bstr) {
		return new sun.misc.BASE64Encoder().encode(bstr);
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
	
	public static void main(String arg[]) throws UnsupportedEncodingException{
//		String str = "PG9yZGVySWQ+MjAxMjA1MTc2PC9vcmRlcklkPjxzdGF0dXM+Tk9STUFMPC9zdGF0dXM+PHBheW1lbnRTdGF0dXM+VU5QQVk8L3BheW1lbnRTdGF0dXM+";
//		System.out.println(new String(decode(str),"utf-8"));
		String str ="229292292922012-05-231822107604218221076042";
		try {
			System.out.println(MD5.encode(str));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
