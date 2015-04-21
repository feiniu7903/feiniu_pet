package com.lvmama.distribution.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCouponBatch;

public class DistributionTuanCouponBatchDAO extends BaseIbatisDAO {

	/**
	 * 插入一条数据
	 * @param ordOrderDitribution
	 */
	public Long insert(DistributionTuanCouponBatch distributorInfoTuanCouponBatch) {
		return (Long)super.insert("DISTRIBUTION_TUAN_COUPON_BATCH.insert", distributorInfoTuanCouponBatch);
	}
	/**
	 * 更新一条数据
	 * @param distributorInfo
	 */
	public void update(DistributionTuanCouponBatch distributorInfoTuanCouponBatch){
		super.update("DISTRIBUTION_TUAN_COUPON_BATCH.update",distributorInfoTuanCouponBatch);
	}
	
	/**
	 * 查询一条数据
	 */
	public DistributionTuanCouponBatch find(Long distributionBatchId){
		return (DistributionTuanCouponBatch)this.queryForObject("DISTRIBUTION_TUAN_COUPON_BATCH.find", distributionBatchId);
	}
	
	/**
	 * 条件查询批次信息
	 */
	@SuppressWarnings("unchecked")
	public List<DistributionTuanCouponBatch> queryByParams(Map<String, Object> parameterObject){
		return this.queryForList("DISTRIBUTION_TUAN_COUPON_BATCH.query", parameterObject);
	}
	
	/**
	 * 条件统计批次信息
	 */
	public Long queryCount(Map<String, Object> parameterObject) {
		return (Long) super.queryForObject("DISTRIBUTION_TUAN_COUPON_BATCH.queryCount",parameterObject);
	}
	
	public List<DistributionTuanCouponBatch> queryTuanCodeByBatchId(
			Long distributionBatchId) {
		return this.queryForList("DISTRIBUTION_TUAN_COUPON_BATCH.queryTuanCodeByBatchId", distributionBatchId);
	}
	public DistributionTuanCouponBatch getTuanCouponByCode(String tuanCode) {
		return (DistributionTuanCouponBatch)this.queryForObject("DISTRIBUTION_TUAN_COUPON_BATCH.queryTuanCouponByCode", tuanCode);
	}
}
