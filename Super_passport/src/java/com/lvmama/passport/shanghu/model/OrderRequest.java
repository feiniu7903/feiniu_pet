package com.lvmama.passport.shanghu.model;

public class OrderRequest {
	private String travelDate;//游玩日期
	private String infoId;//产品号-产品标识
	private String custId;//分销商帐号
	private String orderSourceId;//订单流水号
	private String num;//预定数量
	private String linkMan;//联系人姓名
	private String linkPhone;//联系人手机号
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getOrderSourceId() {
		return orderSourceId;
	}
	public void setOrderSourceId(String orderSourceId) {
		this.orderSourceId = orderSourceId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	
}
