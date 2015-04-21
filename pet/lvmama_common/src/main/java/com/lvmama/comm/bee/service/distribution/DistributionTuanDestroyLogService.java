package com.lvmama.comm.bee.service.distribution;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionTuanDestroyLog;

public interface DistributionTuanDestroyLogService {
	public Long insert(DistributionTuanDestroyLog distributionTuanDestroyLog);
	
	public Long queryCount(Map<String, Object> parameterObject);
	
	public DistributionTuanDestroyLog find(Long lonId);
	
	public List<DistributionTuanDestroyLog> queryList(Map<String, Object> parameterObject);

	public void updatePristineId(Long logId, Long pristineId);

	public void updateErrorId(Long logId, Long errorId);
	
	public void update(DistributionTuanDestroyLog distributionTuanDestroyLog);
}
