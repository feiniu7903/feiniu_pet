package com.lvmama.pet.search.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.search.vo.ComSearchIntelligentType;

@Repository
public class ComSearchIntelligentTypeDAO extends BaseIbatisDAO {
	
	public ComSearchIntelligentType searchByNames(String fromDestName,String toDestName){
		Map map = new HashMap();
		map.put("fromDestName", fromDestName);
		map.put("toDestName", toDestName);
		return (ComSearchIntelligentType) this.queryForObject("COM_SEARCH_INTELLIGENT_TYPE.searchByNames",map);
	}
}
