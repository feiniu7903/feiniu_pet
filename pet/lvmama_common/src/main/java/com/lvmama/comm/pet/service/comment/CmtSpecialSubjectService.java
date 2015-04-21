package com.lvmama.comm.pet.service.comment;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;

/**
 * 专题管理逻辑接口
 * @author yuzhizeng
 */
public interface CmtSpecialSubjectService {
	/**
	 * 查询
	 * @param parms 查询条件
	 * @return 返回列表
	 */
	List<CmtSpecialSubjectVO> query(Map<String, Object> params);
	
	/**
	 * 计数
	 * @param params 查询条件
	 * @return 总数
	 */
	Long count(Map<String, Object> params);
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 
	 */
	CmtSpecialSubjectVO queryByPk(Serializable id);
	
	/**
	 * 保存
	 * @param 
	 * @return 标识
	 * 如果待保存实体未有标识，则会新增；否则只做更新，并返回当前标识
	 */
	Long save(CmtSpecialSubjectVO cmtSpecialSubject);
	
}

