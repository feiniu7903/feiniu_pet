package com.lvmama.distribution.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCouponBatch;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponBatchService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.dao.DistributionTuanCouponBatchDAO;
import com.lvmama.distribution.dao.DistributionTuanCouponDAO;

public class DistributionTuanCouponBatchServiceImpl implements
		DistributionTuanCouponBatchService {
	
	private DistributionTuanCouponBatchDAO distributionTuanCouponBatchDAO;
	private DistributionTuanCouponDAO distributionTuanCouponDAO;
	
	@Override
	public Long insert(DistributionTuanCouponBatch distributionTuanCouponBatch) {
		return distributionTuanCouponBatchDAO.insert(distributionTuanCouponBatch);

	}

	@Override
	public void update(DistributionTuanCouponBatch distributionTuanCouponBatch) {
		distributionTuanCouponBatchDAO.update(distributionTuanCouponBatch);

	}

	public DistributionTuanCouponBatch find(Long distributionBatchId){
		return distributionTuanCouponBatchDAO.find(distributionBatchId);
	}
	
	@Override
	public Long queryCount(Map<String, Object> map) {
		return distributionTuanCouponBatchDAO.queryCount(map);
	}

	@Override
	public List<DistributionTuanCouponBatch> queryList(Map<String, Object> map) {
		List<DistributionTuanCouponBatch> list = distributionTuanCouponBatchDAO.queryByParams(map);
		if(list!=null){
			for(DistributionTuanCouponBatch batch: list){
				batch.setUsedCount(distributionTuanCouponDAO.queryCount(batch.getDistributionBatchId(),Constant.DISTRIBUTION_TUAN_COUPON_STATUS.USED.name()));
				batch.setCanceledCount(distributionTuanCouponDAO.queryCount(batch.getDistributionBatchId(),Constant.DISTRIBUTION_TUAN_COUPON_STATUS.DESTROYED.name()));
			}
		}
		return list;
	}

	public List<DistributionTuanCouponBatch> queryTuanCodeByBatchId(Long distributionBatchId){
		List<DistributionTuanCouponBatch> list = distributionTuanCouponBatchDAO.queryTuanCodeByBatchId(distributionBatchId);
		return list;
	}
	public void setDistributionTuanCouponBatchDAO(
			DistributionTuanCouponBatchDAO distributionTuanCouponBatchDAO) {
		this.distributionTuanCouponBatchDAO = distributionTuanCouponBatchDAO;
	}

	public void setDistributionTuanCouponDAO(
			DistributionTuanCouponDAO distributionTuanCouponDAO) {
		this.distributionTuanCouponDAO = distributionTuanCouponDAO;
	}

	
}
