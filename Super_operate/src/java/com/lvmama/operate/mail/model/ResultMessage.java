package com.lvmama.operate.mail.model;

import java.util.Map;

import com.hanqinet.edm.ws.prnasia.dto.upload.Message;

/**
 * 发送邮件结果
 * 
 * @author likun
 * 
 */
public class ResultMessage {

	/**
	 * 是否发送成功
	 */
	private boolean isSuccess;

	/**
	 * 任务id
	 */
	private Integer taskId;

	private String message;

	/**
	 * 扩展参数
	 */
	Map<Object, Object> paramMap;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<Object, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<Object, Object> paramMap) {
		this.paramMap = paramMap;
	}

}
