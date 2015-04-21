package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

public class LimitSaleTime implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4983684046029057957L;

	protected Long limitSaleTimeId;
	
	protected long productId;
	
	/**
	 * 限制销售时间（7月1日可订购7月20日产品中的7月1日）
	 */
	private Date limitSaleTime;
	
	/**
	 * 限售点开始时间
	 */
	private String limitHourStart;
	
	//限售类型 
	private String limitType;
	
	/**
	 * 限售点结束时间
	 */
	private String limitHourEnd;

	/**
	 * 受限制的游玩时间（7月1日可订购7月20日产品中的7月25日）
	 */
	private Date limitVisitTime;

	public Long getLimitSaleTimeId() {
		return limitSaleTimeId;
	}

	public void setLimitSaleTimeId(Long limitSaleTimeId) {
		this.limitSaleTimeId = limitSaleTimeId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public Date getLimitSaleTime() {
		return limitSaleTime;
	}

	public void setLimitSaleTime(Date limitSaleTime) {
		this.limitSaleTime = limitSaleTime;
	}

	public Date getLimitVisitTime() {
		return limitVisitTime;
	}

	public void setLimitVisitTime(Date limitVisitTime) {
		this.limitVisitTime = limitVisitTime;
	}

	public String getLimitHourStart() {
		return limitHourStart;
	}

	public void setLimitHourStart(String limitHourStart) {
		this.limitHourStart = limitHourStart;
	}

	public String getLimitHourEnd() {
		return limitHourEnd;
	}

	public void setLimitHourEnd(String limitHourEnd) {
		this.limitHourEnd = limitHourEnd;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	
	
}
