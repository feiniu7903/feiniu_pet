package com.lvmama.jinjiang.vo.order;

import com.lvmama.comm.utils.PriceUtil;

public class OrderPayInfo {

	private String externalOrderNo;
	private String payMode = "CREDIT";
	private String payStyle = "MAIN";
	private Long   payAmount;
	private String payToBankNo;
	private String transactionNo;
	public String getExternalOrderNo() {
		return externalOrderNo;
	}
	public void setExternalOrderNo(String externalOrderNo) {
		this.externalOrderNo = externalOrderNo;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getPayStyle() {
		return payStyle;
	}
	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}
	public String getPayAmount() {
		return ""+PriceUtil.convertToYuan(payAmount);
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayToBankNo() {
		return payToBankNo;
	}
	public void setPayToBankNo(String payToBankNo) {
		this.payToBankNo = payToBankNo;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	
	
}
