package com.lvmama.comm.bee.service.insurance;

import java.util.List;

import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;

public interface PolicyOperationLogService {
	/**
	 * 新增日志
	 * @param insPolicyOperationLog
	 */
	void insert(InsPolicyOperationLog insPolicyOperationLog);
	
	/**
	 * 根据保单号查询日志
	 * @param policyId
	 * @return
	 */
	List<InsPolicyOperationLog> queryLogByPolicyId(Long policyId);
	
}
