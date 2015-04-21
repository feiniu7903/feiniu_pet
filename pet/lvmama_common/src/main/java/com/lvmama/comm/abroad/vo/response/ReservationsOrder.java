package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
import java.util.Date;

public class ReservationsOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2010661189372417835L;
	/**主键Id*/
	private Long id;
	/**订单号*/
	private String orderNo;
	/**支付状态*/
	private String paymentStatus;
	/**应付金额*/
	private Long oughtPay;
	/**实付金额*/
	private Long actualPay;
	/**审核状态*/
	private String approveStatus;
	/**处理描述*/
	private String dealDesc;
	/**废单原因*/
	private String cancelReason;
	/**废单人*/
	private String cancelOperator;
	/**废单时间*/
	private Date cancelTime;
	/**用户备注*/
	private String userMemo;
	/**用户反馈*/
	private String userReply;
	/**支付等待时间*/
	private Date waitPayment;
	/**用户id*/
	private String userId;
	/**订单状态*/
	private String orderStatus;
	/**下单渠道*/
	private String channel;
	/**最晚取消时间*/
	private Date lastCancelTime;
	/**下单时间*/
	private Date createdTime;
	/**支付时间*/
	private Date paymentTime;
	/**联系人姓名*/
	private String contactPersonName;
	/**联系电话*/
	private String contactMobile;
	/**产品名称*/
	private String productName;
	/**数量*/
	private String quantity;
	
	private String orderStatusZH;
	private String approveStatusZH;
	private String paymentStatusZH;
	private float oughtPayFloat;
	private float actualPayFloat;
	/**订单号*/
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**支付状态*/
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	/**应付金额*/
	public Long getOughtPay() {
		return oughtPay;
	}
	public void setOughtPay(Long oughtPay) {
		this.oughtPay = oughtPay;
	}
	/**实付金额*/
	public Long getActualPay() {
		return actualPay;
	}
	public void setActualPay(Long actualPay) {
		this.actualPay = actualPay;
	}
	/**审核状态*/
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	/**废单描述*/
	public String getDealDesc() {
		return dealDesc;
	}
	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}
	/**废单原因*/
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	/**废单人*/
	public String getCancelOperator() {
		return cancelOperator;
	}
	public void setCancelOperator(String cancelOperator) {
		this.cancelOperator = cancelOperator;
	}
	/**废单时间*/
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	/**用户备注*/
	public String getUserMemo() {
		return userMemo;
	}
	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}
	/**用户反馈*/
	public String getUserReply() {
		return userReply;
	}
	public void setUserReply(String userReply) {
		this.userReply = userReply;
	}
	/**用户id*/
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**订单状态*/
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**渠道*/
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**最迟取消时间*/
	public Date getLastCancelTime() {
		return lastCancelTime;
	}
	public void setLastCancelTime(Date lastCancelTime) {
		this.lastCancelTime = lastCancelTime;
	}
	/**创建时间*/
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**支付等待时间*/
	public Date getWaitPayment() {
		return waitPayment;
	}
	public void setWaitPayment(Date waitPayment) {
		this.waitPayment = waitPayment;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderStatusZH() {
		return orderStatusZH;
	}
	public void setOrderStatusZH(String orderStatusZH) {
		this.orderStatusZH = orderStatusZH;
	}
	public String getApproveStatusZH() {
		return approveStatusZH;
	}
	public void setApproveStatusZH(String approveStatusZH) {
		this.approveStatusZH = approveStatusZH;
	}
	public String getPaymentStatusZH() {
		return paymentStatusZH;
	}
	public void setPaymentStatusZH(String paymentStatusZH) {
		this.paymentStatusZH = paymentStatusZH;
	}
	public float getOughtPayFloat() {
		return oughtPayFloat;
	}
	public void setOughtPayFloat(float oughtPayFloat) {
		this.oughtPayFloat = oughtPayFloat;
	}
	public float getActualPayFloat() {
		return actualPayFloat;
	}
	public void setActualPayFloat(float actualPayFloat) {
		this.actualPayFloat = actualPayFloat;
	}
}
