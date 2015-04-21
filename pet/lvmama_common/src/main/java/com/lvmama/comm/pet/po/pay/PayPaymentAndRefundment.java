package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

/**
 * 支付及退款对象
 * @author ZHANG Nan
 *
 */
public class PayPaymentAndRefundment implements Serializable{

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 7685645708630482438L;
	
	//***************************退款对象
	/**
	 * 退款主键
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
	
	
	
	//**************************************支付对象
	/**
	 * 支付主键
	 */
	private Long payPaymentId;
	/**
	 * 流水号.
	 */
    private String paySerial;
    /**
     * 业务类型(哪个业务类型(自由行/代售)).
     */
	private String payBizType;
	/**
	 * 对象ID.
	 */
	private Long payObjectId;
	/**
	 * 对象类型(订单).
	 */
	private String payObjectType;
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String payPaymentType;
	/**
	 * 支付网关(渠道).
	 */
	private String payPaymentGateway;
	/**
	 * 支付金额.
	 */
	private Long payAmount;
	/**
	 * 支付状态(CREATE,PRE_SUCCESS,CANCEL,SUCCESS,FAIL).
	 */
	private String payStatus;
	/**
	 * 回馈时间.
	 */
	private Date payCallbackTime;
	/**
	 * 支付回馈信息.
	 */
	private String payCallbackInfo;
	/**
	 * 创建时间.
	 */
	private Date payCreateTime;
	/**
	 * 支付网关交易流水号.
	 */
	private String payGatewayTradeNo;
	/**
	 * 汇付天下标注银行卡所在行.
	 */
	private String payGateId;
	/**
	 * 发出去的交易流水号.
	 */
	private String payPaymentTradeNo;
	/**
	 * 退款的流水号(中行与招行的分期与支付网关交易流水号不一样，其它的一样).
	 */
	private String payRefundSerial;
	/**
	 * 是否已通知业务系统.
	 */
	private String payNotified;
	/**
	 * 操作人(SYSTEM/CSxxx/LVxxx).
	 */
	private String payOperator;
	/**
	 * 通知时间.
	 */
	private Date payNotifyTime;
	/**
	 * 原对象ID,当发生支付转移时使用
	 */
	private Long payOriObjectId;
	
	
	
	
	//**********************************预授权对象
	/**
	 * 预授权主键
	 */
	private Long prePrePaymentId;
	/**
	 * 
	 */
	private Long prePaymentId;
	/**
	 *  预授权状态.
	 */
	private String preStatus;
	/**
	 * 预授权开始时间.
	 */
	private Date preStartTime;
	/**
	 * 预授权结束时间.
	 */
	private Date preEndTime;
	/**
	 * 预授权完成时间.
	 */
	private Date preCompleteTime;
	/**
	 * 预授权撤销时间.
	 */
	private Date preCancelTime;
	/**
	 * 预授权通知类型 .
	 */
	private String preNotifyStatus;
	/**
	 * 预授权通知时间 .
	 */
	private Date preNotifyTime;
	/**
	 * 操作者 .
	 */
	private String preOperator;
	
	
	
