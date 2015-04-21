package com.lvmama.back.web.log;

import org.zkoss.zul.api.Tab;

import com.lvmama.back.web.BaseAction;

public class ToolsAction extends BaseAction {
	private Tab messages;
	private Tab tasks;
	private Tab announ;
	
	private String isChangeType;
	public void doAfter(){
		if("message".indexOf(isChangeType)>-1){
			messages.setSelected(true);	
		}else if("task".indexOf(isChangeType)>-1){
			tasks.setSelected(true);	
		}else if("announce".indexOf(isChangeType)>-1){
			announ.setSelected(true);	
		}
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
}
