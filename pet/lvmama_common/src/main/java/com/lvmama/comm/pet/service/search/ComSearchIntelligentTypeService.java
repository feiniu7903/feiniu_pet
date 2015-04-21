package com.lvmama.comm.pet.service.search;

import com.lvmama.comm.search.vo.ComSearchIntelligentType;

public interface ComSearchIntelligentTypeService {
	
	public ComSearchIntelligentType searchByNames(String fromDestName,String toDestName);
	
}
