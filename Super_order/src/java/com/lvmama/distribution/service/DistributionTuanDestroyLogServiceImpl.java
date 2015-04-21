package com.lvmama.distribution.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionTuanDestroyLog;
import com.lvmama.comm.bee.service.distribution.DistributionTuanDestroyLogService;
import com.lvmama.distribution.dao.DistributionTuanDestroyLogDAO;

public class DistributionTuanDestroyLogServiceImpl implements DistributionTuanDestroyLogService{

	private DistributionTuanDestroyLogDAO distributionTuanDestroyLogDAO;
	@Override
	public Long insert(DistributionTuanDestroyLog distributionTuanDestroyLog) {
		return distributionTuanDestroyLogDAO.insert(distributionTuanDestroyLog);
	}

	public DistributionTuanDestroyLog find(Long lonId){
		return distributionTuanDestroyLogDAO.find(lonId);
	}
	
	@Override
	public Long queryCount(Map<String, Object> parameterObject) {
		return distributionTuanDestroyLogDAO.queryCount(parameterObject);
	}

	@Override
	public List<DistributionTuanDestroyLog> queryList(
			Map<String, Object> parameterObject) {
		return distributionTuanDestroyLogDAO.query(parameterObject);
	}

	public void setDistributionTuanDestroyLogDAO(
			DistributionTuanDestroyLogDAO distributionTuanDestroyLogDAO) {
		this.distributionTuanDestroyLogDAO = distributionTuanDestroyLogDAO;
	}

	public void update(DistributionTuanDestroyLog distributionTuanDestroyLog){
		distributionTuanDestroyLogDAO.update(distributionTuanDestroyLog);
	}
	public void updatePristineId(Long logId, Long pristineId){
		Map<String,Long> param = new  HashMap<String,Long>();
		param.put("logId", logId);
		param.put("pristineId", pristineId);
		distributionTuanDestroyLogDAO.update(param);
	}
	
	public void updateErrorId(Long logId, Long errorId){
		Map<String,Long> param = new  HashMap<String,Long>();
		param.put("logId", logId);
		param.put("errorId", errorId);
		distributionTuanDestroyLogDAO.update(param);
	}
}
