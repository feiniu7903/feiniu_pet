package com.lvmama.operate.service;

/**
 * @version 1.0
 * @author shangzhengyuan
 * EDM任务服务接口
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeBatchJob;
import com.lvmama.comm.pet.po.edm.EdmSubscribeTask;

public interface EdmSubscribeTaskService {
	/**
	 * 查询任务列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<EdmSubscribeTask> getTaskList(Map<Object, Object> paramMap);
	
	/**
	 * 根据任务的id获取任务
	 * @param taskId
	 * @return
	 */
	public EdmSubscribeTask getTaskByTaskId(Long taskId);

	/**
	 * 更新任务
	 * 
	 * @param task
	 * @return
	 */
	public int updateTask(EdmSubscribeTask task);
	
	/**
 	 * 发送邮件以后更新任务状态以及添加一个batchJob
 	 * @param task
 	 * @param batchJob
 	 * @return
 	 */
 	public int sendEmailOverUpdateTask(EdmSubscribeTask task,EdmSubscribeBatchJob batchJob);

	/**
	 * 查询列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<EdmSubscribeTask> getModelList(Map<Object, Object> paramMap);

	/**
	 * 根据条件查询EDM任务列表
	 * 
	 * @param parameters
	 * @return
	 */
	public List<EdmSubscribeTask> search(Map<String, Object> parameters);

	/**
	 * 根据条件查询EDM任务总数
	 * 
	 * @param parameters
	 * @return
	 */
	public Long count(Map<String, Object> parameters);

	/**
	 * 插入一条EDM任务
	 * 
	 * @param object
	 * @return
	 */
	public Long insert(EdmSubscribeTask object);

	/**
	 * 更新一条EDM任务信息
	 * 
	 * @param object
	 * @return
	 */
	public int update(EdmSubscribeTask object);

}
