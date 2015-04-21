package com.lvmama.clutter.model;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class JianYiOrderDTO implements Serializable{
	
	private static final long serialVersionUID = 8363932423304688951L;
	/**
	 *订单对应产品ID 
	 */
	private String productId;
	/**
	 * 订单联系人
	 */
	private String userName;
	/**
	 * 订单联系人手机
	 */
	private String userMobile;
	/**
	 * 游玩时间
	 */
	private String visitTime;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 预订产品列表
	 */
	private List<JianYiProduct> jianYiProductList;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 工单编号
	 */
	private Long workOrderId;
	/**
	 *简易预订单状态 
	 */
	private String orderJYStatus;
	
	/**
	 *简易预订订单图片 
	 */
	private String orderJYImg;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<JianYiProduct> getJianYiProductList() {
		return jianYiProductList;
	}
	public void setJianYiProductList(List<JianYiProduct> jianYiProductList) {
		this.jianYiProductList = jianYiProductList;
	}
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOrderJYStatus() {
		return orderJYStatus;
	}
	public void setOrderJYStatus(String orderJYStatus) {
		this.orderJYStatus = orderJYStatus;
	}
	public String getOrderJYImg() {
		return orderJYImg;
	}
	public void setOrderJYImg(String orderJYImg) {
		this.orderJYImg = orderJYImg;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
