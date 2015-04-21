package com.lvmama.comm.pet.service.search;

import java.util.Map;

import com.lvmama.comm.search.vo.ComSearchTranscode;

public interface ComSearchTranscodeService {

	public Map<Long,String> searchAll();

	/**
	 * @param map
	 * @return
	 */
	public ComSearchTranscode getSearchByParam(Map<String, Object> map);
}
