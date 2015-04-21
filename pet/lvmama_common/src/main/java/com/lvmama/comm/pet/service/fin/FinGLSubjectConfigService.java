package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.fin.FinGLSubjectConfig;

public interface FinGLSubjectConfigService {
	FinGLSubjectConfig insert(FinGLSubjectConfig finGLSubjectConfig);
	int update(FinGLSubjectConfig finGLSubjectConfig);
	int delete(Long subjectConfigId);
	List<FinGLSubjectConfig> selectFinGLSubjectConfigByParamMap(Map<String, Object> paramMap);
	Long selectRowCount(Map<String, Object> paramMap);
	FinGLSubjectConfig queryForKey(Long key);
}
