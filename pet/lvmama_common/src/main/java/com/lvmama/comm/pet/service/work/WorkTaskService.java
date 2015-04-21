/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.service.work;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.work.WorkTask;
/**
 * WorkTask 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public interface WorkTaskService{
	/**
	 * 持久化对象
	 * @param workTask
	 * @return
	 */
	public Long insert(WorkTask workTask);
	/**
	 * 根据主键id查询
	 */
	public WorkTask getWorkTaskById(Long id);
	public WorkTask getWorkTaskByWorkOrderId(Long workOrderId);
	
	public Long getWorkTaskPageCount(Map<String, Object> params);
	
	public List<WorkTask> getWorkTaskPage(Map<String, Object> params);
	
	List<WorkTask> queryWorkTaskByParam(Map<String, Object> params);
	
	int update(WorkTask workTask);
	/**
	 * 获取需要重新分配订单
	 * @param count 一次获取数量
	 * @return
	 */
	List<WorkTask> getReciverOffLineTask(Long count);
	/**
	 * 根据组织查询用户不在线且未处理任务
	 * @param params {workGroupId,count}
	 * @return
	 */
	public List<WorkTask> getReciverOffLineTaskByGroup(Map<String,Object> params);
}
