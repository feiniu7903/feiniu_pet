/**
 * 
 */
package com.lvmama.comm.pet.po.businessCoupon;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 产品优惠验证对象
 * @author liuyi
 *
 */
public class ValidateBusinessCouponInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7020367536136531262L;
	
	/**
	 * 展示文字
	 */
	private String displayInfo;
	/**
	 * 产品ID
	 */
	private Long productId;
	/**
	 * 产品类别ID
	 */
	private Long productBranchId;
	/**
	 * 优惠金额
	 */
	private Long amount;
	/**
	 * 优惠金额元
	 */
	private Float amountYuan;
	
	private String couponType;

	public String getDisplayInfo() {
		return displayInfo;
	}

	public void setDisplayInfo(String displayInfo) {
		this.displayInfo = displayInfo;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductBranchId() {
		return productBranchId;
	}

	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
		this.amountYuan = PriceUtil.convertToYuan(amount);
	}

	public Float getAmountYuan() {
		return amountYuan;
	}

	public void setAmountYuan(Float amountYuan) {
		this.amountYuan = amountYuan;
	}
	
	@Override
	public String toString() {
		return "ValidateBusinessCouponInfo [displayInfo=" + displayInfo + ", productId="
				+ productId + ", productBranchId=" + productBranchId + ", amount=" + amount+"]";
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

}
