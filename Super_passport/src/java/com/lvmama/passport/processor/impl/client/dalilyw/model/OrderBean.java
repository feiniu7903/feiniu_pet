package com.lvmama.passport.processor.impl.client.dalilyw.model;

public class OrderBean {
	private String productId;//产品编码
	private String custName;//客户姓名
	private String custMobile;//客户电话
	private String cardno;
	private String buyNum;//订购数量
	private String arriveDate;//游玩日期
	private boolean isMunit=true;//是否为景点产品
	private String payMethod;//支付方式景区支付还是在线支付
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public boolean isMunit() {
		return isMunit;
	}
	public void setMunit(boolean isMunit) {
		this.isMunit = isMunit;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
}
