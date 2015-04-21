package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;


public class UserRelateMenu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4483019393208818602L;
	/**
	 * 主键
	 */
	private Long userRelateMenuId;
	/**
	 * 用户编号.
	 */
	private Long passPortUserId;
	/**
	 * 菜单编号.
	 */
	private Long resourceId;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getUserRelateMenuId() {
		return userRelateMenuId;
	}

	public void setUserRelateMenuId(Long userRelateMenuId) {
		this.userRelateMenuId = userRelateMenuId;
	}

	public Long getPassPortUserId() {
		return passPortUserId;
	}

	public void setPassPortUserId(Long passPortUserId) {
		this.passPortUserId = passPortUserId;
	}
}