package com.lvmama.comm.bee.service.distribution;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;

public interface DistributionTuanCouponService {

	public void insert(DistributionTuanCoupon distributionTuanCoupon);
	public int update(DistributionTuanCoupon distributionTuanCoupon);
	
	/**
	 * 条件查询分销商总数
	 */
	public Long queryCount(Map<String, Object> parameterObject);
	
	/**
	 * 通过渠道类型查商户信息
	 * @param serialNo
	 * @return
	 */
	public List<DistributionTuanCoupon> queryList(Map<String, Object> parameterObject);
	
	/**
	 * 根据券码状态查询权码信息
	 */
	public List<DistributionTuanCoupon> queryCanUseCodeInfo(Map<String, Object> parameterObject);
	
	public List<String> queryAllCode();
	
	public DistributionTuanCoupon queryCouponCodeBycouponId(Map<String, Object> param);
	
	public int logicalDelete(Map<String, Object> parameterObject);
	
	public int activateCouponCode(Map<String, Object> parameterObject);
	
	public List<DistributionTuanCoupon> queryCouponCodeList(Map<String, Object> parameterObject);

	public DistributionTuanCoupon queryByCode(String couponCode);

}
