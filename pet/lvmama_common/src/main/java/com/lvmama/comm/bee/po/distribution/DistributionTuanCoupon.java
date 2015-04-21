package com.lvmama.comm.bee.po.distribution;
import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class DistributionTuanCoupon implements Serializable {
	private static final long serialVersionUID = -8011321352780923712L;
	private Long distributionCouponId;
	private String distributionCouponCode;
	private Long batchId;
	private Long orderId;
	private String status;
	private String productName;
	private String performStatus;
	private String productBranchName;
	private String orderStatus;
	private Date couponCodeValidTime;
	private String operatorName;
	private Date orderBatchCreatetime;
	private DistributorTuanInfo distributorTuanInfo = new DistributorTuanInfo();
	private DistributionTuanCouponBatch distributionTuanCouponBatch;

	public Long getDistributionCouponId() {
		return distributionCouponId;
	}
	public void setDistributionCouponId(Long distributionCouponId) {
		this.distributionCouponId = distributionCouponId;
	}
	public String getDistributionCouponCode() {
		return distributionCouponCode;
	}
	public void setDistributionCouponCode(String distributionCouponCode) {
		this.distributionCouponCode = distributionCouponCode;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return Constant.DISTRIBUTION_TUAN_COUPON_STATUS.getCnName(status);
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPerformStatus() {
		if(this.performStatus!=null){
			return DistributionTuanCoupon.getPerformStatusCnName(this.performStatus);
		}
		return "";
	}
	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}
	public DistributorTuanInfo getDistributorTuanInfo() {
		return distributorTuanInfo;
	}
	public void setDistributorTuanInfo(DistributorTuanInfo distributorTuanInfo) {
		this.distributorTuanInfo = distributorTuanInfo;
	}
	public String getProductBranchName() {
		return productBranchName;
	}
	public void setProductBranchName(String productBranchName) {
		this.productBranchName = productBranchName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = Constant.ORDER_STATUS.getCnName(orderStatus);
	}

	public DistributionTuanCouponBatch getDistributionTuanCouponBatch() {
		return distributionTuanCouponBatch;
	}
	public void setDistributionTuanCouponBatch(
			DistributionTuanCouponBatch distributionTuanCouponBatch) {
		this.distributionTuanCouponBatch = distributionTuanCouponBatch;
	}

	
	public boolean isVolid(){
		if(!Constant.DISTRIBUTION_TUAN_COUPON_STATUS.NORMAL.name().equalsIgnoreCase(status)){
			return false;
		}
		if(this.distributionTuanCouponBatch!=null && this.distributionTuanCouponBatch.getValidEndTime()!=null){
			if(new Date().after(DateUtil.dsDay_Date(this.distributionTuanCouponBatch.getValidEndTime(), 1))){
				this.status = Constant.DISTRIBUTION_TUAN_COUPON_STATUS.STALEDATED.name();
				return false;
			}
		}
		
		return true;
	}
	
	public Date getCouponCodeValidTime() {
		return couponCodeValidTime;
	}
	public void setCouponCodeValidTime(Date couponCodeValidTime) {
		this.couponCodeValidTime = couponCodeValidTime;
	}
	public Date getOrderBatchCreatetime() {
		return orderBatchCreatetime;
	}
	public void setOrderBatchCreatetime(Date orderBatchCreatetime) {
		this.orderBatchCreatetime = orderBatchCreatetime;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public static String getPerformStatusCnName(String performStatus){
		if(performStatus.equals(Constant.ORDER_PERFORM_STATUS.PERFORMED.name())){
			return "已履行";
		}else if(performStatus.equals(Constant.ORDER_PERFORM_STATUS.UNPERFORMED.name())){
			return "未履行"; 
		}else if(performStatus.equals(Constant.ORDER_PERFORM_STATUS.AUTOPERFORMED.name())){
			return "系统自动履行";
		}else{
			return "";
		}
	}
}
