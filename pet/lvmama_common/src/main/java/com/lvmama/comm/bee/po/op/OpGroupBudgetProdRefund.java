package com.lvmama.comm.bee.po.op;


/**
 * 团预算产品成本明细（包含退款明细信息)
 *
 */
public class OpGroupBudgetProdRefund extends OpGroupBudgetProd {
	//退款金额
	private Double refundAmount;
	//供应商承担金额 OR 游客损失
	private String refundType;
	//订单状态
	private String orderStatus;
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	/**
	 * 打包数量
	 */
	private Long productQuantity;
	/**
	 * 实际结算价格
	 */
	private Long actualSettlementPrice;
	
	public Long getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}
	public Long getActualSettlementPrice() {
		return actualSettlementPrice;
	}
	public void setActualSettlementPrice(Long actualSettlementPrice) {
		this.actualSettlementPrice = actualSettlementPrice;
	}
	
}