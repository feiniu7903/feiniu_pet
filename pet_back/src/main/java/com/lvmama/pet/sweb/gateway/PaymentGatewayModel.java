package com.lvmama.pet.sweb.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;
import com.lvmama.comm.vo.Constant.PAYMENT_GATEWAY_IS_ALLOW_REFUND;
import com.lvmama.comm.vo.Constant.PAYMENT_GATEWAY_STATUS;
import com.lvmama.comm.vo.Constant.PAYMENT_GATEWAY_TYPE;

/**
 * PayPaymentGateway扩展类（用于定义部分查询条件及action相关参数）
 * @author ZHANG Nan
 *
 */
public class PaymentGatewayModel extends PayPaymentGateway{
	

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = -7606641435308386038L;

	/**
	 * 支付网关-是否允许退款
	 */
	private PAYMENT_GATEWAY_IS_ALLOW_REFUND[] paymentGatewayIsAllowRefund;
	
	/**
	 * 支付网关-网关状态
	 */
	private PAYMENT_GATEWAY_STATUS[] paymentGatewayStatus;
	/**
	 *  支付网关-网关类型
	 */
	private PAYMENT_GATEWAY_TYPE[] paymentGatewayType;
	
	/**
	 * 页面展示集合
	 */
	private List<PayPaymentGateway> payPaymentGatewayList=new ArrayList<PayPaymentGateway>();
	
	/**
	 * 页面参数
	 */
	private Map<String,String> paramMap=new HashMap<String,String>();
	
	/**
	 * 跳转页面地址
	 */
	private String target;
	
	/**
	 * 支付网关 控制线下支付可输入项
	 */
	private PayPaymentGatewayElement payPaymentGatewayElement=new PayPaymentGatewayElement();
	
	

	public PayPaymentGatewayElement getPayPaymentGatewayElement() {
		return payPaymentGatewayElement;
	}

	public void setPayPaymentGatewayElement(PayPaymentGatewayElement payPaymentGatewayElement) {
		this.payPaymentGatewayElement = payPaymentGatewayElement;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public PAYMENT_GATEWAY_IS_ALLOW_REFUND[] getPaymentGatewayIsAllowRefund() {
		return paymentGatewayIsAllowRefund;
	}

	public void setPaymentGatewayIsAllowRefund(PAYMENT_GATEWAY_IS_ALLOW_REFUND[] paymentGatewayIsAllowRefund) {
		this.paymentGatewayIsAllowRefund = paymentGatewayIsAllowRefund;
	}

	public PAYMENT_GATEWAY_STATUS[] getPaymentGatewayStatus() {
		return paymentGatewayStatus;
	}

	public void setPaymentGatewayStatus(PAYMENT_GATEWAY_STATUS[] paymentGatewayStatus) {
		this.paymentGatewayStatus = paymentGatewayStatus;
	}

	public PAYMENT_GATEWAY_TYPE[] getPaymentGatewayType() {
		return paymentGatewayType;
	}

	public void setPaymentGatewayType(PAYMENT_GATEWAY_TYPE[] paymentGatewayType) {
		this.paymentGatewayType = paymentGatewayType;
	}

	public List<PayPaymentGateway> getPayPaymentGatewayList() {
		return payPaymentGatewayList;
	}

	public void setPayPaymentGatewayList(List<PayPaymentGateway> payPaymentGatewayList) {
		this.payPaymentGatewayList = payPaymentGatewayList;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
