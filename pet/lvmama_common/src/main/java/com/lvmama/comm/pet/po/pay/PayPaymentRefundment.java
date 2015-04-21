package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.RefundmentToBankInfo;

/**
 * 支付退款明细.
 * @author liwenzhan
 *
 */
public class PayPaymentRefundment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Long payRefundmentId;
	/**
	 * 
	 */
	private Long paymentId;
	
	/**
	 * 对象ID
	 */
	private Long objectId;
	
	/**
	 * 对象类型
	 */
	private String objectType;
	
	/**
	 * 业务类型
	 */
	private String bizType;
	
	/**
	 * 退款类型
	 * @see Constant.REFUND_TYPE
	 * COMPENSATION("补偿"),
	 * ORDER_REFUNDED("订单退款"),
	 * CASH_ACCOUNT_WITHDRAW("现金账户取现");
	 */
	private String refundType;
	/**
	 * 退款金额.
	 */
	private Long amount;
	/**
	 * 退款状态.
	 */
	private String status;
	/**
	 * 通知状态.
	 */
	private String notified;
	
	/**
	 * 通知时间
	 */
	private Date notifyTime;
	/**
	 * 发出去的流水号 退款流水号(10位 订单号+ 9 +支付次数).
	 */
	private String serial;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 返回时间. 
	 */
	private Date callbackTime;
	/**
	 * 返回信息.
	 */
	private String callbackInfo;
	/**
	 * 是否允许退款(允许=TRUE、不允许=FALSE)
	 */
	private String isAllowRefund;
	/**
	 * 退款银行.
	 */
	private String refundGateway;
	
	private String operator;
	
	private Long userId;
	
	private Long orderId;
	/**
	 * 创建退款数据对象
	 * @return
	 */
	public RefundmentToBankInfo createRefundmentVO() {
		RefundmentToBankInfo info = new RefundmentToBankInfo();
		info.setCreateTime(new Date());
		info.setObjectId(getObjectId());
		info.setObjectType(getObjectType());
		info.setCustomerIp("127.0.0.1");
		info.setRefundGateway(getRefundGateway());
		info.setRefundAmount(getAmount());
		info.setPayRefundmentId(getPayRefundmentId());
		info.setUserId(getUserId());
		info.setPaymentId(getPaymentId());
		info.setOrderId(getOrderId());
		info.setRefundType(getRefundType());
		info.setPayPaymentRefunfmentSerial(getSerial());
		info.setOperator(getOperator());
		return info;
	} 
	
	/**
	 * 是否退款成功
	 * @return
	 */
	public boolean isSuccess(){
		return "SUCCESS".equalsIgnoreCase(status);
	}
	
	/**
	 * 是否已通知
	 * @return
	 */
	public  boolean isNotified() {
		return "true".equalsIgnoreCase(notified);
	}
	
	public boolean isRefundToCashAccount() {
		return Constant.PAYMENT_GATEWAY.CASH_ACCOUNT.name().equalsIgnoreCase(this.refundGateway);
	}
	
	public boolean isWithdrawCashAccount() {
		return Constant.PAYMENT_GATEWAY.ALIPAY_BPTB.name().equalsIgnoreCase(refundGateway) || Constant.PAYMENT_GATEWAY.ALIPAY_BATCH.name().equalsIgnoreCase(refundGateway);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isCashAccountRechargePayment() {
		return Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name().equals(bizType);
	}
	/**
	 * 
	 * @return
	 */
	public boolean isBeeOrderPayment() {
		return Constant.PAYMENT_BIZ_TYPE.BEE_ORDER.name().equals(bizType);
	}
	
	public boolean isVstOrderPayment(){
		return Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name().equals(bizType);
	}
	/**
	 * 
	 * @return
	 */
	public boolean isAntOrderPayment() {
		return Constant.PAYMENT_BIZ_TYPE.ANT_ORDER.name().equals(bizType);
	}
	/**
	 * 
	 * @return
	 */
	public boolean isSuperOrderPayment() {
		return Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name().equals(bizType);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isTransHotelOrderPayment() {
		return Constant.PAYMENT_BIZ_TYPE.TRANSHOTEL_ORDER.name().equals(bizType);
	}
	
	public Long getPayRefundmentId() {
		return payRefundmentId;
	}
	public void setPayRefundmentId(Long payRefundmentId) {
		this.payRefundmentId = payRefundmentId;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	
	public Long getObjectId() {
		return objectId;
	}


	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}


	public String getObjectType() {
		return objectType;
	}


	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNotified() {
		return notified;
	}
	public void setNotified(String notified) {
		this.notified = notified;
	}
	public Date getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCallbackTime() {
		return callbackTime;
	}
	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}
	public String getCallbackInfo() {
		return callbackInfo;
	}
	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}

	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getRefundGateway() {
		return refundGateway;
	}


	public void setRefundGateway(String refundGateway) {
		this.refundGateway = refundGateway;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getBizType() {
		return bizType;
	}


	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getIsAllowRefund() {
		return isAllowRefund;
	}

	public void setIsAllowRefund(String isAllowRefund) {
		this.isAllowRefund = isAllowRefund;
	}

	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}	
}
