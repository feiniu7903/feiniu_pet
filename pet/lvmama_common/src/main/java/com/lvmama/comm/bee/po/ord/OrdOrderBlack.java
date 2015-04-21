package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

public class OrdOrderBlack implements Serializable{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long blackOrderId; 	
	  private Long orderId;		//订单ID
	  private String mobile;	//手机号码
	  private Long productId;	//产品ID
	  private Long userId;		//用户ID
	  private String orderStatus;
	  private String paymentStatus;
	  private String imei;
	  private Date createTime;
	  public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getBlackOrderId() {
		return blackOrderId;
	  }
	  public void setBlackOrderId(Long blackOrderId) {
		this.blackOrderId = blackOrderId;
	  }
	  public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public Long getProductId() {
			return productId;
		}
		public void setProductId(Long productId) {
			this.productId = productId;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public String getPaymentStatus() {
			return paymentStatus;
		}
		public void setPaymentStatus(String paymentStatus) {
			this.paymentStatus = paymentStatus;
		}
}
