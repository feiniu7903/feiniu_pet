package com.lvmama.finance.settlement.ibatis.po;

import java.util.Date;

public class OrdSettlementChange {

	private Long settlementChangeId;

	private Long orderItemMetaId;

	private Long settlementId;
	
	private Long subSettlementId;
	
	private Long subSettlementItemId;

	private String changetype;

	private Double amountBeforeChange;

	private Double amountAfterChange;

	private String remark;

	private Long creator;

	private String creatorName;
	
	private Long orderId;

	private Long metaProductId;

	private Date createtime;
	
	private Long totalQuantity;

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Long getSettlementChangeId() {
		return settlementChangeId;
	}

	public void setSettlementChangeId(Long settlementChangeId) {
		this.settlementChangeId = settlementChangeId;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Long getSubSettlementItemId() {
		return subSettlementItemId;
	}

	public void setSubSettlementItemId(Long subSettlementItemId) {
		this.subSettlementItemId = subSettlementItemId;
	}

	public String getChangetype() {
		return changetype;
	}

	public void setChangetype(String changetype) {
		this.changetype = changetype;
	}

	public Double getAmountBeforeChange() {
		return amountBeforeChange;
	}

	public void setAmountBeforeChange(Double amountBeforeChange) {
		this.amountBeforeChange = amountBeforeChange;
	}

	public Double getAmountAfterChange() {
		return amountAfterChange;
	}

	public void setAmountAfterChange(Double amountAfterChange) {
		this.amountAfterChange = amountAfterChange;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Long getSubSettlementId() {
		return subSettlementId;
	}

	public void setSubSettlementId(Long subSettlementId) {
		this.subSettlementId = subSettlementId;
	}
}