package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 支付对象.
 * @author liwenzhan
 *
 */
public class PayAndPrePaymentVO implements Serializable {
	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 5810197899505079003L;
	/**
	 * 
	 */
	private Long paymentId;
	/**
	 * 流水号.
	 */
    private String serial;
    /**
     * 业务类型(哪个业务类型(自由行/代售)).
     */
	private String bizType;
	/**
	 * 对象ID.
	 */
	private Long objectId;
	/**
	 * 对象类型(订单).
	 */
	private String objectType;
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType;
	/**
	 * 支付网关(渠道).
	 */
	private String paymentGateway;
	/**
	 * 支付金额.
	 */
	private Long amount;
	/**
	 * 支付状态(CREATE,PRE_SUCCESS,CANCEL,SUCCESS,FAIL).
	 */
	private String status;
	/**
	 * 回馈时间.
	 */
	private Date callbackTime;
	/**
	 * 支付回馈信息.
	 */
	private String callbackInfo;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 支付网关交易流水号.
	 */
	private String gatewayTradeNo;
	/**
	 * 汇付天下标注银行卡所在行.
	 */
	private String gateId;
	/**
	 * 发出去的交易流水号.
	 */
	private String paymentTradeNo;
	/**
	 * 退款的流水号(中行与招行的分期与支付网关交易流水号不一样，其它的一样).
	 */
	private String refundSerial;
	/**
	 * 是否已通知业务系统.
	 */
	private String notified;
	/**
	 * 操作人(SYSTEM/CSxxx/LVxxx).
	 */
	private String operator;
	/**
	 * 通知时间.
	 */
	private Date notifyTime;
	/**
	 * 原对象ID,当发生支付转移时使用
	 */
	private Long oriObjectId;
	
	/**
	 *  预授权状态.
	 */
	private String preStatus;
	
	/**
	 * 合并支付集合
	 */
	private List<PayAndPrePaymentVO> mergePayPaymentList;
	
	/**
	 * 是否成功
	 * @return
	 */
	public boolean isSuccess(){
		return Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(status);
	}
	
	/**
	 * 是否已经通知
	 * @return
	 */
	public boolean isNotified() {
		return "true".equalsIgnoreCase(notified);
	}
	
	
	/**
	 * 是否预授权
	 * @return
	 */
	public boolean isPrePayment() {
		return Constant.PAYMENT_OPERATE_TYPE.PRE_PAY.name().equalsIgnoreCase(paymentType);
	}
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
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
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	
	/**
	  * 获取支付网关中文含义.
	  * @return
	  */
	 public String getPayWayZh(){
		String gatewayName = Constant.PAYMENT_GATEWAY.getCnName(getPaymentGateway());
		// 如果通过code返回还是code表示没有获取到对应中文名称，那么继续到下一类型网关寻找
		if (getPaymentGateway().equalsIgnoreCase(gatewayName)) {
			gatewayName = Constant.PAYMENT_GATEWAY_OTHER_MANUAL.getCnName(getPaymentGateway());
		}
		// 如果通过code返回还是code表示没有获取到对应中文名称，那么继续到下一类型网关寻找
		if (getPaymentGateway().equalsIgnoreCase(gatewayName)) {
			gatewayName = Constant.PAYMENT_GATEWAY_DIST_MANUAL.getCnName(getPaymentGateway());
		}
		return gatewayName;
	 }
	
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	/**
	 * 获取支付金额以元为单位.
	 * @return
	 */
	public float getAmountYuan() {
		return PriceUtil.convertToYuan(amount);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 获取支付状态.
	 * @return
	 */
	public String getStatusZh() {
		return Constant.PAYMENT_SERIAL_STATUS.getCnName(this.getStatus());
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}
	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}
	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}
	public String getRefundSerial() {
		return refundSerial;
	}
	public void setRefundSerial(String refundSerial) {
		this.refundSerial = refundSerial;
	}
	public String getNotified() {
		return notified;
	}
	public void setNotified(String notified) {
		this.notified = notified;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	public Long getOriObjectId() {
		return oriObjectId;
	}
	public void setOriObjectId(Long oriObjectId) {
		this.oriObjectId = oriObjectId;
	}
	public String getPreStatus() {
		return preStatus;
	}
	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	public String getPayPreStatusZh(){
		String name="";
		if(StringUtils.isNotEmpty(this.getPreStatus())){
			name = Constant.PAYMENT_PRE_STATUS.getCnName(this.getPreStatus());
		}
		return name;
    }
	public List<PayAndPrePaymentVO> getMergePayPaymentList() {
		return mergePayPaymentList;
	}
	public void setMergePayPaymentList(List<PayAndPrePaymentVO> mergePayPaymentList) {
		this.mergePayPaymentList = mergePayPaymentList;
	}
}
