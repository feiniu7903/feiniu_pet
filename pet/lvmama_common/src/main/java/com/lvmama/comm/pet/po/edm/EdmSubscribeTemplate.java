package com.lvmama.comm.pet.po.edm;
/**
 * desc:EDM模板信息PO
 * author:尚正元
 * createDate:20120207
 */
import java.io.Serializable;

public class EdmSubscribeTemplate implements Serializable {

	private static final long serialVersionUID = 6995349835251408227L;
	/**
	 * 模板编号
	 */
	private Long tempId;
	/**
	 * 模板名称
	 */
	private String tempName;
	/**
	 * 模板地址
	 */
	private String tempUrl;
	/**
	 * 模板状态
	 */
	private String tempStatus;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 创建用户
	 */
	private String createUser;
	/**
	 * 修改时间
	 */
	private String updateDate;
	/**
	 * 修改用户
	 */
	private String updateUser;
	public Long getTempId() {
		return tempId;
	}
	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public String getTempUrl() {
		return tempUrl;
	}
	public void setTempUrl(String tempUrl) {
		this.tempUrl = tempUrl;
	}
	public String getTempStatus() {
		return tempStatus;
	}
	public void setTempStatus(String tempStatus) {
		this.tempStatus = tempStatus;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