	/**
	 * 是否预授权
	 * @return
	 */
	public boolean isPrePayment() {
		return Constant.PAYMENT_OPERATE_TYPE.PRE_PAY.name().equalsIgnoreCase(getPayPaymentType());
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
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
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
	public String getIsAllowRefund() {
		return isAllowRefund;
	}
	public void setIsAllowRefund(String isAllowRefund) {
		this.isAllowRefund = isAllowRefund;
	}
	public String getRefundGateway() {
		return refundGateway;
	}
	public void setRefundGateway(String refundGateway) {
		this.refundGateway = refundGateway;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getPayPaymentId() {
		return payPaymentId;
	}
	public void setPayPaymentId(Long payPaymentId) {
		this.payPaymentId = payPaymentId;
	}
	public String getPaySerial() {
		return paySerial;
	}
	public void setPaySerial(String paySerial) {
		this.paySerial = paySerial;
	}
	public String getPayBizType() {
		return payBizType;
	}
	public void setPayBizType(String payBizType) {
		this.payBizType = payBizType;
	}
	public Long getPayObjectId() {
		return payObjectId;
	}
	public void setPayObjectId(Long payObjectId) {
		this.payObjectId = payObjectId;
	}
	public String getPayObjectType() {
		return payObjectType;
	}
	public void setPayObjectType(String payObjectType) {
		this.payObjectType = payObjectType;
	}
	public String getPayPaymentType() {
		return payPaymentType;
	}
	public void setPayPaymentType(String payPaymentType) {
		this.payPaymentType = payPaymentType;
	}
	public String getPayPaymentGateway() {
		return payPaymentGateway;
	}
	public void setPayPaymentGateway(String payPaymentGateway) {
		this.payPaymentGateway = payPaymentGateway;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public Date getPayCallbackTime() {
		return payCallbackTime;
	}
	public void setPayCallbackTime(Date payCallbackTime) {
		this.payCallbackTime = payCallbackTime;
	}
	public String getPayCallbackInfo() {
		return payCallbackInfo;
	}
	public void setPayCallbackInfo(String payCallbackInfo) {
		this.payCallbackInfo = payCallbackInfo;
	}
	public Date getPayCreateTime() {
		return payCreateTime;
	}
	public void setPayCreateTime(Date payCreateTime) {
		this.payCreateTime = payCreateTime;
	}
	public String getPayGatewayTradeNo() {
		return payGatewayTradeNo;
	}
	public void setPayGatewayTradeNo(String payGatewayTradeNo) {
		this.payGatewayTradeNo = payGatewayTradeNo;
	}
	public String getPayGateId() {
		return payGateId;
	}
	public void setPayGateId(String payGateId) {
		this.payGateId = payGateId;
	}
	public String getPayPaymentTradeNo() {
		return payPaymentTradeNo;
	}
	public void setPayPaymentTradeNo(String payPaymentTradeNo) {
		this.payPaymentTradeNo = payPaymentTradeNo;
	}
	public String getPayRefundSerial() {
		return payRefundSerial;
	}
	public void setPayRefundSerial(String payRefundSerial) {
		this.payRefundSerial = payRefundSerial;
	}
	public String getPayNotified() {
		return payNotified;
	}
	public void setPayNotified(String payNotified) {
		this.payNotified = payNotified;
	}
	public String getPayOperator() {
		return payOperator;
	}
	public void setPayOperator(String payOperator) {
		this.payOperator = payOperator;
	}
	public Date getPayNotifyTime() {
		return payNotifyTime;
	}
	public void setPayNotifyTime(Date payNotifyTime) {
		this.payNotifyTime = payNotifyTime;
	}
	public Long getPayOriObjectId() {
		return payOriObjectId;
	}
	public void setPayOriObjectId(Long payOriObjectId) {
		this.payOriObjectId = payOriObjectId;
	}
	public Long getPrePrePaymentId() {
		return prePrePaymentId;
	}
	public void setPrePrePaymentId(Long prePrePaymentId) {
		this.prePrePaymentId = prePrePaymentId;
	}
	public Long getPrePaymentId() {
		return prePaymentId;
	}
	public void setPrePaymentId(Long prePaymentId) {
		this.prePaymentId = prePaymentId;
	}
	public String getPreStatus() {
		return preStatus;
	}
	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	public Date getPreStartTime() {
		return preStartTime;
	}
	public void setPreStartTime(Date preStartTime) {
		this.preStartTime = preStartTime;
	}
	public Date getPreEndTime() {
		return preEndTime;
	}
	public void setPreEndTime(Date preEndTime) {
		this.preEndTime = preEndTime;
	}
	public Date getPreCompleteTime() {
		return preCompleteTime;
	}
	public void setPreCompleteTime(Date preCompleteTime) {
		this.preCompleteTime = preCompleteTime;
	}
	public Date getPreCancelTime() {
		return preCancelTime;
	}
	public void setPreCancelTime(Date preCancelTime) {
		this.preCancelTime = preCancelTime;
	}
	public String getPreNotifyStatus() {
		return preNotifyStatus;
	}
	public void setPreNotifyStatus(String preNotifyStatus) {
		this.preNotifyStatus = preNotifyStatus;
	}
	public Date getPreNotifyTime() {
		return preNotifyTime;
	}
	public void setPreNotifyTime(Date preNotifyTime) {
		this.preNotifyTime = preNotifyTime;
	}
	public String getPreOperator() {
		return preOperator;
	}
	public void setPreOperator(String preOperator) {
		this.preOperator = preOperator;
	}
}
