package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

public class ProdProductItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6412213398048858107L;

	private String metaProductName;

	private Long productItemId;

	private Long quantity;

	private Long metaProductId;

	private Long productId;

	private String productName;

	private String productType;

	private String productManageName;

	private String productState;

	public String getProductName() {
		return productName;
	}

	public String getZhProductType() {
		return Constant.PRODUCT_TYPE.getCnName(productType);
	}
	
	public String getZhProductState() {
		return "Y".equals(productState)?"可售":"不可售";
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductManageName() {
		return productManageName;
	}

	public void setProductManageName(String productManageName) {
		this.productManageName = productManageName;
	}

	public String getProductState() {
		return productState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}

	public Long getProductItemId() {
		return productItemId;
	}

	public void setProductItemId(Long productItemId) {
		this.productItemId = productItemId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getMetaProductName() {
		return metaProductName;
	}

	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}
}