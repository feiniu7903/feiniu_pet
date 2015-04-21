package com.lvmama.distribution.model.lv.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 分销创建订单请求报文体
 * 
 * @author lipengcheng
 * 
 */
public class OrderInfo {

	/** 分销商订单号 */
	private String partnerOrderId;
	/** 产品id */
	private String productId;
	/** 产品类别列表 */
	private List<ProductBranch> productBranchList = new ArrayList<ProductBranch>();
	/** 联系人信息 */
	private ContactPerson contactPerson = new ContactPerson();
	/** 游玩人信息 */
	private List<VisitCustomer> visitCustomerList = new ArrayList<VisitCustomer>();
	/** 邮寄人 */
	private PostPerson postPerson = new PostPerson();
	/** 订单状态*/
	private String status;
	/** 订单支付状态*/
	private String paymentStatus;
	/** 订单ID*/
	private Long orderId;
	/***/
	private String paymentSerialno;
	/***/
	private String bankOrderId;

	public String getPaymentSerialno() {
		return paymentSerialno;
	}

	public void setPaymentSerialno(String paymentSerialno) {
		this.paymentSerialno = paymentSerialno;
	}

	public String getBankOrderId() {
		return bankOrderId;
	}

	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ContactPerson getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(ContactPerson contactPerson) {
		this.contactPerson = contactPerson;
	}

	public List<ProductBranch> getProductBranchList() {
		return productBranchList;
	}

	public void setProductBranchList(List<ProductBranch> productBranchList) {
		this.productBranchList = productBranchList;
	}

	public List<VisitCustomer> getVisitCustomerList() {
		return visitCustomerList;
	}

	public void setVisitCustomerList(List<VisitCustomer> visitCustomerList) {
		this.visitCustomerList = visitCustomerList;
	}

	public PostPerson getPostPerson() {
		return postPerson;
	}

	public void setPostPerson(PostPerson postPerson) {
		this.postPerson = postPerson;
	}

}
