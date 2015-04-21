package com.lvmama.back.web.log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.api.Tab;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.utils.DateUtil;
/**
 * 工具栏提醒是否消息、任务、公告过来，进行进度条变色提醒.
 * @author huangli
 *
 */
@SuppressWarnings("unchecked")
public class ToolsSouthAction extends BaseAction {
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	
	private Tab messages;
	private Tab tasks;
	private Tab announ;
	
	private String isChangeType="";
	
	public void doBefore(){
		isChangeType="";
		if(this.queryMessage()){
			isChangeType="message";
		}else if(this.queryAnnounce()){
			isChangeType="announce";
		}else if(this.queryTask()){
			isChangeType="task";
		}
	}
	/**
	 * 查询当前发送给自己的公告.
	 * @return
	 */
	public boolean queryAnnounce(){
		Map searchMessageMap=new HashMap();
		searchMessageMap.put("skipResults",0);
		searchMessageMap.put("maxResults",10);
		searchMessageMap.put("userId",this.getSessionUser().getUserId());
		searchMessageMap.put("loginName",this.getSessionUserName());
		List list=this.comMessageService.queryToolsComAnnouncement(searchMessageMap);
		return list.size()>0?true:false;
	}
	/**
	 * 查询当前发送给自己的任务.
	 * @return
	 */
	public boolean queryTask(){
		Map searchMessageMap=new HashMap();
		searchMessageMap.put("skipResults",0);
		searchMessageMap.put("maxResults",5);
		searchMessageMap.put("receiver", this.getOperatorName());
		searchMessageMap.put("status","CREATE");
		searchMessageMap.put("notNullBeginTime","not null");
		searchMessageMap.put("beginStartDate",new Date());
		searchMessageMap.put("beginEndDate",DateUtil.DsDay_Minute(new Date(), 5));
		Long count=this.comMessageService.queryComMessageByParamCount(searchMessageMap);
		return count>0?true:false;
	}
	
	/**
	 * 查询当前发送给自己的消息.
	 * @return
	 */
	public boolean queryMessage(){
		Map searchMessageMap=new HashMap();
		searchMessageMap.put("skipResults",0);
		searchMessageMap.put("maxResults",5);
		searchMessageMap.put("receiver", this.getOperatorName());
		searchMessageMap.put("status","CREATE");
		searchMessageMap.put("nullBeginTime","null");
		Long count=this.comMessageService.queryComMessageByParamCount(searchMessageMap);
		return count>0?true:false;
	}
	
	public Tab getMessages() {
		return messages;
	}

	public void setMessages(Tab messages) {
		this.messages = messages;
	}

	public Tab getTasks() {
		return tasks;
	}

	public void setTasks(Tab tasks) {
		this.tasks = tasks;
	}

	public Tab getAnnoun() {
		return announ;
	}

	public void setAnnoun(Tab announ) {
		this.announ = announ;
	}

	public String getIsChangeType() {
		return isChangeType;
	}

	public void setIsChangeType(String isChangeType) {
		this.isChangeType = isChangeType;
	}
	public ComMessageService getComMessageService() {
		return comMessageService;
	}
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}
}
