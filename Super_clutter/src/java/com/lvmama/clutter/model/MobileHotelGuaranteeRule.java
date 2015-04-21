package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.Date;

public class MobileHotelGuaranteeRule implements Serializable {
	private static final long serialVersionUID = -2062565657322992426L;
	/**选项**/
	private String option;
	/**是否需要担保**/
	private boolean isNeedGuarantee;
	/**最早到店时间**/
	private Date earliestArrivalTime;
	/**最晚到店时间**/
	private Date latestArrivalTime;

	
	public MobileHotelGuaranteeRule() {
		super();
	}

	/**
	 * option 选项
	 * isNeedGuarantee 是否需要担保
	 * earliestArrivalTime 最早到店时间
	 * latestArrivalTime	最晚到店时间
	 */
	public MobileHotelGuaranteeRule(String option, boolean isNeedGuarantee,
			Date earliestArrivalTime, Date latestArrivalTime) {
		super();
		this.option = option;
		this.isNeedGuarantee = isNeedGuarantee;
		this.earliestArrivalTime = earliestArrivalTime;
		this.latestArrivalTime = latestArrivalTime;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public boolean isNeedGuarantee() {
		return isNeedGuarantee;
	}

	public void setNeedGuarantee(boolean isNeedGuarantee) {
		this.isNeedGuarantee = isNeedGuarantee;
	}

	public Date getEarliestArrivalTime() {
		return earliestArrivalTime;
	}

	public void setEarliestArrivalTime(Date earliestArrivalTime) {
		this.earliestArrivalTime = earliestArrivalTime;
	}

	public Date getLatestArrivalTime() {
		return latestArrivalTime;
	}

	public void setLatestArrivalTime(Date latestArrivalTime) {
		this.latestArrivalTime = latestArrivalTime;
	}

}
