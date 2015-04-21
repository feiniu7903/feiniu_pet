package com.lvmama.finance.group.ibatis.po;

import java.sql.Timestamp;
import java.util.Date;

public class FinInvoice implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long invoiceId;
	private String invoiceCode;
	private Long supplierId;
	private String remark;
	private Long creator;
	private Date createTime;
	private Double invoiceAmount;
	private String invoiceTitle;

	public FinInvoice() {
	}

	public FinInvoice(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public FinInvoice(Long invoiceId, String invoiceCode, Long supplierId,
			 String remark, Long creator, Timestamp createTime, Double invoiceAmount) {
		this.invoiceId = invoiceId;
		this.invoiceCode = invoiceCode;
		this.supplierId = supplierId;
		this.remark = remark;
		this.creator = creator;
		this.createTime = createTime;
		this.invoiceAmount  = invoiceAmount;
	}

	// Property accessors

	public Long getInvoiceId() {
		return this.invoiceId;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceCode() {
		return this.invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Long getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreator() {
		return this.creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FinInvoice))
			return false;
		FinInvoice castOther = (FinInvoice) other;

		return ((this.getInvoiceId() == castOther.getInvoiceId()) || (this
				.getInvoiceId() != null && castOther.getInvoiceId() != null && this
				.getInvoiceId().equals(castOther.getInvoiceId())))
				&& ((this.getInvoiceCode() == castOther.getInvoiceCode()) || (this
						.getInvoiceCode() != null
						&& castOther.getInvoiceCode() != null && this
						.getInvoiceCode().equals(castOther.getInvoiceCode())))
				&& ((this.getSupplierId() == castOther.getSupplierId()) || (this
						.getSupplierId() != null
						&& castOther.getSupplierId() != null && this
						.getSupplierId().equals(castOther.getSupplierId())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null && castOther.getRemark() != null && this
						.getRemark().equals(castOther.getRemark())))
				&& ((this.getCreator() == castOther.getCreator()) || (this
						.getCreator() != null && castOther.getCreator() != null && this
						.getCreator().equals(castOther.getCreator())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getInvoiceId() == null ? 0 : this.getInvoiceId().hashCode());
		result = 37
				* result
				+ (getInvoiceCode() == null ? 0 : this.getInvoiceCode()
						.hashCode());
		result = 37
				* result
				+ (getSupplierId() == null ? 0 : this.getSupplierId()
						.hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		result = 37 * result
				+ (getCreator() == null ? 0 : this.getCreator().hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		return result;
	}

}