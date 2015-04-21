package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

/**
 * 分销产品返佣表
 * 
 * @author zhangwengang
 * 
 */
public class DistributionRakeBack implements Serializable {
	private static final long serialVersionUID = 5859273289544354715L;
	private Long distributionProdRakebackId;
	private Long productBranchId;
	private Long distributorInfoId;
	private Long rakeBackRate;
	private String rateVolid;

	public Long getDistributionProdRakebackId() {
		return distributionProdRakebackId;
	}

	public void setDistributionProdRakebackId(Long distributionProdRakebackId) {
		this.distributionProdRakebackId = distributionProdRakebackId;
	}

	public Long getProductBranchId() {
		return productBranchId;
	}

	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}

	public Long getDistributorInfoId() {
		return distributorInfoId;
	}

	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}

	public Long getRakeBackRate() {
		return rakeBackRate;
	}

	public void setRakeBackRate(Long rakeBackRate) {
		this.rakeBackRate = rakeBackRate;
	}

	public boolean isRateVolid(){
		return rateVolid.equals(Constant.TRUE_FALSE.TRUE.getValue());
	}
	
	public String getRateVolid() {
		return rateVolid;
	}

	public void setRateVolid(String rateVolid) {
		this.rateVolid = rateVolid;
	}
	
	

}
