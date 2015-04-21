package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品设置时间优惠
 * 每天一笔记录
 * @author yuzhizeng
 *
 */
public class ProdCouponInterval implements Serializable {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = -5934068291814852018L;

	private Long prodCouponIntervalId;

	private Long productId;
	
	private Long branchId;
	
	private Date specDate;
	
	private Date beginTime;

	private Date endTime;

	//优惠类型('促')
	private String couponType;

	public Long getProdCouponIntervalId() {
		return prodCouponIntervalId;
	}

	public void setProdCouponIntervalId(Long prodCouponIntervalId) {
		this.prodCouponIntervalId = prodCouponIntervalId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Date getSpecDate() {
		return specDate;
	}

	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}
	 
}
