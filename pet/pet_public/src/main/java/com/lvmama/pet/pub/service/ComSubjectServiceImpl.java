package com.lvmama.pet.pub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pub.ComSubject;
import com.lvmama.comm.pet.service.pub.ComSubjectService;
import com.lvmama.pet.pub.dao.ComSubjectDAO;

class ComSubjectServiceImpl implements ComSubjectService {
	private ComSubjectDAO comSubjectDAO;

	public void setComSubjectDAO(ComSubjectDAO comSubjectDAO) {
		this.comSubjectDAO = comSubjectDAO;
	}

	@Override
	public List<ComSubject> findComSubjects(ComSubject comSubject, int count) {
		return comSubjectDAO.findComSubjects(comSubject, count);
	}

	@Override
	public ComSubject getComSubjectById(ComSubject comSubject) {
		return comSubjectDAO.getComSubjectById(comSubject);
	}

	@Override
	public void deleteComSubject(ComSubject comSubject) {
		comSubjectDAO.deleteComSubject(comSubject);
	}

	@Override
	public void saveOrUpdateComSubject(ComSubject comSubject) {
		comSubjectDAO.saveOrUpdateComSubject(comSubject);
	}

	@Override
	public void updateSeqs(String subjectSeqs) {
		if(StringUtils.isNotBlank(subjectSeqs)){
			ComSubject comSubject;
			String[] items=subjectSeqs.split(",");
			if(items.length>0){
				for(String item:items){
					String[] sub=item.split("_");
					comSubject=new ComSubject();
					comSubject.setComSubjectId(Long.valueOf(sub[0]));
					comSubject.setSeq(Long.valueOf(sub[1]));
					comSubjectDAO.saveOrUpdateComSubject(comSubject);
				}
			}
		}
	}

	@Override
	public void updateUsedCount(String subjectName) {
		comSubjectDAO.updateUsedCount(subjectName);
	}

	@Override
	public List<ComSubject> querySubjectListByType(String subjectType,int count) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("subjectType", subjectType);
		param.put("maxResults", count);
		return comSubjectDAO.querySubjectListByType(param);
	}


}
