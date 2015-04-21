package com.lvmama.distribution.dao;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionRakeBack;

public class DistributionRakeBackDAO extends BaseIbatisDAO {

	public DistributionRakeBack selectByParams(Long productBranchId,
			Long distributorInfoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productBranchId", productBranchId);
		params.put("distributorInfoId", distributorInfoId);
		return (DistributionRakeBack) super.queryForObject(
				"DISTRIBUTION_RAKE_BACK.selectByParams", params);
	}

	public void insert(DistributionRakeBack distributionRakeBack) {
		super.insert("DISTRIBUTION_RAKE_BACK.insert", distributionRakeBack);
	}

	public void updateRakeBackRateByParams(Long productBranchId,
			Long distributorInfoId, Long rakeBackRate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productBranchId", productBranchId);
		params.put("distributorInfoId", distributorInfoId);
		params.put("rakeBackRate", rakeBackRate);
		super.update("DISTRIBUTION_RAKE_BACK.updateByParams", params);
	}
	
	public void update(DistributionRakeBack distributionRakeBack) {
		super.insert("DISTRIBUTION_RAKE_BACK.update", distributionRakeBack);
	}
}
