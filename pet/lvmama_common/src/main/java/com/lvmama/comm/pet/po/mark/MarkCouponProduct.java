package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;

public class MarkCouponProduct implements Serializable {
	 
	private static final long serialVersionUID = -1181301152860383577L;
	/**
	 * 优惠券绑定类型:按产品ID绑定.
	 */
	public static final int PRODUCT_ID = 1;
	/**
	 * 优惠券绑定类型:按产品子类型绑定.
	 */
	public static final int SUB_PRODUCT_TYPE = 2;
    private Long couponProductId;

    private Long productId;
    
    private String productName = "";

    private Long couponId;
    
    private Long amount=null;
    
    private Long oldamount=null;
    
    //优惠券绑定类型,取值为PRODUCT_ID,SUB_PRODUCT_TYPE其中之一.
    private int couponProductType = SUB_PRODUCT_TYPE;
    
    private String markCouponProduct;
    //绑定的产品子类型值.
    private String subProductType;
    
    private MarkCoupon markCoupon;
    
    
    public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.setCouponProductType(MarkCouponProduct.SUB_PRODUCT_TYPE);
		this.subProductType = subProductType;
	}

	public Long getCouponProductId() {
        return couponProductId;
    }

    public void setCouponProductId(Long couponProductId) {
    	
        this.couponProductId = couponProductId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
    	this.setCouponProductType(MarkCouponProduct.PRODUCT_ID);
        this.productId = productId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public Float getAmountYuan(){
		if (amount==null){
			return null;
		}
		return PriceUtil.convertToYuan(this.amount);
	}

	public int getCouponProductType() {
		return couponProductType;
	}

	public void setCouponProductType(int couponProductType) {
		this.couponProductType = couponProductType;
	}
	public String getMarkCouponProduct() {
		return markCouponProduct;
	}

	public void setMarkCouponProduct(String markCouponProduct) {
		this.markCouponProduct = markCouponProduct;
	}

	public void setOldamount(Long oldamount) {
		this.oldamount = oldamount;
	}

	public Long getOldamount() {
		return oldamount;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

}