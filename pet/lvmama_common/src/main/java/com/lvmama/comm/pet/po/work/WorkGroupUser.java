/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.work;

import java.io.Serializable;
import java.util.Date;

/**
 * WorkGroupUser 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkGroupUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5478135929989531675L;
	//columns START
	/** 变量 workGroupUserId . */
	private Long workGroupUserId;
	/** 变量 workDepartmentId . */
	private Long workDepartmentId;
	/** 变量 workGroupId . */
	private Long workGroupId;
	/** 变量 permUserId . */
	private Long permUserId;
	/** 变量 leader . */
	private String leader;
	/** 变量 createTime . */
	private Date createTime;
	private String valid;
	
	private String userName;
	private String realName;
	private String departmentName;
	private String workGroupName;
	private String workStatus;
	private Integer crCount;
	//columns END
	/**
	* WorkGroupUser 的构造函数
	*/
	public WorkGroupUser() {
	}
	/**
	* WorkGroupUser 的构造函数
	*/
	public WorkGroupUser(
		Long workGroupUserId
	) {
		this.workGroupUserId = workGroupUserId;
	}
	public Long getWorkGroupUserId() {
		return workGroupUserId;
	}
	public void setWorkGroupUserId(Long workGroupUserId) {
		this.workGroupUserId = workGroupUserId;
	}
	public Long getWorkDepartmentId() {
		return workDepartmentId;
	}
	public void setWorkDepartmentId(Long workDepartmentId) {
		this.workDepartmentId = workDepartmentId;
	}
	public Long getWorkGroupId() {
		return workGroupId;
	}
	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}
	public Long getPermUserId() {
		return permUserId;
	}
	public void setPermUserId(Long permUserId) {
		this.permUserId = permUserId;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getWorkGroupName() {
		return workGroupName;
	}
	public void setWorkGroupName(String workGroupName) {
		this.workGroupName = workGroupName;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public Integer getCrCount() {
		return crCount;
	}
	public void setCrCount(Integer crCount) {
		this.crCount = crCount;
	}
	
}
