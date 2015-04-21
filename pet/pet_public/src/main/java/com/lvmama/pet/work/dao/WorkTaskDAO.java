/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.work.WorkTask;
/**
 * WorkTaskDAO,持久层类 用于WorkTask 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkTaskDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(WorkTask workTask) {
		return (Long)super.insert("WORK_TASK.insert", workTask);
	}
	/**
	 * 根据主键id查询
	 */
	public WorkTask getWorkTaskById(Long id) {
		return (WorkTask)super.queryForObject("WORK_TASK.getWorkTaskById", id);
	}
	/**
	 * 根据工单id查询
	 */
	public WorkTask getWorkTaskByWorkOrderId(Long workOrderId) {
		return (WorkTask)super.queryForObject("WORK_TASK.getWorkTaskByWorkOrderId", workOrderId);
	}
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<WorkTask> queryWorkTaskByParam(Map<String,Object> params) {
		return super.queryForList("WORK_TASK.queryWorkTaskByParam", params);
	}
	
	public Long getWorkTaskPageCount(Map<String, Object> params){
		return (Long)queryForObject("WORK_TASK.getWorkTaskPageCount",params);
	}
	@SuppressWarnings("unchecked")
	public List<WorkTask> getWorkTaskPage(Map<String, Object> params){
		return queryForList("WORK_TASK.getWorkTaskPage",params);
	}
	public int update(WorkTask workTask){
		return super.update("WORK_TASK.update",workTask);
	}
	@SuppressWarnings("unchecked")
	public List<WorkTask> getReciverOffLineTask(Long count){
		return queryForList("WORK_TASK.getReciverOffLineTask",count);
	}
	@SuppressWarnings("unchecked")
	public List<WorkTask> getReciverOffLineTaskByGroup(Map<String,Object> params){
		return queryForList("WORK_TASK.getReciverOffLineTaskByGroup",params);
	}
}
