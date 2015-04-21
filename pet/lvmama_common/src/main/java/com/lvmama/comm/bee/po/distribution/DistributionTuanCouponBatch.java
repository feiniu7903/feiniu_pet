
package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;
import java.util.Date;

public class DistributionTuanCouponBatch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3622674433206212439L;
	
	private Long distributionBatchId;
	private Long productId;
	private Long branchId;
	private Long ordBatchCount;
	private Long distributorTuanInfoId;
	private Date validEndTime;
	private Long orderBatchCreator;
	private Date orderBatchCreatetime;
	private String operatorName;
	
	
	private String productName;
	private String branchName;
	private String distributorTuanInfoName;
	private Date startTime;
	private Date endTime;
	private int usedCount;
	private int canceledCount;
	private String productViewUrl;
	private String tuanCode;
	private DistributionTuanCoupon distributionTuanCoupon;

	public Long getDistributionBatchId() {
		return distributionBatchId;
	}

	public void setDistributionBatchId(Long distributionBatchId) {
		this.distributionBatchId = distributionBatchId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getOrdBatchCount() {
		return ordBatchCount;
	}

	public void setOrdBatchCount(Long ordBatchCount) {
		this.ordBatchCount = ordBatchCount;
	}

	public Long getDistributorTuanInfoId() {
		return distributorTuanInfoId;
	}

	public void setDistributorTuanInfoId(Long distributorTuanInfoId) {
		this.distributorTuanInfoId = distributorTuanInfoId;
	}

	public Date getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	public Long getOrderBatchCreator() {
		return orderBatchCreator;
	}

	public void setOrderBatchCreator(Long orderBatchCreator) {
		this.orderBatchCreator = orderBatchCreator;
	}

	public Date getOrderBatchCreatetime() {
		return orderBatchCreatetime;
	}

	public void setOrderBatchCreatetime(Date orderBatchCreatetime) {
		this.orderBatchCreatetime = orderBatchCreatetime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDistributorTuanInfoName() {
		return distributorTuanInfoName;
	}

	public void setDistributorTuanInfoName(String distributorTuanInfoName) {
		this.distributorTuanInfoName = distributorTuanInfoName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(int usedCount) {
		this.usedCount = usedCount;
	}

	public int getCanceledCount() {
		return canceledCount;
	}

	public void setCanceledCount(int canceledCount) {
		this.canceledCount = canceledCount;
	}

	public String getProductViewUrl() {
		return productViewUrl;
	}

	public void setProductViewUrl(String productViewUrl) {
		this.productViewUrl = productViewUrl;
	}

	public String getTuanCode() {
		return tuanCode;
	}

	public void setTuanCode(String tuanCode) {
		this.tuanCode = tuanCode;
	}

	public DistributionTuanCoupon getDistributionTuanCoupon() {
		return distributionTuanCoupon;
	}

	public void setDistributionTuanCoupon(
			DistributionTuanCoupon distributionTuanCoupon) {
		this.distributionTuanCoupon = distributionTuanCoupon;
	}
	
	
	
	
}
