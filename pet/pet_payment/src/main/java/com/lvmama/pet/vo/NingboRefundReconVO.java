package com.lvmama.pet.vo;


/**
 * 宁波银行退款支付对象
 * @author ZHANG Nan
 */
public class NingboRefundReconVO {
	/**
	 * 客户号
	 */
	private String reqCustomerId;
	/**
	 * 退款提交我行平台时间
	 */
	private String gmtRefund;
	/**
	 * 退款状态
	 */
	private String refundStatus;
	/**
	 * 退款金额
	 */
	private String refundFee;
	/**
	 * 支付宝交易号
	 */
	private String tradeNo;
	/**
	 * 退款批次号
	 */
	private String batchNo;
	/**
	 * 商户传入的退款订单号
	 */
	private String outOrderNo;
	
	public String getReqCustomerId() {
		return reqCustomerId;
	}
	public void setReqCustomerId(String reqCustomerId) {
		this.reqCustomerId = reqCustomerId;
	}
	public String getGmtRefund() {
		return gmtRefund;
	}
	public void setGmtRefund(String gmtRefund) {
		this.gmtRefund = gmtRefund;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getOutOrderNo() {
		return outOrderNo;
	}
	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
}
