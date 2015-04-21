package com.lvmama.passport.processor.impl.client.zhiyoubao.model;
public class SubOrder {
	private String needCheckNum; //需检票数量
	private String alreadyCheckNum; //已检票数量
	private String returnNum ; //已退数量
	private String checkStatus;//<un_check(未检), checking（开检）, checked(完成)>  检票状态
	private String orderType;//<hotel酒店, repast餐厅, scenic景区>  订单类型
	private String orderCode;//订单编号
	private String lastCheckTime;//最后一次检票时间，或者是取消订单时间，如果没有发生检票的退票，返回的是空串
	
	public SubOrder() {}
	public SubOrder(String needCheckNum, String alreadyCheckNum,
			String returnNum, String checkStatus, String orderType,
			String orderCode, String lastCheckTime) {
		this.needCheckNum = needCheckNum;
		this.alreadyCheckNum = alreadyCheckNum;
		this.returnNum = returnNum;
		this.checkStatus = checkStatus;
		this.orderType = orderType;
		this.orderCode = orderCode;
		this.lastCheckTime = lastCheckTime;
	}
	public String getNeedCheckNum() {
		return needCheckNum;
	}
	public void setNeedCheckNum(String needCheckNum) {
		this.needCheckNum = needCheckNum;
	}
	public String getAlreadyCheckNum() {
		return alreadyCheckNum;
	}
	public void setAlreadyCheckNum(String alreadyCheckNum) {
		this.alreadyCheckNum = alreadyCheckNum;
	}
	public String getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(String returnNum) {
		this.returnNum = returnNum;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getLastCheckTime() {
		return lastCheckTime;
	}
	public void setLastCheckTime(String lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}

}
