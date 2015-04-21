package com.lvmama.operate.web.task;
/**
 * 查询任务信息，编辑任务状态Action
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTask;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.service.EdmSubscribeTaskService;
import com.lvmama.operate.util.ZkMessage;
import com.lvmama.operate.util.ZkMsgCallBack;
import com.lvmama.operate.web.BaseAction;

public class EdmSubscribeTaskAction extends BaseAction {

     /**
      * 
      */
     private static final long serialVersionUID = -2318592921077624249L;
     
     private EdmSubscribeTaskService edmSubscribeTaskService; 
     
     private Map<String, Object> searchConds = new HashMap<String, Object>(); 
     
     private List<EdmSubscribeTask> list;
     
     private EdmSubscribeTask task;
     
     public void deBefore(){
          
     }
     
     /**
      * 根据查询条件查询任务列表
      */
     public void search(){
          String taskType = (String)searchConds.get("taskType");
          if(StringUtil.isEmptyString(taskType)){
               searchConds.remove("taskType");
          }
          searchConds.put("taskStatus", Constant.EDM_STATUS_TYPE.Y.name());
          searchConds = initialPageInfoByMap(edmSubscribeTaskService.count(searchConds),searchConds);
          list = edmSubscribeTaskService.search(searchConds);
     }
     
     /**
      * 根据任务编号修改任务状态
      * @param params
      */
     public void updateTaskStatus(final Map<String,Object> params){ 
          Long taskId = (Long)params.get("taskId");
          String taskName = (String)params.get("taskName");
          String taskStatus = (String)params.get("taskStatus");
          task = getCurrentTask(taskId);
          if(null != task){ 
        	  if("R".equalsIgnoreCase(taskStatus)){
       		   task.setLastSendDate(DateUtil.getDateByStr("2000-01-01", "yyyy-MM-dd"));
              }else{
           	   task.setTaskStatus(taskStatus);
              } 
               task.setUpdateUser(getOperatorName());
               ZkMessage.showQuestion("您要修改 【"+taskName+"】任务状态, 请确认?", new ZkMsgCallBack(){
                    public void  execute(){
                    	int result = 0;
                    	EdmSubscribeTask tsk =EdmSubscribeTaskAction.this.edmSubscribeTaskService.getTaskByTaskId(task.getTaskId());
                    	if(tsk!=null){
	                		if("Y".equals(tsk.getTaskStatus())){
	                			task.setExecuteDate(tsk.getExecuteDate());
	                			task.setNextExecuteDate(tsk.getNextExecuteDate());
	                		}else{
	                			task.setExecuteDate(null);
	                			task.setNextExecuteDate(null);
	                		}
                    	}
                         result = edmSubscribeTaskService.update(task);
                         if(result == 1){
                              alert("修改成功");
                         }else{
                              alert("修改失败");
                         }
                         refreshComponent("search");
                    }
               }, new ZkMsgCallBack(){
                    public void  execute(){
                    }
               });
          }else{
               alert("根据名称【"+taskName+"】没有查询到任务");
          }
     }
     private EdmSubscribeTask getCurrentTask(Long taskId){
          if(null != list && null != taskId){
               for(EdmSubscribeTask task: list){
                    if(task.getTaskId().equals(taskId)){
                         return task;
                    }
               }
          }else if(null != taskId){
               Map<String,Object> parameters = new HashMap<String,Object>();
               parameters.put("userGroupId", taskId);
               List<EdmSubscribeTask> result = edmSubscribeTaskService.search(parameters);
               for(EdmSubscribeTask task : result){
                    return task;
               }
          }
          return null;
     }
     public EdmSubscribeTaskService getEdmSubscribeTaskService() {
          return edmSubscribeTaskService;
     }

     public void setEdmSubscribeTaskService(
               EdmSubscribeTaskService edmSubscribeTaskService) {
          this.edmSubscribeTaskService = edmSubscribeTaskService;
     }

     public Map<String, Object> getSearchConds() {
          return searchConds;
     }

     public void setSearchConds(Map<String, Object> searchConds) {
          this.searchConds = searchConds;
     }

     public List<EdmSubscribeTask> getList() {
          return list;
     }

     public void setList(List<EdmSubscribeTask> list) {
          this.list = list;
     }

     public EdmSubscribeTask getTask() {
          return task;
     }

     public void setTask(EdmSubscribeTask task) {
          this.task = task;
     }
}
