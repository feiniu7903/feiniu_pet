package com.lvmama.clutter.xml.lv.po;

public class RequestOrderItem {
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 购买数量
	 */
	private String quantity;
	/**
	 * 使用的优惠券代码
	 */
	private String couponCode;
	/**
	 * 类别id
	 */
	private String branchId;
	
	/**
	 * 是否是主产品
	 */
	private boolean isMainProduct;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}


	public boolean isMainProduct() {
		return isMainProduct;
	}
	public void setMainProduct(boolean isMainProduct) {
		this.isMainProduct = isMainProduct;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
}
