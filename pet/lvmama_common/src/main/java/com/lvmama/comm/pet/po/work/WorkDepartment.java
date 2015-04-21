/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.work;

import java.io.Serializable;

/**
 * WorkDepartment 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * 
 * @author ruanxiequan
 * @update dingming
 * @version 1.0
 * @since 1.0
 */

public class WorkDepartment implements Serializable {
	private static final long serialVersionUID = -1079391275757820062L;
	private Long workDepartmentId; //部门id
	private String departmentName;  //部门名称
	private String memo;  //部门描述
	private String valid;//部门是否有效   ( TRUE有效,FALSE无效)
	public WorkDepartment() {
	}

	/**
	 * WorkDepartment 的构造函数
	 */
	public WorkDepartment(Long workDepartmentId) {
		this.workDepartmentId = workDepartmentId;
	}

	public Long getWorkDepartmentId() {
		return workDepartmentId;
	}

	public void setWorkDepartmentId(Long workDepartmentId) {
		this.workDepartmentId = workDepartmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
}
