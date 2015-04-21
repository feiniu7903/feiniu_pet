package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrdTmallMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4592478444637005058L;
	/***
	 * id
	 */
	private Long tmallMapId;
	/**
	 * 淘宝订单号
	 */
	private String tmallOrderNo;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

	/**
	 * 订购人手机号
	 */
	private String buyerMobile;

	/**
	 * 订购产品名称
	 */
	private String productName;

	/**
	 * 订购产品ID
	 */
	private Long productId;
	
	/**
	 * 产品类别ID
	 */
	
	private Long categoryId;

	/**
	 * 淘宝产品的价格
	 */
	private BigDecimal productTmallPrice;

	/**
	 * 驴妈妈订单号
	 */
	private Long lvOrderId;

	/**
	 * 是否系统搬单(true|false)
	 */
	private String systemOrder;

	/**
	 * 订单状态(create|processing|success|failure)
	 */
	private String status;

	/**
	 * 淘宝原备注内容
	 */
	private String tmallMemo;

	/**
	 * 处理该笔淘宝订单的淘宝客服 工号
	 */
	private String seller;

	/**
	 * 系统搬单失败原因
	 */
	private String failedReason;

	/**
	 * 失败订单的处理人
	 */
	private String operatorName;

	/**
	 * 失败订单的处理时间
	 */
	private Date processTime;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/***
	 * 失败订单处理状态
	 */
	private String processStatus;
	
	/***
	 * 产品类型
	 */
	private String productType;
	
	/***
	 * 是否是实体票
	 */
	private String certificate;
	
	/***
	 * 资源确认状态
	 */
	private String resourceConfirmStatus;
	
	/***
	 * 是否大于最大订购量
	 */
	private String gtMaxNum;
	
	/***
	 * ord_order的订单状态
	 * @return
	 */
	private String orderStatus;
	
	/***
	 * 支付目标
	 * @return
	 */
	private String  paymentTarget;
	
	/**
	 * 电子凭证 token
	 */
	private String token;
	
	/**
	 * 购买数量
	 */
	private String buyNum;
	
    public Long getTmallMapId() {
        return tmallMapId;
    }

    public void setTmallMapId(Long tmallMapId) {
        this.tmallMapId = tmallMapId;
    }

	public String getTmallOrderNo() {
		return tmallOrderNo;
	}

	public void setTmallOrderNo(String tmallOrderNo) {
		this.tmallOrderNo = tmallOrderNo;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getProductTmallPrice() {
		return productTmallPrice;
	}

	public void setProductTmallPrice(BigDecimal productTmallPrice) {
		this.productTmallPrice = productTmallPrice;
	}

	public Long getLvOrderId() {
		return lvOrderId;
	}

	public void setLvOrderId(Long lvOrderId) {
		this.lvOrderId = lvOrderId;
	}

	public String getSystemOrder() {
		return systemOrder;
	}

	public void setSystemOrder(String systemOrder) {
		this.systemOrder = systemOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTmallMemo() {
		return tmallMemo;
	}

	public void setTmallMemo(String tmallMemo) {
		this.tmallMemo = tmallMemo;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getResourceConfirmStatus() {
		return resourceConfirmStatus;
	}

	public void setResourceConfirmStatus(String resourceConfirmStatus) {
		this.resourceConfirmStatus = resourceConfirmStatus;
	}

	public String getGtMaxNum() {
		return gtMaxNum;
	}

	public void setGtMaxNum(String gtMaxNum) {
		this.gtMaxNum = gtMaxNum;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	
}