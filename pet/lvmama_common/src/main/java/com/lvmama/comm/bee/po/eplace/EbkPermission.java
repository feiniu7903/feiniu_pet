/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.bee.po.eplace;

import java.io.Serializable;

/**
 * PermPermission 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class EbkPermission implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6168006937637403784L;
	//columns START
	/** 变量 permissionId . */
	private java.lang.Long permissionId;
	/** 变量 name . */
	private java.lang.String name;
	/** 变量 description . */
	private java.lang.String description;
	/** 变量 parentId . */
	private java.lang.Long parentId;
	/** 变量 bizType . */
	private java.lang.String bizType;
	/** 变量 urlPattern . */
	private java.lang.String urlPattern;
	/** 变量 createTime . */
	private java.util.Date createTime;
	private Long seq;
	
	private Long userId;
	//columns END
	/**
	* PermPermission 的构造函数
	*/
	public EbkPermission() {
	}
	/**
	* PermPermission 的构造函数
	*/
	public EbkPermission(
		java.lang.Long permissionId
	) {
		this.permissionId = permissionId;
	}
	public java.lang.Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(java.lang.Long permissionId) {
		this.permissionId = permissionId;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getDescription() {
		return description;
	}
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	public java.lang.Long getParentId() {
		return parentId;
	}
	public void setParentId(java.lang.Long parentId) {
		this.parentId = parentId;
	}
	public java.lang.String getBizType() {
		return bizType;
	}
	public void setBizType(java.lang.String bizType) {
		this.bizType = bizType;
	}
	public java.lang.String getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(java.lang.String urlPattern) {
		this.urlPattern = urlPattern;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}

}


