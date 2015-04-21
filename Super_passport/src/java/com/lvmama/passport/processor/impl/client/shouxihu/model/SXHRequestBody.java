package com.lvmama.passport.processor.impl.client.shouxihu.model;

import com.lvmama.passport.processor.impl.client.shouxihu.ShouxihuUtil;

/**
 * 扬州瘦西湖--请求报文消息体
 * @author lipengcheng
 *
 */
public class SXHRequestBody {
	/*这些属性都是解析回调接口所用,与构造报文没有任何关系*/
	private String serialId;//订单序列号
	private String orderStatus;//订单状态
	private String realTicketCount;//实取门票数量
	private String realPayAmount;//实付总金额
	private String ticketDate;//取票时间

	
	/**
	 * 构造保存订单报文
	 * @param orderInfo
	 * @return
	 */
	public static String buildBodyForCreateOrder(SXHOrderInfo orderInfo) {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(ShouxihuUtil.buildXmlElement("body", orderInfo.buildSaveOrder()));
		return xmlStr.toString();
	}
	
	/**
	 * 构造获取产品信息列表报文
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public static String buildBodyForGetProductList(Long page, Long pageSize) {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<body>");
		xmlStr.append(ShouxihuUtil.buildXmlElement("page", page.toString()));
		xmlStr.append(ShouxihuUtil.buildXmlElement("pageSize", pageSize.toString()));
		xmlStr.append("</body>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造重发二维码报文
	 * @param serialId
	 * @return
	 */
	public static String buildReSendDimensionalCode(String serialId) {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<body>");
		xmlStr.append(ShouxihuUtil.buildXmlElement("serialId", serialId));
		xmlStr.append("</body>");
		return xmlStr.toString();
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRealTicketCount() {
		return realTicketCount;
	}

	public void setRealTicketCount(String realTicketCount) {
		this.realTicketCount = realTicketCount;
	}

	public String getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(String realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public String getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}
	
}
