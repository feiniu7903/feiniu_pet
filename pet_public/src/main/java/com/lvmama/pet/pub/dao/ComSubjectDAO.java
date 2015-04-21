package com.lvmama.pet.pub.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComSubject;

public class ComSubjectDAO extends BaseIbatisDAO {

	@SuppressWarnings("unchecked")
	public List<ComSubject> findComSubjects(ComSubject comSubject, int count) {
		return super.queryForList("COM_SUBJECT.queryPcSubjects", comSubject);
	}

	public ComSubject getComSubjectById(ComSubject comSubject) {
		return (ComSubject) super.queryForObject("COM_SUBJECT.selectByPrimaryKey", comSubject);
	}

	public void deleteComSubject(ComSubject comSubject) {
		super.delete("COM_SUBJECT.deleteByPrimaryKey", comSubject);
	}

	public void saveOrUpdateComSubject(ComSubject comSubject) {
		if(comSubject.getComSubjectId() == null) {
			super.insert("COM_SUBJECT.insert", comSubject);
		} else {
			super.update("COM_SUBJECT.update", comSubject);
		}
	}
	
	public void updateUsedCount(String subjectName) {
		super.update("COM_SUBJECT.updateUsedCount", subjectName);
	}
	
	public List<ComSubject> querySubjectListByType(Map<String,Object> param){
		return (List<ComSubject>)super.queryForList("COM_SUBJECT.querySubjectListByType",param);
	}

}