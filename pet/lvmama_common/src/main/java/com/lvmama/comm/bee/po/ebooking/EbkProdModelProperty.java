package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

/**
 * @since 2013-09-24
 */
public class EbkProdModelProperty implements Serializable {

	private static final long serialVersionUID = 138001285068518660L;

	/**
	 * column EBK_PROD_MODEL_PROPERTY.PRODUCT_ID
	 */
	private Long productId;

	/**
	 * column EBK_PROD_MODEL_PROPERTY.MODEL_PROPERTY_ID
	 */
	private Integer modelPropertyId;

	/**
	 * column EBK_PROD_MODEL_PROPERTY.EBK_PROPERTY_TYPE
	 */
	private String ebkPropertyType;


	/**
	 * 属性名称
	 */
	private String modelPropertyName;
	
	public EbkProdModelProperty() {
		super();
	}

	public EbkProdModelProperty(Long productId, Integer modelPropertyId,
			String ebkPropertyType) {
		this.productId = productId;
		this.modelPropertyId = modelPropertyId;
		this.ebkPropertyType = ebkPropertyType;
	}

	/**
	 * getter for Column EBK_PROD_MODEL_PROPERTY.PRODUCT_ID
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * setter for Column EBK_PROD_MODEL_PROPERTY.PRODUCT_ID
	 * 
	 * @param productId
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * getter for Column EBK_PROD_MODEL_PROPERTY.MODEL_PROPERTY_ID
	 */
	public Integer getModelPropertyId() {
		return modelPropertyId;
	}

	/**
	 * setter for Column EBK_PROD_MODEL_PROPERTY.MODEL_PROPERTY_ID
	 * 
	 * @param modelPropertyId
	 */
	public void setModelPropertyId(Integer modelPropertyId) {
		this.modelPropertyId = modelPropertyId;
	}

	public String getEbkPropertyType() {
		return ebkPropertyType;
	}

	public void setEbkPropertyType(String ebkPropertyType) {
		this.ebkPropertyType = ebkPropertyType;
	}

	public String getModelPropertyName() {
		return modelPropertyName;
	}

	public void setModelPropertyName(String modelPropertyName) {
		this.modelPropertyName = modelPropertyName;
	}

}