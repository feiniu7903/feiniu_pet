package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;


public class PayPaymentGateway implements Serializable{
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 6544118024547672870L;
	/**
	 * 主键
	 */
	private Long paymentGatewayId;
	/**
	 * 网关code
	 */
	private String gateway;
	/**
	 * 网关名称
	 */
	private String gatewayName;
	/**
	 * 是否允许退款(允许=TRUE、不允许=FALSE)
	 */
	private String isAllowRefund;
	/**
	 * 退款网关CODE
	 */
	private String refundGateway;
	/**
	 * 网关创建时间
	 */
	private Date createTime;
	/**
	 * 网关状态(禁用=FORBIDDEN、启动=ENABLE)
	 */
	private String gatewayStatus;
	/**
	 * 网关类型(ONLINE=线上支付、OTHER=其它支付、DIST=分销或团购支付)
	 */
	private String gatewayType;
	
	/**
	 * 退款顺序(部分退款时根据优先级进行退款)
	 */
	private Float refundOrder;
	
	public Long getPaymentGatewayId() {
		return paymentGatewayId;
	}
	public void setPaymentGatewayId(Long paymentGatewayId) {
		this.paymentGatewayId = paymentGatewayId;
	}
	public String getGateway() {
		return gateway;
	}
	 public String getRefundGatewayZh(){
			if(StringUtils.isBlank(getRefundGateway())){
				return "";
			}
			String gatewayName = Constant.PAYMENT_GATEWAY.getCnName(getRefundGateway());
			// 如果通过code返回还是code表示没有获取到对应中文名称，那么继续到下一类型网关寻找
			if (getRefundGateway().equalsIgnoreCase(gatewayName)) {
				gatewayName = Constant.PAYMENT_GATEWAY_OTHER_MANUAL.getCnName(getRefundGateway());
			}
			// 如果通过code返回还是code表示没有获取到对应中文名称，那么继续到下一类型网关寻找
			if (getRefundGateway().equalsIgnoreCase(gatewayName)) {
				gatewayName = Constant.PAYMENT_GATEWAY_DIST_MANUAL.getCnName(getRefundGateway());
			}
			return gatewayName;
		 }
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getGatewayName() {
		return gatewayName;
	}
	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getGatewayStatus() {
		return gatewayStatus;
	}
	public void setGatewayStatus(String gatewayStatus) {
		this.gatewayStatus = gatewayStatus;
	}
	public String getGatewayType() {
		return gatewayType;
	}
	public void setGatewayType(String gatewayType) {
		this.gatewayType = gatewayType;
	}
	
	public Float getRefundOrder() {
		return refundOrder;
	}
	public void setRefundOrder(Float refundOrder) {
		this.refundOrder = refundOrder;
	}
	
	public String printParam() {
		return "PayPaymentGateway [paymentGatewayId=" + paymentGatewayId + ", gateway=" + gateway + ", gatewayName=" + gatewayName + ", isAllowRefund="
				+ isAllowRefund + ", refundGateway=" + refundGateway + ", createTime=" + createTime + ", gatewayStatus=" + gatewayStatus + ", gatewayType="
				+ gatewayType + ", refundOrder=" + refundOrder + "]";
	}
}
