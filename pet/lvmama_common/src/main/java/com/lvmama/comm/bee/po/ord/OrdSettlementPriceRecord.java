package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrdSettlementPriceRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long recordId;
	private Long orderItemMetaId;
	private Long bActualSettlementPrice;
	private Long bTotalSettlementPrice;
	private Long actualSettlementPrice;
	private Long totalSettlementPrice;
	private String changeType;
	private String changeResult;
	private String reason;
	private String creator;
	private Date createTime;
	private String remark;
	private String status;
	private int settlementPay;
	private boolean ifSettlementPayment;
	private Long updatePrice;
	
	public Long getUpdatePrice() {
		return updatePrice;
	}
	public void setUpdatePrice(Long updatePrice) {
		this.updatePrice = updatePrice;
	}
	public boolean getIfSettlementPayment() {
		return ifSettlementPayment;
	}
	public void setIfSettlementPayment(boolean ifSettlementPayment) {
		this.ifSettlementPayment = ifSettlementPayment;
	}
	public Long getbActualSettlementPrice() {
		return bActualSettlementPrice;
	}
	public void setbActualSettlementPrice(Long bActualSettlementPrice) {
		this.bActualSettlementPrice = bActualSettlementPrice;
	}
	public Long getbTotalSettlementPrice() {
		return bTotalSettlementPrice;
	}
	public void setbTotalSettlementPrice(Long bTotalSettlementPrice) {
		this.bTotalSettlementPrice = bTotalSettlementPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSettlementPay() {
		return settlementPay;
	}
	public void setSettlementPay(int settlementPay) {
		this.settlementPay = settlementPay;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getChangeResult() {
		return changeResult;
	}
	public String getZhChangeResult() {
		return Constant.ORD_SETTLEMENT_PRICE_CHANGE_RESULT.getCnName(changeResult);
	}
	public void setChangeResult(String changeResult) {
		this.changeResult = changeResult;
	}
	public String getChangeType() {
		return changeType;
	}
	public String getZhChangeType() {
		return Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE.getCnName(changeType);
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getReason() {
		return reason;
	}
	public String getZhReason() {
		return Constant.ORD_SETTLEMENT_PRICE_REASON.getCnName(reason);
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}
	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	public Long getActualSettlementPrice() {
		return actualSettlementPrice;
	}
	public float getActualSettlementPriceYuan() {
		return PriceUtil.convertToYuan(actualSettlementPrice);
	}
	public void setActualSettlementPrice(Long actualSettlementPrice) {
		this.actualSettlementPrice = actualSettlementPrice;
	}
	public Long getTotalSettlementPrice() {
		return totalSettlementPrice;
	}
	public float getTotalSettlementPriceYuan() {
		return PriceUtil.convertToYuan(totalSettlementPrice);
	}
	public void setTotalSettlementPrice(Long totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}
	
}
