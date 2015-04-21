package com.lvmama.operate.job.model;

import java.util.ArrayList;
import java.util.List;

public class UserEmailModel {

	
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 模板名称
	 */
	private String templateName;
	
	/**
	 * 模板内容
	 */
	private String templateContent;
	/**
	 * email上传以后的地址
	 */
	private String emaiUrl;
	/**
	 * email的列
	 */
	private List<String> columns;
	/**
	 * email的地址列表
	 */
	private List<String> userEmailList;
	public String getEmaiUrl() {
		return emaiUrl;
	}
	public void setEmaiUrl(String emaiUrl) {
		this.emaiUrl = emaiUrl;
	}
	public List<String> getColumns() {
		if(columns==null)
		{
			columns = new ArrayList<String>();
		}
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<String> getUserEmailList() {
		if(userEmailList==null)
		{
			userEmailList = new ArrayList<String>();
		}
		return userEmailList;
	}
	public void setUserEmailList(List<String> userEmailList) {
		this.userEmailList = userEmailList;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	
}
