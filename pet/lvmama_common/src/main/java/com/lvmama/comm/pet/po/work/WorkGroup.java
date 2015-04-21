/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.work;

import java.io.Serializable;
import java.util.Date;

/**
 * WorkGroup 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * 
 * @author ruanxiequan
 * @update dingming
 * @version 1.0
 * @since 1.0
 */

public class WorkGroup implements Serializable {
	private static final long serialVersionUID = -8729334116661624162L;
	private Long workGroupId;//组织id
	private String groupName;//组织名称
	private String memo;//组织名称
	private Long workDepartmentId;//所属部门id
	private Date createTime;//创建时间
	private String valid;//部门是否有效
	private String departmentName;//所属部门名称
	private String groupType; //组织类型
	public WorkGroup() {
	}

	/**
	 * WorkGroup 的构造函数
	 */
	public WorkGroup(Long workGroupId) {
		this.workGroupId = workGroupId;
	}

	public Long getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getWorkDepartmentId() {
		return workDepartmentId;
	}

	public void setWorkDepartmentId(Long workDepartmentId) {
		this.workDepartmentId = workDepartmentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	
}
