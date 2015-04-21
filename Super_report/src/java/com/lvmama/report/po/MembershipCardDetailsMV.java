package com.lvmama.report.po;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class MembershipCardDetailsMV {
	protected Long cardId;			  //会员卡批次代码
	protected String channelName;     //所属渠道
	protected String channelCode;     //渠道代码
	protected String cardPrefixNumber;//批次代码
	protected Long amount;            //发卡数量
	protected Date createTime;        //生成日期
	protected Integer activedCount;   //已激活会员卡(张)
	protected Integer unactiveCount;  //待激活会员卡(张)
	protected String activePercent;   //激活率 
	protected Integer buyCount;       //订购人数
	protected Float actualSum;        //销售总额
	protected Float activedCardSum;	  //激活会员卡后的销售总额
	protected Integer actualPerson;   //实付人数
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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
	public Integer getActivedCount() {
		return activedCount;
	}
	public void setActivedCount(Integer activedCount) {
		this.activedCount = activedCount;
	}
	public Integer getUnactiveCount() {
		return unactiveCount;
	}
	public void setUnactiveCount(Integer unactiveCount) {
		this.unactiveCount = unactiveCount;
	}
	public String getActivePercent() {
		return activePercent;
	}
	public void setActivePercent(String activePercent) {
		this.activePercent = activePercent;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	public Float getActualSum() {
		return actualSum;
	}
	public void setActualSum(Float actualSum) {
		this.actualSum = actualSum;
	}
	
	public String getFormattedCreateTime() {
		return DateUtil.getFormatDate(createTime, "yyyy-MM-dd hh:mm:ss");
	}
	public Integer getActualPerson() {
		return actualPerson;
	}
	public void setActualPerson(Integer actualPerson) {
		this.actualPerson = actualPerson;
	}
	public Float getActivedCardSum() {
	    return activedCardSum;
	}
	public void setActivedCardSum(Float activedCardSum) {
	    this.activedCardSum = activedCardSum;
	}
}
