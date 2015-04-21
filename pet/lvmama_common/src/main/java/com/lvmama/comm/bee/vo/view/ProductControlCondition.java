package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class ProductControlCondition.
 * @author zuoxiaoshuai
 */
public class ProductControlCondition implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5017656521125982269L;

	/** The product name. */
	private String productName;
	
	/** The product id. */
	private Long productId;
	
	/** The suplier id. */
	private Long supplierId;
	
	/** The suplier name. */
	private String supplierName;
	
	/** The branch name. */
	private String branchName;
	
	/** The branch id. */
	private Long branchId;
	
	/** The control type. */
	private String controlType;
	
	private Long workGroupId;
	
	private String applierName;
	
	private Date saleStartDate;
	
	private Date saleEndDate;
	
	private Date startDate;
	
	private Date endDate;

	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the new product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * Sets the product id.
	 *
	 * @param productId the new product id
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * Gets the supplier id.
	 *
	 * @return the supplier id
	 */
	public Long getSupplierId() {
		return supplierId;
	}

	/**
	 * Sets the supplier id.
	 *
	 * @param supplierId the new supplier id
	 */
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * Gets the supplier name.
	 *
	 * @return the supplier name
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * Sets the supplier name.
	 *
	 * @param supplierName the new supplier name
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * Gets the branch name.
	 *
	 * @return the branch name
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * Sets the branch name.
	 *
	 * @param branchName the new branch name
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * Gets the branch id.
	 *
	 * @return the branch id
	 */
	public Long getBranchId() {
		return branchId;
	}

	/**
	 * Sets the branch id.
	 *
	 * @param branchId the new branch id
	 */
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	/**
	 * Gets the control type.
	 *
	 * @return the control type
	 */
	public String getControlType() {
		return controlType;
	}

	/**
	 * Sets the control type.
	 *
	 * @param controlType the new control type
	 */
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public Long getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getApplierName() {
		return applierName;
	}

	public void setApplierName(String applierName) {
		this.applierName = applierName;
	}

	public Date getSaleStartDate() {
		return saleStartDate;
	}

	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
