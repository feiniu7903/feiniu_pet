package com.lvmama.comm.pet.po.fin;

import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class SetSettlementChange implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long settlementChangeId;
	private Long settlementItemId;
	private Long orderItemMetaId;
	private String changetype;
	private Long settlementId;
	private Long amountBeforeChange;
	private Long amountAfterChange;
	private String remark;
	private String creator;
	private Date createtime;
	private Long orderId;
	private Long metaProductId;
	

	public Long getSettlementChangeId() {
		return this.settlementChangeId;
	}

	public void setSettlementChangeId(Long settlementChangeId) {
		this.settlementChangeId = settlementChangeId;
	}

	public Long getSettlementItemId() {
		return this.settlementItemId;
	}

	public void setSettlementItemId(Long settlementItemId) {
		this.settlementItemId = settlementItemId;
	}

	public Long getOrderItemMetaId() {
		return this.orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public String getChangetype() {
		return this.changetype;
	}

	public String getChangetypeName() {
		return Constant.SET_SETTLEMENT_CHANGE_TYPE.getCnName(this.changetype);
	}
	public void setChangetype(String changetype) {
		this.changetype = changetype;
	}

	public Long getAmountBeforeChange() {
		return this.amountBeforeChange;
	}
	public float getAmountBeforeChangeYuan() {
		return PriceUtil.convertToYuan(this.amountBeforeChange);
	}

	public void setAmountBeforeChange(Long amountBeforeChange) {
		this.amountBeforeChange = amountBeforeChange;
	}

	public Long getAmountAfterChange() {
		return this.amountAfterChange;
	}
	public float getAmountAfterChangeYuan() {
		return PriceUtil.convertToYuan(this.amountAfterChange);
	}
	public void setAmountAfterChange(Long amountAfterChange) {
		this.amountAfterChange = amountAfterChange;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
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

}