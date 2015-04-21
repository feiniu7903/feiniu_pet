package com.lvmama.comm.pet.po.fin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.lvmama.comm.utils.PaymentUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 财务业务流水记录
 * 
 * @author taiqichao
 * 
 */
public class FinBizItem implements Serializable {

	private static final long serialVersionUID = 2350050722399243508L;
	
	/**
	 * 主键
	 */
	private Long bizItemId;
	
	/**
	 * 我方交易金额
	 */
	private Long amount;
	
	/**
	 * 我方交易金额
	 * 非持久化
	 */
	private BigDecimal amountBig;
	
	/**
	 * 银行交易金额
	 */
	private Long bankAmount;
	
	/**
	 * 银行交易金额
	 * 非持久化
	 */
	private BigDecimal bankAmountBig;
	
	/**
	 * 我方交易时间
	 */
	private Date callbackTime;
	
	/**
	 * 银行交易时间
	 */
	private Date transactionTime;
	
	/**
	 * 交易类型(包括:驴妈妈支付收入、驴妈妈支付手续费、驴妈妈退款、驴妈妈退款手续费返还、驴妈妈申请提现到卡、银行平台操作普通提现、银行平台操作对外付款
	 * 、驴妈妈现金账户充值)
	 */
	private String transactionType;
	
	/**
	 * 网关code
	 */
	private String gateway;
	
	/**
	 * 订单号
	 */
	private Long orderId;
	
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

	/**
	 * 记账状态
	 */
	private String glStatus;

	/**
	 * 记账时间
	 */
	private Date glTime;
	
	/**
	 * 费用类型
	 */
	private String feeType;

	/**
	 * 状态 (CREATE:新建;MATCH:匹配;POST:已入账)
	 * @see com.lvmama.comm.pet.po.fin.FinBizItem.BIZ_STATUS
	 */
	private String bizStatus;

	/**
	 * 是否取消 Y:是 N:否 
	 */
	private String cancelStatus;

	/**
	 * 取消人 
	 */
	private String cancelUser;

	/**
	 * 取消日期 
	 */
	private Date cancelTime;

	/**
	 * 创建人 
	 */
	private String createUser;

	/**
	 * 关联红字流水
	 */
	private String bizNo;
	
	/**
	 * 勾兑结果id
	 */
	private Long reconResultId;

	public String getGatewayZH() {
		return PaymentUtil.getGatewayNameByGateway(this.getGateway());
	}

	public String getTransactionTypeZH() {
		return Constant.TRANSACTION_TYPE.getCnName(this.getTransactionType());
	}

	public String getGlStatusZH() {
		return Constant.GL_STATUS.getCnName(this.getGlStatus());
	}
	
	public String getBizStatusZH() {
		return BIZ_STATUS.getCnName(this.getBizStatus());
	}

	public Long getBizItemId() {
		return bizItemId;
	}

	public void setBizItemId(Long bizItemId) {
		this.bizItemId = bizItemId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(Long bankAmount) {
		this.bankAmount = bankAmount;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
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

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Date getBankReconTime() {
		return bankReconTime;
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

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(String bizStatus) {
		this.bizStatus = bizStatus;
	}

	public String getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public String getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public Long getReconResultId() {
		return reconResultId;
	}

	public void setReconResultId(Long reconResultId) {
		this.reconResultId = reconResultId;
	}

	public String getBizStatusCn(){
		return BIZ_STATUS.getCnName(this.bizStatus);
	}
	
	public BigDecimal getAmountBig() {
		return amountBig;
	}

	public void setAmountBig(BigDecimal amountBig) {
		this.amountBig = amountBig;
	}

	public BigDecimal getBankAmountBig() {
		return bankAmountBig;
	}

	public void setBankAmountBig(BigDecimal bankAmountBig) {
		this.bankAmountBig = bankAmountBig;
	}



	/**
	 * 业务状态
	 * @author taiqichao
	 *
	 */
	public enum BIZ_STATUS{
		CREATE("新建"),
		MATCH("匹配"),
		POST("已入账"),
		FAIL("入账失败");
		private String cnName;
		BIZ_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(BIZ_STATUS item:BIZ_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}

}