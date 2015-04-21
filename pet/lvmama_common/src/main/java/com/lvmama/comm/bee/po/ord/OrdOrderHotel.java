package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

public class OrdOrderHotel implements Serializable {
	private static final long serialVersionUID = 3843152579337300847L;
	
	private Long id;
	// 驴妈妈订单号
	private Long lvmamaOrderId;
	// 合作伙伴订单号
	private String partnerOrderId;
	// 订单子子项编号
	private Long orderItemId;
	// 酒店供应商平台
	private Long hotelSupplierId;
	// 酒店供应商平台名称
	private String hotelSupplierName;
	// 状态码
	private String statusCode;
	// 状态描述
	private String statusName;
	// 创建时间
	private Date createTime;
	
	//游玩时间
	private Date visitDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLvmamaOrderId() {
		return lvmamaOrderId;
	}

	public void setLvmamaOrderId(Long lvmamaOrderId) {
		this.lvmamaOrderId = lvmamaOrderId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getHotelSupplierId() {
		return hotelSupplierId;
	}

	public void setHotelSupplierId(Long hotelSupplierId) {
		this.hotelSupplierId = hotelSupplierId;
	}

	public String getHotelSupplierName() {
		return hotelSupplierName;
	}

	public void setHotelSupplierName(String hotelSupplierName) {
		this.hotelSupplierName = hotelSupplierName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

}
