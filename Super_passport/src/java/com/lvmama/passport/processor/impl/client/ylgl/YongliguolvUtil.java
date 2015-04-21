package com.lvmama.passport.processor.impl.client.ylgl;

import com.lvmama.passport.processor.impl.client.ylgl.model.Request;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseProductInfo;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransSended;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class YongliguolvUtil {
	
	/**
	 * 申请码响应对象
	 * @param XmlRequest
	 * @return
	 */
	public static ResponseApplyCode getApplyCodeReq(String xmlResponse){
		XStream xstream = getApplyCodeXstreamObject();
		ResponseApplyCode obj = (ResponseApplyCode) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	
	/**
	 * 获取产品响应对象
	 * @param XmlRequest
	 * @return
	 */
	public static ResponseProductInfo getProductReq(String xmlResponse){
		XStream xstream = getProductInfoXstreamObject();
		ResponseProductInfo obj = (ResponseProductInfo) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	
	/**
	 * 重新申码产品响应对象
	 * @param XmlRequest
	 * @return
	 */
	public static ResponseReplayCode getReplayCodeReq(String xmlResponse){
		XStream xstream = getReplayCodeXstreamObject();
		ResponseReplayCode obj = (ResponseReplayCode) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	
	
	/**
	 * 重新申码产品响应对象
	 * 
	 * @param XmlRequest
	 * @return
	 */
	public static ResponseDestoryCode getDestoryCodeReq(String xmlResponse) {
		XStream xstream = getDestoryCodeXstreamObject();
		ResponseDestoryCode obj = (ResponseDestoryCode) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	/**
	 * 交易送达报文对象
	 * 
	 * @param XmlRequest
	 * @return
	 */
	public static ResponseTransSended getTransSendedReq(String xmlResponse) {
		XStream xstream = getTransSendeXstreamObject();
		ResponseTransSended obj = (ResponseTransSended) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	
	
	
	/**
	 * 交易验证报文对象
	 * 
	 * @param XmlRequest
	 * @return
	 */
	public static ResponseTransCheck getTransCheckReq(String xmlResponse) {
		XStream xstream = getTransCheckXstreamObject();
		ResponseTransCheck obj = (ResponseTransCheck) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getApplyCodeXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("business_trans",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode.class);
		xStream.alias("result",com.lvmama.passport.processor.impl.client.ylgl.model.Result.class);
		/** *****定义类中属性********** */
		xStream.aliasField("response_type",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode.class,"responseType");
		xStream.aliasField("sys_seq",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode.class,"sysSeq");
		xStream.aliasField("req_seq",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode.class,"reqSeq");
		xStream.aliasField("trans_time",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode.class,"transTime");
		xStream.aliasField("additional",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode.class,"additional");
		
		xStream.aliasField("result",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode.class,"result");
		xStream.aliasField("id",com.lvmama.passport.processor.impl.client.ylgl.model.Result.class,"id");
		xStream.aliasField("comment",com.lvmama.passport.processor.impl.client.ylgl.model.Result.class,"comment");
		return xStream;
	}
	
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getReplayCodeXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("business_trans",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode.class);
		xStream.alias("result",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode.class);
		/** *****定义类中属性********** */
		xStream.aliasField("response_type",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode.class,"responseType");
		xStream.aliasField("sys_seq",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode.class,"sysSeq");
		xStream.aliasField("req_seq",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode.class,"reqSeq");
		xStream.aliasField("trans_time",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode.class,"transTime");
		
		xStream.aliasField("result",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode.class,"result");
		xStream.aliasField("id",com.lvmama.passport.processor.impl.client.ylgl.model.Result.class,"id");
		xStream.aliasField("comment",com.lvmama.passport.processor.impl.client.ylgl.model.Result.class,"comment");
		return xStream;
	}
	
	
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getDestoryCodeXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("business_trans",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class);
		xStream.alias("result",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class);
		/** *****定义类中属性********** */
		xStream.aliasField("response_type",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class,"responseType");
		xStream.aliasField("sys_seq",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class,"sysSeq");
		xStream.aliasField("req_seq",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class,"reqSeq");
		xStream.aliasField("trans_time",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class,"transTime");
		
		xStream.aliasField("result",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class,"result");
		xStream.aliasField("id",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class,"id");
		xStream.aliasField("comment",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode.class,"comment");
		return xStream;
	}
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getProductInfoXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("business_trans",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseProductInfo.class);
		xStream.alias("product",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class);
		/** *****定义类中属性********** */
		xStream.aliasField("response_type",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseProductInfo.class,"responseType");
		xStream.aliasField("products",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseProductInfo.class,"products");
		
		xStream.aliasField("product",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"product");
		xStream.aliasField("id",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productId");
		xStream.aliasField("product_name",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productName");
		xStream.aliasField("product_facevalue",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productFaceValue");
		xStream.aliasField("product_platformvalue",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productPlatformValue");
		xStream.aliasField("provide_salePrice",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productSalePrice");
		xStream.aliasField("product_tickettype",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productTickettype");
		xStream.aliasField("product_ticketexplain",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productTicketExplain");
		xStream.aliasField("provide_tel",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"provideTel");
		xStream.aliasField("provide_address",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"provideAddress");
		xStream.aliasField("product_begintime",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productBegintime");
		xStream.aliasField("product_endtime",com.lvmama.passport.processor.impl.client.ylgl.model.Product.class,"productEndTime");
		return xStream;
	}
	
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getTransSendeXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("business_trans",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransSended.class);
		xStream.alias("er_deliver_report_sync_request",com.lvmama.passport.processor.impl.client.ylgl.model.ErDeliverReportSyncRequest.class);
		/** *****定义类中属性********** */
		xStream.aliasField("request_type",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransSended.class,"requestType");
		xStream.aliasField("er_deliver_report_sync_request",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransSended.class,"erDeliverReportSyncRequest");
		
		xStream.aliasField("organization",com.lvmama.passport.processor.impl.client.ylgl.model.ErDeliverReportSyncRequest.class,"organization");
		xStream.aliasField("sp_seq",com.lvmama.passport.processor.impl.client.ylgl.model.ErDeliverReportSyncRequest.class,"spSeq");
		xStream.aliasField("deliver_time",com.lvmama.passport.processor.impl.client.ylgl.model.ErDeliverReportSyncRequest.class,"deliverTime");
		xStream.aliasField("deliver_status",com.lvmama.passport.processor.impl.client.ylgl.model.ErDeliverReportSyncRequest.class,"deliverStatus");
		xStream.aliasField("deliver_detail",com.lvmama.passport.processor.impl.client.ylgl.model.ErDeliverReportSyncRequest.class,"deliverDetail");
		
		return xStream;
	}
	
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getTransCheckXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("VerifySyncReq",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class);

		xStream.aliasField("SpSeq",com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "spSeq");
		xStream.aliasField("TransType", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "transType");
		xStream.aliasField("PGoodsId", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "pgoodsId");
		xStream.aliasField("PGoodsName", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "pgoodsName");
		xStream.aliasField("Amt", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "amt");
		xStream.aliasField("ResiduaryAmt", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "residuaryAmt");
		xStream.aliasField("ResiduaryTimes", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "residuaryTimes");
		xStream.aliasField("TransTime", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "transTime");
		xStream.aliasField("Status", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "status");
		xStream.aliasField("PhoneNo", com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck.class, "phoneNo");
		
		return xStream;
	}
	
	
	public static void main(String[] args) {
		Request request = new Request();
		request.setRequestType("roduct_get_request");
		request.setOrganization("");
		request.setUserId("test");
		request.setPwd("000000");
		request.toProductInfoRequestXml();
		
		
	}

}
