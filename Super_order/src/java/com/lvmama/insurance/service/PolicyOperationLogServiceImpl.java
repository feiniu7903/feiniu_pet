package com.lvmama.insurance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.insurance.PolicyOperationLogService;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;
import com.lvmama.insurance.dao.InsPolicyOperationLogDAO;

public class PolicyOperationLogServiceImpl implements PolicyOperationLogService {
	
	private InsPolicyOperationLogDAO insPolicyOperationLogDAO;
	
	@Override
	public void insert(InsPolicyOperationLog insPolicyOperationLog) {
		insPolicyOperationLogDAO.insert(insPolicyOperationLog);
	}

	@Override
	public List<InsPolicyOperationLog> queryLogByPolicyId(Long policyId) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("policyId", policyId);
		return insPolicyOperationLogDAO.query(parameters);
	}

	public InsPolicyOperationLogDAO getInsPolicyOperationLogDAO() {
		return insPolicyOperationLogDAO;
	}

	public void setInsPolicyOperationLogDAO(
			InsPolicyOperationLogDAO insPolicyOperationLogDAO) {
		this.insPolicyOperationLogDAO = insPolicyOperationLogDAO;
	}

}
