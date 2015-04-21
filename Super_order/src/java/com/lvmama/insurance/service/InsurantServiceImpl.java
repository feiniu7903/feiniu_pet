package com.lvmama.insurance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.InsurantService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.insurance.dao.InsInsurantDAO;

class InsurantServiceImpl implements InsurantService {
	private InsInsurantDAO insInsurantDAO;
	
	@Override
	public void insert(InsInsurant insurant) {
		insInsurantDAO.insert(insurant);
	}

	@Override
	public void update(InsInsurant insurant) {
		insInsurantDAO.update(insurant);
	}
	
	@Override
	public void deleteByPK(Long insurantId) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("insurantId", insurantId);
		insInsurantDAO.delete(parameters);
	}

	@Override
	public List<InsInsurant> query(Map<String, Object> parameters) {
		return insInsurantDAO.queryInsInsurant(parameters);
	}
	
	@Override
	public InsInsurant queryInsurantByPK(Long insurantId) {
		return insInsurantDAO.queryInsInsurantByPK(insurantId);
	}
	
	@Override
	public List<InsInsurant> queryInsurantsByPolicyId(Long policyId){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("policyId", policyId);
		return query(parameters);
	}

	public InsInsurantDAO getInsInsurantDAO() {
		return insInsurantDAO;
	}

	public void setInsInsurantDAO(InsInsurantDAO insInsurantDAO) {
		this.insInsurantDAO = insInsurantDAO;
	}

}
