package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 订购并点评.
 *
 * <pre>
 * 返现业务使用
 * </pre>
 *
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class OrderAndComment implements Serializable,Comparable<OrderAndComment> {
	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 9190360010929647045L;
	/**
	 * 用户ID.
	 */
	private Long userId;
	/**
	 * 用户32位NO.
	 */
	private String userNo;
	/**
	 * 用户名字.
	 */
	private String userName;
	/**
	 * 返现金额， 以分为单位.
	 */
	private Long cashRefund;
	/**
	 * 返现金额 (元).
	 */
	private float cashRefundFloat;
	/**
	 * 订单ID.
	 */
	private String orderId;
	/**
	 * 点评ID.
	 */
	private String commentId;
	
	/**
	 * 点评时间.
	 */
	private Date createDate;

	/**
	 * 订单访问时间.
	 */
	private Date orderVisitTime;
	
	
	/**
	 * 以下属性记录订单中对应的产品属性，在这个对象临时放一下，以后可以考虑移走（也是给点评用的）
	 */
	private String orderNo;
	
	private Date orderCreateTime;
	
	private String isCashRefund;
	
	private Long productId;
	
	private String productName;
	
	private String productType;
	
	private String productSmallImage;
	
	private String productOnLine;
	
	private Long productQuantity;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * getCashRefund.
	 *
	 * @return 返现金额，以分为单位
	 */
	public Long getCashRefund() {
		return cashRefund;
	}

	/**
	 * setCashRefund.
	 *
	 * @param cashRefund
	 *            返现金额，以分为单位
	 */
	public void setCashRefund(final Long cashRefund) {
		if (cashRefund != null) {
			this.cashRefundFloat = PriceUtil.convertToYuan(cashRefund.longValue());
		}
		this.cashRefund = cashRefund;
	}

	/**
	 * getOrderId.
	 *
	 * @return 订单ID
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * setOrderId.
	 *
	 * @param orderId
	 *            订单ID
	 */
	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	/**
	 * getCommentId.
	 *
	 * @return 点评ID
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * setCommentId.
	 *
	 * @param commentId
	 *            点评ID
	 */
	public void setCommentId(final String commentId) {
		this.commentId = commentId;
	}

	/**
	 * toString.
	 */
	public String toString() {
		return userId + " " + cashRefund + " " + orderId + " " + commentId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	public float getCashRefundFloat() {
		if(this.cashRefund!=null){
			this.cashRefundFloat = PriceUtil
			.convertToYuan(cashRefund.longValue());
		}
		return cashRefundFloat;
	}

	public void setCashRefundFloat(float cashRefundFloat) {
		this.cashRefundFloat = cashRefundFloat;
	}

	@Override
	public int compareTo(OrderAndComment orderAndComment) {
		if (null == orderAndComment
				|| null == orderAndComment.getOrderId()
				|| null == orderAndComment.getCommentId()) {
			return 1;
		}
		if (orderAndComment.getOrderId().equals(orderId)
				|| orderAndComment.getCommentId().equals(commentId)) {
			return 0;
		}
		return -1;
	}	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static void main(String[] args) {
		java.util.Set< OrderAndComment> c = new java.util.TreeSet<OrderAndComment>();
		java.util.List<OrderAndComment> list = new java.util.ArrayList<OrderAndComment>();
		OrderAndComment c1 = new OrderAndComment();
		c1.setOrderId("12");
		c1.setCommentId("21");
		OrderAndComment c2 = new OrderAndComment();
		c2.setOrderId("12");
		c2.setCommentId("22");
		OrderAndComment c3 = new OrderAndComment();
		c3.setOrderId("21");
		c3.setCommentId("21");
		OrderAndComment c4 = new OrderAndComment();
		c4.setOrderId("21");
		c4.setCommentId("12");		
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		
		c.addAll(list);
		System.out.println(c.size());
		for (OrderAndComment oc : c) {
			System.out.println(oc.getOrderId() + "\t" + oc.getCommentId());
		}
	}

	public Date getOrderVisitTime() {
		return orderVisitTime;
	}

	public void setOrderVisitTime(Date orderVisitTime) {
		this.orderVisitTime = orderVisitTime;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getIsCashRefund() {
		return isCashRefund;
	}

	public void setIsCashRefund(String isCashRefund) {
		this.isCashRefund = isCashRefund;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductSmallImage() {
		return productSmallImage;
	}

	public void setProductSmallImage(String productSmallImage) {
		this.productSmallImage = productSmallImage;
	}

	public String getProductOnLine() {
		return productOnLine;
	}

	public void setProductOnLine(String productOnLine) {
		this.productOnLine = productOnLine;
	}

	public Long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}
	
}
