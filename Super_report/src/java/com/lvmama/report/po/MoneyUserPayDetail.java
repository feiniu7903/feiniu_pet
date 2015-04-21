package com.lvmama.report.po;

import java.util.Date;

public class MoneyUserPayDetail {
	/**
	 * 团号
	 */
	private String travelGroupCode;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 游玩时间
	 */
	private Date visitTime;
	/**
	 * 下单时间
	 */
	private Date orderCreateTime;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 应付金额
	 */
	private Long oughtPay;
	/**
	 * 支付金额
	 */
	private Long payAmount;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 商品数量
	 */
	private Long quantity;
	/**
	 * 商品单价
	 */
	private Long price;
	/**
	 * 商品总价
	 */
	private Long productTotalAmount;
	/**
	 * 商品所属公司编码
	 */
	private String filialeName;
	
	private String payFrom;
	
	public String getPayFrom() {
		return payFrom;
	}
	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}
	
	public String getPayFromName(){
		if("BONUS".equals(payFrom)){
			return "奖金";
		}else if("MONEY".equals(payFrom)){
			return "现金";
		}
		return null;
	}
	
	public String getTravelGroupCode() {
		return travelGroupCode;
	}
	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
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
	public Long getOughtPay() {
		return oughtPay;
	}
	public void setOughtPay(Long oughtPay) {
		this.oughtPay = oughtPay;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getProductTotalAmount() {
		return productTotalAmount;
	}
	public void setProductTotalAmount(Long productTotalAmount) {
		this.productTotalAmount = productTotalAmount;
	}
	public String getFilialeName() {
		return filialeName;
	}
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
	
	public double getOughtPayYuan(){
		return oughtPay / 100.00;
	}
	public double getPriceYuan(){
		return price / 100.00;
	}
	public double getProductTotalAmountYuan(){
		return productTotalAmount / 100.00;
	}
	public double getPayAmountYuan(){
		return payAmount / 100.00;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
}
