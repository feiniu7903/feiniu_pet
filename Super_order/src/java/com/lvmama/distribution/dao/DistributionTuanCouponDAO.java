package com.lvmama.distribution.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;

public class DistributionTuanCouponDAO extends BaseIbatisDAO {
	
	/**
	 * 插入一条数据
	 * @param ordOrderDitribution
	 */
	public void insert(DistributionTuanCoupon distributionTuanCoupon) {
		super.insert("DISTRIBUTION_TUAN_COUPON.insert", distributionTuanCoupon);
	}

	/**
	 * 更新一条数据
	 * @param distributorInfo
	 */
	public int update(DistributionTuanCoupon distributionTuanCoupon){
		return super.update("DISTRIBUTION_TUAN_COUPON.update",distributionTuanCoupon);
	}
	
	public int logicalDelete(Map<String, Object> params){
		return super.update("DISTRIBUTION_TUAN_COUPON.logicalDelete",params);
	}
	
	public int activateCouponCode(Map<String, Object> parameter){
		return super.update("DISTRIBUTION_TUAN_COUPON.activateCouponCode",parameter);
	}
	
	public DistributionTuanCoupon queryCouponCodeByCouponId(Map<String, Object> param){
		return (DistributionTuanCoupon)super.queryForObject("DISTRIBUTION_TUAN_COUPON.queryCouponCodeByCouponId", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<DistributionTuanCoupon> query(Map<String, Object> parameterObject){
		return this.queryForList("DISTRIBUTION_TUAN_COUPON.queryCouponCodeByParam", parameterObject);
	}
	
	
	public Long queryCount(Map<String, Object> parameterObject) {
		return (Long) super.queryForObject("DISTRIBUTION_TUAN_COUPON.selectByParamsCount",parameterObject);
	}
	public int queryCount(Long batchId,String status) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("batchId", batchId);
		map.put("status", status);
		return (Integer) super.queryForObject("DISTRIBUTION_TUAN_COUPON.queryCount",map);
	}

	/**
	 * 根据券码状态查询权码信息
	 */
	public List<DistributionTuanCoupon> queryCanUseCodeInfo(Map<String, Object> parameterObject) {
		return super.queryForList("DISTRIBUTION_TUAN_COUPON.queryCanUseCodeInfo",parameterObject,true);
	}
	public List<String> queryAllCode() {
		return super.queryForListNoMax("DISTRIBUTION_TUAN_COUPON.queryAllCode",null);
	}
	
	@SuppressWarnings("unchecked")
	public List<DistributionTuanCoupon> queryCouponCodeList(Map<String, Object> parameterObject){
		return this.queryForList("DISTRIBUTION_TUAN_COUPON.exportCouponCodeByParam", parameterObject,true);
	}

	public DistributionTuanCoupon queryByCode(String couponCode) {
		return (DistributionTuanCoupon) super.queryForObject("DISTRIBUTION_TUAN_COUPON.queryByCode",couponCode);
	}
}
