package com.lvmama.comm.pet.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 支付退款及预授权明细.
 * @author fengyu
 *
 */
public class PayPaymentRefundmentAndPrePayment implements Serializable{
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
	 * 订单号
	 */
	private Long objectId;
	
	private String objectType;
	
	private Long userId;
	/**
	 * 退款金额.
	 */
	private Long amount;
	/**
	 * 退款金额（元）.
	 */
	private Float amountYuan;
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
	public Float getAmountYuan() {
		this.amountYuan = PriceUtil.convertToYuan(amount);
		return amountYuan;
	}
	public void setAmountYuan(Float amountYuan) {
		this.amountYuan = amountYuan;
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
	
}
