package com.lvmama.comm.pet.vo;

import java.io.Serializable;

public class InvokeResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7245408983958754537L;
	private int code;	//0:成功；大于0：失败
	private String description;  //
	private Long workOrderId;	//新增工单id
	private Long workTaskId;	//新增任务id
	private Object result;
	private String workOrderTypeName;
	
	public String getWorkOrderTypeName() {
		return workOrderTypeName;
	}
	public void setWorkOrderTypeName(String workOrderTypeName) {
		this.workOrderTypeName = workOrderTypeName;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	public Long getWorkTaskId() {
		return workTaskId;
	}
	public void setWorkTaskId(Long workTaskId) {
		this.workTaskId = workTaskId;
	}
}
