package com.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lvmama.clutter.service.IClientService;
import com.lvmama.clutter.xml.lv.po.ClientXmlAliasSet;
import com.lvmama.clutter.xml.lv.po.RequestObject;
import com.thoughtworks.xstream.XStream;

public class TestClient extends TestBase{
	IClientService clientService = (IClientService) this.context.getBean("clientServiceImpl");
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 景区酒店列表
	 */

	public void testquerPlaceList() {
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>placeList"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<stage>3"
			+"</stage>"
			+"<placeId>79"
			+"</placeId>"
			+"<page>2"
			+"</page>"
			+"<pageSize>10"
			+"</pageSize>"
			+"</body>"
			+"</request>"; //线路
		System.out.println("http://127.0.0.1/clutter/cliet/home.do?reqXml="+reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		 String xml = clientService.queryPlaceList(ro);
		 System.out.println(xml);
	}

	
	public RequestObject getRequestObjectFromXml(String reqXml){
		XStream reqXtream = ClientXmlAliasSet.getAllRequestObj();
		RequestObject requestObj = (RequestObject) reqXtream.fromXML(reqXml);
		requestObj.getBody().paramters = new ArrayList<String>();
		return requestObj;
	}
	
	

	public void testQueryDestList() {
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>queryDestList"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.queryDestList(ro);
		System.out.println(xml);
	}

	/**
	 * 查询某个标的的详细信息
	 */
	
	public void testqueryDestDetails(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>queryDestDetails"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<placeId>103610"
			+"</placeId>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.queryDestDetails(ro);
		System.out.println(xml);
		
	}
	
	public void testgetPlaceDetails(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+"<request>"
				+"<head>"
				+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
				+"<deviceId>11231311212"+"</deviceId>"
				+"<deviceName>IPHONE"+"</deviceName>"
				+"<osName>IOS"+"</osName>"
				+"<osVersion>4.3"+"</osVersion>"
				+"<method>queryDestDetails"+"</method>"
				+"<pageName>切换城市"+"</pageName>"
				+"<version>2011-04-25 16:53:31</version>"
				+"</head>"
				+"<body>"
				+"<placeId>79"
				+"</placeId>"
				+"</body>"
				+"</request>"; //线路
			System.out.println(reqXml);
			RequestObject ro = this.getRequestObjectFromXml(reqXml);
			
			String xml = clientService.getPlaceDetails(ro);
			System.out.println(xml);
	}
	
	
	
	public void testgetNameByLocation(){
		
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+"<request>"
				+"<head>"
				+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
				+"<deviceId>11231311212"+"</deviceId>"
				+"<deviceName>IPHONE"+"</deviceName>"
				+"<osName>IOS"+"</osName>"
				+"<osVersion>4.3"+"</osVersion>"
				+"<method>queryDestDetails"+"</method>"
				+"<pageName>切换城市"+"</pageName>"
				+"<version>2011-04-25 16:53:31</version>"
				+"</head>"
				+"<body>"
				+"<keyWord>上海"
				+"</keyWord>"
				+"</body>"
				+"</request>"; //线路
			System.out.println(reqXml);
			RequestObject ro = this.getRequestObjectFromXml(reqXml);
			//String xml = clientService.getNameByLocation(ro);
			//System.out.println(xml);
	}
	
	/**
	 * 门票产品
	 */

	public void testqueryTicketsList(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>queryTicketsList"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<placeId>155565"
			+"</placeId>"
			+"<stage>1"
			+"</stage>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.queryTicketsList(ro);
		System.out.println(xml);
	}
	

	/**
	 * 酒店类型产品单房 和套餐
	 */
	public void testquerHotelProductList(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>querHotelProductList"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<placeId>154155"
			+"</placeId>"
			+"<stage>3"
			+"</stage>"
			+"</body>"
			+"</request>"; //线路

		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.querHotelProductList(ro);
		System.out.println(xml);
		System.out.println(reqXml);
	}
	
	
	/**
	 * 目的地自由行
	 */
	

	public void testqueryDestRouteList(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>queryDestRouteList"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<placeId>79"
			+"</placeId>"
			+"<stage>1"
			+"</stage>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.queryDestRouteList(ro);
		System.out.println(xml);
	}

	/**
	 * 用户登陆
	 */

	public void testclientLogin(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>clientLogin"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<userName><![CDATA[yuzhibing1]]>"
			+"</userName>"
			+"<password><![CDATA[111111]]>"
			+"</password>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.clientLogin(ro);
		System.out.println(xml);
		
	}
	
	/**
	 * 获取验证码
	 */

	public void testclientGetVlidateCode(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>clientGetVlidateCode"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<mobile>15301938332"
			+"</mobile>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.clientGetVlidateCode(ro);
		System.out.println(xml);
		
	}
	
	/**
	 * 提交注册
	 */
	@Test
	public void testclientSubRegister(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>clientSubRegister"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<mobile>13472486666"
			+"</mobile>"
			+"<password><![CDATA[@#$%^^&@!#!]]>"
			+"</password>"
			+"<validateCode>361871"
			+"</validateCode>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.clientSubRegister(ro);
		System.out.println(xml);
		System.out.println(reqXml);
	}
	

	public void testgetMainProdTimePrice(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>getMainProdTimePrice"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<productId>33013"
			+"</productId>"
	
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.getMainProdTimePrice(ro);
		System.out.println(xml);
	}
	

	public void testqueryProductDetails(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>queryProductDetails"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<productId>21163"
			+"</productId>"
	
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.queryProductDetails(ro);
		System.out.println(xml);
	}

	public void testgetRelateProduct(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>getRelateProduct"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<productId>20157"
			+"</productId>"
	
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.getRelateProduct(ro);
		System.out.println(xml);
	}
	
	
	public void testqueryTimePriceByProductIdAndDate(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>queryTimePriceByProductIdAndDate"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<productId>21177"
			+"</productId>"
			+"<date>2011-12-31"
			+"</date>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.queryTimePriceByProductIdAndDate(ro);
		System.out.println(xml);
	}
	

	public void testqueryUserOrderList(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>queryUserOrderList"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<userId>3428a92f3633f71001363404cd56000a123123123213"
			+"</userId>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<userId>3428a92f3633f71001363404cd56000a123123123213"
			+"</userId>"
			+"<pageSize>5"
			+"</pageSize>"
			+"<page>1"
			+"</page>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.queryUserOrderList(ro);
		System.out.println(xml);
	}


	
	public void testcommitOrder(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>commitOrder"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"<userId>ff8080812baf3d35012bce2ffdf329a9"
			+"</userId>"
			+"</head>"
			+"<body>"
			+"<userName>dengcheng"
			+"</userName>"
			+"<mobile>15301938332"
			+"</mobile>"
			+"<payTarget>TO_LVMAMA"
			+"</payTarget>"
			+"<couponCode>"
			+"</couponCode>"
			+"<visitTime>2011-12-22"
			+"</visitTime>"
			+"<list>"
			+"<orderItem>"
			+"<productId>3467"
			+"</productId>"
			+"<quantity>1"
			+"</quantity>"
			+"</orderItem>"
			+"</list>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.commitOrder(ro);
		System.out.println(xml);
	}
	
	public void testqueryGouponOnList(){
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<request>"
			+"<head>"
			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
			+"<deviceId>11231311212"+"</deviceId>"
			+"<deviceName>IPHONE"+"</deviceName>"
			+"<osName>IOS"+"</osName>"
			+"<osVersion>4.3"+"</osVersion>"
			+"<method>commitOrder"+"</method>"
			+"<pageName>切换城市"+"</pageName>"
			+"<version>2011-04-25 16:53:31</version>"
			+"</head>"
			+"<body>"
			+"<userId>ff8080812baf3d35012bce2ffdf329a9"
			+"</userId>"
			+"<userName>dengcheng"
			+"</userName>"
			+"<mobile>15301938332"
			+"</mobile>"
			+"<payTarget>TO_LVMAMA"
			+"</payTarget>"
			+"<couponCode>"
			+"</couponCode>"
			+"<visitTime>2011-11-22"
			+"</visitTime>"
			+"<list>"
			+"<order>"
			+"<productId>3467"
			+"</productId>"
			+"<quantity>12313123"
			+"</quantity>"

			+"</order>"
			+"</list>"
			+"</body>"
			+"</request>"; //线路
		System.out.println(reqXml);
		RequestObject ro = this.getRequestObjectFromXml(reqXml);
		String xml = clientService.queryGouponOnList(ro);
		System.out.println(xml);
	}
//	public void testgetMainProdTimePrice(){
//		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//			+"<request>"
//			+"<head>"
//			+"<firstChannel>IPHONE"+"</firstChannel>"+"<secondChannel>WL"+"</secondChannel>"
//			+"<deviceId>11231311212"+"</deviceId>"
//			+"<deviceName>IPHONE"+"</deviceName>"
//			+"<osName>IOS"+"</osName>"
//			+"<osVersion>4.3"+"</osVersion>"
//			+"<method>getMainProdTimePrice"+"</method>"
//			+"<pageName>切换城市"+"</pageName>"
//			+"<version>2011-04-25 16:53:31</version>"
//			+"</head>"
//			+"<body>"
//			+"<productId>21177"
//			+"</productId>"
//	
//			+"</body>"
//			+"</request>"; //线路
//		System.out.println(reqXml);
//		RequestObject ro = this.getRequestObjectFromXml(reqXml);
//		String xml = clientService.getMainProdTimePrice(ro);
//		System.out.println(xml);
//	}
}
