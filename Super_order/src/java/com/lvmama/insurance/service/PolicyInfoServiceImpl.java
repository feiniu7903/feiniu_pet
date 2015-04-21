package com.lvmama.insurance.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.insurance.dao.InsInsurantDAO;
import com.lvmama.insurance.dao.InsPolicyInfoDAO;

public class PolicyInfoServiceImpl implements PolicyInfoService {
	
	private InsPolicyInfoDAO insPolicyInfoDAO;
	private InsInsurantDAO insInsurantDAO;

	@Override
	public Long countInsPolicyInfo(Map<String,Object> parameters) {
		return insPolicyInfoDAO.countInsPolicyInfo(parameters);
	}
	
	@Override
	public List<InsPolicyInfo> query(Map<String,Object> parameters) {
		return insPolicyInfoDAO.queryInsPolicyInfo(parameters);
	}
	
	@Override
	public List<InsPolicyInfo> queryForReport(Map<String,Object> parameters) {
		return insPolicyInfoDAO.queryInsPolicyInfoForReport(parameters);
	}
	
	@Override
	public InsPolicyInfo insert(final InsPolicyInfo info, final List<InsInsurant> insurants) {
		InsPolicyInfo _info = insPolicyInfoDAO.insert(info);
		for (InsInsurant insurant : insurants) {
			insurant.setPolicyId(info.getPolicyId());
			insInsurantDAO.insert(insurant);
		}
		return _info;
	}
	
	@Override
	public InsPolicyInfo queryByPK(Serializable id) {
		return insPolicyInfoDAO.queryInsPolicyInfoByPK(id);
	}
	
	@Override
	public void update(InsPolicyInfo info) {
		insPolicyInfoDAO.update(info);
	}
	
	@Override
	public void deleteByPK(Serializable id) {
		insPolicyInfoDAO.deleteInsPolicyInfoByPK(id);
	}
	
	//setter and getter
	public InsPolicyInfoDAO getInsPolicyInfoDAO() {
		return insPolicyInfoDAO;
	}

	public void setInsPolicyInfoDAO(InsPolicyInfoDAO insPolicyInfoDAO) {
		this.insPolicyInfoDAO = insPolicyInfoDAO;
	}

	public InsInsurantDAO getInsInsurantDAO() {
		return insInsurantDAO;
	}

	public void setInsInsurantDAO(InsInsurantDAO insInsurantDAO) {
		this.insInsurantDAO = insInsurantDAO;
	}
	
}
