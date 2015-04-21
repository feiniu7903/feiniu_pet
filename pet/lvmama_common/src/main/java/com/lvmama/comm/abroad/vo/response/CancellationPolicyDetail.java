package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
public class CancellationPolicyDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9209744740930362099L;
	/**最晚取消日期*/
	private String DateBefore;
	/**最晚取消时间*/
	private String TimeBefore;
	/**产生费用*/
	private String PercentageCharged;
	/**收取类型（F 第一晚费用，A 全部费用）*/
	private String PercentageOf;
	/**备注*/
	private String Remarks;
	/**
	 * 最晚取消日期
	 * @return
	 */
	public String getDateBefore() {
		return DateBefore;
	}
	public void setDateBefore(String dateBefore) {
		DateBefore = dateBefore;
	}
	/**
	 * 最晚取消时间
	 * @return
	 */
	public String getTimeBefore() {
		return TimeBefore;
	}
	public void setTimeBefore(String timeBefore) {
		TimeBefore = timeBefore;
	}
	/**
	 * 超过时间取消订单，产生费用（百分比）
	 * @return
	 */
	public String getPercentageCharged() {
		return PercentageCharged;
	}
	public void setPercentageCharged(String percentageCharged) {
		PercentageCharged = percentageCharged;
	}
	/**
	 * 超时取消订单产生费用类型（F 收取第一晚费用，A 收取全部费用）
	 * @return
	 */
	public String getPercentageOf() {
		return PercentageOf;
	}
	public void setPercentageOf(String percentageOf) {
		PercentageOf = percentageOf;
	}
	/**
	 * 备注
	 * @return
	 */
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
}
