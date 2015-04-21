package com.lvmama.comm.abroad.po;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class AhotelOrdPayment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7564382308602847079L;
	private Long paymentId;
	private String serial;
	private Long orderId;
	private String paymentGateway;
	private Long amount;
	private String gatewayTradeNo;
	private String status;
	private Date callbackTime;
	private String callbackInfo;
	private Date createTime;
	private String operator;
	private String gateId;
	
	private boolean fullyPayed;
	
	public AhotelOrdPayment() {}
	
	public AhotelOrdPayment(String serial) {
		this.serial = serial;
		if (serial!=null) {
			orderId = Long.parseLong(serial.substring(14));
		}
	}
	
	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public void geneSerialNo() {
		serial = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS")+orderId;
	}
	
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
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
	
	public String getPaymentType() {
		if (paymentGateway.equalsIgnoreCase(Constant.PAYMENT_GATEWAY.ALIPAY.name())) {
			return "ONLINE";
		}
		if (paymentGateway.equalsIgnoreCase(Constant.PAYMENT_GATEWAY.CHINAPNR.name())) {
				return "TELEPHONE";
		}
		return "UNKNOWN";
	}
	
	public boolean isChinaPnr() {
		return paymentGateway.equalsIgnoreCase(Constant.PAYMENT_GATEWAY.CHINAPNR.name());
	}
	public boolean isSuccess() {
		return status.equalsIgnoreCase(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStatusZh() {
		return Constant.PAYMENT_SERIAL_STATUS.getCnName(this.getStatus());
	}
	
	public String getPayWayZh(){
		return Constant.PAYMENT_GATEWAY.getCnName(this.getPaymentGateway());
	}

	public boolean isFullyPayed() {
		return fullyPayed;
	}

	public void setFullyPayed(boolean fullyPayed) {
		this.fullyPayed = fullyPayed;
	}
	
	public float getAmountYuan() {
		return PriceUtil.convertToYuan(amount);
	}

}
