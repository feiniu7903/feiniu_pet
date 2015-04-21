package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;

/**
 * 秒杀规则时间段VO
 * @author liudong
 */
public final class SeckillRuleVO implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5520757321607763330L;
	//特卖规则字段
	private Long id;//秒杀规则ID
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Long reducePrice;//优惠价格
	private Long amount;//秒杀数量
	private Long businessCouponId;//优惠活动id
	private Date createTime;//创建时间
	private Long ipBuyLimit;//每个Ip限制购买数量
	private Long userBuyLimit;//每个用户ID限购数量 
	private Long payValidTime;//支付有效时间
	
	public Long getBusinessCouponId() {
		return businessCouponId;
	}
	public void setBusinessCouponId(Long businessCouponId) {
		this.businessCouponId = businessCouponId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getReducePrice() {
		return reducePrice;
	}
	public void setReducePrice(Long reducePrice) {
		this.reducePrice = reducePrice;
	}
	//获取元
	public Long getReducePriceY(){
		return (long)PriceUtil.convertToYuan(reducePrice);
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getIpBuyLimit() {
		return ipBuyLimit;
	}
	public void setIpBuyLimit(Long ipBuyLimit) {
		this.ipBuyLimit = ipBuyLimit;
	}
	public Long getUserBuyLimit() {
		return userBuyLimit;
	}
	public void setUserBuyLimit(Long userBuyLimit) {
		this.userBuyLimit = userBuyLimit;
	}
	public Long getPayValidTime() {
		return payValidTime;
	}
	public void setPayValidTime(Long payValidTime) {
		this.payValidTime = payValidTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
