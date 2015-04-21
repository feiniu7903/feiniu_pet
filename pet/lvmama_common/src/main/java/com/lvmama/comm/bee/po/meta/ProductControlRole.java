package com.lvmama.comm.bee.po.meta;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class ProductControlRole.
 * @author zuoxiaoshuai
 */
public class ProductControlRole implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8971946989265498628L;

	/** The product control role id. */
	private Long productControlRoleId;
	
	/** The user id. */
	private Long userId;
	
	/** The role area. */
	private String roleArea;
	
	/** The manager id list. */
	private String managerIdList;

	/** The create time. */
	private Date createTime;
	
	/** The update time. */
	private Date updateTime;
	
	/** The user name. */
	private String userName;

	/**
	 * Gets the product control role id.
	 *
	 * @return the product control role id
	 */
	public Long getProductControlRoleId() {
		return productControlRoleId;
	}

	/**
	 * Sets the product control role id.
	 *
	 * @param productControlRoleId the new product control role id
	 */
	public void setProductControlRoleId(Long productControlRoleId) {
		this.productControlRoleId = productControlRoleId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the role area.
	 *
	 * @return the role area
	 */
	public String getRoleArea() {
		return roleArea;
	}

	/**
	 * Sets the role area.
	 *
	 * @param roleArea the new role area
	 */
	public void setRoleArea(String roleArea) {
		this.roleArea = roleArea;
	}

	/**
	 * Gets the manager id list.
	 *
	 * @return the manager id list
	 */
	public String getManagerIdList() {
		return managerIdList;
	}

	/**
	 * Sets the manager id list.
	 *
	 * @param managerIdList the new manager id list
	 */
	public void setManagerIdList(String managerIdList) {
		this.managerIdList = managerIdList;
	}

	/**
	 * Gets the creates the time.
	 *
	 * @return the creates the time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the creates the time.
	 *
	 * @param createTime the new creates the time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the update time.
	 *
	 * @return the update time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * Sets the update time.
	 *
	 * @param updateTime the new update time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
