package com.lvmama.pet.search.dao;

import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.search.vo.ComSearchTranscode;

public class ComSearchTranscodeDAO  extends BaseIbatisDAO {

	public Map<Long,String> searchAll(){
		return queryForMap("COM_SEARCH_TRANSCODE.searchAll", null, "codeId", "keyword");
	}
	public  ComSearchTranscode getSearchByParam(Map<String, Object> map){
		return (ComSearchTranscode) super.queryForObject("COM_SEARCH_TRANSCODE.getSearchByParam",map);
	}
}
