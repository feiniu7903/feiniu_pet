package com.lvmama.comm.pet.po.fin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class SetSettlementPayment extends FinanceBusiness{

	private static final long serialVersionUID = 1L;
	private Long settlementPaymentId;
	private Long targetId;
	private Long settlementId;
	private String paytype;
	private Long amount;
	private String bank;
	private String serial;
	private Date operatetime;
	private String remark;
	private String creator;
	private Date createtime;
	private String currency;
	private Date invoiceRetdate;
	private Long invoiceWriteoffMonth;
	private String recoveryStatus;
	private Double rate;
	private Long groupSettlementId;
	private String travelGroupCode;
	private String supplierName;
	private String targetName;
	
	// 合并打款时选中的团号
	private String groupSettlementIds;
	// 打款时使用的抵扣款
	private Long deductionPayAmount;
	// 打款时使用的预存款
	private Long advanceDepositsPayAmount;
	private Float deductionPayAmountYuan;
	private String invoiceRetdateStr;
	private Float advanceDepositsPayAmountYuan;
	private Float amountYuan;
	
	private String supplierId;
	//查询使用
	private List<Long> targetIds = new ArrayList<Long>();
	
	public String getGroupSettlementIds() {
		return groupSettlementIds;
	}

	public void setGroupSettlementIds(String groupSettlementIds) {
		this.groupSettlementIds = groupSettlementIds;
	}

	public Long getDeductionPayAmount() {
		return deductionPayAmount;
	}

	public void setDeductionPayAmount(Long deductionPayAmount) {
		this.deductionPayAmount = deductionPayAmount;
	}
	@JSON(serialize=false)
	public void setDeductionPayAmountYuan(Float deductionPayAmountYuan) {
		this.deductionPayAmountYuan = deductionPayAmountYuan;
		if(deductionPayAmountYuan != null){
			this.deductionPayAmount = PriceUtil.convertToFen(deductionPayAmountYuan);
		}
	}

	public Long getAdvanceDepositsPayAmount() {
		return advanceDepositsPayAmount;
	}

	public void setAdvanceDepositsPayAmount(Long advanceDepositsPayAmount) {
		this.advanceDepositsPayAmount = advanceDepositsPayAmount;
	}
	@JSON(serialize=false)
	public void setAdvanceDepositsPayAmountYuan(Float advanceDepositsPayAmountYuan) {
		this.advanceDepositsPayAmountYuan = advanceDepositsPayAmountYuan;
		if(advanceDepositsPayAmountYuan != null){
			this.advanceDepositsPayAmount = PriceUtil.convertToFen(advanceDepositsPayAmountYuan);
		}
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getSettlementPaymentId() {
		return this.settlementPaymentId;
	}

	public void setSettlementPaymentId(Long settlementPaymentId) {
		this.settlementPaymentId = settlementPaymentId;
	}

	public Long getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getSettlementId() {
		return this.settlementId;
	}

	public String getSettlementIdStr() {
		if(null == settlementId){
			return "";
		}
		return this.settlementId+"";
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public String getPaytype() {
		return this.paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public Long getAmount() {
		return this.amount;
	}

	public float getAmountYuan() {
		this.amountYuan= PriceUtil.convertToYuan(this.amount);
		return amountYuan;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setAmountYuan(Float amountYuan) {
		this.amountYuan = amountYuan;
		if(null != amountYuan){
			this.amount = PriceUtil.convertToFen(amountYuan);
		}
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Date getOperatetime() {
		return this.operatetime;
	}

	public String getOperatetimeStr() {
		return DateUtil.formatDate(operatetime, "yyyy-MM-dd HH:mm:ss");
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	public void setOperatetimeStr(String operatetimes) {
		if(null != operatetimes){
			this.operatetime = DateUtil.stringToDate(operatetimes, "yyyy-MM-dd HH:mm");
		}
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public String getCreatetimeStr() {
		return DateUtil.formatDate(createtime, "yyyy-MM-dd HH:mm:ss");
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCurrency() {
		return this.currency;
	}

	public String getZhCurrency() {
		return Constant.FIN_CURRENCY.getCnName(currency);
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getInvoiceRetdate() {
		return this.invoiceRetdate;
	}

	public void setInvoiceRetdate(Date invoiceRetdate) {
		this.invoiceRetdate = invoiceRetdate;
	}
	@JSON(serialize=false)
	public void setInvoiceRetdateStr(String invoiceRetdateStr) {
		this.invoiceRetdateStr = invoiceRetdateStr;
		if(null != invoiceRetdateStr){
			this.invoiceRetdate = DateUtil.stringToDate(invoiceRetdateStr, "yyyy-MM-dd");
		}
	}

	public Long getInvoiceWriteoffMonth() {
		return this.invoiceWriteoffMonth;
	}

	public void setInvoiceWriteoffMonth(Long invoiceWriteoffMonth) {
		this.invoiceWriteoffMonth = invoiceWriteoffMonth;
	}

	public String getRecoveryStatus() {
		return this.recoveryStatus;
	}

	public void setRecoveryStatus(String recoveryStatus) {
		this.recoveryStatus = recoveryStatus;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getGroupSettlementId() {
		return this.groupSettlementId;
	}

	public void setGroupSettlementId(Long groupSettlementId) {
		this.groupSettlementId = groupSettlementId;
	}

	public String getTravelGroupCode() {
		return this.travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Float getDeductionPayAmountYuan() {
		return deductionPayAmountYuan;
	}

	public String getInvoiceRetdateStr() {
		return invoiceRetdateStr;
	}

	public Float getAdvanceDepositsPayAmountYuan() {
		return advanceDepositsPayAmountYuan;
	}

	public List<Long> getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(List<Long> targetIds) {
		this.targetIds = targetIds;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
}