package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class DistributionProductCategory implements Serializable {
	private static final long serialVersionUID = 981589302768028020L;
	private Long distributionProductCategoryId;
	/** 分销商Id */
	private Long distributorInfoId;
	/** 分销产品类型 */
	private String productType;
	/** 分销折扣率 */
	private Long discountRate;

	private String payOnline;

	/** 产品子类型 */
	private String subProductType;

	public boolean isTicket() {
		return "TICKET".equals(this.productType);
	}

	public Long getDistributionProductCategoryId() {
		return distributionProductCategoryId;
	}

	public void setDistributionProductCategoryId(
			Long distributionProductCategoryId) {
		this.distributionProductCategoryId = distributionProductCategoryId;
	}

	public Long getDistributorInfoId() {
		return distributorInfoId;
	}

	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Long discountRate) {
		this.discountRate = discountRate;
	}

	public String getPayOnline() {
		return payOnline;
	}

	public void setPayOnline(String payOnline) {
		this.payOnline = payOnline;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getZhProductType() {
		return Constant.PRODUCT_TYPE.getCnName(this.productType);
	}

	public String getZhSubProductType() {
		return Constant.SUB_PRODUCT_TYPE.getCnName(this.subProductType);
	}

	public Float getDiscountRateY() {
		return PriceUtil.convertToYuan(getDiscountRate());
	}

	public void setDiscountRateY(String discountRateY) {
		setDiscountRate(PriceUtil.convertToFen(discountRateY));
	}
}
