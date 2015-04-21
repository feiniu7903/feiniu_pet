package com.lvmama.passport.processor.impl.client.shouxihu.model;

/**
 * 扬州瘦西湖对接--构造报文工具类
 * @author lipengcheng
 *
 */
public class SXHRequestInfo {
	private static String VERSION = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	private SXHRequestHead header;
	private SXHRequestBody body;
	/**
	 * 生成下单报文
	 * @param orderInfo
	 * @return
	 */
	public static String buildCreateOrder(SXHOrderInfo orderInfo) {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(VERSION);
		xmlStr.append("<request>");
		xmlStr.append(SXHRequestHead.buildHead("SaveOrderDetail"));
		xmlStr.append(SXHRequestBody.buildBodyForCreateOrder(orderInfo));
		xmlStr.append("</request>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造产品列表报文
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public static String buildGetProductList(Long page, Long pageSize){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(VERSION);
		xmlStr.append("<request>");
		xmlStr.append(SXHRequestHead.buildHead("GetProductList"));
		xmlStr.append(SXHRequestBody.buildBodyForGetProductList(page, pageSize));
		xmlStr.append("</request>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造重发凭证报文
	 * @param serialId
	 * @return
	 */
	public static String buildReSendDimensionalCode(String serialId) {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(VERSION);
		xmlStr.append("<request>");
		xmlStr.append(SXHRequestHead.buildHead("ReSendDimensionalCode"));
		xmlStr.append(SXHRequestBody.buildReSendDimensionalCode(serialId));
		xmlStr.append("</request>");
		return xmlStr.toString();
	}

	public SXHRequestHead getHeader() {
		return header;
	}

	public void setHeader(SXHRequestHead header) {
		this.header = header;
	}

	public SXHRequestBody getBody() {
		return body;
	}

	public void setBody(SXHRequestBody body) {
		this.body = body;
	}
	
}
