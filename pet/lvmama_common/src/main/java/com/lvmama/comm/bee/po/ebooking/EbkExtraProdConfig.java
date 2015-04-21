package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

/**
 * EBK供应商附加产品配置
 * 
 * @since 2013-10-21
 */
public class EbkExtraProdConfig implements Serializable {

	private static final long serialVersionUID = 138232633866202767L;

	/**
	 * column EBK_EXTRA_PROD_CONFIG.ID
	 */
	private Long id;
	
	/**
	 * ebk产品大类 
	 */
	private String ebkProductType;

	/**
	 * column EBK_EXTRA_PROD_CONFIG.PRODUCT_TYPE
	 */
	private String productType;

	/**
	 * column EBK_EXTRA_PROD_CONFIG.DAYS_TRIP
	 */
	private Integer daysTrip;

	/**
	 * column EBK_EXTRA_PROD_CONFIG.PRODUCT_ID
	 */
	private Long productId;

	/**
	 * column EBK_EXTRA_PROD_CONFIG.PROD_BRANCH_ID
	 */
	private Long prodBranchId;

	/**
	 * column EBK_EXTRA_PROD_CONFIG.SALE_NUM_TYPE
	 */
	private String saleNumType;

	public EbkExtraProdConfig() {
		super();
	}

	public EbkExtraProdConfig(Long id, String productType, Integer daysTrip,
			Long productId, Long prodBranchId, String saleNumType) {
		this.id = id;
		this.productType = productType;
		this.daysTrip = daysTrip;
		this.productId = productId;
		this.prodBranchId = prodBranchId;
		this.saleNumType = saleNumType;
	}

	/**
	 * getter for Column EBK_EXTRA_PROD_CONFIG.ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setter for Column EBK_EXTRA_PROD_CONFIG.ID
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getter for Column EBK_EXTRA_PROD_CONFIG.PRODUCT_TYPE
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * setter for Column EBK_EXTRA_PROD_CONFIG.PRODUCT_TYPE
	 * 
	 * @param productType
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * getter for Column EBK_EXTRA_PROD_CONFIG.DAYS_TRIP
	 */
	public Integer getDaysTrip() {
		return daysTrip;
	}

	/**
	 * setter for Column EBK_EXTRA_PROD_CONFIG.DAYS_TRIP
	 * 
	 * @param daysTrip
	 */
	public void setDaysTrip(Integer daysTrip) {
		this.daysTrip = daysTrip;
	}

	/**
	 * getter for Column EBK_EXTRA_PROD_CONFIG.PRODUCT_ID
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * setter for Column EBK_EXTRA_PROD_CONFIG.PRODUCT_ID
	 * 
	 * @param productId
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * getter for Column EBK_EXTRA_PROD_CONFIG.PROD_BRANCH_ID
	 */
	public Long getProdBranchId() {
		return prodBranchId;
	}

	/**
	 * setter for Column EBK_EXTRA_PROD_CONFIG.PROD_BRANCH_ID
	 * 
	 * @param prodBranchId
	 */
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	/**
	 * getter for Column EBK_EXTRA_PROD_CONFIG.SALE_NUM_TYPE
	 */
	public String getSaleNumType() {
		return saleNumType;
	}

	/**
	 * setter for Column EBK_EXTRA_PROD_CONFIG.SALE_NUM_TYPE
	 * 
	 * @param saleNumType
	 */
	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}

	public String getEbkProductType() {
		return ebkProductType;
	}

	public void setEbkProductType(String ebkProductType) {
		this.ebkProductType = ebkProductType;
	}
	
	
	
	

}