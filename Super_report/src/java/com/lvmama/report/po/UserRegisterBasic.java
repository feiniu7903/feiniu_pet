package com.lvmama.report.po;

import java.util.Date;

public class UserRegisterBasic {
	//用户Id
	private String userId;
	//注册日期
	private Date createdDate;
	//渠道标识
	private String channelId;
	//渠道名字
    private String channelName;
    //应付订单总额
	private Long sumOughtPay;
	//实付订单总额
	private Long sumActualPay;
	//注册日实际支付总额
	private Long sumRegistedDayActualPay;
	//一个月内实际支付总额
	private Long sumOneMonthActualPay;
	//三个月内实际支付总额
	private Long sumThreeMonthActualPay;
	//六个月内实际支付总额
	private Long sumSixMonthActualPay;
	
	//新增会员数
	private Long newUsersNum;
	//新增付费会员数
	private Long newPayedUsersNum;
	//注册日当天付费人数
	private Long registedDayPayedNum;
	//一个月内付费人数
	private Long oneMonthPayedNum;
	//三个月内付费人数
	private Long threeMonthPayedNum;
	//六个月内付费人数
	private Long sixMonthPayedNum;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
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
	public Long getSumRegistedDayActualPay() {

		return sumRegistedDayActualPay;
	}
	public void setSumRegistedDayActualPay(Long sumRegistedDayActualPay) {
		this.sumRegistedDayActualPay = sumRegistedDayActualPay;
	}
	public Long getSumOneMonthActualPay() {

		return sumOneMonthActualPay;
	}
	public void setSumOneMonthActualPay(Long sumOneMonthActualPay) {
		this.sumOneMonthActualPay = sumOneMonthActualPay;
	}
	public Long getSumThreeMonthActualPay() {
		return sumThreeMonthActualPay;
	}
	public void setSumThreeMonthActualPay(Long sumThreeMonthActualPay) {
		this.sumThreeMonthActualPay = sumThreeMonthActualPay;
	}
	public Long getSumSixMonthActualPay() {
		return sumSixMonthActualPay;
	}
	public void setSumSixMonthActualPay(Long sumSixMonthActualPay) {
		this.sumSixMonthActualPay = sumSixMonthActualPay;
	}
	public Long getNewUsersNum() {
		return newUsersNum;
	}
	public void setNewUsersNum(Long newUsersNum) {
		this.newUsersNum = newUsersNum;
	}
	public Long getNewPayedUsersNum() {
		return newPayedUsersNum;
	}
	public void setNewPayedUsersNum(Long newPayedUsersNum) {
		this.newPayedUsersNum = newPayedUsersNum;
	}
	public Long getRegistedDayPayedNum() {
		return registedDayPayedNum;
	}
	public void setRegistedDayPayedNum(Long registedDayPayedNum) {
		this.registedDayPayedNum = registedDayPayedNum;
	}
	public Long getOneMonthPayedNum() {
		return oneMonthPayedNum;
	}
	public void setOneMonthPayedNum(Long oneMonthPayedNum) {
		this.oneMonthPayedNum = oneMonthPayedNum;
	}
	public Long getThreeMonthPayedNum() {
		return threeMonthPayedNum;
	}
	public void setThreeMonthPayedNum(Long threeMonthPayedNum) {
		this.threeMonthPayedNum = threeMonthPayedNum;
	}
	public Long getSixMonthPayedNum() {
		return sixMonthPayedNum;
	}
	public void setSixMonthPayedNum(Long sixMonthPayedNum) {
		this.sixMonthPayedNum = sixMonthPayedNum;
	}
}
