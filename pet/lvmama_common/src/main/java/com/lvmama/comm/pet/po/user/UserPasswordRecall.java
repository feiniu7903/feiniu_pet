package com.lvmama.comm.pet.po.user;

import java.io.Serializable;

/**
 * 用户密码找回
 *
 */
public class UserPasswordRecall implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6517387583896758300L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 是否使用
	 */
	private String isUsed = "Y";
	/**
	 * 邮箱
	 */
	private String email;

	public Long getId() {
		return id;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(final String userId) {
		this.userId = userId;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(final String isUsed) {
		this.isUsed = isUsed;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}
}
