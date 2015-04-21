package com.lvmama.comm.pet.po.shop;

import java.io.Serializable;

public class ShopProductCondition implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long shopProductConditionId;
	private Long productId;
	/**
	 * 限制条件CONSTANT.SHOP_PRODUCT_CONDITION
	 */
	private String conditionX;
	private String conditionY;
	/**
	 * 预留字段
	 */
	private String conditionZ;
	
	
	public Long getShopProductConditionId() {
		return shopProductConditionId;
	}
	public void setShopProductConditionId(Long shopProductConditionId) {
		this.shopProductConditionId = shopProductConditionId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getConditionX() {
		return conditionX;
	}
	public void setConditionX(String conditionX) {
		this.conditionX = conditionX;
	}
	public String getConditionY() {
		return conditionY;
	}
	public void setConditionY(String conditionY) {
		this.conditionY = conditionY;
	}
	public String getConditionZ() {
		return conditionZ;
	}
	public void setConditionZ(String conditionZ) {
		this.conditionZ = conditionZ;
	}

}
