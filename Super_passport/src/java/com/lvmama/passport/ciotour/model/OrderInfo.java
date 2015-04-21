package com.lvmama.passport.ciotour.model;

import java.util.List;

public class OrderInfo {
	private String sellerSysNo;// 第三方网站接入商家ID
	private String thirdpartOrderId;// 第三方代销网站的订单ID
	private String soAmount;// 订单总金额
	private String subscribeDate;//预定日期
	private int tcomboID;//分销套餐编号
	private Long tcomboQty;//分销套餐数量
	private String subscriberName;// 联系人姓名
	private int subscriberIdCardType;// 联系人证件类型：0=身份证，1=导游证，2=护照，3-港澳回乡证，4-台胞证
	private String subscriberIdCard;// 联系人身份证号码
	private String subscriberPhone;// 联系人联系电话/手机
	private String orderDate;// 下单时间,格式:yyyy-MM-dd HH:mm:ss
	private int isPay;// 如果IsPay=0，则表示订单未支付，需要再调支付接口。如果IsPay=1，则表示订单已支付，不需要调支付接口
	private int thirdpartCustomerId;// 第三方代销网站的客户ID
	private String memo;// 备注
	private boolean isTc=false;//是否为套餐类型
	private List<OrderItem> orderItemList;
	private List<OrderConsumer> orderConsumerList;//消费者（游客）用户
	public String getSellerSysNo() {
		return sellerSysNo;
	}
	public void setSellerSysNo(String sellerSysNo) {
		this.sellerSysNo = sellerSysNo;
	}
	public String getThirdpartOrderId() {
		return thirdpartOrderId;
	}
	public void setThirdpartOrderId(String thirdpartOrderId) {
		this.thirdpartOrderId = thirdpartOrderId;
	}
	public String getSoAmount() {
		return soAmount;
	}
	public void setSoAmount(String soAmount) {
		this.soAmount = soAmount;
	}
	public String getSubscribeDate() {
		return subscribeDate;
	}
	public void setSubscribeDate(String subscribeDate) {
		this.subscribeDate = subscribeDate;
	}
	public int getTcomboID() {
		return tcomboID;
	}
	public void setTcomboID(int tcomboID) {
		this.tcomboID = tcomboID;
	}
	public Long getTcomboQty() {
		return tcomboQty;
	}
	public void setTcomboQty(Long tcomboQty) {
		this.tcomboQty = tcomboQty;
	}
	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	public int getSubscriberIdCardType() {
		return subscriberIdCardType;
	}
	public void setSubscriberIdCardType(int subscriberIdCardType) {
		this.subscriberIdCardType = subscriberIdCardType;
	}
	public String getSubscriberIdCard() {
		return subscriberIdCard;
	}
	public void setSubscriberIdCard(String subscriberIdCard) {
		this.subscriberIdCard = subscriberIdCard;
	}
	public String getSubscriberPhone() {
		return subscriberPhone;
	}
	public void setSubscriberPhone(String subscriberPhone) {
		this.subscriberPhone = subscriberPhone;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getIsPay() {
		return isPay;
	}
	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}
	public int getThirdpartCustomerId() {
		return thirdpartCustomerId;
	}
	public void setThirdpartCustomerId(int thirdpartCustomerId) {
		this.thirdpartCustomerId = thirdpartCustomerId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public List<OrderConsumer> getOrderConsumerList() {
		return orderConsumerList;
	}
	public void setOrderConsumerList(List<OrderConsumer> orderConsumerList) {
		this.orderConsumerList = orderConsumerList;
	}
	public boolean isTc() {
		return isTc;
	}
	public void setTc(boolean isTc) {
		this.isTc = isTc;
	}
	
}
