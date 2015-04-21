package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 388701923630131495L;
	private Long userId;
	private Long departmentId;
	private String userName;
	private String realName;
	private String mobile;
	private String dataLimited;
	private String valid;
	private String password;
	private String employeeNum;
	private String position;
	private String departmentName;
	private Boolean permUserPass;
	private Boolean isChecked=false;
	private String extensionNumber;
	private String isHuaweiCc;
	
	private Long permissionId;
	private String status;
	private Integer crCount;
	
	private String workStatus;
	

	/**
	 * 人员邮箱
	 * updater : 尚正元
	 * update_date: 2012-04-19
	 */
	private String email;
	private List<PermPermission> permissionList;
	//该用户所有的角色
	private List<PermRole> userRoleList;
	public PermUser() {

	}

	public PermUser(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDataLimited() {
		return dataLimited;
	}

	public void setDataLimited(String dataLimited) {
		this.dataLimited = dataLimited;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Boolean isAdministrator() {
		boolean flag=false;
		if("admin".equals(userName)){
			return true;
		}else if(this.userRoleList!=null){
			  for(PermRole permRole:this.userRoleList){
				  if("admin".equalsIgnoreCase(permRole.getRoleName().trim())){
					  flag=true;
					   break;
				  }
			  }
		 }
		  return flag;
	}

	public Boolean getPermUserPass() {
		return permUserPass;
	}

	public void setPermUserPass(Boolean permUserPass) {
		this.permUserPass = permUserPass;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<PermPermission> getPermissionTreeList() {
		List<PermPermission> myList = getFirstLevelMenuList();
		for (PermPermission category : myList) {
			List<PermPermission> subList = this.getSecondLevelMenuList(category.getPermissionId());
			category.setSubList(subList);
			for (PermPermission second : subList) {
				List<PermPermission> thirdList = this.getThirdLevelMenuList(second.getPermissionId());
				second.setSubList(thirdList);
			}
		}
		return myList;
	}
	
	/**
	 * 查询第一级菜单列表
	 * 
	 * @param category
	 * @return
	 */
	public List<PermPermission> getFirstLevelMenuList() {
		List<PermPermission> myList = new ArrayList<PermPermission>();
		for (PermPermission permPermission : permissionList) {
			if (permPermission.isMenu() && "1".equals(permPermission.getPermLevel())) {
				myList.add(permPermission);
			}
		}
		return myList;
	}

	/**
	 * 查询指定父CODE的第二级菜单列表
	 * 
	 * @param parentCode
	 * @return
	 */
	public List<PermPermission> getSecondLevelMenuList(Long permissionId) {
		List<PermPermission> myList = new ArrayList<PermPermission>();
		for (PermPermission permPermission : permissionList) {
			if (permPermission.isMenu()
					&& "2".equals(permPermission.getPermLevel())
					&& permissionId.equals(permPermission.getParentId())) {
				myList.add(permPermission);
			}
		}
		return myList;
	}

	/**
	 * 查询指定父CODE的第三级菜单列表
	 * 
	 * @param parentCode
	 * @return
	 */
	public List<PermPermission> getThirdLevelMenuList(Long permissionId) {
		List<PermPermission> myList = new ArrayList<PermPermission>();
		for (PermPermission permPermission : permissionList) {
			if (permPermission.isMenu()
					&& "3".equals(permPermission.getPermLevel())
					&& permissionId.equals(permPermission.getParentId())) {
				myList.add(permPermission);
			}
		}
		return myList;
	}
	
	/**
	 * 查询指定父CODE的组件权限列表
	 * 
	 * @param parentCode
	 * @return
	 */
	public List<PermPermission> getComponentPermissionList(Long permissionId) {
		List<PermPermission> myList = new ArrayList<PermPermission>();
		for (PermPermission permPermission : permissionList) {
			if (permPermission.isComponent()
					&& permissionId.equals(permPermission.getParentId())) {
				myList.add(permPermission);
			}
		}
		return myList;
	}

	/**
	 * 该用户是否有指定权限
	 * 
	 * @param parentCode
	 * @param permissionCode
	 * @return
	 */
	public boolean hasPermission(Long permissionId) {
		for (PermPermission permPermission : permissionList) {
			if (permissionId.equals(permPermission.getPermissionId())) {
				return true;
			}
		}
		return false;
	}

	public List<PermPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<PermPermission> permissionList) {
		this.permissionList = permissionList;
	}

	public String getZhValid() {
		return this.valid.equals("Y") ? "正常" : "锁定";
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public List<PermRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<PermRole> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public String getExtensionNumber() {
		return extensionNumber;
	}

	public void setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	
	public String getUserInfoStr(){
		return "用户ID：" + userId 
				+ "；用户名：" + userName
				+ "；姓名：" + realName
				+ "；部门编号：" + departmentId
				+ "；是否有效：" + valid
				+ "；职务：" + position
				+ "；是否为华为客服系统账号：" + isHuaweiCc
				;
	}

	public String getIsHuaweiCc() {
		return isHuaweiCc;
	}

	public void setIsHuaweiCc(String isHuaweiCc) {
		this.isHuaweiCc = isHuaweiCc;
	}

	public Integer getCrCount() {
		return crCount;
	}

	public void setCrCount(Integer crCount) {
		this.crCount = crCount;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
}
