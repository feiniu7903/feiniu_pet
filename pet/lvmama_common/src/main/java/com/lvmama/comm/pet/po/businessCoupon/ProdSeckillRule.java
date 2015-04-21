package com.lvmama.comm.pet.po.businessCoupon;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;

public class ProdSeckillRule implements Serializable{
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 2773548737398650603L;
	private Long id;
	private Long businessCouponId;//优惠活动表ID
	private Long productId;//产品Id
	private Long branchId;//类别ID
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Long reducePrice;//优惠价格,元
	private Long reducePriceF;//分
	private Long amount;//秒杀数量
	private Long payValidTime;//支付有效时间
	private Long ipBuyLimit;//每个Ip限制购买数量,**更改为放入人数倍数**
	private Long userBuyLimit;//每个用户ID限购数量,**更改为虚拟销量**
	private Long localStock;//本地用户秒杀的库存量
	private Date createTime;//创建时间
	private Long creator;//操作人Id
	private String timePriceStatus="0";//时间价格表状态，0:未修改时间价格表,1:已修改时间价格表，默认为0
	
	private Date timePriceDate;  //取时间价格表某天
	private Long timePrice;    	//取时间价格表某天的价格
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBusinessCouponId() {
		return businessCouponId;
	}
	public void setBusinessCouponId(Long businessCouponId) {
		this.businessCouponId = businessCouponId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
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
	public Long getReducePrice() {
		return reducePrice;
	}
	public void setReducePrice(Long reducePrice) {
		this.reducePrice = reducePrice;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getPayValidTime() {
		return payValidTime;
	}
	public void setPayValidTime(Long payValidTime) {
		this.payValidTime = payValidTime;
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
	public Long getLocalStock() {
		return localStock;
	}
	public void setLocalStock(Long localStock) {
		this.localStock = localStock;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public String getTimePriceStatus() {
		return timePriceStatus;
	}
	public void setTimePriceStatus(String timePriceStatus) {
		this.timePriceStatus = timePriceStatus;
	}
	public Date getTimePriceDate() {
		return timePriceDate;
	}
	public void setTimePriceDate(Date timePriceDate) {
		this.timePriceDate = timePriceDate;
	}
	public Long getTimePrice() {
		return timePrice;
	}
	public void setTimePrice(Long timePrice) {
		this.timePrice = timePrice;
	}
	public float getReducePriceYuan(){
		if (reducePrice==null || reducePrice <= 0L) { 
			return 0L; 
			} else { 
			return PriceUtil.convertToYuan(reducePrice); 
		} 
	}
}
