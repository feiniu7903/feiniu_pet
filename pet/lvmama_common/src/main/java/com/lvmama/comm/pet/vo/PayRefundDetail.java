package com.lvmama.comm.pet.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 支付退款及预授权明细.
 * @author fengyu
 *
 */
public class PayRefundDetail implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 退款明细信息
	 */
	/**
	 * 退款单号
	 */
	private Long payRefundmentId;
	/**
	 * 支付单号
	 */
	private Long paymentId;
	/**
	 * 支付对象ID
	 */
	private Long objectId;
	/**
	 * 支付对象类型
	 */
	private String objectType;
	
	private Long userId;
	/**
	 * 退款金额.
	 */
	private Long amount;
	/**
	 * 退款金额（元）.
	 */
	private String amountYuan;
	/**
	 * 退款状态.
	 */
	private String refundStatus;
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
	 * 操作员
	 */
	private String operator;
	
	/**
	 * 预授权明细信息
	 */
	/**
	 *  预授权状态.
	 */
	private String preStatus;
	/**
	 * 预授权开始时间.
	 */
	private Date startTime;
	/**
	 * 预授权结束时间.
	 */
	private Date endTime;
	/**
	 * 预授权完成时间.
	 */
	private Date completeTime;
	/**
	 * 预授权撤销时间.
	 */
	private Date cancelTime;

	private String refundStatusName;

	private String preStatusName;
	
	private String refundGateway;

	private String paymentGateway;

	private String payRefundGateway;

	private String paymentGatewayName;

	private String refundGatewayName;

	private String isAllowRefund;

	private String refundmentObjectId;

	private String paymentTradeNo;
	
	private String gatewayTradeNo;
	
	private String refundSerial;

	private Long orderId;

	private String refundType;

	private String serial;

	/**
	 * 是否可以手动成功.
	 * @return
	 */
	public boolean isCanToManualSucc(){
		if(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(refundStatus)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 是否成功.
	 * @return
	 */
	public boolean isRefunded(){
		if(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(refundStatus)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否可以手动失败.
	 * @return
	 */
	public boolean isCanToManualFail(){
		if (Constant.PAYMENT_SERIAL_STATUS.FAIL.name().equalsIgnoreCase(refundStatus)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 是否可以再次退款.
	 * @return
	 */
	public boolean isCanToTryAgainRefund(){
		if (Constant.PAYMENT_SERIAL_STATUS.FAIL.name().equalsIgnoreCase(refundStatus)) {
			return true;
		} else {
			return false;
		}
	}
	
	private String refundmentObjectType;

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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
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
	public String getCallbackTimeToSimpleDate() {
		return DateUtil.getFormatDate(callbackTime, "yyyy-MM-dd HH:mm:ss");
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
	public String getPreStatus() {
		return preStatus;
	}
	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getRefundStatusName() {
		refundStatusName = Constant.PAY_REFUNDMENT_SERIAL_STATUS.getCnName(this.refundStatus);
		return refundStatusName;
	}
	public void setRefundStatusName(String refundStatusName) {
		this.refundStatusName = refundStatusName;
	}
	public String getPreStatusName() {
		preStatusName = Constant.PAYMENT_PRE_STATUS.getCnName(this.preStatus);
		return preStatusName;
	}
	public void setPreStatusName(String preStatusName) {
		this.preStatusName = preStatusName;
	}
	public String getAmountYuan() {
		this.amountYuan = String.valueOf(PriceUtil.convertToYuan(amount));
		return amountYuan;
	}

	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRefundGateway() {
		return refundGateway;
	}
	public void setRefundGateway(String refundGateway) {
		this.refundGateway = refundGateway;
	}
	public String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	public String getPaymentGatewayName() {
		return paymentGatewayName;
	}
	public void setPaymentGatewayName(String paymentGatewayName) {
		this.paymentGatewayName = paymentGatewayName;
	}
	public String getRefundGatewayName() {
		return refundGatewayName;
	}
	public void setRefundGatewayName(String refundGatewayName) {
		this.refundGatewayName = refundGatewayName;
	}
	public String getIsAllowRefund() {
		return isAllowRefund;
	}
	public void setIsAllowRefund(String isAllowRefund) {
		this.isAllowRefund = isAllowRefund;
	}
	public String getIsAllowRefundName() {
		return Constant.PAYMENT_GATEWAY_IS_ALLOW_REFUND.getCnName(this.isAllowRefund);
	}

	public String getRefundToSource() {
		if (payRefundGateway != null && paymentGateway != null && paymentGateway.equals(payRefundGateway)) {
			return Constant.TRUE_FALSE.TRUE.getCnName();
		} else {
			return Constant.TRUE_FALSE.FALSE.getCnName();
		}
	}

	public String getPayRefundGateway() {
		return payRefundGateway;
	}
	public void setPayRefundGateway(String payRefundGateway) {
		this.payRefundGateway = payRefundGateway;
	}
	public String getRefundmentObjectId() {
		return refundmentObjectId;
	}
	public void setRefundmentObjectId(String refundmentObjectId) {
		this.refundmentObjectId = refundmentObjectId;
	}
	public String getRefundmentObjectType() {
		return refundmentObjectType;
	}
	public void setRefundmentObjectType(String refundmentObjectType) {
		this.refundmentObjectType = refundmentObjectType;
	}
	public String getRefundmentObjectTypeName() {
		return Constant.PAYMENT_OBJECT_TYPE.getCnName(refundmentObjectType);
	}
	public String getObjectTypeName() {
		return Constant.PAYMENT_OBJECT_TYPE.getCnName(objectType);
	}

	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}

	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}

	public String getRefundSerial() {
		return refundSerial;
	}

	public void setRefundSerial(String refundSerial) {
		this.refundSerial = refundSerial;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundTypeName() {
		return Constant.REFUND_TYPE.getCnName(refundType);
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}

