/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.work.WorkGroup;
/**
 * WorkGroupDAO,持久层类 用于WorkGroup 表的CRUD.
 * @author ruanxiequan
 * @update dingming
 * @version 1.0
 * @since 1.0
 */

public class WorkGroupDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(WorkGroup workGroup) {
		return (Long)super.insert("WORK_GROUP.insert", workGroup);
	}
	/**
	 * 根据主键id查询
	 */
	public WorkGroup getWorkGroupById(Long id) {
		return (WorkGroup)super.queryForObject("WORK_GROUP.getWorkGroupById", id);
	}
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<WorkGroup> queryWorkGroupByParam(Map<String,Object> params) {
		return super.queryForList("WORK_GROUP.queryWorkGroupByParam", params);
	}
	/**
	 * 查询所有的组织名称
	 * @return 组织集合
	 */
	public List<WorkGroup> queryWorkGroupName(){
		return super.queryForList("WORK_GROUP.queryWorkGroupName");
	}
	/**
	 * 更新组织信息
	 * @param workGroup
	 * @return
	 */
	public int updateWorkGroup(WorkGroup workGroup){
		return super.update("WORK_GROUP.update", workGroup);
	}
	
	/**
	 * 分页总条数
	 */
	public Long getWorkGroupPageCount(Map<String, Object> params){
		return (Long)queryForObject("WORK_GROUP.getWorkGroupPageCount", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkGroup> getWorkGroupPage(Map<String, Object> params){
		return queryForList("WORK_GROUP.getWorkGroupPage", params);
	}
	@SuppressWarnings("unchecked")
	public List<WorkGroup> getWorkGroupWithDepartment(Map<String, Object> params){
		return queryForList("WORK_GROUP.getWorkGroupWithDepartment", params);
	}
}
