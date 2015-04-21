package com.lvmama.pet.comment.service;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.service.comment.CmtSpecialSubjectService;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;
import com.lvmama.pet.comment.dao.CmtSpecialSubjectDAO;

/**
 * 专题管理数据库接口
 * @author yuzhizeng
 */
class CmtSpecialSubjectServiceImpl implements CmtSpecialSubjectService {
	
	private CmtSpecialSubjectDAO cmtSpecialSubjectDAO;
	
	public List<CmtSpecialSubjectVO> query(Map<String, Object> params) {
		if (null == params) {
			return null;
		}
		return cmtSpecialSubjectDAO.query(params);
	}

	public Long count(Map<String, Object> params) {
		return cmtSpecialSubjectDAO.count(params);
	}

	public CmtSpecialSubjectVO queryByPk(Serializable id) {
		if (null == id) {
			return null;
		}
		return cmtSpecialSubjectDAO.queryByPk(id);
	}
	
	
	public Long save(final CmtSpecialSubjectVO cmtSpecialSubject) {
		if (null == cmtSpecialSubject) {
			return null;
		}
		if (null == cmtSpecialSubject.getId()) {
			cmtSpecialSubjectDAO.insert(cmtSpecialSubject);
		} else {
			cmtSpecialSubjectDAO.update(cmtSpecialSubject);
		}
		return cmtSpecialSubject.getId();
	}

	public void setCmtSpecialSubjectDAO(CmtSpecialSubjectDAO cmtSpecialSubjectDAO) {
		this.cmtSpecialSubjectDAO = cmtSpecialSubjectDAO;
	}
}
