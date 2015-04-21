package com.lvmama.distribution.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponService;
import com.lvmama.distribution.dao.DistributionTuanCouponBatchDAO;
import com.lvmama.distribution.dao.DistributionTuanCouponDAO;

public class DistributionTuanCouponServiceImpl implements
		DistributionTuanCouponService {

	private DistributionTuanCouponDAO distributionTuanCouponDAO;
	private DistributionTuanCouponBatchDAO distributionTuanCouponBatchDAO;
	@Override
	public void insert(DistributionTuanCoupon distributionTuanCoupon) {
		if(distributionTuanCoupon!=null && distributionTuanCoupon.getDistributionCouponId()!=null){
			distributionTuanCouponDAO.update(distributionTuanCoupon);
		}else{
			distributionTuanCouponDAO.insert(distributionTuanCoupon);
		}

	}

	@Override
	public int update(DistributionTuanCoupon distributionTuanCoupon) {
		return distributionTuanCouponDAO.update(distributionTuanCoupon);

	}

	@Override
	public Long queryCount(Map<String, Object> parameterObject) {
		return distributionTuanCouponDAO.queryCount(parameterObject);
	}
	
	@Override
	public DistributionTuanCoupon queryByCode(String couponCode) {
		DistributionTuanCoupon coup = distributionTuanCouponDAO.queryByCode(couponCode);
		if(coup!=null && coup.getBatchId()!=null){
			coup.setDistributionTuanCouponBatch(distributionTuanCouponBatchDAO.find(coup.getBatchId()));
		}
		return coup;
	}

	@Override
	public List<DistributionTuanCoupon> queryList(
			Map<String, Object> parameterObject) {
		return distributionTuanCouponDAO.query(parameterObject);
	}
	
	public List<DistributionTuanCoupon> queryCanUseCodeInfo(Map<String, Object> parameterObject){
		return distributionTuanCouponDAO.queryCanUseCodeInfo(parameterObject);
	}

	public void setDistributionTuanCouponDAO(
			DistributionTuanCouponDAO distributionTuanCouponDao) {
		distributionTuanCouponDAO = distributionTuanCouponDao;
	}
	
	
	public void setDistributionTuanCouponBatchDAO(
			DistributionTuanCouponBatchDAO distributionTuanCouponBatchDAO) {
		this.distributionTuanCouponBatchDAO = distributionTuanCouponBatchDAO;
	}

	public List<String> queryAllCode(){
		return distributionTuanCouponDAO.queryAllCode();
	}

	@Override
	public DistributionTuanCoupon queryCouponCodeBycouponId(Map<String, Object> param) {
		return (DistributionTuanCoupon)distributionTuanCouponDAO.queryCouponCodeByCouponId(param);
	}

	@Override
	public int logicalDelete(Map<String, Object> parameterObject) {
		return distributionTuanCouponDAO.logicalDelete(parameterObject);
	}

	@Override
	public int activateCouponCode(Map<String, Object> parameterObject) {
		return distributionTuanCouponDAO.activateCouponCode(parameterObject);
	}

	@Override
	public List<DistributionTuanCoupon> queryCouponCodeList(
			Map<String, Object> parameterObject) {
		return distributionTuanCouponDAO.queryCouponCodeList(parameterObject);
	}
	
}
