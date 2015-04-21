package com.lvmama.passport.processor.impl.client.shouxihu.model;

import com.lvmama.passport.processor.impl.client.shouxihu.ShouxihuUtil;

/**
 * 
 * @author lipengcheng
 * 瘦西湖产品信息
 * <order>
 			<serialId>{serialId}</serialId>
 			<comfirmNumber>{comfirmNumber}</comfirmNumber>
 			<sceneryId>{sceneryId}</sceneryId>
 			<sceneryName>{sceneryName}</sceneryName>
 			<ticketTypeId>{ticketTypeId}</ticketTypeId>
	        <ticketTypeName>{ticketTypeName}</ticketTypeName>
			<unitPrice>{unitPrice}</unitPrice>
			<ticketCount>{ticketCount}</ticketCount>
			<totalAmount>{totalAmount}</totalAmount>
			<realPayAmount>{RealPayAmount}</realPayAmount>
			<payType>{payType}</ payType >
			<playDate>{playDate}</playDate>
			<expiryDate>{ expiryDate }</expiryDate>
			<orderStatus>{orderStatus}</orderStatus>
			<travelerName>{travelerName}</travelerName>
			<travelerMobile>{travelerMobile}</travelerMobile>
			<orderDate>{orderDate}</orderDate>
			<identityCard>{identityCard}</identityCard>
		</order>
 *
 */
public class SXHOrderInfo {
	private String serialId;// 订单序列号(流水号)
	private String comfirmNumber;// 确认号
	private String sceneryId;// 景点ID
	private String sceneryName;// 景点名称
	private String ticketTypeId;// 门票类型Id
	private String ticketTypeName;// 门票类型
	private String unitPrice;// 单价(单张网上售价)
	private String ticketCount;// 预订数量
	private String totalAmount;// 总金额(单张网上售价*预订数量)
	private String realPayAmount;// 实付金额
	private String payType;// 支付方式
	private String playDate;// 游玩时间
	private String expiryDate;// 游玩有效截止日期(可不提供)
	private String orderStatus;// 订单状态
	private String travelerName;// 取票人姓名
	private String travelerMobile;// 取票人手机
	private String orderDate;// 预订时间
	private String identityCard;// 身份证
	
	/**
	 * 构造保存订单报文
	 * @return
	 */
	public String buildSaveOrder() {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<order>");
		xmlStr.append(ShouxihuUtil.buildXmlElement("serialId", serialId));
		xmlStr.append(ShouxihuUtil.buildXmlElement("comfirmNumber", comfirmNumber));
		xmlStr.append(ShouxihuUtil.buildXmlElement("sceneryId", sceneryId));
		xmlStr.append(ShouxihuUtil.buildXmlElement("sceneryName", sceneryName));
		xmlStr.append(ShouxihuUtil.buildXmlElement("ticketTypeId", ticketTypeId));
		xmlStr.append(ShouxihuUtil.buildXmlElement("ticketTypeName", ticketTypeName));
		xmlStr.append(ShouxihuUtil.buildXmlElement("unitPrice", unitPrice));
		xmlStr.append(ShouxihuUtil.buildXmlElement("ticketCount", ticketCount));
		xmlStr.append(ShouxihuUtil.buildXmlElement("totalAmount", totalAmount));
		xmlStr.append(ShouxihuUtil.buildXmlElement("realPayAmount", realPayAmount));
		xmlStr.append(ShouxihuUtil.buildXmlElement("payType", payType));
		xmlStr.append(ShouxihuUtil.buildXmlElement("playDate", playDate));
		xmlStr.append(ShouxihuUtil.buildXmlElement("expiryDate", expiryDate));
		xmlStr.append(ShouxihuUtil.buildXmlElement("orderStatus", orderStatus));
		xmlStr.append(ShouxihuUtil.buildXmlElement("travelerName", travelerName));
		xmlStr.append(ShouxihuUtil.buildXmlElement("travelerMobile", travelerMobile));
		xmlStr.append(ShouxihuUtil.buildXmlElement("orderDate", orderDate));
		xmlStr.append(ShouxihuUtil.buildXmlElement("identityCard", identityCard));
		xmlStr.append("</order>");
		return xmlStr.toString();
	}
	
	//setter and getter
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public String getComfirmNumber() {
		return comfirmNumber;
	}

	public void setComfirmNumber(String comfirmNumber) {
		this.comfirmNumber = comfirmNumber;
	}

	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}
	
	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPlayDate() {
		return playDate;
	}
	public void setPlayDate(String playDate) {
		this.playDate = playDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(String realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public String getTravelerName() {
		return travelerName;
	}

	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}

	public String getTravelerMobile() {
		return travelerMobile;
	}

	public void setTravelerMobile(String travelerMobile) {
		this.travelerMobile = travelerMobile;
	}

	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

}
