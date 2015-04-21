package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.vo.Constant;

public class ProdProductHead implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -295548756873943603L;
	private Long productId;
	private String productName;
	private String bizType;
	private String productType;
	private String subProductType;
	private Date createTime;
	private String valid;
	private boolean bottom;
	
	/**
	 * default constructor
	 */
	public ProdProductHead() {}
	
	/**
	 * Constructor for insurance product 
	 * @param insurance insurance product
	 * <p>According the insurance product, the constructor generates a suited product head.</p>
	 */
	public ProdProductHead(ProdInsurance insurance) {
		if (null != insurance) {
			this.productId = insurance.getProductId();
			this.productName = insurance.getProductName();
			this.bizType = Constant.PRODUCT_BIZ_TYPE.PET.name();
			this.productType = Constant.PRODUCT_TYPE.OTHER.name();
			this.subProductType = Constant.SUB_PRODUCT_TYPE.INSURANCE.name();
			this.valid = insurance.getValid();
		} 
	}
	
	/**
	 * Constructor for product which in bee system
	 * @param product product which in bee system
	 * <p>According product which in bee system, the constructor generates a suited product head.</p>
	 */
	public ProdProductHead(ProdProduct product) {
		if (null != product) {
			this.productId = product.getProductId();
			this.productName = product.getProductName();
			this.bizType = Constant.PRODUCT_BIZ_TYPE.BEE.name();
			this.productType = product.getProductType();
			this.subProductType = product.getSubProductType();
			if (null != product.getValid()) {
				this.valid = product.getValid();
			} else {
				this.valid = "Y";
			}
		}
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public boolean isBottom() {
		return bottom;
	}
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}
}