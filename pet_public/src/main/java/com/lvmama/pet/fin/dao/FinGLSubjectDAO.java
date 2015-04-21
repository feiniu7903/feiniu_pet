package com.lvmama.pet.fin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinGLSubject;

/**
 * 科目数据访问层
 * 
 * @author taiqichao
 * 
 */
@Repository
public class FinGLSubjectDAO extends BaseIbatisDAO {

	/**
	 * 查询指定父科目下所有的子科目
	 * 
	 * @param parentSubjectCode
	 *            父科目code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FinGLSubject> querySubjectsByParentCode(String parentSubjectCode) {
		return super.queryForList("FIN_GL_SUBJECT.querySubjectsByParentCode");
	}

}
