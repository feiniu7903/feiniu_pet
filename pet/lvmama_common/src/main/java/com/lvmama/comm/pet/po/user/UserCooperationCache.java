package com.lvmama.comm.pet.po.user;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 用户联合登陆CACHE
 * @author liuyi
 *
 */
public class UserCooperationCache implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6718435905946949736L;
	
	
	/**
	 * 唯一标示
	 */
	private Long cooperationCacheId;
	
	
	private String cooperationType;
	
	
	private String cooperationUserAccount;
	
	
	private String cooperationUserName;
	
	private Date createTime;
	
	private String email;

	public Long getCooperationCacheId() {
		return cooperationCacheId;
	}

	public void setCooperationCacheId(Long cooperationCacheId) {
		this.cooperationCacheId = cooperationCacheId;
	}

	public String getCooperationType() {
		return cooperationType;
	}

	public void setCooperationType(String cooperationType) {
		this.cooperationType = cooperationType;
	}

	public String getCooperationUserAccount() {
		return cooperationUserAccount;
	}

	public void setCooperationUserAccount(String cooperationUserAccount) {
		this.cooperationUserAccount = cooperationUserAccount;
	}

	public String getCooperationUserName() {
		return cooperationUserName;
	}

	public void setCooperationUserName(String cooperationUserName) {
		this.cooperationUserName = cooperationUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
