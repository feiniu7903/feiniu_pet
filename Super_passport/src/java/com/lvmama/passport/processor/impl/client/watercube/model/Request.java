package com.lvmama.passport.processor.impl.client.watercube.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.passport.processor.impl.client.watercube.WaterCubeUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 水魔方--请求报文构造类
 * @author lipengcheng
 *
 */
public class Request {
	private static Log LOG = LogFactory.getLog(Request.class);
	private static final String VERSION_INFO = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	private Order order;// 订单信息
	
	public Request(Order order) {
		this.order = order;
	}
	
	/**
	 * 构造提交订单报文
	 * @return
	 */
	public String buildAddOrderXml() {
		String reqSeq = WaterCubeUtil.REQUESET_TYPE.add_order.name();
		String orderInfo = order.buildAddOrderXml();
		return buildCommonXml(reqSeq, orderInfo);
	}
	
	/**
	 * 构造查询订单报文
	 * @return
	 */
	public String buildQueryOrderXml(){
		String reqSeq = WaterCubeUtil.REQUESET_TYPE.query_order.name();
		String orderInfo = order.buildQueryOrderXml();
		return buildCommonXml(reqSeq,orderInfo);
	}
	
	/**
	 * 构造取消订单报文
	 * @return
	 */
	public String buildCancelOrderXml(){
		String reqSeq = WaterCubeUtil.REQUESET_TYPE.cancel_order.name();
		String orderInfo = order.buildCancelOrderXml();
		return buildCommonXml(reqSeq,orderInfo);
	}
	
	/**
	 * 构造重发电子票报文
	 * @return
	 */
	public String buildRepeatOrderXml(){
		String reqSeq = WaterCubeUtil.REQUESET_TYPE.repeat_order.name();
		String orderInfo = order.buildRepeatOrderXml();
		return buildCommonXml(reqSeq,orderInfo);
	}
	
	/**
	 * 构造转发电子票报文
	 * @return
	 */
	public String buildSendtoOrderXml(){
		String reqSeq = WaterCubeUtil.REQUESET_TYPE.sendto_order.name();
		String orderInfo = order.buildSendtoOrderXml();
		return buildCommonXml(reqSeq,orderInfo);
	}
	
	/**
	 * 构造报文的公用元素
	 * @param requestType
	 * @return
	 */
	public String buildCommonXml(String requestType,String orderInfo){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(VERSION_INFO);
		xmlStr.append("<business_trans>");
		xmlStr.append(WaterCubeUtil.buildXmlElement("request_type", requestType));
		xmlStr.append(WaterCubeUtil.buildXmlElement("organization", WebServiceConstant.getProperties("watercube.organization")));
		xmlStr.append(WaterCubeUtil.buildXmlElement("password", WebServiceConstant.getProperties("watercube.password")));
		xmlStr.append(WaterCubeUtil.buildXmlElement("req_seq", WaterCubeUtil.getReqSeq()));
		xmlStr.append(WaterCubeUtil.buildXmlElement("order", orderInfo));
		xmlStr.append("</business_trans>");
		String result = xmlStr.toString();
		LOG.info("request " + requestType + ": " + result);
		return WaterCubeUtil.encrypt(result);
	}
	
}
