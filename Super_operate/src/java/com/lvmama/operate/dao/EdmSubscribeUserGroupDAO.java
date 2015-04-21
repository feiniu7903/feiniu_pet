package com.lvmama.operate.dao;
/**
 * desc:EDM模板持久类
 * author:尚正元
 * createDate:20120207
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;

public class EdmSubscribeUserGroupDAO extends BaseIbatisDAO  {
	/**
	 * EDM模板SQL空间
	 */
	private static final String SQL_NAME_SPACE = "EDM_SUBSCRIBE_USER_GROUP.";
	/**
	 * 插入
	 */
	private static final String INSERT = SQL_NAME_SPACE + "insert";
	/**
	 * 修改
	 */
	private static final String UPDATE = SQL_NAME_SPACE + "update";
	/**
	 * 查询
	 */
	private static final String SEARCH = SQL_NAME_SPACE + "search";
	/**
	 * 查询总数
	 */
	private static final String COUNT  = SQL_NAME_SPACE + "count";
	
	
	/**
	 * 插入一条EDM模板
	 * @param object
	 * @return
	 */
	public Long insert(EdmSubscribeUserGroup object){
		 Object newKey = super.insert(INSERT,object);
		 return (Long)newKey;
	}
	
	/**
	 * 更新EDM模板
	 * @param object
	 * @return
	 */
	public int update(EdmSubscribeUserGroup object){
		int result = super.update(UPDATE, object);
		return result;
	}
	
	/**
	 * 根据条件查询EDM模板列表
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EdmSubscribeUserGroup> search(Map<String,Object> parameters){
		List<EdmSubscribeUserGroup> list = (List<EdmSubscribeUserGroup>) super.queryForList(SEARCH, parameters);
		return list;
	}
	
	/**
	 * 根据条件查询EDM模板总数
	 * @param parameters
	 * @return
	 */
	public Long count(Map<String,Object> parameters){
		Long result = (Long) super.queryForObject(COUNT,parameters);
		return result;
	}
}
