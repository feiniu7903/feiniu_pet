package com.lvmama.comm.edm;

import java.io.Serializable;
import java.util.Map;

/**
 * 发送邮件结果
 * 
 * @author likun
 * 
 */
public class HqEmailResultMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否成功
	 */
	private boolean isSuccess;

	/**
	 * 任务id
	 */
	private Integer taskId;

	/**
	 * 描述信息
	 */
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
