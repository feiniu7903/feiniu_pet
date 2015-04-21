package com.lvmama.tnt.cashaccount.po;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntUser;


/**
 * 现金帐户充值记录
 * @author gaoxin
 * @version 1.0
 */
public class TntCashRecharge implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 *  cashRechargeId
	 */
	private java.lang.Long cashRechargeId;
	/**
	 *  主键，与用户表USER_ID相同
	 */
	private java.lang.Long cashAccountId;
	/**
	 *  对应转账单编号
	 */
	private java.lang.String billNo;
	/**
	 *  支付网关(渠道)
	 */
	private java.lang.String paymentGateway;
	/**
	 *  支付金额
	 */
	private java.lang.Long amount;
	
	/**
	 *  银行名称或支付宝
	 */
	private java.lang.String bankName;
	/**
	 *  帐户号码
	 */
	private java.lang.String bankAccount;
	/**
	 *  帐户姓名
	 */
	private java.lang.String bankAccountName;
	/**
	 *  开户行
	 */
	private java.lang.String kaiHuHang;
	
	/**
	 * 银行转账日期
	 */
	private String billTime;
	/**
	 *  支付状态(CREATE,SUCCESS,FAIL)
	 */
	private java.lang.String status;
	
	private String reason;
	/**
	 *  创建时间
	 */
	private java.util.Date createTime ;
	//columns END
	
	private String userRealName;
	
	private String isWaiting;
	
	private TntUser tntUser = new TntUser();

	public TntCashRecharge(){
	}

	public TntCashRecharge(
		java.lang.Long cashRechargeId
	){
		this.cashRechargeId = cashRechargeId;
	}

	public void setCashRechargeId(java.lang.Long value) {
		this.cashRechargeId = value;
	}
	
	public java.lang.Long getCashRechargeId() {
		return this.cashRechargeId;
	}
	public void setCashAccountId(java.lang.Long value) {
		this.cashAccountId = value;
	}
	
	public java.lang.Long getCashAccountId() {
		return this.cashAccountId;
	}
	public void setPaymentGateway(java.lang.String value) {
		this.paymentGateway = value;
	}
	
	public java.lang.String getPaymentGateway() {
		return this.paymentGateway;
	}
	public void setAmount(java.lang.Long value) {
		this.amount = value;
	}
	
	public java.lang.Long getAmount() {
		return this.amount;
	}
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public java.lang.String getCnStatus() {
		if(StringUtils.isNotEmpty(this.status)){
			return TntConstant.CASH_RECHARGE_STATUS.getCnName(this.status);
		}
		return this.status;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	

	public java.lang.String getBankName() {
		return bankName;
	}

	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}

	public java.lang.String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public java.lang.String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(java.lang.String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public java.lang.String getKaiHuHang() {
		return kaiHuHang;
	}

	public void setKaiHuHang(java.lang.String kaiHuHang) {
		this.kaiHuHang = kaiHuHang;
	}
	

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	public String getCnCreateTime(){
		return TntUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm");
	}

	public java.lang.String getBillNo() {
		return billNo;
	}

	public void setBillNo(java.lang.String billNo) {
		this.billNo = billNo;
	}

	public String getBillTime() {
		return billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	
	public TntUser getTntUser() {
		return tntUser;
	}

	public void setTntUser(TntUser tntUser) {
		this.tntUser = tntUser;
	}

	public String getAmountY() {
		if(this.getAmount()==null){
			return "";
		}
		return ""+PriceUtil.convertToYuan(this.getAmount());
	}

	public void setAmountY(String amountY) {
		this.setAmount(PriceUtil.convertToFen(amountY));
	}


	public Float getAmountToYuan() {
		return PriceUtil.convertToYuan(this.getAmount());
	}

	public String getIsWaiting() {
		return isWaiting;
	}

	public void setIsWaiting(String isWaiting) {
		this.isWaiting = isWaiting;
	}
	

}

