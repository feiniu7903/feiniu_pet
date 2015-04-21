package com.lvmama.comm.bee.po.ord;

public class OrdOrderBatchOrder implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1106040098788473605L;
	private Long batchId;
	private Long orderId;
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
