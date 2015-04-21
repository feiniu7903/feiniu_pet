package com.lvmama.insurance.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;

public class InsPolicyOperationLogDAO extends BaseIbatisDAO {
	public InsPolicyOperationLog insert(final InsPolicyOperationLog log) {
		super.insert("INS_POLICY_OPERATION_LOG.insert", log);
		return log;
	}
	
	@SuppressWarnings("unchecked")
	public List<InsPolicyOperationLog> query(final Map<String,Object> parameters) {
		return super.queryForList("INS_POLICY_OPERATION_LOG.query", parameters);
	}
}
