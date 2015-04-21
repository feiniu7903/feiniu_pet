package com.lvmama.operate.web.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTask;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.service.EdmSubscribeTaskService;
import com.lvmama.operate.util.CodeSet;
import com.lvmama.operate.util.ZkMessage;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.operate.web.BaseAction;


public class EditTaskAction extends BaseAction {

     /**
      * 
      */
     private static final long serialVersionUID = 3381103476235072975L;
     
     private static final Long DEFAULT_ID_VALUE = 0L;
     
     private static final String TIME_FORMAT = "HH:mm:ss";
     
     /**
      * 任务服务接口
      */
     private EdmSubscribeTaskService edmSubscribeTaskService; 
     /**
      * 任务信息PO
      */
     private EdmSubscribeTask task;
     /**
      * 任务编号
      */
     private Long taskId;
     /**
      * 任务发送时间(小时)
      */
     private Date sendTime;    
     private List<CodeItem> weekOptions = new ArrayList<CodeItem>();
     public void doBefore(){
          if(null != taskId){
               Map<String,Object> parameters = new HashMap<String,Object>();
               parameters.put("taskId", taskId);
               List<EdmSubscribeTask> list = edmSubscribeTaskService.search(parameters);
               if(null != list && list.size() > 0){
                    task = list.get(0);
               }
               sendTime = DateUtil.toDate(task.getSendTime(), TIME_FORMAT);
          }else{
               task = new EdmSubscribeTask();
               task.setTempId(DEFAULT_ID_VALUE);
               task.setUserGroupId(DEFAULT_ID_VALUE);
          }
          initCode();
     }
     
     public void save(){
          if(validate()){
               String time = DateUtil.getFormatDate(sendTime,TIME_FORMAT);
               String operator = getOperatorName();
               task.setSendTime(time);
               task.setCreateUser(operator);
               task.setUpdateUser(operator);
               if("ONE_DAY".equals(task.getSendCycle())){
                    task.setSendDay(null);
               }
               if(null != taskId){
            	   
            	   EdmSubscribeTask tempTask = this.edmSubscribeTaskService.getTaskByTaskId(taskId);
	            	// 如果发送频率，发送日期，发送时间有一个发生变化了，那么定时任务执行的时间将从新进行计算
					if (StringUtil.isSame(tempTask.getSendDay(),
							task.getSendDay())
							&& StringUtil.isSame(tempTask.getSendTime(),
									task.getSendTime())
							&& StringUtil.isSame(tempTask.getSendCycle(),
									task.getSendCycle())) {
						task.setExecuteDate(tempTask.getExecuteDate());
						task.setNextExecuteDate(tempTask.getNextExecuteDate());
					} else {
						task.setExecuteDate(null);
						task.setNextExecuteDate(null);
					}
                    edmSubscribeTaskService.update(task);
               }else{
                    task.setTaskStatus(Constant.EDM_STATUS_TYPE.Y.name());
                    edmSubscribeTaskService.insert(task);
               }
               super.refreshParent("search");
               super.closeWindow();
          }
     }
     /**
      * 验证任务信息完整性
      * @return
      */
     private boolean validate(){
          if(null == task){
               alert("任务为空");
               return false;
          }
          if(StringUtil.isEmptyString(task.getTaskName())){
               alert("请输入任务名称");
               return false;
          }
          if(task.getTaskName().length()>100){
               alert("名称过长");
               return false;
          }
          if(StringUtil.isEmptyString(task.getTaskType())){
               alert("请选择任务类型");
               return false;
          }
          if(null == task.getTempId() || DEFAULT_ID_VALUE == task.getTempId()){
               alert("请正确选择EDM模板");
               return false;
          }
          if(null == task.getUserGroupId() || DEFAULT_ID_VALUE == task.getUserGroupId()){
               alert("请正确选择用户组");
               return false;
          }
          if(StringUtil.isEmptyString(task.getSendDay()) && !"ONE_DAY".equals(task.getSendCycle())){
               alert("请选择发送时间");
               return false;
          }
          if(null == sendTime){
               alert("请选择发送时间小时");
               return false;
          }
          if(StringUtil.isEmptyString(task.getSendCycle())){
               alert("请选择任务发送频率");
               return false;
          }
          if(StringUtil.isEmptyString(task.getChannelType())){
               alert("请选择发送通道");
               return false;
          }
          if(null != task.getEmailTitle() && task.getEmailTitle().length() > 100){
               alert("邮件标题过长");
               return false;
          }
          if(null != task.getSendUser() && task.getSendUser().length() > 30){
               alert("发件人名称过长");
               return false;
          }
          if(null != task.getSendEmail() && !task.getSendEmail().matches("^\\S+@\\S+$")){
               alert("发件邮箱格式不正确");
               return false;
          }
          if(null != task.getSendEmail() && task.getSendEmail().length() > 60){
               alert("发件邮箱过长");
               return false;
          }
          if(StringUtil.isEmptyString(task.getGroupId())){
              ZkMessage.showError("请选择任务组");
              return false;
          }
          return true;
     }
     
     public void initCode(){
    	 weekOptions =CodeSet.getInstance().getCodeList("EDM_TASK_SEND_WEEK");
    	  CodeItem listitem = new CodeItem();
          listitem.setName("-- 请选择  --");
          listitem.setCode("");
          weekOptions.add(0, listitem);
    	 if(null == task){
    		 return;
    	 }
    	 if("ONE_MONTH".equals(task.getSendCycle())){
    		 weekOptions =CodeSet.getInstance().getCodeList("EDM_TASK_SEND_DAYS");
    		 weekOptions.add(0, listitem);
    	 }
    	 if(StringUtil.isEmptyString(task.getSendDay())){
    		 return;
    	 }
    	 for(CodeItem item:weekOptions){
    		 if(task.getSendDay().equals(item.getCode())){
    			 item.setChecked("true");
    		 }
    	 }
     }
     public void changeCycle(final String sendCycle){
    	 task.setSendDay("");
    	 task.setSendCycle(sendCycle);
    	 initCode();
     }
     public EdmSubscribeTaskService getEdmSubscribeTaskService() {
          return edmSubscribeTaskService;
     }

     public void setEdmSubscribeTaskService(
               EdmSubscribeTaskService edmSubscribeTaskService) {
          this.edmSubscribeTaskService = edmSubscribeTaskService;
     }

     public EdmSubscribeTask getTask() {
          return task;
     }

     public void setTask(EdmSubscribeTask task) {
          this.task = task;
     }

     public Long getTaskId() {
          return taskId;
     }

     public void setTaskId(Long taskId) {
          this.taskId = taskId;
     }
     public Date getSendTime() {
          return sendTime;
     }

     public void setSendTime(Date sendTime) {
          this.sendTime = sendTime;
     }

	public List<CodeItem> getWeekOptions() {
		return weekOptions;
	}

	public void setWeekOptions(List<CodeItem> weekOptions) {
		this.weekOptions = weekOptions;
	}
}
