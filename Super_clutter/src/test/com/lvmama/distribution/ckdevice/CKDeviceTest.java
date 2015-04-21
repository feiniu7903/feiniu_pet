package com.lvmama.distribution.ckdevice;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.JAXBUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.distribution.model.ckdevice.Request;

public class CKDeviceTest {
	private static String url = "http://180.169.51.94/clutter2/ckdevice";
	String key = "7a3efaff972d6fdb5999fecefc593883";
	@Test
	public void testCheckReservation()throws Exception {
		
		url += "/checkReservation.do";
		String xmlStr = this.getXmlStr("ckCheckReservation.xml");
		System.out.println(xmlStr);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("requestXml", xmlStr);
		String responseXml = HttpsUtil.requestPostForm(url, requestParas);
		System.out.print(responseXml);
	}
	
	
	@Test
	public void getPrintInfo() throws Exception{
		url += "/getPrintInfo.do";
		String xmlStr = this.getXmlStr("ckGetPrintInfo.xml");
		System.out.println(xmlStr);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("requestXml", xmlStr);
		String responseXml = HttpsUtil.requestPostForm(url, requestParas);
		System.out.print(responseXml);
	}
	
	@Test
	public void confirmPrint() throws Exception{
		url += "/confirmPrint.do";
		String xmlStr = this.getXmlStr("ckconfirmPrint.xml");
		System.out.println(xmlStr);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("requestXml", xmlStr);
		String responseXml = HttpsUtil.requestPostForm(url, requestParas);
		System.out.print(responseXml);
	}
	
	@Test
	public void queryProductList() throws Exception{
		url += "/queryProductList.do";
		String xmlStr = this.getXmlStr("ckqueryProductList.xml");
		System.out.println(xmlStr);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("requestXml", xmlStr);
		String responseXml = HttpsUtil.requestPostForm(url, requestParas);
		System.out.print(responseXml);
	}
	
	@Test
	public void queryOrder() throws Exception{
		url += "/queryOrder.do";
		String xmlStr = this.getXmlStr("ckQueryOrder.xml");
		System.out.println(xmlStr);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("requestXml", xmlStr);
		String responseXml = HttpsUtil.requestPostForm(url, requestParas);
		System.out.print(responseXml);
	}
	@Test
	public void confirmPayment() throws Exception{
		url += "/confirmPayment.do";
		String xmlStr = this.getXmlStr("ckconfirmPayment.xml");
		System.out.println(xmlStr);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("requestXml", xmlStr);
		String responseXml = HttpsUtil.requestPostForm(url, requestParas);
		System.out.print(responseXml);
	}
	
	
	
	@Test
	public void requestPaymentChecksum() throws Exception{
		url += "/requestPaymentChecksum.do";
		String xmlStr = this.getXmlStr("ckrequestPaymentChecksum.xml");
		System.out.println(xmlStr);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("requestXml", xmlStr);
		String responseXml = HttpsUtil.requestPostForm(url, requestParas);
		System.out.print(responseXml);
	}
	
	
	@Test
	public void createOrder() throws Exception{
		url += "/createOrder.do";
		String xmlStr = this.getXmlStr("ckCreateOrder.xml");
		Request request = (Request)JAXBUtil.xml2Bean(xmlStr, Request.class);
	}
	
	@Test
	public void getMD5Str() throws NoSuchAlgorithmException{
		
//		String str = key+"4026049217";
		String str = key+"3255";
		System.out.println(MD5.encode(str));
	}
	
	private String getXmlStr(String fileName) throws Exception{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("com/lvmama/distribution/ckdevice/"+fileName);
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(inputStream);
		return document.asXML();
	}
	
}
