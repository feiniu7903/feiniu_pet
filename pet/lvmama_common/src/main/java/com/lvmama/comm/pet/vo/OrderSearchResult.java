package com.lvmama.comm.pet.vo;

/**
 * 订单查询结果
 * 
 * @author yanggan
 *
 */
public class OrderSearchResult extends SimpleOrderItemMeta {
	
	private Long settlementQueueItemId;
	
	private Long targetId;
	
	private String status;
	
	private Double settlementPrice;
	
	private Double settlementPriceSum;

	private String orderStatus;
	
	
	public Long getSettlementQueueItemId() {
		return settlementQueueItemId;
	}

	public void setSettlementQueueItemId(Long settlementQueueItemId) {
		this.settlementQueueItemId = settlementQueueItemId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Double getSettlementPriceSum() {
		return settlementPriceSum;
	}

	public void setSettlementPriceSum(Double settlementPriceSum) {
		this.settlementPriceSum = settlementPriceSum;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getOrderStatusStr() {
		
		if("CANCEL".equals(orderStatus)){
			return "取消";
		}else if("FINISHED".equals(orderStatus)){
			return "完成 （结束）";
		}else if("NORMAL".equals(orderStatus)){
			return "正常";
		}else if("UNCONFIRM".equals(orderStatus)){
			return "未确认结束";
		}
		return orderStatus;
	}
	
}
