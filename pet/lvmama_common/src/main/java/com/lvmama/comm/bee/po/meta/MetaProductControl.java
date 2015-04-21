package com.lvmama.comm.bee.po.meta;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author zuoxiaoshuai
 * The Class MetaProductControl.
 */
public class MetaProductControl implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5288487678032510254L;
	
	/** The meta product control id. */
	private Long metaProductControlId;
	
	/** The control type. */
	private String controlType;
	
	/** The product id. */
	private Long productId;
	
	/** The product branch id. */
	private Long productBranchId;
	
	/** The control quantity. */
	private Long controlQuantity;
	
	/** The sale quantity. */
	private Long saleQuantity;
	
	/** The delay able. */
	private String delayAble;
	
	/** The back able. */
	private String backAble;
	
	/** The start date. */
	private Date startDate;
	
	/** The end date. */
	private Date endDate;
	
	/** The first time. */
	private Date firstTime;
	
	/** The second time. */
	private Date secondTime;
	
	/** The third time. */
	private Date thirdTime;
	
	/** The first level. */
	private Long firstLevel;
	
	/** The second level. */
	private Long secondLevel;
	
	/** The third level. */
	private Long thirdLevel;
	
	/** The sale start date. */
	private Date saleStartDate;
	
	/** The sale end date. */
	private Date saleEndDate;
	
	/** The ext. */
	private String ext;
	
	/** The product name. */
	private String productName;
	
	/** The branch name. */
	private String branchName;
	
	/** The supplier name. */
	private String supplierName;
	
	/** The not got. */
	private String notGot;
	
	/** The finish sale. */
	private String finishSale;
	
	/**
	 * Gets the meta product control id.
	 *
	 * @return the meta product control id
	 */
	public Long getMetaProductControlId() {
		return metaProductControlId;
	}
	
	/**
	 * Sets the meta product control id.
	 *
	 * @param metaProductControlId the new meta product control id
	 */
	public void setMetaProductControlId(Long metaProductControlId) {
		this.metaProductControlId = metaProductControlId;
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
	 * Gets the product branch id.
	 *
	 * @return the product branch id
	 */
	public Long getProductBranchId() {
		return productBranchId;
	}
	
	/**
	 * Sets the product branch id.
	 *
	 * @param productBranchId the new product branch id
	 */
	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}
	
	/**
	 * Gets the control quantity.
	 *
	 * @return the control quantity
	 */
	public Long getControlQuantity() {
		return controlQuantity;
	}
	
	/**
	 * Sets the control quantity.
	 *
	 * @param controlQuantity the new control quantity
	 */
	public void setControlQuantity(Long controlQuantity) {
		this.controlQuantity = controlQuantity;
	}
	
	/**
	 * Gets the sale quantity.
	 *
	 * @return the sale quantity
	 */
	public Long getSaleQuantity() {
		return saleQuantity == null ? 0 : saleQuantity;
	}
	
	/**
	 * Sets the sale quantity.
	 *
	 * @param saleQuantity the new sale quantity
	 */
	public void setSaleQuantity(Long saleQuantity) {
		this.saleQuantity = saleQuantity;
	}
	
	/**
	 * Gets the delay able.
	 *
	 * @return the delay able
	 */
	public String getDelayAble() {
		return delayAble;
	}
	
	/**
	 * Sets the delay able.
	 *
	 * @param delayAble the new delay able
	 */
	public void setDelayAble(String delayAble) {
		this.delayAble = delayAble;
	}
	
	/**
	 * Gets the back able.
	 *
	 * @return the back able
	 */
	public String getBackAble() {
		return backAble;
	}
	
	/**
	 * Sets the back able.
	 *
	 * @param backAble the new back able
	 */
	public void setBackAble(String backAble) {
		this.backAble = backAble;
	}
	
	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Gets the first time.
	 *
	 * @return the first time
	 */
	public Date getFirstTime() {
		return firstTime;
	}
	
	/**
	 * Sets the first time.
	 *
	 * @param firstTime the new first time
	 */
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	
	/**
	 * Gets the second time.
	 *
	 * @return the second time
	 */
	public Date getSecondTime() {
		return secondTime;
	}
	
	/**
	 * Sets the second time.
	 *
	 * @param secondTime the new second time
	 */
	public void setSecondTime(Date secondTime) {
		this.secondTime = secondTime;
	}
	
	/**
	 * Gets the third time.
	 *
	 * @return the third time
	 */
	public Date getThirdTime() {
		return thirdTime;
	}
	
	/**
	 * Sets the third time.
	 *
	 * @param thirdTime the new third time
	 */
	public void setThirdTime(Date thirdTime) {
		this.thirdTime = thirdTime;
	}
	
	/**
	 * Gets the first level.
	 *
	 * @return the first level
	 */
	public Long getFirstLevel() {
		return firstLevel;
	}
	
	/**
	 * Sets the first level.
	 *
	 * @param firstLevel the new first level
	 */
	public void setFirstLevel(Long firstLevel) {
		this.firstLevel = firstLevel;
	}
	
	/**
	 * Gets the second level.
	 *
	 * @return the second level
	 */
	public Long getSecondLevel() {
		return secondLevel;
	}
	
	/**
	 * Sets the second level.
	 *
	 * @param secondLevel the new second level
	 */
	public void setSecondLevel(Long secondLevel) {
		this.secondLevel = secondLevel;
	}
	
	/**
	 * Gets the third level.
	 *
	 * @return the third level
	 */
	public Long getThirdLevel() {
		return thirdLevel;
	}
	
	/**
	 * Sets the third level.
	 *
	 * @param thirdLevel the new third level
	 */
	public void setThirdLevel(Long thirdLevel) {
		this.thirdLevel = thirdLevel;
	}
	
	/**
	 * Gets the sale start date.
	 *
	 * @return the sale start date
	 */
	public Date getSaleStartDate() {
		return saleStartDate;
	}
	
	/**
	 * Sets the sale start date.
	 *
	 * @param saleStartDate the new sale start date
	 */
	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}
	
	/**
	 * Gets the sale end date.
	 *
	 * @return the sale end date
	 */
	public Date getSaleEndDate() {
		return saleEndDate;
	}
	
	/**
	 * Sets the sale end date.
	 *
	 * @param saleEndDate the new sale end date
	 */
	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}
	
	/**
	 * Gets the ext.
	 *
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}
	
	/**
	 * Sets the ext.
	 *
	 * @param ext the new ext
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

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
	 * Gets the not got.
	 *
	 * @return the not got
	 */
	public String getNotGot() {
		return notGot;
	}

	/**
	 * Sets the not got.
	 *
	 * @param notGot the new not got
	 */
	public void setNotGot(String notGot) {
		this.notGot = notGot;
	}

	/**
	 * Gets the finish sale.
	 *
	 * @return the finish sale
	 */
	public String getFinishSale() {
		return finishSale;
	}

	/**
	 * Sets the finish sale.
	 *
	 * @param finishSale the new finish sale
	 */
	public void setFinishSale(String finishSale) {
		this.finishSale = finishSale;
	}
	
	private String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	
	public String getStartDateStr() {
		return formatDate(this.getStartDate());
	}
	
	public String getSaleStartDateStr() {
		return formatDate(this.getSaleStartDate());
	}
	
	public String getEndDateStr() {
		return formatDate(this.getEndDate());
	}
	
	public String getSaleEndDateStr() {
		return formatDate(this.getSaleEndDate());
	}
	
	public String getFirstTimeStr() {
		return formatDate(this.getFirstTime());
	}
	
	public String getSecondTimeStr() {
		return formatDate(this.getSecondTime());
	}
	
	public String getThirdTimeStr() {
		return formatDate(this.getThirdTime());
	}
}