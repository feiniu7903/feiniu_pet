package com.lvmama.tnt.prod.po;

import com.lvmama.tnt.prod.vo.TntProdProduct;

/**
 * 分销产品黑名单
 * 
 * @author gaoxin
 * @version 1.0
 */
public class TntProduct implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// date formats

	/**
	 * tntBlackId
	 */
	private java.lang.Long tntProductId;
	/**
	 * productId
	 */
	private java.lang.Long productId;
	/**
	 * branchId
	 */
	private java.lang.Long branchId;
	/**
	 * 渠道Id
	 */
	private java.lang.Long channelId;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 期票，非期票
	 */
	private String isAperiodic;

	/**
	 * 是否在线支付
	 */
	private String payToLvmama;

	private String valid;
	// columns END

	private TntProdProduct prod;

	public TntProduct() {
	}

	public TntProduct(java.lang.Long tntProductId) {
		this.tntProductId = tntProductId;
	}

	public TntProduct(Long productId, Long branchId, Long channelId) {
		super();
		this.productId = productId;
		this.branchId = branchId;
		this.channelId = channelId;
	}

	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}

	public java.lang.Long getProductId() {
		return this.productId;
	}

	public void setBranchId(java.lang.Long value) {
		this.branchId = value;
	}

	public java.lang.Long getBranchId() {
		return this.branchId;
	}

	public void setChannelId(java.lang.Long value) {
		this.channelId = value;
	}

	public java.lang.Long getChannelId() {
		return this.channelId;
	}

	public java.lang.Long getTntProductId() {
		return tntProductId;
	}

	public void setTntProductId(java.lang.Long tntProductId) {
		this.tntProductId = tntProductId;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public TntProdProduct getProd() {
		return prod;
	}

	public void setProd(TntProdProduct prod) {
		this.prod = prod;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	public String getPayToLvmama() {
		return payToLvmama;
	}

	public void setPayToLvmama(String payToLvmama) {
		this.payToLvmama = payToLvmama;
	}

	public void trim() {
		if (productName != null)
			setProductName(productName.trim());
	}

	public boolean getIsPayToLvmama() {
		return "true".equals(payToLvmama);
	}

	@Override
	public String toString() {
		return "TntProduct [tntProductId=" + tntProductId + ", productId="
				+ productId + ", branchId=" + branchId + ", channelId="
				+ channelId + ", valid=" + valid + "]";
	}

}
