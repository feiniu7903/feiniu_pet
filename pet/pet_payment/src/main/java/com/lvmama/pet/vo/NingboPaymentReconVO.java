package com.lvmama.pet.vo;


/**
 * 宁波银行对账支付对象
 * @author ZHANG Nan
 */
public class NingboPaymentReconVO {
	/**
	 * 客户号
	 */
	private String reqCustomerId;
	/**
	 * 商户网站唯一订单号
	 */
	private String outTradeNo;
	/**
	 * 交易金额
	 */
	private String totalFee;
	/**
	 * 交易状态
	 */
	private String tradeStatus;
	/**
	 * 商品名称
	 */
	private String subject;
	/**
	 * 默认支付方式
	 */
	private String paymethod;
	/**
	 * 商品描述
	 */
	private String body;
	/**
	 * 公用回传参数
	 */
	private String extraCommonParam;
	/**
	 * 该笔订单当时请求时间（我行平台记录生成）
	 */
	private String reqTime;
	/**
	 * 支付宝交易号
	 */
	private String tradeNo;
	public String getReqCustomerId() {
		return reqCustomerId;
	}
	public void setReqCustomerId(String reqCustomerId) {
		this.reqCustomerId = reqCustomerId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getExtraCommonParam() {
		return extraCommonParam;
	}
	public void setExtraCommonParam(String extraCommonParam) {
		this.extraCommonParam = extraCommonParam;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
