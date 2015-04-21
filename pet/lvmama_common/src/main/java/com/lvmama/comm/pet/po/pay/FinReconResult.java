package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;
import com.lvmama.comm.utils.PaymentUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;

/**
 * 对账结果对象
 * @author ZHANG Nan
 */
public class FinReconResult implements Serializable {
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 2350050722399243508L;
	/**
	 * 主键
	 */
	private Long reconResultId;		
	/**
	 * 我方对账流水号
	 */
	private String paymentTradeNo;	
	/**
	 * 银行对账流水号(一般银行称为商户订单号)
	 */
	private String bankPaymentTradeNo;
	/**
	 * 我方网关交易号
	 */
	private String gatewayTradeNo;	
	/**
	 * 银行网关交易号(一般银行称为交易流水号)
	 */
	private String bankGatewayTradeNo;
	/**
	 * 我方交易金额
	 */
	private Long amount;	
	/**
	 * 银行交易金额
	 */
	private Long bankAmount;	
	/**
	 * 我方交易时间
	 */
	private Date callbackTime;		
	/**
	 * 银行交易时间
	 */
	private Date transactionTime;	
	/**
	 * 交易类型(包括:驴妈妈支付收入、驴妈妈支付手续费、驴妈妈退款、驴妈妈退款手续费返还、驴妈妈申请提现到卡、银行平台操作普通提现、银行平台操作对外付款、驴妈妈现金账户充值)
	 */
	private String transactionType;
	/**
	 * 交易来源(包括:正式环境交易、测试环境交易)
	 */
	private String transactionSource;
	/**
	 * 网关code
	 */
	private String gateway;
	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 对账状态(未对账、成功、失败)
	 */
	private String reconStatus;
	/**
	 * 对账结果(对账后产生的提示、警告、失败原因等信息)
	 */
	private String reconResult;
	/**
	 * 银行对账日期(即:对的是银行哪天的帐)
	 */
	private Date bankReconTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String memo;
	
	private String glStatus;
	private Date glTime;
	
	//交易ID，有些交易没有流水号，只能用此识别
	private Long paymentId;
	public String getGatewayZH(){
		return PaymentUtil.getGatewayNameByGateway(this.getGateway());
	}
	
	public String getTransactionTypeZH(){
		return Constant.TRANSACTION_TYPE.getCnName(this.getTransactionType());	
	}
	
	public String getReconStatusZH(){
		return Constant.RECON_STATUS.getCnName(this.getReconStatus());	
	}
	public String getTransactionSourceZH(){
		return Constant.TRANSACTION_SOURCE.getCnName(this.getTransactionSource());	
	}
	public String getGlStatusZH(){
		return Constant.GL_STATUS.getCnName(this.getGlStatus());	
	}
	
	
	public Long getReconResultId() {
		return reconResultId;
	}
	public void setReconResultId(Long reconResultId) {
		this.reconResultId = reconResultId;
	}
	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}
	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}
	public String getBankPaymentTradeNo() {
		return bankPaymentTradeNo;
	}
	public void setBankPaymentTradeNo(String bankPaymentTradeNo) {
		this.bankPaymentTradeNo = bankPaymentTradeNo;
	}
	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}
	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}
	public String getBankGatewayTradeNo() {
		return bankGatewayTradeNo;
	}
	public void setBankGatewayTradeNo(String bankGatewayTradeNo) {
		this.bankGatewayTradeNo = bankGatewayTradeNo;
	}
	public Long getAmount() {
		return amount;
	}
	public Double getAmountValue() {
		Double amountValue = null;
		if(amount!=null){
			amountValue = (double) (amount/100.00);
		}else{
			amountValue = (double) 0;
		}
		return amountValue;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getBankAmount() {
		return bankAmount;
	}
	public Double getBankAmountValue() {
		Double bankAmountValue = null;
		if(bankAmount!=null){
			bankAmountValue = (double) (bankAmount/100.00);
		}else{
			bankAmountValue = (double) 0;
		}
		return bankAmountValue;
	}
	public void setBankAmount(Long bankAmount) {
		this.bankAmount = bankAmount;
	}
	public Date getCallbackTime() {
		return callbackTime;
	}
	public String getCallbackTimeStr(){
		String callbackTimeStr = UtilityTool.formatDate(callbackTime);
		return callbackTimeStr;
	}
	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public String getTransactionTimeStr(){
		String transactionTimeStr = UtilityTool.formatDate(transactionTime);
		return transactionTimeStr;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getReconStatus() {
		return reconStatus;
	}
	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}
	public String getReconResult() {
		return reconResult;
	}
	public void setReconResult(String reconResult) {
		this.reconResult = reconResult;
	}
	public Date getBankReconTime() {
		return bankReconTime;
	}
	public String getBankReconTimeStr(){
		String bankReconTimeStr = UtilityTool.formatDate(bankReconTime, "yyyy-MM-dd");
		return bankReconTimeStr;
	}
	public void setBankReconTime(Date bankReconTime) {
		this.bankReconTime = bankReconTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getTransactionSource() {
		return transactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}

	public String getGlStatus() {
		return glStatus;
	}

	public void setGlStatus(String glStatus) {
		this.glStatus = glStatus;
	}

	public Date getGlTime() {
		return glTime;
	}

	public void setGlTime(Date glTime) {
		this.glTime = glTime;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
}