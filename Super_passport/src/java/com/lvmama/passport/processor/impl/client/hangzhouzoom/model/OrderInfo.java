package com.lvmama.passport.processor.impl.client.hangzhouzoom.model;

import java.util.List;

public class OrderInfo {
	private String customerOrderId; // 我方订单号
	private String userName; // 用户名称
	private String sex; // 性别
	private String identityCard; // 身份证号
	private String email; // 邮箱地址
	private String mobile; // 手机号码
	private String tel; // 固定电话
	private String orderProductId; // 合作方订单号
	private String enterTime; // 游玩时间
	private String amount; //总数
	private String sendBySelf;//是否在自身平台上发送短信消息(1或0)
	private String isTest;//此订单是否作测试（1或0）
	private List<Product> productList;

	public String getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(String orderProductId) {
		this.orderProductId = orderProductId;
	}

	public String getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(String customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public String getSex() {
		return sex;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSendBySelf() {
		return sendBySelf;
	}

	public void setSendBySelf(String sendBySelf) {
		this.sendBySelf = sendBySelf;
	}

	public String getIsTest() {
		return isTest;
	}

	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
}
