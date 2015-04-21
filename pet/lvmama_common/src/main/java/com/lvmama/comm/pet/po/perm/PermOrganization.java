package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;
import java.util.Date;

public class PermOrganization implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2893168407237353277L;
	private Long orgId = new Long(0);
	private String departmentName;
	private Long parentOrgId = new Long(0);
	private Long permLevel;
	private Long createUser;
	private Date createTime;
	private String valid;
	private String parentDepName;
	private boolean checked;
	public String getParentDepName() {
		return parentDepName;
	}

	public void setParentDepName(String parentDepName) {
		this.parentDepName = parentDepName;
	}

	public Long getPermLevel() {
		return permLevel;
	}

	public void setPermLevel(Long permLevel) {
		this.permLevel = permLevel;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getZhDepartmentName() {
		if(this.parentDepName != null && this.departmentName != null){
			if(this.parentOrgId != null && this.parentOrgId != 0) {
				return this.parentDepName + "--" + this.departmentName;
			}
		}
		return this.departmentName != null ? this.departmentName : "";
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
