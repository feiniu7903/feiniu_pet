package com.lvmama.comm.bee.service.distribution;

import com.lvmama.comm.bee.po.distribution.DistributionRakeBack;

public interface DistributionRakeBackService {

	/**
	 * 保存返佣点
	 * */
	public void saveDistributionRakeBack(Long productBranchId,
			Long distributorInfoId, Long rakeBackRate);

	/**
	 * 保存或更新返佣点
	 * */
	public void save(DistributionRakeBack drb);
	
	/**
	 * 获取返佣点信息
	 * */
	public DistributionRakeBack queryDistributionRakeBack(Long productBranchId,
			Long distributorInfoId);

	/**
	 * 更新返佣点
	 * */
	public void updateRakeBack(Long productBranchId, Long distributorInfoId,
			Long rakeBackRate);
	
	/**
	 * 根据订单id获取返佣点
	 * */
	public Float getRakeBackRatebyOrderId(Long orderId);
}
