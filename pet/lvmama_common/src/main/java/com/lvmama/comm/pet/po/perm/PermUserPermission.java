package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class PermUserPermission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long upId;
	private Long userId;
	private Long permissionId;
	private Long enableDays;
	private Date createTime;
	private String type;
	
	private String permissionName;
	private String parentPermissionName;
	private String permissionRoleName;
	private String permissionType;
	
	public Long getUpId() {
		return upId;
	}
	public void setUpId(Long upId) {
		this.upId = upId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public Long getEnableDays() {
		return enableDays;
	}
	public void setEnableDays(Long enableDays) {
		this.enableDays = enableDays;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getParentPermissionName() {
		return parentPermissionName;
	}
	public void setParentPermissionName(String parentPermissionName) {
		this.parentPermissionName = parentPermissionName;
	}
	public String getPermissionRoleName() {
		return permissionRoleName;
	}
	public void setPermissionRoleName(String permissionRoleName) {
		this.permissionRoleName = permissionRoleName;
	}
	public String getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
	public String getPermissionTypeName() {
		if(Constant.PERM_PERMISSION_TYPE_ENUM.ELEMENT.toString().equals(this.permissionType)){
			return "元素";
		}
		return "URL";
	}
	
}
