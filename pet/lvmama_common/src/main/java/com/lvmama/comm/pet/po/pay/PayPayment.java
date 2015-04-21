package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;

/**
 * 支付对象.
 * @author liwenzhan
 *
 */
public class PayPayment implements Serializable {
	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = -1327340076428874632L;
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
	 * 支付电话号码
	 */
	private String payMobileNum;
	
	private PayPrePayment payPrePayment;
	
	/**
	 * 是否已经通知
	 * @return
	 */
	public boolean isNotified() {
		return "true".equalsIgnoreCase(notified);
	}
	
	/**
	 * 是否成功
	 * @return
	 */
	public boolean isSuccess(){
		return Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(status);
	}
	/**
	 * 是否为已转移订单
	 * @return
	 */
	public boolean isTransferred(){
		return Constant.PAYMENT_SERIAL_STATUS.TRANSFERRED.name().equalsIgnoreCase(status);
	}
	
	/**
	 * 
	 */
	public String geneSerialNo() {
		return SerialUtil.generate24ByteSerialAttaObjectId(objectId);
	}
	
	/**
	 * 是否预授权
	 * @return
	 */
	public boolean isPrePayment() {
		return Constant.PAYMENT_OPERATE_TYPE.PRE_PAY.name().equalsIgnoreCase(paymentType);
	}
	
	
	/**
	 * 是否普通支付
	 * @return
	 */
	public boolean isPayment() {
		return Constant.PAYMENT_OPERATE_TYPE.PAY.name().equalsIgnoreCase(paymentType);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCashAccountRechargePayment() {
		return Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name().equals(bizType);
	}
	
	/**
	 * vst订单支付
	 * @return
	 */
	public boolean isVstOrderPayment(){
		return Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name().equals(bizType);
	}
	/**
	 * 
	 * @return
	 */
	public boolean isBeeOrderPayment() {
		return Constant.PAYMENT_BIZ_TYPE.BEE_ORDER.name().equals(bizType);
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
	/**
	 * 银联预授权支付
	 * @return
	 */
	public boolean isUnionPayPre(){
		return Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name().equalsIgnoreCase(paymentGateway);
	} 
	/**
	 * 百付电话支付
	 * @return
	 */
	public boolean isByPayPre() {
		return Constant.PAYMENT_GATEWAY.TELBYPAY.name().equalsIgnoreCase(paymentGateway);
	}
	/**
	 * 
	 * @return
	 */
	public Long getPaymentId() {
		return paymentId;
	}

	/**
	 * 
	 * @param paymentId
	 */
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * 获取流水号.
	 * @return
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * 设置流水号.
	 * @param serial
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * 获取哪个业务类型(自由行/代售).
	 * @return
	 */
	public String getBizType() {
		return bizType;
	}

	/**
	 * 设置哪个业务类型(自由行/代售).
	 * @param bizType
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/**
	 * 获取对象ID.
	 * @return
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * 设置对象ID.
	 * @param objectId
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * 获取对象类型(订单).
	 * @return
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * 设置对象类型(订单).
	 * @param objectType
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * 获取支付网关(渠道).
	 * @return
	 */
	public String getPaymentGateway() {
		return paymentGateway;
	}

	/**
	 * 设置支付网关(渠道).
	 * @param paymentGateway
	 */
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	/**
	 * 获取支付金额.
	 * @return
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * 设置支付金额.
	 * @param amount
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * 获取支付状态(CREATE,PRE_SUCCESS,CANCEL,SUCCESS,FAIL).
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置支付状态(CREATE,PRE_SUCCESS,CANCEL,SUCCESS,FAIL).
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取回馈时间.
	 * @return
	 */
	public Date getCallbackTime() {
		return callbackTime;
	}

	/**
	 * 设置回馈时间.
	 * @param callbackTime
	 */
	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

	/**
	 * 获取支付回馈信息.
	 * @return
	 */
	public String getCallbackInfo() {
		return callbackInfo;
	}

	/**
	 * 设置支付回馈信息.
	 * @param callbackInfo
	 */
	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}

	/**
	 * 获取创建时间.
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间.
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取支付网关交易流水号.
	 * @return
	 */
	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}

