package com.lvmama.push.model;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class PushMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String addCode;
	
	String command;
	String pushId;

	String dateStr;
	
	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	String notificationMsg;

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}


	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public PushMessage(String command){
		this.command = command;
	}
	
	public String getJson(){
		return null;
	}



	public String getNotificationMsg() {
		return notificationMsg;
	}

	public void setNotificationMsg(String notificationMsg) {
		this.notificationMsg = notificationMsg;
	}
}
