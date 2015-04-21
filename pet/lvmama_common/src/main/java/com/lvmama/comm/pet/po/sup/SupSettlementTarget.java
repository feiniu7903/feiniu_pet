package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class SupSettlementTarget implements Comparable, Serializable {
	
	private static final long serialVersionUID = -6270963947197556857L;

	private Long targetId;
	private String name;
	private String bankName;
	private String bankAccountName;
	private String bankAccount;
	private String alipayName;
	private String alipayAccount;
	private Long deposit;
	private Date createTime;
	private String memo;
	private Long orgId;
	private String paymentType;
	/**
	 * 结算周期
	 */
	private String settlementPeriod = Constant.SETTLEMENT_PERIOD.PERMONTH.name();
	/**
	 * 供应商ID
	 */
	private Long supplierId;

	/**
	 * 类别：PERSON/COMPANY
	 */
	private String type;
	/**
	 * 联行号
	 */
	private String bankLines;
	/**
	 * 提前结算天数
	 */
	private Long advancedDays;
	
	private List<ComContact> contactList=Collections.emptyList();
	/**
	 * 供应商
	 */
	private SupSupplier supplier;
	
	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public Long getDeposit() {
		return deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateTimeStr() {
		return DateUtil.formatDate(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getZhSettlementPeriod() {
		return Constant.SETTLEMENT_PERIOD.getCnName(settlementPeriod);
	}

	public String getSettlementPeriod() {
		return settlementPeriod;
	}

	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public int compareTo(Object arg0) {
		if (arg0 instanceof SupSettlementTarget) {
			SupSettlementTarget sup = (SupSettlementTarget)arg0;
			if (targetId<sup.getTargetId()) {
				return -1;
			}else if(targetId==sup.getTargetId()) {
				return 0;
			}else {
				return 1;
			}
		}
		return -1;
	}
	public boolean equals(Object obj) {
		if (obj instanceof SupSettlementTarget) {
			SupSettlementTarget target = (SupSettlementTarget) obj;
			if (this.targetId == null) {
				return target.getTargetId() == null;
			} else {
				return targetId.longValue() == target.getTargetId();
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (targetId != null)
			return targetId.hashCode();
		else
			return 0;
	}

	@Override
	public String toString() {
		if (targetId != null)
			return "SupSettlementTarget_" + targetId.toString();
		else
			return "SupSettlementTarget_null";
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBankLines() {
		return bankLines;
	}

	public void setBankLines(String bankLines) {
		this.bankLines = bankLines;
	}

	public Long getAdvancedDays() {
		return advancedDays;
	}

	public void setAdvancedDays(Long advancedDays) {
		this.advancedDays = advancedDays;
	}

	public SupSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(SupSupplier supplier) {
		this.supplier = supplier;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getPaymentTypeName(){
		if("CASH".equals(this.paymentType)){
			return "现金";
		}else if("TRANSFER".equals(this.paymentType)){
			return "银行转账";
		}
		return null;
	}
	
	public List<ComContact> getContactList() {
		return contactList;
	}

	public void setContactList(List<ComContact> contactList) {
		this.contactList = contactList;
	}
	
	public String getZhType() {
		return Constant.SETTLEMENT_TARGET_TYPE.getCnName(type);
	}
	
	public String getZhPaymentType() {
		return Constant.SETTLEMENT_PAYMENT_TYPE.getCnName(paymentType);
	}
	
}