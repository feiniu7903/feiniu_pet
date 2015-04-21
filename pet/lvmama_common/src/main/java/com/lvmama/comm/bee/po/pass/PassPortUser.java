package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;

public class PassPortUser implements Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6985170955138332875L;
	/**
	 * 用户编号.
	 */
	private Long passPortUserId;
	/**
	 * 用户名.
	 */
	private String userId;
	/**
	 * 密码.
	 */
	private String password;
	/**
	 * 姓名.
	 */
	private String name;
	/**
	 * 状态.是/否
	 */
	private String status;
	/**
	 * 用户类型.电子通关用户：PASSPORT_USER,E景通供应商用户：EPLACE_USER,E景通LVMAMA用户：LVMAMA_USER
	 */
	private String userType;
	/**
	 * E景通供应商编号.
	 */
	private Long eplaceSupplierId;
	/**
	 * 创建时间.
	 */
	private Date createDate;
	/**
	 * 显示通关用户类型.
	 */
	private String ZKUserType;

	public Long getPassPortUserId() {
		return passPortUserId;
	}

	public void setPassPortUserId(Long passPortUserId) {
		this.passPortUserId = passPortUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getEplaceSupplierId() {
		return eplaceSupplierId;
	}

	public void setEplaceSupplierId(Long eplaceSupplierId) {
		this.eplaceSupplierId = eplaceSupplierId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getZKUserType() {
		String typeName="E景通LVMAMA用户";
		if("EPLACE_USER".equalsIgnoreCase(this.userType)){
			typeName="E景通供应商用户";
		}else if("PASSPORT_USER".equalsIgnoreCase(this.userType)){
			typeName="电子通关用户";
		}
		return typeName;
	}
}