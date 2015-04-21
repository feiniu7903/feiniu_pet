package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

public class PermRolePermission implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5764804153754931543L;
	private Long rpId;
	private Long roleId;
	private Long permissionId;
	private String permName;
	private String roleName;
	private boolean isChecked;
	private String permType;
	private String zkType="URL";
	private String parentPermName;
	public String getParentPermName() {
		return parentPermName;
	}
	public void setParentPermName(String parentPermName) {
		this.parentPermName = parentPermName;
	}
	public Long getRpId() {
		return rpId;
	}
	public void setRpId(Long rpId) {
		this.rpId = rpId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getZkType() {
		if(Constant.PERM_PERMISSION_TYPE_ENUM.ELEMENT.name().equals(this.permType)){
			this.zkType="元素";
		}
		return zkType;
	}
	public String getPermType() {
		return permType;
	}
	public void setPermType(String permType) {
		this.permType = permType;
	}

}
