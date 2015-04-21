package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;

public class Invoice implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1930981603385702377L;
	
	private Long invoiceId;

    private String title;

    private String detail;

    private String memo;
    
    private Long amount;
    private String company;
    private String deliveryType;

	/**
	 * @return the invoiceId
	 */
	public Long getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @return 发票抬头
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title 发票抬头
	 */
	public void setTitle(final String title) {
		this.title = title;
	}
	/**
	 * @return 发票明细
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * @param detail 发票明细
	 */
	public void setDetail(final String detail) {
		this.detail = detail;
	}
	/**
	 * @return 发票备注
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo 发票备注
	 */
	public void setMemo(final String memo) {
		this.memo = memo;
	}

	/**
	 * @return 发票金额
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount 发票金额
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	public float getAmountYuan() {
		return PriceUtil.convertToYuan(this.amount);
	}
	
	
}
