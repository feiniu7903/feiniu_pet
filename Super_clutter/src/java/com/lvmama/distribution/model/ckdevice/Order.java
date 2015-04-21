package com.lvmama.distribution.model.ckdevice;

import java.util.List;

import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 订单对象
 * @author gaoxin
 */
public class Order {
	/** 订单号id*/
	private String orderId = "";
	/**
	 * 订单说明
	 */
	private String orderDesc;
	/** 订单状态 */
	private String status = "";
	/** 支付状态 */
	private String paymentStatus = "";
	/** 凭证状态*/
	private String  credenctStatus = "";
	private String waitPayment;
	private DistributionOrderRefund refund;
	private String performStatus;
	private String totalAmount;
	
	private String totalQuantity;
	private String metaTotalQuantity;
	
	private String phoneNo;
	private List<Product> productList;
	
	
	public Order(){
	}
	
	public Order(String orderId, String status, String paymentStatus) {
		this.orderId = orderId;
		this.status = status;
		this.paymentStatus = paymentStatus;
	}
	
	public Order(String orderId ,String orderDesc, String status, String paymentStatus,String credenctStatus,String performStatus) {
		this.orderId = orderId;
		this.orderDesc = orderDesc;
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
		this.waitPayment=order.getZhWaitPayment();
	}
	
	public Order(DistributionOrderRefund refund){
		this.orderId=String.valueOf(refund.getOrderId());
		this.refund=refund;
	}
	
	public Order(Long orderId, String phoneNo) {
		this.orderId=String.valueOf(orderId);
		this.phoneNo=phoneNo;
	}

	public Long getLongOrderId() {
		return Long.valueOf(StringUtil.replaceNullStr(orderId));
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
		if("PERFORMED".equalsIgnoreCase(performStatus)||"AUTOPERFORMED".equalsIgnoreCase(performStatus)||"USED".equalsIgnoreCase(performStatus)){
			return "USED";
		}
		return "UNUSE";
	}
	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setMetaTotalQuantity(String metaTotalQuantity) {
		this.metaTotalQuantity = metaTotalQuantity;
	}

	public String getMetaTotalQuantity() {
		return metaTotalQuantity;
	}
	
	
}
