package com.lvmama.distribution.model.lv;

import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.util.DistributionUtil;


/**
 * 订单对象
 * @author lipengcheng
 *
 */
public class Order {
	/** 订单号id*/
	private String orderId = "";
	
	/** 订单状态 */
	private String status = "";
	/** 支付状态 */
	private String paymentStatus = "";
	/** 凭证状态*/
	private String  credenctStatus = "";
	/**
	 * 订单审核状态 */
	private String approveStatus="";
	/** 分销商与银行交易流水号 */
	private String paymentSerialno = "";
	/** 分销商与银行做交易的订单号 */
	private String bankOrderId = "";
	private String waitPayment;
	private DistributionOrderRefund refund;
	private String performStatus;
	public Order(){
	}
	public Order(String orderId, String status, String paymentStatus) {
		this.orderId = orderId;
		this.status = status;
		this.paymentStatus = paymentStatus;
	}
	public Order(String orderId, String status, String paymentStatus,String credenctStatus,String performStatus) {
		this.orderId = orderId;
		this.status = status;
		this.paymentStatus = paymentStatus;
		this.credenctStatus = credenctStatus;
		if(Constant.ORDER_PERFORM_STATUS.UNPERFORMED.name().equals(performStatus)){
			this.performStatus = "UNUSE";
		}else{
			this.performStatus = "USED";
		}
	}
	public Order(String orderId, String status,String approveStatus,  String paymentStatus,String credenctStatus,String waitPayment,String performStatus) {
		this.orderId = orderId;
		this.status = status;
		this.approveStatus=approveStatus;
		this.paymentStatus = paymentStatus;
		this.credenctStatus = credenctStatus;
		this.waitPayment=waitPayment;
		if(Constant.ORDER_PERFORM_STATUS.UNPERFORMED.name().equals(performStatus)){
			this.performStatus = "UNUSE";
		}else{
			this.performStatus = "USED";
		}
	}
	
	public void setApproveStatusOfOrder(OrdOrder order) {
		this.orderId =String.valueOf(order.getOrderId());
		this.approveStatus=order.getApproveStatus();
		this.waitPayment=order.getZhWaitPayment();
	}
	
	public Order(DistributionOrderRefund refund){
		this.orderId=String.valueOf(refund.getOrderId());
		this.refund=refund;
	}
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned() {
		return this.getOrderId() + this.getStatus() + this.getPaymentStatus() + this.getPaymentSerialno() + this.getBankOrderId()+this.getWaitPayment();
	}
	
	/**
	 * 构造订单信息报文
	 * @return
	 */
	public String buildOrderXmlStr() {
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<order>");
			xmlStr.append(DistributionUtil.buildXmlElement("orderId", orderId));
			xmlStr.append(DistributionUtil.buildXmlElement("status", status));
			xmlStr.append(DistributionUtil.buildXmlElement("paymentStatus", paymentStatus));
			xmlStr.append("</order>");
			return xmlStr.toString();
	}
	
	/**
	 * 构造订单信息列表报文
	 * @return
	 */
	public String buildForGetOrder() {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<order>");
		xmlStr.append(DistributionUtil.buildXmlElement("orderId", orderId));
		xmlStr.append(DistributionUtil.buildXmlElement("status", status));
		xmlStr.append(DistributionUtil.buildXmlElement("paymentStatus", paymentStatus));
		xmlStr.append(DistributionUtil.buildXmlElement("credenctStatus", credenctStatus));
		xmlStr.append(DistributionUtil.buildXmlElement("performStatus", performStatus));
		xmlStr.append("</order>");
		return xmlStr.toString();
	}
	
	/** 构造订单信息推送报文
	 * @return
	 */
	public String buildForPushOrder(){
		Boolean approve = Constant.ORDER_APPROVE_STATUS.VERIFIED.name().equals(approveStatus) ? true : false;
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<order>");
		xmlStr.append(DistributionUtil.buildXmlElement("orderId", orderId));
		xmlStr.append(DistributionUtil.buildXmlElement("status", status));
		xmlStr.append(DistributionUtil.buildXmlElement("approveStatus", approve.toString()));
		xmlStr.append(DistributionUtil.buildXmlElement("paymentStatus", paymentStatus));
		xmlStr.append(DistributionUtil.buildXmlElement("credenctStatus", credenctStatus));
		xmlStr.append(DistributionUtil.buildXmlElement("waitPayment", this.getWaitPayment()));
		xmlStr.append(DistributionUtil.buildXmlElement("performStatus", performStatus));
		xmlStr.append("</order>");
		return xmlStr.toString();
	}

	/**
	 * 构造订单审核状态报文
	 * @return
	 */
	public String buildForGetOrderApprove() {
		Boolean approve = Constant.ORDER_APPROVE_STATUS.VERIFIED.name().equals(approveStatus) ? true : false;
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<order>");
		xmlStr.append(DistributionUtil.buildXmlElement("orderId", this.orderId));
		xmlStr.append(DistributionUtil.buildXmlElement("status", approve.toString()));
		xmlStr.append(DistributionUtil.buildXmlElement("waitPayment", this.getWaitPayment()));
		xmlStr.append("</order>");
		return xmlStr.toString();
	}
	
	
	
	/**
	 * 构造退款报文
	 * @return
	 */
	public String buildForRefund(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<order>");
		xmlStr.append(DistributionUtil.buildXmlElement("orderId", this.refund.getPartnerOrderId()));
		xmlStr.append(DistributionUtil.buildXmlElement("money", this.refund.getRefundAmount()));
		xmlStr.append(DistributionUtil.buildXmlElement("factorage", this.refund.getFactorage()));
		xmlStr.append(DistributionUtil.buildXmlElement("remark", StringUtil.replaceNullStr(this.refund.getRemark())));
		xmlStr.append("</order>");
		return xmlStr.toString();
	}
	/**
	 * 退款signed
	 * @return
	 */
	public String getRefundSigned(){
		return String.valueOf(this.refund.getPartnerOrderId())+String.valueOf(this.refund.getRefundAmount())+String.valueOf(this.refund.getFactorage())+StringUtil.replaceNullStr(this.refund.getRemark());
	}
	
	
	public String buildForResendCode() {
		return "<status>success</status>";
	}
	
	public String getPaymentSerialno() {
		return StringUtil.replaceNullStr(paymentSerialno);
	}
	public void setPaymentSerialno(String paymentSerialno) {
		this.paymentSerialno = paymentSerialno;
	}
	public String getBankOrderId() {
		return StringUtil.replaceNullStr(bankOrderId);
	}
	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}
	public String getOrderId() {
		return StringUtil.replaceNullStr(orderId);
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return StringUtil.replaceNullStr(status);
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentStatus() {
		return StringUtil.replaceNullStr(paymentStatus);
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getCredenctStatus() {
		return StringUtil.replaceNullStr(credenctStatus);
	}
	public void setCredenctStatus(String credenctStatus) {
		this.credenctStatus = credenctStatus;
	}
	public String getApproveStatus() {
		return StringUtil.replaceNullStr(approveStatus);
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public DistributionOrderRefund getRefund() {
		return refund;
	}
	public void setRefund(DistributionOrderRefund refund) {
		this.refund = refund;
	}
	public String getWaitPayment() {
		return StringUtil.replaceNullStr(waitPayment);
	}
	public void setWaitPayment(String waitPayment) {
		this.waitPayment = waitPayment;
	}
	public String getPerformStatus() {
		return performStatus;
	}
	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}
	
}
