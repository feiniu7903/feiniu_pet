package com.lvmama.pet.fin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.fin.FinGLSubjectConfig;
import com.lvmama.comm.pet.service.fin.FinGLSubjectConfigService;
import com.lvmama.pet.fin.dao.FinGLSubjectConfigDAO;

public class FinGLSubjectConfigServiceImpl implements FinGLSubjectConfigService {
	private FinGLSubjectConfigDAO finGLSubjectConfigDAO;
	@Override
	public FinGLSubjectConfig insert(FinGLSubjectConfig finGLSubjectConfig) {
		finGLSubjectConfigDAO.insert(finGLSubjectConfig);
		return finGLSubjectConfig;
	}

	@Override
	public int update(FinGLSubjectConfig finGLSubjectConfig) {
		FinGLSubjectCodeCache.getInstance().clearConfig();
		return finGLSubjectConfigDAO.update(finGLSubjectConfig);
	}

	@Override
	public int delete(Long subjectConfigId) {
		FinGLSubjectCodeCache.getInstance().clearConfig();
		return finGLSubjectConfigDAO.delete(subjectConfigId);
	}

	@Override
	public List<FinGLSubjectConfig> selectFinGLSubjectConfigByParamMap(
			Map<String, Object> paramMap) {
		return finGLSubjectConfigDAO.selectFinGLSubjectConfigByParamMap(paramMap);
	}
	@Override
	public Long selectRowCount(
			Map<String, Object> paramMap) {
		return finGLSubjectConfigDAO.selectRowCount(paramMap);
	}
	@Override
	public FinGLSubjectConfig queryForKey(Long key) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("subjectConfigId", key);
		List<FinGLSubjectConfig> results =finGLSubjectConfigDAO.selectFinGLSubjectConfigByParamMap(paramMap);
		if(results==null)return null;
		else if(results.size()==0) return null;
		else return results.get(0);
	}

	public void setFinGLSubjectConfigDAO(FinGLSubjectConfigDAO finGLSubjectConfigDAO) {
		this.finGLSubjectConfigDAO = finGLSubjectConfigDAO;
	}

}
