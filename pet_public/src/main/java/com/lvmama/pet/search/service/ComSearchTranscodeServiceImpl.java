package com.lvmama.pet.search.service;

import java.util.Map;

import com.lvmama.comm.pet.service.search.ComSearchTranscodeService;
import com.lvmama.comm.search.vo.ComSearchTranscode;
import com.lvmama.pet.search.dao.ComSearchTranscodeDAO;

public class ComSearchTranscodeServiceImpl implements ComSearchTranscodeService {

	private ComSearchTranscodeDAO comSearchTranscodeDAO;

	@Override
	public Map<Long, String> searchAll() {
		return comSearchTranscodeDAO.searchAll();
	}
	@Override
	public ComSearchTranscode getSearchByParam(Map<String, Object> map) {
		return comSearchTranscodeDAO.getSearchByParam(map);
	}

	public void setComSearchTranscodeDAO(ComSearchTranscodeDAO comSearchTranscodeDAO) {
		this.comSearchTranscodeDAO = comSearchTranscodeDAO;
	}

	

}
