package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;
import java.util.Date;

public class PermRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4179068729515569104L;
	private Long roleId;
	private String roleName;
	private String valid;
	private Date createTime;
	private String description;
	private Long urId;
	private boolean isChecked;
	private boolean isEditvisible=true;
	private String ifShowEdit="Y";
	private String roleLabel;
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEditvisible() {
		if(this.getRoleId()!=null&&this.getRoleId()<=7){
			isEditvisible=false;
		}
		return isEditvisible;
	}

	public String getRoleLabel() {
		return roleLabel;
	}

	public void setRoleLabel(String roleLabel) {
		this.roleLabel = roleLabel;
	}

	public String getIfShowEdit() {
		if(this.getRoleId()!=null&&this.getRoleId()<=7){
			ifShowEdit="N";
		}
		return ifShowEdit;
	}

	public void setIfShowEdit(String ifShowEdit) {
		this.ifShowEdit = ifShowEdit;
	}

}
