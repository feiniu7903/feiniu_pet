package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.fin.FinGlSubjectCfg;

/**
 * 财务做账科目配置服务
 * 
 * @author taiqichao
 * 
 */
public interface FinGlSubjectCfgService {

	/**
	 * 新增科目配置
	 * 
	 * @param finGlSubjectCfg 科目配置信息
	 */
	void insertFinGlSubjectCfg(FinGlSubjectCfg finGlSubjectCfg);
	

	/**
	 * 修改科目配置
	 * 
	 * @param finGlSubjectCfg 科目配置信息
	 */
	void updateFinGlSubjectCfg(FinGlSubjectCfg finGlSubjectCfg);

	/**
	 * 删除科目配置
	 * 
	 * @param subjectConfigId 科目配置id
	 */
	void removeFinGlSubjectCfg(Long subjectConfigId);
	
	/**
	 * 获取科目配置
	 * @param subjectConfigId 科目配置id
	 * @return 科目配置信息
	 */
	FinGlSubjectCfg getFinGlSubjectCfg(Long subjectConfigId);
	
	
	/**
	 * 条件查询科目配置
	 * @param finGlSubjectCfgDO 条件封装
	 * @return 科目配置集合
	 */
	List<FinGlSubjectCfg> findListByTerm(FinGlSubjectCfg finGlSubjectCfgDO);
	
	
	/**
	 * 分页查询科目配置
	 * @param parameters 条件封装
	 * @return
	 */
	List<FinGlSubjectCfg> queryForPage(Map<String, Object> parameters);


	long queryCount(Map<String, Object> parameters);
	

	void deleteAllCfg();
		
	
}
