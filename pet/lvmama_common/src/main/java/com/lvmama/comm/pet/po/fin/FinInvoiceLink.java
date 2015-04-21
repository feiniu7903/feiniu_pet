package com.lvmama.comm.pet.po.fin;

public class FinInvoiceLink implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String code;
	private String type;
	private Long invoiceId;

	public FinInvoiceLink() {
	}

	public FinInvoiceLink(Long id) {
		this.id = id;
	}

	public FinInvoiceLink(Long id, String code, String type, Long invoiceId) {
		this.id = id;
		this.code = code;
		this.type = type;
		this.invoiceId = invoiceId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FinInvoiceLink))
			return false;
		FinInvoiceLink castOther = (FinInvoiceLink) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getCode() == castOther.getCode()) || (this.getCode() != null
						&& castOther.getCode() != null && this.getCode()
						.equals(castOther.getCode())))
				&& ((this.getType() == castOther.getType()) || (this.getType() != null
						&& castOther.getType() != null && this.getType()
						.equals(castOther.getType())))
				&& ((this.getInvoiceId() == castOther.getInvoiceId()) || (this
						.getInvoiceId() != null
						&& castOther.getInvoiceId() != null && this
						.getInvoiceId().equals(castOther.getInvoiceId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getCode() == null ? 0 : this.getCode().hashCode());
		result = 37 * result
				+ (getType() == null ? 0 : this.getType().hashCode());
		result = 37 * result
				+ (getInvoiceId() == null ? 0 : this.getInvoiceId().hashCode());
		return result;
	}

}