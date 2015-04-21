package com.lvmama.pet.fin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinGLSubjectConfig;

/**
 * 科目配置数据访问层
 * 
 * @author taiqichao
 * 
 */
@Repository
public class FinGLSubjectConfigDAO extends BaseIbatisDAO {

	
	public void insert(FinGLSubjectConfig finGLSubjectConfig) {
		super.insert("FIN_GL_SUBJECT_CONFIG.insert", finGLSubjectConfig);
	}
	
	@SuppressWarnings("unchecked")
	public List<FinGLSubjectConfig> selectFinGLSubjectConfigByParamMap(Map<String, Object> paramMap) {
		return super.queryForList("FIN_GL_SUBJECT_CONFIG.selectFinGLSubjectConfigByParamMap",paramMap);
	}
	
	public int update(FinGLSubjectConfig finGLSubjectConfig) {
		return super.update("FIN_GL_SUBJECT_CONFIG.update", finGLSubjectConfig);
	}
	public int delete(Long subjectConfigId) {
		return super.delete("FIN_GL_SUBJECT_CONFIG.delete", subjectConfigId);
	}
	public Long selectRowCount(Map<String, Object> paramMap){
		return (Long)super.queryForObject("FIN_GL_SUBJECT_CONFIG.selectRowCount", paramMap);
	}
}
