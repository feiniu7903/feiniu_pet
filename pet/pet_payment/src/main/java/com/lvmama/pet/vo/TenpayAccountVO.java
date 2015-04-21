package com.lvmama.pet.vo;

import com.lvmama.comm.vo.Constant;



/**
 * 财付通对账返回对象
 * @author zhangjie 
 * @version
 */
public class TenpayAccountVO {

	
	/**
	 * 交易时间
	 */
	private String transTime;
	
	/**
	 * 财付通订单号
	 */
	private String tenpayTradeNo;
	
	/**
	 * 商户订单号
	 */
	private String merchTradeNo;

	/**
	 * 支付类型
	 */
	private String payType;

	/**
	 * 银行订单号
	 */
	private String bankPaymentTradeNo;

	/**
	 * 交易状态
	 */
	private String payState;

	/**
	 * 订单交易金额
	 */
	private String tradeAmount;
	
	/**
	 * 退款单号
	 */
	private String refundTradeNo;

	/**
	 * 退款金额
	 */
	private String refundAmount;

	/**
	 * 退款状态
	 */
	private String refundState;

	/**
	 * 交易说明
	 */
	private String transShow;

	public String getTransactionType(){
		if("0".equals(refundTradeNo)){
			return Constant.TRANSACTION_TYPE.PAYMENT.name();
		}else{
			return Constant.TRANSACTION_TYPE.REFUNDMENT.name();
			
		}
	}
	
	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTenpayTradeNo() {
		return tenpayTradeNo;
	}

	public void setTenpayTradeNo(String tenpayTradeNo) {
		this.tenpayTradeNo = tenpayTradeNo;
	}

	public String getMerchTradeNo() {
		return merchTradeNo;
	}

	public void setMerchTradeNo(String merchTradeNo) {
		this.merchTradeNo = merchTradeNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBankPaymentTradeNo() {
		return bankPaymentTradeNo;
	}

	public void setBankPaymentTradeNo(String bankPaymentTradeNo) {
		this.bankPaymentTradeNo = bankPaymentTradeNo;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getRefundTradeNo() {
		return refundTradeNo;
	}

	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public String getTransShow() {
		return transShow;
	}

	public void setTransShow(String transShow) {
		this.transShow = transShow;
	}
	
}
