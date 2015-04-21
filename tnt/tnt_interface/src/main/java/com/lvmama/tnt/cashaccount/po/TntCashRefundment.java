
package com.lvmama.tnt.cashaccount.po;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.order.po.TntOrder;


/**
 * TntCashRefundment
 * @author gaoxin
 * @version 1.0
 */
public class TntCashRefundment implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 *  fincRefundmentId
	 */
	private Long fincRefundmentId;
	/**
	 *  cashAccountId
	 */
	private java.lang.Long cashAccountId;
	/**
	 *  serial
	 */
	private java.lang.String serial;
	/**
	 *  amount
	 */
	private java.lang.Long amount;
	/**
	 *  createTime
	 */
	private java.util.Date createTime;
	/**
	 *  orderRefundmentId
	 */
	private java.lang.Long orderRefundmentId;
	/**
	 *  refundmentType
	 */
	private java.lang.String refundmentType;
	/**
	 *  tntOrderId
	 */
	private java.lang.Long tntOrderId;
	
	/**
	 *  productName
	 */
	private String productName;
	private TntOrder tntOrder = new TntOrder();
	//columns END

	public TntCashRefundment(){
	}

	public TntCashRefundment(
		Long fincRefundmentId
	){
		this.fincRefundmentId = fincRefundmentId;
	}

	public void setFincRefundmentId(Long value) {
		this.fincRefundmentId = value;
	}
	
	public Long getFincRefundmentId() {
		return this.fincRefundmentId;
	}
	public void setCashAccountId(java.lang.Long value) {
		this.cashAccountId = value;
	}
	
	public java.lang.Long getCashAccountId() {
		return this.cashAccountId;
	}
	public void setSerial(java.lang.String value) {
		this.serial = value;
	}
	
	public java.lang.String getSerial() {
		return this.serial;
	}
	public void setAmount(java.lang.Long value) {
		this.amount = value;
	}
	
	public java.lang.Long getAmount() {
		return this.amount;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setOrderRefundmentId(java.lang.Long value) {
		this.orderRefundmentId = value;
	}
	
	public java.lang.Long getOrderRefundmentId() {
		return this.orderRefundmentId;
	}
	public void setRefundmentType(java.lang.String value) {
		this.refundmentType = value;
	}
	
	public java.lang.String getRefundmentType() {
		return this.refundmentType;
	}
	public void setTntOrderId(java.lang.Long value) {
		this.tntOrderId = value;
	}
	
	public java.lang.Long getTntOrderId() {
		return this.tntOrderId;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCnCreateTime(){
		return TntUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm");
	}
	
	public void setAmountY(String AmountY) {
		this.setAmount(PriceUtil.convertToFen(AmountY));
	}
	public Float getAmountToYuan() {
		return PriceUtil.convertToYuan(this.getAmount());
	}
	
	
	public TntOrder getTntOrder() {
		return tntOrder;
	}

	public void setTntOrder(TntOrder tntOrder) {
		this.tntOrder = tntOrder;
	}

	@Override
	public String toString() {
		return "TntCashRefundment [fincRefundmentId=" + fincRefundmentId + ", cashAccountId=" + cashAccountId + ", serial=" + serial + ", amount=" + amount + ", createTime=" + createTime + ", orderRefundmentId=" + orderRefundmentId + ", refundmentType=" + refundmentType + ", tntOrderId=" + tntOrderId + "]";
	}


}

