/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.bee.po.eplace;

import java.io.Serializable;

/**
 * PermUser 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class EbkUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2189752882404223749L;
	//columns START
	/** 变量 userId . */
	private Long userId;
	/** 变量 userName . */
	private String userName;
	/** 变量 password . */
	private String password;
	/** 变量 name . */
	private String name;
	/** 变量 department . */
	private String department;
	/** 变量 phone . */
	private String phone;
	/** 变量 mobile . */
	private String mobile;
	/** 变量 email . */
	private String email;
	/** 变量 valid . */
	private String valid;
	/** 变量 isAdmin . */
	private String isAdmin;
	/** 变量 supplierId . */
	private Long supplierId;
	private String supplierName;
	/** 变量 description . */
	private String description;
	/** 变量 lvmamaContactName . */
	private String lvmamaContactName;
	/** 变量 lvmamaContactPhone . */
	private String lvmamaContactPhone;
	/** 变量 bizType . */
	private String bizType;
	/** 变量 createTime . */
	private java.util.Date createTime;
	private Long parentUserId;
	private String parentUserName;
	private String canPrint;
	//columns END
	/**
	* PermUser 的构造函数
	*/
	public EbkUser() {
	}
	/**
	* PermUser 的构造函数
	*/
	public EbkUser(
		Long userId
	) {
		this.userId = userId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLvmamaContactName() {
		return lvmamaContactName;
	}
	public void setLvmamaContactName(String lvmamaContactName) {
		this.lvmamaContactName = lvmamaContactName;
	}
	public String getLvmamaContactPhone() {
		return lvmamaContactPhone;
	}
	public void setLvmamaContactPhone(String lvmamaContactPhone) {
		this.lvmamaContactPhone = lvmamaContactPhone;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public Long getParentUserId() {
		return parentUserId;
	}
	public void setParentUserId(Long parentUserId) {
		this.parentUserId = parentUserId;
	}
	public String getAdminInfoStr(){
		return "用户ID：" + userId 
				+ "；用户名：" + userName
				+ "；供应商ID：" + (supplierId == null?"":supplierId)
				+ "；驴妈妈联系人：" + (lvmamaContactName == null?"":lvmamaContactName)
				+ "；联系电话：" + (lvmamaContactPhone == null?"":lvmamaContactPhone)
				+ "；账号备注：" + (description == null?"":description)
				+ "；是否有效：" + valid
				;
	}
	public String getUserInfoStr(){
		return "用户ID：" + userId 
				+ "；用户名：" + userName
				+ "；姓名：" + (name == null?"":name)
				+ "；固定电话：" + (phone == null?"":phone)
				+ "；手机：" + (mobile==null?"":mobile)
				+ "；邮箱：" + (email==null?"":email)
				+ "；供应商ID：" + (supplierId==null?"":supplierId)
				+ "；驴妈妈联系人：" + (lvmamaContactName==null?"":lvmamaContactName)
				+ "；联系电话：" + (lvmamaContactPhone==null?"":lvmamaContactPhone)
				+ "; 所属部门：" + (department==null?"":department)
				+ "；是否有效：" + valid
				;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getParentUserName() {
		return parentUserName;
	}
	public void setParentUserName(String parentUserName) {
		this.parentUserName = parentUserName;
	}
	public String getCanPrint() {
		return canPrint;
	}
	public void setCanPrint(String canPrint) {
		this.canPrint = canPrint;
	}
	
}


