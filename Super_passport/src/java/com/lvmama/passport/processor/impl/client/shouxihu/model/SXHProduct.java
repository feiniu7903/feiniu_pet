package com.lvmama.passport.processor.impl.client.shouxihu.model;

/**
 * 瘦西湖对接--产品
 * @author lipengcheng
 *
 */
public class SXHProduct {
	private String sceneryId;// 景点Id
	private String sceneryName;// 景点名称
	private String ticketTypeId;// 门票类型Id
	private String ticketTypeName;// 门票类型
	private String parPrice;// 票面价格
	private String webPrice;// 网络售价
	private String settlementPrice;// 结算价格
	
	public void printProductInfo() {
		System.out.println("sceneryId:" + sceneryId);
		System.out.println("sceneryName:" + sceneryName);
		System.out.println("ticketTypeId:" + ticketTypeId);
		System.out.println("ticketTypeName:" + ticketTypeName);
		System.out.println("parPrice:" + parPrice);
		System.out.println("webPrice:" + webPrice);
		System.out.println("settlementPrice:" + settlementPrice);
	}
	
	//setter and getter
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
	public String getParPrice() {
		return parPrice;
	}
	public void setParPrice(String parPrice) {
		this.parPrice = parPrice;
	}
	public String getWebPrice() {
		return webPrice;
	}
	public void setWebPrice(String webPrice) {
		this.webPrice = webPrice;
	}
	public String getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(String settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	
}
