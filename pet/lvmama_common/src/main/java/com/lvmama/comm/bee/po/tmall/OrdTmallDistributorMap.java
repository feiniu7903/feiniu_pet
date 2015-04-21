package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;
import java.util.Date;

public class OrdTmallDistributorMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***
	 * id
	 */
	private Long ordTmallDistributorMapId;
	/**
	 * 分销流水号，分销平台产生的主键
	 */
	private Long fenXiaoId;
	/**
	 * 采购单编号
	 */
	private Long distributorNo;
	/**
	 * 订单编号
	 */
	private Long tcOrderId;
	/**
	 * 分销商在来源网站的帐号名。
	 */
	private String distributorUserName;

	/**
	 * 订购人手机号
	 */
	private String buyerMobile;

	/**
	 * 采购的产品标题 
	 */
	private String title;

	/**
	 * 订购产品ID
	 */
	private Long productId;
	
	/**
	 * 产品类别ID
	 */
	
	private Long categoryId;

	/**
	 * 产品的采购价格
	 */
	private Float price;
	/**
	 * 产品的采购数量
	 */
	private Integer num;

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
	 * 是否是实体票
	 */
	private String isCertificate;
	
	/***
	 * 资源确认状态
	 */
	private String resourceConfirmStatus;
	
	/***
	 * 是否大于最大订购量
	 */
	private String gtMaxNum;
	
	/***
	 * 产品类型
	 */
	private String productType;
	
	/***
	 * ord_order的订单状态
	 */
	private String orderStatus;
	
	/***
	 * 支付目标
	 */
	private String  paymentTarget;
	/**
	 * 支付宝交易号。
	 */
	private String alipayNo;
	/**
	 * 付款时间
	 */
	private Date payTime;

	public Long getOrdTmallDistributorMapId() {
		return ordTmallDistributorMapId;
	}

	public void setOrdTmallDistributorMapId(Long ordTmallDistributorMapId) {
		this.ordTmallDistributorMapId = ordTmallDistributorMapId;
	}

	public Long getFenXiaoId() {
		return fenXiaoId;
	}

	public void setFenXiaoId(Long fenXiaoId) {
		this.fenXiaoId = fenXiaoId;
	}

	public String getDistributorUserName() {
		return distributorUserName;
	}

	public void setDistributorUserName(String distributorUserName) {
		this.distributorUserName = distributorUserName;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

	public String getIsCertificate() {
		return isCertificate;
	}

	public void setIsCertificate(String isCertificate) {
		this.isCertificate = isCertificate;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Long getDistributorNo() {
		return distributorNo;
	}

	public void setDistributorNo(Long distributorNo) {
		this.distributorNo = distributorNo;
	}

	public Long getTcOrderId() {
		return tcOrderId;
	}

	public void setTcOrderId(Long tcOrderId) {
		this.tcOrderId = tcOrderId;
	}

}