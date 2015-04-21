package com.lvmama.comm.edm;

import java.io.Serializable;

/**
 * EDM任务
 * 
 * @author likun
 * 
 */
public class TaskDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 邮件列名称，详情可以查看汉启edm接口说明文档
	 */
	private String emailColumnName;
	/**
	 * 任务组id
	 */
	private String taskGroupId;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getEmailColumnName() {
		return emailColumnName;
	}

	public void setEmailColumnName(String emailColumnName) {
		this.emailColumnName = emailColumnName;
	}

	public String getTaskGroupId() {
		return taskGroupId;
	}

	public void setTaskGroupId(String taskGroupId) {
		this.taskGroupId = taskGroupId;
	}

}