	/**
	 * 设置支付网关交易流水号.
	 * @param gatewayTradeNo
	 */
	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}

	/**
	 * 获取汇付天下标注银行卡所在行.
	 * @return
	 */
	public String getGateId() {
		return gateId;
	}

	/**
	 * 设置汇付天下标注银行卡所在行.
	 * @param gateId
	 */
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	/**
	 * 获取发出去的交易流水号.
	 * @return
	 */
	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	/**
	 * 设置发出去的交易流水号.
	 * @param paymentTradeNo
	 */
	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

	/**
	 * 获取退款的流水号（中行与招行的分期与支付网关交易流水号不一样，其它的一样.
	 * @return
	 */
	public String getRefundSerial() {
		return refundSerial;
	}

	/**
	 * 设置退款的流水号（中行与招行的分期与支付网关交易流水号不一样，其它的一样.
	 * @param refundSerial
	 */
	public void setRefundSerial(String refundSerial) {
		this.refundSerial = refundSerial;
	}

	/**
	 * 获取是否已通知业务系统.
	 * @return
	 */
	public String getNotified() {
		return notified;
	}

	/**
	 * 设置是否已通知业务系统.
	 * @param notified
	 */
	public void setNotified(String notified) {
		this.notified = notified;
	}

	/**
	 * 获取操作人（SYSTEM/CSxxx/LVxxx）.
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置操作人（SYSTEM/CSxxx/LVxxx）.
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取开始通知时间.
	 * @return
	 */
	public Date getNotifyTime() {
		return notifyTime;
	}

	/**
	 * 设置开始通知时间.
	 * @param notifyTime
	 */
	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	/**
	 * 获取支付类型（正常支付/预授权）.
	 * @return
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * 设置支付类型（正常支付/预授权）.
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
	public Long getOriObjectId() {
		return oriObjectId;
	}

	public void setOriObjectId(Long oriObjectId) {
		this.oriObjectId = oriObjectId;
	}

	public PayPrePayment getPayPrePayment() {
		return payPrePayment;
	}

	public void setPayPrePayment(PayPrePayment payPrePayment) {
		this.payPrePayment = payPrePayment;
	}

	public String getPayMobileNum() {
		return payMobileNum;
	}

	public void setPayMobileNum(String payMobileNum) {
		this.payMobileNum = payMobileNum;
	}
	
	/**
	  * 获取支付网关中文含义.
	  * @return
	  */
	 public String getPayWayZh(){
		if(StringUtils.isBlank(getPaymentGateway())){
			return "";
		}
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
	/**
	 * 获取支付金额以元为单位.
	 * @return
	 */
	public float getAmountYuan() {
		return PriceUtil.convertToYuan(amount);
	}
	/**
	 * 获取支付状态.
	 * @return
	 */
	public String getStatusZh() {
		return Constant.PAYMENT_SERIAL_STATUS.getCnName(this.getStatus());
	}
	
    public String getPayPreStatusZh(){
    	String preStatusName = "";
    	if(this.getPayPrePayment()!=null){
    		PayPrePayment payPrePayment  = this.getPayPrePayment();
    		if(StringUtils.isNotEmpty(payPrePayment.getStatus())){
    		   preStatusName = Constant.PAYMENT_PRE_STATUS.getCnName(Constant.PAYMENT_PRE_STATUS.PRE_PAY.name());
    		}
    	}
    	return preStatusName;
    }
	
	/**
	 * 创建退款数据对象
	 * @return
	 */
	public RefundmentToBankInfo createRefundmentVO() {
		RefundmentToBankInfo info = new RefundmentToBankInfo();
		info.setRefundSerial(getRefundSerial());
		info.setPaymentTradeNo(getPaymentTradeNo());
		info.setGatewayTradeNo(getGatewayTradeNo());
		info.setPayAmount(getAmount());
		info.setCallbackTime(getCallbackTime());
		info.setCreateTime(new Date());
		info.setGateId(getGateId());
		info.setPaymentId(getPaymentId());
		info.setObjectId(getObjectId());
		info.setObjectType(getObjectType());
		info.setCustomerIp("127.0.0.1");
		return info;
	}
	/**
	 * 附加支付信息到退款对象
	 * @return
	 */
	public RefundmentToBankInfo appendPaymentToRefundmentToBankInfo(RefundmentToBankInfo refundmentToBankInfo) {
		refundmentToBankInfo.setRefundSerial(getRefundSerial());
		refundmentToBankInfo.setPaymentGateway(getPaymentGateway());
		refundmentToBankInfo.setPaymentTradeNo(getPaymentTradeNo());
		refundmentToBankInfo.setGatewayTradeNo(getGatewayTradeNo());
		refundmentToBankInfo.setPayAmount(getAmount());
		refundmentToBankInfo.setCallbackTime(getCallbackTime());
		refundmentToBankInfo.setGateId(getGateId());
		refundmentToBankInfo.setPaymentId(getPaymentId());
		return refundmentToBankInfo;
	}
	
	/**
	 * 取得银联在线预授权的取消方式
	 * 未完成时使用“撤消方式”
	 * 已经完成时使用“退款方式”
	 * @param prePayment
	 * @return
	 */
	public String getRefundmentTransType(PayPrePayment prePayment) {
		if (getPaymentGateway().equalsIgnoreCase(Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name())) {
			if (Constant.PAYMENT_PRE_STATUS.PRE_PAY.name().equalsIgnoreCase(prePayment.getStatus())) {
				return PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_TRANSTYPE_PAY_CANCEL");
			}
			if (Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name().equalsIgnoreCase(prePayment.getStatus())
					||Constant.PAYMENT_PRE_STATUS.PRE_REFUND.name().equalsIgnoreCase(prePayment.getStatus())) {
				return PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_TRANSTYPE_PAY_REFUND");
			}
		}
		return "";
	}
	
	public static void main(String[] args) {
//		Long obj1 = new Long(1l);
//		Long obj2 = new Long(1l);
//		System.out.println(obj1.equals(obj2))
		PayPayment payPayment=new PayPayment();
		for(int i=0;i<1000;i++){
			payPayment.setSerial(payPayment.geneSerialNo());
			System.out.println(payPayment.getSerial());
		}
		
	}

}
