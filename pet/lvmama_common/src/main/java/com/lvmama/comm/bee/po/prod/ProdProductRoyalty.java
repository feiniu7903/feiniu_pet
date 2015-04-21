package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

/**
 * 销售产品分润
 * 
 * @author taiqichao
 * 
 */
public class ProdProductRoyalty implements Serializable {

	private static final long serialVersionUID = -8090450637187101512L;

	/**
	 * 主键ID
	 */
	private Long royaltyId;

	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 分润类型
	 * @see com.lvmama.comm.bee.po.prod.ProdProductRoyalty.ROYALTY_TYPE
	 */
	private String royaltyType;

	/**
	 * 分润百分比
	 */
	private Float percentage;

	/**
	 * 收款方账户(这里指支付宝收款方Email)
	 */
	private String payeeAccount;

	/**
	 * 备注
	 */
	private String memo;

	public ProdProductRoyalty() {
		royaltyType=ROYALTY_TYPE.ORDER_PAY_AMOUNT.name();
		percentage=1.0f;
	}

	public Long getRoyaltyId() {
		return royaltyId;
	}

	public void setRoyaltyId(Long royaltyId) {
		this.royaltyId = royaltyId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getRoyaltyType() {
		return royaltyType;
	}

	public void setRoyaltyType(String royaltyType) {
		this.royaltyType = royaltyType;
	}

	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 分润类型
	 * 
	 * @author taiqichao
	 * 
	 */
	public enum ROYALTY_TYPE {
		/**
		 * 订单支付金额
		 */
		ORDER_PAY_AMOUNT
	}

}
