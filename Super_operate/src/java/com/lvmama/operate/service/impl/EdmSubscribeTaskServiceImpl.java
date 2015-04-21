package com.lvmama.operate.service.impl;
/**
 * EDM任务服务实现类
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeBatchJob;
import com.lvmama.comm.pet.po.edm.EdmSubscribeTask;
import com.lvmama.operate.dao.EdmSubscribeBatchJobDAO;
import com.lvmama.operate.dao.EdmSubscribeTaskDAO;
import com.lvmama.operate.service.EdmSubscribeTaskService;
import com.lvmama.operate.util.EdmLogUtil;
import com.lvmama.operate.util.LogViewUtil;

public class EdmSubscribeTaskServiceImpl implements EdmSubscribeTaskService {

     private EdmSubscribeTaskDAO edmSubscribeTaskDAO;
     
     private EdmSubscribeBatchJobDAO edmSubscribeBatchJobDAO;
     
     
     /**
 	 * 查询任务列表
 	 * 
 	 * @param paramMap
 	 * @return
 	 */
 	public List<EdmSubscribeTask> getTaskList(Map<Object, Object> paramMap) {
 		return this.edmSubscribeTaskDAO.getTaskList(paramMap);
 	}

 	/**
 	 * 更新任务
 	 * 
 	 * @param task
 	 * @return
 	 */
 	public int updateTask(EdmSubscribeTask task){
 		return this.edmSubscribeTaskDAO.updateTask(task);
 	}
 	
 	/**
 	 * 发送邮件以后更新任务状态以及添加一个batchJob
 	 * @param task
 	 * @param batchJob
 	 * @return
 	 */
 	public int sendEmailOverUpdateTask(EdmSubscribeTask task,EdmSubscribeBatchJob batchJob){
 		int i = updateTask(task);
 		if(batchJob!=null){
 			this.edmSubscribeBatchJobDAO.insert(batchJob);
 		}
 		return i;
 	}
     /**
 	 * 查询列表
 	 * 
 	 * @param paramMap
 	 * @return
 	 */
 	public List<EdmSubscribeTask> getModelList(Map<Object, Object> paramMap){
 		return this.edmSubscribeTaskDAO.getModelList(paramMap);
 	}
     
     @Override
     public Long count(Map<String, Object> parameters) {
          return edmSubscribeTaskDAO.count(parameters);
     }

     @Override
     public Long insert(EdmSubscribeTask object) {
          Long key = edmSubscribeTaskDAO.insert(object);
          EdmLogUtil.insert("EDM_SUBSCRIBE_TASK",object.getCreateUser(), object.getTaskId());
          return key;
     }

     @Override
     public List<EdmSubscribeTask> search(Map<String, Object> parameters) {
          return edmSubscribeTaskDAO.search(parameters);
     }
     
     @Override
 	public EdmSubscribeTask getTaskByTaskId(Long taskId) {
 		Map<Object, Object> paramMap = new HashMap<Object, Object>();
 		paramMap.put("taskId", taskId);
 		List<EdmSubscribeTask> list = this.edmSubscribeTaskDAO.getTaskList(paramMap);
 		if(list.size()==1){
 			return list.get(0);
 		}
 		return null;
 	}

     @Override
     public int update(EdmSubscribeTask object) {
          Map<String,Object> param = new HashMap<String,Object>();
          param.put("taskId", object.getTaskId());
          List<EdmSubscribeTask> list = edmSubscribeTaskDAO.search(param);
          if(null == list || (null != list && list.size() == 0)){
               return 0;
          }
          EdmSubscribeTask oldTask = list.get(0);
          int result = edmSubscribeTaskDAO.update(object);
          EdmLogUtil.update("EDM_SUBSCRIBE_TASK", 
        		  	object.getTaskId(),
                  	object.getUpdateUser(),
                  	LogViewUtil.logEditStr("任务编号", ""+object.getTaskId()," ")
                    +LogViewUtil.logEditStr("任务名称",oldTask.getTaskName()," ")
                    +LogViewUtil.logEditStr("任务描述",oldTask.getTaskDesc()," ")
                    +LogViewUtil.logEditStr("模板编号",oldTask.getTempId().toString()," ")
                    +LogViewUtil.logEditStr("用户组编号",oldTask.getUserGroupId().toString()," ")
                    +LogViewUtil.logEditStr("通道类型",oldTask.getChannelType()," ")
                    +LogViewUtil.logEditStr("发送时间",oldTask.getSendTime()," ")
                    +LogViewUtil.logEditStr("发送频率",oldTask.getSendCycle()," ")
                    +LogViewUtil.logEditStr("任务类型",oldTask.getTaskType()," ")
                    +LogViewUtil.logEditStr("邮件标题",oldTask.getEmailTitle()," ")
                    +LogViewUtil.logEditStr("用户名",oldTask.getSendUser()," ")
                    +LogViewUtil.logEditStr("发送邮箱",oldTask.getSendEmail()," ")
                    +LogViewUtil.logEditStr("任务状态",oldTask.getTaskStatus()," "));
          return result;
     }
     
     public EdmSubscribeTaskDAO getEdmSubscribeTaskDAO() {
          return edmSubscribeTaskDAO;
     }

     public void setEdmSubscribeTaskDAO(EdmSubscribeTaskDAO edmSubscribeTaskDAO) {
          this.edmSubscribeTaskDAO = edmSubscribeTaskDAO;
     }

	public EdmSubscribeBatchJobDAO getEdmSubscribeBatchJobDAO() {
		return edmSubscribeBatchJobDAO;
	}

	public void setEdmSubscribeBatchJobDAO(
			EdmSubscribeBatchJobDAO edmSubscribeBatchJobDAO) {
		this.edmSubscribeBatchJobDAO = edmSubscribeBatchJobDAO;
	}
     

}
