package com.lvmama.comm.pet.po.fin;

public class FinInvoiceAmount  extends FinanceBusiness{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long supplierId;
	private Double payAmount;
	private Double invoiceAmount;

	public FinInvoiceAmount() {
	}

	public FinInvoiceAmount(Long id) {
		this.id = id;
	}

	public FinInvoiceAmount(Long id, Long supplierId, Double payAmount,
			Double invoiceAmount) {
		this.id = id;
		this.supplierId = supplierId;
		this.payAmount = payAmount;
		this.invoiceAmount = invoiceAmount;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FinInvoiceAmount))
			return false;
		FinInvoiceAmount castOther = (FinInvoiceAmount) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getSupplierId() == castOther.getSupplierId()) || (this
						.getSupplierId() != null
						&& castOther.getSupplierId() != null && this
						.getSupplierId().equals(castOther.getSupplierId())))
				&& ((this.getPayAmount() == castOther.getPayAmount()) || (this
						.getPayAmount() != null
						&& castOther.getPayAmount() != null && this
						.getPayAmount().equals(castOther.getPayAmount())))
				&& ((this.getInvoiceAmount() == castOther.getInvoiceAmount()) || (this
						.getInvoiceAmount() != null
						&& castOther.getInvoiceAmount() != null && this
						.getInvoiceAmount()
						.equals(castOther.getInvoiceAmount())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37
				* result
				+ (getSupplierId() == null ? 0 : this.getSupplierId()
						.hashCode());
		result = 37 * result
				+ (getPayAmount() == null ? 0 : this.getPayAmount().hashCode());
		result = 37
				* result
				+ (getInvoiceAmount() == null ? 0 : this.getInvoiceAmount()
						.hashCode());
		return result;
	}

}