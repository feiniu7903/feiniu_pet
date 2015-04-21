package com.lvmama.pet.search.service;

import com.lvmama.comm.pet.service.search.ComSearchIntelligentTypeService;
import com.lvmama.comm.search.vo.ComSearchIntelligentType;
import com.lvmama.pet.search.dao.ComSearchIntelligentTypeDAO;

public class ComSearchIntelligentTypeServiceImpl implements ComSearchIntelligentTypeService {

	private ComSearchIntelligentTypeDAO comSearchIntelligentTypeDAO;
	
	@Override
	public ComSearchIntelligentType searchByNames(String fromDestName, String toDestName) {
		return comSearchIntelligentTypeDAO.searchByNames(fromDestName, toDestName);
	}
	public void setComSearchIntelligentTypeDAO(ComSearchIntelligentTypeDAO comSearchIntelligentTypeDAO) {
		this.comSearchIntelligentTypeDAO = comSearchIntelligentTypeDAO;
	}

}
