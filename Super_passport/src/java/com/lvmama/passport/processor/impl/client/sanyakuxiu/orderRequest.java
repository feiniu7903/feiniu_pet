
package com.lvmama.passport.processor.impl.client.sanyakuxiu;
public class orderRequest{
	private String goodid;//商品id
	private String goodnum;//购买商品数量
	private String orderId;//订单号
	private String tel;//购票人手机号(成功后短信将发送二维码到该手机号)
	private String tuanname="驴妈妈";//固定值 网站名称
	private String salesPeople="驴妈妈";//固定值 驴妈妈
	private String sendway;//发送方式（如购买数量为5）1.一个码号包含全部张数 2.一个码号代表一张
	public String getGoodid() {
		return goodid;
	}
	public void setGoodid(String goodid) {
		this.goodid = goodid;
	}
	public String getGoodnum() {
		return goodnum;
	}
	public void setGoodnum(String goodnum) {
		this.goodnum = goodnum;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTuanname() {
		return tuanname;
	}
	public void setTuanname(String tuanname) {
		this.tuanname = tuanname;
	}
	public String getSalesPeople() {
		return salesPeople;
	}
	public void setSalesPeople(String salesPeople) {
		this.salesPeople = salesPeople;
	}
	public String getSendway() {
		return sendway;
	}
	public void setSendway(String sendway) {
		this.sendway = sendway;
	}
	
}