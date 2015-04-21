package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

public class MarkMembershipCardDiscount  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9012732281669448108L;
	protected Long cardDiscountId;
	protected Long cardId;
	protected Long couponId;
	protected Date createTime;
	protected String operatorName;
	
	public Long getCardDiscountId() {
		return cardDiscountId;
	}
	public void setCardDiscountId(Long cardDiscountId) {
		this.cardDiscountId = cardDiscountId;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}
