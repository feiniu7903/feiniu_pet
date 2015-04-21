package com.lvmama.report.po;

/**
 * LOSC订单统计
 * @author ganyingwen
 *
 */
public class LoscOrderStatisticsMV {
	/**
	 * 渠道名称
	 */
	private String channel;
	/**
	 * 订单数
	 */
	private Long orderAmount;
	/**
	 * 订单总金额
	 */
	private Long sumOughtPay;
	/**
	 * 实付总金额
	 */
	private Long sumActualPay;
	/**
	 * 支付单数
	 */
	private Long payedAmount;
	/**
	 * 付费会员数
	 */
	private Long payedMemberAmount;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getSumOughtPay() {
		return sumOughtPay;
	}
	public void setSumOughtPay(Long sumOughtPay) {
		this.sumOughtPay = sumOughtPay;
	}
	public Long getSumActualPay() {
		return sumActualPay;
	}
	public void setSumActualPay(Long sumActualPay) {
		this.sumActualPay = sumActualPay;
	}
	public Long getPayedAmount() {
		return payedAmount;
	}
	public void setPayedAmount(Long payedAmount) {
		this.payedAmount = payedAmount;
	}
	public Long getPayedMemberAmount() {
		return payedMemberAmount;
	}
	public void setPayedMemberAmount(Long payedMemberAmount) {
		this.payedMemberAmount = payedMemberAmount;
	}	
}
