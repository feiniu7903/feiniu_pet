package com.lvmama.tnt.user.po;

/**
 * 返佣规则
 * 
 * @author gaoxin
 * @version 1.0
 */
public class TntCommissionRule implements java.io.Serializable {

	private static final long serialVersionUID = 1347858813847971838L;

	private static final String MONTH_SALE = "月销售额";

	/**
	 * commissionRuleId
	 */
	private Long commissionRuleId;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 产品子类型
	 */
	private String subProductType;
	/**
	 * 在线支付
	 */
	private String payOnline;
	/**
	 * 销售额起点
	 */
	private Long minSales;
	/**
	 * 最大销售额
	 */
	private Long maxSales;
	/**
	 * 折扣率
	 */
	private Integer discountRate;

	public TntCommissionRule() {
	}

	public TntCommissionRule(Long commissionRuleId) {
		this.commissionRuleId = commissionRuleId;
	}

	public void setCommissionRuleId(Long value) {
		this.commissionRuleId = value;
	}

	public Long getCommissionRuleId() {
		return this.commissionRuleId;
	}

	public void setProductType(String value) {
		this.productType = value;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setSubProductType(String value) {
		this.subProductType = value;
	}

	public String getSubProductType() {
		return this.subProductType;
	}

	public void setPayOnline(String value) {
		this.payOnline = value;
	}

	public String getPayOnline() {
		return this.payOnline;
	}

	public void setMinSales(Long value) {
		this.minSales = value;
	}

	public Long getMinSales() {
		return this.minSales;
	}

	public void setMaxSales(Long value) {
		this.maxSales = value;
	}

	public Long getMaxSales() {
		return this.maxSales;
	}

	public void setDiscountRate(Integer value) {
		this.discountRate = value;
	}

	public Integer getDiscountRate() {
		return this.discountRate;
	}

	public String getRule() {
		String rule = "";
		boolean flag = minSales == null && this.maxSales == null;
		if (!flag) {
			if (minSales == null) {
				rule = MONTH_SALE + " <= " + maxSales;
			} else if (maxSales == null) {
				rule = MONTH_SALE + " > " + minSales;
			} else {
				rule = minSales + " < " + MONTH_SALE + " <= " + maxSales;
			}
		}
		return rule;
	}

	@Override
	public String toString() {
		return "TntCommissionRule [commissionRuleId=" + commissionRuleId
				+ ", productType=" + productType + ", subProductType="
				+ subProductType + ", payOnline=" + payOnline + ", minSales="
				+ minSales + ", maxSales=" + maxSales + ", discountRate="
				+ discountRate + "]";
	}

}
