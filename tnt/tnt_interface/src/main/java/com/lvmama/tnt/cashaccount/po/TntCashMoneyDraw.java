package com.lvmama.tnt.cashaccount.po;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant;


/**
 * 现金帐户提现记录
 * @author gaoxin
 * @version 1.0
 */
public class TntCashMoneyDraw implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 *  主键
	 */
	private java.lang.Long moneyDrawId;
	/**
	 *  主键，与用户表USER_ID相同
	 */
	private java.lang.Long cashAccountId;
	
	/**
	 *  对应转账单编号
	 */
	private java.lang.String billNo;
	
	/**
	 * 银行转账日期
	 */
	private String billTime;
	/**
	 *  申请金额
	 */
	private Long drawAmount;
	
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
	 *  审核状态
	 */
	private java.lang.String auditStatus;
	/**
	 *  打款状态
	 */
	private java.lang.String payStatus;
	/**
	 *  备注
	 */
	private java.lang.String memo;
	/**
	 *  createTime
	 */
	private java.util.Date createTime;
	
	private TntCashAccount cashAccount = new TntCashAccount();
	//columns END

	public TntCashMoneyDraw(){
	}

	public TntCashMoneyDraw(
		java.lang.Long moneyDrawId
	){
		this.moneyDrawId = moneyDrawId;
	}

	public void setMoneyDrawId(java.lang.Long value) {
		this.moneyDrawId = value;
	}
	
	public java.lang.Long getMoneyDrawId() {
		return this.moneyDrawId;
	}
	public void setCashAccountId(java.lang.Long value) {
		this.cashAccountId = value;
	}
	
	public java.lang.Long getCashAccountId() {
		return this.cashAccountId;
	}
	public void setDrawAmount(Long value) {
		this.drawAmount = value;
	}
	
	public Long getDrawAmount() {
		return this.drawAmount;
	}
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setBankAccount(java.lang.String value) {
		this.bankAccount = value;
	}
	
	public java.lang.String getBankAccount() {
		return this.bankAccount;
	}
	public void setBankAccountName(java.lang.String value) {
		this.bankAccountName = value;
	}
	
	public java.lang.String getBankAccountName() {
		return this.bankAccountName;
	}
	public void setKaiHuHang(java.lang.String value) {
		this.kaiHuHang = value;
	}
	
	public java.lang.String getKaiHuHang() {
		return this.kaiHuHang;
	}
	public void setAuditStatus(java.lang.String value) {
		this.auditStatus = value;
	}
	
	public java.lang.String getAuditStatus() {
		return this.auditStatus;
	}
	public void setPayStatus(java.lang.String value) {
		this.payStatus = value;
	}
	
	public java.lang.String getPayStatus() {
		return this.payStatus;
	}
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setDrawAmountY(String drawAmountY) {
		this.setDrawAmount(PriceUtil.convertToFen(drawAmountY));
	}
	public String getDrawAmountY() {
		if(this.getDrawAmount()==null){
			return "";
		}
		return ""+PriceUtil.convertToYuan(this.getDrawAmount());
	}
	
	public Float getDrawAmountToYuan() {
		return PriceUtil.convertToYuan(this.getDrawAmount());
	}
	public String getCnCreateTime(){
		return TntUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm");
	}
	
	public java.lang.String getCnAuditStatus() {
		if(this.auditStatus!=null){
			return TntConstant.AUDIT_STATUS.getCnName(this.auditStatus);
		}
		return this.auditStatus;
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

	public TntCashAccount getCashAccount() {
		return cashAccount;
	}

	public void setCashAccount(TntCashAccount cashAccount) {
		this.cashAccount = cashAccount;
	}

	@Override
	public String toString() {
		return "TntCashMoneyDraw [moneyDrawId=" + moneyDrawId + ", cashAccountId=" + cashAccountId + ", drawAmount=" + drawAmount + ", bankName=" + bankName + ", bankAccount=" + bankAccount + ", bankAccountName=" + bankAccountName + ", kaiHuHang=" + kaiHuHang + ", auditStatus=" + auditStatus + ", payStatus=" + payStatus + ", memo=" + memo + ", createTime=" + createTime + "]";
	}


}

