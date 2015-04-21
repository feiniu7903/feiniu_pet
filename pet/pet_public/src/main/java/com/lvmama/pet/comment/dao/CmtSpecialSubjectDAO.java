package com.lvmama.pet.comment.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;

/**
 * 点评专题逻辑实现类
 * @author yuzhizeng
 * */
public class CmtSpecialSubjectDAO extends BaseIbatisDAO{

	@SuppressWarnings("unchecked")
	public List<CmtSpecialSubjectVO> query(final Map<String, Object> params) {
		return super.queryForList("CMT_SPECIAL_SUBJECT.query", params);
	}

	public Long count(final Map<String, Object> params) {
		return (Long) super.queryForObject("CMT_SPECIAL_SUBJECT.count", params);
	}

	public CmtSpecialSubjectVO queryByPk(final Serializable id) {
		if (null == id) {
			return null;
		}
		return (CmtSpecialSubjectVO) super.queryForObject("CMT_SPECIAL_SUBJECT.queryByPk", id);
	}

	public Long insert(final CmtSpecialSubjectVO cmtSpecialSubjectVO) {
		super.insert("CMT_SPECIAL_SUBJECT.insert", cmtSpecialSubjectVO);
		return cmtSpecialSubjectVO.getId();
    }

	public Long update(final CmtSpecialSubjectVO cmtSpecialSubjectVO) {
		super.update("CMT_SPECIAL_SUBJECT.update", cmtSpecialSubjectVO);
		return cmtSpecialSubjectVO.getId();
	}

}
