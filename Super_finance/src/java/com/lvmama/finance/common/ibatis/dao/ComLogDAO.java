package com.lvmama.finance.common.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.BaseDAO;
import com.lvmama.finance.common.ibatis.po.ComLog;

/**
 * 系统日志
 * 
 * @author yanggan
 *
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ComLogDAO extends BaseDAO {
	
	public void insertLog(ComLog log){
		this.insert("COMLOG.insert",log);
	}
	
	public List<ComLog> searchLog(Long objectId,String objectType){
		Map map = new HashMap();
		map.put("objectId", objectId);
		map.put("objectType",objectType);
		return this.queryForList("COMLOG.search",map);
	}
}
