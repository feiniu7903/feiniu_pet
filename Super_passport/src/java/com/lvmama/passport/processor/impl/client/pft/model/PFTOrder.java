package com.lvmama.passport.processor.impl.client.pft.model;

public class PFTOrder {
    String agentAcc; //代理商账户
    String agentPwd; //代理商密码
    String scenceFlag; //供应商标识串
    String scenicSpotId; //景区ID
    String ticketId; //门票ID
    String orderId; //订单号
    String ticketPrice; //门票价格(驴妈妈结算票价)
	String ticketNum; //门票数量
    String visitDate; //游玩时间(YYYY-MM-DD)
    String userNameWhoGetTicket; //取票人姓名
    String userMobilePhoneWhoGetTicket; //取票人手机
    String contactorMobilePhone; //联系人手机
    String isNeedSendSMS; //是否需要发送短信(0:发送 1:不发送)
    String payType; //扣款方式(0:使用账户余额   2:使用供应商处余额   注：余额不足返回错误122)
    String orderSubmitType; //下单方式(0:正常下单   1:手机用户下单   注：如无特殊请使用正常下单)
    String assemblyArea; //集合地点(线路时需要,可为空)
    String teamNo; //团号(线路时需要,可为空)
    String unionTicketId; //联票ID 未开放 值为0
    String packageTicketId; //套票ID 未开放 值为0
    String supplierId; //供应商ID
    
	public String getAgentAcc() {
		return agentAcc;
	}
	public void setAgentAcc(String agentAcc) {
		this.agentAcc = agentAcc;
	}
	public String getAgentPwd() {
		return agentPwd;
	}
	public void setAgentPwd(String agentPwd) {
		this.agentPwd = agentPwd;
	}
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public String getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getUserNameWhoGetTicket() {
		return userNameWhoGetTicket;
	}
	public void setUserNameWhoGetTicket(String userNameWhoGetTicket) {
		this.userNameWhoGetTicket = userNameWhoGetTicket;
	}
	public String getUserMobilePhoneWhoGetTicket() {
		return userMobilePhoneWhoGetTicket;
	}
	public void setUserMobilePhoneWhoGetTicket(String userMobilePhoneWhoGetTicket) {
		this.userMobilePhoneWhoGetTicket = userMobilePhoneWhoGetTicket;
	}
	public String getContactorMobilePhone() {
		return contactorMobilePhone;
	}
	public void setContactorMobilePhone(String contactorMobilePhone) {
		this.contactorMobilePhone = contactorMobilePhone;
	}
	public String getIsNeedSendSMS() {
		return isNeedSendSMS;
	}
	public void setIsNeedSendSMS(String isNeedSendSMS) {
		this.isNeedSendSMS = isNeedSendSMS;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOrderSubmitType() {
		return orderSubmitType;
	}
	public void setOrderSubmitType(String orderSubmitType) {
		this.orderSubmitType = orderSubmitType;
	}
	public String getAssemblyArea() {
		return assemblyArea;
	}
	public void setAssemblyArea(String assemblyArea) {
		this.assemblyArea = assemblyArea;
	}
	public String getTeamNo() {
		return teamNo;
	}
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}
	public String getUnionTicketId() {
		return "0";
	}
	public void setUnionTicketId(String unionTicketId) {
		this.unionTicketId = unionTicketId;
	}
	public String getPackageTicketId() {
		return "0";
	}
	public void setPackageTicketId(String packageTicketId) {
		this.packageTicketId = packageTicketId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
    public String getScenceFlag() {
		return scenceFlag;
	}
	public void setScenceFlag(String scenceFlag) {
		this.scenceFlag = scenceFlag;
	}
}
