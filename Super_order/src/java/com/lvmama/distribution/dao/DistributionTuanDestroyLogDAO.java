package com.lvmama.distribution.dao;
import java.util.List;
import java.util.Map;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionTuanDestroyLog;

public class DistributionTuanDestroyLogDAO extends BaseIbatisDAO {
	
	public Long insert(DistributionTuanDestroyLog distributionTuanDestroyLog) {
		return (Long)super.insert("DISTRIBUTION_TUAN_DESTROY_LOG.insert", distributionTuanDestroyLog);
	}
	
	@SuppressWarnings("unchecked")
	public List<DistributionTuanDestroyLog> query(Map<String, Object> parameter){
		return this.queryForList("DISTRIBUTION_TUAN_DESTROY_LOG.queryDestroyCouponCodeByParam", parameter);
	}
	
	
	public Long queryCount(Map<String, Object> parameter) {
		return (Long) super.queryForObject("DISTRIBUTION_TUAN_DESTROY_LOG.selectByParamsCount",parameter);
	}

	public void update(DistributionTuanDestroyLog distributionTuanDestroyLog){
		super.update("DISTRIBUTION_TUAN_DESTROY_LOG.update", distributionTuanDestroyLog);
	}
	
	public void update(Map<String, Long> param) {
		super.update("DISTRIBUTION_TUAN_DESTROY_LOG.updateFileId", param);
	}

	public DistributionTuanDestroyLog find(Long lonId) {
		return (DistributionTuanDestroyLog)super.queryForObject("DISTRIBUTION_TUAN_DESTROY_LOG.find", lonId);
	}
}
