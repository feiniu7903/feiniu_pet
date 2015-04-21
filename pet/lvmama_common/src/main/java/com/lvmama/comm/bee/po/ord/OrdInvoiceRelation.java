/**
 * 
 */
package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

/**
 * 发票与订单之间关联信息
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public class OrdInvoiceRelation implements Serializable{

	private Long relationId;
	private Long orderId;
	private Long invoiceId;
	
	
	private OrdOrder order;
	/**
	 * @return the relationId
	 */
	public Long getRelationId() {
		return relationId;
	}
	/**
	 * @param relationId the relationId to set
	 */
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the invoiceId
	 */
	public Long getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the order
	 */
	public OrdOrder getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(OrdOrder order) {
		this.order = order;
	}
	
	
}
