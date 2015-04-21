package com.lvmama.comm.pet.po.fin;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 结算单
 * 
 * @author yanggan
 *
 */
public class SetSettlement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -235755552639088683L;
	private Long settlementId;
	private Long targetId;
	private Long settlementAmount;
	private String status;
	private String operatorName;
	private String memo;
	private Date createTime;
	private Date settlementTime;
	private Long payedAmount;
	private Long deductionAmount;
	private String targetName;
	private String settlementPeriod;
	private Long advancedDays;
	private String bankAccountName;
	private String bankName;
	private String bankAccount;
	private String alipayAccount;
	private String alipayName;
	private String targetType;
	private String bankLines;
	private String companyId;
	private String settlementType;
	private Long supplierId;
	private String supplierName;
	private String filialeName;
	private String businessName;//所属业务系统
	/**
	 * 联系人信息
	 */
	private ComContact contact;
	
	public Long getSettlementId() {
		return this.settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Long getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getSettlementAmount() {
		return this.settlementAmount;
	}

	public float getSettlementAmountYuan(){
		return PriceUtil.convertToYuan(settlementAmount);
	}
	public void setSettlementAmount(Long settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getStatus() {
		return this.status;
	}
	public String getStatusName(){
		return Constant.SET_SETTLEMENT_STATUS.getCnName(this.status);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeStr(){
		if(createTime == null){
			return "";
		}
		return DateUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm:ss");
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getPayedAmount() {
		return this.payedAmount;
	}
	public float getPayedAmountYuan() {
		return PriceUtil.convertToYuan(this.payedAmount);
	}

	public void setPayedAmount(Long payedAmount) {
		this.payedAmount = payedAmount;
	}

	public String getTargetName() {
		return this.targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getSettlementPeriod() {
		return this.settlementPeriod;
	}

	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}

	public Long getAdvancedDays() {
		return this.advancedDays;
	}

	public void setAdvancedDays(Long advancedDays) {
		this.advancedDays = advancedDays;
	}

	public String getBankAccountName() {
		return this.bankAccountName;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAlipayAccount() {
		return this.alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getAlipayName() {
		return this.alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public String getTargetType() {
		return this.targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getBankLines() {
		return this.bankLines;
	}

	public void setBankLines(String bankLines) {
		this.bankLines = bankLines;
	}

	public String getCompanyId() {
		return this.companyId;
	}
	public String getCompanyIdStr() {
		if(StringUtil.isEmptyString(this.companyId)){
			return "";
		}
		return Constant.SETTLEMENT_COMPANY.getCnName(this.companyId);
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSettlementType() {
		return this.settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSettlementPeriodStr() {
		if(StringUtil.isEmptyString(this.settlementPeriod)){
			return "";
		}
		return Constant.SETTLEMENT_PERIOD.getCnName(this.settlementPeriod);
	}

	public ComContact getContact() {
		return contact;
	}

	public void setContact(ComContact contact) {
		this.contact = contact;
	}

	public Long getDeductionAmount() {
		return deductionAmount;
	}

	public float getDeductionAmountYuan() {
		return PriceUtil.convertToYuan(this.deductionAmount);
	}
	public void setDeductionAmount(Long deductionAmount) {
		this.deductionAmount = deductionAmount;
	}
	
	/**
	 * 是否是全部打款
	 * @return
	 */
	public boolean isFullPayed(){
		Long da = this.getDeductionAmount() == null ? 0 : this.getDeductionAmount();
		Long pa = this.getPayedAmount() == null ? 0 : this.getPayedAmount();
		Long sa = this.getSettlementAmount() == null ? 0 : this.getSettlementAmount();
		if(da +  pa < sa){
			return false;
		}else{
			return true;
		}
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


}