package com.lvmama.pet.fin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinGlSubjectCfg;
import com.lvmama.comm.pet.service.fin.FinGlSubjectCfgService;
import com.lvmama.pet.fin.dao.FinGLSubjectConfigDAO;
import com.lvmama.pet.fin.dao.FinGlSubjectCfgDAO;

/**
 * 科目配置服务实现
 * 
 * @author taiqichao
 * 
 */
@HessianService("finGlSubjectCfgService")
@Service("finGlSubjectCfgService")
public class FinGlSubjectCfgServiceImpl implements FinGlSubjectCfgService {

	@Autowired
	private FinGlSubjectCfgDAO finGlSubjectCfgDAO;
	
	@Autowired
	private FinGLSubjectConfigDAO finGLSubjectConfigDAO;

	@Override
	public void insertFinGlSubjectCfg(FinGlSubjectCfg finGlSubjectCfg) {
		finGlSubjectCfgDAO.insertFinGlSubjectCfgDO(finGlSubjectCfg);
	}

	@Override
	public void updateFinGlSubjectCfg(FinGlSubjectCfg finGlSubjectCfg) {
		finGlSubjectCfgDAO.updateFinGlSubjectCfgDO(finGlSubjectCfg);
	}

	@Override
	public void removeFinGlSubjectCfg(Long subjectConfigId) {
		finGlSubjectCfgDAO.deleteFinGlSubjectCfgDOByPrimaryKey(subjectConfigId);
	}

	@Override
	public FinGlSubjectCfg getFinGlSubjectCfg(Long subjectConfigId) {
		return finGlSubjectCfgDAO.findFinGlSubjectCfgDOByPrimaryKey(subjectConfigId);
	}

	@Override
	public List<FinGlSubjectCfg> findListByTerm(FinGlSubjectCfg finGlSubjectCfgDO) {
		return finGlSubjectCfgDAO.findListByTerm(finGlSubjectCfgDO);
	}

	@Override
	public List<FinGlSubjectCfg> queryForPage(Map<String, Object> parameters) {
		return finGlSubjectCfgDAO.query(parameters);
	}
	
	@Override
	public long queryCount(Map<String, Object> parameters){
		return finGlSubjectCfgDAO.queryCount(parameters);
	}


	@Override
	public void deleteAllCfg() {
		finGlSubjectCfgDAO.deleteAllFinGlSubjectCfg();
	}

}
