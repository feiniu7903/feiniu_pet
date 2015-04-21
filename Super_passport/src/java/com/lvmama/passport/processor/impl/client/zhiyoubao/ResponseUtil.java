package com.lvmama.passport.processor.impl.client.zhiyoubao;

import com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ResponseUtil {
	
	/**
	 * 解析响应数据转化为对象
	 * @param XmlRequest
	 * @return
	 */
	public static PWBResponse getPWBResponse(String xmlResponse){
		XStream xstream = getResponseXstreamObject();
		PWBResponse response=(PWBResponse)xstream.fromXML(xmlResponse);
		return response;
	}
	/**
	 * 解析发码图片相应数据转化为对象
	 * @param xmlResponse
	 * @return
	 */
	public static PWBResponse getCodeImgRes(String xmlResponse){
		XStream xstream = getResponseImgXstreamObject();
		PWBResponse response=(PWBResponse)xstream.fromXML(xmlResponse);
		return response;
	}
	
	/**
	 * 解析相应数据转化为对象
	 * @param xmlResponse
	 * @return
	 */
	public static PWBResponse getCodeCancelRes(String xmlResponse){
		XStream xstream = getResponseCancelXstreamObject();
		PWBResponse response=(PWBResponse)xstream.fromXML(xmlResponse);
		return response;
	}
	
	
	/**
	 * 初始化xml配置，并返回响应XStream对象
	 * @return
	 */
	private static XStream getResponseXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("PWBResponse",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class);
		xStream.alias("scenicOrder",com.lvmama.passport.processor.impl.client.zhiyoubao.model.ScenicOrder.class);
		xStream.alias("familyOrder",com.lvmama.passport.processor.impl.client.zhiyoubao.model.FamilyOrder.class);
		/** *****定义类中属性********** */
		xStream.aliasField("transactionName",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"transactionName");
		xStream.aliasField("code",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"code");
		xStream.aliasField("description",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"description");
		xStream.aliasField("orderResponse",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"orderResponse");
		xStream.aliasField("order",com.lvmama.passport.processor.impl.client.zhiyoubao.model.OrderResponse.class,"order");
		xStream.aliasField("certificateNo",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"certificateNo");
		xStream.aliasField("linkName",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"linkName");
		xStream.aliasField("linkMobile",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"linkMobile");
		xStream.aliasField("orderCode",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"orderCode");
		xStream.aliasField("orderPrice",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"orderPrice");
		xStream.aliasField("payMethod",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"payMethod");
		xStream.aliasField("groupNo",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"groupNo");
		xStream.aliasField("assistCheckNo",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"assistCheckNo");
		xStream.aliasField("payStatus",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"payStatus");
		xStream.aliasField("src",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"src");
		xStream.aliasField("scenicOrders",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"scenicOrders");
		xStream.aliasField("orderCode",com.lvmama.passport.processor.impl.client.zhiyoubao.model.ScenicOrder.class,"orderCode");
		xStream.aliasField("totalPrice",com.lvmama.passport.processor.impl.client.zhiyoubao.model.ScenicOrder.class,"totalPrice");
		xStream.aliasField("price",com.lvmama.passport.processor.impl.client.zhiyoubao.model.ScenicOrder.class,"price");
		xStream.aliasField("quantity",com.lvmama.passport.processor.impl.client.zhiyoubao.model.ScenicOrder.class,"quantity");
		xStream.aliasField("occDate",com.lvmama.passport.processor.impl.client.zhiyoubao.model.ScenicOrder.class,"occDate");
		xStream.aliasField("goodsCode",com.lvmama.passport.processor.impl.client.zhiyoubao.model.ScenicOrder.class,"goodsCode");
		xStream.aliasField("goodsName",com.lvmama.passport.processor.impl.client.zhiyoubao.model.ScenicOrder.class,"goodsName");
		
		xStream.aliasField("familyOrders",com.lvmama.passport.processor.impl.client.zhiyoubao.model.Order.class,"familyOrders");
		xStream.aliasField("orderCode",com.lvmama.passport.processor.impl.client.zhiyoubao.model.FamilyOrder.class,"orderCode");
		xStream.aliasField("totalPrice",com.lvmama.passport.processor.impl.client.zhiyoubao.model.FamilyOrder.class,"totalPrice");
		xStream.aliasField("price",com.lvmama.passport.processor.impl.client.zhiyoubao.model.FamilyOrder.class,"price");
		xStream.aliasField("quantity",com.lvmama.passport.processor.impl.client.zhiyoubao.model.FamilyOrder.class,"quantity");
		xStream.aliasField("occDate",com.lvmama.passport.processor.impl.client.zhiyoubao.model.FamilyOrder.class,"occDate");
		xStream.aliasField("goodsCode",com.lvmama.passport.processor.impl.client.zhiyoubao.model.FamilyOrder.class,"goodsCode");
		xStream.aliasField("goodsName",com.lvmama.passport.processor.impl.client.zhiyoubao.model.FamilyOrder.class,"goodsName");
		return xStream;
	}
	/**
	 * 初始化xml配置，并返回响应XStream对象
	 * @return
	 */
	private static XStream getResponseCancelXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("PWBResponse",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class);
		/** *****定义类中属性********** */
		xStream.aliasField("transactionName",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"transactionName");
		xStream.aliasField("code",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"code");
		xStream.aliasField("description",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"description");
		return xStream;
	}
	/**
	 * 初始化xml配置，并返回响应XStream对象
	 * @return
	 */
	private static XStream getResponseImgXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("PWBResponse",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class);
		/** *****定义类中属性********** */
		xStream.aliasField("transactionName",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"transactionName");
		xStream.aliasField("code",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"code");
		xStream.aliasField("description",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"description");
		xStream.aliasField("img",com.lvmama.passport.processor.impl.client.zhiyoubao.model.PWBResponse.class,"img");
		return xStream;
	}
}
