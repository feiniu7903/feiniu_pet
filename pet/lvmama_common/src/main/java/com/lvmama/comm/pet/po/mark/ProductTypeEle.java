package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.PriceUtil;

 
/**
 * 
 * 产品子类型的值类型表示.
 *
 */
public class ProductTypeEle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5846643658922070498L;
	//此产品子类型所属的产品类型.
	private String productType;
	//产品子类型.
	private String subProductType;
	//优惠金额.
	private long amount;
	//是否被选中.
	private boolean checked;
	//页面中所显示的产品子类型中文名称.
	private String subProductTypeNames;
	//表MARK_COUPON_PRODUCT的主键COUPON_PRODUCT_ID列表.
	private String couponProductIds;
	//对应的产品子类型的CodeItem表示.
	private CodeItem codeItem;
	
	public String getCouponProductIds() {
		return couponProductIds;
	}
	public void setCouponProductIds(String couponProductIds) {
		this.couponProductIds = couponProductIds;
	}
	public String getSubProductTypeNames() {
		return subProductTypeNames;
	}
	public void setSubProductTypeNames(String subProductTypeNames) {
		this.subProductTypeNames = subProductTypeNames;
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
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getSubProductTypeName() {
		return this.codeItem.getName();
	}
	public float getAmountYuan() {
		return PriceUtil.convertToYuan(this.amount);
	}
	public void setAmountYuan(float amountYuan) {
		this.amount = PriceUtil.convertToFen(amountYuan);
	}
	public void setCodeItem(CodeItem codeItem) {
		this.codeItem = codeItem;
	}
	
	@Override
	public String toString() {
		return "SubProductTypeEle [subProductType=" + subProductType
				+ ", amount=" + amount + ", checked=" + checked + "]";
	}
	
	
	
}
