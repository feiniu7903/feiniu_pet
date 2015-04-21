package com.lvmama.comm.pet.po.mark;

import java.util.Date;

/**
 * 会员卡批次
 * @author Brian
 *
 */
public class MarkMembershipCard implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3939525031959183265L;
	//标识
	protected Long cardId;
	//渠道标识
	protected Long channelId;
	//会员卡批次号
	protected String cardPrefixNumber;
	//数量
	protected Long amount;
	//创建日期
	protected Date createTime;
	//绑定渠道的时间
	protected Date bindingChannelTime;
	//是否绑定优惠措施
	protected String isBindingDiscount = "FALSE";
	//绑定优惠措施的时间
	protected Date bindingDiscountTime;
	//所属会员卡是否已有激活
	protected String isActivate = "FALSE";
	
	
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getCardPrefixNumber() {
		return cardPrefixNumber;
	}
	public void setCardPrefixNumber(String cardPrefixNumber) {
		this.cardPrefixNumber = cardPrefixNumber;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getBindingChannelTime() {
		return bindingChannelTime;
	}
	public void setBindingChannelTime(Date bindingChannelTime) {
		this.bindingChannelTime = bindingChannelTime;
	}
	public String getIsBindingDiscount() {
		return isBindingDiscount;
	}
	public void setIsBindingDiscount(String isBindingDiscount) {
		this.isBindingDiscount = isBindingDiscount;
	}
	public Date getBindingDiscountTime() {
		return bindingDiscountTime;
	}
	public void setBindingDiscountTime(Date bindingDiscountTime) {
		this.bindingDiscountTime = bindingDiscountTime;
	}
	public String getIsActivate() {
		return isActivate;
	}
	public void setIsActivate(String isActivate) {
		this.isActivate = isActivate;
	}
}
