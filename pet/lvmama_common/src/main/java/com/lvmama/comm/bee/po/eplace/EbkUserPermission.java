package com.lvmama.comm.bee.po.eplace;

import java.io.Serializable;

public class EbkUserPermission implements Serializable{
	private Long userPermissionId;
	private Long userId;
	private Long permissionId;
	public Long getUserPermissionId() {
		return userPermissionId;
	}
	public void setUserPermissionId(Long userPermissionId) {
		this.userPermissionId = userPermissionId;
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
	
}
