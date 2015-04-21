package com.lvmama.passport.processor.impl.client.haichang.model;

public class HaichangOrder {
	private String agentOrderId;// 代理商订单号
	private String agentId;// 代理商编号
	private String agentPassword;// 代理商密码
	private String companyId;// 公司代码
	private String visitorName;// 游客姓名
	private String visitorPhoneNumber;// 游客手机号
	private String identificationCardNumber;// 身份证
	private String ticketType; // 票种ID
	private String ticketNumber; // 数量
	private String ticketPrice; // 票价 Y
	private String timeStart; // 有效时间开始 Y yyyy-MM-dd HH:mm:ss
	private String timeEnd;// 有效时间结束 yyyy-MM-dd HH:mm:ss
	private String flagPayOffline; // 到付标识 0:非到付、1:到付
	private String flagPayOnline; // 支付标识 0:非在线支付、1:在线支付
	public String getAgentOrderId() {
		return agentOrderId;
	}
	public void setAgentOrderId(String agentOrderId) {
		this.agentOrderId = agentOrderId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentPassword() {
		return agentPassword;
	}
	public void setAgentPassword(String agentPassword) {
		this.agentPassword = agentPassword;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public String getVisitorPhoneNumber() {
		return visitorPhoneNumber;
	}
	public void setVisitorPhoneNumber(String visitorPhoneNumber) {
		this.visitorPhoneNumber = visitorPhoneNumber;
	}
	public String getIdentificationCardNumber() {
		return identificationCardNumber;
	}
	public void setIdentificationCardNumber(String identificationCardNumber) {
		this.identificationCardNumber = identificationCardNumber;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getFlagPayOffline() {
		return flagPayOffline;
	}
	public void setFlagPayOffline(String flagPayOffline) {
		this.flagPayOffline = flagPayOffline;
	}
	public String getFlagPayOnline() {
		return flagPayOnline;
	}
	public void setFlagPayOnline(String flagPayOnline) {
		this.flagPayOnline = flagPayOnline;
	}

}
