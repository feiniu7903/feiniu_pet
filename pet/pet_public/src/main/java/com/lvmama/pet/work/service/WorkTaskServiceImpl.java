/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.work.WorkTaskService;
import com.lvmama.pet.work.dao.WorkTaskDAO;

/**
 * WorkTask 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkTaskServiceImpl implements WorkTaskService{
	@Autowired
	private WorkTaskDAO workTaskDAO;
	@Override
	public Long insert(WorkTask workTask) {
		return workTaskDAO.insert(workTask);
	}
	@Override
	public WorkTask getWorkTaskById(Long id) {
		return workTaskDAO.getWorkTaskById(id);
	}
	public WorkTask getWorkTaskByWorkOrderId(Long workOrderId) {
		return workTaskDAO.getWorkTaskByWorkOrderId(workOrderId);
	}
	public Long getWorkTaskPageCount(Map<String, Object> params){
		return workTaskDAO.getWorkTaskPageCount(params);
	}
	public List<WorkTask> getWorkTaskPage(Map<String, Object> params){
		return workTaskDAO.getWorkTaskPage(params);
	}
	@Override
	public List<WorkTask> queryWorkTaskByParam(Map<String, Object> params) {
		return workTaskDAO.queryWorkTaskByParam(params);
	}
	@Override
	public int update(WorkTask workTask) {
		return workTaskDAO.update(workTask);
	}
	@Override
	public List<WorkTask> getReciverOffLineTask(Long count) {
		return workTaskDAO.getReciverOffLineTask(count);
	}
	@Override
	public List<WorkTask> getReciverOffLineTaskByGroup(Map<String, Object> params) {
		return workTaskDAO.getReciverOffLineTaskByGroup(params);
	}
}
