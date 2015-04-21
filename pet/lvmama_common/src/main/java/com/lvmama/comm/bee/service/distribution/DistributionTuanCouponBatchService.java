package com.lvmama.comm.bee.service.distribution;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCouponBatch;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;

public interface DistributionTuanCouponBatchService {

	public Long insert(DistributionTuanCouponBatch distributionTuanCouponBatch);
	public void update(DistributionTuanCouponBatch distributionTuanCouponBatch);
	public DistributionTuanCouponBatch find(Long distributionBatchId);
	
	
	/**
	 * 统计查询_条件查询
	 */
	public Long queryCount(Map<String, Object> parameterObject);
	
	/**
	 * 带分页查询
	 * @param serialNo
	 * @return
	 */
	public List<DistributionTuanCouponBatch> queryList(Map<String, Object> parameterObject);
	public List<DistributionTuanCouponBatch> queryTuanCodeByBatchId(Long distributionBatchId);
}
