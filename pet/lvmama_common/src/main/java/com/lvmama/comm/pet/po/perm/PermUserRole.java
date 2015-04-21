package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;

public class PermUserRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3074145826256116554L;
	private Long urId;
	private Long roleId;
	private Long userId;
	private Boolean isChecked=false;
	private String userName;
	private String roleName;
	private String realName;
	private String departmentName;
	public Long getUrId() {
		return urId;
	}
	public void setUrId(Long urId) {
		this.urId = urId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

}
