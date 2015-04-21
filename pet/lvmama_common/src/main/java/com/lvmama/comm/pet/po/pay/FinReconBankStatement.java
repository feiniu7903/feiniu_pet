package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;
/**
 * 银行对账单明细信息
 * @author ranlongfei 2012-6-26
 * @version
 */
public class FinReconBankStatement implements Serializable {
	
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = -5887390758902376104L;
	
	/**
	 * 主键
	 */
	private Long reconBankStatementId;			
	/**
	 * 网关CODE
	 */
	private String gateway;			
	/**
	 * 银行对账流水号(一般银行称为商户订单号)
	 */
	private String bankPaymentTradeNo;			
	/**
	 * 银行网关交易号(一般银行称为交易流水号)
	 */
	private String bankGatewayTradeNo;			
	/**
	 * 收入金额
	 */
	private Long amount;		
	
	/**
	 * 支出金额
	 */
	private Long outAmount;
	/**
	 * 银行交易时间
	 */
	private Date transactionTime;			
	/**
	 * 交易类型(包括:驴妈妈支付收入、驴妈妈支付手续费、驴妈妈退款、驴妈妈退款手续费返还、驴妈妈申请提现到卡、银行平台操作普通提现、银行平台操作对外付款、驴妈妈现金账户充值)
	 */
	private String transactionType;			
	/**
	 * 下载交易明细的日期(yyyy-MM-dd)
	 */
	private Date downloadTime;			
	/**
	 * 创建时间
	 */
	private Date createTime;			
	/**
	 * 备注
	 */
	private String memo;
	
	/**
	 * 是否不需要勾兑(支付手续费、退款手续费、普通提现、对外付款 、废单重下 无法对账)
	 * @author ZHANG Nan
	 * @return
	 */
	public boolean isUnneedRecon(){
		if(Constant.TRANSACTION_TYPE.PAYMENT_FEE.name().equals(this.transactionType)
				||Constant.TRANSACTION_TYPE.REFUNDMENT_FEE.name().equals(this.transactionType)
				||Constant.TRANSACTION_TYPE.NORMAL_DRAWCASH.name().equals(this.transactionType)
				||Constant.TRANSACTION_TYPE.FOREIGN_PAYMENT.name().equals(this.transactionType)){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否为收入类型的交易
	 * @author ZHANG Nan
	 * @return
	 */
	public boolean isIncome(){
		if(Constant.TRANSACTION_TYPE.PAYMENT.name().equals(this.transactionType)
				||Constant.TRANSACTION_TYPE.REFUNDMENT_FEE.name().equals(this.transactionType)
				||Constant.TRANSACTION_TYPE.CASH_RECHARGE.name().equals(this.transactionType)){
			return true;
		}
		return false;
	}
	/**
	 * 根据收入类型 获取用于比较的金额
	 * @author ZHANG Nan
	 * @return
	 */
	public Long getCompareAmount(){
		if(isIncome()){
			return getAmount();
		}
		else{
			return getOutAmount();
		}
	}
	
	
	public Long getReconBankStatementId() {
		return reconBankStatementId;
	}
	public void setReconBankStatementId(Long reconBankStatementId) {
		this.reconBankStatementId = reconBankStatementId;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getBankPaymentTradeNo() {
		return bankPaymentTradeNo;
	}
	public void setBankPaymentTradeNo(String bankPaymentTradeNo) {
		this.bankPaymentTradeNo = bankPaymentTradeNo;
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
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Date getTransactionTime() {
		return transactionTime;
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
	public Date getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
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
	public Long getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(Long outAmount) {
		this.outAmount = outAmount;
	}
}