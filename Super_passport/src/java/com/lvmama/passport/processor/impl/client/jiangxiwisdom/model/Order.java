/**
 * @author tangJing
 * 2013-08-20
 *
 */
package com.lvmama.passport.processor.impl.client.jiangxiwisdom.model;
public class Order {
	private String action;//操作类型
	private String agentNo;//代理商的编码
	private String orderNo;//平台订单号
	private String custName;//客户姓名
	private String custMobile;//客户电话
	private String custId;//身份证
	private String ticketNo;//票务编码
	private String buyNum;//订购数量
	private String isDestine;//景区现付0 或1，1为景区现付 默认值0
	private String arriveDate;//游玩日期
	private String time;//通信时间
	private String sign;//通信签名
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getIsDestine() {
		return isDestine;
	}
	public void setIsDestine(String isDestine) {
		this.isDestine = isDestine;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}