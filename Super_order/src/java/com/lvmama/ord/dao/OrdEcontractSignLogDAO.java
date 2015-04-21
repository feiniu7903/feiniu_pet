package com.lvmama.ord.dao;
/**
 * @author shangzhengyuan
 * @description 电子合同签约日志
 * @version 在线预售权
 * @time 20120727
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;

public class OrdEcontractSignLogDAO extends BaseIbatisDAO {
	private final static String SQL_SPACE = "ORD_ECONTRACT_SIGN_LOG.";
	private final static String INSERT = SQL_SPACE+"insert";
//	private final static String UPDATE = SQL_SPACE+"update";
	private final static String QUERY  = SQL_SPACE+"query";
	
	/**
	 * 插入电子合同签约日志记录
	 */
	public OrdEcontractSignLog insert(OrdEcontractSignLog object){
		Long id= (Long)super.insert(INSERT,object);
		object.setSignLogId(id);
		return object;
	}
	
	/**
	 * 根据条件查询签约日志列表
	 * @param parameters
	 * @return
	 */
	public List<OrdEcontractSignLog> query(Map<String,Object> parameters){
		List<OrdEcontractSignLog> queryForList = super.queryForList(QUERY,parameters);
		return queryForList;
	}
}
