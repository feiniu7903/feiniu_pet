package com.lvmama.comm.vst.vo;

import java.io.Serializable;

public class VstSuppContactVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5571489940246643582L;

	private Long personId;
	private Long supplierId;
	private String name;
	private String tel;
	private String sex;
	private String mobile;
	private String email;
	private String otherAddress;
	private String job;
	private String personDesc;
	private String cancelFlag;
	private String address;
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getOtherAddress() {
		return otherAddress;
	}
	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getPersonDesc() {
		return personDesc;
	}
	public void setPersonDesc(String personDesc) {
		this.personDesc = personDesc;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
