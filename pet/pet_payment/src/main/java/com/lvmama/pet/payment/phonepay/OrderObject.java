package com.lvmama.pet.payment.phonepay;

import com.lvmama.comm.utils.PriceUtil;

public class OrderObject {
	private String orderId;//订单号码
	private String paytotal;//实付金额
	private String bankgate;//银行网关
	private String csNo;//客服编号
	private String cardusername;//信用卡名称
	private String mobilenumber;//手机号码
	//add for refund
	private String orgOldId;//原始订单号码
	private String orgMerDate;//原始交易日期
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public long getPayTotalFen() {
		return PriceUtil.convertToFen(Float.parseFloat(paytotal));
	}
	
	public String getPaytotal() {
		return paytotal;
	}
	public void setPaytotal(String paytotal) {
		this.paytotal = paytotal;
	}
	public String getBankgate() {
		return bankgate.toUpperCase();
	}
	public void setBankgate(String bankgate) {
		this.bankgate = bankgate.toUpperCase();
	}
	public String getCsNo() {
		return csNo;
	}
	public void setCsNo(String csNo) {
		this.csNo = csNo;
	}
	public String getCardusername() {
		return cardusername;
	}
	public void setCardusername(String cardusername) {
		this.cardusername = cardusername;
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getOrgOldId() {
		return orgOldId;
	}
	public void setOrgOldId(String orgOldId) {
		this.orgOldId = orgOldId;
	}
	public String getOrgMerDate() {
		return orgMerDate;
	}
	public void setOrgMerDate(String orgMerDate) {
		this.orgMerDate = orgMerDate;
	}
	
}