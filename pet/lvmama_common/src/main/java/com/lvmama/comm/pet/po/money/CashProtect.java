/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.money;

import java.io.Serializable;

/**
 * CashProtect 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashProtect implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2064632209047054021L;
	//columns START
	/** 变量 businessId . */
	private java.lang.String businessId;
	/** 变量 comeFrom . */
	private java.lang.String comeFrom;
	/** 变量 createTime . */
	private java.sql.Date createTime;
	//columns END
	/**
	* CashProtect 的构造函数
	*/
	public CashProtect() {
	}
	/**
	* CashProtect 的构造函数
	*/
	public CashProtect(
		java.lang.String businessId
	) {
		this.businessId = businessId;
	}
	public java.lang.String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(java.lang.String businessId) {
		this.businessId = businessId;
	}
	public java.lang.String getComeFrom() {
		return comeFrom;
	}
	public void setComeFrom(java.lang.String comeFrom) {
		this.comeFrom = comeFrom;
	}
	public java.sql.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.sql.Date createTime) {
		this.createTime = createTime;
	}

}


